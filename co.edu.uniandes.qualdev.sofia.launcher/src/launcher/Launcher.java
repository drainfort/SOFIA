package launcher;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import algorithm.SchedulingAlgorithm;
import algorithm.SchedulingProblem;
import algorithm.impl.MultiStartAlgorithm;
import algorithm.impl.TrajectoryBasedAlgorithm;
import common.utils.ExecutionResults;
import control.impl.GRASP;
import control.impl.GRASPERLS;

/**
 * Main class that is able to launch a scheduling algorithm
 * @author David Mendez-Acuna
 *
 */
public class Launcher {
	
	// -----------------------------------------
	// Methods
	// -----------------------------------------

	/**
	 * Executes a scheduling algorithm for solving the given scheduling problem
	 * 
	 * @param algorithmFile A properties file that contains the configuration of the scheduling algorithm
	 * @param problemFile A properties file that contains the information of the scheduling problem
	 * @param resultsFile The path of the file that will contain the results of the execution
	 * @param instanceName The name of the instance of the scheduling problem to be solved
	 * @return
	 * @throws Exception
	 */
	public ExecutionResults launch(String algorithmFile, String problemFile, String resultsFile, String instanceName) throws Exception{
		Properties algorithmConfiguration = loadProductConfiguration(new File(algorithmFile));
		Properties problemConfiguration = loadProductConfiguration(new File(problemFile));
		String iterative = algorithmConfiguration.getProperty("scheduling.control");
		
		SchedulingAlgorithm algorithm =  null;
		if(iterative.equals("control.impl.GRASP") || iterative.equals("control.impl.GRASPERLS")){
			algorithm = new MultiStartAlgorithm(algorithmConfiguration, problemConfiguration);
		}else{
			algorithm = new TrajectoryBasedAlgorithm(algorithmConfiguration, problemConfiguration);
		}
		
		return algorithm.execute(resultsFile, instanceName);
	}
	
	/**
	 * Executes a scheduling algorithm for solving the given scheduling problem
	 * 
	 * @param algorithm The VO that contains both: the information of the scheduling problem and the scheduling algorithm
	 * @param resultsFile The path of the file that will contain the results of the execution
	 * @param instanceName The name of the instance of the scheduling problem to be solved
	 * @return
	 * @throws Exception
	 */
	public ExecutionResults launch(SchedulingAlgorithm algorithm, String resultsFile, String instanceName) throws Exception{
		return algorithm.execute(resultsFile, instanceName);
	}
	
	// -----------------------------------------
	// Utilities
	// -----------------------------------------
	
	/**
	 * Loads the configuration of the scheduling algorithm from a properties
	 * file.
	 * 
	 * @param file
	 *            . File that contains the configuration of the scheduling
	 *            algorithm.
	 * @return data. Properties object with the configuration of the scheduling
	 *         algorithm.
	 * @throws Exception. Either
	 *             the file does not exist or it does not exist.
	 */
	private Properties loadProductConfiguration(File file) {
		Properties data = new Properties();

		try {
			System.out.println(file);
			FileInputStream in = new FileInputStream(file);
			data.load(in);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
}
