package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import connectionManager.ConnectionManager;
import dao.ClientDAO;
import dao.ParkingDAO;
import model.Parking;

public class ParkingController {

	public static void fetchAll(JTable parkingtable) {
		// rederiger le travaille de recherche au couche DAO
		ArrayList<Parking> list = ParkingDAO.actualiserParking();
		// preparer le model
		DefaultTableModel dtm = prepareModel(list);

		parkingtable.setModel(dtm);
		
	}
	
	public static void findParkingByName(String string, JTable table) {
		// rederiger le travaille de recherche au couche DAO
			ArrayList<Parking> list = ParkingDAO.findParkingByNameDAO(string);
			// preparer le model
			DefaultTableModel dtm = prepareModel(list);

			table.setModel(dtm);
		
	}
	
	public static Parking findParkingByCode (int code) {
		return ParkingDAO.findParkingByCodeDAO(code);
	}

	public static void deleteParking(int code) {
		ParkingDAO.deleteParking(code);
		
	}
	
	public static boolean creatParking (Parking parking) {
		return ParkingDAO.creatParkingDAO(parking);
	}

	public static boolean modifyParking(Parking parking) {
		return ParkingDAO.modifyParkingDAO(parking);
	}
	
	public static DefaultTableModel prepareModel (ArrayList<Parking> list) {
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.addColumn("code");
		dtm.addColumn("nom");
		dtm.addColumn("capacite");
		
		Iterator itr = list.iterator();
		
		while (itr.hasNext()) {
			Parking parking = (Parking) itr.next();
			Object[] object = {parking.getCodeParking(), parking.getNomParking(), parking.getCapaciteParking()};
			dtm.addRow(object);
		}
		return dtm;
	}
	
	public static void findVehicule (JTable table, int code) {
		ResultSet result = ParkingDAO.chercherVehicule(code);
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.addColumn("Matricule");
		dtm.addColumn("Marque");
		dtm.addColumn("type");
		dtm.addColumn("prix location");
		try {
			while (result.next()) {
				Object[] object = { result.getString(1), result.getString(2), result.getString(3),
						result.getString(4) };
				dtm.addRow(object);
			}
			table.setModel(dtm);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static int nombrePlaceVide (int code, int capacite) {
		int nombreVehicule = ParkingDAO.nombreVehicule(code);
		return capacite - nombreVehicule;
	}

}
