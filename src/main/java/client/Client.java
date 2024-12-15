package client;

import tools.ChatTerminal;
import tools.Tools;

import java.io.*;
import java.net.Socket;

/**
 * Classe client
 */
public class Client {

    private static Client instance;

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    private final String host;
    private final int port;

    private boolean running = true;

    private Client() {
        instance = this;
        this.host = Tools.askIp("Adresse IP du serveur:");
        ChatTerminal.printl("Adresse IP du serveur: " + host);
        this.port = Tools.askPort();
        ChatTerminal.printl("Port du serveur: " + port);
        try {
            this.socket = new Socket(host, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            ChatTerminal.printl("Connexion vers " + socket.getRemoteSocketAddress() + " reussie");
        } catch (IOException e) {

            Tools.fatalError("Impossible de se connecter au serveur");
        }
    }

    public void disconnect() {
        try {
            running = false;
            socket.close();
            reader.close();
            writer.close();
        } catch (Exception e) {
            Tools.forceClose();
        }
    }

    private synchronized void send(String message) {
        try {
            writer.write(message + "\n");
            writer.flush();
        } catch (Exception e) {
            Tools.fatalError("Impossible d'envoyer le message !");
        }
    }

    private synchronized String receive() {
        try {
            return reader.readLine();
        } catch (Exception e) {
            Tools.fatalError("Impossible de recevoir le message !");
        }
        return null;
    }

    public static Client getInstance() {
        return instance;
    }

    private void run() {
        //new Listener().start();
        while (running) {
            String message = ChatTerminal.readLine();
            send(message);
            ChatTerminal.printl("[ Vous ]> " + message);
        }
    }

    private class Listener extends Thread {

        @Override
        public void run() {
            while (running) {
                String message = receive();
                if (message == null) {
                    Tools.fatalError("Connexion perdue !");
                }
                ChatTerminal.printl(message);
                Tools.wait(100);
            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }


}
