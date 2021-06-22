package de.dttt;

import java.util.HashMap;

public class TTTMatch {
	private Square whoseTurn = Square.X, winner = null;
	private HashMap<String, Square> squares;
	private Boolean gameOver = false;
	private String userX, userO, gameID;
	private int moveCount = 0;

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

	public String getWinnerUID() {
		switch (this.winner) {
			case X:
				return this.getUserX();
			case O:
				return this.getUserO();
			default:
				return null;
		}
	}

	public TTTMatch(String gameID, String x, String o) {
		this.gameID = gameID;
		this.userX = x;
		this.userO = o;
		squares = new HashMap<String, Square>(9, 1);
	}

	public Boolean nextTurn(TicTacTurn turn) {
		if (!turn.getMove().matches("[ABC][123]"))
			return false;
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
					moveCount++;
					whoseTurn = Square.O;
					break;
				case O:
					squares.put(turn.getMove(), Square.O);
					moveCount++;
					whoseTurn = Square.X;
					break;
			}
			if (moveCount >= 5 && evaluate())
				this.gameOver = true;
			if (moveCount == 9)
				this.gameOver = true;
			return true; // valid turn, return true
		}
	}

	public Boolean evaluate() {

		// I'm a bit ashamed of this
		if (squares.get("A1") != null && squares.get("A1") == squares.get("B1")
				&& squares.get("B1") == squares.get("C1")) {
			this.winner = squares.get("C1");
		}
		if (squares.get("A2") != null && squares.get("A2") == squares.get("B2")
				&& squares.get("B2") == squares.get("C2")) {
			this.winner = squares.get("C2");
		}
		if (squares.get("A3") != null && squares.get("A3") == squares.get("B3")
				&& squares.get("B3") == squares.get("C3")) {
			this.winner = squares.get("C3");
		}
		if (squares.get("A1") != null && squares.get("A1") == squares.get("A2")
				&& squares.get("A2") == squares.get("A3")) {
			System.out.println(squares.get("A3"));
			this.winner = squares.get("A3");
		}
		if (squares.get("B1") != null && squares.get("B1") == squares.get("B2")
				&& squares.get("B2") == squares.get("B3")) {
			this.winner = squares.get("B3");
		}
		if (squares.get("C1") != null && squares.get("C1") == squares.get("C2")
				&& squares.get("C2") == squares.get("C3")) {
			this.winner = squares.get("C3");
		}
		if (squares.get("A1") != null && squares.get("A1") == squares.get("B2")
				&& squares.get("B2") == squares.get("C3")) {
			this.winner = squares.get("C3");
		}
		if (squares.get("C1") != null && squares.get("C1") == squares.get("B2")
				&& squares.get("B2") == squares.get("A3")) {
			this.winner = squares.get("A3");
		}

		if (this.winner != null)
			return true;
		else
			return false;
	}
}
