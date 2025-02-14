package controleur;

/**
 * Regroupement des constantes de l'application
 * @author emds
 *
 */
public interface Global {
	
	public static final Integer PORT = 6666 ;
	
	// fichiers
	public static final String
		SEPARATOR = "//",
		CHEMIN = "media" + SEPARATOR,
		CHEMINFONDS = CHEMIN + "fonds" + SEPARATOR,
		CHEMINPERSOS = CHEMIN + "personnages" + SEPARATOR,
		CHEMINMURS = CHEMIN + "murs" + SEPARATOR,
		PERSO = CHEMINPERSOS + "perso",
		EXTIMAGE = ".gif" ;

	// images
	public static final String
	FONDENTREE = CHEMINFONDS + "fondentree.png",
	FONDCHOIX = CHEMINFONDS + "choice.png",	
	FONDARENE = CHEMINFONDS+"fondarene.jpg",
	MUR = CHEMINMURS + "mur.gif" ;
	
	// personnages
	public static final int
		GAUCHE = 0,
		DROITE = 1,
		NBPERSOS = 3,
		H_PERSO = 44,
		L_PERSO = 39 ;
	public static final String
		MARCHE = "marche",
		BLESSE = "touche",
		MORT = "mort" ;
	
	// messages serveurs
	public static final String SEPARE = "~" ;
	public static final int
		PSEUDO = 0,
		CHAT = 1 ;
	// tailles
	public static final int
		H_ARENE = 600,
		L_ARENE = 800,
		H_CHAT = 200,
		H_MESSAGE = 8,
		H_SAISIE = 25,
		MARGE = 5 ; // �carts entre les objets
	
	// murs
	//public static final int
		//NBMURS = 20, // nombre de murs
		//H_MUR = 35, // hauteur du mur
		//L_MUR = 34 ; // largeur du mur
	
}