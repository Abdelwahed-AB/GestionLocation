package interfaces;

import java.awt.EventQueue;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import controller.ClientController;
import controller.ParkingController;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.CardLayout;
import java.util.LinkedHashMap;
import java.awt.FlowLayout;

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
	// client panel field
	private JTable clienttable;
	private JTextField clienttextField;
	private JTable parkingtable;
	protected JTextField parkingtextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
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
		ClientController.fetchAll(clienttable);
		ParkingController.fetchAll(parkingtable);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setEnabled(false);
		frame.setResizable(false);
		frame.setBounds(140, 10, 1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		mainColor = new Color(75, 0, 130);
		secondaryColor = new Color(224, 199, 242);
		highlight = new Color(202, 168, 227);
		navFontSize = 12;

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
		sideBar.setBounds(0, 100, 234, 563);
		sideBar.setBackground(secondaryColor);
		frame.getContentPane().add(sideBar);
		sideBar.setLayout(null);

		JPanel navigation = new JPanel();
		navigation.setBounds(0, 80, 234, 388);
		sideBar.add(navigation);
		navigation.setBackground(secondaryColor);
		navigation.setLayout(new GridLayout(8, 1, 0, 0));

		/*
		 * JLabel clients_lbl = new JLabel("Gestion de clients");
		 * 
		 * JLabel reservations_lbl = new JLabel("Gestion des reservations");
		 * 
		 * JLabel contrats_lbl = new JLabel("Gestion des contrats");
		 * 
		 * JLabel factures_lbl = new JLabel("Gestion des factures");
		 * 
		 * JLabel sanctions_lbl = new JLabel("Gestion des sanctions");
		 * 
		 * JLabel vehicules_lbl = new JLabel("Gestion des vehicules");
		 * 
		 * JLabel parking_lbl = new JLabel("Gestion des parkings");
		 * 
		 * JLabel utilisateurs_lbl = new JLabel("Gestion des utilisateurs");
		 */

		// Add all labels to list so we can modify their properties in the method below
		// [Respect name Order Above]
		navItemList = new LinkedHashMap<String, JLabel>();
		navItemList.put("client", new JLabel("Gestion de clients"));
		navItemList.put("reserv", new JLabel("Gestion des reservations"));
		navItemList.put("contrat", new JLabel("Gestion des contrats"));
		navItemList.put("facture", new JLabel("Gestion des factures"));
		navItemList.put("sanction", new JLabel("Gestion des sanctions"));
		navItemList.put("vehicule", new JLabel("Gestion des vehicules"));
		navItemList.put("parking", new JLabel("Gestion des parkings"));
		navItemList.put("user", new JLabel("Gestion des utilisateurs"));

		for (String item : navItemList.keySet()) {
			JLabel lab = navItemList.get(item);
			setupNavItem(lab, item);
			navigation.add(lab);
		}

		mainPanel = new JPanel();
		mainPanel.setBounds(244, 106, 732, 547);
		frame.getContentPane().add(mainPanel);
		mainPanel.setLayout(new CardLayout(0, 0));
		cl = (CardLayout) mainPanel.getLayout();

		JPanel client = new JPanel();
		mainPanel.add(client, "client");

		JPanel parking = new JPanel();
		mainPanel.add(parking, "parking");

		JPanel reservations = new JPanel();
		mainPanel.add(reservations, "reserv");

		JLabel lblNewLabel_2 = new JLabel("Reservations");
		reservations.add(lblNewLabel_2);

		JPanel contrats = new JPanel();
		mainPanel.add(contrats, "contrat");

		JLabel lblContrats = new JLabel("contrats");
		contrats.add(lblContrats);

		JPanel factures = new JPanel();
		mainPanel.add(factures, "facture");

		JLabel lblFactures = new JLabel("factures");
		factures.add(lblFactures);

		JPanel sanctions = new JPanel();
		mainPanel.add(sanctions, "sanction");

		JLabel sanctions_1 = new JLabel("sanctions");
		sanctions.add(sanctions_1);

		JPanel vehicules = new JPanel();
		mainPanel.add(vehicules, "vehicule");

		JLabel lblVehicules = new JLabel("vehicules");
		vehicules.add(lblVehicules);

		JPanel utilisateurs = new JPanel();
		mainPanel.add(utilisateurs, "user");

		JLabel lblUtilisateurs = new JLabel("utilisateurs");
		utilisateurs.add(lblUtilisateurs);
		client.setLayout(null);

		// Client Panel generation
		setupClientPanel(client);
		
		// Parking Panel generation 
		setupParkingPanel(parking);

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

	private void setupClientPanel(JPanel panel) {
		JScrollPane clientscrollPane = new JScrollPane();
		clientscrollPane.setBounds(10, 58, 574, 478);
		panel.add(clientscrollPane);

		clienttable = new JTable();
		clienttable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Object[] clientcolumns = { "id", "nom", "prenom", "numTel" };
		DefaultTableModel clientmodel = new DefaultTableModel();
		clientmodel.setColumnCount(4);
		clientmodel.setColumnIdentifiers(clientcolumns);
		clienttable.setModel(clientmodel);
		clientscrollPane.setViewportView(clienttable);

		JButton clientbtnNewButton = new JButton("Rechercher");
		clientbtnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String string = clienttextField.getText();
				ClientController.findClient(string, clienttable);
				clienttextField.setText("");
			}
		});
		clientbtnNewButton.setBounds(594, 11, 128, 36);
		panel.add(clientbtnNewButton);

		JButton clientbtnNewButton_1 = new JButton("Nouveau Client");
		clientbtnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreerNouveauClient nouveauClient = new CreerNouveauClient(clienttable);
			}
		});
		clientbtnNewButton_1.setBounds(594, 76, 128, 36);
		panel.add(clientbtnNewButton_1);

		clienttextField = new JTextField();
		clienttextField.setBounds(10, 11, 574, 36);
		panel.add(clienttextField);
		clienttextField.setColumns(10);

		JButton clientbtnNewButton_2 = new JButton("Modifier");
		clientbtnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = clienttable.getSelectedRow();
				if (index >= 0) {
					ModifierClient.setId(clienttable.getModel().getValueAt(index, 0).toString());
					ModifierClient window = new ModifierClient(clienttable, index);
				} else {
					JOptionPane.showConfirmDialog(null,
							"Tu dois s�l�ctionn�e un �l�ment du tableau pour le modifier!", "Attention",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		clientbtnNewButton_2.setBounds(594, 170, 128, 36);
		panel.add(clientbtnNewButton_2);

		JButton clientbtnNewButton_3 = new JButton("Actualiser");
		clientbtnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientController.fetchAll(clienttable);
			}
		});
		clientbtnNewButton_3.setBounds(594, 264, 128, 36);
		panel.add(clientbtnNewButton_3);

		JButton clientbtnNewButton_4 = new JButton("Supprimer");
		clientbtnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = clienttable.getSelectedRow();
				if (index >= 0) {
					int result = JOptionPane.showConfirmDialog(null, "Avez-vous Sure?", "Confirmation",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (result == JOptionPane.YES_OPTION) {
						ClientController.deleteClient(clienttable.getModel().getValueAt(index, 0).toString());
						ClientController.fetchAll(clienttable);
					}
				} else {
					JOptionPane.showConfirmDialog(null,
							"Tu dois s�l�ctionn�e un �l�ment du tableau pour le supprimer!", "Attention",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		clientbtnNewButton_4.setBounds(594, 217, 128, 36);
		panel.add(clientbtnNewButton_4);

		JButton clientbtnNewButton_5 = new JButton("Afficher");
		clientbtnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				int index = clienttable.getSelectedRow();
				if (index >= 0) {
					afficherClient window = new afficherClient(clienttable.getModel(), index);
				} else {
					JOptionPane.showConfirmDialog(null,
							"Tu dois s�l�ctionn�e un �l�ment du tableau pour l'afficher!", "Attention",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		clientbtnNewButton_5.setBounds(594, 123, 128, 36);
		panel.add(clientbtnNewButton_5);
	}
	
	private void setupParkingPanel(JPanel panel) {
		panel.setLayout(null);
		JScrollPane parkingscrollPane = new JScrollPane();
		parkingscrollPane.setBounds(10, 58, 574, 478);
		panel.add(parkingscrollPane);

		parkingtable = new JTable();
		parkingtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Object[] parkingcolumns = { "id", "nom", "prenom" };
		DefaultTableModel parkingmodel = new DefaultTableModel();
		parkingmodel.setColumnCount(3);
		parkingmodel.setColumnIdentifiers(parkingcolumns);
		parkingtable.setModel(parkingmodel);
		parkingscrollPane.setViewportView(parkingtable);

		JButton parkingbtnNewButton = new JButton("Rechercher");
		parkingbtnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String string = parkingtextField.getText();
				ParkingController.findParking(string, parkingtable);
				parkingtextField.setText("");
			}
		});
		parkingbtnNewButton.setBounds(594, 11, 128, 36);
		panel.add(parkingbtnNewButton);

		JButton parkingbtnNewButton_1 = new JButton("Nouveau Parking");
		parkingbtnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreerNouveauParking window = new CreerNouveauParking(parkingtable);
			}
		});
		parkingbtnNewButton_1.setBounds(594, 76, 128, 36);
		panel.add(parkingbtnNewButton_1);
		
				JButton parkingbtnNewButton_5 = new JButton("Afficher");
				parkingbtnNewButton_5.addActionListener(new ActionListener() {
					public void actionPerformed (ActionEvent e) {
						int index = parkingtable.getSelectedRow();
						if (index >= 0) {
							
						} else {
							JOptionPane.showConfirmDialog(null,
									"Tu dois s�l�ctionn�e un �l�ment du tableau pour l'afficher!", "Attention",
									JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
						}
					}
				});
				parkingbtnNewButton_5.setBounds(594, 123, 128, 36);
				panel.add(parkingbtnNewButton_5);

		parkingtextField = new JTextField();
		parkingtextField.setBounds(10, 11, 574, 36);
		panel.add(parkingtextField);
		parkingtextField.setColumns(10);

		JButton parkingbtnNewButton_2 = new JButton("Modifier");
		parkingbtnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = parkingtable.getSelectedRow();
				if (index >= 0) {
					ModifierParking.setId(parkingtable.getModel().getValueAt(index, 0).toString());
					ModifierParking window = new ModifierParking(parkingtable, index);
				} else {
					JOptionPane.showConfirmDialog(null,
							"Tu dois s�l�ctionn�e un �l�ment du tableau pour le modifier!", "Attention",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		parkingbtnNewButton_2.setBounds(594, 170, 128, 36);
		panel.add(parkingbtnNewButton_2);

		JButton parkingbtnNewButton_3 = new JButton("Actualiser");
		parkingbtnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ParkingController.fetchAll(parkingtable);
			}
		});
		parkingbtnNewButton_3.setBounds(594, 217, 128, 36);
		panel.add(parkingbtnNewButton_3);

		JButton parkingbtnNewButton_4 = new JButton("Supprimer");
		parkingbtnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = parkingtable.getSelectedRow();
				if (index >= 0) {
					int result = JOptionPane.showConfirmDialog(null, "Avez-vous Sure?", "Confirmation",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (result == JOptionPane.YES_OPTION) {
						ParkingController.deleteParking(parkingtable.getModel().getValueAt(index, 0).toString());
						ParkingController.fetchAll(parkingtable);
					}
				} else {
					JOptionPane.showConfirmDialog(null,
							"Tu dois s�l�ctionn�e un �l�ment du tableau pour le supprimer!", "Attention",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		parkingbtnNewButton_4.setBounds(594, 264, 128, 36);
		panel.add(parkingbtnNewButton_4);
	}
}
