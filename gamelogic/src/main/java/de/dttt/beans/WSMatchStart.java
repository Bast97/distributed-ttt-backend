package de.dttt.beans;

import com.google.gson.Gson;

public class WSMatchStart implements WSAbstractData {
    private int color;
    private boolean turn;
    private static Gson gson = new Gson();

    public WSMatchStart(int color, boolean turn) {
        this.color = color;
        this.turn = turn;
    }

    public WSMatchStart(String json) {
        WSMatchStart generated = gson.fromJson(json, WSMatchStart.class);
        this.color = generated.color;
        this.turn = generated.turn;
    }

    public String toJson() {
        return gson.toJson(this);
    }

    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }
    public boolean isTurn() {
        return turn;
    }
    public void setTurn(boolean turn) {
        this.turn = turn;
    }
}
