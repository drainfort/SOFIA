package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import structure.IOperation;
import structure.IStructure;
import structure.impl.Graph;
import structure.impl.Job;
import structure.impl.Machine;
import structure.impl.Station;
import structure.impl.Vector;
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

	protected IStructure So;
	
	protected ExecutionResults executionResults;
	
	protected String resultFile;
	
	protected String instanceName;
	
	protected final static Logger LOGGER = Logger.getLogger(Control.class
		      .getName());
	
	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------
	
	public Control(){		
	}
	
	// -----------------------------------------------
	// Abstract methods
	// -----------------------------------------------
	
	/**
	 * Executes the scheduling algorithm
	 * @throws params
	 * 			The parameters required by the algorithm
	 * @throws Exception
	 */
	abstract public ExecutionResults execute(IStructure initialSolution,
			INeighborCalculator neighborCalculator, IModifier modifier,
			IGammaCalculator gammaCalculator, Properties params, Integer optimal, boolean isOptimal) throws Exception;
	
	// -----------------------------------------------
	// Methods
	// -----------------------------------------------
	
	protected void initializeLogger (){
		FileHandler fileTxt;
		try {
			LOGGER.setUseParentHandlers(false);
			fileTxt = new FileHandler("./log/Log-execution-"+resultFile+".txt");
			System.out.println(resultFile);
			System.out.println(instanceName);
			SimpleFormatter formatterTxt = new SimpleFormatter();
			fileTxt.setFormatter(formatterTxt);
			LOGGER.addHandler(fileTxt);
			LOGGER.setLevel(Level.INFO);
			LOGGER.info("Instance: "+instanceName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/**
	 * 
	 * @param S0
	 * @return
	 * @throws Exception 
	 */
	protected ExecutionResults obtainExecutionResults(IStructure S0, IGammaCalculator gammaCalculator, boolean printTable, boolean printSolution, boolean printInitialSolution) throws Exception {
		S0.calculateCMatrix();
		executionResults.setBestCmax(gammaCalculator.calculateGamma(S0));
		executionResults.setInitialCmax(gammaCalculator.calculateGamma(So));
		executionResults.setPrintTable(printTable);
		executionResults.setPrintInitialSolution(printInitialSolution);
		executionResults.setPrintFinalSolution(printSolution);
		
		//System.out.println("Valida la solucion:"+ validateSolution(S0));

		if(printInitialSolution){
			So.calculateCMatrix();
			generateGanttTasks(So, true);
		}
		if(printSolution){
			generateGanttTasks(S0, false);
		}
		
		return executionResults;
	}
	
	
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
		if(solution instanceof Vector){
			if(((Vector)solution).isNonDelayActive())
				operations = ((Vector)solution).getVectorDecodNonDelay();
		}
		
		for (IOperation operation : operations) {
			if (operation!=null && !machineNotDefinedInGantt(tasksFinalSolution, operation.getOperationIndex().getStationId())) {
				GanttTask task = new GanttTask();
				task.setStationIdentifier(operation.getOperationIndex().getStationId());
				Station temp = findStation(stations, operation.getOperationIndex().getStationId()+1);
				if(temp==null){
					task.setName("Estación " + operation.getOperationIndex().getStationId());
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
				operationIndex.setNameJob("Trabajo "+operation.getOperationIndex().getJobId());
				operationIndex.setNameMachine("Máquina " + operation.getOperationIndex().getMachineId());
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
	
	
	
	private boolean machineNotDefinedInGantt(ArrayList<GanttTask> tasks,
			int machineIndex) {
		for (GanttTask ganttTask : tasks) {
			if (ganttTask.getStationIdentifier().intValue() == machineIndex)
				return true;
		}
		return false;
	}
	
	
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