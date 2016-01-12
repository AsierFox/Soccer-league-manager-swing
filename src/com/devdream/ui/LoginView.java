package com.devdream.ui;

import java.awt.Color;
import java.awt.SystemColor;
import java.sql.SQLException;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import com.devdream.controller.LoginController;
import com.devdream.exception.InvalidInputException;
import com.devdream.exception.LoginFailedException;
import com.devdream.ui.custom.Alert;
import java.awt.Font;

/**
 * Login view of the application. It is going to login the customer who is going
 * to attend the clients.
 * 
 * @author Asier Gonzalez
 */
public class LoginView extends View {
	private static final long serialVersionUID = 872752750081624433L;
	
	//
	// Attributes
	private LoginController loginController;

	private JTextField usernameTextField;
	private JPasswordField passwordTextField;
	private JButton loginButton;
	private JButton exitButton;

	//
	// Constructors
	public LoginView() {
		super();
		getContentPane().setLayout(null);
		getContentPane().setBackground(SystemColor.control);
		
		loginController = new LoginController();
		
		loadUI();
		loadListeners();
		
		render();
	}

	@Override
	protected void loadUI() {
		JPanel loginPanel = new JPanel();
		loginPanel.setBounds(276, 143, 436, 251);
		loginPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Login", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(204, 204, 255)));
		getContentPane().add(loginPanel);
		loginPanel.setLayout(null);
		
		JLabel forUsernameHintLabel = new JLabel("mikel");
		forUsernameHintLabel.setFont(new Font("Tahoma", Font.ITALIC, 10));
		forUsernameHintLabel.setBounds(362, 53, 46, 14);
		loginPanel.add(forUsernameHintLabel);
		
		JLabel forPasswordHintLabel = new JLabel("123");
		forPasswordHintLabel.setFont(new Font("Tahoma", Font.ITALIC, 10));
		forPasswordHintLabel.setBounds(362, 96, 46, 14);
		loginPanel.add(forPasswordHintLabel);

		JLabel appTitleLabel = new JLabel("Soccer Manager");
		appTitleLabel.setFont(FontStyle.TITLE_FONT);
		appTitleLabel.setBounds(413, 109, 230, 23);
		getContentPane().add(appTitleLabel);
		
		JLabel logoImageLabel = new JLabel(renderImage(ImagePath.LOGO));
		logoImageLabel.setBounds(10, 133, 256, 265);
		getContentPane().add(logoImageLabel);

		JLabel forUsernameLabel = new JLabel("Username");
		forUsernameLabel.setFont(FontStyle.BOLD_FONT);
		forUsernameLabel.setBounds(85, 53, 104, 14);
		loginPanel.add(forUsernameLabel);

		JLabel forPasswordLabel = new JLabel("Password");
		forPasswordLabel.setFont(FontStyle.BOLD_FONT);
		forPasswordLabel.setBounds(85, 96, 104, 14);
		loginPanel.add(forPasswordLabel);

		usernameTextField = new JTextField();
		usernameTextField.setBounds(200, 50, 152, 20);
		usernameTextField.setColumns(10);
		loginPanel.add(usernameTextField);

		passwordTextField = new JPasswordField();
		passwordTextField.setBounds(199, 93, 153, 20);
		passwordTextField.setColumns(10);
		loginPanel.add(passwordTextField);

		loginButton = new JButton("Login");
		loginButton.setIcon(renderImage(ImagePath.LOGIN_ICON));
		loginButton.setHorizontalTextPosition(AbstractButton.LEFT);
		loginButton.setBounds(68, 161, 146, 47);
		loginPanel.add(loginButton);

		exitButton = new JButton("Exit");
		exitButton.setIcon(renderImage(ImagePath.EXIT_ICON));
		exitButton.setHorizontalTextPosition(AbstractButton.LEFT);
		exitButton.setBounds(244, 161, 146, 47);
		loginPanel.add(exitButton);
	}

	@Override
	protected void loadListeners() {
		loginButton.addActionListener((e) -> {
			try {
				loginController.login(usernameTextField.getText(), String.valueOf(passwordTextField.getPassword()));
				changeView(this);
			} catch (InvalidInputException | LoginFailedException | SQLException err) {
				Alert.showError(this, err.getMessage());
			}
		});
		
		exitButton.addActionListener((e) -> super.sendExitAppRequest());
	}
}
