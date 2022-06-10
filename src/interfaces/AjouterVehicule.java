package interfaces;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.ParkingController;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;

public class AjouterVehicule extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	/**
	 * Create the dialog.
	 */
	public AjouterVehicule(int codeParking, int capacite) {
		
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		setBounds(100, 100, 607, 383);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 28, 528, 202);
		contentPanel.add(scrollPane);
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		Object[] columns = { "Matricule", "Marque", "Prix de Location" };
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnCount(3);
		model.setColumnIdentifiers(columns);
		table.setModel(model);
		scrollPane.setViewportView(table);
		
		ParkingController.findVehicule(table);
		
		JButton btnNewButton_1 = new JButton("Quitter");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				get().dispose();
			}
		});
		btnNewButton_1.setBounds(27, 283, 115, 28);
		contentPanel.add(btnNewButton_1);
		
		JLabel warningLabel = new JLabel("");
		warningLabel.setForeground(Color.RED);
		warningLabel.setBounds(37, 241, 507, 26);
		contentPanel.add(warningLabel);
		
		
		JButton btnNewButton = new JButton("Ajouter");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				if (index >= 0) {
					String codeVehicule = table.getValueAt(index, 0).toString();
					int nombrePlaceVide = ParkingController.nombrePlaceVide(codeParking, capacite);
					if (nombrePlaceVide > 0) {
						ParkingController.addVehicule(codeVehicule, codeParking);
					} else {
						warningLabel.setText("*Le parking sélectionné n'a pas des places vides");
					}
				} else {
					warningLabel.setText("*Vous devez séléctionner une vehicule pour l'ajouter");
				}
				ParkingController.findVehicule(table);
			}
		});
		btnNewButton.setBounds(442, 278, 115, 33);
		contentPanel.add(btnNewButton);
	}
	
	// on a besoin d'une référence vers la fenetre pour la fermé
	public JDialog get () {
		return this;
	}
}
