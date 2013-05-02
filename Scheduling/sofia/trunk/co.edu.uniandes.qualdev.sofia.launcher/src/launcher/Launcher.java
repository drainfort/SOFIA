package launcher;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import algorithm.SchedulingAlgorithm;
import algorithm.impl.MultiStartAlgorithm;
import algorithm.impl.TrajectoryBasedAlgorithm;
import common.utils.ExecutionResults;

/**
 * Main class that is able to launch a scheduling algorithm
 * @author David Mendez-Acuna
 *
 */
public class Launcher {
	
	// -----------------------------------------
	// Methods
	// -----------------------------------------

	public ExecutionResults launch(String algorithmFile, String problemFile, String resultsFile, String instanceName) throws Exception{
		Properties algorithmConfiguration = loadProductConfiguration(new File(
				algorithmFile));
		Properties problemConfiguration = loadProductConfiguration(new File(
				problemFile));
		String iterative = algorithmConfiguration
								.getProperty("scheduling.control");
		
		SchedulingAlgorithm algorithm =  null;
		if(iterative.equals("control.GRASP") || iterative.equals("control.GRASPERLS")){
			algorithm = new MultiStartAlgorithm(algorithmConfiguration,problemConfiguration);
		}else{
			algorithm = new TrajectoryBasedAlgorithm(algorithmConfiguration, problemConfiguration);
		}
		
		return algorithm.execute(resultsFile, instanceName);
	}
	
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
