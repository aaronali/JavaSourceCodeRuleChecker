package com.ibm.java.rulecheck;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomRule_CheckForFindingLoops extends ARule { 
	
	public CustomRule_CheckForFindingLoops() {
		super(); 
	}
 
	@Override
	public Map<File, ArrayList<ErrorLine>> getResults(Object... args) {
		return checkForFindingLoops((File) args[0]);
	}
	
	
	public static HashMap<File,ArrayList<ErrorLine>> checkForFindingLoops(File  file){
		 
		   HashMap<File,ArrayList<ErrorLine>> errs = new HashMap<File,ArrayList<ErrorLine>>();
		  
			try {
				InputStream in = Files.newInputStream(file.toPath()); 
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				String line = null;
				int lineCount = 0;
				int nestedLooop = 0; 
 
				while ((line = reader.readLine()) != null) {
					lineCount++;
					  for (JavaLoop jl: JavaLoop.values())  {
						  if(!line.trim().startsWith("*") && !line.trim().startsWith("/")){
							   if(line.contains(" "+jl.name().toLowerCase())){
								   if(!errs.containsKey(file)){
									   errs.put(file, new ArrayList<ErrorLine>());
								   } 
								   errs.get(file).add(new ErrorLine(lineCount,"**POSSIBILE LOOP TO CHECK   ->".concat(line)));
								 
							  } 
						  }
					   }
				}
					 
			} catch (IOException e) {
				System.err.println(e);
			}

			return errs;
			 
		}
	

}
