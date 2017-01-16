package global.tasks;
import XML_Tests.Attribute;
import XML_Tests.Elements;
import global.Task;
import global.TaskType;
/**
 * null task
 * @author Allen
 *
 */

public class DefaultAbstractTask extends Task{

	public DefaultAbstractTask() {
		super(TaskType.Default);
		Timeout=10;
	}

	public void doTask(){}

	@Override
	public Elements getElementsRepresentation() {
		Elements root=new Elements("task",new Attribute[]{new Attribute("type","default")});
		return root;
	}

}
