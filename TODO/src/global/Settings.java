package global;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
/**
 * settings file containing much of the constants used in the program, mostly file locations
 * @author Allen
 *
 */
public class Settings {
	
	public static String fileSource="/Users/Allen/Desktop/data.xml";
	public static int monThreadPoolSize=5;
	public static String notifySource="/Users/Allen/Desktop/Archive/Websites/Automation/Java/Notify/";
	public static String browserLaunchSource="/Users/Allen/Desktop/Archive/Websites/Automation/Java/BrowserLaunch/";
	public static String workflowSource="/Users/Allen/Desktop/Archive/Websites/Automation/Java/Workflow/";
	private static ArrayList<String> values=new ArrayList<String>();
	public static boolean setSettings(){
		File file=new File("Settings.txt");
		BufferedReader in = null;
		values.clear();
		try{
			in=new BufferedReader(new FileReader(file));
			while(in.ready()){
				values.add(in.readLine());
			}
			for(String s:values){
				if(s.startsWith("DataSource=")){
					fileSource=s.substring(11);
				}
				else if(s.startsWith("MonitorPoolSize=")){
					try{
						monThreadPoolSize=Integer.parseInt(s.substring(16));
					}catch(NumberFormatException e){}
				}
				else if(s.startsWith("NotifySource=")){
				notifySource=s.substring(13);
				}
				else if(s.startsWith("BrowserSource=")){
					browserLaunchSource=s.substring(14);
				}
				else if(s.startsWith("WorkflowSource=")){
					workflowSource=s.substring(15);
				}
				//TODO add read lines for other parameters.
			}
		}catch(Exception e){
			return false;
		}
		finally{
			try {
				in.close();
			} catch (Exception e) {}
		}
		return true;
	}
}
