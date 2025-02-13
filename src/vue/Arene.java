package vue;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controleur.Controle;
import controleur.Global;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Frame de l'ar�ne du jeu
 * @author emds
 *
 */
public class Arene extends JFrame implements Global {

	private JPanel contentPane;
	private JTextField txtSaisie;
	private JPanel jpnMurs ;
	private JPanel jpnJeu ;
	private boolean client ; // arene du client ou du serveur ?
	private Controle controle ;
	private JTextArea txtChat ;
	
	/**
	 * Retourne le contenu complet de la zone de chat
	 * @return
	 */
	public String getContenuTxtChat() {
		return txtChat.getText() ;
	}
	
	/**
	 * Remplace le contenu de txtChat par le contenu du param�tre
	 * @param contenuTxtChat
	 */
	public void remplaceChat(String contenuTxtChat) {
		txtChat.setText(contenuTxtChat);
	}
	
	/**
	 * Ajout d'un mur
	 * @param unMur
	 */
	public void ajoutMur(JLabel unMur) {
		jpnMurs.add(unMur);
		jpnMurs.repaint();
	}
	
	/**
	 * Ajout d'un nouveau personnage (c�t� serveur)
	 * @param unJoueur
	 */
	public void ajoutJoueur(JLabel unJoueur, String playerClass) {
	    // Ensure player is properly positioned
	    int x = unJoueur.getX();
	    int y = unJoueur.getY();

	    // If X or Y is 0, manually set a position
	    if (x == 0 && y == 0) {
	        x = 100;  // Default X position
	        y = 100;  // Default Y position
	        unJoueur.setLocation(x, y);
	    }

	    // Check if player already has a class label
	    JLabel lblClass = new JLabel(playerClass);
	    lblClass.setBounds(x, y - 20, 100, 20);  // Position above player

	    // Add both labels to the game panel
	    jpnJeu.add(lblClass);
	    jpnJeu.add(unJoueur);

	    jpnJeu.repaint();
	}
	
	/**
	 * Ajout ou modification d'un personnage (c�t� client)
	 * @param num
	 * @param unLabel
	 */
	public void ajoutModifJoueur(int num, JLabel unLabel, String playerClass) {
	    try {
	        jpnJeu.remove(num);
	    } catch (ArrayIndexOutOfBoundsException e) {
	    }

	    // Ensure player label is positioned correctly
	    int x = unLabel.getX();
	    int y = unLabel.getY();

	    if (x == 0 && y == 0) {
	        x = 100;  // Default position
	        y = 100;
	        unLabel.setLocation(x, y);
	    }

	    // Check if player already has a class label
	    JLabel lblClass = new JLabel(playerClass);
	    lblClass.setBounds(x, y - 20, 100, 20);  // Position above player

	    jpnJeu.add(lblClass);
	    jpnJeu.add(unLabel, num);
	    jpnJeu.repaint();
	}
	
	
	/**
	 * Ajout de tous les murs en une fois
	 * @param lesMurs
	 */
	public void ajoutPanelMurs(JPanel lesMurs) {
		jpnMurs.add(lesMurs);
		jpnMurs.repaint();
		contentPane.requestFocus();
	}
	
	/**
	 * Ajout d'une nouvelle phrase dans la zone de chat
	 * @param unePhrase
	 */
	public void ajoutChat(String unePhrase) {
		txtChat.setText(unePhrase+"\r\n"+txtChat.getText());
	}
	
	/**
	 * Traite la touche utilis�e
	 * @param arg0
	 */
	private void txtSaisie_keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode()==KeyEvent.VK_ENTER) {
			if (!txtSaisie.getText().equals("")) {
				controle.evenementVue(this, CHAT+SEPARE+txtSaisie.getText());
				txtSaisie.setText("");
				contentPane.requestFocus();
			}
		}
	}
	
	/**
	 * @return jpnMurs
	 */
	public JPanel getJpnMurs() {
		return jpnMurs ;
	}

	/**
	 * Create the frame.
	 */
	public Arene(String typeJeu, Controle controle) {
		// arene pour un client ou un serveur ?
		client = (typeJeu.equals("client"));
		// r�cup�ration du controleur
		this.controle = controle;
		
		// les objets graphiques
		setTitle("Arena");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, L_ARENE+3*MARGE, H_ARENE + H_CHAT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		jpnJeu = new JPanel();
		jpnJeu.setBounds(0, 0, L_ARENE, H_ARENE);
		jpnJeu.setOpaque(false);
		contentPane.add(jpnJeu);
		jpnJeu.setLayout(null);
		
		jpnMurs = new JPanel();
		jpnMurs.setBounds(0, 0, L_ARENE, H_ARENE);
		jpnMurs.setOpaque(false);
		contentPane.add(jpnMurs);
		jpnMurs.setLayout(null);
		
		// zone de saisie que pour le client
		if (client) {
			txtSaisie = new JTextField();
			txtSaisie.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent arg0) {
					txtSaisie_keyPressed(arg0);
				}
			});
			txtSaisie.setBounds(0, H_ARENE, L_ARENE, H_SAISIE);
			contentPane.add(txtSaisie);
			txtSaisie.setColumns(10);
		}
		
		JScrollPane jspChat = new JScrollPane();
		jspChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jspChat.setBounds(0, H_ARENE + H_SAISIE, L_ARENE, H_CHAT - H_SAISIE - 7*MARGE);
		contentPane.add(jspChat);
		
		txtChat = new JTextArea();
		jspChat.setViewportView(txtChat);
		
		JLabel lblFond = new JLabel("");
		lblFond.setIcon(new ImageIcon(FONDARENE));
		lblFond.setBounds(0, 0, L_ARENE, H_ARENE);
		contentPane.add(lblFond);
	}
}
