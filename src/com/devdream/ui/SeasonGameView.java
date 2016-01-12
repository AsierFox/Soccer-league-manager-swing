package com.devdream.ui;

import java.awt.Color;
import java.sql.SQLException;

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
import com.devdream.exception.NotTableItemSelectedException;
import com.devdream.model.Performance;
import com.devdream.model.Sanctioned;
import com.devdream.model.Scorer;
import com.devdream.model.SeasonGame;
import com.devdream.model.Team;
import com.devdream.ui.custom.Alert;
import com.devdream.ui.custom.DateObserverTextField;
import com.devdream.ui.custom.ScorersList;
import com.devdream.ui.custom.SanctionsTable;
import com.qt.datapicker.DatePicker;

/**
 * The view that shows the performances, such as the information of
 * a game of the season.
 * 
 * @author Asier Gonzalez
 */
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

	private SanctionsTable sanctionsTable;
	private ScorersList scorersList;

	private JButton gameDateButton;
	private JButton addSactionButton;
	private JButton addScorerButton;
	private JButton returnButton;
	private JButton saveButton;
	private JButton removeSanctionButton;
	private JButton removeScorerButton;
	private JLabel forHomeTeamName;
	private JLabel forAwayTeamName;
	
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
		gamePanel.setBounds(28, 48, 759, 444);
		getContentPane().add(gamePanel);
		gamePanel.setLayout(null);
		
		JLabel forSeasonTitleLabel = new JLabel("Season game");
		forSeasonTitleLabel.setFont(FontStyle.TITLE_FONT);
		forSeasonTitleLabel.setBounds(301, 23, 115, 14);
		getContentPane().add(forSeasonTitleLabel);

		JPanel gameDatePanel = new JPanel();
		gameDatePanel.setLayout(null);
		gameDatePanel.setBounds(265, 21, 236, 39);
		gamePanel.add(gameDatePanel);
		
		JLabel forGameDateLabel = new JLabel("Game date");
		forGameDateLabel.setFont(FontStyle.BOLD_FONT);
		forGameDateLabel.setBounds(163, 34, 92, 14);
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
		
		// TODO DESIGN
//		loadUserTeamPlayers(homeTeamPanel);
//		loadUserTeamPlayers(awayTeamPanel);
		
		loadUserTeamPlayers(seasonGameController.isUserHomeTeam() ? homeTeamPanel : awayTeamPanel);
		
		JLabel forGoalLabel = new JLabel("Goals");
		forGoalLabel.setFont(FontStyle.BOLD_FONT);
		forGoalLabel.setBounds(347, 382, 57, 14);
		gamePanel.add(forGoalLabel);
		
		JLabel forShotsLabel = new JLabel("Shots");
		forShotsLabel.setFont(FontStyle.BOLD_FONT);
		forShotsLabel.setBounds(347, 128, 57, 14);
		gamePanel.add(forShotsLabel);
		
		JLabel forPassesLabel = new JLabel("Passes");
		forPassesLabel.setFont(FontStyle.BOLD_FONT);
		forPassesLabel.setBounds(347, 163, 70, 14);
		gamePanel.add(forPassesLabel);
		
		JLabel forFoulsLabel = new JLabel("Fouls");
		forFoulsLabel.setFont(FontStyle.BOLD_FONT);
		forFoulsLabel.setBounds(347, 210, 70, 14);
		gamePanel.add(forFoulsLabel);
		
		JLabel forOffsidesLabel = new JLabel("Offsides");
		forOffsidesLabel.setFont(FontStyle.BOLD_FONT);
		forOffsidesLabel.setBounds(347, 251, 70, 14);
		gamePanel.add(forOffsidesLabel);
		
		JLabel forCornersLabel = new JLabel("Corners");
		forCornersLabel.setFont(FontStyle.BOLD_FONT);
		forCornersLabel.setBounds(347, 293, 57, 14);
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
		JLabel lblNewLabel = new JLabel(renderImage(ImagePath.LOGOS + homeTeam.getLogo()));
		lblNewLabel.setBounds(50, -35, 137, 109);
		gamePanel.add(lblNewLabel);
		
		homeTeamPanel = new JPanel();
		homeTeamPanel.setBorder(new TitledBorder(null, "Home team", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		homeTeamPanel.setBounds(28, 73, 309, 360);
		gamePanel.add(homeTeamPanel);
		homeTeamPanel.setLayout(null);

		homeTeamGoalsTextField = new JTextField(Integer.toString(homeTeam.getScore()));
		homeTeamGoalsTextField.setEditable(!seasonGameController.isUserHomeTeam());
		homeTeamGoalsTextField.setBounds(240, 291, 44, 44);
		homeTeamPanel.add(homeTeamGoalsTextField);
		
		homeTeamShotsTextField = new JTextField(Integer.toString(homeTeam.getShots()));
		homeTeamShotsTextField.setBounds(240, 41, 44, 31);
		homeTeamPanel.add(homeTeamShotsTextField);
		
		homeTeamPassesTextField = new JTextField(Integer.toString(homeTeam.getPasses()));
		homeTeamPassesTextField.setBounds(240, 83, 44, 31);
		homeTeamPanel.add(homeTeamPassesTextField);
		
		homeTeamFoulsTextField = new JTextField(Integer.toString(homeTeam.getFouls()));
		homeTeamFoulsTextField.setBounds(240, 125, 44, 31);
		homeTeamPanel.add(homeTeamFoulsTextField);
		
		homeTeamOffsidesTextField = new JTextField(Integer.toString(homeTeam.getOffsides()));
		homeTeamOffsidesTextField.setBounds(240, 167, 44, 31);
		homeTeamPanel.add(homeTeamOffsidesTextField);
		
		homeTeamCornersTextField = new JTextField(Integer.toString(homeTeam.getCorners()));
		homeTeamCornersTextField.setBounds(240, 211, 44, 31);
		homeTeamPanel.add(homeTeamCornersTextField);
		
		forHomeTeamName = new JLabel(homeTeam.getName());
		forHomeTeamName.setFont(FontStyle.BOLD_FONT);
		forHomeTeamName.setBounds(84, 21, 138, 14);
		homeTeamPanel.add(forHomeTeamName);
	}
	
	private void loadAwayTeam(Team awayTeam) {
		JLabel lblOgo = new JLabel(renderImage(ImagePath.LOGOS + awayTeam.getLogo()));
		lblOgo.setBounds(547, -33, 131, 107);
		gamePanel.add(lblOgo);
		
		awayTeamPanel = new JPanel();
		awayTeamPanel.setLayout(null);
		awayTeamPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Away team", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		awayTeamPanel.setBounds(427, 73, 309, 360);
		gamePanel.add(awayTeamPanel);
		
		awayTeamGoalsTextField = new JTextField(Integer.toString(awayTeam.getScore()));
		awayTeamGoalsTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		awayTeamGoalsTextField.setEditable(seasonGameController.isUserHomeTeam());
		awayTeamGoalsTextField.setBounds(10, 292, 44, 45);
		awayTeamPanel.add(awayTeamGoalsTextField);
		
		awayTeamShotsTextField = new JTextField(Integer.toString(awayTeam.getShots()));
		awayTeamShotsTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		awayTeamShotsTextField.setBounds(10, 38, 44, 31);
		awayTeamPanel.add(awayTeamShotsTextField);
		
		awayTeamPassesTextField = new JTextField(Integer.toString(awayTeam.getPasses()));
		awayTeamPassesTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		awayTeamPassesTextField.setBounds(10, 80, 44, 31);
		awayTeamPanel.add(awayTeamPassesTextField);
		
		awayTeamFoulsTextField = new JTextField(Integer.toString(awayTeam.getFouls()));
		awayTeamFoulsTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		awayTeamFoulsTextField.setBounds(10, 128, 44, 31);
		awayTeamPanel.add(awayTeamFoulsTextField);
		
		awayTeamOffsidesTextField = new JTextField(Integer.toString(awayTeam.getOffsides()));
		awayTeamOffsidesTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		awayTeamOffsidesTextField.setBounds(10, 170, 44, 31);
		awayTeamPanel.add(awayTeamOffsidesTextField);
		
		awayTeamCornersTextField = new JTextField(Integer.toString(awayTeam.getCorners()));
		awayTeamCornersTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		awayTeamCornersTextField.setBounds(10, 212, 44, 31);
		awayTeamPanel.add(awayTeamCornersTextField);
		
		forAwayTeamName = new JLabel(awayTeam.getName());
		forAwayTeamName.setFont(FontStyle.BOLD_FONT);
		forAwayTeamName.setBounds(130, 25, 144, 14);
		awayTeamPanel.add(forAwayTeamName);
	}
	
	private void loadUserTeamPlayers(JPanel teamPanel) {
		JLabel forSanctionsLabel = new JLabel("Sanctions");
		forSanctionsLabel.setBounds(75, 48, 61, 14);
		teamPanel.add(forSanctionsLabel);
		
		JScrollPane sanctionsScrollPane = new JScrollPane();
		sanctionsScrollPane.setBounds(75, 98, 155, 95);
		teamPanel.add(sanctionsScrollPane);
		
		sanctionsTable = new SanctionsTable(seasonGameController.isUserHomeTeam()
				? seasonGameController.getHomeTeam().getSanctions()
				: seasonGameController.getAwayTeam().getSanctions());
		sanctionsScrollPane.setViewportView(sanctionsTable);
		teamPanel.add(sanctionsScrollPane);
		
		addSactionButton = new JButton("Add");
		addSactionButton.setBounds(75, 73, 69, 21);
		teamPanel.add(addSactionButton);

		removeSanctionButton = new JButton("Remove");
		removeSanctionButton.setBounds(154, 73, 76, 21);
		teamPanel.add(removeSanctionButton);
		
		removeScorerButton = new JButton("Remove");
		removeScorerButton.setBounds(154, 228, 76, 21);
		teamPanel.add(removeScorerButton);
		
		JLabel forScorersLabel = new JLabel("Scorers");
		forScorersLabel.setBounds(75, 204, 61, 14);
		teamPanel.add(forScorersLabel);

		addScorerButton = new JButton("Add");
		addScorerButton.setBounds(75, 228, 69, 21);
		teamPanel.add(addScorerButton);
		
		JScrollPane scorersScrollPane = new JScrollPane();
		scorersScrollPane.setBounds(75, 254, 155, 95);

		scorersList = new ScorersList(seasonGameController.isUserHomeTeam()
				? seasonGameController.getHomeTeam().getScorers()
				: seasonGameController.getAwayTeam().getScorers());
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
		
		addSactionButton.addActionListener((e) -> new SanctionsView(seasonGameController, sanctionsTable));
		removeSanctionButton.addActionListener((e) -> {
			try {
				Sanctioned selSanctioned = sanctionsTable.getSelectedSanctioned();
				if (Alert.showConfirm(this, "Delete sanction",
						"Are you sure you want to remove the " + selSanctioned.getSanction().getType() +
						" for " + selSanctioned.getPlayer().getFirstName() + "?"))
				{
					seasonGameController.deleteSanctionedSanction(selSanctioned);
					sanctionsTable.remove(sanctionsTable.getSelectedRow());
					Alert.showInfo(this, "Sanctioned sanction deleted!");
				}
			} catch(NotTableItemSelectedException err) {
				Alert.showError(this, err.getMessage());
			} catch(SQLException err) {
				Alert.showError(this, "Error connecting to the database");
			}
		});
		
		addScorerButton.addActionListener((e) -> new AddUpdateScorerView(seasonGameController, scorersList));
		removeScorerButton.addActionListener((e) -> {
			try {
				Scorer selScorer = scorersList.getSelectedItem(Scorer.class.getSimpleName());
				if (Alert.showConfirm(this, "Delete scorer",
						"Are you sure you want to remove to " + selScorer.getPlayer().getFirstName() + "?"))
				{
					seasonGameController.deleteScorer(selScorer);
					scorersList.removeSelectedRow(scorersList.getSelectedIndex());
					scorersList.update();
					Alert.showInfo(this, "Scorer deleted!");
				}
			} catch(NotTableItemSelectedException err) {
				Alert.showError(this, err.getMessage());
			} catch(SQLException err) {
				Alert.showError(this, "Error connecting to the database");
			}
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
				
				seasonGameController.updateStats(gameDate, homeTeamPerformance, awayTeamPerformance, scorersList.getScorers());
				
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
	
	@Override
	public boolean isFocused() {
		String totalScore = Integer.toString(scorersList.getTotalScore());
		if (seasonGameController.isUserHomeTeam()) {
			homeTeamGoalsTextField.setText(totalScore);
		} else {
			awayTeamGoalsTextField.setText(totalScore);
		}
		return super.isFocused();
	}
}
