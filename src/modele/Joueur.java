package modele;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import controleur.Global;
import outils.connexion.Connection;

/**
 * Gestion des joueurs
 * @author emds
 *
 */
public class Joueur extends Objet implements Global {

	// propriétés
	private String pseudo ;
	private int numPerso ;
	private Label message ;
	private JeuServeur jeuServeur ;
	private int vie ; // vie restante du joueur
	private int orientation ; // tourné vers la gauche (0) ou vers la droite (1)
	private int etape ; // numéro d'étape dans l'animation
	
	/**
	 * Constructeur
	 */
	public Joueur(JeuServeur jeuServeur) {
		this.jeuServeur = jeuServeur ;
		vie = 10 ;
		etape = 1 ;
		orientation = DROITE ;
	}
	
	/**
	 * @return the pseudo
	 */
	public String getPseudo() {
		return pseudo;
	}

	/**
	 * Affiche le personnage et son message
	 * @param etat
	 * @param etape
	 */
	public void affiche(String etat, int etape) {
		label.getjLabel().setBounds(posX, posY, L_PERSO, H_PERSO);
		label.getjLabel().setIcon(new ImageIcon(PERSO+numPerso+etat+etape+"d"+orientation+EXTIMAGE));
		message.getjLabel().setBounds(posX-10, posY+H_PERSO, L_PERSO+10, H_MESSAGE);
		message.getjLabel().setText(pseudo+" : "+vie);
		// envoi du personnage à tous les autres joueurs
		jeuServeur.envoi(label);
		jeuServeur.envoi(message);
	}
	
	/**
	 * Initialisation d'un joueur (pseudo et numéro)
	 * @param pseudo
	 * @param numPerso
	 */
	public void initPerso(String pseudo, int numPerso, Hashtable<Connection, Joueur> lesJoueurs, ArrayList<Mur> lesMurs) {
		this.pseudo = pseudo ;
		this.numPerso = numPerso ;
		// création de l'affichage du personnage
		label = new Label(Label.getNbLabel(), new JLabel()) ;
		Label.setNbLabel(Label.getNbLabel()+1);
		label.getjLabel().setHorizontalAlignment(SwingConstants.CENTER);
		label.getjLabel().setVerticalAlignment(SwingConstants.CENTER);
		jeuServeur.nouveauLabelJeu(label);
		// création de l'affichage du message sous le personnage
		message = new Label(Label.getNbLabel(), new JLabel()) ;
		Label.setNbLabel(Label.getNbLabel()+1);
		message.getjLabel().setHorizontalAlignment(SwingConstants.CENTER);
		message.getjLabel().setFont(new Font("Dialog", Font.PLAIN, 8));
		jeuServeur.nouveauLabelJeu(message);
		// calcul de la première position aléatoire
		premierePosition(lesJoueurs, lesMurs) ;
		// affichage du personnage
		affiche(MARCHE, etape) ;
	}

	/**
	 * @return the message
	 */
	public Label getMessage() {
		return message;
	}
	
	/**
	 * Contrôle si le joueur chevauche un des autres joueurs
	 * @param lesJoueurs
	 * @return
	 */
	private boolean toucheJoueur(Hashtable<Connection, Joueur> lesJoueurs) {
		for (Joueur unJoueur : lesJoueurs.values()) {
			if (!unJoueur.equals(this)) {
				if (toucheObjet(unJoueur)) {
					return true ;
				}
			}
		}
		return false ;
	}
	
	/**
	 * Contrôle si le joueur chevauche un des murs
	 * @param lesMurs
	 * @return
	 */
	private boolean toucheMur(ArrayList<Mur> lesMurs) {
		for (Mur unMur : lesMurs) {
			if (toucheObjet(unMur)) {
				return true ;
			}
		}
		return false ;
	}
	
	/**
	 * Calcul de la première position aléatoire du joueur (sans chevaucher un autre joueur ou un mur)
	 * @param lesJoueurs
	 * @param lesMurs
	 */
	private void premierePosition(Hashtable<Connection, Joueur> lesJoueurs, ArrayList<Mur> lesMurs) {
		label.getjLabel().setBounds(0, 0, L_PERSO, H_PERSO);
		do {
			posX = (int) Math.round(Math.random() * (L_ARENE - L_PERSO)) ;
			posY = (int) Math.round(Math.random() * (H_ARENE - H_PERSO - H_MESSAGE)) ;
		}while(toucheJoueur(lesJoueurs)||toucheMur(lesMurs)) ;
	}
	
}
