package monitor.monUtil;

import java.util.PriorityQueue;

import monitor.MonVar;

/**
 * thread that is called by task when task is complete to set its completeness in main thread
 * 
 * @author Allen
 *
 */
public class CompleteCallBack implements Runnable {
	private Thread t;
	private MonVar var;
	private PriorityQueue<Long> completed=new PriorityQueue<Long>();
	private PriorityQueue<Long> finished=new PriorityQueue<Long>();
	
	public CompleteCallBack(MonVar var) {
		this.var=var;
		t=new Thread(this);
		t.setDaemon(true);
		t.start();
	}
	public void run(){
		while (true){
			synchronized(this){
				while (!completed.isEmpty()){
					var.taskCompleted(completed.poll());
				}
				while(!finished.isEmpty()){
					var.taskFinished(finished.poll());
				}
			}
			try {
				synchronized(this){
					this.wait();
				}
			} catch (InterruptedException e) {
				break;
			}
		}
	}
	//called by each of the tasks to add to the queue of tasks that are to be set to complete or finished
	public synchronized void addCompletedTask(long ID){
		System.out.println("adding completed"+ID);
		completed.add(ID);
		this.notify();
	}
	public synchronized void addFinishedTask(long ID){
		finished.add(ID);
		this.notify();
	}

}
