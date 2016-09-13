package monitor;

import javax.swing.JMenuBar;

import global.menus.EditorMenu;
import global.menus.FileMenu;
import global.menus.ViewMenu;

public class MonMainMenu extends JMenuBar {
	
	private FileMenu fileMenu;
	private EditorMenu editMenu;
	private ViewMenu viewMenu;
	/**
	 * 
	 */
	private static final long serialVersionUID = 8807475430075760375L;

	public MonMainMenu(MonMain main,final MonVar var){
		fileMenu=new FileMenu(var,FileMenu.MONITOR);
		add(fileMenu);
		
		editMenu=new EditorMenu(var);
		add(editMenu);
		
		viewMenu=new ViewMenu(var);
		add(viewMenu);
		
	}
	public JMenuBar getMonMainMenu(){
		return this;
	}

}
