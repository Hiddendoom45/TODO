package global.tasks;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import XML_Tests.Attribute;
import XML_Tests.Elements;
import global.Console;
import global.Settings;
import global.Task;
import global.TaskType;
/**
 * launches browser to specified URL
 * @author Allen
 *
 */
public class BrowserTask extends Task {
	private URL url;
	private boolean newWindow=false;
	public BrowserTask() {
		super(TaskType.Browser_Launch);
	}
	public BrowserTask(Elements root,Console con,Long id){
		super(root,con,id,TaskType.Browser_Launch);
		try{
			Elements browser=root.getChilds("browser").get(0);
			try{
				url=new URL(browser.getAttribute("url").getValue());
				con.addInfoSet("Browser", "URL", url.toString());
			}catch(Exception e){
				con.addWarnErrRead("Browser", "URL", "null");
			}
			try{
				if(browser.getAttribute("window").getValue().equals("true")){
					newWindow=true;
				}
				else{
					newWindow=false;
				}
				con.addInfoSet("Browser", "New Window", ""+newWindow);
			}catch(Exception e){
				con.addWarnErrRead("Browser", "New Window", "false");
			}
		}catch(Exception e){
			con.addWarn("[Browser] error reading browser values are default");
		}
		
	}
	
	public URL getURL(){
		return url;
	}
	public boolean isNewWindow(){
		return newWindow;
	}
	public void setURL(String url){
		try {
			this.url=new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	public void setNewWindow(boolean newWindow){
		this.newWindow=newWindow;
	}
	
	public Elements getElementsRepresentation(){
		Elements root=super.getElementsRepresentation();
		Elements browser=new Elements ("browser");
		root.getChilds().add(browser);
		browser.getAttributes().add(new Attribute("url",url.toString()));
		browser.getAttributes().add(new Attribute("window",""+newWindow));
		
		return root;
	}
	public void doTask(){
		int count=0;
		File writeFile;
		while(true){
			try {
				//writes file to folder and has automator carry out launch action
				if(count>0){
				writeFile=new File(Settings.browserLaunchSource+"launch"+count+".txt");//avoid dupes
				}else{
				writeFile=new File(Settings.browserLaunchSource+"launch.txt");
				}
				
				if(writeFile.exists()){
					count++;
				}
				else{
					BufferedWriter write=new BufferedWriter(new FileWriter(writeFile));
					write.write(""+url);
					write.close();
					break;
				}
			} catch (IOException e1) {}
		}
	}



}
