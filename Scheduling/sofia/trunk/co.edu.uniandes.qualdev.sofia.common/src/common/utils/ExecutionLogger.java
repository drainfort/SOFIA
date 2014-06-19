package common.utils;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Class tha prints a Log file 
 * @author Jaime Romero
 */
public class ExecutionLogger {

	/** Instance of the logger - One object per Execution */
	private static ExecutionLogger instance;
	
	/** Logger of the class that let us print the information to the file */
	protected final static Logger LOGGER = Logger.getLogger(ExecutionLogger.class
		      .getName());
	
	/** Attribute to know if the logger is initialized */
	private static boolean loggerInitialize = false;
	
	/** Attribute to know if we need to use the logger*/
	private static boolean useLogger = false;
	
	/** Attribute to manage the output file */
	private static FileHandler fileHTML;
	
	
	// ---------------------------------------
	// Constructor
	// ---------------------------------------
	public ExecutionLogger(){
		
	}
	
	// ---------------------------------------
	// Methods
	// ---------------------------------------
	
	/**
	 * Returns the unique instance of the class
	 * @return instance
	 */
	public static ExecutionLogger getInstance(){
		if(instance == null){
			instance = new ExecutionLogger();
		}
		return instance;
	}
	
	/**
	 * Print message into the log
	 */
	public void printLog(String notice){
		if(useLogger)
			LOGGER.info(notice);
	}
	
	/**
	 * Initialize the logger
	 * @param resultFile - name of the output file
	 * @param userId - id of the user that is making the execution
	 */
	public void initializeLogger (String resultFile, String userId){		
		try {
			if(useLogger){
				if(!loggerInitialize){
					LOGGER.setUseParentHandlers(false);
					fileHTML = new FileHandler("./results/"+ userId +"/Log-execution-"+resultFile+".html");
					
					LOGGER.setLevel(Level.INFO);
				    // Create HTML Formatter
					HtmlFormatter formatterHTML = new HtmlFormatter();
				    fileHTML.setFormatter(formatterHTML);
				    LOGGER.addHandler(fileHTML);
					
					loggerInitialize=true;
				}
				LOGGER.info("User id: "+userId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Close the output file
	 */
	public static void stopHandler(){
		fileHTML.close();
	}

	// ---------------------------------------
	// Getters and setters
	// ---------------------------------------
	
	public static boolean isUseLogger() {
		return useLogger;
	}
	
	public static void setUseLogger(boolean useLogger) {
		ExecutionLogger.useLogger = useLogger;
	}
	
	
	
}
