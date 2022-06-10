package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.ParkingController;
import model.Parking;

public class ModifierParkingPanel extends JPanel {
	
	private CardLayout cl;
	private JTextField nomParkingTextField;
	private JTextField rueteleTextField;
	private JTextField arrondissementTextField;

	public ModifierParkingPanel (JPanel panel, JTable table, Parking parking) {
		// on a besion de cl pour revenir au menu principal
		this.cl = (CardLayout) panel.getLayout();
		this.setLayout(null);
		this.setBounds(0, 0, 766, 598);
		
		Color mainColor = new Color(75, 0, 130);
		Color secondaryColor = new Color(224, 199, 242);

		nomParkingTextField = new JTextField();
		nomParkingTextField.setBounds(378, 99, 247, 35);
		this.add(nomParkingTextField);
		nomParkingTextField.setColumns(10);
		nomParkingTextField.setText(parking.getNomParking());

		rueteleTextField = new JTextField();
		rueteleTextField.setBounds(378, 210, 247, 35);
		this.add(rueteleTextField);
		rueteleTextField.setColumns(10);
		rueteleTextField.setText(parking.getRueParking());
		
		JComboBox capaciteParkingcomboBox = new JComboBox();		
		capaciteParkingcomboBox.setBackground(secondaryColor);
		capaciteParkingcomboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09",
				"10", "11", "12", "13", "14", "15", "16", "17", "18","19",
				"20", "21", "22", "23", "24", "25", "26", "27", "28", "29", 
				"30", "31","32", "33", "34", "35", "36", "37", "38", "39", "40"}));
		capaciteParkingcomboBox.setBounds(378, 153, 63, 35);
		this.add(capaciteParkingcomboBox);
		capaciteParkingcomboBox.setSelectedIndex(parking.getCapaciteParking()-1);

		JLabel nomParkinglbl = new JLabel("Nom Parking");
		nomParkinglbl.setHorizontalAlignment(SwingConstants.CENTER);
		nomParkinglbl.setBounds(122, 99, 209, 35);
		this.add(nomParkinglbl);

		JLabel capaciteParkingtlbl = new JLabel("Capacite Parking");
		capaciteParkingtlbl.setHorizontalAlignment(SwingConstants.CENTER);
		capaciteParkingtlbl.setBounds(122, 153, 209, 35);
		this.add(capaciteParkingtlbl);

		JLabel rueParkinglbl = new JLabel("Rue Parking");
		rueParkinglbl.setHorizontalAlignment(SwingConstants.CENTER);
		rueParkinglbl.setBounds(122, 210, 209, 35);
		this.add(rueParkinglbl);

		JLabel warningLabel = new JLabel("");
		warningLabel.setForeground(Color.RED);
		warningLabel.setBounds(102, 441, 582, 25);
		add(warningLabel);

		JButton buttonEffacer = new JButton("Effacer Tout");
		buttonEffacer.setBackground(mainColor);
		buttonEffacer.setForeground(Color.WHITE);
		buttonEffacer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// effacer tous les informations pour les redéfinir
				nomParkingTextField.setText("");
				capaciteParkingcomboBox.setSelectedIndex(0);
				rueteleTextField.setText("");
				arrondissementTextField.setText("");
				warningLabel.setText("");
			}
		});
		buttonEffacer.setBounds(320, 387, 129, 43);
		this.add(buttonEffacer);

		JButton buttonRetour = new JButton("Retour");
		buttonRetour.setBackground(mainColor);
		buttonRetour.setForeground(Color.WHITE);
		buttonRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(panel, "parking");
			}
		});
		buttonRetour.setBounds(92, 387, 129, 43);
		this.add(buttonRetour);

		JLabel arrondissementParkinglbl = new JLabel("Arrondissement Parking");
		arrondissementParkinglbl.setHorizontalAlignment(SwingConstants.CENTER);
		arrondissementParkinglbl.setBounds(122, 266, 209, 35);
		this.add(arrondissementParkinglbl);

		arrondissementTextField = new JTextField();
		arrondissementTextField.setBounds(378, 266, 247, 35);
		this.add(arrondissementTextField);
		arrondissementTextField.setColumns(10);
		arrondissementTextField.setText(parking.getArrondissementParking());

		JButton buttonSauvgarder = new JButton("Sauvgarder");
		buttonSauvgarder.setBackground(mainColor);
		buttonSauvgarder.setForeground(Color.WHITE);
		buttonSauvgarder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// tester si l'utilisateur remplir tous les champs
				if (!nomParkingTextField.getText().isBlank() && !rueteleTextField.getText().isBlank() && !arrondissementTextField.getText().isBlank()) {
					// tester si l'utilisateur ne fait des fautes lors de saisie
					if (nomParkingTextField.getText().matches("^[a-zA-Z _'é-]*") && rueteleTextField.getText().matches("^[a-zA-Z][a-zA-Z _'é0-9-]*") && arrondissementTextField.getText().matches("^[a-zA-Z][a-zA-Z _'é-]*")) {
						int nombrePlaceVide = ParkingController.nombrePlaceVide(parking.getCodeParking(), parking.getCapaciteParking());
						Parking parking1 = new Parking (nomParkingTextField.getText(), Integer.parseInt(capaciteParkingcomboBox.getSelectedItem().toString()), rueteleTextField.getText(), arrondissementTextField.getText(), nombrePlaceVide);
						parking1.setCodeParking(parking.getCodeParking());
						//modification du parking
						ParkingController.modifyParking(parking1);
						//si le parking est bien créer dans la base de donnée afficher message "Opération Effectuée avce Succée"
						JOptionPane.showConfirmDialog(null, "Operation Effectuee avce Succee", "Succee",
									JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
							cl.show(panel, "parking");
						
					} else {
						warningLabel.setText("*Nom : chaine de caractere \n rue : chaine de caractere \n arrondissement : chaine de caractere");
					}
				} else {
					warningLabel.setText("*Vous devez remplir tous les champs");
				}
				// rafraîchir le tableau
				ParkingController.fetchAll(table);
			}
		});
		buttonSauvgarder.setBounds(564, 387, 129, 43);
		this.add(buttonSauvgarder);
		
	}

}
