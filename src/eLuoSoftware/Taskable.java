/*
eLuo Software©
Created and developed by Nathaniel L. Kerr
*/

package eLuoSoftware;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;
import java.util.Vector;
import java.util.concurrent.*;
import java.util.regex.Pattern;

/*
 * Model-hybrid class that handles tasks run by the main class, eLuo
 * 
 * Runnable, may be run as it's own thread to allow multi-tasking on different pages
 */


public class Taskable implements Runnable{

	int count, pcount, x = 0, i = 0, runNum = 0;
	String[] commands = new String[50];
	MsgControl msgCntrl = new MsgControl();
	String cbId = "", temp, results, progressString = ("Command " + this.commands[x] + ":\t " + msgCntrl.Ret() + " cycle# " + count +" " + java.time.LocalTime.now());
	AnalysisResp analysis = new AnalysisResp();	
	Procbuild cmd = new Procbuild();
	File batch = new File(System.getProperty("user.dir") + "\\admin.bat");
	LogWriter logWriter = new LogWriter();

	float prog=0;

	boolean running = true;
	private static boolean disk = false;
	
	
	//TODO fill commands for each category
	//Get commands setup for if %100 disk usage detected
	
	
	public void writeLog() {
		logWriter.Writer(progressString, x);
	}
	public void writeLog(String append) {
		logWriter.Writer("Command " + this.commands[x] + ":\t " + msgCntrl.Ret() +" "+append+ " cycle# " + count +" " + java.time.LocalTime.now(), x);
	}

	/*
	 * Resets command string array + counter to prevent mirror cycling
	 */
	public void taskWipe() {
		i = 0;
	}
	
	public void task(String inQ) {		
		cbId = inQ;
		//i=0;
	
		//Create restore point (needs to be first!)
		if(cbId.indexOf("6") != -1 && (cbId.indexOf(".0") != 1 && cbId.indexOf(".1") != 1)) 
		{ 
			//The 7 is important, it specifies it as a "system" type restore point
			commands[i] = "wmic.exe /Namespace:\\\\root\\default Path SystemRestore Call CreateRestorePoint \"eLuo\", 100, 7";
			i++;
		}
		
		if(cbId.indexOf("###") != -1) 
		{
			disk = true;
		}
		//TODO add system restore and undo restore
		if(cbId.indexOf("1") != -1 && cbId.matches("\\d+\\.\\d{1}$") && cbId.indexOf(".0") != -1) 
		{
			commands[i] = "sfc";
			i++;
		}
		if(cbId.indexOf("2") != -1 && cbId.matches("\\d+\\.\\d{1}$") && cbId.indexOf(".0") != -1) 
		{	//Detect Windows Issues
			runNum += 35;
			commands[i] = "typeperf -si 5 -sc 2 \"\\LogicalDisk(_Total)\\% Idle Time\" "; //command work w/o null ptr err
			i++;
			commands[i] = "typeperf -si 5 -sc 1 \"\\LogicalDisk(_Total)\\Disk Transfers/sec\"";
			i++;
			commands[i] = "typeperf -si 5 -sc 2 \"\\PhysicalDisk(_Total)\\% Idle Time\" "; 
			i++;
			commands[i] = "typeperf -si 5 -sc 1 \"\\PhysicalDisk(_Total)\\Disk Transfers/sec\"";
			i++;
			commands[i] = "typeperf -si 5 -sc 5 \"\\PhysicalDisk(_Total)\\Current Disk Queue Length\"";
			i++;

			if(System.getProperty("os.name").equalsIgnoreCase("windows 10")) {
				System.out.println("Debug: Initiating win 10 commands l-30");
			}
			//Special command intended to help find %100 disk usage issue
		}
		
		if(cbId.indexOf("3") != -1 && cbId.matches("\\d+\\.\\d{1}$") && cbId.indexOf(".0") != -1) 
		{ //Delete Browser History
			commands[i] = "echo DelBro";
			i++;
			commands[i] = "Rundll32.exe inetcpl.cpl,ResetIEtoDefaults";
			i++;
		}
		
		if(cbId.indexOf("4") != -1 && cbId.matches("\\d+\\.\\d{1}$") && cbId.indexOf(".0") != -1) 
		{ //Windows Privacy commands 
			commands[i] = "echo WinPri";
			i++;
		}
		
		if(cbId.indexOf("5") != -1 && cbId.matches("\\d+\\.\\d{1}$") && cbId.indexOf(".0") != -1) 
		{ //Tune up commands
			runNum += 303;
			commands[i] = "chkdsk /f /r";
			i++;
			commands[i] = "netsh int ipv4 reset";
			i++;
			commands[i] = "netsh int ipv6 reset";
			i++;
			commands[i] = "netsh winsock reset";
			i++;
			commands[i] = "net stop SysMain"; //move to Win10 Issues
			i++;
			commands[i] = "rmdir /s /q C:\\Windows\\prefetch";
			i++;
			commands[i] = "sfc /scannow";
			i++;
			
			
			if(disk == true)
			{
				runNum += 1;
				commands[i] = "net stop spooler";
				i++;
			}
			 
			
			if(System.getProperty("os.name").equalsIgnoreCase("windows 10")) 
			{
				commands[i] = "powercfg /h off";
				i++;
			}
			else 
			{
				commands[i] = "powercfg.exe - hibernate off";
				i++;
			}
			
			
			if(System.getProperty("os.name").equalsIgnoreCase("Windows 10") || System.getProperty("os.name").equalsIgnoreCase("Windows 8")) 
			{
				runNum += 891;
				commands[i] = "DISM /Online /Cleanup-Image /RestoreHealth"; //move to Win10 Issues
				i++;
			}
			
			//Special command intended to help find %100 disk usage issue
			if(System.getProperty("os.name").equalsIgnoreCase("Windows 10") || System.getProperty("os.name").equalsIgnoreCase("Windows 8")) 
			{
				runNum += 12;
				commands[i] = "typeperf -si 5 -sc 5 \"\\LogicalDisk(_Total)\\% Idle Time\"";
				i++;
				commands[i] = "typeperf -si 5 -sc 5 \"\\LogicalDisk(_Total)\\Disk Transfers//sec\"";
				i++;
			}
		}
		
		//Use Restore point
		if(cbId.indexOf("7") != -1 && (cbId.indexOf(".0") != -1 && cbId.indexOf(".1") != 1)) 
		{ 
			//The 7 is important, it specifies it as a "system" type restore point 
			/*
			 * 0 Application install
			 * 1 Application Uninst
			 * 10 Driver inst
			 * 12 Modify Settings
			 * 13 Cancelled Operation
			 * 100 specifies system change beginning
			 * Returns S_OK if success
			 */
			commands[i] = "wmic.exe /Namespace:\\\\root\\default Path SystemRestore Call Restore \"eLuo\", 100, 7";
			i++;
		}
		
		//TODO setup winIssFix get the numbers fucking right
		if(cbId.indexOf("1.01") != -1) { //get started
			runNum += 1;
			commands[i] = "Get-AppxPackage *getstarted* | Remove-AppxPackage";
			i++;
		}
		if(cbId.indexOf("1.02") != -1) { //groove
			runNum += 1;
			commands[i] = "Get-AppxPackage *zunemusic* | Remove-AppxPackage -ea 0";
			i++;
		}if(cbId.indexOf("1.03") != -1) {//sports
			runNum += 1;
			commands[i] = "Get-AppxPackage *bingsports* | Remove-AppxPackage";
			i++;
		}if(cbId.indexOf("1.04") != -1) {//weather
			runNum += 1;
			commands[i] = "Get-AppxPackage *bingweather* | Remove-AppxPackage";
			i++;
		}
		if(cbId.indexOf("1.05") != -1) {//xbox
			runNum += 1;
			commands[i] = "Get-AppxPackage *xboxapp* | Remove-AppxPackage";
			i++;
		}
		if(cbId.indexOf("1.06") != -1) {//cortana
			runNum += 1;
			commands[i] = "reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Policies\\Microsoft\\Windows\\Windows Search\" /v AllowCortana /t REG_DWORD /d 0 /f";
			i++;
			runNum += 1;
			commands[i] = "reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Policies\\Microsoft\\Windows\\Windows Search\" /v AllowSearchToUseLocation /t REG_DWORD /d 0 /f";
			i++;
			runNum += 1;
			commands[i] = "reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Policies\\Microsoft\\Windows\\Windows Search\" /v BingSearchEnabled /t REG_DWORD /d 0 /f";
			i++;
			runNum += 1;
			commands[i] = "reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Policies\\Microsoft\\Windows\\Windows Search\" /v CortanaConsent /t REG_DWORD /d 0 /f";
			i++;
		}
		if(cbId.indexOf("1.07") != -1) {//skype
			runNum += 1;
			commands[i] = "Get-AppxPackage *skypeapp* | Remove-AppxPackage";
			i++;
		}
		if(cbId.indexOf("1.08") != -1) {//news
			runNum += 1;
			commands[i] = "Get-AppxPackage *bingnews* | Remove-AppxPackage";
			i++;
		}
		if(cbId.indexOf("1.09") != -1) {//get office
			runNum += 1;
			commands[i] = "Get-AppxPackage *officehub* | Remove-AppxPackage";
			i++;
		}
		if(cbId.indexOf("1.1") != -1) {//solitaire
			runNum += 1;
			commands[i] = "Get-AppxPackage *solitairecollection* | Remove-AppxPackage";
			i++;
		}
		if(cbId.indexOf("1.11") != -1) {//windows store
			runNum += 1;
			commands[i] = "Get-AppxPackage *windowsstore* | Remove-AppxPackage";
			i++;
		}
		if(cbId.indexOf("1.12") != -1) {//undo
			runNum += 1;
			commands[i] = "Get-AppxPackage -AllUsers| Foreach {Add-AppxPackage -DisableDevelopmentMode -Register \"$($_.InstallLocation)\\AppXManifest.xml\"}";
			i++;
		}
	}
	
	Taskable(){
		cbId = null;
	}
	
	/*protected InputStream AdminUp(){
		InputStream adminStream = null;
		
		try {
			adminStream = this.cmd.Send();
		}
		catch(NullPointerException e) {
			System.out.println("fail");
			return null;
		}
		
		return adminStream;
	}*/
	
	@Override
	public void run() 
	{

	}

}
