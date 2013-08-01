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
	
	private ArrayList<String> problemFiles;
	
	private String structrureFactory;
	
	private HashMap<String, Integer> bkss;
	
	private String currentBks;
	
	private boolean hasOptimal;
	
	private String resultFile;
	
	private String instanceName;
	
	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

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