package structure.impl.decoding;

import java.util.ArrayList;

import common.types.OperationIndexVO;
import structure.IOperation;
import structure.impl.Operation;

/**
 * Class that implements an active decoding for permutation lists. 
 * 
 * @author Jaime Romero
 * @author David Mendez-Acuna
 */
public class ActiveDecoding extends Decoding{

	// ––––––––––––––––––––––––––––––––––––––––––––––
	// Concrete methods
	// ––––––––––––––––––––––––––––––––––––––––––––––
	
	@Override
	public ArrayList<IOperation> decode(ArrayList<IOperation> originalVector) {
		
		// Creating a copy of the vector
		int sizeList = originalVector.size();
		ArrayList<IOperation> originalVectorCopy = new ArrayList<IOperation>();
		for (int i = 0; i < sizeList; i++){
			IOperation iOperation = new Operation (originalVector.get(i).getOperationIndex());
			originalVectorCopy.add(iOperation);
		}
		
		// Re-starting the original version so the new order can be proposed
		originalVector = new ArrayList<IOperation>();

		// Creating an array with the set of unscheduled operations
		int actualSize = 0;
		ArrayList<OperationIndexVO> unscheduledOperations = new ArrayList<OperationIndexVO>();
		for(int j = 0; j < vector.getProblem().length; j++){
			for(int z = 0; z < vector.getProblem()[j].length; z++){	
				unscheduledOperations.add(vector.getProblem()[j][z]);
			}
		}
		
		while (actualSize < sizeList){
			
			// Select the operation with lowest finish time in the unscheduled operations list. 
			int minFinalTime = Integer.MAX_VALUE;
			OperationIndexVO operationIndexMinFinalTime = null;
			
			for(int j = 0; j < unscheduledOperations.size(); j++){
				OperationIndexVO temp = unscheduledOperations.get(j);
				boolean canSchedulled = this.scheduleOperation(temp, originalVector);
				if (canSchedulled){
					this.calculateCMatrix(originalVector);
					int finalTime = originalVector.get(actualSize).getFinalTime();
					if (finalTime < minFinalTime ) {
						minFinalTime = finalTime;
						operationIndexMinFinalTime = temp;
					}
					originalVector.remove(actualSize);
				}
				else{
					unscheduledOperations.remove(j);
					j--;
				}
			}
			
			// Candidates array 
			ArrayList<OperationIndexVO> activeCandidates = new ArrayList<OperationIndexVO>();
			activeCandidates.add(operationIndexMinFinalTime);
			
			// Obtaining all the operations that can be scheduled in the same station/machine
			// Filtering the operations so we choose only the ones that produce an active schedule
			for(int i=0; i< vector.getProblem().length; i++){
				OperationIndexVO temp = vector.getProblem()[i][operationIndexMinFinalTime.getMachineId()];
				if(temp.getJobId()!=operationIndexMinFinalTime.getJobId()){
					
					boolean canSchedulled = this.scheduleOperation(temp, originalVector);
					if (canSchedulled){
						this.calculateCMatrix(originalVector);
						int startTimeTested = originalVector.get(actualSize).getInitialTime();
						if (startTimeTested < minFinalTime) {
							activeCandidates.add(temp);
						}
						originalVector.remove(actualSize);
					}
				}
			}
						
			// Final selection: We choose the operation that firstly appears in the original permutation list 
			boolean schedulled = false;
			OperationIndexVO chosen = null;
			int minIndex = originalVectorCopy.size();
			for(int i=0; i< activeCandidates.size();i++){
				OperationIndexVO temp =  activeCandidates.get(i);
				for(int j=0; j< originalVectorCopy.size();j++){
					OperationIndexVO opIndex = originalVectorCopy.get(j).getOperationIndex(); 
					if(temp.getJobId()==opIndex.getJobId() && temp.getStationId()== opIndex.getStationId()&& j<minIndex){
						chosen = temp;
						minIndex = j;
					}	
				}
			}
			
			// Schedule the operation
			schedulled = this.scheduleOperation(chosen, originalVector);
			if (schedulled){
				this.calculateCMatrix(originalVector);
				actualSize++;
				unscheduledOperations.remove(chosen);
			}
		}
		originalVectorCopy = null;
		this.calculateCMatrix(originalVector);
		return originalVector;
	}
}
