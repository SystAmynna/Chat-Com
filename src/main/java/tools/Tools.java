package tools;

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

        System.exit(n);
    }
    /**
     * Ferme l'application normalement
     */
    public static void close() {
        close(0);
    }


}
