/*
eLuo Software©
Created and developed by Nathaniel L. Kerr
*/

package eLuoSoftware;

import java.io.File;
import java.text.SimpleDateFormat;

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
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
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
	
	public void task(String inQ) throws NullPointerException{
		cbId = inQ;
	
		//Create restore point (needs to be first!)
		if(cbId.indexOf("6") != -1 && (cbId.indexOf(".0") != 1 && cbId.indexOf(".1") != 1)) 
		{ 
			//The 12 refers to system change
			//TODO change to powershell script for Win 10
			//TODO add enable restore script

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
			//TODO Restore and disk check feedback use Interactive in Tasks
			runNum += 35;
			commands[i] = "typeperf -si 5 -sc 2 \"\\LogicalDisk(_Total)\\% Idle Time\" "; 
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
		{ //Delete Browser History //TODO change to reset browser to factory def. IE, Chrome + Firefox
			commands[i] = "echo DelBro";
			i++;
			commands[i] = "Rundll32.exe inetcpl.cpl,ResetIEtoDefaults";
			i++;
		}
		
		//TODO reorder privacy has been removed from Cleaning		
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
			commands[i] = "net stop SysMain"; //TODO move to Win10 Issues Shows no notification
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
			 
			//TODO check if laptop 
			//Get-WMIObject -Class Win32_ComputerSystem -Property PCSystemType | Select-Object -ExpandProperty PCSystemType
			//If == 2 then laptop
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
				commands[i] = "DISM /Online /Cleanup-Image /RestoreHealth"; //TODO move to Win10 Issues
				i++;
			}
			
			//Special command intended to help find %100 disk usage issue
			//TODO duplicate?
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
		
		//TODO setup winIssFix get the numbers right
		if(cbId.indexOf("1.01") != -1) { //get started
			runNum += 2;
			commands[i] = "Get-AppxPackage *getstarted* | Remove-AppxPackage";
			i++;
			commands[i] = "echo Removed Getting Started package";
			i++;
		}
		if(cbId.indexOf("1.02") != -1) { //groove
			runNum += 2;
			commands[i] = "Get-AppxPackage *zunemusic* | Remove-AppxPackage -ea 0";
			i++;
			commands[i] = "echo Removed Groove Music package";
			i++;
		}if(cbId.indexOf("1.03") != -1) {//sports
			runNum += 2;
			commands[i] = "Get-AppxPackage *bingsports* | Remove-AppxPackage";
			i++;
			commands[i] = "echo Removed Bing Sports package";
			i++;
		}if(cbId.indexOf("1.04") != -1) {//weather
			runNum += 2;
			commands[i] = "Get-AppxPackage *bingweather* | Remove-AppxPackage";
			i++;
			commands[i] = "echo Removed Weather package";
			i++;
		}
		if(cbId.indexOf("1.05") != -1) {//xbox
			runNum += 2;
			commands[i] = "Get-AppxPackage *xboxapp* | Remove-AppxPackage";
			i++;
			commands[i] = "echo Removed Xbox Gaming package package";
			i++;
		}
		if(cbId.indexOf("1.06") != -1) {//cortana
			runNum += 8;
			commands[i] = "reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Policies\\Microsoft\\Windows\\Windows Search\" /v AllowCortana /t REG_DWORD /d 0 /f";
			i++;
			commands[i] = "echo Disabled Cortana package";
			i++;
			commands[i] = "reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Policies\\Microsoft\\Windows\\Windows Search\" /v AllowSearchToUseLocation /t REG_DWORD /d 0 /f";
			i++;
			commands[i] = "echo Disabled Search Location Use";
			i++;
			commands[i] = "reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Policies\\Microsoft\\Windows\\Windows Search\" /v BingSearchEnabled /t REG_DWORD /d 0 /f";
			i++;
			commands[i] = "echo Disabled Bing Search";
			i++;
			commands[i] = "reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Policies\\Microsoft\\Windows\\Windows Search\" /v CortanaConsent /t REG_DWORD /d 0 /f";
			i++;
			commands[i] = "echo Disabled Cortana Consent";
			i++;
		}
		if(cbId.indexOf("1.07") != -1) {//skype
			runNum += 2;
			commands[i] = "Get-AppxPackage *skypeapp* | Remove-AppxPackage";
			i++;
			commands[i] = "echo Removed Skype package";
			i++;
		}
		if(cbId.indexOf("1.08") != -1) {//news
			runNum += 2;
			commands[i] = "Get-AppxPackage *bingnews* | Remove-AppxPackage";
			i++;
			commands[i] = "echo Removed News package";
			i++;
		}
		if(cbId.indexOf("1.09") != -1) {//get office
			runNum += 2;
			commands[i] = "Get-AppxPackage *officehub* | Remove-AppxPackage";
			i++;
			commands[i] = "echo \"Removed Office Promotional package\"";
			i++;
		}
		if(cbId.indexOf("1.1") != -1) {//solitaire
			runNum += 2;
			commands[i] = "Get-AppxPackage *solitairecollection* | Remove-AppxPackage";
			i++;
			commands[i] = "echo Removed Solitaire Collection package";
			i++;
		}
		if(cbId.indexOf("1.11") != -1) {//windows store
			runNum += 2;
			commands[i] = "Get-AppxPackage *windowsstore* | Remove-AppxPackage";
			i++;
			commands[i] = "echo Removed Windows Store package";
			i++;
		}
		if(cbId.indexOf("1.12") != -1) {//undo
			runNum += 2;
			commands[i] = "Get-AppxPackage -AllUsers| Foreach {Add-AppxPackage -DisableDevelopmentMode -Register \"$($_.InstallLocation)\\AppXManifest.xml\"}";
			i++;
			commands[i] = "echo Restored Default Windows Packages \r\n***Some Privacy Settings may have been reset to Windows Default as well";
			i++;
		}
		
		
		//Privacy Panel Commands
		if(cbId.indexOf("2.01") != -1) { //PTP
			runNum += 2;
			commands[i] = "reg add \"HKEY_LOCAL_MACHINESOFTWARE\\Microsoft\\Windows\\CurrentVersion\\DeliveryOptimization\\Config\" /v DODownloadMode /t REG_DWORD /d 0 /f";
			i++;
			commands[i] = "echo Disabled Peer to Peer update downloads (only received from Microsoft servers now)";
			i++;
		}
		if(cbId.indexOf("2.02") != -1) { //Wifi Sense Hotspot Sharing
			runNum += 1;
			commands[i] = "reg add \"HKEY_LOCAL_MACHINE\\Software\\Microsoft\\PolicyManager\\default\\WiFi\\AllowWiFiHotSpotReporting\" /v value /t REG_DWORD /d 0 /f";
			i++;
			//wifi sense auto-connect turned off
			commands[i] = "reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\WcmSvc\\wifinetworkmanager\\config\" /v AutoConnectAllowedOEM /t REG_DWORD /d 0 /f";
			i++;
		}if(cbId.indexOf("2.03") != -1) {//Bing Search 
			runNum += 1;
			commands[i] = "reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Search\" /v BingSearchEnabled  /t REG_DWORD /d 0 /f";
			i++;
			
		}if(cbId.indexOf("2.04") != -1) {//Web Content Monitor
			runNum += 1;
			commands[i] = "reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\AppHost\" /v EnableWebContentEvaluation /t REG_DWORD /d 0 /f";
			i++;
		}
		if(cbId.indexOf("2.05") != -1) {//Ad ID
			runNum += 1;
			commands[i] = "reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\AdvertisingInfo\" /v Enabled /t REG_DWORD /d 0 /f";
			i++;
		}
		if(cbId.indexOf("2.06") != -1) {//Sync Center HKEY_CURRENT_USER\Software\Microsoft\Windows\CurrentVersion\SettingSync\Groups
			runNum += 4;
			//commands[i] = "reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Policies\\Microsoft\\Windows\\Windows Search\" /v AllowCortana /t REG_DWORD /d 0 /f";
			//i++;
			//commands[i] = "reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Policies\\Microsoft\\Windows\\Windows Search\" /v AllowSearchToUseLocation /t REG_DWORD /d 0 /f";
			//i++;
			//commands[i] = "reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Policies\\Microsoft\\Windows\\Windows Search\" /v BingSearchEnabled /t REG_DWORD /d 0 /f";
			//i++;
			//commands[i] = "reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Policies\\Microsoft\\Windows\\Windows Search\" /v CortanaConsent /t REG_DWORD /d 0 /f";
			//i++;
			commands[i] = "echo Coming Soon! Sync Center Removal";
			i++;
		}
		if(cbId.indexOf("2.07") != -1) 
		{//Telemetry
			runNum += 3;
			commands[i] = "reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Policies\\Microsoft\\Windows\\DataCollection\" /v AllowTelemetry /t REG_DWORD /d 0 /f";
			i++;
			commands[i] = "echo Disabled Windows Telemetry Services";
			i++;
			commands[i] = "Remove-Item -LiteralPath \"C:\\ProgramData\\Microsoft\\Diagnosis\" -Force -Recurse";
			i++;
			commands[i] = "echo Removed Gathered Data for Windows Telemetry";
			i++;
			
		}
		if(cbId.indexOf("2.08") != -1) //Smart Screen Data
		{
			runNum += 3;
			commands[i] = "Get-ScheduledTask SmartScreenSpecific | Disable-ScheduledTask";
			i++;
		}
		//TODO move this todo to a good place, but also have a final check running reg query on all registry values to ensure they have been altered as desired.
		if(cbId.indexOf("4") != -1 && cbId.matches("\\d+\\.\\d{1}$") && cbId.indexOf(".0") != -1) 
			{ //Windows Privacy commands 
			runNum += 1;
			//location is turned off
			commands[i] = "reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\CapabilityAccessManager\\ConsentStore\\location\" /t REG_SZ /d deny /f";
			i++;

				}
		
	}
	
	Taskable(){
		cbId = null;
	}
	
	
	@Override
	public void run() 
	{

	}

}
