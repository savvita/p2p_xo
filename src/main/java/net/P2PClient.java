package net;

import events.Event;

import java.net.ServerSocket;
import java.net.Socket;

public class P2PClient {
    private final int PORT;
    private final String HOST;
    private ServerSocket server;
    private final int TIMEOUT;
    private Socket socket;
    public P2PClient(String host, int port) {
        this(host, port, 100000);
    }

    public P2PClient(String host, int port, int timeout) {
        HOST = host;
        PORT = port;
        TIMEOUT = timeout;
    }

    public Event onAccepted = new Event();

    public void start() {
        try {
            server = new ServerSocket(PORT);
            server.setSoTimeout(TIMEOUT);
            socket = server.accept();
            onAccepted.invoke();
        } catch(Exception ex) {
            try {
                socket = new Socket(HOST, PORT);
                onAccepted.invoke();
            } catch (Exception e) {}
        }
    }

    public <T> boolean sendObject(T obj) {
        return Messaging.sendObj(socket, obj);
    }

    public <T> T getObject(Class<T> t) {
        return Messaging.getObj(socket, t);
    }

    public boolean isServer() {
        return server != null;
    }

    public void close() {
        try {
            if (server != null) {
                server.close();
            }
        } catch (Exception ex) {}
    }
}
