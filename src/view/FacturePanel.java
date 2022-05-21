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
import interfaces.CreerFacture;

public class FacturePanel extends JPanel {
	
	private JTable facture_table;
	private JTextField facture_field;
	private JLabel facture_warning_lbl;

	public FacturePanel() {
		this.setLayout(null);
		
		JScrollPane facture_scroll = new JScrollPane();
		this.add(facture_scroll);
		
		facture_table = new JTable() {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column){  
		          return false;  
		    };
		};
		facture_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		facture_scroll.setViewportView(facture_table);
		
		FactureController.fetchAll(facture_table);
		
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
				if(facture_field.getText().isEmpty()) {
					facture_warning_lbl.setText("<html>Veuillez saisir un code avant de rechercher.</html>");
					return;
				}
				facture_warning_lbl.setText("");
				FactureController.findFacture(facture_field.getText(), facture_table);
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
				int index = facture_table.getSelectedRow();
				if(index < 0) {
					//if user hasnt selected a row
					facture_warning_lbl.setText("<html>Veuillez selectionner une facture à supprimer.</html>");
					return;
				}
				//else reset warning label on success
				facture_warning_lbl.setText("");
				int result = JOptionPane.showConfirmDialog(null, "Êtes vous sûr?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(result == JOptionPane.YES_OPTION) {
					int codeFact = (int) facture_table.getValueAt(index, 0);
					FactureController.deleteFacture(codeFact);
					FactureController.fetchAll(facture_table);
				}
			}
		});
		dltFacture_btn.setBounds(535, 273, 164, 43);
		this.add(dltFacture_btn);
		
		JButton facture_actualiser_btn = new JButton("Actualiser");
		facture_actualiser_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FactureController.fetchAll(facture_table);
				facture_warning_lbl.setText("");
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
	
	
	
}
