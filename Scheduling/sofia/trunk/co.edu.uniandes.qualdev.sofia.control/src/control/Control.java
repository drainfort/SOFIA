package control;

import java.util.ArrayList;
import java.util.Properties;

import structure.IOperation;
import structure.IStructure;
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

//	protected MetricsModel metricsModel;

//	protected Integer iteration = 0;
	
	protected IStructure So;
	
	protected ExecutionResults executionResults;
	
	
	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------
	
	public Control(){
		executionResults = new ExecutionResults();
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
			IGammaCalculator gammaCalculator, Properties params, Integer optimal, int yuSolution) throws Exception;
	
	// -----------------------------------------------
	// Methods
	// -----------------------------------------------
	
	/**
	 * 
	 * @param S0
	 * @return
	 * @throws Exception 
	 */
	protected ExecutionResults obtainExecutionResults(IStructure S0, IGammaCalculator gammaCalculator) throws Exception {
		S0.calculateCMatrix();
		int GammaInitialSolution = gammaCalculator.calculateGamma(S0);
		executionResults.setBestCmax(GammaInitialSolution);
		//System.out.println("Valida la solucion: "+validateSolution(S0));
		
		So.calculateCMatrix();
		generateGanttTasks(So, true);
		generateGanttTasks(S0, false);
		
		return executionResults;
	}
	
	
	private void generateGanttTasks(IStructure solution, boolean initial){

		ArrayList<GanttTask> tasksFinalSolution = new ArrayList<GanttTask>();
		ArrayList<OperationIndexVO> operationIndexesFinalSolution = new ArrayList<OperationIndexVO>();
		
		for (IOperation operation : solution.getOperations()) {
			if (operation!=null && !machineNotDefinedInGantt(tasksFinalSolution, operation.getOperationIndex().getStationId())) {
				GanttTask task = new GanttTask();
				task.setStationIdentifier(operation.getOperationIndex().getStationId());
				task.setName("Estación " + operation.getOperationIndex().getStationId());

				tasksFinalSolution.add(task);
			}
			OperationIndexVO operationIndex = new OperationIndexVO(operation.getOperationIndex().getJobId(), operation.getOperationIndex().getStationId());
			operationIndex.setInitialTime(operation.getInitialTime());
			operationIndex.setFinalTime(operation.getFinalTime());
			operationIndex.setNameJob("Trabajo "+operation.getOperationIndex().getJobId());
			operationIndex.setNameMachine("Máquina " +1);
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