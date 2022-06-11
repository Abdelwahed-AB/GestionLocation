package model;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class TempContratTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String[] cols = new String[] {
		"N° Contrat",
		"Date Contrat",
		"Date Echeance",
		"Date Retour",
		"Matricule",
		"Montant Sanction"
	};

	private Vector<String[]> rows = new Vector<>();

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

	@Override
	public boolean isCellEditable(int row, int column){
        return false;
	}

	public void loadContrats(ArrayList<Contrat> cList){
		rows.clear();

		for(Contrat c : cList) {
			String[] row = {
				Integer.toString(c.getCodeContrat()),
				c.getDateContrat().toString(),
				c.getDateEcheance().toString(),
				c.getDateRetActuel().toString(),
				c.getReservation().getVehicule().getMatricule(),
				Integer.toString(c.getMontantSanction())
			};

			rows.add(row);
		}

		fireTableChanged(null);
	}
}
