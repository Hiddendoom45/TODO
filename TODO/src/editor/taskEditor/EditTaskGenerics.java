package editor.taskEditor;

import javax.swing.JPanel;

import global.Task;

public interface EditTaskGenerics {
	/**
	 * 
	 * @return task that has been set and created by the panel
	 */
	public Task grabTask();
	/**
	 * to allow eclipse to render panel properly
	 * @return
	 */
	public JPanel getPanel();
}
