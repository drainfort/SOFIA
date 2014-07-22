package control;

import java.util.ArrayList;
import java.util.Properties;
import structure.IOperation;
import structure.IStructure;
import structure.impl.Graph;
import structure.impl.Job;
import structure.impl.Machine;
import structure.impl.Station;
import common.types.OperationIndexVO;
import common.utils.ExecutionResults;
import common.utils.GanttTask;
import modifier.IModifier;
import neighborCalculator.INeighborCalculator;
import gammaCalculator.IGammaCalculator;


/**
 * Interface for the control of an scheduling algorithm
 * @author David Mendez-Acuna
 *
 */
public abstract class Control {
	
	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------

	/**
	 * Initial Solution where the control starts.
	 */
	protected IStructure So;
	
	/**
	 * Consolidated results of the instance.
	 */
	protected ExecutionResults executionResults;
	
	/**
	 * Name of the result file.
	 */
	protected String resultFile;
	
	/**
	 * Name of the current instance.
	 */
	protected String instanceName;
		
	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------
	
	/**
	 * Constructor of the class
	 */
	public Control(){		
	}
	
	// -----------------------------------------------
	// Abstract methods
	// -----------------------------------------------
	
	/**
	 * Executes the scheduling algorithm
	 * @param params - Parameters of the algorithm
	 * @param initialSolution - Initial solution
	 * @param gammaCalculator - Component that calculates the objective function
	 * @param neighborCalculator - Component that calculates the neighbors
	 * @param modifiers - List of components that modifies the structure 
	 * @param optimal - The best solution or the optimal solution
	 * @param isOptimal - If the instance has an optimal solution
	 * @return executionResults - The consolidated results
	 * @throws Exception - Method error
	 */
	abstract public ExecutionResults execute(IStructure initialSolution,
			INeighborCalculator neighborCalculator, ArrayList<IModifier> modifiers,
			IGammaCalculator gammaCalculator, Properties params, Integer optimal, boolean isOptimal) throws Exception;
	
	// -----------------------------------------------
	// Methods
	// -----------------------------------------------
	
	/**
	 * Consolidates all the results, and creates the objects that are needed to genrate the charts.
	 * @param S0 - Initial solution.
	 * @param gammaCalculator - Component that calculates the objective function
	 * @param printTable - Boolean that defines if the result file has the consolidated table
	 * @param printSolution - Boolean that defines if the result file has the gantt of the best solution
	 * @param printInitialSolution - Boolean that defines if the result file has the initial solution
	 * @param printLog - Boolean that defines if the result file has the log table
	 * @param printImprovement - Boolean that defines if the result file has the improvement chart
	 * @param executionTime - Max execution time
	 * @return Consolidated results of the algorithm
	 * @throws Exception
	 */
	protected ExecutionResults obtainExecutionResults(IStructure S0, IGammaCalculator gammaCalculator, boolean printTable, boolean printSolution, boolean printInitialSolution, boolean printLog, boolean printImprovement, long executionTime) throws Exception {
		S0.calculateCMatrix(0);
		executionResults.setBestCmax(gammaCalculator.calculateGamma(S0));
		executionResults.setInitialCmax(gammaCalculator.calculateGamma(So));
		executionResults.setPrintTable(printTable);
		executionResults.setPrintInitialSolution(printInitialSolution);
		executionResults.setPrintFinalSolution(printSolution);
		executionResults.setPrintLog(printLog);
		executionResults.setExecutionTime(executionTime);
		executionResults.setPrintImprovement(printImprovement);
		
		//System.out.println("Valida la solucion:"+ validateSolution(S0));

		if(printInitialSolution){
			So.calculateCMatrix(0);
			generateGanttTasks(So, true);
		}
		if(printSolution){
			generateGanttTasks(S0, false);
		}
		
		return executionResults;
	}
	
	/**
	 * Generate the gantt tasks necessary for the gantt chart.
	 * @param solution - Solution that we want to make a char of.
	 * @param initial - If the solution is a final solution or a initial solution.
	 */
	private void generateGanttTasks(IStructure solution, boolean initial){
		ArrayList<Station> stations= new ArrayList<Station>();
		ArrayList<Job> jobs = new ArrayList<Job>();
		ArrayList<Machine> machines =new ArrayList<Machine>();
		if(solution.getClass().equals(Graph.class)){
			machines = ((Graph) solution).getMachines();
			jobs =((Graph) solution).getJobs();
			stations =((Graph) solution).getStations();
		}

		ArrayList<GanttTask> tasksFinalSolution = new ArrayList<GanttTask>();
		ArrayList<OperationIndexVO> operationIndexesFinalSolution = new ArrayList<OperationIndexVO>();
		ArrayList<IOperation> operations = solution.getOperations();
		
		for (IOperation operation : operations) {
			if (operation!=null && !machineNotDefinedInGantt(tasksFinalSolution, operation.getOperationIndex().getStationId())) {
				GanttTask task = new GanttTask();
				task.setStationIdentifier(operation.getOperationIndex().getStationId());
				Station temp = findStation(stations, operation.getOperationIndex().getStationId()+1);
				if(temp==null){
					task.setName("Station " + operation.getOperationIndex().getStationId());
				}
				else{
					task.setName(temp.getNameClass()+" "+ temp.getAtributes().get(0).getValue());
				}
				tasksFinalSolution.add(task);
			}
			Machine machine = findMachine(machines, operation.getOperationIndex().getStationId()+1);
			Job job = findJob(jobs, operation.getOperationIndex().getJobId()+1);
			
			OperationIndexVO operationIndex = new OperationIndexVO(operation.getOperationIndex().getJobId(), operation.getOperationIndex().getStationId());
			operationIndex.setInitialTime(operation.getInitialTime());
			operationIndex.setFinalTime(operation.getFinalTime());
			if(job!=null){
				operationIndex.setNameJob(job.getNameClass()+" "+job.getAtributes().get(0).getValue());
				operationIndex.setNameMachine(machine.getNameClass()+" "+machine.getAtributes().get(0).getValue());
			}
			else{
				operationIndex.setNameJob("Job "+operation.getOperationIndex().getJobId());
				operationIndex.setNameMachine("Machine " + operation.getOperationIndex().getMachineId());
			}
			operationIndexesFinalSolution.add(operationIndex);
			
		}
		if(initial){
			executionResults.setTasksInitialSolution(tasksFinalSolution);
			executionResults.setOperationsInitialSolution(operationIndexesFinalSolution);
		}else{
			executionResults.setTasksFinalSolution(tasksFinalSolution);
			executionResults.setOperationsFinalSolution(operationIndexesFinalSolution);
		}
	}
	
	
	/**
	 * Search for the machine index on the generated gantt tasks
	 * @param tasks - Generated gantt tasks
	 * @param machineIndex - The desire machine index
	 * @return boolean - if the index was found
	 */
	private boolean machineNotDefinedInGantt(ArrayList<GanttTask> tasks,
			int machineIndex) {
		for (GanttTask ganttTask : tasks) {
			if (ganttTask.getStationIdentifier().intValue() == machineIndex)
				return true;
		}
		return false;
	}
	
	/**
	 * Search for the index of a station over an array
	 * @param stations - array of stations
	 * @param id - station id
	 * @return boolean - if the index is found
	 */
	private Station findStation(ArrayList<Station>stations, int id){
		if(stations!=null){
			for(int i=0; i<stations.size();i++){
				if(stations.get(i).getId()==id){
					return stations.get(i);
				}
			}
		}
		return null;
	}
	
	/**
	 * Search for the index of a machine over an array
	 * @param machines - array of machines
	 * @param id - machine id
	 * @return boolean - if the index is found
	 */
	private Machine findMachine(ArrayList<Machine>machines, int id){
		if(machines!=null){
			for(int i=0; i<machines.size();i++){
				if(machines.get(i).getId()==id){
					return machines.get(i);
				}
			}
		}
		return null;
	}
	
	/**
	 * Search for the index of a job over an array
	 * @param jobs - array of jobs
	 * @param id - job id
	 * @return boolean - if the index is found
	 */
	private Job findJob(ArrayList<Job>jobs, int id){
		if(jobs!=null){
			for(int i=0; i<jobs.size();i++){
				if(jobs.get(i).getId()==id){
					return jobs.get(i);
				}
			}
		}
		return null;
	}
	
	// --------------------------------------------
	// Getters and setters
	// --------------------------------------------

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
	
	
	

//	private boolean validateSolution(IVector S0){
//			
//			int [][] initialTimeMatrix = S0.calculateInitialTimesMatrix();
//			int [][] finalTimeMatrix = S0.calculateCMatrix();
//			
//			for(int i=0; i<S0.getTotalJobs();i++){
//				ArrayList<IOperation> job = new ArrayList<IOperation>();
//				for(IOperation operation : S0.getVector()){
//					if(operation.getOperationIndex().getJobId()==i){
//						job.add(operation);
//					}
//				}
//				IOperation firstOperation = job.get(0);
//				int initialTime = initialTimeMatrix[firstOperation.getOperationIndex().getJobId()][firstOperation.getOperationIndex().getStationId()];
//				int traveltime = S0.getTT(-1,firstOperation.getOperationIndex().getStationId());
//				if(traveltime> initialTime){
//					return false;
//				}
//
//				for(int j =0; j<job.size()-1;j++){
//					firstOperation = job.get(j);
//					IOperation secondOperation = job.get(j+1);
//					int finalTime1 = finalTimeMatrix[firstOperation.getOperationIndex().getJobId()][firstOperation.getOperationIndex().getStationId()][firstOperation.getOperationIndex().getMachineId()];
//					traveltime = S0.getTT(firstOperation.getOperationIndex().getStationId(),secondOperation.getOperationIndex().getStationId());
//					int intalTime2 = initialTimeMatrix[secondOperation.getOperationIndex().getJobId()][secondOperation.getOperationIndex().getStationId()];
//					if(finalTime1+traveltime>intalTime2){
//						return false;
//					}
//				}
//				
//			}
//			
//			for(int i=0; i<S0.getTotalStations();i++){
//				ArrayList<IOperation> station = new ArrayList<IOperation>();
//				for(IOperation operation : S0.getVector()){
//					if(operation.getOperationIndex().getStationId()==i){
//						station.add(operation);
//					}
//				}
//
//				for(int j =0; j<station.size()-1;j++){
//					IOperation firstOperation = station.get(j);
//					IOperation secondOperation = station.get(j+1);
//					int finalTime1 = finalTimeMatrix[firstOperation.getOperationIndex().getJobId()][firstOperation.getOperationIndex().getStationId()][firstOperation.getOperationIndex().getMachineId()];
//					int intalTime2 = initialTimeMatrix[secondOperation.getOperationIndex().getJobId()][secondOperation.getOperationIndex().getStationId()];
//					if(finalTime1>intalTime2){
//						return false;
//					}
//				}
//				
//			}
//			
//			return true;
//		}
	

}