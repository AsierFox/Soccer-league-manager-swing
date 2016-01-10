package com.devdream.ui.custom;

import java.util.HashMap;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.devdream.exception.NotTableItemSelectedException;
import com.devdream.model.SeasonGame;

/**
 * This class creates a table with season matches.
 * 
 * @author Asier Gonzalez
 */
public class SeasonGamesTable extends JTable {
	private static final long serialVersionUID = 938427961541059122L;
	
	//
	// Globals
	private final static int ID_COL = 0;
	
	//
	// Attributes
	private TableColumn hiddenIdCol;
	
	private DefaultTableModel model;
	private HashMap<Integer, SeasonGame> seasonGames;
	
	//
	// Constructors
	private SeasonGamesTable() {
		model = new DefaultTableModel();
		setModel(model);
		setOffersTableHeader();
	}
	
	public SeasonGamesTable(HashMap<Integer, SeasonGame> seasonGames) {
		this();
		this.seasonGames = seasonGames;
	}
	
	//
	// Methods
	/** Sets the header to the table. */
	private void setOffersTableHeader() {
		getTableHeader().setReorderingAllowed(false);
		getTableHeader().setResizingAllowed(false);
		model.addColumn("Id");
		model.addColumn("Home team");
		model.addColumn("Away team");
		model.addColumn("Game date");
		hiddenIdCol = getColumnModel().getColumn(0);
	}

	/** Updates the JTable with the updates collection. */
	public void update() {
		model.setRowCount(0);
		for (SeasonGame s : seasonGames.values()) {
			Vector<String> row = new Vector<String>();
			row.addElement(Integer.toString(s.getGame().getId()));
			row.addElement(s.getGame().getHomeTeam().getName());
			row.addElement(s.getGame().getAwayTeam().getName());
			row.addElement(s.getDate());
			model.addRow(row);
		}
		getColumnModel().removeColumn(hiddenIdCol);
	}
	
	public SeasonGame getSelectedSeasonGame() throws NotTableItemSelectedException {
		int selectedRowIndex = getSelectedRow();
		if (selectedRowIndex <= -1) {
			throw new NotTableItemSelectedException("season game");
		}
		return seasonGames.get(Integer.parseInt((String) model.getValueAt(selectedRowIndex, ID_COL)));
	}
	
	/** Sets the cells no editable. */
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	public HashMap<Integer, SeasonGame> getSeasons() {
		return seasonGames;
	}

}
