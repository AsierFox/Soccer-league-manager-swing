package com.devdream.test;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTree;

import com.devdream.ui.View;
import com.devdream.ui.custom.DateObserverTextField;
import com.qt.datapicker.DatePicker;

public class TestView extends View {
	private static final long serialVersionUID = -4093989271963766524L;
	
	public static void main(String[] args) {
		new TestView();
	}
	
	//
	// Constructors
	public TestView() {
		super();
		getContentPane().setLayout(null);
		setSize(400, 400);
		
		
		JPanel panel = new JPanel();
		panel.setBounds(34, 11, 237, 41);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		DateObserverTextField textField = new DateObserverTextField();
		textField.setBounds(22, 11, 86, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(138, 10, 89, 23);
		panel.add(btnNewButton);
		
		btnNewButton.addActionListener((e) -> {
	        DatePicker dp = new DatePicker(textField, getLocale());
	        dp.parseDate(textField.getText());
	        dp.start(textField);
		});
		
		test1();
		
		render();
	}

	private void test1() {
		JLayeredPane layeredPane_1 = new JLayeredPane();
		layeredPane_1.setBounds(586, 177, 228, 123);
		getContentPane().add(layeredPane_1);
		
		JInternalFrame internalFrame = new JInternalFrame("New JInternalFrame");
		internalFrame.setBounds(10, 11, 173, 77);
		layeredPane_1.add(internalFrame);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBounds(533, 416, 157, 116);
		getContentPane().add(desktopPane);
		
		JTree tree = new JTree();
		tree.setBounds(742, 370, 72, 64);
		getContentPane().add(tree);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(426, 81, 118, 11);
		getContentPane().add(separator);
		
		JLayeredPane layeredPane_2 = new JLayeredPane();
		layeredPane_2.setBounds(348, 292, 228, 115);
		getContentPane().add(layeredPane_2);
		
		JLayeredPane layeredPane_3 = new JLayeredPane();
		layeredPane_3.setBounds(31, 11, 166, 93);
		layeredPane_2.add(layeredPane_3);
		
		JMenu mnNewMenu = new JMenu("New menu");
		mnNewMenu.setBounds(268, 118, 107, 22);
		getContentPane().add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("New menu item");
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(39, 177, 97, 21);
		getContentPane().add(menuBar);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("New menu item");
		mntmNewMenuItem_2.setBounds(341, 225, 129, 22);
		getContentPane().add(mntmNewMenuItem_2);
		
		JMenu mnNewMenu_1 = new JMenu("New menu");
		mnNewMenu_1.setBounds(196, 163, 107, 22);
		getContentPane().add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("New menu item");
		mnNewMenu_1.add(mntmNewMenuItem_3);
		internalFrame.setVisible(true);
	}
	
	protected void loadUI() {
	}
	
	protected void loadListeners() {
	}
	
}
