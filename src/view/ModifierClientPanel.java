package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.ClientController;
import model.Client;

public class ModifierClientPanel extends JPanel {

	// on a besion de ces variables � l'interieur de plusieurs fonction donc on
	// les rendre globals
	private CardLayout cl;
	private JTextField nomClientTextField;
	private JTextField prenomClientTextField;
	private JTextField teleClientTextField;
	private JTextField adresseClientTextField;

	/**
	 * Create the panel.
	 */
	public ModifierClientPanel(JPanel panel, Client client, JTable table) {
		// on a besion de cl pour revenir au menu principal
		this.cl = (CardLayout) panel.getLayout();
		this.setLayout(null);
		this.setBounds(0, 0, 766, 598);


		nomClientTextField = new JTextField();
		nomClientTextField.setBounds(142, 20, 202, 20);
		this.add(nomClientTextField);
		nomClientTextField.setColumns(10);
		nomClientTextField.setText(client.getNomClient());

		prenomClientTextField = new JTextField();
		prenomClientTextField.setBounds(488, 20, 202, 20);
		this.add(prenomClientTextField);
		prenomClientTextField.setColumns(10);
		prenomClientTextField.setText(client.getPrenomClient());

		teleClientTextField = new JTextField();
		teleClientTextField.setBounds(142, 73, 202, 20);
		this.add(teleClientTextField);
		teleClientTextField.setColumns(10);
		teleClientTextField.setText(client.getNumTelClient());

		adresseClientTextField = new JTextField();
		adresseClientTextField.setBounds(488, 76, 202, 20);
		this.add(adresseClientTextField);
		adresseClientTextField.setColumns(10);
		adresseClientTextField.setText(client.getAddresseClient());

		JLabel nomClientlbl = new JLabel("Nom Client");
		nomClientlbl.setHorizontalAlignment(SwingConstants.CENTER);
		nomClientlbl.setBounds(25, 23, 139, 14);
		this.add(nomClientlbl);

		JLabel prenomClientlbl = new JLabel("Prenom Client");
		prenomClientlbl.setHorizontalAlignment(SwingConstants.CENTER);
		prenomClientlbl.setBounds(371, 23, 139, 14);
		this.add(prenomClientlbl);

		JLabel teleClientlbl = new JLabel("Num Tel Client");
		teleClientlbl.setHorizontalAlignment(SwingConstants.CENTER);
		teleClientlbl.setBounds(25, 76, 139, 14);
		this.add(teleClientlbl);

		JLabel imagePath = new JLabel(client.getImage());
		imagePath.setHorizontalAlignment(SwingConstants.CENTER);
		imagePath.setBounds(25, 393, 202, 14);
		this.add(imagePath);

		JLabel permisPath = new JLabel(client.getPermisScannee());
		permisPath.setHorizontalAlignment(SwingConstants.CENTER);
		permisPath.setBounds(396, 393, 202, 14);
		this.add(permisPath);

		JLabel warningLabel = new JLabel("");
		warningLabel.setForeground(Color.RED);
		warningLabel.setBounds(51, 518, 621, 30);
		add(warningLabel);

		JLabel imageClient = new JLabel();
		imageClient.setHorizontalAlignment(SwingConstants.CENTER);
		imageClient.setBounds(40, 166, 179, 217);
		add(imageClient);
		ClientController.prepareImage(client.getImage(), imageClient);

		JLabel permisClient = new JLabel();
		permisClient.setBounds(309, 166, 381, 224);
		add(permisClient);
		ClientController.prepareImage(client.getPermisScannee(), permisClient);

		JButton buttonEffacer = new JButton("Effacer Tout");
		buttonEffacer.setBackground(viewSettings.SECONDARY);
		buttonEffacer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// effacer tous les informations pour les red�finir
				nomClientTextField.setText("");
				prenomClientTextField.setText("");
				teleClientTextField.setText("");
				adresseClientTextField.setText("");
				imagePath.setText("Image de taille 179x217");
				permisPath.setText("");
				warningLabel.setText("");
				imageClient.setIcon(null);
				permisClient.setIcon(null);
			}
		});
		buttonEffacer.setBounds(279, 465, 131, 42);
		this.add(buttonEffacer);

		JButton buttonRetour = new JButton("Retour");
		buttonRetour.setBackground(viewSettings.MAIN);
		buttonRetour.setForeground(viewSettings.WHITE);
		buttonRetour.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(panel, "client");
			}
		});
		buttonRetour.setBounds(68, 465, 131, 42);
		this.add(buttonRetour);

		JLabel adressrClientlbl = new JLabel("Adresse Client");
		adressrClientlbl.setHorizontalAlignment(SwingConstants.CENTER);
		adressrClientlbl.setBounds(371, 76, 139, 14);
		this.add(adressrClientlbl);

		JLabel imageClientlbl = new JLabel("Image Client");
		imageClientlbl.setHorizontalAlignment(SwingConstants.CENTER);
		imageClientlbl.setBounds(25, 125, 139, 14);
		this.add(imageClientlbl);

		JLabel permisClientlbl = new JLabel("Permis Scannee de Client");
		permisClientlbl.setHorizontalAlignment(SwingConstants.CENTER);
		permisClientlbl.setBounds(371, 125, 139, 14);
		this.add(permisClientlbl);

		JButton imageButton = new JButton("Choisir un fichier");
		imageButton.setBackground(viewSettings.SECONDARY);
		imageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// ouvrir une fenetre pour s�l�ctionn� l'image
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("Choisir une Image");
				// ajouter un filtre � la fenetre de choix
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Image", "jpg", "png");
				chooser.addChoosableFileFilter(filter);
				chooser.showOpenDialog(null);
				// si l'utisitaeur ne s�l�ctionne aucune image
				File file = chooser.getSelectedFile();
				if (file != null) {
					// si l'utilisateur s�lectionne un fichier d'autre type
					if (file.getName().endsWith(".jpg") || file.getName().endsWith(".JPG")
							|| file.getName().endsWith(".png") || file.getName().endsWith(".PNG")) {
						imagePath.setText(file.getAbsolutePath());
						ClientController.prepareImage(file.getAbsolutePath(), imageClient);
						warningLabel.setText("");
					} else {
						warningLabel.setText("*Vous devez choisir une fichier png ou jpg");
					}
				} else {
					warningLabel.setText("*Vous devez selectionnee une image");
				}
			}
		});
		imageButton.setBounds(68, 417, 130, 23);
		this.add(imageButton);

		JButton permisButton = new JButton("Choisir un fichier");
		permisButton.setBackground(viewSettings.SECONDARY);
		permisButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// ouvrir une fenetre pour s�l�ctionn� l'image
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("Choisir une Image");
				// ajouter un filtre � la fenetre de choix
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Image", "jpg", "png");
				chooser.addChoosableFileFilter(filter);
				chooser.showOpenDialog(null);
				// si l'utisitaeur ne s�l�ctionne aucune image
				File file = chooser.getSelectedFile();
				if (file != null) {
					// si l'utilisateur s�lectionne un fichier d'autre type
					if (file.getName().endsWith(".jpg") || file.getName().endsWith(".JPG")
							|| file.getName().endsWith(".png") || file.getName().endsWith(".PNG")) {
						permisPath.setText(file.getAbsolutePath());
						ClientController.prepareImage(file.getAbsolutePath(), permisClient);
						warningLabel.setText("");
					} else {
						warningLabel.setText("*Vous devez choisir une fichier png ou jpg");
					}
				} else {
					warningLabel.setText("*Vous devez selectionnee une image");
				}
			}
		});
		permisButton.setBounds(432, 417, 131, 23);
		this.add(permisButton);

		JButton buttonSauvgarder = new JButton("Sauvgarder");
		buttonSauvgarder.setBackground(viewSettings.MAIN);
		buttonSauvgarder.setForeground(viewSettings.WHITE);
		buttonSauvgarder.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// tester si l'utilisateur remplir tous les champs
				if (!nomClientTextField.getText().isBlank() && !prenomClientTextField.getText().isBlank()
						&& !adresseClientTextField.getText().isBlank() && !teleClientTextField.getText().isBlank()
						&& !imagePath.getText().isBlank() && !permisPath.getText().isBlank()) {
					// tester si l'utilisateur ne fait des fautes lors de saisie
					if (nomClientTextField.getText().matches("^[a-zA-Z _'-]*")) {
						if (prenomClientTextField.getText().matches("^[a-zA-Z _'-]*")) {
							if (teleClientTextField.getText().matches("0[0-9]*|212[0-9]*")) {
								Client client1 = new Client(nomClientTextField.getText(),
										prenomClientTextField.getText(), adresseClientTextField.getText(),
										teleClientTextField.getText(), imagePath.getText(), permisPath.getText());
								client1.setCodeClient(client.getCodeClient());
								warningLabel.setText("");
								// cr�ation du client
								ClientController.modifyClient(client1);
								// si le client est bien cr�er dans la base de donn�e afficher message
								// "Op�ration Effectu�e avce Succ�e"
								JOptionPane.showConfirmDialog(null, "Operation Effectuee avce Succee", "Succee",
										JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
								cl.show(panel, "client");

							} else {
								warningLabel
										.setText("*Le num tele doit etre sous forme 0(611223344) ou 212(611223344)");
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
				// rafra�chir le tableau
				ClientController.fetchAll(table);
			}
		});
		buttonSauvgarder.setBounds(520, 465, 131, 42);
		this.add(buttonSauvgarder);

	}

}
