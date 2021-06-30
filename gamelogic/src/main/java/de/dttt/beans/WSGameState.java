package de.dttt.beans;

import com.google.gson.Gson;

public class WSGameState {
	private int[] gamestate;
	private int whoseTurn; // 0 for X, 1 for O

	public WSGameState(int[] gamestate, int whoseTurn) {
		this.gamestate = gamestate;
		this.whoseTurn = whoseTurn;
	}

	public WSGameState(String json) {
		Gson gson = new Gson();
		WSGameState generated = gson.fromJson(json, WSGameState.class);
		this.gamestate = generated.gamestate;
	}

	public int getWhoseTurn() {
		return whoseTurn;
	}

	public void setWhoseTurn(int whoseTurn) {
		this.whoseTurn = whoseTurn;
	}

	public int[] getGamestate() {
		return gamestate;
	}

	public void setGamestate(int[] gamestate) {
		this.gamestate = gamestate;
	}

	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
