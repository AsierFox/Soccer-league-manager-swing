package com.devdream.ui.custom;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.devdream.model.Player;

/**
 * This class manages the JTable with offer sale lines.
 * 
 * @author Asier Gonzalez
 * @version 1.0
 * @since 1.0
 */
public class PlayersTable extends JTable {
	private static final long serialVersionUID = 938427961541059122L;

	private static final String DELETE_ICON = "/img/icon/delete-player.png";
	
	//
	// Attributes
	public enum Column { FIRST_NAME, SURNAME, AGE, POSITION, QUIT }

	private DefaultTableModel model;
	private ArrayList<Player> elements;
	private ImageIcon deleteIcon;
	
	//
	// Constructors
	private PlayersTable() {
		model = new DefaultTableModel();
		setModel(model);
		setOffersTableHeader();
	}
	
	public PlayersTable(ArrayList<Player> elements) {
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
		deleteIcon = new ImageIcon(DELETE_ICON);
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
			setValueAt((Object)deleteIcon, 0, 0);
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
