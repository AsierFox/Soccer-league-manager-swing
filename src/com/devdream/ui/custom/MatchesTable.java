package com.devdream.ui.custom;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.devdream.model.Player;

/**
 * This class creates a table with matches.
 * 
 * @author Asier Gonzalez
 */
public class MatchesTable extends JTable {
	private static final long serialVersionUID = 938427961541059122L;
	
	//
	// Attributes
	public enum Column { FIRST_NAME, SURNAME, AGE, POSITION, QUIT }

	private DefaultTableModel model;
	private ArrayList<Player> elements;
	
	//
	// Constructors
	private MatchesTable() {
		model = new DefaultTableModel();
		setModel(model);
		setOffersTableHeader();
	}
	
	public MatchesTable(ArrayList<Player> elements) {
		this();
		this.elements = elements;
	}
	
	//
	// Methods
	/** Sets the header to the table */
	private void setOffersTableHeader() {
		getTableHeader().setReorderingAllowed(false);
		getTableHeader().setResizingAllowed(false);
		model.addColumn("First name");
		model.addColumn("Surname");
		model.addColumn("Age");
		model.addColumn("Position");
		model.addColumn("Fire");
	}

	/** Updates the JTable with the updates collection */
	public void update() {
		for (Player p : elements) {
			Vector<String> row = new Vector<String>();
			row.addElement(p.getFirstName());
			row.addElement(p.getSurname());
			row.addElement(Integer.toString(p.getAge()));
			row.addElement(p.getPosition());
			row.addElement(p.getPosition());
			model.addRow(row);
		}
	}
	
	public Player getSelectedPlayer() {
		int selectedRowIndex = getSelectedRow();
		if (selectedRowIndex > -1) {
			return elements.get(getSelectedRow());
		}
		return null;
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
	
	/** Sets the cells no editable */
	@Override
	public boolean isCellEditable(int row, int column) {
		return true;
	}
	
}
