package game;

public class Player {
    private String name;
    private char symbol;

    public String getName() {
        return name;
    }
    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public Player(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
    }
    public Player(String name) {
        this.name = name;
    }
}
