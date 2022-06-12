package controller;

import java.awt.Image;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dao.ClientDAO;
import model.Client;

public class ClientController {

	public static void fetchAll(JTable table) {
		// rederiger le travaille de recherche au couche DAO
		ArrayList<Client> list = ClientDAO.fetchAllDAO();
		// preparer le model
		DefaultTableModel dtm = preparerModel(list);
		table.setModel(dtm);
	}

	public static void creatClient(Client client) {
		// rederiger le travaille d'interaction avec base de donn�e au couche DAO
		ClientDAO.creatClientDAO(client);
	}

	public static void findClientByName(String nom, JTable table) {
		ArrayList<Client> list = ClientDAO.findClientByNameDAO(nom);
		DefaultTableModel dtm = preparerModel(list);
		table.setModel(dtm);
	}

	public static Client findClientByCode(int code) {
		Client client = ClientDAO.findClientByCodeDAO(code);
		return client;
	}

	public static void deleteClient(String id) {
		ClientDAO.deleteClientDAO(Integer.parseInt(id));
	}

	public static void modifyClient(Client client) {
		ClientDAO.modifyClientDAO(client);
	}

	public static DefaultTableModel preparerModel(ArrayList<Client> list) {
		// preparer le model du tableau
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.addColumn("Id");
		dtm.addColumn("nom");
		dtm.addColumn("prenom");
		dtm.addColumn("num Tel");

		Iterator<Client> itr = list.iterator();

		// remplir le model par les informations extraites de base de donn�es
		while (itr.hasNext()) {
			Client client = (Client) itr.next();
			Object[] object = { client.getCodeClient(), client.getNomClient(), client.getPrenomClient(),
					client.getNumTelClient() };
			dtm.addRow(object);
		}

		return dtm;
	}

	public static void findVehicule(JTable table, String code) {
		ResultSet result = ClientDAO.findVehiculeDAO(code);
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.addColumn("Matricule");
		dtm.addColumn("Marque");
		dtm.addColumn("Date de depart");
		dtm.addColumn("Date de retour");
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

	public static void prepareImage(String path, JLabel label) {
		ImageIcon myImage = new ImageIcon(path);

		Image img = myImage.getImage();
		Image newImage = img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);

		ImageIcon image = new ImageIcon(newImage);
		label.setIcon(image);
	}

}
