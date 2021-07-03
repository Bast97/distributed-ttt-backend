package de.dttt;

import org.glassfish.tyrus.server.Server;

public class WebSocketServer {

    public static void main(String[] args) {
        runServer();
    }

    public static void runServer() {
        Server server = new Server("localhost", 8765, "/game", null, GameEndpoint.class);
        try {
            server.start();
            Thread.currentThread().join();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            server.stop();

        }
    }
}
