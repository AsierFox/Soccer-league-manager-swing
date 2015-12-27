package com.devdream.ui;

import java.awt.Color;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.devdream.controller.Controller;
import com.devdream.controller.TeamController;
import com.devdream.model.Player;
import com.devdream.model.Team;
import com.devdream.ui.custom.PlayersTable;

// TODO TEAM in A VARIABLE¿?
// TODO USER IN A VARIABLE¿?
public class MainView extends View {
	private static final long serialVersionUID = -6488927513006875327L;
	
	//
	// Attributes
	private TeamController teamController;
	private boolean hasUserTeam;
	private boolean hasTeamPlayers;
	
	private JTabbedPane personalInformationTabbedPane;
	private JPanel userPanel;
	private JPanel teamPanel;
	
	private PlayersTable playersTable;
	private JButton teamLogoEditButton;
	private JButton createNewTeamButton;
	
	public MainView() {
		super();
		getContentPane().setLayout(null);
		
		try {
			teamController = new TeamController();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		hasUserTeam = Controller.getLoggedUser().hasTeam();
		if (hasUserTeam) {
			hasTeamPlayers = teamController.getUserTeam().hasPlayers();
		} else {
			hasTeamPlayers = false;
		}
		
		loadUI();
		loadListeners();
		
		render();
	}
	
	@Override
	protected void loadUI() {
		personalInformationTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		personalInformationTabbedPane.setBounds(10, 55, 389, 505);
		getContentPane().add(personalInformationTabbedPane);
		
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
		
		JLabel userUsernameLabel = new JLabel(Controller.getLoggedUser().getUsername()); // TODO Don't like this line
		userUsernameLabel.setBounds(106, 33, 73, 14);
		userPanel.add(userUsernameLabel);
		
		JLabel userFirstNameLabel = new JLabel(Controller.getLoggedUser().getFirstName());
		userFirstNameLabel.setBounds(106, 54, 73, 14);
		userPanel.add(userFirstNameLabel);
		
		JLabel userSurnameLabel = new JLabel(Controller.getLoggedUser().getSurname());
		userSurnameLabel.setBounds(106, 75, 73, 14);
		userPanel.add(userSurnameLabel);
		
		if (hasUserTeam) {
			loadUserTeamLog();
		}
		else {
			loadUserHasNoTeam();
		}
		
		if (hasUserTeam) {
			loadTeamPane(teamController.getUserTeam());
			if (hasTeamPlayers) {
				loadTeamPlayers(teamController.getTeamPlayers());
			}
		}
		
		// TODO CHECK LEAGUES IF NOT SOW FEATURES
		JTabbedPane leagueInformationTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		leagueInformationTabbedPane.setBounds(425, 65, 389, 495);
		getContentPane().add(leagueInformationTabbedPane);
		
		JPanel leaguePanel = new JPanel();
		leagueInformationTabbedPane.addTab("League", renderImage(ImagePath.LEAGUE_ICON), leaguePanel, "League tab");
		leaguePanel.setBorder(new TitledBorder(null, "League", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		leaguePanel.setLayout(null);
		
		JPanel seasonsPanel = new JPanel();
		seasonsPanel.setBorder(new TitledBorder(null, "Seasons", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		leagueInformationTabbedPane.addTab("Sesons", renderImage(ImagePath.LEAGUE_ICON), seasonsPanel, "Season tab");
		
		JPanel matchesPanel = new JPanel();
		matchesPanel.setBorder(new TitledBorder(null, "Matches", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		leagueInformationTabbedPane.addTab("Matches", renderImage(ImagePath.LEAGUE_ICON), matchesPanel, "Matches tab");
	}

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
	
	private void showFeatures() {
		JLabel featuresLabel = new JLabel(renderImage(ImagePath.FEATURES_ICON));
		featuresLabel.setBounds(409, 111, 405, 316);
		getContentPane().add(featuresLabel);
	}

	@Override
	protected void loadListeners() {
		// TODO CHECK
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
		}
		else {
			createNewTeamButton.addActionListener((e) -> {
				System.out.println("CREATE NEW TEAM\n");
			});
		}
	}
	
	private void loadUserTeamLog() {
		JLabel forUserTeamNameLabel = new JLabel("Team");
		forUserTeamNameLabel.setBounds(23, 100, 73, 14);
		userPanel.add(forUserTeamNameLabel);
		
		JLabel userTeamNameLabel = new JLabel(Controller.getLoggedUser().getTeam().getName());
		userTeamNameLabel.setBounds(106, 100, 73, 14);
		userPanel.add(userTeamNameLabel);
		
		// TODO ALSO PUT THE TEAM LOGO¿?
		JLabel TODO_1 = new JLabel("LOG LAST MATCHES");
		TODO_1.setBounds(93, 207, 140, 20);
		userPanel.add(TODO_1);
	}
	
	private void loadTeamPane(Team userTeam) {
		teamPanel = new JPanel();
		personalInformationTabbedPane.addTab("Team", renderImage(ImagePath.TEAM_TAB_ICON), teamPanel, "User team tab");
		teamPanel.setLayout(null);
		teamPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Team", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		teamPanel.setVisible(Controller.getLoggedUser().hasTeam());
		
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
		teamLogoLabel.setBounds(223, 34, 120, 96);
		teamPanel.add(teamLogoLabel);
		
		teamLogoEditButton = new JButton(renderImage(ImagePath.EDIT_IMG_ICON));
		teamLogoEditButton.setBounds(276, 125, 64, 46);
		teamLogoEditButton.setFocusPainted(false);
		teamLogoEditButton.setMargin(new Insets(0, 0, 0, 0));
		teamLogoEditButton.setContentAreaFilled(false);
		teamLogoEditButton.setBorderPainted(false);
		teamLogoEditButton.setOpaque(false);
		teamPanel.add(teamLogoEditButton);
	}
	
	private void loadTeamPlayers(ArrayList<Player> userTeamPlayers) {
		JPanel playersPanel = new JPanel();
		playersPanel.setBorder(new TitledBorder(null, "Players", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		playersPanel.setBounds(23, 167, 320, 252);
		teamPanel.add(playersPanel);
		playersPanel.setLayout(null);
		
		JScrollPane playersTableScrollPane = new JScrollPane();
		playersTableScrollPane.setBounds(10, 32, 300, 196);
		playersPanel.add(playersTableScrollPane);
		
		playersTable = new PlayersTable(userTeamPlayers);
		playersTable.update();
		playersTableScrollPane.setViewportView(playersTable);
	}
	
}
