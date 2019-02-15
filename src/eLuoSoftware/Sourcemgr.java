package eLuoSoftware;

import java.io.PrintStream;
import java.util.prefs.Preferences;

public class Sourcemgr {

	public String pwd() 
	{
		return (String) System.getProperty("user.dir");
	}
	
	public boolean isAdmin() //Used only once to set admin 
	{ 
	    Preferences prefs = Preferences.systemRoot();
	    PrintStream systemErr = System.err;
	    synchronized(systemErr)
	    {  
	        System.setErr(null);
	        try
	        {
	            prefs.put("foo", "bar"); // SecurityException on Windows
	            prefs.remove("foo");
	            prefs.flush(); // BackingStoreException on Linux
	            return true;
	        }catch(Exception e)
	        {
	            return false;
	        }finally
	        {
	            System.out.println(systemErr.toString());
	        }
	    }
	}
}
