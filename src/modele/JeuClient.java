package modele;

import javax.swing.JPanel;

import controleur.Controle;
import outils.connexion.Connection;

/**
 * Gestion du jeu c�t� client
 * @author emds
 *
 */
public class JeuClient extends Jeu {

	// propri�t�s
	private Connection connection ;
	private String playerName;
	private String playerClass;
	private int characterSkin;
	
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
	
	public void setPlayerInfo(String name, String playerClass, int skin) {
	    this.playerName = name;
	    this.playerClass = playerClass;
	    this.characterSkin = skin;
	    
	    // Send player information to the server
	    envoi("player_info:" + name + "," + playerClass + "," + skin);
	}

	@Override
	public void reception(Connection connection, Object info) {
		// arriv�e du panel des murs
		if(info instanceof JPanel) {
			controle.evenementModele(this, "ajout panel murs", info);
		// arriv�e d'un label correspondant � un personnage
		}else if(info instanceof Label) {
		    // Retrieve the player's class from the stored data
		    Joueur joueur = (Joueur) info;
		    String playerName = joueur.getPseudo();
		    String playerClass = joueur.getClasse(); // Assuming there's a getClasse() method

		    controle.evenementModele(this, "ajout joueur", playerName, playerClass);
		}
		// arriv�e du contenu du chat	
		}else if(info instanceof String) {
		    String message = (String) info;

		    // Handling player info update from the server
		    if (message.startsWith("update_player:")) {
		        String[] data = message.substring(14).split(",");
		        String name = data[0];
		        String playerClass = data[1];
		        controle.evenementModele(this, "update_player_display", name, playerClass);
		    } else {
		        controle.evenementModele(this, "remplace chat", info);
		    }
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
