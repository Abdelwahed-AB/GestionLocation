package controller;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import dao.ParkingDAO;
import dao.vehiculeDAO;
import interfaces.MainInterface;
import model.Parking;
import model.Vehicule;
import view.AddNewVehicule;
import view.AllVehiculeInfoPanel;
import view.ChangeExistingVehicle;
import view.VehiculePanel;

import java.security.MessageDigest;
import java.sql.Date;
import java.time.LocalDate;

public class VehiculeController {
	private static JTable vehiculeTable;
	private static MainInterface window;
	private static VehiculePanel vehiculePanel;
	private static MessageDigest md;
	 
	 
	 
// METRHODE AUTOCOMPLETING
	
	public static void autoCompleting(String a) {
		ArrayList<Vehicule> vList = vehiculeDAO.findVehiculeAutoCompleting(a);//RETOURNE UNE ARRAYLIST DES VEHICULES 
		vehiculePanel.getTableModel().loadVehicules(vList);//ATTACHE LA LISTE A LA TABLE (vehiculeTable )DU PANEL (VehiculePanel)
	}
	
//AFFICHER TOUT LES ENREGISTREMENT
	
	public static void fetchAll() {
		ArrayList<Vehicule> vList = vehiculeDAO.fetchAll();
		vehiculePanel.getTableModel().loadVehicules(vList);
	}
	
// METHODE AJOUTER UN NOUVEAU VEHICULE QUI LANCE LE PANEL AddNewVehicule
	
	public static void addVehicule( ) {
		try {
			AddNewVehicule newVehicule = new AddNewVehicule(window);
			window.addToMainPanel(newVehicule,"newVehicule");
			window.showOnMainPanel("newVehicule");
			newVehicule.getDisponible().setSelected(true);
			newVehicule.getLbl_disp().setText("Disponible");;
			newVehicule.getYcomboBox().setSelectedIndex(0);//ON FORCE LE CHOIX D'UNE DATE POUR QU'ON EVITE LA SEVAUGARDE SANS CHOISIR UNE
			newVehicule.getMcomboBox().setSelectedIndex(0);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
//METHODE SAUVEAGARDER L'ENREGISTREMENT
	
	public static void saveNewVehicule(Vehicule v) {
		
		if(v.getCarburant().matches("[a-zA-Z]*") && v.getMatricule().matches("[a-zA-Z 0-9]*")&&v.getMarque().matches("[a-zA-Z 0-9]*")&&v.getType().matches("[a-zA-Z]*")) {
				if(vehiculeDAO.createVehicule(v)) {
					ParkingDAO.addVehiculeDAO(v.getMatricule(), v.getCodePark());//fonction qui decrement le nombre de places vides dans un parking et puis ajoute le vehicule au park et le met à jour 
					JOptionPane.showMessageDialog(null,"Vehicule ajouté", "Ajouté avec succés", JOptionPane.INFORMATION_MESSAGE);
					window.showOnMainPanel("vehicule");//revenir au menu principal
					VehiculeController.fetchAll();
			}else
				JOptionPane.showMessageDialog(null,"Ajout echoué", "Probléme d'ajout", JOptionPane.ERROR_MESSAGE);
		}
		else {
			JOptionPane.showMessageDialog(null,"Seulement les alphabets sont permis pour les champs(type,carburant),"
					+ "les alphabets et les nombres  pour les champs(matricule,marque) ", "Incorrect input", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
//MRTHODE ANNULER L'ENREGISTREMENT/CHANGEMENT
	
	public static void cancel(MainInterface MI) {
		MI.showOnMainPanel("vehicule");	//revenir au menu precedent
		ArrayList<Vehicule> vList = vehiculeDAO.fetchAll();
		vehiculePanel.getTableModel().loadVehicules(vList);
	}
	
	
//METHODE QUI AFFICHE TOUS LES INFORMATIONS D'UNE VEHICULE
	
	public static void DisplayAllVehiculeInfo(String matricule) {
		// TODO Auto-generated method stub
		AllVehiculeInfoPanel AVIP=new AllVehiculeInfoPanel(window);
		window.addToMainPanel(AVIP, "AllVInfo");
		window.showOnMainPanel("AllVInfo");
		Vehicule V=vehiculeDAO.findVehicule(vehiculeTable.getModel().getValueAt(vehiculeTable.getSelectedRow(),0).toString());
		AVIP.getLbl_matricuule().setText(V.getMatricule());
		AVIP.getLbl_Marque().setText(V.getMarque());
		AVIP.getLbl_type().setText(V.getType());
		AVIP.getLbl_carburant().setText(V.getCarburant());
		AVIP.getLbl_kilometrage().setText(V.getKilometrage()+" KM");
		AVIP.getLblDMC().setText(V.getDMC()+"");
		
		Parking p = ParkingDAO.findParkingByCodeDAO(V.getCodePark());
		if(p != null)
			AVIP.getLbl_codeParking().setText(p.getNomParking());
		else
			AVIP.getLbl_codeParking().setText("pas dans un park");
		
		AVIP.getLbl_PLocation().setText(V.getPrixLocation()+" DH");
		if(V.getDisponible())
			AVIP.getLbl_disponibilite().setText("Oui");
		else
			AVIP.getLbl_disponibilite().setText("Non");
	}
	
// METHODE QUI SUPPRIME L'ENREGISTREMENT SELECTIONNE
	
	public static void removeVehicule() {
		// TODO Auto-generated method stub
		int i = vehiculeTable.getSelectedRow();
		try {
			if (i!=-1) {
				int result = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiement supprimer le vehicule immatriculée : "+vehiculeTable.getModel().getValueAt(i, 0).toString()+" ?", "Confirmer la suppression", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(result == JOptionPane.YES_OPTION) {
					String matriculeVehicule =vehiculeTable.getModel().getValueAt(i, 0).toString();
					int oldCodePark=Integer.parseInt(vehiculeTable.getModel().getValueAt(i, 3).toString());
					ParkingDAO.removeVehiculeDAO(matriculeVehicule, oldCodePark);//LIBERER UNE PLACE DANS L'ANCIEN PARKING
					vehiculeDAO.removeVehicule(matriculeVehicule);
					VehiculeController.fetchAll();
					JOptionPane.showMessageDialog(null,"Vehicule supprimé avec succés", "Suppression", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			else
				JOptionPane.showMessageDialog(null,"Aucun vehicule n'est selectionné", "Echec de suppression", JOptionPane.ERROR_MESSAGE);
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage(), "Echec de suppression", JOptionPane.ERROR_MESSAGE);
		}
	}
	
//METHODE QUI LANCE LE PANEL D EMODIFICATION ET QUI INITIALISE LES CHAMPS PAR LES ANCIENNES DONNEES DU VEHICULE
	
	public static void changeVehicle() {		
		// TODO Auto-generated method stub
		int index=VehiculeController.vehiculeTable.getSelectedRow();
		if(index>=0) {
			Vehicule v=vehiculeDAO.findVehicule(vehiculeTable.getModel().getValueAt(index,0).toString());
			ChangeExistingVehicle CEV =new ChangeExistingVehicle(window);
			CEV.getImmatriculationVehicule().setText(v.getMatricule().toString());
			CEV.getMarqueVehicule().setText(v.getMarque().toString());
			CEV.getTypeVehicule().setText(v.getType().toString());
			CEV.getCarburant().setText(v.getCarburant().toString());
			CEV.getKilometrage().setText(v.getKilometrage()+"");
			LocalDate LD=(Date.valueOf(v.getDMC().toString())).toLocalDate();//ON EVITE EXTRAIRE L'ANNEE, LE MOIS ET LA DATE DIRECTEMENT AVEC LES MTHDS GET...(depricated)
			CEV.getYcomboBox().setSelectedItem( LD.getYear()+"");
			CEV.getMcomboBox().setSelectedItem( LD.getMonthValue()+"");
			CEV.getDcomboBox().setSelectedItem( LD.getDayOfMonth()+"");
			
			Parking p = ParkingDAO.findParkingByCodeDAO(v.getCodePark());
			if(p != null) {
				CEV.getParkComboBox().setSelectedItem(v.getCodePark()+"-"+p.getNomParking());//initialise le comboBox par l'ancien nom du park
			}
			CEV.getPrixLocation().setText(v.getPrixLocation()+"");
			CEV.getDisponible().setSelected(v.getDisponible());
			VehiculeController.changeDisponibility(CEV);
			window.addToMainPanel(CEV, "changeVehicle");
			window.showOnMainPanel("changeVehicle");
			ChangeExistingVehicle.setOldId(vehiculeTable.getModel().getValueAt(index,0).toString());
			ChangeExistingVehicle.setOldCodePark(v.getCodePark());
		}
		else
			JOptionPane.showMessageDialog(null,"Aucun vehicule n'est selectionné", "Echec de modification", JOptionPane.ERROR_MESSAGE);
	}
	
//METHODE QUI ENREGISTRE LES MIDIFICATION SD'UN VEHICULE
	
	public static void saveChanges(Vehicule v,String oldId,int oldCodePark) {
		// TODO Auto-generated method stub
		
		if(vehiculeDAO.verifyVehicle(v.getMatricule())&&!(v.getMatricule().equals(oldId))) {//SI LA MATRICULE EXITE DEJA MAIS DIFFERENT DE L'ANCIEN MATRICULE
			JOptionPane.showMessageDialog(null,"le champ matricule existe deja, changer le", "problème d'identifiant", JOptionPane.ERROR_MESSAGE);
		}else {
			if(v.getCarburant().matches("[a-zA-Z]*") && v.getMatricule().matches("[a-zA-Z 0-9]*")&&v.getMarque().matches("[a-zA-Z 0-9]*")&&v.getType().matches("[a-zA-Z]*")) {
				if(vehiculeDAO.ChangeVehicle(v,oldId)) {

					ParkingDAO.removeVehiculeDAO(v.getMatricule(), oldCodePark);//LIBERER UNE PLACE DANS L'ANCIEN PARKING
					ParkingDAO.addVehiculeDAO(v.getMatricule(), v.getCodePark());//OCUPPER UNE PLACE DANS LE NOUVEAU PARKING
					window.showOnMainPanel("vehicule");//REVENIR AU MENU VEHICULE
					JOptionPane.showMessageDialog(null,"Vehicule modifié ", "Modification avec succés", JOptionPane.INFORMATION_MESSAGE);
					VehiculeController.fetchAll();
				}else
					JOptionPane.showMessageDialog(null,"Modification echoué", "Probléme de modification", JOptionPane.ERROR_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(null,"Seulement les alphabets sont permis pour les champs(type,carburant),"
						+ "les alphabets et les nombres  pour les champs(matricule,marque) ", "Incorrect input", JOptionPane.ERROR_MESSAGE);
			}
		}

	}
	
//FONCTION QUI RETROURNE LES NOMS DES PARKINGS[utilisée pour afficher les noms des parking ayant une capacité >0 dans le combobox ]
	
		public static ArrayList<String> ParksNames(){
			return vehiculeDAO.nomPark();
		}
	
// FONCTIONS POUR LE CONTROLE DES CHAMPS RENSEIGNES
		

//VERIFIE SI LES CHAMPS DU NOUVEAU VEHICULE SONT VIDES
			
	@SuppressWarnings("deprecation")
	public static boolean empty(AddNewVehicule ANV) {
		//[ON NE PEUT UTILISER DIRECTEMENT UN OBJET VEHCULE COMME ARGUMENT DE CETTE FONCTION CAR LA MTHD IsEmpty() N'EST PAS DEFINIE POUR CERTAIN ATTRIBUTS]
		if(ANV.getImmatriculationVehicule().getText().isEmpty()||ANV.getMarqueVehicule().getText().isEmpty()||ANV.getTypeVehicule().getText().isEmpty()||
				ANV.getCarburant().getText().isEmpty()||ANV.getKilometrage().getText().isEmpty()||ANV.getPrixLocation().getText().isEmpty()||ANV.getDisponible().getText().isEmpty())
			return true;
		else return false;
	}
	
	@SuppressWarnings("deprecation")
	public static boolean empty(ChangeExistingVehicle CEV) {//VERIFIE SI  LES CHAMPS DU PANEL ChangeExistingVehicle SONT VIDES
		//[ON NE PEUT UTILISER DIRECTEMENT UN OBJET VEHCULE COMME ARGUMENT DE CETTE FONCTION CAR LA MTHD IsEmpty() N'EST PAS DEFINIE POUR CERTAIN ATTRIBUTS]
		if(CEV.getImmatriculationVehicule().getText().isEmpty()||CEV.getMarqueVehicule().getText().isEmpty()||CEV.getTypeVehicule().getText().isEmpty()||
				CEV.getCarburant().getText().isEmpty()||CEV.getKilometrage().getText().isEmpty()||CEV.getPrixLocation().getText().isEmpty()||CEV.getDisponible().getText().isEmpty())
			return true;
		else return false;
	}
	
//CETTE METHODE AFFICHE OU NON LES LABEL DISPONIBLE/INDISPONIBLE SELON L'ETAT DE CHECKBOX
	
	public static void changeDisponibility(AddNewVehicule ANV) {
		if(ANV.getDisponible().isSelected()) {
			ANV.getLbl_disp().setText("Disponible");
		}
		else
			ANV.getLbl_disp().setText("Indisponible");
	}
	
	
//CETTE METHODE AFFICHE OU NON LES LABEL DISPONIBLE/INDISPONIBLE SELON L'ETAT DE CHECKBOX	
	
	public static void changeDisponibility(ChangeExistingVehicle CEV) {
		// TODO Auto-generated method stub
		if(CEV.getDisponible().isSelected()) {
			CEV.getLbl_disp().setText("Disponible");
		}
		else
			CEV.getLbl_disp().setText("Indisponible");
	}

//CETTE METHODE VIDE TOUS LES CHAMPS RENSEIGNES PAR L'UTILISATEUR
	
	public static void emptyAddFields(AddNewVehicule ANV) {
		ANV.getImmatriculationVehicule().setText("");
		ANV.getMarqueVehicule().setText("");
		ANV.getTypeVehicule().setText("");
		ANV.getCarburant().setText("");
		ANV.getKilometrage().setText("");
		ANV.getParkComboBox().setSelectedIndex(0);
		ANV.getPrixLocation().setText("");
		ANV.getLbl_disp().setText("Disponible");;
		ANV.getDisponible().setSelected(true);
		ANV.getYcomboBox().setSelectedIndex(0);//ON FORCE LE CHOIX D'UNE DATE POUR QU'ON EVITE LA SEVAUGARDE SANS CHOISIR UNE
		ANV.getMcomboBox().setSelectedIndex(0);
	}
	
//CETTE METHODE VIDE TOU LES CHAMPS RENSEIGNES PAR L'UTILISATEUR
	
	public static void emptyVehicleFields(ChangeExistingVehicle CEV) {
		CEV.getImmatriculationVehicule().setText("");
		CEV.getMarqueVehicule().setText("");
		CEV.getTypeVehicule().setText("");
		CEV.getCarburant().setText("");
		CEV.getKilometrage().setText("");
		CEV.getParkComboBox().setSelectedIndex(0);;
		CEV.getPrixLocation().setText("");
		CEV.getLbl_disp().setText("Disponible");
		CEV.getDisponible().setSelected(true);
		CEV.getYcomboBox().setSelectedIndex(0);//ON FORCE LE CHOIX D'UNE DATE POUR QU'ON EVITE LA SEVAUGARDE SANS CHOISIR UNE
		CEV.getMcomboBox().setSelectedIndex(0);
		
	}
	
	// setters
	
	public static void setWindow(MainInterface window) {
		VehiculeController.window=window;
	}
	
	public static void setTable(JTable vehiculeTable){
		VehiculeController.vehiculeTable=vehiculeTable;
	}
	
	public static void setPanel(VehiculePanel vehiculePanel) {
		VehiculeController.vehiculePanel=vehiculePanel;
	}

}
