package editor.taskEditor;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;

import global.Task;
import global.tasks.NotifyTask;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
/**
 * task that is used to create an apple notification using automator(this won't work unless you have the same or similar setup)
 * @author Allen
 *
 */
public class EditNotifyTask extends JPanel implements EditTaskGenerics{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8379026677554816257L;
	//variables, title of notification and the messsage in it
	private JTextField TF_title;
	private JTextArea TA_message;

	/**
	 * Create the panel.
	 */
	public EditNotifyTask() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblTitle = new JLabel("Title");
		GridBagConstraints gbc_lblTitle = new GridBagConstraints();
		gbc_lblTitle.anchor = GridBagConstraints.EAST;
		gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitle.gridx = 0;
		gbc_lblTitle.gridy = 0;
		add(lblTitle, gbc_lblTitle);
		
		TF_title = new JTextField();
		GridBagConstraints gbc_TF_title = new GridBagConstraints();
		gbc_TF_title.insets = new Insets(0, 0, 5, 0);
		gbc_TF_title.fill = GridBagConstraints.HORIZONTAL;
		gbc_TF_title.gridx = 1;
		gbc_TF_title.gridy = 0;
		add(TF_title, gbc_TF_title);
		TF_title.setColumns(10);
		
		JLabel lblMessage = new JLabel("Message");
		GridBagConstraints gbc_lblMessage = new GridBagConstraints();
		gbc_lblMessage.insets = new Insets(0, 0, 0, 5);
		gbc_lblMessage.gridx = 0;
		gbc_lblMessage.gridy = 1;
		add(lblMessage, gbc_lblMessage);
		
		JScrollPane SP_message = new JScrollPane();
		GridBagConstraints gbc_SP_message = new GridBagConstraints();
		gbc_SP_message.fill = GridBagConstraints.BOTH;
		gbc_SP_message.gridx = 1;
		gbc_SP_message.gridy = 1;
		add(SP_message, gbc_SP_message);
		
		TA_message = new JTextArea();
		SP_message.setViewportView(TA_message);

	}
	@Override
	public Task grabTask() {
		NotifyTask task=new NotifyTask();
		task.setTitle(TF_title.getText());
		task.setMessage(TA_message.getText());
		return task;
	}
	//set values to ones of the task provided
	public void setValues(NotifyTask task){
		TF_title.setText(task.getTitle());
		TA_message.setText(task.getMessage());
	}
	//to allow eclipse to render things properly
	@Override
	public JPanel getPanel() {
		return this;
	}
	

}
