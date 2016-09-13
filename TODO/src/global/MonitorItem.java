package global;

import java.util.Date;

import monitor.monUtil.CallEvent;

public interface MonitorItem extends Item {
	public Task getTask();
	
	public Task callTask(CallEvent e);
	
	public Date getNextTaskDate();
	
	public boolean getMonitor();
}
