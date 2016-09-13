package editor.taskEditor;

import javax.swing.JPanel;

import global.Task;
import global.tasks.WorkflowTask;

import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.io.File;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class EditWorkflowTask extends JPanel implements EditTaskGenerics {
	/**
	 * 
	 */
	private static final long serialVersionUID = 327991311827383380L;
	
	private JTextField TF_dir;
	private JTextArea TA_inputs;

	/**
	 * Create the panel.
	 */
	public EditWorkflowTask() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblWorkflowDirectory = new JLabel("Workflow Directory");
		GridBagConstraints gbc_lblWorkflowDirectory = new GridBagConstraints();
		gbc_lblWorkflowDirectory.insets = new Insets(0, 0, 5, 5);
		gbc_lblWorkflowDirectory.anchor = GridBagConstraints.EAST;
		gbc_lblWorkflowDirectory.gridx = 0;
		gbc_lblWorkflowDirectory.gridy = 0;
		add(lblWorkflowDirectory, gbc_lblWorkflowDirectory);
		
		TF_dir = new JTextField();
		GridBagConstraints gbc_TF_dir = new GridBagConstraints();
		gbc_TF_dir.insets = new Insets(0, 0, 5, 0);
		gbc_TF_dir.fill = GridBagConstraints.HORIZONTAL;
		gbc_TF_dir.gridx = 1;
		gbc_TF_dir.gridy = 0;
		add(TF_dir, gbc_TF_dir);
		TF_dir.setColumns(10);
		
		JLabel lblWorkflowInputs = new JLabel("Workflow inputs");
		GridBagConstraints gbc_lblWorkflowInputs = new GridBagConstraints();
		gbc_lblWorkflowInputs.anchor = GridBagConstraints.NORTH;
		gbc_lblWorkflowInputs.insets = new Insets(0, 0, 0, 5);
		gbc_lblWorkflowInputs.gridx = 0;
		gbc_lblWorkflowInputs.gridy = 1;
		add(lblWorkflowInputs, gbc_lblWorkflowInputs);
		
		JScrollPane SP_inputs = new JScrollPane();
		GridBagConstraints gbc_SP_inputs = new GridBagConstraints();
		gbc_SP_inputs.fill = GridBagConstraints.BOTH;
		gbc_SP_inputs.gridx = 1;
		gbc_SP_inputs.gridy = 1;
		add(SP_inputs, gbc_SP_inputs);
		
		TA_inputs = new JTextArea();
		TA_inputs.setWrapStyleWord(true);
		SP_inputs.setViewportView(TA_inputs);

	}

	@Override
	public Task grabTask() {
		WorkflowTask task=new WorkflowTask();
		task.setWorkflow(new File(TF_dir.getText()));
		task.setInputs(TA_inputs.getText());
		return task;
	}
	public void setValues(WorkflowTask task){
		TF_dir.setText(task.getWorkflow().getAbsolutePath());
		TA_inputs.setText(task.getInputs());
	}
	@Override
	public JPanel getPanel() {
		return this;
	}

}
