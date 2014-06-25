package algorithm;

import java.util.ArrayList;
import java.util.HashMap;

import common.types.BetaVO;

/**
 * Represents a scheduling problem
 * 
 * @author David Mendez-Acuna
 */
public class SchedulingProblem {

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------

	/**
	 * The collection of betas that compose the problem.
	 */
	private ArrayList<BetaVO> betas;
	/**
	 * The collection of the files (Processing time, Travel Time, Setup) that compose the problem.
	 */
	private ArrayList<String> problemFiles;
	
	/**
	 * Class of the structure that is going to be used in this problem
	 */
	private String structrureFactory;
	
	/**
	 * Has Map with the best known solutions of the scheduling problem.
	 */
	private HashMap<String, Integer> bkss;
	
	/**
	 * The best known solution for the combination of problem and betas
	 */
	private String currentBks;
	
	/**
	 * Boolean that specifies if this problem has optimal solution
	 */
	private boolean hasOptimal;
	
	/**
	 * Name of the file where the results will be printed.
	 */
	private String resultFile;
	
	/**
	 * Name of the current instance.
	 */
	private String instanceName;
	
	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	/**
	 * Constructor of the class
	 * @param problemFiles - Problem files that conform the problem.
	 * @param betas -  collection of betas that compose the problem
	 * @param structrureFactory - structure that is going to be used in this problem
	 * @param bkss - best known solutions of the scheduling problem
	 * @param currentBks -  best known solution for the combination of problem and betas
	 * @param hasOptimal -Boolean that specifies if this problem has optimal solution
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public SchedulingProblem(ArrayList<String> problemFiles, ArrayList<BetaVO> betas, String structrureFactory, 
				HashMap<String, Integer> bkss, String currentBks, boolean hasOptimal) throws InstantiationException, IllegalAccessException, ClassNotFoundException, 
						Exception {
		this.betas = betas;
		this.problemFiles = problemFiles;
		this.structrureFactory = structrureFactory;
		this.bkss = bkss;
		this.currentBks = currentBks;
		this.setHasOptimal(hasOptimal);
	}

	// -----------------------------------------------
	// Methods
	// -----------------------------------------------
	
	// -----------------------------------------------
	// Getters and setters
	// -----------------------------------------------

	public ArrayList<BetaVO> getBetas() {
		return betas;
	}

	public void setBetas(ArrayList<BetaVO> betas) {
		this.betas = betas;
	}

	public ArrayList<String> getProblemFiles() {
		return problemFiles;
	}

	public void setProblemFiles(ArrayList<String> problemFiles) {
		this.problemFiles = problemFiles;
	}

	public String getStructrureFactory() {
		return structrureFactory;
	}

	public void setStructrureFactory(String structrureFactory) {
		this.structrureFactory = structrureFactory;
	}

	public HashMap<String, Integer> getBkss() {
		return bkss;
	}

	public void setBkss(HashMap<String, Integer> bkss) {
		this.bkss = bkss;
	}

	public String getCurrentBks() {
		return currentBks;
	}

	public void setCurrentBks(String currentBks) {
		this.currentBks = currentBks;
	}
	
	public int getCurrentBksValue(){
		return bkss.get(currentBks);
	}

	public boolean isHasOptimal() {
		return hasOptimal;
	}

	public void setHasOptimal(boolean hasOptimal) {
		this.hasOptimal = hasOptimal;
	}

	public String getResultFile() {
		return resultFile;
	}

	public void setResultFile(String resultFile) {
		this.resultFile = resultFile;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	
	
}