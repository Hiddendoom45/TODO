package editor;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Insets;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import global.Console;
import global.Task;
import global.items.DailyItem;

import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.AbstractListModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * Panel to edit task that are to be executed on a daily basis, reseting at a specific time
 * @author Allen
 *
 */
public class EditDaily extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -162234014253583183L;
	//globalish variables, var is used to hold most main variables, con is console/log
	private EditVar var;
	private Console con;
	private Vector<String> dailies=new Vector<String>();
	
	
	//component variables
	private JTextField TF_name;
	private JList<String> L_daily;
	private JSpinner CB_H;
	private JSpinner CB_M;
	private JTextArea TA_description;
	private JComboBox<String> CB_priority;
	
	private boolean mListPress=false;//used to drag and drop items around, holds state of mouse press
	private boolean kControl=false;//same, changes to swap items around instead of insert at point
	

	/**
	 * Create the panel.
	 */
	public EditDaily(final EditVar var,final Console con) {
		this.var=var;
		this.con=con;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0};
		setLayout(gridBagLayout);

		JLabel lblDailyTaskList = new JLabel("Daily Task List");
		GridBagConstraints gbc_lblDailyTaskList = new GridBagConstraints();
		gbc_lblDailyTaskList.gridwidth = 2;
		gbc_lblDailyTaskList.insets = new Insets(0, 0, 5, 0);
		gbc_lblDailyTaskList.gridx = 3;
		gbc_lblDailyTaskList.gridy = 0;
		add(lblDailyTaskList, gbc_lblDailyTaskList);
		
		JLabel lblHour = new JLabel("Hour");
		GridBagConstraints gbc_lblHour = new GridBagConstraints();
		gbc_lblHour.insets = new Insets(0, 0, 5, 5);
		gbc_lblHour.gridx = 1;
		gbc_lblHour.gridy = 1;
		add(lblHour, gbc_lblHour);
		
		JLabel lblMinute = new JLabel("Minute");
		GridBagConstraints gbc_lblMinute = new GridBagConstraints();
		gbc_lblMinute.insets = new Insets(0, 0, 5, 5);
		gbc_lblMinute.gridx = 2;
		gbc_lblMinute.gridy = 1;
		add(lblMinute, gbc_lblMinute);
		
		JLabel lblResetTime = new JLabel("Reset Time");
		GridBagConstraints gbc_lblResetTime = new GridBagConstraints();
		gbc_lblResetTime.anchor = GridBagConstraints.EAST;
		gbc_lblResetTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblResetTime.gridx = 0;
		gbc_lblResetTime.gridy = 2;
		add(lblResetTime, gbc_lblResetTime);
		
		CB_H = new JSpinner();
		GridBagConstraints gbc_CB_H = new GridBagConstraints();
		gbc_CB_H.insets = new Insets(0, 0, 5, 5);
		gbc_CB_H.fill = GridBagConstraints.HORIZONTAL;
		gbc_CB_H.gridx = 1;
		gbc_CB_H.gridy = 2;
		add(CB_H, gbc_CB_H);
		
		CB_M = new JSpinner();
		GridBagConstraints gbc_CB_M = new GridBagConstraints();
		gbc_CB_M.insets = new Insets(0, 0, 5, 5);
		gbc_CB_M.fill = GridBagConstraints.HORIZONTAL;
		gbc_CB_M.gridx = 2;
		gbc_CB_M.gridy = 2;
		add(CB_M, gbc_CB_M);
		
		JScrollPane SP_dailies = new JScrollPane();
		SP_dailies.setPreferredSize(new Dimension(0, 0));
		GridBagConstraints gbc_SP_dailies = new GridBagConstraints();
		gbc_SP_dailies.gridwidth = 2;
		gbc_SP_dailies.fill = GridBagConstraints.BOTH;
		gbc_SP_dailies.gridheight = 4;
		gbc_SP_dailies.insets = new Insets(0, 0, 5, 0);
		gbc_SP_dailies.gridx = 3;
		gbc_SP_dailies.gridy = 2;
		add(SP_dailies, gbc_SP_dailies);
		//set up lists
		L_daily = new JList<String>();
		L_daily.setModel(new AbstractListModel<String>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -988170498780935788L;
			public int getSize() {
				return dailies.size();
			}
			public String getElementAt(int index) {
				return dailies.get(index);
			}
		});
		SP_dailies.setViewportView(L_daily);
		
		JLabel lblName = new JLabel("Name");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 3;
		add(lblName, gbc_lblName);
		
		TF_name = new JTextField();
		GridBagConstraints gbc_TF_name = new GridBagConstraints();
		gbc_TF_name.gridwidth = 2;
		gbc_TF_name.insets = new Insets(0, 0, 5, 5);
		gbc_TF_name.fill = GridBagConstraints.HORIZONTAL;
		gbc_TF_name.gridx = 1;
		gbc_TF_name.gridy = 3;
		add(TF_name, gbc_TF_name);
		TF_name.setColumns(10);
		
		JLabel lblDescription = new JLabel("Description");
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescription.gridx = 0;
		gbc_lblDescription.gridy = 4;
		add(lblDescription, gbc_lblDescription);
		
		JScrollPane SP_description = new JScrollPane();
		SP_description.setPreferredSize(new Dimension(0, 0));
		GridBagConstraints gbc_SP_description = new GridBagConstraints();
		gbc_SP_description.fill = GridBagConstraints.BOTH;
		gbc_SP_description.gridwidth = 2;
		gbc_SP_description.insets = new Insets(0, 0, 5, 5);
		gbc_SP_description.gridx = 1;
		gbc_SP_description.gridy = 4;
		add(SP_description, gbc_SP_description);
		
		SP_description.setPreferredSize(new Dimension(0, 0));
		TA_description = new JTextArea();
		TA_description.setWrapStyleWord(true);
		SP_description.setViewportView(TA_description);
		
		JLabel lblPriority = new JLabel("Priority");
		GridBagConstraints gbc_lblPriority = new GridBagConstraints();
		gbc_lblPriority.insets = new Insets(0, 0, 5, 5);
		gbc_lblPriority.gridx = 0;
		gbc_lblPriority.gridy = 5;
		add(lblPriority, gbc_lblPriority);
		
		CB_priority = new JComboBox<String>();
		CB_priority.setModel(new DefaultComboBoxModel<String>(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		GridBagConstraints gbc_CB_priority = new GridBagConstraints();
		gbc_CB_priority.gridwidth = 2;
		gbc_CB_priority.insets = new Insets(0, 0, 5, 5);
		gbc_CB_priority.fill = GridBagConstraints.HORIZONTAL;
		gbc_CB_priority.gridx = 1;
		gbc_CB_priority.gridy = 5;
		add(CB_priority, gbc_CB_priority);
		
		JButton B_task = new JButton("Set task");
		GridBagConstraints gbc_B_task = new GridBagConstraints();
		gbc_B_task.gridwidth = 2;
		gbc_B_task.insets = new Insets(0, 0, 0, 5);
		gbc_B_task.gridx = 1;
		gbc_B_task.gridy = 6;
		add(B_task, gbc_B_task);
		
		JButton B_new = new JButton("New DailyItem");
		GridBagConstraints gbc_B_New = new GridBagConstraints();
		gbc_B_New.insets = new Insets(0, 0, 0, 5);
		gbc_B_New.gridx = 3;
		gbc_B_New.gridy = 6;
		add(B_new, gbc_B_New);
		
		final JButton B_delete = new JButton("Delete");
		GridBagConstraints gbc_B_delete = new GridBagConstraints();
		gbc_B_delete.gridx = 4;
		gbc_B_delete.gridy = 6;
		add(B_delete, gbc_B_delete);
		//various listeners to update value of the daily item in Edit var when something changes
		CB_H.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int index=L_daily.getSelectedIndex();
				if(index>-1){
					if((int)CB_H.getValue()>=0&&(int)CB_H.getValue()<24){
						var.getDaily().get(index).setResetH((int)CB_H.getValue());
					}
					else if((int)CB_H.getValue()<0){
						CB_H.setValue(0);
						var.getDaily().get(index).setResetH((int)CB_H.getValue());
					}
					else if((int)CB_H.getValue()>=24){
						CB_H.setValue(23);
						var.getDaily().get(index).setResetH((int)CB_H.getValue());
					}
					con.addMsg("[Daily]["+dailies.get(index)+"] reset hour set to "+CB_H.getValue());
				}
			}
		});
		CB_M.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int index=L_daily.getSelectedIndex();
				if(index>-1){
					if((int)CB_M.getValue()>=0&&(int)CB_M.getValue()<60){
						var.getDaily().get(index).setResetM((int)CB_M.getValue());
					}
					else if((int)CB_M.getValue()<0){
						CB_M.setValue(0);
						var.getDaily().get(index).setResetM((int)CB_M.getValue());
					}
					else if((int)CB_M.getValue()>=60){
						CB_M.setValue(59);
						var.getDaily().get(index).setResetM((int)CB_M.getValue());
					}
					con.addMsg("[Daily]["+dailies.get(index)+"] reset minute set to "+CB_M.getValue());
				}
			}
		});
		TF_name.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				int index=L_daily.getSelectedIndex();
				if(index>-1){
					String old=dailies.get(index);
					TF_name.setText(checkExisting(TF_name.getText(),index));
					var.getDaily().get(index).setName(TF_name.getText());
					dailies.set(index, TF_name.getText());
					con.addMsg("[Daily]["+dailies.get(index)+"] name changed from "+old);
					L_daily.updateUI();
				}
			}
		});
		TF_name.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index=L_daily.getSelectedIndex();
				if(index>-1){
					String old=dailies.get(index);
					TF_name.setText(checkExisting(TF_name.getText(),index));
					var.getDaily().get(index).setName(TF_name.getText());
					dailies.set(index, TF_name.getText());
					con.addMsg("[Daily]["+dailies.get(index)+"] name changed from"+old);
					L_daily.updateUI();
				}
			}
		});
		TA_description.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				int index=L_daily.getSelectedIndex();
				if(index>-1){
					var.getDaily().get(index).setDescription(TA_description.getText());
					con.addMsg("[Daily]["+dailies.get(index)+"] description changed to "+TA_description.getText());
				}
			}
		});
		L_daily.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				L_daily.grabFocus();
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
				int index=L_daily.getSelectedIndex();
				if(index>-1){
					if(CB_priority.getSelectedIndex()>-1){
						var.getDaily().get(index).setPriority(Integer.parseInt(""+CB_priority.getSelectedItem()));
						con.addMsg("[Daily]["+dailies.get(index)+"] priority set to "+CB_priority.getSelectedItem());
					}
				}
			}
		});
		L_daily.addListSelectionListener(new ListSelectionListener() {
			int index=-1;
			public void valueChanged(ListSelectionEvent e) {
				if(L_daily.getSelectedIndex()>-1){
					if(mListPress&&index==-1){
						index=L_daily.getSelectedIndex();
					}
					if(!mListPress){
						if(L_daily.getSelectedIndex()!=index&&index>-1&&kControl){
							swapItems(index,L_daily.getSelectedIndex());
						}
						else if(L_daily.getSelectedIndex()!=index&&index>-1&&!kControl){
							moveItems(index,L_daily.getSelectedIndex());
							}
						index=-1;
					}
					setValues();
				}
			}
		});
		//create a new daily item
		B_new.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DailyItem daily=new DailyItem(checkExisting(),var);
				dailies.add(daily.getName());
				var.getDaily().add(daily);
				L_daily.setSelectedIndex(dailies.size()-1);
				L_daily.updateUI();
				setValues();
				con.addMsg("[Daily]["+dailies.get(L_daily.getSelectedIndex())+"] item created");
			}
		});
		B_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int response=JOptionPane.showConfirmDialog(B_delete, "Confirm delete?");
				if(response==JOptionPane.OK_OPTION){

					int index=L_daily.getSelectedIndex();
					if(index>-1){
						con.addMsg("[Daily]["+dailies.get(index)+"] removed");
						var.getDaily().remove(index);
						dailies.remove(index);
						L_daily.setSelectedIndex(index-1);
						setValues();
						L_daily.updateUI();
						L_daily.clearSelection();

					}
				}
			}
		});
		B_task.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(L_daily.getSelectedIndex()>-1){
					DailyItem daily=var.getDaily().get(L_daily.getSelectedIndex());
					EditTask eTask=new EditTask(var.getEdit(),daily.getTask(),daily.getMonitor(),daily.isHide());
					eTask.setVisible(true);
					Task task=eTask.getTask();
					if(!(task==null)){
						daily.setTask(task);
						boolean monitor=eTask.getMonitored();
						daily.setMonitor(monitor);
						boolean hide=eTask.getHide();
						daily.setHide(hide);
					}
				}
			}
		});
		
		
	}
	//checks if the default name for component(component type+number) is used, if not it will save as that otherwise it will continue searching
	public String checkExisting(){
		boolean noMatch=false;//boolean to check to make sure that there are no components that already have the same default names
		int num = 0;//number added to end of default names to differentiate
		while(!noMatch){//continues if a match has been found with the current name
			num++;
			noMatch=true;
			for(int i=0;i<dailies.size();i++){//checks if current numbering of default name exists
				if(dailies.get(i).equals("dailyItem"+num)){
					noMatch=false;//set noMatch at false continuing search, 
				}
			}
		}
		return "dailyItem"+num;
	}
	//similar to first one but has index to avoid checking against itself
	public String checkExisting(String name, int currentIndex){
		boolean noMatch=false;//boolean to check to make sure that there are no components that already have the same default names
		int num = 0;//number added to end of default names to differentiate
		while(!noMatch){//continues if a match has been found with the current name
			noMatch=true;
			num++;
			for(int i=0;i<dailies.size();i++){//checks if current numbering of default name exists
				if(i!=currentIndex){//to make sure that it isn't checking against itself
					if(dailies.get(i).equals(name)&&num==1){//first case if name is present
						System.out.println("firstCase");
						noMatch=false;
					}
					if(dailies.get(i).equals(name+num)&&num>1){//secound case if numbered name(from other checks) is present
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
	//used for drag and drop, to swap items, toggled with control
	public void swapItems(int item, int insert){
		if(item>-1&&insert>-1){
			DailyItem swap=var.getDaily().get(item);
			DailyItem replace=var.getDaily().get(insert);
			var.getDaily().set(item, replace);
			var.getDaily().set(insert, swap);
			reset();
		}
	}
	//just to move and insert at point
	public void moveItems(int item,int insert){
		if(item>-1&&insert>-1){
			DailyItem swap=var.getDaily().get(item);
			var.getDaily().remove(item);
			var.getDaily().add(insert, swap);
			reset();
		}
	}
	//called everytime it eneds to update the value in the swing components, usually when selected daily item changes in the list or new item creation
	public void setValues(){
		int index=L_daily.getSelectedIndex();
		if(index>-1){
			DailyItem selected=var.getDaily().get(index);
			TF_name.setText(selected.getName());
			CB_H.setValue(selected.getResetH());
			CB_M.setValue(selected.getResetM());
			TA_description.setText(selected.getDescription());
			CB_priority.setSelectedIndex(selected.getPriority()-1);
		}
	}
	//reset things reread things from main variable class
	public void reset(){
		dailies.clear();
		L_daily.clearSelection();
		for(int i=0;i<var.getDaily().size();i++){
			dailies.add(var.getDaily().get(i).getName());
		}
		CB_H.setValue(0);
		CB_M.setValue(0);
		TF_name.setText("");
		TA_description.setText("");
		CB_priority.setSelectedIndex(-1);
		L_daily.updateUI();
		con.addMsg("[Daily] data reset/refreshed");
	}
	public JPanel getP_Daily(){
		return this;
	}
	//used to swap between viewer and editor, jumps directly to item
	public void setItemIndex(long ID) {
		for(int i=0;i<var.getDaily().size();i++){
			if(var.getDaily().get(i).getID()==ID){
				L_daily.setSelectedIndex(i);
			}
		}
	}

}
