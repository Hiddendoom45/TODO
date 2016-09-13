package global.tasks;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.jaunt.JauntException;
import com.jaunt.UserAgent;

import XML_Tests.Elements;
import global.Console;
import global.Task;
import global.TaskType;

public class WebpageTask extends Task {
	private URL url;
	private File download;
	
	public WebpageTask() {
		super(TaskType.Page_Download);
	}

	public WebpageTask(Elements root, Console con, Long ID) {
		super(root, con, ID, TaskType.Page_Download);
		try{
			Elements webpage=root.getChilds("Webpage").get(0);
			try{
				url=new URL(webpage.getChilds("url").get(0).getText());
				con.addInfoSet("Webpage", "url", url.toString());
			}catch(Exception e){
				con.addWarnErrRead("Webpage", "url", "null");
			}
			try{
				download=new File(webpage.getChilds("download").get(0).getText());
				con.addInfoSet("Webpage", "download location", download.getAbsolutePath());
			}catch(Exception e){
				con.addWarnErrRead("Webpage", "download location", "null");
			}
		}catch(Exception e){
			con.addWarn("[Webpage] error reading webpage values are default");
		}
	}

	public URL getUrl() {
		return url;
	}

	public File getDownload() {
		return download;
	}

	public void setUrl(String url) {
		try {
			this.url=new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void setDownload(File download) {
		this.download = download;
	}
	public Elements getElementsRepresentation(){
		Elements root=super.getElementsRepresentation();
		Elements webpage=new Elements("Webage");
		root.add(webpage);
		
		Elements url=new Elements("url",this.url.toString());
		webpage.add(url);
		Elements download=new Elements("download",this.download.getAbsolutePath());
		webpage.add(download);
		
		return root;
	}

	@Override
	public void doTask() {
		try{
			  UserAgent ua = new UserAgent();
			  ua.visit(url.toString());
			  ua.doc.saveCompleteWebPage(download);
			}
			catch(JauntException | IOException j){
			  System.err.println(j);
			}

	}

}
