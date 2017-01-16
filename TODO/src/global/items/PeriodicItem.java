package global.items;

import java.util.Date;

import XML_Tests.Attribute;
import XML_Tests.Elements;
import global.Console;
import global.ItemType;
import global.MonitorItem;
import global.TODOItem;
import global.Task;
import global.Vars;
import global.tasks.DefaultAbstractTask;
import monitor.monUtil.CallEvent;
/**
 * Item that is reset at periodic intervals
 * @author Allen
 *
 */
public class PeriodicItem extends TODOItem implements MonitorItem {
	private long timePeriod=1000;//in terms of whatever measurement the date system uses period interval in which completeness is reset
	private Date dateRef=new Date(0);//used to reference to when periodic is referencing to i.e. 4:00PM as reference time or otherwise
	private boolean monitor=false;
	private Date lastComplete=new Date(0);
	private Task task=new DefaultAbstractTask();//default task/null
	
	public PeriodicItem(String name, Vars var) {
		super(name, var);
	}
	public PeriodicItem(Elements periodicItem,Console con,Vars var){
		super(periodicItem,con,var);
		con.addSeperator("PeriodicItem Construction started");
		try{
			Elements period=periodicItem.getChilds("period").get(0);
			try{
				timePeriod=Long.parseLong(period.getChilds("period").get(0).getText());
				con.addInfoSet(name, "Time Period", ""+timePeriod);
			}catch(Exception e){
				con.addWarnErrRead(name, "Time Period", ""+timePeriod);
			}
			try{
				lastComplete=new Date(Long.parseLong(period.getChilds("lastComplete").get(0).getText()));
				con.addInfoSet(name, "last complete", lastComplete.toString());
			}catch(Exception e){
				con.addWarnErrRead(name, "last complete", new Date(0).toString());
			}
		}catch(Exception e){
			con.addWarn("["+name+"] error reading periodic data, values are default");
		}
		try{
			task=Vars.decodeTask(periodicItem.getChilds("task").get(0), con,getID());
		}catch(Exception e){
			con.addWarnErrRead(name, "Task", "default");
		}
	}

	public long getTimePeriod(){
		return timePeriod;
	}

	public Date getDateRef() {
		return dateRef;
	}
	public boolean getMonitor(){
		return monitor;
	}
	public Task getTask(){
		return task;
	}
	public boolean getComplete(){
		Date current=new Date();
		Date lastReset;//last time task was reset
		if(current.after(dateRef)){//to determine the last time in which it should've reset, based on whether it's currently before or after the reference data
			long time=current.getTime()-dateRef.getTime();
			long split=time%timePeriod;//get the time since the last periodic reset
			lastReset=new Date(current.getTime()-split);
		}
		else{
			long time=dateRef.getTime()-current.getTime();
			long split=time%timePeriod;//time till the next periodic reset
			lastReset=new Date(current.getTime()-(timePeriod-split));//last reset time>>(timeperiod-split)=time since reset
		}//if after or not determine if complete
		if(lastReset.after(lastComplete)){
			return false;
		}
		else{
			return true;
		}
	}
	public void setTimePeriod(long timePeriod){
		this.timePeriod=timePeriod;
	}
	public void setDateRef(Date dateRef) {
		this.dateRef = dateRef;
	}
	public void setMonitor(boolean monitor){
		this.monitor=monitor;
	}
	public void setTask(Task task){
		this.task=task;
	}
	public Elements parseToElements(){
		Elements root=super.parseToElements();
		root.getAttributes().add(new Attribute("monitor",monitor+""));
		root.getAttributes().add(new Attribute("type","periodicItem"));
		Elements period=new Elements("period");
		root.getChilds().add(period);

		Elements time=new Elements("period",""+timePeriod);
		period.getChilds().add(time);
		Elements complete=new Elements("lastComplete",""+lastComplete.getTime());
		period.getChilds().add(complete);
		
		Elements notify=task.getElementsRepresentation();
		root.getChilds().add(notify);
		
		return root;		
	}

	public Task callTask(CallEvent e) {
		task.callTask(null);
		return task;
	}

	public Date getNextTaskDate() {
		Date current=new Date();
		Date nextReset;//next time in which item would reset
		if(current.after(dateRef)){
			long time=current.getTime()-dateRef.getTime();
			long split=time%timePeriod;//get the time since the last periodic reset
			nextReset=new Date(current.getTime()+(timePeriod-split));//next reset time>>timePeriod-split==time till next reset
		}
		else{
			long time=dateRef.getTime()-current.getTime();
			long split=time%timePeriod;//time till the next periodic reset
			nextReset=new Date(current.getTime()+split);//next reset time
		}
		return nextReset;
	}

	public void taskComplete() {
		lastComplete=new Date();
		task.itemCompleted();
	}

	public void nullComplete() {
		lastComplete=new Date(0);

	}

	public ItemType getType() {
		return ItemType.PeriodicItem;
	}

}
