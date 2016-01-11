package com.devdream.ui.custom;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import com.devdream.exception.NotTableItemSelectedException;

/**
 * Personalized JList component of Swing.
 * 
 * @author Asier Gonzalez
 */
public class MyList <E> extends JList<E> {

	private static final long serialVersionUID = 3507577225196987540L;

	private DefaultListModel<E> model;
	private ArrayList<E> items;
	
	public MyList(ArrayList<E> items) {
		this.items = items;
		model = new DefaultListModel<E>();
		setModel(model);
		update();
	}
	
	public void update() {
		model.removeAllElements();
        for (E e : items) {
        	model.addElement(e);
        }
	}
	
	public void addItem(E e) {
		items.add(e);
		update();
	}
	
	public E getSelectedItem(String itemName) throws NotTableItemSelectedException {
		if (getSelectedValue() == null) {
			throw new NotTableItemSelectedException(itemName);
		}
		return getSelectedValue();
	}
	
	public ArrayList<E> getItems() {
		return items;
	}

}
