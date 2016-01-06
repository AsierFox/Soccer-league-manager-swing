package com.devdream.ui.custom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.devdream.exception.NotTableItemSelectedException;
import com.devdream.model.Team;

/**
 * This class manages the teams on a JTable.
 * 
 * @author Asier Gonzalez
 */
public class TeamsTable extends JTable {
	private static final long serialVersionUID = 938427961541059122L;
	
	//
	// Global
	private static final int NAME_COL = 0;
	
	//
	// Attributes
	private DefaultTableModel model;
	private HashMap<String, Team> teams;
	
	//
	// Constructors
	public TeamsTable() {
		model = new DefaultTableModel();
		setModel(model);
		setOffersTableHeader();
		teams = new HashMap<>();
	}
	
	public TeamsTable(HashMap<String, Team> elements) {
		this();
		this.teams = elements;
	}
	
	//
	// Methods
	/** Sets the header to the table. */
	private void setOffersTableHeader() {
		getTableHeader().setReorderingAllowed(false);
		getTableHeader().setResizingAllowed(false);
		model.addColumn("Name");
		model.addColumn("Shortname");
		model.addColumn("Location");
	}

	/** Updates the JTable with the updates collection. */
	public void update() {
		model.setRowCount(0);
		for (Team t : teams.values()) {
			addTeam(t);
		}
	}
	
	public void addTeam(Team newTeam) {
		Vector<String> row = new Vector<String>();
		row.addElement(newTeam.getName());
		row.addElement(newTeam.getShortName());
		row.addElement(newTeam.getLocation());
		model.addRow(row);
		teams.put(newTeam.getName(), newTeam);
	}
	
	public void removeTeam(Team team) {
		teams.remove(team.getName());
		update();
	}
	
	public Team getSelectedTeam() throws NotTableItemSelectedException {
		int selectedRowIndex = getSelectedRow();
		if (selectedRowIndex <= -1) {
			throw new NotTableItemSelectedException("player");
		}
		String teamName = (String) model.getValueAt(selectedRowIndex, NAME_COL);
		return teams.get(teamName);
	}
	
	public boolean hasAlreadyTeam(Team team) {
		return teams.containsKey(team.getName());
	}
	
	/** Sets the cells no editable. */
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	//
	// Getters and setters
	public ArrayList<Team> getTeams() {
		return new ArrayList<Team>(teams.values());
	}
	public void setTeams(HashMap<String, Team> teams) {
		this.teams = teams;
	}

}
