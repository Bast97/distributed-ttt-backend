package de.dttt;

import java.util.HashMap;

public class TTTMatch {
	private Square whoseTurn = Square.X, winner = null;
	private HashMap<String, Square> squares;
	private Boolean gameOver = false;
	private String userX, userO, gameID;

	public String getUserX() {
		return userX;
	}

	public String getUserO() {
		return userO;
	}

	public String getGameID() {
		return gameID;
	}

	public Boolean isOver() {
		return gameOver;
	}

	public TTTMatch(String gameID, String x, String o) {
		this.gameID = gameID;
		this.userX = x;
		this.userO = o;
		squares = new HashMap<String, Square>(9, 1);
	}

	public Boolean nextTurn(TicTacTurn turn) {
		if (this.gameOver)
			return false;
		switch (whoseTurn) {
			case X:
				if (!turn.getPlayerUID().equals(userX)) {
					return false;
				}
				break;
			case O:
				if (!turn.getPlayerUID().equals(userO)) {
					return false;
				}
				break;
			default:
				return false;
		}
		if (squares.get(turn.getMove()) != null)
			return false;
		else {
			switch (whoseTurn) {
				case X:
					squares.put(turn.getMove(), Square.X);
					break;
				case O:
					squares.put(turn.getMove(), Square.O);
					break;
			}
			if (evaluateWinner())
				this.gameOver = true;
			else
				switch (whoseTurn) {
					case X:
						whoseTurn = Square.O;
						break;
					case O:
						whoseTurn = Square.X;
						break;
				}
			return true; // valid turn, return true
		}
	}

	public Boolean evaluateWinner() {
		// Clever function that determines if there is a winner; sets MatchState.winner
		// if there is and returns true
		return false;
	}
}
