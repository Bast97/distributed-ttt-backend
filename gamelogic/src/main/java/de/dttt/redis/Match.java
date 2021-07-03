package de.dttt.redis;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("Match")
public class Match implements Serializable {

    @Id private String matchId;
    private String player1Id;
    private String player2Id;
    private int gamestate;

    public Match(String matchId, String player1Id, String player2Id, int gamestate) {
        this.matchId = matchId;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.gamestate = gamestate;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getPlayer1Id() {
        return player1Id;
    }

    public void setPlayer1Id(String player1Id) {
        this.player1Id = player1Id;
    }

    public String getPlayer2Id() {
        return player2Id;
    }

    public void setPlayer2Id(String player2Id) {
        this.player2Id = player2Id;
    }

    public int getGamestate() {
        return gamestate;
    }

    public void setGamestate(int gamestate) {
        this.gamestate = gamestate;
    }

    @Override
    public String toString() {
        return "Match{" + "matchId='" + matchId + '\'' + ", player1Id='" + player1Id + '\'' + ", player2Id=" + player2Id + ", gamestate=" + gamestate + '}';
    }
}