package com.ibm.java.rulechek;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public interface IRule {

	
	public Map<File, ArrayList<ErrorLine>> getResults(Object... args);
}
