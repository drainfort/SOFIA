package common.utils;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class ExecutionLogger {

	private static ExecutionLogger instance;
	
	protected final static Logger LOGGER = Logger.getLogger(ExecutionLogger.class
		      .getName());
	
	private static boolean loggerInitialize = false;
	
	private static boolean useLogger = false;
	
	public ExecutionLogger(){
		
	}
	
	public static ExecutionLogger getInstance(){
		if(instance == null){
			instance = new ExecutionLogger();
		}
		return instance;
	}
	
	public void printLog(String notice){
		if(useLogger)
			LOGGER.info(notice);
	}
	
	public void initializeLogger (String resultFile, String instanceName){
		FileHandler fileTxt;
		FileHandler fileHTML;
		try {
			if(useLogger){
				if(!loggerInitialize){
					LOGGER.setUseParentHandlers(false);
					fileTxt = new FileHandler("./log/Log-execution-"+resultFile+".txt");
					fileHTML = new FileHandler("./results/Om_TT/Log-execution-"+resultFile+".html");
					
					LOGGER.setLevel(Level.INFO);

				    // Create txt Formatter
				    SimpleFormatter formatterTxt = new SimpleFormatter();
					fileTxt.setFormatter(formatterTxt);
					LOGGER.addHandler(fileTxt);

				    // Create HTML Formatter
					HtmlFormatter formatterHTML = new HtmlFormatter();
				    fileHTML.setFormatter(formatterHTML);
				    LOGGER.addHandler(fileHTML);
					
					loggerInitialize=true;
				}
				LOGGER.info("Instance: "+instanceName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public static boolean isUseLogger() {
		return useLogger;
	}

	public static void setUseLogger(boolean useLogger) {
		ExecutionLogger.useLogger = useLogger;
	}
	
	
	
}
