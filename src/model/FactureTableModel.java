package model;

import javax.swing.table.AbstractTableModel;

import java.util.ArrayList;
import java.util.Vector;

public class FactureTableModel extends AbstractTableModel {
	
	private String[] cols = {
		"N� Facture",
		"Prenom Client", 
		"Nom Client",
		"N� Contrat",
		"Montant"
	};
	
	private Vector<String[]> rows = new Vector<String[]>();
	@Override
	public int getRowCount() {
		return rows.size();
	}

	@Override
	public int getColumnCount() {
		return cols.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return rows.get(rowIndex)[columnIndex];
	}
	
	@Override
	public String getColumnName(int index) {
		return cols[index];
	}
	
	public boolean isCellEditable(int row, int column){  
        return false;  
	}
	
	public void loadData(ArrayList<Facture> fact_list) {
		rows.clear();
		for(Facture f: fact_list) {
			String[] row = {
				Integer.toString(f.getCodeFacture()),
				f.getContrat().getReservation().getClient().getPrenom(),
				f.getContrat().getReservation().getClient().getNom(),
				Integer.toString(f.getContrat().getCodeContrat()),
				Integer.toString(f.getMontant())
			};
			rows.add(row);
		}
	}

}
