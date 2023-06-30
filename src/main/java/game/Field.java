package game;

import java.io.Serializable;

public class Field implements Serializable {
    public final int ROWS;
    public final int COLUMNS;
    public final char EMPTY_SYMBOL = ' ';
    private final char[][] cells;

    public Field() {
        this(3, 3);
    }
    public Field(int rows, int columns) {
        ROWS = Math.max(rows, 0);
        COLUMNS = Math.max(columns, 0);
        cells = new char[ROWS][COLUMNS];
        initializeField();
    }

    public boolean setSymbol(int row, int column, char symbol) {
        if(row < 0 || row >= ROWS || column < 0 || column >= COLUMNS || cells[row][column] != EMPTY_SYMBOL) {
            return false;
        }

        cells[row][column] = symbol;
        return true;
    }

    public boolean checkEmptySpace() {
        for(char[] row : cells) {
            for(char cell : row) {
                if(cell == EMPTY_SYMBOL) return true;
            }
        }

        return false;
    }

    private void initializeField() {
        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLUMNS; j++) {
                cells[i][j] = EMPTY_SYMBOL;
            }
        }
    }

    public char getCellValue(int row, int column) {
        return cells[row][column];
    }

    public boolean isRawComplete(int index) {
        int countX = 0;
        int countO = 0;

        for(char cell : cells[index]) {
            if(cell == 'X') countX++;
            else if(cell == 'O') countO++;
        }

        return countX == COLUMNS || countO == COLUMNS;
    }

    public boolean isColumnComplete(int index)
    {
        for (int i = 0; i < ROWS - 1; i++)
        {
            if (cells[i][index] == EMPTY_SYMBOL || cells[i][index] != cells[i + 1][index])
                return false;
        }

        return true;
    }

    public boolean isMainDiagonalComplete()
    {
        int min = Math.min(ROWS, COLUMNS) - 1;
        for (int i = 0; i < min; i++)
        {
            if (cells[i][i] == EMPTY_SYMBOL || cells[i][i] != cells[i + 1][i + 1])
                return false;
        }

        return true;
    }

    public boolean isAdditionalDiagonalComplete()
    {
        int min = Math.min(ROWS, COLUMNS) - 1;
        for (int i = 0; i < min; i++)
        {
            if (cells[i][min - i] == EMPTY_SYMBOL || cells[i][min - i] != cells[i + 1][min - i - 1])
                return false;
        }

        return true;
    }
}
