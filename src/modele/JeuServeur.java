package modele;

import java.util.Hashtable;

import controleur.Controle;
import controleur.Global;
import outils.connexion.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Gestion du jeu côté serveur
 * @author emds
 *
 */
public class JeuServeur extends Jeu implements Global {

	// propriétés
	private Hashtable<Connection, Joueur> lesJoueurs = new Hashtable<Connection, Joueur>();
	private ArrayList<Joueur> lesJoueursDansLordre = new ArrayList<Joueur>();

	/**
	 * Constructeur
	 * @param controle
	 */
	public JeuServeur(Controle controle) {
		super.controle = controle;
		// initialisation du rang du dernier label mémorisé
		Label.setNbLabel(0);
	}

	/**
	 * Demande au controleur d'ajouter un joueur dans l'arène
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
		lesJoueurs.put(connection, new Joueur(this));
	}

	@Override
	public void reception(Connection connection, Object info) {
		String[] infos = ((String)info).split(SEPARE);
		String laPhrase;
		switch(Integer.parseInt(infos[0])) {
			// un nouveau joueur vient d'arriver
			case PSEUDO:
				// envoi des précédents joueurs au nouveau joueur
				for(Joueur joueur : lesJoueursDansLordre) {
					super.envoi(connection, joueur.getLabel());
					super.envoi(connection, joueur.getMessage());
				}
				// initialisation du nouveau joueur (positionnement aléatoire...)
				lesJoueurs.get(connection).initPerso(
					    infos[1],                       // pseudo
					    Integer.parseInt(infos[2]),     // numPerso
					    infos[3],                       // classe <--- new parameter
					    lesJoueurs
					);
				// insertion du nouveau joueur dans la liste dans l'ordre, pour l'envoyer dans l'ordre aux joueurs suivants
				lesJoueursDansLordre.add(lesJoueurs.get(connection));
				laPhrase = "***"+lesJoueurs.get(connection).getPseudo()+" vient de se connecter ***";
				controle.evenementModele(this, "ajout phrase", laPhrase);
				break;
			case CHAT:
			    // 1) Get the current time in HH:mm
			    String timeStamp = new SimpleDateFormat("HH:mm").format(new Date());
			    
			    // 2) Retrieve class and pseudo from the Joueur
			    Joueur sender = lesJoueurs.get(connection);
			    String classe = sender.getClasse();
			    String pseudo = sender.getPseudo();
			    
			    // 3) The chat text itself is in infos[1]
			    String messageText = infos[1];
			    
			    // 4) Combine them all
			    laPhrase = timeStamp 
			               + " [" + classe + "] "
			               + pseudo
			               + " > "
			               + messageText;
			    
			    // 5) Pass it to the controller
			    controle.evenementModele(this, "ajout phrase", laPhrase);
			    break;
		}
	}
	
	@Override
	public void deconnection(Connection connection) {
		// TODO Auto-generated method stub
		
	}

}
