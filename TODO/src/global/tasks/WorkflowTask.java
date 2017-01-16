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
 * executes an automator workflow
 * @author Allen
 *
 */
public class WorkflowTask extends Task {
	private File workflow;//location of the workflow to run
	private String inputs;//additional input give to the workflow when running
	public WorkflowTask() {
		super(TaskType.Start_Workflow);
	}

	public WorkflowTask(Elements root, Console con, Long ID) {
		super(root, con, ID,TaskType.Start_Workflow);
		try{
			Elements workflow=root.getChilds("workflow").get(0);
			try{
				this.workflow=new File(workflow.getChilds("directory").get(0).getText());
				con.addInfoSet("Workflow", "workflow directory", this.workflow.getAbsolutePath());
			}catch(Exception e){
				con.addWarnErrRead("Workflow", "workflow directory", "null");
			}
			try{
				inputs=workflow.getChilds("inputs").get(0).getText();
				con.addInfoSet("Workflow", "workflow inputs", inputs);
			}catch(Exception e){
				con.addWarnErrRead("Workflow", "workflow inputs", "null");
			}
		}catch(Exception e){
			con.addWarn("[Workflow] error reading workflow, values are default");
		}
	}

	public File getWorkflow() {
		return workflow;
	}
	public String getInputs(){
		return inputs;
	}

	public void setWorkflow(File workflow) {
		this.workflow = workflow;
	}
	public void setInputs(String inputs){
		this.inputs=inputs;
	}
	public void doTask(){
		int count=0;
		File writeFile;
		while(true){
			try {
				System.out.println("writefile");
				if(count>0){//write file to location, folder action detects write, and executes workflow specified
				writeFile=new File(Settings.workflowSource+"workflow"+count+".txt");
				}else{
				writeFile=new File(Settings.workflowSource+"workflow.txt");
				}
				
				if(writeFile.exists()){
					count++;
				}
				else{
					BufferedWriter write=new BufferedWriter(new FileWriter(writeFile));
					write.write("directory:"+workflow.getAbsolutePath());
					if(!(inputs==null)||inputs!=""){
						write.write("\n");
					}
					write.write(inputs);
					write.close();
					break;
				}
			} catch (IOException e1) {}
		}
	}
	public Elements getElementsRepresentation(){
		Elements root=super.getElementsRepresentation();
		Elements workflow=new Elements("workflow");
		root.getChilds().add(workflow);

		Elements directory=new Elements("directory",this.workflow.getAbsolutePath());
		workflow.getChilds().add(directory);
		Elements inputs=new Elements("inputs",this.inputs);
		workflow.getChilds().add(inputs);
		return root;
	}

}
