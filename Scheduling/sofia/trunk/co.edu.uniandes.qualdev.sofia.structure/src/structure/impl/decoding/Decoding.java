package structure.impl.decoding;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.Arrays;

import common.types.OperationIndexVO;
import structure.IOperation;
import structure.impl.Operation;
import structure.impl.Vector;

/**
 * Abstract class for encapsulating the functionality of "decoding" a permutation list
 * @author David Mendez-Acuna
 */
public abstract class Decoding {

	// ––––––––––––––––––––––––––––––––––––––––––––––
	// Attributes
	// ––––––––––––––––––––––––––––––––––––––––––––––
	
	protected Vector vector;
	
	protected ArrayList<OperationIndexVO> unscheduledOperations;
	
	// ––––––––––––––––––––––––––––––––––––––––––––––
	// Abstract methods
	// ––––––––––––––––––––––––––––––––––––––––––––––
	
	/**
	 * Decodes the permutation list received in the parameter
	 * @param operations Permutation list to be decoded
	 */
	abstract public ArrayList<IOperation> decode(ArrayList<IOperation> operations);
	
	// ––––––––––––––––––––––––––––––––––––––––––––––
	// Concrete methods
	// ––––––––––––––––––––––––––––––––––––––––––––––
	
	public void initialize(OperationIndexVO [][] problem){
		unscheduledOperations = new ArrayList<OperationIndexVO>();
		for(int j = 0; j < problem.length; j++){
			for(int z = 0; z < problem[j].length; z++){	
				unscheduledOperations.add(problem[j][z]);
			}
		}
	}
	
	/**
	 * Initializes the vector relationship with the reference given in the parameter
	 * @param vector
	 */
	public void setVector(Vector vector){
		this.vector = vector;
		if(unscheduledOperations==null)
			initialize(vector.getProblem());
	}
	
	protected boolean canSchedule(OperationIndexVO operationIndexVO, ArrayList<IOperation> vectorToSchedule) {
		for(int i=0; i< vectorToSchedule.size();i++){
			OperationIndexVO temp = vectorToSchedule.get(i).getOperationIndex();
			if(temp.getJobId()== operationIndexVO.getJobId() && operationIndexVO.getStationId()== temp.getStationId())
				return false;
		}
		return true;
	}
	
	public boolean scheduleOperation(OperationIndexVO operationIndexVO,  ArrayList<IOperation> vectorToSchedule) {

		Operation newOperation = new Operation(operationIndexVO);
		boolean schedule = canSchedule(operationIndexVO,  vectorToSchedule);
		
		if(schedule){
			newOperation.setScheduled(true);
			vectorToSchedule.add(newOperation);
		}
		return schedule;
	}
	
	public int[][] calculateCMatrix(ArrayList<IOperation> vectorToSchedule) {

		int [][] CSolution = new int[vector.getTotalJobs()][vector.getTotalStations() + 1];
			
		for (int i = 0; i < vectorToSchedule.size(); i++) {
			IOperation Cij = vectorToSchedule.get(i);
			
			int Ciminus1J = vector.getCiminus1J(Cij, i, vectorToSchedule) != null ? vector.getCiminus1J(Cij, i, vectorToSchedule).getFinalTime() : 0;
			int CiJminus1 = vector.getCiJminus1(Cij, i, vectorToSchedule) != null ? vector.getCiJminus1(Cij, i, vectorToSchedule).getFinalTime() : 0;
			
			int sumTTBetas = this.vector.getTTBetas(vector.getCiminus1J(Cij, i, vectorToSchedule), i, vectorToSchedule);
			int sumSetupBetas = this.vector.getSetupBetas(Cij.getOperationIndex().getJobId(), Cij.getOperationIndex().getStationId());
			
			int initialTime = Math.max(Ciminus1J + sumTTBetas, CiJminus1);
			int finalTime = initialTime + Cij.getOperationIndex().getProcessingTime() + sumSetupBetas;
			
			Cij.setInitialTime(initialTime);
			Cij.setFinalTime(finalTime);
			
			CSolution[Cij.getOperationIndex().getJobId()][Cij.getOperationIndex().getStationId()] = finalTime;
		}
		
		
		int[][] newC = vector.applyTearDownBetas(CSolution);
		if (newC != null)
			CSolution = newC;
	
		return CSolution;
	}

	public ArrayList<OperationIndexVO> getUnscheduledOperations() {
		ArrayList<OperationIndexVO> a= new ArrayList<OperationIndexVO>();
		a.addAll(unscheduledOperations);
		return a;
	}

	public void setUnscheduledOperations(
			ArrayList<OperationIndexVO> unscheduledOperations) {
		this.unscheduledOperations = unscheduledOperations;
	}
	
	
}
