package tools;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;

import java.io.IOException;

/**
 * Classe de gestion du terminal
 */
public class ChatTerminal {

    /**
     * Instance unique de la classe
     */
    public static final ChatTerminal INSTANCE = new ChatTerminal();

    /**
     * Terminal
     */
    private Terminal terminal;
    /**
     * Lecteur non bloquant
     */
    private NonBlockingReader reader;

    /**
     * StringBuilder pour la lecture
     */
    private final StringBuilder sb = new StringBuilder();

    /**
     * Constructeur privé
     */
    private ChatTerminal() {
        try {
            terminal = TerminalBuilder.builder()
                    .system(true)
                    .build();
            reader = terminal.reader();
        } catch (IOException e) {
            Tools.fatalError("Impossible de créer le terminal");
        }
    }

    /**
     * Ferme le terminal
     */
    public static void close() {
        try {
            INSTANCE.terminal.close();
            INSTANCE.reader.close();
        } catch (IOException e) {
            Tools.fatalError("Impossible de fermer le terminal");
        }
    }

    /**
     * Lit un caractère
     * @return char : le caractère lu
     */
    private char read() {
        try {
            return (char) reader.read();
        } catch (IOException e) {
            Tools.fatalError("Impossible de lire le terminal");
            return 0;
        }
    }

    /**
     * Lit une ligne
     * @return String : la ligne lue
     */
    public static String readLine() {
        // Importe le StringBuilder
        StringBuilder sb = INSTANCE.sb;
        // le caractère de lecture
        char c;
        // Vide le StringBuilder
        sb.setLength(0);
        // Tant que le caractère lu n'est pas un retour à la ligne
        while ((c = INSTANCE.read()) != '\n' && c != '\r') {
            switch (c) {
                case '\b': // Si c'est un backspace
                    // Supprime le dernier caractère
                    if (!sb.isEmpty()) sb.deleteCharAt(sb.length() - 1);
                    break;
                case '\u001B': // Si c'est ESC
                    sb.delete(0, sb.length());
                    break;
                default: // ajoute le caractère
                    sb.append(c);
                    break;
            }
            // Nettoyer la ligne
            INSTANCE.terminal.writer().print("\r\033[K");
            // Afficher la ligne
            INSTANCE.terminal.writer().print(sb);
        }
        // Retourne la ligne
        return sb.toString();
    }

    /**
     * Affiche un message
     * @param message : le message à afficher
     */
    public static synchronized void printl(String message) {
        INSTANCE.terminal.writer().print("\u001B[2K");
        INSTANCE.terminal.writer().println("\r" + message);
        INSTANCE.terminal.writer().print(INSTANCE.sb);
    }


}




