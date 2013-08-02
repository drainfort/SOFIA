package common.utils;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class ExecutionLogger {

	private static ExecutionLogger instance;
	
	protected final static Logger LOGGER = Logger.getLogger(ExecutionLogger.class
		      .getName());
	
	private static boolean loggerInitialize=false;
	
	public ExecutionLogger(){
		
	}
	
	public static ExecutionLogger getInstance(){
		if(instance == null){
			System.out.println("Nueva instancia");
			instance = new ExecutionLogger();
		}
		return instance;
	}
	
	public void printLog(String notice){
		LOGGER.info(notice);
	}
	
	public void initializeLogger (String resultFile, String instanceName){
		FileHandler fileTxt;
		try {
			if(!loggerInitialize){
				System.out.println("Entro");
				LOGGER.setUseParentHandlers(false);
				fileTxt = new FileHandler("./log/Log-execution-"+resultFile+".txt");
				SimpleFormatter formatterTxt = new SimpleFormatter();
				fileTxt.setFormatter(formatterTxt);
				LOGGER.addHandler(fileTxt);
				LOGGER.setLevel(Level.INFO);
				loggerInitialize=true;
			}
			LOGGER.info("Instance: "+instanceName);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
}
