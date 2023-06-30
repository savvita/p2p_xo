package game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Game implements Serializable {

    private final Field field;
    private final List<Player> players;
    private final int PLAYERS_COUNT = 2;

    public Game() {
        this(3, 3);
    }

    public Game(int rows, int columns) {
        field = new Field(rows, columns);
        players = new ArrayList<>();
    }

    public Player getPlayer(int idx) {
        return players.get(idx);
    }
    public Field getField() {
        return field;
    }

    public Player addPlayer(Player player) {
        if(players == null || players.size() >= PLAYERS_COUNT) {
            return null;
        }

        if(players.size() == 0) {
            player.setSymbol('X');
            players.add(player);
        } else if(players.size() == 1) {
            player.setSymbol('O');
            players.add(player);
        }

        return player;
    }

    public boolean setSymbol(Player player, int row, int column) {
        return field.setSymbol(row, column, player.getSymbol());
    }

    public boolean isGameOver() {
        return isDraw() || isHaveWinner();
    }

    public boolean isHaveWinner()
    {
        for (int i = 0; i < field.ROWS; i++)
        {
            if (field.isRawComplete(i)) return true;
        }

        for (int i = 0; i < field.COLUMNS; i++)
        {
            if (field.isColumnComplete(i)) return true;
        }

        return field.isMainDiagonalComplete() || field.isAdditionalDiagonalComplete();
    }

    public boolean isDraw()
    {
        return !field.checkEmptySpace() && !isHaveWinner();
    }
}
