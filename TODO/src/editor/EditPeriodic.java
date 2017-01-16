package editor;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import global.Console;
import global.Task;
import global.items.PeriodicItem;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JSpinner;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.FlowLayout;
import java.awt.Dimension;

/**
 * for periodic items, stuff that occur in a periodic fashion, resets every x period
 * @author Allen
 *
 */
public class EditPeriodic extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4866351213339964500L;
	//main global variables var, for all the variables, con for console/log
	private EditVar var;
	private Console con;
	private Vector<String> periodics=new Vector<String>();
	private JTextField TF_name;
	
	private boolean mListPress=false;//used to drag and drop items around(on list), holds state of mouse press
	private boolean kControl=false;//drag and drop, swap items around whether control is pressed
	//swing elements
	private JList<String> L_periodic;
	private JComboBox<String> CB_priority;
	private JTextArea TA_description;
	private JSpinner Sp_refDate;
	private JButton B_task;
	private JButton B_new;
	private JButton B_delete;
	private JSpinner Sp_d;
	private JSpinner Sp_h;
	private JSpinner Sp_m;
	private JSpinner Sp_s;

	/**
	 * Create the panel.
	 */
	public EditPeriodic(final EditVar var,final Console con) {
		this.var=var;
		this.con=con;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblParameters = new JLabel("Parameters");
		GridBagConstraints gbc_lblParameters = new GridBagConstraints();
		gbc_lblParameters.gridwidth = 2;
		gbc_lblParameters.insets = new Insets(0, 0, 5, 5);
		gbc_lblParameters.gridx = 0;
		gbc_lblParameters.gridy = 0;
		add(lblParameters, gbc_lblParameters);
		
		JLabel lblPeriodicTaskList = new JLabel("Periodic Task List");
		GridBagConstraints gbc_lblPeriodicTaskList = new GridBagConstraints();
		gbc_lblPeriodicTaskList.gridwidth = 2;
		gbc_lblPeriodicTaskList.insets = new Insets(0, 0, 5, 0);
		gbc_lblPeriodicTaskList.gridx = 2;
		gbc_lblPeriodicTaskList.gridy = 0;
		add(lblPeriodicTaskList, gbc_lblPeriodicTaskList);
		
		JLabel lblName = new JLabel("Name");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 1;
		add(lblName, gbc_lblName);
		
		TF_name = new JTextField();
		GridBagConstraints gbc_TF_name = new GridBagConstraints();
		gbc_TF_name.insets = new Insets(0, 0, 5, 5);
		gbc_TF_name.fill = GridBagConstraints.HORIZONTAL;
		gbc_TF_name.gridx = 1;
		gbc_TF_name.gridy = 1;
		add(TF_name, gbc_TF_name);
		TF_name.setColumns(10);
		
		JScrollPane SP_periodic = new JScrollPane();
		SP_periodic.setPreferredSize(new Dimension(0, 0));
		GridBagConstraints gbc_SP_periodic = new GridBagConstraints();
		gbc_SP_periodic.fill = GridBagConstraints.BOTH;
		gbc_SP_periodic.gridheight = 6;
		gbc_SP_periodic.gridwidth = 2;
		gbc_SP_periodic.insets = new Insets(0, 0, 5, 0);
		gbc_SP_periodic.gridx = 2;
		gbc_SP_periodic.gridy = 1;
		add(SP_periodic, gbc_SP_periodic);
		//set up list
		L_periodic = new JList<String>();
		L_periodic.setModel(new AbstractListModel<String>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 8817049873049988L;
			public int getSize() {
				return periodics.size();
			}
			public String getElementAt(int index) {
				return periodics.get(index);
			}
		});
		SP_periodic.setViewportView(L_periodic);
		
		JLabel lblPriority = new JLabel("Priority");
		GridBagConstraints gbc_lblPriority = new GridBagConstraints();
		gbc_lblPriority.insets = new Insets(0, 0, 5, 5);
		gbc_lblPriority.gridx = 0;
		gbc_lblPriority.gridy = 2;
		add(lblPriority, gbc_lblPriority);
		
		CB_priority = new JComboBox<String>();
		GridBagConstraints gbc_CB_priority = new GridBagConstraints();
		gbc_CB_priority.insets = new Insets(0, 0, 5, 5);
		gbc_CB_priority.fill = GridBagConstraints.HORIZONTAL;
		gbc_CB_priority.gridx = 1;
		gbc_CB_priority.gridy = 2;
		add(CB_priority, gbc_CB_priority);
		CB_priority.setModel(new DefaultComboBoxModel<String>(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		
		JLabel lblDescription = new JLabel("Description");
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.gridwidth = 2;
		gbc_lblDescription.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescription.gridx = 0;
		gbc_lblDescription.gridy = 3;
		add(lblDescription, gbc_lblDescription);
		
		JScrollPane SP_description = new JScrollPane();
		SP_description.setPreferredSize(new Dimension(0, 0));
		GridBagConstraints gbc_SP_description = new GridBagConstraints();
		gbc_SP_description.fill = GridBagConstraints.BOTH;
		gbc_SP_description.gridwidth = 2;
		gbc_SP_description.insets = new Insets(0, 0, 5, 5);
		gbc_SP_description.gridx = 0;
		gbc_SP_description.gridy = 4;
		add(SP_description, gbc_SP_description);
		
		TA_description = new JTextArea();
		SP_description.setViewportView(TA_description);
		
		JLabel lblRefDate = new JLabel("Ref Date");
		GridBagConstraints gbc_lblRefDate = new GridBagConstraints();
		gbc_lblRefDate.insets = new Insets(0, 0, 5, 5);
		gbc_lblRefDate.gridx = 0;
		gbc_lblRefDate.gridy = 5;
		add(lblRefDate, gbc_lblRefDate);
		
		Sp_refDate = new JSpinner();
		GridBagConstraints gbc_Sp_refDate = new GridBagConstraints();
		gbc_Sp_refDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_Sp_refDate.insets = new Insets(0, 0, 5, 5);
		gbc_Sp_refDate.gridx = 1;
		gbc_Sp_refDate.gridy = 5;
		add(Sp_refDate, gbc_Sp_refDate);
		
		JLabel lblPeriodicPeriod = new JLabel("Period");
		GridBagConstraints gbc_lblPeriodicPeriod = new GridBagConstraints();
		gbc_lblPeriodicPeriod.anchor = GridBagConstraints.EAST;
		gbc_lblPeriodicPeriod.insets = new Insets(0, 0, 5, 5);
		gbc_lblPeriodicPeriod.gridx = 0;
		gbc_lblPeriodicPeriod.gridy = 6;
		add(lblPeriodicPeriod, gbc_lblPeriodicPeriod);
		
		JPanel P_period = new JPanel();
		GridBagConstraints gbc_P_period = new GridBagConstraints();
		gbc_P_period.insets = new Insets(0, 0, 5, 5);
		gbc_P_period.fill = GridBagConstraints.BOTH;
		gbc_P_period.gridx = 1;
		gbc_P_period.gridy = 6;
		add(P_period, gbc_P_period);
		P_period.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Sp_d = new JSpinner();
		Sp_d.setPreferredSize(new Dimension(52, 28));
		P_period.add(Sp_d);
		
		Sp_h = new JSpinner();
		Sp_h.setPreferredSize(new Dimension(52, 28));
		P_period.add(Sp_h);
		
		Sp_m = new JSpinner();
		Sp_m.setPreferredSize(new Dimension(52, 28));
		P_period.add(Sp_m);
		
		Sp_s = new JSpinner();
		Sp_s.setPreferredSize(new Dimension(52, 28));
		P_period.add(Sp_s);
		
		B_task = new JButton("Set Task");
		GridBagConstraints gbc_B_task = new GridBagConstraints();
		gbc_B_task.insets = new Insets(0, 0, 0, 5);
		gbc_B_task.gridx = 1;
		gbc_B_task.gridy = 7;
		add(B_task, gbc_B_task);
		
		B_new = new JButton("New");
		GridBagConstraints gbc_B_new = new GridBagConstraints();
		gbc_B_new.insets = new Insets(0, 0, 0, 5);
		gbc_B_new.gridx = 2;
		gbc_B_new.gridy = 7;
		add(B_new, gbc_B_new);
		
		B_delete = new JButton("Delete");
		GridBagConstraints gbc_B_delete = new GridBagConstraints();
		gbc_B_delete.gridx = 3;
		gbc_B_delete.gridy = 7;
		add(B_delete, gbc_B_delete);
		//various listeners that will change the value of variables in edit var whenever something is changed
		Sp_d.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int index=L_periodic.getSelectedIndex();
				if(index>-1){
					if((int)Sp_d.getValue()<0){
						Sp_d.setValue(0);
					}
					setPeriod();
				}
			}
		});
		Sp_h.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int index=L_periodic.getSelectedIndex();
				if(index>-1){
					if((int)Sp_h.getValue()<0){
						Sp_h.setValue(0);
					}
					else if((int)Sp_h.getValue()>24){
						Sp_h.setValue(24);
					}
					setPeriod();
				}
			}
		});
		Sp_m.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int index=L_periodic.getSelectedIndex();
				if(index>-1){
					if((int)Sp_m.getValue()<0){
						Sp_m.setValue(0);
					}
					else if((int)Sp_m.getValue()>60){
						Sp_m.setValue(60);
					}
					setPeriod();
				}
			}
		});
		Sp_s.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int index=L_periodic.getSelectedIndex();
				if(index>-1){
					if((int)Sp_s.getValue()<0){
						Sp_s.setValue(0);
					}
					else if((int)Sp_m.getValue()>60){
						Sp_s.setValue(60);
					}
					setPeriod();
				}
			}
		});
		Sp_refDate.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int index=L_periodic.getSelectedIndex();
				if(index>-1){
					var.getPeriodic().get(index).setDateRef(new Date((long)Sp_refDate.getValue()));
				}
			}
		});
		
		
		TF_name.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				int index=L_periodic.getSelectedIndex();
				if(index>-1){
					String old=periodics.get(index);
					TF_name.setText(checkExisting(TF_name.getText(),index));
					var.getPeriodic().get(index).setName(TF_name.getText());
					periodics.set(index, TF_name.getText());
					con.addMsg("[Periodic]["+periodics.get(index)+"] name changed from "+old);
					L_periodic.updateUI();
				}
			}
		});
		TF_name.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index=L_periodic.getSelectedIndex();
				if(index>-1){
					String old=periodics.get(index);
					TF_name.setText(checkExisting(TF_name.getText(),index));
					var.getPeriodic().get(index).setName(TF_name.getText());
					periodics.set(index, TF_name.getText());
					con.addMsg("[Periodic]["+periodics.get(index)+"] name changed from"+old);
					L_periodic.updateUI();
				}
			}
		});
		TA_description.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				int index=L_periodic.getSelectedIndex();
				if(index>-1){
					var.getPeriodic().get(index).setDescription(TA_description.getText());
					con.addMsg("[Periodic]["+periodics.get(index)+"] description changed to "+TA_description.getText());
				}
			}
		});
		//used for drag and drop of list
		L_periodic.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				L_periodic.grabFocus();
			}
			@Override
			public void mousePressed(MouseEvent e) {
				mListPress=true;
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				mListPress=false;
				if(e.isControlDown()){
					kControl=true;
				}
				else{
					kControl=false;
				}
			}
		});
		CB_priority.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index=L_periodic.getSelectedIndex();
				if(index>-1){
					if(CB_priority.getSelectedIndex()>-1){
						var.getPeriodic().get(index).setPriority(Integer.parseInt(""+CB_priority.getSelectedItem()));
					}
				}
			}
		});
		L_periodic.addListSelectionListener(new ListSelectionListener() {
			int index=-1;
			public void valueChanged(ListSelectionEvent e) {
				if(L_periodic.getSelectedIndex()>-1){
					if(mListPress&&index==-1){
						index=L_periodic.getSelectedIndex();
					}
					if(!mListPress){
						if(L_periodic.getSelectedIndex()!=index&&index>-1&&kControl){
							swapItems(index,L_periodic.getSelectedIndex());
						}
						else if(L_periodic.getSelectedIndex()!=index&&index>-1&&!kControl){
							moveItems(index,L_periodic.getSelectedIndex());
							}
						index=-1;
					}
					setValues();
				}
			}
		});
		//create new item
		B_new.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PeriodicItem daily=new PeriodicItem(checkExisting(),var);
				periodics.add(daily.getName());
				var.getPeriodic().add(daily);
				L_periodic.setSelectedIndex(periodics.size()-1);
				L_periodic.updateUI();
				setValues();
				con.addMsg("[Periodic]["+periodics.get(L_periodic.getSelectedIndex())+"] item created");
			}
		});
		B_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int response=JOptionPane.showConfirmDialog(B_delete, "Confirm delete?");
				if(response==JOptionPane.OK_OPTION){

					int index=L_periodic.getSelectedIndex();
					if(index>-1){
						con.addMsg("[Daily]["+periodics.get(index)+"] removed");
						var.getPeriodic().remove(index);
						periodics.remove(index);
						L_periodic.setSelectedIndex(index-1);
						setValues();
						L_periodic.updateUI();
						L_periodic.clearSelection();

					}
				}
			}
		});
		B_task.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(L_periodic.getSelectedIndex()>-1){
					PeriodicItem periodic=var.getPeriodic().get(L_periodic.getSelectedIndex());
					EditTask eTask=new EditTask(var.getEdit(),periodic.getTask(),periodic.getMonitor(),periodic.isHide());
					eTask.setVisible(true);
					Task task=eTask.getTask();
					if(!(task==null)){
						periodic.setTask(task);
						boolean monitor=eTask.getMonitored();
						periodic.setMonitor(monitor);
						boolean hide=eTask.getHide();
						periodic.setHide(hide);
					}
				}
			}
		});
	}
	//sets the period in which completion will reset, various calculations to convert days, hours, minutes, seconds to miliseconds
	public void setPeriod(){
		long period=(long)((86400*(int)Sp_d.getValue())+(3600*(int)Sp_h.getValue())+(60*(int)Sp_m.getValue())+(int)Sp_s.getValue())*1000;
		var.getPeriodic().get(L_periodic.getSelectedIndex()).setTimePeriod(period);
	}
	//extract numbers from string, accepts decimals
	public String keepNum(String number){
		char[] numbers=number.toCharArray();
		String text="";
		for(char c:numbers){
			if(Character.isDigit(c)||c=='.'){
				text+=c;
			}
		}
		return text;
	}
	//checks if the default name for component(component type+number) is used, if not it will save as that otherwise it will continue searching
		public String checkExisting(){
			boolean noMatch=false;//boolean to check to make sure that there are no components that already have the same default names
			int num = 0;//number added to end of default names to differentiate
			while(!noMatch){//continues if a match has been found with the current name
				num++;
				noMatch=true;
				for(int i=0;i<periodics.size();i++){//checks if current numbering of default name exists
					if(periodics.get(i).equals("periodicItem"+num)){
						noMatch=false;//set noMatch at false continuing search, 
					}
				}
			}
			return "periodicItem"+num;
		}
		//similar to first one but has index to avoid checking against itself
		public String checkExisting(String name, int currentIndex){
			boolean noMatch=false;//boolean to check to make sure that there are no components that already have the same default names
			int num = 0;//number added to end of default names to differentiate
			while(!noMatch){//continues if a match has been found with the current name
				noMatch=true;
				num++;
				for(int i=0;i<periodics.size();i++){//checks if current numbering of default name exists
					if(i!=currentIndex){//to make sure that it isn't checking against itself
						if(periodics.get(i).equals(name)&&num==1){//first case if name is present
							System.out.println("firstCase");
							noMatch=false;
						}
						if(periodics.get(i).equals(name+num)&&num>1){//secound case if numbered name(from other checks) is present
							System.out.println("secound case");
							noMatch=false;//set noMatch at false continuing search,

						}
					}
				}
			}
			if(num>1){
				return ""+name+num;
			}
			else{
				return name;
			}
		}
		//drag and dropping of items around in list
		public void swapItems(int item, int insert){
			if(item>-1&&insert>-1){
				PeriodicItem swap=var.getPeriodic().get(item);
				PeriodicItem replace=var.getPeriodic().get(insert);
				var.getPeriodic().set(item, replace);
				var.getPeriodic().set(insert, swap);
				reset();
			}
		}
		//drag and dropping of items around in list
		public void moveItems(int item,int insert){
			if(item>-1&&insert>-1){
				PeriodicItem swap=var.getPeriodic().get(item);
				var.getPeriodic().remove(item);
				var.getPeriodic().add(insert, swap);
				reset();
			}
		}
		//sets the values in the swing components, called everytime the selected item changes or something else changes it etc
		public void setValues(){
			int index=L_periodic.getSelectedIndex();
			if(index>-1){
				PeriodicItem selected=var.getPeriodic().get(index);
				TF_name.setText(selected.getName());
				TA_description.setText(selected.getDescription());
				CB_priority.setSelectedIndex(selected.getPriority()-1);
				long time=var.getPeriodic().get(L_periodic.getSelectedIndex()).getTimePeriod()/1000;
				int s=(int) (time%60);
		    	int m=(int) (time/60%60);
		    	int h=(int) (time/3600%24);
		    	int d=(int) (time/86400);
		    	Sp_s.setValue(s);
		    	Sp_m.setValue(m);
		    	Sp_h.setValue(h);
		    	Sp_d.setValue(d);
			}
		}
		//resets things, loads things
		public void reset(){
			periodics.clear();
			L_periodic.clearSelection();
			Sp_s.setValue(0);
			Sp_m.setValue(0);
			Sp_h.setValue(0);
			Sp_d.setValue(0);
			for(int i=0;i<var.getPeriodic().size();i++){
				periodics.add(var.getPeriodic().get(i).getName());
			}
			TF_name.setText("");
			TA_description.setText("");
			CB_priority.setSelectedIndex(-1);
			L_periodic.updateUI();
			con.addMsg("[Daily] data reset/refreshed");
		}
		//used to get eclipse to render panel properly
		public JPanel getP_periodic(){
			return this;
		}
		//used to switch between the different parts so that it'll jump to selected item in viewer
		public void setItemIndex(long ID) {
			for(int i=0;i<var.getPeriodic().size();i++){
				if(var.getPeriodic().get(i).getID()==ID){
					L_periodic.setSelectedIndex(i);
				}
			}
		}

}
