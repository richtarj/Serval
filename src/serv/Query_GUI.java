package serv;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.JComboBox;

import serv.Query_Object;
import serv.Enums;

import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Vector;


public class Query_GUI extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	/**
	 * All of the text fields for entering
	 * query data
	 */
	private JTextField uxTitleBox;
	private JTextArea uxConditionalBox;
	private JTextArea uxIntroBox;
	private JTextArea uxMainBox;
	private JTextArea uxConclusionBox;
	private boolean returnable;
	private String[] arr = 
	{
		"SQLite",
		"IBM DB2",
		"PostgreSQL",
		"MySQL",
		"SQL Server"
		
	};
	private Vector<String> dbTypes = new Vector<String>(Arrays.asList(arr));
	
	
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		try {
//			Query_GUI dialog = new Query_GUI(null);
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public Query_GUI(Query_Object q) {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setModal(true);
		
		setResizable(false);
		setBounds(100, 100, 456, 675);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel uxTitleLabel = new JLabel("Title:");
		uxTitleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		uxTitleLabel.setBounds(69, 15, 36, 16);
		contentPanel.add(uxTitleLabel);
		
		JLabel lblConditional = new JLabel("Conditional");
		lblConditional.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblConditional.setBounds(24, 50, 81, 16);
		contentPanel.add(lblConditional);
		
		JLabel lblQuery = new JLabel("Query:");
		lblQuery.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblQuery.setBounds(57, 70, 48, 16);
		contentPanel.add(lblQuery);
		
		JLabel lblInfo = new JLabel("Intro:");
		lblInfo.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblInfo.setBounds(66, 168, 39, 16);
		contentPanel.add(lblInfo);
		
		JLabel lblMainQuery = new JLabel("Main Query:");
		lblMainQuery.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblMainQuery.setBounds(16, 300, 89, 16);
		contentPanel.add(lblMainQuery);
		
		uxTitleBox = new JTextField();
		uxTitleBox.setBounds(117, 10, 327, 28);
		contentPanel.add(uxTitleBox);
		uxTitleBox.setColumns(10);
		
		uxConditionalBox = new JTextArea();
		// used to catch tab keypress and turn them into 4 spaces
		uxConditionalBox.setDocument(new TabDocument());
		uxConditionalBox.setRows(30);
		uxConditionalBox.setColumns(5);
		uxConditionalBox.setBounds(117, 60, 323, 164);
		contentPanel.add(uxConditionalBox);
		
		JScrollPane uxConditionalScroll = new JScrollPane(uxConditionalBox);
		uxConditionalScroll.setBounds(117, 49, 323, 110);
		contentPanel.add(uxConditionalScroll);
		
		uxIntroBox = new JTextArea();
		// used to catch tab keypress and turn them into 4 spaces
		uxIntroBox.setDocument(new TabDocument());
		uxIntroBox.setRows(30);
		uxIntroBox.setColumns(5);
		uxIntroBox.setBounds(117, 270, 327, 164);
		contentPanel.add(uxIntroBox);
		
		JScrollPane uxInfoScroll = new JScrollPane(uxIntroBox);
		uxInfoScroll.setBounds(117, 168, 323, 125);
		contentPanel.add(uxInfoScroll);
		
		uxMainBox = new JTextArea();
		uxMainBox.setDocument(new TabDocument());
		uxMainBox.setColumns(5);
		uxMainBox.setRows(30);
		uxMainBox.setBounds(118, 428, 317, 164);
		contentPanel.add(uxMainBox);
		
		JScrollPane uxMainScroll = new JScrollPane(uxMainBox);
		uxMainScroll.setBounds(117, 300, 323, 125);
		contentPanel.add(uxMainScroll);
		
		JLabel lblConclusion = new JLabel("Conclusion:");
		lblConclusion.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblConclusion.setBounds(16, 432, 89, 16);
		contentPanel.add(lblConclusion);
		
		uxConclusionBox = new JTextArea();
		uxConclusionBox.setDocument(new TabDocument());
		uxConclusionBox.setColumns(5);
		uxConclusionBox.setRows(30);
		uxConclusionBox.setBounds(-41, 445, 604, 164);
		contentPanel.add(uxConclusionBox);
		
		JScrollPane uxConclusionScroll = new JScrollPane(uxConclusionBox);
		uxConclusionScroll.setBounds(117, 432, 323, 125);
		contentPanel.add(uxConclusionScroll);
		
		JLabel lblDbType = new JLabel("DB Type:");
		lblDbType.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblDbType.setBounds(41, 576, 64, 16);
		contentPanel.add(lblDbType);
		
		JComboBox<String> uxTypeCombo = new JComboBox<String>(dbTypes);
		uxTypeCombo.setBounds(117, 569, 327, 27);
		contentPanel.add(uxTypeCombo);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(buttonListener);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("CANCEL");
				cancelButton.addActionListener(buttonListener);
				buttonPane.add(cancelButton);
			}
		}
		
		// Fill text boxes with data, if supplied
		if (q != null)
		{
			uxTitleBox.setText(q.getTitle());
			uxConditionalBox.setText(q.getConditionalQuery());
			uxIntroBox.setText(q.getIntro());
			uxMainBox.setText(q.getMainQuery());
			uxConclusionBox.setText(q.getConclusion());
		}
	}
	
	public Map<Enums, String> ShowDialog()
	{
		this.setVisible(true);
		if (returnable)
		{
			Map<Enums, String> d = new HashMap<Enums, String>();
			d.put(Enums.TITLE, uxTitleBox.getText());
			d.put(Enums.CONDITIONAL, uxConditionalBox.getText());
			d.put(Enums.INTRO, uxIntroBox.getText());
			d.put(Enums.MAIN, uxMainBox.getText());
			d.put(Enums.CONCLUSION, uxConclusionBox.getText());
			return d;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Used to catch the "TAB" keypress and replace it with 4 spaces
	 * taken from an answer at:
	 * http://stackoverflow.com/questions/363784/setting-the-tab-policy-in-swings-jtextpane
	 */
	static class TabDocument extends DefaultStyledDocument {
    	@Override
    	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
    		str = str.replaceAll("\t", "    ");
    		super.insertString(offs, str, a);
    	}
    }
	
	private void CheckContents()
	{
		
	}
	
	private ActionListener buttonListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getActionCommand() == "OK")
			{
				returnable = true;
			}
			if (e.getActionCommand() == "CANCEL")
			{
				returnable = false;
			}
			dispose();
		}
	};
}
