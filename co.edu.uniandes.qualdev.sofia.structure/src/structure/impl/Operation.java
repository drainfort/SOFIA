package structure.impl;

import structure.IOperation;
import common.types.OperationIndexVO;

public class Operation implements IOperation{

	// -------------------------------------
	// Attributes
	// -------------------------------------
	
	private OperationIndexVO operationIndex;
	
	private int processingTime;
	
	private int initialTime;
	
	private int finalTime;
	
	private int jobRemainingTime;
	
	private boolean scheduled;
	
	// -------------------------------------
	// Constructor
	// -------------------------------------
	
	public Operation(){
		
	}

	public Operation(int processingTime, int job, int station){
		this.processingTime = processingTime;
		operationIndex = new OperationIndexVO(job, station);
	}
	
	public Operation(int processingTime, int job, int station, int machine){
		this.processingTime = processingTime;
		operationIndex = new OperationIndexVO(job, station, machine);
	}
	
	// -------------------------------------
	// Methods
	// -------------------------------------
	
	/**
	 * Returns an exact copy of the current operation.
	 */
	public IOperation clone(){
		Operation clone = new Operation(this.getOperationIndex().getJobId(), this.getOperationIndex().getStationId(), this.getOperationIndex().getMachineId());
		clone.processingTime = this.processingTime;
		clone.operationIndex = this.operationIndex;
		clone.initialTime = this.initialTime;
		clone.finalTime = this.finalTime;
		
		return clone;
	}
	
	/**
	 * Returns the string representation of an operation.
	 */
	public String toString(){
		return "<" + this.operationIndex.getJobId() + "," + this.operationIndex.getStationId() + "," + this.operationIndex.getMachineId() + ">";
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

	public int getProcessingTime() {
		return processingTime;
	}

	public void setProcessingTime(int processingTime) {
		this.processingTime = processingTime;
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