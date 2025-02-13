package vue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controleur.Controle;
import controleur.Global;

import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Image;
import java.awt.Font;

/**
 * Frame d'entr�e dans le jeu
 * @author emds
 *
 */
public class EntreeJeu extends JFrame implements Global {

	// propri�t�s
	private JPanel contentPane;
	private JTextField txtIp;
	private Controle controle;
	
	/**
	 * clic sur le bouton Start pour lancer le serveur
	 */
	private void btnStart_clic() {
		controle.evenementVue(this, "serveur");
	}
	
	/**
	 * clic sur le bouton Exit pour arr�ter l'application
	 */
	private void btnExit_clic() {
		System.exit(0);
	}
	
	/**
	 * clic sur le bouton Connect pour se connecter � un serveur
	 */
	private void btnConnect_clic() {
		controle.evenementVue(this, txtIp.getText());
	}
	/**
	 * Create the frame.
	 * @param controle 
	 */
	public EntreeJeu(Controle controle) {
		setTitle("Urban Marginal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 314, 195);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnConnect = new JButton("");
		btnConnect.setOpaque(false);
		btnConnect.setContentAreaFilled(false); // Supprime le fond du bouton
        btnConnect.setBorderPainted(false); // Supprime la bordure
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnConnect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnConnect_clic() ;
			}
		});
		btnConnect.setBounds(186, 108, 74, 23);
		contentPane.add(btnConnect);
		
		txtIp = new JTextField();
		txtIp.setFont(new Font("Tahoma", Font.PLAIN, 10));
		txtIp.setBackground(new Color(255, 204, 51));
		txtIp.setOpaque(false);
		txtIp.setBorder(null);
		txtIp.setText("127.0.0.1");
		txtIp.setBounds(126, 65, 55, 14);
		contentPane.add(txtIp);
		txtIp.setColumns(10);
		
		JButton btnExit = new JButton("");
		btnExit.setOpaque(false);
		btnExit.setContentAreaFilled(false); // Supprime le fond du bouton
        btnExit.setBorderPainted(false); // Supprime la bordure
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnExit_clic();
			}
		});
		btnExit.setBounds(132, 124, 24, 23);
		contentPane.add(btnExit);
		
		ImageIcon fondIcon = new ImageIcon(FONDENTREE);
        Image fondImage = fondIcon.getImage().getScaledInstance(301, 162, Image.SCALE_SMOOTH);
        JLabel lblFond = new JLabel(new ImageIcon(fondImage));
        lblFond.setBounds(0, 0, 301, 162);
        contentPane.add(lblFond);
        
		JButton btnStart = new JButton("");
		btnStart.setOpaque(false);
		btnStart.setContentAreaFilled(false); // Supprime le fond du bouton
        btnStart.setBorderPainted(false); // Supprime la bordure
		btnStart.setBackground(new Color(255, 255, 255));
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnStart_clic();
			}
		});
		btnStart.setBounds(31, 97, 74, 23);
		contentPane.add(btnStart);
		
		// r�cup�ration du controleur
		this.controle = controle ;
	}
}