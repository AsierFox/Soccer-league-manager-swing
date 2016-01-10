package com.devdream.ui;

import java.sql.SQLException;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.devdream.controller.TeamController;
import com.devdream.exception.InvalidInputException;
import com.devdream.exception.RecordAlreadyException;
import com.devdream.model.Team;
import com.devdream.ui.custom.Alert;
import com.devdream.ui.custom.TeamsTable;

public class CreateTeamView extends View {
	private static final long serialVersionUID = -4093989271963766524L;
	
	//
	// Attributes
	private TeamController teamController;
	
	private TeamsTable teamsTable;
	
	private JTextField nameTextField;
	private JTextField shortNameTextField;
	private JTextField foundedYearTextField;
	private JTextField locationTextField;
	
	private JButton newTeamButton;
	private JButton cancelButton;
	
	//
	// Constructors
	public CreateTeamView(TeamController teamController, TeamsTable teamsTable) {
		super(false);
		getContentPane().setLayout(null);
		
		this.teamController = teamController;
		this.teamsTable = teamsTable;
		
		loadUI();
		loadListeners();
		
		render();
	}
	
	//
	// Methods
	@Override
	protected void loadUI() {
		JLabel createTeamTitleLabel = new JLabel("Create team");
		createTeamTitleLabel.setBounds(396, 56, 99, 14);
		getContentPane().add(createTeamTitleLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "New team", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(155, 153, 534, 289);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel forNameLabel = new JLabel("Name");
		forNameLabel.setBounds(42, 48, 70, 14);
		panel.add(forNameLabel);
		
		nameTextField = new JTextField();
		nameTextField.setBounds(42, 75, 86, 20);
		panel.add(nameTextField);
		nameTextField.setColumns(10);
		
		shortNameTextField = new JTextField();
		shortNameTextField.setColumns(10);
		shortNameTextField.setBounds(42, 149, 86, 20);
		panel.add(shortNameTextField);
		
		JLabel forShortNameLabel = new JLabel("Short name");
		forShortNameLabel.setBounds(42, 122, 70, 14);
		panel.add(forShortNameLabel);
		
		foundedYearTextField = new JTextField();
		foundedYearTextField.setColumns(10);
		foundedYearTextField.setBounds(193, 75, 86, 20);
		panel.add(foundedYearTextField);
		
		JLabel forFoundedYearLabel = new JLabel("Founded year");
		forFoundedYearLabel.setBounds(193, 48, 86, 14);
		panel.add(forFoundedYearLabel);
		
		JLabel forLocationLabel = new JLabel("Location");
		forLocationLabel.setBounds(337, 48, 86, 14);
		panel.add(forLocationLabel);
		
		locationTextField = new JTextField();
		locationTextField.setColumns(10);
		locationTextField.setBounds(337, 75, 86, 20);
		panel.add(locationTextField);
		
		JLabel lblLogo = new JLabel("Logo");
		lblLogo.setBounds(337, 122, 86, 14);
		panel.add(lblLogo);
		
		newTeamButton = new JButton("New team");
		newTeamButton.setIcon(renderImage(ImagePath.CREATE_ICON));
		newTeamButton.setHorizontalTextPosition(AbstractButton.LEFT);
		newTeamButton.setBounds(280, 488, 239, 60);
		getContentPane().add(newTeamButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setIcon(renderImage(ImagePath.CANCEL_RETURN_ICON));
		cancelButton.setHorizontalTextPosition(AbstractButton.LEFT);
		cancelButton.setBounds(550, 496, 179, 45);
		getContentPane().add(cancelButton);
	}

	@Override
	protected void loadListeners() {
		newTeamButton.addActionListener((e) -> {
			try {
				String name = nameTextField.getText();
				String shortName = shortNameTextField.getText();
				String foundedYear = foundedYearTextField.getText();
				String location = locationTextField.getText();
				String logo = ""; // TODO GET PATH OF LOGO
				
				teamController.submitTeam(name, shortName, foundedYear, location, logo);
				teamsTable.addTeam(new Team(name, shortName, Integer.parseInt(foundedYear), location, logo));
				
				Alert.showInfo(this, "Team created!");
				if (Alert.showConfirm(this, "Return to the main view", "Do you wish to return to the main view?")) {
					dispose();
				} else {
					clearData();
				}
			} catch (RecordAlreadyException | InvalidInputException err) {
				Alert.showError(this, err.getMessage());
			} catch(SQLException err) {
				Alert.showError(this, "Unable to connect to the database.");
			}
		});
		
		cancelButton.addActionListener((e) -> dispose());
	}

	private void clearData() {
		nameTextField.setText("");
		shortNameTextField.setText("");
		foundedYearTextField.setText("");
		locationTextField.setText("");
		// TODO LOGO PATH
	}

}
