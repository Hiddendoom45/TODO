package monitor.monUtil;

import java.util.Date;
import java.util.ArrayList;


/**
 * a simple queuing system that adds items in with a delay until the items can be retrieved,   
 * items added cannot be removed until delay time has expired
 * @author Allen
 *
 * @param <E> object in the queue
 */
public class DelayedQueue<E> {
	private ArrayList<DelayedBlock<? extends E>> queue=new ArrayList<DelayedBlock<? extends E>>();
	
	/**
	 * create object
	 */
	public DelayedQueue() {}
	
	/**
	 * adds item in with the corresponding delayed block
	 * @param block block containing object and delay of object
	 */
	public synchronized void add(DelayedBlock<? extends E> block){
		for(int i=0;i<queue.size();i++){
			if(queue.get(i).getDelay().after(block.getDelay())){
				queue.add(i, block);
				return;
			}
		}
		queue.add(block);
	}
	/**
	 * adds item e with given delay into the queue
	 * @param e object to be added to the queue
	 * @param delay delay before object can be retrieved if at front of queue in milliseconds
	 */
	public void add(E e, long delay){
		DelayedBlock<E> block=new DelayedBlock<E>(e);
		block.setDelay(delay);
		add(block);
	}
	/**
	 * adds item e with given delay into the queue
	 * @param e object to be added into the queue
	 * @param delay date when the object can be retrieved from the queue if in front
	 */
	public void add(E e,Date delay){
		DelayedBlock<E> block=new DelayedBlock<E>(e);
		block.setDelay(delay);
		add(block);
	}
	public ArrayList<DelayedBlock<? extends E>> getQueue(){
		return queue;
	}
	/**
	 * 
	 * @return first item in the queue without removing it if possible otherwise null
	 */
	public synchronized E peek(){
		if(queue.size()>0&&queue.get(0).getDelay().before(new Date())){
			return queue.get(0).getBlock();
		}
		return null;
	}
	/**
	 * 
	 * @return removes and return first item in queue if possible otherwise null
	 */
	public synchronized E poll(){
		if(queue.size()>0&&queue.get(0).getDelay().before(new Date())){
			E block=queue.get(0).getBlock();
			queue.remove(0);
			return block;
		}
		return null;
	}
	
	public synchronized boolean hasNext(){
		if(queue.size()>0&&queue.get(0).getDelay().before(new Date())){
			return true;
		}
		return false;
	}
	/**
	 * clears the queue
	 */
	public synchronized void clear(){
		queue.clear();
	}
	public String toString(){
		return queue.toString();
	}

}
