package frameUtil.Listeners;

import java.awt.Dimension;

import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class JSpinnerListeners {

	public static ChangeListener MaxMinLimiter(final int max,final int min){
		return new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				MaxMinLimiter(e, max, min);
			}
			
		};
	}
	public static void MaxMinLimiter(ChangeEvent e,int max, int min){
		JSpinner spin=(JSpinner) (e.getSource());
		if((int)spin.getValue()>max){
			spin.setValue(max);
		}
		else if((int)spin.getValue()<min){
			spin.setValue(min);
		}
	}
	public static ChangeListener MaxLimiter(final int max){
		return new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				MaxLimiter(e, max);
			}
		}; 
	}
	public static void MaxLimiter(ChangeEvent e,final int max){
		JSpinner spin=(JSpinner) (e.getSource());
		if((int)spin.getValue()>max){
			spin.setValue(max);
		}
	}
	public static ChangeListener MinLimiter(final int min){
		return new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				MinLimiter(e, min);
			}
		}; 
	}
	public static void MinLimiter(ChangeEvent e,final int min){
		JSpinner spin=(JSpinner) (e.getSource());
		if((int)spin.getValue()<min){
			spin.setValue(min);
		}
	}
	public static ChangeListener spinnerResize(){
		return new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				spinnerResize(e);
			}
		};
	}
	public static void spinnerResize(ChangeEvent e){
		final JSpinner spin=(JSpinner)e.getSource();
		int length=spin.getValue().toString().length();
		Dimension size=spin.getPreferredSize();
		int numlength;
		if(spin.getValue().toString().startsWith("-")){
			numlength=length-1;
		}
		else{
			numlength=length;
		}
		size.width=length*8+29+(4*(numlength/3));
		spin.setPreferredSize(size);
		spin.getParent().validate();
	}
}
