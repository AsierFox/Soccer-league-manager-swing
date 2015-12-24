package com.devdream.ui;

import javax.swing.JButton;

/**
 * Login view of the application. It is going to login
 * the customer who is going to attend the clients.
 * 
 * @author Asier Gonzalez
 * @version 1.0
 * @since 1.0
 */
public class LoginView extends View {

	private static final long serialVersionUID = 872752750081624433L;
	
	//
	// Attributes
	private JButton loginButton;
	private JButton exitButton;
	
	//
	// Constructors
	public LoginView() {
		super();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		
		loadUI();

		loadListeners();
		
		render();
	}
	
	@Override
	protected void loadUI() {
		// Exit button
		exitButton = new JButton("Exit");
		exitButton.setBounds(158, 117, 131, 33);
		add(exitButton);
		
		// Login button
		loginButton = new JButton("Login");
		loginButton.setBounds(20, 117, 127, 33);
		add(loginButton);
	}
	
	@Override
	protected void loadListeners() {
	}
	
}
