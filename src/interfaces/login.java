package interfaces;

import javax.swing.*;
import javax.swing.border.*;
import dao.UserDAO;
import view.viewSettings;

import java.awt.*;
import java.awt.event.*;

public class login extends JFrame {

	private JPanel contentPane;
	private JTextField username;
	private JPasswordField password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login frame = new login();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);// display window in the middle of the screen
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public login() {
		setTitle("Login Gestion Location");
		setIconImage(Toolkit.getDefaultToolkit().getImage(login.class.getResource("/icons/icons8-garage-80.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 520, 482);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(-19, -16, 61, 487);
		panel.setBackground(viewSettings.MAIN);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel username_lbl = new JLabel("Username:");
		username_lbl.setBounds(128, 118, 106, 27);
		contentPane.add(username_lbl);
		
		JLabel pass_lbl = new JLabel("Password:");
		pass_lbl.setBounds(128, 202, 76, 30);
		contentPane.add(pass_lbl);
		// username text field
		username = new JTextField();
		username.setBounds(128, 155, 238, 27);
		contentPane.add(username);
		username.setColumns(10);
		
		// password text field
		password = new JPasswordField();
		password.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					//if fields are empty ( you can add type of conditions on data types)
					if(emptyField()) {
						JOptionPane.showMessageDialog(null,"username or password are empty", "missing information", JOptionPane.ERROR_MESSAGE);
					}	
					else {
						if (UserDAO.verifyLogin(username.getText(),password.getText())){
							boolean isAdmin1 = UserDAO.checkAdmin(username.getText());
							MainInterface window = new MainInterface(isAdmin1);
							window.frame.setVisible(true);
							window.frame.setLocationRelativeTo(null);
							dispose();
						}
						else {
							JOptionPane.showMessageDialog(null,"username or password is not correct", "NON EXISTING ACCOUNT", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
			// Verify if text fields are empty
			private boolean emptyField() {
				if(username.getText().equals("")||password.getText().equals(""))
					return true;
				else return false;
			}
		});
		password.setBounds(128, 232, 238, 27);
		password.setColumns(10);
		contentPane.add(password);
		
		JButton login = new JButton("Login");
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//if fields are empty ( you can add type of conditions on data types)
				if(emptyField()) {
					JOptionPane.showMessageDialog(login,"username or password are empty", "missing information", JOptionPane.ERROR_MESSAGE);
				}	
				else {
					if (UserDAO.verifyLogin(username.getText(),password.getText())){
						boolean isAdmin1 = UserDAO.checkAdmin(username.getText());
						MainInterface window = new MainInterface(isAdmin1);
						window.frame.setVisible(true);
						window.frame.setLocationRelativeTo(null);
						dispose();
					}
					else {
						JOptionPane.showMessageDialog(login,"username or password is not correct", "NON EXISTING ACCOUNT", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			// Verify if text fields are empty
			private boolean emptyField() {
			if(username.getText().equals("")||password.getText().equals(""))
				return true;
			else return false;
			}
		});
		login.setBackground(viewSettings.SECONDARY);
		login.setBounds(171, 290, 149, 46);
		contentPane.add(login);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(viewSettings.SECONDARY);
		panel_1.setBounds(10, -16, 61, 487);
		contentPane.add(panel_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(456, -16, 61, 487);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		panel_2.setBackground(new Color(75, 0, 130));
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setLayout(null);
		panel_1_1.setBackground(new Color(224, 199, 242));
		panel_1_1.setBounds(424, -16, 61, 487);
		contentPane.add(panel_1_1);
	}
}
