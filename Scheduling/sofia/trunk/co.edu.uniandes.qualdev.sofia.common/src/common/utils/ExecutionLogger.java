package common.utils;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ExecutionLogger {
	static private FileHandler fileTxt;
	static private SimpleFormatter formatterTxt;

	static public void setup() throws IOException {

		// Get the global logger to configure it
		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

		logger.setLevel(Level.INFO);
		fileTxt = new FileHandler("./log/Log-execution-"+System.currentTimeMillis()+".txt");

		// Create txt Formatter
		formatterTxt = new SimpleFormatter();
		fileTxt.setFormatter(formatterTxt);
		logger.addHandler(fileTxt);

	}
}