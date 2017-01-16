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
 * item that when completed has a time period after which it will reset
 * @author Allen
 *
 */
public class TimedItem extends TODOItem implements MonitorItem {
	private Date startDate=new Date(0);//last time item was complete
	private long timeElapse=0;//time before complete task is reset after completetion
	private Task task=new DefaultAbstractTask();
	private boolean monitor=false;



	public TimedItem(String name,Vars var){
		super(name,var);
	}
	public TimedItem(Elements timedItem,Console con,Vars var){
		super(timedItem,con,var);
		con.addSeperator("TimedItem Construction Started");
		try{
			if(timedItem.getAttribute("monitor").getValue().equals("true")){
				monitor=true;
				con.addInfoSet(name, "monitor", "true");
			}
			else{
				monitor=false;
				con.addInfoSet(name, "monitor", "false");
			}
		}catch(Exception e){
			con.addWarnErrRead(name, "monitor", "false");
		}
		try{
			Elements timing=timedItem.getChilds("timing").get(0);
			try{
				startDate=new Date(Long.parseLong(timing.getAttribute("startDate").getValue()));
				con.addInfoSet(name, "start date", startDate.toString());
			}catch(Exception e){
				con.addWarnErrRead(name, "start date", new Date(0).toString());
				startDate=new Date(0);
			}
			try{
				timeElapse=Long.parseLong(timing.getAttribute("timeElapse").getValue());
				con.addInfoSet(name, "time elapse", timeElapse+"(ms)");
			}catch(Exception e){
				con.addWarnErrRead(name, "time elapse", "0(ms)");
			}
		}catch(Exception e){
			con.addWarn("["+name+"] error reading timing info, values reset to default");
		}
		try{
			task=Vars.decodeTask(timedItem.getChilds("task").get(0),con,getID());
		}catch(Exception e){
			con.addWarnErrRead(name, "task", "default");
		}
	}

	//getters
	public Date getStartDate() {
		return startDate;
	}
	public long getTimeElapse() {
		return timeElapse;
	}
	public boolean getMonitor(){
		return monitor;
	}
	public Task getTask() {
		return task;
	}
	//setters
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setTimeElapse(long timeElapse) {
		this.timeElapse = timeElapse;
	}
	public void setMonitor(boolean monitor){
		this.monitor=monitor;
	}
	public void setTask(Task task) {
		this.task = task;
	}

	public Elements parseToElements(){
		Elements root=super.parseToElements();
		root.getAttributes().add(new Attribute("monitor",monitor+""));
		root.getAttributes().add(new Attribute("type","timedItem"));
		Elements timing=new Elements("timing");
		timing.getAttributes().add(new Attribute("startDate",""+startDate.getTime()));
		timing.getAttributes().add(new Attribute("timeElapse",""+timeElapse));
		root.getChilds().add(timing);
		
		Elements notify=task.getElementsRepresentation();
		root.getChilds().add(notify);
		return root;
	}

	@Override
	public Task callTask(CallEvent e) {
		task.callTask(null);
		return task;
	}

	@Override
	public Date getNextTaskDate() {
		Date current=new Date();
		Date end=new Date(startDate.getTime()+timeElapse);//time when timed period ends
		if(current.after(end)){//if time period has alread elapsed
			return end;
		}
		else return current;
		
	}
	public boolean getComplete(){
		Date tComplete=new Date(startDate.getTime()+timeElapse);
		Date current=new Date();
		if(tComplete.after(current)){
			return true;
		}
		else{
			return false;
		}
	}
	@Override
	public void taskComplete() {
		startDate=new Date();
		task.itemCompleted();
	}
	public void nullComplete(){
		startDate=new Date(0);
	}
	@Override
	public ItemType getType() {
		return ItemType.TimedItem;
	}
	
	
	

}
