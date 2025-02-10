package modele;

import java.util.ArrayList;
import java.util.Hashtable;

import controleur.Controle;
import controleur.Global;
import outils.connexion.Connection;

/**
 * Gestion du jeu côté serveur
 * @author emds
 *
 */
public class JeuServeur extends Jeu implements Global {

	// propriétés
	private ArrayList<Mur> lesMurs = new ArrayList<Mur>() ;
	private Hashtable<Connection, Joueur> lesJoueurs = new Hashtable<Connection, Joueur>() ;
	private ArrayList<Joueur> lesJoueursDansLordre = new ArrayList<Joueur>() ;
	
	/**
	 * Constructeur
	 * @param controle
	 */
	public JeuServeur(Controle controle) {
		super.controle = controle ;
		// initialisation du rang du dernier label mémorisé
		Label.setNbLabel(0);
	}
	
	/**
	 * Génération des murs
	 */
	public void constructionMurs() {
		for (int k=0 ; k<NBMURS ; k++) {
			lesMurs.add(new Mur()) ;
			controle.evenementModele(this, "ajout mur", lesMurs.get(lesMurs.size()-1).getLabel().getjLabel());
		}
	}
	
	/**
	 * Demande au controleur d'ajouter un joueuer dans l'arêne
	 * @param label
	 */
	public void nouveauLabelJeu(Label label) {
		controle.evenementModele(this, "ajout joueur", label.getjLabel());
	}
	
	/**
	 * Envoi à tous les clients
	 */
	public void envoi(Object info) {
		for (Connection connection : lesJoueurs.keySet()) {
			super.envoi(connection, info);
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
				// envoi des précédents joueurs au nouveau joueur
				for(Joueur joueur : lesJoueursDansLordre) {
					super.envoi(connection, joueur.getLabel());
					super.envoi(connection, joueur.getMessage());
				}
				// initialisation du nouveau joueur (positionnement aléatoire...)
				lesJoueurs.get(connection).initPerso(infos[1], Integer.parseInt(infos[2]), lesJoueurs, lesMurs);
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
