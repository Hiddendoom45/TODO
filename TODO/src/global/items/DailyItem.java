package global.items;

import java.util.Calendar;
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
 * Class used to handle TODO items that are done on a daily basis
 * @author Allen
 *
 */
public class DailyItem extends TODOItem implements MonitorItem{
	private int resetH=0;//time when task resets during the day
	private int resetM=0;
	private boolean monitor=false;//whether this will be monitored by the monitor task
	private Task task=new DefaultAbstractTask();//holds notfication to call when time arises
	private Date lastComplete=new Date(0);//time when task was last completed, used to calculate if it's been done for the day
	
	public DailyItem(String name,Vars var){
		super(name,var);
	}
	public DailyItem(String name,String description,Vars var){
		super(name,var);
		setDescription(description);
	}
	/**
	 * Used when loading from XML file containing saved daily items.
	 * @param dailyItem Elements variable representing the daily item
	 */
	public DailyItem(Elements dailyItem,Console con,Vars var){
		super(dailyItem,con,var);
		con.addSeperator("DailyItem Construcstion Started");
		try{
			if(dailyItem.getAttribute("monitor").getValue().equals("true")){
				monitor=true;
				con.addInfoSet(name,"monitor","true");
			}
			else{
				monitor=false;
				con.addInfoSet(name, "monitor", "false");
			}
		}catch(Exception e){
			con.addWarnErrRead(name, "monitor", "false");
		}
		try{
			Elements reset=dailyItem.getChilds("reset").get(0);
			try{
				resetH=Integer.parseInt(reset.getAttribute("hour").getValue());
				con.addInfoSet(name, "reset hour", ""+resetH);
			}catch(Exception e){
				con.addInfoSet(name, "reset hour", "0");
			}
			try{
				resetM=Integer.parseInt(reset.getAttribute("minute").getValue());
				con.addInfoSet(name, "reset minute", ""+resetM);
			}catch(Exception e){
				con.addWarnErrRead(name, "reset minute", "0");
			}
			try{
				lastComplete=new Date(Long.parseLong(reset.getAttribute("lastComplete").getValue()));
				con.addInfoSet(name, "last complete", lastComplete.toString());
			}catch(Exception e){
				con.addWarnErrRead(name, "last compelte", new Date(0).toString());
			}
		}catch(Exception e){
			con.addWarn("["+name+"] error reading reset data, values are default");
		}
		try{
			task=Vars.decodeTask(dailyItem.getChilds("task").get(0), con,getID());
		}catch(Exception e){
			con.addWarnErrRead(name, "Task", "default");
		}
		//TODO set task for all types

	}
	//getters
	public int getResetH() {
		return resetH;
	}
	public int getResetM() {
		return resetM;
	}
	public boolean getMonitor(){
		return monitor;
	}
	public Task getTask(){
		return task;
	}
	
	//setters
	public void setResetH(int resetH) {
		this.resetH = resetH;
	}
	public void setResetM(int resetM) {
		this.resetM = resetM;
	}
	public void setMonitor(boolean monitor){
		this.monitor=monitor;
	}
	public void setTask(Task task){
		this.task=task;
	}
	
	/**
	 * parses it to Elements form to save to XML
	 * @return Elements representing this
	 */
	public Elements parseToElements(){
		Elements root=super.parseToElements();
		root.getAttributes().add(new Attribute("monitor",monitor+""));
		root.getAttributes().add(new Attribute("type","dailyItem"));
		Elements reset=new Elements("reset");
		reset.getAttributes().add(new Attribute("hour",""+resetH));
		reset.getAttributes().add(new Attribute("minute",""+resetM));
		reset.getAttributes().add(new Attribute("lastComplete",""+lastComplete.getTime()));
		root.getChilds().add(reset);
		
		
		Elements notify=task.getElementsRepresentation();
		root.getChilds().add(notify);
		
		return root;
	}
	
	public String toString(){
		return name;
	}
	
	@Override
	public Task callTask(CallEvent e) {
		task.callTask(e);
		return task;
	}
	@Override
	public Date getNextTaskDate() {
		Date current=new Date();
		if(lastComplete!=null){
			Calendar reset=Calendar.getInstance();//get current date
			reset.set(Calendar.SECOND, 0);//so that it's always at top of minute rather than constantly changing
			reset.set(Calendar.MINUTE, resetM);//changes minute to time when it resets
			reset.set(Calendar.HOUR_OF_DAY, resetH);//changes hour likewise
			//roll back date if it's after reset time for day
			if(current.before(reset.getTime())){
				reset.roll(Calendar.DAY_OF_YEAR,false);//TODO watch for roll to 0 errors
			}
			//resulting reset is the time during the day in which it should've reset
			if(lastComplete.after(reset.getTime())){
				reset.roll(Calendar.DAY_OF_YEAR, true);
				return reset.getTime();
			}
		}
		return current;
	}
	public boolean getComplete(){
		Date current=new Date();
		if(lastComplete!=null){
			Calendar reset=Calendar.getInstance();//get current date
			reset.set(Calendar.MINUTE, resetM);//changes minute to time when it resets
			reset.set(Calendar.HOUR_OF_DAY, resetH);//changes hour likewise
			//roll back date if it's after reset time for day
			if(current.before(reset.getTime())){
				reset.roll(Calendar.DAY_OF_YEAR,false);//TODO watch for roll to 0 errors
			}
			//resulting reset is the time during the day in which it should've reset
			if(lastComplete.after(reset.getTime())){
				return true;
			}
		}
		return false;
	}
	public void taskComplete() {
		complete=true;
		lastComplete=new Date();
		task.itemCompleted();
	}
	public void nullComplete(){
		complete=false;
		lastComplete=new Date(0);
	}
	public ItemType getType() {
		return ItemType.DailyItem;
	}

}
