package monitor.monUtil;

import java.util.Date;
/**
 * adds a delay to an object E
 * Used to determine delay before tasks can be executed
 * 
 * @author Allen
 *
 * @param <E> block/item with a delay
 */
public class DelayedBlock<E> {
	private E block;
	private Date delay;//date it is delayed until;
	
	
	public DelayedBlock(E e) {
		block=e;
	}
	public DelayedBlock(E e,Date delay){
		block=e;
		this.delay=delay;
	}
	public DelayedBlock(E e,long delay){
		block=e;
		setDelay(delay);
	}
	public void setBlock(E block){
		this.block=block;
	}
	public void setDelay(Date delay){
		this.delay=delay;
	}
	public void setDelay(long delay){
		this.delay=new Date(java.lang.System.currentTimeMillis()+delay);
	}
	public E getBlock(){
		return block;
	}
	public Date getDelay(){
		return delay;
	}
	public String toString(){
		return "Delay:"+delay+"\n"+
				"Block:\n"+block;
			
				
	}
	

}
