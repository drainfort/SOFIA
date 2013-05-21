package afs.schedule;

import afs.model.Operation;

/**
 * <p>Title: AFS Java version 3.0</p>
 *
 * <p>Description: Algorithms for Scheduling version Java</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Pylo.Uniandes.edu.co</p>
 * <b> TimedOperation Members </b>
 * <b> int jobNumber </b>  Job number<br>
 * <b> int machineNumber </b> Machine number <br>
 * <b> int workcenterNumber </b> Workcenter number<br>
 * <b> int startTime </b> Operation start time <br>
 * <b> int endTime </b> Operation end time <br>
 * <b> int setupStartTime </b> start time of the setup<br>
 * <b> int endStartTime </b> end time of the setup<br>
 * 
 * @author Gonzalo Mejia
 * @version 3.0
 */

public class TimedOperation{

	//start time of the schedule operation
	private int startTime;

	//end time of the operation
	private int endTime;

	// start time of the setup operation
	private int setupStartTime;

	//	start time of the schedule operation
	private int setupEndTime;

	// job number
	private int jobNumber;

	// workcenter number
	private int workcenterNumber;

	//machine number (nested in the workcenter)
	private int machineNumber;


	/**
	 * Default Constructor
	 */
	public TimedOperation() {
		super();
	}


	/** Constructor with fields
	 * @param endTime
	 * @param jobNumber
	 * @param machineNumber
	 * @param setupEndTime
	 * @param setupStartTime
	 * @param startTime
	 * @param workcenterNumber
	 */
	public TimedOperation(int endTime, int jobNumber, int machineNumber,
			int setupEndTime, int setupStartTime, int startTime,
			int workcenterNumber) {
		super();
		this.endTime = endTime;
		this.jobNumber = jobNumber;
		this.machineNumber = machineNumber;
		this.setupEndTime = setupEndTime;
		this.setupStartTime = setupStartTime;
		this.startTime = startTime;
		this.workcenterNumber = workcenterNumber;
	}

	public TimedOperation(Operation operation){

		this.jobNumber= operation.getJobNumber();
		this.workcenterNumber=operation.getWorkcenterNumber();
	}


	public TimedOperation (int jobNumber, int wksNumber, int mchNumber){

		this.jobNumber = jobNumber;
		this.workcenterNumber = wksNumber;
		this.machineNumber = mchNumber;
	}


	/**
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}


	/**
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}


	/**
	 * @return the setupStartTime
	 */
	public int getSetupStartTime() {
		return setupStartTime;
	}


	/**
	 * @return the setupEndTime
	 */
	public int getSetupEndSetupTime() {
		return setupEndTime;
	}


	/**
	 * @return the jobNumber
	 */
	public int getJobNumber() {
		return jobNumber;
	}


	/**
	 * @return the workcenterNumber
	 */
	public int getWorkcenterNumber() {
		return workcenterNumber;
	}


	/**
	 * @return the machineNumber
	 */
	public int getMachineNumber() {
		return machineNumber;
	}


	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}


	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	
	/**
	 * @param setupStartTime the setupStartTime to set
	 */
	public void setSetupStartTime(int setupStartTime) {
		this.setupStartTime = setupStartTime;
	}


	/**
	 * @param setupEndTime the setupEndTime to set
	 */
	public void setSetupEndTime(int setupEndTime) {
		this.setupEndTime = setupEndTime;
	}


	/**
	 * @param jobNumber the jobNumber to set
	 */
	public void setJobNumber(int jobNumber) {
		this.jobNumber = jobNumber;
	}


	/**
	 * @param workcenterNumber the workcenterNumber to set
	 */
	public void setWorkcenterNumber(int workcenterNumber) {
		this.workcenterNumber = workcenterNumber;
	}


	/**
	 * @param machineNumber the machineNumber to set
	 */
	public void setMachineNumber(int machineNumber) {
		this.machineNumber = machineNumber;
	}

	/**
	 * Calculates and assigns the earliest operation start and end times given a partial schedule
	 * The operation (this) must not have been scheduled.
	 * @param timedOperation
	 * @param jobNumber
	 * @param wksNumber
	 * @param mchNumber
	 * @param schedule
	 * invariant: all parameters except timedOperation
	 */
	public void setOperationTimes( int jobNumber, int wksNumber, int mchNumber,
			TimedSchedule schedule){

		//calculates the earliest setup start time for the operation
		int time;
		
		//calculates the initial start time for the operation including the depot travel time
		int travelTime=0;
		
		//Travel times between workcenters
		if (schedule.currentWkc[jobNumber].isEmpty()){
			travelTime=schedule.wkcArray[wksNumber].getDepotTravelTime();
		}
		else {	
			int lastPos=schedule.currentWkc[jobNumber].size()-1;
			int lastWkc=schedule.currentWkc[jobNumber].get(lastPos).getWorkcenterNumber();
			travelTime=schedule.wkcArray[lastWkc].getTravelTime(wksNumber);
		}		
		
		time = Math.max(schedule.machineTimes[wksNumber][mchNumber], schedule.jobTimes[jobNumber]+travelTime);
			
		
		this.setSetupStartTime(time);							
		
		int rowIndex=0, colIndex; // indices for the sequence dependent setup time
		
		if (schedule.timedGantt[wksNumber][mchNumber].isEmpty()){
			// 65 is the ASCCI code for 'A'
			rowIndex = schedule.wkcArray[wksNumber].getMachine(mchNumber).getStatus() - 65;
		}
		else {	
			int lastPosition = schedule.timedGantt[wksNumber][mchNumber].size()-1;
			int lastJob = schedule.timedGantt[wksNumber][mchNumber].get(lastPosition).getJobNumber();
			rowIndex = schedule.jobArray[lastJob].getStatus(wksNumber) - 65;
		}
		
		colIndex = schedule.jobArray[jobNumber].getStatus(wksNumber) - 65;
		int setupTime = schedule.wkcArray[wksNumber].getSetup(rowIndex, colIndex);//+schedule.wkcArray[lastWkc].getTravel(wksNumber);
		
		//Tiempo de inicio de una operación en un wkc
		time = time + setupTime;
		
		// end of the setup time
		this.setSetupEndTime(time);

		// start of the operation
		this.setStartTime(time);
		
		int processTime = schedule.jobArray[jobNumber].getProcessTime(wksNumber);
		
		time = time + processTime;
		
		//end of the operation
		this.setEndTime(time);
		


	}


	public Integer get(String string) {
		return null;
	}


	public void set(TimedOperation timedOperation) {
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + jobNumber;
		result = prime * result + machineNumber;
		result = prime * result + setupEndTime;
		result = prime * result + setupStartTime;
		result = prime * result + startTime;
		result = prime * result + workcenterNumber;
		return result;
	}

//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		TimedOperation other = (TimedOperation) obj;
////		if (endTime != other.endTime)
////			return false;
//		if (jobNumber != other.jobNumber)
//			return false;
//		if (machineNumber != other.machineNumber)
//			return false;
////		if (setupEndTime != other.setupEndTime)
////			return false;
////		if (setupStartTime != other.setupStartTime)
////			return false;
////		if (startTime != other.startTime)
////			return false;
//		if (workcenterNumber != other.workcenterNumber)
//			return false;
//		return true;
//	}





}
