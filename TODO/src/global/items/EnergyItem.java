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

public class EnergyItem extends TODOItem implements MonitorItem{
	private Date energyReplenish=new Date(0);//time when Energy will be full
	private int energyMax=0;
	private float energyURefill=1;//time it takes to refill one unit of energy
	private Task task=new DefaultAbstractTask();
	private Boolean monitor=false;
	
	
	
	public EnergyItem(String name,Vars var) {
		super(name,var);
	}
	public EnergyItem(Elements energyItem, Console con, Vars var){
		super(energyItem,con,var);
		con.addSeperator("EnergyItem Construction Started");
		try{
			if(energyItem.getAttribute("monitor").getValue().equals("true")){
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
			Elements energy=energyItem.getChilds("energy").get(0);
			try{
				energyMax=Integer.parseInt(energy.getAttribute("max").getValue());
				con.addInfoSet(name, "max energy", ""+energyMax);
			}catch(Exception e){
				con.addWarnErrRead(name, "max energy", "0");
			}
			try{
				energyURefill=Float.parseFloat(energy.getAttribute("refill").getValue());
				con.addInfoSet(name, "refill time", energyURefill+"(min)");
			}catch(Exception e){
				con.addWarnErrRead(name, "refill time", "1(min)");
			}
			try{
				energyReplenish=new Date(Long.parseLong(energy.getChilds("replenish").get(0).getText()));
				con.addInfoSet(name, "replenish date", energyReplenish.toString());
			}catch(Exception e){
				con.addWarnErrRead(name, "replenish date", "none");
			}
		}catch(Exception e){
			con.addError("["+name+"]error reading energy info values set to default");
		}
		try{
			task=Vars.decodeTask(energyItem.getChilds("task").get(0), con,getID());
		}catch(Exception e){
			con.addWarnErrRead(name, "Task", "default");
		}
	}
	//getters
	public Date getEnergyReplenish() {
		return energyReplenish;
	}
	public int getEnergyMax() {
		return energyMax;
	}
	public float getEnergyURefill() {
		return energyURefill;
	}
	public Task getTask() {
		return task;
	}
	public boolean getMonitor() {
		return monitor;
	}
	public boolean getComplete(){
		Date current=new Date();
		if(energyReplenish.after(current)){
			return true;
		}
		else{
			return false;
		}
	}
	
	//setters
	public void setEnergyReplenish(Date energyReplenish) {
		this.energyReplenish = energyReplenish;
	}
	public void setEnergyMax(int energyMax) {
		this.energyMax = energyMax;
	}
	public void setEnergyURefill(float energyURefill) {
		this.energyURefill = energyURefill;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	public void setMonitor(Boolean monitor) {
		this.monitor = monitor;
	}
	public int calculateCurrentEnergy(){
		int energy=0;
		Date current=new Date();
		if(current.after(energyReplenish)){//if energy if full
			return energyMax;
		}
		else{
			long time=energyReplenish.getTime()-current.getTime();
			energy=(int) (time/energyURefill);//calculate energy remaining based on time to refill
			energy=energyMax-energy;//calculate actual energy from that.
		}
		return energy;
	}
	public Elements parseToElements(){
		Elements root=super.parseToElements();
		root.getAttributes().add(new Attribute("monitor",monitor+""));
		root.getAttributes().add(new Attribute("type","energyItem"));
		Elements energy=new Elements("energy");
		energy.getAttributes().add(new Attribute("max",""+energyMax));
		energy.getAttributes().add(new Attribute("refill",""+energyURefill));
		Elements replenish=new Elements("replenish",""+energyReplenish.getTime());
		energy.getChilds().add(replenish);
		root.getChilds().add(energy);
		
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
		return energyReplenish;
	}

	@Override
	public void taskComplete() {
		Date current=new Date();
		energyReplenish=new Date((long) (current.getTime()+(long)(energyMax*energyURefill*60000)));//set the replenish time to when energy will refill
		task.itemCompleted();
	}

	@Override
	public void nullComplete() {
		energyReplenish=new Date(); 
	}

	@Override
	public ItemType getType() {
		return ItemType.EnergyItem;
	}
	

}
