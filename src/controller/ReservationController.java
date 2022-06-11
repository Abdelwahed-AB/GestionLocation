package controller;

import java.awt.CardLayout;
import java.sql.Date;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import dao.ReservationDAO;
import dao.vehiculeDAO;
import interfaces.MainInterface;
import model.Client;
import model.Reservation;
import model.Reservation.filtre;
import model.Vehicule;
import view.CreerReservPanel;
import view.ModifierReserPanel;
import view.ReservationPanel;


/**
 * Controlleur qui gére l'interaction entre la BD et l'interface
 * @author Abd-AB
 * @version 1.0
 */

public class ReservationController {
	private ReservationPanel reserv_panel;
	private CreerReservPanel creer_reserv;
	private ModifierReserPanel mod_reserv;
	private MainInterface mInterface;
	private CardLayout cl;

	/**
	 * Constructeur par defaut
	 */
	public ReservationController() {
	}

	/**
	 * Constructeur pour associer l'interface de creation des reservations aux controlleur
	 * @param reserv_panel
	 * @param creer_reserv
	 */
	public ReservationController(ReservationPanel reserv_panel, CreerReservPanel creer_reserv, MainInterface mInterface) {
		this.creer_reserv = creer_reserv;
		this.reserv_panel = reserv_panel;
		this.mInterface = mInterface;
		this.cl = (CardLayout) mInterface.getMainPanel().getLayout();
		reserv_panel.setReservController(this);
		creer_reserv.setReservController(this);

		ActualiserTableau();
	}

	/**
	 * Methode pour actualiser le tableau des reservations
	 */
	public void ActualiserTableau() {
		filtre fil = (filtre) reserv_panel.getReserv_filtre().getSelectedItem();
		ArrayList<Reservation> list_reserv = ReservationDAO.fetchAll(fil);
		reserv_panel.getReserv_model().loadReservations(list_reserv);
	}

	/**
	 * Methode qui recupere les donnés a partir de l'interface CreerReservation et les verifie, puis il les enregistre dans la BD
	 */
	public void CreerReservation() {
		int indexClient = creer_reserv.getCient_table().getSelectedRow();
		int indexVehicule = creer_reserv.getReserv_vehi_table().getSelectedRow();

		if(indexClient < 0) {
			creer_reserv.getWarning_lbl().setText("<html>Veuillez Selectionner un client.</html>");
			return;
		}
		if(indexVehicule < 0) {
			creer_reserv.getWarning_lbl().setText("<html>Veuillez Selectionner une Vehicule.</html>");
			return;
		}

		int codeClient = (int) creer_reserv.getCient_table().getValueAt(indexClient, 0);
		String codeVehicule = (String) creer_reserv.getReserv_vehi_table().getValueAt(indexVehicule, 0);
		Date dateDep, dateRet;

		//Si la valeur de date est erroné
		try {
			dateDep = creer_reserv.getDateDepart();
		} catch (IllegalArgumentException e1) {
			creer_reserv.getWarning_lbl().setText("Veillez choisir une date de depart.");
			return;
		}

		//Si la valeur de date est erroné
		try {
			dateRet = creer_reserv.getDateRetour();
		} catch (IllegalArgumentException e1) {
			creer_reserv.getWarning_lbl().setText("Veillez choisir une date de retour.");
			return;
		}


		if(checkDates(dateDep, dateRet)) {
			if(ReservationDAO.isReservationDateAvailable(codeVehicule, dateDep, dateRet, -1)) {
				//si vehicule disponible pendant cette date creer reservation
				ReservationDAO.createReservation(codeClient, codeVehicule, dateDep, dateRet);
				JOptionPane.showConfirmDialog(null, "Operation Effectuée.", "Operation Effectuée", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
				creer_reserv.getWarning_lbl().setText("");

				ActualiserTableau();
			}
			else
				creer_reserv.getWarning_lbl().setText("<html>La vehicule est déja reservé pendant cette interval choisi.</html>");
		} else {
			creer_reserv.getWarning_lbl().setText("<html>Veuillez selectionner une date appropriee</html>");
		}
	}

	public void goToNewReserv() {
		ActualiserTableClient();
		ActualiserTableVehicule();

		cl.show(mInterface.getMainPanel(), "newReserv");
		reserv_panel.getReserv_warning_lbl().setText("");
	}

	/**
	 * Methode pour creer la nouvelle panel de modification
	 */
	public void goToModReserv() {

		int index = reserv_panel.getReserv_table().getSelectedRow();
		if(index < 0) {
			// if user hasnt selected any row :
			reserv_panel.getReserv_warning_lbl().setText("<html>Veuillez Selectionner une reservation à modifier.</html>");
			// ^ html tag is for automatic text wrapping
			return;
		}

		int codeReserv = Integer.parseInt((String) reserv_panel.getReserv_table().getValueAt(index, 0));

		Reservation r = ReservationDAO.findReservation(codeReserv).get(0);

		//Open reservation modification window
		//ModifierReservation newReserv = new ModifierReservation(self, r);
		this.mod_reserv = new ModifierReserPanel(r, this);
		mInterface.getMainPanel().add(mod_reserv, "modReserv");
		cl.show(mInterface.getMainPanel(), "modReserv");

		//Reset warning label on succesful operation
		reserv_panel.getReserv_warning_lbl().setText("");
	}

	public void goBack() {
		cl.show(mInterface.getMainPanel(), "reserv");
		mInterface.getMainPanel().remove(mod_reserv);
	}

	/**
	 * Methode qui recupere les donnés a partir de l'interface ModifierReservation et les verifie, puis il les enregistre dans la BD
	 */
	public void ModifierReservation() {

		Date dateDep, dateRet;

		//Si la valeur de date est erroné
		try {
			dateDep = mod_reserv.getDateDepart();
		} catch (IllegalArgumentException e1) {
			mod_reserv.getWarning_lbl().setText("Veillez choisir une date de depart.");
			return;
		}

		//Si la valeur de date est erroné
		try {
			dateRet = mod_reserv.getDateRetour();
		} catch (IllegalArgumentException e1) {
			mod_reserv.getWarning_lbl().setText("Veillez choisir une date de retour.");
			return;
		}

		String codeVehicule = mod_reserv.getMatricule();
		int codeReserv = mod_reserv.getCodeReserv();
		boolean isValid = mod_reserv.isValid();
		boolean isCanceled = mod_reserv.isCanceled();

		if(isValid && isCanceled) {
			mod_reserv.getWarning_lbl().setText("Une reservation ne peut pas être validée est annulée aux même temps.");
			return;
		}

		if(checkDates(dateDep, dateRet)) {
			if(ReservationDAO.isReservationDateAvailable(codeVehicule, dateDep, dateRet, codeReserv)) {
				//si vehicule disponible pendant cette date creer reservation
				ReservationDAO.modifyReservation(codeReserv, dateDep, dateRet, isValid, isCanceled);
				JOptionPane.showConfirmDialog(null, "Operation Effectuée.", "Operation Effectuée", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
				mod_reserv.getWarning_lbl().setText("");

				ActualiserTableau();
				goBack();
			}
			else
				mod_reserv.getWarning_lbl().setText("<html>La vehicule est déja reservé pendant cette interval choisi.</html>");
		} else {
			mod_reserv.getWarning_lbl().setText("<html>Veuillez selectionner une date appropriee.</html>");
		}

	}
	/**
	 * Methode qui recuperere la reservation selectionnée dans le tableau des reservations
	 * et le supprime.
	 */
	public void SupprimerReservation() {
		int index = reserv_panel.getReserv_table().getSelectedRow();
		if(index < 0) {
			//Si l'utilisateur n'a pas choisi une reservation
			reserv_panel.getReserv_warning_lbl().setText("<html>Veuillez choisir une reservation à supprimer.</html>");
			return;
		}
		reserv_panel.getReserv_warning_lbl().setText("");

		int result = JOptionPane.showConfirmDialog(null, "Êtes vous sûr?", "Verification", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if(result == JOptionPane.YES_OPTION) {
			//si l'utilisateur a choisi oui -> supprimer reservation de la bd
			ReservationDAO.deleteReservation(Integer.parseInt((String) reserv_panel.getReserv_table().getValueAt(index, 0)));
		}

		ActualiserTableau();
	}

	/**
	 * Methode qui recupére l'identifiant de la reservation a partir de barre de recherche, et rempli le tableau avec l'entree correspondant
	 */
	public void RechercherReservation() {
		String input = reserv_panel.getReserv_field().getText();

		//verifier si l'entree est seulement des entier et qu'il n'est pas vide
		if(input.isEmpty() || !input.matches("^[0-9]+$")) {
			reserv_panel.getReserv_warning_lbl().setText("<html>Veuillez entrer un code valid.</html>");
			return;
		}

		ArrayList<Reservation> list_reserv = ReservationDAO.findReservation(Integer.parseInt(input));
		reserv_panel.getReserv_warning_lbl().setText("");
		reserv_panel.getReserv_model().loadReservations(list_reserv);
	}

	/**
	 * Methode qui verifie si deux dates sont en ordre
	 * @param dateDep
	 * @param dateRet
	 * @return true si dateDep < dateRet, false sinon
	 */
	public boolean checkDates(Date dateDep, Date dateRet) {
		LocalDate today = LocalDate.now();
		LocalDate d1 = dateDep.toLocalDate();
		LocalDate d2 = dateRet.toLocalDate();
		Duration d = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
		
		if(today.isAfter(d1)) return false;
		return !(d.toSeconds() <= 0);
	}


	public void setReservModPanel(ModifierReserPanel p) {
		this.mod_reserv = p;
	}

	/**
	 * Methode qui actualise le tableau des vehicules dans le panel de creation de reservation
	 */
	public void ActualiserTableVehicule() {
		ArrayList<Vehicule> vList = ReservationDAO.getAvailableVehicles();
		creer_reserv.getVehiculeTableModel().loadVehicules(vList);
	}
	public void ActualiserTableClient() {
		ClientController.fetchAll(creer_reserv.getCient_table());
	}

	/**
	 * Methode qui permet de rechercher un client dans le panel de creation de reservation
	 * @param nom
	 */
	public void searchClient(String nom) {
		ArrayList<Client> cList = ReservationDAO.findClient(nom);
		DefaultTableModel dtm = ClientController.preparerModel(cList);
		creer_reserv.getCient_table().setModel(dtm);
	}

	public void searchVehicle(String matricule) {
		ArrayList<Vehicule> vList = vehiculeDAO.findVehiculeAutoCompleting(matricule);
		creer_reserv.getVehiculeTableModel().loadVehicules(vList);
	}
}
