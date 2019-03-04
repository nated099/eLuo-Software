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
		//TODO concatonate strings with line lengths of 65 or greater. Concatonate strings with large amounts of spaces.
		if(line.equalsIgnoreCase("Exiting, please wait...") || line.length() > 65 || line.indexOf("---") != -1 || line.isEmpty() || line.indexOf("Error") !=-1 || line.indexOf("Restart the") != -1 || line.indexOf("failed") != -1 || line.indexOf("Access is denied") != -1 || line.indexOf("restart the computer") != -1 || line.indexOf("%") !=-1 || line.indexOf("successfully") != -1) 
		{
			control = null;
			//summary[num] = task +" had issue involving " + line;
		}
		else if(line.indexOf("Cannot lock current drive") != -1 || line.indexOf("Chkdsk cannot run") != -1) {
			control = "break";
			summary[num] = task +" had issue involving " + line + " and could not run.";
		}
		else if(line.indexOf("s c a n") !=-1 || line.indexOf("v e r i f i c a t i o n") != -1){
			control = "SFC Scan and Repair in progress, please wait";
		}
		else
			control = line;
		
		return control;
		
	}
	public int Restore(String line) {
		int rID = 0;
		String[] restore = line.split(" ", 0);
		
		if(line.indexOf("eLuo") != -1)
		for(int i = 0; i < restore.length; i++) {
			if(restore[i].equals("eLuo")) {
				for(int x = 0; x < restore.length - i; x++) {
				if(restore[i+x].matches("\\d+")) {
					rID = Integer.parseInt(restore[i+x]);
					break;
				}
			}
		}
		}
		return rID;
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
