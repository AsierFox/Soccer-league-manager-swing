package com.devdream.ui;

import java.awt.Font;
import java.sql.SQLException;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.devdream.controller.Controller;
import com.devdream.controller.SeasonGameController;
import com.devdream.db.DBConnectionManager;
import com.devdream.model.Player;
import com.devdream.model.Sanction;
import com.devdream.model.Sanctioned;
import com.devdream.ui.custom.Alert;
import com.devdream.ui.custom.MyComboBox;
import com.devdream.ui.custom.SanctionsTable;

public class SanctionsView extends View {
	private static final long serialVersionUID = 5929086564680399814L;

	private SeasonGameController seasonGameController;
	
	private SanctionsTable sanctionsTable;
	
	private MyComboBox<Integer, Player> teamPlayersComboBox;
	private MyComboBox<String, Sanction> sanctionTypesComboBox;
	
	private JButton addSactionButton;
	private JButton cancelButton;
	
	public SanctionsView(SeasonGameController seasonGameController, SanctionsTable sanctionsTable) {
		super(false);
		getContentPane().setLayout(null);
		
		this.seasonGameController = seasonGameController;
		this.sanctionsTable = sanctionsTable;
		
		loadUI();
		loadListeners();
		
		render();
	}
	
	@Override
	protected void loadUI() {

		JLabel AddScorerViewTitle = new JLabel("New sanction");
		AddScorerViewTitle.setFont(FontStyle.TITLE_FONT);
		AddScorerViewTitle.setBounds(151, 24, 156, 14);
		getContentPane().add(AddScorerViewTitle);
		
		JPanel addScorerPanel = new JPanel();
		addScorerPanel.setBorder(new TitledBorder(null, "New sanction", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		addScorerPanel.setBounds(29, 65, 374, 236);
		getContentPane().add(addScorerPanel);
		addScorerPanel.setLayout(null);
		
		JLabel forSelectPlayerLabel = new JLabel("Select the player");
		forSelectPlayerLabel.setFont(FontStyle.BOLD_FONT);
		forSelectPlayerLabel.setBounds(30, 26, 145, 14);
		addScorerPanel.add(forSelectPlayerLabel);
		
		JLabel sanctionTypeComboBox = new JLabel("Sanctions");
		sanctionTypeComboBox.setFont(new Font("Verdana", Font.BOLD, 13));
		sanctionTypeComboBox.setBounds(30, 115, 145, 14);
		addScorerPanel.add(sanctionTypeComboBox);
		
		teamPlayersComboBox = new MyComboBox<>(Controller.getLoggedUser().getTeam().getPlayers());
		teamPlayersComboBox.setBounds(30, 62, 182, 28);
		addScorerPanel.add(teamPlayersComboBox);
		
		sanctionTypesComboBox = new MyComboBox<>(seasonGameController.getSactionTypes());
		sanctionTypesComboBox.setBounds(30, 153, 182, 28);
		addScorerPanel.add(sanctionTypesComboBox);
		
		addSactionButton = new JButton("Add sanction");
		addSactionButton.setIcon(renderImage(ImagePath.CREATE_ICON));
		addSactionButton.setHorizontalTextPosition(AbstractButton.LEFT);
		addSactionButton.setBounds(57, 309, 190, 51);
		getContentPane().add(addSactionButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setIcon(renderImage(ImagePath.CANCEL_RETURN_ICON));
		cancelButton.setHorizontalTextPosition(AbstractButton.LEFT);
		cancelButton.setBounds(257, 318, 107, 33);
		getContentPane().add(cancelButton);
	}

	@Override
	protected void loadListeners() {
		addSactionButton.addActionListener((e) -> {
			Player selPlayer = (Player) teamPlayersComboBox.getSelectedItem();
			Sanction sanction = (Sanction) sanctionTypesComboBox.getSelectedItem();
			try {
				seasonGameController.createSanction(selPlayer, sanction);
				sanctionsTable.addSanctioned(new Sanctioned(selPlayer, sanction));
				
				Alert.showInfo(this, "Sanction created!");
				if (Alert.showConfirm(this, "Return to the season view",
						"Do you wish to return to the season game view?")) {
					dispose();
				}
			} catch (SQLException err) {
				Alert.showError(this, DBConnectionManager.CONNECT_ERROR_MSG);
			}
		});
		
		cancelButton.addActionListener((e) -> dispose());
	}
}
