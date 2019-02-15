/*
eLuo Software©
Created and developed by Nathaniel L. Kerr
*/

package eLuoSoftware;

import java.awt.EventQueue;

public class eLuo 
{

	public static void main(String[] args) 
	{
		Smgr window = new Smgr();
		NotifyPop license = new NotifyPop();
		
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					//Display license agreement
					license.License();
					window.Display();
				} catch(NullPointerException n) {
					window.Display();
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
		});
	}
}

//TODO perhaps cause the process to wait for current stream to end/next stream to start to continue.
//TODO reorganize, OOP
//TODO implement command caller class, can send results to string for later use
//TODO reorder GUI, advanced and basic
//TODO if we're going to modify the registry it may be a good idea to back it up first. Might need a folder for logs + registry backups


//TODO adjust options and reactions based on OS (i.e. Linux)
//TODO add language support? may require Locale object
