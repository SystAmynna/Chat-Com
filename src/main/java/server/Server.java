package server;

import tools.ChatTerminal;
import tools.Tools;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    public static final Server INSTANCE = new Server();

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
                new Session(serverSocket.accept()).start();
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

    public static void main(String[] args) {
        INSTANCE.run();
    }


}
