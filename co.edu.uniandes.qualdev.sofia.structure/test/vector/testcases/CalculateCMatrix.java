package vector.testcases;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import structure.IOperation;
import structure.factory.AbstractStructureFactory;
import structure.factory.impl.VectorFactory;
import structure.impl.Vector;

import common.types.BetaVO;
import common.types.OperationIndexVO;
import common.utils.MatrixUtils;

public class CalculateCMatrix {

	// ----------------------------------------
	// Attributes
	// ----------------------------------------

	private Vector vector;

	// ----------------------------------------
	// Setup scenarios
	// ----------------------------------------

	@Before
	public void setupScenario() throws Exception {

		ArrayList<String> problemFiles = new ArrayList<String>();

		String TFile = "./data/T-04x04-01.txt";
		Integer[][] T = MatrixUtils.loadMatrix(TFile);
		
		String TTFile = "./data/TT-04x04-01.txt";
		Integer[][] TT = MatrixUtils.loadMatrix(TTFile);
		
		problemFiles.add(TFile);
		problemFiles.add(TTFile);

		ArrayList<BetaVO> betas = new ArrayList<BetaVO>();
		ArrayList<String> informationFiles = new ArrayList<String>();
		informationFiles.add(TTFile);

		BetaVO TTBeta = new BetaVO("TravelTimes", "beta.impl.TravelTimes",
				informationFiles);
		BetaVO TearDownTT = new BetaVO("TearDownTravelTime",
				"beta.impl.TearDownTravelTime", informationFiles);
		betas.add(TTBeta);
		betas.add(TearDownTT);

		vector = (Vector) VectorFactory.createNewInstance(
				"structure.factory.impl.VectorFactory").createSolutionStructure(problemFiles, betas);
		
		
		
		
		
		ArrayList<IOperation> operations = new ArrayList<IOperation>();
		for (int i = 0; i < T.length; i++) {
			for (int j = 0; j < T[0].length; j++) {
				IOperation currentIOperation = AbstractStructureFactory.createNewInstance("structure.factory.impl.VectorFactory").createIOperation();
				OperationIndexVO currentOperationIndex = new OperationIndexVO(i, j);
				currentIOperation.setOperationIndex(currentOperationIndex);
				currentIOperation.setProcessingTime(T[i][j]);
				operations.add(currentIOperation);
			}
		}
		
		for (IOperation operation : operations) {
			operation.setInitialTime(TT[0][operation.getOperationIndex().getStationId() + 1]);
		}
		
		int operationsAmount = T.length * T[0].length;
		int index = 0;
		while(index < operationsAmount){
			
			int minInitialTime = Integer.MAX_VALUE;
			for (IOperation operation : operations) {
				int currentInitialTime = operation.getInitialTime();
				if(currentInitialTime < minInitialTime)
					minInitialTime = currentInitialTime;
			}
			
			ArrayList<IOperation> operationsMinInitialTime = new ArrayList<IOperation>();
			
			for (IOperation operation : operations) {
				if(operation.getInitialTime() == minInitialTime){
					operationsMinInitialTime.add(operation);
				}
			}
			
			// Obteniendo la operacion de mayor tiempo de proceso (LPT  LongestProcessingTime)
			// dentro de las que estan en el arreglo operationsMinInitialTime
			int minProcessingTime = Integer.MAX_VALUE;
			IOperation selectedOperation = null;
			
			for (int i = 0; i < operationsMinInitialTime.size(); i++) {
				IOperation operation = operationsMinInitialTime.get(i);
				
				int currentProcessingTime = T[operation.getOperationIndex().getJobId()][operation.getOperationIndex().getStationId()];
				if(currentProcessingTime < minProcessingTime){
					minProcessingTime = currentProcessingTime;
					selectedOperation = operation;
					
				}
			}
			operations.remove(selectedOperation);
			
			if(selectedOperation!=null)
				vector.scheduleOperation(selectedOperation.getOperationIndex());
			
//			finalList.calculateCMatrix();

			// Actualizando los tiempos de inicio de las operaciones que quedan por programar
			for (IOperation iOperation : operations) {
				
				vector.scheduleOperation(iOperation.getOperationIndex());
				
				IOperation lastJob = vector.getCiminus1J(iOperation, index + 1);
				IOperation lastStation = vector.getCiJminus1(iOperation, index + 1);
				
				int finalTimeLastJob = lastJob != null ? lastJob.getFinalTime() : 0;
				int finalTimeLastStation = lastStation != null ? lastStation.getFinalTime() : 0;
				int travelTime = lastJob != null ? vector.getTTBetas(lastJob, index + 1) : TT[0][iOperation.getOperationIndex().getStationId() + 1];
				
				int initialTime = Math.max(finalTimeLastJob + travelTime, finalTimeLastStation);
				int finalTime = initialTime + T[iOperation.getOperationIndex().getJobId()][iOperation.getOperationIndex().getStationId()];
				iOperation.setInitialTime(initialTime);
				iOperation.setFinalTime(finalTime);
				vector.removeOperationFromSchedule(iOperation.getOperationIndex());
			}
			index++;
		}
	}
	
	@Test
	public void testCalculateCMatrix(){
		int[][] C = vector.calculateCMatrix();
		
		MatrixUtils.printMatrix(C);
	}
}
