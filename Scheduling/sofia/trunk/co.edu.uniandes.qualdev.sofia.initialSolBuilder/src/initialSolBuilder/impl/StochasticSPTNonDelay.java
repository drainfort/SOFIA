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
 * from a given instance of an Open Shop. It uses a NonDelay constructive algorithm with the SPT
 * (ShortestProcessingTime) rule.
 * 
 * @author Juan Pablo Caballero-Villalobos
 */
public class StochasticSPTNonDelay implements IInitialSolBuilder{

	// -----------------------------------------------
	// Constants
	// -----------------------------------------------
	
	private static final String constructiveAlgorithmName = "StochasticSPTNonDelay";
	
	// -----------------------------------------------
	// Atributes
	// -----------------------------------------------
	
	private String name;
	
	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------
	
	public StochasticSPTNonDelay(){
		name = constructiveAlgorithmName;
	}
	
	// -----------------------------------------------
	// Methods
	// -----------------------------------------------
	
	@Override
	public IStructure createInitialSolution(ArrayList<String> problemFiles, ArrayList<BetaVO> betas, String structureFactory, IGammaCalculator gammaCalculator) throws Exception {
		Integer [][] T = MatrixUtils.loadMatrix(problemFiles.get(0));
		Integer [][] TT = MatrixUtils.loadMatrix(problemFiles.get(1));
		
		//Amplitud porcentual para escoger los candidatos de la RCL
		float alfa=0.7f;
		
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
		//Poner Travel Times desde bodega
		for (IOperation operation : operations) {
			operation.setInitialTime(TT[0][operation.getOperationIndex().getStationId() + 1]);
		}
		
		int numberOfOperations = T.length * T[0].length;  //Numero total de operaciones
		int index = 0;
		while(index < numberOfOperations){
			//Calcula el tiempo minimo de inicio de las operaciones aun sin programar.
			int minInitialTime = Integer.MAX_VALUE;
			for (IOperation operation : operations) {
				int currentInitialTime = operation.getInitialTime();
				if(currentInitialTime < minInitialTime)
					minInitialTime = currentInitialTime;
			}
			//Crear estructura para guardar operaciones que se pueden procesar en el menor tiempo posible
			ArrayList<IOperation> operationsMinInitialTime = new ArrayList<IOperation>();
			//Identificar operaciones que pueden iniciar en el menor tiempo posible y establecer el min y max
			//tiempo incluido el procesamiento y travel times.
			
			int minProcessingTime = Integer.MAX_VALUE;
			int maxProcessingTime = -1;		
			for (IOperation operation : operations) {
				if(operation.getInitialTime() == minInitialTime){
					operationsMinInitialTime.add(operation);
					int currentProcessingTime = operation.getProcessingTime();
					if(currentProcessingTime > maxProcessingTime)
						maxProcessingTime = currentProcessingTime;
					if(currentProcessingTime < minProcessingTime)
						minProcessingTime = currentProcessingTime;
				}
			}
			
			// La RCL estara conformada por algunas operaciones que se encuentran en el arreglo
			// operationsMinInitialTime. Entonces se eliminaran las que no cumplan el criterio para permanecer 
			int upperBoundRCL=minProcessingTime+Math.round((float) alfa*(maxProcessingTime-minProcessingTime));
			int i=0;  
			while(i<operationsMinInitialTime.size()){
				IOperation operation = operationsMinInitialTime.get(i);

				if(operation.getProcessingTime() > upperBoundRCL)
					operationsMinInitialTime.remove(operation);					
				else
					i++;
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
				finalList.scheduleOperation(iOperation.getOperationIndex());  //OJO Para que se adiciona si se va a eliminar al final del ciclo?
				
				IOperation lastJob = finalList.getCiminus1J(iOperation, index + 1);
				IOperation lastStation = finalList.getCiJminus1(iOperation, index + 1);
				
				int finalTimeLastJob = lastJob != null ? lastJob.getFinalTime() : 0;
				int finalTimeLastStation = lastStation != null ? lastStation.getFinalTime() : 0;
				int travelTime = lastJob != null ? finalList.getTTBetas(lastJob, index + 1) : TT[0][iOperation.getOperationIndex().getStationId() + 1];
				
				int initialTime = Math.max(finalTimeLastJob + travelTime, finalTimeLastStation);
				int finalTime = initialTime + iOperation.getProcessingTime();
				iOperation.setInitialTime(initialTime);
				iOperation.setFinalTime(finalTime);
				
				finalList.removeOperationFromSchedule(iOperation.getOperationIndex());  //OJO comentario al inicio del ciclo
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