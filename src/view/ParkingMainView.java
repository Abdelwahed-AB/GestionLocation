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
import interfaces.AjouterVehicule;
import model.Parking;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
		
		JLabel warninglbl = new JLabel("");
		warninglbl.setForeground(Color.RED);
		warninglbl.setBounds(10, 51, 574, 21);
		add(warninglbl);

		parkingtable = new JTable();
		parkingtable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				//si l'utilisateur presse le button supprimer 
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					// tester si le client a séléctionné une ligne
					int index = parkingtable.getSelectedRow();
					if (index >= 0) {
						int result = JOptionPane.showConfirmDialog(null, "Etes-vous Sure?", "Confirmation",
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (result == JOptionPane.YES_OPTION) {
							ParkingController.deleteParking(Integer.parseInt(parkingtable.getModel().getValueAt(index, 0).toString()));
							ParkingController.fetchAll(parkingtable);
							warninglbl.setText("");
						}
					} else {
						warninglbl.setText("*Vous devez séléctionnée un élément du tableau pour le supprimer!");
					}
					// rafraîchir le tableau
					ParkingController.fetchAll(parkingtable);
				}
			}
		});
		parkingtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Object[] parkingcolumns = { "code", "nom", "capacite" };
		DefaultTableModel parkingmodel = new DefaultTableModel();
		parkingmodel.setColumnCount(3);
		parkingmodel.setColumnIdentifiers(parkingcolumns);
		parkingtable.setModel(parkingmodel);
		parkingscrollPane.setViewportView(parkingtable);

		JButton parkingSearchButton = new JButton("Rechercher");
		parkingSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					String string = searchParkingTextField.getText();
					// si le champ de recherche est vide une message doit affiché
					if (!string.isBlank()) {
					ParkingController.findParkingByName(string, parkingtable);
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
		searchParkingTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				//si l'utilisateur presse le button entrer
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String string = searchParkingTextField.getText();
					// si le champ de recherche est vide une message doit affiché
					if (!string.isBlank()) {
					ParkingController.findParkingByName(string, parkingtable);
					warninglbl.setText("");
				} else {
					warninglbl.setText("*Vous deuvez remplir le nom du parking tu as en train de chercher");
				}
				}
			}
		});
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
					warninglbl.setText("");
				} else {
					// si l'utilisateur ne séléctionne aucun ligne de tableau
					warninglbl.setText("*Vous devez séléctionnée un élément du tableau pour le modifier!");
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
					int result = JOptionPane.showConfirmDialog(null, "Etes-vous Sure?", "Confirmation",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (result == JOptionPane.YES_OPTION) {
						ParkingController.deleteParking(Integer.parseInt(parkingtable.getModel().getValueAt(index, 0).toString()));
						ParkingController.fetchAll(parkingtable);
						warninglbl.setText("");
					}
				} else {
					warninglbl.setText("*Vous devez séléctionnée un élément du tableau pour le supprimer!");
				}
				// rafraîchir le tableau
				ParkingController.fetchAll(parkingtable);
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
					warninglbl.setText("");
				} else {
					warninglbl.setText("*Vous ddevez séléctionnée un élément du tableau pour l'afficher!");
				}
			}
		});
		afficherParkingButton.setBounds(594, 123, 128, 36);
		this.add(afficherParkingButton);
		
		JButton ajouterVehiculeButton = new JButton("Ajouter Vehicule");
		ajouterVehiculeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = parkingtable.getSelectedRow();
				if (index >= 0) {
					int codeParking = Integer.parseInt(parkingtable.getValueAt(index, 0).toString());
					int capacite = Integer.parseInt(parkingtable.getValueAt(index, 2).toString());
					//calculer le nombre des places vides d'une park avant d'affecter la vehicule
					int nombrePlaceVide = ParkingController.nombrePlaceVide(codeParking, capacite);
					if (nombrePlaceVide > 0) {
						AjouterVehicule ajouterVehicule = new AjouterVehicule(codeParking, capacite);
						warninglbl.setText("");
					} else {
						warninglbl.setText("*Le parking sélectionné n'a pas des places vides");
					}
				} else {
					warninglbl.setText("*Vous devez séléctionnée un élément du tableau pour l'aafecter une vehicule");
				}
			}
		});
		ajouterVehiculeButton.setBounds(594, 311, 128, 36);
		add(ajouterVehiculeButton);

	}
}
