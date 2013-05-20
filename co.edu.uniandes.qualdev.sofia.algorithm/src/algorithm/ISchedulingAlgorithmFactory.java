package algorithm;

import java.util.Properties;

/**
 * Interface that defines the functionality of a factory for scheduling algorithms
 * @author David Mendez-Acuna
 */
public interface ISchedulingAlgorithmFactory {

	// -------------------------------------------------------
	// Methods
	// -------------------------------------------------------
	
	/**
	 * Creates a new scheduling algorithm according to the information given in the properties files
	 * @param algorithmConfiguration The properties file with the configuration of the scheduling algorithm
	 * @param problemConfiguration The properties file with the information of the scheduling problem
	 * @return schedulingAlgorithm A VO with the information of the scheduling algorithm 
	 */
	public SchedulingAlgorithm createSchedulingAlgorithm(Properties algorithmConfiguration, Properties problemConfiguration);
	
	public SchedulingAlgorithm createSchedulingAlgorithm();
}
