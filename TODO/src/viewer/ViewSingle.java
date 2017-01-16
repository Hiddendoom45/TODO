package viewer;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import global.ItemType;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JList;

import editor.EditMain;
import java.awt.Dimension;
import javax.swing.JTextField;

/**
 * view single item
 * @author Allen
 *
 */
public class ViewSingle extends ViewGenerics {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1274022657239632966L;
	private JList<String> L_incomplete;
	private JList<String> L_complete;
	private JButton B_toggle;
	private JButton B_edit;
	private JButton B_delete;
	private JButton B_update;
	private JTextArea TA_description;
	
	private ArrayList<String> complete=new ArrayList<String>();//holds name
	private ArrayList<String> incomplete=new ArrayList<String>();//ditto
	private ArrayList<Long> completeID=new ArrayList<Long>();//keep id for purposes of editing/removing item in variable area
	private ArrayList<Long> incompleteID=new ArrayList<Long>();//ditto
	private JLabel lblPriority;
	private JTextField TF_priority;
	
	/**
	 * Create the panel.
	 */
	public ViewSingle(final ViewVar var) {
		this.var=var;
		super.var=var;
		super.incompleteID=incompleteID;
		super.completeID=completeID;
		super.incomplete=incomplete;
		super.complete=complete;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblIncomplete = new JLabel("incomplete");
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
		gbc_SP_incomplete.weightx = 1.0;
		gbc_SP_incomplete.fill = GridBagConstraints.BOTH;
		gbc_SP_incomplete.gridheight = 5;
		gbc_SP_incomplete.insets = new Insets(0, 0, 5, 5);
		gbc_SP_incomplete.gridx = 0;
		gbc_SP_incomplete.gridy = 1;
		add(SP_incomplete, gbc_SP_incomplete);
		
		L_incomplete = new JList<String>();
		SP_incomplete.setViewportView(L_incomplete);
		
		B_toggle = new JButton("complete>>");
		GridBagConstraints gbc_B_toggle = new GridBagConstraints();
		gbc_B_toggle.gridwidth = 2;
		gbc_B_toggle.insets = new Insets(0, 0, 5, 5);
		gbc_B_toggle.gridx = 1;
		gbc_B_toggle.gridy = 1;
		add(B_toggle, gbc_B_toggle);
		
		JScrollPane SP_complete = new JScrollPane();
		SP_complete.setPreferredSize(new Dimension(0, 0));
		GridBagConstraints gbc_SP_complete = new GridBagConstraints();
		gbc_SP_complete.weightx = 1.0;
		gbc_SP_complete.fill = GridBagConstraints.BOTH;
		gbc_SP_complete.gridheight = 5;
		gbc_SP_complete.insets = new Insets(0, 0, 5, 0);
		gbc_SP_complete.gridx = 3;
		gbc_SP_complete.gridy = 1;
		add(SP_complete, gbc_SP_complete);
		
		L_complete = new JList<String>();
		SP_complete.setViewportView(L_complete);
		
		B_edit = new JButton("Edit");
		GridBagConstraints gbc_B_edit = new GridBagConstraints();
		gbc_B_edit.weightx = 1.0;
		gbc_B_edit.insets = new Insets(0, 0, 5, 5);
		gbc_B_edit.gridx = 1;
		gbc_B_edit.gridy = 2;
		add(B_edit, gbc_B_edit);
		
		B_delete = new JButton("Delete");
		GridBagConstraints gbc_B_delete = new GridBagConstraints();
		gbc_B_delete.weightx = 1.0;
		gbc_B_delete.insets = new Insets(0, 0, 5, 5);
		gbc_B_delete.gridx = 2;
		gbc_B_delete.gridy = 2;
		add(B_delete, gbc_B_delete);
		
		B_update = new JButton("Update");
		GridBagConstraints gbc_B_update = new GridBagConstraints();
		gbc_B_update.gridwidth = 2;
		gbc_B_update.insets = new Insets(0, 0, 5, 5);
		gbc_B_update.gridx = 1;
		gbc_B_update.gridy = 3;
		add(B_update, gbc_B_update);
		
		lblPriority = new JLabel("Priority");
		GridBagConstraints gbc_lblPriority = new GridBagConstraints();
		gbc_lblPriority.weightx = 1.0;
		gbc_lblPriority.anchor = GridBagConstraints.EAST;
		gbc_lblPriority.insets = new Insets(0, 0, 5, 5);
		gbc_lblPriority.gridx = 1;
		gbc_lblPriority.gridy = 4;
		add(lblPriority, gbc_lblPriority);
		
		TF_priority = new JTextField();
		GridBagConstraints gbc_TF_priority = new GridBagConstraints();
		gbc_TF_priority.insets = new Insets(0, 0, 5, 5);
		gbc_TF_priority.fill = GridBagConstraints.BOTH;
		gbc_TF_priority.gridx = 2;
		gbc_TF_priority.gridy = 4;
		add(TF_priority, gbc_TF_priority);
		TF_priority.setColumns(10);
		
		JLabel lblDescription = new JLabel("Description");
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.gridwidth = 2;
		gbc_lblDescription.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescription.gridx = 1;
		gbc_lblDescription.gridy = 5;
		add(lblDescription, gbc_lblDescription);
		
		JScrollPane B_description = new JScrollPane();
		GridBagConstraints gbc_B_description = new GridBagConstraints();
		gbc_B_description.gridwidth = 4;
		gbc_B_description.fill = GridBagConstraints.BOTH;
		gbc_B_description.gridx = 0;
		gbc_B_description.gridy = 6;
		add(B_description, gbc_B_description);
		
		TA_description = new JTextArea();
		B_description.setViewportView(TA_description);
		
		
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
					edit.setSelected(1,completeID.get(L_complete.getSelectedIndex()));
				}
				else if(L_incomplete.getSelectedIndex()>-1){
					edit.setSelected(1,incompleteID.get(L_incomplete.getSelectedIndex()));
				}
				else{
					edit.setSelected(1, 0);
				}
				edit.getVar().setView(var.getView());
			}
		});
		
		super.L_incomplete=L_incomplete;
		super.L_complete=L_complete;
		super.B_delete=B_delete;
		super.B_update=B_update;
		super.B_toggle=B_toggle;
		super.TA_description=TA_description;
		super.setFilter(ItemType.SingleItem);
		super.setListeners();
	}
	public void reset(){
		super.reset();
		TF_priority.setText("0");
	}
	public void setValues(boolean complete){
		super.setValues(complete);
		if(complete){
			if(L_complete.getSelectedIndex()>-1){
				TF_priority.setText(var.getSingleItem(completeID.get(L_complete.getSelectedIndex())).getPriority()+"");
			}
		}
		else{
			if(L_incomplete.getSelectedIndex()>-1){
				TF_priority.setText(var.getSingleItem(incompleteID.get(L_incomplete.getSelectedIndex())).getPriority()+"");
			}
		}
	}
	public JPanel getP_Single(){
		return this;
	}
	
}
