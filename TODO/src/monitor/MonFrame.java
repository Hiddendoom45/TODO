package monitor;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import global.Console;
import javax.swing.JTabbedPane;

public class MonFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -243434235016424051L;
	private JPanel contentPane;
	private JPanel P_overview;
	
	private MonOverview overview;

	/**
	 * Create the frame.
	 */
	public MonFrame(MonVar var,Console con) {
		overview=new MonOverview(var);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane TP_main = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(TP_main, BorderLayout.CENTER);
		
		
		P_overview=overview.getP_overview();
		TP_main.addTab("Overview", P_overview);
		
	}
	public void reset(){
		overview.reset();
	}

}
