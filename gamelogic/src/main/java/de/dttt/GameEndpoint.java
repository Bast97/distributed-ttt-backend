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

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;
import de.dttt.beans.WSBean;
import de.dttt.beans.WSTurn;

@Component
@ServerEndpoint(value = "/game/{gameID}", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class GameEndpoint {
	private Session session;
	private static final Set<GameEndpoint> connections = new CopyOnWriteArraySet<>();
	private String gameID;
	
	JedisPoolConfig config;
	static JedisPool jedisPool;
	Gson gson = new Gson();
	String redis_host = null;
	String redis_port = null;
	private static final String JEDIS_PASSWORD = System.getenv("REDIS_PASSWORD");
	
	public GameEndpoint() {
		redis_host = System.getenv("REDIS_HOST");
		redis_port = System.getenv("REDIS_PORT");
		config = new JedisPoolConfig();
		// JedisPool config
		config.setMaxTotal(10000); // Set the maximum number of connections
		config.setMaxIdle(10000); // Set the maximum number of idle connections

		jedisPool = new JedisPool(config, redis_host, Integer.parseInt(redis_port));
	}

	@OnOpen
	public void onOpen(Session session, @PathParam("gameID") String gameID) throws IOException, EncodeException {
		
		// Set timeout time to 5 minutes
		session.setMaxIdleTimeout(300000);

		this.session = session;
		connections.add(this);

		System.out.println("\nSocket " + session.getId() + " connected to endpoint " + gameID);

		MatchmakerInfo mmInfo = new MatchmakerInfo(gameID);

		Jedis jedis = jedisPool.getResource();
		jedis.auth(JEDIS_PASSWORD);
		try {
			if (jedis.exists(gameID)) { // I think this runs when player 2 joins
				String json = jedis.get(gameID);
				TTTMatch match = gson.fromJson(json, TTTMatch.class);
				match.setUserO(mmInfo.getO());
				json = gson.toJson(match);
				jedis.set(gameID, json);
				this.gameID = gameID;
				jedis.publish(gameID, WSBean.TURN);
			} else if (mmInfo.isValid()) { // I think this runs when player 1 joins
				TTTMatch newMatch = new TTTMatch(gameID, mmInfo.getX());			
				String json = gson.toJson(newMatch);
				jedis.set(gameID, json);
				this.gameID = gameID;
				System.out.println("New Match was created: " + gameID);
			}
		} finally {
			if(jedis != null)
				jedis.close();
		}

		subscribeJedis(this, gameID);
	}

	public void subscribeJedis(GameEndpoint endpoint, String channel_name) {
		new Thread(new Runnable(){
			public void run(){
				redis_host = System.getenv("REDIS_HOST");
				redis_port = System.getenv("REDIS_PORT");
				Jedis subscriberJedis = new Jedis(redis_host, Integer.parseInt(redis_port));
				subscriberJedis.auth(JEDIS_PASSWORD);
				try {
					subscriberJedis.subscribe(new JedisPubSub() {
						@Override
						public void onMessage(String channel, String message) {
							endpoint.onJedisPublish(channel, message);
						}
					}, channel_name);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if(subscriberJedis != null)
					subscriberJedis.close();
				}
			}
		}).start();
	}

	// handle a new message from Redis
	public void onJedisPublish(String channel, String message) {
		Jedis jedis = jedisPool.getResource();
		jedis.auth(JEDIS_PASSWORD);
		try {
			String json = jedis.get(this.gameID);
			TTTMatch match = gson.fromJson(json, TTTMatch.class);

			switch (message) {
				case "TURN":
					broadcast(WSBean.TURN, match);
					break;
				case "GAMEOVER":
					broadcast(WSBean.GAME_OVER, match);
					break;
				default:
					System.out.println("Unknown Message Type!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(jedis != null)
				jedis.close();
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
		// System.out.println("Player " + turn.getUid() + " sent move " + turn.getX() + turn.getY() + " into game " + gameID);

		Jedis jedis = jedisPool.getResource();
		jedis.auth(JEDIS_PASSWORD);
		try {
			if(jedis.exists(gameID)) {
				String json = jedis.get(gameID);
				TTTMatch match = gson.fromJson(json, TTTMatch.class);
				if(match.nextTurn(turn)) {
					json = gson.toJson(match);
					jedis.set(gameID, json);
					jedis.publish(gameID, WSBean.TURN);

					if (match.isOver()) {
						try {
							System.out.println(match.getWinnerUID() + " won!");
						} finally {
							jedis.publish(gameID, WSBean.GAME_OVER);
							System.out.println("Game over! Removing...");
							// jedis.del(gameID);
							MatchmakerCloseMatch.closeMatch(gameID);
						}
					}
				} else {
					System.out.println("Move was illegal! State was not updated.");
				}
			} else {

			}
		} finally {
			if(jedis != null)
				jedis.close();
		}

	}

	@OnClose
	public void onClose(Session session) throws IOException, EncodeException {
		connections.remove(this);
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		System.out.println("Backend Error!");
		System.out.println(throwable.toString());
	}

	private void broadcast(String beantype, TTTMatch message) {

		connections.forEach(endpoint -> {
			synchronized (endpoint) {
				if (endpoint.gameID.equals(message.getGameID())) {
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