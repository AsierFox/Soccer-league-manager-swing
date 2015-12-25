package com.devdream.ui;

import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.devdream.ui.custom.Alert;

/**
 * Abstract view class for all views. The purpose of this class
 * y to organize the view code separating different behaviors.
 * 
 * @author Asier Gonzalez
 * @version 1.1
 * @since 1.0
 */
public abstract class View extends JFrame {
	private static final long serialVersionUID = 6433561099170298893L;
	
	//
	// Global
	/**
	 * Class for saving the image relative paths.
	 * 
	 * @author Asier Gonzalez
	 */
	static class ImagePath {
		private static final String IMG_DIR = "./img/";
		
		public static final String FAVICON = IMG_DIR + "favicon/favicon.png";
		public static final String LOGOS = IMG_DIR + "logo/";
	}
	
	/** Application title */
	public static final String APP_TITLE = "APP Title"; // TODO Set title for the application
	
	/** Height and Width of the JFrame. */
	public static final int WIDTH = 830, HEIGHT = 600;
	
	//
	// Constructors
	public View() {
		super();
		setTitle(APP_TITLE);
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null); // Center the window
		setResizable(false);
		setIconImage();
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
	
	/** Sets the Icon image to the JFrame */
	private void setIconImage() {
		try {
			setIconImage(ImageIO.read(View.class.getResource(ImagePath.FAVICON)));
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
			Alert.showError(this, "Error loading the icon image!");
		}
	}
	
}
