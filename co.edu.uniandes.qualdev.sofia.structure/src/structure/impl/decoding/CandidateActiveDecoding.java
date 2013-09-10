package structure.impl.decoding;

import java.util.ArrayList;

import structure.IOperation;

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

//		System.out.println();
//		System.out.println();
//		System.out.println("Decoding: " + originalVector);
//		System.out.println("Decoding.size: " + originalVector.size());
//		System.out.println();
		
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
		
//		System.out.println("candidateActiveSchedule: " + candidateActiveSchedule);
//		System.out.println("candidateActiveSchedule.size: " + candidateActiveSchedule.size());
//		System.out.println();
		
		for (int i = 0; i < originalVector.size(); i++){
			IOperation current = candidateActiveSchedule.get(i);
//			System.out.println("current: " + current);
			
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
			
//			System.out.println();
//			System.out.println("candidate:" + candidate);
//			System.out.println("firstStartOperation: " + firstStartOperation);
			
			
			
			if(firstStartOperation != null){
				int minEndTime = firstStartOperation.getFinalTime();
				
				if(candidate.getOperationIndex().getJobId() == firstStartOperation.getOperationIndex().getJobId()
						&& minEndTime <= minInitialTime - vector.getTT(candidate.getOperationIndex().getStationId(), firstStartOperation.getOperationIndex().getStationId())){
					
//					System.out.println("Schedule firstStartOperation");
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
					
//					System.out.println("Schedule firstStartOperation");
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
//					System.out.println("Schedule candidate");
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
//				System.out.println("Schedule last candidate");
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
//			System.out.println("candidateActiveSchedule: " + candidateActiveSchedule);
//			System.out.println("candidateActiveSchedule.size: " + candidateActiveSchedule.size());
//			System.out.println();
//			System.out.println();
		}
		
		if(candidateActiveSchedule.size() != 16){
			System.out.println("Pilas!!");
		}
		
		return candidateActiveSchedule;
	}
}
