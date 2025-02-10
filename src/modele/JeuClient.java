package modele;

import javax.swing.JPanel;

import controleur.Controle;
import outils.connexion.Connection;

/**
 * Gestion du jeu côté client
 * @author emds
 *
 */
public class JeuClient extends Jeu {

	// propriétés
	private Connection connection ;
	
	/**
	 * Controleur
	 * @param controle
	 */
	public JeuClient(Controle controle) {
		super.controle = controle ;
	}
	
	@Override
	public void setConnection(Connection connection) {
		this.connection = connection ;
	}

	@Override
	public void reception(Connection connection, Object info) {
		// arrivée du panel des murs
		if(info instanceof JPanel) {
			controle.evenementModele(this, "ajout panel murs", info);
		// arrivée d'un label correspondant à un personnage
		}else if(info instanceof Label) {
			controle.evenementModele(this, "ajout joueur", info);
		// arrivée du contenu du chat	
		}else if(info instanceof String) {
			controle.evenementModele(this, "remplace chat", info);
		}
		
	}

	@Override
	public void deconnection(Connection connection) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Envoi d'une information vers l'ordinateur distant
	 * @param info
	 */
	public void envoi(Object info) {
		super.envoi(connection, info) ;
	}

}
