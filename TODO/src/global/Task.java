package global;

import java.util.Date;

import XML_Tests.Attribute;
import XML_Tests.Elements;
import monitor.monUtil.CallEvent;
import monitor.monUtil.CompleteCallBack;

/**
 * Class used to handle the different methods of notifying user of event, cannot be constructed, made using subclasses
 * contains overall code used to interface with tasks.
 * @author Allen
 *
 */
public abstract class Task implements Runnable{
	protected String name="task";
	protected TaskType type;
	protected long Timeout=600000;//time before task can be run again(if it's not currently running)//10 min default
	protected Date lastRan=new Date(0);//last time task was run, determines how long until task can be run again
	protected long ID;//ID of the item the task is associated with, no unique checks as could be possible that single item has multiple tasks
	private boolean completeOnFinish=false;//whether task will result in item as finished when the finisher in this class is called
	
	protected CallEvent callback=new CallEvent();
	
	protected Task(TaskType type){
		this.type=type;
	}
	protected Task(Elements root,Console con,Long ID,TaskType type){
		this.type=type;
		this.ID=ID;
		con.addSeperator("Basic Task generation started");
		try{
			name=root.getAttribute("name").getValue();
			con.addInfoSet("Task", "Name", name);
		}catch(Exception e){
			con.addWarnErrRead("Task", "Name", name);
		}
		try{
			Elements info=root.getChilds("info").get(0);
			try{
				Elements timeout=info.getChilds("timeout").get(0);
				try{
					Timeout=Long.parseLong(timeout.getAttribute("timeout").getValue());
					con.addInfoSet("Task", "Timeout", ""+Timeout);
				}catch(Exception e){
					con.addWarnErrRead("Task", "Timeout", ""+Timeout);
				}
				try{
					lastRan=new Date(Long.parseLong(timeout.getAttribute("lastRan").getValue()));
					con.addInfoSet("Task", "Last Ran", ""+lastRan);
				}catch(Exception e){
					con.addWarnErrRead("Task", "Last Ran", "never");
				}
			}catch(Exception e){
				con.addWarn("[Task] error reading timeout, values are default");
			}
		}catch(Exception e){
			con.addWarn("[Task] error reading info, values are default");
		}
	}
	//getters
	public TaskType getType() {
		return type;
	}
	public long getTimeout() {
		return Timeout;
	}
	public AdditionalTaskSetting getAdditionalTasksetting(){
		AdditionalTaskSetting set=new AdditionalTaskSetting();
		set.setName(name);
		set.setTaskComplete(completeOnFinish);
		set.setTimeout(Timeout);
		return set;
	}
	public long getID(){
		return ID;
	}
	public String getName(){
		return name;
	}
	public boolean getCompleteOnFinish(){
		return completeOnFinish;
	}

	//setters
	protected void setType(TaskType type) {
		this.type = type;
	}
	protected void setTimeout(int timeout) {
		Timeout = timeout;
	}
	public void setAdditionalTaskSettings(AdditionalTaskSetting set){
		Timeout=set.getTimeout();
		completeOnFinish=set.isTaskComplete();
		name=set.getName();
	}
	public void setName(String name){
		this.name=name;
	}
	//parse to xml elements to save
	public Elements getElementsRepresentation(){
		Elements root=new Elements("task",new Attribute[]{new Attribute("type",""+type),new Attribute("name",name)});
		
		Elements info=new Elements("info");
		root.getChilds().add(info);
		
		Elements timeout=new Elements("timeout",new Attribute[]{new Attribute("timeout",""+Timeout),new Attribute("lastRan",""+lastRan.getTime())});
		info.getChilds().add(timeout);
		
		return root;
	}
	//executed when task is complete, calls back to monitor thread to notify it
	public void setCallback(CompleteCallBack callback){
		this.callback.setCompleteCallBack(callback);
	}
	public void itemCompleted(){
		lastRan=new Date(0);
	}
	//time before task can be executed again
	public Date getTimeoutFinish(){
		return new Date(lastRan.getTime()+Timeout);
	}
	public boolean timeoutDone(){ 
		if(lastRan.getTime()>System.currentTimeMillis()-Timeout){
			return false; //task was run within(after start) the timeout period(current-timeout)
		}
		else{
			return true;
		}
	}
	@Override
	public void run(){
		lastRan=new Date();
		callTask(callback);
	}
	/**
	 * Calls/launches the task for the given item
	 */
	public void callTask(CallEvent e){
		doTask();
		taskFinished(e);
	}
	//implemented by sub classes, does task
	public abstract void doTask();
	//called when task is finished
	protected void taskFinished(CallEvent e){
		if(completeOnFinish){//whether task was complete or not, whether monitor should change the state of the item or not
			e.getCallBack().addCompletedTask(ID);
		}
		else{
			e.getCallBack().addFinishedTask(ID);
		}
	}
	public String toString(){
		return "Task:"+name+"\n"+
				"type:"+type+"\n"+
				"Timeout:"+Timeout+"\n"+
				"LastRan:"+lastRan+"\n"+
				"ID:"+ID+"\n"+
				"CompleteOn inish:"+completeOnFinish;
	}

	
}
