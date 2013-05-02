package afs.model;

import java.io.BufferedReader;
import java.io.IOException;
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
 * <p>
 * Title: AFS Java version 3.0
 * </p>
 * 
 * <p>
 * Description: Algorithms for Scheduling version Java
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Pylo.Uniandes.edu.co
 * </p>
 * <b> Workcenter Members </b> <b> static final int MAXSETUP</b> Maximum size of
 * the setup matrix. <br>
 * <b> static final int MAXTRAVEL</b> Maximum size of the travel matrix. <br>
 * <b> static final int MAXMACHINES </b> Maximum number of parallel machines. <br>
 * <b> static final int MAXWORKCENTERS </b> Maximum number of workcenters. <br>
 * <b> static String framework</b> Framework defines the type of machine
 * setting. <br>
 * <b> static int numberOfWorkcenters </b> Defines the current number of
 * workcenters in the system. <br>
 * <b> String WorkcenterId </b> Defines the workcenter ID.<br>
 * <b> Machine machineArray[] </b> Defines the array of parallel machines of the
 * the workcenter. <br>
 * <b> int setupMatrix[][] </b> Defines the matrix of sequence dependent setup
 * times.<br>
 * <b> int numberOfMachines </b> Defines the total number of machines in the
 * workcenter. <br>
 * 
 * @author Gonzalo Mejia
 * @version 3.0
 */
public class Workcenter {

	/**
	 * maximum size of the setup matrix
	 */
	private static final int MAXSETUPMATRIX = 28;

	/**
	 * maximum size of the travel matrix
	 */
	private static final int MAXTRAVELTIMESMATRIX = 20;

	/**
	 * maximum number of machines in a workcenter
	 */
	private static final int MAXMACHINES = 100;

	/**
	 * maximum size of workcenters
	 */
	private static final int MAXWORKCENTERS = 20;

	/**
	 * framework: "Ordinary" or "Flexible"
	 */
	private static String framework;

	/**
	 * numberOfWorkcenters: Current number of workcenters in the system
	 */
	private static int numberOfWorkcenters;

	/**
	 * unique consecutive number
	 */
	private int workcenterNumber;

	/**
	 * workcenterID: String identifying the ID of a workcenter Wkcxxx
	 */
	private String workcenterID;

	/**
	 * machine array: array of parallel machines of a workcenter
	 */
	Machine machineArray[]; //

	/**
	 * setupMatrix: matrix with sequence dependent setup times
	 */
	private int setupMatrix[][];

	/**
	 * travelTime: matrix with travel times
	 */
	private int travelTimes[];

	/**
	 * 
	 * depotTravel: travel time from the depot to the this workcenter
	 */
	private int depotTravelTime;

	/**
	 * number of machines: number of identical parallel machines in a workcenter
	 */
	private int numberOfMachines;

	/**
	 * Default Constructor
	 */
	public Workcenter() {
		workcenterNumber = numberOfWorkcenters;
		numberOfWorkcenters++;
		setupMatrix = new int[MAXSETUPMATRIX][MAXSETUPMATRIX];
		travelTimes = new int[MAXTRAVELTIMESMATRIX];// ORI
	}

	// ////////////////////////////////////////////////////////////////////
	// get methods
	// ////////////////////////////////////////////////////////////////////
	/**
	 * getFramework returns the framework that can be either "Flexible" or
	 * "Ordinary"
	 * 
	 * @return String
	 */
	public static String getFramework() {
		return framework;
	}

	/**
	 * @return the workcenterNumber
	 */
	public int getWorkcenterNumber() {
		return workcenterNumber;
	}

	/**
	 * @return the workcenterID
	 */
	public String getWorkcenterID() {
		return workcenterID;
	}

	/**
	 * @return the machineArray
	 */
	public Machine[] getMachineArray() {
		return machineArray;
	}

	/**
	 * @return the setupMatrix
	 */
	public int[][] getSetupMatrix() {
		return setupMatrix;
	}

	/**
	 * @return the travelTimes
	 */
	public int[] getTravelTimes() {
		return travelTimes;
	}

	/**
	 * @return the depotTravelTimes
	 */
	public int getDepotTravelTimes() {
		return depotTravelTime;
	}

	/**
	 * getId returns the workcenter Id
	 * 
	 * @return String
	 */
	public String getID() {
		return workcenterID;
	}

	/**
	 * getNumberOfMachines returns the number of parallel machines in a
	 * workcenter
	 * 
	 * @return int
	 */
	public int getNumberOfMachines() {
		return numberOfMachines;
	}

	/**
	 * getSetup returns the sequence dependent setup times two dimensional
	 * array. Returns integer numbers. <br>
	 * 0 corresponds to 'A"; 1 to 'B' and so forth. <br>
	 * 
	 * @return int[][]
	 */
	public int[][] getSetup() {
		return setupMatrix;
	}

	/**
	 * getSetup (i,l) returns the i,l position of the sequence dependent setup
	 * times two dimensional array. <br>
	 * 0 corresponds to 'A'; 1 to 'B' and so forth. <br>
	 * 
	 * @return int
	 */
	public int getSetup(int i, int l) {

		try {
			return setupMatrix[i][l];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException();
		}

	}

	/**
	 * getTravel (i) returns the travel time from this workcenter to the i-th
	 * workcenter. <br>
	 * 
	 * @return int
	 */
	public int getTravelTime(int i) {

		try {
			return travelTimes[i];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new ArrayIndexOutOfBoundsException();
		}

	}

	/**
	 * @param i
	 * @param travelTime
	 */
	public void setTravel(int i, int travelTime) {

		this.travelTimes[i] = travelTime;

	}

	/**
	 * getDepotTravel returns the travel time from the depot to the other
	 * workcenters
	 * 
	 * @return int
	 */
	public int getDepotTravelTime() {
		return depotTravelTime;
	}

	/**
	 * setDepotTravel sets the travel time TT0i and TTi0
	 * 
	 * @param depTravel
	 *            int
	 */
	public void setDepotTravelTime(int depTravel) {
		depotTravelTime = depTravel;

	}

	/**
	 * getMchArray returns the array of parallel machines in a workcenter
	 * 
	 * @return Machine[]
	 */
	public Machine[] getMchArray() {
		return machineArray;
	}

	/**
	 * getMachine returns the i-th machine in a workcenter
	 * 
	 * @param i
	 *            int
	 * @return Machine
	 */
	public Machine getMachine(int i) {
		try {
			return machineArray[i];
		} catch (Exception e) {
			throw new ArrayIndexOutOfBoundsException();
		}

	}

	/**
	 * getNumberOfWorkcenters
	 * 
	 * @return int: Returns the total number of workcenters
	 */
	public static int getNumberOfWorkcenters() {
		return numberOfWorkcenters;
	}
	
	public static void setNumberOfWorkcenters(int nNumber) {
		Workcenter.numberOfWorkcenters= nNumber;
	}

	public static void readTravelTimesFile(BufferedReader in,
			Workcenter[] wkcArray, int numberOfWorkcenters) throws IOException {

		String textLine;
		textLine = in.readLine(); // # of workcenters
		textLine = in.readLine(); // # of workcenters
		String travelTimesStr[];
		String depotTravelTimesStr[];

		textLine = in.readLine(); // reads the first line
		textLine = textLine.substring(3);
		depotTravelTimesStr = textLine.split("\\|");

		for (int i = 0; i < numberOfWorkcenters; i++) {

			wkcArray[i] = new Workcenter();
			wkcArray[i].workcenterID = "Wkc." + i;
			wkcArray[i].setupMatrix = new int[numberOfWorkcenters][numberOfWorkcenters];
			wkcArray[i].depotTravelTime = 0;
			if(!depotTravelTimesStr[i].equals("")){
				wkcArray[i].depotTravelTime = Integer
					.parseInt(depotTravelTimesStr[i]);
			}// first position is the
														// depot travel times
			wkcArray[i].machineArray = new Machine[1];
			wkcArray[i].machineArray[0] = new Machine();
			wkcArray[i].machineArray[0].setAvailableDate(0);
			wkcArray[i].machineArray[0].setMachineID("Wkc." + i + "Mach.0");
			wkcArray[i].machineArray[0].setMachineNumber(0);
			wkcArray[i].machineArray[0].setStatus('A');
			wkcArray[i].machineArray[0].setWorkcenterNumber(i);
			wkcArray[i].numberOfMachines = 1;

			// travel time from/to the other workcenters and the depot
			wkcArray[i].travelTimes = new int[numberOfWorkcenters];
			textLine = in.readLine(); // reads the first line
			textLine = textLine.substring(1); // eliminates the first "|"
			int pos = textLine.indexOf("|");
			textLine = textLine.substring(pos + 1);

			travelTimesStr = textLine.split("\\|");

			for (int l = 0; l < numberOfWorkcenters; l++) {

				wkcArray[i].travelTimes[l] = Integer
						.parseInt(travelTimesStr[l]);
			}
		}

	}

	/**
	 * printWorkCenters prints the workcenter information to the console
	 * 
	 * @param wkcArray
	 *            Workcenter[]
	 * @param workcenters
	 *            StringBuffer
	 */
	public static void printWorkcenters(Workcenter[] wkcArray) {

		StringBuffer stringBuffer = new StringBuffer();
		String workcenterOutput = "";
		workcenterOutput += "Number of Workcenters \t" + numberOfWorkcenters
				+ "\n";

		for (int i = 0; i < numberOfWorkcenters; i++) {

			workcenterOutput += "WorkCenter " + wkcArray[i].getID() + "\n";

			for (int j = 0; j < wkcArray[i].getNumberOfMachines(); j++) {

				workcenterOutput += "Machine " + j + "\t Available date "
						+ wkcArray[i].getMachine(j).getAvailableDate();
				workcenterOutput += "\t Initial Status "
						+ wkcArray[i].getMachine(j).getStatus() + "\n";
			}

			workcenterOutput += "\n";

		}

		stringBuffer.append(workcenterOutput);
		System.out.print(stringBuffer);
	}

	public void travelTimes(Workcenter[] wkcArray) {
		// /////ORI
		// int maxTravelIndex = -1;
		//
		// //int travelTime=0;
		// int indexFrom = -1;
		// int indexTo = -1;
		// if (indexFrom > maxTravelIndex || indexTo > maxTravelIndex) {
		// if (indexFrom > indexTo)
		// maxTravelIndex = indexFrom;
		// else
		// maxTravelIndex = indexTo;
		// }
		for (int i = -1; i < Workcenter.getNumberOfWorkcenters(); i++) {
			int indexFrom;
			int indexTo;

			// settravelTime();
			// setTravel(0, 1, 3);
		}

	}

	// ,4,7,10},{9,0,5,7,10},{4,5,0,10,6},{7,7,10,0,2},{10,10,6,2,0}};

	// public void readTravelTime(ArrayList<String> informationFiles) throws
	// IOException {
	// BufferedReader reader = null;
	// try {
	// File file = new File(informationFiles.get(0));
	// reader = new BufferedReader(new FileReader(file));
	//
	// String matrixHeightString = reader.readLine();
	// Integer matrixHeight = Integer.parseInt(matrixHeightString);
	//
	// String matrixWidthString = reader.readLine();
	// Integer matrixWidth = Integer.parseInt(matrixWidthString);
	//
	// travelTime = new int[matrixWidth][matrixHeight];
	//
	// for (int i = 0; i < matrixHeight; i++) {
	// String currentLine = reader.readLine();
	// for (int j = 0; j < matrixWidth; j++) {
	//
	// int numerito = currentLine.substring(1,
	// currentLine.length() - 1).indexOf("|") + 1;
	// if (numerito == 0)
	// numerito = currentLine.length() - 1;
	// String currentNumberString = currentLine.substring(1,
	// numerito);
	// int currentNumber = 0;
	// if (!currentNumberString.equals(" ")) {
	// currentNumber = Integer.parseInt(currentNumberString);
	// }
	// travelTime[i][j] = currentNumber;
	//
	// currentLine = currentLine.substring(
	// currentLine.substring(1, currentLine.length() - 1)
	// .indexOf("|") + 1, currentLine.length());
	// }
	// }
	// } catch (IOException e) {
	// throw e;
	// } finally {
	// if(reader!=null)reader.close();
	// }
	// }

}
