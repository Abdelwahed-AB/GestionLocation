package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import connectionManager.ConnectionManager;
import log.LogMgr;
import model.Client;

public class ClientDAO {

	public static ArrayList<Client> fetchAllDAO() {
		// la requete a executer
		String query = "SELECT * FROM client ORDER BY `client`.`nomClient` ASC";
		// execution de la requete
		ResultSet result = ConnectionManager.execute(query);
		ArrayList<Client> list = new ArrayList<Client>();
		try {
			while (result.next()) {
				Client client = new Client(Integer.parseInt(result.getString(1)), result.getString(2),
						result.getString(3), result.getString(4), result.getString(5), result.getString(6),
						result.getString(7));
				list.add(client);
			}

		} catch (SQLException e) {
			// genere erreur si le tableau client dans la base de donnée est non validé
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur Client", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur Client.", e);

		}
		return list;
	}

	public static void creatClientDAO(Client client) {
		try {
			// preparer la requete sql et apres l'executer
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(
					"INSERT INTO `client` (`codeClient`, `nomClient`, `prenomClient`, `adresseClient`, `telClient`, `imageClient`, `permisScanneeClient`) VALUES (NULL, ?, ?, ?, ?, ?, ?)");
			prepared.setString(1, client.getNomClient());
			prepared.setString(2, client.getPrenomClient());
			prepared.setString(3, client.getAddresseClient());
			prepared.setString(4, client.getNumTelClient());
			prepared.setString(5, client.getImage());
			prepared.setString(6, client.getPermisScannee());
			// executer la requete
			prepared.execute();
		} catch (SQLException e) {
			// genere erreur si le tableau client dans la base de donnée est non validé
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur Client", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur Creer Client.", e);
		}

	}

	// cette fonction pour trouver une client a partir de son code
	public static Client findClientByCodeDAO(int code) {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection()
					.prepareStatement("SELECT * FROM client WHERE codeClient = ?");
			prepared.setInt(1, code);
			ResultSet result = prepared.executeQuery();
			while (result.next()) {
				Client client = new Client(Integer.parseInt(result.getString(1)), result.getString(2),
						result.getString(3), result.getString(4), result.getString(5), result.getString(6),
						result.getString(7));
				return client;
			}
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur Client", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur Search Client By Code.", e);
		}
		return null;
	}

	// cette fonction pour trouver une client a partir de son nom
	public static ArrayList<Client> findClientByNameDAO(String nom) {
		try {
			ArrayList<Client> list = new ArrayList<Client>();
			PreparedStatement prepared = ConnectionManager.getConnection()
					.prepareStatement("SELECT * FROM client WHERE nomClient LIKE ?");
			prepared.setString(1, nom);
			ResultSet result = prepared.executeQuery();
			while (result.next()) {
				list.add(new Client(Integer.parseInt(result.getString(1)), result.getString(2), result.getString(3),
						result.getString(4), result.getString(5), result.getString(6), result.getString(7)));
			}
			return list;
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur Client", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur Search Client By Name.", e);
		}
		return null;
	}

	public static boolean modifyClientDAO(Client client) {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(
					"UPDATE `client` SET `nomClient` = ?, `prenomClient` = ?, `adresseClient` = ?, `telClient` = ?, `imageClient` = ?, `permisScanneeClient` = ? WHERE `client`.`codeClient` = ?");
			prepared.setString(1, client.getNomClient());
			prepared.setString(2, client.getPrenomClient());
			prepared.setString(3, client.getAddresseClient());
			prepared.setString(4, client.getNumTelClient());
			prepared.setString(5, client.getImage());
			prepared.setString(6, client.getPermisScannee());
			prepared.setInt(7, client.getCodeClient());
			prepared.execute();
			return true;
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur Client", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur Modify Client.", e);
		}
		return false;
	}

	public static void deleteClientDAO(int code) {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection()
					.prepareStatement("DELETE FROM `client` WHERE `client`.`codeClient` = ?");
			prepared.setInt(1, code);
			prepared.execute();
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur Client", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur Delete Client.", e);
		}
	}

	// chercher les vehicules loueé par une client pour les afficheés
	public static ResultSet findVehiculeDAO(String code) {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(
					"SELECT Immatriculation, marqueVehicule, dateDepReservation, dateRetReservation FROM vehicule, reservation, client WHERE reservation.codeVehicule=vehicule.Immatriculation AND reservation.codeClient=client.codeClient AND reservation.codeClient=? AND reservation.isValid=1");
			prepared.setString(1, code);
			ResultSet result = prepared.executeQuery();
			return result;
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur Client", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur Search Vehicule.", e);
		}
		return null;
	}
}
