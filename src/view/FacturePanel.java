package view;

import java.awt.CardLayout;
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
import interfaces.MainInterface;
import model.FactureTableModel;
import javax.swing.table.TableModel;

public class FacturePanel extends JPanel {
	
	private JTextField facture_field;
	private JLabel facture_warning_lbl;
	
	private FactureTableModel factureTableModel = new FactureTableModel();
	private tempFactureController cont;
	private JTable facture_table;
	
	private CardLayout cl;

	public FacturePanel(MainInterface mInterface) {
		this.cl = (CardLayout) mInterface.getMainPanel().getLayout();
		this.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 70, 493, 467);
		add(scrollPane);
		
		facture_table = new JTable(factureTableModel);
		scrollPane.setViewportView(facture_table);
		facture_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
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
				cl.show(mInterface.getMainPanel(), "newFacture");
			}
		});
		newFacture_btn.setBounds(535, 220, 164, 43);
		this.add(newFacture_btn);
		
		JButton dltFacture_btn = new JButton("Supprimer");
		dltFacture_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cont.SupprimerFacture();
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
	
	//setter
	public void setFactureController(tempFactureController cont) {
		this.cont = cont;
	}
}
