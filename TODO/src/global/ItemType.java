package global;
/**
 * different types of items
 * @author Allen
 *
 */
public enum ItemType {
	
	DailyItem("dailyItem"),
	TimedItem("timedItem"),
	SingleItem("singleItem"),
	PeriodicItem("periodicItem"),
	EnergyItem("energyItem"),
	DateItem("dateItem"),
	DueItem("dueItem");
	
	
	private String rep;
	ItemType(String rep){
		this.rep=rep;
	}
	public String toString(){
		return rep;
	}
}
