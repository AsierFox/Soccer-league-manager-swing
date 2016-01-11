package com.devdream.ui.custom;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.devdream.model.Team;
import com.devdream.ui.View.FontStyle;

/**
 * This class manages the JTable with team performances.
 * 
 * @author Asier Gonzalez
 */
public class PerformancesTable extends JTable {
	private static final long serialVersionUID = -2727533720033456242L;
	
	private static final Color SEARCHED_COLOR = new Color(0, 188, 212);
	
	//
	// Attributes
	private DefaultTableModel model;
	private ArrayList<Team> teams;
	private int lastSearchedColum, searchingColIndex;
	
	//
	// Constructors
	public PerformancesTable() {
		model = new DefaultTableModel();
		setModel(model);
		setOffersTableHeader();
		teams = new ArrayList<>();
	}
	
	public PerformancesTable(ArrayList<Team> teams) {
		this();
		this.teams = teams;
	}
	
	//
	// Methods
	/** Sets the header to the table. */
	private void setOffersTableHeader() {
		getTableHeader().setReorderingAllowed(false);
		getTableHeader().setResizingAllowed(false);
		model.addColumn("Pos.");
		model.addColumn("Team name");
		model.addColumn("Shots");
		model.addColumn("Passes");
		model.addColumn("Fouls");
		model.addColumn("Offsides");
		model.addColumn("Corners");
		model.addColumn("Total goals");
	}

	/** Updates the JTable with the updates collection. */
	public void update() {
		model.setRowCount(0);
		for (Team t : teams) {
			addTeam(t);
		}
		getColumnModel().getColumn(lastSearchedColum).setCellRenderer(new CustomRenderer(Color.BLACK));
		getColumnModel().getColumn(searchingColIndex).setCellRenderer(new CustomRenderer(SEARCHED_COLOR, true));
		lastSearchedColum = searchingColIndex;
	}
	
	public void addTeam(Team team) {
		Vector<String> row = new Vector<String>();
		row.addElement(Integer.toString(model.getRowCount() + 1));
		row.addElement(team.getName());
		row.addElement(Integer.toString(team.getShots()));
		row.addElement(Integer.toString(team.getPasses()));
		row.addElement(Integer.toString(team.getFouls()));
		row.addElement(Integer.toString(team.getOffsides()));
		row.addElement(Integer.toString(team.getCorners()));
		row.addElement(Integer.toString(team.getScore()));
		model.addRow(row);
	}
	
	/** Sets the cells to not editable. */
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	public void setTeams(ArrayList<Team> teams) {
		this.teams = teams;
		update();
	}
	
	public void setSeachBy(String seachBy) {
		searchingColIndex = getColumnIndex(seachBy);
	}
	
	private int getColumnIndex(String colName) {
		for (int i = 0; i < getColumnCount(); i++)
			if (getColumnName(i).equals(colName))
				return i;
		return -1;
	}
	
	/**
	 * Class for editing the column style of the tables.
	 */
	class CustomRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 4888797556515234737L;
		
		private Color bgColColor;
		private boolean bold;
		
		public CustomRenderer(Color bgColColor) {
			this.bgColColor = bgColColor;
		}
		
		public CustomRenderer(Color bgColColor, boolean bold) {
			this(bgColColor);
			this.bold = bold;
		}
		
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if (bold) setFont(FontStyle.BOLD_FONT);
			setForeground(bgColColor);
			return c;
		}
	}

}
