package com.devdream.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.devdream.controller.Controller;
import com.devdream.controller.LeagueController;
import com.devdream.controller.PlayerController;
import com.devdream.controller.TeamController;
import com.devdream.db.DBConnectionManager;
import com.devdream.exception.InvalidInputException;
import com.devdream.exception.NotTableItemSelectedException;
import com.devdream.exception.OperationCancelledException;
import com.devdream.model.League;
import com.devdream.model.Player;
import com.devdream.model.SeasonGame;
import com.devdream.model.Team;
import com.devdream.model.User;
import com.devdream.ui.custom.Alert;
import com.devdream.ui.custom.PlayersTable;
import com.devdream.ui.custom.SeasonGamesTable;
import com.devdream.ui.custom.TeamsTable;
import com.devdream.util.ExportDatabase;
import com.devdream.util.UploadImage;
import com.itextpdf.text.DocumentException;

public class MainView extends View {
	private static final long serialVersionUID = -6488927513006875327L;
	
	//
	// Attributes
	private TeamController teamController;
	private PlayerController playerController;
	private LeagueController leagueController;
	
	private boolean hasUserTeam;
	private boolean hasTeamPlayers;
	private boolean isLeagueUnderway;
	
	private JTabbedPane personalInformationTabbedPane;
	private JPanel userPanel;
	private JPanel teamPanel;
	
	private JLabel teamLogoLabel;
	
	private PlayersTable playersTable;
	private TeamsTable opponentTeamsTable;
	private SeasonGamesTable seasonsTable;
	
	private JButton teamLogoEditButton;
	private JButton createNewTeamButton;
	private JButton newLeagueButton;
	private JButton newOpponentButton;
	private JButton newPlayerButton;
	private JButton modifyPlayerButton;
	private JButton deletePlayerButton;
	private JButton viewSeasonButton;
	private JButton exportAllSeasonsGamesCSVButton;
	private JButton exportSeasonGameCSVButton;
	private JButton exportSeasonGamePDFButton;
	private JButton topScorersButton;
	private JButton searchPerformancesButton;
	private JButton exportDBButton;
	
	//
	// Constructors
	public MainView() {
		super();
		getContentPane().setLayout(null);
		
		try {
			teamController = new TeamController();
			playerController = new PlayerController();
			leagueController = new LeagueController();
		} catch (SQLException e) {
			e.printStackTrace();
			Alert.showError(this, "Error connecting to the database!");
		}
		
		hasUserTeam = teamController.hasUserTeam();
		hasTeamPlayers = teamController.hasTeamPlayers();
		isLeagueUnderway = leagueController.isLeagueUnderway();
		
		loadUI();
		loadListeners();
		
		// TODO For design
		/*** BEGIN ***/
//		loadUserTeam();
//		loadTeamPane(new Team(1, "qwe", "wqwe", 2016, "qwe", "team-default-logo.png"));
//		loadTeamPlayersTab(new HashMap<Integer, Player>());
//		loadLeagueTab(new League("25/23/2333", "23/66/243", "LeagueLOL", "Description blah blah", 3));
//		showFeatures();
		/*** END ***/
		
		render();
	}
	
	//
	// Methods
	@Override
	protected void loadUI() {
		personalInformationTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		personalInformationTabbedPane.setBounds(10, 30, 389, 530);
		getContentPane().add(personalInformationTabbedPane);
		
		loadUserInfortation(Controller.getLoggedUser());
		
		if (hasUserTeam) {
			loadUserTeam();
			loadTeamPane(teamController.getUserTeam());
			loadTeamPlayersTab(teamController.getTeamPlayers());
			if (isLeagueUnderway) {
				loadLeagueTab(leagueController.getCurrentLeague());
			} else {
				showFeatures();
			}
		} else {
			loadUserHasNoTeam();
		}
	}
	
	private void loadUserTeam() {
		JLabel forUserTeamNameLabel = new JLabel("Team");
		forUserTeamNameLabel.setFont(FontStyle.BOLD_FONT);
		forUserTeamNameLabel.setBounds(23, 145, 73, 14);
		userPanel.add(forUserTeamNameLabel);
		
		JLabel userTeamNameLabel = new JLabel(Controller.getLoggedUser().getTeam().getName());
		userTeamNameLabel.setBounds(177, 145, 103, 14);
		userPanel.add(userTeamNameLabel);
	}
	
	/**
	 * Loads the User personal information, the user and the team (with players).
	 */
	private void loadUserInfortation(User user) {
		userPanel = new JPanel();
		personalInformationTabbedPane.addTab("User", renderImage(ImagePath.USER_TAB_ICON), userPanel, "User tab");
		userPanel.setBorder(new TitledBorder(null, "User", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		userPanel.setLayout(null);
		
		JLabel forUserUsernameLabel = new JLabel("Username");
		forUserUsernameLabel.setFont(FontStyle.BOLD_FONT);
		forUserUsernameLabel.setBounds(23, 33, 97, 14);
		userPanel.add(forUserUsernameLabel);
		
		JLabel forUserFirstNameLabel = new JLabel("First name");
		forUserFirstNameLabel.setFont(FontStyle.BOLD_FONT);
		forUserFirstNameLabel.setBounds(23, 72, 97, 14);
		userPanel.add(forUserFirstNameLabel);
		
		JLabel forUserSurnameLabel = new JLabel("Surname");
		forUserSurnameLabel.setFont(FontStyle.BOLD_FONT);
		forUserSurnameLabel.setBounds(23, 114, 97, 14);
		userPanel.add(forUserSurnameLabel);
		
		JLabel userUsernameLabel = new JLabel(user.getUsername());
		userUsernameLabel.setBounds(177, 35, 80, 14);
		userPanel.add(userUsernameLabel);
		
		JLabel userFirstNameLabel = new JLabel(user.getFirstName());
		userFirstNameLabel.setBounds(177, 74, 86, 14);
		userPanel.add(userFirstNameLabel);
		
		JLabel userSurnameLabel = new JLabel(user.getSurname());
		userSurnameLabel.setBounds(177, 116, 86, 14);
		userPanel.add(userSurnameLabel);
		
		exportDBButton = new JButton("Export all database");
		exportDBButton.setIcon(renderImage(ImagePath.DDBB_ICON));
		exportDBButton.setHorizontalTextPosition(AbstractButton.LEFT);
		exportDBButton.setBounds(70, 318, 209, 57);
		userPanel.add(exportDBButton);
	}
	
	/**
	 * Loads the team of the User.
	 * @param userTeam
	 */
	private void loadTeamPane(Team userTeam) {
		teamPanel = new JPanel();
		personalInformationTabbedPane.addTab("Teams", renderImage(ImagePath.TEAM_TAB_ICON), teamPanel, "User team tab");
		teamPanel.setLayout(null);
		teamPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Team", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		JLabel forTeamNameLabel = new JLabel("Team name");
		forTeamNameLabel.setFont(FontStyle.BOLD_FONT);
		forTeamNameLabel.setBounds(23, 29, 92, 14);
		teamPanel.add(forTeamNameLabel);
		
		JLabel forTeamShortNameLabel = new JLabel("Short name");
		forTeamShortNameLabel.setFont(FontStyle.BOLD_FONT);
		forTeamShortNameLabel.setBounds(23, 54, 92, 14);
		teamPanel.add(forTeamShortNameLabel);
		
		JLabel forTeamFoundedYearLabel = new JLabel("Founded year");
		forTeamFoundedYearLabel.setFont(FontStyle.BOLD_FONT);
		forTeamFoundedYearLabel.setBounds(23, 79, 128, 14);
		teamPanel.add(forTeamFoundedYearLabel);
		
		JLabel forTeamLocationLabel = new JLabel("Location");
		forTeamLocationLabel.setFont(FontStyle.BOLD_FONT);
		forTeamLocationLabel.setBounds(23, 132, 73, 14);
		teamPanel.add(forTeamLocationLabel);
		
		JLabel teamTeamNameLabel = new JLabel(userTeam.getName());
		teamTeamNameLabel.setBounds(146, 31, 73, 14);
		teamPanel.add(teamTeamNameLabel);
		
		JLabel teamShortNameLabel = new JLabel(userTeam.getShortName());
		teamShortNameLabel.setBounds(146, 56, 73, 14);
		teamPanel.add(teamShortNameLabel);
		
		JLabel teamFoundedYearLabel = new JLabel(Integer.toString(userTeam.getFoundedYear()));
		teamFoundedYearLabel.setBounds(23, 104, 73, 14);
		teamPanel.add(teamFoundedYearLabel);
		
		JLabel teamLocationLabel = new JLabel(userTeam.getLocation());
		teamLocationLabel.setBounds(23, 157, 73, 14);
		teamPanel.add(teamLocationLabel);
		
		teamLogoLabel = new JLabel(renderImage(ImagePath.LOGOS + userTeam.getLogo()));
		teamLogoLabel.setBounds(229, 33, 128, 97);
		teamPanel.add(teamLogoLabel);
		
		teamLogoEditButton = new JButton(renderImage(ImagePath.EDIT_IMG_ICON));
		teamLogoEditButton.setBounds(305, 127, 52, 44);
		teamLogoEditButton.setFocusPainted(false);
		teamLogoEditButton.setMargin(new Insets(0, 0, 0, 0));
		teamLogoEditButton.setContentAreaFilled(false);
		teamLogoEditButton.setBorderPainted(false);
		teamLogoEditButton.setOpaque(false);
		teamPanel.add(teamLogoEditButton);
		
		JLabel forNewOpponentLabel = new JLabel("Opponents teams");
		forNewOpponentLabel.setFont(FontStyle.BOLD_FONT);
		forNewOpponentLabel.setBounds(10, 205, 161, 14);
		teamPanel.add(forNewOpponentLabel);

		newOpponentButton = new JButton("Add opponent team");
		newOpponentButton.setBounds(181, 203, 170, 23);
		teamPanel.add(newOpponentButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(11, 230, 340, 237);
		teamPanel.add(scrollPane);
		
		opponentTeamsTable = new TeamsTable(teamController.getOpponentTeams());
		scrollPane.setViewportView(opponentTeamsTable);
	}
	
	/**
	 * Loads the team players.
	 * @param userTeamPlayers
	 */
	private void loadTeamPlayersTab(HashMap<Integer, Player> userTeamPlayers) {
		JPanel playersPanel = new JPanel();
		personalInformationTabbedPane.addTab("Players", renderImage(ImagePath.PLAYER_TAB_ICON), playersPanel, "Team players tab");
		playersPanel.setLayout(null);
		playersPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Team players", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
				
		JLabel forPlayersTableLabel = new JLabel("Team players");
		forPlayersTableLabel.setFont(FontStyle.BOLD_FONT);
		forPlayersTableLabel.setBounds(10, 109, 124, 14);
		playersPanel.add(forPlayersTableLabel);
		
		newPlayerButton = new JButton("New player");
		newPlayerButton.setBounds(10, 58, 109, 23);
		playersPanel.add(newPlayerButton);

		modifyPlayerButton = new JButton("Modify player");
		modifyPlayerButton.setBounds(132, 58, 109, 23);
		playersPanel.add(modifyPlayerButton);
		
		deletePlayerButton = new JButton("Delete player");
		deletePlayerButton.setBounds(250, 58, 124, 23);
		playersPanel.add(deletePlayerButton);
		
		JScrollPane playersTableScrollPane = new JScrollPane();
		playersTableScrollPane.setBounds(10, 134, 364, 322);
		playersPanel.add(playersTableScrollPane);
		
		playersTable = new PlayersTable(userTeamPlayers);
		playersTableScrollPane.setViewportView(playersTable);
	}
	
	/**
	 * Load when the user has no team, to create one.
	 */
	private void loadUserHasNoTeam() {
		createNewTeamButton = new JButton(renderImage(ImagePath.CREATE_TEAM_ICON));
		createNewTeamButton.setBounds(44, 128, 279, 183);
		createNewTeamButton.setFocusPainted(false);
		createNewTeamButton.setMargin(new Insets(0, 0, 0, 0));
		createNewTeamButton.setContentAreaFilled(false);
		createNewTeamButton.setBorderPainted(false);
		createNewTeamButton.setOpaque(false);
		userPanel.add(createNewTeamButton);
	}
	
	/**
	 * Show features of the application.
	 */
	private void showFeatures() {
		JLabel featuresLabel = new JLabel(renderImage(ImagePath.FEATURES_ICON));
		featuresLabel.setBounds(423, 181, 379, 185);
		getContentPane().add(featuresLabel);
		
		newLeagueButton = new JButton("Create a new League");
		newLeagueButton.setFont(new Font("Tekton Pro Cond", Font.BOLD | Font.ITALIC, 26));
		newLeagueButton.setBackground(new Color(153, 255, 255));
		newLeagueButton.setForeground(new Color(255, 255, 255));
		newLeagueButton.setBounds(491, 381, 242, 49);
		getContentPane().add(newLeagueButton);
	}
	
	/**
	 * Loads the league information.
	 */
	private void loadLeagueTab(League currentLeague) {
		JTabbedPane leagueInformationTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		leagueInformationTabbedPane.setBounds(425, 30, 389, 530);
		getContentPane().add(leagueInformationTabbedPane);
		
		JPanel leaguePanel = new JPanel();
		leagueInformationTabbedPane.addTab("League", renderImage(ImagePath.LEAGUE_ICON), leaguePanel, "League tab");
		leaguePanel.setBorder(new TitledBorder(null, "League", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		leaguePanel.setLayout(null);
		
		JLabel forLeagueNameLabel = new JLabel("League name");
		forLeagueNameLabel.setFont(FontStyle.BOLD_FONT);
		forLeagueNameLabel.setBounds(24, 46, 111, 14);
		leaguePanel.add(forLeagueNameLabel);
		
		JLabel leagueNameLabel = new JLabel(currentLeague.getName());
		leagueNameLabel.setBounds(24, 71, 111, 14);
		leaguePanel.add(leagueNameLabel);

		JLabel forLeagueDescriptionLabel = new JLabel("Description");
		forLeagueDescriptionLabel.setFont(FontStyle.BOLD_FONT);
		forLeagueDescriptionLabel.setBounds(175, 46, 99, 14);
		leaguePanel.add(forLeagueDescriptionLabel);
		
		JLabel forLeagueStartDateLabel = new JLabel("Start date");
		forLeagueStartDateLabel.setFont(FontStyle.BOLD_FONT);
		forLeagueStartDateLabel.setBounds(24, 107, 99, 14);
		leaguePanel.add(forLeagueStartDateLabel);
		
		JLabel leagueStartDateLabel = new JLabel(currentLeague.getStartDate());
		leagueStartDateLabel.setBounds(24, 132, 91, 14);
		leaguePanel.add(leagueStartDateLabel);
		
		JLabel forLeagueEndDateLabel = new JLabel("End date");
		forLeagueEndDateLabel.setFont(FontStyle.BOLD_FONT);
		forLeagueEndDateLabel.setBounds(24, 163, 99, 14);
		leaguePanel.add(forLeagueEndDateLabel);
		
		JLabel leagueEndDateLabel = new JLabel(currentLeague.getEndDate());
		leagueEndDateLabel.setBounds(24, 188, 91, 14);
		leaguePanel.add(leagueEndDateLabel);
		
		JLabel forLeaguePeriodLabel = new JLabel("League period (in days)");
		forLeaguePeriodLabel.setFont(FontStyle.BOLD_FONT);
		forLeaguePeriodLabel.setBounds(175, 163, 147, 14);
		leaguePanel.add(forLeaguePeriodLabel);
		
		JLabel leaguePeriodLabel = new JLabel(Integer.toString(currentLeague.getPeriod()));
		leaguePeriodLabel.setBounds(175, 188, 91, 14);
		leaguePanel.add(leaguePeriodLabel);
		

		JLabel forLeagueLeftDaysLabel = new JLabel("Days left");
		forLeagueLeftDaysLabel.setFont(FontStyle.BOLD_FONT);
		forLeagueLeftDaysLabel.setBounds(24, 216, 99, 14);
		leaguePanel.add(forLeagueLeftDaysLabel);
		
		JLabel leagueLeftDaysLabel = new JLabel(Integer.toString(currentLeague.getLeftDays()));
		leagueLeftDaysLabel.setBounds(24, 244, 91, 14);
		leaguePanel.add(leagueLeftDaysLabel);
		
		JLabel forLeagueNumberSeasonsLabel = new JLabel("Number of seasons");
		forLeagueNumberSeasonsLabel.setFont(FontStyle.BOLD_FONT);
		forLeagueNumberSeasonsLabel.setBounds(175, 213, 163, 14);
		leaguePanel.add(forLeagueNumberSeasonsLabel);
		
		JLabel leagueNumberSeasonsLabel = new JLabel(Integer.toString(currentLeague.getNumberSeasons()));
		leagueNumberSeasonsLabel.setBounds(175, 244, 91, 14);
		leaguePanel.add(leagueNumberSeasonsLabel);
		
		topScorersButton = new JButton("Top league scorers");
		topScorersButton.setIcon(renderImage(ImagePath.TOP_SCORERS_ICON));
		topScorersButton.setHorizontalTextPosition(AbstractButton.LEFT);
		topScorersButton.setBounds(84, 317, 238, 57);
		leaguePanel.add(topScorersButton);
		
		searchPerformancesButton = new JButton("Search by performances");
		searchPerformancesButton.setIcon(renderImage(ImagePath.STATISTICS_ICON));
		searchPerformancesButton.setHorizontalTextPosition(AbstractButton.LEFT);
		searchPerformancesButton.setBounds(84, 402, 238, 57);
		leaguePanel.add(searchPerformancesButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(175, 71, 181, 71);
		leaguePanel.add(scrollPane);
		
		JTextArea leagueDescriptionTextArea = new JTextArea(currentLeague.getDescription());
		leagueDescriptionTextArea.setEditable(false);
		scrollPane.setViewportView(leagueDescriptionTextArea);
		
		JPanel seasonsPanel = new JPanel();
		seasonsPanel.setBorder(new TitledBorder(null, "Seasons", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		leagueInformationTabbedPane.addTab("Seasons", renderImage(ImagePath.SEASONS_TAB_ICON), seasonsPanel, "Season tab");
		seasonsPanel.setLayout(null);

		viewSeasonButton = new JButton("View season game");
		viewSeasonButton.setIcon(renderImage(ImagePath.VIEW_SEASON_ICON));
		viewSeasonButton.setHorizontalTextPosition(AbstractButton.LEFT);
		viewSeasonButton.setBounds(62, 21, 257, 46);
		seasonsPanel.add(viewSeasonButton);
		
		JPanel gamesPanel = new JPanel();
		gamesPanel.setBorder(new TitledBorder(null, "Games", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		gamesPanel.setBounds(10, 78, 364, 323);
		seasonsPanel.add(gamesPanel);
		gamesPanel.setLayout(null);
		
		JScrollPane matchesTableScrollPane = new JScrollPane();
		matchesTableScrollPane.setBounds(10, 22, 344, 290);
		gamesPanel.add(matchesTableScrollPane);
		
		seasonsTable = new SeasonGamesTable(leagueController.getLeagueSeasonGames());
		matchesTableScrollPane.setViewportView(seasonsTable);

		exportAllSeasonsGamesCSVButton = new JButton("Export All seasons games to CSV");
		exportAllSeasonsGamesCSVButton.setBounds(75, 467, 257, 25);
		exportAllSeasonsGamesCSVButton.setIcon(renderImage(ImagePath.CSV_ICON));
		exportAllSeasonsGamesCSVButton.setHorizontalTextPosition(AbstractButton.LEFT);
		seasonsPanel.add(exportAllSeasonsGamesCSVButton);
		
		exportSeasonGameCSVButton = new JButton("Export season game to CSV");
		exportSeasonGameCSVButton.setIcon(renderImage(ImagePath.CSV_ICON));
		exportSeasonGameCSVButton.setHorizontalTextPosition(SwingConstants.LEFT);
		exportSeasonGameCSVButton.setBounds(75, 436, 256, 25);
		seasonsPanel.add(exportSeasonGameCSVButton);
		
		exportSeasonGamePDFButton = new JButton("Export season game to PDF");
		exportSeasonGamePDFButton.setIcon(renderImage(ImagePath.PDF_ICON));
		exportSeasonGamePDFButton.setHorizontalTextPosition(SwingConstants.LEFT);
		exportSeasonGamePDFButton.setBounds(75, 404, 257, 25);
		seasonsPanel.add(exportSeasonGamePDFButton);
	}
	
	@Override
	protected void loadListeners() {
		exportDBButton.addActionListener((e) -> {
			try {
				Alert.showInfo(this, "Select the path to save the database file. (Name it as you want)");
				String savePath = Alert.showFileChooser(DBConnectionManager.DB_FILE_EXT);
				ExportDatabase exportDatabase = new ExportDatabase(savePath);
				exportDatabase.export();
				Alert.showInfo(this, "Database exported.");
			} catch (OperationCancelledException err) {
				Alert.showError(this, err.getMessage());
			} catch (IOException err) {
				err.printStackTrace();
				Alert.showError(this, "Error uploading the image!");
			}
		});
		
		if (hasUserTeam) {
			teamLogoEditButton.addActionListener((e) -> {
				try {
					Alert.showInfo(this, "Select the new image for your team.");
					
					String imgPath = Alert.showFileChooser("");
					UploadImage uploadImage = new UploadImage(imgPath);
					String fileName = uploadImage.uploadImage();
					
					teamLogoLabel.setIcon(renderImage(ImagePath.LOGOS + fileName));
					teamController.updateLogoImage(fileName, teamController.getUserTeam().getId());
					
					Alert.showInfo(this, "Image uploaded.");
				} catch (OperationCancelledException err) {
					Alert.showError(this, err.getMessage());
				} catch (IOException | URISyntaxException | SQLException err) {
					Alert.showError(this, "Error uploading the image!");
				}
			});
			
			newOpponentButton.addActionListener((e) -> new CreateTeamView(teamController, opponentTeamsTable));
			
			newPlayerButton.addActionListener((e) -> new CreateUpdatePlayerView(teamController, playersTable));
			
			modifyPlayerButton.addActionListener((e) -> {
				try {
					Player selPlayer = playersTable.getSelectedPlayer();
					new CreateUpdatePlayerView(teamController, playersTable, selPlayer);
				} catch (NotTableItemSelectedException err) {
					Alert.showError(this, err.getMessage());
				}
				
			});
			
			deletePlayerButton.addActionListener((e) -> {
				try {
					Player selPlayer = playersTable.getSelectedPlayer();
					if (Alert.showConfirm(this, "Remove player",
							"Are you sure you want to remove to " + selPlayer.getFirstName() +
							" with the dorsal " + selPlayer.getDorsal() + "?"))
					{
						playerController.deleteTeamPlayer(selPlayer.getDorsal());
						playersTable.removePlayer(selPlayer);
						Alert.showInfo(this, "Player deleted!");
					}
				} catch (NotTableItemSelectedException err) {
					Alert.showError(this, err.getMessage());
				} catch (SQLException err) {
					Alert.showError(this, "Deletion could not be completed!");
				}
			});
			
			if (isLeagueUnderway) {
				topScorersButton.addActionListener((e) -> changeView(this, TopScorersView.class));
				
				searchPerformancesButton.addActionListener((e) -> changeView(this, PerformanceSearchView.class));
				
				viewSeasonButton.addActionListener((e) -> {
					try {
						SeasonGame selSeasonGame = seasonsTable.getSelectedSeasonGame();
						new SeasonGameView(selSeasonGame);
						dispose();
					} catch (NotTableItemSelectedException err) {
						Alert.showError(this, err.getMessage());
					}
				});

				exportAllSeasonsGamesCSVButton.addActionListener((e) -> {
					try {
						SeasonGame.exportToCSV(new ArrayList<SeasonGame>(seasonsTable.getSeasons().values()));
						Alert.showInfo(this, "CSV exported successfully!");
					} catch(OperationCancelledException err) {
						Alert.showError(this, err.getMessage());
					} catch (IOException err) {
						Alert.showError(this, "Error creating the CSV of the season.");
					}
				});
				
				exportSeasonGameCSVButton.addActionListener((e) -> {
					SeasonGame selectedSeason;
					try {
						selectedSeason = seasonsTable.getSelectedSeasonGame();
						selectedSeason.exportToCSV();
						Alert.showInfo(this, "CSV exported successfully!");
					} catch (NotTableItemSelectedException | OperationCancelledException err) {
						Alert.showError(this, err.getMessage());
					} catch (IOException err) {
						Alert.showError(this, "Error creating the CSV of the season.");
					}
				});
				
				exportSeasonGamePDFButton.addActionListener((e) -> {
					SeasonGame selectedSeason;
					try {
						selectedSeason = seasonsTable.getSelectedSeasonGame();
						selectedSeason.exportToPDF();
						Alert.showInfo(this, "PDF exported successfully!");
					} catch (NotTableItemSelectedException | OperationCancelledException err) {
						Alert.showError(this, err.getMessage());
					} catch (IOException | DocumentException err) {
						Alert.showError(this, "Error creating the CSV of the season.");
					}
				});
			} else {
				newLeagueButton.addActionListener((e) -> {
					try {
						if (opponentTeamsTable.isEmpty()) {
							throw new InvalidInputException("You must have at least one opponent team to create a league!");
						}
						changeView(this, CreateLeagueView.class);
					} catch (Exception err) {
						Alert.showError(this, err.getMessage());
					}
				});
			}
		} else {
			createNewTeamButton.addActionListener((e) -> {
				new CreateUserTeamView(teamController);
				dispose();
			});
		}
	}
	
	@Override
	public boolean isFocused() {
		update();
		return super.isFocused();
	}
	
	private void update() {
		if (hasUserTeam) opponentTeamsTable.update();
		if (hasTeamPlayers) playersTable.update();
		if (isLeagueUnderway) seasonsTable.update();
	}

}
