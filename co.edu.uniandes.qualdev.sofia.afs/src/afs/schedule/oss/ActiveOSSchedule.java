package afs.schedule.oss;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import afs.model.Operation;
import afs.model.Job;
import afs.model.Workcenter;
import afs.schedule.ActiveSchedule;
import afs.schedule.TimedSchedule;


/**
 * @author gmejia
 * This class creates the methods for active schedules for Flexible Open Shops
 */
public class ActiveOSSchedule extends ActiveSchedule{


	/**
	 * @param wkcArray
	 * @param jobArray
	 */
	public ActiveOSSchedule(Workcenter[] wkcArray, Job[] jobArray) {

		super(wkcArray, jobArray);
		timedSchedule = new OpenShopSchedule(wkcArray, jobArray);
		for (int i=0; i < unScheduledOperations.length; i++){
			unScheduledOperations[i]= new ArrayList[wkcArray[i].getNumberOfMachines()];

			for (int l=0; l < wkcArray[i].getNumberOfMachines(); l++){

				unScheduledOperations[i][l] = new ArrayList();

				for (int j=0; j < Job.getNumberOfJobs(); j++){

					this.addOperationToList(j, i, l);

				}		
			}	
		}
	}





	/**Adds the selected operation to the timed Schedule. Updates indicators
	 * Removes the index-th operation of the unscheduled operations list and puts it in the timedScheduled
	 * @param index
	 * @param wksNumber
	 * @param mchNumber
	 */
	public void addToActiveOSSchedule(int index, int wksNumber, int mchNumber){

		int jobNumber = this.unScheduledOperations[wksNumber][mchNumber].get(index).getJobNumber();
		this.addToActiveSchedule(index, jobNumber, wksNumber, mchNumber);
		
		int maxRow = ((OpenShopSchedule) this.timedSchedule).getMaxRow()[wksNumber];
		int maxCol = ((OpenShopSchedule) this.timedSchedule).getMaxCol()[jobNumber];
		
		((OpenShopSchedule) this.timedSchedule).getRankMatrix()[jobNumber][wksNumber]= Math.max(maxRow, maxCol) + 1;
		
		((OpenShopSchedule) this.timedSchedule).getMaxRow()[wksNumber]=((OpenShopSchedule) this.timedSchedule).getRankMatrix()[jobNumber][wksNumber];
		((OpenShopSchedule) this.timedSchedule).getMaxCol()[jobNumber]=((OpenShopSchedule) this.timedSchedule).getRankMatrix()[jobNumber][wksNumber];

}
	
	public void dispatchRuleScheduling(String rule){

		int operationIndices [] = new int [3];
		//int[] totalRemainingWork = this.getTimedSchedule().getRemainingTimes();

		int i=0;
		while(i<Job.getTotalNumberOfOperations()){

			operationIndices = this.getEarliestFinishIndex(); //index, wkcNumber and mchNumber
			int wkcNumber = operationIndices[0];
			int machineNumber = operationIndices[1];
			int pos = operationIndices[2]; // position of the selected operation

			//int minStartTime = this.getUnScheduledOperations()[wkcNumber][machineNumber].get(pos).getStartTime();
			int minEndTime = this.getUnScheduledOperations()[wkcNumber][ machineNumber].get(pos).getEndTime();

			int index = getOperationIndex (wkcNumber,  machineNumber, minEndTime, rule);
			this.addToActiveSchedule(this.getUnScheduledOperations()[wkcNumber][machineNumber].get(index));
			i++;
		}//end-while

		//Printing results: size of the instance and Rank matrix
		System.out.println();
		System.out.println(Job.getNumberOfJobs());
		System.out.println(Workcenter.getNumberOfWorkcenters());

		for (int j = 0; j < Job.getNumberOfJobs(); j++){
			for (i=0; i< Workcenter.getNumberOfWorkcenters();i++){
				System.out.print("|"+this.getTimedSchedule().getRankMatrix()[j][i]);
			}//end-for
			System.out.println("|");
		}//end-for
		this.getTimedSchedule().printPerformance();
	}


	/** Returns the index of the selected operation on the list of schedulable operations at the machine of the workcenter
	 * @param workcenterNumber
	 * @param machineNumber
	 * @param rule
	 * @return
	 */
	private int getOperationIndex (int wkcNumber, int machineNumber, int minEndTime, String rule){

		int jobNumber;
		int index=0;
		int maxRPT=0;
		int maxPT=0;
		int minRPT=Integer.MAX_VALUE;
		int minPT=Integer.MAX_VALUE;
		int rule2 = 0;
		if(rule.equals("LRPT")){
			rule2=1;
		}else if(rule.equals("LPT")){
			rule2=2;
		}else if(rule.equals("SRPT")){
			rule2=3;
		}else if(rule.equals("SPT")){
			rule2=4;
		}
		

		for (int k=0; k < this.getUnScheduledOperations()[wkcNumber][machineNumber].size(); k++){	

			//calculates the start time of all schedulable operations at the selected machine of the selected workcenter 
			int startTime=this.getUnScheduledOperations()[wkcNumber][machineNumber].get(k).getSetupStartTime();

			jobNumber= this.getUnScheduledOperations()[wkcNumber][machineNumber].get(k).getJobNumber();
			this.getTimedSchedule().getRemainingTimes()[jobNumber]=this.getTimedSchedule().getRemainingTimes()[jobNumber];
			Workcenter wkcenter = wkcArray[wkcNumber];
			int processTime=jobArray[jobNumber].getProcessTime(wkcenter);

			switch (rule2){
			case 1:
				if (maxRPT<this.getTimedSchedule().getRemainingTimes()[jobNumber] && startTime < minEndTime){

					maxRPT=	this.getTimedSchedule().getRemainingTimes()[jobNumber];
					index=k;
				}//end-if 
				break;
			case 2:

				if (maxPT<processTime && startTime <= minEndTime){				

					maxPT=	this.getTimedSchedule().getRemainingTimes()[jobNumber];
					index=k;
				}//end-if				
				break;
			case 3:

				if (minRPT>this.getTimedSchedule().getRemainingTimes()[jobNumber] && startTime <= minEndTime){

					minRPT=	this.getTimedSchedule().getRemainingTimes()[jobNumber];
					minRPT=k;
				}//end-if
				break;
			case 4:

				//Workcenter wkcenter = wkcArray[wkcNumber];
				//int processTime=jobArray[jobNumber].getProcessTime(wkcenter);
				if (minPT>processTime && startTime <= minEndTime){				

					minPT=	this.getTimedSchedule().getRemainingTimes()[jobNumber];
					index=k;
				}//end-if				
				break;

			default:
				System.out.println("Invalid rule");
			} //switch
		}//end-for
		return index;
	}

	
	



}



