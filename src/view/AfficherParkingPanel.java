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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.ParkingController;
import dao.ParkingDAO;
import model.Parking;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class AfficherParkingPanel extends JPanel {

	
	// on a besion de ces variables à l'interieur de plusieurs fonction donc on les
		// rendre globals
	private CardLayout cl;
	private JTable vehiculeTable;

	public AfficherParkingPanel (JPanel panel, Parking parking) {
		// on a besion de cl pour revenir au menu principal
				this.cl = (CardLayout) panel.getLayout();
				this.setLayout(null);
				this.setBounds(0, 0, 766, 598);

				JLabel nomParkinglbl = new JLabel("nom parking");
				nomParkinglbl.setHorizontalAlignment(SwingConstants.CENTER);
				nomParkinglbl.setBounds(122, 76, 209, 35);
				this.add(nomParkinglbl);

				JLabel capaciteParkingtlbl = new JLabel("capacite");
				capaciteParkingtlbl.setHorizontalAlignment(SwingConstants.CENTER);
				capaciteParkingtlbl.setBounds(122, 124, 209, 35);
				this.add(capaciteParkingtlbl);

				JLabel rueParkinglbl = new JLabel("rue");
				rueParkinglbl.setHorizontalAlignment(SwingConstants.CENTER);
				rueParkinglbl.setBounds(122, 170, 209, 35);
				this.add(rueParkinglbl);

				JButton buttonRetour = new JButton("Retour");
				buttonRetour.setBackground(new Color(75, 0, 130));
				buttonRetour.setForeground(Color.WHITE);
				buttonRetour.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cl.show(panel, "parking");
					}
				});
				buttonRetour.setBounds(592, 508, 129, 43);
				this.add(buttonRetour);

				JLabel arrondissementParkinglbl = new JLabel("arrondissement");
				arrondissementParkinglbl.setHorizontalAlignment(SwingConstants.CENTER);
				arrondissementParkinglbl.setBounds(122, 227, 209, 35);
				this.add(arrondissementParkinglbl);
				
				JLabel nomParking = new JLabel("");
				nomParking.setBounds(385, 76, 209, 35);
				add(nomParking);
				nomParking.setText(parking.getNomParking());
				
				JLabel capaciteParking = new JLabel("");
				capaciteParking.setBounds(385, 126, 209, 35);
				add(capaciteParking);
				capaciteParking.setText(parking.getCapaciteParking()+"");
				
				JLabel rueParking = new JLabel("");
				rueParking.setBounds(385, 172, 209, 35);
				add(rueParking);
				rueParking.setText(parking.getRueParking());
				
				JLabel arrondissementParking = new JLabel("");
				arrondissementParking.setBounds(385, 229, 209, 35);
				add(arrondissementParking);
				arrondissementParking.setText(parking.getArrondissementParking());
				
				JLabel nobrePlaceVidelbl = new JLabel("nombre de place vide");
				nobrePlaceVidelbl.setHorizontalAlignment(SwingConstants.CENTER);
				nobrePlaceVidelbl.setBounds(122, 285, 209, 35);
				add(nobrePlaceVidelbl);
				
				JLabel placeVideParking = new JLabel("");
				placeVideParking.setBounds(385, 285, 209, 35);
				add(placeVideParking);
				placeVideParking.setText(parking.getNombrePlaceVide()+"");
				
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(73, 347, 663, 138);
				add(scrollPane);
				
				vehiculeTable = new JTable();
				vehiculeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				scrollPane.setViewportView(vehiculeTable);
				ParkingController.findVehicule(vehiculeTable, parking.getCodeParking());
				
				JButton removeVehiculeButton = new JButton("Retirer Vehicule");
				removeVehiculeButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int index = vehiculeTable.getSelectedRow();
						if (index >=0) {
							String codeVehicule = vehiculeTable.getValueAt(index, 0).toString();
							ParkingController.removeVehicule(codeVehicule, parking.getCodeParking());
						}else {
							// si l'utilisateur ne séléctionne aucun ligne de tableau
							JOptionPane.showConfirmDialog(null, "Tu dois séléctionnée une vehicule du tableau pour la retirer!",
									"Attention", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
						}
						ParkingController.findVehicule(vehiculeTable, parking.getCodeParking());
					}
				});
				removeVehiculeButton.setBounds(73, 496, 129, 35);
				add(removeVehiculeButton);
	}
}
