package com.devdream.ui.custom;

import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.devdream.model.Player;

/**
 * This class manages the JTable with players.
 * 
 * @author Asier Gonzalez
 */
public class PlayersTable extends JTable {
	private static final long serialVersionUID = 938427961541059122L;
	
	//
	// Globals
	private static final String DELETE_ICON = "/img/icon/delete-player.png";
	
	private static final int DORSAL_COL = 3;
	
	//
	// Attributes
	public enum Column { FIRST_NAME, SURNAME, AGE, DORSAL, POSITION, QUIT }

	private DefaultTableModel model;
	private HashMap<Integer, Player> elements;
	private ImageIcon deleteIcon;
	
	//
	// Constructors
	private PlayersTable() {
		model = new DefaultTableModel();
		setModel(model);
		setOffersTableHeader();
	}
	
	public PlayersTable(HashMap<Integer, Player> elements) {
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
		model.addColumn("Dorsal");
		model.addColumn("Position");
		model.addColumn("Fire");
	}

	/** Updates the JTable with the updates collection */
	public void update() {
		for (Player p : elements.values()) {
			Vector<String> row = new Vector<String>();
			row.addElement(p.getFirstName());
			row.addElement(p.getSurname());
			row.addElement(Integer.toString(p.getAge()));
			row.addElement(Integer.toString(p.getDorsal()));
			row.addElement(p.getPosition());
			row.addElement("X");
			model.addRow(row);
		}
	}
	
	public Player getSelectedPlayer() {
		int selectedRowIndex = getSelectedRow();
		if (selectedRowIndex > -1) {
			int selectedPlayerDorsal = Integer.valueOf((String) model.getValueAt(getSelectedRow(), DORSAL_COL));
			return elements.get(selectedPlayerDorsal);
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
	
	/** Sets the cells editable */
	@Override
	public boolean isCellEditable(int row, int column) {
		return true;
	}

}
