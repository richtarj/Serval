package reportingTool.src;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.List;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.util.Vector;

import reportingTool.src.ErrorDialog;

@SuppressWarnings("serial")
public class SaveDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private boolean returnable;
	private Vector<Report_Object> list;
	private List uxFileList;
	private JTextField uxTitleBox;

	/**
	 * Create the dialog.
	 */
	public SaveDialog(Vector<Report_Object> l) { 
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setModal(true);
		setBounds(100, 100, 266, 387);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		list = new Vector<Report_Object>(l);
		
		uxFileList = new List();
		uxFileList.setBackground(Color.WHITE);
		uxFileList.setMultipleMode(false);
		uxFileList.setBounds(6, 36, 246, 219);
		
		int len = l.size();
		for (int i = 0; i < len; i++)
		{
			uxFileList.add(list.get(i).getTitle());
		}
		
		contentPanel.add(uxFileList);
		
		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblTitle.setBounds(6, 261, 36, 16);
		contentPanel.add(lblTitle);
		
		uxTitleBox = new JTextField();
		uxTitleBox.setBounds(6, 289, 246, 28);
		contentPanel.add(uxTitleBox);
		uxTitleBox.setColumns(10);
		
		JLabel lblSavedReports = new JLabel("Saved Reports");
		lblSavedReports.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblSavedReports.setBounds(80, 14, 101, 16);
		contentPanel.add(lblSavedReports);
		
		this.addWindowListener( new WindowAdapter() {
		    public void windowOpened( WindowEvent e ){
		        uxTitleBox.requestFocus();
		    }
		});
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Save");
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
	}
	
	private ActionListener buttonListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getActionCommand() == "OK")
			{
				returnable = true;
				if (!uxTitleBox.getText().equals(""))
				{
					returnable = true;
					dispose();
				}
				else
				{
					ErrorDialog d = new ErrorDialog("Title cannot be empty.");
					d.ShowDialog();
				}
			}
			if (e.getActionCommand() == "CANCEL")
			{
				returnable = false;
				dispose();
			}
		}
	};
	
	public String ShowDialog()
	{
		this.setVisible(true);
		if (returnable)
		{
			return uxTitleBox.getText();
		}
		else
		{
			return null;
		}
	}
}
