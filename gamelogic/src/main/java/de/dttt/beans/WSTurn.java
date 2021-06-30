package de.dttt.beans;

import com.google.gson.Gson;

public class WSTurn {
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
		this.x = generated.x;
		this.y = generated.y;
		this.uid = generated.uid;
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
	public String getPlayerUID() {
		return uid;
	}
	public void setPlayerUID(String uid) {
		this.uid = uid;
	}

	
}
