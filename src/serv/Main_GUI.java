//package reportingTool.src;
package serv;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.AbstractListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollBar;
import javax.swing.JDialog;

import java.awt.List;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.Vector;
import java.util.Map;

import javax.swing.JMenuBar;
import javax.swing.JMenu;

import serv.Query_GUI;
import serv.Query_Object;
import serv.Enums;


public class Main_GUI {

	private JFrame frame;
	private List allList; // List displaying all possible queries
	private List selectedList; // List displaying selected queries for report
	private Vector<String> allItems;  // Vector of all queries
	private Vector<String> selectedItems; // Vector of selected queries
	private boolean VERBOSE = true;
	private boolean DEBUG = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main_GUI window = new Main_GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main_GUI() {
		if (DEBUG) {System.out.println("In MainGUI()");}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		if (DEBUG) {System.out.println("In Initialize()");}
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 528, 324);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 528, 302);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		allItems = new Vector<String>();
		selectedItems = new Vector<String>();
		
		allList = new List(5);
		allList.setFont(new Font("Arial", Font.PLAIN, 12));
		allList.setMultipleMode(true);
		allList.setForeground(Color.BLACK);
		allList.setBackground(Color.WHITE);
		allList.setMultipleMode(true);
		PopulateAllList();
		allList.setBounds(10, 30, 208, 144);
		panel.add(allList);
		
		selectedList = new List();
		selectedList.setFont(new Font("Arial", Font.PLAIN, 12));
		selectedList.setForeground(Color.BLACK);
		selectedList.setBackground(Color.WHITE);
		selectedList.setMultipleMode(true);
		selectedList.setBounds(290, 30, 216, 144);
		panel.add(selectedList);
		
		JButton pushBtn = new JButton("->");
		pushBtn.setBounds(224, 42, 60, 29);
		pushBtn.addActionListener(pushListener);
		panel.add(pushBtn);
		
		JButton popBtn = new JButton("<-");
		popBtn.setBounds(224, 132, 60, 29);
		popBtn.addActionListener(popListener);
		panel.add(popBtn);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 516, 22);
		panel.add(menuBar);
		
		JMenu file = new JMenu("File");
		JMenuItem open = new JMenuItem("Open...");
		JMenuItem save = new JMenuItem("Save...");
		file.add(open);
		file.add(save);
		
		JMenu about = new JMenu("About");
		JMenuItem aboutserval = new JMenuItem("About Serval");
		about.add(aboutserval);
		
		
		menuBar.add(file);
		menuBar.add(about);
		
		JButton clearBtn = new JButton("Clear");
		clearBtn.setBounds(359, 192, 76, 29);
		clearBtn.addActionListener(clearListener);
		panel.add(clearBtn);
		
		JButton uxEditBtn = new JButton("Edit");
		uxEditBtn.setBounds(73, 192, 76, 29);
		uxEditBtn.addActionListener(editListener);
		panel.add(uxEditBtn);
		
	}
	
	private void PopulateAllList()
	{
		if (DEBUG) {System.out.println("In PopulateAllList()");}
		AddQueries();
		int size = allItems.size();
		for (int i = 0; i < size; i++)
		{
			allList.add(allItems.get(i));
		}
	}
	
	private void AddQueries()
	{
		if (DEBUG) {System.out.println("In AddQueries()");}
		allItems.add("One");
		allItems.add("Two");
		allItems.add("Three");
		allItems.add("Four");
		allItems.add("Five");
		allItems.add("Six");
		allItems.add("Seven");
		allItems.add("Eight");
		allItems.add("Nine");
		allItems.add("Ten");
		allItems.add("Eleven");
		allItems.add("Twelve");
	}
	
	private void RefreshSelected()
	{
		if (DEBUG) {System.out.println("In RefreshSelected()");}
		selectedList.removeAll();
		int size = selectedItems.size();
		for (int i = 0; i < size; i++)
		{
			String item = selectedItems.get(i);
			if (VERBOSE){ System.out.println("Re-Adding: " + item); }
			selectedList.add(item);
		}
	}
	
	/**
	 * 	ActionListener for the push button
	 */
	private ActionListener pushListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			if (DEBUG) {System.out.println("In PushListener");}
			int[] selected = allList.getSelectedIndexes();
			for (int i = 0; i < selected.length; i++)
			{
				int index = selected[i];
				String item = allItems.get(index);
				if (VERBOSE){System.out.println("Pushing: " + item);}
				selectedList.add(item);
				selectedItems.add(item);
				allList.deselect(index);
			}
			
		}
	};
	
	private ActionListener popListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			if (DEBUG) {System.out.println("In PopListener");}
			int[] selected = selectedList.getSelectedIndexes();
			Vector<String> newList = new Vector<String>();
			Vector<Integer> sel = new Vector<Integer>();
			for (int i = 0; i < selected.length; i++)
			{
				sel.add(new Integer(selected[i]));
			}
			int size = selectedItems.size();
			for (int i = 0; i < size; i++)
			{
				if (!sel.contains(i))
				{
					newList.add(selectedItems.get(i));
				}
			}
			selectedItems = newList;
			RefreshSelected();
		}
	};
	
	private ActionListener clearListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			selectedItems.clear();
			RefreshSelected();
		}
	};
	
	private ActionListener editListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			Query_GUI dialog = new Query_GUI(null);
			Map<Enums, String> result = dialog.ShowDialog();
			// Debug logic
			if (DEBUG)
			{
				if (result != null)
				{
					System.out.println("DIALOG RETURNED:");
					for(Enums f : result.keySet())
					{
						System.out.println(result.get(f));
						System.out.println("=-=-=-=-=-=-=-=--=-=-=-=-");
					}
				}
				else
				{
					System.out.println("DIALOG WAS CANCELED.");
				}
			}
		}
	};
}
