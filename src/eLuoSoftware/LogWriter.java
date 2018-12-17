/*
eLuo Software©
Created and developed by Nathaniel L. Kerr
*/

package eLuoSoftware;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/*
 * Class handles writing log file 
 */

public class LogWriter{
	int writeNum = 0;
	

	
	public void Writer(String log, int num) 
	{
		
		
        File pathname = new File("C:\\eLog "+java.time.LocalDate.now()+".txt");  
        BufferedWriter buffWrite;
        if(!pathname.exists())
        {
            try 
            {
                pathname.createNewFile();
                pathname.setWritable(true);
            }
            catch (IOException e) 
            {
                    System.out.println("Failure to create log file");
            }
        }
        try 
        {
        	
        	buffWrite = new BufferedWriter(new FileWriter(pathname, true));
        	
        	//to separate logs by # of command run
        	if(num>writeNum)
        	{
        		writeNum = num;
        		buffWrite.write("#" + num + "\r\n\r\n");
        	}
        	
            buffWrite.write(log + "\r\n");
            buffWrite.close();
        }
        
        catch (IOException e)
        {
        	System.out.println("Failure to initiate buffered writer for log");
        }
        
    }

}
