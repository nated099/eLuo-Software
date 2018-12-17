package eLuoSoftware;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.jupiter.api.Test;

class logEntryTest {
	String caseFile;
	int counter = 0;
	String[] caseString = new String[900];
	
	@Test
	void test() {
		LogWriter junit = new LogWriter();
		for(int i=0;i<100000;i++) {
			junit.Writer("test", i);
		}
		
		
	}
	//method uses class caseFile variable to open and read file
		public String readFile() {
			
			File pathname = new File(caseFile);
			BufferedReader fileReader;
			StringBuilder retString = new StringBuilder();
			
			//returns null and notifies user that the path is invalid
	        if(!pathname.exists())
	        {
		        System.out.println("need valid lasermaze path");
		        return null;
	        }
	        else {
	        	try {
					fileReader = new BufferedReader(new FileReader(pathname));
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					return null;
				}
	        	
	        	try {
	        		//Increases counter variable for each line in the file not equal to null and appends each line to the returned string
					while((caseString[counter] = fileReader.readLine()) != null) {
						//System.out.println(counter + " "+ caseString[counter]);
						retString.append(caseString[counter] + "\r\n");
						if(!(caseString[counter].isEmpty()))
							counter++;
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
	        	
	        	
	        	try {
					fileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        	return retString.toString();
	        }
		}
		//Method receives file, sets the caseFile variable and runs readFile method
		public void inputFile(String argFile) {
			caseFile = argFile;
			readFile();
		}
		//method that returns the number of lines in the file
		public int retCount() {
			return counter;
		}
		
	}



