package viewer;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import global.Console;
import global.ItemData;
import global.Settings;
import global.TODOItem;
import global.Vars;

public class ViewVar extends Vars {
	private ArrayList<TODOItem> complete= new ArrayList<TODOItem>();
	private ArrayList<TODOItem> incomplete= new ArrayList<TODOItem>();
	
	public ViewVar(Console con) {
		super(con);
	}
	public ViewVar(Console con,ItemData data){
		super(con,data);
	}
	public ArrayList<TODOItem> getIncomplete(){
		return incomplete;
	}
	public ArrayList<TODOItem> getComplete(){
		return complete;
	}
	public TODOItem getTODOItem(long ID){
		for(int i=0;i<complete.size();i++){
			if(complete.get(i).getID()==ID){
				return complete.get(i);
			}
		}
		for(int i=0;i<incomplete.size();i++){
			if(incomplete.get(i).getID()==ID){
				return incomplete.get(i);
			}
		}
		return null;
	}
	public void read(File xmlFile){
		if(new Date(new File(Settings.fileSource).lastModified()).after(lastRead)){
			super.read(xmlFile);
			lastRead=new Date();
		}
	}
	public void reload(){
		if(new Date(new File(Settings.fileSource).lastModified()).after(lastRead)){
			read(new File(Settings.fileSource));
			lastRead=new Date();
		}
		update();
	}
	public void update(){
		updateIncomplete();
		updateComplete();
	}
	public boolean rewrite(){
		if(new Date(new File(Settings.fileSource).lastModified()).after(lastRead)){
			return false;
		}
		super.write(new File(Settings.fileSource));
		lastRead=new Date();
		return true;
	}
	public ArrayList<TODOItem> updateIncomplete(){
		incomplete.clear();
		for(int i=0;i<dailies.size();i++){
			if(!dailies.get(i).getComplete()&&!dailies.get(i).isHide()){
				incomplete.add(dailies.get(i));
				con.addMsg("[ViewVar]DailyItem:"+dailies.get(i).getName()+" added to incomplete list");
			}
		}
		for(int i=0;i<timed.size();i++){
			if(!timed.get(i).getComplete()&&!timed.get(i).isHide()){
				incomplete.add(timed.get(i));
				con.addMsg("[ViewVar]TimedItem:"+timed.get(i).getName()+" added to incomplete list");
			}
		}
		for(int i=0;i<single.size();i++){
			if(!single.get(i).getComplete()&&!single.get(i).isHide()){
				incomplete.add(single.get(i));
				con.addMsg("[ViewVar]SingleItem:"+single.get(i).getName()+" added to incomplete list");
			}
		}
		for(int i=0;i<periodic.size();i++){
			if(!periodic.get(i).getComplete()&&!periodic.get(i).isHide()){
				incomplete.add(periodic.get(i));
				con.addMsg("[ViewVar]PeriodicItem:"+periodic.get(i).getName()+" added to incomplete list");
			}
		}
		for(int i=0;i<energy.size();i++){
			if(!energy.get(i).getComplete()&&!energy.get(i).isHide()){
				incomplete.add(energy.get(i));
				con.addMsg("[ViewVar]EnergyItem:"+energy.get(i).getName()+" added to incomplete list");
			}
		}
		//TODO add for other item types
		return incomplete;
	}
	public ArrayList<TODOItem> updateComplete(){
		complete.clear();
		for(int i=0;i<dailies.size();i++){
			if(dailies.get(i).getComplete()&&!dailies.get(i).isHide()){
				complete.add(dailies.get(i));
				con.addMsg("[ViewVar]DailyItem:"+dailies.get(i).getName()+" added to complete list");
			}
		}
		for(int i=0;i<timed.size();i++){
			if(timed.get(i).getComplete()&&!timed.get(i).isHide()){
				complete.add(timed.get(i));
				con.addMsg("[ViewVar]TimedItem:"+timed.get(i).getName()+" added to complete list");
			}
		}
		for(int i=0;i<single.size();i++){
			if(single.get(i).getComplete()&&!single.get(i).isHide()){
				complete.add(single.get(i));
				con.addMsg("[ViewVar]SingleItem:"+single.get(i).getName()+" added to complete list");
			}
		}
		for(int i=0;i<periodic.size();i++){
			if(periodic.get(i).getComplete()&&!periodic.get(i).isHide()){
				complete.add(periodic.get(i));
				con.addMsg("[ViewVar]PeriodicItem:"+periodic.get(i).getName()+" added to complete list");
			}
		}
		for(int i=0;i<energy.size();i++){
			if(energy.get(i).getComplete()&&!energy.get(i).isHide()){
				complete.add(energy.get(i));
				con.addMsg("[ViewVar]EnergyItem:"+energy.get(i).getName()+" added to complete list");
			}
		}
		//TODO add for other item types
		return complete;
	}
	
	public void swapIncomplete(long ID){
		reload();
		getTODOItem(ID).taskComplete();
		if(!rewrite()){
			swapIncomplete(ID);
		}
		con.addInfo("[ViewVar] "+getTODOItem(ID).getName()+" changed to complete");
	}
	public void swapComplete(long ID){
		reload();
		getTODOItem(ID).nullComplete();
		if(!rewrite()){
			swapComplete(ID);
		}
		con.addInfo("[ViewVar] "+getTODOItem(ID).getName()+" changed to incomplete");
	}
	public void deleteItem(long ID){
		reload();
		con.addInfo("[ViewVar] "+getTODOItem(ID).getName()+" deleted");
		super.removeItem(ID);
		if(!rewrite()){
			deleteItem(ID);
		}
	}
}	