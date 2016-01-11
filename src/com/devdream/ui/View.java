package com.devdream.ui;

import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

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
	
	//
	// Global
	/** Application title */
	public static final String APP_TITLE = "Soccer manager";
	
	/** Height and Width of the JFrame. */
	public static final int WIDTH = 830, HEIGHT = 600;
	public static final int OTHER_WIDTH = 430, OTHER_HEIGHT = 400;
	
	/** On exit question for the user. */
	private static final String EXIT_QUESTION = "Are you sure you want to exit?";
	
	//
	// Constructors
	public View() {
		this(true);
	}
	
	/**
	 * Creates a view.
	 * @param rootView If the view is root / main or not.
	 */
	public View(boolean rootView) {
		super();
		setTitle(APP_TITLE);
		if (rootView) {
			setSize(WIDTH, HEIGHT);
			setCloseOperation();
		} else {
			setSize(OTHER_WIDTH, OTHER_HEIGHT);
		}
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
		if (Alert.showConfirm(null, "Exit", EXIT_QUESTION)) {
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
	
	/**
	 * Class for saving the image relative paths.
	 */
	public static class ImagePath {
		private static final String IMG_DIR = "/img/";
		
		public static final String LOGOS = IMG_DIR + "logo/";
		protected static final String ICONS = IMG_DIR + "icon/";
		
		private static final String FAVICON = IMG_DIR + "favicon/favicon.png";
		
		protected static final String LOGO = LOGOS + "app-logo.png";
		
		protected static final String LOGIN_ICON = ICONS + "login.png";
		protected static final String EXIT_ICON = ICONS + "exit.png";
		
		protected static final String FEATURES_ICON = ICONS + "features.png";
		
		protected static final String USER_TAB_ICON = ICONS + "user-tab.png";
		protected static final String TEAM_TAB_ICON = ICONS + "team-tab.png";
		protected static final String PLAYER_TAB_ICON = ICONS + "player-tab.png";
		protected static final String SEASONS_TAB_ICON = ICONS + "seasons-tab.png";
		
		protected static final String CREATE_TEAM_ICON = ICONS + "create-team.png";
		
		protected static final String PLAYER_TOP_SCORERS_ICON = ICONS + "player-scorers.png";
		
		protected static final String LEAGUE_ICON = ICONS + "league-icon.png";
		
		protected static final String STATISTICS_ICON = ICONS + "statistics-icon.png";
		protected static final String STATISTICS_TITLE_ICON = ICONS + "statistics-title-icon.png";
		
		protected static final String VIEW_SEASON_ICON = ICONS + "view-season-icon.png";
		
		protected static final String TOP_SCORERS_ICON = ICONS + "top-scorers-icon.png";

		protected static final String CSV_ICON = ICONS + "csv-icon.png";
		protected static final String PDF_ICON = ICONS + "pdf-icon.png";
		protected static final String DDBB_ICON = ICONS + "database-icon.png";

		protected static final String SEARCH_ICON = ICONS + "search-icon.png";
		protected static final String EDIT_IMG_ICON = ICONS + "edit-image.png";
		protected static final String CREATE_ICON = ICONS + "create-icon.png";
		protected static final String CANCEL_RETURN_ICON = ICONS + "return-icon.png";
		
		public static final String DEFAULT_TEAM_LOGO = "team-default-logo.png";
	}
	
	/**
	 * Class for saving the font style of the application.
	 */
	public static class FontStyle {
		public static final Font TITLE_FONT = new Font("Verdana", Font.BOLD | Font.ITALIC, 15);
		public static final Font BOLD_FONT = new Font("Verdana", Font.BOLD, 13);
		public static final Font NORMAL_FONT = new Font("Verdana", 0, 10);
	}

}
