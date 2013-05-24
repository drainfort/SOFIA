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
public class StochasticLPTNonDelay implements IInitialSolBuilder{

	// -----------------------------------------------
	// Constants
	// -----------------------------------------------
	
	private static final String constructiveAlgorithmName = "StochasticLPTNonDelay";
	
	// -----------------------------------------------
	// Atributes
	// -----------------------------------------------
	
	private String name;
	
	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------
	
	public StochasticLPTNonDelay(){
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
				travelTimesIncluded = true;
			}
			if(betaVO.getName().equals("SetupTimes")){
				setupTimes = betaVO;
				setupTimesIncluded = true;
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
		
		//Amplitud porcentual para escoger los candidatos de la RCL
		float alfa=0.5f;
		
		IStructure finalList = AbstractStructureFactory.createNewInstance(structureFactory).createSolutionStructure(problemFiles, betas);

		//Cargar las operaciones usando datos de archivos
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
		
		if(travelTimesIncluded){
			//Poner Travel Times desde bodega
			for (IOperation operation : operations) {
				operation.setInitialTime(TT[0][operation.getOperationIndex().getStationId() + 1]);
			}
		}
		
		int operationsAmount = T.length * T[0].length;  //Numero total de operaciones
		int index = 0;
		while(index < operationsAmount){
			//Calcula el tiempo minimo de inicio de las operaciones.
			int minInitialTime = Integer.MAX_VALUE;
			for (IOperation operation : operations) {
				int currentInitialTime = operation.getInitialTime();
				if(currentInitialTime < minInitialTime)
					minInitialTime = currentInitialTime;
			}
			//Crear estructura para guardar operaciones que se pueden procesar en el menor tiempo posible
			ArrayList<IOperation> operationsMinInitialTime = new ArrayList<IOperation>();
			//Identificar operaciones que pueden iniciar en el menor tiempo posible y establecer el min y max
			//tiempo de procesamiento entre ellas.
			int minProcessingTime = Integer.MAX_VALUE;
			int maxProcessingTime = -1;		
			for (IOperation operation : operations) {
				if(operation.getInitialTime() == minInitialTime){
					operationsMinInitialTime.add(operation);
					int currentProcessingTime = T[operation.getOperationIndex().getJobId()][operation.getOperationIndex().getStationId()];
					if(currentProcessingTime > maxProcessingTime){
						maxProcessingTime = currentProcessingTime;
					}
					if(currentProcessingTime < minProcessingTime){
						minProcessingTime = currentProcessingTime;
					}
				}
			}
			
			// Obteniendo la operacion de mayor tiempo de proceso (LPT  LongestProcessingTime)
			// dentro de las que estan en el arreglo operationsMinInitialTime
			// La RCL estara conformada por algunas operaciones que se encuentran en el arreglo
			// operationsMinInitialTime. Entonces se eliminaran las que no cumplan el criterio para permanecer 
			int lowerBoundRCL=maxProcessingTime-Math.round((float) alfa*(maxProcessingTime-minProcessingTime));
			int i=0;
			while(i<operationsMinInitialTime.size()){
				IOperation operation = operationsMinInitialTime.get(i);
				int currentProcessingTime = T[operation.getOperationIndex().getJobId()][operation.getOperationIndex().getStationId()];

				if(currentProcessingTime < lowerBoundRCL){
					operationsMinInitialTime.remove(operation);					
				}else{
					i++;
				}
			}
			

			// Escoger operacion a programar
			int posOperation=Math.round((float)Math.random()*(operationsMinInitialTime.size()-1));

			IOperation selectedOperation = null;
			selectedOperation = operationsMinInitialTime.get(posOperation);
			
			//Eliminar de las operaciones aun por programar
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
		return finalList;
	}
	
	@Override
	public String getName(){
		return name;
	}
}