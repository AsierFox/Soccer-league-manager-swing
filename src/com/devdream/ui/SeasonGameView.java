package com.devdream.ui;

import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.devdream.controller.SeasonGameController;
import com.devdream.exception.InvalidInputException;
import com.devdream.model.Performance;
import com.devdream.model.Scorer;
import com.devdream.model.SeasonGame;
import com.devdream.model.Team;
import com.devdream.ui.custom.Alert;
import com.devdream.ui.custom.DateObserverTextField;
import com.devdream.ui.custom.MyList;
import com.qt.datapicker.DatePicker;

public class SeasonGameView extends View {
	private static final long serialVersionUID = 4061083358990967716L;
	
	//
	// Attributes
	private SeasonGameController seasonGameController;
	
	private JPanel gamePanel;
	private JPanel homeTeamPanel;
	private JPanel awayTeamPanel;
	
	private DateObserverTextField gameDateObserverTextField;
	
	private JTextField homeTeamGoalsTextField;
	private JTextField homeTeamShotsTextField;
	private JTextField homeTeamPassesTextField;
	private JTextField homeTeamFoulsTextField;
	private JTextField homeTeamOffsidesTextField;
	private JTextField homeTeamCornersTextField;
	
	private JTextField awayTeamGoalsTextField;
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
	public SeasonGameView(SeasonGame selSeasonGame) {
		super();
		getContentPane().setLayout(null);
		
		seasonGameController = new SeasonGameController(selSeasonGame);
		
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
		gamePanel.setBounds(28, 48, 759, 427);
		getContentPane().add(gamePanel);
		gamePanel.setLayout(null);
		
		JLabel forSeasonTitleLabel = new JLabel("Season");
		forSeasonTitleLabel.setBounds(301, 23, 46, 14);
		getContentPane().add(forSeasonTitleLabel);

		JPanel gameDatePanel = new JPanel();
		gameDatePanel.setLayout(null);
		gameDatePanel.setBounds(225, 23, 236, 39);
		gamePanel.add(gameDatePanel);
		
		JLabel forGameDateLabel = new JLabel("Game date");
		forGameDateLabel.setBounds(157, 36, 69, 14);
		gamePanel.add(forGameDateLabel);
		
		gameDateObserverTextField = new DateObserverTextField();
		gameDateObserverTextField.setText(seasonGameController.getSeason().getDate());
		gameDateObserverTextField.setEditable(false);
		gameDateObserverTextField.setBounds(10, 11, 98, 20);
		gameDatePanel.add(gameDateObserverTextField);

		gameDateButton = new JButton("Game date");
		gameDateButton.setBounds(118, 10, 108, 23);
		gameDatePanel.add(gameDateButton);
		
		loadHomeTeam(seasonGameController.getHomeTeam());
		loadAwayTeam(seasonGameController.getAwayTeam());
		
		// TODO Scorers!!
//		loadScorers(seasonGameController.isUserHomeTeam() ? homeTeamPanel : awayTeamPanel);
		
		//TODO DESIGN
		/*** */
//		loadScorers(awayTeamPanel);
		/*** */
		
		JLabel forGoalLabel = new JLabel("Goals");
		forGoalLabel.setBounds(358, 372, 46, 14);
		gamePanel.add(forGoalLabel);
		
		JLabel forShotsLabel = new JLabel("Shots");
		forShotsLabel.setBounds(358, 172, 46, 14);
		gamePanel.add(forShotsLabel);
		
		JLabel forPassesLabel = new JLabel("Passes");
		forPassesLabel.setBounds(358, 206, 46, 14);
		gamePanel.add(forPassesLabel);
		
		JLabel forFoulsLabel = new JLabel("Fouls");
		forFoulsLabel.setBounds(358, 237, 46, 14);
		gamePanel.add(forFoulsLabel);
		
		JLabel forOffsidesLabel = new JLabel("Offsides");
		forOffsidesLabel.setBounds(358, 272, 46, 14);
		gamePanel.add(forOffsidesLabel);
		
		JLabel forCornersLabel = new JLabel("Corners");
		forCornersLabel.setBounds(358, 298, 46, 14);
		gamePanel.add(forCornersLabel);
		
		returnButton = new JButton("Return");
		returnButton.setIcon(renderImage(ImagePath.CANCEL_RETURN_ICON));
		returnButton.setHorizontalTextPosition(AbstractButton.LEFT);
		returnButton.setBounds(429, 509, 122, 45);
		getContentPane().add(returnButton);
		
		saveButton = new JButton("Save");
		saveButton.setIcon(renderImage(ImagePath.CREATE_ICON));
		saveButton.setHorizontalTextPosition(AbstractButton.LEFT);
		saveButton.setBounds(273, 503, 122, 57);
		getContentPane().add(saveButton);
	}
	
	private void loadHomeTeam(Team homeTeam) {
		homeTeamPanel = new JPanel();
		homeTeamPanel.setBorder(new TitledBorder(null, "Home team", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		homeTeamPanel.setBounds(29, 120, 309, 285);
		gamePanel.add(homeTeamPanel);
		homeTeamPanel.setLayout(null);
		
		JLabel homeTeamLabel = new JLabel(homeTeam.getName());
		homeTeamLabel.setBounds(21, 26, 104, 14);
		homeTeamPanel.add(homeTeamLabel);

		homeTeamGoalsTextField = new JTextField(Integer.toString(homeTeam.getScore()));
		homeTeamGoalsTextField.setEditable(false);
		homeTeamGoalsTextField.setBounds(175, 254, 44, 20);
		homeTeamPanel.add(homeTeamGoalsTextField);
		
		homeTeamShotsTextField = new JTextField(Integer.toString(homeTeam.getShots()));
		homeTeamShotsTextField.setBounds(175, 52, 44, 20);
		homeTeamPanel.add(homeTeamShotsTextField);
		
		homeTeamPassesTextField = new JTextField(Integer.toString(homeTeam.getPasses()));
		homeTeamPassesTextField.setBounds(175, 83, 44, 20);
		homeTeamPanel.add(homeTeamPassesTextField);
		
		homeTeamFoulsTextField = new JTextField(Integer.toString(homeTeam.getFouls()));
		homeTeamFoulsTextField.setBounds(175, 114, 44, 20);
		homeTeamPanel.add(homeTeamFoulsTextField);
		
		homeTeamOffsidesTextField = new JTextField(Integer.toString(homeTeam.getOffsides()));
		homeTeamOffsidesTextField.setBounds(175, 145, 44, 20);
		homeTeamPanel.add(homeTeamOffsidesTextField);
		
		homeTeamCornersTextField = new JTextField(Integer.toString(homeTeam.getCorners()));
		homeTeamCornersTextField.setBounds(175, 176, 44, 20);
		homeTeamPanel.add(homeTeamCornersTextField);
	}
	
	private void loadAwayTeam(Team awayTeam) {
		awayTeamPanel = new JPanel();
		awayTeamPanel.setLayout(null);
		awayTeamPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Away team", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		awayTeamPanel.setBounds(427, 120, 309, 285);
		gamePanel.add(awayTeamPanel);
		
		JLabel awayTeamLabel = new JLabel(awayTeam.getName());
		awayTeamLabel.setBounds(144, 45, 104, 14);
		awayTeamPanel.add(awayTeamLabel);
		
		awayTeamGoalsTextField = new JTextField(Integer.toString(awayTeam.getScore()));
		awayTeamGoalsTextField.setEditable(false);
		awayTeamGoalsTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		awayTeamGoalsTextField.setBounds(10, 254, 44, 20);
		awayTeamPanel.add(awayTeamGoalsTextField);
		
		awayTeamShotsTextField = new JTextField(Integer.toString(awayTeam.getShots()));
		awayTeamShotsTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		awayTeamShotsTextField.setBounds(10, 55, 44, 20);
		awayTeamPanel.add(awayTeamShotsTextField);
		
		awayTeamPassesTextField = new JTextField(Integer.toString(awayTeam.getPasses()));
		awayTeamPassesTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		awayTeamPassesTextField.setBounds(10, 86, 44, 20);
		awayTeamPanel.add(awayTeamPassesTextField);
		
		awayTeamFoulsTextField = new JTextField(Integer.toString(awayTeam.getFouls()));
		awayTeamFoulsTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		awayTeamFoulsTextField.setBounds(10, 117, 44, 20);
		awayTeamPanel.add(awayTeamFoulsTextField);
		
		awayTeamOffsidesTextField = new JTextField(Integer.toString(awayTeam.getOffsides()));
		awayTeamOffsidesTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		awayTeamOffsidesTextField.setBounds(10, 148, 44, 20);
		awayTeamPanel.add(awayTeamOffsidesTextField);
		
		awayTeamCornersTextField = new JTextField(Integer.toString(awayTeam.getCorners()));
		awayTeamCornersTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		awayTeamCornersTextField.setBounds(10, 179, 44, 20);
		awayTeamPanel.add(awayTeamCornersTextField);
	}
	
	private void loadScorers(JPanel teamPanel) {
		JScrollPane scorersScrollPane = new JScrollPane();
		scorersScrollPane.setBounds(132, 179, 116, 95);
		
		JLabel scorersLabel = new JLabel("Scorers");
		scorersLabel.setBounds(130, 151, 61, 14);
		teamPanel.add(scorersLabel);
		
		ArrayList<Scorer> scorers = seasonGameController.isUserHomeTeam()
				? seasonGameController.getHomeTeam().getScorers()
				: seasonGameController.getAwayTeam().getScorers();
		MyList<Scorer> scorersList = new MyList<>(scorers);
		scorersScrollPane.setViewportView(scorersList);
		teamPanel.add(scorersScrollPane);
	}
	
	@Override
	protected void loadListeners() {
		gameDateButton.addActionListener((e) -> {
	        DatePicker dp = new DatePicker(gameDateObserverTextField);
	        dp.parseDate(gameDateObserverTextField.getText());
	        dp.start(gameDateObserverTextField);
		});
		
		saveButton.addActionListener((e) -> {
			try {
				String gameDate = gameDateObserverTextField.getText();
				Performance homeTeamPerformance = new Performance(Integer.parseInt(homeTeamGoalsTextField.getText()),
						Integer.parseInt(homeTeamShotsTextField.getText()),
						Integer.parseInt(homeTeamPassesTextField.getText()),
						Integer.parseInt(homeTeamFoulsTextField.getText()),
						Integer.parseInt(homeTeamOffsidesTextField.getText()),
						Integer.parseInt(homeTeamCornersTextField.getText()));
				Performance awayTeamPerformance = new Performance(Integer.parseInt(awayTeamGoalsTextField.getText()),
						Integer.parseInt(awayTeamShotsTextField.getText()),
						Integer.parseInt(awayTeamPassesTextField.getText()),
						Integer.parseInt(awayTeamFoulsTextField.getText()),
						Integer.parseInt(awayTeamOffsidesTextField.getText()),
						Integer.parseInt(awayTeamCornersTextField.getText()));
				
				seasonGameController.updateStats(gameDate, homeTeamPerformance, awayTeamPerformance);
				
				Alert.showInfo(this, "Statistics updated!");
			} catch(InvalidInputException err){
				Alert.showError(this, err.getMessage());
			} catch(SQLException err) {
				err.printStackTrace();
				Alert.showError(this, "Unable to connect to the database!");
			} catch(NumberFormatException err) {
				Alert.showError(this, "All the stats values must be numeric!");
			}
		});
		
		returnButton.addActionListener((e) -> changeView(this));
	}

}
