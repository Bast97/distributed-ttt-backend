package de.dttt.beans;

import com.google.gson.Gson;

public class WSGameOver implements WSAbstractData {
    private boolean victory;
    private boolean tie;
    private static Gson gson = new Gson();

    public WSGameOver(boolean victory, boolean tie) {
        this.victory = victory;
        this.tie = tie;
    }

    public WSGameOver(String json) {
        WSGameOver generated = gson.fromJson(json, WSGameOver.class);
        this.victory = generated.isVictory();
        this.tie = generated.isTie();
    }

    public String toJson() {
        return gson.toJson(this);
    }

    public boolean isVictory() {
        return victory;
    }

    public void setVictory(boolean victory) {
        this.victory = victory;
    }

    public boolean isTie() {
        return tie;
    }

    public void setTie(boolean tie) {
        this.tie = tie;
    }
}
