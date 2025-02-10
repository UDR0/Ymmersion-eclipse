package vue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controleur.Controle;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Frame d'entrée dans le jeu
 * @author emds
 *
 */
public class EntreeJeu extends JFrame {

	// propriétés
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
	 * clic sur le bouton Exit pour arrêter l'application
	 */
	private void btnExit_clic() {
		System.exit(0);
	}
	
	/**
	 * clic sur le bouton Connect pour se connecter à un serveur
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
		setBounds(100, 100, 301, 162);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblStartAServer = new JLabel("Start a server :");
		lblStartAServer.setBounds(10, 11, 94, 14);
		contentPane.add(lblStartAServer);
		
		JButton btnStart = new JButton("Start");
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				btnStart_clic();
			}
		});
		btnStart.setBounds(186, 7, 89, 23);
		contentPane.add(btnStart);
		
		JLabel lblConnectAnExisting = new JLabel("Connect an existing server :");
		lblConnectAnExisting.setBounds(10, 36, 197, 14);
		contentPane.add(lblConnectAnExisting);
		
		JLabel lblIpServer = new JLabel("IP server :");
		lblIpServer.setBounds(10, 61, 68, 14);
		contentPane.add(lblIpServer);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnConnect_clic() ;
			}
		});
		btnConnect.setBounds(186, 57, 89, 23);
		contentPane.add(btnConnect);
		
		txtIp = new JTextField();
		txtIp.setText("127.0.0.1");
		txtIp.setBounds(69, 58, 107, 20);
		contentPane.add(txtIp);
		txtIp.setColumns(10);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnExit_clic();
			}
		});
		btnExit.setBounds(186, 91, 89, 23);
		contentPane.add(btnExit);
		
		// récupération du controleur
		this.controle = controle ;
	}
}
