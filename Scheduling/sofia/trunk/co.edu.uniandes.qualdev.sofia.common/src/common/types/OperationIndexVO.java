package common.types;

/**
 * Operation index that contains a reference to a job, a reference to a station,
 * and a reference to a station
 * 
 * @author Rubby Casallas
 * @author David Méndez Acuña - Modificación máquinas en paralelo.
 * 
 */
public class OperationIndexVO {

	// --------------------------------------------
	// Attributes
	// --------------------------------------------

	/** Reference to the job */
	private int jobId;

	/** Reference to the station */
	private int stationId;

	/** Reference to the machine */
	private int machineId;

	/** Initial time of the operation */
	private int initialTime;

	/** Initial time of the operation */
	private int finalTime;

	/** Processing time of the operation */
	private int processingTime;
	
	private String nameStation;
	
	private String nameJob;
	
	private String nameMachine;

	// --------------------------------------------
	// Constructor
	// --------------------------------------------

	/**
	 * Constructor of the class
	 * 
	 * @param job
	 *            Job index
	 * @param station
	 *            Station index
	 */
	public OperationIndexVO(int job, int station) {
		jobId = job;
		stationId = station;
		machineId = 0;
	}

	/**
	 * Constructor of the class
	 * 
	 * @param job
	 *            Job index
	 * @param station
	 *            Station index
	 * @param machine
	 *            Machine index
	 */
	public OperationIndexVO(int job, int station, int machine) {
		jobId = job;
		stationId = station;
		machineId = machine;
	}

	// --------------------------------------------
	// Getters and setters
	// --------------------------------------------

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public int getStationId() {
		return stationId;
	}

	public void setStationId(int stationId) {
		this.stationId = stationId;
	}

	public String toString() {
		return ("<" + jobId + "," + stationId + "," + machineId + ">");
	}

	public int getInitialTime() {
		return initialTime;
	}

	public void setInitialTime(int initialTime) {
		this.initialTime = initialTime;
	}

	public int getFinalTime() {
		return finalTime;
	}

	public void setFinalTime(int finalTime) {
		this.finalTime = finalTime;
	}

	public int getMachineId() {
		return machineId;
	}

	public void setMachineId(int machineId) {
		this.machineId = machineId;
	}

	public int getProcessingTime() {
		return processingTime;
	}

	public void setProcessingTime(int processingTime) {
		this.processingTime = processingTime;
	}

	public boolean equals(Object operationIndex) {
		return jobId == ((OperationIndexVO) operationIndex).jobId
				&& ((OperationIndexVO) operationIndex).stationId == stationId
				&& ((OperationIndexVO) operationIndex).machineId == machineId;
	}

	public String getNameStation() {
		return nameStation;
	}

	public void setNameStation(String nameStation) {
		this.nameStation = nameStation;
	}

	public String getNameJob() {
		return nameJob;
	}

	public void setNameJob(String nameJob) {
		this.nameJob = nameJob;
	}

	public String getNameMachine() {
		return nameMachine;
	}

	public void setNameMachine(String nameMachine) {
		this.nameMachine = nameMachine;
	}
	
	
}