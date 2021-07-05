package de.dttt;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import de.dttt.beans.WSBean;
import de.dttt.beans.WSTurn;

@ServerEndpoint(value = "/{gameID}", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class GameEndpoint {
	private Session session;
	private static final Set<GameEndpoint> connections = new CopyOnWriteArraySet<>();
	private String gameID;
	
	String redis_host = System.getenv("REDIS_HOST");
	String redis_port = System.getenv("REDIS_PORT");
	Jedis jedis = new Jedis(redis_host, Integer.parseInt(redis_port));
	Gson gson = new Gson();
	

	@OnOpen
	public void onOpen(Session session, @PathParam("gameID") String gameID) throws IOException, EncodeException {

		this.session = session;
		connections.add(this);

		System.out.println("\nSocket " + session.getId() + " connected to endpoint " + gameID);

		MatchmakerInfo mmInfo = new MatchmakerInfo(gameID);



		if (jedis.exists(gameID)) { // I think this runs when player 2 joins
			String json = jedis.get(gameID);
			TTTMatch match = gson.fromJson(json, TTTMatch.class);
			match.setUserO(mmInfo.getO());
			json = gson.toJson(match);
			jedis.set(gameID, json);
			this.gameID = gameID;
			broadcast(WSBean.TURN, match);
		} else if (mmInfo.isValid()) { // I think this runs when player 1 joins
			TTTMatch newMatch = new TTTMatch(gameID, mmInfo.getX());			
			String json = gson.toJson(newMatch);
			jedis.set(gameID, json);
			this.gameID = gameID;
			System.out.println("New Match was created");
		}

	}

	@OnMessage
	public void onMessage(Session session, @PathParam("gameID") String gameID, WSBean bean)
			throws IOException, EncodeException {

		switch (bean.getType()) {
			case "TURN":
				WSTurn turn = new WSTurn(bean.getData());
				handleTurn(turn, gameID);
				break;
			default:
				System.out.println("Unknown Message Type!");
		}
	}

	private void handleTurn(WSTurn turn, String gameID) {
		System.out.println(
				"Player " + turn.getUid() + " sent move " + turn.getX() + turn.getY() + " into game " + gameID);

		if(jedis.exists(gameID)) {
			String json = jedis.get(gameID);
			System.out.println(json);
			TTTMatch match = gson.fromJson(json, TTTMatch.class);
			if(match.nextTurn(turn)) {
				json = gson.toJson(match);
				jedis.set(gameID, json);

				broadcast(WSBean.TURN, match);
				System.out.println("Move was legal. State updated.");
				if (match.isOver()) {
					try {
						System.out.println(match.getWinnerUID() + " won!");
					} finally {
						broadcast(WSBean.GAME_OVER, match);
						jedis.del(gameID);
						System.out.println("Game over! Removing...");
					}
				}
			} else {
				System.out.println("Move was illegal! State was not updated.");
			}
		} else {

		}

	}

	@OnClose
	public void onClose(Session session) throws IOException, EncodeException {
		connections.remove(this);
		System.out.println("Socket disconnected: " + session.getId());
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		// Do error handling here
	}

	private void broadcast(String beantype, TTTMatch message) {

		System.out.println("broadcast to: ");
		connections.forEach(endpoint -> {
			synchronized (endpoint) {
				if (endpoint.gameID.equals(message.getGameID())) {
					System.out.println(endpoint);
					try {
						switch (beantype) {
							case WSBean.TURN:
								endpoint.session.getBasicRemote()
										.sendObject(new WSBean(beantype, message.toGameState()));
								break;
							case WSBean.GAME_OVER:
								endpoint.session.getBasicRemote()
										.sendObject(new WSBean(beantype, message.toGameOver()));
								break;
						
							default:
								break;
						}
					} catch (IOException | EncodeException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

}