package editor;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import global.Console;
import global.Task;
import global.items.EnergyItem;

import javax.swing.event.ChangeEvent;
import javax.swing.DefaultComboBoxModel;

public class EditEnergy extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3249360708047474360L;
	private JTextField TF_name;
	private JList<String> L_energy;
	private Vector<String> energies=new Vector<String>();
	private JComboBox<String> CB_priority;
	private JTextArea TA_description;
	private JSpinner Sp_max;
	private JButton B_task;
	private JButton B_new;
	private JButton B_delete;
	private EditVar var;
	private Console con;
	private JTextField TF_replenish;
	
	
	private boolean mListPress=false;
	private boolean kControl;

	/**
	 * Create the panel.
	 */
	public EditEnergy(final EditVar var,final Console con) {
		this.var=var;
		this.con=con;
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.1, 4.0, 2.0, 2.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblParameters = new JLabel("Parameters");
		GridBagConstraints gbc_lblParameters = new GridBagConstraints();
		gbc_lblParameters.gridwidth = 2;
		gbc_lblParameters.insets = new Insets(0, 0, 5, 5);
		gbc_lblParameters.gridx = 0;
		gbc_lblParameters.gridy = 0;
		add(lblParameters, gbc_lblParameters);
		
		JLabel lblEnergyTaskList = new JLabel("Energy Task List");
		GridBagConstraints gbc_lblEnergyTaskList = new GridBagConstraints();
		gbc_lblEnergyTaskList.insets = new Insets(0, 0, 5, 5);
		gbc_lblEnergyTaskList.gridx = 2;
		gbc_lblEnergyTaskList.gridy = 0;
		add(lblEnergyTaskList, gbc_lblEnergyTaskList);
		
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
		
		JScrollPane SP_energy = new JScrollPane();
		GridBagConstraints gbc_SP_energy = new GridBagConstraints();
		gbc_SP_energy.fill = GridBagConstraints.BOTH;
		gbc_SP_energy.gridheight = 6;
		gbc_SP_energy.gridwidth = 2;
		gbc_SP_energy.insets = new Insets(0, 0, 5, 0);
		gbc_SP_energy.gridx = 2;
		gbc_SP_energy.gridy = 1;
		add(SP_energy, gbc_SP_energy);
		
		L_energy = new JList<String>();
		L_energy.setModel(new AbstractListModel<String>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -988170498780935788L;
			public int getSize() {
				return energies.size();
			}
			public String getElementAt(int index) {
				return energies.get(index);
			}
		});
		SP_energy.setViewportView(L_energy);
		
		JLabel lblPriority = new JLabel("Priority");
		GridBagConstraints gbc_lblPriority = new GridBagConstraints();
		gbc_lblPriority.insets = new Insets(0, 0, 5, 5);
		gbc_lblPriority.gridx = 0;
		gbc_lblPriority.gridy = 2;
		add(lblPriority, gbc_lblPriority);
		
		CB_priority = new JComboBox<String>();
		CB_priority.setModel(new DefaultComboBoxModel<String>(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		GridBagConstraints gbc_CB_priority = new GridBagConstraints();
		gbc_CB_priority.insets = new Insets(0, 0, 5, 5);
		gbc_CB_priority.fill = GridBagConstraints.HORIZONTAL;
		gbc_CB_priority.gridx = 1;
		gbc_CB_priority.gridy = 2;
		add(CB_priority, gbc_CB_priority);
		
		JLabel lblDescription = new JLabel("Description");
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.gridwidth = 2;
		gbc_lblDescription.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescription.gridx = 0;
		gbc_lblDescription.gridy = 3;
		add(lblDescription, gbc_lblDescription);
		
		JScrollPane SP_description = new JScrollPane();
		GridBagConstraints gbc_SP_description = new GridBagConstraints();
		gbc_SP_description.weighty = 3.0;
		gbc_SP_description.gridwidth = 2;
		gbc_SP_description.fill = GridBagConstraints.BOTH;
		gbc_SP_description.insets = new Insets(0, 0, 5, 5);
		gbc_SP_description.gridx = 0;
		gbc_SP_description.gridy = 4;
		add(SP_description, gbc_SP_description);
		
		TA_description = new JTextArea();
		SP_description.setViewportView(TA_description);
		
		JLabel lblMaxEnergy = new JLabel("Max Energy");
		GridBagConstraints gbc_lblMaxEnergy = new GridBagConstraints();
		gbc_lblMaxEnergy.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaxEnergy.gridx = 0;
		gbc_lblMaxEnergy.gridy = 5;
		add(lblMaxEnergy, gbc_lblMaxEnergy);
		
		Sp_max = new JSpinner();
		GridBagConstraints gbc_Sp_max = new GridBagConstraints();
		gbc_Sp_max.fill = GridBagConstraints.HORIZONTAL;
		gbc_Sp_max.insets = new Insets(0, 0, 5, 5);
		gbc_Sp_max.gridx = 1;
		gbc_Sp_max.gridy = 5;
		add(Sp_max, gbc_Sp_max);
		
		JLabel lblSingleEnergyReplenish = new JLabel("min/Energy Replenish");
		GridBagConstraints gbc_lblSingleEnergyReplenish = new GridBagConstraints();
		gbc_lblSingleEnergyReplenish.insets = new Insets(0, 0, 5, 5);
		gbc_lblSingleEnergyReplenish.gridx = 0;
		gbc_lblSingleEnergyReplenish.gridy = 6;
		add(lblSingleEnergyReplenish, gbc_lblSingleEnergyReplenish);
		
		TF_replenish = new JTextField();
		GridBagConstraints gbc_TF_replenish = new GridBagConstraints();
		gbc_TF_replenish.fill = GridBagConstraints.HORIZONTAL;
		gbc_TF_replenish.insets = new Insets(0, 0, 5, 5);
		gbc_TF_replenish.gridx = 1;
		gbc_TF_replenish.gridy = 6;
		add(TF_replenish, gbc_TF_replenish);
		
		B_task = new JButton("Set Task");
		GridBagConstraints gbc_B_task = new GridBagConstraints();
		gbc_B_task.insets = new Insets(0, 0, 0, 5);
		gbc_B_task.gridx = 1;
		gbc_B_task.gridy = 7;
		add(B_task, gbc_B_task);
		
		B_new = new JButton("New Item");
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
		
		Sp_max.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int index=L_energy.getSelectedIndex();
				if(index>-1)
				if((int)Sp_max.getValue()<0){
					Sp_max.setValue(0);
				}
				var.getEnergy().get(index).setEnergyMax((int)Sp_max.getValue());
			}
		});
		TF_replenish.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				int index=L_energy.getSelectedIndex();
				if(index>-1){
					String number=keepNum(TF_replenish.getText());//convert to numbers only
					TF_replenish.setText(number);
					if(TF_replenish.getText().equals("")||TF_replenish.equals(".")){
						TF_replenish.setText("0");
					}
					var.getEnergy().get(index).setEnergyURefill(Float.parseFloat(TF_replenish.getText()));//save value
				}
			}
		});
		TF_replenish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index=L_energy.getSelectedIndex();
				if(index>-1){
					String number=keepNum(TF_replenish.getText());//convert to numbers only
					TF_replenish.setText(number);
					if(TF_replenish.getText().equals("")||TF_replenish.equals(".")){
						TF_replenish.setText("0");
					}
					var.getEnergy().get(index).setEnergyURefill(Float.parseFloat(TF_replenish.getText()));//save value
				}
			}
		});
		TF_name.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				int index=L_energy.getSelectedIndex();
				if(index>-1){
					String old=energies.get(index);
					TF_name.setText(checkExisting(TF_name.getText(),index));
					var.getEnergy().get(index).setName(TF_name.getText());
					energies.set(index, TF_name.getText());
					con.addMsg("[Energy]["+energies.get(index)+"] name changed from "+old);
					L_energy.updateUI();
				}
			}
		});
		TF_name.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index=L_energy.getSelectedIndex();
				if(index>-1){
					String old=energies.get(index);
					TF_name.setText(checkExisting(TF_name.getText(),index));
					var.getEnergy().get(index).setName(TF_name.getText());
					energies.set(index, TF_name.getText());
					con.addMsg("[Energy]["+energies.get(index)+"] name changed from "+old);
					L_energy.updateUI();
				}
			}
		});
		
		TA_description.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				int index=L_energy.getSelectedIndex();
				if(index>-1){
					var.getEnergy().get(index).setDescription(TA_description.getText());
					con.addMsg("[Energy]["+energies.get(index)+"] description changed to "+TA_description.getText());
				}
			}
		});
		L_energy.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				L_energy.grabFocus();
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
				int index=L_energy.getSelectedIndex();
				if(index>-1){
					if(CB_priority.getSelectedIndex()>-1){
						var.getEnergy().get(index).setPriority(Integer.parseInt(""+CB_priority.getSelectedItem()));
						con.addMsg("[Energy]["+energies.get(index)+"] priority set to "+CB_priority.getSelectedItem());
					}
				}
			}
		});
		L_energy.addListSelectionListener(new ListSelectionListener() {
			int index=-1;
			public void valueChanged(ListSelectionEvent e) {
				if(L_energy.getSelectedIndex()>-1){
					if(mListPress&&index==-1){
						index=L_energy.getSelectedIndex();
					}
					if(!mListPress){
						if(L_energy.getSelectedIndex()!=index&&index>-1&&kControl){
							swapItems(index,L_energy.getSelectedIndex());
						}
						else if(L_energy.getSelectedIndex()!=index&&index>-1&&!kControl){
							moveItems(index,L_energy.getSelectedIndex());
						}
						index=-1;
					}
					setValues();
				}
			}
		});
		B_new.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EnergyItem energy=new EnergyItem(checkExisting(),var);
				energies.add(energy.getName());
				var.getEnergy().add(energy);
				L_energy.setSelectedIndex(energies.size()-1);
				L_energy.updateUI();
				setValues();
				con.addMsg("[Energy]["+energies.get(L_energy.getSelectedIndex())+"] item created");
			}
		});
		B_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int response=JOptionPane.showConfirmDialog(B_delete, "Confirm delete?");
				if(response==JOptionPane.OK_OPTION){

					int index=L_energy.getSelectedIndex();
					if(index>-1){
						con.addMsg("[Energy]["+energies.get(index)+"] removed");
						var.getEnergy().remove(index);
						energies.remove(index);
						L_energy.setSelectedIndex(index-1);
						setValues();
						L_energy.updateUI();
						L_energy.clearSelection();

					}
				}
			}
		});
		B_task.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(L_energy.getSelectedIndex()>-1){
					EnergyItem energy=var.getEnergy().get(L_energy.getSelectedIndex());
					EditTask eTask=new EditTask(var.getEdit(),energy.getTask(),energy.getMonitor(),energy.isHide());
					eTask.setVisible(true);
					Task task=eTask.getTask();
					if(!(task==null)){
						energy.setTask(task);
						boolean monitor=eTask.getMonitored();
						energy.setMonitor(monitor);
						boolean hide=eTask.getHide();
						energy.setHide(hide);
					}
				}
			}
		});
	}
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
				for(int i=0;i<energies.size();i++){//checks if current numbering of default name exists
					if(energies.get(i).equals("energyItem"+num)){
						noMatch=false;//set noMatch at false continuing search, 
					}
				}
			}
			return "energyItem"+num;
		}
		//similar to first one but has index to avoid checking against itself
		public String checkExisting(String name, int currentIndex){
			boolean noMatch=false;//boolean to check to make sure that there are no components that already have the same default names
			int num = 0;//number added to end of default names to differentiate
			while(!noMatch){//continues if a match has been found with the current name
				noMatch=true;
				num++;
				for(int i=0;i<energies.size();i++){//checks if current numbering of default name exists
					if(i!=currentIndex){//to make sure that it isn't checking against itself
						if(energies.get(i).equals(name)&&num==1){//first case if name is present
							System.out.println("firstCase");
							noMatch=false;
						}
						if(energies.get(i).equals(name+num)&&num>1){//secound case if numbered name(from other checks) is present
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
				EnergyItem swap=var.getEnergy().get(item);
				EnergyItem replace=var.getEnergy().get(insert);
				var.getEnergy().set(item, replace);
				var.getEnergy().set(insert, swap);
				reset();
			}
		}
		public void moveItems(int item,int insert){
			if(item>-1&&insert>-1){
				EnergyItem swap=var.getEnergy().get(item);
				var.getEnergy().remove(item);
				var.getEnergy().add(insert, swap);
				reset();
			}
		}
		public void setValues(){
			int index=L_energy.getSelectedIndex();
			if(index>-1){
				EnergyItem selected=var.getEnergy().get(index);
				TF_name.setText(selected.getName());
				TA_description.setText(selected.getDescription());
				CB_priority.setSelectedIndex(selected.getPriority()-1);
				Sp_max.setValue(selected.getEnergyMax());
				TF_replenish.setText(""+((float)selected.getEnergyURefill()));
			}
		}
		public void reset(){
			energies.clear();
			L_energy.clearSelection();
			for(int i=0;i<var.getEnergy().size();i++){
				energies.add(var.getEnergy().get(i).getName());
				Sp_max.setValue(0);
				TF_replenish.setText("0");
				TF_name.setText("");
				TA_description.setText("");
				CB_priority.setSelectedIndex(-1);
				L_energy.updateUI();
				con.addMsg("[Energy] data reset/refreshed");
				
			}
		}
		public JPanel getP_energy(){
			return this;
		}
		public void setItemIndex(long ID) {
			for(int i=0;i<var.getEnergy().size();i++){
				if(var.getEnergy().get(i).getID()==ID){
					L_energy.setSelectedIndex(i);
				}
			}
			
		}

}
