package net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class Messaging {
    static String getString(Socket socket) throws IOException {
        DataInputStream in = new DataInputStream(socket.getInputStream());
        return in.readUTF();
    }

    static void sendString(Socket socket, String msg) throws IOException {
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF(msg);
    }

    static <T> boolean sendObj(Socket socket, T obj) {
        String json = getJson(obj);

        if(json != null) {
            try {
                sendString(socket, json);
                return true;
            } catch(Exception ex) {}
        }
        return false;
    }

    static <T> T getObj(Socket socket, Class<T> t) {
        try {
            String response = getString(socket);
            return getObjFromJson(response, t);
        } catch (Exception ex) {}
        return null;
    }

    private static <T> String getJson(T obj) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.toJson(obj);
    }

    private static <T> T getObjFromJson(String json, Class<T> t) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(json, t);
    }
}
