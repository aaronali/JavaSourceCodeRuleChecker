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

public class CustomRule_CheckForVariablesCreatedInLoop extends ARule { 
	
	public CustomRule_CheckForVariablesCreatedInLoop() {
		super(); 
	}
 
	@Override
	public Map<File, ArrayList<ErrorLine>> getResults(Object... args) {
		return checkForVariablesCreatedInLoop((File) args[0]);
	}
	
	
	public static HashMap<File,ArrayList<ErrorLine>> checkForVariablesCreatedInLoop(File  file){
		 
		   HashMap<File,ArrayList<ErrorLine>> errs = new HashMap<File,ArrayList<ErrorLine>>();
		  
			try {
				InputStream in = Files.newInputStream(file.toPath()); 
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				String line = null;
				int lineCount = 0;
				int nestedLooop = 0; 
				boolean inBraces = false;
				boolean inLoop = false;
				boolean isBracedLoop = false;
				int oldNested = nestedLooop ; 
				while ((line = reader.readLine()) != null) {
					lineCount++;
					  for (JavaLoop jl: JavaLoop.values())  {
						  String s = line;
						  while(s.contains(" ")){
							  s=s.replace(" ", "");
						  }
						  if(s.trim().startsWith(jl.toString().toLowerCase())){
							  inLoop=true; 
						  } 
					   }
					  if(inLoop && line.contains("{")){
						  nestedLooop++;  
						  
					  }
					  if(inLoop && line.contains("}")){
						  nestedLooop--;  
						  if(nestedLooop==0)
							  inLoop=false;
					  }
					  if(inLoop&& nestedLooop>0){ 
						  if(line.replace(" ", "").contains("=new")){ 
							  if(line.replace("=", " = ").replace("  ", " ").split("\\(")[0].split(" ").length >4){
								 if(errs.containsKey(file)){
									 errs.get(file).add(new ErrorLine(lineCount,line));
								 }else{
									 errs.put(file,new  ArrayList<ErrorLine>());
									 errs.get(file).add(new ErrorLine(lineCount,line));
								 }
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
