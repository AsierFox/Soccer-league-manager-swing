package com.devdream.ui;

import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import com.devdream.controller.PlayerController;
import com.devdream.ui.custom.ScorersTable;
import javax.swing.JLabel;

public class TopScorersView extends View {
	private static final long serialVersionUID = 7905392397548194207L;
	
	//
	// Attributes
	private PlayerController playerController;
	
	private ScorersTable topScorersTable;
	
	private JButton returnButton;
	
	//
	// Constructors
	public TopScorersView() {
		super();
		getContentPane().setLayout(null);
		
		playerController = new PlayerController();
		
		loadUI();
		loadListeners();
		
		render();
	}

	//
	// Methods
	@Override
	protected void loadUI() {
		JLabel topScorersImageLabel = new JLabel(renderImage(ImagePath.PLAYER_TOP_SCORERS_ICON));
		topScorersImageLabel.setBounds(300, 11, 186, 119);
		getContentPane().add(topScorersImageLabel);
		
		JPanel topScorersPanel = new JPanel();
		topScorersPanel.setBorder(new TitledBorder(null, "Top scorers", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		topScorersPanel.setBounds(119, 131, 596, 356);
		getContentPane().add(topScorersPanel);
		topScorersPanel.setLayout(null);
		
		JScrollPane topScorersScrollPane = new JScrollPane();
		topScorersScrollPane.setBounds(73, 50, 436, 258);
		topScorersPanel.add(topScorersScrollPane);
		
		topScorersTable = new ScorersTable(new ArrayList<>(playerController.getTopScorers()));
		topScorersTable.update();
		topScorersScrollPane.setViewportView(topScorersTable);
		
		returnButton = new JButton("Return");
		returnButton.setIcon(renderImage(ImagePath.CANCEL_RETURN_ICON));
		returnButton.setHorizontalTextPosition(AbstractButton.LEFT);
		returnButton.setBounds(321, 498, 162, 51);
		getContentPane().add(returnButton);
	}
	
	@Override
	protected void loadListeners() {
		returnButton.addActionListener((e) -> changeView(this));
	}
}
