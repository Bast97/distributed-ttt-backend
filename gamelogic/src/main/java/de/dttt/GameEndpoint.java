package de.dttt;

import java.io.IOException;
import java.util.HashMap;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import de.dttt.redis.RedisConfig;
import de.dttt.redis.MessagePublisher;
import de.dttt.redis.RedisMessagePublisher;
import de.dttt.redis.repo.MatchRepository;
import redis.clients.jedis.Jedis;
import de.dttt.redis.Match;
import de.dttt.beans.WSBean;
import de.dttt.beans.WSTurn;

@ServerEndpoint(value = "/{gameID}", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
@ContextConfiguration(classes = RedisConfig.class)
public class GameEndpoint {
	private Session session;
	private TTTMatch matchState;
	private static final Set<GameEndpoint> connections = new CopyOnWriteArraySet<>();
	private static HashMap<String, TTTMatch> games = new HashMap<>();
	
	Jedis jedis = new Jedis("35.204.146.65", 6379);
	Gson gson = new Gson();
	
    @Autowired(required = true)
    private MatchRepository matchRepository;
    @Autowired
    private MessagePublisher redisMessagePublisher;

	@OnOpen
	public void onOpen(Session session, @PathParam("gameID") String gameID) throws IOException, EncodeException {

		this.session = session;
		connections.add(this);

		System.out.println("\nSocket " + session.getId() + " connected to endpoint " + gameID);

		MatchmakerInfo mmInfo = new MatchmakerInfo(gameID);

		if (games.containsKey(gameID)) { // I think this runs when player 2 joins
			this.matchState = games.get(gameID);
			matchState.setUserO(mmInfo.getO());
			String json = jedis.get(gameID);
			TTTMatch match = gson.fromJson(json, TTTMatch.class);
			match.setUserO(mmInfo.getO());
			json = gson.toJson(match);
			jedis.set(gameID, json);

			
			broadcast(match);
			broadcast(matchState);
		} else if (mmInfo.isValid()) { // I think this runs when player 1 joins
			TTTMatch newMatch = new TTTMatch(gameID, mmInfo.getX());
			games.put(gameID, newMatch);
			this.matchState = newMatch;
			
			String json = gson.toJson(newMatch);
			jedis.set(gameID, json);
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

				broadcast(match);
				System.out.println("Move was legal. State updated.");
				if (match.isOver()) {
					try {
						System.out.println(match.getWinnerUID() + " won!");
					} finally {
						System.out.println("Game over! Removing...");
					}
					games.remove(gameID);
				}
			} else {
				System.out.println("Move was illegal! State was not updated.");
			}
		} else {

		}

		if (matchState.nextTurn(turn)) {

			// int[] gamestate = {1, 2, 3, 4, 5, 6, 7, 8, 9};
			// int gamestate = 1;
			// System.out.println("about to save");
			// Match match = new Match(gameID, "player1id", "player2id", gamestate);
			// String json = gson.toJson(match);




			// try {
			// 	jedis.set(gameID, json);
			// 	jedis.close();
			// 	matchRepository.save(match);
			// 	System.out.println("saved succesfully");
			// } catch (Exception e) {
			// 	System.out.println(e.toString());
			// }

			broadcast(matchState);
			System.out.println("Move was legal. State updated.");
			if (matchState.isOver()) {
				try {
					System.out.println(matchState.getWinnerUID() + " won!");
				} finally {
					System.out.println("Game over! Removing...");
				}
				games.remove(gameID);
			}
		} else {
			System.out.println("Move was illegal! State was not updated.");
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

	private void broadcast(TTTMatch message) {

		connections.forEach(endpoint -> {
			synchronized (endpoint) {
				if (endpoint.matchState == message) {
					try {
						endpoint.session.getBasicRemote()
								.sendObject(new WSBean(WSBean.TURN, message.toGameState()));
					} catch (IOException | EncodeException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

}
