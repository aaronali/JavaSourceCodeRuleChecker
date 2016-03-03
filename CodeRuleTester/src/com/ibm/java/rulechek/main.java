package com.ibm.java.rulechek;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class main {


	static ArrayList<File> classList = new ArrayList<File>();
	
 
	/**
	 * Reads a file and returns a String containing all the readable file contents
	 * @param filename
	 * @return 
	 * @return String of file contents
	 */
	public  static String readFile(String filename){
		
		
		String  result = new String(); 
		Path file = Paths.get(filename); 
		try {
			InputStream in = Files.newInputStream(file); 
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while ((line = reader.readLine()) != null) {
				result = result.concat(line).concat("\n"); 
			}
		} catch (IOException e) {
			System.err.println(e);
		}
		return result;
	}
	
	 
	
	
	/**
	 * Returns an array with a string collection of the current listing in the path
	 * @param path
	 * @return
	 */
	public static Collection<String> getDirListing(String path){ 
		ArrayList<String> strings= new ArrayList<String>();
		Path dir =  Paths.get(path);
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
			for (Path file: stream) {
				strings.add(file.getFileName().toString()); 
				}
		} catch (IOException | DirectoryIteratorException e) {  
			System.out.println(e);
		}
		return strings; 
	}
	public static void  printFnames(String sDir){
		  File[] faFiles = new File(sDir).listFiles();
		  for(File file: faFiles){
		    if(file.getName().matches("^(.*?)")){
		      if(file.getName().endsWith(".java"))
					classList.add(file);
		    }
		    if(file.isDirectory()){
		      printFnames(file.getAbsolutePath());
		    }
		  }
		}
	
	
	public static HashMap<File,ArrayList<ErrorLine>> findStringInFile(File  file, String searchTerm){
		 
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
	
	
	
	
	
	public static void main(String...  arg){
		HashMap<File,ArrayList<ErrorLine>> errs = new HashMap<File, ArrayList<ErrorLine>>();
		HashMap<File,ArrayList<ErrorLine>> temp = new HashMap<File, ArrayList<ErrorLine>>();
		int errorCount = 0;
		ArrayList<IRule> rulesToCheck = new ArrayList<IRule>();
		
		rulesToCheck.add(new CustomRule_CheckForGC());
		rulesToCheck.add(new CustomRule_CheckForRuntimeException());
		rulesToCheck.add(new CustomRule_CheckForGeneralException());
		rulesToCheck.add(new CustomRule_CheckForSystemPrint());
		
		printFnames("//Users//aaronali//Documents/DICE//");
			for(File file: classList){ 
			  String s = readFile(file.getAbsolutePath());
			  for(int i=0;i<rulesToCheck.size();i++){
				 
			  	temp = (HashMap<File, ArrayList<ErrorLine>>) rulesToCheck.get(i).getResults(file);
			 	if(temp!=null && temp.size()>0){
			 		errs.put(file, temp.get(file));
			 		 
			 	}
			  } 
			}
			 
			
			
			
			for(File file:errs.keySet()){
				errorCount++;
				 System.out.println();
				 System.out.println("FileError:" + errorCount);
			 System.out.println(file.getName());
			 for(ErrorLine er:errs.get(file)){
				 System.out.println(er.lineNumber+ " " + er.lineText.trim());
			 }
			}
		}
	
	
	
		 
	}


	
 

