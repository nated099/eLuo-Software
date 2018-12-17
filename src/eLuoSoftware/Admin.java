package eLuoSoftware;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
/*
 * Class to create admin file
 */
public abstract class Admin {
	private static String adminString = "echo off\r\n" + 
			"\r\n" + 
			"NET SESSION >nul 2>&1\r\n" + 
			"IF %ERRORLEVEL% EQU 0 (\r\n" + 
			"    GOTO RUNNER\r\n" + 
			") ELSE (\r\n" + 
			"    GOTO CHECKADMIN\r\n" + 
			")\r\n" + 
			"\r\n" + 
			":CHECKADMIN\r\n" + 
			"setlocal DisableDelayedExpansion\r\n" + 
			"set \"filePath=%~0\"\r\n" + 
			"for %%k in (%0) do set fileName=%%~nk\r\n" + 
			"set \"vbsPriv=%temp%\\OEgetPriv_%fileName%.vbs\"\r\n" + 
			"setlocal EnableDelayedExpansion\r\n" + 
			"NET FILE 1>NUL 2>NUL\r\n" + 
			"if '%errorlevel%' == '0' (goto ISADMIN) else (goto ESCALATE)\r\n" + 
			"\r\n" + 
			":ESCALATE\r\n" + 
			"if '%1'=='ELEV' (echo ELEV & shift /1 & goto ISADMIN)\r\n" + 
			"ECHO Set userAccCntrl = CreateObject^(\"Shell.Application\"^) > \"%vbsPriv%\"\r\n" + 
			"ECHO args = \"ELEV \" >> \"%vbsPriv%\"\r\n" + 
			"ECHO For Each strArg in WScript.Arguments >> \"%vbsPriv%\"\r\n" + 
			"ECHO args = args ^& strArg ^& \" \"  >> \"%vbsPriv%\"\r\n" + 
			"ECHO Next >> \"%vbsPriv%\"\r\n" + 
			"ECHO userAccCntrl.ShellExecute \"!filePath!\", args, \"\", \"runas\", 1 >> \"%vbsPriv%\"\r\n" + 
			"\"%SystemRoot%\\System32\\WScript.exe\" \"%vbsPriv%\" %*\r\n" + 
			"exit /B\r\n" + 
			"\r\n" + 
			":ISADMIN\r\n" + 
			"setlocal & pushd .\r\n" + 
			"cd /d %~dp0\r\n" + 
			"if '%1'=='ELEV' (del \"%vbsPriv%\" 1>nul 2>nul  &  shift /1)\r\n" + 
			"REM cmd /k\r\n" + 
			"\r\n" + 
			":RUNNER\r\n" + 
			"set \"progPath=%~dp0\"\r\n" + 
			"start \"eLuo Software\" \"%progPath%eLuo Software.exe\"\r\n" + 
			":END\r\n" + 
			"exit\r\n" + 
			"";

		
		public static boolean MakeAdmin() 
		{
	        File adminFile = new File(System.getProperty("user.dir") + "\\admin.bat");  
	        BufferedWriter buffWrite;
	        if(!adminFile.exists())
	        {
	            try 
	            {
	                adminFile.createNewFile();
	                adminFile.setWritable(true);
	            }
	            catch (IOException e) 
	            {
	                    System.out.println("Failure to create admin");
	            }
	        }
	        try 
	        {
	        	
	        	buffWrite = new BufferedWriter(new FileWriter(adminFile, true));
	            buffWrite.write(adminString);
	            buffWrite.close();
	            return true;
	        }
	        
	        catch (IOException e)
	        {
	        	System.out.println("Failure to initiate buffered writer for log");
	        	return false;
	        }
	    }
}
