package de.dttt;

import org.glassfish.tyrus.server.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebSocketServer {

    public static void main(String[] args) {
        SpringApplication.run(WebSocketServer.class, args);
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
