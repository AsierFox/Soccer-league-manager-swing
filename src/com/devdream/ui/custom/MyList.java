package com.devdream.ui.custom;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 * Personalized JList component of Swing.
 * 
 * @author Asier Gonzalez
 * @version 1.0
 * @since 1.0
 */
public class MyList <E> extends JList<E> {

	private static final long serialVersionUID = 3507577225196987540L;

	private DefaultListModel<E> model;
	private ArrayList<E> items;
	
	public MyList(ArrayList<E> items) {
		this.items = items;
		model = new DefaultListModel<E>();
		setModel(model);
		fillList();
	}
	
	private void fillList() {
        for (E e : items) {
        	model.addElement(e);
        }
	}

}
