package com.devdream.ui;

import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.devdream.controller.TeamController;
import com.devdream.exception.InvalidInputException;
import com.devdream.exception.ItemAlreadyException;
import com.devdream.model.Player;
import com.devdream.ui.custom.Alert;
import com.devdream.ui.custom.PlayersTable;

public class CreatePlayerView extends View {
	private static final long serialVersionUID = 8857462047641506672L;
	
	//
	// Attributes
	private TeamController teamController;
	
	private PlayersTable playersTable;
	
	private JTextField firstNameTextField;
	private JTextField surnameTextField;
	private JTextField positionTextField;
	
	private JSpinner ageSpinner;
	private JSpinner dorsalSpinner;
	
	private JButton createPlayerButton;
	private JButton cancelButton;
	
	//
	// Constructors
	public CreatePlayerView(TeamController teamController, PlayersTable playersTable) {
		super(false);
		getContentPane().setLayout(null);
		
		this.teamController = teamController;
		this.playersTable = playersTable;
		
		loadUI();
		loadListeners();
		
		render();
	}
	
	//
	// Methods
	@Override
	protected void loadUI() {
		JLabel newPlayerViewTitle = new JLabel("New player");
		newPlayerViewTitle.setBounds(377, 51, 107, 14);
		getContentPane().add(newPlayerViewTitle);
		
		JPanel newPlayerPanel = new JPanel();
		newPlayerPanel.setBorder(new TitledBorder(null, "New player", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		newPlayerPanel.setBounds(71, 87, 643, 299);
		getContentPane().add(newPlayerPanel);
		newPlayerPanel.setLayout(null);
		
		JLabel forFirstNameLabel = new JLabel("First name");
		forFirstNameLabel.setBounds(38, 46, 82, 14);
		newPlayerPanel.add(forFirstNameLabel);
		
		firstNameTextField = new JTextField();
		firstNameTextField.setBounds(38, 71, 86, 20);
		newPlayerPanel.add(firstNameTextField);
		firstNameTextField.setColumns(10);
		
		JLabel forSurnameLabel = new JLabel("Surname");
		forSurnameLabel.setBounds(34, 115, 82, 14);
		newPlayerPanel.add(forSurnameLabel);
		
		surnameTextField = new JTextField();
		surnameTextField.setColumns(10);
		surnameTextField.setBounds(34, 140, 86, 20);
		newPlayerPanel.add(surnameTextField);
		
		JLabel forAgeLabel = new JLabel("Age");
		forAgeLabel.setBounds(38, 183, 46, 14);
		newPlayerPanel.add(forAgeLabel);
		
		ageSpinner = new JSpinner();
		ageSpinner.setBounds(38, 208, 46, 20);
		newPlayerPanel.add(ageSpinner);
		
		JLabel forDorsalLabel = new JLabel("Dorsal");
		forDorsalLabel.setBounds(175, 46, 46, 14);
		newPlayerPanel.add(forDorsalLabel);
		
		dorsalSpinner = new JSpinner();
		dorsalSpinner.setBounds(175, 71, 46, 20);
		newPlayerPanel.add(dorsalSpinner);
		
		positionTextField = new JTextField();
		positionTextField.setBounds(170, 140, 86, 20);
		newPlayerPanel.add(positionTextField);
		positionTextField.setColumns(10);
		
		JLabel forPositionLabel = new JLabel("Position");
		forPositionLabel.setBounds(174, 115, 82, 14);
		newPlayerPanel.add(forPositionLabel);
		
		createPlayerButton = new JButton("Create player");
		createPlayerButton.setBounds(155, 454, 215, 52);
		getContentPane().add(createPlayerButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(395, 454, 215, 52);
		getContentPane().add(cancelButton);
	}
	
	@Override
	protected void loadListeners() {
		createPlayerButton.addActionListener((e) -> {
			try {
				String firstName = firstNameTextField.getText();
				String surname = surnameTextField.getText();
				int age = (Integer) ageSpinner.getValue();
				String pos = positionTextField.getText();
				int dorsal = (Integer) dorsalSpinner.getValue();
				
				teamController.submitPlayer(teamController.getUserTeam().getId(), firstName, surname, age, pos, dorsal);
				playersTable.addPlayer(new Player(firstName, surname, age, dorsal, pos));
				
				Alert.showInfo(this, "Player created!");
				if (Alert.showConfirm(this, "Return to the main view", "Do you wish to return to the main view?") == 0) {
					dispose();
				} else {
					clearData();
				}
			} catch (ItemAlreadyException | InvalidInputException err) {
				Alert.showError(this, err.getMessage());
			} catch(SQLException err) {
				Alert.showError(this, "Unable to connect to the database.");
			}
		});
		
		cancelButton.addActionListener((e) -> dispose());
	}

	private void clearData() {
		firstNameTextField.setText("");
		surnameTextField.setText("");
		ageSpinner.setValue(0);
		positionTextField.setText("");
		dorsalSpinner.setValue(0);
	}
	
}
