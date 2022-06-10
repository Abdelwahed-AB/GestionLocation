package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import controller.SanctionController;
import model.SanctionTableModel;
import java.awt.Color;
import javax.swing.ListSelectionModel;
import javax.swing.JTextField;

public class SanctionPanel extends JPanel {
	
	private JTable sanctions_table;
	private SanctionTableModel table_model = new SanctionTableModel();
	
	private SanctionController cont;
	private JLabel warning_lbl;
	private JTextField sanction_search;

	/**
	 * Constructeur par defaut
	 */
	public SanctionPanel() {
		init();
	}
	
	private void init() {
		this.setLayout(null);
		
		JScrollPane sanction_scroll = new JScrollPane();
		sanction_scroll.setBounds(23, 57, 483, 462);
		this.add(sanction_scroll);
		
		sanctions_table = new JTable(table_model);
		sanctions_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		sanctions_table.setSelectionBackground(viewSettings.SECONDARY);
		sanction_scroll.setViewportView(sanctions_table);
		
		JLabel sanctions_lbl = new JLabel("Les clients sanctionn\u00E9es :");
		sanctions_lbl.setBounds(23, 22, 189, 25);
		this.add(sanctions_lbl);
		
		sanction_search = new JTextField();
		sanction_search.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(!sanction_search.getText().isEmpty())
					cont.findClient(sanction_search.getText());
				else
					cont.Actualiser();
			}
		});
		sanction_search.setBounds(221, 22, 285, 25);
		add(sanction_search);
		sanction_search.setColumns(10);
		
		JButton actualiser_btn = new JButton("Actualiser");
		actualiser_btn.setBackground(viewSettings.SECONDARY);
		actualiser_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cont.Actualiser();
				warning_lbl.setText("");
			}
		});
		actualiser_btn.setBounds(522, 463, 193, 56);
		this.add(actualiser_btn);
		
		JButton plusInfo_btn = new JButton("Plus d'informations");
		plusInfo_btn.setBounds(522, 57, 193, 56);
		plusInfo_btn.setBackground(viewSettings.SECONDARY);
		
		plusInfo_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				warning_lbl.setText("");
				cont.moreInfo();
			}
		});
		this.add(plusInfo_btn);
		
		warning_lbl = new JLabel("");
		warning_lbl.setForeground(Color.RED);
		warning_lbl.setHorizontalAlignment(SwingConstants.CENTER);
		warning_lbl.setBounds(525, 204, 190, 88);
		this.add(warning_lbl);
		
		JButton regler_btn = new JButton("Regler");
		regler_btn.setBackground(viewSettings.SECONDARY);
		regler_btn.setBounds(522, 123, 193, 56);
		regler_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				warning_lbl.setText("");
				cont.reglerSanction();
			}
		});
		add(regler_btn);
	}
	
	public void setSanctionController(SanctionController sCon) {
		this.cont = sCon;
	}
	
	public SanctionTableModel getSanctionTableModel() {
		return this.table_model;
	}
	
	public JTable getSanctionTable() {
		return this.sanctions_table;
	}
	
	public JLabel getWarningLabel() {
		return this.warning_lbl;
	}
}
