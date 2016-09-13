package global.items;

import XML_Tests.Attribute;
import XML_Tests.Elements;
import global.Console;
import global.ItemType;
import global.TODOItem;
import global.Vars;

public class SingleItem extends TODOItem{
	
	/*
	 * used when first creating the item
	 */
	public SingleItem(String name,Vars var){
		super(name,var);
	}
	public SingleItem(Elements singleItem, Console con,Vars var){
		super(singleItem,con,var);
		con.addSeperator("SingleItem Construction Started");
			try{
				if(singleItem.getChilds("complete").get(0).getText().equals("true")){
					complete=true;
					con.addInfoSet(name, "complete", "true");
				}
				else{
					complete=false;
					con.addInfoSet(name, "complete", "false");
				}
			}catch(Exception e){
				con.addWarnErrRead(name, "complete", "false");;
			}
	}
	
	public void taskComplete(){
		complete=true;
	}
	public void nullComplete() {
		complete=false;
	}

	public Elements parseToElements(){
		Elements root= super.parseToElements();
		root.getAttributes().add(new Attribute("type","singleItem"));
		Elements complete=new Elements("complete",""+this.complete);
		root.getChilds().add(complete);
		
		return root;
	}
	public ItemType getType() {
		return ItemType.SingleItem;
	}
	
	

}
