package vue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controleur.Controle;
import controleur.Global;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;

/**
 * Frame du choix du joueur
 * @author emds
 *
 */
public class ChoixJoueur extends JFrame implements Global {

	// propri�t�s
	private Integer numPerso ;
	private Controle controle ;
	
	// objets graphiques
	private JPanel contentPane;
	private JTextField txtPseudo;
	private JLabel lblPersonnage;
	private JComboBox<String> cbClasse;

	/**
	 * Affichage du personnage
	 */
	private void affichePerso() {
		lblPersonnage.setIcon(new ImageIcon(PERSO+numPerso+MARCHE+"1d1"+EXTIMAGE));
	}
	
	/**
	 * Clic sur la fl�che "pr�c�dent"
	 */
	private void lblPrecedent_clic() {
		numPerso = ((numPerso+1)%NBPERSOS)+1;
		affichePerso();
	}
	
	/**
	 * Clic sur la fl�che "suivant"
	 */
	private void lblSuivant_clic() {
		numPerso = (numPerso%NBPERSOS)+1 ;
		affichePerso();
	}
	
	/**
	 * Clic sur GO
	 */
	private void lblGo_clic() {
        // Check pseudo
        if (txtPseudo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                null, 
                "La saisie du pseudo est obligatoire"
            );
            txtPseudo.requestFocus();
            return;
        }
        // 3) Retrieve selected item from JComboBox
        String classe = (String) cbClasse.getSelectedItem();

        // Send data along (pseudo, class, etc.)
        controle.evenementVue(
            this,
            PSEUDO
                + SEPARE
                + txtPseudo.getText()
                + SEPARE
                + numPerso
                + SEPARE
                + classe
        );
    }
	
	/**
	 * Change l'apparence de la souris en "normal"
	 */
	private void souris_normale() {
		contentPane.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
	
	/**
	 * Change l'apparence de la souris en "doigt"
	 */
	private void souris_doigt() {
		contentPane.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	/**
	 * Create the frame.
	 * @param controle 
	 */
	public ChoixJoueur(Controle controle) {
		setTitle("Choice");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 416, 313);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPrecedent = new JLabel("");
		lblPrecedent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				lblPrecedent_clic();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				souris_doigt();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				souris_normale();
			}
		});
		
		JLabel lblSuivant = new JLabel("");
		lblSuivant.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblSuivant_clic();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				souris_doigt();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				souris_normale();
			}
		});
		
		JLabel lblGo = new JLabel("");
		lblGo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblGo_clic();
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				souris_doigt();
			}
			@Override
			public void mouseExited(MouseEvent e) {
				souris_normale();
			}
		});
		
		lblPersonnage = new JLabel("");
		lblPersonnage.setHorizontalAlignment(SwingConstants.CENTER);
		lblPersonnage.setBounds(142, 112, 120, 120);
		contentPane.add(lblPersonnage);
		
		cbClasse = new JComboBox<>(new String[] {
	            "gobelin", 
	            "archer", 
	            "wizard", 
	            "doctor"
	        });
		// Adjust size/position as needed
		cbClasse.setBounds(142, 215, 120, 20);
		contentPane.add(cbClasse);
		
		txtPseudo = new JTextField();
		txtPseudo.setBounds(142, 245, 120, 20);
		contentPane.add(txtPseudo);
		txtPseudo.setColumns(10);
		
		lblGo.setBounds(311, 202, 65, 61);
		contentPane.add(lblGo);
		lblSuivant.setBounds(301, 145, 25, 46);
		contentPane.add(lblSuivant);
		lblPrecedent.setBounds(65, 146, 31, 45);
		contentPane.add(lblPrecedent);
		
		JLabel lblFond = new JLabel("");
		lblFond.setBounds(0, 0, 400, 275);
		lblFond.setIcon(new ImageIcon(FONDCHOIX));
		contentPane.add(lblFond);
		
		// positionnement sur la zone de saisie
		txtPseudo.requestFocus();
		
		// initialisations
		this.controle = controle ;
		numPerso = 1; // premier personnage par d�faut
		affichePerso(); // affichage du premier personnage
		
	}
}
