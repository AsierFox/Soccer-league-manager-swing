package com.devdream.ui;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.devdream.controller.LeagueController;
import com.devdream.controller.TeamController;
import com.devdream.exception.InvalidInputException;
import com.devdream.exception.LeagueUnderwayException;
import com.devdream.exception.NotTeamSelectedException;
import com.devdream.model.Team;
import com.devdream.ui.custom.Alert;
import com.devdream.ui.custom.DateObserverTextField;
import com.devdream.ui.custom.TeamsTable;
import com.qt.datapicker.DatePicker;

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
	// Contructors
	public CreateLeagueView() {
		super();
		getContentPane().setLayout(null);
		
		try {
			leagueController = new LeagueController();
			teamController = new TeamController();
		} catch (SQLException e) {
			Alert.showError(this, "Error connecting to the database");
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
		
		JLabel forEndDateLabel = new JLabel("End date");
		forEndDateLabel.setBounds(367, 95, 86, 14);
		createLeaguePanel.add(forEndDateLabel);
		
		JLabel forStartDateLabel = new JLabel("Start date");
		forStartDateLabel.setBounds(367, 40, 86, 14);
		createLeaguePanel.add(forStartDateLabel);
		
		JLabel forDescriptionLabel = new JLabel("Description");
		forDescriptionLabel.setBounds(210, 40, 82, 14);
		createLeaguePanel.add(forDescriptionLabel);

		descriptionEditorPane = new JEditorPane();
		descriptionEditorPane.setBounds(149, 65, 196, 130);
		createLeaguePanel.add(descriptionEditorPane);
		
		nameTextField = new JTextField();
		nameTextField.setBounds(37, 98, 86, 20);
		createLeaguePanel.add(nameTextField);
		nameTextField.setColumns(10);
		JLabel forNameLabel = new JLabel("Name");
		forNameLabel.setBounds(37, 72, 82, 14);
		createLeaguePanel.add(forNameLabel);
		
		JPanel startDatePanel = new JPanel();
		startDatePanel.setBounds(463, 29, 236, 39);
		createLeaguePanel.add(startDatePanel);
		startDatePanel.setLayout(null);
		
		startDateTextField = new DateObserverTextField();
		startDateTextField.setEditable(false);
		startDateTextField.setBounds(10, 11, 86, 20);
		startDatePanel.add(startDateTextField);
		
		startDateButton = new JButton("Start date");
		startDateButton.setBounds(118, 10, 89, 23);
		startDatePanel.add(startDateButton);
		
		JPanel endDatePanel = new JPanel();
		endDatePanel.setLayout(null);
		endDatePanel.setBounds(463, 87, 236, 39);
		createLeaguePanel.add(endDatePanel);
		
		endDateTextField = new DateObserverTextField();
		endDateTextField.setEditable(false);
		endDateTextField.setBounds(10, 11, 86, 20);
		endDatePanel.add(endDateTextField);
		
		endDateButton = new JButton("End date");
		endDateButton.setBounds(118, 10, 89, 23);
		endDatePanel.add(endDateButton);
		
		JScrollPane avaliableOpponentsScrollPane = new JScrollPane();
		avaliableOpponentsScrollPane.setBounds(22, 248, 323, 156);
		createLeaguePanel.add(avaliableOpponentsScrollPane);
		
		avaliableOpponentTeamsTable = new TeamsTable(teamController.getOpponentTeams());
		avaliableOpponentTeamsTable.update();
		avaliableOpponentsScrollPane.setViewportView(avaliableOpponentTeamsTable);
		
		JScrollPane selectedOpponentsScrollPane = new JScrollPane();
		selectedOpponentsScrollPane.setBounds(435, 248, 332, 156);
		createLeaguePanel.add(selectedOpponentsScrollPane);
		
		selectedOpponentsTable = new TeamsTable();
		selectedOpponentsScrollPane.setViewportView(selectedOpponentsTable);
		
		JLabel forAvaliableOpponentsTable = new JLabel("Avaliable opponents teams");
		forAvaliableOpponentsTable.setBounds(37, 210, 196, 14);
		createLeaguePanel.add(forAvaliableOpponentsTable);
		
		JLabel opponentsTeamsLabel = new JLabel("Opponents teams");
		opponentsTeamsLabel.setBounds(571, 162, 196, 14);
		createLeaguePanel.add(opponentsTeamsLabel);
		
		addOpponentTeamButton = new JButton(">>");
		addOpponentTeamButton.setBounds(355, 286, 70, 23);
		createLeaguePanel.add(addOpponentTeamButton);
		
		removeOpponentTeamButton = new JButton("<<");
		removeOpponentTeamButton.setBounds(355, 320, 70, 23);
		createLeaguePanel.add(removeOpponentTeamButton);
		
		createNewLeagueButton = new JButton("Create the new league");
		createNewLeagueButton.setBounds(305, 487, 233, 57);
		getContentPane().add(createNewLeagueButton);
		
		cancelButton = new JButton("Cancel");
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
			} catch (NotTeamSelectedException err) {
				Alert.showError(this, err.getMessage());
			}
		});
		
		removeOpponentTeamButton.addActionListener((e) -> {
			try {
				Team selectedTeam = selectedOpponentsTable.getSelectedTeam();
				avaliableOpponentTeamsTable.addTeam(selectedTeam);
				selectedOpponentsTable.removeTeam(selectedTeam);
			} catch (NotTeamSelectedException err) {
				Alert.showError(this, err.getMessage());
			}
		});
		
		createNewLeagueButton.addActionListener((e) -> {
			try {
				leagueController.createNewLeague(nameTextField.getText(), startDateTextField.getText(), endDateTextField.getText(),
						descriptionEditorPane.getText(), selectedOpponentsTable.getTeams());
				Alert.showInfo(this, "League created successfully.");
				
				leagueController.generateSeasonsPairing(selectedOpponentsTable.getTeams());
				Alert.showInfo(this, "Seasons generated successfully.");
				
				clearData();
			} catch(SQLException | InvalidInputException | LeagueUnderwayException err) {
				Alert.showError(this, err.getMessage());
			}
		});
		
		cancelButton.addActionListener((e) -> changeView(this));
	}
	
	private void clearData() {
		nameTextField.setText("");
		descriptionEditorPane.setText("");
		startDateTextField.setText("");
		endDateTextField.setText("");
	}
	
}
