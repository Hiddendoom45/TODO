package editor.taskEditor;
import javax.swing.JPanel;

import global.Task;
import global.tasks.BrowserTask;

import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
/**
 * task that is to launch browser to specific web page when executed
 * @author Allen
 *
 */
public class EditBrowserTask extends JPanel implements EditTaskGenerics {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1020668274827875667L;
	//variables, just the url
	private JTextField TF_url;
	private String displayText="";

	/**
	 * Create the panel.
	 */
	public EditBrowserTask() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		
		JLabel lblUrl = new JLabel("URL");
		GridBagConstraints gbc_lblUrl = new GridBagConstraints();
		gbc_lblUrl.anchor = GridBagConstraints.WEST;
		gbc_lblUrl.insets = new Insets(0, 0, 5, 5);
		gbc_lblUrl.gridx = 0;
		gbc_lblUrl.gridy = 0;
		add(lblUrl, gbc_lblUrl);
		
		TF_url = new JTextField();
		TF_url.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				TF_url.setToolTipText(TF_url.getText());
				try{
					@SuppressWarnings("unused")
					URL url=new URL(TF_url.getText());
					displayText="";
				}catch(MalformedURLException e1){
					displayText="Invalid URL";
				}
				finally{
					updateUI();
				}
			}
		});
		GridBagConstraints gbc_TF_url = new GridBagConstraints();
		gbc_TF_url.insets = new Insets(0, 0, 5, 0);
		gbc_TF_url.weightx = 1.0;
		gbc_TF_url.fill = GridBagConstraints.HORIZONTAL;
		gbc_TF_url.anchor = GridBagConstraints.NORTH;
		gbc_TF_url.gridx = 1;
		gbc_TF_url.gridy = 0;
		add(TF_url, gbc_TF_url);
		TF_url.setColumns(10);
		
	}
	//draw string whether url is valid or not, centers text
	 public void paintComponent(Graphics g) {
	        super.paintComponent(g);      
	        g.drawString(displayText, (this.getWidth()/2)-(displayText.length()*2), 28);
	    }  
	@Override
	public Task grabTask() {
		BrowserTask task=new BrowserTask();
		task.setURL(TF_url.getText());
		return task;
		
	}
	//set values to the values of the task provided
	public void setValues(BrowserTask task){
		try{
			TF_url.setText(task.getURL().toString());
		}catch(Exception e){};
	}
	//to allow eclipse to render panel properly
	@Override
	public JPanel getPanel() {
		return this;
	}

}
