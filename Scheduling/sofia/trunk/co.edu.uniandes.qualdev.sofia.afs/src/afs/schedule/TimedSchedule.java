package afs.schedule;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import afs.model.Operation;
import afs.model.Job;
import afs.model.Workcenter;


/**
 * <p> Class Timed Schedule </p>
 * <p> The Schedule class provides the methods required to generate a valid schedule for Lekin <br>
 * A schedule is a multi-dimensional array which contains in its i,l,k position the job processed k-th on the l-th machine
 * of the i-th workstation</p>
 * In addition a TimedSchedule contains all Lekin performance indicators:
 * Makespan, max Tardiness, number of Tardy jobs, Total and Total Weighted Flow Time
 * and Total and Total Weighted Tardiness.</p>
 * @author Gonzalo Mejia
 * @version 3.0
 */


//public abstract class TimedSchedule {
	public class TimedSchedule {
	/**
	 * the array of jobs
	 */
	protected Job[] jobArray;


	/**
	 * the array of workcenters
	 */
	protected Workcenter[] wkcArray;

	/**
	 * jobTimes[j] contains the end time of the last scheduled operation of the j-th job
	 */
	protected int jobTimes[];

	/**
	 * machineTimes[i][l] contains the earliest time that some job can be scheduled on machine  l-th
	 * of workcenter i
	 * First index = workcenter. Second index = machine
	 */	
	protected int machineTimes[][];


	/**
	 * machineCurrentSetups[i][l] contains the last setup (status) of machine l of workstation i
	 * First index = workcenter. Second index = machine
	 */	
	protected char machineCurrentSetups[][];



	/**
	 * This is an array of W workstations, Mi machines per workstation.
	 * Stores the operation scheduled on the i,j position
	 */	
	protected ArrayList<TimedOperation> timedGantt[][];
	
	protected int[] remainingTimes;
	
	protected ArrayList<TimedOperation> currentWkc[];

	// stores Cj-rj for each job
	protected int[] flowTimes;
	
	//stores the tardiness of each job max(Cj-dj,0)
	protected int[] jobTardiness;

	//	stores the 1 if the job is tardy; 0 otherwise
	protected int[] tardyJobs;

	protected int makespan=0; //max Cj

	protected int maxTardiness=0; //max Tj; 

	protected int totalFlowTime=0; // sum Cj

	protected int totalTardiness=0; // sum Tj

	protected int totalWeightedFlowTime=0; // sum Cj

	protected int totalWeightedTardiness=0; // sum Tj

	protected int numberOfTardyJobs=0;

	protected boolean valid=true;

	/**
	 * Contructs an empty timed schedule
	 * @param wkcArray
	 * @param jobArray
	 */
	public TimedSchedule(Workcenter[] wkcArray, Job[] jobArray, ActiveSchedule untimedSchedule) {

		this.jobArray = jobArray;
		this.wkcArray = wkcArray;
		jobTimes = new int[Job.getNumberOfJobs()];
		remainingTimes=new int[Job.getNumberOfJobs()];
		jobTardiness = new int[Job.getNumberOfJobs()];
		tardyJobs = new int[Job.getNumberOfJobs()];
		flowTimes = new int[Job.getNumberOfJobs()];
		machineCurrentSetups = new char[Workcenter.getNumberOfWorkcenters()][];
		machineTimes = new int[Workcenter.getNumberOfWorkcenters()][];
		timedGantt = new ArrayList[Workcenter.getNumberOfWorkcenters()][];
		currentWkc=new ArrayList[Job.getNumberOfJobs()];

		for (int j =0; j < Job.getNumberOfJobs(); j++){
				jobTimes[j] = jobArray[j].getRelease();
				flowTimes[j] = jobTimes[j];
				remainingTimes[j]=jobArray[j].getTotalWorkTime();	
				currentWkc[j]=new ArrayList<TimedOperation>();
			}
		
		
		for (int i = 0; i < machineTimes.length; i++) {

			machineTimes[i] = new int[wkcArray[i].getNumberOfMachines()];
			machineCurrentSetups[i]= new char [wkcArray[i].getNumberOfMachines()];

			for (int l=0 ; l < wkcArray[i].getNumberOfMachines(); l++){
				machineTimes[i][l] = wkcArray[i].getMachine(l).getAvailableDate();
				machineCurrentSetups[i][l] = wkcArray[i].getMachine(l).getStatus();
			}             

			timedGantt[i] = new ArrayList[wkcArray[i].getNumberOfMachines()];
			for (int j=0; j < timedGantt[i].length;j++){

				timedGantt[i][j] = new ArrayList<TimedOperation>();

			}// for

		} //for

	} // constructor
	/**
	 * Contructs an empty timed schedule
	 * @param wkcArray
	 * @param jobArray
	 */
	public TimedSchedule(Workcenter[] wkcArray, Job[] jobArray) {

		this.jobArray = jobArray;
		this.wkcArray = wkcArray;
		jobTimes = new int[Job.getNumberOfJobs()];
		remainingTimes=new int[Job.getNumberOfJobs()];
		jobTardiness = new int[Job.getNumberOfJobs()];
		tardyJobs = new int[Job.getNumberOfJobs()];
		flowTimes = new int[Job.getNumberOfJobs()];
		machineCurrentSetups = new char[Workcenter.getNumberOfWorkcenters()][];
		machineTimes = new int[Workcenter.getNumberOfWorkcenters()][];
		timedGantt = new ArrayList[Workcenter.getNumberOfWorkcenters()][];
		currentWkc=new ArrayList[Job.getNumberOfJobs()];
		
		for (int j =0; j < Job.getNumberOfJobs(); j++){
				jobTimes[j] = jobArray[j].getRelease();
				flowTimes[j] = jobTimes[j];
				remainingTimes[j]=jobArray[j].getTotalWorkTime();	
				currentWkc[j]=new ArrayList<TimedOperation>();
			}
		
		
		for (int i = 0; i < machineTimes.length; i++) {

			machineTimes[i] = new int[wkcArray[i].getNumberOfMachines()];
			machineCurrentSetups[i]= new char [wkcArray[i].getNumberOfMachines()];

			for (int l=0 ; l < wkcArray[i].getNumberOfMachines(); l++){
				machineTimes[i][l] = wkcArray[i].getMachine(l).getAvailableDate();
				machineCurrentSetups[i][l] = wkcArray[i].getMachine(l).getStatus();
			}             

			timedGantt[i] = new ArrayList[wkcArray[i].getNumberOfMachines()];
			for (int j=0; j < timedGantt[i].length;j++){

				timedGantt[i][j] = new ArrayList<TimedOperation>();

			}// for

		} //for

	} // constructor
	/**
	 * @return the machineCurrentSetups
	 */
	public char[][] getMachineCurrentSetups() {
		return machineCurrentSetups;
	}


	/**
	 * @param machineCurrentSetups the machineCurrentSetups to set
	 */
	public void setMachineCurrentSetups(char[][] machineCurrentSetups) {
		this.machineCurrentSetups = machineCurrentSetups;
	}


	/**
	 * @param the remaining times
	 */
	public int[] getRemainingTimes() {
		return remainingTimes;
	}

	/**
	 * @param the remaining times to set
	 */
	public void setRemainingTimes(int[] remainingTimes) {
		this.remainingTimes = remainingTimes;
	}


	/**
	 * @return
	 */
	public int[] getFlowTimes() {
		return flowTimes;
	}
	
	/**
	 * @return
	 */
	public int[] getJobTardiness() {
		return jobTardiness;
	}


	/**
	 * @return
	 */
	public int[] getJobTimes() {
		return jobTimes;
	}


	/**
	 * @return
	 */
	public int[][] getMachineTimes() {
		return machineTimes;
	}


	/**
	 * @return
	 */
	public int getMakespan() {
		return makespan;
	}


	/**
	 * @return
	 */
	public int getMaxTardiness() {
		return maxTardiness;
	}


	/**
	 * @return
	 */
	public int getNumberOfTardyJobs() {
		return numberOfTardyJobs;
	}


	/**
	 * @return
	 */
	public int[] getTardyJobs() {
		return tardyJobs;
	}

//ORIANA
	/**
	 * @return
	 */
	public ArrayList<TimedOperation>[] getCurrentWkc() {
		return currentWkc;
	}
//ORIANA

	/**
	 * @return
	 */
	public ArrayList<TimedOperation>[][] getTimedGantt() {
		return timedGantt;
	}

	/**
	 * @return
	 */
	public int getTotalFlowTime() {
		return totalFlowTime;
	}


	/**
	 * @return
	 */
	public int getTotalTardiness() {
		return totalTardiness;
	}


	/**
	 * @return
	 */
	public int getTotalWeightedFlowTime() {
		return totalWeightedFlowTime;
	}


	/**
	 * @return totalWeightedTardiness
	 */
	public int getTotalWeightedTardiness() {
		return totalWeightedTardiness;
	}


	/** returns the boolean value of "valid"
	 * @return valid
	 */
	public boolean isValid() {
		return valid;
	}

	/**
	 * @param currentOperationOnMachine
	 */
	/*public void setCurrentOperationOnMachine(int[][] currentOperationOnMachine) {
		this.currentOperationOnMachine = currentOperationOnMachine;
	}*/

	/**
	 * @param flowTimes
	 */
	public void setFlowTimes(int[] flowTimes) {
		this.flowTimes = flowTimes;
	}

	
	/**
	 * @param jobTardiness
	 */
	public void setJobTardiness(int[] jobTardiness) {
		this.jobTardiness = jobTardiness;
	}

	/**
	 * @param jobTimes
	 */
	public void setJobTimes(int[] jobTimes) {
		this.jobTimes = jobTimes;
	}

	/**
	 * @param machineTimes
	 */
	public void setMachineTimes(int[][] machineTimes) {
		this.machineTimes = machineTimes;
	}
	
	/**
	 * @param i
	 * @param l
	 * @param time
	 */
	public void setMachineTimes(int i, int l, int time){
		this.machineTimes[i][l] = time;
		
	}


	/**
	 * @param makespan
	 */
	public void setMakespan(int makespan) {
		this.makespan = makespan;
	}


	/**
	 * @param maxTardiness
	 */
	public void setMaxTardiness(int maxTardiness) {
		this.maxTardiness = maxTardiness;
	}


	/**
	 * @param numberOfTardyJobs
	 */
	public void setNumberOfTardyJobs(int numberOfTardyJobs) {
		this.numberOfTardyJobs = numberOfTardyJobs;
	}


	/**
	 * @param tardyJobs
	 */
	public void setTardyJobs(int[] tardyJobs) {
		this.tardyJobs = tardyJobs;
	}
//ORIANA
	/**
	 * @param timedGantt
	 */
	public void setCurrentWkc(ArrayList<TimedOperation>[] currentWkc) {
		this.currentWkc = currentWkc;
	}	
//ORIANA
	
	/**
	 * @param timedGantt
	 */
	public void setTimedGantt(ArrayList<TimedOperation>[][] timedGantt) {
		this.timedGantt = timedGantt;
	}

	/**
	 * @param totalFlowTime
	 */
	public void setTotalFlowTime(int totalFlowTime) {
		this.totalFlowTime = totalFlowTime;
	}

	/**
	 * @param totalTardiness
	 */
	public void setTotalTardiness(int totalTardiness) {
		this.totalTardiness = totalTardiness;
	}

	/**
	 * @param totalWeightedFlowTime
	 */
	public void setTotalWeightedFlowTime(int totalWeightedFlowTime) {
		this.totalWeightedFlowTime = totalWeightedFlowTime;
	}

	/**
	 * @param totalWeightedTardiness
	 */
	public void setTotalWeightedTardiness(int totalWeightedTardiness) {
		this.totalWeightedTardiness = totalWeightedTardiness;
	}

	/**
	 * @param valid
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	/**
	 * @param jobNumber
	 * @param wksNumber
	 * @param mchNumber
	 */
	public void addOperation(int jobNumber, int wksNumber, int mchNumber){

		TimedOperation timedOperation = new TimedOperation(jobNumber, wksNumber, mchNumber);

		
		int time = timedOperation.getEndTime();
		//update job and machine times
		this.updateTimes(jobNumber, wksNumber, mchNumber, time);

		//adds the operation
		timedGantt[wksNumber][mchNumber].add(timedOperation);
//ORIANA		
		currentWkc[jobNumber].add(timedOperation);
//ORIANA
		
		//System.out.println("Job " + jobNumber + " Workcenter " + wksNumber + " Machine " + mchNumber +  " Start time " + 
			//	timedOperation.getStartTime() + " End time " + timedOperation.getEndTime());


	}


	/**
	 * Adds a timed Operation to the timedGantt. Puts the operation at the end of the list of the wksNumber-th workstation 
	 * of the mchNumber-th machine
	 * @param timedOperation
	 * @param wksNumber
	 * @param mchNumber
	 */
	public void addOperation(TimedOperation timedOperation){

		int wksNumber = timedOperation.getWorkcenterNumber();
		int mchNumber = timedOperation.getMachineNumber();
		int time = timedOperation.getEndTime(); // end time of the operation
		int jobNumber = timedOperation.getJobNumber();
		
		timedOperation.setOperationTimes(jobNumber, wksNumber, mchNumber, this);
		
		//adds operation to timedGantt and currentWkc
		timedGantt[wksNumber][mchNumber].add(timedOperation);

		currentWkc[jobNumber].add(timedOperation);

		//update job and machine times
		this.updateTimes(jobNumber, wksNumber, mchNumber, time);
		
//
		//System.out.println("Job " + jobNumber + " Workcenter " + wksNumber + " Machine " + mchNumber +  " Start time " + 
			//	timedOperation.getStartTime() + " End time " + timedOperation.getEndTime());
		
	}

	/**
	 * @param jobNumber
	 * @param wksNumber
	 * @param mchNumber
	 * @param time
	 */
	public void updateTimes(int jobNumber, int wksNumber, int mchNumber, int time){
		
		//Subtracts these values before the update of the indicators
		totalFlowTime = totalFlowTime -jobTimes[jobNumber];
		totalWeightedFlowTime = totalWeightedFlowTime -jobArray[jobNumber].getWeight()*jobTimes[jobNumber];
		totalTardiness = totalTardiness -jobTardiness[jobNumber];
		totalWeightedTardiness = totalWeightedTardiness -jobArray[jobNumber].getWeight()*jobTardiness[jobNumber];
		numberOfTardyJobs = numberOfTardyJobs - tardyJobs[jobNumber];
		
		//updates job and machine clock times
		machineTimes[wksNumber][mchNumber] = time;
		jobTimes[jobNumber] = time;
		
		//adds travel time to the depot
		if (currentWkc[jobNumber].size()==jobArray[jobNumber].getNumberOfOperations()){
			
			int depotTravel=0;
			int lastPos=this.currentWkc[jobNumber].size()-1;
			int lastWkc=this.currentWkc[jobNumber].get(lastPos).getWorkcenterNumber();
			depotTravel=this.wkcArray[lastWkc].getDepotTravelTime();
			jobTimes[jobNumber]+= depotTravel;

		}

		//updates the current operation indices
		this.calcPerformance(jobNumber);
	}

	/** Calculates the performance indicators of a schedule
	 * Makespan, max Tardiness, number of Tardy jobs, Total and Total Weighted Flow Time
	 * and Total and Total Weighted Tardiness.
	 * @param jobArray
	 * @throws Exception
	 */
	public void calcPerformance(){

		//finds the performance indicators
		makespan=0;
		maxTardiness=0;
		numberOfTardyJobs=0;
		totalFlowTime=0;
		totalWeightedFlowTime=0;
		totalTardiness=0;
		totalWeightedTardiness=0;

		try {
			for (int j=0; j < Job.getNumberOfJobs();j++ ){

				int weight = jobArray[j].getWeight();
				int release = jobArray[j].getRelease();
				flowTimes[j] = jobTimes[j];//-release;
				if (jobTimes[j] > makespan){
					makespan = jobTimes[j];
					
				}

				totalFlowTime = totalFlowTime + flowTimes[j];
				totalWeightedFlowTime = totalWeightedFlowTime + weight*flowTimes[j];
				
				int dueDate = jobArray[j].getDue();
				jobTardiness[j]= Math.max(jobTimes[j]-dueDate, 0);

				if (jobTardiness[j] > maxTardiness){
					maxTardiness = jobTardiness[j];
				}

				totalTardiness = totalTardiness + jobTardiness[j];
				totalWeightedTardiness = totalWeightedTardiness + weight*jobTardiness[j];

				if (jobTardiness[j] > 0){			
					tardyJobs[j]=1;
					numberOfTardyJobs++;
				}// if

			}// for
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error calculating performance");
			System.exit(1);
		}

	}

	/** Calculates the performance indicators of a schedule when a new job is scheduled.
	 * Makespan, max Tardiness, number of Tardy jobs, Total and Total Weighted Flow Time
	 * and Total and Total Weighted Tardiness.
	 * @param jobArray
	 * @throws Exception
	 */
	public void calcPerformance(int jobNumber){

		//finds the performance indicators

		int weight = jobArray[jobNumber].getWeight();
		int release = jobArray[jobNumber].getRelease();
		int wksNumber=jobArray[jobNumber].getWorkcenterNumber(jobNumber);
			
		try {

			flowTimes[jobNumber] = jobTimes[jobNumber];//-release;
			if (jobTimes[jobNumber] > makespan){
				makespan = jobTimes[jobNumber];
			}
			
			totalFlowTime = totalFlowTime + flowTimes[jobNumber];
			totalWeightedFlowTime = totalWeightedFlowTime + weight*flowTimes[jobNumber];
			
			int dueDate = jobArray[jobNumber].getDue();
			jobTardiness[jobNumber]= Math.max(jobTimes[jobNumber]-dueDate, 0);

			if (jobTardiness[jobNumber] > maxTardiness){
				maxTardiness = jobTardiness[jobNumber];
			}

			totalTardiness = totalTardiness + jobTardiness[jobNumber];
			totalWeightedTardiness = totalWeightedTardiness + weight*jobTardiness[jobNumber];

			if (jobTardiness[jobNumber] > 0){			
				tardyJobs[jobNumber]=1;
				numberOfTardyJobs++;//=numberOfTardyJobs + tardyJobs[jobNumber];
			}// if
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error calculating performance");
			System.exit(1);
		}

	}

	/**
	 * Prints the performance indicators to the console
	 */
	public void printPerformance(){

		if (valid == true) {
			System.out.println();
			
			for (int j=0; j < Job.getNumberOfJobs(); j++){
				
				System.out.println("Job " + j + ": Completion time " + this.jobTimes[j]);
			}
			
			System.out.println();
			System.out.println("Makespan " + makespan);
			System.out.println("Max Tardiness " + maxTardiness);
			System.out.println("Tardy Jobs " + numberOfTardyJobs); 
			System.out.println("Total Flow Time " + totalFlowTime); 
			System.out.println("Total Tardiness " + totalTardiness); 
			System.out.println("Total Weighted Flow Time " + totalWeightedFlowTime); 
			System.out.println("Total Weighted Tardiness " + totalWeightedTardiness);
			System.out.println();
		}
		else {
			System.out.println("Error printing performance");
		}
	}

	/**
	 * The current timed scheduled is written to the _user.seq file in Lekin.<br>
	 *
	 */
	public void printToLekin(){

		File outFile = new File("_user.seq");

		try {

			BufferedWriter out = new BufferedWriter(new FileWriter(outFile));
			out.write("Schedule \t \t AFS Library");
			out.newLine();

			for (int i = 0; i < timedGantt.length; i++) {

				for (int j = 0; j < timedGantt[i].length; j++) {

					out.write("Machine \t" + i + "." +j);
					out.newLine();

					for (int k = 0; k < timedGantt[i][j].size(); k++) {

						Integer jobNumber = (Integer)timedGantt[i][j].get(k).getJobNumber();
						out.write("Oper: \t" + jobNumber.intValue());
						out.newLine();
					}
				}
			}
			out.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}


	public Integer[][]getRankMatrix() {
		return null;
	}


	public int[] getMaxCol() {
		return null;
	}
	
	

}// class
