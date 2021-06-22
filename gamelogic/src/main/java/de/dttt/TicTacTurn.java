package de.dttt;

public class TicTacTurn {
    private String player;
    private String move;

    public TicTacTurn(String player, String move) {
        // Constructor is never called, validation is done before updating match
        if (move.matches("[ABC][123]")) {
            this.move = move;
            this.player = player;
        }
    }

    public String getPlayerUID() {
        return player;
    }

    public String getMove() {
        return move;
    }
}
