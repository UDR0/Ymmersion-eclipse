package modele;

import java.util.ArrayList;
import java.util.Hashtable;

import controleur.Controle;
import controleur.Global;
import outils.connexion.Connection;

/**
 * Gestion du jeu c�t� serveur
 * @author emds
 *
 */
public class JeuServeur extends Jeu implements Global {

	// propri�t�s
	private ArrayList<Mur> lesMurs = new ArrayList<Mur>() ;
	private Hashtable<Connection, Joueur> lesJoueurs = new Hashtable<Connection, Joueur>() ;
	private Hashtable<Connection, String> lesClasses = new Hashtable<>();
	private ArrayList<Joueur> lesJoueursDansLordre = new ArrayList<Joueur>() ;
	
	/**
	 * Constructeur
	 * @param controle
	 */
	public JeuServeur(Controle controle) {
		super.controle = controle ;
		// initialisation du rang du dernier label m�moris�
		Label.setNbLabel(0);
	}
	
	/**
	 * G�n�ration des murs
	 */
	public void constructionMurs() {
		for (int k=0 ; k<NBMURS ; k++) {
			lesMurs.add(new Mur()) ;
			controle.evenementModele(this, "ajout mur", lesMurs.get(lesMurs.size()-1).getLabel().getjLabel());
		}
	}
	
	/**
	 * Demande au controleur d'ajouter un joueuer dans l'ar�ne
	 * @param label
	 */
	public void nouveauLabelJeu(Label label) {
		controle.evenementModele(this, "ajout joueur", joueur.getLabel(), joueur.getClasse());
	}
	
	/**
	 * Envoi � tous les clients
	 */
	public void envoi(Object info) {
		for (Connection connection : lesJoueurs.keySet()) {
		    super.envoi(connection, info);

		    // Send player class update
		    Joueur joueur = lesJoueurs.get(connection);
		    if (joueur != null && lesClasses.containsKey(connection)) {
		        super.envoi(connection, "update_player:" + joueur.getPseudo() + "," + lesClasses.get(connection));
		    }
		}
	}

	@Override
	public void setConnection(Connection connection) {
		lesJoueurs.put(connection, new Joueur(this)) ;
	}

	@Override
	public void reception(Connection connection, Object info) {
		String[] infos = ((String)info).split(SEPARE) ;
		String laPhrase ;
		switch(Integer.parseInt(infos[0])) {
			// un nouveau joueur vient d'arriver
			case PSEUDO : 
				// envoi des murs au nouveau joueur
				controle.evenementModele(this, "envoi panel murs", connection);
				// envoi des pr�c�dents joueurs au nouveau joueur
				for(Joueur joueur : lesJoueursDansLordre) {
					super.envoi(connection, joueur.getLabel());
					super.envoi(connection, joueur.getMessage());
				}
				// initialisation du nouveau joueur (positionnement al�atoire...)
				
				// Store player class
				String playerClass = infos[2];
				lesClasses.put(connection, playerClass);

				// Initialize player (positioning, etc.)
				lesJoueurs.get(connection).initPerso(infos[1], Integer.parseInt(infos[3]), lesJoueurs, lesMurs);
				// insertion du nouveau joueur dans la liste dans l'ordre, pour l'envoyer dans l'ordre aux joueurs suivants
				lesJoueursDansLordre.add(lesJoueurs.get(connection)) ;
				laPhrase = "***"+lesJoueurs.get(connection).getPseudo()+" vient de se connecter ***" ;
				controle.evenementModele(this, "ajout phrase", laPhrase);
				break ;
			case CHAT :
				laPhrase = lesJoueurs.get(connection).getPseudo()+" > "+infos[1] ;
				controle.evenementModele(this, "ajout phrase", laPhrase);
				break ;
		}
	}
	
	@Override
	public void deconnection(Connection connection) {
		// TODO Auto-generated method stub
		
	}

}
