package interfaces;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import controller.FactureController;
import controller.ReservationController;
import controller.SanctionController;
import controller.UserController;
import view.ClientMainView;
import view.ContratPanel;
import view.CreerFacturePanel;
import view.CreerReservPanel;
import view.FacturePanel;
import view.ParkingMainView;
import view.ReservationPanel;
import view.SanctionInfoPanel;
import view.SanctionPanel;
import view.UserPanel;
import view.VehiculePanel;
import view.viewSettings;


public class MainInterface {

	JFrame frame;

	private int navFontSize;
	private CardLayout cl;
	private JPanel mainPanel;
	private String currentPane;
	private LinkedHashMap<String, JLabel> navItemList;

	//CONTROLLERS @ABD-AB
	private ReservationController reservController;
	private FactureController factureController;
	private SanctionController sanctionController;

	private boolean isAdmin = false;

	/**
	 * Create the application.
	 */
	public MainInterface() {
		initialize();
	}

	public MainInterface(boolean isAdmin) {
		this.isAdmin = isAdmin;
		initialize();
	}


	/**
	 * Initialize the contents of the frame.
	 *
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(MainInterface.class.getResource("/icons/icons8-garage-80.png")));
		frame.setTitle("Gestion de location");
		frame.setResizable(false);
		frame.getContentPane().setEnabled(false);
		frame.setBounds(140, 10, 1000, 700);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		navFontSize = 12;
		frame.getContentPane().setLayout(null);

		JPanel titleBar = new JPanel();
		titleBar.setBackground(viewSettings.MAIN);
		titleBar.setBounds(0, 0, 986, 102);
		frame.getContentPane().add(titleBar);
		titleBar.setLayout(null);

		JLabel logoPlaceHolder = new JLabel("Gestion De Location");
		logoPlaceHolder.setHorizontalAlignment(SwingConstants.CENTER);
		logoPlaceHolder.setForeground(new Color(255, 255, 255));
		logoPlaceHolder.setFont(new Font("Tahoma", Font.BOLD, 24));
		logoPlaceHolder.setBounds(333, 17, 315, 75);
		titleBar.add(logoPlaceHolder);

		JPanel sideBar = new JPanel();
		sideBar.setBounds(0, 94, 234, 569);
		sideBar.setBackground(viewSettings.SECONDARY);


		JPanel navigation = new JPanel();
		navigation.setBounds(0, 80, 234, 388);
		navigation.setBackground(viewSettings.SECONDARY);
		if(isAdmin)
			navigation.setLayout(new GridLayout(8, 1, 0, 0));
		else
			navigation.setLayout(new GridLayout(7, 1, 0, 0));

		frame.getContentPane().add(sideBar);
		sideBar.setLayout(null);
		sideBar.add(navigation);

		JLabel exit_lbl = new JLabel("Quitter");
		exit_lbl.setBackground(viewSettings.SECONDARY);
		exit_lbl.setOpaque(true);
		exit_lbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				exit_lbl.setBackground(viewSettings.HIGHLIGHT);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				exit_lbl.setBackground(viewSettings.SECONDARY);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiement  quitter ? ",
						"Confirmer le logout", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(result == JOptionPane.YES_OPTION) {
					login window = new login();
					window.setVisible(true);
					window.setLocationRelativeTo(null);
					frame.dispose();
				}
				else {
					UserController.fetchAll();
				}

			}
		});
		exit_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		exit_lbl.setBounds(0, 524, 234, 45);
		sideBar.add(exit_lbl);

		mainPanel = new JPanel();
		mainPanel.setBounds(234, 102, 752, 561);
		mainPanel.setLayout(new CardLayout(0, 0));
		cl = (CardLayout) mainPanel.getLayout();

		//Panel des clients ---------------------------------------------------------------------
		ClientMainView client = new ClientMainView(getMainPanel());
		mainPanel.add(client, "client");

		//END PANEL clients ---------------------------------------------------------------------

		//Panel des parkings ---------------------------------------------------------------------
		ParkingMainView parking = new ParkingMainView(getMainPanel());
		mainPanel.add(parking, "parking");

		//END PANEL parkings ---------------------------------------------------------------------

		//Panel des RESERVATIONS ---------------------------------------------------------------------

		ReservationPanel reservPanel = new ReservationPanel(this);
		mainPanel.add(reservPanel, "reserv");

		CreerReservPanel createReservPanel = new CreerReservPanel(this);
		mainPanel.add(createReservPanel, "newReserv");


		//END PANEL RESERVATIONS --------------------------------------------------------------------

		//Panel des factures @ABD-AB-------------------------------------------------------------------

		FacturePanel factures = new FacturePanel();
		mainPanel.add(factures, "facture");

		CreerFacturePanel creerFacturePanel = new CreerFacturePanel(this);
		mainPanel.add(creerFacturePanel, "newFacture");

		//END Panel des factures ----------------------------------------------------------------------


		//Panel des sanctions -------------------------------------------------------------------------

		SanctionPanel sanctions = new SanctionPanel();
		mainPanel.add(sanctions, "sanction");

		SanctionInfoPanel sanctionInfo = new SanctionInfoPanel();
		mainPanel.add(sanctionInfo, "sanctionInfo");

		//END Panel des Sanctions ---------------------------------------------------------------------


		cl.show(mainPanel, "facture");
		frame.getContentPane().add(mainPanel);


		//Association des panels aux controlleurs @ABD-AB
		factureController = new FactureController(factures, creerFacturePanel, this);
		reservController = new ReservationController(reservPanel, createReservPanel, this);
		sanctionController = new SanctionController(sanctions, sanctionInfo, this);


		navItemList = new LinkedHashMap<>();
		navItemList.put("client", new JLabel("Gestion de clients"));
		navItemList.put("reserv", new JLabel("Gestion des reservations"));
		navItemList.put("contrat", new JLabel("Gestion des contrats"));
		navItemList.put("facture", new JLabel("Gestion des factures"));
		navItemList.put("sanction", new JLabel("Gestion des sanctions"));
		navItemList.put("vehicule", new JLabel("Gestion des vehicules"));
		navItemList.put("parking", new JLabel("Gestion des parkings"));
		navItemList.put("user", new JLabel("Gestion des utilisateurs"));

		for(String item : navItemList.keySet()) {
			if(!isAdmin && item.equals("user")) continue;
			JLabel lab = navItemList.get(item);
			setupNavItem(lab, item);
			navigation.add(lab);
		}
	// PANEL CONTRATS
		ContratPanel.setNavList(navItemList);
		ContratPanel contrats = new ContratPanel(this);
		mainPanel.add(contrats, "contrat");

		JLabel lblContrats = new JLabel("contrats");
		contrats.add(lblContrats);
	//PANEL VEHICULE
		VehiculePanel.setNavList(navItemList);
		VehiculePanel vehicules = new VehiculePanel(this);
		mainPanel.add(vehicules, "vehicule");
		vehicules.setLayout(null);

		JLabel lblVehicules = new JLabel("vehicules");
		vehicules.add(lblVehicules);

	//PANEL UTILISATEURS
		UserPanel.setNavList(navItemList);
		UserPanel utilisateurs = new UserPanel(this);
		mainPanel.add(utilisateurs, "user");
		utilisateurs.setLayout(null);
	}

	private void setupNavItem(JLabel lab, String name) {
		lab.setOpaque(true);
		lab.setHorizontalAlignment(SwingConstants.CENTER);
		lab.setFont(new Font("Tahoma", Font.BOLD, navFontSize));
		lab.setBackground(viewSettings.SECONDARY);
		lab.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				// on hover change color
				lab.setBackground(viewSettings.HIGHLIGHT);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// if mouse exit && not the curret pane change color

				// [ NOTE ] add condition to check if it's the current pane!
				if (currentPane != name)
					lab.setBackground(viewSettings.SECONDARY);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				switch(name) {
				case "reserv" : reservController.ActualiserTableau();
					break;
				case "sanction": sanctionController.Actualiser();
					break;
				case "facture": factureController.ActualiserTableau();
					break;	
				}
				cl.show(mainPanel, name);
				currentPane = name;
				for (String item : navItemList.keySet()) {
					if (item != name) {
						JLabel tmp = navItemList.get(item);
						tmp.setBackground(viewSettings.SECONDARY);
					}
				}
			}
		});
	}
	//GETTERS
	public JPanel getMainPanel() {
		return this.mainPanel;
	}
	// AJOUTE LES PANELS AU MAINPANEL
	public void addToMainPanel(JPanel Panel,String key) {
		this.mainPanel.add(Panel,key);
		Panel.setLayout(null);
	}
	public void showOnMainPanel(String key) {
		cl.show(mainPanel,key);
	}
	public FactureController getFactureController() {
		return factureController;
	}
}
