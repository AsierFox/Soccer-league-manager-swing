package com.devdream.ui;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.devdream.model.Season;
import com.devdream.model.Team;

public class SeasonGameView extends View {
	private static final long serialVersionUID = 4061083358990967716L;
	
	//
	// Attributes
	private Season season;
	private Team homeTeam;
	private Team awayTeam;
	
	//
	// Constructors
	public SeasonGameView(Season selectedSeason) { // TODO Controller for statistics and that
		super();
		getContentPane().setLayout(null);
		
		season = selectedSeason;
		homeTeam = season.getGame().getHomeTeam();
		awayTeam = season.getGame().getAwayTeam();
		
		loadUI();
		loadListeners();
		
		render();
	}
	
	//
	// Methods
	@Override
	protected void loadUI() {
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Game", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(69, 72, 728, 377);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel forHomeTeamLabel = new JLabel("Home team");
		forHomeTeamLabel.setBounds(61, 48, 86, 14);
		panel.add(forHomeTeamLabel);
		
		JLabel forAwayTeamLabel = new JLabel("Away team");
		forAwayTeamLabel.setBounds(479, 48, 86, 14);
		panel.add(forAwayTeamLabel);
		
		JLabel homeTeamLabel = new JLabel(homeTeam.getName());
		homeTeamLabel.setBounds(71, 73, 104, 14);
		panel.add(homeTeamLabel);
		
		JLabel awayTeamLabel = new JLabel(awayTeam.getName());
		awayTeamLabel.setBounds(441, 83, 104, 14);
		panel.add(awayTeamLabel);
		
		JLabel forSeasonTitleLabel = new JLabel("Season");
		forSeasonTitleLabel.setBounds(231, 29, 46, 14);
		getContentPane().add(forSeasonTitleLabel);
		
		JLabel seasonDateLabel = new JLabel((season != null) ? season.getDate() : "No date");
		seasonDateLabel.setBounds(314, 29, 86, 14);
		getContentPane().add(seasonDateLabel);
	}

	@Override
	protected void loadListeners() {
	}

}
