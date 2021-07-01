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

import de.dttt.beans.WSBean;
import de.dttt.beans.WSTurn;

@ServerEndpoint(value = "/{gameID}", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class GameEndpoint {
	private Session session;
	private TTTMatch matchState;
	private static final Set<GameEndpoint> connections = new CopyOnWriteArraySet<>();
	private static HashMap<String, TTTMatch> games = new HashMap<>();

	@OnOpen
	public void onOpen(Session session, @PathParam("gameID") String gameID) throws IOException, EncodeException {

		this.session = session;
		connections.add(this);

		System.out.println("\nSocket " + session.getId() + " connected to endpoint " + gameID);

		if (games.containsKey(gameID)) {
			this.matchState = games.get(gameID);
			broadcast(matchState);
		} else if (true) { // TODO: Call Matchmaker for Match Details here
			TTTMatch newMatch = new TTTMatch(gameID, "123", "456");
			games.put(gameID, newMatch);
			this.matchState = newMatch;
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
		if (matchState.nextTurn(turn)) {
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
