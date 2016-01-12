package com.devdream.ui;

import java.awt.Font;
import java.sql.SQLException;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.TitledBorder;

import com.devdream.controller.Controller;
import com.devdream.controller.SeasonGameController;
import com.devdream.exception.InvalidInputException;
import com.devdream.model.Player;
import com.devdream.model.Scorer;
import com.devdream.ui.custom.Alert;
import com.devdream.ui.custom.MyComboBox;
import com.devdream.ui.custom.ScorersList;

public class AddUpdateScorerView extends View {
	private static final long serialVersionUID = 1664079085664852146L;
	
	//
	// Attributes
	private SeasonGameController seasonGameController;
	private ScorersList scorersList;
	
	private MyComboBox<Integer, Player> teamPlayersComboBox;
	
	private JSpinner goalsSpinner;
	
	private JButton addScorerButton;
	private JButton cancelButton;

	//
	// Constructors
	public AddUpdateScorerView(SeasonGameController seasonGameController, ScorersList scorersList) {
		super(false);
		getContentPane().setLayout(null);
		
		this.seasonGameController = seasonGameController;
		this.scorersList = scorersList;
		
		loadUI();
		loadListeners();
		
		render();
	}
	
	//
	// Methods
	@Override
	protected void loadUI() {
		JLabel AddScorerViewTitle = new JLabel("Add scorer");
		AddScorerViewTitle.setFont(FontStyle.TITLE_FONT);
		AddScorerViewTitle.setBounds(113, 22, 198, 14);
		getContentPane().add(AddScorerViewTitle);
		
		JPanel addScorerPanel = new JPanel();
		addScorerPanel.setBorder(new TitledBorder(null, "New scorer", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		addScorerPanel.setBounds(29, 65, 365, 236);
		getContentPane().add(addScorerPanel);
		addScorerPanel.setLayout(null);
		
		JLabel forSelectPlayerLabel = new JLabel("Select the player");
		forSelectPlayerLabel.setFont(FontStyle.BOLD_FONT);
		forSelectPlayerLabel.setBounds(30, 26, 145, 14);
		addScorerPanel.add(forSelectPlayerLabel);
		
		JLabel forGoalsLabel = new JLabel("Player goals");
		forGoalsLabel.setFont(new Font("Verdana", Font.BOLD, 13));
		forGoalsLabel.setBounds(30, 115, 145, 14);
		addScorerPanel.add(forGoalsLabel);
		
		teamPlayersComboBox = new MyComboBox<>(Controller.getLoggedUser().getTeam().getPlayers());
		teamPlayersComboBox.setBounds(30, 62, 182, 28);
		addScorerPanel.add(teamPlayersComboBox);
		
		goalsSpinner = new JSpinner();
		goalsSpinner.setBounds(30, 150, 55, 28);
		addScorerPanel.add(goalsSpinner);
		
		addScorerButton = new JButton("Add/Update scorer");
		addScorerButton.setIcon(renderImage(ImagePath.CREATE_ICON));
		addScorerButton.setHorizontalTextPosition(AbstractButton.LEFT);
		addScorerButton.setBounds(57, 309, 190, 51);
		getContentPane().add(addScorerButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setIcon(renderImage(ImagePath.CANCEL_RETURN_ICON));
		cancelButton.setHorizontalTextPosition(AbstractButton.LEFT);
		cancelButton.setBounds(257, 318, 107, 33);
		getContentPane().add(cancelButton);
	}
	
	@Override
	protected void loadListeners() {
		addScorerButton.addActionListener((e) -> {
			try {
				Player selPlayer = (Player) teamPlayersComboBox.getSelectedItem();
				int goals = (Integer) goalsSpinner.getValue();
				
				seasonGameController.updateScorer(selPlayer, goals);
				scorersList.addItem(new Scorer(goals, selPlayer));
				
				Alert.showInfo(this, "Player goals added!");
				askReturn();
			} catch (InvalidInputException err) {
				Alert.showError(this, err.getMessage());
			} catch (NullPointerException err) {
				Alert.showError(this, "You need to select a player!");
			} catch (SQLException err) {
				Alert.showError(this, "Error connecting to the database!");
			}
		});
		
		cancelButton.addActionListener((e) -> dispose());
	}

	private void clearData() {
		goalsSpinner.setValue(0);
	}
	
	private void askReturn() {
		if (Alert.showConfirm(this, "Return to the season view", "Do you wish to return to the season game view?")) {
			dispose();
		} else {
			clearData();
		}
	}

}
