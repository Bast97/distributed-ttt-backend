package de.dttt;

import de.dttt.beans.WSGameOver;
import de.dttt.beans.WSGameState;
import de.dttt.beans.WSTurn;

public class TTTMatch {
	private Square whoseTurn = Square.X, winner = null;
	private Square[][] squares = { { Square.EMPTY, Square.EMPTY, Square.EMPTY }, { Square.EMPTY, Square.EMPTY, Square.EMPTY }, { Square.EMPTY, Square.EMPTY, Square.EMPTY } };
	private Boolean gameOver = false;
	private String userX, userO, gameID;
	private int moveCount = 0;

	public TTTMatch(String gameID, String x, String o) {
		this.gameID = gameID;
		this.userX = x;
		this.userO = o;
	}

	public TTTMatch(String gameID, String x){
		this(gameID, x, "");
	}

	public String getUserX() {
		return userX;
	}

	public void setUserO(String userO) {
		this.userO = userO;
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

	public Boolean nextTurn(WSTurn turn) {
		int chosenSquare = 3 * turn.getX() + turn.getY();
		if (chosenSquare < 0 || chosenSquare > 8)
			return false;
		if (this.gameOver)
			return false;
		switch (whoseTurn) {
			case X:
				if (!turn.getUid().equals(userX)) {
					return false;
				}
				break;
			case O:
				if (!turn.getUid().equals(userO)) {
					return false;
				}
				break;
			default:
				return false;
		}
		if (squares[turn.getX()][turn.getY()] != Square.EMPTY)
			return false;
		else {
			switch (whoseTurn) {
				case X:
					squares[turn.getX()][turn.getY()] = Square.X;
					moveCount++;
					whoseTurn = Square.O;
					break;
				case O:
					squares[turn.getX()][turn.getY()] = Square.O;
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
		if (squares[0][0] != Square.EMPTY && squares[0][0] == squares[1][0] && squares[1][0] == squares[2][0]) {
			this.winner = squares[2][0];
		}
		if (squares[0][1] != Square.EMPTY && squares[0][1] == squares[1][1] && squares[1][1] == squares[2][1]) {
			this.winner = squares[2][1];
		}
		if (squares[0][2] != Square.EMPTY && squares[0][2] == squares[1][2] && squares[1][2] == squares[2][2]) {
			this.winner = squares[2][2];
		}
		if (squares[0][0] != Square.EMPTY && squares[0][0] == squares[0][1] && squares[0][1] == squares[0][2]) {
			this.winner = squares[0][2];
		}
		if (squares[1][0] != Square.EMPTY && squares[1][0] == squares[1][1] && squares[1][1] == squares[1][2]) {
			this.winner = squares[1][2];
		}
		if (squares[2][0] != Square.EMPTY && squares[2][0] == squares[2][1] && squares[2][1] == squares[2][2]) {
			this.winner = squares[2][2];
		}
		if (squares[0][0] != Square.EMPTY && squares[0][0] == squares[1][1] && squares[1][1] == squares[2][2]) {
			this.winner = squares[2][2];
		}
		if (squares[2][0] != Square.EMPTY && squares[2][0] == squares[1][1] && squares[1][1] == squares[0][2]) {
			this.winner = squares[0][2];
		}

		if (this.winner != null)
			return true;
		else
			return false;
	}

	public WSGameState toGameState() {
		int whoseTurn;
		if (this.whoseTurn == Square.X)
			whoseTurn = 1;
		else
			whoseTurn = 2;

		int[] stateArray = new int[9];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				try {
					switch (this.squares[j][i]) {
						case X:
							stateArray[3 * i + j] = 1;
							break;
						case O:
							stateArray[3 * i + j] = 2;
							break;
						default:
							stateArray[3 * i + j] = 0;
							break;
					}
				} catch (NullPointerException e) {
					stateArray[3 * i + j] = 0;
				}
			}
		}
		return new WSGameState(stateArray, whoseTurn);
	}

	public WSGameOver toGameOver() {
		int winnerInt = 0;
		if (winner == Square.X) {
			winnerInt = 1;
		} else if (winner == Square.O) {
			winnerInt = 2;
		} else {
			winnerInt = 0;
		}
			
		return new WSGameOver(winnerInt);
	}
}
