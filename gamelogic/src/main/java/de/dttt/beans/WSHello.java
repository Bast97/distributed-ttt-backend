package de.dttt.beans;

import com.google.gson.Gson;

public class WSHello {
	private String uid;
	private static Gson gson = new Gson();

	public String getPlayerUID() {
		return uid;
	}

	public void setPlayerUID(String uid) {
		this.uid = uid;
	}

	public WSHello(String json) {
		WSHello generated = gson.fromJson(json, WSHello.class);
		this.uid = generated.uid;
	}

	public String toJson() {
		return gson.toJson(this);
	}
}
