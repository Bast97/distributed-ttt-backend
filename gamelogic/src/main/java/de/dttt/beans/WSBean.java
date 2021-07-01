package de.dttt.beans;

import com.google.gson.Gson;

public class WSBean {
    private String type;
    private String data;

    public static final String INIT = "INIT";
    public static final String TURN = "TURN";
    public static final String MATCH_START = "MATCHSTART";
    public static final String GAME_OVER = "GAMEOVER";
    public static final String ERROR = "ERROR";

    private static Gson gson = new Gson();

    public WSBean(String type, WSAbstractData data) {
        this.type = type;
        this.data = data.toJson();
    }

    public WSBean(String json) {
        WSBean generated = gson.fromJson(json, WSBean.class);
        this.type = generated.getType();
        this.data = generated.getData();
    }

    public String toJson() {
        return gson.toJson(this);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
