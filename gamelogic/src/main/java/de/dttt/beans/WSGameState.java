package de.dttt.beans;

import com.google.gson.Gson;

public class WSGameState implements WSAbstractData {
    private int[] gamestate;
    private static Gson gson = new Gson();

    public WSGameState(int[] gamestate) {
        this.gamestate = gamestate;
    }

    public WSGameState(String json) {
        WSGameState generated = gson.fromJson(json, WSGameState.class);
        this.gamestate = generated.getGamestate();
    }

    public int[] getGamestate() {
        return gamestate;
    }

    public void setGamestate(int[] gamestate) {
        this.gamestate = gamestate;
    }

    public String toJson() {
        return gson.toJson(this);
    }
}
