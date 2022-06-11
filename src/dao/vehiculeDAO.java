package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import connectionManager.ConnectionManager;
import log.LogMgr;
import model.Vehicule;

public class vehiculeDAO {

//RECUPERE LES VEHICULES EXISTANT DE LA BASE DE DONNEES 
	
	public static ArrayList<Vehicule> fetchAll () {
		String query= "SELECT * "
				+ "FROM  vehicule ";
		
		ResultSet result = ConnectionManager.execute(query);
		
		ArrayList<Vehicule> Vehicule_list = new ArrayList<Vehicule>();
		
		try {
			while (result.next()) {
				Vehicule V = new Vehicule(result.getString("Immatriculation"),
						result.getString("marqueVehicule"),
						result.getString("typeVehicule"),
						result.getString("carburant"),
						result.getLong("kilometrage"),
						result.getDate("dateMiseCirculation"),
						result.getInt("codePark"),
						result.getInt("prixLocation"),
						result.getBoolean("disponible"));
				Vehicule_list.add(V);
			}
			
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, "Erreur affichage vehicules", "Erreur vehicule", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur affichage vehicules", e);
		}
		return Vehicule_list;
	}
	
// METHODE RECHERCHANT LE VEHICULE PAR SON MATRICULE
	
	public static Vehicule findVehicule(String matricule) {
		String query="SELECT *"
				+" FROM vehicule"
				+" WHERE  Immatriculation like ?;";
		Vehicule V=null;
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
			prepared.setString(1, matricule);
			ResultSet result = prepared.executeQuery();
			while (result.next()) {
				V = new Vehicule(result.getString("Immatriculation"),
						result.getString("marqueVehicule"),
						result.getString("typeVehicule"),
						result.getString("carburant"),
						result.getInt("kilometrage"),
						result.getDate("dateMiseCirculation"),
						result.getInt("codePark"),
						result.getInt("prixLocation"),
						result.getBoolean("disponible"));
				return V;
			}
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, "Erreur recherche vehicule", "Erreur vehicule", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur recherche vehicule", e);
		}
		return V;	
	}
	
//VERIFIER SI UN VEHICULE EXISTE EN TESTANT SUR SON CODE MATRICULE ,LE RETOUR EST UN BOOLEAN
	
	public static boolean verifyVehicle(String matricule) {
		String query="SELECT *"
				+" FROM vehicule"
				+" WHERE Immatriculation LIKE ?";
			
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
			prepared.setString(1, matricule);
			ResultSet result = prepared.executeQuery();
			if(result.next())
				return true;
			else 
				return false;
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, "Erreur verification vehicules", "Erreur vehicule", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur verification vehicules", e);
		}
		return false;
	}
	
//METHODE QUI RETOURNE LA LISTE VEHCICULES CORRESPONDANT AU CRITERE DE RECHERCHE 
	
	public static ArrayList<Vehicule>  findVehiculeAutoCompleting (String Immatriculation) {
		String query="SELECT *"
					+" FROM vehicule"
					+" WHERE  Immatriculation like ?;";
		ArrayList<Vehicule> Vehicule_list = new ArrayList<Vehicule>();
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
			prepared.setString(1, Immatriculation+"%");
			ResultSet result = prepared.executeQuery();
			while (result.next()) {
				Vehicule V = new Vehicule(result.getString("Immatriculation"),
						result.getString("marqueVehicule"),
						result.getString("typeVehicule"),
						result.getString("carburant"),
						result.getInt("kilometrage"),
						result.getDate("dateMiseCirculation"),
						result.getInt("codePark"),
						result.getInt("prixLocation"),
						result.getBoolean("disponible"));
				Vehicule_list.add(V);
			}
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, "Erreur recherche vehicule", "Erreur vehicule", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur autocomplete vehicule", e);
		}
		return Vehicule_list;
	}
	
// MEHTODE QUI AJOUTE UN VEHICULE A  LA BASE DE DONNEES 
	
	public static boolean createVehicule(Vehicule vehicule) {
		String query="INSERT INTO `vehicule` (`Immatriculation`, `marqueVehicule`, `typeVehicule`, `carburant`,"+
					" `kilometrage`, `dateMiseCirculation`, `codePark`, `prixLocation`, `disponible`) "+
					"VALUES (?, ?, ?, ?, ?, ?, 0, ?, ?);";
		PreparedStatement prepared;
		try {
			prepared = ConnectionManager.getConnection().prepareStatement(query);
			prepared.setString(1, vehicule.getMatricule());
			prepared.setString(2, vehicule.getMarque());
			prepared.setString(3, vehicule.getType());
			prepared.setString(4, vehicule.getCarburant());
			prepared.setDouble(5, vehicule.getKilometrage());
			prepared.setDate(6, vehicule.getDMC());
			prepared.setDouble(7, vehicule.getPrixLocation());
			prepared.setBoolean(8, vehicule.getDisponible());
			prepared.execute();
			return true;
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, "Erreur creation vehicules", "Erreur vehicule", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur creation vehicules", e);
		}
		return false;
	}
	
// METHODE SUPPRIMANT UN VEHICULE A L'AIDE DE SON MATRICULE
	
	public static boolean removeVehicule(String id) {
		String query="DELETE FROM `vehicule`"
					+" WHERE (`Immatriculation` = ?);";
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
			prepared.setString(1, id);
			prepared.execute();
			return true;
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, "Erreur supression vehicule", "Erreur vehicule", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur supression vehicule", e);
		}
		return false;
	}
	
//METHODE QUI ENVOI UNE REQUTE UPDATE AFIN DE CHANGER LES ATTRIBUTS D'UN VEHICULE
	
	public static boolean ChangeVehicle(Vehicule vehicule,String oldId) {
		String query="UPDATE `vehicule`"
				+" SET `Immatriculation`=?, `marqueVehicule`=?, `typeVehicule`=?, `carburant`=?,"
				+ " `kilometrage`=?, `dateMiseCirculation`=?, `codePark`=?, `prixLocation`=?, `disponible`=?"
				+" WHERE (`Immatriculation` = ?);";
		PreparedStatement prepared;
		try {
			prepared = ConnectionManager.getConnection().prepareStatement(query);
			prepared.setString(1, vehicule.getMatricule());
			prepared.setString(2, vehicule.getMarque());
			prepared.setString(3, vehicule.getType());
			prepared.setString(4, vehicule.getCarburant());
			prepared.setDouble(5, vehicule.getKilometrage());
			prepared.setDate(6, vehicule.getDMC());
			prepared.setInt(7, vehicule.getCodePark());
			prepared.setDouble(8, vehicule.getPrixLocation());
			prepared.setBoolean(9, vehicule.getDisponible());
			prepared.setString(10,oldId);
			prepared.execute();
			return true;
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, "Erreur modification vehicule", "Erreur vehicule", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur modification vehicule", e);
		}
		
		return false;
	}
//FONCTION QUI RETOURNE LES NOMS DES PARKINGS
	
	public static ArrayList<String> nomPark() {
		String query="SELECT codeParking, nomParking FROM parking "
				+ "WHERE nombrePlaceVide>0;";
		ArrayList<String> Parkings=new ArrayList<String>();
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
			ResultSet result = prepared.executeQuery();
			while(result.next()) {
				Parkings.add(result.getString("codeParking")+"-"+result.getString("nomParking"));
			}
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, "Erreur recherche des parks.", "Erreur vehicule", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur recherche des parks", e);
		}
		return Parkings;
	}
	
	
	
}
