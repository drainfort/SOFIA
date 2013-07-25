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
		boolean travelTimesIncluded = false;
		boolean setupTimesIncluded = false;
		
		// -------------------------------------------------------------------------------------------------------------------
		// TENIENDO EN CUENTA LAS BETAS
		// -------------------------------------------------------------------------------------------------------------------
		
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
		
		// -------------------------------------------------------------------------------------------------------------------
		// ALGORITMO CONSTRUCTIVO: CONSTRUYENDO LA SOLUCIÓN INICIAL
		// -------------------------------------------------------------------------------------------------------------------
	
		// Construyendo un arreglo con las operaciones sin programar basandose en el problema que ya está definido
		// Este arreglo incluye TODAS las operaciones posibles. Por ejemplo: <0,0,0>, <0,0,1>, <0,0,2> ...
		IStructure finalList = AbstractStructureFactory.createNewInstance(structureFactory).createSolutionStructure(problemFiles, betas);
		OperationIndexVO[][] problem = finalList.getProblem();
		
		// Arreglo con las operaciones sin programar
		ArrayList<IOperation> operations = new ArrayList<IOperation>();
		for (int i = 0; i < T.length; i++) {
			for (int j = 0; j < T[0].length; j++) {
				
				IOperation currentIOperation = AbstractStructureFactory.createNewInstance(structureFactory).createIOperation();
				OperationIndexVO temp = problem [i][j];
				OperationIndexVO currentOperationIndex = new OperationIndexVO(temp.getProcessingTime(), temp.getJobId(), temp.getStationId(), temp.getMachineId());
				currentIOperation.setOperationIndex(currentOperationIndex);
				operations.add(currentIOperation);
			}
		}
		
		// Actualizando los tiempos remanentes de las operaciones por programar
		for (int i = 0; i < operations.size(); i++) {
			IOperation operationI = operations.get(i);
			int remainingTime = 0;
			
			// Esta lista me sirve para saber cuales son las estaciones que el job actual (identificado con i) ya visitó. De esta manera 
			// se cumple la restricción de no visitar más de una máquina en la misma estación. 
			ArrayList<Integer> listStations = new ArrayList<Integer>();
			for (int j = 0; j < operations.size(); j++) {
				IOperation operationJ = operations.get(j);
				if(operationJ.getOperationIndex().getJobId() == operationI.getOperationIndex().getJobId() && !listStations.contains(operationJ.getOperationIndex().getStationId())){
					remainingTime += operationJ.getOperationIndex().getProcessingTime();
					listStations.add(operationJ.getOperationIndex().getStationId());
				}
			}
			operationI.setJobRemainingTime(remainingTime);
		}
		
		// Actualizando los tIempos de inicio cuando existen traveltimes para las operaciones sin programar
		if(travelTimesIncluded){
			for (IOperation operation : operations) {
				operation.setInitialTime(TT[0][operation.getOperationIndex().getStationId() + 1]);
			}
		}
		
		int operationsAmount = T.length * T[0].length;
		int index = 0;
		
		// CICLO PRINCIPAL: Iteracion del algoritmo constructivo. Por cada iteración, programa una operacion
		while(index < operationsAmount){
			
			// Calcula el menor tiempo de inicio dentro de las operaciones sin programar.
			int minInitialTime = Integer.MAX_VALUE;
			for (IOperation operation : operations) {
				int currentInitialTime = operation.getInitialTime();
				if(currentInitialTime < minInitialTime)
					minInitialTime = currentInitialTime;
			}
			
			// Construye un arreglo las operaciones que tienen ese menor tiempo de inicio
			ArrayList<IOperation> operationsMinInitialTime = new ArrayList<IOperation>();
			for (IOperation operation : operations) {
				if(operation.getInitialTime() == minInitialTime){
					operationsMinInitialTime.add(operation);
				}
			}
			
			// Obteniendo la operacion de menor tiempo de proceso (SRPT ShortestProcessingTime)
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
			
			if(selectedOperation!=null){
				removeAll(operations, selectedOperation);
				finalList.scheduleOperation(selectedOperation.getOperationIndex());
			}
			
			finalList.calculateCMatrix();

			// Actualizando los tiempos de inicio de las operaciones que quedan por programar
			for (IOperation iOperation : operations) {
				
				boolean canBeScheduled = finalList.scheduleOperation(iOperation.getOperationIndex());
				if(canBeScheduled){
					IOperation lastJob = finalList.getCiminus1J(iOperation, index + 1, finalList.getOperations());
					IOperation lastStation = finalList.getCiJminus1(iOperation, index + 1, finalList.getOperations());
					
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
					int finalTime = initialTime + T[iOperation.getOperationIndex().getJobId()][iOperation.getOperationIndex().getMachineId()] + setupTime;
					iOperation.setInitialTime(initialTime);
					iOperation.setFinalTime(finalTime);
					finalList.removeOperationFromSchedule(iOperation.getOperationIndex());
				}
			}
			
			//Updating initial remaining time
			for (int i = 0; i < operations.size(); i++) {
				IOperation operationI = operations.get(i);
				int remainingTime = 0;
				for (int j = 0; j < operations.size(); j++) {
					IOperation operationJ = operations.get(j);
					
					if(operationJ.getOperationIndex().getJobId() == operationI.getOperationIndex().getJobId()){
						remainingTime += operationJ.getOperationIndex().getProcessingTime();
					}
				}
				operationI.setJobRemainingTime(remainingTime);
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

		ArrayList<IOperation> operations = new ArrayList<IOperation>();
		for (int i = 0; i < T.length; i++) {
			for (int j = 0; j < T[0].length; j++) {
				IOperation currentIOperation = AbstractStructureFactory.createNewInstance(structureFactory).createIOperation();
				OperationIndexVO currentOperationIndex = new OperationIndexVO(i, j);
				currentOperationIndex.setProcessingTime(T[i][j]);
				currentIOperation.setOperationIndex(currentOperationIndex);
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
					remainingTime += operationJ.getOperationIndex().getProcessingTime();
				}
			}
			operationI.setJobRemainingTime(remainingTime);
		}
		
		if(travelTimesIncluded){
			for (IOperation operation : operations) {
				operation.setInitialTime(TT[0][operation.getOperationIndex().getStationId() + 1]);
			}
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
			
			if(selectedOperation!=null){
				removeAll(operations, selectedOperation);
				finalList.scheduleOperation(selectedOperation.getOperationIndex());
			}
			finalList.calculateCMatrix();

			// Actualizando los tiempos de inicio de las operaciones que quedan por programar
			for (IOperation iOperation : operations) {
				
				boolean canBeScheduled = finalList.scheduleOperation(iOperation.getOperationIndex());
				if(canBeScheduled){
				
					IOperation lastJob = finalList.getCiminus1J(iOperation, index + 1, finalList.getOperations());
					IOperation lastStation = finalList.getCiJminus1(iOperation, index + 1, finalList.getOperations());
					
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
					int finalTime = initialTime + T[iOperation.getOperationIndex().getJobId()][iOperation.getOperationIndex().getStationId()] + setupTime;
					iOperation.setInitialTime(initialTime);
					iOperation.setFinalTime(finalTime);
					finalList.removeOperationFromSchedule(iOperation.getOperationIndex());
				}
			}
			
			//Updating initial remaining time
			for (int i = 0; i < operations.size(); i++) {
				IOperation operationI = operations.get(i);
				int remainingTime = 0;
				ArrayList<Integer> listStations = new ArrayList<Integer>();
				for (int j = 0; j < operations.size(); j++) {
					IOperation operationJ = operations.get(j);
					
					if(operationJ.getOperationIndex().getJobId() == operationI.getOperationIndex().getJobId() && !listStations.contains(operationJ.getOperationIndex().getStationId())){
						remainingTime += operationJ.getOperationIndex().getProcessingTime();
						listStations.add(operationJ.getOperationIndex().getStationId());
					}
				}
				operationI.setJobRemainingTime(remainingTime);
			}
			index++;
		}
		
		return finalList;
	}
	
	private void removeAll(ArrayList<IOperation> operations,
			IOperation selectedOperation) {
		
		int job = selectedOperation.getOperationIndex().getJobId();
		int station = selectedOperation.getOperationIndex().getStationId();
		
		for(int i=0; i< operations.size();i++){
			OperationIndexVO temp = operations.get(i).getOperationIndex();
			
			if(temp.getJobId()==job && temp.getStationId()==station){
				operations.remove(i);
				i--;
			}
		}
		
	}
	
	@Override
	public String getName(){
		return name;
	}
}