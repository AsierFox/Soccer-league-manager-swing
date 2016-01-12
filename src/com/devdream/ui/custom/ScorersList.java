package com.devdream.ui.custom;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import com.devdream.exception.NotTableItemSelectedException;
import com.devdream.model.Scorer;

/**
 * Personalized JList component of Swing.
 * 
 * @author Asier Gonzalez
 */
public class ScorersList extends JList<Scorer> {

	private static final long serialVersionUID = 3507577225196987540L;

	private DefaultListModel<Scorer> model;
	private ArrayList<Scorer> scorers;
	
	public ScorersList(ArrayList<Scorer> scorers) {
		this.scorers = scorers;
		model = new DefaultListModel<Scorer>();
		setModel(model);
		update();
	}
	
	public void update() {
		model.removeAllElements();
        for (Scorer s : scorers) {
        	model.addElement(s);
        }
	}
	
	public void addItem(Scorer scorer) {
		boolean exists = false;
		for (Iterator<Scorer> it = scorers.iterator(); !exists && it.hasNext(); ) {
			Scorer s = it.next();
			if (s.getPlayer().getDorsal() == scorer.getPlayer().getDorsal()) {
				s.setScore(s.getScore() + scorer.getScore());
				exists = true;
			}
		}
		if (!exists) {
			scorers.add(scorer);
		}
		update();
	}
	
	public Scorer getSelectedItem(String itemName) throws NotTableItemSelectedException {
		if (getSelectedValue() == null) {
			throw new NotTableItemSelectedException(itemName);
		}
		return getSelectedValue();
	}
	
	public void removeSelectedRow(int index) {
		scorers.remove(index);
	}
	
	public ArrayList<Scorer> getScorers() {
		return scorers;
	}

	public int getTotalScore() {
		int total = 0;
		for (Iterator<Scorer> it = scorers.iterator(); it.hasNext(); ) {
			total += it.next().getScore();
		}
		return total;
	}

}
