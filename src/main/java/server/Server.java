package server;

import tools.ChatTerminal;
import tools.Tools;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    public static final Server INSTANCE = new Server();

    public final List<Session> sessions = new ArrayList<>();

    private boolean running = true;

    private final int port;

    private ServerSocket serverSocket;

    private Server() {
        port = Tools.askPort();
        try {
            serverSocket = new ServerSocket(port);
            ChatTerminal.printl("Port definit sur " + port);
        } catch (IOException e) {
            Tools.fatalError("Impossible de démarrer le serveur !");
        }
    }

    private void run() {
        ChatTerminal.printl("Demarrage sur le port " + port);
        while (running) {
            try {
                Session s = new Session(serverSocket.accept());
                sessions.add(s);
                s.start();
            } catch (IOException e) {
                Tools.fatalError("Impossible de démarrer une session !");
            }
        }
    }

    public synchronized void close() {
        INSTANCE.running = false;
        try {
            INSTANCE.serverSocket.close();
        } catch (IOException e) {
            Tools.fatalError("Impossible de fermer le serveur !");
        }
    }

    public void broadcast(String message, Session sender) {
        for (Session s : sessions) {
            if (s != sender) s.send(message);
        }
    }

    public static void main(String[] args) {
        INSTANCE.run();
    }




}
