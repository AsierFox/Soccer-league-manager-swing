package com.devdream.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.devdream.main.App;
import com.devdream.ui.custom.Alert;

/**
 * Abstract view class for all views. The purpose of this class
 * y to organize the view code separating different behaviors.
 * 
 * @author Asier Gonzalez
 */
public abstract class View extends JFrame {
	private static final long serialVersionUID = 6433561099170298893L;
	
	/**
	 * Class for saving the image relative paths.
	 * 
	 * @author Asier Gonzalez
	 */
	static class ImagePath {
		private static final String IMG_DIR = "/img/";
		
		protected static final String LOGOS = IMG_DIR + "logo/";
		protected static final String ICONS = IMG_DIR + "icon/";
		
		private static final String FAVICON = IMG_DIR + "favicon/favicon.png";
		
		protected static final String LOGO = LOGOS + "app-logo.png";
		
		protected static final String LOGIN_ICON = ICONS + "login.png";
		protected static final String EXIT_ICON = ICONS + "exit.png";
		
		protected static final String FEATURES_ICON = ICONS + "features.png";
		
		protected static final String USER_TAB_ICON = ICONS + "user-tab.png";
		protected static final String TEAM_TAB_ICON = ICONS + "team-tab.png";
		protected static final String SEASONS_TAB_ICON = ICONS + "seasons-tab.png";
		
		protected static final String CREATE_TEAM_ICON = ICONS + "create-team.png";
		
		protected static final String LEAGUE_ICON = ICONS + "league-icon.png";
		
		protected static final String STATISTICS_ICON = ICONS + "statistics.png";	
		
		protected static final String EDIT_IMG_ICON = ICONS + "edit-image.png";
	}

	//
	// Global
	/** Application title */
	public static final String APP_TITLE = "APP Title"; // TODO Set title for the application
	
	/** Height and Width of the JFrame. */
	public static final int WIDTH = 830, HEIGHT = 600;
	
	/** On exit question for the user. */
	private static final String EXIT_QUESTION = "Are you sure you want to exit?";
	
	//
	// Constructors
	public View() {
		super();
		setTitle(APP_TITLE);
		setSize(WIDTH, HEIGHT);
		setCloseOperation();
		setLocationRelativeTo(null); // Center the window
		setResizable(false);
		setIconImage();
	}
	
	//
	// Methods
	/** Renders an ImageIcon to show in the JLabel. */
	protected ImageIcon renderImage(String filePath) {
		try {
			return new ImageIcon(View.class.getResource(filePath));
		} catch(NullPointerException e) {
			Alert.showError(this, "Error loading the image: " + filePath);
		}
		return null;
	}
	
	/**
	 * Switches the View to the MainView.
	 * @param actualView The actual View
	 */
	protected final void changeView(View actualView) {
		changeView(actualView, MainView.class);
	}
	
	/**
	 * Switches between two Views.
	 * @param actualView The actual View
	 * @param newViewClass The view to switch to
	 */
	protected final void changeView(View actualView, Class<? extends View> newViewClass) {
		View newView = null;
		try {
			newView = (View) Class.forName(newViewClass.getName()).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		if (newView != null) {
			actualView.dispose();
			actualView = newView;
		}
	}
	
	/** Renders the view. */
	protected void render() {
		setVisible(true);
	}
	
	/** Closes the View. */
	protected void sendExitAppRequest() {
		int exitRequest = Alert.showConfirm(null, "Exit", EXIT_QUESTION);
		if (exitRequest == JOptionPane.OK_OPTION) {
			closeApp();
		}
	}
	
	/** Sets the defaults closing operation for the Views. */
	private void setCloseOperation() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (!App.DEBUG) {
					sendExitAppRequest();
				} else {
					closeApp();
				}
			}
		});
	}
	
	/** Closes the application. */
	private void closeApp() {
		dispose();
		System.exit(0);
	}
	
	/** Sets the Icon image to the View. */
	private void setIconImage() {
		try {
			setIconImage(ImageIO.read(View.class.getResource(ImagePath.FAVICON)));
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
			Alert.showError(this, "Error loading the icon image!");
		}
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
	
}
