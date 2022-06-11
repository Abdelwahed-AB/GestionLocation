package view;

import java.awt.CardLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Year;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import controller.ContratController;
import interfaces.MainInterface;
public class ChangeExistingContrat extends JPanel {

// ATTRIBUTS DE LA CLASSE ADDNEWVEHICULE
	private MainInterface mainInterface;
	private JTable table;
	private CardLayout cl;
	private static JComboBox YcomboBox;
	private static JComboBox McomboBox;
	private static JComboBox DcomboBox;
	private static  int oldId;
	private static ContratPanel contratPanel;
// CONSTRUCTOR
	public ChangeExistingContrat(MainInterface mainInterface) {
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
		JLabel lblNewLabel = new JLabel("Modification d'un contrat existant");
		lblNewLabel.setBounds(88, 11, 549, 46);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(lblNewLabel);


// BOUTON ENREGISTRER CONTRAT
		JButton saveChanges = new JButton("Enregistrer");
		saveChanges.setBackground(viewSettings.SECONDARY);
		saveChanges.setBounds(531, 479, 159, 42);
		saveChanges.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ContratController.setCEC(ChangeExistingContrat.this);
				ContratController.saveChanges(ChangeExistingContrat.oldId);
			}
		});
		this.add(saveChanges);

// BOUTON POUR VIDER TOUT LES CHAMPS
		JButton emptyAllFields = new JButton("Effacer tout");
		emptyAllFields.setBackground(viewSettings.SECONDARY);
		emptyAllFields.setBounds(284, 481, 159, 42);
		emptyAllFields.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ContratController.emptyContratFields(ChangeExistingContrat.this);
			}
		});
		this.add(emptyAllFields);

// BOUTON POUR ANNULER LA MODIFICATION
		JButton CancelChanging = new JButton("Annuler");
		CancelChanging.setBackground(viewSettings.SECONDARY);
		CancelChanging.setBounds(27, 481, 159, 42);
		CancelChanging.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ContratController.cancel(ChangeExistingContrat.this.mainInterface);//Annuler l'enregistrement
			}
		});
		this.add(CancelChanging);
//LES LABELS ET COMBOBOX DU PANEL
		JLabel lblNewLabel_1_1_5_2_1 = new JLabel("Date d'\u00E9ch\u00E9ance");
		lblNewLabel_1_1_5_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_1_5_2_1.setBounds(52, 141, 202, 19);
		add(lblNewLabel_1_1_5_2_1);
//COMBOBOX ANNEE
		YcomboBox = new JComboBox();
		YcomboBox.setBackground(viewSettings.SECONDARY);
		YcomboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		Year year=Year.now().plusYears(20);
		ArrayList<Year> YearAL = new ArrayList<>();
		while(year.isAfter(Year.parse("1949"))) {
			YearAL.add(year);
			year=year.minusYears(1);
		}
		YcomboBox.setModel(new DefaultComboBoxModel(YearAL.toArray()));
		YcomboBox.setBounds(88, 228, 110, 27);
		add(YcomboBox);

//COMBOBOX MOIS
		McomboBox = new JComboBox();
		McomboBox.setBackground(viewSettings.SECONDARY);
		McomboBox.setBounds(288, 228, 110, 27);
		McomboBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		add(McomboBox);

		DcomboBox = new JComboBox();
		DcomboBox.setBackground(viewSettings.SECONDARY);
		DcomboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
//COMBOBOX JOUR
		DcomboBox.setBounds(495, 228, 110, 27);
		DayCBSetModel(YcomboBox,McomboBox,DcomboBox);//RENSEIGNER LE JOUR SELON L'ANNEE ET LE MOIS QUE L'UTILISATEUR A CHOISI
		add(DcomboBox);

		JLabel annee_lbl = new JLabel("Ann\u00E9e");
		annee_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		annee_lbl.setBounds(27, 193, 110, 33);
		add(annee_lbl);

		JLabel mois_lbl = new JLabel("Mois");
		mois_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		mois_lbl.setBounds(230, 194, 109, 30);
		add(mois_lbl);

		JLabel jour_lbl = new JLabel("Jour");
		jour_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		jour_lbl.setBounds(432, 194, 110, 31);
		add(jour_lbl);
	}



	//METHODE RENSEIGNANT LE DAY COMBOBOX SELON LE MOIS ET L'ANNEE
	private void DayCBSetModel(JComboBox annee, JComboBox mois, JComboBox jour) {
		mois.addActionListener(new ActionListener() {
			@Override
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				switch((String)mois.getSelectedItem()) {
					case "1": case"3": case"5": case"7": case "8": case "10": case "12":
						jour.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9",
																			"10", "11", "12", "13", "14", "15", "16", "17", "18","19",
																			"20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
																			"30", "31"}));
						break;
					case "2":
						if(Year.isLeap( Integer.parseInt((String)annee.getSelectedItem()))) {
							jour.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9",
																				"10", "11", "12", "13", "14", "15", "16", "17", "18","19",
																				"20", "21", "22", "23", "24", "25", "26", "27", "28", "29"}));
						}else {
							jour.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9",
																				"10", "11", "12", "13", "14", "15", "16", "17", "18","19",
																				"20", "21", "22", "23", "24", "25", "26", "27", "28"}));
							}
						break;
					default:
						jour.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9",
																			"10", "11", "12", "13", "14", "15", "16", "17", "18","19",
																			"20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
																			"30"}));
					break;
				}
			}
		});
	}


	// GETTERS
	public JComboBox getYcomboBox() {
		return YcomboBox;
	}
	public JComboBox getMcomboBox() {
		return McomboBox;
	}
	public JComboBox getDcomboBox() {
		return DcomboBox;
	}
	public static void setPanel(ContratPanel p) {// POUR POUVOIR REVENIR AU MENU PRECEDENT
		ChangeExistingContrat.contratPanel=p;
	}
	public static void setOldId(int oldId) {
		ChangeExistingContrat.oldId=oldId;
	}
}
