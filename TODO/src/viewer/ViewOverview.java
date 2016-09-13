package viewer;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import java.util.ArrayList;

import javax.swing.JList;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import java.awt.Dimension;

public class ViewOverview extends ViewGenerics {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3724995333891670682L;
	private ArrayList<Long> incompleteID=new ArrayList<Long>();//ditto
	private ArrayList<Long> completeID=new ArrayList<Long>();//keep id for purposes of editing/removing item in variable are
	private ArrayList<String> incomplete=new ArrayList<String>();
	private ArrayList<String> complete=new ArrayList<String>();
	
	private JList<String> L_incomplete;
	private JList<String> L_complete; 
	private JButton B_toggle;
	private JButton B_update;
	private JButton B_delete;
	private JTextArea TA_description;
	/**
	 * Create the panel.
	 */
	public ViewOverview(final ViewVar var) {
		super.var=var;
		super.incompleteID=incompleteID;
		super.completeID=completeID;
		super.incomplete=incomplete;
		super.complete=complete;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblIncomplete = new JLabel("Incomplete");
		GridBagConstraints gbc_lblIncomplete = new GridBagConstraints();
		gbc_lblIncomplete.insets = new Insets(0, 0, 5, 5);
		gbc_lblIncomplete.gridx = 0;
		gbc_lblIncomplete.gridy = 0;
		add(lblIncomplete, gbc_lblIncomplete);
		
		JScrollPane SP_incomplete = new JScrollPane();
		SP_incomplete.setPreferredSize(new Dimension(0, 0));
		GridBagConstraints gbc_SP_incomplete = new GridBagConstraints();
		gbc_SP_incomplete.weightx = 3.0;
		gbc_SP_incomplete.gridheight = 3;
		gbc_SP_incomplete.weighty = 2.0;
		gbc_SP_incomplete.fill = GridBagConstraints.BOTH;
		gbc_SP_incomplete.insets = new Insets(0, 0, 5, 5);
		gbc_SP_incomplete.gridx = 0;
		gbc_SP_incomplete.gridy = 1;
		add(SP_incomplete, gbc_SP_incomplete);
		
		L_incomplete = new JList<String>();
		SP_incomplete.setViewportView(L_incomplete);
		
		JScrollPane SP_complete = new JScrollPane();
		SP_complete.setPreferredSize(new Dimension(0, 0));
		GridBagConstraints gbc_SP_complete = new GridBagConstraints();
		gbc_SP_complete.weightx = 3.0;
		gbc_SP_complete.gridheight = 3;
		gbc_SP_complete.weighty = 2.0;
		gbc_SP_complete.fill = GridBagConstraints.BOTH;
		gbc_SP_complete.insets = new Insets(0, 0, 5, 0);
		gbc_SP_complete.gridx = 2;
		gbc_SP_complete.gridy = 1;
		add(SP_complete, gbc_SP_complete);
		
		L_complete = new JList<String>();
		SP_complete.setViewportView(L_complete);
		
		B_delete = new JButton("Delete");
		GridBagConstraints gbc_B_delete = new GridBagConstraints();
		gbc_B_delete.insets = new Insets(0, 0, 5, 5);
		gbc_B_delete.gridx = 1;
		gbc_B_delete.gridy = 2;
		add(B_delete, gbc_B_delete);
		
		B_update = new JButton("Update");
		GridBagConstraints gbc_B_updates = new GridBagConstraints();
		gbc_B_updates.insets = new Insets(0, 0, 5, 5);
		gbc_B_updates.gridx = 1;
		gbc_B_updates.gridy = 3;
		add(B_update, gbc_B_updates);
		
		JLabel lblDescription = new JLabel("Description");
		GridBagConstraints gbc_lblDescription = new GridBagConstraints();
		gbc_lblDescription.insets = new Insets(0, 0, 5, 5);
		gbc_lblDescription.gridx = 1;
		gbc_lblDescription.gridy = 4;
		add(lblDescription, gbc_lblDescription);
		
		JLabel lblComplete = new JLabel("Complete");
		GridBagConstraints gbc_lblComplete = new GridBagConstraints();
		gbc_lblComplete.insets = new Insets(0, 0, 5, 0);
		gbc_lblComplete.gridx = 2;
		gbc_lblComplete.gridy = 0;
		add(lblComplete, gbc_lblComplete);
		
		B_toggle = new JButton("completed>>");		
		GridBagConstraints gbc_B_toggle = new GridBagConstraints();
		gbc_B_toggle.insets = new Insets(0, 0, 5, 5);
		gbc_B_toggle.gridx = 1;
		gbc_B_toggle.gridy = 1;
		add(B_toggle, gbc_B_toggle);
		
		JScrollPane SP_description = new JScrollPane();
		SP_description.setPreferredSize(new Dimension(0, 0));
		GridBagConstraints gbc_SP_description = new GridBagConstraints();
		gbc_SP_description.fill = GridBagConstraints.BOTH;
		gbc_SP_description.gridwidth = 3;
		gbc_SP_description.gridx = 0;
		gbc_SP_description.gridy = 5;
		add(SP_description, gbc_SP_description);
		
		TA_description = new JTextArea();
		TA_description.setEditable(false);
		SP_description.setViewportView(TA_description);
		
		
		
		super.L_incomplete=L_incomplete;
		super.L_complete=L_complete;
		super.B_delete=B_delete;
		super.B_update=B_update;
		super.B_toggle=B_toggle;
		super.TA_description=TA_description;
		super.noFilter();
		super.setListeners();
		

	}
	public JPanel getP_Overview(){
		return this;
	}

}
