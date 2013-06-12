package common.utils;

import java.util.ArrayList;
import java.util.Properties;

import common.types.OperationIndexVO;


/**
 * Class that contains the information of the results for a given execution
 * @author David Mendez-Acuna
 *
 */
public class ExecutionResults {

	// --------------------------------------------
	// Attributes
	// --------------------------------------------
	
	/**
	 * Total number of iterations
	 */
	private int numberOfIterations;
	
	/**
	 * Metrics model required for the construction of the ganttChart
	 */
	private MetricsModel metricsModel;
	
	private ArrayList<GanttTask> tasksFinalSolution;
	
	private ArrayList<OperationIndexVO> operationsFinalSolution;
	
	private ArrayList<GanttTask> tasksInitialSolution;
	
	private ArrayList<OperationIndexVO> operationsInitialSolution;
	
	private Properties parameters;
	
	private int bestCmax;
	
	private int initialCmax;
	
	private String instanceName;
	
	private long executionTime;
	
	private int optimal;
	
	private int numberOfVisitedNeighbors;
	
	
	private boolean printTable;
	
	private boolean printInitialSolution;
	
	private boolean printFinalSolution;
	
	// --------------------------------------------
	// Constructor
	// --------------------------------------------
	
	public int getOptimal() {
		return optimal;
	}

	public void setOptimal(int optimal) {
		this.optimal = optimal;
	}

	/**
	 * Default constructor of the class
	 */
	public ExecutionResults(){
	}
	
	// --------------------------------------------
	// Getters and setters
	// --------------------------------------------
	
	public int getNumberOfIterations() {
		return numberOfIterations;
	}

	public void setNumberOfIterations(int numberOfIterations) {
		this.numberOfIterations = numberOfIterations;
	}

	public MetricsModel getMetricsModel() {
		return metricsModel;
	}

	public void setMetricsModel(MetricsModel metricsModel) {
		this.metricsModel = metricsModel;
	}

	public ArrayList<GanttTask> getTasksFinalSolution() {
		return tasksFinalSolution;
	}

	public void setTasksFinalSolution(ArrayList<GanttTask> tasks) {
		this.tasksFinalSolution = tasks;
	}

	public ArrayList<OperationIndexVO> getOperationsFinalSolution() {
		return operationsFinalSolution;
	}

	public void setOperationsFinalSolution(ArrayList<OperationIndexVO> operations) {
		this.operationsFinalSolution = operations;
	}

	public ArrayList<GanttTask> getTasksInitialSolution() {
		return tasksInitialSolution;
	}

	public void setTasksInitialSolution(ArrayList<GanttTask> tasksInitialSolution) {
		this.tasksInitialSolution = tasksInitialSolution;
	}

	public ArrayList<OperationIndexVO> getOperationsInitialSolution() {
		return operationsInitialSolution;
	}

	public void setOperationsInitialSolution(
			ArrayList<OperationIndexVO> operationsInitialSolution) {
		this.operationsInitialSolution = operationsInitialSolution;
	}
	
	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public int getBestCmax() {
		return bestCmax;
	}

	public void setBestCmax(int bestCmax) {
		this.bestCmax = bestCmax;
	}

	public float getInitialCmax() {
		return initialCmax;
	}

	public void setInitialCmax(int initialCmax) {
		this.initialCmax = initialCmax;
	}

	public long getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(long executionTime) {
		this.executionTime = executionTime;
	}

	public int getNumberOfVisitedNeighbors() {
		return numberOfVisitedNeighbors;
	}

	public void setNumberOfVisitedNeighbors(int numberOfVisitedNeighbors) {
		this.numberOfVisitedNeighbors = numberOfVisitedNeighbors;
	}

	public boolean isPrintTable() {
		return printTable;
	}

	public void setPrintTable(boolean printTable) {
		this.printTable = printTable;
	}

	public boolean isPrintInitialSolution() {
		return printInitialSolution;
	}

	public void setPrintInitialSolution(boolean printInitialSolution) {
		this.printInitialSolution = printInitialSolution;
	}

	public boolean isPrintFinalSolution() {
		return printFinalSolution;
	}

	public void setPrintFinalSolution(boolean printFinalSolution) {
		this.printFinalSolution = printFinalSolution;
	}

	public Properties getParameters() {
		return parameters;
	}

	public void setParameters(Properties parameters) {
		this.parameters = parameters;
	}
	
	
}