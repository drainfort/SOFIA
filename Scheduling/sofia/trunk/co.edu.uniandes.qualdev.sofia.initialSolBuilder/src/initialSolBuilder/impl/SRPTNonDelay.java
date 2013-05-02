package initialSolBuilder.impl;

import gammaCalculator.IGammaCalculator;

import initialSolBuilder.IInitialSolBuilder;

import java.util.ArrayList;

import structure.IOperation;
import structure.IStructure;
import structure.factory.AbstractStructureFactory;

import common.types.BetaVO;
import common.types.OperationIndexVO;
import common.utils.MatrixUtils;

/**
 * Class that calculates an initial solution (represented in a permutation list)
 * from a given instance. It uses a NonDelay constructive algorithm with the SRPT
 * (ShortestRemainingProcessingTime) rule.
 * 
 * @author David Mendez-Acuna
 * @author Oriana Cendales
 */
public class SRPTNonDelay implements IInitialSolBuilder{

	// -----------------------------------------------
	// Constants
	// -----------------------------------------------
	
	private static final String constructiveAlgorithmName = "SRPTNonDelay";
	
	// -----------------------------------------------
	// Atributes
	// -----------------------------------------------
	
	private String name;
	
	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------
	
	public SRPTNonDelay(){
		name = constructiveAlgorithmName;
	}
	
	// -----------------------------------------------
	// Methods
	// -----------------------------------------------
	
	@Override
	public IStructure createInitialSolution(ArrayList<String> problemFiles, ArrayList<BetaVO> betas, String structureFactory, IGammaCalculator gammaCalculator) throws Exception {
		Integer [][] T = MatrixUtils.loadMatrix(problemFiles.get(0));
		Integer [][] TT = MatrixUtils.loadMatrix(problemFiles.get(1));
		
		IStructure finalList = AbstractStructureFactory.createNewInstance(structureFactory).createSolutionStructure(problemFiles, betas);

		ArrayList<IOperation> operations = new ArrayList<IOperation>();
		for (int i = 0; i < T.length; i++) {
			for (int j = 0; j < T[0].length; j++) {
				IOperation currentIOperation = AbstractStructureFactory.createNewInstance(structureFactory).createIOperation();
				OperationIndexVO currentOperationIndex = new OperationIndexVO(i, j);
				currentIOperation.setOperationIndex(currentOperationIndex);
				currentIOperation.setProcessingTime(T[i][j]);
				operations.add(currentIOperation);
			}
		}
		
		//Calculate initial remaining time
		for (int i = 0; i < operations.size(); i++) {
			IOperation operationI = operations.get(i);
			int remainingTime = 0;
			for (int j = 0; j < operations.size(); j++) {
				IOperation operationJ = operations.get(j);
				
				if(operationJ.getOperationIndex().getJobId() == operationI.getOperationIndex().getJobId()){
					remainingTime += operationJ.getProcessingTime();
				}
			}
			operationI.setJobRemainingTime(remainingTime);
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
			
			// Obteniendo la operacion de mayor tiempo de proceso (LRPT  LongestProcessingTime)
			// dentro de las que estan en el arreglo operationsMinInitialTime
			int minRemainingProcessingTime = Integer.MAX_VALUE;
			IOperation selectedOperation = null;
			
			for (int i = 0; i < operationsMinInitialTime.size(); i++) {
				IOperation operation = operationsMinInitialTime.get(i);
				
				int currentProcessingTime = operation.getJobRemainingTime();
				if(currentProcessingTime < minRemainingProcessingTime){
					minRemainingProcessingTime = currentProcessingTime;
					selectedOperation = operation;
					
				}
			}
			operations.remove(selectedOperation);
			
			if(selectedOperation!=null)
				finalList.scheduleOperation(selectedOperation.getOperationIndex());
			
			finalList.calculateCMatrix();

			// Actualizando los tiempos de inicio de las operaciones que quedan por programar
			for (IOperation iOperation : operations) {
				
				finalList.scheduleOperation(iOperation.getOperationIndex());
				
				IOperation lastJob = finalList.getCiminus1J(iOperation, index + 1);
				IOperation lastStation = finalList.getCiJminus1(iOperation, index + 1);
				
				int finalTimeLastJob = lastJob != null ? lastJob.getFinalTime() : 0;
				int finalTimeLastStation = lastStation != null ? lastStation.getFinalTime() : 0;
				int travelTime = lastJob != null ? TT[lastJob.getOperationIndex().getStationId() + 1][iOperation.getOperationIndex().getStationId() + 1] : TT[0][iOperation.getOperationIndex().getStationId() + 1];
				
				int initialTime = Math.max(finalTimeLastJob + travelTime, finalTimeLastStation);
				int finalTime = initialTime + T[iOperation.getOperationIndex().getJobId()][iOperation.getOperationIndex().getStationId()];
				iOperation.setInitialTime(initialTime);
				iOperation.setFinalTime(finalTime);
				finalList.removeOperationFromSchedule(iOperation.getOperationIndex());
			}
			
			//Updating initial remaining time
			for (int i = 0; i < operations.size(); i++) {
				IOperation operationI = operations.get(i);
				int remainingTime = 0;
				for (int j = 0; j < operations.size(); j++) {
					IOperation operationJ = operations.get(j);
					
					if(operationJ.getOperationIndex().getJobId() == operationI.getOperationIndex().getJobId()){
						remainingTime += operationJ.getProcessingTime();
					}
				}
				operationI.setJobRemainingTime(remainingTime);
			}
			index++;
		}
		
		return finalList;
	}
	
	@Override
	public String getName(){
		return name;
	}
}