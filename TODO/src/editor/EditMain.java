package editor;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import global.Console;
import global.ItemData;
import global.Settings;

import java.awt.GridBagLayout;
import java.io.File;

import javax.swing.JTabbedPane;
import java.awt.GridBagConstraints;
/**
 * primary task of this portion is to create new items in the TODO list, generally all items used by viewer, and may be imiplemented in monitor
 * Also deletion of TODO items which can also be done in viewer, though not created, viewer format optimized to view the different TODO tasks available to do
 * @author Allen
 *
 */
public class EditMain extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4055908655102159762L;
	private JTabbedPane TP_Main;
	private JPanel contentPane;
	private JPanel P_Daily;
	private JPanel P_Timed;
	private JPanel P_Single;
	private JPanel P_Energy;
	private JPanel P_Periodic;
	private EditVar var;
	private EditDaily daily;
	private EditSingle single;
	private EditTimed timed;
	private EditEnergy energy;
	private EditPeriodic periodic;
	private Console con;

	//sets menu passes on this class containing everything to it
	private EditMainMenu menu;
	private JMenuBar menuBar;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditMain frame = new EditMain(new Console(),null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public EditMain(){

	}
	/**
	 * Create the frame.
	 */
	public EditMain(Console con,ItemData data) {
		this.con=con;
		if(data==null){
			var=new EditVar(con);
		}
		else var=new EditVar(con,data);
		Thread t=new Thread(){
			public void run(){
				var.read(new File(Settings.fileSource));
			}
		};
		t.start();
		var.setEdit(this);
		
		daily=new EditDaily(var,con);
		single=new EditSingle(var,con);
		timed=new EditTimed(var,con);
		energy=new EditEnergy(var,con);
		periodic=new EditPeriodic(var,con);
		
		menu = new EditMainMenu(this,var);
		menuBar= menu.getEditMenuBar();
		setJMenuBar(menuBar);
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 625, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		TP_Main = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_TP_Main = new GridBagConstraints();
		gbc_TP_Main.fill = GridBagConstraints.BOTH;
		gbc_TP_Main.gridx = 0;
		gbc_TP_Main.gridy = 0;
		contentPane.add(TP_Main, gbc_TP_Main);
		
		P_Daily = daily.getP_Daily();
		TP_Main.addTab("Dailies", null, P_Daily, null);
		
		P_Single=single.getP_Single();
		TP_Main.addTab("Single", null,P_Single,null);
		
		P_Timed = timed.getP_Timed();
		TP_Main.addTab("Timed", null,P_Timed,null);
		
		P_Energy = energy.getP_energy();
		TP_Main.addTab("Energy", null,P_Energy,null);
		
		P_Periodic= periodic.getP_periodic();
		TP_Main.addTab("Periodic", null,P_Periodic,null);
		
		reset();
		
	}
	public void reset(){
		daily.reset();
		single.reset();
		timed.reset();
		energy.reset();
		periodic.reset();
		con.addMsg("[Main] all values reset/refreshed");
	}
	public void setSelected(int tabNum,long ID){
		TP_Main.setSelectedIndex(tabNum);
		switch(tabNum){
		case 0:daily.setItemIndex(ID); break;
		case 1:single.setItemIndex(ID); break;
		case 2:timed.setItemIndex(ID); break;
		case 3:energy.setItemIndex(ID); break;
		case 4:periodic.setItemIndex(ID); break;
		case 5:energy.setItemIndex(ID); break;
		}
	}
	public EditVar getVar(){
		return var;
	}
}
