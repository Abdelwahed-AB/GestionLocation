package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.sql.Date;
import connectionManager.ConnectionManager;

public class TempContratController {
	
	
	public static DefaultTableModel prepareTable(JTable table) {
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.addColumn("N° Contrat");
		dtm.addColumn("Prenom Client");
		dtm.addColumn("Nom Client");
		dtm.addColumn("Date Contrat");
		dtm.addColumn("Date Echeance");
		dtm.addColumn("Vehicule");
		dtm.setRowCount(0);
		table.setModel(dtm);
		return dtm;
	}
}
