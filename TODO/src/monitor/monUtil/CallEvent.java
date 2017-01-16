package monitor.monUtil;
/**
 * used to allow tasks to tell main monitor thread that they've finished running
 * @author Allen
 *
 */
public class CallEvent {
	private CompleteCallBack callback;
	public CallEvent() {}

	public void setCompleteCallBack(CompleteCallBack callback){
		this.callback=callback;
	}
	public CompleteCallBack getCallBack(){
		return callback;
	}
	
}
