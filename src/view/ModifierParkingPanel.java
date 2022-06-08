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

		nomParkingTextField = new JTextField();
		nomParkingTextField.setBounds(378, 76, 247, 35);
		this.add(nomParkingTextField);
		nomParkingTextField.setColumns(10);
		nomParkingTextField.setText(parking.getNomParking());

		rueteleTextField = new JTextField();
		rueteleTextField.setBounds(378, 172, 247, 35);
		this.add(rueteleTextField);
		rueteleTextField.setColumns(10);
		rueteleTextField.setText(parking.getRueParking());
		
		JComboBox capaciteParkingcomboBox = new JComboBox();		
		capaciteParkingcomboBox.setBackground(new Color(224, 199, 242));
		capaciteParkingcomboBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
		capaciteParkingcomboBox.setModel(new DefaultComboBoxModel(new String[] {"01", "02", "03", "04", "05", "06", "07", "08", "09",
				"10", "11", "12", "13", "14", "15", "16", "17", "18","19",
				"20", "21", "22", "23", "24", "25", "26", "27", "28", "29", 
				"30", "31","32", "33", "34", "35", "36", "37", "38", "39", "40"}));
		capaciteParkingcomboBox.setBounds(378, 126, 63, 35);
		this.add(capaciteParkingcomboBox);
		capaciteParkingcomboBox.setSelectedIndex(parking.getCapaciteParking()-1);

		JLabel nomParkinglbl = new JLabel("nom parking");
		nomParkinglbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		nomParkinglbl.setHorizontalAlignment(SwingConstants.CENTER);
		nomParkinglbl.setBounds(122, 76, 209, 35);
		this.add(nomParkinglbl);

		JLabel capaciteParkingtlbl = new JLabel("capacite");
		capaciteParkingtlbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		capaciteParkingtlbl.setHorizontalAlignment(SwingConstants.CENTER);
		capaciteParkingtlbl.setBounds(122, 124, 209, 35);
		this.add(capaciteParkingtlbl);

		JLabel rueParkinglbl = new JLabel("rue");
		rueParkinglbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rueParkinglbl.setHorizontalAlignment(SwingConstants.CENTER);
		rueParkinglbl.setBounds(122, 170, 209, 35);
		this.add(rueParkinglbl);

		JButton buttonEffacer = new JButton("Effacer");
		buttonEffacer.setBackground(new Color(75, 0, 130));
		buttonEffacer.setForeground(Color.WHITE);
		buttonEffacer.setFont(new Font("Tahoma", Font.PLAIN, 16));
		buttonEffacer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// effacer tous les informations pour les redéfinir
				nomParkingTextField.setText("");
				capaciteParkingcomboBox.setSelectedIndex(0);
				rueteleTextField.setText("");
				arrondissementTextField.setText("");
			}
		});
		buttonEffacer.setBounds(341, 384, 129, 43);
		this.add(buttonEffacer);

		JButton buttonRetour = new JButton("Retour");
		buttonRetour.setBackground(new Color(75, 0, 130));
		buttonRetour.setForeground(Color.WHITE);
		buttonRetour.setFont(new Font("Tahoma", Font.PLAIN, 16));
		buttonRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(panel, "parking");
			}
		});
		buttonRetour.setBounds(130, 384, 129, 43);
		this.add(buttonRetour);

		JLabel arrondissementParkinglbl = new JLabel("arrondissement");
		arrondissementParkinglbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
		arrondissementParkinglbl.setHorizontalAlignment(SwingConstants.CENTER);
		arrondissementParkinglbl.setBounds(122, 227, 209, 35);
		this.add(arrondissementParkinglbl);

		arrondissementTextField = new JTextField();
		arrondissementTextField.setBounds(378, 229, 247, 35);
		this.add(arrondissementTextField);
		arrondissementTextField.setColumns(10);
		arrondissementTextField.setText(parking.getArrondissementParking());

		JButton buttonSauvgarder = new JButton("Sauvgarder");
		buttonSauvgarder.setBackground(new Color(75, 0, 130));
		buttonSauvgarder.setForeground(Color.WHITE);
		buttonSauvgarder.setFont(new Font("Tahoma", Font.PLAIN, 16));
		buttonSauvgarder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// tester si l'utilisateur remplir tous les champs
				if (!nomParkingTextField.getText().isBlank() && !rueteleTextField.getText().isBlank() && !arrondissementTextField.getText().isBlank()) {
					// tester si l'utilisateur ne fait des fautes lors de saisie
					if (nomParkingTextField.getText().matches("[a-zA-Z -_'é]*") && rueteleTextField.getText().matches("[a-zA-Z -_'é0-9]*") && arrondissementTextField.getText().matches("[a-zA-Z -_'é]*")) {
						int nombrePlaceVide = ParkingController.nombrePlaceVide(parking.getCodeParking(), parking.getCapaciteParking());
						Parking parking1 = new Parking (nomParkingTextField.getText(), Integer.parseInt(capaciteParkingcomboBox.getSelectedItem().toString()), rueteleTextField.getText(), arrondissementTextField.getText(), nombrePlaceVide);
						parking1.setCodeParking(parking.getCodeParking());
						//modification du parking
						boolean b = ParkingController.modifyParking(parking1);
						//si le parking est bien créer dans la base de donnée afficher message "Opération Effectuée avce Succée"
						if (b) {
							JOptionPane.showConfirmDialog(null, "Opération Effectuée avce Succée", "Succée",
									JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
						} else {
							//si non
							JOptionPane.showConfirmDialog(null, "Opération Echouée", "Echoue",
									JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showConfirmDialog(null,
								"nom : chaine de caractere \n rue : chaine de caractere \n num arrondissement : chaine de caractere",
								"Echoue", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
					}
				} else {
					JOptionPane.showConfirmDialog(null, "Tu dois remplir tous les champs", "Echoue",
							JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
				}
				// rafraîchir le tableau
				ParkingController.fetchAll(table);
			}
		});
		buttonSauvgarder.setBounds(546, 384, 129, 43);
		this.add(buttonSauvgarder);
	}

}
