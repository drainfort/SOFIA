package structure;

import common.types.OperationIndexVO;

public interface IOperation {

	// -----------------------------------------
	// Methods
	// -----------------------------------------
	
	public IOperation clone();
	
	// -----------------------------------------
	// Getters and setters
	// -----------------------------------------
	
	public OperationIndexVO getOperationIndex();

	public void setOperationIndex(OperationIndexVO operationIndex);
	
	public int getProcessingTime();

	public void setProcessingTime(int processingTime);
	
	public int getInitialTime();

	public void setInitialTime(int initialTime);

	public int getFinalTime();

	public void setFinalTime(int finalTime);
	
	public int getJobRemainingTime();

	public void setJobRemainingTime(int jobRemainingTime);
}
