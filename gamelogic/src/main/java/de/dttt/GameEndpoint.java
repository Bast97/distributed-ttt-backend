package de.dttt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;
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

@ServerEndpoint(value = "/{gameID}", decoders = TurnDecoder.class, encoders = TurnEncoder.class)
public class GameEndpoint {
    private Session session;
    private static TTTMatch matchState = new TTTMatch("Abra", "123", "456"); // TODO: Where should this be initialised?
    private static final Set<GameEndpoint> gameEndpoints = new CopyOnWriteArraySet<>();
    private static HashMap<String, String> users = new HashMap<>();
    private Logger logger = Logger.getLogger("Game");

    @OnOpen
    public void onOpen(Session session, @PathParam("gameID") String gameID) throws IOException, EncodeException {

        this.session = session;
        gameEndpoints.add(this);
        System.out.println("\nSocket " + session.getId() + " connected to game " + gameID);
        logger.fine("Socket Connected: " + session + " to game " + gameID);
    }

    @OnMessage
    public void onMessage(Session session, @PathParam("gameID") String gameID, TicTacTurn turn)
            throws IOException, EncodeException {
        logger.fine("Player " + turn.getPlayerUID() + " sent move " + turn.getMove() + " into game " + gameID);
        System.out.println("Player " + turn.getPlayerUID() + " sent move " + turn.getMove() + " into game " + gameID);
        users.put(turn.getPlayerUID(), session.getId());
        if (matchState.nextTurn(turn)) {
            broadcast(matchState);
            System.out.println("Move was legal. State updated.");
        } else {
            System.out.println("Move was illegal! State was not updated.");
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        gameEndpoints.remove(this);
        logger.fine("Socket disconnected: " + session.getId());
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

        gameEndpoints.forEach(endpoint -> {
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
