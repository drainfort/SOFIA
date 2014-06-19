package common.utils;

import java.util.ArrayList;
import java.util.Properties;

import common.types.OperationIndexVO;


/**
 * Class that contains the information of the results for a given execution
 * @author David Mendez-Acuna
 * @author Jaime Romero
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
	 * Gantt tasks for final solution gantt chart.
	 */
	private ArrayList<GanttTask> tasksFinalSolution;
	
	/**
	 * Operations for the final solution charts
	 */
	private ArrayList<OperationIndexVO> operationsFinalSolution;
	
	/**
	 * Gantt tasks for initial solution gantt chart.
	 */
	private ArrayList<GanttTask> tasksInitialSolution;
	
	/**
	 * Operations for the initial solution charts
	 */
	private ArrayList<OperationIndexVO> operationsInitialSolution;
	
	/**
	 * Improvement Graphic
	 */
	private ArrayList<Graphic> graphics = new ArrayList<Graphic>();
	
	/**
	 * Parameters of the heuristic
	 */
	private Properties parameters;
	
	/**
	 * Best objective function
	 */
	private double bestCmax;
	
	/**
	 * Initial objective function
	 */
	private double initialCmax;
	
	/**
	 * Name of the instance
	 */
	private String instanceName;
	
	/**
	 * Execution time of the instance
	 */
	private long executionTime;
	
	/**
	 * Optimal value or best known solution
	 */
	private int optimal;
	
	/**
	 * Number of visited neighbors
	 */
	private int numberOfVisitedNeighbors;
	
	/**
	 * Stop criteria
	 */
	private int stopCriteria;
	
	/**
	 * Attribute that specifies if in the result file will appear the table of results
	 */
	private boolean printTable;
	/**
	 * Attribute that specifies if in the result file will appear the gantt of the initial solution
	 */
	private boolean printInitialSolution;
	/**
	 * Attribute that specifies if in the result file will appear the gannt of the final solution
	 */
	private boolean printFinalSolution;
	/**
	 * Attribute that specifies if in the result file will appear the log table
	 */
	private boolean printLog;
	/**
	 * Attribute that specifies if in the result file will appear the chart of improvements
	 */
	private boolean printImprovement;
	
	// --------------------------------------------
	// Constructor
	// --------------------------------------------
	
	/**
	 * Default constructor of the class
	 */
	public ExecutionResults(){
	}
	
	
	// --------------------------------------------
	// Getters and setters
	// --------------------------------------------
	
	public boolean isPrintLog() {
		return printLog;
	}

	public void setPrintLog(boolean printLog) {
		this.printLog = printLog;
	}

	public int getOptimal() {
		return optimal;
	}

	public void setOptimal(int optimal) {
		this.optimal = optimal;
	}
	
	public int getNumberOfIterations() {
		return numberOfIterations;
	}

	public void setNumberOfIterations(int numberOfIterations) {
		this.numberOfIterations = numberOfIterations;
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

	public double getBestCmax() {
		return bestCmax;
	}

	public void setBestCmax(double bestCmax) {
		this.bestCmax = bestCmax;
	}

	public double getInitialCmax() {
		return initialCmax;
	}

	public void setInitialCmax(double initialCmax) {
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

	public int getStopCriteria() {
		return stopCriteria;
	}

	public void setStopCriteria(int stopCriteria) {
		this.stopCriteria = stopCriteria;
	}

	public ArrayList<Graphic> getGraphics() {
		return graphics;
	}

	public void setGraphics(ArrayList<Graphic> graphics) {
		this.graphics = graphics;
	}
	
	public void addGraphic(Graphic newGraphic){
		graphics.add(newGraphic);
	}

	public boolean isPrintImprovement() {
		return printImprovement;
	}

	public void setPrintImprovement(boolean printImprovement) {
		this.printImprovement = printImprovement;
	}
	
	
	
}