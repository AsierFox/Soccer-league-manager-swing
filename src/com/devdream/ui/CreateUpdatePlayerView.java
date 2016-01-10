package com.devdream.ui;

import java.sql.SQLException;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.devdream.controller.PlayerController;
import com.devdream.controller.TeamController;
import com.devdream.exception.InvalidInputException;
import com.devdream.exception.RecordAlreadyException;
import com.devdream.model.Player;
import com.devdream.ui.custom.Alert;
import com.devdream.ui.custom.PlayersTable;

public class CreateUpdatePlayerView extends View {
	private static final long serialVersionUID = 8857462047641506672L;
	
	//
	// Attributes
	private Player player;
	private boolean modify;
	
	private PlayerController playerController;
	
	private TeamController teamController;
	private PlayersTable playersTable;
	
	private JTextField firstNameTextField;
	private JTextField surnameTextField;
	private JTextField positionTextField;
	
	private JSpinner ageSpinner;
	private JSpinner dorsalSpinner;
	
	private JButton createUpdatePlayerButton;
	private JButton cancelButton;
	
	//
	// Constructors
	/**
	 * @wbp.parser.constructor
	 */
	public CreateUpdatePlayerView(TeamController teamController, PlayersTable playersTable) {
		super(false);
		getContentPane().setLayout(null);
		
		this.teamController = teamController;
		playerController = new PlayerController();
		this.playersTable = playersTable;
		player = new Player();
		modify = false;
		
		loadUI();
		loadListeners();
		
		render();
	}
	
	public CreateUpdatePlayerView(TeamController teamController, PlayersTable playersTable, Player selPlayer) {
		super(false);
		getContentPane().setLayout(null);
		
		this.teamController = teamController;
		playerController = new PlayerController();
		this.playersTable = playersTable;
		player = selPlayer;
		modify = true;
		
		loadUI();
		loadListeners();
		
		render();
	}
	
	//
	// Methods
	@Override
	protected void loadUI() {
		JLabel newPlayerViewTitle = new JLabel(printCreateModify() + " new player");
		newPlayerViewTitle.setFont(FontStyle.TITLE_FONT);
		newPlayerViewTitle.setBounds(113, 22, 198, 14);
		getContentPane().add(newPlayerViewTitle);
		
		JPanel newPlayerPanel = new JPanel();
		newPlayerPanel.setBorder(new TitledBorder(null, "New player", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		newPlayerPanel.setBounds(29, 65, 365, 236);
		getContentPane().add(newPlayerPanel);
		newPlayerPanel.setLayout(null);
		
		JLabel forFirstNameLabel = new JLabel("First name");
		forFirstNameLabel.setFont(FontStyle.BOLD_FONT);
		forFirstNameLabel.setBounds(30, 26, 82, 14);
		newPlayerPanel.add(forFirstNameLabel);
		
		firstNameTextField = new JTextField(player.getFirstName());
		firstNameTextField.setBounds(30, 51, 122, 27);
		newPlayerPanel.add(firstNameTextField);
		firstNameTextField.setColumns(10);
		
		JLabel forSurnameLabel = new JLabel("Surname");
		forSurnameLabel.setFont(FontStyle.BOLD_FONT);
		forSurnameLabel.setBounds(30, 89, 82, 14);
		newPlayerPanel.add(forSurnameLabel);
		
		surnameTextField = new JTextField(player.getSurname());
		surnameTextField.setColumns(10);
		surnameTextField.setBounds(30, 114, 126, 27);
		newPlayerPanel.add(surnameTextField);
		
		JLabel forAgeLabel = new JLabel("Age");
		forAgeLabel.setFont(FontStyle.BOLD_FONT);
		forAgeLabel.setBounds(34, 152, 46, 14);
		newPlayerPanel.add(forAgeLabel);
		
		ageSpinner = new JSpinner();
		ageSpinner.setValue(player.getAge());
		ageSpinner.setBounds(34, 177, 60, 27);
		newPlayerPanel.add(ageSpinner);
		
		JLabel forDorsalLabel = new JLabel("Dorsal");
		forDorsalLabel.setFont(FontStyle.BOLD_FONT);
		forDorsalLabel.setBounds(227, 89, 46, 14);
		newPlayerPanel.add(forDorsalLabel);
		
		dorsalSpinner = new JSpinner();
		dorsalSpinner.setValue(player.getDorsal());
		dorsalSpinner.setBounds(227, 114, 60, 27);
		newPlayerPanel.add(dorsalSpinner);
		
		positionTextField = new JTextField();
		positionTextField.setText(player.getPosition());
		positionTextField.setBounds(227, 54, 106, 24);
		newPlayerPanel.add(positionTextField);
		positionTextField.setColumns(10);
		
		JLabel forPositionLabel = new JLabel("Position");
		forPositionLabel.setFont(FontStyle.BOLD_FONT);
		forPositionLabel.setBounds(229, 26, 82, 14);
		newPlayerPanel.add(forPositionLabel);
		
		createUpdatePlayerButton = new JButton(printCreateModify() + " player");
		createUpdatePlayerButton.setIcon(renderImage(ImagePath.CREATE_ICON));
		createUpdatePlayerButton.setHorizontalTextPosition(AbstractButton.LEFT);
		createUpdatePlayerButton.setBounds(70, 312, 143, 42);
		getContentPane().add(createUpdatePlayerButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setIcon(renderImage(ImagePath.CANCEL_RETURN_ICON));
		cancelButton.setHorizontalTextPosition(AbstractButton.LEFT);
		cancelButton.setBounds(239, 317, 107, 33);
		getContentPane().add(cancelButton);
	}
	
	@Override
	protected void loadListeners() {
		createUpdatePlayerButton.addActionListener((e) -> {
			try {
				String firstName = firstNameTextField.getText();
				String surname = surnameTextField.getText();
				int age = (Integer) ageSpinner.getValue();
				String position = positionTextField.getText();
				int dorsal = (Integer) dorsalSpinner.getValue();
				
				if (modify) {
					playerController.updatePlayer(firstName, surname, age, position, dorsal, player.getDorsal());
					player.modifyPlayer(firstName, surname, age, dorsal, position);
					playersTable.update();
				} else {
					playerController.submitPlayer(teamController.getUserTeam().getId(),
							firstName, surname, age, position, dorsal);
					playersTable.addPlayer(new Player(firstName, surname, age, dorsal, position));
				}
				Alert.showInfo(this, "Player " + (modify ? "modified" : "created") + "!");
				askReturn();
			} catch (RecordAlreadyException | InvalidInputException err) {
				Alert.showError(this, err.getMessage());
			} catch(SQLException err) {
				err.printStackTrace();
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
	
	private String printCreateModify() {
		return modify ? "Modify" : "Create";
	}
	
	private void askReturn() {
		if (Alert.showConfirm(this, "Return to the main view", "Do you wish to return to the main view?")) {
			dispose();
		} else if (!modify) {
			clearData();
		}
	}

}
