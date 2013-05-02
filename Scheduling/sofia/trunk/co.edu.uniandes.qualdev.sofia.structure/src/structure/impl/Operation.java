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
	
	/**
	 * The longest path is already calculated
	 */
	private boolean longestPathCalculated = false;

	/**
	 * The longest path to the current node
	 */
	private Integer longestPath = 0;
	
	/**
	 * ¿Qué es esto?
	 */
	private boolean sameInitialTime= false;
	
	/**
	 * ¿Qué es esto?
	 */
	private int positionSort = -1;
	
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
	
	public IOperation clone(){
		
		Operation clone = new Operation(this.getOperationIndex().getJobId(), this.getOperationIndex().getStationId(), this.getOperationIndex().getMachineId());
		clone.processingTime = this.processingTime;
		clone.operationIndex = this.operationIndex;
		clone.initialTime = this.initialTime;
		clone.finalTime = this.finalTime;
		
		return clone;
	}
	
	public String toString(){
		return "<" + this.operationIndex.getJobId() + "," + this.operationIndex.getStationId() + "," + this.operationIndex.getMachineId() + ">";
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
}