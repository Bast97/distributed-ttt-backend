package de.dttt;

import java.util.HashMap;

public class TTTMatch {
	private Square whoseTurn = Square.X, winner = null;
	private Square[][] squares;
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
		squares = new Square[3][3];
		squares[0][0] = Square.EMPTY;
		squares[0][1] = Square.EMPTY;
		squares[0][2] = Square.EMPTY;
		squares[1][0] = Square.EMPTY;
		squares[1][1] = Square.EMPTY;
		squares[1][2] = Square.EMPTY;
		squares[2][0] = Square.EMPTY;
		squares[2][1] = Square.EMPTY;
		squares[2][2] = Square.EMPTY;

		// squares = [[Square.EMPTY, Square.EMPTY, Square.EMPTY],
		// 			[Square.EMPTY, Square.EMPTY, Square.EMPTY],
		// 			[Square.EMPTY, Square.EMPTY, Square.EMPTY]];
	}

	public Boolean nextTurn(TicTacTurn turn) {
		// if (!turn.getMove().matches("[ABC][123]"))
		// 	return false;
		if (this.gameOver)
			return false;
		// switch (whoseTurn) {
		// 	case X:
		// 		if (!turn.getPlayerUID().equals(userX)) {
		// 			return false;
		// 		}
		// 		break;
		// 	case O:
		// 		if (!turn.getPlayerUID().equals(userO)) {
		// 			return false;
		// 		}
		// 		break;
		// 	default:
		// 		return false;
		// }
		if (squares[turn.getY()][turn.getX()] != Square.EMPTY) {
			System.out.println("there is already a move on field: " + turn.getY() + " " + turn.getX());
			return false;
		} else {
			switch(turn.getColor()) {
				case 1:
					squares[turn.getY()][turn.getX()] = Square.X;
					moveCount++;
					break;
				case 2:
					squares[turn.getY()][turn.getX()] = Square.O;
					moveCount++;
					break;
				default:
					System.out.println("unknown player number");
					break;

			}
			// switch (whoseTurn) {
			// 	case X:
			// 		squares.put(turn.getMove(), Square.X);
			// 		moveCount++;
			// 		whoseTurn = Square.O;
			// 		break;
			// 	case O:
			// 		squares.put(turn.getMove(), Square.O);
			// 		moveCount++;
			// 		whoseTurn = Square.X;
			// 		break;
			// }
			if (moveCount >= 5 && evaluate())
				this.gameOver = true;
			if (moveCount == 9)
				this.gameOver = true;
			return true; // valid turn, return true
		}
	}

	public Boolean evaluate() {

		// I'm a bit ashamed of this
		if (squares[0][0] != null && squares[0][0] == squares[1][0]
				&& squares[1][0] == squares[2][0]) {
			this.winner = squares[0][0];
		}
		if (squares[0][1] != null && squares[0][1] == squares[1][1]
				&& squares[1][1] == squares[2][1]) {
			this.winner = squares[0][1];
		}
		if (squares[0][2] != null && squares[0][2] == squares[1][2]
				&& squares[1][2] == squares[2][2]) {
			this.winner = squares[0][2];
		}
		if (squares[0][0] != null && squares[0][0] == squares[0][1]
				&& squares[0][1] == squares[0][2]) {
			this.winner = squares[0][0];
		}
		if (squares[1][0] != null && squares[1][0] == squares[1][1]
				&& squares[1][1] == squares[1][2]) {
			this.winner = squares[1][0];
		}
		if (squares[2][0] != null && squares[2][0] == squares[2][1]
				&& squares[2][1] == squares[2][2]) {
			this.winner = squares[2][0];
		}
		if (squares[0][0] != null && squares[0][0] == squares[1][1]
				&& squares[1][1] == squares[2][2]) {
			this.winner = squares[0][0];
		}
		if (squares[0][2] != null && squares[0][2] == squares[1][1]
				&& squares[1][1] == squares[2][0]) {
			this.winner = squares[0][2];
		}

		if (this.winner != null)
			return true;
		else
			return false;
	}

	public Square[][] getSquares() {
		return squares;
	}
}
