package com.devdream.ui.custom;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.devdream.exception.NotTableItemSelectedException;
import com.devdream.model.Player;
import com.devdream.model.Sanctioned;

/**
 * This class retains a table of sanctioned players.
 * 
 * @author Asier Gonzalez
 */
public class SanctionsTable extends JTable {
	private static final long serialVersionUID = -1561102919384332771L;
	
	//
	// Attributes
	private DefaultTableModel model;
	private ArrayList<Sanctioned> sanctions;
	
	//
	// Constructors
	public SanctionsTable(ArrayList<Sanctioned> sanctions) {
		model = new DefaultTableModel();
		setModel(model);
		setOffersTableHeader();
		this.sanctions = sanctions;
		update();
	}
	
	//
	// Methods
	/** Sets the header to the table. */
	private void setOffersTableHeader() {
		getTableHeader().setReorderingAllowed(false);
		getTableHeader().setResizingAllowed(false);
		model.addColumn("Player");
		model.addColumn("Dorsal");
		model.addColumn("Sanction");
	}
	
	/** Updates the JTable with the updates collection. */
	public void update() {
		model.setRowCount(0);
		for (Sanctioned s : sanctions) {
			addSanctioned(s);
		}
	}
	
	public void addSanctioned(Sanctioned sanctioned) {
		Player player = sanctioned.getPlayer();
		Vector<String> row = new Vector<String>();
		row.addElement(player.getFirstName());
		row.addElement(Integer.toString(player.getDorsal()));
		row.addElement(sanctioned.getSanction().getType());
		model.addRow(row);
	}
	
	public Sanctioned getSelectedSanctioned() throws NotTableItemSelectedException {
		int selectedRowIndex = getSelectedRow();
		if (selectedRowIndex <= -1) {
			throw new NotTableItemSelectedException("sanctioned");
		}
		return sanctions.get(getSelectedRow());
	}
	
	/** Sets the cells to not editable. */
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

}
