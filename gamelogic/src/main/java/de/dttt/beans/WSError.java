package de.dttt.beans;

import com.google.gson.Gson;

public class WSError implements WSAbstractData {
    private String msg;
    private boolean fatal;
    private int[] gamestate;

    private static Gson gson = new Gson();

    public WSError(String msg, boolean fatal, int[] gamestate) {
        this.msg = msg;
        this.fatal = fatal;
        this.gamestate = gamestate;
    }

    public WSError(String json) {
        WSError generated = gson.fromJson(json, WSError.class);
        this.msg = generated.getMsg();
        this.fatal = generated.isFatal();
        this.gamestate = generated.getGamestate();
    }

    public String toJson() {
        return gson.toJson(this);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isFatal() {
        return fatal;
    }

    public void setFatal(boolean fatal) {
        this.fatal = fatal;
    }

    public int[] getGamestate() {
        return gamestate;
    }

    public void setGamestate(int[] gamestate) {
        this.gamestate = gamestate;
    }

    
}
