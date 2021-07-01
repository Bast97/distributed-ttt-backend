package io.swagger.matchmaker;

public class Match {
    
    private String matchId = null;
    private String player1Id = null;
    private String player2Id = null;
    private int playerCount = 0;
    
    public Match() {

    }


    public String getMatchId() {
        return this.matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }


    public String getPlayer1Id() {
        return this.player1Id;
    }

    public void setPlayer1Id(String player1Id) {
        this.player1Id = player1Id;
    }


    public String getPlayer2Id() {
        return this.player2Id;
    }

    public void setPlayer2Id(String player2Id) {
        this.player2Id = player2Id;
    }

    public int getPlayerCount() {
        return this.playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }


    public void addPlayer1(String player1Id) {
        this.player1Id = player1Id;
        this.setPlayerCount(1);
    }

    public void addPlayer2(String player2Id) {
        this.player2Id = player2Id;
        this.setPlayerCount(2);
    }

}