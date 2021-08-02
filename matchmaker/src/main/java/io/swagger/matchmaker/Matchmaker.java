package io.swagger.matchmaker;

import java.util.ArrayList;
import io.swagger.model.NewMatch;
import io.swagger.model.MatchInfo;
import io.swagger.matchmaker.Match;
import java.util.Date;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Matchmaker {

	JedisPoolConfig config;
	static JedisPool jedisPool;
	Gson gson = new Gson();
	String redis_host = null;
	String redis_port = null;
	private static final String JEDIS_PASSWORD = System.getenv("REDIS_PASSWORD");
    
    public Matchmaker() {
		redis_host = System.getenv("REDIS_HOST");
		redis_port = System.getenv("REDIS_PORT");
		config = new JedisPoolConfig();
		// JedisPool config
		config.setMaxTotal(10000); // Set the maximum number of connections
		config.setMaxIdle(10000); // Set the maximum number of idle connections

		jedisPool = new JedisPool(config, redis_host, Integer.parseInt(redis_port));

        
        Jedis jedis = jedisPool.getResource();
		jedis.auth(JEDIS_PASSWORD);
        try {
            if(!jedis.exists("matchlist")) {
                ArrayList<Match> matches = new ArrayList<Match>();
                String json = gson.toJson(matches);
				jedis.set("matchlist", json);
            }
        } finally {
			if(jedis != null)
                jedis.close();
        }
    }

    public void addMatch(Match match) {        
        Jedis jedis = jedisPool.getResource();
		jedis.auth(JEDIS_PASSWORD);
        try {
            if(jedis.exists("matchlist")) {
                String json = jedis.get("matchlist");
                ArrayList<Match> matches = gson.fromJson(json, new TypeToken<ArrayList<Match>>(){}.getType());
                matches.add(match);
                
                json = gson.toJson(matches);
				jedis.set("matchlist", json);
            }
        } finally {
			if(jedis != null)
                jedis.close();
        }
    }

    public int getNumberOfMatches() {
        int size = 0;
        Jedis jedis = jedisPool.getResource();
		jedis.auth(JEDIS_PASSWORD);
        try {
            if(jedis.exists("matchlist")) {
                String json = jedis.get("matchlist");
                ArrayList<Match> matches = gson.fromJson(json, new TypeToken<ArrayList<Match>>(){}.getType());
                size = matches.size();
            }
        } finally {
			if(jedis != null)
                jedis.close();
        }
        return size;
    }

    public void deleteMatch(String matchId) {
        Jedis jedis = jedisPool.getResource();
		jedis.auth(JEDIS_PASSWORD);
        try {
            if(jedis.exists("matchlist")) {
                String json = jedis.get("matchlist");
                ArrayList<Match> matches = gson.fromJson(json, new TypeToken<ArrayList<Match>>(){}.getType());
                
                for (int i = 0; i < matches.size(); i++)  {
                    if(matches.get(i).getMatchId().equals(matchId)) {
                        matches.remove(i);
                        json = gson.toJson(matches);
                        jedis.set("matchlist", json);
                        jedis.close();
                        return;
                    }
                }
            }
        } finally {
			if(jedis != null)
                jedis.close();
        }
    }

    public NewMatch joinMatch(String userId) {
        Jedis jedis = jedisPool.getResource();
		jedis.auth(JEDIS_PASSWORD);
        String json = jedis.get("matchlist");
        ArrayList<Match> matches = gson.fromJson(json, new TypeToken<ArrayList<Match>>(){}.getType());

        try {
            if(jedis.exists("matchlist")) {
                for (int i = 0; i < matches.size(); i++)  {
                    if (matches.get(i).getPlayerCount() == 1) {
                        Match match = matches.get(i);
                        match.setPlayerCount(2);
                        match.setPlayer2Id(userId);

                        matches.set(i, match);
                        json = gson.toJson(matches);
                        jedis.set("matchlist", json);

                        NewMatch newMatch = new NewMatch();
                        newMatch.setMatchId(match.getMatchId());
                        newMatch.setPlayerId(userId);
                        newMatch.setPlayerNum(2);

                        jedis.close();
                        return newMatch;
                    }
                }
            }
        } finally {
			if(jedis != null)
                jedis.close();
        }

        String matchId = "m" + UUID.randomUUID().toString();
        System.out.println("new match: " + matchId);
        Match match = new Match();
        match.setMatchId(matchId);
        match.setPlayerCount(1);
        match.setPlayer1Id(userId);
        this.addMatch(match);

        NewMatch newMatch = new NewMatch();
        newMatch.setMatchId(match.getMatchId());
        newMatch.setPlayerId(userId);
        newMatch.setPlayerNum(1);

        System.out.println("num of matches: " + this.getNumberOfMatches());

        return newMatch;
    }

    public MatchInfo getMatchInfoForMatchId(String matchId) {
        
        Jedis jedis = jedisPool.getResource();
		jedis.auth(JEDIS_PASSWORD);
        String json = jedis.get("matchlist");
        ArrayList<Match> matches = gson.fromJson(json, new TypeToken<ArrayList<Match>>(){}.getType());

        try {
            if(jedis.exists("matchlist")) {
                for (int i = 0; i < matches.size(); i++)  {
                    if(matches.get(i).getMatchId().equals(matchId)) {
                        MatchInfo matchInfo = new MatchInfo();
                        matchInfo.setMatchIdIsValid(true);
                        matchInfo.setPlayer1Id(matches.get(i).getPlayer1Id());
                        matchInfo.setPlayer2Id(matches.get(i).getPlayer2Id());
                        jedis.close();
                        return matchInfo;
                    }
                }
            }
        } finally {
			if(jedis != null)
                jedis.close();
        }

        MatchInfo matchInfo = new MatchInfo();
        matchInfo.setMatchIdIsValid(false);
        return matchInfo;

    }
}