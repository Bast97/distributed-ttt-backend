package de.dttt.beans;

import com.google.gson.Gson;

public class WSGameState {
    private int[] gamestate;
    private static Gson gson = new Gson();

    public WSGameState(int[] gamestate) {
        this.gamestate = gamestate;
    }

    public WSGameState(String json) {
        WSGameState generated = gson.fromJson(json, WSGameState.class);
        this.gamestate = generated.gamestate;
    }

    public int[] getGamestate() {
        return gamestate;
    }

    public void setGamestate(int[] gamestate) {
        this.gamestate = gamestate;
    }
}
