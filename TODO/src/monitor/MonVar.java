package monitor;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import global.Console;
import global.ItemData;
import global.MonitorItem;
import global.Settings;
import global.Task;
import global.Vars;
import global.items.DailyItem;
import global.items.EnergyItem;
import global.items.PeriodicItem;
import global.items.TimedItem;
import monitor.monUtil.CompleteCallBack;
import monitor.monUtil.DelayedBlock;
import monitor.monUtil.DelayedQueue;

public class MonVar extends Vars {
	private ArrayList<MonitorItem> monitoredIncomplete=new ArrayList<MonitorItem>();//list of items that are monitored that are incomplete
	private ArrayList<MonitorItem> monitoredComplete=new ArrayList<MonitorItem>();//list of items that are monitored that are complete
	private DelayedQueue<Task> tasks=new DelayedQueue<Task>();//list of currently planned tasks
	private CompleteCallBack callback=new CompleteCallBack(this);//to set the completeness of tasks
	
	private HashMap<Long,Task> runningTasks=new HashMap<Long,Task>();
	
	private boolean pause=false;
	
	private ScheduledExecutorService executor = Executors.newScheduledThreadPool(Settings.monThreadPoolSize);
	
	public MonVar(Console con) {
		super(con);
	}
	public MonVar(Console con, ItemData data) {
		super(con, data);
	}
	public ArrayList<MonitorItem> getMonitoredIncomplete() {
		return monitoredIncomplete;
	}
	public MonitorItem getMonitoredIncompleteItem(Long ID){
		for(MonitorItem item:monitoredIncomplete){
			if(item.getID()==ID){
				return item;
			}
		}
		return null;
	}
	public ArrayList<MonitorItem> getMonitoredComplete() {
		return monitoredComplete;
	}
	public MonitorItem getMonitoredCompleteItem(Long ID){
		for(MonitorItem item:monitoredComplete){
			if(item.getID()==ID){
				return item;
			}
		}
		return null;
	}
	public ArrayList<DelayedBlock<? extends Task>> getPlannedTasks(){
		return tasks.getQueue();
	}
	public synchronized void taskCompleted(long taskID){
		reload();
		synchronized(tasks){
			runningTasks.remove(taskID);
			for(MonitorItem item:monitoredIncomplete){
				if(item.getID()==taskID){
					item.taskComplete();
				}
			}
		update();
		}
		if(!rewrite()){
			taskCompleted(taskID);
		}
	}
	public synchronized void taskFinished(long taskID){
		runningTasks.remove(taskID);
	}
	public synchronized void setPause(boolean pause){
		if(!pause){
			this.notify();
		}
		this.pause=pause;
	}
	public synchronized void setMonitored(){
		monitoredComplete.clear();
		monitoredIncomplete.clear();
		for(DailyItem item:dailies){
			if(item.getMonitor()){
				if(item.getComplete()){
					monitoredComplete.add(item);
				}else{
					monitoredIncomplete.add(item);
				}
			}
		}
		for(TimedItem item:timed){
			if(item.getMonitor()){
				if(item.getComplete()){
					monitoredComplete.add(item);
				}else{
					monitoredIncomplete.add(item);
				}
			}
		}
		for(EnergyItem item:energy){
			if(item.getMonitor()){
				if(item.getComplete()){
					monitoredComplete.add(item);
				}else{
					monitoredIncomplete.add(item);
				}
			}
		}
		for(PeriodicItem item:periodic){
			if(item.getMonitor()){
				if(item.getComplete()){
					monitoredComplete.add(item);
				}else{
					monitoredIncomplete.add(item);
				}
			}
		}
	}
	public synchronized void setTaskDates(){
		synchronized(tasks){
			tasks.clear();
			for(MonitorItem item:monitoredIncomplete){
				if(runningTasks.get(item.getID())==null){//if the task isn't already running
					if(item.getTask().getTimeoutFinish().before(item.getNextTaskDate())){//if timeouts before it's scheduled next to run
						con.addInfo("[MonVar] adding task of "+item.getName()+" to run at "+item.getNextTaskDate());
						tasks.add(item.getTask(), item.getNextTaskDate());
					}
					else{//or if timeout continues after next task date
						con.addInfo("[MonVar] adding task of "+item.getName()+" to run at "+item.getTask().getTimeoutFinish());
						tasks.add(item.getTask(),item.getTask().getTimeoutFinish());
					}
				}
			}
		}
	}
	public void read(File xmlFile){
		if(new Date(new File(Settings.fileSource).lastModified()).after(lastRead)){
			super.read(xmlFile);
			lastRead=new Date();
		}
	}
	public synchronized void reload(){
		if(new Date(new File(Settings.fileSource).lastModified()).after(lastRead)){
			read(new File(Settings.fileSource));
			update();
		}
	}
	public synchronized void startTasks(){
		Thread t=new Thread(){
			public void run(){
				runTasks();
			}
		};
		t.setName("Task Thread");
		t.setDaemon(true);
		t.start();
	}
	
	//once stared  will run whatever tasks are set to be run
	private void runTasks(){
		while(true){
			System.out.println("taskrun");
			reload();//so rewrite will almost certainly happen
			synchronized(tasks){
				boolean hasUpdate=false;//boolean to determine if any tasks has been run, and whether last ran should be updated for those tasks
				while(tasks.hasNext()){
					hasUpdate=true;
					tasks.peek().setCallback(callback);
					runningTasks.put(tasks.peek().getID(), tasks.peek());
					con.addInfo("[MonVar]: running task:"+tasks.peek().getName());
					executor.execute(tasks.poll());
				}
				if(hasUpdate){//only writes if there's tasks' lastRans to update
					rewrite();
				}
			}
			System.out.println(tasks.toString());
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {}
			if(pause){
				try {
					synchronized(this){
						this.wait();
					}
				} catch (InterruptedException e) {}
			}
		}
	}
	public synchronized void update(){
		setMonitored();
		setTaskDates();
	}
	public synchronized boolean rewrite(){
		if(new Date(new File(Settings.fileSource).lastModified()).after(lastRead)){
			return false;
		}
		super.write(new File(Settings.fileSource));
		lastRead=new Date();
		return true;
	}
}
