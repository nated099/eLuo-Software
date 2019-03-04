/*
eLuo Software©
Created and developed by Nathaniel L. Kerr
*/

package eLuoSoftware;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;

/*
 * Model-hybrid class that handles tasks run by the main class, eLuo
 * 
 * Runnable, may be run as it's own thread to allow multi-tasking on different pages
 */


public class Taskable implements Runnable{

	int count, pcount, x = 0, i = 0, runNum = 0, restoreId;
	float prog=0;
	MsgControl msgCntrl = new MsgControl();
	Hashtable<Integer, String[]> commands = new Hashtable<>();
	String temp, results, progressString = ("Command " + commands.get(x) + ":\t " + msgCntrl.Ret() + " cycle# " + count +" " + java.time.LocalTime.now());
	AnalysisResp analysis = new AnalysisResp();	
	Procbuild cmd = new Procbuild();
	File batch = new File(System.getProperty("user.dir") + "\\admin.bat");
	LogWriter logWriter = new LogWriter();
	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	boolean running = true;
	ArrayList<String> tasks = new ArrayList<String>();
	
	
	//TODO fill commands for each category
	//Get commands setup for if %100 disk usage detected
	
	public void writeLog() 
	{
		logWriter.Writer(progressString, x);
	}
	public void writeLog(String append) 
	{
		logWriter.Writer("Command " + commands.get(x) + ":\t " + msgCntrl.Ret() +" "+append+ " cycle# " + count +" " + java.time.LocalTime.now(), x);
	}

	/*
	 * Resets command string array + counter to prevent mirror cycling
	 */
	public void taskWipe() 
	{
		i = 0;
	}
	
	public void task(ArrayList<String> inQ) throws NullPointerException{
		tasks = inQ;
		//cbId = inQ;
	
		//Create restore point (needs to be first!)
		
		for(int i = 0; i < inQ.size(); i++) {
			//CLEAN
			if(inQ.get(i).equalsIgnoreCase("chkbxRst")) {
				commands.put(i, new String[] {"echo Ensuring restore points are enabled...", "Enable-ComputerRestore -drive \"c:\\\"", "echo Creating Restore Point", "Checkpoint-Computer -Description \"eLuo\" -RestorePointType \"MODIFY_SETTINGS\"",
				"echo Restore Point Created. Cannot create another from this app for 24 hours."});
			}
			if(inQ.get(i).equalsIgnoreCase("chkbxRunVir")) {
				commands.put(i, new String[] {"echo virus removal not created yet"});
			}
			if(inQ.get(i).equalsIgnoreCase("chkbxWinIss")) {//TODO get this checkbox resolved after multithreading expansion
				//Detect Windows Issues
				//TODO Restore and disk check feedback use Interactive in Tasks
				runNum += 35;
				commands.put(i, new String[] {"typeperf -si 5 -sc 2 \"\\LogicalDisk(_Total)\\% Idle Time\"", "typeperf -si 5 -sc 1 \"\\LogicalDisk(_Total)\\Disk Transfers/sec\"",
						"typeperf -si 5 -sc 2 \"\\PhysicalDisk(_Total)\\% Idle Time\"", "typeperf -si 5 -sc 2 \"\\PhysicalDisk(_Total)\\% Idle Time\"", "typeperf -si 5 -sc 2 \"\\PhysicalDisk(_Total)\\% Idle Time\"",
						"typeperf -si 5 -sc 1 \"\\PhysicalDisk(_Total)\\Disk Transfers/sec\"", "typeperf -si 5 -sc 5 \"\\PhysicalDisk(_Total)\\Current Disk Queue Length\""}); 
	
				if(System.getProperty("os.name").equalsIgnoreCase("windows 10")) {
					System.out.println("Debug: Initiating win 10 commands l-30");
				}
			}
			if(inQ.get(i).equalsIgnoreCase("chkbxDelBro")) {
				//Delete Browser History //TODO change to reset browser to factory def. IE, Chrome + Firefox
				commands.put(i, new String[] {"echo DelBro", "Rundll32.exe inetcpl.cpl,ResetIEtoDefaults" });
			}	
			//TU if Win10
			if(inQ.get(i).equalsIgnoreCase("chkbxTU") && (System.getProperty("os.name").equalsIgnoreCase("Windows 10") || System.getProperty("os.name").equalsIgnoreCase("Windows 8"))) {
				runNum += 1195;
				//TODO check if laptop 
				//Get-WMIObject -Class Win32_ComputerSystem -Property PCSystemType | Select-Object -ExpandProperty PCSystemType
				//If == 2 then laptop
				
				commands.put(i, new String[] {"chkdsk /f /r", "netsh int ipv4 reset", "netsh int ipv6 reset", "netsh winsock reset", "net stop SysMain",
						"rmdir /s /q C:\\Windows\\prefetch", "echo System File Check commencing. Please wait.", "sfc /scannow", "DISM /Online /Cleanup-Image /RestoreHealth", "powercfg /h off"});
	 			if(System.getProperty("os.name").equalsIgnoreCase("Windows 10") || System.getProperty("os.name").equalsIgnoreCase("Windows 8")) 
				{
					runNum += 891;
					commands.put(i, new String[] {"" }); //TODO move to Win10 Issues
				}
			}//TU if < Win10
			else if(inQ.get(i).equalsIgnoreCase("chkbxTU")) {
				runNum += 304;
				commands.put(i, new String[] {"chkdsk /f /r", "netsh int ipv4 reset", "netsh int ipv6 reset", "netsh winsock reset", "net stop SysMain",
						"rmdir /s /q C:\\Windows\\prefetch", "echo System File Check commencing. Please wait.", "sfc /scannow", "powercfg.exe - hibernate off"});
			}
			if(inQ.get(i).equalsIgnoreCase("chkbxUndoRest")) {
				commands.put(i, new String[] {"restore-computer -restorepoint " + restoreId, "Beginning System Restore..."});
			}//WINISS
			if(inQ.get(i).equalsIgnoreCase("wCbGetStarted")) {
				runNum += 2;
				commands.put(i, new String[] {"Get-AppxPackage *getstarted* | Remove-AppxPackage", "echo Removed Getting Started package"});
			}
			if(inQ.get(i).equalsIgnoreCase("wCbGroove")) {
				runNum += 2;
				commands.put(i, new String[] {"Get-AppxPackage *zunemusic* | Remove-AppxPackage -ea 0", "echo Removed Groove Music package"});
			}
			if(inQ.get(i).equalsIgnoreCase("wCbSports")) {
				runNum += 2;
				commands.put(i, new String[] {"Get-AppxPackage *bingsports* | Remove-AppxPackage", "echo Removed Bing Sports package"});
			}
			if(inQ.get(i).equalsIgnoreCase("wCbWeather")) {
				runNum += 2;
				commands.put(i, new String[] {"Get-AppxPackage *bingweather* | Remove-AppxPackage", "echo Removed Weather package"});
			}
			if(inQ.get(i).equalsIgnoreCase("wCbXbx")) {
				runNum += 2;
				commands.put(i, new String[] {"Get-AppxPackage *xboxapp* | Remove-AppxPackage", "echo Removed Xbox Gaming package package"});
				
			}
			if(inQ.get(i).equalsIgnoreCase("wCbCortana")) {
				runNum += 8;
				commands.put(i, new String[] {"reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Policies\\Microsoft\\Windows\\Windows Search\" /v AllowCortana /t REG_DWORD /d 0 /f", "echo Disabled Cortana package",
						"reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Policies\\Microsoft\\Windows\\Windows Search\" /v AllowSearchToUseLocation /t REG_DWORD /d 0 /f", "echo Disabled Search Location Use", 
						"reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Policies\\Microsoft\\Windows\\Windows Search\" /v BingSearchEnabled /t REG_DWORD /d 0 /f", "echo Disabled Bing Search", 
						"reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Policies\\Microsoft\\Windows\\Windows Search\" /v CortanaConsent /t REG_DWORD /d 0 /f", "echo Disabled Cortana Consent"});
			}
			if(inQ.get(i).equalsIgnoreCase("wCbSkype")) {
				runNum += 2;
				commands.put(i, new String[] {"Get-AppxPackage *skypeapp* | Remove-AppxPackage", "echo Removed Skype package"});
				
			}
			if(inQ.get(i).equalsIgnoreCase("wCbNews")) {
				runNum += 2;
				commands.put(i, new String[] {"Get-AppxPackage *bingnews* | Remove-AppxPackage", "echo Removed News package"});
			}
			if(inQ.get(i).equalsIgnoreCase("wCbOffice")) {
				runNum += 2;
				commands.put(i, new String[] {"Get-AppxPackage *officehub* | Remove-AppxPackage", "echo \"Removed Office Promotional package\"" });	
			}
			if(inQ.get(i).equalsIgnoreCase("wCbSolitaire")) {
				runNum += 2;
				commands.put(i, new String[] {"Get-AppxPackage *solitairecollection* | Remove-AppxPackage", "echo Removed Solitaire Collection package" });
			}
			if(inQ.get(i).equalsIgnoreCase("wCbStore")) {
				runNum += 2;
				commands.put(i, new String[] {"Get-AppxPackage *windowsstore* | Remove-AppxPackage", "echo Removed Windows Store package"});
			}
			if(inQ.get(i).equalsIgnoreCase("wCbWup")) {
				runNum += 3;
				commands.put(i, new String[] {"reg add HKEY_LOCAL_MACHINE\\SOFTWARE\\Policies\\Microsoft\\Windows\\WindowsUpdate\\AU /v NoAutoUpdate\r\n" + 
						"/t REG_DWORD /d 1 /f", "reg add HKEY_LOCAL_MACHINE\\SYSTEM\\CurrentControlSet\\Services\\WaaSMedicSvc /v Start /t REG_DWORD /d 4 /f",
						"echo Disabled Windows Updates and Windows Update Medic Service"});
			}
			if(inQ.get(i).equalsIgnoreCase("wCbUndo")) {
				runNum += 5;
				commands.put(i, new String[] {"Get-AppxPackage -AllUsers| Foreach {Add-AppxPackage -DisableDevelopmentMode -Register \"$($_.InstallLocation)\\AppXManifest.xml\"}", 
						"echo Restored Default Windows Packages \r\n***Some Privacy Settings may have been reset to Windows Default as well", 
						"reg add HKEY_LOCAL_MACHINE\\SOFTWARE\\Policies\\Microsoft\\Windows\\WindowsUpdate\\AU /v NoAutoUpdate /t REG_DWORD /d 0 /f",
						"reg add HKEY_LOCAL_MACHINE\\SYSTEM\\CurrentControlSet\\Services\\WaaSMedicSvc /v Start /t REG_DWORD /d 2 /f", "echo Re-Enabled All Windows Packages and Defaults",
						"echo Disabled Peer to Peer update downloads (only received from Microsoft servers now)"});
				
			}
			//PRIV
			if(inQ.get(i).equalsIgnoreCase("chkbxPTP")) {//Peer to peer update downloads
				runNum += 2;
				commands.put(i, new String[] {"reg add \"HKEY_LOCAL_MACHINESOFTWARE\\Microsoft\\Windows\\CurrentVersion\\DeliveryOptimization\\Config\" /v DODownloadMode /t REG_DWORD /d 0 /f", "echo Disabled Peer To Peer Updating"});
			}
			if(inQ.get(i).equalsIgnoreCase("chkbxWifiSense")) { //Wifi Sense Hotspot Sharing 			//wifi sense auto-connect turned off
				runNum += 1;
				commands.put(i, new String[] {"reg add \"HKEY_LOCAL_MACHINE\\Software\\Microsoft\\PolicyManager\\default\\WiFi\\AllowWiFiHotSpotReporting\" /v value /t REG_DWORD /d 0 /f", 
						"reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\WcmSvc\\wifinetworkmanager\\config\" /v AutoConnectAllowedOEM /t REG_DWORD /d 0 /f", "echo Disabled Wifi Sense Hotspot Sharing and Wifi sense auto-connect"});
			}
			if(inQ.get(i).equalsIgnoreCase("chkbxBSearch")) { //Bing Search
				runNum += 1;
				commands.put(i, new String[] {"reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Search\" /v BingSearchEnabled  /t REG_DWORD /d 0 /f", "echo Disabled Bing Search" });

			}
			if(inQ.get(i).equalsIgnoreCase("chkbxWWebCont")) { //Web Content Monitor
				runNum += 1;
				commands.put(i, new String[] {"reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\AppHost\" /v EnableWebContentEvaluation /t REG_DWORD /d 0 /f", "echo Disabled Windows Web Content Monitor" });

			}
			if(inQ.get(i).equalsIgnoreCase("chkbxWAdInf")) { //Microsoft Ad ID's
				runNum += 1;
				commands.put(i, new String[] {"reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\AdvertisingInfo\" /v Enabled /t REG_DWORD /d 0 /f", "echo Disabled Microsoft Ad Identifier" });

			}
			if(inQ.get(i).equalsIgnoreCase("chkbxWSync")) {//Sync Center HKEY_CURRENT_USER\Software\Microsoft\Windows\CurrentVersion\SettingSync\Groups //TODO finish Sync Center disabler
				runNum += 4;
				//commands.put(i, new String[] {"reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Policies\\Microsoft\\Windows\\Windows Search\" /v AllowCortana /t REG_DWORD /d 0 /f" });
				//commands.put(i, new String[] {"reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Policies\\Microsoft\\Windows\\Windows Search\" /v AllowSearchToUseLocation /t REG_DWORD /d 0 /f" });
				//commands.put(i, new String[] {"reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Policies\\Microsoft\\Windows\\Windows Search\" /v BingSearchEnabled /t REG_DWORD /d 0 /f" });
				//commands.put(i, new String[] {"reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Policies\\Microsoft\\Windows\\Windows Search\" /v CortanaConsent /t REG_DWORD /d 0 /f" });
				commands.put(i, new String[] {"echo Coming Soon! Sync Center Removal" });
			}
			if(inQ.get(i).equalsIgnoreCase("chkbxWTelem")) { //Windows Telemetry + location
				runNum += 3;
				commands.put(i, new String[] {"reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Policies\\Microsoft\\Windows\\DataCollection\" /v AllowTelemetry /t REG_DWORD /d 0 /f", "echo Disabled Windows Telemetry Services",
						"Remove-Item -LiteralPath \"C:\\ProgramData\\Microsoft\\Diagnosis\" -Force -Recurse", "reg add \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\CapabilityAccessManager\\ConsentStore\\location\" /t REG_SZ /d deny /f", 
						"echo Removed Gathered Data for Windows Telemetry and Disabled Location Services"});
				runNum += 1;
				//location is turned off
				commands.put(i, new String[] {});
			}
			if(inQ.get(i).equalsIgnoreCase("chkbxSsd")) { //Smart Screen Data
				runNum += 3;
				commands.put(i, new String[] {"Get-ScheduledTask SmartScreenSpecific | Disable-ScheduledTask", "echo Disabled Windows Smart Screen Data" });
			}
			
			//Added checkboxes appended here until better method is resolved. Add to wCbUndo as needed for new disablers
						
		}
		i=0;
		//TODO Final check running reg query on all registry values to ensure they have been altered as desired.		
	}
	
	@Override
	public void run() 
	{
	}

}
