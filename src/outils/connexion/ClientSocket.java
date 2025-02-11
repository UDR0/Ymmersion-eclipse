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
	public ClientSocket (String ip, int port, Object leRecepteur) {
		connexionOk = false ;
		try {
			Socket socket = new Socket(ip, port);
			System.out.println("connexion serveur r�ussie");
			connexionOk = true;
			new Connection(socket, leRecepteur) ;
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "serveur non disponible");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "IP incorrecte");
		}
	}

	/**
	 * @return the connexionOk
	 */
	public boolean isConnexionOk() {
		return connexionOk;
	}
	
}