package structure.impl.decoding;

import java.util.ArrayList;

import common.types.OperationIndexVO;

import structure.IOperation;
import structure.impl.Operation;

/**
 * Class that implements a decoding. 
 * 
 * @author Jaime Romero
 */
public class NewDecoding2 extends Decoding{

	// ––––––––––––––––––––––––––––––––––––––––––––––
	// Concrete methods
	// ––––––––––––––––––––––––––––––––––––––––––––––
	
	@Override
	public ArrayList<IOperation> decode(ArrayList<IOperation> originalVector) {
		
		ArrayList<IOperation> unscheduledOperations = new ArrayList<IOperation>();
		for(int i =0; i< vector.getProblem().length;i++){
			for(int j =0; j< vector.getProblem()[i].length;j++){
				unscheduledOperations.add(new Operation(vector.getProblem()[i][j]));
			}
		}
		
		vector.setVectorDecodSimple(new ArrayList<IOperation>());
		
		int pos = 0;
		for(int i=0; i< originalVector.size();i++){
			IOperation actual = originalVector.get(i);
			
			ArrayList<IOperation> candidateMachines = vector.getOperationsbyJobAndStation(actual.getOperationIndex());
			int minStartTime = Integer.MAX_VALUE;
			IOperation minStartTimeOperation = null;
			for(int j =0; j< candidateMachines.size();j++){
				vector.scheduleOperation(candidateMachines.get(j).getOperationIndex());
				vector.calculateCMatrix(pos);
				IOperation candidate = vector.getVector().get(pos);
				if(candidate.getInitialTime()<minStartTime){
					minStartTime = candidate.getInitialTime();
					minStartTimeOperation = candidate;
				}
				vector.getOperations().remove(pos);
			}
			
			int minStartTime2 = Integer.MAX_VALUE;
			IOperation minStartTimeUnschedulledOperation = null;
			int minfinalTime = Integer.MAX_VALUE;
			
			for(int j =0; j< unscheduledOperations.size();j++){
				IOperation temp = unscheduledOperations.get(j);
				if(temp.getOperationIndex().getJobId()==actual.getOperationIndex().getJobId() || temp.getOperationIndex().getStationId()==actual.getOperationIndex().getStationId())
				{
					boolean a = vector.scheduleOperation(unscheduledOperations.get(j).getOperationIndex());
					if(a){
						vector.calculateCMatrix(pos);
						IOperation candidate = vector.getVector().get(pos);
						if(candidate.getFinalTime()<minfinalTime){
							
							minStartTime2 = candidate.getInitialTime();
							minStartTimeUnschedulledOperation = candidate;
							minfinalTime = candidate.getFinalTime();
						}
						vector.getOperations().remove(pos);
					}
				}
			}
			
			OperationIndexVO newOperation = minStartTimeOperation.getOperationIndex();
			if(minStartTimeOperation.equals(minStartTimeUnschedulledOperation)){
				newOperation = minStartTimeOperation.getOperationIndex();
			}
			else if(minStartTimeOperation.getOperationIndex().getJobId()==minStartTimeUnschedulledOperation.getOperationIndex().getJobId()){
				if(minStartTime2<minStartTime && minfinalTime< minStartTime - vector.getTT(minStartTimeUnschedulledOperation.getOperationIndex().getStationId(), minStartTimeOperation.getOperationIndex().getStationId()))
				{
					newOperation = minStartTimeUnschedulledOperation.getOperationIndex();
				}
			}
			else if(minStartTimeOperation.getOperationIndex().getStationId()==minStartTimeUnschedulledOperation.getOperationIndex().getStationId()){
				if(minfinalTime<minStartTime){
					newOperation = minStartTimeUnschedulledOperation.getOperationIndex();
				}
			}
			
			vector.scheduleOperation(newOperation);
			vector.calculateCMatrix(0);
			pos++;
			
			for(int j =0; j< unscheduledOperations.size();j++){
				OperationIndexVO candidate = unscheduledOperations.get(j).getOperationIndex();
				if(candidate.getJobId()== newOperation.getJobId()&& candidate.getStationId()== newOperation.getStationId()){
					unscheduledOperations.remove(j);
					j--;
				}
			}
			for(int j = 0; j< originalVector.size();j++){
				OperationIndexVO candidate = originalVector.get(j).getOperationIndex();
				if(candidate.getJobId()== newOperation.getJobId()&& candidate.getStationId()== newOperation.getStationId()){
					originalVector.remove(j);
					i--;
				}
			}

		}	
		return vector.getVector();
	}
}
