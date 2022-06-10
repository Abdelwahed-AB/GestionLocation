package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Date;
import java.time.Year;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import controller.ClientController;
import controller.ReservationController;
import interfaces.MainInterface;
import model.VehiculeTableModel;
import javax.swing.JTextField;

public class CreerReservPanel extends JPanel {
	private JTable client_table;
	private JTable reserv_table;

	private String dateDepart, dateRetour;
	private JTable reserv_vehi_table;
	private JLabel warning_lbl;


	private MainInterface mInterface;
	private VehiculeTableModel vTable_model = new VehiculeTableModel();
	private CardLayout cl;
	private ReservationController cont;
	private JTextField search_client;
	private JTextField search_voiture;
	private JButton sauvegarder_btn;

	/**
	 * Create the application.
	 */
	public CreerReservPanel() {
		this.setBounds(0, 0, 732, 547);
		initialize();
	}
	public CreerReservPanel(MainInterface mInterface) {
		this.mInterface = mInterface;
		initialize();

		this.cl = (CardLayout) mInterface.getMainPanel().getLayout();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setLayout(null);
		JScrollPane reserv_client_Scroll = new JScrollPane();
		reserv_client_Scroll.setBounds(9, 34, 713, 79);
		this.add(reserv_client_Scroll);

		warning_lbl = new JLabel("");
		warning_lbl.setForeground(Color.RED);
		warning_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		warning_lbl.setBounds(148, 501, 423, 36);
		this.add(warning_lbl);

		//to make table cells uneditable
		client_table = new JTable(){
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column){
		          return false;
		    }};

		client_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		client_table.setSelectionBackground(viewSettings.SECONDARY);
		client_table.setBackground(new Color(255, 255, 255));
		reserv_client_Scroll.setViewportView(client_table);

		JLabel choice_lbl = new JLabel("Choisir un client :");
		choice_lbl.setBounds(23, 11, 136, 13);
		this.add(choice_lbl);

		JLabel dateDepart_lbl = new JLabel("Date depart:");
		dateDepart_lbl.setBounds(23, 254, 202, 13);
		this.add(dateDepart_lbl);

		JLabel dateRetour_lbl = new JLabel("Date Retour:");
		dateRetour_lbl.setBounds(23, 382, 136, 13);
		this.add(dateRetour_lbl);
		
		search_client = new JTextField();
		search_client.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!search_client.getText().isEmpty()) {
					cont.searchClient(search_client.getText());
				}else {
					cont.ActualiserTableClient();
				}
			}
		});
		search_client.setToolTipText("Rechercher Client");
		search_client.setBounds(170, 5, 352, 24);
		add(search_client);
		search_client.setColumns(10);
		
		search_voiture = new JTextField();
		search_voiture.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!search_voiture.getText().isEmpty()) {
					cont.searchVehicle(search_voiture.getText());
				}else {
					cont.ActualiserTableVehicule();
				}
			}
		});
		search_voiture.setToolTipText("Rechercher Vehicule");
		search_voiture.setColumns(10);
		search_voiture.setBounds(170, 138, 352, 24);
		add(search_voiture);
		
		JButton reserv_client_actualiser = new JButton("Actualiser");
		reserv_client_actualiser.setBackground(viewSettings.SECONDARY);
		reserv_client_actualiser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//To refill client table
				ClientController.fetchAll(client_table);
				search_client.setText("");
				warning_lbl.setText("");
			}
		});
		reserv_client_actualiser.setBounds(570, 5, 152, 24);
		this.add(reserv_client_actualiser);

		ClientController.fetchAll(client_table);

		JScrollPane reserv_vehi_Scroll = new JScrollPane();
		reserv_vehi_Scroll.setBounds(9, 165, 713, 79);
		this.add(reserv_vehi_Scroll);

		//to make table cells uneditable
		reserv_vehi_table = new JTable(vTable_model);
		reserv_vehi_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		reserv_vehi_table.setSelectionBackground(viewSettings.SECONDARY);
		reserv_vehi_Scroll.setViewportView(reserv_vehi_table);

		JButton reserv_vehi_actualiser = new JButton("Actualiser");
		reserv_vehi_actualiser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cont.ActualiserTableVehicule();
				search_voiture.setText("");
				warning_lbl.setText("");
			}
		});
		reserv_vehi_actualiser.setBackground(viewSettings.SECONDARY);
		reserv_vehi_actualiser.setBounds(570, 138, 152, 24);
		this.add(reserv_vehi_actualiser);

		sauvegarder_btn = new JButton("Sauvegarder");
		sauvegarder_btn.setForeground(viewSettings.WHITE);
		sauvegarder_btn.setBackground(viewSettings.MAIN);
		sauvegarder_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cont.CreerReservation();
			}
		});
		sauvegarder_btn.setBounds(598, 501, 124, 36);
		this.add(sauvegarder_btn);
		
		//Creation des panels de choix de date
		dateDep();
		dateRet();

		JButton Annuler_btn = new JButton("Annuler");
		Annuler_btn.setForeground(new Color(255, 255, 255));
		Annuler_btn.setBackground(viewSettings.MAIN);
		Annuler_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//return to gestion des reservations
				warning_lbl.setText("");
				cl.show(mInterface.getMainPanel(), "reserv");
			}
		});
		Annuler_btn.setBounds(9, 501, 124, 36);
		this.add(Annuler_btn);

		JLabel choice_lbl_1 = new JLabel("Choisir une voiture:");
		choice_lbl_1.setBounds(23, 142, 136, 13);
		this.add(choice_lbl_1);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void dateDep() {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);

		JPanel dateDepInput = new JPanel();
		dateDepInput.setBounds(148, 277, 423, 67);
		this.add(dateDepInput);
		dateDepInput.setLayout(null);

		JComboBox annee_comboBox = new JComboBox();
		annee_comboBox.setBackground(viewSettings.SECONDARY);
		annee_comboBox.setModel(new DefaultComboBoxModel(new String[] {Integer.toString(currentYear), Integer.toString(currentYear+1), Integer.toString(currentYear+2), Integer.toString(currentYear+3), Integer.toString(currentYear+4)}));
		annee_comboBox.setBounds(10, 27, 124, 27);
		dateDepInput.add(annee_comboBox);

		JComboBox jour_comboBox = new JComboBox();
		jour_comboBox.setBounds(278, 27, 124, 27);
		jour_comboBox.setBackground(viewSettings.SECONDARY);
		dateDepInput.add(jour_comboBox);

		JComboBox mois_comboBox = new JComboBox();

		mois_comboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		mois_comboBox.setBounds(144, 27, 124, 27);
		mois_comboBox.setBackground(viewSettings.SECONDARY);
		dateDepInput.add(mois_comboBox);

		setupDayChooser(annee_comboBox, mois_comboBox, jour_comboBox);

		for(JComboBox box : new JComboBox[]{annee_comboBox, jour_comboBox, mois_comboBox} ) {
			//Foreach combo box update date value on change
			box.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dateDepart = annee_comboBox.getSelectedItem() + "-" + mois_comboBox.getSelectedItem() + "-" + jour_comboBox.getSelectedItem();
				}
			});
		}
		
		sauvegarder_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dateDepart = annee_comboBox.getSelectedItem() + "-" + mois_comboBox.getSelectedItem() + "-" + jour_comboBox.getSelectedItem();
			}
		});

		JLabel anneeDep_lbl = new JLabel("Ann\u00E9e");
		anneeDep_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		anneeDep_lbl.setBounds(48, 10, 45, 13);
		dateDepInput.add(anneeDep_lbl);

		JLabel moisDep_lbl = new JLabel("Mois");
		moisDep_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		moisDep_lbl.setBounds(182, 10, 45, 13);
		dateDepInput.add(moisDep_lbl);

		JLabel jourDep_lbl = new JLabel("Jour");
		jourDep_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		jourDep_lbl.setBounds(319, 10, 45, 13);
		dateDepInput.add(jourDep_lbl);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void dateRet() {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);

		JPanel dateRetInput = new JPanel();
		dateRetInput.setLayout(null);
		dateRetInput.setBounds(148, 405, 423, 67);
		this.add(dateRetInput);

		JComboBox annee_comboBox = new JComboBox();
		annee_comboBox.setModel(new DefaultComboBoxModel(new String[] {Integer.toString(currentYear), Integer.toString(currentYear+1), Integer.toString(currentYear+2), Integer.toString(currentYear+3), Integer.toString(currentYear+4)}));
		annee_comboBox.setBounds(10, 27, 124, 27);
		annee_comboBox.setBackground(viewSettings.SECONDARY);
		dateRetInput.add(annee_comboBox);

		JComboBox jour_comboBox = new JComboBox();
		jour_comboBox.setBounds(278, 27, 124, 27);
		jour_comboBox.setBackground(viewSettings.SECONDARY);
		dateRetInput.add(jour_comboBox);

		JComboBox mois_comboBox = new JComboBox();
		mois_comboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		mois_comboBox.setBounds(144, 27, 124, 27);
		mois_comboBox.setBackground(viewSettings.SECONDARY);
		dateRetInput.add(mois_comboBox);

		setupDayChooser(annee_comboBox, mois_comboBox, jour_comboBox);
		for(JComboBox box : new JComboBox[]{annee_comboBox, jour_comboBox, mois_comboBox} ) {
			//Foreach combo box update date value on change
			box.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
						dateRetour = annee_comboBox.getSelectedItem() + "-" + mois_comboBox.getSelectedItem() + "-" + jour_comboBox.getSelectedItem();
				}
			});

		}
		
		sauvegarder_btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dateRetour = annee_comboBox.getSelectedItem() + "-" + mois_comboBox.getSelectedItem() + "-" + jour_comboBox.getSelectedItem();
			}
		});

		JLabel anneeDep_lbl = new JLabel("Ann\u00E9e");
		anneeDep_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		anneeDep_lbl.setBounds(56, 10, 45, 13);
		dateRetInput.add(anneeDep_lbl);

		JLabel moisDep_lbl = new JLabel("Mois");
		moisDep_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		moisDep_lbl.setBounds(191, 10, 45, 13);
		dateRetInput.add(moisDep_lbl);

		JLabel jourDep_lbl = new JLabel("Jour");
		jourDep_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		jourDep_lbl.setBounds(315, 10, 45, 13);
		dateRetInput.add(jourDep_lbl);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setupDayChooser(JComboBox annee, JComboBox mois, JComboBox jour) {
		mois.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setupJour(annee, mois, jour);			
			}
		});
		annee.setSelectedIndex(0);
		mois.setSelectedIndex(0);
		setupJour(annee, mois, jour);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setupJour(JComboBox annee, JComboBox mois, JComboBox jour) {
		switch((String)mois.getSelectedItem()) {
		case "01": case"03": case"05": case"07": case "08": case "10": case "12": 
			jour.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09",
					"10", "11", "12", "13", "14", "15", "16", "17", "18","19",
					"20", "21", "22", "23", "24", "25", "26", "27", "28", "29", 
					"30", "31"}));
			break;
		case "02":
			if(Year.isLeap( Integer.parseInt((String)annee.getSelectedItem()))) {
				jour.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09",
						"10", "11", "12", "13", "14", "15", "16", "17", "18","19",
						"20", "21", "22", "23", "24", "25", "26", "27", "28", "29"}));
			}else {
				jour.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09",
						"10", "11", "12", "13", "14", "15", "16", "17", "18","19",
						"20", "21", "22", "23", "24", "25", "26", "27", "28"}));
			}
			break;
		default:
			jour.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09",
					"10", "11", "12", "13", "14", "15", "16", "17", "18","19",
					"20", "21", "22", "23", "24", "25", "26", "27", "28", "29", 
					"30"}));
			break;
		}
		jour.setSelectedIndex(0);
	}



	//Getters
	public JTable getCient_table() {
		return client_table;
	}

	public Date getDateDepart() {
		return Date.valueOf(dateDepart);
	}

	public Date getDateRetour() {
		return Date.valueOf(dateRetour);
	}

	public JTable getReserv_vehi_table() {
		return reserv_vehi_table;
	}

	public JLabel getWarning_lbl() {
		return warning_lbl;
	}
	
	public VehiculeTableModel getVehiculeTableModel() {
		return this.vTable_model;
	}
	
	//Setter
	public void setReservController(ReservationController reservCont) {
		this.cont = reservCont;
	}
}
