package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import connectionManager.ConnectionManager;
import model.*;

public class FactureDAO {
	
	/**
	 * recherche tous les factures stockees dans la base de donees
	 * @return list des factures
	 */
	public static ArrayList<Facture> fetchAll () {
		String query= "SELECT * "
				+ "FROM facture, contrat, client, reservation "
				+ "WHERE facture.codeContrat = contrat.codeContrat "
				+ "AND contrat.codeReservation = reservation.codeReservation "
				+ "AND reservation.codeClient = client.codeClient "
				+ "ORDER BY dateFacture DESC;";
		
		ResultSet result = ConnectionManager.execute(query);
		
		ArrayList<Facture> fact_list = new ArrayList<Facture>();
		
		try {
			while (result.next()) {
				Facture f = new Facture();
				f.setCodeFacture(result.getInt("codeFacture"));
				f.setDateFacture(result.getDate("dateFacture"));
				f.setMontant(result.getInt("montantFacture"));
				
				Client client = new Client();
				client.setNom(result.getString("nomClient"));
				client.setPrenom(result.getString("prenomClient"));
				client.setCodeClient(result.getInt("codeClient"));
				
				Reservation reserv = new Reservation();
				reserv.setClient(client);
				reserv.setCodeVehicule(result.getString("codeVehicule"));
				
				Contrat contrat = new Contrat();
				contrat.setCodeContrat(result.getInt("codeContrat"));
				contrat.setReservation(reserv);
				contrat.setDateEcheance(result.getDate("dateEcheance"));
				contrat.setDateContrat(result.getDate("dateContrat"));
				
				f.setContrat(contrat);
				
				fact_list.add(f);
			}
			
		} catch (SQLException e) {
			JOptionPane.showConfirmDialog(null, e.getMessage(), "Erreur", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		}
		
		return fact_list;
	}
}
