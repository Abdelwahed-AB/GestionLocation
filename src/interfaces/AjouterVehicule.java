package interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.ParkingController;
import view.viewSettings;

public class AjouterVehicule extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;

	/**
	 * Create the dialog.
	 */
	public AjouterVehicule(int codeParking, int capacite) {

		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		setBounds(100, 100, 607, 427);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(27, 73, 528, 202);
		contentPanel.add(scrollPane);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setSelectionBackground(viewSettings.SECONDARY);
		Object[] columns = { "Matricule", "Marque", "Prix de Location" };
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnCount(3);
		model.setColumnIdentifiers(columns);
		table.setModel(model);
		scrollPane.setViewportView(table);

		ParkingController.findVehicule(table);

		JButton btnNewButton_1 = new JButton("Quitter");
		btnNewButton_1.setForeground(viewSettings.WHITE);
		btnNewButton_1.setBackground(viewSettings.MAIN);
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				get().dispose();
			}
		});
		btnNewButton_1.setBounds(27, 332, 115, 28);
		contentPanel.add(btnNewButton_1);

		JLabel warningLabel = new JLabel("");
		warningLabel.setForeground(Color.RED);
		warningLabel.setBounds(37, 286, 507, 26);
		contentPanel.add(warningLabel);

		JButton btnNewButton = new JButton("Ajouter");
		btnNewButton.setForeground(viewSettings.WHITE);
		btnNewButton.setBackground(viewSettings.MAIN);
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = table.getSelectedRow();
				if (index >= 0) {
					String codeVehicule = table.getValueAt(index, 0).toString();
					int nombrePlaceVide = ParkingController.nombrePlaceVide(codeParking, capacite);
					if (nombrePlaceVide > 0) {
						ParkingController.addVehicule(codeVehicule, codeParking);
						warningLabel.setText("");
					} else {
						warningLabel.setText("*Le parking selectionnee n'a pas des places vides");
					}
				} else {
					warningLabel.setText("*Vous devez selectionnee une vehicule pour l'ajouter");
				}
				ParkingController.findVehicule(table);
			}
		});
		btnNewButton.setBounds(440, 330, 115, 33);
		contentPanel.add(btnNewButton);

		JLabel lblNewLabel = new JLabel("Selectionner une vehicule");
		lblNewLabel.setBounds(37, 36, 277, 26);
		contentPanel.add(lblNewLabel);
	}

	// on a besoin d'une référence vers la fenetre pour la fermé
	public JDialog get() {
		return this;
	}
}
