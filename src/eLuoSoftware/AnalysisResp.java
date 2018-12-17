/*
eLuo Software©
Created and developed by Nathaniel L. Kerr
*/

package eLuoSoftware;

/*
 * This class is intended to manage criteria for fixing 100% disk usage issues in Windows based on results from typeperf
 */
public class AnalysisResp {
	String[] commandOut = new String[50];
	String percentage = "";
	int counter = 0; 
	
	//TODO setup parsing logic to get percentage for each typeperf command, getting avg
	public String typeperf() 
	{
		for(int i=0;i<5;i++) 
		{
			if(commandOut[i].length() == 37) 
			{
				percentage = "yes";
			}
			else
				percentage = "no";
		}
		return percentage;
	}
	
	public String Build(String line) {
		commandOut[counter] = line;
		counter++;
		String split = "\"";
		String[] lineSep = line.split(split, 4);
		for(int y=0;y < lineSep.length;y++) {
			if(lineSep[y].matches("^\\d{2}.\\d{6}\"")) {
				lineSep[y] = (lineSep[y].replaceAll("^(\\d{2}).(\\d{6}\")","$1"));
				return (lineSep[y] + "%\r\n");
			}
			else if (lineSep[y].matches("^\\d{1}.\\d{6}\"")) {
				lineSep[y] = (lineSep[y].replaceAll("^(\\d{1}).(\\d{6}\")","$1"));
				return (lineSep[y] + " objects queued\r\n");
			}
			else if (lineSep[y].matches("^.*[(_Total)].*\"")) {
				//lineSep[y] = (lineSep[y].replaceAll("^.*([(_Total)]\")","$1"));
				//lineSep[y] = (lineSep[y].replaceAll(".*(?<=)\\)(.*)\"","$1"));
				lineSep[y] = (lineSep[y].replaceAll(".*(?<=\\\\)(.*)\"","$1"));
				
				if(lineSep[y].matches(".*(PhysicalDisk)")) {
					return "PhysicalDisk"+ lineSep[y] ;
				}
				else
					return "LogicalDisk" + lineSep[y];

			}
		}
		return line;
	}
	
	public boolean Build() {
		//If we've reached 5 items added to the array we're ready to get what we need
		if(counter % 4 == 0)
			return true;
		else
			return false;
	}
}
