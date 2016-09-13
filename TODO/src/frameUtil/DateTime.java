package frameUtil;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import frameUtil.Listeners.JSpinnerListeners;

public class DateTime extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7925439632800856567L;
	private long time;
	private JSpinner Sp_d;
	private JSpinner Sp_h;
	private JSpinner Sp_m;
	private JSpinner Sp_s;

	/**
	 * Create the panel.
	 */
	public DateTime() {
		GridBagConstraints gbc_P_elapseTime = new GridBagConstraints();
		gbc_P_elapseTime.fill = GridBagConstraints.BOTH;
		gbc_P_elapseTime.insets = new Insets(0, 0, 5, 5);
		gbc_P_elapseTime.gridx = 1;
		gbc_P_elapseTime.gridy = 4;
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		Sp_d = new JSpinner();
		add(Sp_d);

		Sp_h = new JSpinner();
		add(Sp_h);

		Sp_m = new JSpinner();
		add(Sp_m);

		Sp_s = new JSpinner();
		add(Sp_s);

		Sp_d.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSpinnerListeners.MinLimiter(e, 0);
				JSpinnerListeners.spinnerResize(e);
				setTimeValue();
			}

		});
		Sp_h.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSpinnerListeners.MaxMinLimiter(e, 23, 0);
				JSpinnerListeners.spinnerResize(e);
				setTimeValue();

			}
		});
		Sp_m.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSpinnerListeners.MaxMinLimiter(e, 59, 0);
				JSpinnerListeners.spinnerResize(e);
				setTimeValue();

			}
		});
		Sp_s.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSpinnerListeners.MaxMinLimiter(e, 59, 0);
				JSpinnerListeners.spinnerResize(e);
				setTimeValue();

			}
		});
	}
	public JPanel getPanel(){
		return this;
	}
	private void setTimeValue() {
		time=(long)((86400*(long)(int)Sp_d.getValue())+(3600*(int)Sp_h.getValue())+(60*(int)Sp_m.getValue())+(int)Sp_s.getValue())*1000;
	}
	public void setTime(long time){
		time=time/1000;
		String s=time%60+"";
    	String m=time/60%60+"";
    	String h=time/3600%24+"";
    	String d=time/86400+"";
    	Sp_s.setValue(Integer.parseInt(s));
    	Sp_m.setValue(Integer.parseInt(m));
    	Sp_h.setValue(Integer.parseInt(h));
    	Sp_d.setValue(Integer.parseInt(d));
	}
	public long getTime(){
		return time;
	}

}
