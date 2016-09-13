package editor.taskEditor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import frameUtil.DateTime;
import global.AdditionalTaskSetting;

import java.awt.GridBagLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

public class TaskSet extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3615462616189335440L;
	private final JPanel contentPanel = new JPanel();
	private DateTime timeout=new DateTime();
	private JCheckBox CB_complete;
	private JCheckBox CB_monitor;
	private JCheckBox CB_hide;
	private JButton okButton;
	private JButton cancelButton;

	private boolean cancel=true;
	private JLabel lblName;
	private JTextField TF_name;

	/**
	 * Create the dialog.
	 */
	public TaskSet(JDialog frame) {
		super(frame,true);
		

		setBounds(100, 100, 334, 201);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0};
		contentPanel.setLayout(gbl_contentPanel);
		
		lblName = new JLabel("Name");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.anchor = GridBagConstraints.EAST;
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		contentPanel.add(lblName, gbc_lblName);
		
		TF_name = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		contentPanel.add(TF_name, gbc_textField);
		TF_name.setColumns(10);

		JLabel lblTimeout = new JLabel("Timeout");
		GridBagConstraints gbc_lblTimeout = new GridBagConstraints();
		gbc_lblTimeout.insets = new Insets(0, 0, 5, 5);
		gbc_lblTimeout.gridx = 0;
		gbc_lblTimeout.gridy = 1;
		contentPanel.add(lblTimeout, gbc_lblTimeout);

		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		contentPanel.add(timeout.getPanel(), gbc_panel);

		CB_complete = new JCheckBox("Complete on finish");
		GridBagConstraints gbc_CB_complete = new GridBagConstraints();
		gbc_CB_complete.gridwidth = 2;
		gbc_CB_complete.insets = new Insets(0, 0, 5, 0);
		gbc_CB_complete.gridx = 0;
		gbc_CB_complete.gridy = 2;
		contentPanel.add(CB_complete, gbc_CB_complete);

		CB_monitor = new JCheckBox("Monitored?");
		CB_monitor.setSelected(true);
		GridBagConstraints gbc_CB_monitor = new GridBagConstraints();
		gbc_CB_monitor.insets = new Insets(0, 0, 5, 0);
		gbc_CB_monitor.gridwidth = 2;
		gbc_CB_monitor.gridx = 0;
		gbc_CB_monitor.gridy = 3;
		contentPanel.add(CB_monitor, gbc_CB_monitor);
		
		CB_hide=new JCheckBox("Hidden");
		GridBagConstraints gbc_CB_hide=new GridBagConstraints();
		gbc_CB_hide.gridwidth=2;
		gbc_CB_hide.gridx=0;
		gbc_CB_hide.gridy=4;
		contentPanel.add(CB_hide,gbc_CB_hide);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		ActionListener closing=new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getActionCommand().equals("OK")){
					cancel=false;
				}
				dispose();
			}
		};
		cancelButton.addActionListener(closing);
		okButton.addActionListener(closing);
	}
	/**
	 * returns button selection
	 * @return 0=cancel, 1=OK
	 */
	public int getSelection(){
		if(cancel){
			return 0;
		}
		else{
			return 1;
		}
	}
	public AdditionalTaskSetting getValues(){
		AdditionalTaskSetting values=new AdditionalTaskSetting();
		values.setMonitored(CB_monitor.isSelected());
		values.setHidden(CB_hide.isSelected());
		values.setTaskComplete(CB_complete.isSelected());
		values.setTimeout(timeout.getTime());
		values.setName(TF_name.getText());
		return values;
	}
	public void setValues(boolean monitor,boolean hide,long time,String name,boolean completeOnFinish){
		CB_hide.setSelected(hide);
		CB_monitor.setSelected(monitor);
		CB_complete.setSelected(completeOnFinish);
		TF_name.setText(name);
		timeout.setTime(time);
	}

}
