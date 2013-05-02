package algorithm;

import java.util.ArrayList;

import common.types.BetaVO;


/**
 * Represents a scheduling problem
 * 
 * @author David Mendez-Acuna
 * 
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
	
	private int optimal;
	
	private int yuSolution;

	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	public SchedulingProblem(ArrayList<String> problemFiles, ArrayList<BetaVO> betas, String structrureFactory, int nOptimal)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, Exception {
		this.betas = betas;
		this.problemFiles = problemFiles;
		this.structrureFactory = structrureFactory;
		this.optimal = nOptimal;
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

	public int getOptimal() {
		return optimal;
	}

	public void setOptimal(int optimal) {
		this.optimal = optimal;
	}

	public int getYuSolution() {
		return yuSolution;
	}

	public void setYuSolution(int yuSolution) {
		this.yuSolution = yuSolution;
	}
	
	
}