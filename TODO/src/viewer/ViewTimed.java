package viewer;


import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTextField;

import editor.EditMain;
import global.ItemType;
import global.items.TimedItem;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Dimension;
/**
 * view timed items
 * @author Allen
 *
 */
public class ViewTimed extends ViewGenerics {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4442065228269524231L;
	private JTextField TF_tReset;
	private JTextField TF_priority;
	private JButton B_taskRun;
	private int timeToReset=0;
	
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
	public ViewTimed(final ViewVar var) {
		this.var=var;
		super.var=var;
		super.incompleteID=incompleteID;
		super.completeID=completeID;
		super.incomplete=incomplete;
		super.complete=complete;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblIncomplete = new JLabel("Incomplete");
		GridBagConstraints gbc_lblIncomplete = new GridBagConstraints();
		gbc_lblIncomplete.insets = new Insets(0, 0, 5, 5);
		gbc_lblIncomplete.gridx = 0;
		gbc_lblIncomplete.gridy = 0;
		add(lblIncomplete, gbc_lblIncomplete);
		
		JLabel lblComplete = new JLabel("Complete");
		GridBagConstraints gbc_lblComplete = new GridBagConstraints();
		gbc_lblComplete.insets = new Insets(0, 0, 5, 0);
		gbc_lblComplete.gridx = 3;
		gbc_lblComplete.gridy = 0;
		add(lblComplete, gbc_lblComplete);
		
		JScrollPane SP_incomplete = new JScrollPane();
		SP_incomplete.setPreferredSize(new Dimension(0, 0));
		GridBagConstraints gbc_SP_incomplete = new GridBagConstraints();
		gbc_SP_incomplete.weightx = 5.0;
		gbc_SP_incomplete.fill = GridBagConstraints.BOTH;
		gbc_SP_incomplete.gridheight = 7;
		gbc_SP_incomplete.insets = new Insets(0, 0, 5, 5);
		gbc_SP_incomplete.gridx = 0;
		gbc_SP_incomplete.gridy = 1;
		add(SP_incomplete, gbc_SP_incomplete);
		
		L_incomplete = new JList<String>();
		SP_incomplete.setViewportView(L_incomplete);
		
		JLabel lblTimeTillReset = new JLabel("Time till reset");
		GridBagConstraints gbc_lblTimeTillReset = new GridBagConstraints();
		gbc_lblTimeTillReset.gridwidth = 2;
		gbc_lblTimeTillReset.insets = new Insets(0, 0, 5, 5);
		gbc_lblTimeTillReset.gridx = 1;
		gbc_lblTimeTillReset.gridy = 1;
		add(lblTimeTillReset, gbc_lblTimeTillReset);
		
		JScrollPane SP_complete = new JScrollPane();
		SP_complete.setPreferredSize(new Dimension(0, 0));
		GridBagConstraints gbc_SP_complete = new GridBagConstraints();
		gbc_SP_complete.weightx = 5.0;
		gbc_SP_complete.fill = GridBagConstraints.BOTH;
		gbc_SP_complete.gridheight = 7;
		gbc_SP_complete.insets = new Insets(0, 0, 5, 0);
		gbc_SP_complete.gridx = 3;
		gbc_SP_complete.gridy = 1;
		add(SP_complete, gbc_SP_complete);
		
		L_complete = new JList<String>();
		SP_complete.setViewportView(L_complete);
		
		TF_tReset = new JTextField();
		TF_tReset.setEditable(false);
		GridBagConstraints gbc_TF_tReset = new GridBagConstraints();
		gbc_TF_tReset.gridwidth = 2;
		gbc_TF_tReset.insets = new Insets(0, 0, 5, 5);
		gbc_TF_tReset.fill = GridBagConstraints.HORIZONTAL;
		gbc_TF_tReset.gridx = 1;
		gbc_TF_tReset.gridy = 2;
		add(TF_tReset, gbc_TF_tReset);
		TF_tReset.setColumns(10);
		
		B_edit = new JButton("Edit");
		GridBagConstraints gbc_B_edit = new GridBagConstraints();
		gbc_B_edit.weightx = 1.0;
		gbc_B_edit.insets = new Insets(0, 0, 5, 5);
		gbc_B_edit.gridx = 1;
		gbc_B_edit.gridy = 3;
		add(B_edit, gbc_B_edit);
		
		B_delete = new JButton("Delete");
		GridBagConstraints gbc_B_delete = new GridBagConstraints();
		gbc_B_delete.weightx = 1.0;
		gbc_B_delete.insets = new Insets(0, 0, 5, 5);
		gbc_B_delete.gridx = 2;
		gbc_B_delete.gridy = 3;
		add(B_delete, gbc_B_delete);
		
		B_toggle = new JButton("complete>>");
		GridBagConstraints gbc_B_toggle = new GridBagConstraints();
		gbc_B_toggle.gridwidth = 2;
		gbc_B_toggle.insets = new Insets(0, 0, 5, 5);
		gbc_B_toggle.gridx = 1;
		gbc_B_toggle.gridy = 4;
		add(B_toggle, gbc_B_toggle);
		
		B_update = new JButton("Update");
		GridBagConstraints gbc_B_update = new GridBagConstraints();
		gbc_B_update.weightx = 1.0;
		gbc_B_update.insets = new Insets(0, 0, 5, 5);
		gbc_B_update.gridx = 1;
		gbc_B_update.gridy = 5;
		add(B_update, gbc_B_update);
		
		B_taskRun = new JButton("Run Task");
		GridBagConstraints gbc_B_taskRun = new GridBagConstraints();
		gbc_B_taskRun.weightx = 1.0;
		gbc_B_taskRun.insets = new Insets(0, 0, 5, 5);
		gbc_B_taskRun.gridx = 2;
		gbc_B_taskRun.gridy = 5;
		add(B_taskRun, gbc_B_taskRun);
		
		JLabel lblPriority = new JLabel("Priority");
		GridBagConstraints gbc_lblPriority = new GridBagConstraints();
		gbc_lblPriority.anchor = GridBagConstraints.EAST;
		gbc_lblPriority.insets = new Insets(0, 0, 5, 5);
		gbc_lblPriority.gridx = 1;
		gbc_lblPriority.gridy = 6;
		add(lblPriority, gbc_lblPriority);
		
		TF_priority = new JTextField();
		TF_priority.setEditable(false);
		GridBagConstraints gbc_TF_priority = new GridBagConstraints();
		gbc_TF_priority.insets = new Insets(0, 0, 5, 5);
		gbc_TF_priority.fill = GridBagConstraints.HORIZONTAL;
		gbc_TF_priority.gridx = 2;
		gbc_TF_priority.gridy = 6;
		add(TF_priority, gbc_TF_priority);
		TF_priority.setColumns(10);
		
		JLabel lblDescription = new JLabel("Description");
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.gridwidth = 2;
		gbc_lblDescription.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescription.gridx = 1;
		gbc_lblDescription.gridy = 7;
		add(lblDescription, gbc_lblDescription);
		
		JScrollPane SP_description = new JScrollPane();
		GridBagConstraints gbc_SP_description = new GridBagConstraints();
		gbc_SP_description.weighty = 3.0;
		gbc_SP_description.fill = GridBagConstraints.BOTH;
		gbc_SP_description.gridwidth = 4;
		gbc_SP_description.insets = new Insets(0, 0, 0, 5);
		gbc_SP_description.gridx = 0;
		gbc_SP_description.gridy = 8;
		add(SP_description, gbc_SP_description);
		
		TA_description = new JTextArea();
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
					edit.setSelected(2,completeID.get(L_complete.getSelectedIndex()));
				}
				else if(L_incomplete.getSelectedIndex()>-1){
					edit.setSelected(2,incompleteID.get(L_incomplete.getSelectedIndex()));
				}
				else{
					edit.setSelected(2, 0);
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
		super.setFilter(ItemType.TimedItem);
		super.setListeners();
		Thread t=new Thread(){
			public void run(){
				countDown();
			}
		};
		t.setDaemon(true);
		t.start();
		

	}
	public void reset(){
		super.reset();
		TF_priority.setText("0");
		TF_tReset.setText("0");
		timeToReset=0;
	}
	public void setValues(boolean complete){
		super.setValues(complete);
		if(complete){
			if(L_complete.getSelectedIndex()>-1){
				TimedItem timed=var.getTimedItem(completeID.get(L_complete.getSelectedIndex()));
				TF_priority.setText(""+timed.getPriority());
				Date tComplete=new Date(timed.getStartDate().getTime()+timed.getTimeElapse());
				Date current=new Date();
				if(tComplete.after(current)){//if time hasn't been elapsed yet
					synchronized(this){
						timeToReset=(int) ((tComplete.getTime()-current.getTime())/1000);
						
					}
				}
			}
		}
		else{
			if(L_incomplete.getSelectedIndex()>-1){
				TimedItem timed=var.getTimedItem(incompleteID.get(L_incomplete.getSelectedIndex()));
				TF_priority.setText(""+timed.getPriority());
				synchronized(this){
					timeToReset=0;
				}
				TF_tReset.setText("0");
			}
		}
	}
	public void countDown(){
		while(true){
			synchronized(this){
				if(timeToReset>0){
					timeToReset--;//time till next reset
				}
			}
			//set time display for time till reset
			java.awt.EventQueue.invokeLater(new Runnable() {
			    public void run() {
			    	if(timeToReset>0){
			    	//convert to DD/HH/MM/SS timing
			    	String s=timeToReset%60+"";
			    	String m=timeToReset/60%60+"";
			    	String h=timeToReset/3600%24+"";
			    	String d=timeToReset/86400+"";
			    	//add 0s to make it 00 etc.
			    	if(s.length()==1){
			    		s="0"+s;
			    	}
			    	if(m.length()==1){
			    		m="0"+m;
			    	}
			    	if(h.length()==1){
			    		h="0"+h;
			    	}
			    	if(d.length()==1){
			    		d="0"+d;
			    	}
			    	String out=d+":"+h+":"+m+":"+s;
			    	TF_tReset.setText(out);
			    	}
			    	else{
			    		TF_tReset.setText("0");
			    	}
			    }
			} );
			try {
				synchronized(this){
					this.wait(1000);
				}
			} catch (InterruptedException e) {
				break;
			}
		}
	}
	public JPanel getP_Timed(){
		return this;
	}

}
