package editor;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;

public class EditSettings extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2283418604364173978L;
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public EditSettings() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblFileSource = new JLabel("File Source");
		GridBagConstraints gbc_lblFileSource = new GridBagConstraints();
		gbc_lblFileSource.insets = new Insets(0, 0, 5, 0);
		gbc_lblFileSource.gridx = 0;
		gbc_lblFileSource.gridy = 0;
		add(lblFileSource, gbc_lblFileSource);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 1;
		add(textField, gbc_textField);
		textField.setColumns(10);
		//TODO complete
	}	

}
