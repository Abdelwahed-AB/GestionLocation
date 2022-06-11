package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import connectionManager.ConnectionManager;
import log.LogMgr;
import model.Parking;

public class ParkingDAO {

	public static ArrayList<Parking> fetchAllDAO() {
		ArrayList<Parking> list = new ArrayList<Parking>();
		// la requete a executer
		String query = "SELECT * FROM parking";
		// execution de la requete
		ResultSet result = ConnectionManager.execute(query);

		try {
			while (result.next()) {
				list.add(new Parking(Integer.parseInt(result.getString(1)), result.getString(2),
						Integer.parseInt(result.getString(3)), result.getString(4), result.getString(5),
						Integer.parseInt(result.getString(6))));
			}
		} catch (SQLException e) {
			// genere erreur si le tableau parking dans la base de donnée est non validé
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur Parking", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur Parking.", e);
		}
		return list;
	}

	public static ArrayList<Parking> findParkingByNameDAO(String string) {
		ArrayList<Parking> list = new ArrayList<Parking>();
		PreparedStatement prepared;
		ResultSet result;
		try {
			prepared = ConnectionManager.getConnection()
					.prepareStatement("SELECT * FROM parking WHERE nomParking LIKE ?");
			prepared.setString(1, string);
			result = prepared.executeQuery();
			while (result.next()) {
				list.add(new Parking(Integer.parseInt(result.getString(1)), result.getString(2),
						Integer.parseInt(result.getString(3)), result.getString(4), result.getString(5),
						Integer.parseInt(result.getString(6))));
			}
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur Parking", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur Find Parking By Name.", e);
		}

		return list;
	}

	public static Parking findParkingByCodeDAO(int code) {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection()
					.prepareStatement("SELECT * FROM parking WHERE codeParking = ?");
			prepared.setInt(1, code);
			ResultSet result = prepared.executeQuery();
			while (result.next()) {
				Parking parking = new Parking(Integer.parseInt(result.getString(1)), result.getString(2),
						Integer.parseInt(result.getString(3)), result.getString(4), result.getString(5),
						Integer.parseInt(result.getString(6)));
				return parking;
			}
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur Parking", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur Find Parking By Code.", e);
		}
		return null;
	}

	public static void deleteParkingDAO(int code) {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection()
					.prepareStatement("DELETE FROM `parking` WHERE `parking`.`codeParking` = ?");
			prepared.setInt(1, code);
			prepared.execute();
			prepared = ConnectionManager.getConnection()
					.prepareStatement("UPDATE `vehicule` SET `codePark` = '0', `disponible` = '0' WHERE `vehicule`.`codePark` = ?");
			prepared.setInt(1, code);
			prepared.execute();
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur Parking", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur Delete Parking.", e);
		}
	}

	public static void creatParkingDAO(Parking parking) {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(
					"INSERT INTO `parking` (`codeParking`, `nomParking`, `capaciteParking`, `rueParking`, `arrondissement`, `nombrePlaceVide`) VALUES (NULL, ?, ?, ?, ?, ?)");
			prepared.setString(1, parking.getNomParking());
			prepared.setInt(2, parking.getCapaciteParking());
			prepared.setString(3, parking.getRueParking());
			prepared.setString(4, parking.getArrondissementParking());
			prepared.setInt(5, parking.getNombrePlaceVide());
			prepared.execute();
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur Parking", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur Creat Parking.", e);
		}
	}

	public static void modifyParkingDAO(Parking parking) {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(
					"UPDATE `parking` SET `nomParking` = ?, `capaciteParking` = ?, `rueParking` = ?, `arrondissement` = ?, `nombrePlaceVide` = ? WHERE `parking`.`codeParking` = ?");
			prepared.setString(1, parking.getNomParking());
			prepared.setInt(2, parking.getCapaciteParking());
			;
			prepared.setString(3, parking.getRueParking());
			prepared.setString(4, parking.getArrondissementParking());
			prepared.setInt(5, parking.getNombrePlaceVide());
			prepared.setInt(6, parking.getCodeParking());
			prepared.execute();
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur Parking", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur Modify Parking.", e);
		}
	}

	// chercher l'ensemble des vehicules stationnées dans un park pour les afficheés
	public static ResultSet findVehiculeByCodeDAO(int code) {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(
					"SELECT Immatriculation, marqueVehicule, typeVehicule, prixLocation FROM vehicule, parking WHERE vehicule.codePark=parking.codeParking AND parking.codeParking=? AND vehicule.disponible = 1");
			prepared.setInt(1, code);
			ResultSet result = prepared.executeQuery();
			return result;
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur Parking", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur Find Vehicule By Code.", e);
		}
		return null;
	}

	// pour calculer le nombre de place vide dans une parking on a besion du nombre
	// de vehicule situées dans ce park
	public static int numberOfVehiculeDAO(int code) {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(
					"SELECT COUNT(Immatriculation) FROM vehicule, parking WHERE vehicule.codePark = parking.codeParking AND parking.codeParking = ?");
			prepared.setInt(1, code);
			ResultSet result = prepared.executeQuery();
			if (result.next()) {
				return Integer.parseInt(result.getString(1));
			}
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur Parking", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur Number Of Vehicule.", e);
		}
		return 0;
	}

	public static void removeVehiculeDAO(String codeVehicule, int codeParking) {
		try {
			// supprimer la vehicule du park
			PreparedStatement prepared = ConnectionManager.getConnection()
					.prepareStatement("UPDATE `vehicule` SET `codePark` = '0', `disponible` = '0' WHERE `vehicule`.`Immatriculation` = ?");
			prepared.setString(1, codeVehicule);
			prepared.execute();
			// augmenter le nombre de place vide dans le park
			prepared = ConnectionManager.getConnection().prepareStatement(
					"UPDATE `parking` SET `nombrePlaceVide` = `nombrePlaceVide`+1 WHERE `parking`.`codeParking` = ?");
			prepared.setInt(1, codeParking);
			prepared.execute();
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur Parking", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur Remove Vehicule.", e);
		}
	}

	// chercher l'ensemble des vehicules n'affecter à aucune park
	public static ResultSet findAllVehiculeDAO() {
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(
					"SELECT Immatriculation, marqueVehicule, prixLocation FROM vehicule WHERE vehicule.codePark=0 ");
			ResultSet result = prepared.executeQuery();
			return result;
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur Parking", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur Find All Vehicule.", e);
		}
		return null;
	}

	public static void addVehiculeDAO(String codeVehicule, int codeParking) {
		try {
			// ajouter la vehicule au park
			PreparedStatement prepared = ConnectionManager.getConnection()
					.prepareStatement("UPDATE `vehicule` SET `codePark` = ?, `disponible` = '1' WHERE `vehicule`.`Immatriculation` = ?");
			prepared.setInt(1, codeParking);
			prepared.setString(2, codeVehicule);
			prepared.execute();
			// deminuer le nombre de place vide du park
			prepared = ConnectionManager.getConnection().prepareStatement(
					"UPDATE `parking` SET `nombrePlaceVide` = `nombrePlaceVide`-1 WHERE `parking`.`codeParking` = ?");
			prepared.setInt(1, codeParking);
			prepared.execute();
			//ajouter la date de retoure au contrat lors d'ajout de la vehicule au park 
			prepared = ConnectionManager.getConnection()
					.prepareStatement("UPDATE `contrat` SET dateRetActuel = current_date() WHERE dateRetActuel IS NULL AND dateContrat < current_date() AND codeMatricule = ?");
			prepared.setString(1, codeVehicule);
			prepared.execute();
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur Parking", JOptionPane.DEFAULT_OPTION,
					JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur Add Vehicule.", e);
		}
	}

}
