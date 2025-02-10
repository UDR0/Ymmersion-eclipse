package modele;

import controleur.Controle;
import outils.connexion.Connection;

/**
 * Informations et m�thodes communes aux jeux client et serveur
 * @author emds
 *
 */
public abstract class Jeu {

	// propri�t�s
	protected Controle controle ;
	
	/**
	 * R�ception d'une connexion (pour communiquer avec un ordinateur distant)
	 * @param connection
	 */
	public abstract void setConnection(Connection connection) ;
	
	/**
	 * R�ception d'une information provenant de l'ordinateur distant
	 * @param connection
	 * @param info
	 */
	public abstract void reception(Connection connection, Object info) ;
	
	/**
	 * Envoi d'une information vers l'ordinateur distant
	 * @param connection
	 * @param info
	 */
	public void envoi(Connection connection, Object info) {
		connection.envoi(info);
	}
	
	/**
	 * D�connexion de l'ordinateur distant
	 * @param connection
	 */
	public abstract void deconnection(Connection connection) ;
	
}
