package global;
/**
 * variable class used to hold base settings for tasks 
 * @author Allen
 *
 */
public class AdditionalTaskSetting {
	private String name="Task";
	private boolean taskComplete;
	private long timeout;
	private boolean monitored;
	private boolean hide;

	public AdditionalTaskSetting() {
	}
	public String getName(){
		return name;
	}
	public boolean isTaskComplete() {
		return taskComplete;
	}
	public long getTimeout() {
		return timeout;
	}

	public boolean isMonitored() {
		return monitored;
	}
	public boolean isHidden(){
		return hide;
	}
	public void setName(String name){
		this.name=name;
	}
	public void setTaskComplete(boolean taskComplete) {
		this.taskComplete = taskComplete;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public void setMonitored(boolean monitored) {
		this.monitored = monitored;
	}
	public void setHidden(boolean hidden){
		hide=hidden;
	}
}
