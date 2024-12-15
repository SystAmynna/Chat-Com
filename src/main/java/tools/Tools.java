package tools;

import client.Client;

import javax.swing.*;

/**
 * Classe utilitaire
 */
public class Tools {

    private static final int DEFAULT_PORT = 6666;

    /**
     * Constructeur privé (non utilisé)
     */
    private Tools() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Affiche un message d'erreur et ferme l'application
     * @param message : message d'erreur
     */
    public static void fatalError(String message) {
        ChatTerminal.setStartOfLine("");
        ChatTerminal.printl("\n \u001B[30m  \u001B[41m Erreur fatale: " + message + " \u001B[0m \n");
        // Ferme l'application
        close(1);
    }

    /**
     * Ferme l'application
     * @param n : code de sortie
     */
    private static void close(int n) {
        ChatTerminal.close();
        if (Client.getInstance() != null) Client.getInstance().disconnect();

        System.exit(n);
    }
    /**
     * Ferme l'application normalement
     */
    public static void close() {
        close(0);
    }
    public static void forceClose() {
        System.exit(0);
    }

    public static String askString(String message) {
        String sol = ChatTerminal.getStartOfLine();
        ChatTerminal.setStartOfLine(message + "> ");
        String r = ChatTerminal.readLine();
        ChatTerminal.setStartOfLine(sol);
        return r;
    }

    public static String askIp(String message) {
        final String r = askString(message);
        return r.isEmpty() ? "localhost" : r;
    }

    public static int askPort() {
        int port = 0;
        boolean valid = false;
        String message = "Port du serveur (6666 par defaut) ";
        while (!valid) {
            String r = askString(message);
            if (r.isEmpty()) return DEFAULT_PORT;
            try {
                port = Integer.parseInt(r);
            } catch (NumberFormatException e) {
                message = "Port invalide (entier requis)";
                port = 0;
                continue;
            }
            if (port <= 1024) message = "Port invalide (port > 1024)";
            else if (port > 65535) message = "Port invalide (port <= 65535)";
            else valid = true;
        }
        return port;
    }

    public static synchronized void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            fatalError("Impossible d'attendre");
        }
    }


}
