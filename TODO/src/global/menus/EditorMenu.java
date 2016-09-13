package global.menus;

 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import editor.EditMain;
import global.Vars;

public class EditorMenu extends JMenu {
	private Vars var;

	/**
	 * 
	 */
	private static final long serialVersionUID = -7196892250663963430L;

	public EditorMenu(Vars var) {
		super("Edit");
		this.var=var;
		
		JMenuItem MI_edit=new JMenuItem("Launch Editor");
		add(MI_edit);
		
		addSeparator();
		
		JMenuItem MI_editDaily=new JMenuItem("Edit Dailies");
		add(MI_editDaily);
		
		JMenuItem MI_editSingle=new JMenuItem("Edit Singles");
		add(MI_editSingle);
		
		JMenuItem MI_editTimed=new JMenuItem("Edit Timed");
		add(MI_editTimed);
		
		JMenuItem MI_editEnergy=new JMenuItem("Edit Energy");
		add(MI_editEnergy);
		
		JMenuItem MI_editPeriodic=new JMenuItem("Edti Periodic");
		add(MI_editPeriodic);
		
		MI_edit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				launchEditor(-1);
			}
		});
		
		MI_editDaily.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				launchEditor(0);
			}
		});
		MI_editSingle.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				launchEditor(1);
			}
		});
		MI_editTimed.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				launchEditor(2);
			}
		});
		MI_editEnergy.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				launchEditor(3);
			}
		});
		MI_editPeriodic.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				launchEditor(4);
			}
		});
	}
	private void launchEditor(int tab){
		EditMain edit;
		if(var.getEdit()==null||!var.getEdit().isShowing()){
			edit=new EditMain(var.getConsole(),var.getItemData());
			var.setEdit(edit);
		}
		else{
			edit=var.getEdit();
		}
		edit.setVisible(true);
		if(tab>=0){
			edit.setSelected(tab, 0);
		}
	}
	
	public JMenu getMenu(){
		return this;
	}

}
