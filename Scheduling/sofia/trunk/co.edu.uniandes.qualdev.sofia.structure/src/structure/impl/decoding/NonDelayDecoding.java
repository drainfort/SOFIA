package structure.impl.decoding;

import java.util.ArrayList;

import common.types.OperationIndexVO;

import structure.IOperation;

/**
 * Class that implements a non-delay decoding for permutation lists. 
 * 
 * @author Jaime Romero
 * @author David Mendez-Acuna
 */
public class NonDelayDecoding extends Decoding{

	// ––––––––––––––––––––––––––––––––––––––––––––––
	// Concrete methods
	// ––––––––––––––––––––––––––––––––––––––––––––––
	
	@Override
	public ArrayList<IOperation> decode(ArrayList<IOperation> originalVector) {
		
		// Building an array with the unscheduled operations sorted according the original vector
		ArrayList<IOperation> vectorDecodNonDelay = new ArrayList<IOperation>();
		for (int i = 0; i < originalVector.size(); i++){
			ArrayList<IOperation> iOperations = vector.getOperationsbyJobAndStation(originalVector.get(i).getOperationIndex());
			vectorDecodNonDelay.addAll(iOperations);
		}
			
		// Updating the initial times
		for (int i = 0; i < vectorDecodNonDelay.size(); i++){
			IOperation iOperation = vectorDecodNonDelay.get(i);
			int sumTTBetas = vector.getTTBetas(null, i, vectorDecodNonDelay);
			iOperation.setInitialTime(sumTTBetas);
			iOperation.setScheduled(false);
		}
		
		int operationsAmount = vectorDecodNonDelay.size();
		int index = 0;
		
		// Main loop: Schedules one operation for each iteration according the constructive algorithm
		while(index < operationsAmount){
			
			// Calculating the lowest initial time and 
			// selects the next operation to schedule according the lowest initial time
			int minInitialTime = Integer.MAX_VALUE;
			IOperation selectedOperation = null;
			for (int i = index; i < vectorDecodNonDelay.size(); i++) {
				IOperation operation = vectorDecodNonDelay.get(i);
				int currentInitialTime = operation.getInitialTime();
				
				if(currentInitialTime < minInitialTime){
					selectedOperation = operation;
					minInitialTime = currentInitialTime;
				}
					
			} 
			
			if(selectedOperation!=null){
				selectedOperation.setScheduled(true);
				this.removeAll(vectorDecodNonDelay, selectedOperation);
				operationsAmount = vectorDecodNonDelay.size();
			}
			
			// Performing the exchanges required by the decoding strategy
			if(selectedOperation != vectorDecodNonDelay.get(index)){
				IOperation operationAtIndex = vectorDecodNonDelay.get(index);
				int indexOfSelected = vectorDecodNonDelay.indexOf(selectedOperation);
				
				vectorDecodNonDelay.set(index, selectedOperation);
				vectorDecodNonDelay.set(indexOfSelected, operationAtIndex);
			}
			
			// Calculating the final time of the selected operation
			int finalTimeToSchedule = selectedOperation.getInitialTime() + selectedOperation.getOperationIndex().getProcessingTime() ;
			selectedOperation.setFinalTime(finalTimeToSchedule);
			
			// Actualizando los tiempos de inicio de las operaciones que quedan por programar
			for (int i = index + 1; i < vectorDecodNonDelay.size(); i++){
				IOperation iOperation = vectorDecodNonDelay.get(i);
				
				IOperation lastJobSchedule = vector.getCiminus1J(iOperation, i, vectorDecodNonDelay);
				int finalTimeLastJob = lastJobSchedule != null ? lastJobSchedule.getFinalTime() : 0;
				
				IOperation lastStationSchedule = vector.getCiJminus1(iOperation, i, vectorDecodNonDelay);
				int finalTimeLastStation = lastStationSchedule != null ? lastStationSchedule.getFinalTime() : 0;
				
				int sumTTBetas = vector.getTTBetas(vector.getCiminus1J(iOperation, i, vectorDecodNonDelay), i, vectorDecodNonDelay);
				
				int initialTime = Math.max(finalTimeLastJob + sumTTBetas, finalTimeLastStation);
				iOperation.setInitialTime(initialTime);
			}
			index++;
		}
		return vectorDecodNonDelay;
	}
	
	private void removeAll(ArrayList<IOperation> operations,
			IOperation selectedOperation) {
		
		int job = selectedOperation.getOperationIndex().getJobId();
		int station = selectedOperation.getOperationIndex().getStationId();
		int machine = selectedOperation.getOperationIndex().getMachineId();
		
		for(int i=0; i< operations.size();i++){
			OperationIndexVO temp = operations.get(i).getOperationIndex();
			
			if(temp.getJobId()==job && temp.getStationId()==station && temp.getMachineId()!=machine){
				operations.remove(i);
				i--;
			}
		}
		
	}

}
