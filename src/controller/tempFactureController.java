package controller;

import java.util.ArrayList;

import dao.FactureDAO;
import model.Facture;
import view.FacturePanel;

public class tempFactureController {
	private FacturePanel fPanel;
	
	public tempFactureController() {
	}
	
	public tempFactureController(FacturePanel fpanel) {
		this.fPanel = fpanel;
	}
	
	/**
	 * Methode qui actualise le tableau des factures
	 */
	public void ActualiserTableau() {
		ArrayList<Facture> fList = FactureDAO.fetchAll();
		fPanel.getFactureTableModel().loadFactures(fList);
		
		//reset warning message
		fPanel.getFacture_warning_lbl().setText("");
	}
	
	/**
	 * Methode qui recupére l'identifiant de la facture a partir de barre de recherche, et rempli le tableau avec l'entree correspondant
	 */
	public void RechercherFacture() {
		String input = fPanel.getFacture_field().getText();
		
		//verifier si l'entree est seulement des entier et qu'il n'est pas vide
		if(input.isEmpty() || !input.matches("^[0-9]+$")) {
			fPanel.getFacture_warning_lbl().setText("<html>Veuillez entrer un code valid.</html>");
			return;
		}
		
		ArrayList<Facture> fList = FactureDAO.findFacture(Integer.parseInt(input));
		fPanel.getFactureTableModel().loadFactures(fList);
		fPanel.getFacture_warning_lbl().setText("");
	}
}
