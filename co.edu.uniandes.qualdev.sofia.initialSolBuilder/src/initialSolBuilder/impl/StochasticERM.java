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
 * from a given instance of an Open Shop. It uses a NonDelay constructive algorithm with the ERM
 * (Earliest Released Machine) rule.
 * Priority given to the operation that releases its machine at the earliest time. 
 * 
 * @author Juan Pablo Caballero-Villalobos
 */

public class StochasticERM implements IInitialSolBuilder{

	// -----------------------------------------------
	// Constants
	// -----------------------------------------------
	
	private static final String constructiveAlgorithmName = "StochasticERMNonDelay";
	
	// -----------------------------------------------
	// Atributes
	// -----------------------------------------------
	
	private String name;
	
	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------
	
	public StochasticERM(){
		name = constructiveAlgorithmName;
	}
	
	// -----------------------------------------------
	// Methods
	// -----------------------------------------------
	
	@Override
	public IStructure createInitialSolution(ArrayList<String> problemFiles, ArrayList<BetaVO> betas, String structureFactory, IGammaCalculator gammaCalculator) throws Exception {
		Integer [][] T = MatrixUtils.loadMatrix(problemFiles.get(0));
		Integer [][] TT = MatrixUtils.loadMatrix(problemFiles.get(1));
		
		
		float operationsAmount = T.length * T[0].length;
		//Amplitud porcentual para escoger los candidatos de la RCL
		float alfa=(float)((float)5/operationsAmount);
		
		IStructure finalList = AbstractStructureFactory.createNewInstance(structureFactory).createSolutionStructure(problemFiles, betas);

		//Cargar las operaciones usando datos de archivos
		ArrayList<IOperation> operations = new ArrayList<IOperation>();
		for (int i = 0; i < T.length; i++) {
			for (int j = 0; j < T[0].length; j++) {
				IOperation currentIOperation = AbstractStructureFactory.createNewInstance(structureFactory).createIOperation();
				OperationIndexVO currentOperationIndex = new OperationIndexVO(i, j);
				currentIOperation.setOperationIndex(currentOperationIndex);
				currentIOperation.setProcessingTime(T[i][j]);
				currentIOperation.setInitialTime(TT[0][j + 1]);
				currentIOperation.setFinalTime(currentIOperation.getProcessingTime()+currentIOperation.getInitialTime());
				operations.add(currentIOperation);
			}
		}
//		//Poner Travel Times desde bodega
//		for (IOperation operation : operations) {
//			operation.setInitialTime(TT[0][operation.getOperationIndex().getStationId() + 1]);
//		}
		
		int numberOfOperations = T.length * T[0].length;  //Numero total de operaciones
		int index = 0;
		while(index < numberOfOperations){
			//Calcula el tiempo minimo y maximo de finalizacion de las operaciones aun sin programar.
			int minFinalTime = Integer.MAX_VALUE;
			int maxFinalTime = -1;
			for (IOperation operation : operations) {
				int currentFinalTime = operation.getFinalTime();
				if(currentFinalTime < minFinalTime)
					minFinalTime = currentFinalTime;
				if(currentFinalTime > maxFinalTime)
					maxFinalTime = currentFinalTime;
			}
			//Crear estructura para guardar operaciones que se pueden procesar en el menor tiempo posible
			ArrayList<IOperation> operationsRCL = new ArrayList<IOperation>();
			
			// La RCL estara conformada por operaciones con tiempo de finalizacion menor o igual
			// al upper bound calculado. 
			int upperBoundRCL=minFinalTime+Math.round((float) alfa*(maxFinalTime-minFinalTime));
			for (IOperation operation : operations)
				if(operation.getFinalTime()<=upperBoundRCL)
					operationsRCL.add(operation);
			
			// Escoger posicion dentro de la RCL de la operacion a programar
			int posOperation=Math.round((float)Math.random()*(operationsRCL.size()-1));

			IOperation selectedOperation = null;
			selectedOperation = operationsRCL.get(posOperation);
			
			//Eliminar de las operaciones aun por programar
			operations.remove(selectedOperation);
			//Adicionar a la lista de operaciones ya programadas
			if(selectedOperation!=null)
				finalList.scheduleOperation(selectedOperation.getOperationIndex());
			
			//Calcular nuevos cij
			finalList.calculateCMatrix();

			// Actualizando los tiempos de inicio de las operaciones que quedan por programar
			for (IOperation iOperation : operations) {
				finalList.scheduleOperation(iOperation.getOperationIndex()); 
				
				IOperation lastJob = finalList.getCiminus1J(iOperation, index + 1);
				IOperation lastStation = finalList.getCiJminus1(iOperation, index + 1);
				
				int finalTimeLastJob = lastJob != null ? lastJob.getFinalTime() : 0;
				int finalTimeLastStation = lastStation != null ? lastStation.getFinalTime() : 0;
				int travelTime = lastJob != null ? finalList.getTTBetas(lastJob, index + 1) : TT[0][iOperation.getOperationIndex().getStationId() + 1];
				
				int initialTime = Math.max(finalTimeLastJob + travelTime, finalTimeLastStation);
				int finalTime = initialTime + iOperation.getProcessingTime();
				iOperation.setInitialTime(initialTime);
				iOperation.setFinalTime(finalTime);
				
				finalList.removeOperationFromSchedule(iOperation.getOperationIndex()); 
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
