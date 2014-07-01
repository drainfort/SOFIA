package structure.impl;

import structure.IOperation;
import common.types.OperationIndexVO;

public class Operation implements IOperation{

	// -------------------------------------
	// Attributes
	// -------------------------------------
	
	/**
	 * Operation index of the operation
	 */
	private OperationIndexVO operationIndex;
	
	/**
	 * Initial time of the operation
	 */
	private int initialTime;
	
	/**
	 * Time in which the operation ends
	 */
	private int finalTime;
	
	/**
	 * Time to finish the job
	 */
	private int jobRemainingTime;
	
	/**
	 * If the operation is schedulled or not
	 */
	private boolean scheduled;
	
	// -------------------------------------
	// Constructor
	// -------------------------------------
	/**
	 * Constructor of the class
	 */
	public Operation(){
		
	}

	/**
	 * Co0nstructor of the class
	 * @param operationIndex- index of the operation
	 */
	public Operation(OperationIndexVO operationIndex){
		this.operationIndex = operationIndex;
	}
	
	
	// -------------------------------------
	// Methods
	// -------------------------------------
	
	/**
	 * Returns an exact copy of the current operation.
	 */
	public IOperation clone(){
		Operation clone = new Operation(this.operationIndex);
		clone.initialTime = this.initialTime;
		clone.finalTime = this.finalTime;
		clone.scheduled = this.scheduled;
		return clone;
	}
	
	/**
	 * Returns the string representation of an operation.
	 */
	public String toString(){
		return "<" + this.operationIndex.getJobId() + "," + this.operationIndex.getStationId() + "," + this.operationIndex.getMachineId() + "," +"> " +  this.initialTime + " - " + this.finalTime;
	}
	
	/**
	 * Compares two operations and return true if equals. False otherwise. 
	 */
	public boolean equals(Object toCompare){
		Operation operationToCompare = (Operation) toCompare;
		
		boolean answer = this.getOperationIndex().getJobId() == operationToCompare.getOperationIndex().getJobId() &&
				this.getOperationIndex().getStationId() == operationToCompare.getOperationIndex().getStationId() &&
						this.getOperationIndex().getMachineId() == operationToCompare.getOperationIndex().getMachineId();
		
		return answer;
	}
	
	// -------------------------------------
	// Getters and setters
	// -------------------------------------
	
	public OperationIndexVO getOperationIndex() {
		return operationIndex;
	}

	public void setOperationIndex(OperationIndexVO operationIndex) {
		this.operationIndex = operationIndex;
	}
	
	public int getInitialTime() {
		return initialTime;
	}

	public void setInitialTime(int initialTime) {
		this.initialTime = initialTime;
	}

	public int getFinalTime() {
		return finalTime;
	}

	public void setFinalTime(int finalTime) {
		this.finalTime = finalTime;
	}
	
	public int getJobRemainingTime() {
		return jobRemainingTime;
	}

	public void setJobRemainingTime(int jobRemainingTime) {
		this.jobRemainingTime = jobRemainingTime;
	}

	public boolean isScheduled() {
		return scheduled;
	}

	public void setScheduled(boolean scheduled) {
		this.scheduled = scheduled;
	}
}