package reportingTool.src;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.util.Vector;
import javax.swing.JLabel;
import java.awt.Font;

@SuppressWarnings("serial")
public class OpenDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Vector<Report_Object> list;
	private boolean returnable;
	List uxFileList;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		try {
//			OpenDialog dialog = new OpenDialog();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public OpenDialog(Vector<Report_Object> l) {
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setModal(true);
		setBounds(100, 100, 263, 335);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		list = new Vector<Report_Object>(l);
		
		uxFileList = new List();
		uxFileList.setMultipleMode(false);
		uxFileList.setBounds(10, 45, 243, 219);
		
		int len = l.size();
		for (int i = 0; i < len; i++)
		{
			uxFileList.add(list.get(i).getTitle());
		}
		contentPanel.add(uxFileList);
		{
			JLabel lblSavedReports = new JLabel("Saved Reports");
			lblSavedReports.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
			lblSavedReports.setBounds(78, 16, 101, 16);
			contentPanel.add(lblSavedReports);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Open");
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
	
	
	public Report_Object ShowDialog()
	{
		this.setVisible(true);
		if (returnable)
		{
			return list.get(uxFileList.getSelectedIndex());
		}
		else
		{
			return null;
		}
	}
	
	private ActionListener buttonListener = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getActionCommand() == "OK")
			{
				returnable = true;
				dispose();
			}
			if (e.getActionCommand() == "CANCEL")
			{
				returnable = false;
				dispose();
			}
		}
	};
}
