package view;

import java.awt.CardLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.time.Year;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.VehiculeController;
import interfaces.MainInterface;
import model.Vehicule;

public class AddNewVehicule  extends JPanel{

// ATTRIBUTS DE LA CLASSE ADDNEWVEHICULE

	private MainInterface mainInterface;
	private JTable table;
	private CardLayout cl;
	private JTextField ImmatriculationVehicule;
	private JTextField marqueVehicule;
	private JTextField typeVehicule;
	private JTextField carburant;
	private JTextField kilometrage;
	private JComboBox YcomboBox;
	private JComboBox McomboBox;
	private JComboBox DcomboBox;
	private JComboBox parkComboBox;
	private JTextField prixLocation;
	private JCheckBox disponible;
	private JLabel lbl_disp ;
	private static VehiculePanel vehiculePanel;

// CONSTRUCTOR

	public AddNewVehicule(MainInterface mainInterface) {
		setLayout(null);
		setBounds(new Rectangle(0, 0, 732, 547));
		this.mainInterface = mainInterface;
		this.cl = (CardLayout) mainInterface.getMainPanel().getLayout();
		initialize();
	}

// METHODE INITIALIZE QUI CREE UN PANEL

	@SuppressWarnings("unchecked")
	private void initialize() {
		setLayout(null);
		setLayout(null);
		JLabel lblNewLabel = new JLabel("Ajout d'un nouveau v\u00E9hicule");
		lblNewLabel.setBounds(116, 11, 549, 46);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(lblNewLabel);

		JLabel matricule_lbl = new JLabel("Immatriculation:");
		matricule_lbl.setBounds(97, 67, 100, 20);
		matricule_lbl.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(matricule_lbl);

		ImmatriculationVehicule = new JTextField();
		ImmatriculationVehicule.setBounds(97, 91, 249, 27);
		ImmatriculationVehicule.setColumns(10);
		this.add(ImmatriculationVehicule);
//AJOUTER L'ENREGISTREMENT LORSQU'ON CLIQUE SUR BOUTON ENTRER
		ImmatriculationVehicule.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(!VehiculeController.empty(AddNewVehicule.this)) {
						try {
							@SuppressWarnings("deprecation")
							Vehicule V = new Vehicule(ImmatriculationVehicule.getText(),
									marqueVehicule.getText(),
									typeVehicule.getText(),
									carburant.getText(),
									Long.parseLong(kilometrage.getText()),
									Date.valueOf(YcomboBox.getSelectedItem() +"-"+McomboBox.getSelectedItem()+"-"+DcomboBox.getSelectedItem()),
									Integer.parseInt(parkComboBox.getSelectedItem().toString().split("-")[0]),
									Integer.parseInt(prixLocation.getText()),
									disponible.isSelected());
							VehiculeController.saveNewVehicule(V);
						}catch(NumberFormatException ex) {
							JOptionPane.showMessageDialog(null,ex.getMessage(), "Entrer un nombre", JOptionPane.WARNING_MESSAGE);
						}
					}
					else {
						JOptionPane.showMessageDialog(null,"Un ou plusieurs champs sont vides", "Champs vides", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});

		JLabel marque_lbl = new JLabel("Marque:");
		marque_lbl.setBounds(401, 72, 81, 20);
		marque_lbl.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(marque_lbl);

		marqueVehicule = new JTextField();
		marqueVehicule.setBounds(401, 91, 242, 27);
		marqueVehicule.setColumns(10);
		this.add(marqueVehicule);

//AJOUTER L'ENREGISTREMENT LORSQU'ON CLIQUE SUR BOUTON ENTRER

		marqueVehicule.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(!VehiculeController.empty(AddNewVehicule.this)) {
						try {
							@SuppressWarnings("deprecation")
							Vehicule V = new Vehicule(ImmatriculationVehicule.getText(),
									marqueVehicule.getText(),
									typeVehicule.getText(),
									carburant.getText(),
									Long.parseLong(kilometrage.getText()),
									Date.valueOf(YcomboBox.getSelectedItem() +"-"+McomboBox.getSelectedItem()+"-"+DcomboBox.getSelectedItem()),
									Integer.parseInt(parkComboBox.getSelectedItem().toString().split("-")[0]),
									Integer.parseInt(prixLocation.getText()),
									disponible.isSelected());
							VehiculeController.saveNewVehicule(V);
						}catch(NumberFormatException ex) {
							JOptionPane.showMessageDialog(null,ex.getMessage(), "Entrer un nombre", JOptionPane.WARNING_MESSAGE);
						}
					}
					else {
						JOptionPane.showMessageDialog(null,"Un ou plusieurs champs sont vides", "Champs vides", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});

		JLabel type_lbl = new JLabel("Type:");
		type_lbl.setBounds(97, 141, 120, 20);
		type_lbl.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(type_lbl);

		typeVehicule = new JTextField();
		typeVehicule.setBounds(97, 161, 249, 27);
		typeVehicule.setColumns(10);
		this.add(typeVehicule);

//AJOUTER L'ENREGISTREMENT LORSQU'ON CLIQUE SUR BOUTON ENTRER
		typeVehicule.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(!VehiculeController.empty(AddNewVehicule.this)) {
						try {
							@SuppressWarnings("deprecation")
							Vehicule V = new Vehicule(ImmatriculationVehicule.getText(),
									marqueVehicule.getText(),
									typeVehicule.getText(),
									carburant.getText(),
									Long.parseLong(kilometrage.getText()),
									Date.valueOf(YcomboBox.getSelectedItem() +"-"+McomboBox.getSelectedItem()+"-"+DcomboBox.getSelectedItem()),
									Integer.parseInt(parkComboBox.getSelectedItem().toString().split("-")[0]),
									Integer.parseInt(prixLocation.getText()),
									disponible.isSelected());
							VehiculeController.saveNewVehicule(V);
//							AddUser.this.mainInterface.setMainPanel(AddUser.this.userPanel,"user");	//revenir au menu precedent
						}catch(NumberFormatException ex) {
							JOptionPane.showMessageDialog(null,ex.getMessage(), "Entrer un nombre", JOptionPane.WARNING_MESSAGE);
						}
					}
					else {
						JOptionPane.showMessageDialog(null,"Un ou plusieurs champs sont vides", "Champs vides", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});

		JLabel carburant_lbl = new JLabel("Carburant:");
		carburant_lbl.setBounds(401, 141, 112, 20);
		carburant_lbl.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(carburant_lbl);

		carburant = new JTextField();
		carburant.setBounds(401, 161, 242, 27);
		carburant.setColumns(10);
		this.add(carburant);
//AJOUTER L'ENREGISTREMENT LORSQU'ON CLIQUE SUR BOUTON ENTRER
		carburant.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(!VehiculeController.empty(AddNewVehicule.this)) {
						try {
							@SuppressWarnings("deprecation")
							Vehicule V = new Vehicule(ImmatriculationVehicule.getText(),
									marqueVehicule.getText(),
									typeVehicule.getText(),
									carburant.getText(),
									Long.parseLong(kilometrage.getText()),
									Date.valueOf(YcomboBox.getSelectedItem() +"-"+McomboBox.getSelectedItem()+"-"+DcomboBox.getSelectedItem()),
									Integer.parseInt(parkComboBox.getSelectedItem().toString().split("-")[0]),
									Integer.parseInt(prixLocation.getText()),
									disponible.isSelected());
							VehiculeController.saveNewVehicule(V);
//							AddUser.this.mainInterface.setMainPanel(AddUser.this.userPanel,"user");	//revenir au menu precedent
						}catch(NumberFormatException ex) {
							JOptionPane.showMessageDialog(null,ex.getMessage(), "Entrer un nombre", JOptionPane.WARNING_MESSAGE);
						}
					}
					else {
						JOptionPane.showMessageDialog(null,"Un ou plusieurs champs sont vides", "Champs vides", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});

// BOUTON ENREGISTRER  VEHICULE
		JButton saveNewVehicule = new JButton("Enregistrer");
		saveNewVehicule.setBackground(viewSettings.MAIN);
		saveNewVehicule.setForeground(viewSettings.WHITE);
		saveNewVehicule.setBounds(544, 486, 159, 37);
		saveNewVehicule.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!VehiculeController.empty(AddNewVehicule.this)) {
					try {
						@SuppressWarnings("deprecation")
						Vehicule V = new Vehicule(ImmatriculationVehicule.getText(),
								marqueVehicule.getText(),
								typeVehicule.getText(),
								carburant.getText(),
								Long.parseLong(kilometrage.getText()),
								Date.valueOf(YcomboBox.getSelectedItem() +"-"+McomboBox.getSelectedItem()+"-"+DcomboBox.getSelectedItem()),
								Integer.parseInt(parkComboBox.getSelectedItem().toString().split("-")[0]),//prendre le code park uniquement
								Integer.parseInt(prixLocation.getText()),
								disponible.isSelected());
						VehiculeController.saveNewVehicule(V);
					}catch(NumberFormatException ex) {
						JOptionPane.showMessageDialog(null,ex.getMessage(), "Entrer un nombre", JOptionPane.WARNING_MESSAGE);
					}
				}
				else {
					JOptionPane.showMessageDialog(null,"Un ou plusieurs champs sont vides", "Champs vides", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		this.add(saveNewVehicule);

// BOUTON POUR VIDER TOUT LES CHAMPS
		JButton deleteNewVehiculeFields = new JButton("Effacer tout");
		deleteNewVehiculeFields.setBackground(viewSettings.SECONDARY);
		deleteNewVehiculeFields.setBounds(284, 486, 159, 37);
		deleteNewVehiculeFields.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VehiculeController.emptyAddFields(AddNewVehicule.this);
			}
		});
		this.add(deleteNewVehiculeFields);

// BOUTON POUR ANNULER L'AJOUT
		JButton CancelNewVehiculeCreation = new JButton("Annuler");
		CancelNewVehiculeCreation.setBackground(viewSettings.MAIN);
		CancelNewVehiculeCreation.setForeground(viewSettings.WHITE);
		CancelNewVehiculeCreation.setBounds(28, 486, 159, 37);
		CancelNewVehiculeCreation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VehiculeController.cancel(AddNewVehicule.this.mainInterface);//Annuler l'enregistrement
			}
		});
		this.add(CancelNewVehiculeCreation);

		JLabel kilometrage_lbl = new JLabel("Kilom\u00E9trage:");
		kilometrage_lbl.setBounds(96, 215, 121, 20);
		kilometrage_lbl.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(kilometrage_lbl);

		kilometrage = new JTextField();
		kilometrage.setBounds(97, 240, 249, 27);
		kilometrage.setColumns(10);
		this.add(kilometrage);

//AJOUTER L'ENREGISTREMENT LORSQU'ON CLIQUE SUR BOUTON ENTRER

		kilometrage.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(!VehiculeController.empty(AddNewVehicule.this)) {
						try {
							@SuppressWarnings("deprecation")
							Vehicule V = new Vehicule(ImmatriculationVehicule.getText(),
									marqueVehicule.getText(),
									typeVehicule.getText(),
									carburant.getText(),
									Long.parseLong(kilometrage.getText()),
									Date.valueOf(YcomboBox.getSelectedItem() +"-"+McomboBox.getSelectedItem()+"-"+DcomboBox.getSelectedItem()),
									Integer.parseInt(parkComboBox.getSelectedItem().toString().split("-")[0]),
									Integer.parseInt(prixLocation.getText()),
									disponible.isSelected());
							VehiculeController.saveNewVehicule(V);
//							AddUser.this.mainInterface.setMainPanel(AddUser.this.userPanel,"user");	//revenir au menu precedent
						}catch(NumberFormatException ex) {
							JOptionPane.showMessageDialog(null,ex.getMessage(), "Entrer un nombre", JOptionPane.WARNING_MESSAGE);
						}
					}
					else {
						JOptionPane.showMessageDialog(null,"Un ou plusieurs champs sont vides", "Champs vides", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});


		JLabel code_park_lbl = new JLabel("Nom parking");
		code_park_lbl.setBounds(97, 295, 112, 20);
		code_park_lbl.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(code_park_lbl);

		disponible = new JCheckBox("Disponibilit\u00E9:");
		disponible.setBackground(viewSettings.SECONDARY);
		disponible.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VehiculeController.changeDisponibility(AddNewVehicule.this);
			}
		});
		disponible.setSelected(true);
		disponible.setIconTextGap(70);
		disponible.setHorizontalTextPosition(SwingConstants.LEFT);
		disponible.setHorizontalAlignment(SwingConstants.LEFT);
		disponible.setBounds(401, 314, 189, 27);
		add(disponible);

		lbl_disp = new JLabel("Disponible");
		lbl_disp.setBounds(523, 347, 120, 27);
		add(lbl_disp);


		JLabel date_miseCirc_lbl = new JLabel("Date de mise en circulation:");
		date_miseCirc_lbl.setHorizontalAlignment(SwingConstants.LEFT);
		date_miseCirc_lbl.setBounds(97, 354, 202, 37);
		add(date_miseCirc_lbl);

		YcomboBox = new JComboBox();
		YcomboBox.setBackground(viewSettings.SECONDARY);
		Year year= Year.now();
		ArrayList<Year> YearAL = new ArrayList<>();
		while(year.isAfter(Year.parse("1949"))) {
			YearAL.add(year);
			year=year.minusYears(1);
		}
		YcomboBox.setModel(new DefaultComboBoxModel(YearAL.toArray()));
		YcomboBox.setBounds(138, 430, 110, 27);
		add(YcomboBox);

		McomboBox = new JComboBox();
		McomboBox.setBackground(viewSettings.SECONDARY);
		McomboBox.setBounds(304, 430, 110, 27);
		McomboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"}));
		add(McomboBox);

		DcomboBox = new JComboBox();
		DcomboBox.setBackground(viewSettings.SECONDARY);
		DcomboBox.setBounds(466, 430, 110, 27);
		DayCBSetModel(YcomboBox,McomboBox,DcomboBox);//RENSEIGNER LE JOUR SELON L'ANNEE ET LE MOIS QUE L'UTILISATEUR A CHOISI
		add(DcomboBox);

		JLabel annee_lbl = new JLabel("Ann\u00E9e");
		annee_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		annee_lbl.setBounds(138, 392, 110, 33);
		add(annee_lbl);

		JLabel mois_lbl = new JLabel("Mois");
		mois_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		mois_lbl.setBounds(305, 395, 109, 30);
		add(mois_lbl);

		JLabel jour_lbl = new JLabel("Jour");
		jour_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		jour_lbl.setBounds(466, 395, 110, 31);
		add(jour_lbl);

		JLabel prixLocation_lbl = new JLabel("Prix location:");
		prixLocation_lbl.setHorizontalAlignment(SwingConstants.LEFT);
		prixLocation_lbl.setBounds(401, 215, 90, 20);
		add(prixLocation_lbl);

		prixLocation = new JTextField();
		prixLocation.setColumns(10);
		prixLocation.setBounds(401, 240, 242, 27);
		add(prixLocation);

//AJOUTER L'ENREGISTREMENT LORSQU'ON CLIQUE SUR BOUTON ENTRER

		prixLocation.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					if(!VehiculeController.empty(AddNewVehicule.this)) {
						try {
							@SuppressWarnings("deprecation")
							Vehicule V = new Vehicule(ImmatriculationVehicule.getText(),
									marqueVehicule.getText(),
									typeVehicule.getText(),
									carburant.getText(),
									Long.parseLong(kilometrage.getText()),
									Date.valueOf(YcomboBox.getSelectedItem() +"-"+McomboBox.getSelectedItem()+"-"+DcomboBox.getSelectedItem()),
									Integer.parseInt(parkComboBox.getSelectedItem().toString().split("-")[0]),
									Integer.parseInt(prixLocation.getText()),
									disponible.isSelected());
							VehiculeController.saveNewVehicule(V);
//							AddUser.this.mainInterface.setMainPanel(AddUser.this.userPanel,"user");	//revenir au menu precedent
						}catch(NumberFormatException ex) {
							JOptionPane.showMessageDialog(null,ex.getMessage(), "Entrer un nombre", JOptionPane.WARNING_MESSAGE);
						}
					}
					else {
						JOptionPane.showMessageDialog(null,"Un ou plusieurs champs sont vides", "Champs vides", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});

		parkComboBox = new JComboBox();
		parkComboBox.setBackground(viewSettings.SECONDARY);
		parkComboBox.setBounds(97, 314, 154, 27);
		add(parkComboBox);
		parkComboBox.setModel(new DefaultComboBoxModel(VehiculeController.ParksNames().toArray()));
	}



//METHODE RENSEIGNANT LE DAY COMBOBOX SELON LE MOIS ET L'ANNEE
	private void DayCBSetModel(JComboBox annee, JComboBox mois, JComboBox jour) {
		mois.addActionListener(new ActionListener() {
			@Override
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
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
			}
		});
	}


	// GETTERS
	public JTextField getImmatriculationVehicule() {
		return this.ImmatriculationVehicule;
	}
	public JTextField getMarqueVehicule() {
		return marqueVehicule;
	}
	public JTextField getTypeVehicule() {
		return typeVehicule;
	}
	public JTextField getCarburant() {
		return carburant;
	}
	public JTextField getKilometrage() {
		return kilometrage;
	}
	public JComboBox getYcomboBox() {
		return YcomboBox;
	}
	public JComboBox getParkComboBox() {
		return parkComboBox;
	}
	public JComboBox getMcomboBox() {
		return McomboBox;
	}
	public JComboBox getDcomboBox() {
		return DcomboBox;
	}

	public JTextField getPrixLocation() {
		return prixLocation;
	}
	public JCheckBox getDisponible() {
		return disponible;
	}
	public JLabel getLbl_disp() {
		return lbl_disp;
	}


//SETTERS
	public static void setPanel(VehiculePanel p) {// POUR POUVOIR REVENIR AU MENU PRECEDENT
		AddNewVehicule.vehiculePanel=p;
	}

}
