package editor;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import editor.taskEditor.EditBrowserTask;
import editor.taskEditor.EditNotifyTask;
import editor.taskEditor.EditWorkflowTask;
import editor.taskEditor.TaskSet;
import global.AdditionalTaskSetting;
import global.Task;
import global.TaskType;
import global.tasks.BrowserTask;
import global.tasks.DefaultAbstractTask;
import global.tasks.NotifyTask;
import global.tasks.WorkflowTask;

public class EditTask extends JDialog {
	/*TODO when adding tasks methods to edit
	 * setTasks()
	 * bit at the beginning of constructor
	 * adding panel to card
	 */
	/**
	 * 
	 */
	private static final long serialVersionUID = -608790542445556203L;
	private final JPanel P_tasks = new JPanel();
	private JPanel P_notify;
	private JPanel P_browser;
	private JPanel P_workflow;
	private boolean monitor=true;
	private boolean hide=false;
	private Task task;
	private CardLayout card;
	private TaskType currentType=TaskType.Default;
	private AdditionalTaskSetting taskSetting;
	private TaskSet settings;
	
	
	private EditNotifyTask notify;
	private EditBrowserTask browser;
	private EditWorkflowTask workflow;
	private JComboBox<TaskType> CB_TaskTypes;

	/**
	 * Create the dialog.
	 */
	public EditTask(JFrame parent,Task task,boolean monitor, boolean hide) {
		super(parent,true);
		this.monitor=monitor;
		this.hide=hide;
		//Initialize the planes before setting the values
		notify=new EditNotifyTask();
		browser=new EditBrowserTask();
		workflow=new EditWorkflowTask();
		P_notify=notify.getPanel();
		P_browser=browser.getPanel();
		P_workflow=workflow.getPanel();
		
		//set main GUI stuffs
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 598, 360);
		getContentPane().setLayout(new BorderLayout());
		P_tasks.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(P_tasks, BorderLayout.CENTER);
		card=new CardLayout(0, 0);
		P_tasks.setLayout(card);
		JPanel P_default = new JPanel();
		P_tasks.add(P_default, ""+TaskType.Default);
		P_tasks.add(P_notify,""+TaskType.Apple_Notification);
		P_tasks.add(P_browser,""+TaskType.Browser_Launch);
		P_tasks.add(P_workflow,""+TaskType.Start_Workflow);
		JLabel lblDefaultDoesNothing = new JLabel("Default does nothing when task is run.");
		P_default.add(lblDefaultDoesNothing);
		
		JPanel P_buttons = new JPanel();
		P_buttons.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(P_buttons, BorderLayout.SOUTH);
		
		CB_TaskTypes = new JComboBox<TaskType>();
		CB_TaskTypes.setModel(new DefaultComboBoxModel<TaskType>(TaskType.values()));
		CB_TaskTypes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TaskType selected=(TaskType)CB_TaskTypes.getSelectedItem();
				currentType=selected;
				setPlane();
			}
		});
		
		JButton B_settings = new JButton("Task Settings");
		P_buttons.add(B_settings);
		P_buttons.add(CB_TaskTypes);
		JButton B_create = new JButton("Create Task");
		B_create.setActionCommand("Create");
		P_buttons.add(B_create);
		getRootPane().setDefaultButton(B_create);
		JButton B_cancel = new JButton("Cancel");
		B_cancel.setActionCommand("Cancel");
		P_buttons.add(B_cancel);
		ActionListener closing=new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(e.getActionCommand().equals("Create")){
					//TODOadd switch to set task to the one currently selected and created
					setTask();
					close(false);
				}
				else{
					close(true);
				}
			}
		};
		B_create.addActionListener(closing);
		B_cancel.addActionListener(closing);
		B_settings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setAdditionalSettings();
			}
		});
		if(!(task==null)){
			setTask(task);
		}
		
	}
	
	public void close(boolean cancel){
		if(cancel){
			task=null;
		}
		dispose();
	}
	private void setAdditionalSettings(){
		settings=new TaskSet(this);
		settings.setValues(monitor, hide,task.getTimeout(),task.getName(),task.getCompleteOnFinish());
		settings.setVisible(true);
		if(settings.getSelection()>=1){
			taskSetting=settings.getValues();
			monitor=taskSetting.isMonitored();
			hide=taskSetting.isHidden();
			task.setAdditionalTaskSettings(taskSetting);
		}
	}
	/**
	 * set plane for task
	 * @param plane
	 */
	private void setPlane(){
		card.show(P_tasks, ""+currentType);
		setTask();
	}
	/**
	 * setting the task variable for closing
	 */
	private void setTask(){
		System.out.println("setting"+task+"\n"+new Date()+"\n");
		switch(currentType){
		case Apple_Notification:task=notify.grabTask();
			break;
		case Browser_Launch:task=browser.grabTask();
			break;
		case Default:task=new DefaultAbstractTask();
			break;
		case Manga_Download:
			break;
		case Page_Download:
			break;
		case Start_Workflow:task=workflow.grabTask();
			break;
		default:
			break;
		}
		if(!(taskSetting==null)){
			task.setAdditionalTaskSettings(taskSetting);
			System.out.println("task setting");
		}
		System.out.println("setting"+task+"\n"+new Date()+"\n");
	}
	/**
	 * setting task based on existing task
	 * @param task
	 */
	private void setTask(Task task){
		int selection=0;
		if(task==null){
			task=new DefaultAbstractTask();
			System.out.println("task set as null");
		}
		switch(task.getType()){
		case Apple_Notification:
			this.task=task;
			notify.setValues((NotifyTask)task);
			currentType=TaskType.Apple_Notification;
			selection=1;
			break;
		case Browser_Launch:
			this.task=task;
			browser.setValues((BrowserTask)task);
			currentType=TaskType.Browser_Launch;
			selection=2;
			break;
		case Default:this.task=new DefaultAbstractTask();
		break;
		case Manga_Download:
			currentType=TaskType.Manga_Download;
			selection=3;
			break;
		case Page_Download:
			currentType=TaskType.Page_Download;
			selection=4;
			break;
		case Start_Workflow:
			this.task=task;
			workflow.setValues((WorkflowTask)task);
			currentType=TaskType.Start_Workflow;
			selection=5;
			break;
		case Anime_Download:
			currentType=TaskType.Anime_Download;
			selection=6;
			break;
		default:
			break;
		}
		taskSetting=task.getAdditionalTasksetting();
		CB_TaskTypes.setSelectedIndex(selection);

	}
	public Task getTask(){
		return task;
	}
	public boolean getHide(){
		return hide;
	}
	public boolean getMonitored(){
		return monitor;
	}

}
