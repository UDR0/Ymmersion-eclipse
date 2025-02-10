package outils.connexion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JOptionPane;

/**
 * Gestion de la connexion entre 2 ordinateurs distants
 * @author emds
 *
 */
public class Connection extends Thread {
	
	// propri�t�s
	private Object leRecepteur ;
	private ObjectInputStream in ;
	private ObjectOutputStream out ;
	private AtomicInteger activeConnections;

	/**
	 * Constructeur
	 * @param socket
	 * @param leRecepteur
	 */
	public Connection(Socket socket, Object leRecepteur) {
		this.leRecepteur = leRecepteur ;
		// cr�ation du canal de sortie pour envoyer des informations
		try {
			this.out = new ObjectOutputStream(socket.getOutputStream()) ;
		} catch (IOException e) {
			System.out.println("erreur cr�ation canal out : "+e);
			System.exit(0);
		}
		// cr�ation du canal d'entr�e pour recevoir des informations
		try {
			this.in = new ObjectInputStream(socket.getInputStream()) ;
		} catch (IOException e) {
			System.out.println("erreur cr�ation canal in : "+e);
			System.exit(0);
		}
		// d�marrage du thread d'�coute (attente d'un message de l'ordi distant)
		this.start() ;
		// envoi de l'instance de connexion vers le recepteur (g�n�ralement le controleur)
		((controleur.Controle)this.leRecepteur).setConnection(this) ;
	}
	
	public synchronized void envoi(Object unObjet) {
		try {
			this.out.reset();
			out.writeObject(unObjet);
			out.flush();
		} catch (IOException e) {
			System.out.println("erreur d'envoi sur le canal out : "+e);
		}
	}
	
	/**
	 * M�thode thread qui permet d'attendre des message provenant de l'ordi distant
	 */
	public void run() {
		boolean inOk = true ;
		Object reception ;
		while (inOk) {
			try {
				reception = in.readObject();
				((controleur.Controle)this.leRecepteur).receptionInfo(this, reception);
			} catch (ClassNotFoundException e) {
				System.out.println("erreur de classe sur r�ception : "+e);
				System.exit(0);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "l'ordinateur distant est d�connect�");
				inOk = false ;
				try {
					in.close();
				} catch (IOException e1) {
					System.out.println("la fermeture du canal d'entr�e a �chou� : "+e);
				}
				activeConnections.decrementAndGet();
			}
		}
		
	}
	
}
