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

@ServerEndpoint(value = "/{gameID}")
public class GameEndpoint {
	private Session session;
	private static final Set<GameEndpoint> connections = new CopyOnWriteArraySet<>();
	private static HashMap<String, String> users = new HashMap<>(); // TODO:: Users are never removed

	@OnOpen
	public void onOpen(Session session, @PathParam("gameID") String gameID) throws IOException, EncodeException {

		this.session = session;
		connections.add(this);

		System.out.println("\nSocket " + session.getId() + " connected to endpoint " + gameID);

	}

	@OnMessage
	public void onMessage(Session session, @PathParam("gameID") String gameID, String data)
			throws IOException, EncodeException {
		System.out.println(data);
		
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
