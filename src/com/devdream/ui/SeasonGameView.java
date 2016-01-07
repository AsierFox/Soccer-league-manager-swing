package com.devdream.ui;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.devdream.controller.SeasonGameController;
import com.devdream.controller.TeamController;
import com.devdream.model.Season;
import com.devdream.model.Team;
import com.devdream.ui.custom.DateObserverTextField;
import com.qt.datapicker.DatePicker;

public class SeasonGameView extends View {
	private static final long serialVersionUID = 4061083358990967716L;
	
	//
	// Attributes
	private TeamController teamController;
	private SeasonGameController seasonGameController;
	
	private JPanel gamePanel;
	
	private DateObserverTextField gameDateObserverTextField;
	
	private JTextField homeTeamGoalsTextField;
	private JTextField homeTeamPossessionTextField;
	private JTextField homeTeamShotsTextField;
	private JTextField homeTeamPassesTextField;
	private JTextField homeTeamFoulsTextField;
	private JTextField homeTeamOffsidesTextField;
	private JTextField homeTeamCornersTextField;
	
	private JTextField awayTeamGoalsTextField;
	private JTextField awayTeamPossessionTextField;
	private JTextField awayTeamShotsTextField;
	private JTextField awayTeamPassesTextField;
	private JTextField awayTeamFoulsTextField;
	private JTextField awayTeamOffsidesTextField;
	private JTextField awayTeamCornersTextField;

	private JButton gameDateButton;
	private JButton returnButton;
	private JButton saveButton;
	
	//
	// Constructors
	public SeasonGameView(TeamController teamController, Season selectedSeason) { // TODO Controller for statistics and that
		super();
		getContentPane().setLayout(null);
		
		this.teamController = teamController; // TODO I really need this on this class? (ONLY PASS THE USER TEAM)
		seasonGameController = new SeasonGameController(selectedSeason);
		
		loadUI();
		loadListeners();
		
		render();
	}
	
	//
	// Methods
	@Override
	protected void loadUI() {
		gamePanel = new JPanel();
		gamePanel.setBorder(new TitledBorder(null, "Game", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		gamePanel.setBounds(69, 69, 728, 377);
		getContentPane().add(gamePanel);
		gamePanel.setLayout(null);
		
		JLabel forSeasonTitleLabel = new JLabel("Season");
		forSeasonTitleLabel.setBounds(231, 29, 46, 14);
		getContentPane().add(forSeasonTitleLabel);

		JPanel gameDatePanel = new JPanel();
		gameDatePanel.setLayout(null);
		gameDatePanel.setBounds(225, 23, 236, 39);
		gamePanel.add(gameDatePanel);
		
		JLabel forGameDateLabel = new JLabel("Game date");
		forGameDateLabel.setBounds(157, 36, 69, 14);
		gamePanel.add(forGameDateLabel);
		
		JLabel seasonDateLabel = new JLabel("DATE");
		seasonDateLabel.setBounds(314, 29, 86, 14);
		getContentPane().add(seasonDateLabel);
		
		gameDateObserverTextField = new DateObserverTextField();
		gameDateObserverTextField.setEditable(false);
		gameDateObserverTextField.setBounds(10, 11, 98, 20);
		gameDatePanel.add(gameDateObserverTextField);

		gameDateButton = new JButton("Game date");
		gameDateButton.setBounds(118, 10, 108, 23);
		gameDatePanel.add(gameDateButton);
		
		loadHomeTeam(seasonGameController.getHomeTeam());
		loadAwayTeam(seasonGameController.getAwayTeam());
		
		JLabel forGoalLabel = new JLabel("Goals");
		forGoalLabel.setBounds(296, 163, 46, 14);
		gamePanel.add(forGoalLabel);
		
		JLabel forShotsLabel = new JLabel("Shots");
		forShotsLabel.setBounds(296, 224, 46, 14);
		gamePanel.add(forShotsLabel);
		
		JLabel forPassesLabel = new JLabel("Passes");
		forPassesLabel.setBounds(296, 249, 46, 14);
		gamePanel.add(forPassesLabel);
		
		JLabel forFoulsLabel = new JLabel("Fouls");
		forFoulsLabel.setBounds(296, 283, 46, 14);
		gamePanel.add(forFoulsLabel);
		
		JLabel forOffsidesLabel = new JLabel("Offsides");
		forOffsidesLabel.setBounds(296, 308, 46, 14);
		gamePanel.add(forOffsidesLabel);
		
		JLabel forCornersLabel = new JLabel("Corners");
		forCornersLabel.setBounds(296, 336, 46, 14);
		gamePanel.add(forCornersLabel);
		
		JLabel forPossessionLabel = new JLabel("Possesion %");
		forPossessionLabel.setBounds(281, 199, 70, 14);
		gamePanel.add(forPossessionLabel);
		
		returnButton = new JButton("Return");
		returnButton.setBounds(437, 492, 122, 45);
		getContentPane().add(returnButton);
		
		saveButton = new JButton("Save");
		saveButton.setBounds(278, 486, 122, 57);
		getContentPane().add(saveButton);
	}
	
	@Override
	protected void loadListeners() {
		gameDateButton.addActionListener((e) -> {
	        DatePicker dp = new DatePicker(gameDateObserverTextField);
	        dp.parseDate(gameDateObserverTextField.getText());
	        dp.start(gameDateObserverTextField);
		});
		
		returnButton.addActionListener((e) -> changeView(this));
	}
	
	private void loadHomeTeam(Team homeTeam) {
		JPanel homeTeampanel = new JPanel();
		homeTeampanel.setBorder(new TitledBorder(null, "Home team", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		homeTeampanel.setBounds(29, 120, 242, 246);
		gamePanel.add(homeTeampanel);
		homeTeampanel.setLayout(null);
		
		JLabel homeTeamLabel = new JLabel(homeTeam.getName());
		homeTeamLabel.setBounds(21, 29, 104, 14);
		homeTeampanel.add(homeTeamLabel);

		homeTeamGoalsTextField = new JTextField(Integer.toString(homeTeam.getGoals()));
		homeTeamGoalsTextField.setBounds(175, 41, 44, 20);
		homeTeampanel.add(homeTeamGoalsTextField);
		
		homeTeamPossessionTextField = new JTextField(Float.toString(homeTeam.getPossession()));
		homeTeamPossessionTextField.setBounds(175, 72, 44, 20);
		homeTeampanel.add(homeTeamPossessionTextField);
		
		homeTeamShotsTextField = new JTextField(Integer.toString(homeTeam.getShots()));
		homeTeamShotsTextField.setBounds(175, 103, 44, 20);
		homeTeampanel.add(homeTeamShotsTextField);
		
		homeTeamPassesTextField = new JTextField(Integer.toString(homeTeam.getPasses()));
		homeTeamPassesTextField.setBounds(175, 134, 44, 20);
		homeTeampanel.add(homeTeamPassesTextField);
		
		homeTeamFoulsTextField = new JTextField(Integer.toString(homeTeam.getFouls()));
		homeTeamFoulsTextField.setBounds(175, 165, 44, 20);
		homeTeampanel.add(homeTeamFoulsTextField);
		
		homeTeamOffsidesTextField = new JTextField(Integer.toString(homeTeam.getOffsides()));
		homeTeamOffsidesTextField.setBounds(175, 196, 44, 20);
		homeTeampanel.add(homeTeamOffsidesTextField);
		
		homeTeamCornersTextField = new JTextField(Integer.toString(homeTeam.getCorners()));
		homeTeamCornersTextField.setBounds(175, 215, 44, 20);
		homeTeampanel.add(homeTeamCornersTextField);
	}
	
	private void loadAwayTeam(Team awayTeam) {
		JPanel awayTeamPanel = new JPanel();
		awayTeamPanel.setLayout(null);
		awayTeamPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Away team", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		awayTeamPanel.setBounds(361, 107, 357, 259);
		gamePanel.add(awayTeamPanel);
		
		JLabel awayTeamLabel = new JLabel(awayTeam.getName());
		awayTeamLabel.setBounds(144, 45, 104, 14);
		awayTeamPanel.add(awayTeamLabel);
		
		awayTeamGoalsTextField = new JTextField(Integer.toString(awayTeam.getGoals()));
		awayTeamGoalsTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		awayTeamGoalsTextField.setBounds(10, 54, 44, 20);
		awayTeamPanel.add(awayTeamGoalsTextField);
		
		awayTeamPossessionTextField = new JTextField(Float.toString(awayTeam.getPossession()));
		awayTeamPossessionTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		awayTeamPossessionTextField.setBounds(10, 85, 44, 20);
		awayTeamPanel.add(awayTeamPossessionTextField);
		
		awayTeamShotsTextField = new JTextField(Integer.toString(awayTeam.getShots()));
		awayTeamShotsTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		awayTeamShotsTextField.setBounds(10, 116, 44, 20);
		awayTeamPanel.add(awayTeamShotsTextField);
		
		awayTeamPassesTextField = new JTextField(Integer.toString(awayTeam.getPasses()));
		awayTeamPassesTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		awayTeamPassesTextField.setBounds(10, 147, 44, 20);
		awayTeamPanel.add(awayTeamPassesTextField);
		
		awayTeamFoulsTextField = new JTextField(Integer.toString(awayTeam.getFouls()));
		awayTeamFoulsTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		awayTeamFoulsTextField.setBounds(10, 178, 44, 20);
		awayTeamPanel.add(awayTeamFoulsTextField);
		
		awayTeamOffsidesTextField = new JTextField(Integer.toString(awayTeam.getOffsides()));
		awayTeamOffsidesTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		awayTeamOffsidesTextField.setBounds(10, 209, 44, 20);
		awayTeamPanel.add(awayTeamOffsidesTextField);
		
		awayTeamCornersTextField = new JTextField(Integer.toString(awayTeam.getCorners()));
		awayTeamCornersTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		awayTeamCornersTextField.setBounds(10, 228, 44, 20);
		awayTeamPanel.add(awayTeamCornersTextField);
	}

}
