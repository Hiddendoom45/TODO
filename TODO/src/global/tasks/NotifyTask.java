package global.tasks;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import XML_Tests.Elements;
import global.Console;
import global.Settings;
import global.Task;
import global.TaskType;


/**
 * one of the basic task types simply creates an apple notification through one way or another
 * @author Allen
 *
 */
public class NotifyTask extends Task {
	private String message="";
	private String title="";
	public NotifyTask() {
		super(TaskType.Apple_Notification);
	}
	public NotifyTask(Elements root,Console con,Long id){
		super(root,con,id,TaskType.Apple_Notification);
		
		try{
			Elements notify=root.getChilds("notify").get(0);
			try{
				title=notify.getChilds("title").get(0).getText();
				con.addInfoSet("Notify", "Title", title);
			}catch(Exception e){
				con.addWarnErrRead("Notify", "Title", "null");
			}
			try{
				message=notify.getChilds("message").get(0).getText();
				con.addInfoSet("Notify", "Message", message);
			}catch(Exception e){
				con.addWarnErrRead("Notify", "Message", "null");
			}
		}catch(Exception e){
			con.addWarn("[Notify] error reading notify, values are default");
		}
	}

	public String getMessage() {
		return message;
	}

	public String getTitle() {
		return title;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void doTask(){
		try {
			BufferedWriter write=new BufferedWriter(new FileWriter(new File(Settings.notifySource+title+".txt")));
			write.write(message);
			write.close();
		} catch (IOException e1) {}
	}

	@Override
	public Elements getElementsRepresentation() {
		Elements root=super.getElementsRepresentation();
		
		Elements notify=new Elements("notify");
		root.getChilds().add(notify);
		
		Elements title=new Elements("title",this.title);
		notify.getChilds().add(title);
		Elements message=new Elements("message",this.message);
		notify.getChilds().add(message);
		
		return root;
	}

}
