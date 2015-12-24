package com.devdream.ui.custom;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.FocusManager;
import javax.swing.JTextField;

/**
 * Personalized component to add the placeholder
 * feature to a JTexField.
 * 
 * @author Asier Gonzalez
 * @version 1.0
 * @since 1.0
 */
public class TextFieldPlaceHolder extends JTextField {

	private static final long serialVersionUID = 3974099664208396403L;
	
	//
	// Attributes
	private String text;

	//
	// Constructors
	public TextFieldPlaceHolder(String text) {
		this.text = text;
	}
	
	//
	// Methods
	/** Sets the placeholder effect to to the JTextField. */
	@Override
	protected void paintComponent(java.awt.Graphics g) {
		super.paintComponent(g);
		if (getText().isEmpty() && !(FocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == this)) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setBackground(Color.gray);
			g2.setFont(getFont().deriveFont(Font.PLAIN));
			g2.drawString(text, 6, 13);
			g2.dispose();
		}
	}
	
}
