package com.devdream.ui;

import java.sql.SQLException;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.devdream.controller.LeagueController;
import com.devdream.controller.TeamController;
import com.devdream.db.DBConnectionManager;
import com.devdream.exception.InvalidInputException;
import com.devdream.exception.LeagueUnderwayException;
import com.devdream.exception.NotTableItemSelectedException;
import com.devdream.model.Team;
import com.devdream.ui.custom.Alert;
import com.devdream.ui.custom.DateObserverTextField;
import com.devdream.ui.custom.TeamsTable;
import com.qt.datapicker.DatePicker;

/**
 * The view for creating a new league.
 * 
 * @author Asier Gonzalez
 */
public class CreateLeagueView extends View {
	private static final long serialVersionUID = 2459181120724606930L;
	
	//
	// Attributes
	private LeagueController leagueController;
	private TeamController teamController;
	
	private JTextField nameTextField;

	private JEditorPane descriptionEditorPane;
	
	private DateObserverTextField startDateTextField;
	private DateObserverTextField endDateTextField;
	
	private TeamsTable avaliableOpponentTeamsTable;
	private TeamsTable selectedOpponentsTable;
	
	private JButton startDateButton;
	private JButton endDateButton;
	private JButton addOpponentTeamButton;
	private JButton removeOpponentTeamButton;
	private JButton createNewLeagueButton;
	private JButton cancelButton;

	//
	// Constructors
	public CreateLeagueView() {
		super();
		getContentPane().setLayout(null);
		
		try {
			leagueController = new LeagueController();
			teamController = new TeamController();
		} catch (SQLException e) {
			Alert.showError(this, DBConnectionManager.CONNECT_ERROR_MSG);
		}
		
		loadUI();
		loadListeners();
		
		render();
	}
	
	//
	// Methods
	@Override
	protected void loadUI() {
		JPanel createLeaguePanel = new JPanel();
		createLeaguePanel.setBorder(new TitledBorder(null, "Create league", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		createLeaguePanel.setBounds(37, 37, 777, 423);
		getContentPane().add(createLeaguePanel);
		createLeaguePanel.setLayout(null);
		
		nameTextField = new JTextField();
		nameTextField.setBounds(22, 58, 196, 31);
		createLeaguePanel.add(nameTextField);
		nameTextField.setColumns(10);
		
		JLabel forNameLabel = new JLabel("Name");
		forNameLabel.setFont(FontStyle.BOLD_FONT);
		forNameLabel.setBounds(22, 37, 82, 14);
		createLeaguePanel.add(forNameLabel);
		
		JLabel forDescriptionLabel = new JLabel("Description");
		forDescriptionLabel.setFont(FontStyle.BOLD_FONT);
		forDescriptionLabel.setBounds(22, 107, 82, 14);
		createLeaguePanel.add(forDescriptionLabel);
		
		descriptionEditorPane = new JEditorPane();
		descriptionEditorPane.setBounds(22, 128, 298, 71);
		createLeaguePanel.add(descriptionEditorPane);
		
		JLabel forStartDateLabel = new JLabel("Start date");
		forStartDateLabel.setBounds(413, 62, 86, 14);
		createLeaguePanel.add(forStartDateLabel);
		
		JPanel startDatePanel = new JPanel();
		startDatePanel.setFont(FontStyle.BOLD_FONT);
		startDatePanel.setBounds(509, 54, 236, 39);
		createLeaguePanel.add(startDatePanel);
		startDatePanel.setLayout(null);
		
		startDateTextField = new DateObserverTextField();
		startDateTextField.setEditable(false);
		startDateTextField.setBounds(10, 11, 98, 20);
		startDatePanel.add(startDateTextField);
		
		startDateButton = new JButton("Start date");
		startDateButton.setBounds(118, 10, 108, 23);
		startDatePanel.add(startDateButton);

		JLabel forEndDateLabel = new JLabel("End date");
		forEndDateLabel.setFont(FontStyle.BOLD_FONT);
		forEndDateLabel.setBounds(413, 124, 86, 14);
		createLeaguePanel.add(forEndDateLabel);
		
		JPanel endDatePanel = new JPanel();
		endDatePanel.setLayout(null);
		endDatePanel.setBounds(509, 120, 236, 39);
		createLeaguePanel.add(endDatePanel);
		
		endDateTextField = new DateObserverTextField();
		endDateTextField.setEditable(false);
		endDateTextField.setBounds(10, 11, 98, 20);
		endDatePanel.add(endDateTextField);
		
		endDateButton = new JButton("End date");
		endDateButton.setBounds(118, 10, 108, 23);
		endDatePanel.add(endDateButton);
		
		JScrollPane avaliableOpponentsScrollPane = new JScrollPane();
		avaliableOpponentsScrollPane.setBounds(22, 248, 323, 156);
		createLeaguePanel.add(avaliableOpponentsScrollPane);
		
		JLabel opponentsTeamsLabel = new JLabel("Opponents teams");
		opponentsTeamsLabel.setFont(FontStyle.BOLD_FONT);
		opponentsTeamsLabel.setBounds(435, 210, 196, 14);
		createLeaguePanel.add(opponentsTeamsLabel);

		JLabel forAvaliableOpponentsTable = new JLabel("Avaliable opponents teams");
		forAvaliableOpponentsTable.setFont(FontStyle.BOLD_FONT);
		forAvaliableOpponentsTable.setBounds(22, 210, 196, 14);
		createLeaguePanel.add(forAvaliableOpponentsTable);
		
		avaliableOpponentTeamsTable = new TeamsTable(teamController.getOpponentTeams());
		avaliableOpponentTeamsTable.update();
		avaliableOpponentsScrollPane.setViewportView(avaliableOpponentTeamsTable);
		
		JScrollPane selectedOpponentsScrollPane = new JScrollPane();
		selectedOpponentsScrollPane.setBounds(435, 248, 332, 156);
		createLeaguePanel.add(selectedOpponentsScrollPane);
		
		selectedOpponentsTable = new TeamsTable();
		selectedOpponentsScrollPane.setViewportView(selectedOpponentsTable);
		
		addOpponentTeamButton = new JButton(">>");
		addOpponentTeamButton.setBounds(355, 294, 70, 23);
		createLeaguePanel.add(addOpponentTeamButton);
		
		removeOpponentTeamButton = new JButton("<<");
		removeOpponentTeamButton.setBounds(355, 328, 70, 23);
		createLeaguePanel.add(removeOpponentTeamButton);
		
		createNewLeagueButton = new JButton("Create the new league");
		createNewLeagueButton.setIcon(renderImage(ImagePath.CREATE_ICON));
		createNewLeagueButton.setHorizontalTextPosition(AbstractButton.LEFT);
		createNewLeagueButton.setBounds(305, 487, 233, 57);
		getContentPane().add(createNewLeagueButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setIcon(renderImage(ImagePath.CANCEL_RETURN_ICON));
		cancelButton.setHorizontalTextPosition(AbstractButton.LEFT);
		cancelButton.setBounds(579, 494, 130, 42);
		getContentPane().add(cancelButton);
	}

	@Override
	protected void loadListeners() {
		startDateButton.addActionListener((e) -> {
	        DatePicker dp = new DatePicker(startDateTextField);
	        dp.parseDate(startDateTextField.getText());
	        dp.start(startDateTextField);
		});
		
		endDateButton.addActionListener((e) -> {
	        DatePicker dp = new DatePicker(endDateTextField);
	        dp.parseDate(endDateTextField.getText());
	        dp.start(endDateTextField);
		});
		
		addOpponentTeamButton.addActionListener((e) -> {
			try {
				Team selectedTeam = avaliableOpponentTeamsTable.getSelectedTeam();
				if (!selectedOpponentsTable.hasAlreadyTeam(selectedTeam)) {
					selectedOpponentsTable.addTeam(selectedTeam);
					avaliableOpponentTeamsTable.removeTeam(selectedTeam);
				}
				else {
					Alert.showError(this, "This team is already selected!");
				}
			} catch (NotTableItemSelectedException err) {
				Alert.showError(this, err.getMessage());
			}
		});
		
		removeOpponentTeamButton.addActionListener((e) -> {
			try {
				Team selectedTeam = selectedOpponentsTable.getSelectedTeam();
				avaliableOpponentTeamsTable.addTeam(selectedTeam);
				selectedOpponentsTable.removeTeam(selectedTeam);
			} catch (NotTableItemSelectedException err) {
				Alert.showError(this, err.getMessage());
			}
		});
		
		createNewLeagueButton.addActionListener((e) -> {
			try {
				int leagueId = leagueController.submitNewLeague(nameTextField.getText(), startDateTextField.getText(), endDateTextField.getText(),
						descriptionEditorPane.getText(), selectedOpponentsTable.getTeams());
				Alert.showInfo(this, "League created successfully.");
				
				leagueController.submitSeasonsGames(leagueId, selectedOpponentsTable.getTeams());
				Alert.showInfo(this, "Seasons generated successfully.");
				
				changeView(this);
			} catch(SQLException | InvalidInputException | LeagueUnderwayException err) {
				Alert.showError(this, err.getMessage());
			}
		});
		
		cancelButton.addActionListener((e) -> changeView(this));
	}

}
