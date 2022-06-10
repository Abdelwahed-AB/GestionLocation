package view;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import controller.ClientController;
import model.Client;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ClientMainView extends JPanel {

	// on a besion de ces variables � l'interieur de plusieurs fonction donc on les
	// rendre globals
	private JTable clienttable;
	private CardLayout cl;
	private JTextField searchclienttextField;

	public ClientMainView(JPanel panel) {
		initialize(panel);
		ClientController.fetchAll(clienttable);
	}

	private void initialize(JPanel panel) {
		// on a besion de cl pour revenir au menu principal
		this.cl = (CardLayout) panel.getLayout();
		this.setLayout(null);
		this.setBounds(0, 0, 766, 598);

		JScrollPane clientscrollPane = new JScrollPane();
		clientscrollPane.setBounds(10, 76, 574, 463);
		this.add(clientscrollPane);

		JLabel warninglbl = new JLabel("");
		warninglbl.setForeground(new Color(255, 0, 0));
		warninglbl.setHorizontalAlignment(SwingConstants.LEFT);
		warninglbl.setBounds(10, 51, 574, 22);
		this.add(warninglbl);

		clienttable = new JTable();
		clienttable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// tester si l'utilisateur presse le button de suppression
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					int index = clienttable.getSelectedRow();
					// tester si le client a s�l�ctionn� une ligne
					if (index >= 0) {
						int result = JOptionPane.showConfirmDialog(null, "Etes-vous Sure?", "Confirmation",
								JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (result == JOptionPane.YES_OPTION) {
							ClientController.deleteClient(clienttable.getModel().getValueAt(index, 0).toString());
							ClientController.fetchAll(clienttable);
							warninglbl.setText("");
						}
					} else {
						warninglbl.setText("*Vous devez selectionner un client pour le supprimer");
					}
				}
			}
		});
		clienttable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		clienttable.setSelectionBackground(viewSettings.SECONDARY);
		Object[] clientcolumns = { "id", "nom", "prenom", "numTel" };
		DefaultTableModel clientmodel = new DefaultTableModel();
		clientmodel.setColumnCount(4);
		clientmodel.setColumnIdentifiers(clientcolumns);
		clienttable.setModel(clientmodel);
		clientscrollPane.setViewportView(clienttable);

		JButton buttonRecherche = new JButton("Rechercher");
		buttonRecherche.setBackground(viewSettings.MAIN);
		buttonRecherche.setForeground(viewSettings.WHITE);
		buttonRecherche.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String string = searchclienttextField.getText();
				// si le champ de recherche est vide une message doit affich�
				if (!string.isBlank()) {
					ClientController.findClientByName(string, clienttable);
					warninglbl.setText("");
				} else {
					warninglbl.setText("*Vous deuvez remplir le nom de client vous etes en train de chercher");
				}
			}
		});
		buttonRecherche.setBounds(594, 11, 128, 36);
		this.add(buttonRecherche);

		JButton nouveauClientButton = new JButton("Nouveau Client");
		nouveauClientButton.setBackground(viewSettings.SECONDARY);
		nouveauClientButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ouvrir la fenetre cr�er nouveau client
				NouveauClientPanel nouveauClientPanel = new NouveauClientPanel(panel, clienttable);
				panel.add(nouveauClientPanel, "nouveauClientPanel");
				cl.show(panel, "nouveauClientPanel");
				warninglbl.setText("");
			}
		});
		nouveauClientButton.setBounds(594, 76, 128, 36);
		this.add(nouveauClientButton);

		searchclienttextField = new JTextField();
		searchclienttextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String string = searchclienttextField.getText();
				// tester si l'utilisateur presse la button entr�e
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!string.isBlank()) {
						ClientController.findClientByName(string, clienttable);
						warninglbl.setText("");
					} else {
						warninglbl.setText("*Vous deuvez remplir le nom de client vous etes en train de chercher");
					}
				}
			}
		});
		searchclienttextField.setBounds(10, 11, 574, 36);
		this.add(searchclienttextField);
		searchclienttextField.setColumns(10);

		JButton modifierClientButton = new JButton("Modifier");
		modifierClientButton.setBackground(viewSettings.SECONDARY);
		modifierClientButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// recuperer l'index de la ligne du tableau s�l�ctionn�e
				int index = clienttable.getSelectedRow();
				if (index >= 0) {
					int code = (int) clienttable.getModel().getValueAt(index, 0);
					Client client = ClientController.findClientByCode(code);
					// ouvrir la fenetre modifier client
					ModifierClientPanel modifierClientPanel = new ModifierClientPanel(panel, client, clienttable);
					panel.add(modifierClientPanel, "modifierClientPanel");
					cl.show(panel, "modifierClientPanel");
					warninglbl.setText("");
				} else {
					// si l'utilisateur ne s�l�ctionne aucun ligne de tableau
					warninglbl.setText("*Vous devez selectionner un client pour le modifier");
				}
			}
		});
		modifierClientButton.setBounds(594, 170, 128, 36);
		this.add(modifierClientButton);

		JButton actualiserClientButton = new JButton("Actualiser");
		actualiserClientButton.setBackground(viewSettings.SECONDARY);
		actualiserClientButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// rafra�chir le tableau des clients
				ClientController.fetchAll(clienttable);
				warninglbl.setText("");
			}
		});
		actualiserClientButton.setBounds(594, 264, 128, 36);
		this.add(actualiserClientButton);

		JButton supprimerClientButton = new JButton("Supprimer");
		supprimerClientButton.setBackground(viewSettings.SECONDARY);
		supprimerClientButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = clienttable.getSelectedRow();
				// tester si le client a s�l�ctionn� une ligne
				if (index >= 0) {
					int result = JOptionPane.showConfirmDialog(null, "Etes-vous Sure?", "Confirmation",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (result == JOptionPane.YES_OPTION) {
						ClientController.deleteClient(clienttable.getModel().getValueAt(index, 0).toString());
						ClientController.fetchAll(clienttable);
						warninglbl.setText("");
					}
				} else {
					warninglbl.setText("*Vous devez selectionner un client pour le supprimer");
				}
			}
		});
		supprimerClientButton.setBounds(594, 217, 128, 36);
		this.add(supprimerClientButton);

		JButton afficherClientButton = new JButton("Afficher");
		afficherClientButton.setBackground(viewSettings.SECONDARY);
		afficherClientButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// recuperer l'index de la ligne du tableau s�l�ctionn�e
				int index = clienttable.getSelectedRow();
				if (index >= 0) {
					int code = Integer.parseInt(clienttable.getModel().getValueAt(index, 0).toString());
					Client client = ClientController.findClientByCode(code);
					//ouvrir la fenetre d'affichage
					AfficherClientPanel afficherClientPanel = new AfficherClientPanel(panel, client);
					panel.add(afficherClientPanel, "afficherClientPanel");
					cl.show(panel, "afficherClientPanel");
					warninglbl.setText("");
				} else {
					warninglbl.setText("*Vous devez selectionner un client pour l'afficher");
				}
			}
		});
		afficherClientButton.setBounds(594, 123, 128, 36);
		this.add(afficherClientButton);
	}

}
