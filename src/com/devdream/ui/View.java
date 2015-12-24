package com.devdream.ui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * Abstract view class for all views. The purpose of this class
 * y to organize the view code separating different behaviors.
 * 
 * @author Asier Gonzalez
 * @version 1.0
 * @since 1.1
 */
public abstract class View extends JFrame{

	private static final long serialVersionUID = 5509695633560856542L;
	
	//
	// Global
	/** Application title */
	public static final String APP_TITLE = "APP Title"; // TODO Set title for the application
	/** Height and Width of the JFrame. */
	public static final int WIDTH = 830, HEIGHT = 600;
	
	//
	// Constructors
	public View() {
		super();
		setTitle(APP_TITLE);
		setIconImage();
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null); // Center the window
		setResizable(false);
	}
	
	//
	// Methods
	/** Renders an ImageIcon to show in the JLabel. */
	protected static ImageIcon renderImage(String filePath) {
		return new ImageIcon(View.class.getResource(filePath));
	}
	
	
	/** Renders the view. */
	protected void render() {
		setVisible(true);
	}
	
	/**
	 * Methods for loading the User Interface
	 * or the components of the view.
	 */
	protected abstract void loadUI();
	
	/**
	 * Load the listeners for the components
	 * of the view.
	 */
	protected abstract void loadListeners();
	
	/** Sets the Icon to the JFrame */
	private void setIconImage() {// TODO Set icon image
//		try {
//			setIconImage(ImageIO.read(View.class.getResource(AppData.ImagePath.FAVICON)));
//		} catch (IOException e) {
//			Alert.showError(this, e.getMessage());
//		}
	}
	
}
