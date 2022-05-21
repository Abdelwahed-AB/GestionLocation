package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import controller.FactureController;
import controller.tempFactureController;
import interfaces.CreerFacture;
import model.FactureTableModel;
import javax.swing.table.TableModel;

public class FacturePanel extends JPanel {
	
	private JTextField facture_field;
	private JLabel facture_warning_lbl;
	
	private FactureTableModel factureTableModel = new FactureTableModel();
	private tempFactureController cont = new tempFactureController(this);
	private JTable facture_table;

	public FacturePanel() {
		this.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 70, 493, 467);
		add(scrollPane);
		
		facture_table = new JTable(factureTableModel);
		scrollPane.setViewportView(facture_table);
		facture_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//FactureController.fetchAll(facture_table);
		
		facture_warning_lbl = new JLabel("");
		facture_warning_lbl.setForeground(Color.RED);
		facture_warning_lbl.setBounds(535, 57, 164, 113);
		this.add(facture_warning_lbl);
		
		facture_field = new JTextField();
		facture_field.setBounds(10, 10, 353, 36);
		this.add(facture_field);
		facture_field.setColumns(10);
		
		JButton searchFacture_btn = new JButton("Rechercher");
		searchFacture_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cont.RechercherFacture();
			}
		});
		searchFacture_btn.setBounds(373, 10, 130, 36);
		this.add(searchFacture_btn);
		
		JButton newFacture_btn = new JButton("Nouvelle facture");
		newFacture_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreerFacture newFacture = new CreerFacture(facture_table);
				facture_warning_lbl.setText("");
			}
		});
		newFacture_btn.setBounds(535, 220, 164, 43);
		this.add(newFacture_btn);
		
		JButton dltFacture_btn = new JButton("Supprimer");
		dltFacture_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				int index = facture_table.getSelectedRow();
//				if(index < 0) {
//					//if user hasnt selected a row
//					facture_warning_lbl.setText("<html>Veuillez selectionner une facture à supprimer.</html>");
//					return;
//				}
//				//else reset warning label on success
//				facture_warning_lbl.setText("");
//				int result = JOptionPane.showConfirmDialog(null, "Êtes vous sûr?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//				if(result == JOptionPane.YES_OPTION) {
//					int codeFact = (int) facture_table.getValueAt(index, 0);
//					FactureController.deleteFacture(codeFact);
//					FactureController.fetchAll(facture_table);
//				}
			}
		});
		dltFacture_btn.setBounds(535, 273, 164, 43);
		this.add(dltFacture_btn);
		
		JButton facture_actualiser_btn = new JButton("Actualiser");
		facture_actualiser_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cont.ActualiserTableau();
			}
		});
		facture_actualiser_btn.setBounds(535, 494, 164, 43);
		this.add(facture_actualiser_btn);
		
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
}
