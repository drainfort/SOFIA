package structure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import structure.impl.Operation;
import common.types.BetaVO;

import beta.Beta;

/**
 * Abstract class for the structures.
 * 
 * @author David Mendez-Acuna
 */
public abstract class AbstractStructure  implements IStructure{

	// -------------------------------------------------
	// Attributes
	// -------------------------------------------------

	/**
	 * Matrix with the operations of the problem
	 */
	//TODO OperationIndex
	protected IOperation[][] operationsMatrix;
	
	/**
	 * C matrix of the current solution
	 */
	protected int[][] C;
	
	/**
	 * Constraints (betas) associated to the problem
	 */
	protected Map<String, Beta> betas = null;
	
	/**
	 * File that contains the processing times of the problem that the graph
	 * represents.
	 */
	protected String processingTimesFile;
	
	/**
	 * Amount of jobs in the system
	 */
	protected int totalJobs = 0;

	/**
	 * Amount of machines in the system
	 */
	protected int totalStations = 0;

	/**
	 * Max amount of machines per station in the system
	 */
	protected int maxMachinesPerStation = 1;
	
	// -------------------------------------------------
	// Constructor
	// -------------------------------------------------
	
	/**
	 * Constructor of the class. It is able to create a structure
	 * with a non-defined problem: Neither processing times nor betas.
	 */
	public AbstractStructure(int totalJobs, int totalStations){
		this.totalJobs = totalJobs;
		this.totalStations = totalStations;
		//TODO cambiar el constructor
		operationsMatrix = new IOperation[this.totalJobs][this.totalStations];
		
		for (int i = 0; i < this.totalJobs; i++) {
			for (int j = 0; j < this.totalStations; j++) {
				operationsMatrix[i][j] = new Operation(0, i, j);
			}
		}
	}
	
	/**
	 * Constructor of the class. It is able to create a structure
	 * with the problem: Operations with processing times + betas.
	 */
	public AbstractStructure(String processingTimesFile, ArrayList<BetaVO> pBetas) throws Exception{
		betas = new HashMap<String, Beta>();
		operationsMatrix = loadProblemMatrix(processingTimesFile);
		
		totalJobs = operationsMatrix.length;
		totalStations = operationsMatrix[0].length;
		
		this.processingTimesFile = processingTimesFile;
		loadBetas(pBetas);
	}
	
	/**
	 * Loads the betas to the problem from the collection of VOs in the parameter
	 * @param pBetas Collection of VOs with the information of the betas
	 * @throws Exception
	 */
	private void loadBetas(ArrayList<BetaVO> pBetas) throws Exception{
		for (BetaVO currentBeta : pBetas) {
			Beta beta = (Beta) this.getClass().getClassLoader()
					.loadClass(currentBeta.getClassCanonicalName())
					.newInstance();
			beta.loadBeta(currentBeta.getInformationFiles());
			beta.setInformationFiles(currentBeta.getInformationFiles());
			beta.setConsidered(currentBeta.isConsidered());
			this.betas.put(currentBeta.getName(), beta);
		}
	}
	
	// ---------------------------------------------
	// Methods
	// ---------------------------------------------
	
	@Override
	public IOperation[][] getProblem(){
		return operationsMatrix;
	}
	
	public void setProblem(IOperation[][] problem){
		operationsMatrix=problem;
	}
	
	public void setBetas(Map<String, Beta> betas) {
		this.betas = betas;
	}
	
	public void setOperation(int job, int station, Operation nOperation){
		operationsMatrix[job][station]=nOperation;
	}
	
	// ---------------------------------------------
	// Utilities
	// ---------------------------------------------
	
	/**
	 * Loads an IOperation matrix from a file in the parameter
	 * @param processingTimesFile File with the processing times of the problem
	 * @return
	 * @throws Exception
	 */
	private IOperation[][] loadProblemMatrix(String processingTimesFile)
			throws Exception {
		IOperation[][] problemMatrix = null;

		BufferedReader reader = null;
		try {
			File file = new File(processingTimesFile);
			reader = new BufferedReader(new FileReader(file));

			String matrixHeightString = reader.readLine();
			Integer matrixHeight = Integer.parseInt(matrixHeightString);

			String matrixWidthString = reader.readLine();
			Integer matrixWidth = Integer.parseInt(matrixWidthString);

			problemMatrix = new IOperation[matrixHeight][matrixWidth];

			for (int i = 0; i < matrixHeight; i++) {
				String currentLine = reader.readLine();
				for (int j = 0; j < matrixWidth; j++) {

					int numerito = currentLine.substring(1,
							currentLine.length() - 1).indexOf("|") + 1;
					if (numerito == 0)
						numerito = currentLine.length() - 1;
					String currentNumberString = currentLine.substring(1,
							numerito);
					int currentNumber = 0;
					if (!currentNumberString.equals(" ")) {
						currentNumber = Integer.parseInt(currentNumberString);
					} else {
						currentNumber = -1;
					}

					if (currentNumber != -1) {
						Operation operation = new Operation(currentNumber, i, j);
						problemMatrix[i][j] = operation;
					}
					currentLine = currentLine.substring(
							currentLine.substring(1, currentLine.length() - 1)
									.indexOf("|") + 1, currentLine.length());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (reader != null)
				reader.close();
		}

		return problemMatrix;
	}
	
	
}