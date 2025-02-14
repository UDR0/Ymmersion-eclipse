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
	private String classe;
	private int numPerso ;
	private Label message ;
	private JeuServeur jeuServeur ;
	private int orientation ; // tourné vers la gauche (0) ou vers la droite (1)
	private int etape ; // numéro d'étape dans l'animation
	
	/**
	 * Constructeur
	 */
	public Joueur(JeuServeur jeuServeur) {
		this.jeuServeur = jeuServeur ;
		//vie = 10 ;
		etape = 1 ;
		orientation = DROITE ;
	}
	
	/**
	 * @return the pseudo
	 */
	public String getPseudo() {
		return pseudo;
	}
	
	public void setClasse(String classe) {
        this.classe = classe;
    }
	
	public String getClasse() {
        return classe;
    }

	/**
	 * Affiche le personnage et son message
	 * @param etat
	 * @param etape
	 */
	public void affiche(String etat, int etape) {
		label.getjLabel().setBounds(posX, posY, L_PERSO, H_PERSO);
		label.getjLabel().setIcon(new ImageIcon(PERSO+numPerso+etat+etape+"d"+orientation+EXTIMAGE));
		message.getjLabel().setBounds(posX - 10, 
                posY + H_PERSO, 
                L_PERSO + 10, 
                H_MESSAGE * 3);
		message.getjLabel().setText(
			    "<html>" + pseudo /**+ " : " + vie*/ + "<br>" + classe + "</html>"
			);
		// envoi du personnage à tous les autres joueurs
		jeuServeur.envoi(label);
		jeuServeur.envoi(message);
	}
	
	/**
	 * Initialisation d'un joueur (pseudo et numéro)
	 * @param pseudo
	 * @param numPerso
	 */
	public void initPerso(String pseudo, int numPerso, String classe,
            Hashtable<Connection, Joueur> lesJoueurs) {
		this.pseudo = pseudo ;
		this.numPerso = numPerso ;
		this.classe = classe;
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
		premierePosition(lesJoueurs) ;
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
	 * Calcul de la première position aléatoire du joueur (sans chevaucher un autre joueur)
	 * @param lesJoueurs
	 */
	private static Integer posY = (int) MARGE * 5;
	private static Integer var = 1;

	private void premierePosition(Hashtable<Connection, Joueur> lesJoueurs) {
		if (var < 3) {
	        // Si moins de 10 joueurs, on affiche les personnages sur la même ligne
	        posX = L_PERSO * var;  // Position horizontale calculée en fonction de var
	        var++;  // Incrémente pour le prochain joueur
	    } else {
	        // Si 10 joueurs ont été placés sur la ligne, on passe à la ligne suivante
	        posX = L_PERSO;  // Réinitialise la position horizontale
	        posY += H_PERSO;  // Décale vers le bas pour la nouvelle ligne
	        var = 2;  // Réinitialise var pour recommencer à placer les personnages à partir de la deuxième colonne
	    }
	}

	    


}
