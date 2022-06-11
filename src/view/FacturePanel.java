package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import controller.FactureController;
import model.FactureTableModel;

public class FacturePanel extends JPanel {

	private JTextField facture_field;
	private JLabel facture_warning_lbl;

	private FactureTableModel factureTableModel = new FactureTableModel();
	private FactureController cont;
	private JTable facture_table;


	public FacturePanel() {
		this.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 57, 483, 462);
		add(scrollPane);

		facture_table = new JTable(factureTableModel);
		facture_table.setSelectionBackground(viewSettings.SECONDARY);
		scrollPane.setViewportView(facture_table);
		facture_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


		facture_warning_lbl = new JLabel("");
		facture_warning_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		facture_warning_lbl.setForeground(Color.RED);
		facture_warning_lbl.setBounds(522, 265, 193, 113);
		this.add(facture_warning_lbl);

		facture_field = new JTextField();
		facture_field.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					cont.RechercherFacture();
				}
			}
		});
		facture_field.setBounds(23, 10, 340, 36);
		this.add(facture_field);
		facture_field.setColumns(10);

		JButton searchFacture_btn = new JButton("Rechercher");

		searchFacture_btn.setBackground(viewSettings.MAIN);
		searchFacture_btn.setForeground(viewSettings.WHITE);

		searchFacture_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cont.RechercherFacture();
			}
		});
		searchFacture_btn.setBounds(376, 9, 130, 36);
		this.add(searchFacture_btn);

		JButton newFacture_btn = new JButton("Nouvelle facture");
		newFacture_btn.setBackground(viewSettings.SECONDARY);

		newFacture_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cont.goToNewFacture();
			}
		});
		newFacture_btn.setBounds(522, 57, 193, 56);
		this.add(newFacture_btn);

		JButton dltFacture_btn = new JButton("Supprimer");
		dltFacture_btn.setBackground(viewSettings.SECONDARY);
		dltFacture_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				facture_field.setText("");
				cont.SupprimerFacture();
			}
		});
		dltFacture_btn.setBounds(522, 189, 193, 56);
		this.add(dltFacture_btn);

		JButton facture_actualiser_btn = new JButton("Actualiser");
		facture_actualiser_btn.setBackground(viewSettings.SECONDARY);
		facture_actualiser_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				facture_field.setText("");
				cont.ActualiserTableau();
			}
		});
		facture_actualiser_btn.setBounds(522, 463, 193, 56);
		this.add(facture_actualiser_btn);

		JButton imprimer_btn = new JButton("Imprimer");
		imprimer_btn.setBackground(viewSettings.SECONDARY);
		imprimer_btn.setBounds(522, 123, 193, 56);
		imprimer_btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				facture_field.setText("");
				cont.imprimerFacture();
			}
		});
		add(imprimer_btn);

	}


	//getters
	public JTable getFacture_table() {
		return facture_table;
	}

	public JTextField getFacture_field() {
		return facture_field;
	}

	public JLabel getFacture_warning_lbl() {
		return facture_warning_lbl;
	}

	public FactureTableModel getFactureTableModel() {
		return factureTableModel;
	}

	//setter
	public void setFactureController(FactureController cont) {
		this.cont = cont;
	}
}
