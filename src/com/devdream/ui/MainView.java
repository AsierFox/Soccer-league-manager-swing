package com.devdream.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.devdream.controller.Controller;
import com.devdream.controller.LeagueController;
import com.devdream.controller.TeamController;
import com.devdream.model.League;
import com.devdream.model.Player;
import com.devdream.model.Team;
import com.devdream.model.User;
import com.devdream.ui.custom.Alert;
import com.devdream.ui.custom.PlayersTable;
import com.devdream.ui.custom.SeasonsTable;
import com.devdream.ui.custom.TeamsTable;

public class MainView extends View {
	private static final long serialVersionUID = -6488927513006875327L;
	
	//
	// Attributes
	private TeamController teamController;
	private LeagueController leagueController;
	
	private boolean hasUserTeam;
	private boolean hasTeamPlayers;
	private boolean isLeagueUnderway;
	
	private JTabbedPane personalInformationTabbedPane;
	private JPanel userPanel;
	private JPanel teamPanel;
	
	private PlayersTable playersTable;
	private TeamsTable opponentTeamsTable;
	private SeasonsTable seasonsTable;
	
	private JButton teamLogoEditButton;
	private JButton createNewTeamButton;
	private JButton createNewLeagueButton;
	
	//
	// Constructors
	public MainView() {
		super();
		getContentPane().setLayout(null);
		
		try {
			teamController = new TeamController();
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
//		loadTeamPane(new Team("qwe", "wqwe", 2, 2, "qwe", "team-default.png"));
//		loadTeamPlayersTab(new HashMap<Integer, Player>());
//		loadLeagueTab(new League("25/23/2333", "23/66/243", "LeagueLOL", "Description blah blah", 3));
//		
//		showFeatures();
		/*** END ***/
		
		render();
	}
	
	//
	// Methods
	@Override
	protected void loadUI() {
		personalInformationTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		personalInformationTabbedPane.setBounds(10, 55, 389, 505);
		getContentPane().add(personalInformationTabbedPane);
		
		loadUserInfortation(Controller.getLoggedUser());
		
		if (hasUserTeam) {
			loadUserTab();
			loadTeamPane(teamController.getUserTeam());
			
			if (hasTeamPlayers) {
				loadTeamPlayersTab(teamController.getTeamPlayers());
			}
			
			// Only if the user has a team will be able to create a league
			if (isLeagueUnderway) {
				loadLeagueTab(leagueController.getCurrentLeague());
				
				// TODO CHECK IF THE LEAGUE seasons HAS MATCHES GENERATED
				
			}
			else {
				showFeatures();
			}
		}
		else {
			loadUserHasNoTeam();
		}
		
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
		forUserUsernameLabel.setBounds(23, 33, 73, 14);
		userPanel.add(forUserUsernameLabel);
		
		JLabel forUserFirstNameLabel = new JLabel("First name");
		forUserFirstNameLabel.setBounds(23, 54, 73, 14);
		userPanel.add(forUserFirstNameLabel);
		
		JLabel forUserSurnameLabel = new JLabel("Surname");
		forUserSurnameLabel.setBounds(23, 75, 73, 14);
		userPanel.add(forUserSurnameLabel);
		
		JLabel userUsernameLabel = new JLabel(user.getUsername()); // TODO Don't like this line
		userUsernameLabel.setBounds(106, 33, 73, 14);
		userPanel.add(userUsernameLabel);
		
		JLabel userFirstNameLabel = new JLabel(user.getFirstName());
		userFirstNameLabel.setBounds(106, 54, 73, 14);
		userPanel.add(userFirstNameLabel);
		
		JLabel userSurnameLabel = new JLabel(user.getSurname());
		userSurnameLabel.setBounds(106, 75, 73, 14);
		userPanel.add(userSurnameLabel);
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
		forTeamNameLabel.setBounds(23, 33, 73, 14);
		teamPanel.add(forTeamNameLabel);
		
		JLabel forTeamShortNameLabel = new JLabel("Short name");
		forTeamShortNameLabel.setBounds(23, 54, 73, 14);
		teamPanel.add(forTeamShortNameLabel);
		
		JLabel forTeamFoundedYearLabel = new JLabel("Founded year");
		forTeamFoundedYearLabel.setBounds(23, 75, 73, 14);
		teamPanel.add(forTeamFoundedYearLabel);
		
		JLabel forTeamAchievementsLabel = new JLabel("Achievements");
		forTeamAchievementsLabel.setBounds(23, 100, 73, 14);
		teamPanel.add(forTeamAchievementsLabel);
		
		JLabel forTeamLocationLabel = new JLabel("Location");
		forTeamLocationLabel.setBounds(23, 125, 73, 14);
		teamPanel.add(forTeamLocationLabel);
		
		JLabel teamTeamNameLabel = new JLabel(userTeam.getName());
		teamTeamNameLabel.setBounds(106, 33, 73, 14);
		teamPanel.add(teamTeamNameLabel);
		
		JLabel teamShortNameLabel = new JLabel(userTeam.getShortName());
		teamShortNameLabel.setBounds(106, 54, 73, 14);
		teamPanel.add(teamShortNameLabel);
		
		JLabel teamFoundedYearLabel = new JLabel(Integer.toString(userTeam.getFoundedYear()));
		teamFoundedYearLabel.setBounds(106, 75, 73, 14);
		teamPanel.add(teamFoundedYearLabel);
		
		JLabel teamAchievementsLabel = new JLabel(Integer.toString(userTeam.getAchievements()));
		teamAchievementsLabel.setBounds(106, 100, 73, 14);
		teamPanel.add(teamAchievementsLabel);
		
		JLabel teamLocationLabel = new JLabel(userTeam.getLocation());
		teamLocationLabel.setBounds(106, 125, 73, 14);
		teamPanel.add(teamLocationLabel);
		
		JLabel teamLogoLabel = new JLabel(renderImage(ImagePath.LOGOS + userTeam.getLogo()));
		teamLogoLabel.setBounds(223, 33, 128, 97);
		teamPanel.add(teamLogoLabel);
		
		teamLogoEditButton = new JButton(renderImage(ImagePath.EDIT_IMG_ICON));
		teamLogoEditButton.setBounds(276, 125, 64, 46);
		teamLogoEditButton.setFocusPainted(false);
		teamLogoEditButton.setMargin(new Insets(0, 0, 0, 0));
		teamLogoEditButton.setContentAreaFilled(false);
		teamLogoEditButton.setBorderPainted(false);
		teamLogoEditButton.setOpaque(false);
		teamPanel.add(teamLogoEditButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 201, 340, 196);
		teamPanel.add(scrollPane);
		
		opponentTeamsTable = new TeamsTable(teamController.getOpponentTeams());
		opponentTeamsTable.update();
		scrollPane.setViewportView(opponentTeamsTable);
	}
	
	/**
	 * Loads the team players.
	 * @param userTeamPlayers
	 */
	private void loadTeamPlayersTab(HashMap<Integer, Player> userTeamPlayers) {
		JPanel playersPanel = new JPanel();
		personalInformationTabbedPane.addTab("Players", renderImage(ImagePath.TEAM_TAB_ICON), playersPanel, "Team players tab");
		playersPanel.setLayout(null);
		playersPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Team players", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		JScrollPane playersTableScrollPane = new JScrollPane();
		playersTableScrollPane.setBounds(10, 51, 364, 267);
		playersPanel.add(playersTableScrollPane);
		
		playersTable = new PlayersTable(userTeamPlayers);
		playersTable.update();
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
		
		createNewLeagueButton = new JButton("Create a new League");
		createNewLeagueButton.setFont(new Font("Tekton Pro Cond", Font.BOLD | Font.ITALIC, 26));
		createNewLeagueButton.setBackground(new Color(153, 255, 255));
		createNewLeagueButton.setForeground(new Color(255, 255, 255));
		createNewLeagueButton.setBounds(491, 381, 242, 49);
		getContentPane().add(createNewLeagueButton);
	}
	
	/**
	 * Loads the league information.
	 */
	private void loadLeagueTab(League currentLeague) {
		JTabbedPane leagueInformationTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		leagueInformationTabbedPane.setBounds(425, 65, 389, 495);
		getContentPane().add(leagueInformationTabbedPane);
		
		JPanel leaguePanel = new JPanel();
		leagueInformationTabbedPane.addTab("League", renderImage(ImagePath.LEAGUE_ICON), leaguePanel, "League tab");
		leaguePanel.setBorder(new TitledBorder(null, "League", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		leaguePanel.setLayout(null);
		
		JLabel forLeagueNameLabel = new JLabel("League name");
		forLeagueNameLabel.setBounds(24, 46, 99, 14);
		leaguePanel.add(forLeagueNameLabel);
		
		JLabel leagueNameLabel = new JLabel(currentLeague.getName());
		leagueNameLabel.setBounds(175, 46, 91, 14);
		leaguePanel.add(leagueNameLabel);

		JLabel forLeagueDescriptionLabel = new JLabel("Description");
		forLeagueDescriptionLabel.setBounds(24, 71, 99, 14);
		leaguePanel.add(forLeagueDescriptionLabel);
		
		JLabel leagueDescriptionLabel = new JLabel(currentLeague.getDescription());
		leagueDescriptionLabel.setBounds(175, 69, 91, 14);
		leaguePanel.add(leagueDescriptionLabel);
		
		JLabel forLeagueStartDateLabel = new JLabel("Start date");
		forLeagueStartDateLabel.setBounds(24, 94, 99, 14);
		leaguePanel.add(forLeagueStartDateLabel);
		
		JLabel leagueStartDateLabel = new JLabel(currentLeague.getStartDate());
		leagueStartDateLabel.setBounds(175, 94, 91, 14);
		leaguePanel.add(leagueStartDateLabel);
		
		JLabel forLeagueEndDateLabel = new JLabel("End date");
		forLeagueEndDateLabel.setBounds(24, 119, 99, 14);
		leaguePanel.add(forLeagueEndDateLabel);
		
		JLabel leagueEndDateLabel = new JLabel(currentLeague.getEndDate());
		leagueEndDateLabel.setBounds(175, 119, 91, 14);
		leaguePanel.add(leagueEndDateLabel);
		
		JLabel forLeaguePeriodLabel = new JLabel("Days left");
		forLeaguePeriodLabel.setBounds(24, 154, 99, 14);
		leaguePanel.add(forLeaguePeriodLabel);
		
		JLabel leaguePeriodLabel = new JLabel(Integer.toString(currentLeague.getPeriod()));
		leaguePeriodLabel.setBounds(175, 154, 91, 14);
		leaguePanel.add(leaguePeriodLabel);
		
		JLabel forLeagueNumberSeasonsLabel = new JLabel("Number of seasons");
		forLeagueNumberSeasonsLabel.setBounds(24, 195, 111, 14);
		leaguePanel.add(forLeagueNumberSeasonsLabel);
		
		JLabel leagueNumberSeasonsLabel = new JLabel(Integer.toString(currentLeague.getNumberSeasons()));
		leagueNumberSeasonsLabel.setBounds(175, 195, 91, 14);
		leaguePanel.add(leagueNumberSeasonsLabel);
		
		// TODO CHECK LEAGUE SEASONS MATCHES, if not button to create them
		JPanel seasonsPanel = new JPanel();
		seasonsPanel.setBorder(new TitledBorder(null, "Seasons", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		leagueInformationTabbedPane.addTab("Seasons", renderImage(ImagePath.SEASONS_TAB_ICON), seasonsPanel, "Season tab");
		seasonsPanel.setLayout(null);
		
		JPanel matchesPanel = new JPanel();
		matchesPanel.setBorder(new TitledBorder(null, "Matches", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		matchesPanel.setBounds(10, 93, 364, 353);
		seasonsPanel.add(matchesPanel);
		matchesPanel.setLayout(null);
		
		JScrollPane matchesTableScrollPane = new JScrollPane();
		matchesTableScrollPane.setBounds(10, 24, 344, 318);
		matchesPanel.add(matchesTableScrollPane);
		
		seasonsTable = new SeasonsTable(leagueController.getLeagueSeasons()); // TODO Take this from League object (CHANGE IN CONTROLLER TO ADD TO ITS ARRAYLIST ATTR)
		seasonsTable.update();
		matchesTableScrollPane.setViewportView(seasonsTable);
	}

	private void loadUserTab() {
		JLabel forUserTeamNameLabel = new JLabel("Teams");
		forUserTeamNameLabel.setBounds(23, 100, 73, 14);
		userPanel.add(forUserTeamNameLabel);
		
		JLabel userTeamNameLabel = new JLabel(Controller.getLoggedUser().getTeam().getName()); // TODO Why this
		userTeamNameLabel.setBounds(106, 100, 73, 14);
		userPanel.add(userTeamNameLabel);
		
		// TODO ALSO PUT THE TEAM LOGO¿?
		JLabel TODO_1 = new JLabel("LOG LAST MATCHES");
		TODO_1.setBounds(93, 207, 140, 20);
		userPanel.add(TODO_1);
	}
	
	@Override
	protected void loadListeners() {
		if (hasUserTeam) {
			teamLogoEditButton.addActionListener((e) -> {
				System.out.println("EDIT TEAM IMAGE\n");
			});
			if (hasTeamPlayers) {
				playersTable.getModel().addTableModelListener((e) -> {
					System.out.println("EDITED PLAYER: " + playersTable.getSelectedPlayer().getFirstName());
					System.out.println("EDITED COLUMN: " + playersTable.getEditedColumn() + "\n");
				});
			}
			if (isLeagueUnderway) {
				
			}
			else {
				createNewLeagueButton.addActionListener((e) -> changeView(this, CreateLeagueView.class));
			}
		}
		else {
			createNewTeamButton.addActionListener((e) -> {
				System.out.println("CREATE NEW TEAM\n");
			});
		}
	}

}
