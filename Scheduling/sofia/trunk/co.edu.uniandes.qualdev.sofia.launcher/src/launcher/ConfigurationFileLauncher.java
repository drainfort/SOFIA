package launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import launcher.adapters.ConfigurationAdapter;
import launcher.vos.AlgorithmConfigurationVO;

public class ConfigurationFileLauncher {

	// ----------------------------------------------
	// Constants
	// ----------------------------------------------
	
	public static final String CONFIGURATION_FILE = "./data/config/configuration.properties";
	
	// ----------------------------------------------
	// Constants
	// ----------------------------------------------
	
	private String initialSolutionBuilder;
	private String structure;
	private String neighborCalculator;
	private String modifier;
	private String control;
	private String gammaCalculator;
	
	private String considerTravelTimes;
	private String considerSetupTimes;
	
	private String showConfigurationTable;
	private String showInitialSolutions;
	private String showFinalSolutions;
	private String showLog;
	
	// ----------------------------------------------
	// Methods
	// ----------------------------------------------
	
	public void launchSofia() throws IOException{
		Properties data = loadPropertiesFile();
		
		// Loading the instances to execute
		ArrayList<String> instancesToExecute = new ArrayList<String>();
		
		// 04x04
		for (int i = 1; i <= 10; i++) {
			String key = "04x04_";
			if(i != 10) key += "0";
			key += i;
			
			if(data.getProperty(key).equals("true")){
				instancesToExecute.add(key);
			}
		}
		
		// 05x05
		for (int i = 1; i <= 10; i++) {
			String key = "05x05_";
			if(i != 10) key += "0";
			key += i;
			
			if(data.getProperty(key).equals("true")){
				instancesToExecute.add(key);
			}
		}
		
		// 07x07
		for (int i = 1; i <= 10; i++) {
			String key = "07x07_";
			if(i != 10) key += "0";
			key += i;
			
			if(data.getProperty(key).equals("true")){
				instancesToExecute.add(key);
			}
		}
		
		// 10x10
		for (int i = 1; i <= 10; i++) {
			String key = "10x10_";
			if(i != 10) key += "0";
			key += i;
			
			if(data.getProperty(key).equals("true")){
				instancesToExecute.add(key);
			}
		}
		
		// 15x15
		for (int i = 1; i <= 10; i++) {
			String key = "15x15_";
			if(i != 10) key += "0";
			key += i;
			
			if(data.getProperty(key).equals("true")){
				instancesToExecute.add(key);
			}
		}
		
		// 20x20
		for (int i = 1; i <= 10; i++) {
			String key = "20x20_";
			if(i != 10) key += "0";
			key += i;
			
			if(data.getProperty(key).equals("true")){
				instancesToExecute.add(key);
			}
		}
		
		// Loading the solution method configuration
		initialSolutionBuilder = data.getProperty("initialSolutionBuilder");
		structure = data.getProperty("structure");
		neighborCalculator = data.getProperty("neighborCalculator");
		gammaCalculator = data.getProperty("gammaCalculator");
		modifier = data.getProperty("modifier");
		control = data.getProperty("control");
		
		Properties algorithmConfiguration = new Properties();
		algorithmConfiguration.setProperty("scheduling.initialSolutionBuilder", initialSolutionBuilder);
		algorithmConfiguration.setProperty("scheduling.structureFactory", structure);
		algorithmConfiguration.setProperty("scheduling.neighborCalculator", neighborCalculator);
		algorithmConfiguration.setProperty("scheduling.modifier", modifier);
		algorithmConfiguration.setProperty("scheduling.control", control);
		algorithmConfiguration.setProperty("scheduling.control", control);
		
		// Loading selected betas
		considerTravelTimes = data.getProperty("betas.considerTravelTimes");
		considerSetupTimes = data.getProperty("betas.considerSetupTimes");
		
		String gammaBKS = null;
		if(gammaCalculator.equals(ConfigurationAdapter.CMAX)){
			gammaBKS = ConfigurationAdapter.CMAX_BKS;
		}else if(gammaCalculator.equals(ConfigurationAdapter.MEAN_FLOW_TIME)){
			gammaBKS = ConfigurationAdapter.MEAN_FLOW_TIME_BKS;
		}
		
		String currentBks = "gamma." + gammaBKS + ".bks.";
		
		boolean travelTimesSelected = Boolean.parseBoolean(considerTravelTimes);
		boolean setupTimesSelected = Boolean.parseBoolean(considerSetupTimes);
		
		if(!travelTimesSelected && !setupTimesSelected){
			currentBks += "om";
		}
		else if(!travelTimesSelected && setupTimesSelected){
			currentBks += "om.s";
		}
		else if(travelTimesSelected && !setupTimesSelected){
			currentBks += "om.tt";
		}
		else if(travelTimesSelected && setupTimesSelected){
			currentBks += "om.tt.s";
		}
		
		System.out.println("currentBks: " + currentBks);
		
		// Loading the results report configuration
		showConfigurationTable = data.getProperty("report.consolidationTable");
		showInitialSolutions = data.getProperty("report.gantt.initialsolutions");
		showFinalSolutions = data.getProperty("report.gantt.bestsolutions");
		showLog = data.getProperty("report.gantt.log");
		
		algorithmConfiguration.setProperty("report.consolidationTable", showConfigurationTable);
		algorithmConfiguration.setProperty("report.gantt.initialsolutions", showInitialSolutions);
		algorithmConfiguration.setProperty("report.gantt.bestsolutions", showFinalSolutions);
		algorithmConfiguration.setProperty("report.gantt.log", showLog);
		
		// Executing instances
		for (String instance : instancesToExecute) {
			
		}
		
	}
	
	/**
	 * Loads the information contained in the properties file located
	 * in the path given in the constant CONFIGURATION_FILE
	 * 
	 * @return data. A properties object containing the information in the file
	 * @throws IOException If any input/output error occurs
	 */
	private Properties loadPropertiesFile() throws IOException{
		Properties data = new Properties();
		
		File propertiesFile = new File(CONFIGURATION_FILE);
		FileInputStream in = new FileInputStream( propertiesFile );
		
		data.load( in );
        in.close( );
        
		return data;
	}
	
	// ----------------------------------------------
	// Main
	// ----------------------------------------------
	
	/**
	 * Main method. Execution start
	 * @param args. Execution arguments
	 */
	public static void main(String[] args) {
		ConfigurationFileLauncher launcher = new ConfigurationFileLauncher();
		try {
			launcher.launchSofia();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
