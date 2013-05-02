package afs;

import afs.model.Job;
import afs.model.Workcenter;
import afs.schedule.Schedule;
import afs.schedule.oss.NonDelayOSSchedule;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

/*
 * AFS (Algorithms for Scheduling) - Copyright (C) 2005-2008 Gonzalo Mejía
 *
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
 * Title: Class AFSMain
 * </p>
 * 
 * <p>
 * Description: AFS Main is the main class:<br>
 * The constructor is invoked when the main method is run.
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * 
 * <p>
 * Company: Pylo.Uniandes.edu.co
 * </p>
 * 
 * @author Gonzalo Mejia
 * @version 3.0
 */
public class AFSInitialSolutionCreator {

	// ------------------------------------------------
	// Attributes
	// ------------------------------------------------

	public Job[] jobArray;

	public Workcenter[] wkcArray;

	public Schedule schedule;

	public static AFSInitialSolutionCreator instance;

	// ------------------------------------------------
	// Constructor + Singleton
	// ------------------------------------------------

	/**
	 * Constructor of the class. Initializes the attributes and setup the AFS
	 * for the construction of initial solutions.
	 * 
	 * @throws IOException
	 */
	private AFSInitialSolutionCreator() {

	}

	public static AFSInitialSolutionCreator getInstance() {
		if (instance == null) {
			instance = new AFSInitialSolutionCreator();
		}
		return instance;
	}

	// ------------------------------------------------
	// Methods
	// ------------------------------------------------

	/**
	 * Calls the NonDelaySchedule with SPT rule
	 * 
	 * @param tTFile
	 * @param tFile
	 */
	public Integer[][] createInitialSolution(String ruleName, String TFile,
			String TTFile) {
		NonDelayOSSchedule os = null;
		Job.setNumberOfJobs(0);
		Workcenter.setNumberOfWorkcenters(0);

		try {
			this.readData(TFile, TTFile);
			schedule = new Schedule(wkcArray, jobArray);
			os = new NonDelayOSSchedule(wkcArray, jobArray);
			os.dispatchRuleScheduling(ruleName);
		} catch (Exception e) {
			e.printStackTrace();
		}	

		return os.getTimedSchedule().getRankMatrix();
	}

	// ------------------------------------------------
	// Utilities
	// ------------------------------------------------

	/**
	 * Reads the job and workcenter data from the Lekin files _user.job and
	 * _user.mch. The job and machine data are stored in two arrays:
	 * 
	 * jobArray and wkcArray
	 * @param TTFile 
	 * @param TFile 
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("resource")
	public void readData(String TFile, String TTFile) throws IOException {

		File dataFile = new File(TFile);
		File travelTimesFile = new File(TTFile);

		BufferedReader in = new BufferedReader(new FileReader(travelTimesFile));

		String textLine = in.readLine();
		int numberOfJobs = Integer.parseInt(textLine) - 1;

		textLine = in.readLine();
		int numberOfWorkcenters = Integer.parseInt(textLine) - 1;

		// reads the _user.job
		jobArray = new Job[numberOfJobs];
		wkcArray = new Workcenter[numberOfWorkcenters];

		in = new BufferedReader(new FileReader(travelTimesFile));

		Workcenter.readTravelTimesFile(in, wkcArray, numberOfWorkcenters);

		in = new BufferedReader(new FileReader(dataFile));
		textLine = in.readLine();
		textLine = in.readLine();
		Job.readJobTextFile(in, jobArray, numberOfJobs, numberOfWorkcenters);

		for (int j = 0; j < numberOfJobs; j++) {
			jobArray[j].initWkPosition(numberOfWorkcenters);
			for (int k = 0; k < jobArray[j].getWkcOPosition().length; k++) {

				int wkcNumber = jobArray[j].getRoute(k).getWorkcenterNumber();
				jobArray[j].setWkcPosition(wkcNumber, k);

			}
		}
	}
}