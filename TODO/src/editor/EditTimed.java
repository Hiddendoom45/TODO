package editor;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import global.Console;
import global.Task;
import global.items.TimedItem;

import javax.swing.JSpinner;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.AbstractListModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;
import java.awt.Dimension;

public class EditTimed extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5365377829709869462L;
	private EditVar var;
	private Console con;
	private Vector<String> times=new Vector<String>();
	private JTextField TF_name;
	private JList<String> L_timed;
	private JComboBox<String> CB_priority;
	private JSpinner Sp_d;
	private JTextArea TA_description;
	
	
	private boolean mListPress;
	private boolean kControl;
	private JSpinner Sp_h;
	private JSpinner Sp_m;
	private JSpinner Sp_s;

	/**
	 * Create the panel.
	 */
	public EditTimed(final EditVar var,final Console con) {
		this.var=var;
		this.con=con;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0};
		setLayout(gridBagLayout);
		
		JLabel lblName = new JLabel("Name");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 1;
		add(lblName, gbc_lblName);
		
		JLabel lblTimedItems = new JLabel("Timed Items");
		GridBagConstraints gbc_lblTimedItems = new GridBagConstraints();
		gbc_lblTimedItems.gridwidth = 2;
		gbc_lblTimedItems.insets = new Insets(0, 0, 5, 0);
		gbc_lblTimedItems.gridx = 2;
		gbc_lblTimedItems.gridy = 0;
		add(lblTimedItems, gbc_lblTimedItems);
		
		TF_name = new JTextField();
		GridBagConstraints gbc_TF_name = new GridBagConstraints();
		gbc_TF_name.weightx = 3.0;
		gbc_TF_name.insets = new Insets(0, 0, 5, 5);
		gbc_TF_name.fill = GridBagConstraints.HORIZONTAL;
		gbc_TF_name.gridx = 1;
		gbc_TF_name.gridy = 1;
		add(TF_name, gbc_TF_name);
		TF_name.setColumns(10);
		
		JScrollPane SP_timed = new JScrollPane();
		SP_timed.setPreferredSize(new Dimension(0, 0));
		GridBagConstraints gbc_SP_timed = new GridBagConstraints();
		gbc_SP_timed.weightx = 6.0;
		gbc_SP_timed.fill = GridBagConstraints.BOTH;
		gbc_SP_timed.gridheight = 5;
		gbc_SP_timed.gridwidth = 2;
		gbc_SP_timed.insets = new Insets(0, 0, 5, 0);
		gbc_SP_timed.gridx = 2;
		gbc_SP_timed.gridy = 1;
		add(SP_timed, gbc_SP_timed);
		
		L_timed = new JList<String>();
		L_timed.setModel(new AbstractListModel<String>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 3566753031772939050L;
			public int getSize() {
				return times.size();
			}
			public String getElementAt(int index) {
				return times.get(index);
			}
		});
		SP_timed.setViewportView(L_timed);
		
		JLabel lblDescription = new JLabel("Description");
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.gridwidth = 2;
		gbc_lblDescription.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescription.gridx = 0;
		gbc_lblDescription.gridy = 2;
		add(lblDescription, gbc_lblDescription);
		
		JScrollPane SP_description = new JScrollPane();
		SP_description.setPreferredSize(new Dimension(0, 0));
		GridBagConstraints gbc_SP_description = new GridBagConstraints();
		gbc_SP_description.fill = GridBagConstraints.BOTH;
		gbc_SP_description.gridwidth = 2;
		gbc_SP_description.insets = new Insets(0, 0, 5, 5);
		gbc_SP_description.gridx = 0;
		gbc_SP_description.gridy = 3;
		add(SP_description, gbc_SP_description);
		
		TA_description = new JTextArea();
		SP_description.setViewportView(TA_description);
		
		JLabel lblElapseTime = new JLabel("Elapse Time");
		GridBagConstraints gbc_lblElapseTime = new GridBagConstraints();
		gbc_lblElapseTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblElapseTime.gridx = 0;
		gbc_lblElapseTime.gridy = 4;
		add(lblElapseTime, gbc_lblElapseTime);
		
		JPanel P_elapseTime = new JPanel();
		GridBagConstraints gbc_P_elapseTime = new GridBagConstraints();
		gbc_P_elapseTime.fill = GridBagConstraints.BOTH;
		gbc_P_elapseTime.insets = new Insets(0, 0, 5, 5);
		gbc_P_elapseTime.gridx = 1;
		gbc_P_elapseTime.gridy = 4;
		add(P_elapseTime, gbc_P_elapseTime);
		P_elapseTime.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Sp_d = new JSpinner();
		Sp_d.setPreferredSize(new Dimension(52, 28));
		P_elapseTime.add(Sp_d);
		
		Sp_h = new JSpinner();
		Sp_h.setPreferredSize(new Dimension(52, 28));
		P_elapseTime.add(Sp_h);
		
		Sp_m = new JSpinner();
		Sp_m.setPreferredSize(new Dimension(52, 28));
		P_elapseTime.add(Sp_m);
		
		Sp_s = new JSpinner();
		Sp_s.setPreferredSize(new Dimension(52, 28));
		P_elapseTime.add(Sp_s);
		
		Sp_d.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int index=L_timed.getSelectedIndex();
				if(index>-1){
					if((int)Sp_d.getValue()<0){
						Sp_d.setValue(0);
					}
					setElapseTime();
				}
			}
		});
		Sp_h.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int index=L_timed.getSelectedIndex();
				if(index>-1){
					if((int)Sp_h.getValue()<0){
						Sp_h.setValue(0);
					}
					else if((int)Sp_h.getValue()>=24){
						Sp_h.setValue(23);
					}
					setElapseTime();
				}
			}
		});
		Sp_m.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int index=L_timed.getSelectedIndex();
				if(index>-1){
					if((int)Sp_m.getValue()<0){
						Sp_m.setValue(0);
					}
					else if((int)Sp_m.getValue()>=60){
						Sp_m.setValue(59);
					}
					setElapseTime();
				}
			}
		});
		Sp_s.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int index=L_timed.getSelectedIndex();
				if(index>-1){
					if((int)Sp_s.getValue()<0){
						Sp_s.setValue(0);
					}
					else if((int)Sp_m.getValue()>=60){
						Sp_s.setValue(59);
					}
					setElapseTime();
				}
			}
		});
		
		JLabel lblPriority = new JLabel("Priority");
		GridBagConstraints gbc_lblPriority = new GridBagConstraints();
		gbc_lblPriority.anchor = GridBagConstraints.EAST;
		gbc_lblPriority.insets = new Insets(0, 0, 5, 5);
		gbc_lblPriority.gridx = 0;
		gbc_lblPriority.gridy = 5;
		add(lblPriority, gbc_lblPriority);
		
		CB_priority = new JComboBox<String>();
		CB_priority.setModel(new DefaultComboBoxModel<String>(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		CB_priority.setSelectedIndex(0);
		GridBagConstraints gbc_CB_priority = new GridBagConstraints();
		gbc_CB_priority.insets = new Insets(0, 0, 5, 5);
		gbc_CB_priority.fill = GridBagConstraints.HORIZONTAL;
		gbc_CB_priority.gridx = 1;
		gbc_CB_priority.gridy = 5;
		add(CB_priority, gbc_CB_priority);
		
		CB_priority.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index=L_timed.getSelectedIndex();
				if(index>-1){
					var.getTimed().get(index).setPriority(Integer.parseInt(""+CB_priority.getSelectedItem()));
					con.addMsg("[Timed]["+times.get(index)+"]priority changed to "+CB_priority.getSelectedItem());
				}
			}
		});
		
		JButton B_new = new JButton("New");
		GridBagConstraints gbc_B_new = new GridBagConstraints();
		gbc_B_new.insets = new Insets(0, 0, 5, 5);
		gbc_B_new.gridx = 2;
		gbc_B_new.gridy = 6;
		add(B_new, gbc_B_new);
		
		B_new.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TimedItem timed=new TimedItem(checkExisting(),var);
				var.getTimed().add(timed);
				times.add(timed.getName());
				L_timed.updateUI();
				L_timed.setSelectedIndex(times.size()-1);
				setValues();
				con.addMsg("[Timed]new item added:"+times.get(L_timed.getSelectedIndex()));
			}
		});
		
		final JButton B_delete = new JButton("Delete");
		GridBagConstraints gbc_B_delete = new GridBagConstraints();
		gbc_B_delete.insets = new Insets(0, 0, 5, 0);
		gbc_B_delete.gridx = 3;
		gbc_B_delete.gridy = 6;
		add(B_delete, gbc_B_delete);
		
		JButton B_task = new JButton("Set Task");
		GridBagConstraints gbc_B_task = new GridBagConstraints();
		gbc_B_task.insets = new Insets(0, 0, 0, 5);
		gbc_B_task.gridx = 1;
		gbc_B_task.gridy = 6;
		add(B_task, gbc_B_task);
		B_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int response=JOptionPane.showConfirmDialog(B_delete, "Confirm delete?");
				if(response==JOptionPane.OK_OPTION){
					int index=L_timed.getSelectedIndex();
					if(index>-1){
						con.addMsg("[Timed]item deleted:"+times.get(index));
						var.getTimed().remove(index);
						times.remove(index);
						L_timed.setSelectedIndex(index-1);
						L_timed.updateUI();
						L_timed.clearSelection();
					}
				}
			}
		});
		
		TF_name.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index=L_timed.getSelectedIndex();
				if(index>-1){
					String old=var.getTimed().get(index).getName();
					TF_name.setText(checkExisting(TF_name.getText(),index));
					var.getTimed().get(index).setName(TF_name.getText());
					times.set(index, TF_name.getText());
					L_timed.updateUI();
					con.addMsg("[Timed]["+times.get(index)+"]name changed from "+old);
					L_timed.updateUI();
				}
			}
		});
		TF_name.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				int index=L_timed.getSelectedIndex();
				if(index>-1){
					String old=var.getTimed().get(index).getName();
					TF_name.setText(checkExisting(TF_name.getText(),index));
					var.getTimed().get(index).setName(TF_name.getText());
					times.set(index, TF_name.getText());
					L_timed.updateUI();
					con.addMsg("[Timed]["+times.get(index)+"]name changed from "+old);
					L_timed.updateUI();
				}
			}
		});
		
		TA_description.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				int index=L_timed.getSelectedIndex();
				if(index>-1){
					String old=var.getTimed().get(index).getDescription();
					var.getTimed().get(index).setDescription(TA_description.getText());
					con.addMsg("[Timed]["+times.get(index)+"]description:"+old+" changed to "+TA_description.getText());
				}
			}
		});
		L_timed.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				L_timed.grabFocus();
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
		L_timed.addListSelectionListener(new ListSelectionListener() {
			int index=-1;
			public void valueChanged(ListSelectionEvent e) {
				if(L_timed.getSelectedIndex()>-1){
					if(mListPress&&index==-1){
						index=L_timed.getSelectedIndex();
					}
					if(!mListPress){
						if(L_timed.getSelectedIndex()!=index&&index>-1&&kControl){
							swapItems(index,L_timed.getSelectedIndex());
						}
						else if(L_timed.getSelectedIndex()!=index&&index>-1&&!kControl){
							moveItems(index,L_timed.getSelectedIndex());
							}
						index=-1;
					}
					setValues();

				}
			}
		});
		B_task.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(L_timed.getSelectedIndex()>-1){
					TimedItem timed=var.getTimed().get(L_timed.getSelectedIndex());
					EditTask eTask=new EditTask(var.getEdit(),timed.getTask(),timed.getMonitor(),timed.isHide());
					eTask.setVisible(true);
					Task task=eTask.getTask();
					if(!(task==null)){
						timed.setTask(task);
						boolean monitor=eTask.getMonitored();
						timed.setMonitor(monitor);
						boolean hide=eTask.getHide();
						timed.setHide(hide);
					}
				}
			}
		});
		
		reset();
	}
	public void setElapseTime(){
		if(L_timed.getSelectedIndex()>-1){
			long elapseTime=(long)((86400*(long)(int)Sp_d.getValue())+(3600*(int)Sp_h.getValue())+(60*(int)Sp_m.getValue())+(int)Sp_s.getValue())*1000;
			var.getTimed().get(L_timed.getSelectedIndex()).setTimeElapse(elapseTime);
		}
	}
	//checks if the default name for component(component type+number) is used, if not it will save as that otherwise it will continue searching
		public String checkExisting(){
			boolean noMatch=false;//boolean to check to make sure that there are no components that already have the same default names
			int num = 0;//number added to end of default names to differentiate
			while(!noMatch){//continues if a match has been found with the current name
				num++;
				noMatch=true;
				for(int i=0;i<times.size();i++){//checks if current numbering of default name exists
					if(times.get(i).equals("timedItem"+num)){
						noMatch=false;//set noMatch at false continuing search, 
					}
				}
			}
			return "timedItem"+num;
		}
		//similar to first one but has index to avoid checking against itself
		public String checkExisting(String name, int currentIndex){
			boolean noMatch=false;//boolean to check to make sure that there are no components that already have the same default names
			int num = 0;//number added to end of default names to differentiate
			while(!noMatch){//continues if a match has been found with the current name
				noMatch=true;
				num++;
				for(int i=0;i<times.size();i++){//checks if current numbering of default name exists
					if(i!=currentIndex){//to make sure that it isn't checking against itself
						if(times.get(i).equals(name)&&num==1){//first case if name is present
							System.out.println("firstCase");
							noMatch=false;
						}
						if(times.get(i).equals(name+num)&&num>1){//secound case if numbered name(from other checks) is present
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
		public void swapItems(int item, int insert){
			if(item>-1&&insert>-1){
				TimedItem swap=var.getTimed().get(item);
				TimedItem replace=var.getTimed().get(insert);
				var.getTimed().set(item, replace);
				var.getTimed().set(insert, swap);
				reset();
			}
		}
		public void moveItems(int item,int insert){
			if(item>-1&&insert>-1){
				TimedItem swap=var.getTimed().get(item);
				var.getTimed().remove(item);
				var.getTimed().add(insert, swap);
				reset();
			}
		}
		public void setValues(){
			if(L_timed.getSelectedIndex()>-1){
				TF_name.setText(var.getTimed().get(L_timed.getSelectedIndex()).getName());
				CB_priority.setSelectedIndex(var.getTimed().get(L_timed.getSelectedIndex()).getPriority()-1);
				long time=var.getTimed().get(L_timed.getSelectedIndex()).getTimeElapse()/1000;
				int s=(int) (time%60);
		    	int m=(int) (time/60%60);
		    	int h=(int) (time/3600%24);
		    	int d=(int) (time/86400);
		    	Sp_s.setValue(s);
		    	Sp_m.setValue(m);
		    	Sp_h.setValue(h);
		    	Sp_d.setValue(d);
				TA_description.setText(var.getTimed().get(L_timed.getSelectedIndex()).getDescription());
			}
		}
		public void reset(){
			times.clear();
			L_timed.clearSelection();
			TA_description.setText("");
			TF_name.setText("");
			CB_priority.setSelectedIndex(0);
			Sp_s.setValue(0);
			Sp_m.setValue(0);
			Sp_h.setValue(0);
			Sp_d.setValue(0);
			for(int i=0;i<var.getTimed().size();i++){
				times.add(var.getTimed().get(i).getName());
			}
			setValues();
			L_timed.updateUI();
			con.addMsg("[Timed]data refreshed/reset for times");
		}
		public JPanel getP_Timed(){
			return this;
		}
		public void setItemIndex(long ID) {
			for(int i=0;i<var.getTimed().size();i++){
				if(var.getTimed().get(i).getID()==ID){
					L_timed.setSelectedIndex(i);
				}
			}
			
		}
}
