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
 * @author Jaime Romero
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
		// ALGORITMO CONSTRUCTIVO: CONSTRUYENDO LA SOLUCI�N INICIAL
		// -------------------------------------------------------------------------------------------------------------------
		
		// Inicializando lista de permutacion que se va a retornar
		IStructure finalList = AbstractStructureFactory.createNewInstance(structureFactory).createSolutionStructure(problemFiles, betas);
		OperationIndexVO[][] problem = finalList.getProblem();
		
		// Construyendo un arreglo con las operaciones sin programar basandose en el problema que ya est� definido
		// Este arreglo incluye TODAS las operaciones posibles. Por ejemplo: <0,0,0>, <0,0,1>, <0,0,2> ...
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
		
		// Actualizando los tIempos de inicio cuando existen traveltimes para las operaciones sin programar
		if(travelTimesIncluded){
			for (IOperation operation : operations) {
				operation.setInitialTime(TT[0][operation.getOperationIndex().getStationId() + 1]);
			}
		}
		
		int operationsAmount = T.length * T[0].length;
		int index = 0;
		
		// CICLO PRINCIPAL: Iteracion del algoritmo constructivo. Por cada iteraci�n, programa una operacion
		while(index < operationsAmount){
			
			// Calcula el menor tiempo de inicio dentro de las operaciones sin programar.
			int minInitialTime = Integer.MAX_VALUE;
			for (IOperation operation : operations) {
				int currentInitialTime = operation.getInitialTime();
				if(currentInitialTime < minInitialTime && currentInitialTime>-1 )
					minInitialTime = currentInitialTime;
			}
				
			// Construye un arreglo las operaciones que tienen ese menor tiempo de inicio
			ArrayList<IOperation> operationsMinInitialTime = new ArrayList<IOperation>();
			for (IOperation operation : operations) {
				if(operation.getInitialTime() == minInitialTime){
					operationsMinInitialTime.add(operation);
				}
			}
				
			// REGLA DE DESPACHO: Obteniendo la operacion de mayor tiempo de proceso (LPT  LongestProcessingTime)
			// dentro de las que estan en el arreglo operationsMinInitialTime
			int maxProcessingTime = -1;
			IOperation selectedOperation = null;
			
			for (int i = 0; i < operationsMinInitialTime.size(); i++) {
				IOperation operation = operationsMinInitialTime.get(i);
				
				int currentProcessingTime = T[operation.getOperationIndex().getJobId()][operation.getOperationIndex().getMachineId()];
				if(currentProcessingTime > maxProcessingTime){
					maxProcessingTime = currentProcessingTime;
					selectedOperation = operation;
				}
			}
			// Quita la operacion a programar, de las operaciones por programar
			operations.remove(selectedOperation);
			
			// Programa la operacion
			if(selectedOperation!=null){
				// �C�mo sabe que esta operaci�n si se puede programar? Porque ya se descartaron al inicio las que no.
				// Una operaci�n no se puede programar cuando ya hay una operaci�n previa atendiendo el mismo trabajo en la misma estaci�n. 
				finalList.scheduleOperation(selectedOperation.getOperationIndex());
			}
			
			// Calcula C con la nueva operaci�n programada
			finalList.calculateCMatrix();

			// Actualizando los tiempos de inicio de las operaciones que quedan por programar
			for (IOperation iOperation : operations) {
				// Esta lista contiene operaciones que no pueden ser programadas porque hay estaciones con varias m�quinas. Aqu� se descartarn estas operaciones. 
				boolean canBeScheduled = finalList.scheduleOperation(iOperation.getOperationIndex());
				
				if(canBeScheduled){
					
					// Calculos sobre los tiempos de inicio de las operaciones. 
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
				else{
					iOperation.setInitialTime(-1);
					iOperation.setFinalTime(-1);
				}
			}
			index++;
		}
		return finalList;
	}
	
	// TODO Ajustar este m�todo con los cambios para soportar FOS
	@Override
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
		// Arranca vac�a y en cada iteraci�n se le agrega una operaci�n.
		IStructure finalList = structure;

		// Arreglo con las operaciones sin programar
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
		
		if(travelTimesIncluded){
			// Para las operaciones sin programar actualiza los tiempos de inicio de la operacion.
			for (IOperation operation : operations) {
				operation.setInitialTime(TT[0][operation.getOperationIndex().getStationId() + 1]);
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
			if(selectedOperation!=null){
				finalList.scheduleOperation(selectedOperation.getOperationIndex());
			}
				
			finalList.calculateCMatrix();

			// Actualizando los tiempos de inicio de las operaciones que quedan por programar
			for (IOperation iOperation : operations) {
				
				finalList.scheduleOperation(iOperation.getOperationIndex());
				
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
			index++;
		}
		System.out.println();
		return finalList;
	}
	
	@Override
	public String getName(){
		return name;
	}
}