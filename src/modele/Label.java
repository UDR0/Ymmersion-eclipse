package modele;

import java.io.Serializable;

import javax.swing.JLabel;

/**
 * Contient un JLabel et un numéro (pour les joueurs, les boules et les murs)
 * @author emds
 *
 */
public class Label implements Serializable {

	// propriétés
	private static Integer nbLabel ; // numéro dernire label ajouté
	private Integer numLabel ;
	private JLabel jLabel ;

	/**
	 * Constructeur
	 * @param numLabel
	 * @param jLabel
	 */
	public Label(Integer numLabel, JLabel jLabel) {
		this.numLabel = numLabel;
		this.jLabel = jLabel;
	}

	/**
	 * @return the nbLabel
	 */
	public static Integer getNbLabel() {
		return nbLabel;
	}

	/**
	 * @param nbLabel the nbLabel to set
	 */
	public static void setNbLabel(Integer nbLabel) {
		Label.nbLabel = nbLabel;
	}

	/**
	 * @return the numLabel
	 */
	public Integer getNumLabel() {
		return numLabel;
	}

	/**
	 * @return the jLabel
	 */
	public JLabel getjLabel() {
		return jLabel;
	}
	
}
