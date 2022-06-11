package view;

import java.awt.CardLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import interfaces.MainInterface;
import model.VehiculeTableModel;

public class AllVehiculeInfoPanel extends JPanel {
	// ATTRIBUTS DE LA CLASSE AVIP
			private CardLayout cl;
			private static VehiculeTableModel vTable ;
			private JTable vehiculeTable;
			private JLabel lbl_PLocation;
			private JLabel lbl_codeParking;
			private JLabel lbl_matricuule;
			private JLabel lbl_Marque;
			private JLabel lbl_type;
			private JLabel lbl_carburant;
			private JLabel lbl_kilometrage;
			private JLabel lblDMC;
			private JLabel lbl_disponibilite;



			//CONSTRUCTEUR DU PANEL
			public AllVehiculeInfoPanel(MainInterface mainInterface) {
				setBounds(new Rectangle(0, 0, 732, 547));
				this.cl = (CardLayout) mainInterface.getMainPanel().getLayout();
				this.setLayout(null);



			//BOUTON RETOUR AU MENU PRECEDENT
				JButton retour_btn = new JButton("Retour");
				retour_btn.setBackground(viewSettings.MAIN);
				retour_btn.setForeground(viewSettings.WHITE);
				retour_btn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						mainInterface.showOnMainPanel("vehicule");
					}
				});
				retour_btn.setBounds(576, 496, 146, 40);
				this.add(retour_btn);

				JLabel lblNewLabel = new JLabel("Matricule:");
				lblNewLabel.setBounds(52, 65, 130, 33);
				add(lblNewLabel);

				lbl_matricuule = new JLabel("................................");
				lbl_matricuule.setBorder(new LineBorder(viewSettings.MAIN, 2));
				lbl_matricuule.setHorizontalAlignment(SwingConstants.CENTER);
				lbl_matricuule.setBounds(318, 65, 369, 33);
				add(lbl_matricuule);

				lbl_Marque = new JLabel("................................");
				lbl_Marque.setBorder(new LineBorder(viewSettings.MAIN, 2));
				lbl_Marque.setHorizontalAlignment(SwingConstants.CENTER);
				lbl_Marque.setBounds(318, 109, 369, 33);
				add(lbl_Marque);

				JLabel lblNewLabel_2 = new JLabel("Marque:");
				lblNewLabel_2.setBounds(52, 109, 130, 33);
				add(lblNewLabel_2);

				lbl_type = new JLabel("................................");
				lbl_type.setBorder(new LineBorder(viewSettings.MAIN, 2));
				lbl_type.setHorizontalAlignment(SwingConstants.CENTER);
				lbl_type.setBounds(318, 153, 369, 33);
				add(lbl_type);

				JLabel lblNewLabel_3 = new JLabel("Type:");
				lblNewLabel_3.setBounds(52, 153, 130, 33);
				add(lblNewLabel_3);

				lbl_carburant = new JLabel("................................");
				lbl_carburant.setBorder(new LineBorder(viewSettings.MAIN, 2));
				lbl_carburant.setHorizontalAlignment(SwingConstants.CENTER);
				lbl_carburant.setBounds(318, 197, 369, 33);
				add(lbl_carburant);

				JLabel lblNewLabel_4 = new JLabel("Carburant:");
				lblNewLabel_4.setBounds(52, 197, 130, 33);
				add(lblNewLabel_4);

				lbl_kilometrage = new JLabel("................................");
				lbl_kilometrage.setBorder(new LineBorder(viewSettings.MAIN, 2));
				lbl_kilometrage.setHorizontalAlignment(SwingConstants.CENTER);
				lbl_kilometrage.setBounds(318, 241, 369, 33);
				add(lbl_kilometrage);

				JLabel lblNewLabel_5 = new JLabel("Kilom\u00E9trage:");
				lblNewLabel_5.setBounds(52, 241, 130, 33);
				add(lblNewLabel_5);

				lblDMC = new JLabel("................................");
				lblDMC.setBorder(new LineBorder(viewSettings.MAIN, 2));
				lblDMC.setHorizontalAlignment(SwingConstants.CENTER);
				lblDMC.setBounds(318, 285, 369, 33);
				add(lblDMC);

				JLabel lblNewLabel_6 = new JLabel("Date de mise en circulation:");
				lblNewLabel_6.setBounds(52, 285, 235, 33);
				add(lblNewLabel_6);

				lbl_codeParking = new JLabel("................................");
				lbl_codeParking.setBorder(new LineBorder(viewSettings.MAIN, 2));
				lbl_codeParking.setHorizontalAlignment(SwingConstants.CENTER);
				lbl_codeParking.setBounds(318, 329, 369, 33);
				add(lbl_codeParking);

				JLabel lblNewLabel_7 = new JLabel("Code parking:");
				lblNewLabel_7.setBounds(52, 329, 130, 33);
				add(lblNewLabel_7);

				lbl_PLocation = new JLabel("................................");
				lbl_PLocation.setBorder(new LineBorder(viewSettings.MAIN, 2));
				lbl_PLocation.setHorizontalAlignment(SwingConstants.CENTER);
				lbl_PLocation.setBounds(318, 377, 369, 33);
				add(lbl_PLocation);

				JLabel lblNewLabel_8 = new JLabel("Prix location:");
				lblNewLabel_8.setBounds(52, 377, 130, 33);
				add(lblNewLabel_8);

				JLabel lblNewLabel_8_1 = new JLabel("Disponible:");
				lblNewLabel_8_1.setBounds(52, 421, 130, 33);
				add(lblNewLabel_8_1);

				lbl_disponibilite = new JLabel("................................");
				lbl_disponibilite.setBorder(new LineBorder(viewSettings.MAIN, 2));
				lbl_disponibilite.setHorizontalAlignment(SwingConstants.CENTER);
				lbl_disponibilite.setBounds(318, 421, 369, 33);
				add(lbl_disponibilite);


			}
			public JLabel getLbl_PLocation() {
				return lbl_PLocation;
			}

			public JLabel getLbl_codeParking() {
				return lbl_codeParking;
			}

			public JLabel getLbl_matricuule() {
				return lbl_matricuule;
			}

			public JLabel getLbl_Marque() {
				return lbl_Marque;
			}

			public JLabel getLbl_type() {
				return lbl_type;
			}

			public JLabel getLbl_carburant() {
				return lbl_carburant;
			}

			public JLabel getLbl_kilometrage() {
				return lbl_kilometrage;
			}

			public JLabel getLblDMC() {
				return lblDMC;
			}

			public JLabel getLbl_disponibilite() {
				return lbl_disponibilite;
			}
}
