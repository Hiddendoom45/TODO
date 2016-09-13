package editor;

import javax.swing.JMenuBar;

import global.menus.FileMenu;
import global.menus.MonMenu;
import global.menus.ViewMenu;


public class EditMainMenu extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8476683103298220434L;
	private FileMenu fileMenu;
	private ViewMenu viewMenu;
	private MonMenu monMenu;
	public EditMainMenu(final EditMain main, final EditVar var ) {
		
		fileMenu=new FileMenu(var, FileMenu.EDITOR);
		add(fileMenu);
		
		viewMenu=new ViewMenu(var);
		add(viewMenu);
		
		monMenu=new MonMenu(var);
		add(monMenu);
		
		
	}
	
	public JMenuBar getEditMenuBar(){
		return this;
	}

}
