package interfaces;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import controller.ClientController;
import controller.ReservationController;
import controller.SanctionController;
import controller.FactureController;
import view.ClientMainView;
import view.CreerFacturePanel;
import view.CreerReservPanel;
import view.FacturePanel;
import view.ReservationPanel;
import view.SanctionInfoPanel;
import view.SanctionPanel;
import view.UserPanel;
import view.VehiculePanel;
import view.viewSettings;
import controller.ParkingController;
import javax.swing.ImageIcon;


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
	private JTable parkingtable;
	private JTextField parkingtextField;
	private JTable contrat_table;

	private boolean isAdmin = false;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					MainInterface window = new MainInterface();
					window.frame.setVisible(true);
					window.frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

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
		frame.setBounds(100, 100, 1000, 700);
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
		navigation.setLayout(new GridLayout(8, 1, 0, 0));
		
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
				login window = new login();
				window.setVisible(true);
				window.setLocationRelativeTo(null);
				frame.dispose();
			}
		});
		exit_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		exit_lbl.setBounds(0, 524, 234, 45);
		sideBar.add(exit_lbl);
		
		mainPanel = new JPanel();
		mainPanel.setBounds(234, 102, 752, 561);
		mainPanel.setLayout(new CardLayout(0, 0));
		cl = (CardLayout) mainPanel.getLayout();

		// Client Panel generation
		ClientMainView client = new ClientMainView(getMainPanel());
		mainPanel.add(client, "client");

		JPanel parking = new JPanel();
		mainPanel.add(parking, "parking");

		//Panel des RESERVATIONS ---------------------------------------------------------------------

		ReservationPanel reservPanel = new ReservationPanel(this);
		mainPanel.add(reservPanel, "reserv");

		CreerReservPanel createReservPanel = new CreerReservPanel(this);
		mainPanel.add(createReservPanel, "newReserv");


		//END PANEL RESERVATIONS ---------------------------------------------------------------------
		
		JPanel contrats = new JPanel();
		mainPanel.add(contrats, "contrat");

		JLabel lblContrats = new JLabel("contrats");
		contrats.add(lblContrats);

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
		

		// Parking Panel generation
//		setupParkingPanel(parking);
		
		
		navItemList = new LinkedHashMap<String, JLabel>();
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

		VehiculePanel.setNavList(navItemList);
		VehiculePanel vehicules = new VehiculePanel(this);
		mainPanel.add(vehicules, "vehicule");
		vehicules.setLayout(null);
		
		JLabel lblVehicules = new JLabel("vehicules");
		vehicules.add(lblVehicules);
		
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
	// SETTERS  

	// AJOUTE LES PANELS AU MAINPANEL
	public void addToMainPanel(JPanel Panel,String key) {
		this.mainPanel.add(Panel,key);
		Panel.setLayout(null);
	}
	public void showOnMainPanel(String key) {
		cl.show(mainPanel,key);
	}
}
