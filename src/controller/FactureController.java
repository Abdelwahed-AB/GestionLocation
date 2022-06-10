package controller;

import java.awt.CardLayout;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import dao.FactureDAO;
import interfaces.MainInterface;
import metier.FactureMetier;
import model.Facture;
import view.CreerFacturePanel;
import view.FacturePanel;

public class FactureController {
	private FacturePanel fPanel;
	private CreerFacturePanel cfPanel;
	private MainInterface mInterface;
	private CardLayout cl;
	
	
	public FactureController(FacturePanel fpanel, CreerFacturePanel cfPanel, MainInterface mInterface) {
		this.fPanel = fpanel;
		this.cfPanel = cfPanel;
		this.mInterface = mInterface;
		this.cl = (CardLayout) mInterface.getMainPanel().getLayout();
		
		this.fPanel.setFactureController(this);
		this.cfPanel.setFactureController(this);
		ActualiserTableau();
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
	 * Methode qui recup�re l'identifiant de la facture a partir de barre de recherche, et rempli le tableau avec l'entree correspondant
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
	
	/**
	 * Methode qui recuperere la facture selectionn�e dans le tableau des factures
	 * et le supprime.
	 */
	public void SupprimerFacture() {
		int index = fPanel.getFacture_table().getSelectedRow();
		if(index < 0) {
			fPanel.getFacture_warning_lbl().setText("<html>Veuillez choisir une facture � supprimer.</html>");
			return;
		}
		
		int result = JOptionPane.showConfirmDialog(null, "�tes vous s�r?", "Verification", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if(result == JOptionPane.YES_OPTION) {
			FactureDAO.deleteFacture(Integer.parseInt((String) fPanel.getFacture_table().getValueAt(index, 0)));			
		}
		ActualiserTableau();
	}
	
	
	/**
	 * Methode qui recupere le contrat selectionn� dans le panel de creation des factures
	 * puis le cr�e dans la bd (Calcule automatique)
	 * puis il g�nere un pdf
	 */
	public void CreerFacture() {
		int index = cfPanel.getContrat_table().getSelectedRow();
		if(index < 0) {
			cfPanel.getWarning_lbl().setText("<html>Veuillez choisir un contrat pour creer une facture.</html>");
			return;
		}
		
		int codeContrat =  (int) cfPanel.getContrat_table().getValueAt(index, 0);
		
		FactureDAO.createFacture(codeContrat);
		
		Facture fact = FactureDAO.findFactureByContrat(codeContrat);
		
		if(fact == null) {
			JOptionPane.showConfirmDialog(null, "facture : " + fact, "Erreur Creation Facture", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
		}
		FactureMetier.createPdf(fact);
		goBack();
		ActualiserTableau();
	}
	
	/**
	 * Methode qui recup�re la facture selectionnee et l'imprime
	 */
	public void imprimerFacture() {
		int index = fPanel.getFacture_table().getSelectedRow();
		if(index < 0) {
			fPanel.getFacture_warning_lbl().setText("<html>Veuillez choisir une facture � imprimer.</html>");
			return;
		}
		
		Facture f = FactureDAO.findFacture(Integer.parseInt((String) fPanel.getFacture_table().getValueAt(index, 0))).get(0);
		
		FactureMetier.createPdf(f);
		ActualiserTableau();
	}
	
	public void searchContrat(int codeContrat) {
		//
	}
	
	//Methodes de navigation
	public void goToNewFacture() {
		cl.show(mInterface.getMainPanel(), "newFacture");
	}
	public void goBack() {
		cl.show(mInterface.getMainPanel(), "facture");
	}
}
