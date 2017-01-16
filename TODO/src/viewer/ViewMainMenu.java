package viewer;



import javax.swing.JMenuBar;

import global.menus.EditorMenu;
import global.menus.FileMenu;
import global.menus.MonMenu;
/**
 * menu for viewer
 * @author Allen
 *
 */
public class ViewMainMenu extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6160772848602680523L;
	private EditorMenu editMenu;
	private FileMenu fileMenu;
	private MonMenu monMenu;
	
	public ViewMainMenu(ViewMain main,final ViewVar var) {
		fileMenu=new FileMenu(var, FileMenu.VIEWER);
		add(fileMenu);
		
		editMenu=new EditorMenu(var);
		add(editMenu);
		
		monMenu=new MonMenu(var);
		add(monMenu);
	}
	public JMenuBar getJMenuBar(){
		return this;
	}

}
