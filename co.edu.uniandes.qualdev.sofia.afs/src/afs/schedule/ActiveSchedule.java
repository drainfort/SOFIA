/**
 * 
 */
package afs.schedule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


import afs.model.Operation;
import afs.model.Job;
import afs.model.Workcenter;
import afs.schedule.oss.NonDelayOSSchedule;
import afs.schedule.oss.OpenShopSchedule;

/**
 * @author gmejia
 *
 */
//public abstract class ActiveSchedule {
	public class ActiveSchedule {

	/**
	 * An array [i][l] of Unscheduled (candidate) Timed Operations
	 * Stores those operations with no predecessors or whose predecessors have been scheduled
	 */
	protected ArrayList<TimedOperation> unScheduledOperations[][];
	
	
	/**
	 *  A timed open shop schedule
	 */
	protected TimedSchedule timedSchedule;

	/**
	 * The array of jobs
	 */
	protected Job[] jobArray;

	/**
	 * The array of workcenters
	 */
	protected Workcenter[] wkcArray;


	/**
	 * @param wkcArray
	 * @param jobArray
	 */
	public ActiveSchedule(Workcenter[] wkcArray, Job[] jobArray){

		this.jobArray = jobArray;
		this.wkcArray = wkcArray;
		unScheduledOperations = new ArrayList[Workcenter.getNumberOfWorkcenters()][];
		//Ori
		//currentSchedule= new ArrayList();


	}

	/**
	 * @return the unScheduledOperations
	 */
	public ArrayList<TimedOperation>[][] getUnScheduledOperations() {
		return unScheduledOperations;
	}

	/**
	 * @return the timedSchedule
	 */
	public TimedSchedule getTimedSchedule() {
		return timedSchedule;
	}


	/**
	 * @param unScheduledOperations the unScheduledOperations to set
	 */
	public void setUnScheduledOperations(
			ArrayList<TimedOperation>[][] unScheduledOperations) {
		this.unScheduledOperations = unScheduledOperations;
	}

	/**
	 * @param timedSchedule the timedSchedule to set
	 */
	public void setTimedSchedule(TimedSchedule timedSchedule) {
		this.timedSchedule = timedSchedule;
	}

	/** Adds a timedOperation to the list of unscheduled operations
	 * @param jobNumber
	 * @param wksNumber
	 * @param mchNumber
	 */
	//protected void addOperationToList(int jobNumber, int wksNumber, int mchNumber){
		public void addOperationToList(int jobNumber, int wksNumber, int mchNumber){

		TimedOperation timedOperation = new TimedOperation(jobNumber, wksNumber, mchNumber);
		timedOperation.setOperationTimes(jobNumber, wksNumber, mchNumber, this.timedSchedule);

		unScheduledOperations[wksNumber][mchNumber].add(timedOperation);
	}

	/**
	 *  Removes the selected timed operation from the list of unscheduled operations. Also removes all operations of the same job from all other 
	 *  machines (in parallel) in the workstation
	 *  @param timedOperation
	 */
	protected void removeOperationsfromList(TimedOperation timedOperation){

		int jobNumber = timedOperation.getJobNumber(); // job number
		int wksNumber = timedOperation.getWorkcenterNumber();
		int mchNumber = timedOperation.getMachineNumber();

		int index = unScheduledOperations[wksNumber][mchNumber].indexOf(timedOperation);

		for (int l=0; l < wkcArray[wksNumber].getNumberOfMachines(); l++){

			unScheduledOperations[wksNumber][l].remove(index);

		}

		//unScheduledOperations[wksNumber][mchNumber].remove(timedOperation);
	}


	/** Updates the machine and job times of all unscheduled operations given that a job (jobNumber) is scheduled
	 * on machine "mchNumber" of workcenter "wksNumber"
	 * 
	 * @param jobNumber
	 * @param wksNumber
	 * @param mchNumber
	 */
	protected void updateUnscheduledTimes(int jobNumber, int wksNumber, int mchNumber){

		this.timedSchedule.getRemainingTimes()[jobNumber]=this.timedSchedule.getRemainingTimes()[jobNumber]-jobArray[jobNumber].getProcessTime(wksNumber);
		//updates machine times

		for (int k=0; k < unScheduledOperations[wksNumber][mchNumber].size(); k++){

			int jNumber = this.unScheduledOperations[wksNumber][mchNumber].get(k).getJobNumber();
			this.unScheduledOperations[wksNumber][mchNumber].get(k).
			setOperationTimes(jNumber, wksNumber, mchNumber, timedSchedule);

		}

		Comparator<TimedOperation> c = new Comparator<TimedOperation>() {
			public int compare(TimedOperation to1, TimedOperation to2) {
				return new Integer(to1.getJobNumber()).compareTo(to2.getJobNumber());
			}
		};

		//updates job times
		for (int i=0; i < wkcArray.length; i++){
			if (i != wksNumber){ // Searches for the job at all other workstations

				for (int l=0; l < wkcArray[i].getNumberOfMachines(); l++){

					//searches for the operation.  
					TimedOperation to = new TimedOperation(jobNumber, i,l);
					int index = Collections.binarySearch(unScheduledOperations[i][l], to , c);

					//System.out.println(index); 
					if (index >=0) {// the job is in the list of unscheduled operations

						unScheduledOperations[i][l].get(index).setOperationTimes(jobNumber, i, l, timedSchedule);
					}

				} // for l
			} // if 
			else { //removes the job operations from the same workcenter

			}
		} // for i

	}

	/** Active schedules
	 * @return The timed operation with the earliest finish time
	 */
	public TimedOperation getEarliestFinishOperation(){

		final int BIGNUMBER = 10000;
		int minEndTime = BIGNUMBER;
		int wksNumber=0, mchNumber=0, index=0;
		int indices[] = new int[3]; // indices i,l,k of the operation

		for (int i=0; i < wkcArray.length; i++){
			for (int l=0; l < wkcArray[i].getNumberOfMachines(); l++){

				for (int k=0; k < unScheduledOperations[i][l].size(); k++){

					int endTime = unScheduledOperations[i][l].get(k).getEndTime(); //possible end time of the operation
					if (endTime < minEndTime){

						minEndTime = endTime;
						wksNumber = i;
						mchNumber = l;
						index = k;
					}
				}
			}
		}


		return unScheduledOperations[wksNumber][mchNumber].get(index);
	}

	/** Active schedules
	 * @return The Index, WksNumber and mchNumber of the timed operation with the earliest finish time
	 */
	public int[] getEarliestFinishIndex(){

		final int BIGNUMBER = 10000;
		int minEndTime = BIGNUMBER;
		int wksNumber=0, mchNumber=0, index=0;
		int indices[] = new int[3]; // indices i,l,k of the operation

		for (int i=0; i < wkcArray.length; i++){
			for (int l=0; l < wkcArray[i].getNumberOfMachines(); l++){

				for (int k=0; k < unScheduledOperations[i][l].size(); k++){

					int endTime = unScheduledOperations[i][l].get(k).getEndTime(); //possible end time of the operation
					if (endTime < minEndTime){

						minEndTime = endTime;
						wksNumber = i;
						mchNumber = l;
						index = k;
					}
				}
			}
		}

		indices[0]=wksNumber;
		indices[1]=mchNumber;
		indices[2]=index;

		return indices;
	}
	
	/** Non-delay schedules
	 * @return the timed operation with the earliest start time
	 */
	public TimedOperation getEarliestStartOperation(){

		final int BIGNUMBER = Integer.MAX_VALUE;
		int minStartTime = BIGNUMBER;
		int wksNumber=0, mchNumber=0, index=0;
		int indices[] = new int[3]; // indices i,l,k of the operation

		for (int i=0; i < wkcArray.length; i++){
			for (int l=0; l < wkcArray[i].getNumberOfMachines(); l++){

				for (int k=0; k < unScheduledOperations[i][l].size(); k++){

					int startTime = unScheduledOperations[i][l].get(k).getStartTime(); //possible end time of the operation
					if (startTime < minStartTime){

						minStartTime = startTime;
						wksNumber = i;
						mchNumber = l;
						index = k;
					}
				}
			}
		}


		return unScheduledOperations[wksNumber][mchNumber].get(index);
	}

	/** Non-delay schedules
	 * @return The Index, WksNumber and mchNumber of the timed operation with the earliest start time
	 */
	public int[] getEarliestStartIndex(){

		final int BIGNUMBER = Integer.MAX_VALUE;
		int minStartTime = BIGNUMBER;
		int wksNumber=0, mchNumber=0, index=0;
		int indices[] = new int[3]; // indices i,l,k of the operation

		for (int i=0; i < wkcArray.length; i++){
			for (int l=0; l < wkcArray[i].getNumberOfMachines(); l++){

				for (int k=0; k < unScheduledOperations[i][l].size(); k++){

					int startTime = unScheduledOperations[i][l].get(k).getStartTime(); //possible end time of the operation
					if (startTime < minStartTime){

						minStartTime = startTime;
						wksNumber = i;
						mchNumber = l;
						index = k;
					}
				}
			}
		}

		indices[0]=wksNumber;
		indices[1]=mchNumber;
		indices[2]=index;
		return indices;
	}


	/**Adds the selected operation to the timed Schedule. Updates indicators
	 * Removes the index-th operation of the unscheduled operations list and puts it in the timedScheduled
	 * @param index
	 * @param jobNumber
	 * @param wksNumber
	 * @param mchNumber
	 */
	public void addToActiveSchedule(int index, int jobNumber, int wksNumber, int mchNumber){

		TimedOperation to = unScheduledOperations[wksNumber][mchNumber].get(index);

		this.timedSchedule.addOperation(to); // adds the operation and updates job and machine times
		//this.permutationList.add(to);
		for (int l=0; l < wkcArray[wksNumber].getNumberOfMachines(); l++){

			unScheduledOperations[wksNumber][l].remove(index);

		}
		
		//updates the times of all operations on the same machine and of the same job
		this.updateUnscheduledTimes(jobNumber, wksNumber, mchNumber); 

	}
	/**
	 * Returns a permutation of a given vector in which two random numbers were
	 * exchanged
	 * 
	 * @param totalJobs
	 *            Amount of jobs in the system
	 * @param totalMachines
	 *            Amount of machines in the system
	 * @param vector
	 *            Vector that needs to be permuted
	 * @return vector Permutation of the vector
	 */

	/**
	 * @param timedOperation
	 */
	public void addToActiveSchedule(TimedOperation timedOperation){
	
		int jobNumber = timedOperation.getJobNumber(); // job number
		int wksNumber = timedOperation.getWorkcenterNumber();
		int mchNumber = timedOperation.getMachineNumber();
	
		this.timedSchedule.addOperation(timedOperation); // adds the operation and updates job and machine times
		
		((OpenShopSchedule) this.timedSchedule).getRankMatrix()[jobNumber] [wksNumber] = Math.max(((OpenShopSchedule) this.timedSchedule).getMaxRow()[wksNumber], this.timedSchedule.getMaxCol()[jobNumber]) + 1;
		((OpenShopSchedule) this.timedSchedule).getMaxRow()[wksNumber]=((OpenShopSchedule) this.timedSchedule).getRankMatrix()[jobNumber][wksNumber];
		((OpenShopSchedule) this.timedSchedule).getMaxCol()[jobNumber]=this.timedSchedule.getRankMatrix()[jobNumber][wksNumber];
	
		this.removeOperationsfromList(timedOperation);
	
		//updates the times of all operations on the same machine and of the same job
		this.updateUnscheduledTimes(jobNumber, wksNumber, mchNumber); 
		
		
		
	}


	/**
	 * Returns a permutation of a given vector in which two numbers in the artificialRandom vector were
	 * exchanged
	 * 
	 * @param totalJobs
	 *            Amount of jobs in the system
	 * @param totalMachines
	 *            Amount of machines in the system
	 * @param vector
	 *            Vector that needs to be permuted
	 * @param artificialRandom
	 *            Vector that contains the artificial random numbers
	 * @return vector Permutation of the vector
	 */

//	public void addToActiveSchedule(TimedOperation timedOperation) {
//		
//	}	
	
}
