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

				JButton buttonRetour = new JButton("Retour");
				buttonRetour.setBackground(new Color(75, 0, 130));
				buttonRetour.setForeground(Color.WHITE);
				buttonRetour.setFont(new Font("Tahoma", Font.PLAIN, 16));
				buttonRetour.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cl.show(panel, "parking");
					}
				});
				buttonRetour.setBounds(592, 508, 129, 43);
				this.add(buttonRetour);

				JLabel arrondissementParkinglbl = new JLabel("arrondissement");
				arrondissementParkinglbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
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
				nobrePlaceVidelbl.setFont(new Font("Tahoma", Font.PLAIN, 16));
				nobrePlaceVidelbl.setHorizontalAlignment(SwingConstants.CENTER);
				nobrePlaceVidelbl.setBounds(122, 285, 209, 35);
				add(nobrePlaceVidelbl);
				
				JLabel placeVideParking = new JLabel("");
				placeVideParking.setBounds(385, 285, 209, 35);
				add(placeVideParking);
				placeVideParking.setText(parking.getNombrePlaceVide()+"");
				
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(73, 347, 648, 138);
				add(scrollPane);
				
				vehiculeTable = new JTable();
				scrollPane.setViewportView(vehiculeTable);
				ParkingController.findVehicule(vehiculeTable, parking.getCodeParking());
	}
}
