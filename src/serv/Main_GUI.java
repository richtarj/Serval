package reportingTool.src;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.EventQueue;
import java.awt.List;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Rectangle;

import java.util.Vector;

import reportingTool.src.Query_GUI;
import reportingTool.src.Query_Object;
import reportingTool.src.SQLiteJDBC;
import reportingTool.src.ErrorDialog;
import reportingTool.src.OpenDialog;
import reportingTool.src.SaveDialog;


public class Main_GUI {

	private JFrame frame;
	private List allList; // List displaying all possible queries
	private List selectedList; // List displaying selected queries for report
	private Vector<Query_Object> allItems;  // Vector of all queries
	private Vector<Query_Object> selectedItems; // Vector of selected queries
	private JButton uxRunBtn;
	private boolean VERBOSE = true;
	private boolean DEBUG = false;
	private SQLiteJDBC db;
	private final int PREFERRED_ERROR_WIDTH = 450; // Preferred width for the ErrorDialog panel.

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
		frame.setBounds(100, 100, 528, 283);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		// Makes sure to close the connection when the 
		// application quits
		frame.addWindowListener(new WindowAdapter()
		{
		    public void windowClosing(WindowEvent e)
		    {
		        if (db.isConnected())
		        {
		        	db.CloseConnection();
		        }
		    }
		});
		
		db = new SQLiteJDBC();
		db.GetConnection();
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 528, 302);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		selectedItems = new Vector<Query_Object>();
		
		allList = new List();
		allList.setFont(new Font("Arial", Font.PLAIN, 12));
		allList.setMultipleMode(true);
		allList.setForeground(Color.BLACK);
		allList.setBackground(Color.WHITE);
		allList.setMultipleMode(true);
		
		PopulateAllList();
		
		allList.setBounds(10, 62, 208, 144);
		panel.add(allList);
		
		selectedList = new List();
		selectedList.setFont(new Font("Arial", Font.PLAIN, 12));
		selectedList.setForeground(Color.BLACK);
		selectedList.setBackground(Color.WHITE);
		selectedList.setMultipleMode(true);
		selectedList.setBounds(290, 62, 216, 144);
		panel.add(selectedList);
		
		JButton pushBtn = new JButton("->");
		pushBtn.setBounds(224, 74, 60, 29);
		pushBtn.addActionListener(pushListener);
		panel.add(pushBtn);
		
		JButton popBtn = new JButton("<-");
		popBtn.setBounds(224, 164, 60, 29);
		popBtn.addActionListener(popListener);
		panel.add(popBtn);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 516, 22);
		panel.add(menuBar);
		
		JMenu file = new JMenu("File");
		JMenuItem uxMenuOpen = new JMenuItem("Open...");
		JMenuItem uxMenuSave = new JMenuItem("Save...");
		uxMenuOpen.addActionListener(openListener);
		uxMenuSave.addActionListener(saveListener);
		file.add(uxMenuOpen);
		file.add(uxMenuSave);
		
		JMenu about = new JMenu("About");
		JMenuItem aboutserval = new JMenuItem("About Serval");
		about.add(aboutserval);
		
		
		menuBar.add(file);
		menuBar.add(about);
		
		JButton clearBtn = new JButton("Clear");
		clearBtn.setBounds(417, 224, 76, 29);
		clearBtn.addActionListener(clearListener);
		panel.add(clearBtn);
		
		JButton uxEditBtn = new JButton("Edit");
		uxEditBtn.setBounds(110, 224, 76, 29);
		uxEditBtn.addActionListener(editListener);
		panel.add(uxEditBtn);
		
		JButton uxNewBtn = new JButton("New");
		uxNewBtn.setBounds(34, 224, 76, 29);
		uxNewBtn.addActionListener(newListener);
		panel.add(uxNewBtn);
		
		JLabel lblAllQueries = new JLabel("All Queries");
		lblAllQueries.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblAllQueries.setBounds(73, 34, 78, 16);
		panel.add(lblAllQueries);
		
		JLabel lblCurrentReport = new JLabel("Current Report");
		lblCurrentReport.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblCurrentReport.setBounds(337, 34, 106, 16);
		panel.add(lblCurrentReport);
		
		uxRunBtn = new JButton("Run Report");
		uxRunBtn.setBounds(311, 224, 94, 29);
		uxRunBtn.addActionListener(runListener);
		panel.add(uxRunBtn);
		uxRunBtn.setEnabled(false);
		
	}
	
	
	private void PopulateAllList()
	{
		if (DEBUG) {System.out.println("In PopulateAllList()");}
		AddQueries();
		allList.removeAll();
		int size = allItems.size();
		for (int i = 0; i < size; i++)
		{
			allList.add(allItems.get(i).toString());
		}
	}
	
	private void AddQueries()
	{
		if (DEBUG) {System.out.println("In AddQueries()");}
		allItems = db.getQueries("SQLite");
	}
	
	private void RefreshSelected()
	{
		if (DEBUG) {System.out.println("In RefreshSelected()");}
		selectedList.removeAll();
		int size = selectedItems.size();
		for (int i = 0; i < size; i++)
		{
			Query_Object item = selectedItems.get(i);
			if (VERBOSE){ System.out.println("Re-Adding: " + item); }
			selectedList.add(item.toString());
		}
	}
	
	private Query_Object FindQuery(int id, Vector<Query_Object> v)
	{
		int len = v.size();
		for (int i = 0; i < len; i++)
		{
			if (v.get(i).getID() == id)
			{
				return v.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Method: VerifyReportOrder
	 * 
	 * Creates a vector of query objects in the correct
	 * order specified in the report object. Useful because 
	 * database will return queries in ascending order
	 * and it will not return duplicates
	 * 
	 * Parameters:
	 * 
	 *   Report_Object r        - used to get order/frequency of
	 *                            	queries
	 *   Vector<Query_Object> v - vector all query objects in report
	 *                            	although possibily out of order or
	 *                             	not enough of them
	 * 
	 * Returns:
	 * 
	 *   Vector<Query_Object> - vector of query objects that exactly matches
	 *                          	the desired report
	 */
	private Vector<Query_Object> VerifyReportOrder(Report_Object r, Vector<Query_Object> v)
	{
		Vector<Query_Object> fin = new Vector<Query_Object>();
		Vector<Integer> list = r.getQueries();
		int len = list.size();
		for (int i = 0; i < len; i++)
		{
			Query_Object q = FindQuery(list.get(i), v);
			if (q != null)
			{
				fin.add(q);				
			}
		}
		return fin;	
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
				Query_Object item = allItems.get(index);
				if (VERBOSE){System.out.println("Pushing: " + item);}
				selectedList.add(item.toString());
				selectedItems.add(item);
				allList.deselect(index);
			}
			uxRunBtn.setEnabled(true);
		}
	};
	
	private ActionListener popListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			if (DEBUG) {System.out.println("In PopListener");}
			int[] selected = selectedList.getSelectedIndexes();
			Vector<Query_Object> newList = new Vector<Query_Object>();
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
			if (selectedItems.size() == 0)
			{
				uxRunBtn.setEnabled(false);
			}
		}
	};
	
	private ActionListener clearListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			selectedItems.clear();
			RefreshSelected();
			uxRunBtn.setEnabled(false);
		}
	};
	
	private ActionListener saveListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			if (selectedItems.size() > 0)
			{
				Vector<Report_Object> v = db.GetReports("SQLite");
				SaveDialog d = new SaveDialog(v);
				String title = d.ShowDialog();
				if (title != null)
				{
					Vector<Integer> l = new Vector<Integer>();
					int len = selectedItems.size();
					String type = selectedItems.get(0).getType();
					for (int i = 0; i < len; i++)
					{
						l.add(selectedItems.get(i).getID());
					}
					Report_Object r = new Report_Object(title, l, type);
					if(db.SaveNewReport(r))
					{
						ErrorDialog f = new ErrorDialog("Report succesfully saved.");
						f.ShowDialog();
					}
				}
			}
			else
			{
				ErrorDialog d = new ErrorDialog("Cannot save empty report.");
				d.ShowDialog();
			}
		}
	};
	
	private ActionListener openListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			if (DEBUG) { System.out.println("Creating OpenDialog."); }
			Vector<Report_Object> v = db.GetReports("SQLite");
			OpenDialog d = new OpenDialog(v);
			Report_Object result = d.ShowDialog();
			if (result != null)
			{
				selectedList.removeAll();
				selectedItems.clear();
				Vector<Query_Object> queries = db.GetSelectedQueries(result.getQueries());
				selectedItems.addAll(VerifyReportOrder(result, queries));
				RefreshSelected();
				uxRunBtn.setEnabled(true);
			}
		}
	};
	
	private ActionListener newListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			Query_GUI dialog = new Query_GUI(null);
			Query_Object result = dialog.ShowDialog();
			if (result != null)
			{
				if (db.SaveNewQuery(result))
				{
					PopulateAllList();
				}
			}
		}
	};
	
	private ActionListener runListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			int top = selectedItems.size();
			if (top > 0)
			{
				StringBuilder sb = new StringBuilder();
				sb.append("<html>");
				for (int i = 0; i < top; i++)
				{
					Query_Object obj = selectedItems.get(i);
					sb.append(obj.getTitle() + ": " + obj.getID()+"<br>");
				}
				sb.append("</html>");
				ErrorDialog d = new ErrorDialog(sb.toString());
				d.pack();
				Rectangle r = d.getBounds();
				Rectangle n = new Rectangle((int)r.getX(), (int)r.getY(), PREFERRED_ERROR_WIDTH, (int)r.getHeight());
				d.setBounds(n);
				d.ShowDialog();
			}
			else
			{
				ErrorDialog d = new ErrorDialog("Cannot run an empty report.");
				d.ShowDialog();
			}
		}
	};
	
	private ActionListener editListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			boolean cont = true;
			int[] sel = allList.getSelectedIndexes();
			if (sel.length != 1)
			{
				ErrorDialog d = new ErrorDialog("You cannot edit multiple queries at once.");
				d.ShowDialog();
				cont = false;
			}
			if (cont)
			{
				Query_GUI dialog = new Query_GUI(allItems.get(sel[0]));
				Query_Object result = dialog.ShowDialog();
				// Debug logic
				if (DEBUG)
				{
					if (result != null)
					{
						System.out.println("DIALOG RETURNED:");
						System.out.println("Title: " + result.getTitle());
						System.out.println("ID: " + result.getID());
						System.out.println("Meta_ID: " + result.getMetaID());
						System.out.println("Type: " + result.getType());
						System.out.println("Conditional: " + result.getConditionalQuery());
						System.out.println("Main Query: " + result.getMainQuery());
						System.out.println("Intro: " + result.getIntro());
						System.out.println("Conclusion: " + result.getConclusion());						
					}	
					else
					{
						System.out.println("DIALOG WAS CANCELED.");
					}
				}
				if (result != null)
				{
					if (db.UpdateExistingQuery(result))
					{
						PopulateAllList();
					}
				}
			}
		}
	};
}
