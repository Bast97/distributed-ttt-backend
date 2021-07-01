package de.dttt.beans;

import com.google.gson.Gson;

public class WSTurn implements WSAbstractData {
    private int x;
    private int y;
    private String uid;
    private static Gson gson = new Gson();

    public WSTurn(int x, int y, String uid) {
        this.x = x;
        this.y = y;
        this.uid = uid;
    }

    public WSTurn(String json) {
        WSTurn generated = gson.fromJson(json, WSTurn.class);
        this.x = generated.getX();
        this.y = generated.getY();
        this.uid = generated.getUid();
    }

    public String toJson() {
        return gson.toJson(this);
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
}
