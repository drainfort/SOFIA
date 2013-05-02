/**
 * 
 */
package afs.schedule.oss;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;


import afs.model.Operation;
import afs.model.Job;
import afs.model.Workcenter;
import afs.schedule.TimedOperation;
import afs.schedule.TimedSchedule;

/**
 * This class creates the methods for Flexible Open Shop schedules
 * 
 * @author Gonzalo Mejía
 * @author Oriana Cendales
 */

public class OpenShopSchedule extends TimedSchedule{

	private LinkedList <Operation> schedulableOperations [] ;
	private Workcenter [] wkcArray;
	private Job [] jobArray;
	private Integer rankMatrix [][]; // the rank matrix
	private int maxRow []; // maximum value of each row of the rank matrix
	private int maxCol []; // maximum value of each column of the rank matrix
	private int remainingWorkTime []; // remainining work time of the job related to the operation

	public OpenShopSchedule(Workcenter[] wkcArray, Job[] jobArray) {

		super(wkcArray, jobArray);
		this.jobArray = jobArray;
		this.wkcArray = wkcArray;
		this.rankMatrix = new Integer [Job.getNumberOfJobs()][Workcenter.getNumberOfWorkcenters()];
		this.maxRow = new int [Workcenter.getNumberOfWorkcenters()];
		this.maxCol = new int[Job.getNumberOfJobs()];
		
		schedulableOperations = new LinkedList [Workcenter.getNumberOfWorkcenters()];

		for (int i =0; i<Workcenter.getNumberOfWorkcenters();i++){

			schedulableOperations[i]= new LinkedList();
		}
		
		for(int j=0; j<Job.getNumberOfJobs();j++){ 
				
				for (int k=0; k < jobArray[j].getRoute().length;k++){

					int wksNumber = jobArray[j].getRoute()[k].getWorkcenterNumber();
					schedulableOperations[wksNumber].add(jobArray[j].getRoute()[k]);//creates the operations
				}
			}

		//System.out.println();
	}
	


	/**
	 * @return the schedulableOperations
	 */
	public LinkedList<Operation>[] getSchedulableOperations() {
		return schedulableOperations;
	}


	/**
	 * @return the rankMatrix
	 */
	public Integer[][] getRankMatrix() {
		return rankMatrix;
	}


	/**
	 * @return the maxRow
	 */
	public int[] getMaxRow() {
		return maxRow;
	}


	/**
	 * @return the maxCol
	 */
	public int[] getMaxCol() {
		return maxCol;
	}

	/**
	 * @param schedulableOperations the schedulableOperations to set
	 */
	public void setSchedulableOperations(
			LinkedList<Operation>[] schedulableOperations) {
		this.schedulableOperations = schedulableOperations;
	}


	/**
	 * @param rankMatrix the rankMatrix to set
	 */
	public void setRankMatrix(Integer[][] rankMatrix) {
		this.rankMatrix = rankMatrix;
	}


	/**
	 * @param maxRow the maxRow to set
	 */
	public void setMaxRow(int[] maxRow) {
		this.maxRow = maxRow;
	}


	/**
	 * @param maxCol the maxCol to set
	 */
	public void setMaxCol(int[] maxCol) {
		this.maxCol = maxCol;
	}


	/** Adds a selected operation to a Timed Schedule and updates machine and job times
	 * @param jobNumber
	 * @param wksNumber
	 * @param mchNumber
	 */
	public void addToTimedSchedule(int jobNumber, int wksNumber, int mchNumber){

		try {
			// checks if the operation can be scheduled
			int index = isSchedulable(jobNumber, wksNumber);
			
			if (  index >= 0 ){ //the operation can be scheduled

				this.addOperation(jobNumber, wksNumber, mchNumber);
				schedulableOperations[wksNumber].remove(index);
				rankMatrix[jobNumber][wksNumber] = Math.max(maxRow[wksNumber], maxCol[jobNumber]) + 1;
				maxRow[wksNumber]++;
				maxCol[jobNumber]++;
				remainingWorkTime[jobNumber]++;
			}
			else {
				valid = false;
				throw new Exception();				
			}// else              

		} catch (Exception e) {
			System.out.println("Error adding to a Timed Schedule");
			System.exit(1);
		}		
	}
	
	/**
	 * Adds a selected operation to a Timed Schedule and updates machine and job times
	 * @param  a Timed Operation
	 */
	
	public void addToTimedSchedule(TimedOperation tOperation){
		
		try {
			// if the operation is schedulable
			int jobNumber = tOperation.getJobNumber();
			int wksNumber = tOperation.getWorkcenterNumber();
			int mchNumber = tOperation.getMachineNumber();
			int index = isSchedulable(jobNumber, wksNumber);
			
			if (  index >= 0 ){ //the operation can be scheduled

				this.timedGantt[wksNumber][mchNumber].add(tOperation);
				this.currentWkc[jobNumber].add(tOperation);
				schedulableOperations[wksNumber].remove(index);
				int time = tOperation.getEndTime();
				this.updateTimes(jobNumber, wksNumber, mchNumber, time);
				rankMatrix[jobNumber][wksNumber] = Math.max(maxRow[jobNumber], maxCol[wksNumber]) + 1;
				maxRow[jobNumber]=rankMatrix[jobNumber][wksNumber];
				maxCol[wksNumber]=rankMatrix[jobNumber][wksNumber];
			}
			else {
				valid = false;
				throw new Exception();				
			}// else              

		} catch (Exception e) {
			System.out.println("Error adding to a Timed Schedule");
			System.exit(1);
		}		
	}
		


	/** Identifies if an operation is schedulable and returns the operation index
	 * @param jobNumber
	 * @param wksNumber
	 * @param mchNumber
	 * @return index
	 */
	public int isSchedulable (int jobNumber, int wksNumber){

		Comparator<Operation> c = new Comparator<Operation>() {
			public int compare(Operation to1, Operation to2) {
				return new Integer(to1.getJobNumber()).compareTo(to2.getJobNumber());
			}
		};

		//searches for a candidate operation. the status and workcenter ID (null) are not specified in the constructor  
		
		Operation operation = new Operation(jobNumber, wksNumber, 0, null,'0');
		int index = Collections.binarySearch(schedulableOperations[wksNumber],operation , c);
   
		return index;

	}


}


