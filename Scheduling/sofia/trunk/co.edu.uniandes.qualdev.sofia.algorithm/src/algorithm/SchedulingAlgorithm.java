package algorithm;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
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
	
	protected Properties algorithmConfiguration;
	
	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	/**
	 * Constructor of the class.
	 * @param currentBks 
	 * 
	 * @param configurationFile. The configuration file that contains the 
	 * 								information about what components should be instantiated. 
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public SchedulingAlgorithm(Properties algorithmConfiguration, Properties problemConfiguration, String currentBks, String instanceType, boolean hasOptimal)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, Exception {
		
		this.algorithmConfiguration= algorithmConfiguration;

		String structrureFactory = (String) algorithmConfiguration
				.getProperty("scheduling.structureFactory");
		
		this.structureFactory = structrureFactory;
		
		String processingTimesFiles = (String) problemConfiguration
				.getProperty("scheduling.instancesFile." + instanceType);
		
		String processingTimesFiles2 = null;
		
		if(instanceType.equals("Parallel")){
			processingTimesFiles = (String) problemConfiguration
					.getProperty("scheduling.instancesFile.T" );
			processingTimesFiles2 = (String) problemConfiguration
					.getProperty("scheduling.instancesFile.M" );
		}
		
		
		System.out.println("scheduling.instancesFile." + instanceType);
		
		String machinesFile = (String) problemConfiguration
				.getProperty("scheduling.instancesFile.machines");
		
		// Initializes the constructive algorithm according to the configuration file
		String initialSolutionBuilderClassName = (String) algorithmConfiguration
				.getProperty("scheduling.initialSolutionBuilder");
		
		ArrayList<String> problemFiles = new ArrayList<String>();
		problemFiles.add(processingTimesFiles);
		
		
		if(machinesFile != null)
			problemFiles.add(machinesFile);
		
		// Initializes the constraints (betas) that the given solution should satisfy.
		ArrayList<BetaVO> betas = new ArrayList<BetaVO>();
		int betasAmount = (Integer.parseInt((String) problemConfiguration.getProperty("betas.amount")));
		for (int i = 0; i < betasAmount; i++) {
			String currentBetaName = (String) problemConfiguration.getProperty("betas.beta." + i + ".name");
			String currentBetaClass = (String) problemConfiguration.getProperty("betas.beta." + i + ".class");
			int informationFilesAmount = Integer.parseInt((String) problemConfiguration.getProperty("betas.beta." + i + ".informationFiles.amount"));
			ArrayList<String> informationFiles = new ArrayList<String>();

			for (int j = 0; j < informationFilesAmount; j++) {
				String currentInformationFile = (String) problemConfiguration.getProperty("betas.beta." + i + ".informationFiles." + j + ".path");
				informationFiles.add(currentInformationFile);
				//problemFiles.add(currentInformationFile);
			}

			String selectedBetas = currentBks.replace("gamma.cmax.bks.om", "");
			selectedBetas = currentBks.replace("gamma.cmax.bks.om", "");
			
			boolean considered = false;
			
			if(selectedBetas.contains("tt") &&  (currentBetaName.equals("TravelTimes") || currentBetaName.equals("TearDownTravelTime"))){
				considered = true;
			}else if(selectedBetas.contains("s") && currentBetaName.equals("SetupTimes")){
				considered = true;
			}
			File f = new File(informationFiles.get(0));
			if(!f.exists()){
				considered = false;
			}
			System.out.println(currentBetaName + "." + considered);
			BetaVO beta = new BetaVO(currentBetaName, currentBetaClass,informationFiles, considered);
			betas.add(beta);
		}
		
		if(processingTimesFiles2!=null){
			problemFiles.add(1,"");
			problemFiles.add(2,processingTimesFiles2);
		}
		
		HashMap<String, Integer> bkss = getBkss(problemConfiguration);
		
		// Initializes the problem according to the inputs
		problem = new SchedulingProblem(problemFiles, betas, structrureFactory, bkss, currentBks, hasOptimal);
		
		constructiveAlgorithm = new ConstructiveAlgorithm(initialSolutionBuilderClassName);
		
		// Initializes the solution algorithm according to the configuration file
		iterativeAlgorithm = new IterativeAlgorithm(algorithmConfiguration);
	}

	// -----------------------------------------------
	// Methods
	// -----------------------------------------------

	private HashMap<String, Integer> getBkss(Properties problemConfiguration) {
		HashMap<String, Integer> bkss = new HashMap<String, Integer>();
		
		int gamma_cmax_bks_om = (int) Integer.parseInt(problemConfiguration.getProperty("gamma.cmax.bks.om"));
		bkss.put("gamma.cmax.bks.om", gamma_cmax_bks_om);
		
		int gamma_cmax_bks_om_s = (int) Integer.parseInt(problemConfiguration.getProperty("gamma.cmax.bks.om.s"));
		bkss.put("gamma.cmax.bks.om.s", gamma_cmax_bks_om_s);
		
		int gamma_cmax_bks_om_tt = (int) Integer.parseInt(problemConfiguration.getProperty("gamma.cmax.bks.om.tt"));
		bkss.put("gamma.cmax.bks.om.tt", gamma_cmax_bks_om_tt);
		
		int gamma_cmax_bks_om_tt_s = (int) Integer.parseInt(problemConfiguration.getProperty("gamma.cmax.bks.om.tt.s"));
		bkss.put("gamma.cmax.bks.om.tt.s", gamma_cmax_bks_om_tt_s);
		
		int gamma_mft_bks_om = (int) Integer.parseInt(problemConfiguration.getProperty("gamma.mft.bks.om"));
		bkss.put("gamma.mft.bks.om", gamma_mft_bks_om);
		
		int gamma_mft_bks_om_s = (int) Integer.parseInt(problemConfiguration.getProperty("gamma.mft.bks.om.s"));
		bkss.put("gamma.mft.bks.om.s", gamma_mft_bks_om_s);
		
		int gamma_mft_bks_om_tt = (int) Integer.parseInt(problemConfiguration.getProperty("gamma.mft.bks.om.tt"));
		bkss.put("gamma.mft.bks.om.tt", gamma_mft_bks_om_tt);
		
		int gamma_mft_bks_om_tt_s = (int) Integer.parseInt(problemConfiguration.getProperty("gamma.mft.bks.om.tt.s"));
		bkss.put("gamma.mft.bks.om.tt.s", gamma_mft_bks_om_tt_s);
		
		return bkss;
	}

	/**
	 * Executes the algorithm by calling the execute method of the control
	 * component
	 * 
	 * @throws Exception
	 *             If something fails
	 */
	public abstract ExecutionResults execute(String instanceName, String instanceFile) throws Exception;

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