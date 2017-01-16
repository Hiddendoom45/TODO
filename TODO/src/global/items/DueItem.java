package global.items;

import java.util.Date;

import XML_Tests.Elements;
import global.Console;
import global.ItemType;
import global.MonitorItem;
import global.TODOItem;
import global.Task;
import global.Vars;
import global.tasks.DefaultAbstractTask;
import monitor.monUtil.CallEvent;
/**
 * class to handle items that are due at some point(really should be completed) and 
 * ups the priority as that point gets closer
 * @author Allen
 *NOTE: incomplete, dropped project before this was completed
 */
public class DueItem extends TODOItem implements MonitorItem {
	//private Date dueDate;
	//private boolean complete;
	
	private Task task=new DefaultAbstractTask();
	
	public DueItem(Vars var) {
		super(var);
		// TODO Auto-generated constructor stub
	}

	public DueItem(String name, Vars var) {
		super(name, var);
		// TODO Auto-generated constructor stub
	}

	public DueItem(Elements root, Console con, Vars var) {
		super(root, con, var);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Task getTask() {
		return task;
	}

	@Override
	public Task callTask(CallEvent e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getNextTaskDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getMonitor() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void taskComplete() {
		complete=true;

	}

	@Override
	public void nullComplete() {
		complete=false;

	}

	@Override
	public ItemType getType() {
		return ItemType.DueItem;
	}

}
