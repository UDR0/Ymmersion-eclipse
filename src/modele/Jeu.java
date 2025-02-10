package modele;

import controleur.Controle;
import outils.connexion.Connection;

/**
 * Informations et méthodes communes aux jeux client et serveur
 * @author emds
 *
 */
public abstract class Jeu {

	// propriétés
	protected Controle controle ;
	
	/**
	 * Réception d'une connexion (pour communiquer avec un ordinateur distant)
	 * @param connection
	 */
	public abstract void setConnection(Connection connection) ;
	
	/**
	 * Réception d'une information provenant de l'ordinateur distant
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
	 * Déconnexion de l'ordinateur distant
	 * @param connection
	 */
	public abstract void deconnection(Connection connection) ;
	
}
