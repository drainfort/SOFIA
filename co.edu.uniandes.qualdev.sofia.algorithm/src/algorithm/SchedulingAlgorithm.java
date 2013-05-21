package algorithm;

import java.util.ArrayList;
import java.util.Properties;

import common.types.BetaVO;
import common.utils.ExecutionResults;

/**
 * Represents an scheduling algorithm
 * 
 * @author David Mendez-Acuna
 * 
 */
public abstract class SchedulingAlgorithm {

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------

	/**
	 * The scheduling algorithm that should be solved
	 */
	protected SchedulingProblem problem;

	/**
	 * The canonical name of the factory of the structure
	 */
	protected String structureFactory;
	
	/**
	 * The constructive algorithm that aims at providing an initial solution to the problem. 
	 */
	protected ConstructiveAlgorithm constructiveAlgorithm;
	
	/**
	 * The iterative algorithm that aims at optimizing the initial solution given by the constructive algorithm.
	 */
	protected IterativeAlgorithm iterativeAlgorithm;
	
	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	/**
	 * Constructor of the class.
	 * 
	 * @param configurationFile. The configuration file that contains the 
	 * 								information about what components should be instantiated. 
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public SchedulingAlgorithm(Properties algorithmConfiguration, Properties problemConfiguration)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, Exception {
		

		String structrureFactory = (String) algorithmConfiguration
				.getProperty("scheduling.structureFactory");
		
		this.structureFactory = structrureFactory;
		
		String processingTimesFiles = (String) problemConfiguration
				.getProperty("scheduling.instancesFile");
		
		String machinesFile = (String) problemConfiguration
				.getProperty("scheduling.instancesFile.machines");
		
		int optimal = (int) Integer.parseInt(problemConfiguration
				.getProperty("params.optimal"));
		
		int yuSolution=0;
		if(problemConfiguration.getProperty("params.yuSolution")!=null){
			 yuSolution = (int) Integer.parseInt(problemConfiguration
					.getProperty("params.yuSolution"));
		}
		// Initializes the constructive algorithm according to the configuration file
		String initialSolutionBuilderClassName = (String) algorithmConfiguration
				.getProperty("scheduling.initialSolutionBuilder");
		
		ArrayList<String> problemFiles = new ArrayList<String>();
		problemFiles.add(processingTimesFiles);
		
		if(machinesFile != null)
			problemFiles.add(machinesFile);
		
		// Initializes the constraints (betas) that the given solution should satisfy.
		ArrayList<BetaVO> betas = new ArrayList<BetaVO>();
		int betasAmount = (Integer.parseInt((String) problemConfiguration
				.getProperty("betas.amount")));
		for (int i = 0; i < betasAmount; i++) {
			String currentBetaName = (String) problemConfiguration
					.getProperty("betas.beta." + i + ".name");
			String currentBetaClass = (String) problemConfiguration
					.getProperty("betas.beta." + i + ".class");
			int informationFilesAmount = Integer
					.parseInt((String) problemConfiguration
							.getProperty("betas.beta." + i
									+ ".informationFiles.amount"));
			ArrayList<String> informationFiles = new ArrayList<String>();

			for (int j = 0; j < informationFilesAmount; j++) {
				String currentInformationFile = (String) problemConfiguration
						.getProperty("betas.beta." + i + ".informationFiles."
								+ j + ".path");
				informationFiles.add(currentInformationFile);
				problemFiles.add(currentInformationFile);
			}

			BetaVO beta = new BetaVO(currentBetaName, currentBetaClass,
					informationFiles);
			betas.add(beta);
			
		}
		
		// Initializes the problem according to the inputs
		problem = new SchedulingProblem(problemFiles, betas, structrureFactory,optimal);
		
		problem.setYuSolution(yuSolution);
		
		constructiveAlgorithm = new ConstructiveAlgorithm(initialSolutionBuilderClassName);
		
		// Initializes the solution algorithm according to the configuration file
		iterativeAlgorithm = new IterativeAlgorithm(algorithmConfiguration);
	}

	// -----------------------------------------------
	// Methods
	// -----------------------------------------------

	/**
	 * Executes the algorithm by calling the execute method of the control
	 * component
	 * 
	 * @throws Exception
	 *             If something fails
	 */
	public abstract ExecutionResults execute(String instanceName) throws Exception;

	// -----------------------------------------------
	// Getters and setters
	// -----------------------------------------------
	
	public SchedulingProblem getProblem() {
		return problem;
	}

	public void setProblem(SchedulingProblem problem) {
		this.problem = problem;
	}

	public IterativeAlgorithm getSolution() {
		return iterativeAlgorithm;
	}

	public void setSolution(IterativeAlgorithm solution) {
		this.iterativeAlgorithm = solution;
	}
}