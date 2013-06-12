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
		boolean travelTimesIncluded = false;
		boolean setupTimesIncluded = false;
		
		BetaVO travelTimes = null;
		BetaVO setupTimes = null;
		
		for (BetaVO betaVO : betas) {
			if(betaVO.getName().equals("TravelTimes")){
				travelTimes = betaVO;
				travelTimesIncluded = travelTimes.isConsidered();
			}
			if(betaVO.getName().equals("SetupTimes")){
				setupTimes = betaVO;
				setupTimesIncluded = setupTimes.isConsidered();
			}
		}
		
		Integer [][] T = MatrixUtils.loadMatrix(problemFiles.get(0));
		Integer [][] TT = null;
		Integer [][] S = null;
		
		// When the problem includes travel times but not setup times
		if(travelTimesIncluded && !setupTimesIncluded){
			TT = MatrixUtils.loadMatrix(travelTimes.getInformationFiles().get(0));
		}
		// When the problem includes setup times but not travel times
		else if(setupTimesIncluded && !travelTimesIncluded){
			S = MatrixUtils.loadMatrix(setupTimes.getInformationFiles().get(0));
		}
		// When the problems includes both travel times and setup times
		else if(setupTimesIncluded && travelTimesIncluded){
			TT = MatrixUtils.loadMatrix(travelTimes.getInformationFiles().get(0));
			S = MatrixUtils.loadMatrix(setupTimes.getInformationFiles().get(0));
		}
		
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
				
				if(travelTimesIncluded)
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
			
				int travelTime = 0;
				int setupTime = 0;
				
				// When the problem includes travel times but not setup times
				if(travelTimesIncluded && !setupTimesIncluded){
					travelTime = lastJob != null ? TT[lastJob.getOperationIndex().getStationId() + 1][iOperation.getOperationIndex().getStationId() + 1] : TT[0][iOperation.getOperationIndex().getStationId() + 1];
				}
				// When the problem includes setup times but not travel times
				else if(setupTimesIncluded && !travelTimesIncluded){
					setupTime = S[iOperation.getOperationIndex().getJobId()][iOperation.getOperationIndex().getStationId()];
				}
				// When the problems includes both travel times and setup times
				else if(setupTimesIncluded && travelTimesIncluded){
					travelTime = lastJob != null ? TT[lastJob.getOperationIndex().getStationId() + 1][iOperation.getOperationIndex().getStationId() + 1] : TT[0][iOperation.getOperationIndex().getStationId() + 1];
					setupTime = S[iOperation.getOperationIndex().getJobId()][iOperation.getOperationIndex().getStationId()];
				}
				
				int initialTime = Math.max(finalTimeLastJob + travelTime, finalTimeLastStation);
				int finalTime = initialTime + iOperation.getProcessingTime() + setupTime;
				
				iOperation.setInitialTime(initialTime);
				iOperation.setFinalTime(finalTime);
				
				finalList.removeOperationFromSchedule(iOperation.getOperationIndex()); 
			}
			index++;
		}
		return finalList;
	}
	
	public IStructure createInitialSolution(Integer [][] TMatrix,  Integer[][] TTMatrix, Integer[][]STMatrix, String structureFactory, IGammaCalculator gammaCalculator, IStructure structure) throws Exception {
		Integer [][] T = TMatrix;
		Integer [][] TT = TTMatrix;
		Integer [][] S = TTMatrix;
		boolean travelTimesIncluded = false;
		boolean setupTimesIncluded = false;

		if(S!=null)
			setupTimesIncluded=true;
		if(TT!=null)
			travelTimesIncluded=true;
		
		// Esta es la lista de permutacion que se va a retornar.
		// Arranca vacía y en cada iteración se le agrega una operación.
		IStructure finalList = structure;
		float operationsAmount = T.length * T[0].length;
		float alfa=(float)((float)5/operationsAmount);

		//Cargar las operaciones usando datos de archivos
				ArrayList<IOperation> operations = new ArrayList<IOperation>();
				for (int i = 0; i < T.length; i++) {
					for (int j = 0; j < T[0].length; j++) {
						IOperation currentIOperation = AbstractStructureFactory.createNewInstance(structureFactory).createIOperation();
						OperationIndexVO currentOperationIndex = new OperationIndexVO(i, j);
						currentIOperation.setOperationIndex(currentOperationIndex);
						currentIOperation.setProcessingTime(T[i][j]);
						
						if(travelTimesIncluded)
							currentIOperation.setInitialTime(TT[0][j + 1]);
						
						currentIOperation.setFinalTime(currentIOperation.getProcessingTime()+currentIOperation.getInitialTime());
						operations.add(currentIOperation);
					}
				}
//				//Poner Travel Times desde bodega
//				for (IOperation operation : operations) {
//					operation.setInitialTime(TT[0][operation.getOperationIndex().getStationId() + 1]);
//				}
				
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
					
						int travelTime = 0;
						int setupTime = 0;
						
						// When the problem includes travel times but not setup times
						if(travelTimesIncluded && !setupTimesIncluded){
							travelTime = lastJob != null ? TT[lastJob.getOperationIndex().getStationId() + 1][iOperation.getOperationIndex().getStationId() + 1] : TT[0][iOperation.getOperationIndex().getStationId() + 1];
						}
						// When the problem includes setup times but not travel times
						else if(setupTimesIncluded && !travelTimesIncluded){
							setupTime = S[iOperation.getOperationIndex().getJobId()][iOperation.getOperationIndex().getStationId()];
						}
						// When the problems includes both travel times and setup times
						else if(setupTimesIncluded && travelTimesIncluded){
							travelTime = lastJob != null ? TT[lastJob.getOperationIndex().getStationId() + 1][iOperation.getOperationIndex().getStationId() + 1] : TT[0][iOperation.getOperationIndex().getStationId() + 1];
							setupTime = S[iOperation.getOperationIndex().getJobId()][iOperation.getOperationIndex().getStationId()];
						}
						
						int initialTime = Math.max(finalTimeLastJob + travelTime, finalTimeLastStation);
						int finalTime = initialTime + iOperation.getProcessingTime() + setupTime;
						
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
