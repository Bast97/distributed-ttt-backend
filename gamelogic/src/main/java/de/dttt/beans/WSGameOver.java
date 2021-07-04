package de.dttt.beans;

import com.google.gson.Gson;

public class WSGameOver implements WSAbstractData {
    private int winner;
    private static Gson gson = new Gson();

    public WSGameOver(int winner) {
        this.winner = winner;
    }

    public WSGameOver(String json) {
        WSGameOver generated = gson.fromJson(json, WSGameOver.class);
        this.winner = generated.getWinner();
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public String toJson() {
        return gson.toJson(this);
    }

}
