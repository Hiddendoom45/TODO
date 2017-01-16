package editor;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

import global.Console;
import global.items.SingleItem;

import javax.swing.JToggleButton;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.AbstractListModel;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * most generic task, something todo and you mark it as complete when you're done
 * @author Allen
 *
 */
public class EditSingle extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7797758757484527738L;
	//swing variables and main global variables var and con, for variable, holds most important variables, console is used to log stuff 
	private JTextField TF_name;
	private Vector<String> singles=new Vector<String>();
	private EditVar var;
	private Console con;
	private JList<String> L_single;
	private JToggleButton TB_complete;
	private final JTextArea TA_description;
	private JComboBox<String> CB_priority;
	
	
	
	//drag and dropping of stuff in the list
	private boolean mListPress=false;
	private boolean kControl;//whether control is pressed, to change between just inserting and swapping the items
	/**
	 * Create the panel.
	 */
	public EditSingle(final EditVar var,final Console con) {
		this.var=var;
		this.con=con;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE, 0.0};
		setLayout(gridBagLayout);
		
		JLabel lblName = new JLabel("Name");
		GridBagConstraints gbc_lblName = new GridBagConstraints();
		gbc_lblName.insets = new Insets(0, 0, 5, 5);
		gbc_lblName.gridx = 0;
		gbc_lblName.gridy = 0;
		add(lblName, gbc_lblName);
		
		JLabel lblSingleItems = new JLabel("Single Items");
		GridBagConstraints gbc_lblSingleItems = new GridBagConstraints();
		gbc_lblSingleItems.gridwidth = 2;
		gbc_lblSingleItems.insets = new Insets(0, 0, 5, 5);
		gbc_lblSingleItems.gridx = 1;
		gbc_lblSingleItems.gridy = 0;
		add(lblSingleItems, gbc_lblSingleItems);
		
		TF_name = new JTextField();
		TF_name.setPreferredSize(new Dimension(0, 28));
		GridBagConstraints gbc_TF_name = new GridBagConstraints();
		gbc_TF_name.weighty = 0.3;
		gbc_TF_name.insets = new Insets(0, 0, 5, 5);
		gbc_TF_name.fill = GridBagConstraints.HORIZONTAL;
		gbc_TF_name.gridx = 0;
		gbc_TF_name.gridy = 1;
		add(TF_name, gbc_TF_name);
		TF_name.setColumns(10);
		
		JScrollPane SP_items = new JScrollPane();
		SP_items.setPreferredSize(new Dimension(0, 0));
		GridBagConstraints gbc_SP_items = new GridBagConstraints();
		gbc_SP_items.weightx = 1.0;
		gbc_SP_items.gridwidth = 2;
		gbc_SP_items.insets = new Insets(0, 0, 5, 0);
		gbc_SP_items.fill = GridBagConstraints.BOTH;
		gbc_SP_items.gridheight = 6;
		gbc_SP_items.gridx = 1;
		gbc_SP_items.gridy = 1;
		add(SP_items, gbc_SP_items);
		//setup list
		L_single = new JList<String>();
		L_single.setModel(new AbstractListModel<String>() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 7489825115418125659L;
			public int getSize() {
				return singles.size();
			}
			public String getElementAt(int index) {
				return singles.get(index);
			}
		});
		SP_items.setViewportView(L_single);
		
		JLabel lblDescription = new JLabel("Description");
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescription.gridx = 0;
		gbc_lblDescription.gridy = 2;
		add(lblDescription, gbc_lblDescription);
		
		JScrollPane SP_description = new JScrollPane();
		SP_description.setPreferredSize(new Dimension(0, 0));
		GridBagConstraints gbc_SP_description = new GridBagConstraints();
		gbc_SP_description.weighty = 3.0;
		gbc_SP_description.fill = GridBagConstraints.BOTH;
		gbc_SP_description.insets = new Insets(0, 0, 5, 5);
		gbc_SP_description.gridx = 0;
		gbc_SP_description.gridy = 3;
		add(SP_description, gbc_SP_description);
		
		TA_description = new JTextArea();
		TA_description.setPreferredSize(new Dimension(0, 0));
		SP_description.setViewportView(TA_description);
		TA_description.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JLabel lblCompleted = new JLabel("Completed?");
		GridBagConstraints gbc_lblCompleted = new GridBagConstraints();
		gbc_lblCompleted.insets = new Insets(0, 0, 5, 5);
		gbc_lblCompleted.gridx = 0;
		gbc_lblCompleted.gridy = 4;
		add(lblCompleted, gbc_lblCompleted);
		
		TB_complete = new JToggleButton("False");
		GridBagConstraints gbc_TB_complete = new GridBagConstraints();
		gbc_TB_complete.insets = new Insets(0, 0, 5, 5);
		gbc_TB_complete.gridx = 0;
		gbc_TB_complete.gridy = 5;
		add(TB_complete, gbc_TB_complete);
		
		JLabel lblPriority = new JLabel("Priority");
		GridBagConstraints gbc_lblPriority = new GridBagConstraints();
		gbc_lblPriority.insets = new Insets(0, 0, 5, 5);
		gbc_lblPriority.gridx = 0;
		gbc_lblPriority.gridy = 6;
		add(lblPriority, gbc_lblPriority);
		
		CB_priority = new JComboBox<String>();
		CB_priority.setPreferredSize(new Dimension(0, 27));
		CB_priority.setModel(new DefaultComboBoxModel<String>(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		CB_priority.setSelectedIndex(0);
		GridBagConstraints gbc_CB_priority = new GridBagConstraints();
		gbc_CB_priority.weightx = 2.0;
		gbc_CB_priority.insets = new Insets(0, 0, 0, 5);
		gbc_CB_priority.fill = GridBagConstraints.HORIZONTAL;
		gbc_CB_priority.gridx = 0;
		gbc_CB_priority.gridy = 7;
		add(CB_priority, gbc_CB_priority);
		
		JButton B_new = new JButton("New Single Item");
		GridBagConstraints gbc_B_new = new GridBagConstraints();
		gbc_B_new.weightx = 0.5;
		gbc_B_new.insets = new Insets(0, 0, 0, 5);
		gbc_B_new.gridx = 1;
		gbc_B_new.gridy = 7;
		add(B_new, gbc_B_new);
		
		final JButton B_delete = new JButton("Delete");
		GridBagConstraints gbc_B_delete = new GridBagConstraints();
		gbc_B_delete.weightx = 0.5;
		gbc_B_delete.gridx = 2;
		gbc_B_delete.gridy = 7;
		add(B_delete, gbc_B_delete);
		
		TF_name.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index=L_single.getSelectedIndex();
				if(index>-1){
					String old=var.getSingle().get(index).getName();
					TF_name.setText(checkExisting(TF_name.getText(),index));
					var.getSingle().get(index).setName(TF_name.getText());
					singles.set(index, TF_name.getText());
					con.addMsg("[Single]["+singles.get(index)+"]name changed from "+old);
					L_single.updateUI();
				}
			}
		});
		TF_name.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				int index=L_single.getSelectedIndex();
				if(index>-1){
					String old=var.getSingle().get(index).getName();
					TF_name.setText(checkExisting(TF_name.getText(),index));
					var.getSingle().get(index).setName(TF_name.getText());
					singles.set(index, TF_name.getText());
					con.addMsg("[Single]["+singles.get(index)+"]name changed from "+old);
					L_single.updateUI();
				}
			}
		});
		TA_description.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				int index=L_single.getSelectedIndex();
				if(index>-1){
					String old=var.getSingle().get(index).getDescription();
					var.getSingle().get(index).setDescription(TA_description.getText());
					con.addMsg("[Single]["+singles.get(index)+"]description:"+old+" changed to "+var.getSingle().get(index).getDescription());
				}
			}
		});
		//used for drag and drop in the list
		L_single.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				L_single.grabFocus();//grab focus so that there isn't any error with focus lost listeners for text components
			}
			public void mousePressed(MouseEvent e){
				mListPress=true;
			}
			public void mouseReleased(MouseEvent e){
				mListPress=false;
				if(e.isControlDown()){
					kControl=true;
				}
				else{
					kControl=false;
				}
			}
		});
		
		TB_complete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index=L_single.getSelectedIndex();
				if(index>-1){
					if(TB_complete.isSelected()){
						TB_complete.setText("True");
						var.getSingle().get(index).taskComplete();;
						con.addMsg("[Single]["+singles.get(index)+"] complete set to TRUE");
					}
					else{
						TB_complete.setText("False");
						var.getSingle().get(index).nullComplete();;
						con.addMsg("[Single]["+singles.get(index)+"] complete set to FALSE");
					}
				}
			}
		});
		CB_priority.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index=L_single.getSelectedIndex();
				if(index>-1){
					if(CB_priority.getSelectedIndex()>-1){
						var.getSingle().get(index).setPriority(Integer.parseInt(""+CB_priority.getSelectedItem()));
						con.addMsg("[Single]["+singles.get(index)+"] priority set to "+CB_priority.getSelectedItem());
					}
				}
			}
		});
		L_single.addListSelectionListener(new ListSelectionListener() {
			int index=-1;
			public void valueChanged(ListSelectionEvent e) {
				if(L_single.getSelectedIndex()>-1){
					if(mListPress&&index==-1){
						index=L_single.getSelectedIndex();
					}
					if(!mListPress){
						if(L_single.getSelectedIndex()!=index&&index>-1&&kControl){
							swapItems(index,L_single.getSelectedIndex());
						}
						else if(L_single.getSelectedIndex()!=index&&index>-1&&kControl){
							moveItems(index,L_single.getSelectedIndex());
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
				SingleItem single=new SingleItem(checkExisting(),var);
				singles.add(single.getName());
				var.getSingle().add(single);
				L_single.setSelectedIndex(singles.size()-1);
				L_single.updateUI();
				setValues();
				con.addMsg("[Single]["+singles.get(L_single.getSelectedIndex())+"] new item added");
			}
		});
		B_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int response=JOptionPane.showConfirmDialog(B_delete, "Confirm delete?");
				if(response==JOptionPane.OK_OPTION){
					int index=L_single.getSelectedIndex();
					if(index>-1){
						String old=singles.get(index);
						var.getSingle().remove(index);
						singles.remove(index);
						L_single.setSelectedIndex(index-1);
						setValues();
						L_single.updateUI();
						L_single.clearSelection();
						con.addMsg("[Single]["+old+"] removed");
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
			for(int i=0;i<singles.size();i++){//checks if current numbering of default name exists
				if(singles.get(i).equals("singleItem"+num)){
					noMatch=false;//set noMatch at false continuing search, 
				}
			}
		}
		return "singleItem"+num;
	}
	//similar to first one but has index to avoid checking against itself
	public String checkExisting(String name, int currentIndex){
		boolean noMatch=false;//boolean to check to make sure that there are no components that already have the same default names
		int num = 0;//number added to end of default names to differentiate
		while(!noMatch){//continues if a match has been found with the current name
			noMatch=true;
			num++;
			for(int i=0;i<singles.size();i++){//checks if current numbering of default name exists
				if(i!=currentIndex){//to make sure that it isn't checking against itself
					if(singles.get(i).equals(name)&&num==1){//first case if name is present
						System.out.println("firstCase");
						noMatch=false;
					}
					if(singles.get(i).equals(name+num)&&num>1){//secound case if numbered name(from other checks) is present
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
	//for drag and drop, swaps items around in list
	public void swapItems(int item, int insert){
		if(item>-1&&insert>-1){
			SingleItem swap=var.getSingle().get(item);
			SingleItem replace=var.getSingle().get(insert);
			var.getSingle().set(item, replace);
			var.getSingle().set(insert, swap);
			reset();
		}
	}
	//for drag and drop, move items around in list
	public void moveItems(int item,int insert){
		if(item>-1&&insert>-1){
			SingleItem swap=var.getSingle().get(item);
			var.getSingle().remove(item);
			var.getSingle().add(insert, swap);
			reset();
		}
	}
	//set values of swing eleents, called when item selected in list is changed or otherwise similar reasons
	public void setValues(){
		if(L_single.getSelectedIndex()>-1){
			TF_name.setText(var.getSingle().get(L_single.getSelectedIndex()).getName());
			TA_description.setText(var.getSingle().get(L_single.getSelectedIndex()).getDescription());
			CB_priority.setSelectedIndex(var.getSingle().get(L_single.getSelectedIndex()).getPriority()-1);
			if(var.getSingle().get(L_single.getSelectedIndex()).getComplete()){
				TB_complete.setSelected(true);
				TB_complete.setText("true");
			}
			else{
				TB_complete.setSelected(false);
				TB_complete.setText("false");
			}
		}
	}
	//reset things and reload things
	public void reset(){
		singles.clear();
		for(int i=0;i<var.getSingle().size();i++){
			singles.add(var.getSingle().get(i).getName());
		}
		L_single.setSelectedIndex(-1);
		L_single.updateUI();
		con.addMsg("[Single] data refreshed/reset");
	}
	//used to allow eclipse to render panel properly
	public JPanel getP_Single(){
		return this;
	}
	//used to switch from viewer to an item in editor withe item selected
	public void setItemIndex(long ID){
		for(int i=0;i<var.getSingle().size();i++){
			if(var.getSingle().get(i).getID()==ID){
				L_single.setSelectedIndex(i);
			}
		}
	}
}
