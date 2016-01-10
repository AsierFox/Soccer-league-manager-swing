package com.devdream.ui.custom;

import java.awt.Font;
import java.util.Iterator;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

/**
 * Personalized ComboBox for my custom objects.
 * 
 * @author Asier Gonzalez
 */
public class MyComboBox <K, V> extends JComboBox<V> {

	private static final long serialVersionUID = -1001219177283780102L;
	
	//
	// Attributes
	private DefaultComboBoxModel<V> model;
	private Map<K, V> items;
	
	//
	// Constructors
    public MyComboBox(Map<K, V> items) {
    	this.items = items;
    	setFont(new Font("SansSerif", Font.PLAIN, 12));
    	update();
    }
    
    //
    // Methods
    public void update() {
        model = new DefaultComboBoxModel<V>();
        setModel(model);
        for (Iterator<V> it = items.values().iterator(); it.hasNext(); ) {
        	model.addElement(it.next());
        }
    }
    
    public K getSelected() {
		return getKeyFromValue(model.getElementAt(getSelectedIndex()));
    }
    
	private K getKeyFromValue(V value) {
		for (K k : items.keySet())
			if (items.get(k).equals(value))
				return k;
		return null;
	}

}
