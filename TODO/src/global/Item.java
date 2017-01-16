package global;
/**
 * Generic for all items, i.e. daily items, etc
 * @author Allen
 *
 */
public interface Item {
	
	public long getID();
	public String getName();
	public String getDescription();
	public int getPriority();
	
	public void taskComplete();

	public void nullComplete();

	public ItemType getType();
}
