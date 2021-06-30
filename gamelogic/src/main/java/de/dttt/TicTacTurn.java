package de.dttt;

public class TicTacTurn {
    private int x;
    private int y;
    private int color;

    public TicTacTurn(int x, int y, int color) {
        // Constructor is never called, validation is done before updating match
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getColor() {
        return color;
    }
}
