package view;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.ClientController;
import interfaces.MainInterface;
import model.Client;
import java.awt.Color;

public class ModifierClientPanel extends JPanel {

	//on a besion de ces variables à l'interieur de plusieurs fonction donc on les rendre globals
	private CardLayout cl;
	private JTextField nomClientTextField;
	private JTextField prenomClientTextField;
	private JTextField teleClientTextField;
	private JTextField adresseClientTextField;

	/**
	 * Create the panel.
	 */
	public ModifierClientPanel(JPanel panel, Client client, JTable table) {
		//on a besion de cl pour revenir au menu principal
		this.cl = (CardLayout) panel.getLayout();
		this.setLayout(null);
		this.setBounds(0, 0, 766, 598);
		
		nomClientTextField = new JTextField();
		nomClientTextField.setBounds(217, 40, 408, 20);
		this.add(nomClientTextField);
		nomClientTextField.setColumns(10);
		nomClientTextField.setText(client.getNomClient());
		
		prenomClientTextField = new JTextField();
		prenomClientTextField.setBounds(217, 85, 408, 20);
		this.add(prenomClientTextField);
		prenomClientTextField.setColumns(10);
		prenomClientTextField.setText(client.getPrenomClient());
		
		teleClientTextField = new JTextField();
		teleClientTextField.setBounds(217, 131, 408, 20);
		this.add(teleClientTextField);
		teleClientTextField.setColumns(10);
		teleClientTextField.setText(client.getNumTelClient());

		adresseClientTextField = new JTextField();
		adresseClientTextField.setBounds(217, 185, 408, 20);
		this.add(adresseClientTextField);
		adresseClientTextField.setColumns(10);
		adresseClientTextField.setText(client.getAddresseClient());
		
		JLabel nomClientlbl = new JLabel("nom client");
		nomClientlbl.setHorizontalAlignment(SwingConstants.CENTER);
		nomClientlbl.setBounds(0, 43, 197, 14);
		this.add(nomClientlbl);
		
		JLabel prenomClientlbl = new JLabel("prenom client");
		prenomClientlbl.setHorizontalAlignment(SwingConstants.CENTER);
		prenomClientlbl.setBounds(10, 88, 197, 14);
		this.add(prenomClientlbl);
		
		JLabel teleClientlbl = new JLabel("num Tel client");
		teleClientlbl.setHorizontalAlignment(SwingConstants.CENTER);
		teleClientlbl.setBounds(10, 134, 197, 14);
		this.add(teleClientlbl);
		
		JLabel imagePath = new JLabel(client.getImage());
		imagePath.setBounds(336, 240, 289, 14);
		this.add(imagePath);
		
		JLabel permisPath = new JLabel(client.getPermisScannee());
		permisPath.setBounds(336, 292, 289, 14);
		this.add(permisPath);

		JLabel warningLabel = new JLabel("");
		warningLabel.setForeground(Color.RED);
		warningLabel.setBounds(80, 422, 584, 43);
		add(warningLabel);
		
		JButton buttonEffacer = new JButton("Effacer");
		buttonEffacer.addMouseListener(new MouseAdapter() {
			@Override
			//effacer tous les informations pour les redéfinir
			public void mouseClicked(MouseEvent e) {
				nomClientTextField.setText("");
				prenomClientTextField.setText("");
				teleClientTextField.setText("");
				adresseClientTextField.setText("");
				imagePath.setText("image de taille 179x217");
				permisPath.setText("");
				warningLabel.setText("");
				
			}
		});
		buttonEffacer.setBounds(272, 346, 129, 43);
		this.add(buttonEffacer);
		
		JButton buttonRetour = new JButton("Retour");
		buttonRetour.addActionListener(new ActionListener() {
			//retour au page principale
			public void actionPerformed(ActionEvent e) {
				cl.show(panel, "client");
			}
		});
		buttonRetour.setBounds(68, 346, 129, 43);
		this.add(buttonRetour);
		
		JLabel adressrClientlbl = new JLabel("Adresse Client");
		adressrClientlbl.setHorizontalAlignment(SwingConstants.CENTER);
		adressrClientlbl.setBounds(10, 188, 187, 14);
		this.add(adressrClientlbl);
		
		JLabel imageClientlbl = new JLabel("Image Client");
		imageClientlbl.setHorizontalAlignment(SwingConstants.CENTER);
		imageClientlbl.setBounds(10, 240, 187, 14);
		this.add(imageClientlbl);
		
		JLabel permisClientlbl = new JLabel("Permis Scan\u00E9e de Client");
		permisClientlbl.setHorizontalAlignment(SwingConstants.CENTER);
		permisClientlbl.setBounds(10, 292, 187, 14);
		this.add(permisClientlbl);
		
		JButton imageButton = new JButton("choisir un fichier");
		imageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ouvrir une fenetre pour séléctionné l'image
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("Choisir une Image");
				//ajouter un filtre à la fenetre de choix
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Image", "jpg", "png");
				chooser.addChoosableFileFilter(filter);
				chooser.showOpenDialog(null);
				// si l'utisitaeur ne séléctionne aucune image
				File file = chooser.getSelectedFile();
				if (file != null) {
					//si l'utilisateur sélectionne un fichier d'autre type
					if (file.getName().endsWith(".jpg") || file.getName().endsWith(".JPG") || file.getName().endsWith(".png") || file.getName().endsWith(".PNG")) {
						imagePath.setText(file.getAbsolutePath());
						warningLabel.setText("");
					} else {
						warningLabel.setText("*Vous devez choisir une fichier png ou jpg");
					}
				} else {
					warningLabel.setText("*Vous devez séléctionnée une image");
				}
			}
		});
		imageButton.setBounds(217, 236, 109, 23);
		this.add(imageButton);
		
		JButton permisButton = new JButton("choisir un fichier");
		permisButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ouvrir une fenetre pour séléctionné l'image
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("Choisir une Image");
				//ajouter un filtre à la fenetre de choix
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Image", "jpg", "png");
				chooser.addChoosableFileFilter(filter);
				chooser.showOpenDialog(null);
				// si l'utisitaeur ne séléctionne aucune image
				File file = chooser.getSelectedFile();
				if (file != null) {
					//si l'utilisateur sélectionne un fichier d'autre type
					if (file.getName().endsWith(".jpg") || file.getName().endsWith(".JPG") || file.getName().endsWith(".png") || file.getName().endsWith(".PNG")) {
						permisPath.setText(file.getAbsolutePath());
						warningLabel.setText("");
					} else {
						warningLabel.setText("*Vous devez choisir une fichier png ou jpg");
					}
				} else {
					warningLabel.setText("*Vous devez séléctionnée une image");
				}
			}
		});
		permisButton.setBounds(217, 288, 109, 23);
		this.add(permisButton);
		
		JButton buttonSauvgarder = new JButton("Sauvgarder");
		buttonSauvgarder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//tester si l'utilisateur remplir tous les champs
				if (!nomClientTextField.getText().isBlank() && !prenomClientTextField.getText().isBlank() && !adresseClientTextField.getText().isBlank() && !teleClientTextField.getText().isBlank() && !imagePath.getText().isBlank() && !permisPath.getText().isBlank()) {
					//tester si l'utilisateur ne fait des fautes lors de saisie
					if (nomClientTextField.getText().matches("^[a-zA-Z _'é-]*")) {
						if (prenomClientTextField.getText().matches("^[a-zA-Z _'é-]*")) {
							if (teleClientTextField.getText().matches("0[0-9]*|212[0-9]*")) {
								Client client1 = new Client(nomClientTextField.getText(), prenomClientTextField.getText(), adresseClientTextField.getText(), teleClientTextField.getText(), imagePath.getText(), permisPath.getText());
								client1.setCodeClient(client.getCodeClient());
								warningLabel.setText("");
								boolean b = ClientController.modifyClient(client1);
								if (b) {
									JOptionPane.showConfirmDialog(null, "Opération Effectuée avce Succée", "Succée", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
									cl.show(panel, "client");
								} else {
									JOptionPane.showConfirmDialog(null, "Opération Echouée \n réssayer à nouveau", "Echoue", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
								}
							} else {
								warningLabel.setText("*Le num tele doit etre sous forme 0(611223344) ou 212(611223344)");
							}
							
						} else {
							warningLabel.setText("*Le prenom doit etre une chaine de caractere");
						}
					} else {
						warningLabel.setText("*Le nom doit etre une chaine de caractere");
					}
				} else {
					warningLabel.setText("*Vous devez remplir tous les champs");
				}
				// rafraîchir le tableau
				ClientController.fetchAll(table);
			}
		});
		buttonSauvgarder.setBounds(496, 346, 129, 43);
		this.add(buttonSauvgarder);
		
	}

}
