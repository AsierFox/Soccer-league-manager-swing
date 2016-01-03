package com.devdream.ui.custom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.devdream.exception.NotTeamSelectedException;
import com.devdream.model.Team;

/**
 * This class manages the JTable with players.
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
	public enum Column { NAME, SHORTNAME, LOCATION }

	private DefaultTableModel model;
	private HashMap<String, Team> elements;
	
	//
	// Constructors
	public TeamsTable() {
		model = new DefaultTableModel();
		setModel(model);
		setOffersTableHeader();
		elements = new HashMap<>();
	}
	
	public TeamsTable(HashMap<String, Team> elements) {
		this();
		this.elements = elements;
	}
	
	//
	// Methods
	/** Sets the header to the table */
	private void setOffersTableHeader() {
		getTableHeader().setReorderingAllowed(false);
		getTableHeader().setResizingAllowed(false);
		model.addColumn("Name");
		model.addColumn("Shortname");
		model.addColumn("Location");
	}

	/** Updates the JTable with the updates collection */
	public void update() {
		model.setRowCount(0);
		for (Team t : elements.values()) {
			addTeam(t);
		}
	}
	
	public void addTeam(Team newTeam) {
		Vector<String> row = new Vector<String>();
		row.addElement(newTeam.getName());
		row.addElement(newTeam.getShortName());
		row.addElement(newTeam.getLocation());
		model.addRow(row);
		elements.put(newTeam.getName(), newTeam);
	}
	
	public void removeTeam(Team newTeam) {
		elements.remove(newTeam.getName());
		update();
	}
	
	public Team getSelectedTeam() throws NotTeamSelectedException {
		int selectedRowIndex = getSelectedRow();
		if (selectedRowIndex <= -1) {
			throw new NotTeamSelectedException();
		}
		String teamName = (String) model.getValueAt(selectedRowIndex, NAME_COL);
		return elements.get(teamName);
	}
	
	public boolean hasAlreadyTeam(Team team) {
		return elements.containsKey(team.getName());
	}
	
	public Column getEditedColumn() {
		int editedColumn = getEditingColumn();
		int i = 0;
		for (Column c : Column.values()) {
			if (editedColumn == i++) {
				return c;
			}
		}
		return null;
	}
	
	/** Sets the cells no editable. */
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	//
	// Getters and setters
	public ArrayList<Team> getTeams() {
		return new ArrayList<Team>(elements.values());
	}
	
}
