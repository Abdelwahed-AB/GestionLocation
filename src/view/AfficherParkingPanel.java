package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import controller.ParkingController;
import model.Parking;

public class AfficherParkingPanel extends JPanel {

	// on a besion de ces variables à l'interieur de plusieurs fonction donc on les
	// rendre globals
	private CardLayout cl;
	private JTable vehiculeTable;

	public AfficherParkingPanel(JPanel panel, Parking parking) {
		// on a besion de cl pour revenir au menu principal
		this.cl = (CardLayout) panel.getLayout();
		this.setLayout(null);
		this.setBounds(0, 0, 766, 598);

		Color mainColor = new Color(75, 0, 130);
		Color secondaryColor = new Color(224, 199, 242);

		JLabel nomParkinglbl = new JLabel("Nom Parking");
		nomParkinglbl.setHorizontalAlignment(SwingConstants.CENTER);
		nomParkinglbl.setBounds(163, 40, 209, 35);
		this.add(nomParkinglbl);

		JLabel capaciteParkingtlbl = new JLabel("Capacite Parking");
		capaciteParkingtlbl.setHorizontalAlignment(SwingConstants.CENTER);
		capaciteParkingtlbl.setBounds(163, 75, 209, 35);
		this.add(capaciteParkingtlbl);

		JLabel rueParkinglbl = new JLabel("Rue Parking");
		rueParkinglbl.setHorizontalAlignment(SwingConstants.CENTER);
		rueParkinglbl.setBounds(163, 107, 209, 35);
		this.add(rueParkinglbl);

		JButton buttonRetour = new JButton("Retour");
		buttonRetour.setBackground(mainColor);
		buttonRetour.setForeground(Color.WHITE);
		buttonRetour.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(panel, "parking");
			}
		});
		buttonRetour.setBounds(58, 457, 129, 43);
		this.add(buttonRetour);

		JLabel arrondissementParkinglbl = new JLabel("Arrondissement Parking");
		arrondissementParkinglbl.setHorizontalAlignment(SwingConstants.CENTER);
		arrondissementParkinglbl.setBounds(163, 142, 209, 35);
		this.add(arrondissementParkinglbl);

		JLabel nomParking = new JLabel("");
		nomParking.setHorizontalAlignment(SwingConstants.CENTER);
		nomParking.setBounds(382, 40, 209, 35);
		add(nomParking);
		nomParking.setText(parking.getNomParking());

		JLabel capaciteParking = new JLabel("");
		capaciteParking.setHorizontalAlignment(SwingConstants.CENTER);
		capaciteParking.setBounds(382, 75, 209, 35);
		add(capaciteParking);
		capaciteParking.setText(parking.getCapaciteParking() + "");

		JLabel rueParking = new JLabel("");
		rueParking.setHorizontalAlignment(SwingConstants.CENTER);
		rueParking.setBounds(382, 107, 209, 35);
		add(rueParking);
		rueParking.setText(parking.getRueParking());

		JLabel arrondissementParking = new JLabel("");
		arrondissementParking.setHorizontalAlignment(SwingConstants.CENTER);
		arrondissementParking.setBounds(382, 142, 209, 35);
		add(arrondissementParking);
		arrondissementParking.setText(parking.getArrondissementParking());

		JLabel nobrePlaceVidelbl = new JLabel("Nombre de place vide");
		nobrePlaceVidelbl.setHorizontalAlignment(SwingConstants.CENTER);
		nobrePlaceVidelbl.setBounds(163, 176, 209, 35);
		add(nobrePlaceVidelbl);

		JLabel placeVideParking = new JLabel("");
		placeVideParking.setHorizontalAlignment(SwingConstants.CENTER);
		placeVideParking.setBounds(382, 176, 209, 35);
		add(placeVideParking);
		placeVideParking.setText(parking.getNombrePlaceVide() + "");

		JLabel warninglbl = new JLabel("");
		warninglbl.setForeground(Color.RED);
		warninglbl.setBounds(68, 383, 466, 24);
		add(warninglbl);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(58, 273, 663, 99);
		add(scrollPane);

		vehiculeTable = new JTable();
		vehiculeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		vehiculeTable.setSelectionBackground(viewSettings.SECONDARY);
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.addColumn("Matricule");
		dtm.addColumn("Marque");
		dtm.addColumn("Prix de Location");
		vehiculeTable.setModel(dtm);
		scrollPane.setViewportView(vehiculeTable);
		ParkingController.findVehicule(vehiculeTable, parking.getCodeParking());

		JButton removeVehiculeButton = new JButton("Retirer Vehicule");
		removeVehiculeButton.setBackground(secondaryColor);
		removeVehiculeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = vehiculeTable.getSelectedRow();
				if (index >= 0) {
					String codeVehicule = vehiculeTable.getValueAt(index, 0).toString();
					ParkingController.removeVehicule(codeVehicule, parking.getCodeParking());
					// augmenter le nombre de place vide
					parking.setNombrePlaceVide(parking.getNombrePlaceVide() + 1);
					// afficher le nombre de place vide
					placeVideParking.setText(parking.getNombrePlaceVide() + "");
					warninglbl.setText("");
				} else {
					// si l'utilisateur ne séléctionne aucun ligne de tableau
					warninglbl.setText("Tu dois selectionnee une vehicule du tableau pour la retirer!");
				}
				ParkingController.findVehicule(vehiculeTable, parking.getCodeParking());
			}
		});
		removeVehiculeButton.setBounds(592, 383, 129, 35);
		add(removeVehiculeButton);

		JLabel lblNewLabel = new JLabel("Les voitures situees dans le parking");
		lblNewLabel.setBounds(58, 227, 209, 35);
		add(lblNewLabel);
	}
}
