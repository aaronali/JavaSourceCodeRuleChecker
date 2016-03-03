package com.ibm.java.rulechek;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomRule_CheckForGeneralException extends ARule {

	public CustomRule_CheckForGeneralException() {
		super(); 
	}
 
	@Override
	public Map<File, ArrayList<ErrorLine>> getResults(Object... args) {
		return checkForGeneralException((File)args[0], "new Exception",  "throws Exception");
	}
	
	
	public static HashMap<File,ArrayList<ErrorLine>> checkForGeneralException(File  file, String... searchTerms){
		 
		
		   HashMap<File,ArrayList<ErrorLine>> errs = new HashMap<File,ArrayList<ErrorLine>>();
		  
			try {
				InputStream in = Files.newInputStream(file.toPath()); 
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				String line = null;
				int lineCount = 0;
				while ((line = reader.readLine()) != null) {
					lineCount++; 
					 if(line.contains(searchTerms[0]) || line.contains(searchTerms[1])){ 
						 if(errs.containsKey(file)){
							 errs.get(file).add(new ErrorLine(lineCount,line));
						 }else{
							 errs.put(file,new  ArrayList<ErrorLine>());
							 errs.get(file).add(new ErrorLine(lineCount,line));
						 }
					 }
				}
			} catch (IOException e) {
				System.err.println(e);
			}

			return errs;
			 
		}
	

}
