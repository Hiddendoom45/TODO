package global;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import XML_Tests.Attribute;
import XML_Tests.Elements;
import XML_Tests.XMLStAXFile;
import editor.EditMain;
import global.items.DailyItem;
import global.items.EnergyItem;
import global.items.PeriodicItem;
import global.items.SingleItem;
import global.items.TimedItem;
import global.tasks.BrowserTask;
import global.tasks.DefaultAbstractTask;
import global.tasks.NotifyTask;
import global.tasks.WorkflowTask;
import monitor.MonMain;
import viewer.ViewMain;
/**
 * main class holding the importnatn variables
 * @author Allen
 *
 */
public class Vars {
	//console/logger
	protected Console con;
	//various items stored in arrays and the ids
	protected ArrayList<Long> ID;
	protected ArrayList<DailyItem> dailies;
	protected ArrayList<TimedItem> timed;
	protected ArrayList<SingleItem> single;
	protected ArrayList<PeriodicItem> periodic;
	protected ArrayList<EnergyItem> energy;
	//used to determine if data saved on file has been modified or not
	protected Date lastRead=new Date(0);
	//used to modify only items that have changed since last read
	private ItemSaveInfoUtility saveInfo=new ItemSaveInfoUtility();
	//constructor
	public Vars(Console con){
		this.con=con;
		ID=new ArrayList<Long>();
		dailies=new ArrayList<DailyItem>();
		timed=new ArrayList<TimedItem>();
		single=new ArrayList<SingleItem>();
		periodic=new ArrayList<PeriodicItem>();
		energy=new ArrayList<EnergyItem>();
	}
	//constructor for use when another part's aready launched i.e. monitor, editor or viewer(likely failed)
	public Vars(Console con,ItemData data){
		this.con=con;
		dailies=data.getDailies();
		timed=data.getTimed();
		single=data.getSingle();
		periodic=data.getPeriodic();
		energy=data.getEnergy();
		ID=data.getID();
	}


	public Console getConsole(){
		return con;
	}
	public ArrayList<Long> getIDs(){
		return ID;
	}
	//methods to get each of the items
	public ArrayList<DailyItem> getDaily() {
		return dailies;
	}
	public DailyItem getDailyItem(long ID){
		for(int i=0;i<dailies.size();i++){
			if(dailies.get(i).getID()==ID){
				return dailies.get(i);
			}
		}
		return null;
	}
	public ArrayList<TimedItem> getTimed() {
		return timed;
	}
	public TimedItem getTimedItem(long ID){
		for(int i=0;i<timed.size();i++){
			if(timed.get(i).getID()==ID){
				return timed.get(i);
			}
		}
		return null;
	}
	public ArrayList<SingleItem> getSingle() {
		return single;
	}
	public SingleItem getSingleItem(long ID){
		for(int i=0;i<single.size();i++){
			if(single.get(i).getID()==ID){
				return single.get(i);
			}
		}
		return null;
	}
	public ArrayList<PeriodicItem> getPeriodic(){
		return periodic;
	}
	public PeriodicItem getPeriodicItem(long ID){
		for(int i=0;i<periodic.size();i++){
			if(periodic.get(i).getID()==ID){
				return periodic.get(i);
			}
		}
		return null;
	}
	public ArrayList<EnergyItem> getEnergy(){
		return energy;
	}
	public EnergyItem getEnergyItem(long ID){
		for(int i=0;i<energy.size();i++){
			if(energy.get(i).getID()==ID){
				return energy.get(i);
			}
		}
		return null;
	}
	//get the main classes for each of the parts(again not sure if sucessful, around the time I gave up on program)
	public EditMain getEdit() {
		return Exasperation.edit;
	}
	public ViewMain getView() {
		return Exasperation.view;
	}
	public MonMain getMon(){
		return Exasperation.mon;
	}
	//supposed to have main data of this var class to be transferred to the var class of other parts(not sure if sucessufl)
	public ItemData getItemData(){
		ItemData data=new ItemData();
		data.setDailies(dailies);
		data.setEnergy(energy);
		data.setID(ID);
		data.setPeriodic(periodic);
		data.setSingle(single);
		data.setTimed(timed);
		return data;
	}
	public void setEdit(EditMain edit) {
		Exasperation.edit=edit;
	}
	public void setView(ViewMain view) {
		Exasperation.view=view;
	}
	public void setMon(MonMain mon){
		Exasperation.mon=mon;
	}
	//when removing item, remove ID so it can be used again
	public boolean removeItem(long ID){
		removeID(ID);
		for(int i=0;i<dailies.size();i++){
			if(dailies.get(i).getID()==ID){
				dailies.remove(i);
				return true;
			}
		}
		for(int i=0;i<timed.size();i++){
			if(timed.get(i).getID()==ID){
				timed.remove(i);
				return true;
			}
		}
		for(int i=0;i<single.size();i++){
			if(single.get(i).getID()==ID){
				single.remove(i);
				return true;
			}
		}
		for(int i=0;i<periodic.size();i++){
			if(periodic.get(i).getID()==ID){
				periodic.remove(i);
				return true;
			}
		}
		for(int i=0;i<energy.size();i++){
			if(energy.get(i).getID()==ID){
				energy.remove(i);
			}
		}
		//TODO complete for other types
		return false;
	}
	//record state of items
	public synchronized void register(){
		saveInfo.register(dailies);
		saveInfo.register(energy);
		saveInfo.register(periodic);
		saveInfo.register(single);
		saveInfo.register(timed);
	}
	//read from file
	public synchronized void read(File xmlFile){
		//reset();
		register();
		con.addSeperator("Read start");//log
		XMLStAXFile xml=new XMLStAXFile(xmlFile);
		if(XMLresetread(xml)){//parsing for each of the items
			ArrayList<Elements> dailies=xml.parseToElements(new Attribute("type","dailyItem"));
			for(int i=0;i<dailies.size();i++){
				if(saveInfo.getSaveState(dailies.get(i))!=ItemSaveInfoUtility.ITEM_SAME){
					if(saveInfo.getSaveState(dailies.get(i))==ItemSaveInfoUtility.NON_EXISTENCE){
						DailyItem insert=new DailyItem(dailies.get(i),con,this);
						this.dailies.add(insert);
					}
					else if(saveInfo.getSaveState(dailies.get(i))==ItemSaveInfoUtility.ITEM_DIFFERENT){
						int index=saveInfo.getSaveIndex(dailies.get(i));
						Long id=Long.parseLong(dailies.get(i).getAttribute("id").getValue());
						ID.remove(id);
						DailyItem insert=new DailyItem(dailies.get(i),con,this);
						this.dailies.set(index,insert);
					}
				}
			}
		}
		if(XMLresetread(xml)){
			ArrayList<Elements> timed=xml.parseToElements(new Attribute("type","timedItem"));
			for(int i=0;i<timed.size();i++){
				if(saveInfo.getSaveState(timed.get(i))!=ItemSaveInfoUtility.ITEM_SAME){
					if(saveInfo.getSaveState(timed.get(i))==ItemSaveInfoUtility.NON_EXISTENCE){
						TimedItem insert=new TimedItem(timed.get(i),con,this);
						this.timed.add(insert);
					}
					else if(saveInfo.getSaveState(timed.get(i))==ItemSaveInfoUtility.ITEM_DIFFERENT){
						int index=saveInfo.getSaveIndex(timed.get(i));
						Long id=Long.parseLong(timed.get(i).getAttribute("id").getValue());
						ID.remove(id);
						TimedItem insert=new TimedItem(timed.get(i),con,this);
						this.timed.set(index,insert);
					}
				}
			}
		}
		if(XMLresetread(xml)){
			ArrayList<Elements> single=xml.parseToElements(new Attribute("type","singleItem"));
			for(int i=0;i<single.size();i++){
				if(saveInfo.getSaveState(single.get(i))!=ItemSaveInfoUtility.ITEM_SAME){
					if(saveInfo.getSaveState(single.get(i))==ItemSaveInfoUtility.NON_EXISTENCE){
						SingleItem insert=new SingleItem(single.get(i),con,this);
						this.single.add(insert);
					}
					else if(saveInfo.getSaveState(single.get(i))==ItemSaveInfoUtility.ITEM_DIFFERENT){
						int index=saveInfo.getSaveIndex(single.get(i));
						Long id=Long.parseLong(single.get(i).getAttribute("id").getValue());
						ID.remove(id);
						SingleItem insert=new SingleItem(single.get(i),con,this);
						this.single.set(index,insert);
					}
				}
			}
		}
		if(XMLresetread(xml)){
			ArrayList<Elements> periodic=xml.parseToElements(new Attribute("type","periodicItem"));
			for(int i=0;i<periodic.size();i++){
				if(saveInfo.getSaveState(periodic.get(i))!=ItemSaveInfoUtility.ITEM_SAME){
					if(saveInfo.getSaveState(periodic.get(i))==ItemSaveInfoUtility.NON_EXISTENCE){
						PeriodicItem insert=new PeriodicItem(periodic.get(i),con,this);
						this.periodic.add(insert);
					}
					else if(saveInfo.getSaveState(periodic.get(i))==ItemSaveInfoUtility.ITEM_DIFFERENT){
						int index=saveInfo.getSaveIndex(periodic.get(i));
						Long id=Long.parseLong(periodic.get(i).getAttribute("id").getValue());
						ID.remove(id);
						PeriodicItem insert=new PeriodicItem(periodic.get(i),con,this);
						this.periodic.set(index,insert);
					}
				}
			}
		}
		if(XMLresetread(xml)){
			ArrayList<Elements> energy=xml.parseToElements(new Attribute("type","energyItem"));
			for(int i=0;i<energy.size();i++){
				if(saveInfo.getSaveState(energy.get(i))!=ItemSaveInfoUtility.ITEM_SAME){
					if(saveInfo.getSaveState(energy.get(i))==ItemSaveInfoUtility.NON_EXISTENCE){
						EnergyItem insert=new EnergyItem(energy.get(i),con,this);
						this.energy.add(insert);
					}
					else if(saveInfo.getSaveState(energy.get(i))==ItemSaveInfoUtility.ITEM_DIFFERENT){
						int index=saveInfo.getSaveIndex(energy.get(i));
						Long id=Long.parseLong(energy.get(i).getAttribute("id").getValue());
						ID.remove(id);
						EnergyItem insert=new EnergyItem(energy.get(i),con,this);
						this.energy.set(index,insert);
					}
				}
			}
		}
		int tries=0;
		while(tries<10){
			if(!xml.endReader()&&tries<=10){//more tries to ensure that reader is ended, to free space
				tries++;
				con.addError("[Vars] failed to end reader, attempt "+tries);
			}
			else{
				break;
			}
		}
		register();

	}
	//used to condense code, resets XML reader
	private boolean XMLresetread(XMLStAXFile xml){
		int tries=0;
		while(tries<4){
			try{
				if(!xml.resetReader()){
					throw new IOException();
				}
				else{
					return true;
				}
			}catch(Exception e){
				tries++;
				con.addError("[Vars] error reading for energy items, attempt"+tries);
				
			}
		}
		return false;
	}
	//write to file
	public synchronized void write(File xmlFile){
		con.addSeperator("Write start");
		XMLStAXFile xml=new XMLStAXFile(xmlFile);
		Elements root=new Elements("root");
		for(int i=0;i<dailies.size();i++){
			root.getChilds().add(dailies.get(i).parseToElements());
		}
		for(int i=0;i<timed.size();i++){
			root.getChilds().add(timed.get(i).parseToElements());
		}
		for(int i=0;i<single.size();i++){
			root.getChilds().add(single.get(i).parseToElements());
		}
		for(int i=0;i<periodic.size();i++){
			root.getChilds().add(periodic.get(i).parseToElements());
		}
		for(int i=0;i<energy.size();i++){
			root.getChilds().add(energy.get(i).parseToElements());
		}
		//TODO complete for additional items
		int tries=0;
		while(tries<4){
			if(!xml.writeNewXMLFile(xmlFile)||!xml.startWriter()){
				tries++;
				con.addError("[Vars] failed to start XML file writing, attempt "+tries);
			}
			else{
				break;
			}
		}
		tries=0;
		while(tries<4){
			if(!xml.writeElement(root)){
				tries++;
				con.addError("[Vars] failed to write data, attempt "+tries);
			}
			else{
				break;
			}
		}
		tries=0;
		while(tries<4){
			if(!xml.endWriter()){
				tries++;
				con.addError("[Vars] failed to end writer, attempt"+tries);
			}
			else{
				break;
			}
		}
	}
	//resets everything, clears data
	public synchronized void reset(){
		ID.clear();
		timed.clear();
		dailies.clear();
		single.clear();
		periodic.clear();
		energy.clear();
		//TODO add for more
	}
	//parsing for the tasks the items have
	public static Task decodeTask(Elements task,Console con,Long id){
		String type="";
		try{
		type=task.getAttribute("type").getValue();
		}catch(Exception e){
			e.printStackTrace();
			con.addWarn("[Task] error reading task type");
		}
		if(type.equals("default")){
			con.addInfo("[TaskDecode] decoded new default task");
			return new DefaultAbstractTask();
		}
		else if(type.equals("Apple_Notification")){
			return new NotifyTask(task,con,id);
		}
		else if(type.equals("Browser_Launch")){
			return new BrowserTask(task,con,id);
		}
		else if(type.equals("Start_Workflow")){
			return new WorkflowTask(task,con,id);
		}
		//TODO add for more
		con.addWarn("[TaskDecode] no type data available, reverting to default task");
		return new DefaultAbstractTask();
	}
	/**
	 * generate a random ID for a new item
	 * @return random ID
	 */
	public long generateID(){
		long returnVal=0;
		while(true){
			Random rand=new Random();
			returnVal=rand.nextLong();
			boolean found=false;
			for(long id:ID){
				if(id==returnVal){
					found=true;
				}
			}
			if(!found){
				break;
			}
		}
		ID.add(returnVal);
		return returnVal;
	}
	//method used to set ID by checking if it exists or not
	/**
	 * 
	 * @param currID ID you want to add to ID list
	 * @return currID if no duplicate, if duplicate random ID
	 */
	public long generateID(long currID){
		for(long id:ID){
			if(id==currID){
				currID=generateID();
			}
		}
		ID.add(currID);
		return currID;
	}
	/**
	 * Remove ID from system so that it can be used again
	 * @param removeID ID to remove
	 */
	public void removeID(long removeID){
		for(int i=0;i<ID.size();i++){
			if(ID.get(i)==removeID){
				ID.remove(i);
				//if ID's found and removed avoid searching for more as they are unique
				break;
			}
		}
	}

}
