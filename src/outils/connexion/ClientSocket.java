package outils.connexion;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

/**
 * Gestion d'un client : cr�ation d'une connexion cliente
 * @author emds
 *
 */

public class ClientSocket {
    
    // propri�t�s
    boolean connexionOk ;

    /**
     * Constructeur
     * @param ip
     * @param port
     * @param leRecepteur
     */
    public ClientSocket(String ip, int port, Object leRecepteur) {
        connexionOk = false;
        try {
            Socket socket = new Socket(ip, port);

            // Lire la réponse du serveur
            byte[] buffer = new byte[7]; // Taille max pour "REFUSED"
            socket.getInputStream().read(buffer);
            String response = new String(buffer).trim();

            if (response.equals("REFUSED")) {
                JOptionPane.showMessageDialog(null, "Le serveur est complet, réessayez ultérieurement");
                socket.close();
                return;
            }

            // Si on reçoit "OK", la connexion est établie
            System.out.println("Connexion serveur réussie");
            connexionOk = true;
            new Connection(socket, leRecepteur);
        } catch (UnknownHostException e) {
            JOptionPane.showMessageDialog(null, "Serveur non disponible");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Problème de connexion au serveur");
        }
    }


    /**
     * @return the connexionOk
     */
    public boolean isConnexionOk() {
        return connexionOk;
    }
    
}