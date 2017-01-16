package global;

import java.util.ArrayList;
import java.util.HashMap;

import XML_Tests.Elements;
/**
 * used to make sure that when reading, only items that have changed are read
 * @author Allen
 *
 */
public class ItemSaveInfoUtility {
	//int values representing different ways items can be found to exist
	public static int NON_EXISTENCE=0;//no instance of item
	public static int ITEM_DIFFERENT=1;//an instance of item with same id, but different values
	public static int ITEM_SAME=2;//exactly the same item
	
	private HashMap<Long,ItemSaveInfo> data=new HashMap<Long,ItemSaveInfo>();
	
	public ItemSaveInfoUtility() {}
	public void register(ArrayList<? extends TODOItem> item){
		for(int i=0;i<item.size();i++){
			register(item.get(i),i);
		}
	}
	//register item into map
	public void register(TODOItem item, int index){
		data.put(item.getID(), new ItemSaveInfo(index,item.parseToElements().hashCode()));
	}
	//get whether item in save has changed, same or is a new item that has been created since last read
	public int getSaveState(Elements item){
		Long id=Long.parseLong(item.getAttribute("id").getValue());
		if(data.get(id)==null){
			return ItemSaveInfoUtility.NON_EXISTENCE;
		}
		else{
			if(data.get(id).getHashCode()==item.hashCode()){
				return ItemSaveInfoUtility.ITEM_SAME;
			}
			else{
				return ItemSaveInfoUtility.ITEM_DIFFERENT;
			}
		}
	}
	public int getSaveIndex(Elements item){
		Long id=Long.parseLong(item.getAttribute("id").getValue());
		if(!(data.get(id)==null)){
			return data.get(id).getIndex();
		}
		return -1;
	}
}
