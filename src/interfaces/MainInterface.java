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
import controller.FactureController;
import view.ClientMainView;
import view.CreerFacturePanel;
import view.CreerReservPanel;
import view.FacturePanel;
import view.ParkingMainView;
import view.ReservationPanel;
import controller.ParkingController;


public class MainInterface {

	private JFrame frame;
	private Color mainColor;
	private Color secondaryColor;
	private Color highlight;

	private int navFontSize;
	private CardLayout cl;
	private JPanel mainPanel;
	private String currentPane;
	private LinkedHashMap<String, JLabel> navItemList;
	
	//CONTROLLERS @ABD-AB
	private ReservationController reservController;
	private FactureController factureController;
	private JTable parkingtable;
	private JTextField parkingtextField;

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

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(MainInterface.class.getResource("/icons/icons8-garage-80.png")));
		frame.setTitle("Gestion de location");
		frame.setResizable(false);
		frame.getContentPane().setEnabled(false);
		frame.setBounds(140, 10, 1000, 700);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		mainColor = new Color(75, 0, 130);
		secondaryColor = new Color(224, 199, 242);
		highlight = new Color(202, 168, 227);
		navFontSize = 12;
		frame.getContentPane().setLayout(null);

		JPanel titleBar = new JPanel();
		titleBar.setBackground(mainColor);
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
		sideBar.setBackground(secondaryColor);

		JPanel navigation = new JPanel();
		navigation.setBounds(0, 80, 234, 388);
		navigation.setBackground(secondaryColor);
		navigation.setLayout(new GridLayout(8, 1, 0, 0));

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


		//END PANEL RESERVATIONS ---------------------------------------------------------------------
		
		JPanel contrats = new JPanel();
		mainPanel.add(contrats, "contrat");

		JLabel lblContrats = new JLabel("contrats");
		contrats.add(lblContrats);

		//Panel des factures @ABD-AB-------------------------------------------------------------------

		FacturePanel factures = new FacturePanel(this);
		mainPanel.add(factures, "facture");

		//END Panel des factures ----------------------------------------------------------------------
		
		
		//Panel des sanctions -------------------------------------------------------------------------
		
		JPanel sanctions = new JPanel();
		mainPanel.add(sanctions, "sanction");

		JLabel sanctions_1 = new JLabel("sanctions");
		sanctions.add(sanctions_1);
		
		//END Panel des Sanctions ---------------------------------------------------------------------
		
		
		JPanel vehicules = new JPanel();
		mainPanel.add(vehicules, "vehicule");

		JLabel lblVehicules = new JLabel("vehicules");
		vehicules.add(lblVehicules);

		JPanel utilisateurs = new JPanel();
		mainPanel.add(utilisateurs, "user");

		JLabel lblUtilisateurs = new JLabel("utilisateurs");
		utilisateurs.add(lblUtilisateurs);

		
		cl.show(mainPanel, "facture");
		frame.getContentPane().add(sideBar);
		sideBar.setLayout(null);
		sideBar.add(navigation);
		frame.getContentPane().add(mainPanel);

		//PANEL CREATION FACTURE @ABD-AB ----------------------------------------------------------------
		CreerFacturePanel creerFacturePanel = new CreerFacturePanel(this);
		mainPanel.add(creerFacturePanel, "newFacture");

		//Association des panels aux controlleurs @ABD-AB
		factureController = new FactureController(factures, creerFacturePanel);
		factureController.ActualiserTableau();

		reservController = new ReservationController(reservPanel, createReservPanel);
		
		
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
			JLabel lab = navItemList.get(item);
			setupNavItem(lab, item);
			navigation.add(lab);
		}

	}

	private void setupNavItem(JLabel lab, String name) {
		lab.setOpaque(true);
		lab.setHorizontalAlignment(SwingConstants.CENTER);
		lab.setFont(new Font("Tahoma", Font.BOLD, navFontSize));
		lab.setBackground(secondaryColor);
		lab.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				// on hover change color
				lab.setBackground(highlight);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// if mouse exit && not the curret pane change color

				// [ NOTE ] add condition to check if it's the current pane!
				if (currentPane != name)
					lab.setBackground(secondaryColor);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				cl.show(mainPanel, name);
				currentPane = name;
				for (String item : navItemList.keySet()) {
					if (item != name) {
						JLabel tmp = navItemList.get(item);
						tmp.setBackground(secondaryColor);
					}
				}
			}
		});
	}


	//GETTERS
	public JPanel getMainPanel() {
		return this.mainPanel;
	}
	public FactureController getFactureController() {
		return factureController;
	}
	
}
