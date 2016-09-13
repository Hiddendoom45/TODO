package viewer;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JTextField;

import global.ItemType;
import global.items.DailyItem;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JList;

import editor.EditMain;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

public class ViewDaily extends ViewGenerics{
	/**
	 * 
	 */
	private static final long serialVersionUID = -885794188892319262L;
	private JTextField TF_M;
	private JTextField TF_H;
	private JButton B_taskRun;
	private JTextArea TA_description;
	private JLabel lblPriority;
	private JTextField TF_priority;
	
	
	//all variables in relation to super class
	private ViewVar var;
	private JList<String> L_incomplete;
	private JList<String> L_complete;
	private JButton B_edit;
	private JButton B_delete;
	private JButton B_toggle;
	private JButton B_update;
	private ArrayList<String> complete=new ArrayList<String>();//holds name
	private ArrayList<String> incomplete=new ArrayList<String>();//ditto
	private ArrayList<Long> completeID=new ArrayList<Long>();//keep id for purposes of editing/removing item in variable area
	private ArrayList<Long> incompleteID=new ArrayList<Long>();//ditto
	
	
	/**
	 * Create the panel.
	 */
	public ViewDaily(final ViewVar var) {
		this.var=var;
		super.var=var;
		super.incompleteID=incompleteID;
		super.completeID=completeID;
		super.incomplete=incomplete;
		super.complete=complete;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblIncomplete = new JLabel("Incomplete");
		GridBagConstraints gbc_lblIncomplete = new GridBagConstraints();
		gbc_lblIncomplete.insets = new Insets(0, 0, 5, 5);
		gbc_lblIncomplete.gridx = 0;
		gbc_lblIncomplete.gridy = 0;
		add(lblIncomplete, gbc_lblIncomplete);
		
		JLabel lblCompleted = new JLabel("Completed");
		GridBagConstraints gbc_lblCompleted = new GridBagConstraints();
		gbc_lblCompleted.insets = new Insets(0, 0, 5, 0);
		gbc_lblCompleted.gridx = 3;
		gbc_lblCompleted.gridy = 0;
		add(lblCompleted, gbc_lblCompleted);
		
		JScrollPane SP_incomplete = new JScrollPane();
		SP_incomplete.setPreferredSize(new Dimension(0, 0));
		GridBagConstraints gbc_SP_incomplete = new GridBagConstraints();
		gbc_SP_incomplete.weightx = 2.0;
		gbc_SP_incomplete.fill = GridBagConstraints.BOTH;
		gbc_SP_incomplete.gridheight = 7;
		gbc_SP_incomplete.insets = new Insets(0, 0, 5, 5);
		gbc_SP_incomplete.gridx = 0;
		gbc_SP_incomplete.gridy = 1;
		add(SP_incomplete, gbc_SP_incomplete);
		
		L_incomplete = new JList<String>();
		SP_incomplete.setViewportView(L_incomplete);
		
		JLabel lblResetTime = new JLabel("Reset Time");
		GridBagConstraints gbc_lblResetTime = new GridBagConstraints();
		gbc_lblResetTime.gridwidth = 2;
		gbc_lblResetTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblResetTime.gridx = 1;
		gbc_lblResetTime.gridy = 1;
		add(lblResetTime, gbc_lblResetTime);
		
		JScrollPane SP_complete = new JScrollPane();
		SP_complete.setPreferredSize(new Dimension(0, 0));
		GridBagConstraints gbc_SP_complete = new GridBagConstraints();
		gbc_SP_complete.weightx = 2.0;
		gbc_SP_complete.fill = GridBagConstraints.BOTH;
		gbc_SP_complete.gridheight = 7;
		gbc_SP_complete.insets = new Insets(0, 0, 5, 0);
		gbc_SP_complete.gridx = 3;
		gbc_SP_complete.gridy = 1;
		add(SP_complete, gbc_SP_complete);
		
		L_complete = new JList<String>();
		SP_complete.setViewportView(L_complete);
		
		JLabel lblH = new JLabel("H");
		GridBagConstraints gbc_lblH = new GridBagConstraints();
		gbc_lblH.insets = new Insets(0, 0, 5, 5);
		gbc_lblH.gridx = 1;
		gbc_lblH.gridy = 2;
		add(lblH, gbc_lblH);
		
		JLabel lblM = new JLabel("M");
		GridBagConstraints gbc_lblM = new GridBagConstraints();
		gbc_lblM.insets = new Insets(0, 0, 5, 5);
		gbc_lblM.gridx = 2;
		gbc_lblM.gridy = 2;
		add(lblM, gbc_lblM);
		
		TF_M = new JTextField();
		TF_M.setEditable(false);
		GridBagConstraints gbc_TF_M = new GridBagConstraints();
		gbc_TF_M.insets = new Insets(0, 0, 5, 5);
		gbc_TF_M.fill = GridBagConstraints.HORIZONTAL;
		gbc_TF_M.gridx = 2;
		gbc_TF_M.gridy = 3;
		add(TF_M, gbc_TF_M);
		TF_M.setColumns(10);
		
		TF_H = new JTextField();
		TF_H.setEditable(false);
		GridBagConstraints gbc_TF_H = new GridBagConstraints();
		gbc_TF_H.insets = new Insets(0, 0, 5, 5);
		gbc_TF_H.fill = GridBagConstraints.HORIZONTAL;
		gbc_TF_H.gridx = 1;
		gbc_TF_H.gridy = 3;
		add(TF_H, gbc_TF_H);
		TF_H.setColumns(10);
		
		B_edit = new JButton("Edit");
		GridBagConstraints gbc_B_edit = new GridBagConstraints();
		gbc_B_edit.insets = new Insets(0, 0, 5, 5);
		gbc_B_edit.gridx = 1;
		gbc_B_edit.gridy = 4;
		add(B_edit, gbc_B_edit);
		
		B_delete = new JButton("Delete");
		GridBagConstraints gbc_B_delete = new GridBagConstraints();
		gbc_B_delete.insets = new Insets(0, 0, 5, 5);
		gbc_B_delete.gridx = 2;
		gbc_B_delete.gridy = 4;
		add(B_delete, gbc_B_delete);
		
		B_toggle = new JButton("Complete>>");
		GridBagConstraints gbc_B_toggle = new GridBagConstraints();
		gbc_B_toggle.gridwidth = 2;
		gbc_B_toggle.insets = new Insets(0, 0, 5, 5);
		gbc_B_toggle.gridx = 1;
		gbc_B_toggle.gridy = 5;
		add(B_toggle, gbc_B_toggle);
		
		B_update = new JButton("Update");
		GridBagConstraints gbc_B_update = new GridBagConstraints();
		gbc_B_update.insets = new Insets(0, 0, 5, 5);
		gbc_B_update.gridx = 1;
		gbc_B_update.gridy = 6;
		add(B_update, gbc_B_update);
		
		B_taskRun = new JButton("Run Task");
		GridBagConstraints gbc_B_taskRun = new GridBagConstraints();
		gbc_B_taskRun.insets = new Insets(0, 0, 5, 5);
		gbc_B_taskRun.gridx = 2;
		gbc_B_taskRun.gridy = 6;
		add(B_taskRun, gbc_B_taskRun);
		
		lblPriority = new JLabel("Priority");
		GridBagConstraints gbc_lblPriority = new GridBagConstraints();
		gbc_lblPriority.anchor = GridBagConstraints.EAST;
		gbc_lblPriority.insets = new Insets(0, 0, 5, 5);
		gbc_lblPriority.gridx = 1;
		gbc_lblPriority.gridy = 7;
		add(lblPriority, gbc_lblPriority);
		
		TF_priority = new JTextField();
		TF_priority.setEditable(false);
		GridBagConstraints gbc_TF_priority = new GridBagConstraints();
		gbc_TF_priority.insets = new Insets(0, 0, 5, 5);
		gbc_TF_priority.fill = GridBagConstraints.HORIZONTAL;
		gbc_TF_priority.gridx = 2;
		gbc_TF_priority.gridy = 7;
		add(TF_priority, gbc_TF_priority);
		TF_priority.setColumns(10);
		
		JLabel lblDescription = new JLabel("Description");
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.gridwidth = 4;
		gbc_lblDescription.insets = new Insets(0, 0, 5, 0);
		gbc_lblDescription.gridx = 0;
		gbc_lblDescription.gridy = 8;
		add(lblDescription, gbc_lblDescription);
		
		JScrollPane SP_description = new JScrollPane();
		SP_description.setPreferredSize(new Dimension(0, 0));
		GridBagConstraints gbc_SP_description = new GridBagConstraints();
		gbc_SP_description.weighty = 20.0;
		gbc_SP_description.fill = GridBagConstraints.BOTH;
		gbc_SP_description.gridwidth = 4;
		gbc_SP_description.gridx = 0;
		gbc_SP_description.gridy = 9;
		add(SP_description, gbc_SP_description);
		
		TA_description = new JTextArea();
		TA_description.setEditable(false);
		SP_description.setViewportView(TA_description);

		
		B_edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditMain edit;
				if(var.getEdit()==null||!var.getEdit().isShowing()){
					edit=new EditMain(var.getConsole(),var.getItemData());
					var.setEdit(edit);
				}else{
					edit=var.getEdit();
				}
				edit.setVisible(true);
				if(L_complete.getSelectedIndex()>-1){
					edit.setSelected(0,completeID.get(L_complete.getSelectedIndex()));
				}
				else if(L_incomplete.getSelectedIndex()>-1){
					edit.setSelected(0,incompleteID.get(L_incomplete.getSelectedIndex()));
				}
				else{
					edit.setSelected(0, 0);
				}
				edit.getVar().setView(var.getView());
			}
		});
		
		B_taskRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(L_complete.getSelectedIndex()>-1){
					var.getDailyItem(completeID.get(L_complete.getSelectedIndex())).callTask(null);
				}
				else if(L_incomplete.getSelectedIndex()>-1){
					var.getDailyItem(incompleteID.get(L_incomplete.getSelectedIndex())).callTask(null);
				}
			}
		});
		super.L_incomplete=L_incomplete;
		super.L_complete=L_complete;
		super.B_delete=B_delete;
		super.B_update=B_update;
		super.B_toggle=B_toggle;
		super.TA_description=TA_description;
		super.setFilter(ItemType.DailyItem);
		super.setListeners();
		
	}
	
	
	/**
	 * resets all the GUI components to their default values
	 */
	public void reset(){
		super.reset();
		TF_M.setText("0");
		TF_H.setText("0");
		TF_priority.setText("0");
	}
	public void setValues(boolean complete){
		super.setValues(complete);
		if(complete){
			if(L_complete.getSelectedIndex()>-1){
				DailyItem daily=var.getDailyItem(completeID.get(L_complete.getSelectedIndex()));
				TF_M.setText(""+daily.getResetM());
				TF_H.setText(""+daily.getResetH());
				TF_priority.setText(""+daily.getPriority());
			}
		}
		else{
			if(L_incomplete.getSelectedIndex()>-1){
				DailyItem daily=var.getDailyItem(incompleteID.get(L_incomplete.getSelectedIndex()));
				TF_M.setText(""+daily.getResetM());
				TF_H.setText(""+daily.getResetH());
				TF_priority.setText(""+daily.getPriority());
			}
		}
	}
	public JPanel getP_Daily(){
		return this;
	}

}
