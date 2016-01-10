package com.devdream.ui.custom;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.devdream.model.Player;
import com.devdream.model.Scorer;

/**
 * This class manages the JTable with top scorer players.
 * 
 * @author Asier Gonzalez
 */
public class ScorersTable extends JTable {
	private static final long serialVersionUID = -2727533720033456242L;
	
	//
	// Attributes
	private DefaultTableModel model;
	private ArrayList<Scorer> scorers;
	
	//
	// Constructors
	public ScorersTable(ArrayList<Scorer> scorers) {
		model = new DefaultTableModel();
		setModel(model);
		setOffersTableHeader();
		this.scorers = scorers;
	}
	
	//
	// Methods
	/** Sets the header to the table. */
	private void setOffersTableHeader() {
		getTableHeader().setReorderingAllowed(false);
		getTableHeader().setResizingAllowed(false);
		model.addColumn("Rank.");
		model.addColumn("First name");
		model.addColumn("Surname");
		model.addColumn("Dorsal");
		model.addColumn("Goals");
	}

	/** Updates the JTable with the updates collection. */
	public void update() {
		model.setRowCount(0);
		for (Scorer s : scorers) {
			addScorer(s);
		}
	}
	
	public void addScorer(Scorer scorer) {
		Player player = scorer.getPlayer();
		Vector<String> row = new Vector<String>();
		row.addElement(Integer.toString(model.getRowCount() + 1));
		row.addElement(player.getFirstName());
		row.addElement(player.getSurname());
		row.addElement(Integer.toString(player.getDorsal()));
		row.addElement(Integer.toString(scorer.getScore()));
		model.addRow(row);
	}
	
	/** Sets the cells to not editable. */
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

}
