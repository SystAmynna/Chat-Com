package tools;

import client.Client;

import javax.swing.*;

/**
 * Classe utilitaire
 */
public class Tools {

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
        // Ouvre une pop-up avec le message d'erreur (JOptionPane)
        JOptionPane.showMessageDialog(null, message, "Fatal error", JOptionPane.ERROR_MESSAGE);
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
        return JOptionPane.showInputDialog(null, message, "Input", JOptionPane.QUESTION_MESSAGE);
    }

    public static String askIp(String message) {
        final String r = askString(message);
        return r.isEmpty() ? "localhost" : r;
    }

    public static int askPort() {
        int port = 0;
        boolean valid = false;
        String message = "Port du serveur (6666 par defaut):";
        while (!valid) {
            String r = askString(message);
            if (r.isEmpty()) return 6666;
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
