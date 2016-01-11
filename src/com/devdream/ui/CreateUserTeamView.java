package com.devdream.ui;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.devdream.controller.Controller;
import com.devdream.controller.TeamController;
import com.devdream.exception.InvalidInputException;
import com.devdream.exception.OperationCancelledException;
import com.devdream.exception.RecordAlreadyException;
import com.devdream.ui.custom.Alert;
import com.devdream.util.UploadImage;

public class CreateUserTeamView extends View {
	private static final long serialVersionUID = 2729399989294510685L;

	//
	// Attributes
	private TeamController teamController;
	private String userUsername;
	private String logoImagePath;
	
	private JTextField nameTextField;
	private JTextField shortNameTextField;
	private JTextField foundedYearTextField;
	private JTextField locationTextField;
	
	private JButton selectLogoButton;
	private JButton newTeamButton;
	private JButton cancelButton;
	
	//
	// Constructors
	public CreateUserTeamView(TeamController teamController) {
		super(false);
		getContentPane().setLayout(null);
		
		this.teamController = teamController;
		userUsername = Controller.getLoggedUser().getUsername();
		
		loadUI();
		loadListeners();
		
		render();
	}
	
	//
	// Methods
	@Override
	protected void loadUI() {
		JLabel createTeamTitleLabel = new JLabel("Welcome " + userUsername + ", Create now your new team");
		createTeamTitleLabel.setFont(FontStyle.BOLD_FONT);
		createTeamTitleLabel.setBounds(21, 14, 368, 14);
		getContentPane().add(createTeamTitleLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "New team", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(21, 39, 368, 255);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel forNameLabel = new JLabel("Name");
		forNameLabel.setFont(FontStyle.BOLD_FONT);
		forNameLabel.setBounds(42, 35, 70, 14);
		panel.add(forNameLabel);
		
		nameTextField = new JTextField();
		nameTextField.setBounds(42, 58, 86, 20);
		panel.add(nameTextField);
		nameTextField.setColumns(10);
		
		shortNameTextField = new JTextField();
		shortNameTextField.setColumns(10);
		shortNameTextField.setBounds(42, 118, 86, 20);
		panel.add(shortNameTextField);
		
		JLabel forShortNameLabel = new JLabel("Short name");
		forShortNameLabel.setFont(FontStyle.BOLD_FONT);
		forShortNameLabel.setBounds(42, 93, 105, 14);
		panel.add(forShortNameLabel);
		
		foundedYearTextField = new JTextField();
		foundedYearTextField.setColumns(10);
		foundedYearTextField.setBounds(193, 58, 86, 20);
		panel.add(foundedYearTextField);
		
		JLabel forFoundedYearLabel = new JLabel("Founded year");
		forFoundedYearLabel.setFont(FontStyle.BOLD_FONT);
		forFoundedYearLabel.setBounds(193, 35, 121, 14);
		panel.add(forFoundedYearLabel);
		
		JLabel forLocationLabel = new JLabel("Location");
		forLocationLabel.setFont(FontStyle.BOLD_FONT);
		forLocationLabel.setBounds(193, 93, 86, 14);
		panel.add(forLocationLabel);
		
		locationTextField = new JTextField();
		locationTextField.setColumns(10);
		locationTextField.setBounds(193, 118, 86, 20);
		panel.add(locationTextField);
		
		JLabel lblLogo = new JLabel("Logo");
		lblLogo.setFont(FontStyle.BOLD_FONT);
		lblLogo.setBounds(42, 149, 86, 14);
		panel.add(lblLogo);
		
		selectLogoButton = new JButton("Choose your logo");
		selectLogoButton.setBounds(42, 178, 137, 23);
		panel.add(selectLogoButton);
		
		newTeamButton = new JButton("New team");
		newTeamButton.setIcon(renderImage(ImagePath.CREATE_ICON));
		newTeamButton.setHorizontalTextPosition(AbstractButton.LEFT);
		newTeamButton.setBounds(78, 305, 143, 45);
		getContentPane().add(newTeamButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setIcon(renderImage(ImagePath.CANCEL_RETURN_ICON));
		cancelButton.setHorizontalTextPosition(AbstractButton.LEFT);
		cancelButton.setBounds(231, 311, 108, 33);
		getContentPane().add(cancelButton);
	}

	@Override
	protected void loadListeners() {
		selectLogoButton.addActionListener((e) -> {
			try {
				Alert.showInfo(this, "Select the new image for your team.");

				String imgPath = Alert.showFileChooser("");
				UploadImage uploadImage = new UploadImage(imgPath);
				String fileName = uploadImage.uploadImage();
				logoImagePath = fileName;
				
				Alert.showInfo(this, fileName + " Image selected.");
			} catch (OperationCancelledException err) {
				Alert.showError(this, err.getMessage());
			} catch (IOException | URISyntaxException err) {
				Alert.showError(this, "Error selecting that image!");
			}
		});
		
		newTeamButton.addActionListener((e) -> {
			try {
				String name = nameTextField.getText();
				String shortName = shortNameTextField.getText();
				String foundedYear = foundedYearTextField.getText();
				String location = locationTextField.getText();
				String logo = logoImagePath == null ? ImagePath.DEFAULT_TEAM_LOGO : logoImagePath;
				
				teamController.submitUserTeam(userUsername,
						name, shortName, foundedYear, location, logo);
				
				Alert.showInfo(this, "Your team has been created! (You must login again to apply the changes)");
				changeView(this, LoginView.class);
			} catch (RecordAlreadyException | InvalidInputException err) {
				Alert.showError(this, err.getMessage());
			} catch(SQLException err) {
				Alert.showError(this, "Unable to connect to the database.");
			}
		});
		
		cancelButton.addActionListener((e) -> changeView(this));
	}

}
