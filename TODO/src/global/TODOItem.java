package global;

import XML_Tests.Attribute;
import XML_Tests.Elements;
/**
 * generic item for todo list
 * @author Allen
 *
 */
public abstract class TODOItem implements Item {
	private long ID=0;
	protected String name="";
	protected String description="";
	protected int priority=1;
	protected boolean complete=false;//whether item is completed or not
	protected boolean hide=false;//whether to hide the task from viewers or not
	protected Vars var;
	
	public TODOItem(Vars var){
		this.var=var;
		ID=var.generateID();
	}
	public TODOItem(String name, Vars var){
		this.var=var;
		this.name=name;
		ID=var.generateID();
	}
	public TODOItem(Elements root,Console con,Vars var){
		this.var=var;
		ID=var.generateID();
		con.addSeperator("Base Item Generation Started");
		try{
			this.name=root.getAttribute("name").getValue();
			con.addInfo("[Item] reading item "+name);
			}catch(Exception e){
				con.addWarn("[Item] error reading name, reset to default \"Item\"");
				con.addError(e.getLocalizedMessage());
				name="Item";
			}
		try{
			setID(Long.parseLong(root.getAttribute("id").getValue()));
			con.addInfoSet(name, "ID", ""+ID);
		}catch(Exception e){
			con.addWarnErrRead(name, "ID", ""+ID);
		}
		try{
			if(root.getAttribute("hide").getValue().equals("true")){
				hide=true;
				con.addInfo("["+name+"] Item is hidden from viewers");
			}else{
				con.addInfo("["+name+"] Item is visible in viewers");
			}
		}catch(Exception e){
			con.addWarnErrRead(name, "hidden", "false");
		}
		try{
			Elements info=root.getChilds("info").get(0);
			try{
				priority=Integer.parseInt(info.getChilds("priority").get(0).getText());
				con.addInfoSet(name, "priority", ""+priority);
			}catch(Exception e){
				con.addWarnErrRead(name, "priority", "1");
			}		
			try{
				description=info.getChilds("description").get(0).getText();
				con.addInfoSet(name, "description", description);
			}catch(Exception e){
				con.addWarn("["+name+"] error reading description, no description set");
			}
		}catch(Exception e){
			con.addWarn("["+name+"] error reading info, default values used");
		}
		
		//TODO complete
	}
	//getters
	public long getID(){
		return ID;
	}
	
	public String getName(){
		return name;
	}
	public String getDescription(){
		return description;
	}
	public int getPriority(){
		return priority;
	}
	public boolean getComplete(){
		return complete;
	}
	public boolean isHide() {
		return hide;
	}
	
	//setters
	public void setID(long ID){
		var.removeID(this.ID);
		this.ID=var.generateID(ID);
	}
	public void setName(String name){
		this.name=name;
	}
	public void setDescription(String description){
		this.description=description;
	}
	public void setPriority(int priority){
		this.priority=priority;
	}
	public void setHide(boolean hide) {
		this.hide = hide;
	}
	
	public Elements parseToElements(){
		Elements root=new Elements("item",new Attribute[]{new Attribute("name",name),
				new Attribute("id",""+ID), new Attribute("hide",""+hide)});
		
		Elements info=new Elements("info");
		root.getChilds().add(info);
		
		Elements priority=new Elements("priority",""+this.priority);
		info.getChilds().add(priority);
		Elements desc=new Elements("description",description);
		info.getChilds().add(desc);
		
		return root;
	}
	//abstract methods
	public abstract void taskComplete();
	
	public abstract void nullComplete();
	
	public abstract ItemType getType();
	
	
	

}
