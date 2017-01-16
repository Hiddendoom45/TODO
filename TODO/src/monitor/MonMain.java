package monitor;

import java.awt.EventQueue;
import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JMenuBar;

import global.Console;
import global.ItemData;
import global.Settings;



/**
 * main class for the monitor
 * @author Allen
 *
 */
public class MonMain implements Runnable {
	
	private MonMain main;
	private MonFrame mainFrame;
	private MonVar var;
	
	private MonMainMenu menu;
	private JMenuBar menuBar;
	
	public MonMain(final Console con,ItemData data) {
		main=this;
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		if(data==null){
		var=new MonVar(con);
		}
		else var=new MonVar(con,data);
		
		//setting up GUI stuff while thread reads
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				mainFrame=new MonFrame(var,con);
				mainFrame.setVisible(true);
				menu=new MonMainMenu(main, var);
				menuBar=menu.getMonMainMenu();
				mainFrame.setJMenuBar(menuBar);
			}
		});
		Runnable read=new Runnable(){
			public void run(){
				var.read(new File(Settings.fileSource));
				con.addInfo("[Main] read finished");
				//update GUI once thread has done reading
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						var.update();
						mainFrame.reset();
						var.startTasks();
					}
				});
			}
		};
		//read while GUI stuff is set up
		executor.execute(read);
		
		//thread to update GUI a fixed intervals(10min,first after 5min)
		executor.scheduleAtFixedRate(this, 5, 10, TimeUnit.MINUTES);
	}
	public MonFrame getFrame(){
		return mainFrame;
	}
	public void run(){
		var.reload();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				mainFrame.reset();
			}
		});
	}

}
