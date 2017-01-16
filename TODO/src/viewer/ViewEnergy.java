package viewer;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JProgressBar;
import javax.swing.JButton;
import javax.swing.JTextField;

import editor.EditMain;
import global.ItemType;
import global.items.EnergyItem;

import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import javax.swing.JSpinner;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
 * view current energy items
 * @author Allen
 *
 */
public class ViewEnergy extends ViewGenerics{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1516687755164038934L;
	private JProgressBar PB_energy;
	private JTextField TF_priority;
	private JButton B_taskRun;
	private JScrollPane SP_description;
	private JTextArea TA_description;
	
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
	
	//variables used by counting thread
	private int max;//max energy
	private int current;//current energy
	private int tToRefill;//time until refill
	private int tRefill;//time to refill
	private JSpinner Sp_energy;
	private JButton B_setEnergy;

	/**
	 * Create the panel.
	 */
	public ViewEnergy(final ViewVar var) {
		this.var=var;
		super.var=var;
		super.incompleteID=incompleteID;
		super.completeID=completeID;
		super.incomplete=incomplete;
		super.complete=complete;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
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
		gbc_SP_incomplete.fill = GridBagConstraints.BOTH;
		gbc_SP_incomplete.gridheight = 8;
		gbc_SP_incomplete.insets = new Insets(0, 0, 5, 5);
		gbc_SP_incomplete.gridx = 0;
		gbc_SP_incomplete.gridy = 1;
		add(SP_incomplete, gbc_SP_incomplete);
		
		L_incomplete = new JList<String>();
		SP_incomplete.setViewportView(L_incomplete);
		
		JLabel lblEnergyProgress = new JLabel("Energy Progress");
		GridBagConstraints gbc_lblEnergyProgress = new GridBagConstraints();
		gbc_lblEnergyProgress.gridwidth = 2;
		gbc_lblEnergyProgress.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnergyProgress.gridx = 1;
		gbc_lblEnergyProgress.gridy = 1;
		add(lblEnergyProgress, gbc_lblEnergyProgress);
		
		JScrollPane SP_complete = new JScrollPane();
		SP_complete.setPreferredSize(new Dimension(0, 0));
		GridBagConstraints gbc_SP_complete = new GridBagConstraints();
		gbc_SP_complete.fill = GridBagConstraints.BOTH;
		gbc_SP_complete.gridheight = 8;
		gbc_SP_complete.insets = new Insets(0, 0, 5, 0);
		gbc_SP_complete.gridx = 3;
		gbc_SP_complete.gridy = 1;
		add(SP_complete, gbc_SP_complete);
		
		L_complete = new JList<String>();
		SP_complete.setViewportView(L_complete);
		
		PB_energy = new JProgressBar();
		PB_energy.setStringPainted(true);
		GridBagConstraints gbc_PB_energy = new GridBagConstraints();
		gbc_PB_energy.fill = GridBagConstraints.HORIZONTAL;
		gbc_PB_energy.gridwidth = 2;
		gbc_PB_energy.insets = new Insets(0, 0, 5, 5);
		gbc_PB_energy.gridx = 1;
		gbc_PB_energy.gridy = 2;
		add(PB_energy, gbc_PB_energy);
		
		Sp_energy = new JSpinner();
		GridBagConstraints gbc_Sp_energy = new GridBagConstraints();
		gbc_Sp_energy.fill = GridBagConstraints.HORIZONTAL;
		gbc_Sp_energy.insets = new Insets(0, 0, 5, 5);
		gbc_Sp_energy.gridx = 2;
		gbc_Sp_energy.gridy = 3;
		add(Sp_energy, gbc_Sp_energy);
		
		B_setEnergy = new JButton("Set Current Energy");
		GridBagConstraints gbc_B_setEnergy = new GridBagConstraints();
		gbc_B_setEnergy.insets = new Insets(0, 0, 5, 5);
		gbc_B_setEnergy.gridx = 1;
		gbc_B_setEnergy.gridy = 3;
		add(B_setEnergy, gbc_B_setEnergy);
		
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
		GridBagConstraints gbc_B_complete = new GridBagConstraints();
		gbc_B_complete.gridwidth = 2;
		gbc_B_complete.insets = new Insets(0, 0, 5, 5);
		gbc_B_complete.gridx = 1;
		gbc_B_complete.gridy = 5;
		add(B_toggle, gbc_B_complete);
		
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
		
		JLabel lblPriority = new JLabel("Priority");
		GridBagConstraints gbc_lblPriority = new GridBagConstraints();
		gbc_lblPriority.insets = new Insets(0, 0, 5, 5);
		gbc_lblPriority.anchor = GridBagConstraints.EAST;
		gbc_lblPriority.gridx = 1;
		gbc_lblPriority.gridy = 7;
		add(lblPriority, gbc_lblPriority);
		
		TF_priority = new JTextField();
		GridBagConstraints gbc_TF_priority = new GridBagConstraints();
		gbc_TF_priority.insets = new Insets(0, 0, 5, 5);
		gbc_TF_priority.fill = GridBagConstraints.HORIZONTAL;
		gbc_TF_priority.gridx = 2;
		gbc_TF_priority.gridy = 7;
		add(TF_priority, gbc_TF_priority);
		TF_priority.setColumns(10);
		
		JLabel lblDescription = new JLabel("Description");
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescription.gridx = 1;
		gbc_lblDescription.gridy = 8;
		add(lblDescription, gbc_lblDescription);
		
		SP_description = new JScrollPane();
		SP_description.setPreferredSize(new Dimension(0, 0));
		GridBagConstraints gbc_SP_description = new GridBagConstraints();
		gbc_SP_description.fill = GridBagConstraints.BOTH;
		gbc_SP_description.gridwidth = 4;
		gbc_SP_description.gridx = 0;
		gbc_SP_description.gridy = 9;
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
					edit.setSelected(3,completeID.get(L_complete.getSelectedIndex()));
				}
				else if(L_incomplete.getSelectedIndex()>-1){
					edit.setSelected(3,incompleteID.get(L_incomplete.getSelectedIndex()));
				}
				else{
					edit.setSelected(3, 0);
				}
				edit.getVar().setView(var.getView());
			}
		});
		B_taskRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(L_complete.getSelectedIndex()>-1){
					var.getEnergyItem(completeID.get(L_complete.getSelectedIndex())).callTask(null);
				}
				else if(L_incomplete.getSelectedIndex()>-1){
					var.getEnergyItem(incompleteID.get(L_incomplete.getSelectedIndex())).callTask(null);
				}
			}
		});
		Sp_energy.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(L_complete.getSelectedIndex()>-1){
					EnergyItem energy=var.getEnergyItem(completeID.get(L_complete.getSelectedIndex()));
					if((int)Sp_energy.getValue()>energy.getEnergyMax()){
						Sp_energy.setValue(energy.getEnergyMax());
					}
					else if((int)Sp_energy.getValue()<0){
						Sp_energy.setValue(0);
					}
				}
				else if(L_incomplete.getSelectedIndex()>-1){
					EnergyItem energy=var.getEnergyItem(incompleteID.get(L_incomplete.getSelectedIndex()));
					if((int)Sp_energy.getValue()>energy.getEnergyMax()){
						Sp_energy.setValue(energy.getEnergyMax());
					}
					else if((int)Sp_energy.getValue()<0){
						Sp_energy.setValue(0);
					}
				}
			}
		});
		B_setEnergy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(L_complete.getSelectedIndex()>-1){
					var.reload();
					synchronized(this){
						current=(int)Sp_energy.getValue();
						tToRefill=tRefill-1;
					}
					EnergyItem energy=var.getEnergyItem(completeID.get(L_complete.getSelectedIndex()));
					Date replenish=new Date(new Date().getTime()+(max-current-1)*(long)(energy.getEnergyURefill()*60000)+(tToRefill*1000));
					energy.setEnergyReplenish(replenish);
					if(!var.rewrite()){
						actionPerformed(e);
						
					}
				}
				else if(L_incomplete.getSelectedIndex()>-1){
					var.reload();
					synchronized(this){
						current=(int)Sp_energy.getValue();
						tToRefill=tRefill-1;
					}
					EnergyItem energy=var.getEnergyItem(incompleteID.get(L_incomplete.getSelectedIndex()));
					Date replenish=new Date(new Date().getTime()+(max-current-1)*(long)(energy.getEnergyURefill()*60000)+(tToRefill*1000));
					energy.setEnergyReplenish(replenish);
					update();
					if(!var.rewrite()){
						actionPerformed(e);
					}
				}
				
			}
		});
		super.L_incomplete=L_incomplete;
		super.L_complete=L_complete;
		super.B_delete=B_delete;
		super.B_update=B_update;
		super.B_toggle=B_toggle;
		super.TA_description=TA_description;
		super.setFilter(ItemType.EnergyItem);
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
		PB_energy.setValue(0);
		PB_energy.setMaximum(100);
		PB_energy.setString(null);
		synchronized(this){
		current=100;
		max=100;
		}
	}
	//used to set the values for the energy display
	public void setValues(boolean complete){
		super.setValues(complete);
		if(complete){
			if(L_complete.getSelectedIndex()>-1){
				EnergyItem energy=var.getEnergyItem(completeID.get(L_complete.getSelectedIndex()));
				TF_priority.setText(""+energy.getPriority());
				PB_energy.setMaximum(energy.getEnergyMax());
				synchronized(this){
				max=energy.getEnergyMax();//set max energy to max energy of item
				Date current=new Date();
				Date replenish=energy.getEnergyReplenish();
				long split=replenish.getTime()-current.getTime();//time to replenish
				this.current=max-(int)(split/(long)(energy.getEnergyURefill()*60000))-1;//current energy
				tToRefill=(int) ((split%(long)(energy.getEnergyURefill()*60000))/1000);//current time till refill of next energy unit
				tRefill=(int)(energy.getEnergyURefill()*60);//used to determine time for each unit of energy to refill
				PB_energy.setString("("+this.current+"/"+max+")");
				PB_energy.setValue(this.current);
				}
			}
		}
		else{
			if(L_incomplete.getSelectedIndex()>-1){
				EnergyItem energy=var.getEnergyItem(incompleteID.get(L_incomplete.getSelectedIndex()));
				TF_priority.setText(""+energy.getPriority());
				synchronized(this){
				max=energy.getEnergyMax();
				current=max;
				tToRefill=0;
				PB_energy.setMaximum(max);
				PB_energy.setString("("+max+"/"+max+")0:00");
				}
			}
		}
	}
	//used to count down energy timer
	public void countDown(){
		while (true){
			synchronized(this){
				if(current<max){//if there's still energy to be filled
					if(tToRefill>0){
						tToRefill--;//decrement time till
					}
					else{//if no time remaining, add unit of energy, reset
						current++;
						tToRefill=tRefill-1;
					}
					//set time display
					java.awt.EventQueue.invokeLater(new Runnable() {
						public void run() {
							PB_energy.setValue(current);
							String s=tToRefill%60+"";
							if(s.length()==1){
								s="0"+s;
							}
							String time=tToRefill/60+":"+s;
							PB_energy.setString("("+current+"/"+max+")"+time);
						}
					});
				}
				else{
					//set time display for when energy is filled
					java.awt.EventQueue.invokeLater(new Runnable() {
						public void run() {
							PB_energy.setValue(max);
							PB_energy.setString("("+max+"/"+max+")0:00");
						}
					});
				}

				try {
					this.wait(1000);
				} catch (InterruptedException e) {
					break;
				}
			}
		}
	}
	public JPanel getP_Energy(){
		return this;
	}
}
