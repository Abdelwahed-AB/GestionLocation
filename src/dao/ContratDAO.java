package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import connectionManager.ConnectionManager;
import log.LogMgr;
import model.Client;
import model.Contrat;
import model.Reservation;
import model.Vehicule;

public class ContratDAO {

//METHODE RETOURNANT UNE LISTE TOUS LES ENREGISTREMENT
	public static ArrayList<Contrat> fetchAll () {
		String query= "SELECT * "
				+ "FROM  contrat ";

		ResultSet result = ConnectionManager.execute(query);
		ArrayList<Contrat> contrat_list = new ArrayList<>();
		try {
			while (result.next()) {
			Contrat c=new Contrat(result.getInt("codeContrat"),
						result.getDate("dateContrat"),
						result.getDate("dateEcheance"),
						result.getDate("dateRetActuel"),
						result.getInt("codeReservation"));
				contrat_list.add(c);
			}

		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, "Contrat display error", "Erreur Contrat", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Contrat display error", e);
		}
		return contrat_list;
	}

// METHODE RECHERCHANT LE CONTRAT PAR SON MATRICULE
	public static Contrat findContract(int codeContrat) {
		String query="SELECT *"
				+" FROM contrat"
				+" WHERE  codeContrat LIKE ?;";
		Contrat c=null;
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
			prepared.setInt(1, codeContrat);
			ResultSet result = prepared.executeQuery();
				while (result.next()) {
					 c=new Contrat(result.getInt("codeContrat"),
								result.getDate("dateContrat"),
								result.getDate("dateEcheance"),
								result.getDate("dateRetActuel"),
								result.getInt("codeReservation"));
					c.setCodeReservation(result.getInt("codeReservation"));
				}
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, "Ereur de recherche Contrat", "Erreur Contrat", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Ereur de recherche Contrat", e);
		}
		return c;
	}
//VERIFIER SI UN CONTRAT EXISTE EN TESTANT SUR SON codeContrat LE RETOUR EST UN BOOLEAN
	public static boolean verifyContract(int codeContrat) {
		String query="SELECT *"
				+" FROM contrat"
				+" WHERE codeContrat LIKE ?";

		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
			prepared.setInt(1,codeContrat);
			ResultSet result = prepared.executeQuery();
			if(result.next())
				return true;
			else
				return false;
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, "Ereur de verification Contrat", "Erreur Contrat", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Ereur de verification Contrat", e);
		}
		return false;
	}
//METHODE QUI RETOURNE LES CONTRATS  CORRESPONDANT AU CRITERE DE RECHERCHE
	public static ArrayList<Contrat>  findContratAutoCompleting (int codeContrat) {
		String query="SELECT *"
					+" FROM contrat"
					+" WHERE  codeContrat like ?;";
		ArrayList<Contrat> contrat_list = new ArrayList<>();
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
			prepared.setString(1, codeContrat+"%");
			ResultSet result = prepared.executeQuery();
			while (result.next()) {
				Contrat  c=new Contrat(result.getInt("codeContrat"),
						result.getDate("dateContrat"),
						result.getDate("dateEcheance"),
						result.getDate("dateRetActuel"),
						result.getInt("codeReservation"));
				contrat_list.add(c);
			}
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, "Ereur de AutoComplete Contrat", "Erreur Contrat", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Ereur de AutoComplete Contrat", e);
		}
		return contrat_list;
	}
// MEHTODE QUI AJOUTE UN contrat A  LA BASE DE DONNEES
	public static boolean createContrat(Contrat contrat) {
		String query="INSERT INTO `contrat` ( `dateContrat`, `dateEcheance`,`codeReservation`) VALUES ( ?, ?,?);";
		PreparedStatement prepared;
		try {
			prepared = ConnectionManager.getConnection().prepareStatement(query);
			prepared.setDate(1, contrat.getDateContrat());
			prepared.setDate(2, contrat.getDateEcheance());
			prepared.setInt(3, contrat.getReservation().getCodeReservation());
			prepared.execute();
			return true;
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, "Erreur Creation contrat", "Erreur Contrat", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur Creation contrat", e);
		}
		return false;
	}
//METHODE QUI ENVOI UNE REQUETE UPDATE AFIN DE CHANGER LES ATTRIBUTS D'UN CONTRAT
	public static boolean ChangeContrat(Date date,int oldCodeContrat) {
		String query="UPDATE `contrat`"
				+" SET `dateEcheance`=? "
				+" WHERE (`codeContrat` = ?);";
		PreparedStatement prepared;
		try {
			prepared = ConnectionManager.getConnection().prepareStatement(query);
			prepared.setDate(1,date);
			prepared.setInt(2,oldCodeContrat);
			prepared.execute();
			return true;
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null,"Erreur Modification contrat", "Erreur Contrat", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur Modification contrat", e);
		}

		return false;
	}

// METHODE SUPPRIMANT UN CONTRAT A L'AIDE DE SON MATRICULE
	public static boolean removeContrat(String id) {
		String query="DELETE FROM `contrat`"
					+" WHERE (`codeContrat` = ?);";
		try {
			PreparedStatement prepared = ConnectionManager.getConnection().prepareStatement(query);
			prepared.setString(1, id);
			prepared.execute();
			return true;
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, "Erreur Supression contrat", "Erreur Contrat", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur Supression contrat", e);
		}
		return false;
	}

	/**
	 * Methode qui cherche tous les contrats qui n'ont pas une facture
	 * @return ArrayList<Contrat>
	 */
	public static ArrayList<Contrat> getContratsNoFacture() {
		String query= "SELECT * "
					+ "FROM contrat C, client, reservation "
					+ "WHERE C.codeReservation = reservation.codeReservation "
					+ "AND reservation.codeClient = client.codeClient "
					+ "AND NOT EXISTS(SELECT * FROM facture WHERE C.codeContrat = facture.codeContrat);";
		// We dont need to order by date because they are ordered by id which auto increment when we add a new date in

		ResultSet result = ConnectionManager.execute(query);

		ArrayList<Contrat> cList = new ArrayList<>();
		try {
			while (result.next()) {
				Contrat c = new Contrat();
				c.setCodeContrat(result.getInt("codeContrat"));
				c.setDateContrat(result.getDate("dateContrat"));
				c.setDateEcheance(result.getDate("dateEcheance"));
				c.setDateRetActuel(result.getDate("dateRetActuel"));

				Reservation r = new Reservation();
				Client cli = new Client();
				Vehicule v = new Vehicule();

				v.setMatricule(result.getString("codeVehicule"));

				cli.setNomClient(result.getString("nomClient"));
				cli.setPrenomClient(result.getString("prenomClient"));

				r.setClient(cli);
				r.setVehicule(v);
				r.setCodeReservation(result.getInt("codeReservation"));

				c.setCodeReservation(r.getCodeReservation());
				c.setReservation(r);

				cList.add(c);
			}

		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, "Erreur Recherche Contrat sans Facture", "Erreur Facture", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			LogMgr.error("Erreur Recherche Contrat sans Facture", e);
		}

		return cList;
	}

	/**
	 * Methode qui cherche un contrats qui n'a pas une facture
	 * @return ArrayList<Contrat>
	 */
	public static ArrayList<Contrat> searchContratNoFacture(int codeContrat){
		String query= "SELECT * "
				+ "FROM contrat C, client, reservation "
				+ "WHERE C.codeReservation = reservation.codeReservation "
				+ "AND reservation.codeClient = client.codeClient "
				+ "AND C.codeContrat = ? "
				+ "AND NOT EXISTS(SELECT * FROM facture WHERE C.codeContrat = facture.codeContrat);";
	// We dont need to order by date because they are ordered by id which auto increment when we add a new date in


	ArrayList<Contrat> cList = new ArrayList<>();
	try {
		PreparedStatement ps = ConnectionManager.getConnection().prepareStatement(query);
		ps.setInt(1, codeContrat);
		ResultSet result = ps.executeQuery();
		while (result.next()) {
			Contrat c = new Contrat();
			c.setCodeContrat(result.getInt("codeContrat"));
			c.setDateContrat(result.getDate("dateContrat"));
			c.setDateEcheance(result.getDate("dateEcheance"));
			c.setDateRetActuel(result.getDate("dateRetActuel"));

			Reservation r = new Reservation();
			Client cli = new Client();
			Vehicule v = new Vehicule();

			v.setMatricule(result.getString("codeVehicule"));

			cli.setNomClient(result.getString("nomClient"));
			cli.setPrenomClient(result.getString("prenomClient"));

			r.setClient(cli);
			r.setVehicule(v);
			r.setCodeReservation(result.getInt("codeReservation"));
			c.setCodeReservation(r.getCodeReservation());

			cList.add(c);
		}

	} catch (SQLException e) {
		e.printStackTrace();
	}

	return cList;
	}
}
