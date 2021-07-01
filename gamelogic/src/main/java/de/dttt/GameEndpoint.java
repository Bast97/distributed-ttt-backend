package de.dttt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
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

import de.dttt.beans.WSBean;
import de.dttt.beans.WSGameState;
import de.dttt.beans.WSTurn;

@ServerEndpoint(value = "/{gameID}")
public class GameEndpoint {
	private Session session;
	private static Map<String, Session> endpoints = new ConcurrentHashMap<>();
	private static Gson gson = new Gson();

	@OnOpen
	public void onOpen(Session session, @PathParam("gameID") String gameID) throws IOException, EncodeException {

		this.session = session;
		if(endpoints.get(session.getId()) == null) {
			endpoints.put(session.getId(), session);
		}

		System.out.println("\nSocket " + session.getId() + " connected to endpoint " + gameID);
		
	}

	@OnMessage
	public void onMessage(Session session, @PathParam("gameID") String gameID, String data)
			throws IOException, EncodeException {
		System.out.println(data);
		WSBean bean = gson.fromJson(data, WSBean.class);
		switch(bean.getType()) {
			case WSBean.TURN:
				System.out.println("Received TURN message");
				handlerTurn(session, bean.getData());
				break;
			default:
				System.out.println("Unknown type of message");
		}
	}

	@OnClose
	public void onClose(Session session) throws IOException, EncodeException {
		this.endpoints.remove(session.getId());
		System.out.println("Socket disconnected: " + session.getId());
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		// Do error handling here
	}

	private void handlerTurn(Session session, String data) {
		WSTurn turnBean = gson.fromJson(data, WSTurn.class);
		System.out.println(turnBean.getX());
		System.out.println(turnBean.getY());

		// Überprüfe Daten

		// Schreibe Match Informationen

		// Regiere dann auf die Änderungen an der REDIS Datenbank

		// TESTWEISE:

		int[] gamestate = {1,1,2,0,0,0,0,0,0};
		WSGameState dataBean = new WSGameState(gamestate);
		try {
			System.out.println(new WSBean(WSBean.TURN, dataBean).toJson());
			session.getBasicRemote().sendText(new WSBean(WSBean.TURN, dataBean).toJson());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
