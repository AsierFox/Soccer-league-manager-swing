package com.devdream.ui.custom;

import java.util.HashMap;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import com.devdream.exception.NotTableItemSelectedException;
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
	private static final int DORSAL_COL = 3;
	
	//
	// Attributes
	private DefaultTableModel model;
	/** Key -> Dorsal */
	private HashMap<Integer, Player> players;
	
	//
	// Constructors
	private PlayersTable() {
		model = new DefaultTableModel();
		setModel(model);
		setOffersTableHeader();
	}
	
	public PlayersTable(HashMap<Integer, Player> elements) {
		this();
		this.players = elements;
	}
	
	//
	// Methods
	/** Sets the header to the table. */
	private void setOffersTableHeader() {
		getTableHeader().setReorderingAllowed(false);
		getTableHeader().setResizingAllowed(false);
		model.addColumn("First name");
		model.addColumn("Surname");
		model.addColumn("Age");
		model.addColumn("Dorsal");
		model.addColumn("Position");
	}

	/** Updates the JTable with the updates collection. */
	public void update() {
		model.setRowCount(0);
		for (Player p : players.values()) {
			addPlayer(p);
		}
	}
	
	public void addPlayer(Player newPlayer) {
		Vector<String> row = new Vector<String>();
		row.addElement(newPlayer.getFirstName());
		row.addElement(newPlayer.getSurname());
		row.addElement(Integer.toString(newPlayer.getAge()));
		row.addElement(Integer.toString(newPlayer.getDorsal()));
		row.addElement(newPlayer.getPosition());
		model.addRow(row);
		players.put(newPlayer.getDorsal(), newPlayer);
	}
	
	public void removePlayer(Player player) {
		players.remove(player.getDorsal());
		update();
	}
	
	public Player getSelectedPlayer() throws NotTableItemSelectedException {
		int selectedRowIndex = getSelectedRow();
		if (selectedRowIndex <= -1) {
			throw new NotTableItemSelectedException("player");
		}
		int selectedPlayerDorsal = Integer.valueOf((String) model.getValueAt(getSelectedRow(), DORSAL_COL));
		return players.get(selectedPlayerDorsal);
	}
	
	@Override
	public TableCellEditor getCellEditor() {
		int col = getEditingColumn(), row = getEditingRow();
		if (col < 0 || row < 0) {
			return null;
		}
		// TODO Edit player
		return super.getCellEditor();
	}
	
	/** Sets the cells editable. */
	@Override
	public boolean isCellEditable(int row, int column) {
		return true;
	}
	
	//
	// Getters and setters
	public void setPlayers(HashMap<Integer, Player> players) {
		this.players = players;
	}
	public HashMap<Integer, Player> getPlayers() {
		return players;
	}

}
