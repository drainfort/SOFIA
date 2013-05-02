package afs.model;
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
 * <p> Class Operation </p>
 * <p> The class Operation provides all the attributes defined in Lekin for an operation. Also provides the methods to read a job from the _usr.job file.</p>
 * The attributes (members) are: <br>
 * <b> int jobNumber: </b>job number (as read). <br>
 * <b> int processTime: </b>process time of the operation. <br>
 * <b> int status: </b> operation type for sequence dependent setup times <br>
 * <b> int workcenterNumber: </b> Number of the workcenter <br>
 * <b> int workcenterID </b>  String name of the workcenter <br>
 */

public class Operation {


	/**
	 * processTime. The processing time of the operation valid integer:
	 */
	protected int processTime;	


	/**
	 * job number
	 */
	protected int jobNumber;


	/**
	 * 
	 * workcenterID: <br>
	 * Type: String. <br>
	 * Corresponds to the workcenter where the operation is performed. 
	 * workcenterID has the following format: "Wkc.xxx" 
	 */
	protected String workcenterID= new String();

	/**
	 * 
	 * status: <br>
	 * Type: char. <br>
	 * status corresponds to the operation type (for sequence dependent setups). <br>
	 * Valid values are 'A' to 'Z'
	 */
	protected char status;

	/**
	 * 
	 * workcenterNumber: <br>
	 * Type: int
	 * Assigns a number to each workcenter. The number is extracted from the workcenterID.
	 * For example if the ID is: "Wkc004", then the workcenter number is 4. 
	 */
	protected int workcenterNumber;


	/////////////////////////////////////////////////////////////////////////////////////////////
	//	/ Constructors
	////////////////////////////////////////////////////////////////////////////////////////////	/
	/**
	 * Constructor
	 * @param int processTime: Operation process time <br>
	 * @param String workcenterID: Workcenter identifier <br>
	 * @param status: Machine status <br>
	 * @param workcenterNumber: Workcenter number <br>
	 */	  
	public Operation(int jobNumber, int workcenterNumber, int processTime, String workcenterID, char status) {

		this.jobNumber = jobNumber;
		this.processTime = processTime;
		this.workcenterID = workcenterID;
		this.status = status;
		this.workcenterNumber = workcenterNumber;
	} 

	/**
	 * Default Constructor
	 */	 
	public Operation(){

	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	/// Get Methods
	/////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns the job number
	 * @return int
	 */
	public int getJobNumber() {
		return jobNumber;
	}
	
	/**
	 * Returns the operation process time
	 * @return int
	 */
	public int getProcessTime() {
		return processTime;
	}

	/**
	 * Returns the operation status
	 * @return char
	 */
	public char getStatus() {
		return status;
	}

	/**
	 * Returns the workcenter ID
	 * @return String
	 */
	public String getWorkcenterID() {
		return workcenterID;
	}

	/**
	 * Returns the workcenter number
	 * @return int
	 */
	public int getWorkcenterNumber() {
		return workcenterNumber;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////
	/// Set Methods
	////////////////////////////////////////////////////////////////////////////////////////////	/

	/**
	 * Sets the operation job number
	 * @param jobNumber
	 */
	public void setJobNumber(int jobNumber) {
		this.jobNumber = jobNumber;
	}
	
	/**
	 * Sets the operation process time
	 * @param processingTime: The operation time
	 */
	public void setProcessTime(int processTime) {
		this.processTime = processTime;
	}

	/**
	 * Sets the operation status
	 * @param status: status (type of operation)
	 */
	public void setStatus(char status) {
		this.status = status;
	}

	/**
	 * Sets the workcenter identifier
	 * @param workcenterID: The workcenter identifier
	 */
	public void setWorkcenterID(String workcenterID) {
		this.workcenterID = workcenterID;
	}

	/**
	 * Sets the workcenter number
	 * @param workcenterNumber: The workcenter number
	 */
	public void setWorkcenterNumber(int workcenterNumber) {
		this.workcenterNumber = workcenterNumber;
	} 

	
}
