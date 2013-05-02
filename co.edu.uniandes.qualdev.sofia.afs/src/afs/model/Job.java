package afs.model;


import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Arrays;
import java.util.StringTokenizer;
/*
 * AFS (Algorithms for Scheduling) - Copyright (C) 2005-2008 Gonzalo Mejía
 * This file is part of AFS
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Library General Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Library General Public License for more details.
 *
 * You should have received a copy of the GNU Library General Public
 * License along with this library; if not, write to the Free
 * Software Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */

/**
 * <p>Title: AFS Java version 3.0</p>
 *
 * <p>Description: Algorithms for Scheduling version Java</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Pylo.Uniandes.edu.co</p>
 * 
 * <p> Class Tjob </p>
 * <p> The class Tjob provides all the attributes defined in Lekin for a job. Also provides a method to read a job from the _usr.job file.</p>
 * The attributes (members) are: <br>
 * <b> String jobId:</b> The job identifier. <br>
 * <b> int jobNumber: </b>job number (as read). <br>
 * <b> int releaseDate: </b>minimum availability date of a job. <br>
 * <b> int dueDate: </b>Due date of a job. <br>
 * <b> int weight: </b>Priority of a job. <br>
 * <b> int totalWorkTime: </b> The sum of the processing times of a job <br>
 * <b> int numberOfOperations: </b>Number of operations of a job. <br>
 * <b> Operation route[]: </b>"Routing sheet". Array with the sequence of machines which a job visits (precedence constraints).<br>
 * <p> Statics: <br>
 * <b> String framework: </b> Can be "single", "flexible"  or "shop".
 * <b> Constant int MAXNUMOPERATIONS: </b> Maximum job operations.
 * <b> int totalNumberOfOperationst: </b> Total number of operations to be scheduled. <br>
 * <b> int numberOfJobs: </b> Number of jobs in the system.</p>
 * @author Gonzalo Mejia
 * @version 3.0
 */
/**
 * @author gmejia
 *
 */
public class Job {

	/**
	 * 
	 * Maximum number of operations
	 */
	final static int MAXNUMOPERATIONS = 50;

	/**
	 * 
	 * total number of operations
	 */
	static int totalNumberOfOperations=0;

	/**
	 * 
	 * Number of jobs in the system
	 */
	private static int numberOfJobs=0;

	/**
	 * 
	 * framework: "Single", "Flow", "Shop"
	 */
	private static String framework;

	/**
	 * 
	 * jobId: JobIdentifier: Jobxxx
	 */
	private String jobID;

	/**
	 * 
	 * jobNumber: valid integer
	 */
	private int jobNumber;

	/**
	 * 
	 * releaseDate: Job release or availability date
	 */
	private int releaseDate;				


	/**
	 * 
	 * dueDate: Job due date
	 */
	private int dueDate;

	/**
	 * 
	 * weight: Job weight or priority
	 */
	private int weight;

	/**
	 * 
	 * number of operations: valid integer; cannot exceed Lekin limits
	 */
	private int numberOfOperations;


	/**
	 * 
	 * Array of Operations. See the documentation of the class Operation
	 * The k-th position of the array contains the k-th Operation of the job 
	 */
	private Operation route[];


	/**
	 * 
	 * Array of int. 
	 * the k-th position contains the position in the job route of the  workcenter number k.
	 * wkcPosition[5] = 4, means that Workcenter 5 is the 4-th one to be visited
	 */
	private int wkcPosition[];


	/**
	 * 
	 */
	private int totalWorkTime=0;

	//////////////////////////////////////////////////////////////////////
	// Construction
	//////////////////////////////////////////////////////////////////////
	/** Default Constructor.<br>
	 */
	public Job(){

		this.jobNumber = numberOfJobs;
		numberOfJobs++;
	}


	//////////////////////////////////////////////////////////////////////
	// Set methods
	//////////////////////////////////////////////////////////////////////


	/**
	 * setJobId sets the job identifier
	 *
	 * @param idn String
	 */
	public void setJobID(String idn) {
		jobID = idn;
	}

	/**
	 * setReleaseDate sets the job release date rj
	 *
	 * @param rls int
	 */
	public void setReleaseDate(int rls){
		releaseDate = rls;

	}

	/**
	 * setDueDate sets the job due date dj
	 *
	 * @param dd int
	 */
	public void setDueDate(int dd){
		dueDate = dd;

	}

	/**
	 * setWeight sets the job weight
	 *
	 * @param wgt int
	 */
	public void setWeight(int wgt){

		weight = wgt;
	}

	/**
	 * setProcessTime sets the processing time of the i-th operation 
	 *
	 * @param processTime int (Operation processing time)
	 * @param i int (i-th position)
	 * @throws an ArrayIndexOutOfBoundsException if i is out of range
	 */
	public void setProcessTime(int processTime, int i){

		try{
			route[i].setProcessTime(processTime);

			if (i >= numberOfOperations){

				throw new Exception();
			}
		}
		catch (ArrayIndexOutOfBoundsException e){
			throw new ArrayIndexOutOfBoundsException();
		}
		catch (Exception e) {

			System.out.println("Invalid operation subscript"); 

		}

	}

	/**
	 * setStatus sets status of the i-th operation
	 *
	 * @param status int (Operation status)
	 * @param i int (i-th position)
	 * @throws an ArrayIndexOutOfBoundsException if i is out of range
	 */
	public void setStatus(char status, int i){

		try{
			route[i].setStatus(status);

			if (i >= numberOfOperations){

				throw new Exception();
			}
		}
		catch (ArrayIndexOutOfBoundsException e){
			throw new ArrayIndexOutOfBoundsException();
		}
		catch (Exception e) {

			System.out.println("Invalid operation subscript"); 

		}

	}

	//////////////////////////////////////////////////////////////////////
	// get Methods
	//////////////////////////////////////////////////////////////////////

	/**
	 * getNumberOfJobs returns the number of jobs
	 *
	 * @return int
	 */
	public static int getNumberOfJobs(){
		return numberOfJobs;
	}

	/**
	 * getId returns the job identifier
	 *
	 * @return String
	 */
	public String getId() {
		return jobID;
	}

	/**
	 * getRelease returns the job release date
	 *
	 * @return int
	 */
	public int getRelease() {
		return releaseDate;
	}

	/**
	 * getDue returns the job due date
	 *
	 * @return int
	 */
	public int getDue() {
		return dueDate;
	}

	/**
	 * getWeight returns the job weight
	 *
	 * @return int
	 */
	public int getWeight() {
		return weight;
	}

	/**
	 * getNumber returns the job number
	 *
	 * @return int
	 */
	public int getNumber() {
		return jobNumber;
	}

	/**
	 * getNumberOfOperations returns the number of operations of the job
	 *
	 * @return int
	 */
	public int getNumberOfOperations() {
		return numberOfOperations;
	}

	/**
	 * getRoute returns the route array (of Operations).<br>
	 * The i-th position of this array corresponds to the i-th job operation
	 *
	 * @return Operation[]
	 */
	public Operation[] getRoute() {
		return route;
	}


	/**
	 * getRoute returns the i-th operation.<br>
	 * @param i int
	 * @return Operation
	 * @throws ArrayIndexOutOfBoundsException
	 */

	public Operation getRoute(int i) {

		try {

			if (i >= numberOfOperations){

				throw new Exception();
			}
			return route[i];
		}
		catch (Exception e){
			throw new ArrayIndexOutOfBoundsException();
		}
	}


	/**
	 * @return the workcenter position array
	 */
	public int[] getWkcOPosition(){

		return  wkcPosition;
	}

	/**
	 * getProcessingTime(i) returns the processing time of the job operation performed on the i-th workcenter
	 *
	 * @param int
	 * @return int
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public int getProcessTime(int i) {
		try {
			
			return route[i].processTime;
		}
		catch (Exception e){
			throw new ArrayIndexOutOfBoundsException();
		}

	}
	
	/**
	 * getProcessingTime(workcenter) returns the processing time of the job operation performed on the workcenter
	 *
	 * @param Workcenter
	 * @return int
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public int getProcessTime(Workcenter workcenter) {
		try {
			
			int pos = wkcPosition[workcenter.getWorkcenterNumber()];
			return route[pos].processTime;
		}
		catch (Exception e){
			throw new ArrayIndexOutOfBoundsException();
		}

	}

	/**
	 * getWorkcenter returns the workcenterID used on the i-th job operation 
	 *
	 * @param i int
	 * @return String
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public String getWorkcenterID(int i)  {
		try {

			if (i >= numberOfOperations){

				throw new Exception();
			}

			return route[i].getWorkcenterID();

		}
		catch (Exception e) {
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	/**
	 * getStatus returns the status of the i-th job operation
	 *
	 * @param i int
	 * @return char
	 */
	public char getStatus( int i) {
		try {
			return route[i].getStatus();
		}
		catch (Exception e) {
			throw new ArrayIndexOutOfBoundsException();
		}

	}

	/**
	 * getWorkcenterNumber returns i-th position of the routeNumber array
	 *
	 * @param i int
	 * @return int
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public int getWorkcenterNumber(int i)  {
		try {

			return route[i].getWorkcenterNumber();
		}
		catch (Exception e) {
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	/**
	 * getFramework returns the "framework". Possible values "Single", "Flexible", "Shop"
	 *
	 * @return String
	 */
	public static String getFramework(){
		return framework;
	}

	/**
	 * getTotalNumberOfOperations returns the Total Number Of Operations
	 *
	 * @return int
	 */
	public static int getTotalNumberOfOperations(){
		return totalNumberOfOperations;

	}

	/**
	 * getTotalWorkTime returns the sum of all processing times of all operations
	 *
	 * @return int
	 */
	public int getTotalWorkTime(){
		return totalWorkTime;
	}
	
	
	/**
	 * @return the wkcPosition
	 */
	public int[] getWkcPosition() {
		return wkcPosition;
	}


	/**
	 * @param totalNumberOfOperations the totalNumberOfOperations to set
	 */
	public static void setTotalNumberOfOperations(int totalNumberOfOperations) {
		Job.totalNumberOfOperations = totalNumberOfOperations;
	}


	/**
	 * @param numberOfJobs the numberOfJobs to set
	 */
	public static void setNumberOfJobs(int numberOfJobs) {
		Job.numberOfJobs = numberOfJobs;
	}


	/**
	 * @param framework the framework to set
	 */
	public static void setFramework(String framework) {
		Job.framework = framework;
	}


	/**
	 * @param jobNumber the jobNumber to set
	 */
	public void setJobNumber(int jobNumber) {
		this.jobNumber = jobNumber;
	}


	/**
	 * @param numberOfOperations the numberOfOperations to set
	 */
	public void setNumberOfOperations(int numberOfOperations) {
		this.numberOfOperations = numberOfOperations;
	}


	/**
	 * @param route the route to set
	 */
	public void setRoute(Operation[] route) {
		this.route = route;
	}


	/**
	 * @param wkcPosition the wkcPosition to set
	 */
	public void setWkcPosition(int wkcNumber, int position) {
		this.wkcPosition[wkcNumber] = position;
	}


	/**
	 * @param totalWorkTime the totalWorkTime to set
	 */
	public void setTotalWorkTime(int totalWorkTime) {
		this.totalWorkTime = totalWorkTime;
	}


	/**
	 * Initializes the wkcOrder array with -1s. A -1 at the position k-th means that the workcenter k-th is not visited.
	 * @param numberOfWorkcenters
	 */
	public void initWkPosition(int numberOfWorkcenters){

		wkcPosition = new int[numberOfWorkcenters];
		Arrays.fill(wkcPosition, -1);
	}

	public static void readJobTextFile(BufferedReader in, Job[] jobArray, int numberOfJobs, int numberOfWorkcenters) throws IOException {

		//Job.setNumberOfJobs(numberOfJobs);
		String textLine;
		String token;
		totalNumberOfOperations = numberOfJobs * numberOfWorkcenters;	
		StringTokenizer str;

		for (int j=0; j < numberOfJobs; j++){
			
			jobArray[j] = new Job();
			jobArray[j].setReleaseDate(0);
			jobArray[j].setDueDate(0);
			jobArray[j].setWeight(1);
			jobArray[j].setNumberOfOperations(numberOfWorkcenters);
			
			String jobID = "Job" + j;
			jobArray[j].setJobID(jobID);
			jobArray[j].wkcPosition = new int[numberOfWorkcenters];
			
			textLine = in.readLine();
			str = new StringTokenizer(textLine,"|");
			Operation route[]= new Operation[numberOfWorkcenters];
			int totalWorkTime=0;
			Workcenter.setNumberOfWorkcenters(numberOfWorkcenters);
			
			for (int i = 0; i < Workcenter.getNumberOfWorkcenters(); i++ ){
				
				
				jobArray[j].wkcPosition[i]=i;//sets the workcenter position
				
				Operation operation = new Operation();				
				operation.setJobNumber(j);
				operation.setStatus('A');
				String wkcID = "Wkc"+i; 

				operation.setWorkcenterID(wkcID);
				operation.setWorkcenterNumber(i);
				
				token = str.nextToken();  // the process time
				int processTime = Integer.parseInt(token);
				totalWorkTime = totalWorkTime + processTime;
				operation.setProcessTime(processTime);
				route [i] = operation;
			}
			
			jobArray[j].setRoute(route);
			jobArray[j].totalWorkTime = totalWorkTime;
		}
	}

	/**
	 * printJobs writes the job information to an output file
	 * Default file is "outputdata.txt"
	 *
	 * @param out BufferedWriter
	 * @param jobArray Tjob[]
	 * @throws Exception
	 */
	public static void printJobs(BufferedWriter out, Job[] jobArray){
		try {
			out.write("Number of Jobs \t" + Job.getNumberOfJobs());
			out.newLine();
			out.newLine();
			for (int j = 0; j < numberOfJobs; j++) {

				out.write(jobArray[j].getId());
				out.newLine();
				out.write("Release Date \t" + jobArray[j].getRelease());
				out.newLine();
				out.write("Due Date \t" + jobArray[j].getDue());
				out.newLine();
				out.write("Weight \t \t" + jobArray[j].getWeight());
				out.newLine();
				for (int i = 0; i < jobArray[j].getNumberOfOperations(); i++) {

					out.write("Operation: \t" + jobArray[j].route[i].getWorkcenterID());
					out.write(": " + jobArray[j].route[i].getProcessTime()+ "\t Status: ");
					out.write(jobArray[j].route[i].getStatus());

					out.newLine();
				}
				out.newLine();
			}
		}

		catch (Exception e){

			System.out.println("Writing file error");

		}
	}
	


	/**
	 * printJobs prints the job information to the console
	 *
	 * @param jobArray Tjob[]
	 * @param jobs StringBuffer
	 */
	public static void printJobs(Job[] jobArray) {

		StringBuffer stringBuffer= new StringBuffer();
		String jobOutput = "Number of Jobs " + Job.getNumberOfJobs() + "\n";
		for (int j = 0; j < numberOfJobs; j++) {

			jobOutput += jobArray[j].getId() + "\n";
			jobOutput += "Release Date \t" + jobArray[j].getRelease()+ "\n";;
			jobOutput += "Due Date \t" + jobArray[j].getDue() + "\n";
			jobOutput += "Weight \t \t" + jobArray[j].getWeight()+ "\n";

			for (int i = 0; i < jobArray[j].getNumberOfOperations(); i++) {

				jobOutput += "Operation:  \t" + jobArray[j].route[i].getWorkcenterID();                                                
				jobOutput += ": " + jobArray[j].route[i].getProcessTime() + "\t Status: ";;  
				jobOutput += jobArray[j].route[i].getStatus() + "\n";

			}

			jobOutput += "\n";
		}
		stringBuffer.append(jobOutput);

		System.out.print(stringBuffer);
	}




} // class




