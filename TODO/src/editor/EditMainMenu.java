package editor;

import javax.swing.JMenuBar;

import global.menus.FileMenu;
import global.menus.MonMenu;
import global.menus.ViewMenu;

//menu bar for editor, mostly same between the various onees
public class EditMainMenu extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8476683103298220434L;
	private FileMenu fileMenu;
	private ViewMenu viewMenu;
	private MonMenu monMenu;
	public EditMainMenu(final EditMain main, final EditVar var ) {
		//main file menu
		fileMenu=new FileMenu(var, FileMenu.EDITOR);
		add(fileMenu);
		//menu to do stuff with the viewer
		viewMenu=new ViewMenu(var);
		add(viewMenu);
		//likewise for the monitor part
		monMenu=new MonMenu(var);
		add(monMenu);
		
		
	}
	//to allow eclipse to render things properly
	public JMenuBar getEditMenuBar(){
		return this;
	}

}
