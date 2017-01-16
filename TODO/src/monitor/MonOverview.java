package monitor;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import javax.swing.JList;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JTextField;

import global.MonitorItem;

import javax.swing.JScrollPane;
import javax.swing.AbstractListModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
/**
 * panel displaying overview of all items monitored
 * @author Allen
 *
 */
public class MonOverview extends JPanel implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4922953733351817236L;
	private JTextField TF_time;//to show time till next run
	//arrays to hold lists of items that are either complete or incomplete
	private ArrayList<String> incomplete=new ArrayList<String>();
	private ArrayList<String> complete=new ArrayList<String>();
	//maps the id to the index in the var arrays
	private ArrayList<Long> incompleteID=new ArrayList<Long>();
	private ArrayList<Long> completeID=new ArrayList<Long>();
	//swing elements
	private MonVar var;
	private JButton B_update;
	private JList<String> L_incomplete;
	private int time=0;
	private JList<String> L_complete;
	private JButton B_pause;
	/**
	 * Create the panel.
	 */
	public MonOverview(final MonVar var) {
		this.var=var;
		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		
		JLabel lblPlannedTasks = new JLabel("Planned Tasks");
		GridBagConstraints gbc_lblPlannedTasks = new GridBagConstraints();
		gbc_lblPlannedTasks.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlannedTasks.gridx = 0;
		gbc_lblPlannedTasks.gridy = 0;
		add(lblPlannedTasks, gbc_lblPlannedTasks);
		
		JLabel lblCompletedTasks = new JLabel("Completed Tasks");
		GridBagConstraints gbc_lblCompletedTasks = new GridBagConstraints();
		gbc_lblCompletedTasks.insets = new Insets(0, 0, 5, 0);
		gbc_lblCompletedTasks.gridx = 3;
		gbc_lblCompletedTasks.gridy = 0;
		add(lblCompletedTasks, gbc_lblCompletedTasks);
		
		JScrollPane SP_incomplete = new JScrollPane();
		GridBagConstraints gbc_SP_incomplete = new GridBagConstraints();
		gbc_SP_incomplete.weighty = 1.0;
		gbc_SP_incomplete.weightx = 1.0;
		gbc_SP_incomplete.gridheight = 3;
		gbc_SP_incomplete.fill = GridBagConstraints.BOTH;
		gbc_SP_incomplete.insets = new Insets(5, 5, 0, 5);
		gbc_SP_incomplete.gridx = 0;
		gbc_SP_incomplete.gridy = 1;
		add(SP_incomplete, gbc_SP_incomplete);
		
		L_incomplete = new JList<String>();
		L_incomplete.setModel(new AbstractListModel<String>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -9114896899392341391L;
			public int getSize() {
				return incomplete.size();
			}
			public String getElementAt(int index) {
				return incomplete.get(index);
			}
		});
		SP_incomplete.setViewportView(L_incomplete);
		
		JScrollPane SP_complete = new JScrollPane();
		GridBagConstraints gbc_SP_complete = new GridBagConstraints();
		gbc_SP_complete.weighty = 1.0;
		gbc_SP_complete.weightx = 1.0;
		gbc_SP_complete.gridheight = 3;
		gbc_SP_complete.fill = GridBagConstraints.BOTH;
		gbc_SP_complete.gridx = 3;
		gbc_SP_complete.gridy = 1;
		add(SP_complete, gbc_SP_complete);
		
		L_complete = new JList<String>();
		L_complete.setModel(new AbstractListModel<String>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 7305339075874383373L;
			public int getSize() {
				return complete.size();
			}
			public String getElementAt(int index) {
				return complete.get(index);
			}
		});
		SP_complete.setViewportView(L_complete);
		
		B_pause = new JButton("Pause");
		GridBagConstraints gbc_B_pause = new GridBagConstraints();
		gbc_B_pause.gridwidth = 2;
		gbc_B_pause.insets = new Insets(0, 0, 5, 5);
		gbc_B_pause.gridx = 1;
		gbc_B_pause.gridy = 2;
		add(B_pause, gbc_B_pause);
		
		B_update = new JButton("Update");
		GridBagConstraints gbc_B_update = new GridBagConstraints();
		gbc_B_update.insets = new Insets(0, 0, 0, 5);
		gbc_B_update.gridwidth = 2;
		gbc_B_update.weightx = 1.0;
		gbc_B_update.gridx = 1;
		gbc_B_update.gridy = 3;
		add(B_update, gbc_B_update);
		
		JLabel lblTimeTillRun = new JLabel("Time till Run");
		GridBagConstraints gbc_lblTimeTillRun = new GridBagConstraints();
		gbc_lblTimeTillRun.weighty = 1.0;
		gbc_lblTimeTillRun.anchor = GridBagConstraints.EAST;
		gbc_lblTimeTillRun.insets = new Insets(0, 0, 5, 5);
		gbc_lblTimeTillRun.gridx = 1;
		gbc_lblTimeTillRun.gridy = 1;
		add(lblTimeTillRun, gbc_lblTimeTillRun);
		
		TF_time = new JTextField();
		TF_time.setEditable(false);
		GridBagConstraints gbc_TF_time = new GridBagConstraints();
		gbc_TF_time.insets = new Insets(0, 0, 5, 5);
		gbc_TF_time.fill = GridBagConstraints.HORIZONTAL;
		gbc_TF_time.gridx = 2;
		gbc_TF_time.gridy = 1;
		add(TF_time, gbc_TF_time);
		TF_time.setColumns(10);
		//pause the monitor and execution of tasks
		B_pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(B_pause.getText().equals("Pause")){
					var.setPause(true);
					B_pause.setText("Continue");
				}
				else{
					var.setPause(false);
					B_pause.setText("Pause");
				}
			}
		});
		//update from file
		B_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
			}
		});
		L_incomplete.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(L_incomplete.getSelectedIndex()>-1){
					L_complete.clearSelection();
					setValues(false);
				}
			}
		});
		L_complete.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(L_complete.getSelectedIndex()>-1){
					L_incomplete.clearSelection();
					setValues(true);
				}
			}
		});
		
		
		//countdowns time till next run
		Thread t=new Thread(){
			public void run(){
				countDown();
			}
		};
		t.setDaemon(true);
		t.start();
	}
	//sets the values of everything, called when selected item changes
	public void setValues(boolean complete){
		if(complete){
			if(L_complete.getSelectedIndex()>-1){
				MonitorItem item=var.getMonitoredCompleteItem(completeID.get(L_complete.getSelectedIndex()));
				long taskTime=item.getNextTaskDate().getTime();
				synchronized(this){
					time=(int)((taskTime-System.currentTimeMillis())/1000);
					setTimeDisplay();
				}
			}
		}
		else{
			if(L_incomplete.getSelectedIndex()>-1){
				MonitorItem item=var.getMonitoredIncompleteItem(incompleteID.get(L_incomplete.getSelectedIndex()));
				long taskTime;
				if(item.getNextTaskDate().before(item.getTask().getTimeoutFinish())){
					taskTime=item.getTask().getTimeoutFinish().getTime();
				}
				else{
					taskTime=item.getNextTaskDate().getTime();
				}
				synchronized(this){
					time=(int)((taskTime-System.currentTimeMillis())/1000);
					setTimeDisplay();
				}
			}
		}
	}
	public void run(){
		var.reload();
		reset();
	}
	public void update(){
		Thread t=new Thread(this);
		t.setDaemon(true);
		t.start();
	}
	public void countDown(){
		while(true){
			if(time>0){
				synchronized(this){
					time--;
				}
			}
			else if(time<0){
				time=0;
			}
			setTimeDisplay();
			try{
				Thread.sleep(1000);
			}catch(InterruptedException e){
				break;
			}
		}
	}
	public void setTimeDisplay(){
		synchronized(this){
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					String s=""+(time%60);
					String m=""+(time/60%60);
					String h=""+(time/3600%24);
					String d=""+(time/86400);
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
					TF_time.setText(out);
				}

			});
		}
	}
	//methods used to update the lists
	public void updateIncomplete(){
		incomplete.clear();
		incompleteID.clear();
		ArrayList<MonitorItem> incomplete=var.getMonitoredIncomplete();
		for(MonitorItem item:incomplete){
			setIncomplete(item);
		}
		EventQueue.invokeLater(new Runnable() {
			public void run(){
				L_incomplete.updateUI();
			}
		});
		
	}
	private void setIncomplete(MonitorItem item){
		for(int i=0;i<this.incomplete.size();i++){
			Long itemD=item.getNextTaskDate().getTime();
			Long selected=var.getMonitoredIncompleteItem(incompleteID.get(i)).getNextTaskDate().getTime();
			if(itemD>selected&&Math.abs(itemD-selected)<1000){
				this.incomplete.add(i,item.getName());
				incompleteID.add(i,item.getID());
				return;
			}
		}
		this.incomplete.add(item.getName());
		incompleteID.add(item.getID());
	}
	public void updateComplete(){
		complete.clear();
		completeID.clear();
		ArrayList<MonitorItem> complete=var.getMonitoredComplete();
		for(MonitorItem item:complete){
			setComplete(item);
		}
		EventQueue.invokeLater(new Runnable() {
			public void run(){
				L_complete.updateUI();
			}
		});
	}
	private void setComplete(MonitorItem item){
		for(int i=0;i<this.complete.size();i++){
			Long itemD=item.getNextTaskDate().getTime();
			Long selected=var.getMonitoredCompleteItem(completeID.get(i)).getNextTaskDate().getTime();
			if(itemD>selected&&Math.abs(itemD-selected)<1000){
				this.complete.add(i,item.getName());
				completeID.add(i,item.getID());
				return;
			}
		}
		this.complete.add(item.getName());
		completeID.add(item.getID());
		
	}
	//reset everything/reload
	public void reset(){
		updateIncomplete();
		updateComplete();
		synchronized(this){
			time=0;
		}
		setTimeDisplay();
	}
	public JPanel getP_overview(){
		return this;
	}

}
