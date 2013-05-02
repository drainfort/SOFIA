package afs.schedule;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import afs.model.Operation;
import afs.model.Job;
import afs.model.Workcenter;

/**
 * <p>Title: AFS Java version 3.0</p>
 *
 * <p>Description: Algorithms for Scheduling version Java</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Pylo.Uniandes.edu.co</p>
 * <b> Schedule Members </b>
 * <b> Tjob[] jobArray </b>  The array of jobs<br>
 * <b> Workcenter[] wkcArray  </b> The array of workcenters <br>
 * <b> int numberOfOperations </b> Number of scheduled Operations<br>
 * <b> ArrayList<Operation> gantt[][]; </b> 
 * The data structure of the schedule. The position i,l,k 
 * corresponds to the k-th job of the l-th machine of the i-th workcenter<br>

 * 
 * @author Gonzalo Mejia
 * @version 3.0
 */
/**
 * @author gmejia
 *
 */
public class Schedule {

	/**
	 * The array of jobs
	 */
	private Job[] jobArray;
	
	
	/**
	 * The array of workcenters
	 */
	private Workcenter[] wkcArray;

	/**
	 * The variable gantt is an array[][] of Operation (s)
	 */
	private ArrayList<Operation> gantt[][];

	/**
	 * The variable numberOfOperations is the number of scheduled operations so far
	 */
	private int numberOfOperations;

	/**
	 * Schedule Constructor. Constructs the skeleton of a schedule given an array
	 * of workcenters. A schedule consists of an array of workcenters. Each
	 * workcenter contains an array of machines.
	 * Each machine contains the list (ArrayList) of jobs scheduled on such a machine
	 
	 * @param wkcArray
	 */
	public Schedule(Workcenter[] wkcArray, Job[] jobArray) {
		int numberOfWorkCenters = Workcenter.getNumberOfWorkcenters();

		this.wkcArray = wkcArray;
		this.jobArray = jobArray;
		gantt = new ArrayList[numberOfWorkCenters][];
		for (int i = 0; i <gantt.length ; i++) {
			gantt[i] = new ArrayList[wkcArray[i].getNumberOfMachines()];
			for (int j=0; j < gantt[i].length;j++){

				gantt[i][j] = new ArrayList<Operation>();

			}

		} // for
		numberOfOperations = 0;
	} //constructor

	/**
	 * Returns the gantt element
	 * 
	 * @return
	 */

	public ArrayList[][] getGantt() {
		return gantt;
	}



	public void setGantt(ArrayList[][] gantt) {
		this.gantt = (ArrayList<Operation>[][])gantt;
	}


	/**
	 * @param i
	 * @return WorkcenterSequence (i)
	 */
	public ArrayList[] getGantt(int i){

		return gantt[i];
	}


	/**
	 * @param i
	 * @param j
	 * @return an arraylist
	 */
	public ArrayList<Operation> getGantt(int i, int j){

		return gantt[i][j];

	}



	/**
	 * @param i
	 * @param j
	 * @param k
	 * @return the jobNumber
	 */
	public int getGantt(int i, int j, int k){

		return gantt[i][j].get(k).getJobNumber();
	}



	/**
	 * addToSchedule: adds a job to the schedule of a machine of a workcenter. The job is added at
	 * the end of each machine schedule. machineIndex must be a valid index (0 to
	 * Mi-1) where Mi is the number of machines at the worcenter i.
	 * workcenterIndex must be also valid (0 to #Wkc-1) where #Wkc is the total
	 * number of workcenters.
	 * jobNumber must be a valid index ranging from 0 to the total number of jobs-1
	 *
	 * @param jobNumber int
	 * @param workstation int
	 * @param machine int
	 * @throws Exception (Array out of Bounds Exception)
	 */
	public void addToSchedule (int jobNumber, int workstation, int machine) throws Exception{

		try {
			if (jobNumber >= Job.getNumberOfJobs()){
				throw new ArrayIndexOutOfBoundsException();

			}

			int wksPosition = jobArray[jobNumber].getWkcPosition()[workstation];
			if (wksPosition == -1) {
				
				throw new ArrayIndexOutOfBoundsException();
			}
				
			else{ 
				gantt[workstation][machine].add(jobArray[jobNumber].getRoute()[wksPosition]);
				numberOfOperations++;
			}
		}
		catch (ArrayIndexOutOfBoundsException e){

			System.out.println("Error: Invalid workcenter " + workstation + " or machine " + machine + " indices");
			System.exit(1);
		}

	}


	/**
	 * insertToSchedule: inserts a job to the schedule of a machine of a workcenter. The job is added at
	 * the position "pos" of each machine schedule. machineIndex must be a valid index (0 to
	 * Mi-1) where Mi is the number of machines at the worcenter i.
	 * workcenterIndex must be also valid (1 to #Wkc) where #Wkc is the total
	 * number of workcenters.
	 * jobNumber must be a valid index ranging from 0 to the total number of jobs
	 *
	 * @param jobNumber int
	 * @param workstation int
	 * @param machine int
	 * @param pos int
	 * @throws Exception (Array out of Bounds Exception)
	 */
	public void insertToSchedule (int jobNumber, int workstation, int machine, int pos) throws Exception{

		try {
			if (jobNumber >= Job.getNumberOfJobs())
				throw new ArrayIndexOutOfBoundsException();
			
			int wksPosition = jobArray[jobNumber].getWkcPosition()[workstation];
			gantt[workstation][machine].add(pos, jobArray[jobNumber].getRoute()[wksPosition]);
			numberOfOperations++;
		}
		catch (Exception e){

			System.out.println("Error: Invalid insertion");
			System.exit(1);
		}

	}

	/**
	 * swapToSchedule: swaps two jobs on the same machine.
	 * Swaped jobs are in positions pos1 and pos2. 
	 * machineIndex must be a valid index (0 to Mi-1) where Mi is the number of machines 
	 * at the worcenter i.
	 * workcenterIndex must be also valid (1 to #Wkc) where #Wkc is the total
	 * number of workcenters.
	 * jobNumber must be a valid index ranging from 0 to the total number of jobs
	 *
	 * @param workstation int
	 * @param machine int
	 * @param pos1 int
	 * @param pos2 int
	 * @throws Exception (Array out of Bounds Exception)
	 */
	public void swapToSchedule (int workstation, int machine, int pos1, int pos2) throws Exception{

		try {
			Operation op2 =  gantt[workstation][machine].get(pos2);
			Operation op1 = gantt[workstation][machine].get(pos1);
			gantt[workstation][machine].set(pos2, op1);
			gantt[workstation][machine].set(pos1, op2);
		}
		catch (Exception e){

			System.out.println("Error: Invalid swap of jobs");
			System.exit(1);
		}

	}




	/**
	 * printToLekin prints to the "user.seq" file the current schedule in a Lekin
	 * format. See Lekin documentation for details.
	 * Throws a IOException if the method fails. Notice that this method does not
	 * validate the schedule. Lekin does this validation
	 */
	public void printToLekin(){

		File outFile = new File("_user.seq");


		try {

			BufferedWriter out = new BufferedWriter(new FileWriter(outFile));
			out.write("Schedule \t \t AFS Library");
			out.newLine();

			for (int i = 0; i < gantt.length; i++) {

				for (int j = 0; j < gantt[i].length; j++) {

					out.write("Machine \t" + i + "." +j);
					out.newLine();

					for (int k = 0; k < gantt[i][j].size(); k++) {

						int jobNumber = ((Operation)gantt[i][j].get(k)).getJobNumber();
						out.write("Oper: \t" + jobNumber);
						out.newLine();
					} // for k

				} //for j
			} // for i
			out.close();
		} // try


		catch (IOException e){

			System.out.println("Error writing to Lekin");
			System.exit(1);

		}

	}

	/**
	 * @return the number of Operations
	 */
	public int getNumberOfOperations(){

		return numberOfOperations;
	}
	
	

}//class


