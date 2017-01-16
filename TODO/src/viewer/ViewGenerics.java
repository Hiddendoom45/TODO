package viewer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import global.ItemType;
import global.TODOItem;


//a generic class containing the most common components of all the view panels with all the handlers for listeners generated
public class ViewGenerics extends JPanel implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3385370860211278436L;
	protected ViewVar var;
	protected ArrayList<Long> incompleteID=new ArrayList<Long>();//ditto
	protected ArrayList<Long> completeID=new ArrayList<Long>();//keep id for purposes of editing/removing item in variable area
	protected ArrayList<String> incomplete=new ArrayList<String>();
	protected ArrayList<String> complete=new ArrayList<String>();
	
	protected JList<String> L_incomplete;
	protected JList<String> L_complete; 
	protected JButton B_toggle;
	protected JButton B_update;
	protected JButton B_delete;
	protected JTextArea TA_description;
	
	private ArrayList<ItemType> filter=new ArrayList<ItemType>();
	public ViewGenerics() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				update();
			}
		});
	}
	//sets most of the main listeners used
	public void setListeners(){
		TA_description.setWrapStyleWord(true);
		L_incomplete.setModel(new AbstractListModel<String>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 26341L;
			public int getSize() {
				return incomplete.size();
			}
			public String getElementAt(int index) {
				return incomplete.get(index);
			}
		});
		L_complete.setModel(new AbstractListModel<String>() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 133525L;
			public int getSize() {
				return complete.size();
			}
			public String getElementAt(int index) {
				return complete.get(index);
			}
		});
		
		L_incomplete.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				setValues(false);
			}
		});
		L_complete.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				setValues(true);
			}
		});
		B_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int response=JOptionPane.showConfirmDialog(B_delete, "Confirm delete?");
				if(response==JOptionPane.OK_OPTION){
					if(L_incomplete.getSelectedIndex()>-1&&L_complete.isSelectionEmpty()){
						var.deleteItem(incompleteID.get(L_incomplete.getSelectedIndex()));
						L_incomplete.updateUI();
						update();
					}
					else if(L_complete.getSelectedIndex()>-1&&L_incomplete.isSelectionEmpty()){
						var.deleteItem(completeID.get(L_complete.getSelectedIndex()));
						L_complete.updateUI();
						update();
					}
				}
			}
		});
		
		B_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update();
			}
		});
		B_toggle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(L_incomplete.getSelectedIndex()>-1&&L_complete.isSelectionEmpty()){
					Thread t=new Thread(){
						public void run(){
							var.swapIncomplete(incompleteID.get(L_incomplete.getSelectedIndex()));
							java.awt.EventQueue.invokeLater(new Runnable() {
							    public void run() {
							    	L_incomplete.clearSelection();
									L_complete.clearSelection();
							    }
							});
							update();
						}
					};
					t.start();
				}
				else if(L_complete.getSelectedIndex()>-1&&L_incomplete.isSelectionEmpty()){
					//Thread to handle toggling completness in order to reduce potential lag
					Thread t=new Thread(){
						public void run(){
							var.swapComplete(completeID.get(L_complete.getSelectedIndex()));
							java.awt.EventQueue.invokeLater(new Runnable() {
							    public void run() {
							    	L_incomplete.clearSelection();
									L_complete.clearSelection();
							    }
							});
							update();
						}
					};
					t.start();
				}
			}
		});
	}
	//filter used to determine which type of item to display
	public void setFilter(ItemType filter){
		this.filter.clear();
		this.filter.add(filter);
	}
	public void setFilter(ArrayList<ItemType> filter){
		this.filter=filter;
	}
	public void noFilter(){
		for(int i=0;i<ItemType.values().length;i++){
			filter.add(ItemType.values()[i]);
		}
	}
	public void setValues(boolean complete){
		if(complete){
			if(L_complete.getSelectedIndex()>-1){
				L_incomplete.clearSelection();
				L_incomplete.updateUI();
				TODOItem TODO=var.getTODOItem(completeID.get(L_complete.getSelectedIndex()));
				TA_description.setText(TODO.getDescription());
				B_toggle.setText("<<incomplete");
			}
		}
		else{
			if(L_incomplete.getSelectedIndex()>-1){
				L_complete.clearSelection();
				L_complete.updateUI();
				TODOItem TODO=var.getTODOItem(incompleteID.get(L_incomplete.getSelectedIndex()));
				TA_description.setText(TODO.getDescription());
				B_toggle.setText("complete>>");
			}
		}
	}
	public void run(){
		var.reload();
		reset();
	}
	public void update(){
		//thread to wrap update so that GUI won't be lagged by the reading of the save.
		Thread t=new Thread(this);
		t.setDaemon(true);
		t.start();
	}
	public void reset(){
		var.updateIncomplete();
		var.updateComplete();
		updateIncomplete();
		updateComplete();
		java.awt.EventQueue.invokeLater(new Runnable() {
		    public void run() {
		    	TA_description.setText("");
		    }
		});
	}
	//update the lists displaying complete/incomplete tasks
	private void updateIncomplete(){
		incomplete.clear();
		incompleteID.clear();
		for(int i=10;i>0;i--){
			for(int c=0;c<var.getIncomplete().size();c++){
				TODOItem add=var.getIncomplete().get(c);
				if(add.getPriority()==i&&filteredType(add.getType())){
					incomplete.add(add.getName());
					incompleteID.add(add.getID());
				}
			}
		}
		java.awt.EventQueue.invokeLater(new Runnable() {
		    public void run() {
		    	L_incomplete.clearSelection();
				L_incomplete.updateUI();
		    }
		});
	}
	
	private void updateComplete(){
		complete.clear();
		completeID.clear();
		for(int i=10;i>0;i--){
			for(int c=0;c<var.getComplete().size();c++){
				TODOItem add=var.getComplete().get(c);
				if(add.getPriority()==i&&filteredType(add.getType())){
					complete.add(add.getName());
					completeID.add(add.getID());
				}
			}
		}
		java.awt.EventQueue.invokeLater(new Runnable() {
		    public void run() {
		    	L_complete.clearSelection();
				L_complete.updateUI();
		    }
		});
	}
	//if item type is contained in filter
	private boolean filteredType(ItemType type){
		for(ItemType i:filter){
			if(i==type){
				return true;
			}
		}
		return false;
	}

}


