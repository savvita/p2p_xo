package controllers;

import events.Event;
import game.Game;
import game.Player;
import net.P2PClient;

public class GameController extends Thread {
    private Game game;
    private final P2PClient client;
    private Player player;
    public Event onAccepted = new Event();
    public Event onGameUpdated = new Event();
    public Event onGameEnded = new Event();

    public GameController(String host, int port, int rows, int columns) {
        client = new P2PClient(host, port);
        client.onAccepted.add(() -> onAccepted.invoke());
        game = new Game(rows, columns);
    }

    public Player getPlayer() {
        return player;
    }
    public Game getGame() {
        return game;
    }

    public void addPlayer(String name) {
        player = game.addPlayer(new Player(name));
        if(player != null) {
            client.start();
            this.start();
        }
    }

    public void setValue(int i, int j) {
        game.setSymbol(player, i, j);
        client.sendObject(game);
    }

    public void run() {
        if(client.isServer()) {
            client.sendObject(game);
            game = client.getObject(Game.class);
        } else {
            var received = client.getObject(Game.class);
            if(received == null) return;
            game.getPlayer(0).setSymbol('O');
            game.addPlayer(received.getPlayer(0));
            client.sendObject(game);
        }

        do {
            game = client.getObject(Game.class);
            if(game == null) break;

            onGameUpdated.invoke();

            if(game.isGameOver()) {
                onGameEnded.invoke();
                break;
            }
        } while(true);

        client.close();
    }
}
