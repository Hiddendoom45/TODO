package monitor.monUtil;

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
