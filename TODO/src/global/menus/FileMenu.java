package global.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import editor.EditMain;
import global.Settings;
import global.Vars;
import monitor.MonMain;
import viewer.ViewMain;

public class FileMenu extends JMenu {
	public static int EDITOR=0;
	public static int VIEWER=1;
	public static int MONITOR=2;
	/**
	 * 
	 */
	private static final long serialVersionUID = 6118464664780791544L;
	public FileMenu(final Vars var, final int type) {
		super("File");
		
		JMenuItem MI_open=new JMenuItem("Open");
		add(MI_open);
		
		JMenuItem MI_save=new JMenuItem("Save");
		add(MI_save);
		
		JMenuItem MI_console=new JMenuItem("Console");
		add(MI_console);
		
		JMenuItem MI_reload=new JMenuItem("Reload");
		add(MI_reload);
		
		MI_open.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				var.read(new File(Settings.fileSource));
			}
		});
		MI_save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				var.write(new File(Settings.fileSource));
			}
		});
		MI_console.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				 var.getConsole().setVisible(true);
			}
		});
		MI_reload.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				switch(type){
				case 0:
					var.getEdit().setVisible(false);
					var.getEdit().dispose();
					EditMain edit=new EditMain(var.getConsole(),var.getItemData());
					var.setEdit(edit);
					edit.setVisible(true);
					break;
				case 1:
					var.getView().dispose();
					ViewMain view=new ViewMain(var.getConsole(),var.getItemData());
					var.setView(view);
					view.setVisible(true);
					break;
				case 2:
					var.getMon().getFrame().dispose();
					MonMain mon=new MonMain(var.getConsole(),var.getItemData());
					var.setMon(mon);
					break;
				}
				
			}
		});
	}
	public JMenu getMenu(){
		return this;
	}

}
