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
		loginPanel.setBounds(276, 117, 436, 251);
		loginPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Login", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(204, 204, 255)));
		getContentPane().add(loginPanel);
		loginPanel.setLayout(null);

		JLabel logoImageLabel = new JLabel(renderImage(ImagePath.LOGO));
		logoImageLabel.setBounds(10, 103, 256, 265);
		getContentPane().add(logoImageLabel);

		JLabel forUsernameLabel = new JLabel("Username");
		forUsernameLabel.setBounds(85, 53, 74, 14);
		loginPanel.add(forUsernameLabel);

		JLabel forPasswordLabel = new JLabel("Password");
		forPasswordLabel.setBounds(85, 96, 58, 14);
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
