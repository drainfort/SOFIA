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
 * from a given instance. It uses a NonDelay constructive algorithm with the LPT
 * (LongestProcessingTime) rule.
 * 
 * @author David Mendez-Acuna
 * @author Oriana Cendales
 */
public class LPTNonDelay implements IInitialSolBuilder{

	// -----------------------------------------------
	// Constants
	// -----------------------------------------------
	
	private static final String constructiveAlgorithmName = "LPTNonDelay";
	
	// -----------------------------------------------
	// Atributes
	// -----------------------------------------------
	
	private String name;
	
	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------
	
	public LPTNonDelay(){
		name = constructiveAlgorithmName;
	}
	
	// -----------------------------------------------
	// Methods
	// -----------------------------------------------
	
	@Override
	public IStructure createInitialSolution(ArrayList<String> problemFiles, ArrayList<BetaVO> betas, String structureFactory, IGammaCalculator gammaCalculator) throws Exception {
		boolean tt = false;
		boolean setup = false;
		
		BetaVO travelTimes = null;
		BetaVO setupTimes = null;
		
		for (BetaVO betaVO : betas) {
			if(betaVO.getName().equals("TravelTimes")){
				travelTimes = betaVO;
				tt = true;
			}
			if(betaVO.getName().equals("SetupTimes")){
				setupTimes = betaVO;
				setup = true;
			}
		}
		
		if(tt){
			Integer [][] T = MatrixUtils.loadMatrix(problemFiles.get(0));
			Integer [][] TT = MatrixUtils.loadMatrix(travelTimes.getInformationFiles().get(0));
			
			// Esta es la lista de permutacion que se va a retornar.
			// Arranca vac�a y en cada iteraci�n se le agrega una operaci�n.
			IStructure finalList = AbstractStructureFactory.createNewInstance(structureFactory).createSolutionStructure(problemFiles, betas);

			// Arreglo con las operaciones sin programar
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
			
			// Para las operaciones sin programar actualiza los tiempos de inicio de la operacion.
			for (IOperation operation : operations) {
				operation.setInitialTime(TT[0][operation.getOperationIndex().getStationId() + 1]);
			}
			
			int operationsAmount = T.length * T[0].length;
			int index = 0;
			
			// Iteracion del algoritmo constructivo. Por cada iteraci�n, programa una operacion
			while(index < operationsAmount){
				
				// Calcula el menor tiempo de inicio
				int minInitialTime = Integer.MAX_VALUE;
				for (IOperation operation : operations) {
					int currentInitialTime = operation.getInitialTime();
					if(currentInitialTime < minInitialTime)
						minInitialTime = currentInitialTime;
				}
				
				// Agrega a este arreglo las operaciones que tienen ese menor tiempo de inicio
				ArrayList<IOperation> operationsMinInitialTime = new ArrayList<IOperation>();
				for (IOperation operation : operations) {
					if(operation.getInitialTime() == minInitialTime){
						operationsMinInitialTime.add(operation);
					}
				}
				
				// Obteniendo la operacion de mayor tiempo de proceso (LPT  LongestProcessingTime)
				// dentro de las que estan en el arreglo operationsMinInitialTime
				int maxProcessingTime = -1;
				IOperation selectedOperation = null;
				
				for (int i = 0; i < operationsMinInitialTime.size(); i++) {
					IOperation operation = operationsMinInitialTime.get(i);
					
					int currentProcessingTime = T[operation.getOperationIndex().getJobId()][operation.getOperationIndex().getStationId()];
					if(currentProcessingTime > maxProcessingTime){
						maxProcessingTime = currentProcessingTime;
						selectedOperation = operation;
						
					}
				}
				// Quita la operacion a programar, de las operaciones por programar
				operations.remove(selectedOperation);
				
				// Programa la operacion
				if(selectedOperation!=null){
					finalList.scheduleOperation(selectedOperation.getOperationIndex());
				}
				
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
					int finalTime = initialTime + T[iOperation.getOperationIndex().getJobId()][iOperation.getOperationIndex().getStationId()];
					iOperation.setInitialTime(initialTime);
					iOperation.setFinalTime(finalTime);
					finalList.removeOperationFromSchedule(iOperation.getOperationIndex());
				}
				index++;
			}
			System.out.println();
			return finalList;
		}else if(setup){
			Integer [][] T = MatrixUtils.loadMatrix(problemFiles.get(0));
			Integer [][] S = MatrixUtils.loadMatrix(problemFiles.get(1));
			
			// Esta es la lista de permutacion que se va a retornar.
			// Arranca vac�a y en cada iteraci�n se le agrega una operaci�n.
			IStructure finalList = AbstractStructureFactory.createNewInstance(structureFactory).createSolutionStructure(problemFiles, betas);

			// Arreglo con las operaciones sin programar
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
			
			int operationsAmount = T.length * T[0].length;
			int index = 0;
			
			// Iteracion del algoritmo constructivo. Por cada iteraci�n, programa una operacion
			while(index < operationsAmount){
				
				// Calcula el menor tiempo de inicio
				int minInitialTime = Integer.MAX_VALUE;
				for (IOperation operation : operations) {
					int currentInitialTime = operation.getInitialTime();
					if(currentInitialTime < minInitialTime)
						minInitialTime = currentInitialTime;
				}
				
				// Agrega a este arreglo las operaciones que tienen ese menor tiempo de inicio
				ArrayList<IOperation> operationsMinInitialTime = new ArrayList<IOperation>();
				for (IOperation operation : operations) {
					if(operation.getInitialTime() == minInitialTime){
						operationsMinInitialTime.add(operation);
					}
				}
				
				// Obteniendo la operacion de mayor tiempo de proceso (LPT  LongestProcessingTime)
				// dentro de las que estan en el arreglo operationsMinInitialTime
				int maxProcessingTime = -1;
				IOperation selectedOperation = null;
				
				for (int i = 0; i < operationsMinInitialTime.size(); i++) {
					IOperation operation = operationsMinInitialTime.get(i);
					
					int currentProcessingTime = T[operation.getOperationIndex().getJobId()][operation.getOperationIndex().getStationId()];
					if(currentProcessingTime > maxProcessingTime){
						maxProcessingTime = currentProcessingTime;
						selectedOperation = operation;
						
					}
				}
				// Quita la operacion a programar, de las operaciones por programar
				operations.remove(selectedOperation);
				
				// Programa la operacion
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
					
					int initialTime = Math.max(finalTimeLastJob, finalTimeLastStation);
					int finalTime = initialTime + T[iOperation.getOperationIndex().getJobId()][iOperation.getOperationIndex().getStationId()] +  + S[iOperation.getOperationIndex().getJobId()][iOperation.getOperationIndex().getStationId()];
					iOperation.setInitialTime(initialTime);
					iOperation.setFinalTime(finalTime);
					finalList.removeOperationFromSchedule(iOperation.getOperationIndex());
				}
				index++;
			}
			return finalList;
		}else{
			return null;
		}
	}
	
	@Override
	public String getName(){
		return name;
	}
}