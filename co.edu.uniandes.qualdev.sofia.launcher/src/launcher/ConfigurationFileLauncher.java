package launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import chart.printer.ChartPrinter;
import common.utils.ExecutionLogger;
import common.utils.ExecutionResults;
import algorithm.SchedulingAlgorithm;
import algorithm.impl.TrajectoryBasedAlgorithm;
import launcher.adapters.ConfigurationAdapter;

public class ConfigurationFileLauncher {

	// ----------------------------------------------
	// Constants
	// ----------------------------------------------
	
	public static final String CONFIGURATION_FILE = "./config/configuration.properties";
	
	// ----------------------------------------------
	// Constants
	// ----------------------------------------------
	
	private String userId;
	
	private String benchmark;
	private int amountOfExecutionsPerInstance;
	
	private String initialSolutionBuilder;
	private String structure;
	private String neighborCalculator;
	private String modifier;
	private String control;
	private String gammaCalculator;
	private String decodingStrategy;
	
	private String considerTravelTimes;
	private String considerSetupTimes;
	
	private String showConfigurationTable;
	private String showInitialSolutions;
	private String showFinalSolutions;
	private String showLog;
	
	// ----------------------------------------------
	// Methods
	// ----------------------------------------------
	
	public void launchSofia() throws Exception{
		Properties data = loadPropertiesFile(new File(CONFIGURATION_FILE));
		
		userId = data.getProperty("userId");
		
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
		
		benchmark = data.getProperty("benchmark");
		amountOfExecutionsPerInstance = Integer.parseInt(data.getProperty("amountOfExecutionsPerInstance"));
		
		// Loading the solution method configuration
		initialSolutionBuilder = data.getProperty("initialSolutionBuilder");
		structure = data.getProperty("structure");
		neighborCalculator = data.getProperty("neighborCalculator");
		gammaCalculator = data.getProperty("gammaCalculator");
		modifier = data.getProperty("modifier");
		control = data.getProperty("control");
		decodingStrategy = data.getProperty("decodingStrategy");
		
		Properties algorithmConfiguration = new Properties();
		algorithmConfiguration.setProperty("scheduling.initialSolutionBuilder", initialSolutionBuilder);
		algorithmConfiguration.setProperty("scheduling.structureFactory", structure);
		algorithmConfiguration.setProperty("scheduling.neighborCalculator", neighborCalculator);
		algorithmConfiguration.setProperty("scheduling.gammaCalculator", gammaCalculator);
		algorithmConfiguration.setProperty("scheduling.modifier", modifier);
		algorithmConfiguration.setProperty("scheduling.control", control);
		algorithmConfiguration.setProperty("scheduling.parametersLoader", control + "ParametersLoader");
		algorithmConfiguration.setProperty("scheduling.decodingStrategy", decodingStrategy);
		
		// Params Taboo search
		algorithmConfiguration.setProperty("params.iterations", data.getProperty("params.iterations"));
		algorithmConfiguration.setProperty("params.tabulist-size", data.getProperty("params.tabulist-size"));
		algorithmConfiguration.setProperty("params.neighborhodSize", data.getProperty("params.neighborhodSize"));
		
		// Params simulated annealing
		algorithmConfiguration.setProperty("params.T0", data.getProperty("params.T0"));
		algorithmConfiguration.setProperty("params.Tf", data.getProperty("params.Tf"));
		algorithmConfiguration.setProperty("params.k", data.getProperty("params.k"));
		algorithmConfiguration.setProperty("params.coolingFactor", data.getProperty("params.coolingFactor"));
		algorithmConfiguration.setProperty("params.restarts", data.getProperty("params.restarts"));
		algorithmConfiguration.setProperty("params.boltzmann", data.getProperty("params.boltzmann"));
		
		//Params iteratedTabu
		algorithmConfiguration.setProperty("params.outerIterations", data.getProperty("params.outerIterations"));
		
		//Params grasperls
		algorithmConfiguration.setProperty("params.strategyLS", data.getProperty("params.strategyLS"));
		algorithmConfiguration.setProperty("params.maxLSDepth", data.getProperty("params.maxLSDepth"));
		algorithmConfiguration.setProperty("params.maxNeighbors", data.getProperty("params.maxNeighbors"));

		// Params general
		algorithmConfiguration.setProperty("params.maxNumberImprovements", data.getProperty("params.maxNumberImprovements"));
		algorithmConfiguration.setProperty("params.maxExecutionTime", data.getProperty("params.maxExecutionTime"));
		algorithmConfiguration.setProperty("params.non-improving-in", data.getProperty("params.non-improving-in"));
		algorithmConfiguration.setProperty("params.non-improving-out", data.getProperty("params.non-improving-out"));
		
		// Loading selected betas
		considerTravelTimes = data.getProperty("betas.considerTravelTimes");
		considerSetupTimes = data.getProperty("betas.considerSetupTimes");
		
		String gammaBKS = null;
		if(gammaCalculator.equals(ConfigurationAdapter.CMAX)){
			gammaBKS = ConfigurationAdapter.CMAX_BKS;
		}else if(gammaCalculator.equals(ConfigurationAdapter.MEAN_FLOW_TIME)){
			gammaBKS = ConfigurationAdapter.MEAN_FLOW_TIME_BKS;
		}else if(gammaCalculator.equals(ConfigurationAdapter.TOTAL_FLOW_TIME)){
			gammaBKS = ConfigurationAdapter.TOTAL_FLOW_TIME_BKS;
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
		
		String time=""+System.currentTimeMillis();
		
		if(showLog.equals("true")){
			ExecutionLogger.getInstance().setUseLogger(true);
			ExecutionLogger.getInstance().initializeLogger (""+time, "");
			
		}
		
		// Executing instances
		for (int i = 0; i < instancesToExecute.size(); i++) {
			String instance = instancesToExecute.get(i);
			
			boolean hasOptimal = false;
			if(instance.contains("04x04") || instance.contains("05x05"))
				hasOptimal = true;
			
			String problemFile = "./data/FilesIndex/" + instance.substring(0, 5);
			if(benchmark.equals("Parallel")){
				problemFile += "x02";
				instance = instance.substring(0, 5) + "x02_" + instance.substring(6, 8);
			}
			problemFile += "/" + instance + ".properties";
			
			Properties problem = loadPropertiesFile(new File(problemFile));
			SchedulingAlgorithm algorithm = new TrajectoryBasedAlgorithm(algorithmConfiguration, problem, currentBks, benchmark, hasOptimal);
			
			ArrayList<ExecutionResults> results = new ArrayList<ExecutionResults>();
			for(int j = 0; j < amountOfExecutionsPerInstance; j++){
				ExecutionResults result = algorithm.execute(instance, problemFile);
				results.add(result);
			}
			ChartPrinter.getInstance().addResults(results);
		}
		File destinationFolder = new File("./results/" + userId);
		if(!destinationFolder.exists()){
			destinationFolder.mkdirs();
		}
		ChartPrinter.getInstance().printGlobalResultsHTML("./results/" + userId + "/experiment-results-" + time + ".html");
	}
	
	/**
	 * Loads the information contained in the properties file located
	 * in the path given in the constant CONFIGURATION_FILE
	 * 
	 * @return data. A properties object containing the information in the file
	 * @throws IOException If any input/output error occurs
	 */
	private Properties loadPropertiesFile(File propertiesFile) throws IOException{
		Properties data = new Properties();
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
