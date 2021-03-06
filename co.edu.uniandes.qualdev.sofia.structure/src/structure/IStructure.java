package structure;

import java.util.ArrayList;
import java.util.Collection;

import structure.impl.CriticalPath;

import common.types.BetaVO;
import common.types.OperationIndexVO;
import common.types.PairVO;

/**
 * Interface that represents the definition of the functionality that the Graph
 * must provide
 * 
 * @author Rubby Casallas
 * @author David Mendez-Acuna
 */
public interface IStructure {

	// -------------------------------------------------
	// Neighbor methods
	// -------------------------------------------------

	/**
	 * Exchanges the operations given in the parameters defined by their
	 * positions within the vector
	 * 
	 * @param initialOperationPosition
	 *            Position of the initial operation within the vector
	 * @param finalOperationPosition
	 *            Position of the final operation within the vector
	 * @throws Exception - Method error
	 */
	public void exchangeOperations(int initialOperationPosition,
			int finalOperationPosition) throws Exception;

	/**
	 * Exchanges the operations given in the parameters defined by the operation
	 * indexes
	 * 
	 * @param initialOperationIndex
	 *            Operation index of the initial operation
	 * @param finalOperationIndex
	 *            Operation index of the final operation
	 */
	public void exchangeOperations(OperationIndexVO initialOperationIndex,
			OperationIndexVO finalOperationIndex);
	/**
	 * Reverse the operations between the given operation indexes
	 * @param initialOperationIndex- Operation index of the initial operation
	 * @param finalOperationIndex - Operations index of the final operation
	 */
	public void reverseOperationsBetween(OperationIndexVO initialOperationIndex,
			OperationIndexVO finalOperationIndex);

	/**
	 * Inserts an operation before the given successor. The operations are
	 * identified by their position within the structure
	 * 
	 * @param toInsertOperationPosition
	 *            Operation that must be inserted
	 * @param successorOperationPosition
	 *            Operation that indicates the successor
	 * @throws Exception - Method error
	 */
	public void insertOperationBefore(int toInsertOperationPosition,
			int successorOperationPosition) throws Exception;

	/**
	 * Inserts an operation before the given successor. The operations are
	 * identified by their corresponding operation index
	 * 
	 * @param toInsertOperationIndex
	 *            Operation that must be inserted
	 * @param successorOperationIndex
	 *            Operation that indicates the successor
	 */
	public void insertOperationBefore(OperationIndexVO toInsertOperationIndex,
			OperationIndexVO successorOperationIndex);

	/**
	 * Inserts an operation after the given successor. The operations are
	 * identified by their position within the structure
	 * 
	 * @param toInsertOperationPosition
	 *            Operation that must be inserted
	 * @param successorOperationPosition
	 *            Operation that indicates the successor
	 * @throws Exception - Method error
	 */
	public void insertOperationAfter(int toInsertOperationPosition,
			int successorOperationPosition) throws Exception;
	
	/**
	 * Inserts an operation after the given successor. The operations are
	 * identified by their corresponding operation index
	 * 
	 * @param toInsertOperationIndex
	 *            Operation that must be inserted
	 * @param successorOperationIndex
	 *            Operation that indicates the successor
	 */
	public void insertOperationAfter(OperationIndexVO toInsertOperationIndex,
			OperationIndexVO successorOperationIndex);

	// -------------------------------------------------
	// Queries
	// -------------------------------------------------

	/**
	 * Returns the amount of jobs in the system
	 * 
	 * @return jobs Amount of jobs in the system
	 */
	public int getTotalJobs();

	/**
	 * Returns the amount of stations in the system
	 * 
	 * @return machines Amount of stations in the system
	 */
	public int getTotalStations();
	
	/**
	 * Returns the amount of machines in the system
	 * 
	 * @return machines Amount of machines in the system
	 */
	public int getTotalMachines();
	
	/**
	 * Returns the max amount of machines per station
	 * 
	 * @return max Max amount of machines per station
	 */
	public int getMaxMachinesPerStation();
	
	/**
	 * Returns the operation that refers to the operation index given in the
	 * parameter
	 * 
	 * @param operationIndex Operation index that identifies the operation required
	 * @return operation The operation required
	 */
	public IOperation getOperationByOperationIndex(OperationIndexVO operationIndex);
	
	/**
	 * Returns the operation that is located in the position given in the
	 * parameter
	 * 
	 * @param position Position where the required operation is located
	 * @return operation The operation required
	 * @throws Exception - Method error
	 */
	public IOperation getOperationByPosition(int position) throws Exception;

	/**
	 * Position in the structure where the operation identified by the parameter
	 * is located.
	 * 
	 * @param operationIndex
	 *            Operation index that identifies the operation required
	 * @return position The position where the operation is located within the
	 *         structure
	 */
	public int getPositionByOperationIndex(OperationIndexVO operationIndex);

	/**
	 * Returns an ordered list with the operations of the structure
	 * 
	 * @return operations Ordered list with the operations of the structure
	 */
	public ArrayList<IOperation> getOperations();
	
	/**
	 * Returns a list with the operations occurring in the job and station given
	 * in the parameters.
	 * 
	 * @param jobId The required job id
	 * @param stationId The required station id
	 * @return operations List of operations occurring in the job and station in the parameters
	 * @throws Exception - Method error
	 */
	public ArrayList<IOperation> getOperationsByJobAndStation(int jobId,
			int stationId) throws Exception;
	
	/**
	 * Calculates the C matrix of the current solution. That is calculate the
	 * initial and final time for each node (operation)
	 * @param initialPosition - Initial position of the operation to recalculate part of the matrix
	 * @return CMatrix
	 * @throws Exception - Method error
	 */
	public int[][] calculateCMatrix(int initialPosition) throws Exception;

	/**
	 * Updates the C matrix according to an interchange (swap) given by the pair
	 * in the parameter
	 * <pre> The corresponding swap must be done before calling this method. </pre>
	 * @param pair - last pair to be inserted to the structure
	 * @return the updated C matrix.
	 * @throws Exception - Method error 
	 */
	public int[][] updateCMatrix(PairVO pair) throws Exception;
	
	/**
	 * Calculates and returns a matrix that contains the current
	 * initial time of each operation.
	 * 
	 * @return initialTimes A matrix containing the current initial time
	 * of each operation
	 * @throws Exception - Method error 
	 */
	public int[][] calculateInitialTimesMatrix() throws Exception;
	
	/**
	 * Calculates and returns the A matrix that represents the current solution
	 * 
	 * @return A matrix that represents the current solution
	 */
	public int[][] calculateAMatrix();
	
	/**
	 * Returns the route of a given job
	 * 
	 * @param jobId
	 *            Identifier of the job
	 * @return route. A collection of integers that represents the collection of
	 *         the identifiers of the machines that compose the route of the job
	 * @throws Exception - Method error
	 */
	public Collection<Integer> getJobRoute(int jobId) throws Exception;

	/**
	 * Returns the sequence of a given station
	 * 
	 * @param stationId
	 *            Identifier of the station
	 * @return route. A collection of integers that represents the collection of
	 *         the identifiers of the jobs that compose the sequence of the
	 *         station
	 * @throws Exception - Method error
	 */
	public Collection<Integer> getStationSequence(int stationId) throws Exception;
	
	/**
	 * Returns the problem under study. That is the matrix with the operations
	 * and their corresponding processing times.
	 * 
	 * @return problem
	 */
	public OperationIndexVO[][] getProblem();
	
	/**
	 * Returns the initial time of the operation identified by the operation
	 * index in the parameter.
	 * 
	 * @param operationIndex
	 *            Operation index that identifies the operation
	 * @return initialTime Initial time of the operation.
	 */
	public int geInitialTime(OperationIndexVO operationIndex);

	/**
	 * Returns the final time of the operation identified by the operation index
	 * in the parameter.
	 * 
	 * @param operationIndex
	 *            Operation index that identifies the operation
	 * @return finalTime Final time of the operation.
	 */
	public int geFinalTime(OperationIndexVO operationIndex);
	
	/**
	 * Search for an operation with the same job id, that is scheduled before from the the operation that enter by parameter
	 * @param Cij - Operation that we want to search
	 * @param vectorPos - Position of this operation
	 * @param vector - Array of operations
	 * @return Operation - with same job id as operation in the parameter but was scheduled before or null
	 */
	public IOperation getCiminus1J(IOperation Cij, int vectorPos, ArrayList<IOperation> vector);

	/**
	 * Search for an operation with the same station id, that is scheduled before from the the operation that enter by parameter
	 * @param Cij - Operation that we want to search
	 * @param vectorPos - Position of this operation
	 * @param vector - Array of operations
	 * @return Operation - with same station id as operation in the parameter but was scheduled before or null
	 */
	public IOperation getCiJminus1(IOperation Cij, int vectorPos, ArrayList<IOperation> vector);
	
	/**
	 * Get the operation in the position that enter as parameter
	 * @param pos - position of the query
	 * @return Operation - in the specified position
	 * @throws Exception - Method error
	 */
	public IOperation getPosition(int pos) throws Exception;
	
	/**
	 * Get betas of the problem
	 * @return array with the betas present in the problem
	 */
	public ArrayList<BetaVO> getBetas();
	
	/**
	 * Get the travel time between two station given their indexes
	 * @param initialPosition - index of first station
	 * @param finalPosition - position of the second station
	 * @return int - value with the travel time between the two stations
	 * @throws Exception - Method error
	 */
	public int getTT(int initialPosition, int finalPosition) throws Exception;
	
	/**
	 * Get the travel time between an operation an the position of the predecessor
	 * @param Cij - Operation that we want to find the travel time with the predecessor
	 * @param predecessor - Position of the predecessor in the structure
	 * @param vector - Array of operations
	 * @return int - value with the travel time
	 * @throws Exception - Method error
	 */
	public int getTTBetas(IOperation Cij, int predecessor, ArrayList<IOperation> vector) throws Exception;
	
	/**
	 * Get the travel time between two given operations
	 * @param origin - Origin operation
	 * @param destination - Destination operation
	 * @return int - value of the travel time among the specified operations
	 * @throws Exception - Method error
	 */
	public int getTTBetas(IOperation origin, IOperation destination) throws Exception;
	
	// -------------------------------------------------
	// Manipulation methods
	// -------------------------------------------------

	public void decodeSolution();
	
	/**
	 * Add an operation to the current operation
	 * 
	 * @param operationIndexVO
	 *            Object of the class OperationIndexVO with the information of an
	 *            operation
	 * @return if the operation was scheduled
	 */
	public boolean scheduleOperation(OperationIndexVO operationIndexVO);
	
	/**
	 * Removes the operation defined by the parameter from the current schedule
	 * @param operationIndexVO Operation index that defines the operation to remove
	 */
	public void removeOperationFromSchedule(OperationIndexVO operationIndexVO);
	
	// -------------------------------------------------
	// Utilities
	// -------------------------------------------------

	/**
	 * Returns a copy of the graph
	 * 
	 * @return a copy of the graph
	 */
	public IStructure cloneStructure();

	/**
	 * Deletes all the object references existing in the structure so the
	 * garbage collector clean the objects.
	 */
	public void clean();

	// -------------------------------------------------
	// Getters and setters
	// -------------------------------------------------

	public void setTotalJobs(int totalJobs);

	public void setTotalStations(int totalStations);
	
	public void setTotalMachines(int totalMachines);

	public String getProcessingTimesFile();

	public void setProcessingTimesFile(String processingTimesFile);

	public void setMaxMachinesPerStation(int maxMachinesPerStation);

	public ArrayList<IOperation> getVector() throws Exception;
	
	public ArrayList<CriticalPath> getCriticalPaths() throws Exception;
	
	public ArrayList<int[]> getWeightedNodesCriticaRoute();
	
	public boolean validateStructure();

}