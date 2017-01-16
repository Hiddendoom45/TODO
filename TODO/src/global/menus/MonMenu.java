package global.menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import global.Vars;
import monitor.MonMain;
/**
 * menu for monitor
 * @author Allen
 *
 */
public class MonMenu extends JMenu {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1397617944911052420L;

	public MonMenu(final Vars var) {
		super("Monitor");
		//launch, that's it
		JMenuItem MI_monitor=new JMenuItem("Launch Monitor");
		add(MI_monitor);
		
		MI_monitor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				MonMain mon;
				if(var.getMon()==null||!var.getMon().getFrame().isShowing()){//if one doesn't exist create new
					mon=new MonMain(var.getConsole(),var.getItemData());
					var.setMon(mon);
				}
				else{//if does focus on it
					mon=var.getMon();
				}
			}
		});
	}

}
