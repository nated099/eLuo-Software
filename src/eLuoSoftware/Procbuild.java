/*
eLuo Software©
Created and developed by Nathaniel L. Kerr
*/

package eLuoSoftware;

import java.io.IOException;
import java.io.InputStream;

/*
 * This class runs specific processes via cmd and powershell based on the command
 */

public class Procbuild extends InputStream implements Runnable 
{
	InputStream out;
	public InputStream Send(String command) {	
		ProcessBuilder cmdRun;
		if(command.equalsIgnoreCase("admin.bat") || (command.indexOf("echo") != -1) || (command.indexOf("typeperf") != -1) || command.indexOf("reg add") != -1) {
			cmdRun = new ProcessBuilder("cmd", "/c", command); 
			System.out.println("cmd used");
		}
		else {
			cmdRun = new ProcessBuilder("powershell", "-NonInteractive", command);
		}
		//must have /C argument to move from one process to another
		
        cmdRun.redirectErrorStream(false);

        try {
            Process p = cmdRun.start();   
            out = p.getInputStream();
        }  
        catch(IOException e) {
        	System.out.println("DEBUG: line 36 oops");
        	return null;
        }
        return out;
	}
	
	public InputStream Send() {
		ProcessBuilder cmdRun;
		System.getProperty("admin.bat");
		cmdRun = new ProcessBuilder("cmd", "/c", System.getProperty("admin.bat")); 

        cmdRun.redirectErrorStream(false);
        

        try {
            Process p = cmdRun.start();   
            out = p.getInputStream();
        }  
        catch(IOException e) {
        	System.out.println("DEBUG: line 55 oops");
        	return null;
        }
        return out;
	}
	
	@Override
	public int read() throws IOException{
		return 0;
	}

	@Override
	public void run() {
		//Auto-generated method stub
		
	} 
		
}
