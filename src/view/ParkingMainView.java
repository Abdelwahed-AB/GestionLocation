package view;

import java.awt.CardLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import controller.ParkingController;
import model.Parking;

import javax.swing.JLabel;
import java.awt.Color;

public class ParkingMainView extends JPanel {

	// on a besion de ces variables à l'interieur de plusieurs fonction donc on les
		// rendre globals
	private CardLayout cl;
	private JTable parkingtable;
	protected JTextField searchParkingTextField;
	
	public ParkingMainView(JPanel panel) {
		initialize (panel);
		ParkingController.fetchAll(parkingtable);
		
	}

	public void initialize (JPanel panel) {
		// on a besion de cl pour revenir au menu principal
		this.cl = (CardLayout) panel.getLayout();
		this.setLayout(null);
		this.setBounds(0, 0, 766, 598);
				
				
		JScrollPane parkingscrollPane = new JScrollPane();
		parkingscrollPane.setBounds(10, 76, 574, 478);
		this.add(parkingscrollPane);

		parkingtable = new JTable();
		parkingtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Object[] parkingcolumns = { "code", "nom", "capacite" };
		DefaultTableModel parkingmodel = new DefaultTableModel();
		parkingmodel.setColumnCount(3);
		parkingmodel.setColumnIdentifiers(parkingcolumns);
		parkingtable.setModel(parkingmodel);
		parkingscrollPane.setViewportView(parkingtable);
		
		JLabel warninglbl = new JLabel("");
		warninglbl.setForeground(Color.RED);
		warninglbl.setBounds(10, 51, 574, 21);
		add(warninglbl);

		JButton parkingSearchButton = new JButton("Rechercher");
		parkingSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					String string = searchParkingTextField.getText();
					// si le champ de recherche est vide une message doit affiché
					if (!string.isBlank()) {
					ParkingController.findParkingByName(string, parkingtable);
					searchParkingTextField.setText("");
					warninglbl.setText("");
				} else {
					warninglbl.setText("*Vous deuvez remplir le nom du parking tu as en train de chercher");
				}

			}
		});
		parkingSearchButton.setBounds(594, 11, 128, 36);
		this.add(parkingSearchButton);

		JButton nouveauParkingButton = new JButton("Nouveau Parking");
		nouveauParkingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ouvrir la fenetre créer nouveau parking
				NouveauParkingPanel nouveauParkingPanel = new NouveauParkingPanel(panel, parkingtable);
				panel.add(nouveauParkingPanel, "nouveauParkingPanel");
				cl.show(panel, "nouveauParkingPanel");
				warninglbl.setText("");
			}
		});
		nouveauParkingButton.setBounds(594, 76, 128, 36);
		this.add(nouveauParkingButton);

		searchParkingTextField = new JTextField();
		searchParkingTextField.setBounds(10, 11, 574, 36);
		this.add(searchParkingTextField);
		searchParkingTextField.setColumns(10);

		JButton modifierParkingButton = new JButton("Modifier");
		modifierParkingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// recuperer l'index de la ligne du tableau séléctionnée
				int index = parkingtable.getSelectedRow();
				if (index >= 0) {
					Parking parking = ParkingController.findParkingByCode((int) parkingtable.getModel().getValueAt(index, 0));
					// ouvrir la fenetre modifier client
					ModifierParkingPanel modifierParkingPanel = new ModifierParkingPanel(panel, parkingtable, parking);
					panel.add(modifierParkingPanel, "modifierParkingPanel");
					cl.show(panel, "modifierParkingPanel");
				} else {
					// si l'utilisateur ne séléctionne aucun ligne de tableau
					JOptionPane.showConfirmDialog(null, "Tu dois séléctionnée un élément du tableau pour le modifier!",
							"Attention", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
				}
				warninglbl.setText("");
			}
		});
		modifierParkingButton.setBounds(594, 170, 128, 36);
		this.add(modifierParkingButton);

		JButton actualiserParkingButton = new JButton("Actualiser");
		actualiserParkingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// rafraîchir le tableau des clients
				ParkingController.fetchAll(parkingtable);
				warninglbl.setText("");
			}
		});
		actualiserParkingButton.setBounds(594, 264, 128, 36);
		this.add(actualiserParkingButton);

		JButton supprimerParkingButton = new JButton("Supprimer");
		supprimerParkingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// tester si le client a séléctionné une ligne
				int index = parkingtable.getSelectedRow();
				if (index >= 0) {
					int result = JOptionPane.showConfirmDialog(null, "Avez-vous Sure?", "Confirmation",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (result == JOptionPane.YES_OPTION) {
						ParkingController.deleteParking(Integer.parseInt(parkingtable.getModel().getValueAt(index, 0).toString()));
						ParkingController.fetchAll(parkingtable);
					}
				} else {
					JOptionPane.showConfirmDialog(null, "Tu dois séléctionnée un élément du tableau pour le supprimer!",
							"Attention", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
				}
				// rafraîchir le tableau
				ParkingController.fetchAll(parkingtable);
				warninglbl.setText("");
			}
		});
		supprimerParkingButton.setBounds(594, 217, 128, 36);
		this.add(supprimerParkingButton);

		JButton afficherParkingButton = new JButton("Afficher");
		afficherParkingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = parkingtable.getSelectedRow();
				if (index >= 0) {
					int code = (int) parkingtable.getValueAt(index, 0);
					Parking parking = ParkingController.findParkingByCode(code);
					AfficherParkingPanel afficherParkingPanel = new AfficherParkingPanel(panel, parking);
					panel.add(afficherParkingPanel, "afficherParkingPanel");
					cl.show(panel, "afficherParkingPanel");
				} else {
					JOptionPane.showConfirmDialog(null, "Tu dois séléctionnée un élément du tableau pour l'afficher!",
							"Attention", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
				}
				warninglbl.setText("");
			}
		});
		afficherParkingButton.setBounds(594, 123, 128, 36);
		this.add(afficherParkingButton);

	}
}
