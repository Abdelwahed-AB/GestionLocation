package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import controller.ClientController;
import model.Client;

public class AfficherClientPanel extends JPanel {
	private CardLayout cl;
	private JTable table_1;

	public AfficherClientPanel(JPanel panel, Client client) {
		// on a besion de cl pour revenir au menu principal
		this.cl = (CardLayout) panel.getLayout();
		this.setLayout(null);
		this.setBounds(0, 0, 766, 598);
		
		Color mainColor = new Color(75, 0, 130);
		Color secondaryColor = new Color(224, 199, 242);

		JLabel codeClientlbl = new JLabel("Code Client");
		codeClientlbl.setHorizontalAlignment(SwingConstants.CENTER);
		codeClientlbl.setBounds(41, 69, 156, 32);
		this.add(codeClientlbl);

		JLabel nomClientlbl = new JLabel("Nom Client");
		nomClientlbl.setHorizontalAlignment(SwingConstants.CENTER);
		nomClientlbl.setBounds(41, 102, 156, 32);
		this.add(nomClientlbl);

		JLabel prenomClientlbl = new JLabel("Prenom Client");
		prenomClientlbl.setHorizontalAlignment(SwingConstants.CENTER);
		prenomClientlbl.setBounds(41, 134, 156, 32);
		this.add(prenomClientlbl);

		JLabel teleClientlbl = new JLabel("Num Tel Client");
		teleClientlbl.setHorizontalAlignment(SwingConstants.CENTER);
		teleClientlbl.setBounds(41, 205, 156, 32);
		this.add(teleClientlbl);

		JLabel adresseClientlbl = new JLabel("Adresse Client");
		adresseClientlbl.setHorizontalAlignment(SwingConstants.CENTER);
		adresseClientlbl.setBounds(41, 170, 156, 32);
		this.add(adresseClientlbl);

		JButton btnNewButton = new JButton("Retour");
		btnNewButton.setForeground(viewSettings.WHITE);
		btnNewButton.setBackground(viewSettings.MAIN);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(panel, "client");
			}
		});
		btnNewButton.setBounds(576, 477, 124, 45);
		this.add(btnNewButton);

		//afficher l'image de client
		JLabel imageClient = new JLabel();
		imageClient.setBounds(518, 69, 179, 217);
		ClientController.prepareImage(client.getImage(), imageClient);
		this.add(imageClient);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(51, 308, 649, 158);
		add(scrollPane);

		// table de voiture lou�e
		table_1 = new JTable();
		table_1.setBackground(secondaryColor);
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.addColumn("Matricule");
		dtm.addColumn("Marque");
		dtm.addColumn("Date de depart");
		dtm.addColumn("Date de retour");
		table_1.setModel(dtm);
		scrollPane.setViewportView(table_1);
		
		JLabel vehiculeAllouee = new JLabel("Les voitures Allouees");
		vehiculeAllouee.setHorizontalAlignment(SwingConstants.CENTER);
		vehiculeAllouee.setBounds(58, 267, 156, 32);
		add(vehiculeAllouee);
		
		JLabel codeClient = new JLabel(client.getCodeClient()+"");
		codeClient.setHorizontalAlignment(SwingConstants.CENTER);
		codeClient.setBounds(229, 69, 279, 32);
		add(codeClient);
		
		JLabel nomClient = new JLabel(client.getNomClient());
		nomClient.setHorizontalAlignment(SwingConstants.CENTER);
		nomClient.setBounds(229, 102, 279, 32);
		add(nomClient);
		
		JLabel prenomClient = new JLabel(client.getPrenomClient());
		prenomClient.setHorizontalAlignment(SwingConstants.CENTER);
		prenomClient.setBounds(229, 134, 279, 32);
		add(prenomClient);
		
		JLabel adresseClient = new JLabel(client.getAddresseClient());
		adresseClient.setHorizontalAlignment(SwingConstants.CENTER);
		adresseClient.setBounds(229, 170, 279, 32);
		add(adresseClient);
		
		JLabel numTelClient = new JLabel(client.getNumTelClient());
		numTelClient.setHorizontalAlignment(SwingConstants.CENTER);
		numTelClient.setBounds(229, 205, 279, 32);
		add(numTelClient);

	}
}
