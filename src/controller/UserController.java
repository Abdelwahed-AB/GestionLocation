package controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import dao.UserDAO;
import interfaces.MainInterface;
import model.User;
import view.AddUser;
import view.ChangeExistingUser;
import view.UserPanel;

public class UserController {
	
	private static UserPanel userPanel;
	private static JTable userTable;
	private static MainInterface window;
	private static MessageDigest md;
	private static ChangeExistingUser CES;
	private static AddUser AU;;
	 
	 


// METHODE AUTOCOMPLETING
	public static void autoCompletion(String CleRecherche) {
		 ArrayList<User>  u=UserDAO.findUserAutoCompleting(CleRecherche);
		 UserPanel.getTableModel().loadUsers(u);
	}
	
// SUPPRIMER UN UTILISATEUR
	public static void removeUser() {
		int i = userTable.getSelectedRow();
		if (i>= 0) {
			int result = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiement supprimer l'utilisateur : "+userTable.getModel().getValueAt(i, 1).toString()+"  "+userTable.getModel().getValueAt(i, 2).toString()+" ?",
					"Confirmer la suppression", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(result == JOptionPane.YES_OPTION) {
				int id = Integer.parseInt(userTable.getModel().getValueAt(i, 0).toString());
				UserDAO.removeUser(id);
				UserController.fetchAll();
				JOptionPane.showMessageDialog(userPanel,"Utilisateur supprimé avec succés", "Suppression avec succés", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				UserController.fetchAll();
			}
		}
		else
			JOptionPane.showMessageDialog(userPanel,"Aucun utilisateur n'est sélectionné ", "Erreu de suppression", JOptionPane.ERROR_MESSAGE);
	}
//CHANGER LES ATTRIBUTS D'UN UTILISATEUR
	public static void changeUser() {
		int i = userTable.getSelectedRow();
		if (i>= 0) {
			/** ADD ALREADY EXISTING DATA*/
			User u=UserDAO.findUser(Integer.parseInt(userTable.getModel().getValueAt(i, 0).toString()));
			ChangeExistingUser changeUser = new ChangeExistingUser(window);
			UserController.CES=changeUser;
			changeUser.setVisible(true);
			changeUser.setTable(userTable);//AJOUTER LES ATTRIBUTS QUI SONT DEJA DANS LA BASE DE DONNEES
			changeUser.setNewMatricule(u.getMatricule());
			changeUser.setNewNom(u.getNom());
			changeUser.setNewPrenom(u.getPrenom());
			changeUser.setNewTel(u.getTelephone());
			changeUser.setNewAdresse(u.getAdresse());
			changeUser.setChkBox(u.isStatut());
			if(u.isStatut())
				changeUser.getUserSuspendu().setText("Actif");
			else
				changeUser.getUserSuspendu().setText("Suspendu");
			changeUser.setUsername(u.getUsername().toString());
			window.addToMainPanel(changeUser,"changeUser");// AJOUTER LE PANEL AU MAIN PANEL
			window.showOnMainPanel("changeUser");
			ChangeExistingUser.setOldId(Integer.parseInt(userTable.getModel().getValueAt(i,0).toString()));
			}
		else
			JOptionPane.showMessageDialog(userPanel,"Aucun utilisateur n'est sélectionné ", "Erreur de modification", JOptionPane.ERROR_MESSAGE);
		
	}
	
// AFFICHER TOUS LES UTILISATEUR QUI SONT DANS LA BASE DE DONNEES 
	public static void fetchAll() {
		ArrayList<User> uList = UserDAO.fetchAll();
		userPanel.getTableModel().loadUsers(uList);
	}
	
// AJOUTER UN NOUVEAU UTILISATEUR
	public static void createUser(UserPanel UP) {
		try {
			AddUser newUser = new AddUser(window);
			UserController.AU=newUser;
			window.addToMainPanel(newUser,"newUser");
			window.showOnMainPanel("newUser");
			newUser.setUserPanel(UP);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
//verifies si un utilisatru existe
	public static boolean findUser(int id) {
		if(UserDAO.findUser( id).equals(null)) return true;
		return false;
	}
	
// changer le status d'un utilisateur en cochant le CheckBox
	public static void changeStatus(ChangeExistingUser changeUserPanel) {
		if(!changeUserPanel.getChkBox().isSelected()) {
			changeUserPanel.setStatus(false);
			changeUserPanel.getUserSuspendu().setText("Suspendu");;
		}
		else {
		changeUserPanel.setStatus(true);
		changeUserPanel.getUserSuspendu().setText("Actif");;
		}
	}
	
	
// sousmettre les information que l'admin a modifier 
	public static void saveUserUpdate(User U,int oldId,String newPassword) {
		if(UserDAO.verifyUser(U.getMatricule())&&U.getMatricule()!=oldId) {//SI LA MATRICULE EXITE DEJA MAIS DIFFERENT DE L'ANCIEN MATRICULE
			JOptionPane.showMessageDialog(null,"le champ 'matricule' existe déja, changer le et cliquer sur enregistrer", "Probléme d'identifiant", JOptionPane.ERROR_MESSAGE);
		}else {
			if(UserController.CES.getNewTel().getText().matches("[0-9 *+ #]*")){
				if(UserDAO.modifyUser(U,oldId,newPassword)) {
				UserController.fetchAll();
				JOptionPane.showMessageDialog(null,"Utilisateur modifié ", "Modification avec succés", JOptionPane.INFORMATION_MESSAGE);
				window.showOnMainPanel("user");//REVENIR AU MENU UTILISATEUR
				
				}else
				JOptionPane.showMessageDialog(null,"Modification echouée", "Probléme de modification", JOptionPane.ERROR_MESSAGE);
			}else {
					JOptionPane.showMessageDialog(null,"Le champ telephone ne peut contenir que des nombres ou des symboles + * #", "Probléme de modification", JOptionPane.ERROR_MESSAGE);
					UserController.CES.getNewTel().setText("");
			}
		}
		
	}
	
// ajouter les nouveaux utilisateurs à la base de données
	public static void saveNewUsers() {
		if(!empty(UserController.AU)) {
	
			String a=UserController.AU.getNewUserNom().getText();
		 
			String b=UserController.AU.getNewUserPrenom().getText();
		 
			String c=UserController.AU.getNewUserTel().getText();
		 
			String d=UserController.AU.getNewUserAdresse().getText();
		
			String ee=UserController.AU.getNewUserUsername().getText();
			
			@SuppressWarnings("deprecation")
			String encryptedPassword=UserController.cryptWithMD5(UserController.AU.getNewUserPassword().getText());// we store encrypted value instead of the real password
			if(UserController.AU.getNewUserTel().getText().matches("[0-9 *+ #]*")){
				if (matches(UserController.AU)) {
					if(!UserDAO.findUser(ee)) {
						if(UserDAO.createUser(a, b, c, d, ee, encryptedPassword)) {
							UserController.fetchAll();
							JOptionPane.showMessageDialog(null,"Utilisateur ajouté avec succés", "Ajout evec succés", JOptionPane.INFORMATION_MESSAGE);
							window.showOnMainPanel("user");//revenir au menu principal
						}
						else
							JOptionPane.showMessageDialog(null,"Ajout d'utilisateur echoué", "Probleme d'Ajout", JOptionPane.ERROR_MESSAGE);
					}else
						JOptionPane.showMessageDialog(null,"Utilisateur existe deja", "Probleme d'ajout", JOptionPane.ERROR_MESSAGE);
				}else 
						JOptionPane.showMessageDialog(null,"Verifie que les deux mots de passe sont similaires", "Probleme de mot de passe", JOptionPane.WARNING_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(null,"Le champ telephone ne peut contenir que des nombres ou des symboles + * #", "Probléme de modification", JOptionPane.ERROR_MESSAGE);
				UserController.AU.getNewUserTel().setText("");;
			}
		}
		else 
			JOptionPane.showMessageDialog(null,"Un ou plusieurs champs sont vides", "Manque de données", JOptionPane.WARNING_MESSAGE);
		
	}

	
	
	
//méthodes de contrôle
	public static boolean empty(ChangeExistingUser JP) {//verifie si les cahmps de text sont vides[ça concerne la modification des utilisateurs]
		if(JP.getNewNom().getText().isEmpty()||JP.getNewPrenom().getText().isEmpty()||JP.getNewMatricule().getText().isEmpty()||JP.getNewTel().getText().isEmpty()||
				JP.getNewAdresse().getText().isEmpty())
			return true;
		else return false;
	}
	@SuppressWarnings("deprecation")
	public static boolean empty(AddUser addUser) {//verifie si les champs du nouveau utilisateur sont vides
		if(addUser.getNewUserNom().getText().isEmpty()||addUser.getNewUserPrenom().getText().isEmpty()||addUser.getNewUserTel().getText().isEmpty()||
				addUser.getNewUserAdresse().getText().isEmpty()||addUser.getNewUserPassword().getText().isEmpty()||addUser.getPasswordConfirmed().getText().isEmpty()||addUser.getNewUserUsername().getText().isEmpty())
			return true;
		else return false;
		
	}
	@SuppressWarnings("deprecation")
	public static boolean matches(AddUser addUser) {
		if(addUser.getPasswordConfirmed().getText().equals(addUser.getNewUserPassword().getText()))
			return true;
		else return false;
	}

// vider les champs du panel ChangeExistingUser
	public static void emptyUpdateFields(ChangeExistingUser changeExistingUser) {
		changeExistingUser.getNewMatricule().setText("");
		changeExistingUser.getNewNom().setText("");
		changeExistingUser.getNewPrenom().setText("");
		changeExistingUser.getNewTel().setText("");
		changeExistingUser.getNewAdresse().setText("");
		changeExistingUser.getChkBox().setSelected(true);
		changeExistingUser.getUserSuspendu().setText("Actif");;
		changeExistingUser.getUsername().setText("");
		changeExistingUser.getNewPasword().setText("");
	
	}
// vider les champs du panel AddUser
		public static void emptyAddFields(AddUser addUser) {
			addUser.getNewUserNom().setText("");
			addUser.getNewUserPrenom().setText("");
			addUser.getNewUserTel().setText("");
			addUser.getNewUserAdresse().setText("");
			addUser.getNewUserUsername().setText("");
			addUser.getNewUserPassword().setText("");
			addUser.getPasswordConfirmed().setText("");
		}
	
// methode de cryptage
	public static String cryptWithMD5(String pass){
	    try {
	        md = MessageDigest.getInstance("MD5");
	        byte[] passBytes = pass.getBytes();
	        md.reset();
	        byte[] digested = md.digest(passBytes);
	        StringBuffer sb = new StringBuffer();
	        for(int i=0;i<digested.length;i++){
	            sb.append(Integer.toHexString(0xff & digested[i]));
	        }
	        return sb.toString();
	    } catch (NoSuchAlgorithmException ex) {
	        Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
	    }
	        return null;
	}
	   
// SETTERS 
	public static void setTable(JTable a) {
		UserController.userTable=a;
	}
	public static  void setPanel(UserPanel a) {
		UserController.userPanel=a;
	}
	public static  void setWindow(MainInterface a) {
		UserController.window=a;
	}

	public static void setAU(AddUser addUser) {
		UserController.AU=addUser;
		
	}

	
	
}
