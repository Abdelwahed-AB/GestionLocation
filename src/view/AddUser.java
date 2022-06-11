package view;

import java.awt.CardLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.UserController;
import interfaces.MainInterface;

public class AddUser extends JPanel{
	private MainInterface mainInterface;
	private CardLayout cl;
	private JTextField newUserNom;
	private JTextField newUserPrenom;
	private JTextField newUserTel;
	private JTextField newUserAdresse;
	private JTextField newUserUsername;
	private JTextField newUserPassword;
	private JTextField passwordConfirmed;
	private UserPanel userPanel;

// constructor

	public AddUser(MainInterface mainInterface) {
		setBounds(new Rectangle(0, 0, 732, 547));
		this.mainInterface = mainInterface;
		this.cl = (CardLayout) mainInterface.getMainPanel().getLayout();
		initialize();
	}




	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

// creation d'un Panel

		setLayout(null);
		setLayout(null);
		JLabel lblNewLabel = new JLabel("Ajout d'un nouveau utilisateur");
		lblNewLabel.setBounds(116, 11, 549, 46);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(lblNewLabel);

		JLabel lblNewLabel_1_1 = new JLabel("Nom");
		lblNewLabel_1_1.setBounds(86, 93, 138, 28);
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(lblNewLabel_1_1);

		newUserNom = new JTextField();
		newUserNom.setBounds(244, 92, 399, 29);
		newUserNom.setColumns(10);
		this.add(newUserNom);


//ENREGISTRER EN CLIQUANT SUR ENTRER

		newUserNom.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					UserController.setAU(AddUser.this);
					UserController.saveNewUsers();
				}
			}
		});

		JLabel lblNewLabel_1_1_1 = new JLabel("Prenom");
		lblNewLabel_1_1_1.setBounds(86, 131, 138, 29);
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(lblNewLabel_1_1_1);

		newUserPrenom = new JTextField();
		newUserPrenom.setBounds(244, 132, 399, 28);
		newUserPrenom.setColumns(10);
		this.add(newUserPrenom);


//ENREGISTRER EN CLIQUANT SUR ENTRER

		newUserPrenom.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					UserController.setAU(AddUser.this);
					UserController.saveNewUsers();
				}
			}
		});

		JLabel lblNewLabel_1_1_2 = new JLabel("T\u00E9l\u00E9phone");
		lblNewLabel_1_1_2.setBounds(86, 180, 138, 28);
		lblNewLabel_1_1_2.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(lblNewLabel_1_1_2);

		newUserTel = new JTextField();
		newUserTel.setBounds(243, 180, 400, 28);
		newUserTel.setColumns(10);
		this.add(newUserTel);

//ENREGISTRER EN CLIQUANT SUR ENTRER

		newUserTel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					UserController.setAU(AddUser.this);
					UserController.saveNewUsers();
				}
			}
		});

		JLabel lblNewLabel_1_1_3 = new JLabel("Adresse");
		lblNewLabel_1_1_3.setBounds(86, 228, 138, 28);
		lblNewLabel_1_1_3.setHorizontalAlignment(SwingConstants.LEFT);
		this.add(lblNewLabel_1_1_3);

		newUserAdresse = new JTextField();
		newUserAdresse.setBounds(244, 228, 399, 28);
		newUserAdresse.setColumns(10);
		this.add(newUserAdresse);

//ENREGISTRER EN CLIQUANT SUR ENTRER

		newUserAdresse.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					UserController.setAU(AddUser.this);
					UserController.saveNewUsers();
				}
			}
		});

		JButton saveNewUser = new JButton("Enregistrer");
		saveNewUser.setBounds(38, 444, 159, 54);
		saveNewUser.setBackground(viewSettings.SECONDARY);
		saveNewUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UserController.setAU(AddUser.this);
				UserController.saveNewUsers();
			}
		});
		this.add(saveNewUser);


		JButton deleteNewUserFields = new JButton("Effacer tout");
		deleteNewUserFields.setBounds(288, 444, 159, 54);
		deleteNewUserFields.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UserController.emptyAddFields(AddUser.this);
			}
		});
		deleteNewUserFields.setBackground(viewSettings.SECONDARY);
		this.add(deleteNewUserFields);

		JButton CancelNewUserCreation = new JButton("Annuler");
		CancelNewUserCreation.setBounds(538, 444, 159, 54);
		CancelNewUserCreation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddUser.this.mainInterface.showOnMainPanel("user");	//revenir au menu precedent
			}
		});
		CancelNewUserCreation.setBackground(viewSettings.SECONDARY);
		this.add(CancelNewUserCreation);

		JLabel lblNewLabel_1_1_5_1 = new JLabel("Username");
		lblNewLabel_1_1_5_1.setBounds(86, 275, 138, 28);
		lblNewLabel_1_1_5_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_1_5_1.setBackground(viewSettings.SECONDARY);
		this.add(lblNewLabel_1_1_5_1);

		newUserUsername = new JTextField();
		newUserUsername.setBounds(244, 276, 399, 28);
		newUserUsername.setColumns(10);
		this.add(newUserUsername);

//ENREGISTRER EN CLIQUANT SUR ENTRER

		newUserUsername.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					UserController.setAU(AddUser.this);
					UserController.saveNewUsers();
				}
			}
		});

		JLabel lblNewLabel_1_1_5_2 = new JLabel("Mot de passe");
		lblNewLabel_1_1_5_2.setBounds(86, 324, 138, 28);
		lblNewLabel_1_1_5_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_1_5_2.setBackground(viewSettings.SECONDARY);
		this.add(lblNewLabel_1_1_5_2);

		newUserPassword = new JTextField();
		newUserPassword.setBounds(244, 324, 399, 28);
		newUserPassword.setColumns(10);
		this.add(newUserPassword);

//ENREGISTRER EN CLIQUANT SUR ENTRER

		newUserPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					UserController.setAU(AddUser.this);
					UserController.saveNewUsers();
				}
			}
		});

		JLabel lblNewLabel_1_1_5_2_1 = new JLabel("Confirmer mot de passe ");
		lblNewLabel_1_1_5_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1_1_5_2_1.setBackground(viewSettings.SECONDARY);
		lblNewLabel_1_1_5_2_1.setBounds(78, 371, 140, 28);
		add(lblNewLabel_1_1_5_2_1);

		passwordConfirmed = new JTextField();
		passwordConfirmed.setColumns(10);
		passwordConfirmed.setBounds(244, 371, 399, 28);
		add(passwordConfirmed);

//ENREGISTRER EN CLIQUANT SUR ENTRER

		passwordConfirmed.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					UserController.setAU(AddUser.this);
					UserController.saveNewUsers();
				}
			}
		});

	}


	// GETTERS
	public JTextField getNewUserNom() {
		return newUserNom;
	}


	public JTextField getNewUserPrenom() {
		return newUserPrenom;
	}


	public JTextField getNewUserTel() {
		return newUserTel;
	}


	public JTextField getNewUserAdresse() {
		return newUserAdresse;
	}


	public JTextField getNewUserUsername() {
		return newUserUsername;
	}


	public JTextField getNewUserPassword() {
		return newUserPassword;
	}

	public JTextField getPasswordConfirmed() {
		return passwordConfirmed;
	}

//SETTERS

	public void setUserPanel(UserPanel a) {
		this.userPanel=a;
	}
}
