package de.dttt.beans;

import com.google.gson.Gson;

public class WSTurn implements WSAbstractData {
    private int x;
    private int y;
    private int color;
    private static Gson gson = new Gson();

    public WSTurn(int x, int y, int color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public WSTurn(String json) {
        WSTurn generated = gson.fromJson(json, WSTurn.class);
        this.x = generated.x;
        this.y = generated.y;
        this.color = generated.color;
    }

    public String toJson() {
        return gson.toJson(this);
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
    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }

    
}
