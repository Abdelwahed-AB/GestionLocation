package model;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class ReservationTableModel extends AbstractTableModel{
	
	private static final long serialVersionUID = 1L;

	private String[] nomColonnes = new String[]{
			"Code Reservation",
			"Nom Client",
			"Prenom Client",
			"Vehicule",
			"Date Depart",
			"Date Retour"
	};
	
	private Vector<String[]> rows = new Vector<String[]>();
	
	@Override
	public int getRowCount() {
		return rows.size();
	}

	@Override
	public int getColumnCount() {
		return nomColonnes.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return rows.get(rowIndex)[columnIndex];
	}
	
	@Override
	public String getColumnName(int index) {
		return nomColonnes[index];
	}
	
	@Override
	public boolean isCellEditable(int row, int column){  
        return false;  
	}
	
	public void loadReservations(ArrayList<Reservation> list) {
		rows.clear();
		for(Reservation r: list) {
			Client c = r.getClient();
			String[] row = {
					Integer.toString(r.getCodeReservation()),
					c.getNomClient(),
					c.getPrenomClient(),
					r.getVehicule().getMatricule(),
					r.getDateDepart().toString(),
					r.getDateRetour().toString()
			};
			
			rows.add(row);
		}
		
		fireTableChanged(null);
	}

}
