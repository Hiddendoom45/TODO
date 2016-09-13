package global;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import editor.EditMain;
import monitor.MonMain;
import viewer.ViewMain;

import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TODOLaunch extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2894970804939725488L;
	private JPanel contentPane;
	private JFrame launch=null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TODOLaunch frame = new TODOLaunch();
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
	public TODOLaunch() {
		launch=this;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 481, 203);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		final JCheckBox CB_console = new JCheckBox("Console");
		GridBagConstraints gbc_CB_console = new GridBagConstraints();
		gbc_CB_console.weighty = 2.0;
		gbc_CB_console.weightx = 1.0;
		gbc_CB_console.insets = new Insets(0, 0, 5, 5);
		gbc_CB_console.gridx = 1;
		gbc_CB_console.gridy = 1;
		contentPane.add(CB_console, gbc_CB_console);
		
		JButton B_Monitor = new JButton("Monitor TODO");
		GridBagConstraints gbc_B_Monitor = new GridBagConstraints();
		gbc_B_Monitor.anchor = GridBagConstraints.SOUTH;
		gbc_B_Monitor.weighty = 1.0;
		gbc_B_Monitor.weightx = 1.0;
		gbc_B_Monitor.insets = new Insets(0, 0, 0, 5);
		gbc_B_Monitor.gridx = 0;
		gbc_B_Monitor.gridy = 2;
		contentPane.add(B_Monitor, gbc_B_Monitor);
		
		JButton B_View = new JButton("View TODO");
		GridBagConstraints gbc_B_View = new GridBagConstraints();
		gbc_B_View.anchor = GridBagConstraints.SOUTH;
		gbc_B_View.weightx = 1.0;
		gbc_B_View.weighty = 1.0;
		gbc_B_View.insets = new Insets(0, 0, 0, 5);
		gbc_B_View.gridx = 1;
		gbc_B_View.gridy = 2;
		contentPane.add(B_View, gbc_B_View);
		
		JButton B_Edit = new JButton("Edit TODO");
		GridBagConstraints gbc_B_Edit = new GridBagConstraints();
		gbc_B_Edit.anchor = GridBagConstraints.SOUTH;
		gbc_B_Edit.weightx = 1.0;
		gbc_B_Edit.weighty = 1.0;
		gbc_B_Edit.gridx = 2;
		gbc_B_Edit.gridy = 2;
		contentPane.add(B_Edit, gbc_B_Edit);
		B_Monitor.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent e) {
				Console con=new Console();
				MonMain mon=new  MonMain(con,null);
				if(CB_console.isSelected()){
					con.setVisible(true);
				}
				launch.dispose();
			}
		});
		
		B_Edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Console con=new Console();
				EditMain edit=new EditMain(con,null);
				if(CB_console.isSelected()){
					con.setVisible(true);
				}
				edit.setVisible(true);
				launch.dispose();
			}
		});
		
		B_View.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				Console con=new Console();
				ViewMain view=new ViewMain(con,null);
				if(CB_console.isSelected()){
					con.setVisible(true);
				}
				view.setVisible(true);
				launch.dispose();
			}
		
		});
		//read custom settings
		Settings.setSettings();
		
	}

}
