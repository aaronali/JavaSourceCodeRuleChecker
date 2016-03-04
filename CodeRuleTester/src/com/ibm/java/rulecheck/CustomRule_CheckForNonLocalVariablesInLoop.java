package com.ibm.java.rulecheck;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class CustomRule_CheckForNonLocalVariablesInLoop extends ARule { 
	
	public CustomRule_CheckForNonLocalVariablesInLoop() {
		super(); 
	}
 
	@Override
	public Map<File, ArrayList<ErrorLine>> getResults(Object... args) {
		return checkForVariablesCreatedInLoop((File) args[0]);
	}
	
  
	
	public static HashMap<File,ArrayList<ErrorLine>> checkForVariablesCreatedInLoop(File  file){
		 	String tmp;
		 	ArrayList<String> vars = new ArrayList<String>();
			File temp = file; 
			InputStream in;
			int bracesCount = 0;
			try {
				in = Files.newInputStream(temp.toPath());
				boolean insideMethod=true;
				BufferedReader tempreader = new BufferedReader(new InputStreamReader(in));
				System.out.println("\n"+file.getName());
				String lastVar = "";
				while ((tmp = tempreader.readLine()) != null) {  
				 if(tmp.contains("{")){
					 bracesCount++;
					 if(bracesCount==2){
						 insideMethod=true;
					 } 
				 }
				 if(tmp.contains("}")){
					 bracesCount--;
					 if(bracesCount==1){
						 insideMethod=false;
					 } 
				 }
			
 			 
			
			if(!insideMethod){ 
					if(tmp.contains("private")||tmp.contains("public")|| tmp.contains("protected")){
						if(!tmp.contains("{") && !tmp.trim().startsWith("/")){
							vars.add(tmp);
							System.out.println(tmp);
							
						}
					}
			}
				}
			HashMap<File,ArrayList<ErrorLine>> errs = new HashMap<File,ArrayList<ErrorLine>>();
			
			}catch (IOException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			} 
			
 	   HashMap<File,ArrayList<ErrorLine>> errs = new HashMap<File,ArrayList<ErrorLine>>();
//		  for(int i = 0 ; i< vars.size();i++)
//			  System.out.println(vars.get(i));
//		   
//			try {
//				 in = Files.newInputStream(file.toPath()); 
//				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//				String line = null;
//				int lineCount = 0;
//				int nestedLooop = 0; 
//				boolean inBraces = false;
//				boolean inLoop = false;
//				boolean isBracedLoop = false;
//				int oldNested = nestedLooop ; 
//				while ((line = reader.readLine()) != null) {
//					lineCount++;
//					  for (JavaLoop jl: JavaLoop.values())  {
//						  String s = line;
//						  while(s.contains(" ")){
//							  s=s.replace(" ", "");
//						  }
//						  if(s.trim().startsWith(jl.toString().toLowerCase())){
//							  inLoop=true; 
//						  } 
//					   }
//					  if(inLoop && line.contains("{")){
//						  nestedLooop++;  
//						  
//					  }
//					  if(inLoop && line.contains("}")){
//						  nestedLooop--;  
//						  if(nestedLooop==0)
//							  inLoop=false;
//					  }
//					  if(inLoop&& nestedLooop>0){ 
//						  if(line.replace(" ", "").contains("=new")){ 
//							  if(line.replace("=", " = ").replace("  ", " ").split("\\(")[0].split(" ").length >4){
//								 if(errs.containsKey(file)){
//									 errs.get(file).add(new ErrorLine(lineCount,line));
//								 }else{
//									 errs.put(file,new  ArrayList<ErrorLine>());
//									 errs.get(file).add(new ErrorLine(lineCount,line));
//								 }
//							  }
//						  }
//					 }
//				}
//			} catch (IOException e) {
//				System.err.println(e);
//			}

			return errs;
			 
		}
	

}
