package structure.impl.decoding;

import java.util.ArrayList;

import common.types.OperationIndexVO;

import structure.IOperation;
import structure.impl.Operation;

/**
 * Class that implements an active decoding for permutation lists. 
 * 
 * @author Oriana Cendales
 * @author Juan Guillermo Amortegui
 * @author David Mendez-Acuna
 */
public class CandidateActiveDecoding extends Decoding{

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
				boolean a = vector.scheduleOperation(unscheduledOperations.get(j).getOperationIndex());
				if(a){
					vector.calculateCMatrix(pos);
					IOperation candidate = vector.getVector().get(pos);
					if(candidate.getInitialTime()<minStartTime2){
						minStartTime2 = candidate.getInitialTime();
						minStartTimeUnschedulledOperation = candidate;
						minfinalTime = candidate.getFinalTime();
					}
					vector.getOperations().remove(pos);
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
				if(minStartTime2<minStartTime){
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
		
		/*
		ArrayList<IOperation> candidateActiveSchedule = new ArrayList<IOperation>();
		
		for (int i = 0; i < originalVector.size(); i++){
			ArrayList<IOperation> iOperations = vector.getOperationsbyJobAndStation(originalVector.get(i).getOperationIndex());
			candidateActiveSchedule.addAll(iOperations);
		}

		// Updating the initial times
		for (int i = 0; i < candidateActiveSchedule.size(); i++){
			IOperation iOperation = candidateActiveSchedule.get(i);
			int sumTTBetas = vector.getTTBetas(null, i, candidateActiveSchedule);
			iOperation.setInitialTime(sumTTBetas);
			iOperation.setScheduled(false);
		}
		this.calculateCMatrix(candidateActiveSchedule);
				
		for (int i = 0; i < originalVector.size(); i++){
			IOperation current = candidateActiveSchedule.get(i);
			
			// Obtaining the candidates operations and selecting the one with lowest initial time
			IOperation candidate = null;
			int minInitialTime = Integer.MAX_VALUE;
			
			for (int j = i; j < candidateActiveSchedule.size(); j++) {
				IOperation currentCandidate = candidateActiveSchedule.get(j);
				if(currentCandidate.getOperationIndex().getJobId() == current.getOperationIndex().getJobId() &&
						currentCandidate.getOperationIndex().getStationId() == current.getOperationIndex().getStationId()){
					
					int currentInitialTime = currentCandidate.getInitialTime();
					if(currentInitialTime < minInitialTime){
						minInitialTime = currentInitialTime;
						candidate = currentCandidate;
					}
				}
			}
			
			// Selecting the operation that can start first
			IOperation firstStartOperation = null;
			int minInitialTimeAux = Integer.MAX_VALUE;
			
			for (int j = i + 1; j < candidateActiveSchedule.size(); j++) {
				IOperation currentOperation = candidateActiveSchedule.get(j);

				if(!(currentOperation.getOperationIndex().getJobId() == current.getOperationIndex().getJobId() &&
						currentOperation.getOperationIndex().getStationId() == current.getOperationIndex().getStationId())){
					int currentInitialTime = currentOperation.getInitialTime();
					if(currentInitialTime < minInitialTimeAux){
						minInitialTimeAux = currentInitialTime;
						firstStartOperation = currentOperation;
					}
				}
			}

			if(firstStartOperation != null){
				int minEndTime = firstStartOperation.getFinalTime();
				
				if(candidate.getOperationIndex().getJobId() == firstStartOperation.getOperationIndex().getJobId()
						&& minEndTime <= minInitialTime - vector.getTT(candidate.getOperationIndex().getStationId(), firstStartOperation.getOperationIndex().getStationId())){
					
//					Schedule firstStartOperation i.e., remove the machine operations and insert it in the current index
					firstStartOperation.setScheduled(true);
					ArrayList<IOperation> toRemove = new ArrayList<IOperation>();
					for (int j = i; j < candidateActiveSchedule.size(); j++) {
						IOperation currentCandidate = candidateActiveSchedule.get(j);
						if(currentCandidate.getOperationIndex().getJobId() == firstStartOperation.getOperationIndex().getJobId() &&
								currentCandidate.getOperationIndex().getStationId() == firstStartOperation.getOperationIndex().getStationId()){
							toRemove.add(currentCandidate);
						}
					}
					
					for (int j = 0; j < toRemove.size(); j++) {
						IOperation currentCandidate = toRemove.get(j);
						candidateActiveSchedule.remove(currentCandidate);
					}
					
					candidateActiveSchedule.add(i, firstStartOperation);
					this.calculateCMatrix(candidateActiveSchedule);
					
				}else if(candidate.getOperationIndex().getJobId() != firstStartOperation.getOperationIndex().getJobId()
						&& minEndTime <= minInitialTime){

					//Schedule firstStartOperation i.e., remove the machine operations and insert it in the current index
					firstStartOperation.setScheduled(true);
					ArrayList<IOperation> toRemove = new ArrayList<IOperation>();
					for (int j = i; j < candidateActiveSchedule.size(); j++) {
						IOperation currentCandidate = candidateActiveSchedule.get(j);
						if(currentCandidate.getOperationIndex().getJobId() == firstStartOperation.getOperationIndex().getJobId() &&
								currentCandidate.getOperationIndex().getStationId() == firstStartOperation.getOperationIndex().getStationId()){
							toRemove.add(currentCandidate);
						}
					}
					
					for (int j = 0; j < toRemove.size(); j++) {
						IOperation currentCandidate = toRemove.get(j);
						candidateActiveSchedule.remove(currentCandidate);
					}
					
					candidateActiveSchedule.add(i, firstStartOperation);
					this.calculateCMatrix(candidateActiveSchedule);
					
				}else{
					//Schedule candidate i.e., remove the machine operations
					candidate.setScheduled(true);
					ArrayList<IOperation> toRemove = new ArrayList<IOperation>();
					for (int j = i; j < candidateActiveSchedule.size(); j++) {
						IOperation currentCandidate = candidateActiveSchedule.get(j);
						if(currentCandidate.getOperationIndex().getJobId() == candidate.getOperationIndex().getJobId() &&
								currentCandidate.getOperationIndex().getStationId() == candidate.getOperationIndex().getStationId() &&
									currentCandidate.getOperationIndex().getMachineId() != candidate.getOperationIndex().getMachineId()){
							toRemove.add(currentCandidate);
						}
					}
					
					for (int j = 0; j < toRemove.size(); j++) {
						IOperation currentCandidate = toRemove.get(j);
						candidateActiveSchedule.remove(currentCandidate);
					}
					
					this.calculateCMatrix(candidateActiveSchedule);
				}
			}else{
				// Last operation. Schedule current
				candidate.setScheduled(true);
				ArrayList<IOperation> toRemove = new ArrayList<IOperation>();
				for (int j = i; j < candidateActiveSchedule.size(); j++) {
					IOperation currentCandidate = candidateActiveSchedule.get(j);
					if(currentCandidate.getOperationIndex().getJobId() == candidate.getOperationIndex().getJobId() &&
							currentCandidate.getOperationIndex().getStationId() == candidate.getOperationIndex().getStationId() &&
								currentCandidate.getOperationIndex().getMachineId() != candidate.getOperationIndex().getMachineId()){
						toRemove.add(currentCandidate);
					}
				}
				
				for (int j = 0; j < toRemove.size(); j++) {
					IOperation currentCandidate = toRemove.get(j);
					candidateActiveSchedule.remove(currentCandidate);
				}
				
				this.calculateCMatrix(candidateActiveSchedule);
			}
		}
		
		if(candidateActiveSchedule.size() != 16){
			System.out.println("Pilas!!");
		}
		
		return candidateActiveSchedule;
		*/
	}
}
