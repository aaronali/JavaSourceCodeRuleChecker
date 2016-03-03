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

public class CustomRule_CheckForRuntimeException extends ARule {

	public CustomRule_CheckForRuntimeException() {
		super(); 
	}
 
	@Override
	public Map<File, ArrayList<ErrorLine>> getResults(Object... args) {
		return checkForRuntimeException((File)args[0], "new RuntimeException");
	}
	
	
	public static HashMap<File,ArrayList<ErrorLine>> checkForRuntimeException(File  file, String searchTerm){
		 
		   HashMap<File,ArrayList<ErrorLine>> errs = new HashMap<File,ArrayList<ErrorLine>>();
		  
			try {
				InputStream in = Files.newInputStream(file.toPath()); 
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				String line = null;
				int lineCount = 0;
				while ((line = reader.readLine()) != null) {
					lineCount++;
					 if(line.contains(searchTerm)){ 
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
