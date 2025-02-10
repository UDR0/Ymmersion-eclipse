package modele;

/**
 * Informations communes à tous les objets (joueurs, murs, boules)
 * @author emds
 *
 */
public abstract class Objet {

	// propriétés
	protected Integer posX, posY ; // position du jLabel
	protected Label label ;
	
	/**
	 * @return the posX
	 */
	public Integer getPosX() {
		return posX;
	}
	
	/**
	 * @return the posY
	 */
	public Integer getPosY() {
		return posY;
	}
	
	/**
	 * @return the label
	 */
	public Label getLabel() {
		return label;
	}
	
	/**
	 * contrôle si l'objet actuel touche l'objet passé en paramètre
	 * @param objet
	 * @return vrai si les 2 objets se touchent
	 */
	public boolean toucheObjet (Objet objet) {
		if (objet.label==null) {
			return false ;
		}else{
			if (objet.label.getjLabel()==null) {
				return false ;
			}else{
				int l_obj = objet.label.getjLabel().getWidth() ;
				int h_obj = objet.label.getjLabel().getHeight() ;
				int l_this = this.label.getjLabel().getWidth() ;
				int h_this = this.label.getjLabel().getHeight() ;
				return(!((this.posX+l_this<objet.posX ||
					this.posX>objet.posX+l_obj) || 
					(this.posY+h_this<objet.posY ||
					this.posY>objet.posY+h_obj))) ;
			}
		}
	}

	
}
