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
		ArrayList<Parking> list = ParkingDAO.fetchAllDAO();
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

	public static Parking findParkingByCode(int code) {
		return ParkingDAO.findParkingByCodeDAO(code);
	}

	public static void deleteParking(int code) {
		ParkingDAO.deleteParkingDAO(code);

	}

	public static void creatParking(Parking parking) {
		ParkingDAO.creatParkingDAO(parking);
	}

	public static void modifyParking(Parking parking) {
		ParkingDAO.modifyParkingDAO(parking);
	}

	public static DefaultTableModel prepareModel(ArrayList<Parking> list) {
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.addColumn("code");
		dtm.addColumn("nom");
		dtm.addColumn("capacite");

		Iterator itr = list.iterator();

		while (itr.hasNext()) {
			Parking parking = (Parking) itr.next();
			Object[] object = { parking.getCodeParking(), parking.getNomParking(), parking.getCapaciteParking() };
			dtm.addRow(object);
		}
		return dtm;
	}

	public static void findVehicule(JTable table, int code) {
		ResultSet result = ParkingDAO.findVehiculeByCodeDAO(code);
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

	public static void removeVehicule(String codeVehicule, int codeParking) {
		ParkingDAO.removeVehiculeDAO(codeVehicule, codeParking);
	}

	public static int nombrePlaceVide(int code, int capacite) {
		int nombreVehicule = ParkingDAO.numberOfVehiculeDAO(code);
		return capacite - nombreVehicule;
	}

	public static void findVehicule(JTable table) {
		ResultSet result = ParkingDAO.findAllVehiculeDAO();
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.addColumn("Matricule");
		dtm.addColumn("Marque");
		dtm.addColumn("Prix de Location");
		try {
			while (result.next()) {
				Object[] object = { result.getString(1), result.getString(2), result.getString(3) };
				dtm.addRow(object);
			}
			table.setModel(dtm);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void addVehicule(String codeVehicule, int codeParking) {
		ParkingDAO.addVehiculeDAO(codeVehicule, codeParking);
	}

}
