package global;

import java.util.Date;

import monitor.monUtil.CallEvent;
/**
 * generics for items that can be monitored by the monitor system
 * @author Allen
 *
 */
public interface MonitorItem extends Item {
	public Task getTask();
	
	public Task callTask(CallEvent e);
	
	public Date getNextTaskDate();
	
	public boolean getMonitor();
}
