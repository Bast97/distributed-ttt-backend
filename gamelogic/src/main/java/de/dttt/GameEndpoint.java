package de.dttt;

import java.io.IOException;
import java.util.ArrayList;
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

import org.graalvm.compiler.nodes.NodeView.Default;

import com.google.gson.Gson;

@ServerEndpoint(value = "/{gameID}", decoders = TurnDecoder.class, encoders = MatchEncoder.class)
public class GameEndpoint {
	private Session session;
	private TTTMatch matchState;
	private static final Set<GameEndpoint> connections = new CopyOnWriteArraySet<>();
	private static HashMap<String, TTTMatch> games = new HashMap<>();
	private static HashMap<String, String> users = new HashMap<>(); // TODO:: Users are never removed

	private static Gson gson = new Gson();

	@OnOpen
	public void onOpen(Session session, @PathParam("gameID") String gameID) throws IOException, EncodeException {
		System.out.println("\n");
		this.session = session;
		connections.add(this);
		System.out.println("Socket " + session.getId() + " connected to endpoint " + gameID);
		System.out.println("Player " + connections.size() + " joined");
		// session.getBasicRemote().sendText("hello, you are player " + connections.size());
		
		// TicTacTurn turn = new TicTacTurn(0, 0, connections.size());
		// WSBean bean = new WSBean("TURN", new Gson().toJson(turn).toString());
		// session.getBasicRemote().sendText(new Gson().toJson(bean));

		if (games.containsKey(gameID)) {
			this.matchState = games.get(gameID);
		} else if (true) { // TODO: Call Matchmaker for Match Details here
			TTTMatch newMatch = new TTTMatch(gameID, "123", "456");
			games.put(gameID, newMatch);
			this.matchState = newMatch;
			System.out.println("New Match was created");
		}

	}

	@OnMessage
	public void onMessage(Session session, @PathParam("gameID") String gameID, WSBean message)
			throws IOException, EncodeException {
		switch (message.getType()) {
			case "TURN":
				TicTacTurn turn = gson.fromJson(message.getData(), TicTacTurn.class);
				System.out.println("x: " + turn.getX() + " y: " + turn.getY() + " color: " + turn.getColor());
				boolean isLegalMove = this.matchState.nextTurn(turn);
				System.out.println("is a legal move: " + isLegalMove);
				
				WSBean bean = new WSBean("TURN", new Gson().toJson(this.matchState.getSquares()).toString());
				session.getBasicRemote().sendText(new Gson().toJson(bean));
				break;

			default:
				System.out.println("unknown WebSocket message received");
				break;
		}
		// System.out.println(turn.getData());
		// System.out.println("Player " + turn.getPlayerUID() + " sent move " + turn.getMove() + " into game " + gameID);
		// users.put(turn.getPlayerUID(), session.getId());
		// if (matchState.nextTurn(turn)) {
		// 	broadcast(matchState);
		// 	System.out.println("Move was legal. State updated.");
		// 	if (matchState.isOver()) {
		// 		try {
		// 			System.out.println(matchState.getWinnerUID() + " won!");
		// 		} finally {
		// 			System.out.println("Game over! Removing...");
		// 		}
		// 		games.remove(gameID);
		// 	}
		// } else {
		// 	System.out.println("Move was illegal! State was not updated.");
		// }
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

	private void broadcast(TTTMatch message) throws IOException, EncodeException {

		ArrayList<String> sessions = new ArrayList<>();
		sessions.add(users.get(this.matchState.getUserX()));
		sessions.add(users.get(this.matchState.getUserO()));

		connections.forEach(endpoint -> {
			synchronized (endpoint) {
				if (sessions.contains(endpoint.session.getId())) {
					try {
						endpoint.session.getBasicRemote().sendObject(message);
					} catch (IOException | EncodeException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

}
