package global.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import global.Vars;
import viewer.ViewMain;
/**
 * menu for viewer
 * @author Allen
 *
 */
public class ViewMenu extends JMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6263737024828510184L;

	public ViewMenu(final Vars var) {
		super("Viewer");
		
		JMenuItem MI_view=new JMenuItem("Launch Viewer");
		add(MI_view);
		
		MI_view.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ViewMain view;
					if(var.getView()==null||!var.getView().isShowing()){//if viwer exists, if not create new
						view=new ViewMain(var.getConsole(),var.getItemData());
						var.setView(view);
					}
					else{//if exists, focus on it
						view=var.getView();
						 
					}
				view.setVisible(true);
			}
		});
	}
	public JMenu getMenu(){
		return this;
	}

}
