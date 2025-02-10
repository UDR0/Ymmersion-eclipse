package modele;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import controleur.Global;

/**
 * Gestion des murs
 * @author emds
 *
 */
public class Mur extends Objet implements Global {

	/**
	 * Constructeur
	 */
	public Mur() {
		// calcul position aléatoire du mur
		posX = (int) Math.round(Math.random() * (L_ARENE - L_MUR)) ;
		posY = (int) Math.round(Math.random() * (H_ARENE - H_MUR)) ;
		// création du label pour ce mur (pas d'importance pour le rang dans le panel, d'où -1)
		label = new Label(-1, new JLabel());
		// caractéristiques du mur (centrage, position, image)
		label.getjLabel().setHorizontalAlignment(SwingConstants.CENTER);
		label.getjLabel().setVerticalAlignment(SwingConstants.CENTER);
		label.getjLabel().setBounds(posX, posY, L_MUR, H_MUR);
		label.getjLabel().setIcon(new ImageIcon(MUR));
	}
	
}
