package com.devdream.ui;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import com.devdream.controller.SeasonGameController;
import com.devdream.db.DBConnectionManager;
import com.devdream.model.Performance;
import com.devdream.model.Team;
import com.devdream.ui.custom.Alert;
import com.devdream.ui.custom.MyComboBox;
import com.devdream.ui.custom.PerformancesTable;

public class PerformanceSearchView extends View {
	private static final long serialVersionUID = 2353183308699502451L;
	
	//
	// Attributes
	private SeasonGameController seasonGameController;
	
	private PerformancesTable performancesTable;
	
	private MyComboBox<String, String> performanceComboBox;
	private MyComboBox<String, String> orderComboBox;
	
	private JButton searchButton;
	private JButton returnButton;
	
	//
	// Constructors
	public PerformanceSearchView() {
		super();
		getContentPane().setLayout(null);
		
		seasonGameController = new SeasonGameController();
		
		loadUI();
		loadListeners();
		
		render();
	}

	//
	// Methods
	@Override
	protected void loadUI() {
		JPanel forPerformanceSearchPanel = new JPanel();
		forPerformanceSearchPanel.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		forPerformanceSearchPanel.setBounds(52, 96, 717, 394);
		getContentPane().add(forPerformanceSearchPanel);
		forPerformanceSearchPanel.setLayout(null);
		
		JPanel searchPanel = new JPanel();
		searchPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		searchPanel.setBounds(155, 23, 459, 48);
		forPerformanceSearchPanel.add(searchPanel);
		searchPanel.setLayout(null);
		
		JLabel forSearchByLabel = new JLabel("Search by");
		forSearchByLabel.setBounds(10, 15, 67, 14);
		searchPanel.add(forSearchByLabel);
		
		searchButton = new JButton("Search");
		searchButton.setIcon(renderImage(ImagePath.SEARCH_ICON));
		searchButton.setHorizontalTextPosition(AbstractButton.LEFT);
		searchButton.setBounds(338, 11, 111, 23);
		searchPanel.add(searchButton);
		
		performanceComboBox = new MyComboBox<>(Performance.sPerformances);
		performanceComboBox.setBounds(111, 11, 89, 20);
		searchPanel.add(performanceComboBox);
		
		orderComboBox = new MyComboBox<>(seasonGameController.getOrderMap());
		orderComboBox.setBounds(219, 11, 109, 20);
		searchPanel.add(orderComboBox);
		
		JScrollPane performancesScrollPane = new JScrollPane();
		performancesScrollPane.setBounds(22, 82, 671, 282);
		forPerformanceSearchPanel.add(performancesScrollPane);
		
		performancesTable = new PerformancesTable();
		performancesScrollPane.setViewportView(performancesTable);
		
		returnButton = new JButton("Return");
		returnButton.setIcon(renderImage(ImagePath.CANCEL_RETURN_ICON));
		returnButton.setHorizontalTextPosition(AbstractButton.LEFT);
		returnButton.setBounds(308, 501, 187, 53);
		getContentPane().add(returnButton);
		
		JLabel statsTitleImageLabel = new JLabel(renderImage(ImagePath.STATISTICS_TITLE_ICON));
		statsTitleImageLabel.setBounds(316, 0, 157, 103);
		getContentPane().add(statsTitleImageLabel);
	}
	
	@Override
	protected void loadListeners() {
		searchButton.addActionListener((e) -> {
			String selPerformance = performanceComboBox.getSelected();
			String selOrder = orderComboBox.getSelected();
			
			try {
				ArrayList<Team> teams = new ArrayList<>();
				if (selPerformance.equals(Performance.sPerformances.get("Total goals"))) {
					teams = seasonGameController.searchTeamsByTotalGoals(selOrder);
				} else {
					teams = seasonGameController.searchTeamsByPerformance(selPerformance, selOrder);
				}
				performancesTable.setSeachBy(selPerformance);
				performancesTable.setTeams(teams);
				
				if (teams.isEmpty()) Alert.showInfo(this, "There are no teams with performances!");
			} catch (SQLException err) {
				Alert.showError(this, DBConnectionManager.CONNECT_ERROR_MSG);
			}
		});
		
		returnButton.addActionListener((e) -> changeView(this));
	}

}
