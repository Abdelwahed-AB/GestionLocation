package controller;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import dao.ContratDAO;
import dao.ParkingDAO;
import dao.ReservationDAO;
import interfaces.MainInterface;
import model.Contrat;
import model.Reservation;
import model.Reservation.filtre;
import model.Vehicule;
import view.AddNewContract;
import view.ChangeExistingContrat;
import view.ContratPanel;

public class ContratController {
	private static JTable contratTable;
	private static JTable reservationTable;
	private static MainInterface window;
	private static ContratPanel contratPanel;
	private static AddNewContract addNewContract;
	private static ChangeExistingContrat CEC;

	// METHODE AUTOCOMPLETING version contrats
	public static void autoCompleting() {//à chaque clique sur le clavier on appelle cette fonction
		if(!ContratController.contratPanel.getChercherContrat().getText().equals("")) {//tester si la zone de recherche est vide
			try {
				//on appel la fonction de la classe ContratDAO qui retourne une liste de contrats correspondant au critère de recherche
				ArrayList<Contrat> contrat=ContratDAO.findContratAutoCompleting(Integer.parseInt(ContratController.contratPanel.getChercherContrat().getText()));
				contratPanel.getTableModel().loadContracts(contrat);//attache la liste retournée à la table
			}catch(NumberFormatException ex) {
				JOptionPane.showMessageDialog(null,"veuillez entrer un nombre", "Input error", JOptionPane.ERROR_MESSAGE);
				contratPanel.getChercherContrat().setText("");
				ContratController.fetchAll();
			}
		}else {
			ContratController.fetchAll();
		}
	}
// METHODE AUTOCOMPLETING version reservations
	public static void autoCompleting1() {
		if(!ContratController.addNewContract.getChercherReservation().getText().equals("")) {//similaire à la methode précendente sauf qu'elle retourne la liste des reservations
			try {
				ArrayList<Reservation> reserv = ReservationDAO.findReservationAutoCompleting(Integer.parseInt(ContratController.addNewContract.getChercherReservation().getText()));
				AddNewContract.getRTableModel().loadReservations(reserv);
			}catch(NumberFormatException ex) {
				JOptionPane.showMessageDialog(null,"veuillez entrer un nombre", "Input error", JOptionPane.ERROR_MESSAGE);
				ContratController.addNewContract.getChercherReservation().setText("");
				ContratController.displayReservation();
			}
		}else {
			ContratController.displayReservation();
		}
	}
//METHODE QUI AFFICHE LA LISTE DES RESERVATION
	public static void displayReservation() {//actualise la table d'affichage des reservations
		 ArrayList<Reservation> reserv = ReservationDAO.fetchAll2(filtre.Non_valide) ;
		 AddNewContract.getRTableModel().loadReservations(reserv);
		 
	}
		
//METHODE QUI AFFICHE LA LISTE DES CONTRATS 
	//AFFICHER TOUT LES ENREGISTREMENT
	public static void fetchAll() {//actualise la table d'affichage des contrats
		ArrayList<Contrat> cList = ContratDAO.fetchAll();
		ContratPanel.getTableModel().loadContracts(cList);
	}
//METHODE QUI LANCE LE PANEL DE MODIFICATIONS D'UN CONTRAT ET INITIALISE LES CHAMPS PAR LES ANCIENNES DONNEES DU CONTRAT
	public static void changeContrat() {
		int index=ContratController.contratTable.getSelectedRow();
		if(index>=0) {
			Contrat c=ContratDAO.findContract(Integer.parseInt(contratTable.getModel().getValueAt(index,0).toString()));//on cherche le contrat par son code
			ChangeExistingContrat CEC =new ChangeExistingContrat(window);
			Date date =c.getDateEcheance();
			LocalDate LD=date.toLocalDate();//ON EVITE EXTRAIRE L'ANNEE, LE MOIS ET LA DATE DIRECTEMENT AVEC LES MTHDS GET...(depricated)
			CEC.getYcomboBox().setSelectedItem( Year.parse(LD.getYear()+""));
			CEC.getMcomboBox().setSelectedItem( LD.getMonthValue()+"");
			CEC.getDcomboBox().setSelectedItem( LD.getDayOfMonth()+"");
			window.addToMainPanel(CEC, "changeContrat");
			window.showOnMainPanel("changeContrat");
			ChangeExistingContrat.setOldId(Integer.parseInt(contratTable.getModel().getValueAt(index,0).toString()));
		}
		else
			JOptionPane.showMessageDialog(null,"Aucun contrat n'est selectionné", "Echec de modification", JOptionPane.ERROR_MESSAGE);
	}
// METHODE QUI ENREGISTRE LE CHANGEMENT
	
	public static void saveChanges(int oldId) {
		
		if(CEC.getYcomboBox().getSelectedIndex()>=0&&CEC.getMcomboBox().getSelectedIndex()>=0&&CEC.getDcomboBox().getSelectedIndex()>=0) 
		{//TESTER SI L'UTILISATEUR A SELECTIONNE TOUS LES CHAMPS
			
			Contrat c=ContratDAO.findContract(oldId);//RECUPERER LE CONTRAT QUI CORRESPOND A L'IDENTIFIANT PASSE EN ARGUMENT
			
			ArrayList<Reservation> RL=ReservationDAO.findReservation(c.getCodeReservation());//RECUPERER LA RESERVATION QUI CORRSEPOND A L'ATTRIBUT CODE RESERVATION DE CE CONTRAT 
			
			Vehicule v=RL.get(0).getVehicule();
			
			Date dateModifiee=Date.valueOf(CEC.getYcomboBox().getSelectedItem()+"-"+
					CEC.getMcomboBox().getSelectedItem()+"-"+
					CEC.getDcomboBox().getSelectedItem());//RECUPERER LA DATE CHOISI PAR L'UTILISATEUR
			
			if(ReservationDAO.isReservationDateAvailable(v.getMatricule(), c.getDateContrat(),dateModifiee,c.getCodeReservation())) {//TESTER SI LE VEHICULE EST DEJA RESERVEE AVANT CETTE DATE
				if(!LocalDate.now().isAfter(LocalDate.parse(dateModifiee+""))) {//TESTER SI L'UTILISATEUR TENTE D'ENTRER UNE DATE ILLOGIQUE
					if(ContratDAO.ChangeContrat(dateModifiee,oldId)) {//EFFECTUER LE CHANGEMENT
						ContratController.fetchAll();
						JOptionPane.showMessageDialog(null,"Contrat modifié ", "Modification avec succés", JOptionPane.INFORMATION_MESSAGE);
						window.showOnMainPanel("contrat");//REVENIR AU MENU	CONTRAT
					}else
						JOptionPane.showMessageDialog(null,"Modification echoué", "Probléme de modification", JOptionPane.ERROR_MESSAGE);
				}else
					JOptionPane.showMessageDialog(null,"La date actuelle  est plus récente que la date que vous venez d'entrer ! ", "Probléme de modification", JOptionPane.ERROR_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(null,"Le vehicule est déja réservé", "Echec de prolongement", JOptionPane.ERROR_MESSAGE);
			}
		}else {
			JOptionPane.showMessageDialog(null,"Veuillez renseigner tous les champs", "Manque de données", JOptionPane.ERROR_MESSAGE);
		}
	}
// METHODE QUI ANNULE LE CHANGEMENT
	public static void cancel(MainInterface mainInterface) {//METHODE QUI ANNULE LE CHANGEMENT/AJOUT D'UN CONTRAT
			mainInterface.showOnMainPanel("contrat");
			ContratController.fetchAll();	
	}
//METHODE QUI AFFICHE LES RESERVATION DONT L'UTILISATEUR DOIT CHOISR UNE ET LUI AFFECTER UN CONTRAT 	
	public static void addContrat() {
		AddNewContract ANC=new AddNewContract(window);
		window.addToMainPanel(ANC,"newContrat");
		window.showOnMainPanel("newContrat");
		ContratController.displayReservation();
	}
//METHODE QUI CREE UN CONTRAT SELON LA RESERVATION CHOISIE
	public static void createContract() {
		int index=reservationTable.getSelectedRow();
		if(index>=0) {
			
			int i=Integer.parseInt(reservationTable.getValueAt(index,0).toString());//recuperer le code reservation
			
			ArrayList<Reservation> RV=ReservationDAO.findReservation(i);
			Reservation reserv=RV.get(0);// recuperer la reservation correspondant à ce code
			
			Contrat contrat= new Contrat(Date.valueOf(java.time.LocalDate.now().toString()),reserv.getDateRetour(),reserv);
			ContratDAO.createContrat(contrat);//creer un nouveau contrat et l'ajouter
			
			ReservationDAO.setReservationValid(i,true);//VALIDER LA RESERVATION
			ContratController.fetchAll();
			ContratController.displayReservation();
			JOptionPane.showMessageDialog(null,"Contrat creé avec succés", "Creation avec succés", JOptionPane.INFORMATION_MESSAGE);
			window.showOnMainPanel("contrat");
			ParkingDAO.removeVehiculeDAO(reserv.getVehicule().getMatricule(), reserv.getVehicule().getCodePark());//FAIRE SORTIR LE VEHICULE DE SON PARKING
		}else {
			JOptionPane.showMessageDialog(null,"Aucune reservation n'est selectionnée", "Ajout echoué", JOptionPane.ERROR_MESSAGE);
		}	
	}
//SUPPRIMER UN ENREGISTREMENT
	public static void removeContrat() {
		int i = contratTable.getSelectedRow();
		try {
			if (i!=-1) {
				int result = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiement supprimer le contrat numéro : "+contratTable.getModel().getValueAt(i, 0).toString()+" ?", "Confirmer la suppression", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(result == JOptionPane.YES_OPTION) {
					String id =contratTable.getModel().getValueAt(i, 0).toString();//recuperer le code contrat
					Contrat c=ContratDAO.findContract(Integer.parseInt(id));
					ContratDAO.removeContrat(id);//chercher le contrat par son code et le supprimer
					ReservationDAO.setReservationValid(c.getCodeReservation(),false);//RENDRE LA RESERVATION INVALIDE
					ContratController.fetchAll();
					JOptionPane.showMessageDialog(null,"Contrat supprimé avec succés", "Suppression", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			else
				JOptionPane.showMessageDialog(null,"Aucun contrat n'est selectionné", "Echec de suppression", JOptionPane.ERROR_MESSAGE);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage(), "Echec de suppression", JOptionPane.ERROR_MESSAGE);
		}
	}
		
	//METHODE QUI INITIALISE LES COMBOBOX PAR LA DATE ACTUELLE
	public static void emptyContratFields(ChangeExistingContrat CEC) {
		LocalDate LD=java.time.LocalDate.now();
		CEC.getYcomboBox().setSelectedItem(Year.parse(LD.getYear()+""));
		CEC.getMcomboBox().setSelectedItem(LD.getMonthValue()+"");
		CEC.getDcomboBox().setSelectedItem(LD.getDayOfMonth()+"");
	}
	
// setters
	public static void setWindow(MainInterface window) {
		ContratController.window=window;
	}
	public static void setTable(JTable contratTable){
		ContratController.contratTable=contratTable;
	}
	public static void setPanel(ContratPanel ContratPanel) {
		ContratController.contratPanel=ContratPanel;
	}
	public static void setReservationTable(JTable RT) {
		ContratController.reservationTable=RT;
	}
	public static void setPanel(AddNewContract addNewContract) {
		ContratController.addNewContract=addNewContract;	
	}
	public static void setCEC(ChangeExistingContrat cEC) {
		CEC = cEC;
	}
		
}
