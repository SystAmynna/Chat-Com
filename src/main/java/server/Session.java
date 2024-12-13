package server;

import tools.ChatTerminal;
import tools.Tools;

import java.io.*;
import java.net.Socket;

public class Session extends Thread {

    private final Socket socket;

    private BufferedReader reader;
    private BufferedWriter writer;

    public Session(Socket socket) {
        this.socket = socket;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            Tools.fatalError("Impossible de créer la session !");
        }
    }

    private synchronized void send(String message) {
        try {
            writer.write(message + "\n");
            writer.flush();
        } catch (Exception e) {
            disconnect();
        }
    }

    private synchronized String receive() {
        try {
            return reader.readLine();
        } catch (Exception e) {
            return "!exit";
        }
    }

    public void disconnect() {
        try {
            socket.close();
            ChatTerminal.printl("\033[36mClient déconnecté\033[0m");
        } catch (IOException e) {
            ChatTerminal.printl("\033[36mClient déconnecté\033[0m");
        }
    }

    @Override
    public void run() {
        ChatTerminal.printl("\033[36mNouvelle connexion\033[0m");
        while (true) {
            String message = receive();
            if (message == null) continue;
            ChatTerminal.printl("\033[36m" + socket.getRemoteSocketAddress() + ":\033[0m " + message);
            send("Message reçu");
        }
    }




}
