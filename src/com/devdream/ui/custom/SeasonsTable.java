package com.devdream.ui.custom;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.devdream.model.Season;

/**
 * This class creates a table with matches.
 * 
 * @author Asier Gonzalez
 */
public class SeasonsTable extends JTable {
	private static final long serialVersionUID = 938427961541059122L;
	
	//
	// Attributes
	public enum Column { HOME_TEAM, AWAY_TEAM, DATE }

	private DefaultTableModel model;
	private ArrayList<Season> elements;
	
	//
	// Constructors
	private SeasonsTable() {
		model = new DefaultTableModel();
		setModel(model);
		setOffersTableHeader();
	}
	
	public SeasonsTable(ArrayList<Season> elements) {
		this();
		this.elements = elements;
	}
	
	//
	// Methods
	/** Sets the header to the table */
	private void setOffersTableHeader() {
		getTableHeader().setReorderingAllowed(false);
		getTableHeader().setResizingAllowed(false);
		model.addColumn("Home team");
		model.addColumn("Away Team");
		model.addColumn("Game date");
	}

	/** Updates the JTable with the updates collection */
	public void update() {
		for (Season s : elements) {
			Vector<String> row = new Vector<String>();
			row.addElement(s.getGame().getHomeTeam().getName());
			row.addElement(s.getGame().getAwayTeam().getName());
			row.addElement(s.getDate());
			model.addRow(row);
		}
	}
	
	public Season getSelectedSeason() {
		// TODO Return game
		return null;
	}
	
	/** Sets the cells no editable */
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
}
