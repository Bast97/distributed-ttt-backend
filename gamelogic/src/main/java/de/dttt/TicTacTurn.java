package de.dttt;

public class TicTacTurn {
    private String player;
    private String move;

    public TicTacTurn(String player, String move) {
        //TODO: Validate
        this.player = player;
        this.move = move;
    }

    public String getPlayerUID() {
        return player;
    }

    public String getMove() {
        return move;
    }
}
