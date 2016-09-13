package global;

import java.util.ArrayList;

import editor.EditMain;
import global.items.DailyItem;
import global.items.EnergyItem;
import global.items.PeriodicItem;
import global.items.SingleItem;
import global.items.TimedItem;
import monitor.MonMain;
import viewer.ViewMain;

public class ItemData {
	private ArrayList<Long> ID=new ArrayList<Long>();
	private ArrayList<DailyItem> dailies=new ArrayList<DailyItem>();
	private ArrayList<TimedItem> timed=new ArrayList<TimedItem>();
	private ArrayList<SingleItem> single=new ArrayList<SingleItem>();
	private ArrayList<PeriodicItem> periodic=new ArrayList<PeriodicItem>();
	private ArrayList<EnergyItem> energy=new ArrayList<EnergyItem>();
	
	private EditMain edit=null;
	private ViewMain view=null;
	private MonMain mon=null;
	
	public ItemData() {}
	public ArrayList<DailyItem> getDailies() {
		return dailies;
	}
	public ArrayList<TimedItem> getTimed() {
		return timed;
	}
	public ArrayList<SingleItem> getSingle() {
		return single;
	}
	public ArrayList<PeriodicItem> getPeriodic() {
		return periodic;
	}
	public ArrayList<EnergyItem> getEnergy() {
		return energy;
	}
	public EditMain getEdit() {
		return edit;
	}
	public ViewMain getView() {
		return view;
	}
	public MonMain getMon() {
		return mon;
	}
	public void setDailies(ArrayList<DailyItem> dailies) {
		this.dailies = dailies;
	}
	public void setTimed(ArrayList<TimedItem> timed) {
		this.timed = timed;
	}
	public void setSingle(ArrayList<SingleItem> single) {
		this.single = single;
	}
	public void setPeriodic(ArrayList<PeriodicItem> periodic) {
		this.periodic = periodic;
	}
	public void setEnergy(ArrayList<EnergyItem> energy) {
		this.energy = energy;
	}
	public ArrayList<Long> getID() {
		return ID;
	}
	public void setID(ArrayList<Long> iD) {
		ID = iD;
	}
	public void setEdit(EditMain edit) {
		this.edit = edit;
	}
	public void setView(ViewMain view) {
		this.view = view;
	}
	public void setMon(MonMain mon) {
		this.mon = mon;
	}

}
