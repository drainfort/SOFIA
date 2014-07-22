package structure.impl;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import common.types.BetaVO;
import common.types.OperationIndexVO;
import common.types.PairVO;

import structure.AbstractStructure;
import structure.IOperation;
import structure.IStructure;

import beta.Beta;
import beta.SetupBeta;
import beta.TTBeta;
import beta.TearDownBeta;
import beta.impl.TearDownTravelTime;

/**
 * Implementation of a graph
 * 
 * @author Rubby Casallas
 * @author David Mendez-Acuna
 * @author Jaime Romero
 */
public class Graph extends AbstractStructure {

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------

	/**
	 * Matrix that contains the nodes of the graph
	 */
	public Node[][] nodes = null;

	/**
	 * Array with the initial nodes for each route
	 */
	public Node[] initialJobNodesArray = null;

	/**
	 * Array with the initial nodes for each sequence
	 */
	public Node[] initialStationNodesArray = null;

	/**
	 * Array of jobs and characteristics
	 */
	private ArrayList<Job> jobs;
	
	/**
	 * Array of machines and characteristics
	 */
	private ArrayList<Machine> machines;
	
	/**
	 * Array of stations and characteristics
	 */
	private ArrayList<Station> stations;

	/**
	 * Array with the occurrences of operations in critical routes
	 */
	private ArrayList<int[]> weightedNodesCriticaRoute;

	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	/**
	 * Constructor of the class
	 * @param totalJobs - number of jobs in the problem
	 * @param totalStations - number of stations in the problem
	 */
	public Graph(int totalJobs, int totalStations) {
		super(totalJobs, totalStations);

		nodes = new Node[totalJobs][totalStations];

		for (int i = 0; i < totalJobs; i++) {
			for (int j = 0; j < totalStations; j++) {
				nodes[i][j] = new Node(new Operation(operationsMatrix[i][j]), this);
			}
		}

		initialJobNodesArray = new Node[totalJobs];
		initialStationNodesArray = new Node[totalStations];

		jobs = new ArrayList<Job>();
		machines = new ArrayList<Machine>();
		stations = new ArrayList<Station>();
	}

	/**
	 * Constructor of the class
	 * @param processingTimesFile - File of the processing time of the problem
	 * @param pBetas - Betas of the problem
	 * @throws Exception - Functionality error
	 */
	public Graph(String processingTimesFile, ArrayList<BetaVO> pBetas) throws Exception {
		super(processingTimesFile, pBetas);

		nodes = new Node[totalJobs][totalStations];

		for (int i = 0; i < totalJobs; i++) {
			for (int j = 0; j < totalStations; j++) {
				nodes[i][j] = new Node(new Operation(operationsMatrix[i][j]), this);
			}
		}

		initialJobNodesArray = new Node[totalJobs];
		initialStationNodesArray = new Node[totalStations];

		jobs = new ArrayList<Job>();
		machines = new ArrayList<Machine>();
		stations = new ArrayList<Station>();
	}

	// -------------------------------------------------
	// Construction auxiliary methods
	// -------------------------------------------------

	/**
	 * Create a route arc between two operations
	 * @param start - Initial operations
	 * @param end - Final operations
	 */
	public void createRouteArc(OperationIndexVO start, OperationIndexVO end) {
		nodes[start.getJobId()][start.getStationId()]
				.setNextRouteNode(nodes[end.getJobId()][end.getStationId()]);
		nodes[end.getJobId()][end.getStationId()]
				.setPreviousRouteNode(nodes[start.getJobId()][start
				                                              .getStationId()]);

	}

	/**
	 * Create a sequence arc between two operations
	 * @param start - Initial operations
	 * @param end - Final operations
	 */
	public void createSequenceArc(OperationIndexVO start, OperationIndexVO end) {
		nodes[start.getJobId()][start.getStationId()]
				.setNextSequenceNode(nodes[end.getJobId()][end.getStationId()]);
		nodes[end.getJobId()][end.getStationId()]
				.setPreviousSequenceNode(nodes[start.getJobId()][start
				                                                 .getStationId()]);
	}

	/**
	 * Get initial node for an specific job
	 * @param job - job id
	 * @return initial node for a particular job
 	 */
	public Node getInitialJobNode(int job) {
		return initialJobNodesArray[job];
	}

	/**
	 * Set initial node for an specific job
	 * @param job - job id
	 * @param initialJobNode - initial node to add.
	 */
	public void setInitialJobNode(int job, Node initialJobNode) {
		assert ((job > -1) && (job < totalJobs));
		this.initialJobNodesArray[job] = initialJobNode;
		initialJobNode.setPreviousRouteNode(null);
	}

	/**
	 * Get initial node for an specific station
	 * @param station - station id
	 * @return initial node for a particular station
 	 */
	public Node getInitialStationNode(int station) {
		return initialStationNodesArray[station];
	}

	/**
	 * Set initial node for an specific station
	 * @param station - station id
	 * @param initialStationNode - initial node to add.
	 */
	public void setInitialStationNode(int station,
			Node initialStationNode) {
		this.initialStationNodesArray[station] = initialStationNode;
		initialStationNode.setPreviousSequenceNode(null);
	}

	/**
	 * Get the node in an specific position of the matrix of nodes
	 * @param job - job id 
	 * @param station - station id
	 * @return node that is found in the specified position of the matrix
	 */
	public Node getNode(int job, int station){
		return nodes[job][station];
	}

	// -------------------------------------------------
	// Neighbor methods
	// -------------------------------------------------

	@Override
	public void exchangeOperations(OperationIndexVO first, OperationIndexVO second) {

		int jobId = first.getJobId();
		int machineId = first.getStationId();

		// If the exchange is in the route. Same job
		if (jobId == second.getJobId()) {
			Collection<Integer> route = getJobRoute(jobId);

			Integer[] routearray = (Integer[]) route.toArray(new Integer[route
			                                                             .size()]);

			// swap
			Integer machine1Index = find(machineId, routearray);
			Integer machine2Index = find(second.getStationId(), routearray);

			Integer machine1 = routearray[machine1Index];
			routearray[machine1Index] = routearray[machine2Index];
			routearray[machine2Index] = machine1;

			route = Arrays.asList(routearray);

			// Rebuild the route for jobId
			Node initialNode = getNode(jobId, routearray[0]);
			this.initialJobNodesArray[jobId] = initialNode;
			initialNode.setPreviousRouteNode(null);

			for (int k = 0; k < (routearray.length - 1); k++) {
				OperationIndexVO start = new OperationIndexVO(0, jobId, routearray[k]);
				OperationIndexVO end = new OperationIndexVO(0, jobId,
						routearray[k + 1]);
				this.createRouteArc(start, end);
			}

			this.getNode(jobId, routearray[routearray.length - 1])
			.setNextRouteNode(null);

		}
		// If the exchange is in the route. Same machine
		else if (machineId == second.getStationId()) {
			Collection<Integer> seq = getStationSequence(machineId);

			Integer[] seqarray = (Integer[]) seq
					.toArray(new Integer[seq.size()]);

			Integer job1Index = find(jobId, seqarray);
			Integer job2Index = find(second.getJobId(), seqarray);
			Integer job1 = seqarray[job1Index];
			seqarray[job1Index] = seqarray[job2Index];
			seqarray[job2Index] = job1;

			seq = Arrays.asList(seqarray);

			Node initialMachineOperation = getNode(seqarray[0], machineId);
			this.initialStationNodesArray[machineId] = initialMachineOperation;
			initialMachineOperation.setPreviousSequenceNode(null);

			for (int k = 0; k < (seqarray.length - 1); k++) {
				OperationIndexVO start = new OperationIndexVO(0, seqarray[k],
						machineId);
				OperationIndexVO end = new OperationIndexVO(0, seqarray[k + 1],
						machineId);
				this.createSequenceArc(start, end);
			}

			this.getNode(seqarray[seqarray.length - 1], machineId)
			.setNextSequenceNode(null);
		}
	}

	@Override
	public void exchangeOperations(int initialOperationPosition,
			int finalOperationPosition) throws Exception{
		throw new Exception("Operation not currently supported");

	}

	/**
	 * Find an id over an array of values
	 * @param id - id that we are searching for
	 * @param routearray - array of values 
	 * @return position where the method finds the id
	 */
	private int find(int id, Integer[] routearray) {
		for (int i = 0; i < routearray.length; i++) {
			if (routearray[i] == id)
				return i;
		}
		return -1;
	}

	@Override
	public void insertOperationBefore(OperationIndexVO toInsertOperationIndex,
			OperationIndexVO successorOperationIndex) {

		int jobId = toInsertOperationIndex.getJobId();
		int machineId = toInsertOperationIndex.getStationId();

		// If the insertion is in the route. Same job
		if (jobId == successorOperationIndex.getJobId()) {
			Vector<Integer> route = (Vector<Integer>) getJobRoute(jobId);

			Integer[] routearray = (Integer[]) route.toArray(new Integer[route
			                                                             .size()]);

			// insertion
			Integer machine1Index = find(machineId, routearray);
			Integer machine2Index = find(
					successorOperationIndex.getStationId(), routearray);

			// if the element to insert is located after the target index
			if (machine1Index > machine2Index) {
				Integer machineToInterchange;
				machineToInterchange = route.get(machine1Index);

				route.insertElementAt(machineToInterchange, machine2Index);
				route.remove(machine1Index + 1);
				routearray = (Integer[]) route
						.toArray(new Integer[route.size()]);
			}
			// if the element to insert is located before the target index
			else if (machine1Index < machine2Index) {
				Integer machineToInterchange;
				machineToInterchange = route.get(machine1Index);
				route.insertElementAt(machineToInterchange, machine2Index);
				route.remove(machine1Index.intValue());
				routearray = (Integer[]) route
						.toArray(new Integer[route.size()]);
			}


			Node initialNode = getNode(jobId, routearray[0]);
			this.initialJobNodesArray[jobId] = initialNode;
			initialNode.setPreviousRouteNode(null);

			for (int k = 0; k < (routearray.length - 1); k++) {
				OperationIndexVO start = new OperationIndexVO(0, jobId, routearray[k]);
				OperationIndexVO end = new OperationIndexVO(0, jobId,
						routearray[k + 1]);
				this.createRouteArc(start, end);
			}

			this.getNode(jobId, routearray[routearray.length - 1])
			.setNextRouteNode(null);

		}
		// If the insertion is in the sequence. Same machine
		else if (machineId == successorOperationIndex.getStationId()) {
			Vector<Integer> seq = (Vector<Integer>) getStationSequence(machineId);

			Integer[] seqarray = (Integer[]) seq
					.toArray(new Integer[seq.size()]);

			// insertion
			Integer job1Index = find(jobId, seqarray);
			Integer job2Index = find(successorOperationIndex.getJobId(),
					seqarray);

			// if the element to insert is located after the target index
			if (job1Index > job2Index) {
				Integer jobToInterchange;

				jobToInterchange = seq.get(job1Index);
				seq.insertElementAt(jobToInterchange, job2Index);
				seq.remove(job1Index + 1);
				seqarray = (Integer[]) seq.toArray(new Integer[seq.size()]);
			}
			// if the element to insert is located before the target index
			else if (job1Index < job2Index) {
				Integer jobToInterchange;

				jobToInterchange = seq.get(job1Index);
				seq.insertElementAt(jobToInterchange, job2Index);
				seq.remove(job1Index.intValue());
				seqarray = (Integer[]) seq.toArray(new Integer[seq.size()]);
			}

			Node initialMachineOperation = getNode(seqarray[0], machineId);
			this.initialStationNodesArray[machineId] = initialMachineOperation;
			initialMachineOperation.setPreviousSequenceNode(null);

			for (int k = 0; k < (seqarray.length - 1); k++) {
				OperationIndexVO start = new OperationIndexVO(0, seqarray[k],
						machineId);
				OperationIndexVO end = new OperationIndexVO(0, seqarray[k + 1],
						machineId);
				this.createSequenceArc(start, end);
			}

			this.getNode(seqarray[seqarray.length - 1], machineId)
			.setNextSequenceNode(null);
		}
	}

	@Override
	public void insertOperationBefore(int toInsertOperationPosition,
			int successorOperationPosition) throws Exception{
		throw new Exception("Operation not currently supported");

	}

	@Override
	public void insertOperationAfter(OperationIndexVO toInsertOperationIndex,
			OperationIndexVO predeccessorOperationIndex) {

		int jobId = toInsertOperationIndex.getJobId();
		int machineId = toInsertOperationIndex.getStationId();

		// If the insertion is in the route. Same job
		if (jobId == predeccessorOperationIndex.getJobId()) {
			Vector<Integer> route = (Vector<Integer>) getJobRoute(jobId);

			Integer[] routearray = (Integer[]) route.toArray(new Integer[route
			                                                             .size()]);

			// insertion
			Integer machine1Index = find(machineId, routearray);
			Integer machine2Index = find(
					predeccessorOperationIndex.getStationId(), routearray);

			// if the element to insert is located after the target index
			if (machine1Index > machine2Index) {
				Integer machineToInterchange;
				machineToInterchange = route.get(machine1Index);

				route.insertElementAt(machineToInterchange, machine2Index + 1);
				route.remove(machine1Index + 1);
				routearray = (Integer[]) route
						.toArray(new Integer[route.size()]);
			}
			// if the element to insert is located before the target index
			else if (machine1Index < machine2Index) {
				Integer machineToInterchange;
				machineToInterchange = route.get(machine1Index);
				route.insertElementAt(machineToInterchange, machine2Index + 1);
				route.remove(machine1Index.intValue());
				routearray = (Integer[]) route
						.toArray(new Integer[route.size()]);
			}

			Node initialNode = getNode(jobId, routearray[0]);
			this.initialJobNodesArray[jobId] = initialNode;
			initialNode.setPreviousRouteNode(null);

			for (int k = 0; k < (routearray.length - 1); k++) {
				OperationIndexVO start = new OperationIndexVO(0, jobId, routearray[k]);
				OperationIndexVO end = new OperationIndexVO(0, jobId,
						routearray[k + 1]);
				this.createRouteArc(start, end);
			}

			this.getNode(jobId, routearray[routearray.length - 1])
			.setNextRouteNode(null);

		}
		// If the insertion is in the sequence. Same machine
		else if (machineId == predeccessorOperationIndex.getStationId()) {
			Vector<Integer> seq = (Vector<Integer>) getStationSequence(machineId);

			Integer[] seqarray = (Integer[]) seq
					.toArray(new Integer[seq.size()]);

			// insertion
			Integer job1Index = find(jobId, seqarray);
			Integer job2Index = find(predeccessorOperationIndex.getJobId(),
					seqarray);

			// if the element to insert is located after the target index
			if (job1Index > job2Index) {
				Integer jobToInterchange;

				jobToInterchange = seq.get(job1Index);
				seq.insertElementAt(jobToInterchange, job2Index + 1);
				seq.remove(job1Index + 1);
				seqarray = (Integer[]) seq.toArray(new Integer[seq.size()]);
			}
			// if the element to insert is located before the target index
			else if (job1Index < job2Index) {
				Integer jobToInterchange;

				jobToInterchange = seq.get(job1Index);
				seq.insertElementAt(jobToInterchange, job2Index + 1);
				seq.remove(job1Index.intValue());
				seqarray = (Integer[]) seq.toArray(new Integer[seq.size()]);
			}

			Node initialStationNode = getNode(seqarray[0], machineId);
			this.initialStationNodesArray[machineId] = initialStationNode;
			initialStationNode.setPreviousSequenceNode(null);

			for (int k = 0; k < (seqarray.length - 1); k++) {
				OperationIndexVO start = new OperationIndexVO(0, seqarray[k],
						machineId);
				OperationIndexVO end = new OperationIndexVO(0, seqarray[k + 1],
						machineId);
				this.createSequenceArc(start, end);
			}

			this.getNode(seqarray[seqarray.length - 1], machineId)
			.setNextSequenceNode(null);
		}
	}

	@Override
	public void insertOperationAfter(int toInsertOperationPosition,
			int successorOperationPosition) throws Exception{
		throw new Exception("Operation not currently supported");
	}

	// -------------------------------------------------
	// Queries
	// -------------------------------------------------

	@Override
	public int getTotalJobs() {
		return this.totalJobs;
	}

	@Override
	public int getTotalStations() {
		return this.totalStations;
	}
	
	@Override
	public int getTotalMachines() {
		return this.totalMachines;
	}
	
	@Override
	public void setTotalMachines(int totalMachines) {
		this.totalMachines = totalMachines;
	}

	@Override
	public int[][] calculateCMatrix(int initialPosition) throws Exception{
		C = new int[totalJobs][totalStations + 1];
		for (int i = 0; i < totalJobs; i++) {
			for (int j = 0; j < totalStations; j++) {
				if (nodes[i][j] != null){
					int currentC = nodes[i][j].calculateC();
					C[i][j] = currentC;
				}
			}
		}
		int[][] newC = applyTearDownBetas();

		if (newC != null)
			C = newC;

		return C;
	}

	@Override
	public int[][] calculateInitialTimesMatrix() throws Exception {
		C = new int[getTotalJobs()][getTotalStations()];
		for (int i = 0; i < totalJobs; i++) {
			for (int j = 0; j < totalStations; j++) {
				if (nodes[i][j] != null)
					C[i][j] = nodes[i][j].calculateInitialTime();
			}
		}
		return C;
	}

	@Override
	public int[][] calculateAMatrix() {
		int[][] A = new int[this.getTotalJobs()][this.totalStations];

		for (int i = 0; i < this.getTotalJobs(); i++) {
			for (int j = 0; j < this.totalStations; j++) {
				if (this.getNode(i, j) != null)
					A[i][j] = this.getNode(i, j).getRank();
				else
					A[i][j] = 0;
			}
		}
		return A;
	}

	@Override
	public IOperation getCiminus1J(IOperation Cij, int vectorPos, ArrayList<IOperation> vector) {
		return nodes[Cij.getOperationIndex().getJobId()][Cij.getOperationIndex().getStationId()].getPreviousRouteNode() != null ? 
				nodes[Cij.getOperationIndex().getJobId()][Cij.getOperationIndex().getStationId()].getPreviousRouteNode().getOperation() : null;
	}

	@Override
	public IOperation getCiJminus1(IOperation Cij, int vectorPos, ArrayList<IOperation> vector) {
		return nodes[Cij.getOperationIndex().getJobId()][Cij.getOperationIndex().getStationId()].getPreviousSequenceNode() != null ? 
				nodes[Cij.getOperationIndex().getJobId()][Cij.getOperationIndex().getStationId()].getPreviousSequenceNode().getOperation() : null;
	}

	@Override
	public int geInitialTime(OperationIndexVO operationIndex) {
		return nodes[operationIndex.getJobId()][operationIndex.getStationId()]
				.getOperation().getInitialTime();
	}

	@Override
	public int geFinalTime(OperationIndexVO operationIndex) {
		return nodes[operationIndex.getJobId()][operationIndex.getStationId()]
				.getOperation().getFinalTime();
	}

	@Override
	public ArrayList<IOperation> getOperations() {
		ArrayList<IOperation> operations = new ArrayList<IOperation>();

		// Put the nodes in an array
		for (int i = 0; i < totalJobs; i++) {
			for (int j = 0; j < totalStations; j++) {
				Operation a = (Operation)nodes[i][j].getOperation().clone();
				operations.add(a);
			}
		}

		// Orders the array by the initial time attribute
		for (int i = operations.size(); i > 0; i--) {
			for (int j = 0; j < i - 1; j++) {
				IOperation p1 = operations.get(j);
				IOperation p2 = (IOperation) operations.get(j + 1);

				// Si es necesario se deben intercambiar p1 y p2
				if ((p1 != null && p2 != null)
						&& (p1.getInitialTime() > p2.getInitialTime())) {
					operations.set(j, p2);
					operations.set(j + 1, p1);
				}
			}
		}

		return operations;
	}

	@Override
	public ArrayList<BetaVO> getBetas() {
		ArrayList<BetaVO> betas = new ArrayList<BetaVO>();
		Iterator<String> it = this.betas.keySet().iterator();
		for (int i = 0; i < this.betas.keySet().size(); i++) {
			String nextKey = (String) it.next();
			String className = this.betas.get(nextKey).clone().getClass()
					.getCanonicalName();
			ArrayList<String> informationFiles = new ArrayList<String>();
			for (int j = 0; j < this.betas.get(nextKey).getInformationFiles()
					.size(); j++) {
				informationFiles.add(this.betas.get(nextKey)
						.getInformationFiles().get(j));
			}
			BetaVO beta = new BetaVO(nextKey, className, informationFiles, this.betas.get(nextKey).isConsidered());
			betas.add(beta);

		}
		return betas;
	}

	@Override
	public int getTTBetas(IOperation Cij, int predecessor, ArrayList<IOperation> vector) throws Exception{
		throw new Exception("Operation not currently supported");
	}

	@Override
	public int getTTBetas(IOperation origin, IOperation destination) throws Exception{
		int sumBetas = 0;
		if(this.betas!=null){
			Iterator<Beta> iterator = betas.values().iterator();
			while (iterator.hasNext()) {
				Beta beta = iterator.next();
				if (beta instanceof TTBeta){
					if(origin==null && destination != null){
						sumBetas += ((TTBeta) beta).getValue(-1, destination.getOperationIndex().getStationId());
					}else if(origin != null && destination == null){
						sumBetas += ((TTBeta) beta).getValue(origin.getOperationIndex().getStationId(), -1);
					}else{
						sumBetas += ((TTBeta) beta).getValue(origin.getOperationIndex().getStationId(), destination.getOperationIndex().getStationId());
					}
				}
			}
		}
		return sumBetas;
	}

	/**
	 * Method that applies the tear down betas
	 * @return new C Matrix - after aplling the tear down beta
	 */
	private int[][] applyTearDownBetas() {
		int[][] newC = null;
		if (this.betas != null) {
			Iterator<Beta> i = betas.values().iterator();
			while (i.hasNext()) {
				Beta beta = i.next();

				if (beta instanceof TearDownBeta) {
					newC = ((TearDownTravelTime) beta).applyBeta(C);
				}

			}
		}
		return newC == null ? C : newC;
	}

	@Override
	public Collection<Integer> getJobRoute(int job) {
		Collection<Integer> route = new Vector<Integer>(totalStations);
		Node node = initialJobNodesArray[job];

		while (node != null) {
			Integer j = new Integer(node.getOperation().getOperationIndex().getStationId());
			route.add(j);
			node = node.getNextRouteNode();
		}
		return route;
	}

	@Override
	public Collection<Integer> getStationSequence(int stationId) {
		Collection<Integer> sequence = new Vector<Integer>(totalJobs);
		Node node = initialStationNodesArray[stationId];
		while (node != null) {
			Integer i = new Integer(node.getOperation().getOperationIndex().getJobId());
			sequence.add(i);
			node = node.getNextSequenceNode();
		}
		return sequence;
	}

	/**
	 * Search for an operation with the specified job and station id
	 * @param job - job id
	 * @param station - station id
	 * @return Operation that satisfies the search parameters
	 */
	private IOperation getOperation(int job, int station) {
		
		for (int i = 0; i < this.totalJobs; i++) {
			for (int j = 0; j < this.totalStations; j++) {
				Node temp = nodes[i][j];
				OperationIndexVO index = temp.getOperation().getOperationIndex();
				if(index.getJobId()==job && index.getStationId()==station)
					return temp.getOperation();
			}
		}
		return null;
	}

	@Override
	public IOperation getPosition(int pos) throws Exception{
		throw new Exception("Operation not currently supported");
	}

	@Override
	public int getTT(int initialPosition, int finalPosition) throws Exception {
		throw new Exception("Operation not currently supported");
	}

	@Override
	public ArrayList<IOperation> getVector() throws Exception {
		throw new Exception("Operation not currently supported");
	}

	@Override
	public int getMaxMachinesPerStation() {
		return 0;
	}

	@Override
	public IOperation getOperationByOperationIndex(
			OperationIndexVO operationIndex) {
		
		for (int i = 0; i < this.totalJobs; i++) {
			for (int j = 0; j < this.totalStations; j++) {
				Node temp = nodes[i][j];
				OperationIndexVO index = temp.getOperation().getOperationIndex();
				if(index.getJobId()==operationIndex.getJobId() && index.getStationId()==operationIndex.getStationId())
					return temp.getOperation();
			}
		}
		return null;
	}

	@Override
	public IOperation getOperationByPosition(int position) throws Exception {
		throw new Exception("Operation not currently supported");
	}

	@Override
	public int getPositionByOperationIndex(OperationIndexVO operationIndex) {
		return 0;
	}

	@Override
	public ArrayList<IOperation> getOperationsByJobAndStation(int jobId,
			int stationId) throws Exception {
		throw new Exception("Operation not currently supported");
	}

	@Override
	public int[][] updateCMatrix(PairVO pair) throws Exception{
		throw new Exception("Operation not currently supported");
	}

	// -------------------------------------------------
	// Manipulation methods
	// -------------------------------------------------

	@Override
	public boolean scheduleOperation(OperationIndexVO operationIndex) {
		Vector<Integer> route = (Vector<Integer>) this.getJobRoute(operationIndex.getJobId());
		Vector<Integer> sequence = (Vector<Integer>) this.getStationSequence(operationIndex.getStationId());

		Node graphNode = this.getNode(operationIndex.getJobId(), operationIndex.getStationId());

		//Scheduling the route
		if(route.size() == 0){
			initialJobNodesArray[operationIndex.getJobId()] = graphNode;
		}else{
			OperationIndexVO start = new OperationIndexVO(0,operationIndex.getJobId(), route.get(route.size()-1));
			this.createRouteArc(start, operationIndex); 
		}

		//Scheduling the sequence
		if(sequence.size() == 0){
			initialStationNodesArray[operationIndex.getStationId()] = graphNode;
		}else{
			OperationIndexVO start = new OperationIndexVO(0,sequence.get(sequence.size()-1), operationIndex.getStationId());
			this.createSequenceArc(start, operationIndex); 
		}
		
		return true;
	}

	@Override
	public void removeOperationFromSchedule(OperationIndexVO operationIndex) {
		Node graphNode = this.getNode(operationIndex.getJobId(), operationIndex.getStationId());

		//Unscheduling the route
		if(graphNode.getPreviousRouteNode() == null && initialJobNodesArray[operationIndex.getJobId()] == graphNode){
			initialJobNodesArray[operationIndex.getJobId()] = null;
		}else{
			graphNode.getPreviousRouteNode().setNextRouteNode(null);
			graphNode.setPreviousRouteNode(null);
		}

		//Unscheduling the sequence
		if(graphNode.getPreviousSequenceNode() == null && initialStationNodesArray[operationIndex.getStationId()] == graphNode){
			initialStationNodesArray[operationIndex.getStationId()] = null;
		}else{
			graphNode.getPreviousSequenceNode().setNextSequenceNode(null);
			graphNode.setPreviousSequenceNode(null);
		}
	}

	// -------------------------------------------------
	// Utilities
	// -------------------------------------------------

	/**
	 * Returns a copy of the graph
	 * 
	 * @return a copy of the graph
	 */
	@Override
	public IStructure cloneStructure() {

		Graph newGraph = new Graph(this.totalJobs, this.totalStations);
		newGraph.totalJobs = this.getTotalJobs();
		newGraph.totalStations = this.getTotalStations();
		newGraph.nodes = new Node[newGraph.totalJobs][newGraph.totalStations];
		newGraph.operationsMatrix = new OperationIndexVO[newGraph.totalJobs][newGraph.totalStations];

		newGraph.initialJobNodesArray = new Node[newGraph.totalJobs];
		newGraph.initialStationNodesArray = new Node[newGraph.totalStations];
		newGraph.jobs= this.jobs;
		newGraph.machines= this.machines;
		newGraph.stations= this.stations;

		// Clone betas
		if (this.betas != null) {
			newGraph.betas = new HashMap<String, Beta>();
			Iterator<String> it = this.betas.keySet().iterator();
			for (int i = 0; i < this.betas.keySet().size(); i++) {
				String nextKey = (String) it.next();
				newGraph.betas.put(nextKey, this.betas.get(nextKey).clone());
			}
		}
		// Clone Operations
		for (int i = 0; i < newGraph.totalJobs; i++) {
			for (int j = 0; j < newGraph.totalStations; j++) {
				IOperation op = this.getOperation(i, j);
				if (op != null)
					newGraph.operationsMatrix[i][j] = this.operationsMatrix[i][j];
				newGraph.nodes[i][j] = new Node(new Operation(this.operationsMatrix[i][j]), this);
			}
		}

		// Clone Next Route and Next Sequence
		// Clone Previous Route and Previous Sequence
		for (int i = 0; i < newGraph.totalJobs; i++) {
			for (int j = 0; j < newGraph.totalStations; j++) {
				if (this.getNode(i, j) != null) {
					int ni, nj;
					if (this.getNode(i, j).getNextRouteNode() != null) {
						ni = this.getNode(i, j).getNextRouteNode().getOperation().getOperationIndex().getJobId();
						nj = this.getNode(i, j).getNextRouteNode().getOperation().getOperationIndex().getStationId();

						newGraph.createRouteArc(new OperationIndexVO(0, i, j), new OperationIndexVO(0, ni, nj));
					}

					if (this.getNode(i, j).getNextSequenceNode() != null) {
						ni = this.getNode(i, j).getNextSequenceNode().getOperation().getOperationIndex().getJobId();
						nj = this.getNode(i, j).getNextSequenceNode().getOperation().getOperationIndex().getStationId();

						newGraph.createSequenceArc(new OperationIndexVO(0, i, j), new OperationIndexVO(0, ni, nj));
					}
				}
			}
		}

		for (int i = 0; i < newGraph.totalJobs; i++) {
			if (this.initialJobNodesArray[i] != null) {
				int job = this.initialJobNodesArray[i].getOperation().getOperationIndex().getJobId();
				int machine = this.initialJobNodesArray[i].getOperation().getOperationIndex().getStationId();

				newGraph.setInitialJobNode(i,newGraph.getNode(job, machine));
			}
		}

		for (int j = 0; j < newGraph.totalStations; j++) {
			if (this.initialStationNodesArray[j] != null) {
				int job = this.initialStationNodesArray[j].getOperation().getOperationIndex().getJobId();
				int machine = this.initialStationNodesArray[j].getOperation().getOperationIndex().getStationId();
				newGraph.setInitialStationNode(j, newGraph.getNode(job, machine));
			}
		}
		newGraph.processingTimesFile = this.processingTimesFile;
		return newGraph;
	}

	@Override
	public void clean() {

		for (int i = 0; i < this.totalJobs; i++) {
			for (int j = 0; j < this.totalStations; j++) {
				operationsMatrix[i][j] = null;
				nodes[i][j] = null;
			}
		}
		for (int i = 0; i < this.totalJobs; i++) {
			initialJobNodesArray[i] = null;
		}
		for (int j = 0; j < this.totalStations; j++) {
			initialStationNodesArray[j] = null;
		}
		
		weightedNodesCriticaRoute = null;
	}

	public String toString() {
		String matrix = "";
		int numMachines = operationsMatrix.length;
		int numJobs = operationsMatrix[0].length;
		for (int i = 0; i < numMachines; i++) {

			for (int j = 0; j < numJobs; j++) {
				if (operationsMatrix[i][j] != null) {
					matrix += " | [" + operationsMatrix[i][j].getJobId() + ","
							+ operationsMatrix[i][j].getStationId() + "]"
							+ operationsMatrix[i][j].getProcessingTime();
				} else {
					matrix += " | [ " + " ]";
				}
			}
			matrix += " |\n";
		}
		return matrix;
	}

	// -------------------------------------------------
	// Getters and setters
	// -------------------------------------------------

	@Override
	public String getProcessingTimesFile() {
		return processingTimesFile;
	}

	@Override
	public void setProcessingTimesFile(String processingTimesFile) {
		this.processingTimesFile = processingTimesFile;
	}

	@Override
	public void setTotalJobs(int totalJobs) {
		this.totalJobs = totalJobs;

	}

	@Override
	public void setTotalStations(int totalStations) {
		this.totalStations = totalStations;

	}

	@Override
	public void setMaxMachinesPerStation(int maxMachinesPerStation) {
		// TODO setMaxMachinesPerStation on Graph
	}

	public ArrayList<Job> getJobs() {
		return jobs;
	}

	public void setJobs(ArrayList<Job> jobs) {
		this.jobs = jobs;
	}

	public ArrayList<Machine> getMachines() {
		return machines;
	}

	public void setMachines(ArrayList<Machine> machines) {
		this.machines = machines;
	}

	public ArrayList<Station> getStations() {
		return stations;
	}

	public void setStations(ArrayList<Station> stations) {
		this.stations = stations;
	}

	public ArrayList<int[]> getWeightedNodesCriticaRoute() {
		return weightedNodesCriticaRoute;
	}

	public void setWeightedNodesCriticaRoute(
			ArrayList<int[]> weightedNodesCriticaRoute) {
		this.weightedNodesCriticaRoute = weightedNodesCriticaRoute;
	}

	public int getSetupBetas(int i, int j) {
		int sumBetas = 0;
		if(this.betas!=null){
			Iterator<Beta> iterator = betas.values().iterator();
			while (iterator.hasNext()) {
				Beta beta = iterator.next();
				if (beta instanceof SetupBeta){
					sumBetas += ((SetupBeta) beta).getValue(i,j);
				}
			}
		}
		return sumBetas;
	}
	
	@Override
	public boolean validateStructure() {
		try {
			this.restartC();
			this.topologicalSort2();
			return true;
		} catch (Exception e) {
			//e.printStackTrace();
			return false;
		}
	}

	public void restartC(){
		C=null;
		for (int i = 0; i < totalJobs; i++) {
			for (int j = 0; j < totalStations; j++) {
				if (nodes[i][j] != null){
					nodes[i][j].restartC();
				}
			}
		}
	}

	@Override
	public void decodeSolution() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reverseOperationsBetween(
			OperationIndexVO initialOperationIndex,
			OperationIndexVO finalOperationIndex) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Calculates the longest paths of the graph.
	 * @throws Exception - Functionality error
	 */
	public ArrayList<CriticalPath> getCriticalPaths() throws Exception{
		int cmax = 0;
		restartC();
		//clean();
		ArrayList<int[]> incidenciasCriticaRoute = new ArrayList<int[]>();
		ArrayList<Node> cMaxNodes= new ArrayList<Node>();
		for (int i = 0; i < totalJobs; i++) {
			for (int j = 0; j < totalStations; j++) {
				if (nodes[i][j] != null){
					int[] vector = new int[3];
					vector[0] = i;
					vector[1] = j;
					vector[2] = 0;
					incidenciasCriticaRoute.add(vector);
					int c = nodes[i][j].calculateC();
					if (c>cmax){
						cmax=c;
					}	
				}
			}
		}
		for (int i = 0; i < totalJobs; i++) {
			for (int j = 0; j < totalStations; j++) {
				if (nodes[i][j] != null){
					int c = nodes[i][j].calculateC();
					if (c==cmax){
						cMaxNodes.add(nodes[i][j]);
					}	
				}
			}
		}

		ArrayList<CriticalPath> totalRoutes = new ArrayList<CriticalPath>();

		for(int i=0; i< cMaxNodes.size();i++){
			ArrayList<CriticalPath> criticalRoutes = new ArrayList<CriticalPath>();
			CriticalPath firstRoute = new CriticalPath();
			criticalRoutes.add(firstRoute);
			cMaxNodes.get(i).getCriticalRoutes(criticalRoutes, incidenciasCriticaRoute );
			totalRoutes.addAll(criticalRoutes);
		}

		Collections.sort(incidenciasCriticaRoute, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				int a = o1[2];
				int b = o2[2];
				return (a>b ? -1 : (a==b ? 0 : 1));

			}
		});

		weightedNodesCriticaRoute = incidenciasCriticaRoute;
		return totalRoutes;
	}

	

	/**
	 * Calculates the topological sort in the graph, also we check presence of cycles in it.
	 * @return array of operations ordered by this sort.
	 * @throws Exception - Precence of cycles.
	 */
	public ArrayList<IOperation> topologicalSort2()  throws Exception{
		ArrayList<IOperation> l = new ArrayList<IOperation>();
		ArrayList<Node> s = new ArrayList<Node>();
		int counter = 1;

		for (int i = 0; i < totalJobs; i++) {
			for (int j = 0; j < totalStations; j++) {
				if (nodes[i][j] != null){
					if (nodes[i][j].getPreviousRouteNode()==null && nodes[i][j].getPreviousSequenceNode()==null){
						s.add(nodes[i][j]);
					}	
				}
			}
		}

		while(s.size()>0)
		{
			if(counter>totalJobs*totalStations)
				throw new Exception("Prensence of cycles");

			Node temp = s.get(0);
			temp.setPositionSort(counter);

			Node nextRoute = temp.getNextRouteNode();
			Node nextSequence = temp.getNextSequenceNode();
			s.remove(0);
			l.add(temp.getOperation());
			if(nextRoute !=null){
				Node previosRoute = nextRoute.getPreviousRouteNode();
				Node previousSequence = nextRoute.getPreviousSequenceNode();
				boolean condition1=false;
				boolean condition2 =false;

				if(previosRoute==null){
					condition1=true;
				}
				else{
					if(l.contains(previosRoute.getOperation()))
						condition1=true;
				}

				if(previousSequence==null){
					condition2=true;
				}
				else{
					if(l.contains(previousSequence.getOperation()))
						condition2=true;
				}
				if(condition1 && condition2)
					s.add(nextRoute);

			}

			if(nextSequence !=null){
				Node previosRoute = nextSequence.getPreviousRouteNode();
				Node previousSequence = nextSequence.getPreviousSequenceNode();
				boolean condition1=false;
				boolean condition2 =false;

				if(previosRoute==null){
					condition1=true;
				}
				else{
					if(l.contains(previosRoute.getOperation()))
						condition1=true;
				}

				if(previousSequence==null){
					condition2=true;
				}
				else{
					if(l.contains(previousSequence.getOperation()))
						condition2=true;
				}
				if(condition1 && condition2)
					s.add(nextSequence);
			}

			counter++;
		}

		if(l.size() !=totalJobs*totalStations-getNumberNullNodes())
			throw new Exception("Prensence of cycles");
		return l;
	}
	
	// -------------------------------------------------
	// Methods to drawGraphs
	// -------------------------------------------------
	public void drawGraph(String resultsFile, boolean critical, PairVO pair){
		FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(resultsFile);
            System.out.println(resultsFile);
            pw = new PrintWriter(fichero);
            
            pw.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">");
            pw.println("<html lang=\"en\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
            pw.println("<title> Graph - </title> <link rel=\"stylesheet\" href=\"./style/style.css\" type=\"text/css\">");
		    pw.println("</head> <body> <canvas id=\"viewport\" width=\"1000\" height=\"1000\"></canvas>");
		    pw.println("  <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js\"></script>");
		    pw.println("  <script src=\"./js/arbor.js\"></script>  ");
		    pw.println("  <script type=\"text/javascript\" language=\"JavaScript\">");
		    pw.println("  	(function($){");
		    pw.println("  var Renderer = function(canvas){");
		    pw.println("    var canvas = $(canvas).get(0)");
		    pw.println("    var ctx = canvas.getContext(\"2d\");");
		    pw.println("    var particleSystem");
		    pw.println("    var that = {");
		    pw.println("      init:function(system){");
		    pw.println("        particleSystem = system");
		    pw.println("        particleSystem.screenSize(canvas.width-100, canvas.height-100)");
		    pw.println("        particleSystem.screenPadding(40)},");
		    pw.println("      redraw:function(){");
		    pw.println("        ctx.fillStyle = \"white\"");
		    pw.println("        ctx.fillRect(0,0, canvas.width, canvas.height)");
		    pw.println("        particleSystem.eachEdge(function(edge, pt1, pt2){");
		    pw.println("          ctx.strokeStyle = (edge.data.route) ? 'green' :'blue'");
		    pw.println("          ctx.lineWidth = 1");
		    pw.println("          ctx.beginPath()");

		    pw.println(" var headlen = 10; ");
			pw.println("  toy = pt2.y");
			pw.println("  tox = pt2.x");
			pw.println("  fromy = pt1.y");
			pw.println("  fromx = pt1.x");
			pw.println("  var angle = Math.atan2(toy-fromy,tox-fromx);");
			pw.println("  ctx.moveTo(fromx, fromy);");
			pw.println("  ctx.lineTo(tox, toy);");
			pw.println("  ctx.lineTo(tox-headlen*Math.cos(angle-Math.PI/6),toy-headlen*Math.sin(angle-Math.PI/6));");
			pw.println("  ctx.moveTo(tox, toy);");
			pw.println("  ctx.lineTo(tox-headlen*Math.cos(angle+Math.PI/6),toy-headlen*Math.sin(angle+Math.PI/6));");
		    pw.println("          ctx.stroke()})");
		    pw.println("        particleSystem.eachNode(function(node, pt){");
		    pw.println("          var w = 50");
		    pw.println("          ctx.fillStyle = (node.data.critical) ? \"#22F9FF\" : (node.data.change) ? \"#22F9FF\": \"white\"");
		    pw.println("ctx.globalAlpha=0.2;");
		    pw.println("          ctx.fillRect(pt.x-w/2, pt.y-w/2, w,w)");
		    pw.println("		  if(node.data.change){");
		    pw.println("			ctx.fillStyle = \"black\""); 
		    pw.println("			ctx.fillRect(pt.x-w/2+w/8, pt.y-w/2+w/8, 6*w/8,6*w/8)}");
		    pw.println("		  label = node.data.label || \"\"");
		    pw.println("		  if (label){");
		    pw.println("ctx.globalAlpha=1;");
		    pw.println("            ctx.font = \"bold 11px Arial\"");
		    pw.println("            ctx.textAlign = \"center\"");
		    pw.println("            ctx.fillStyle = \"black\"");
		    pw.println("            ctx.fillText(label||\"\", pt.x, pt.y+4)}})  },      }");
		    pw.println("    return that}    ");
		    pw.println("  $(document).ready(function(){");
		    pw.println("    var sys = arbor.ParticleSystem(2600, 412, 0.5) ");
		    pw.println("    sys.parameters({gravity:false}) ");
		    pw.println("    sys.renderer = Renderer(\"#viewport\")"); 
		    ArrayList<IOperation> operationsCritical= new ArrayList<IOperation>();
		    if(critical){
		    	ArrayList<CriticalPath> paths = getCriticalPaths();
		    	System.out.println(paths);
		    	for(int i = 0; i < paths.size() ;i++){
		    		operationsCritical.addAll(paths.get(i).getRoute());
		    	}
		    	System.out.println(operationsCritical);

		    }
		    	
		    //Iterar sobre el grafo
		    ArrayList<IOperation> l = new ArrayList<IOperation>();
			ArrayList<Node> s = new ArrayList<Node>();

			for (int i = 0; i < totalJobs; i++) {
				for (int j = 0; j < totalStations; j++) {
					if (nodes[i][j] != null){
						if (nodes[i][j].getPreviousRouteNode()==null && nodes[i][j].getPreviousSequenceNode()==null){
							s.add(nodes[i][j]);
							String nameA = nodes[i][j].getOperation().toString();
							if(critical){
								if(operationsCritical.contains(nodes[i][j].getOperation()))
									pw.println("    sys.addNode('"+nameA+"', {label:'"+nameA+"', mass:2, critical:true})");
								else
									pw.println("    sys.addNode('"+nameA+"', {label:'"+nameA+"', mass:2, critical:false})");
							}
							else
								pw.println("    sys.addNode('"+nameA+"', {label:'"+nameA+"', mass:2, critical:false})");
						}	
					}
				}
			}
		    
			while(s.size()>0)
			{

				Node temp = s.get(0);
				String nameA = temp.getOperation().toString();
							
				Node nextRoute = temp.getNextRouteNode();
				Node nextSequence = temp.getNextSequenceNode();
				s.remove(0);
				l.add(temp.getOperation());
				if(nextRoute !=null){
					String nameB = nextRoute.getOperation().toString();
					boolean impreso= false;
					if(pair!=null){
						if(pair.getoX().equals(nextRoute.getOperation().getOperationIndex())||pair.getoY().equals(nextRoute.getOperation().getOperationIndex())){
							impreso =true;
							pw.println("    sys.addNode('"+nameB+"', {label:'"+nameB+"', mass:2, change:true})");
						}
					}
					if(critical && !impreso){
						if(operationsCritical.contains(nextRoute.getOperation())){
							impreso =true;
							pw.println("    sys.addNode('"+nameB+"', {label:'"+nameB+"', mass:2, critical:true})");
						}
						
					}
					if(!impreso)
						pw.println("    sys.addNode('"+nameB+"', {label:'"+nameB+"', mass:2})");
					
					pw.println("	sys.addEdge('"+nameA+"','"+nameB+"',{route:true})");
					
					Node previosRoute = nextRoute.getPreviousRouteNode();
					Node previousSequence = nextRoute.getPreviousSequenceNode();
					boolean condition1=false;
					boolean condition2 =false;

					if(previosRoute==null){
						condition1=true;
					}
					else{
						if(l.contains(previosRoute.getOperation()))
							condition1=true;
					}

					if(previousSequence==null){
						condition2=true;
					}
					else{
						if(l.contains(previousSequence.getOperation()))
							condition2=true;
					}
					if(condition1 && condition2)
						s.add(nextRoute);
						
				}

				if(nextSequence !=null){
					
					String nameC = nextSequence.getOperation().toString();
					boolean impreso= false;
					if(pair!=null){
						if(pair.getoX().equals(nextSequence.getOperation().getOperationIndex())||pair.getoY().equals(nextSequence.getOperation().getOperationIndex())){
							impreso =true;
							pw.println("    sys.addNode('"+nameC+"', {label:'"+nameC+"', mass:2, change:true})");
						}
					}
					if(critical && !impreso){
						if(operationsCritical.contains(nextSequence.getOperation())){
							impreso =true;
							pw.println("    sys.addNode('"+nameC+"', {label:'"+nameC+"', mass:2, critical:true})");
						}
						
					}
					if(!impreso)
						pw.println("    sys.addNode('"+nameC+"', {label:'"+nameC+"', mass:2})");
					pw.println("	sys.addEdge('"+nameA+"','"+nameC+"')");
					Node previosRoute = nextSequence.getPreviousRouteNode();
					Node previousSequence = nextSequence.getPreviousSequenceNode();
					boolean condition1=false;
					boolean condition2 =false;

					if(previosRoute==null){
						condition1=true;
					}
					else{
						if(l.contains(previosRoute.getOperation()))
							condition1=true;
					}

					if(previousSequence==null){
						condition2=true;
					}
					else{
						if(l.contains(previousSequence.getOperation()))
							condition2=true;
					}
					if(condition1 && condition2)
						s.add(nextSequence);
				}

			}
		    //Iterar sobre el grafo
		    pw.println("})})");
		    pw.println(		"(this.jQuery)");
		    pw.println("</script></body></html>");
            

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
	}
	
	public void drawGraph2(String resultsFile, boolean critical, PairVO pair){
		FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(resultsFile);
            System.out.println(resultsFile);
            pw = new PrintWriter(fichero);
            
            pw.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">");
            pw.println("<html lang=\"en\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
            pw.println("<title> Graph - </title> <link rel=\"stylesheet\" href=\"./style/style.css\" type=\"text/css\">");
		    int width = (getTotalJobs()+1)*110;
            pw.println("</head> <body> <canvas id=\"viewport\" width=\""+width+"\" height=\""+width+"\"></canvas>");
		    pw.println("  <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js\"></script>");
		    pw.println("  <script src=\"./js/arbor.js\"></script>  ");
		    pw.println("  <script type=\"text/javascript\" language=\"JavaScript\">");
		    pw.println("  	(function($){");
		    pw.println(" var addNode = function(x1,y1,critical,change, label){");
		    pw.println("	var ctx = document.getElementById(\"viewport\").getContext( \"2d\");");
		    pw.println("	var ancho = 50");
		    pw.println("	var entre = 10");
		    pw.println("	var x = 100*x1+ancho+entre*x1");
		    pw.println("	var y = 100*y1+ancho+entre*y1");
		    pw.println("	var w = 50");
		    pw.println("      ctx.fillStyle = (critical) ? \"#22F9FF\" : (change) ? \"#22F9FF\": \"white\"");
		    pw.println("	  ctx.globalAlpha=0.2;");
		    pw.println("      ctx.fillRect(x-w/2, y-w/2, w,w)");
		    pw.println("	  if(change){");
		    pw.println("		ctx.fillStyle = \"black\" ");
		    pw.println("		ctx.fillRect(x-w/2+w/8, y-w/2+w/8, 6*w/8,6*w/8)");
		    pw.println("	 }");
		    pw.println("	  if (label){");
		    pw.println("		ctx.globalAlpha=1;");
		    pw.println("        ctx.font = \"bold 11px Arial\"");
		    pw.println("        ctx.textAlign = \"center\"");
		    pw.println("        ctx.fillStyle = \"black\"");
		    pw.println("        ctx.fillText(label||\"\", x, y+4)");
		    pw.println("      }");
		    pw.println("};");
		    pw.println("var addEdge = function (x1,y1,x2,y2,route){");
		    pw.println("	var ctx = document.getElementById(\"viewport\").getContext(\"2d\");");
		    pw.println("	var ancho = 50");
		    pw.println("	var entre = 10");
		    pw.println("	var fromx = 100*x1+ancho+entre*x1");
		    pw.println("	var fromy = 100*y1+ancho+entre*y1");
		    pw.println("	var tox = 100*x2+ancho+entre*x2");
		    pw.println("	var toy = 100*y2+ancho+entre*y2");
		    pw.println("	ctx.strokeStyle = route ? 'green' :'blue'");
		    pw.println("	ctx.globalAlpha=0.4;");
		    pw.println("    ctx.lineWidth = 2");
		    pw.println("    ctx.beginPath()");
		    pw.println("	var headlen = 15; ");
		    pw.println("	var angle = Math.atan2(toy-fromy,tox-fromx);");
		    pw.println("	ctx.moveTo(fromx, fromy);");
		    pw.println("	ctx.lineTo(tox, toy);");
		    pw.println("	ctx.lineTo(tox-headlen*Math.cos(angle-Math.PI/6),toy-headlen*Math.sin(angle-Math.PI/6));");
		    pw.println("	ctx.moveTo(tox, toy);");
		    pw.println("	ctx.lineTo(tox-headlen*Math.cos(angle+Math.PI/6),toy-headlen*Math.sin(angle+Math.PI/6));");
		    pw.println("   ctx.stroke()");
		    pw.println("	ctx.globalAlpha=1;");
		    pw.println("};	");
		    pw.println("$(document).ready(function(){");

		    ArrayList<IOperation> operationsCritical= new ArrayList<IOperation>();
		    if(critical){
		    	ArrayList<CriticalPath> paths = getCriticalPaths();
		    	System.out.println(paths);
		    	for(int i = 0; i < paths.size() ;i++){
		    		operationsCritical.addAll(paths.get(i).getRoute());
		    	}
		    }
		    	
		    //Iterar sobre el grafo
		    ArrayList<IOperation> l = new ArrayList<IOperation>();
			ArrayList<Node> s = new ArrayList<Node>();

			for (int i = 0; i < totalJobs; i++) {
				for (int j = 0; j < totalStations; j++) {
					if (nodes[i][j] != null){
						if (nodes[i][j].getPreviousRouteNode()==null && nodes[i][j].getPreviousSequenceNode()==null){
							s.add(nodes[i][j]);
							String nameA = nodes[i][j].getOperation().toString();
							int jobId= nodes[i][j].getOperation().getOperationIndex().getJobId();
							int machineId = nodes[i][j].getOperation().getOperationIndex().getStationId();
							boolean impreso= false;
							if(pair!=null){
								if(pair.getoX().equals(nodes[i][j].getOperation().getOperationIndex())||pair.getoY().equals(nodes[i][j].getOperation().getOperationIndex())){
									impreso =true;
									pw.println("addNode ("+machineId+","+jobId+",false,true,'"+nameA+"')");
								}
							}
							if(critical && !impreso){
								if(operationsCritical.contains(nodes[i][j].getOperation())){
									impreso =true;
									pw.println("addNode ("+machineId+","+jobId+",true,false,'"+nameA+"')");
								}
								
							}
							if(!impreso)
								pw.println("addNode ("+machineId+","+jobId+",false,false,'"+nameA+"')");
							
							
						}	
					}
				}
			}
		    
			while(s.size()>0)
			{

				Node temp = s.get(0);
				String nameA = temp.getOperation().toString();
				int jobIdA= temp.getOperation().getOperationIndex().getJobId();
				int machineIdA = temp.getOperation().getOperationIndex().getStationId();
							
				Node nextRoute = temp.getNextRouteNode();
				Node nextSequence = temp.getNextSequenceNode();
				s.remove(0);
				l.add(temp.getOperation());
				if(nextRoute !=null){
					String nameB = nextRoute.getOperation().toString();
					int jobId= nextRoute.getOperation().getOperationIndex().getJobId();
					int machineId = nextRoute.getOperation().getOperationIndex().getStationId();
					boolean impreso= false;
					if(pair!=null){
						if(pair.getoX().equals(nextRoute.getOperation().getOperationIndex())||pair.getoY().equals(nextRoute.getOperation().getOperationIndex())){
							impreso =true;
							pw.println("addNode ("+machineId+","+jobId+",false,true,'"+nameB+"')");
						}
					}
					if(critical && !impreso){
						if(operationsCritical.contains(nextRoute.getOperation())){
							impreso =true;
							pw.println("addNode ("+machineId+","+jobId+",true,false,'"+nameB+"')");
						}
						
					}
					if(!impreso)
						pw.println("addNode ("+machineId+","+jobId+",false,false,'"+nameB+"')");
					
					pw.println("addEdge("+machineIdA+","+jobIdA+","+machineId+","+jobId+",true)  ");
					
					Node previosRoute = nextRoute.getPreviousRouteNode();
					Node previousSequence = nextRoute.getPreviousSequenceNode();
					boolean condition1=false;
					boolean condition2 =false;

					if(previosRoute==null){
						condition1=true;
					}
					else{
						if(l.contains(previosRoute.getOperation()))
							condition1=true;
					}

					if(previousSequence==null){
						condition2=true;
					}
					else{
						if(l.contains(previousSequence.getOperation()))
							condition2=true;
					}
					if(condition1 && condition2)
						s.add(nextRoute);
						
				}

				if(nextSequence !=null){
					
					String nameC = nextSequence.getOperation().toString();
					int jobId= nextSequence.getOperation().getOperationIndex().getJobId();
					int machineId = nextSequence.getOperation().getOperationIndex().getStationId();
					boolean impreso= false;
					if(pair!=null){
						if(pair.getoX().equals(nextSequence.getOperation().getOperationIndex())||pair.getoY().equals(nextSequence.getOperation().getOperationIndex())){
							impreso =true;
							pw.println("addNode ("+machineId+","+jobId+",false,true,'"+nameC+"')");
						}
					}
					if(critical && !impreso){
						if(operationsCritical.contains(nextSequence.getOperation())){
							impreso =true;
							pw.println("addNode ("+machineId+","+jobId+",true,false,'"+nameC+"')");
						}
						
					}
					if(!impreso)
						pw.println("addNode ("+machineId+","+jobId+",false,false,'"+nameC+"')");
					pw.println("addEdge("+machineIdA+","+jobIdA+","+machineId+","+jobId+",false)  ");
					Node previosRoute = nextSequence.getPreviousRouteNode();
					Node previousSequence = nextSequence.getPreviousSequenceNode();
					boolean condition1=false;
					boolean condition2 =false;

					if(previosRoute==null){
						condition1=true;
					}
					else{
						if(l.contains(previosRoute.getOperation()))
							condition1=true;
					}

					if(previousSequence==null){
						condition2=true;
					}
					else{
						if(l.contains(previousSequence.getOperation()))
							condition2=true;
					}
					if(condition1 && condition2)
						s.add(nextSequence);
				}

			}
		    //Iterar sobre el grafo
		    pw.println("})})");
		    pw.println(		"(this.jQuery)");
		    pw.println("</script></body></html>");
            

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
	}
	
	public void drawGraph3(String resultsFile, boolean critical, PairVO pair){
		FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(resultsFile);
            System.out.println(resultsFile);
            pw = new PrintWriter(fichero);
            
            pw.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">");
            pw.println("<html lang=\"en\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
            pw.println("<title> Graph - </title> <link rel=\"stylesheet\" href=\"./style/style.css\" type=\"text/css\">");
		    int width = (getTotalJobs()+1)*110;
            pw.println("</head> <body> <canvas id=\"viewport\" width=\""+width+"\" height=\""+width+"\"></canvas>");
		    pw.println("  <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js\"></script>");
		    pw.println("  <script src=\"./js/arbor.js\"></script>  ");
		    pw.println("  <script type=\"text/javascript\" language=\"JavaScript\">");
		    pw.println("  	(function($){");
		    pw.println(" var addNode = function(x1,y1,critical,change, label){");
		    pw.println("	var ctx = document.getElementById(\"viewport\").getContext( \"2d\");");
		    pw.println("	var ancho = 50");
		    pw.println("	var entre = 10");
		    pw.println("	var x = 100*x1+ancho+entre*x1");
		    pw.println("	var y = 100*y1+ancho+entre*y1");
		    pw.println("	var w = 50");
		    pw.println("      ctx.fillStyle = (critical) ? \"#22F9FF\" : (change) ? \"#22F9FF\": \"white\"");
		    pw.println("	  ctx.globalAlpha=0.2;");
		    pw.println("      ctx.fillRect(x-w/2, y-w/2, w,w)");
		    pw.println("	  if(change){");
		    pw.println("		ctx.fillStyle = \"black\" ");
		    pw.println("		ctx.fillRect(x-w/2+w/8, y-w/2+w/8, 6*w/8,6*w/8)");
		    pw.println("	 }");
		    pw.println("	  if (label){");
		    pw.println("		ctx.globalAlpha=1;");
		    pw.println("        ctx.font = \"bold 11px Arial\"");
		    pw.println("        ctx.textAlign = \"center\"");
		    pw.println("        ctx.fillStyle = \"black\"");
		    pw.println("        ctx.fillText(label||\"\", x, y+4)");
		    pw.println("      }");
		    pw.println("};");
		    pw.println(" var addEdge = function (x1,y1,x2,y2,route,xm,ym,x2m,y2m){");
		    pw.println("	var ctx = document.getElementById(\"viewport\").getContext(\"2d\");");
		    pw.println("var a = 0;");
		    pw.println("if(route){");
		    pw.println("	if(y2m<0){");
		    pw.println("		a = -10;");
		    pw.println("	}");
		    pw.println("	else if(y2m>0){");
		    pw.println("	  a = 10;");
		    pw.println("	}");
		    pw.println("}");
		    pw.println("	var ancho = 50;");
		    pw.println("	var entre = 10;");
		    pw.println("	var fromx = 100*x1+ancho+entre*x1");
		    pw.println("	var fromy = 100*y1+ancho+entre*y1+a");
		    pw.println("	var tox = 100*x2+ancho+entre*x2");
		    pw.println("	var toy = 100*y2+ancho+entre*y2");
		    pw.println("	var sx = xm*(tox-fromx)+fromx+x2m;");
		    pw.println("	var sy = ym*(toy-fromy+a)+fromy+a+y2m;");
		    pw.println("	var ex = tox;");
		    pw.println("	var ey = toy+a;");
		    pw.println("if(!route){");
		    pw.println("	var dif = fromy-toy;");
		    pw.println("	if(dif<0){");
		    pw.println("		fromy+=10;");
		    pw.println("		ey-=10;");
		    pw.println("	}");
		    pw.println("	if(dif>0){");
		    pw.println("		fromy-=10;");
		    pw.println("		ey+=10;");
		    pw.println("	}");
		    pw.println("    sy = ym*(ey-fromy)+fromy+y2m;");
		    pw.println("}");
		    
		    pw.println("	ctx.beginPath();");
		    pw.println("	ctx.globalAlpha = 0.4;");
		    pw.println("	ctx.lineWidth = 2");
		    pw.println("	ctx.strokeStyle = route ? 'green' :'blue';");
		    pw.println("	ctx.moveTo(fromx,fromy);");
		    pw.println("	ctx.quadraticCurveTo(sx, sy, ex, ey);");
		    pw.println("	ctx.stroke();");
		    pw.println("	ctx.closePath();");
		    pw.println("	var ang = Math.atan((ey - sy) / (ex - sx));");
		    pw.println("	ctx.fillRect(ex, ey, 2, 2);");
		    pw.println("	drawArrowhead(ex, ey, ang, 12, 12,route);");
		    pw.println("	ctx.globalAlpha=1;");
		    pw.println("};	");
		    
		    
		    pw.println(" var drawArrowhead = function (locx, locy, angle, sizex, sizey, route) {");
		    pw.println("	var ctx = document.getElementById(\"viewport\").getContext(\"2d\");");
		    pw.println("	var hx = sizex / 2;");
		    pw.println("	var hy = sizey / 2;");
		    pw.println("	ctx.translate((locx ), (locy));");
		    pw.println("	ctx.rotate(angle);");
		    pw.println("	ctx.translate(-hx,-hy);");
		    pw.println("	ctx.beginPath();");
		    pw.println("	ctx.fillStyle = route ? 'green' :'blue';");
		    pw.println("	ctx.moveTo(0,0);");
		    pw.println("	ctx.lineTo(0,1*sizey); ");   
		    pw.println("	ctx.lineTo(1*sizex,1*hy);");
		    pw.println("	ctx.closePath();");
		    pw.println("	ctx.fill();");
		    pw.println("	ctx.setTransform(1, 0, 0, 1, 0, 0);");
		    pw.println("};	");
		    
		    
		    pw.println("$(document).ready(function(){");

		    ArrayList<IOperation> operationsCritical= new ArrayList<IOperation>();
		    if(critical){
		    	ArrayList<CriticalPath> paths = getCriticalPaths();
		    	for(int i = 0; i < paths.size() ;i++){
		    		operationsCritical.addAll(paths.get(i).getRoute());
		    	}
		    }
		    	
		    //Iterar sobre el grafo
		    ArrayList<IOperation> l = new ArrayList<IOperation>();
			ArrayList<Node> s = new ArrayList<Node>();

			for (int i = 0; i < totalJobs; i++) {
				for (int j = 0; j < totalStations; j++) {
					if (nodes[i][j] != null){
						if (nodes[i][j].getPreviousRouteNode()==null && nodes[i][j].getPreviousSequenceNode()==null){
							s.add(nodes[i][j]);
							String nameA = nodes[i][j].getOperation().toString();
							int jobId= nodes[i][j].getOperation().getOperationIndex().getJobId();
							int machineId = nodes[i][j].getOperation().getOperationIndex().getStationId();
							boolean impreso= false;
							if(pair!=null){
								if(pair.getoX().equals(nodes[i][j].getOperation().getOperationIndex())||pair.getoY().equals(nodes[i][j].getOperation().getOperationIndex())){
									impreso =true;
									pw.println("addNode ("+machineId+","+jobId+",false,true,'"+nameA+"')");
								}
							}
							if(critical && !impreso){
								if(operationsCritical.contains(nodes[i][j].getOperation())){
									impreso =true;
									pw.println("addNode ("+machineId+","+jobId+",true,false,'"+nameA+"')");
								}
								
							}
							if(!impreso)
								pw.println("addNode ("+machineId+","+jobId+",false,false,'"+nameA+"')");
							
							
						}	
					}
				}
			}
			int number = -60;
			while(s.size()>0)
			{
				
				Node temp = s.get(0);
				String nameA = temp.getOperation().toString();
				int jobIdA= temp.getOperation().getOperationIndex().getJobId();
				int machineIdA = temp.getOperation().getOperationIndex().getStationId();
							
				Node nextRoute = temp.getNextRouteNode();
				Node nextSequence = temp.getNextSequenceNode();
				s.remove(0);
				l.add(temp.getOperation());
				if(nextRoute !=null){
					String nameB = nextRoute.getOperation().toString();
					int jobId= nextRoute.getOperation().getOperationIndex().getJobId();
					int machineId = nextRoute.getOperation().getOperationIndex().getStationId();
					boolean impreso= false;
					if(pair!=null){
						if(pair.getoX().equals(nextRoute.getOperation().getOperationIndex())||pair.getoY().equals(nextRoute.getOperation().getOperationIndex())){
							impreso =true;
							pw.println("addNode ("+machineId+","+jobId+",false,true,'"+nameB+"')");
						}
					}
					if(critical && !impreso){
						if(operationsCritical.contains(nextRoute.getOperation())){
							impreso =true;
							pw.println("addNode ("+machineId+","+jobId+",true,false,'"+nameB+"')");
						}
						
					}
					if(!impreso)
						pw.println("addNode ("+machineId+","+jobId+",false,false,'"+nameB+"')");
					
					pw.println("addEdge("+machineIdA+","+jobIdA+","+machineId+","+jobId+",true, 1/2,1,0,"+number+")  ");
					
					Node previosRoute = nextRoute.getPreviousRouteNode();
					Node previousSequence = nextRoute.getPreviousSequenceNode();
					boolean condition1=false;
					boolean condition2 =false;

					if(previosRoute==null){
						condition1=true;
					}
					else{
						if(l.contains(previosRoute.getOperation()))
							condition1=true;
					}

					if(previousSequence==null){
						condition2=true;
					}
					else{
						if(l.contains(previousSequence.getOperation()))
							condition2=true;
					}
					if(condition1 && condition2)
						s.add(nextRoute);
						
				}

				if(nextSequence !=null){
					
					String nameC = nextSequence.getOperation().toString();
					int jobId= nextSequence.getOperation().getOperationIndex().getJobId();
					int machineId = nextSequence.getOperation().getOperationIndex().getStationId();
					boolean impreso= false;
					if(pair!=null){
						if(pair.getoX().equals(nextSequence.getOperation().getOperationIndex())||pair.getoY().equals(nextSequence.getOperation().getOperationIndex())){
							impreso =true;
							pw.println("addNode ("+machineId+","+jobId+",false,true,'"+nameC+"')");
						}
					}
					if(critical && !impreso){
						if(operationsCritical.contains(nextSequence.getOperation())){
							impreso =true;
							pw.println("addNode ("+machineId+","+jobId+",true,false,'"+nameC+"')");
						}
						
					}
					if(!impreso)
						pw.println("addNode ("+machineId+","+jobId+",false,false,'"+nameC+"')");
					pw.println("addEdge("+machineIdA+","+jobIdA+","+machineId+","+jobId+",false,1,1/2,"+number+",0)  ");
					Node previosRoute = nextSequence.getPreviousRouteNode();
					Node previousSequence = nextSequence.getPreviousSequenceNode();
					boolean condition1=false;
					boolean condition2 =false;

					if(previosRoute==null){
						condition1=true;
					}
					else{
						if(l.contains(previosRoute.getOperation()))
							condition1=true;
					}

					if(previousSequence==null){
						condition2=true;
					}
					else{
						if(l.contains(previousSequence.getOperation()))
							condition2=true;
					}
					if(condition1 && condition2)
						s.add(nextSequence);
				}
				if(number==60)
					number=-60;
				number+=15;

			}
		    //Iterar sobre el grafo
		    pw.println("})})");
		    pw.println(		"(this.jQuery)");
		    pw.println("</script></body></html>");
            

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
	}

	public int getNumberNullNodes(){
		int number=0;
		for (int i = 0; i < totalJobs; i++) {
			for (int j = 0; j < totalStations; j++) {
				if (nodes[i][j] == null){
					number++;	
				}
			}
		}
		return number;
	}

	
	

	//	public int getTT(int stationId1, int stationId2){
	//		if(this.betas!=null){
	//			Iterator<Beta> iterator = betas.values().iterator();
	//			while (iterator.hasNext()) {
	//				Beta beta = iterator.next();
	//				if (beta instanceof TTBeta){
	//					return (int) ((TTBeta) beta).getValue(stationId1,stationId2);
	//				}
	//			}
	//		}
	//		return 0;
	//	}
	
//	
		
	//	// Metodo orden topologico
	//	// Tener cuidado porque destruye las relaciones del grafo de solucion
	//	public ArrayList<IOperation> topologicalSort()  throws Exception{
	//		ArrayList<IOperation> l = new ArrayList<IOperation>();
	//		ArrayList<IOperation> s = new ArrayList<IOperation>();
	//		
	//		for (int i = 0; i < totalJobs; i++) {
	//			for (int j = 0; j < totalStations; j++) {
	//				if (nodes[i][j] != null){
	//					if (nodes[i][j].getPreviousRouteNode()==null && nodes[i][j].getPreviousSequenceNode()==null){
	//						s.add(nodes[i][j]);
	//					}	
	//				}
	//			}
	//		}
	//		
	//		while(s.size()>0)
	//		{
	//			IOperation temp = s.get(0);
	//			s.remove(0);
	//			IOperation nextRoute = temp.getNextRouteNode();
	//			IOperation nextSequence = temp.getNextSequenceNode();
	//			temp.setNextRouteNode(null);
	//			temp.setNextSequenceNode(null);
	//			l.add(temp);
	//			if(nextRoute !=null){
	//				nextRoute.setPreviousRouteNode(null);
	//				if( nextRoute.getPreviousRouteNode()==null && nextRoute.getPreviousSequenceNode()==null)
	//					s.add(nextRoute);
	//			}
	//			
	//			if(nextSequence !=null){
	//				nextSequence.setPreviousSequenceNode(null);
	//				if( nextSequence.getPreviousRouteNode()==null && nextSequence.getPreviousSequenceNode()==null)
	//					s.add(nextSequence);
	//			}
	//			
	//		}
	//		
	//		boolean presenceOfCycles = false;
	//		
	//		for (int i = 0; i < totalJobs; i++) {
	//			for (int j = 0; j < totalStations; j++) {
	//				if (nodes[i][j] != null){
	//					if (nodes[i][j].getPreviousRouteNode()!=null && nodes[i][j].getPreviousSequenceNode()!=null){
	//						presenceOfCycles =true;
	//					}	
	//				}
	//			}
	//		}
	//		if(presenceOfCycles){
	//			throw new Exception("Prensence of cycles");
	//		}
	//		return l;
	//	}
	//	
	//	
}