package viewer;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import global.Console;
import global.ItemData;

import java.awt.GridBagLayout;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.awt.GridBagConstraints;
import javax.swing.JTabbedPane;
/**
 * main class for viewer
 * @author Allen
 *
 */
public class ViewMain extends JFrame implements Runnable {
	private ViewVar var;
	/**
	 * 
	 */
	private static final long serialVersionUID = 8603974481159062789L;
	private ViewOverview overview;
	private ViewDaily daily;
	private ViewSingle single;
	private ViewTimed timed;
	private ViewEnergy energy;
	private ViewPeriodic periodic;
	private ViewMainMenu menu;
	
	private JPanel contentPane;
	private JPanel P_Overview;
	private JPanel P_Daily;
	private JPanel P_Single;
	private JPanel P_Timed;
	private JPanel P_Energy;
	private JPanel P_Periodic;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewMain frame = new ViewMain(new Console(),null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ViewMain(Console con,ItemData data) {
		if(data==null){
		var=new ViewVar(con);
		}
		else var=new ViewVar(con,data);
		var.setView(this);
		menu=new ViewMainMenu(this,var);
		
		overview=new ViewOverview(var);
		daily=new ViewDaily(var);
		single=new ViewSingle(var);
		timed=new ViewTimed(var);
		energy=new ViewEnergy(var);
		periodic=new ViewPeriodic(var);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 684, 481);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		contentPane.setLayout(gbl_contentPane);
		
		
		JTabbedPane TP_Main = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_TP_Main = new GridBagConstraints();
		gbc_TP_Main.fill = GridBagConstraints.BOTH;
		gbc_TP_Main.weighty = 1.0;
		gbc_TP_Main.weightx = 1.0;
		gbc_TP_Main.anchor = GridBagConstraints.NORTHWEST;
		gbc_TP_Main.gridx = 0;
		gbc_TP_Main.gridy = 0;
		contentPane.add(TP_Main, gbc_TP_Main);
		
		P_Overview = overview.getP_Overview();
		TP_Main.addTab("Overview", null, P_Overview, null);
		
		P_Daily = daily.getP_Daily();
		TP_Main.addTab("Dailies", null, P_Daily, null);
		
		P_Single= single.getP_Single();
		TP_Main.addTab("Single",null, P_Single,null);
		
		P_Timed=timed.getP_Timed();
		TP_Main.addTab("Timed", null,P_Timed,null);
		
		P_Energy=energy.getP_Energy();
		TP_Main.addTab("Energy", null,P_Energy,null);

		P_Periodic=periodic.getP_Periodic();
		TP_Main.addTab("Periodic", null,P_Periodic,null);
		setJMenuBar(menu.getJMenuBar());

		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		//thread to update GUI a fixed intervals(10min,first after 5min)
		executor.scheduleAtFixedRate(this, 5, 10, TimeUnit.MINUTES);
	}
	public void reset(){
		overview.reset();
		daily.reset();
		single.reset();
		timed.reset();
		energy.reset();
		periodic.reset();
	}
	public ViewVar getVar(){
		return var;
	}
	public void run(){
		var.reload();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				reset();
			}
		});
	}

}
