package com.devdream.ui.custom;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * This class manages the JTable with offer sale lines.
 * 
 * @author Asier Gonzalez
 * @version 1.0
 * @since 1.0
 */
public class MyTable <E> extends JTable {

	private static final long serialVersionUID = 938427961541059122L;

	//
	// Attributes
	private DefaultTableModel model;
	private ArrayList<E> elements;
	
	//
	// Constructors
	public MyTable(ArrayList<E> elements) {
		this.elements = elements;
		model = new DefaultTableModel();
		setModel(model);
		setOffersTableHeader();
	}
	
	//
	// Methods
	/** Sets the header to the table */
	private void setOffersTableHeader() {
		getTableHeader().setReorderingAllowed(false);
		getTableHeader().setResizingAllowed(false);
//		model.addColumn("ID");
	}

	/** Updates the JTable with the updates collection */
	public void update() {
		for (E e : elements) {
			Vector<String> row = new Vector<String>();
//			row.addElement(Integer.toString(E.getOffer().ID));
			model.addRow(row);
		}
	}
	
	/** Sets the cells no editable */
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
}
