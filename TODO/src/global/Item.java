package global;

public interface Item {
	
	public long getID();
	public String getName();
	public String getDescription();
	public int getPriority();
	
	public void taskComplete();

	public void nullComplete();

	public ItemType getType();
}
