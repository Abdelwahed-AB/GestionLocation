package view;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import controller.ContratController;
import controller.UserController;
import interfaces.MainInterface;
import model.ContractTableModel;

public class ContratPanel extends JPanel {
// ATTRIBUTS DE LA CLASSE ContratEPANEL
	private CardLayout cl;
	private static ContractTableModel cTable ;
	private JTable contratTable;
	private static LinkedHashMap<String, JLabel> navItemList;
	private JTextField 	chercherContrat;
//CONSTRUCTEUR DU PANEL
	public ContratPanel(MainInterface mainInterface) {
		setBounds(new Rectangle(0, 0, 732, 547));
		this.cl = (CardLayout) mainInterface.getMainPanel().getLayout();
		this.setLayout(null);
				
// ZONE D'AFFICHAGE
		JScrollPane CScrollPane = new JScrollPane();
		CScrollPane.setBounds(10, 60, 585, 477);
		this.add(CScrollPane);
		cTable = new ContractTableModel();
		contratTable=new JTable(cTable);
		contratTable.setSelectionBackground(viewSettings.SECONDARY);
		CScrollPane.setViewportView(contratTable);
		contratTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				
// ZONE DE RECHERCHE[UTILISE LA METHODE AUTOCOMPLETING A CHAQUE CLIQUE]
		chercherContrat = new JTextField();
		chercherContrat.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {// affiche les resultats qui commencent par le mot que l'utilisateur tappe
				ContratController.setTable(contratTable);
				ContratController.autoCompleting();
			}
		});
		chercherContrat.setBounds(10, 16, 585, 33);
		this.add(chercherContrat);
		chercherContrat.setColumns(10);
				
//BOUTON AJOUTER UN NOUVEAU CONTRAT
		JButton addContrat = new JButton("Ajouter ");
		addContrat.setBackground(viewSettings.SECONDARY);
		addContrat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddNewContract.setPanel(ContratPanel.this);
				ContratController.setWindow(mainInterface);// pour pouvoir instancier le panelAddNewContrat
				ContratController.displayReservation();//actualiser la table des reservations
				ContratController.addContrat();
			}
		});
		addContrat.setBounds(605, 60, 117, 50);
		this.add(addContrat);
				
//BOUTON SUPPRIMER UN CONTRAT ECXISTANT
				
		JButton removeContrat = new JButton("Suppimer");
		removeContrat.setBackground(viewSettings.SECONDARY);
		removeContrat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ContratController.setPanel(ContratPanel.this);
				ContratController.setTable(contratTable);
				ContratController.removeContrat();
			}
		});
		removeContrat.setBounds(605, 181, 117, 50);
		this.add(removeContrat);
//POUR SUPPRIMER AVEC LE BOUTON delete du clavier
		contratTable.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_DELETE&&contratTable.getSelectedRow()!=-1) {
					ContratController.setPanel(ContratPanel.this);
					ContratController.setTable(contratTable);
					ContratController.removeContrat();
				}
			}
		});
				
//BOUTON MODIFIER LES ATTRIBUTS D'UN Contrat EXISTANT
				
		JButton changeContrat = new JButton("Modifier");
		changeContrat.setBackground(viewSettings.SECONDARY);
		changeContrat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ContratController.setPanel(ContratPanel.this);
				ContratController.setTable(contratTable);
				ContratController.setWindow(mainInterface);
				ContratController.changeContrat();
			}
		});
		changeContrat.setBounds(605, 121, 117, 50);
		this.add(changeContrat);
		this.setLayout(null);
		
//BOUTON AFFICHER TOUT LES ATTRIBUTS D'UN ENREGISTREMENT
		JButton afficher = new JButton("Actualiser");
		afficher.setBackground(viewSettings.SECONDARY);
		afficher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ContratController.fetchAll();
			}
		});
		afficher.setBounds(605, 241, 117, 50);
		this.add(afficher);
				
				
				
//AFFCIHER TOUT LES ENREGISTREMENT DES QU'ON CLIQUE SUR CONTRAT DANS LA BARRE DE NAVIGATION
		navItemList.get("contrat").addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ContratController.setPanel(ContratPanel.this);
				ContratController.fetchAll();
				mainInterface.showOnMainPanel("contrat");
			}
		});
	}
//SETTERS 
	public static void setNavList(LinkedHashMap<String, JLabel> a) {
		ContratPanel.navItemList=a;
	}
	public static ContractTableModel getTableModel() { 
		return cTable;
	}
	public JTextField getChercherContrat() {
		return chercherContrat;
	}

}
