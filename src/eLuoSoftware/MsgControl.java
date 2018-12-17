/*
eLuo Software©
Created and developed by Nathaniel L. Kerr
*/

package eLuoSoftware;

/*
 * Class filters message output
 */

public class MsgControl {
	String control = null, error = null;
	String[] summary = new String[20];
	private StringBuilder compile = new StringBuilder();
	
	public String Checker(String line, int num, String task){

		if(line.equalsIgnoreCase("Exiting, please wait...") || line.isEmpty() || line.indexOf("Error") !=-1 || line.indexOf("Restart the") != -1 || line.indexOf("failed") != -1 || line.indexOf("Access is denied") != -1 || line.indexOf("restart the computer") != -1 || line.indexOf("%") !=-1) 
		{
			control = null;
			//summary[num] = task +" had issue involving " + line;
		}
		if(line.indexOf("Cannot lock current drive") != -1 || line.indexOf("Chkdsk cannot run") != -1) {
			control = "break";
			summary[num] = task +" had issue involving " + line + " and could not run.";
		}
		
		else
			control = line;
		
		return control;
		
	}
	
	public String Ret() {
		return control;
	}
	
	public String results() 
	{
		for(int i=0;i<20;i++) 
		{
			if(summary[i].isEmpty() || summary[i] == null) 
			{
				continue;
			}
			else 
			{
				compile.append(summary[i] + " " + i + " ");
			}
		}
		
		return compile.toString();
	}
}
