package global;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import java.awt.FlowLayout;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;

import javax.swing.JToggleButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Console extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4188527558908146546L;
	private JPanel contentPane;
	private JFrame console=null;
	private String consoleText="";
	private String consoleAppend="";
	private JTextArea TA_console;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Console frame = new Console();
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
	public Console() {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		
		console=this;
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 700, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JScrollPane SP_console = new JScrollPane();
		contentPane.add(SP_console, BorderLayout.CENTER);
		
		TA_console = new JTextArea();
		TA_console.setWrapStyleWord(true);
		SP_console.setViewportView(TA_console);
		
		JPanel P_options = new JPanel();
		contentPane.add(P_options, BorderLayout.SOUTH);
		P_options.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton B_clear = new JButton("Clear");
		P_options.add(B_clear);
		
		final JButton B_save = new JButton("Save");
		P_options.add(B_save);
		
		final JToggleButton TB_wrap = new JToggleButton("Text Wrapping");
		P_options.add(TB_wrap);
		
		JButton B_close = new JButton("Close");
		P_options.add(B_close);
		
		//action listeners after everything
		
		B_clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TA_console.setText("");
			}
		});
		
		B_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc=new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnVal=fc.showSaveDialog(B_save);
				if(returnVal==JFileChooser.APPROVE_OPTION){
					String location=""+fc.getSelectedFile();
					if(!location.endsWith(".txt")){
						location=location+".txt";
						saveConsole(location);
					}
				}
				
			}
		});
		
		
		TB_wrap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TA_console.setLineWrap(TB_wrap.isSelected());
			}
		});
		
		B_close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				console.setVisible(false);
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowDeactivated(WindowEvent e) {
				saveConsole("");
			}
		});
		Runnable save=new Runnable(){
			public void run(){
				saveConsole("");
				System.gc();
			}
		};
		Runnable update=new Runnable(){
			public void run(){
				synchronized (this){
					java.awt.EventQueue.invokeLater(new Runnable() {
						public void run() {
							TA_console.append(consoleAppend);
							TA_console.updateUI();
							consoleAppend="";
							System.out.println("console ran"+new Date().toString());
						}
					} );
				}
			}
		};
		executor.scheduleAtFixedRate(save, 5, 2, TimeUnit.MINUTES);//loop auto save every 2 minute or so
		executor.scheduleAtFixedRate(update, 5, 10, TimeUnit.SECONDS);//loop update GUI every 10 sec
	}
	private synchronized void addString(final String msg){
		final Date date= new Date();
		final String dateString=DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(date);
		final String append="["+dateString+"]"+msg;
		consoleText+=append;//saves to text that will be later saved in file form
		consoleAppend+=append;//adds to buffer of text to append to the console, reduces lag when a lot of text needs to be appended
		
	}
	public void addError(final String msg){
		addString("[ERR]"+msg+"\n");
	}
	public void addMsg(final String msg){
		addString("[STATUS]"+msg+"\n");
	}
	public void addInfo(final String msg){
		addString("[INFO]"+msg+"\n");
	}
	public void addInfoSet(final String source, final String variable, final String value){
		addString("[INFO]["+source+"]"+variable+" set to "+value+"\n");
	}
	public void addWarn(final String msg){
		addString("[WARNING]"+msg+"\n");
	}
	public void addWarnErrRead(String source, final String variable,final String value){
		addString("[WARNING] error reading "+variable+", set to "+value);
	}
	public void addSeperator(final String msg){
		addString("****-----****"+msg+"****-----****\n");
	}
	public void saveConsole(String location){
		File file;
		if(location.equals("")){		
			file=new File("console.txt");
		}
		else{
			file=new File(location);
		}
		addInfo("[Console]Saving console as"+file);
		int tries=0;
		while(tries<4){
			try{
				BufferedWriter out=new BufferedWriter(new FileWriter(file));
				out.write(consoleText);
				out.close();
				break;
			}catch(IOException e){
				System.out.println("Saving failed");
				tries++;
			}
		}
	}

}
