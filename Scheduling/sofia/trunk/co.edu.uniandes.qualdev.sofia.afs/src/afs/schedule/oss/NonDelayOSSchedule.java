package afs.schedule.oss;

import java.util.ArrayList;
import afs.model.Job;
import afs.model.Workcenter;
import afs.schedule.ActiveSchedule;
import afs.schedule.TimedOperation;

/**
 * This class creates the methods for Non-delay schedules for Flexible Open
 * Shops
 * 
 * @author Oriana Cendales
 */
public class NonDelayOSSchedule extends ActiveSchedule {

	@SuppressWarnings("unchecked")
	public NonDelayOSSchedule(Workcenter[] wkcArray, Job[] jobArray) {
		super(wkcArray, jobArray);

		timedSchedule = new OpenShopSchedule(wkcArray, jobArray);
		for (int i = 0; i < unScheduledOperations.length; i++) {
			unScheduledOperations[i] = new ArrayList[wkcArray[i]
					.getNumberOfMachines()];

			for (int l = 0; l < wkcArray[i].getNumberOfMachines(); l++) {
				unScheduledOperations[i][l] = new ArrayList();
				for (int j = 0; j < Job.getNumberOfJobs(); j++) {
					this.addOperationToList(j, i, l);
				}
			}
		}
	}

	/**
	 * 
	 * @param rule
	 */
	public void dispatchRuleScheduling(String rule) {
		int operationIndices[] = new int[3];

		int i = 0;
		while (i < Job.getTotalNumberOfOperations()) {
			operationIndices = this.getEarliestStartIndex(); 
															
			int wkcNumber = operationIndices[0];
			int machineNumber = operationIndices[1];
			int pos = operationIndices[2]; 

			int minStartTime = this.getUnScheduledOperations()[wkcNumber][machineNumber]
					.get(pos).getStartTime();

			int index = getOperationIndex(wkcNumber, machineNumber,
					minStartTime, rule);
			this.addToActiveSchedule(this.getUnScheduledOperations()[wkcNumber][machineNumber]
					.get(index));

			i++;
		}
	}

	/**
	 * Returns the index of the selected operation on the list of schedulable
	 * operations at the machine of the workcenter
	 * 
	 * @param workcenterNumber
	 * @param machineNumber
	 * @param rule
	 * @return
	 */
	private int getOperationIndex(int wkcNumber, int machineNumber,
			int minStartTime, String rule) {

		int jobNumber;
		int index = 0;
		int maxRPT = 0;
		int maxPT = 0;
		int minRPT = Integer.MAX_VALUE;
		int minPT = Integer.MAX_VALUE;
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

		for (int k = 0; k < this.getUnScheduledOperations()[wkcNumber][machineNumber]
				.size(); k++) {

			// calculates the start time of all schedulable operations at the
			// selected machine of the selected workcenter
			int startTime = this.getUnScheduledOperations()[wkcNumber][machineNumber]
					.get(k).getSetupStartTime();

			jobNumber = this.getUnScheduledOperations()[wkcNumber][machineNumber]
					.get(k).getJobNumber();
			this.getTimedSchedule().getRemainingTimes()[jobNumber] = this
					.getTimedSchedule().getRemainingTimes()[jobNumber];
			Workcenter wkcenter = wkcArray[wkcNumber];
			int processTime = jobArray[jobNumber].getProcessTime(wkcenter);

			
			switch (rule2) {
			case 1:
				if (maxRPT < this.getTimedSchedule().getRemainingTimes()[jobNumber]
						&& startTime <= minStartTime) {

					maxRPT = this.getTimedSchedule().getRemainingTimes()[jobNumber];
					index = k;
				}// end-if
				break;
			case 2:

				if (maxPT < processTime && startTime <= minStartTime) {

					maxPT = this.getTimedSchedule().getRemainingTimes()[jobNumber];
					index = k;
				}// end-if
				break;
			case 3:

				if (minRPT > this.getTimedSchedule().getRemainingTimes()[jobNumber]
						&& startTime <= minStartTime) {

					minRPT = this.getTimedSchedule().getRemainingTimes()[jobNumber];
					index = k;
				}// end-if
				break;
			case 4:

				// Workcenter wkcenter = wkcArray[wkcNumber];
				// int processTime=jobArray[jobNumber].getProcessTime(wkcenter);
				if (minPT > processTime && startTime <= minStartTime) {

					minPT = this.getTimedSchedule().getRemainingTimes()[jobNumber];
					index = k;
				}// end-if
				break;

			default:
				System.out.println("Invalid rule");
			} // switch
		}// end-for
		return index;
	}

	/**
	 * Non-delay Schedule for Flexible Open Shops with Longest Remaining Process
	 * Time (LRPT) rule
	 */
	public void longestRemainingProcessRule() {
		int operationIndices[] = new int[3];
		int[] totalRemainingWork = this.getTimedSchedule().getRemainingTimes();

		int i = 0;
		while (i < Job.getTotalNumberOfOperations()) {

			operationIndices = this.getEarliestStartIndex(); // index, wkcNumber
																// and mchNumber
			int wkcNumber = operationIndices[0];
			int machineNumber = operationIndices[1];
			int pos = operationIndices[2]; // position of the selected operation

			int minStartTime = this.getUnScheduledOperations()[wkcNumber][machineNumber]
					.get(pos).getStartTime();
			int rptIndex = 0;
			int jobNumber;
			int maxRPT = 0;

			for (int k = 0; k < this.getUnScheduledOperations()[wkcNumber][machineNumber]
					.size(); k++) {

				int startTime = this.getUnScheduledOperations()[wkcNumber][machineNumber]
						.get(k).getSetupStartTime();

				jobNumber = this.getUnScheduledOperations()[wkcNumber][machineNumber]
						.get(k).getJobNumber();
				totalRemainingWork[jobNumber] = this.getTimedSchedule()
						.getRemainingTimes()[jobNumber];

				if (maxRPT < totalRemainingWork[jobNumber]
						&& startTime <= minStartTime) {

					maxRPT = totalRemainingWork[jobNumber];
					rptIndex = k;
				}// end-if
			}// end-for

			// jobNumber=
			// this.getUnScheduledOperations()[workcenterNumber][machineNumber].get(rptIndex).getJobNumber();
			System.out.println("Wkc "
					+ wkcNumber
					+ " job "
					+ this.getUnScheduledOperations()[wkcNumber][machineNumber]
							.get(rptIndex).getJobNumber());
			this.addToActiveSchedule(this.getUnScheduledOperations()[wkcNumber][machineNumber]
					.get(rptIndex));
			i++;
		}// end-while

		System.out.println();
		System.out.println(Job.getNumberOfJobs());
		System.out.println(Workcenter.getNumberOfWorkcenters());

		for (int j = 0; j < Job.getNumberOfJobs(); j++) {
			for (i = 0; i < Workcenter.getNumberOfWorkcenters(); i++) {
				System.out.print("|"
						+ this.getTimedSchedule().getRankMatrix()[j][i]);
			}// end-for
			System.out.println("|");
		}// end-for
	}// end-mostWorkRemainingRule

	/**
	 * Non-delay Schedule for Flexible Open Shops with Longest Remaining Process
	 * Time (LRPT). It has the permutationList
	 */
	public void mostWorkRemainingRule(ArrayList<TimedOperation> permutationList) {

		int operationIndices[] = new int[3];
		int[] totalRemainingWork = this.getTimedSchedule().getRemainingTimes();

		int i = 0;
		while (i < Job.getTotalNumberOfOperations()) {

			operationIndices = this.getEarliestStartIndex(); // index, wkcNumber
																// and mchNumber
			int workcenterNumber = operationIndices[0];
			int machineNumber = operationIndices[1];
			int pos = operationIndices[2]; // position of the selected operation
			int minStartTime = this.getUnScheduledOperations()[workcenterNumber][machineNumber]
					.get(pos).getStartTime();
			int rptIndex = 0;
			int jobNumber;
			int maxRPT = 0;

			for (int k = 0; k < this.getUnScheduledOperations()[workcenterNumber][machineNumber]
					.size(); k++) {

				// calculates the start time of all schedulable operations at
				// the selected machine of the selected workcenter
				int startTime = this.getUnScheduledOperations()[workcenterNumber][machineNumber]
						.get(k).getSetupStartTime();

				jobNumber = this.getUnScheduledOperations()[workcenterNumber][machineNumber]
						.get(k).getJobNumber();
				totalRemainingWork[jobNumber] = this.getTimedSchedule()
						.getRemainingTimes()[jobNumber];

				if (maxRPT < totalRemainingWork[jobNumber]
						&& startTime <= minStartTime) {

					maxRPT = totalRemainingWork[jobNumber];
					rptIndex = k;
				}// end-if
			}// end-for
			permutationList
					.add(i,
							this.getUnScheduledOperations()[workcenterNumber][machineNumber]
									.get(rptIndex));

			this.addToActiveSchedule(this.getUnScheduledOperations()[workcenterNumber][machineNumber]
					.get(rptIndex));

			// System.out.println(totalRemainingWork[0]+ " "
			// +totalRemainingWork[1]+ " " + totalRemainingWork[2]+ " " +
			// totalRemainingWork[3]);
			i++;
		}// end-while

		this.getTimedSchedule().printToLekin();
	}// end-mostWorkRemainingRule

	/**
	 * Non-delay Schedule for Flexible Open Shops with LPT rule
	 */
	public void longestProcessTimeRule() {
		int operationIndices[] = new int[3];
		int processTime;
		int jobNumber;
		int wkcNumber;

		int i = 0;
		while (i < Job.getTotalNumberOfOperations()) {
			operationIndices = this.getEarliestStartIndex(); // index, wkcNumber
																// and mchNumber
			int workcenterNumber = operationIndices[0];
			int machineNumber = operationIndices[1];
			int pos = operationIndices[2]; // position of the selected operation

			int minStartTime = this.getUnScheduledOperations()[workcenterNumber][machineNumber]
					.get(pos).getStartTime();
			int lptIndex = 0;
			int maxPT = 0;

			for (int k = 0; k < this.getUnScheduledOperations()[workcenterNumber][machineNumber]
					.size(); k++) {

				// calculates the start time of all schedulable operations at
				// the selected machine of the selected workcenter
				int startTime = this.getUnScheduledOperations()[workcenterNumber][machineNumber]
						.get(k).getSetupStartTime();

				jobNumber = this.getUnScheduledOperations()[workcenterNumber][machineNumber]
						.get(k).getJobNumber();
				wkcNumber = this.getUnScheduledOperations()[workcenterNumber][machineNumber]
						.get(k).getWorkcenterNumber();
				processTime = jobArray[jobNumber].getProcessTime(wkcNumber);

				if (maxPT < processTime && startTime <= minStartTime) {
					maxPT = processTime;
					lptIndex = k;
				}// end-if
			}// end-for

			this.addToActiveSchedule(this.getUnScheduledOperations()[workcenterNumber][machineNumber]
					.get(lptIndex));

			i++;
		}// end-while

		// Printing results: size of the instance and Rank matrix
		System.out.println();
		System.out.println(Job.getNumberOfJobs());
		System.out.println(Workcenter.getNumberOfWorkcenters());

		for (int j = 0; j < Job.getNumberOfJobs(); j++) {
			for (i = 0; i < Workcenter.getNumberOfWorkcenters(); i++) {
				System.out.print("|"
						+ this.getTimedSchedule().getRankMatrix()[j][i]);
			}// end-for
			System.out.println("|");
		}// end-for
	}// end-LongestProcessTimeRule

	/**
	 * Non-delay Schedule for Flexible Open Shops with LPT rule. It has the
	 * permutationList
	 */
	public void longestProcessTimeRule(ArrayList<TimedOperation> permutationList) {

		int operationIndices[] = new int[3];
		int processTime;
		int jobNumber;
		int wkcNumber;

		int i = 0;
		while (i < Job.getTotalNumberOfOperations()) {
			operationIndices = this.getEarliestStartIndex(); // index, wkcNumber
																// and mchNumber
			int workcenterNumber = operationIndices[0];
			int machineNumber = operationIndices[1];
			int pos = operationIndices[2]; // position of the selected operation
			int minStartTime = this.getUnScheduledOperations()[workcenterNumber][machineNumber]
					.get(pos).getStartTime();
			int lptIndex = 0;
			int maxPT = 0;

			for (int k = 0; k < this.getUnScheduledOperations()[workcenterNumber][machineNumber]
					.size(); k++) {

				// calculates the start time of all schedulable operations at
				// the selected machine of the selected workcenter
				int startTime = this.getUnScheduledOperations()[workcenterNumber][machineNumber]
						.get(k).getSetupStartTime();

				jobNumber = this.getUnScheduledOperations()[workcenterNumber][machineNumber]
						.get(k).getJobNumber();
				wkcNumber = this.getUnScheduledOperations()[workcenterNumber][machineNumber]
						.get(k).getWorkcenterNumber();
				processTime = jobArray[jobNumber].getProcessTime(wkcNumber);

				if (maxPT < processTime && startTime <= minStartTime) {
					maxPT = processTime;
					lptIndex = k;
				}// end-if
			}// end-for
			permutationList
					.add(i,
							this.getUnScheduledOperations()[workcenterNumber][machineNumber]
									.get(lptIndex));
			this.addToActiveSchedule(this.getUnScheduledOperations()[workcenterNumber][machineNumber]
					.get(lptIndex));

			i++;
		}// end-while

		// this.getTimedSchedule().printToLekin();
	}// end-LongestProcessTimeRule

	/**
	 * Non-delay Schedule for Flexible Open Shops with Shortest Remaining
	 * Process Time (SRPT) rule
	 */
	public void shortestRemainingTimeRule() {

		int operationIndices[] = new int[3];
		int[] totalRemainingWork = this.getTimedSchedule().getRemainingTimes();

		int i = 0;
		while (i < Job.getTotalNumberOfOperations()) {

			operationIndices = this.getEarliestStartIndex(); // index, wkcNumber
																// and mchNumber
			int wkcNumber = operationIndices[0];
			int machineNumber = operationIndices[1];
			int pos = operationIndices[2]; // position of the selected operation
			int minStartTime = this.getUnScheduledOperations()[wkcNumber][machineNumber]
					.get(pos).getStartTime();
			int rptIndex = 0;
			int jobNumber;
			int minRPT = 10000;

			for (int k = 0; k < this.getUnScheduledOperations()[wkcNumber][machineNumber]
					.size(); k++) {

				// calculates the start time of all schedulable operations at
				// the selected machine of the selected workcenter
				int startTime = this.getUnScheduledOperations()[wkcNumber][machineNumber]
						.get(k).getSetupStartTime();

				jobNumber = this.getUnScheduledOperations()[wkcNumber][machineNumber]
						.get(k).getJobNumber();
				totalRemainingWork[jobNumber] = this.getTimedSchedule()
						.getRemainingTimes()[jobNumber];

				if (minRPT > totalRemainingWork[jobNumber]
						&& startTime <= minStartTime) {

					minRPT = totalRemainingWork[jobNumber];
					rptIndex = k;
				}// end-if
			}// end-for

			System.out.println("Wkc "
					+ wkcNumber
					+ " job "
					+ this.getUnScheduledOperations()[wkcNumber][machineNumber]
							.get(rptIndex).getJobNumber());
			this.addToActiveSchedule(this.getUnScheduledOperations()[wkcNumber][machineNumber]
					.get(rptIndex));

			// System.out.println(totalRemainingWork[0]+ " "
			// +totalRemainingWork[1]+ " " + totalRemainingWork[2]+ " " +
			// totalRemainingWork[3]);
			i++;
		}// end-while
	}// end-lessWorkRemainingRule

	/**
	 * Non-delay Schedule for Flexible Open Shops with Shortest Remaining
	 * Process Time (SRPT) rule. It has the permutationList
	 */
	public void lessWorkRemainingRule(ArrayList<TimedOperation> permutationList) {

		int operationIndices[] = new int[3];
		int[] totalRemainingWork = this.getTimedSchedule().getRemainingTimes();

		int i = 0;
		while (i < Job.getTotalNumberOfOperations()) {

			operationIndices = this.getEarliestStartIndex(); // index, wkcNumber
																// and mchNumber
			int workcenterNumber = operationIndices[0];
			int machineNumber = operationIndices[1];
			int pos = operationIndices[2]; // position of the selected operation
			int minStartTime = this.getUnScheduledOperations()[workcenterNumber][machineNumber]
					.get(pos).getStartTime();
			int rptIndex = 0;
			int jobNumber;
			int minRPT = 10000;

			for (int k = 0; k < this.getUnScheduledOperations()[workcenterNumber][machineNumber]
					.size(); k++) {

				// calculates the start time of all schedulable operations at
				// the selected machine of the selected workcenter
				int startTime = this.getUnScheduledOperations()[workcenterNumber][machineNumber]
						.get(k).getSetupStartTime();

				jobNumber = this.getUnScheduledOperations()[workcenterNumber][machineNumber]
						.get(k).getJobNumber();
				totalRemainingWork[jobNumber] = this.getTimedSchedule()
						.getRemainingTimes()[jobNumber];

				if (minRPT > totalRemainingWork[jobNumber]
						&& startTime <= minStartTime) {

					minRPT = totalRemainingWork[jobNumber];
					rptIndex = k;
				}// end-if
			}// end-for
			permutationList
					.add(i,
							this.getUnScheduledOperations()[workcenterNumber][machineNumber]
									.get(rptIndex));

			this.addToActiveSchedule(this.getUnScheduledOperations()[workcenterNumber][machineNumber]
					.get(rptIndex));

			// System.out.println(totalRemainingWork[0]+ " "
			// +totalRemainingWork[1]+ " " + totalRemainingWork[2]+ " " +
			// totalRemainingWork[3]);
			i++;
		}// end-while

	}// end-lessWorkRemainingRule

	/**
	 * Non-delay Schedule for Flexible Open Shops with SPT rule
	 */
	public void shortestProcessTimeRule() {
		int operationIndices[] = new int[3];
		int processTime;
		int jobNumber;
		int wkcNumber;

		int i = 0;
		while (i < Job.getTotalNumberOfOperations()) {

			operationIndices = this.getEarliestStartIndex(); // index, wkcNumber
																// and mchNumber
			int workcenterNumber = operationIndices[0];
			int machineNumber = operationIndices[1];
			int pos = operationIndices[2]; // position of the selected operation
			int minStartTime = this.getUnScheduledOperations()[workcenterNumber][machineNumber]
					.get(pos).getStartTime();
			int sptIndex = 0;
			int minPT = 10000;

			for (int k = 0; k < this.getUnScheduledOperations()[workcenterNumber][machineNumber]
					.size(); k++) {

				int startTime = this.getUnScheduledOperations()[workcenterNumber][machineNumber]
						.get(k).getSetupStartTime();

				jobNumber = this.getUnScheduledOperations()[workcenterNumber][machineNumber]
						.get(k).getJobNumber();
				wkcNumber = this.getUnScheduledOperations()[workcenterNumber][machineNumber]
						.get(k).getWorkcenterNumber();
				processTime = jobArray[jobNumber].getProcessTime(wkcNumber);

				if (minPT > processTime && startTime <= minStartTime) {
					minPT = processTime;
					sptIndex = k;
				}// end-if
			}// end-for

			this.addToActiveSchedule(this.getUnScheduledOperations()[workcenterNumber][machineNumber]
					.get(sptIndex));

			i++;
		}// end-while

		// Printing results: size of the instance and Rank matrix
		System.out.println();
		System.out.println(Job.getNumberOfJobs());
		System.out.println(Workcenter.getNumberOfWorkcenters());

		for (int j = 0; j < Job.getNumberOfJobs(); j++) {
			for (i = 0; i < Workcenter.getNumberOfWorkcenters(); i++) {
				// for(int k=0;k<Machine.getTotalNumberOfMachines();k++){
				System.out.print("|"
						+ this.getTimedSchedule().getRankMatrix()[j][i]);
				// }//end-for
			}
			System.out.println("|");

		}// end-for
	}// end-shortestProcessTimeRule

	/**
	 * Non-delay Schedule for Flexible Open Shops with SPT rule. It has the
	 * permutationList
	 */
	public void shortestProcessTimeRule(
			ArrayList<TimedOperation> permutationList) {
		int operationIndices[] = new int[3];
		int processTime;
		int jobNumber;
		int wkcNumber;

		int i = 0;
		while (i < Job.getTotalNumberOfOperations()) {

			operationIndices = this.getEarliestStartIndex(); // index, wkcNumber
																// and mchNumber
			int workcenterNumber = operationIndices[0];
			int machineNumber = operationIndices[1];
			int pos = operationIndices[2]; // position of the selected operation

			int minStartTime = this.getUnScheduledOperations()[workcenterNumber][machineNumber]
					.get(pos).getStartTime();
			int sptIndex = 0;
			int minPT = 10000;

			for (int k = 0; k < this.getUnScheduledOperations()[workcenterNumber][machineNumber]
					.size(); k++) {

				int startTime = this.getUnScheduledOperations()[workcenterNumber][machineNumber]
						.get(k).getSetupStartTime();

				jobNumber = this.getUnScheduledOperations()[workcenterNumber][machineNumber]
						.get(k).getJobNumber();
				wkcNumber = this.getUnScheduledOperations()[workcenterNumber][machineNumber]
						.get(k).getWorkcenterNumber();
				processTime = jobArray[jobNumber].getProcessTime(wkcNumber);

				if (minPT > processTime && startTime <= minStartTime) {
					minPT = processTime;
					sptIndex = k;
				}// end-if
			}// end-for
			permutationList
					.add(i,
							this.getUnScheduledOperations()[workcenterNumber][machineNumber]
									.get(sptIndex));
			this.addToActiveSchedule(this.getUnScheduledOperations()[workcenterNumber][machineNumber]
					.get(sptIndex));

			i++;
		}// end-while

		this.getTimedSchedule().printToLekin();
	}// end-shortestProcessTimeRule

}
