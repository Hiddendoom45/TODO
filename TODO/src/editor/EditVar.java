package editor;

import global.Console;
import global.ItemData;
import global.Vars;
/**
 * main class holding all the important data variables
 * @see Var class
 * @author Allen
 *
 */
public class EditVar extends Vars {

	public EditVar(Console con) {
		super(con);
	}

	public EditVar(Console con, ItemData data) {
		super(con, data);
	}
	

}
