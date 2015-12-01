package reportingTool.src;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;


public class Query_GUI extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField uxTitleBox;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		try {
//			Query_GUI dialog = new Query_GUI();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public Query_GUI(String[] list) {
		setResizable(false);
		setBounds(100, 100, 456, 658);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel uxTitleLabel = new JLabel("Title:");
		uxTitleLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		uxTitleLabel.setBounds(69, 17, 36, 16);
		contentPanel.add(uxTitleLabel);
		
		JLabel lblConditional = new JLabel("Conditional");
		lblConditional.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblConditional.setBounds(24, 61, 81, 16);
		contentPanel.add(lblConditional);
		
		JLabel lblQuery = new JLabel("Query:");
		lblQuery.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblQuery.setBounds(57, 81, 48, 16);
		contentPanel.add(lblQuery);
		
		JLabel lblInfo = new JLabel("Info:");
		lblInfo.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblInfo.setBounds(69, 238, 36, 16);
		contentPanel.add(lblInfo);
		
		JLabel lblMainQuery = new JLabel("Main Query:");
		lblMainQuery.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblMainQuery.setBounds(16, 416, 89, 16);
		contentPanel.add(lblMainQuery);
		
		uxTitleBox = new JTextField();
		uxTitleBox.setBounds(117, 12, 327, 28);
		contentPanel.add(uxTitleBox);
		uxTitleBox.setColumns(10);
		
		JTextArea uxConditionalArea = new JTextArea();
		// used to catch tab keypress and turn them into 4 spaces
		uxConditionalArea.setDocument(new TabDocument());
		uxConditionalArea.setRows(30);
		uxConditionalArea.setColumns(5);
		uxConditionalArea.setBounds(117, 60, 323, 164);
		contentPanel.add(uxConditionalArea);
		
		JScrollPane uxConditionalScroll = new JScrollPane(uxConditionalArea);
		uxConditionalScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		uxConditionalScroll.setBounds(117, 60, 323, 164);
		contentPanel.add(uxConditionalScroll);
		
		JTextArea uxInfoBox = new JTextArea();
		// used to catch tab keypress and turn them into 4 spaces
		uxInfoBox.setDocument(new TabDocument());
		uxInfoBox.setRows(30);
		uxInfoBox.setColumns(5);
		uxInfoBox.setBounds(117, 270, 327, 164);
		contentPanel.add(uxInfoBox);
		
		JScrollPane uxInfoScroll = new JScrollPane(uxInfoBox);
		uxInfoScroll.setBounds(117, 238, 323, 164);
		contentPanel.add(uxInfoScroll);
		
		JTextArea uxMainBox = new JTextArea();
		uxMainBox.setColumns(5);
		uxMainBox.setRows(30);
		uxMainBox.setBounds(118, 428, 317, 164);
		contentPanel.add(uxMainBox);
		
		JScrollPane uxMainScroll = new JScrollPane(uxMainBox);
		uxMainScroll.setBounds(118, 416, 323, 164);
		contentPanel.add(uxMainScroll);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
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
}
