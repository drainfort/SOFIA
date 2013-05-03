package structure.impl;

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
	
	
	private ArrayList<Job> jobs;
	private ArrayList<Machine> machines;
	private ArrayList<Station> stations;

	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	/**
	 * Constructor of the class
	 */
	public Graph(int totalJobs, int totalStations) {
		super(totalJobs, totalStations);
		
		nodes = new Node[totalJobs][totalStations];
		
		for (int i = 0; i < totalJobs; i++) {
			for (int j = 0; j < totalStations; j++) {
				nodes[i][j] = new Node(operationsMatrix[i][j], this);
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
	 */
	public Graph(String processingTimesFile, ArrayList<BetaVO> pBetas) throws Exception {
		super(processingTimesFile, pBetas);
		
		nodes = new Node[totalJobs][totalStations];
		
		for (int i = 0; i < totalJobs; i++) {
			for (int j = 0; j < totalStations; j++) {
				nodes[i][j] = new Node(operationsMatrix[i][j], this);
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
	

	public void createRouteArc(OperationIndexVO start, OperationIndexVO end) {
		nodes[start.getJobId()][start.getStationId()]
				.setNextRouteNode(nodes[end.getJobId()][end.getStationId()]);
		nodes[end.getJobId()][end.getStationId()]
				.setPreviousRouteNode(nodes[start.getJobId()][start
						.getStationId()]);

	}

	public void createSequenceArc(OperationIndexVO start, OperationIndexVO end) {
		nodes[start.getJobId()][start.getStationId()]
				.setNextSequenceNode(nodes[end.getJobId()][end.getStationId()]);
		nodes[end.getJobId()][end.getStationId()]
				.setPreviousSequenceNode(nodes[start.getJobId()][start
						.getStationId()]);
	}
	
	public Node getInitialJobNode(int job) {
		return initialJobNodesArray[job];
	}

	public void setInitialJobNode(int job, Node initialJobNode) {
		assert ((job > -1) && (job < totalJobs));
		this.initialJobNodesArray[job] = initialJobNode;
		initialJobNode.setPreviousRouteNode(null);
	}

	public Node getInitialStationNode(int station) {
		return initialStationNodesArray[station];
	}

	public void setInitialStationNode(int station,
			Node initialStationNode) {
		this.initialStationNodesArray[station] = initialStationNode;
		initialStationNode.setPreviousSequenceNode(null);
	}
	
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
				OperationIndexVO start = new OperationIndexVO(jobId, routearray[k]);
				OperationIndexVO end = new OperationIndexVO(jobId,
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
				OperationIndexVO start = new OperationIndexVO(seqarray[k],
						machineId);
				OperationIndexVO end = new OperationIndexVO(seqarray[k + 1],
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
				OperationIndexVO start = new OperationIndexVO(jobId, routearray[k]);
				OperationIndexVO end = new OperationIndexVO(jobId,
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
				OperationIndexVO start = new OperationIndexVO(seqarray[k],
						machineId);
				OperationIndexVO end = new OperationIndexVO(seqarray[k + 1],
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
				OperationIndexVO start = new OperationIndexVO(jobId, routearray[k]);
				OperationIndexVO end = new OperationIndexVO(jobId,
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
				OperationIndexVO start = new OperationIndexVO(seqarray[k],
						machineId);
				OperationIndexVO end = new OperationIndexVO(seqarray[k + 1],
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
	public int[][] calculateCMatrix() throws Exception{
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
	public IOperation getCiminus1J(IOperation Cij, int vectorPos) {
		return nodes[Cij.getOperationIndex().getJobId()][Cij.getOperationIndex().getStationId()].getPreviousRouteNode() != null ? 
				nodes[Cij.getOperationIndex().getJobId()][Cij.getOperationIndex().getStationId()].getPreviousRouteNode().getOperation() : null;
	}

	@Override
	public IOperation getCiJminus1(IOperation Cij, int vectorPos) {
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
				operations.add(operationsMatrix[i][j]);
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
			BetaVO beta = new BetaVO(nextKey, className, informationFiles);
			betas.add(beta);

		}
		return betas;
	}
	
	@Override
	public int getTTBetas(IOperation Cij, int predecessor) throws Exception{
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
	
	private IOperation getOperation(int job, int station) {
		return operationsMatrix[job][station];
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
		return operationsMatrix[operationIndex.getJobId()][operationIndex.getStationId()];
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
	public void scheduleOperation(OperationIndexVO operationIndex) {
		Vector<Integer> route = (Vector<Integer>) this.getJobRoute(operationIndex.getJobId());
		Vector<Integer> sequence = (Vector<Integer>) this.getStationSequence(operationIndex.getStationId());
		
		Node graphNode = this.getNode(operationIndex.getJobId(), operationIndex.getStationId());
		
		//Scheduling the route
		if(route.size() == 0){
				initialJobNodesArray[operationIndex.getJobId()] = graphNode;
		}else{
			OperationIndexVO start = new OperationIndexVO(operationIndex.getJobId(), route.get(route.size()-1));
			this.createRouteArc(start, operationIndex); 
		}
		
		//Scheduling the sequence
		if(sequence.size() == 0){
				initialStationNodesArray[operationIndex.getStationId()] = graphNode;
		}else{
			OperationIndexVO start = new OperationIndexVO(sequence.get(sequence.size()-1), operationIndex.getStationId());
			this.createSequenceArc(start, operationIndex); 
		}
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
		newGraph.operationsMatrix = new IOperation[newGraph.totalJobs][newGraph.totalStations];
		
		newGraph.initialJobNodesArray = new Node[newGraph.totalJobs];
		newGraph.initialStationNodesArray = new Node[newGraph.totalStations];

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
					newGraph.operationsMatrix[i][j] = new Operation(this.operationsMatrix[i][j].getProcessingTime(), i, j);
					newGraph.nodes[i][j] = new Node(newGraph.operationsMatrix[i][j], this);
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
						
						newGraph.createRouteArc(new OperationIndexVO(i, j), new OperationIndexVO(ni, nj));
					}

					if (this.getNode(i, j).getNextSequenceNode() != null) {
						ni = this.getNode(i, j).getNextSequenceNode().getOperation().getOperationIndex().getJobId();
						nj = this.getNode(i, j).getNextSequenceNode().getOperation().getOperationIndex().getStationId();
						
						newGraph.createSequenceArc(new OperationIndexVO(i, j), new OperationIndexVO(ni, nj));
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
	}
	
	/**
	 * Print in the console the matrix given in the parameter.
	 * 
	 * @param matrixToPrint
	 *            . The matrix that is going to be printed in the console.
	 */
	public String toString() {
		String matrix = "";
		int numMachines = operationsMatrix.length;
		int numJobs = operationsMatrix[0].length;
		for (int i = 0; i < numMachines; i++) {

			for (int j = 0; j < numJobs; j++) {
				if (operationsMatrix[i][j] != null) {
					matrix += " | [" + operationsMatrix[i][j].getOperationIndex().getJobId() + ","
							+ operationsMatrix[i][j].getOperationIndex().getStationId() + "]"
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
		// TODO Complete
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
	
	// -------------------------------------------------
	// TODO Metodos aun por arreglar...
	// -------------------------------------------------
	
	/**
	 * Calculates the longest paths of the graph.
	 * @throws Exception 
	 */
	public ArrayList<CriticalRoute> getLongestRoutes() throws Exception{
		int cmax = 0;
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
					nodes[i][j].restartC();
					int c= nodes[i][j].calculateC();
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
		
		ArrayList<CriticalRoute> totalRoutes = new ArrayList<CriticalRoute>();
		
		for(int i=0; i< cMaxNodes.size();i++){
			ArrayList<CriticalRoute> criticalRoutes = new ArrayList<CriticalRoute>();
			CriticalRoute firstRoute = new CriticalRoute();
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
	
//	
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
//	//Topological sort no destruye el grafo
//	public ArrayList<IOperation> topologicalSort2()  throws Exception{
//		ArrayList<IOperation> l = new ArrayList<IOperation>();
//		ArrayList<IOperation> s = new ArrayList<IOperation>();
//		int counter = 1;
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
//			if(counter >totalJobs*totalStations)
//				throw new Exception("Prensence of cycles");
//			
//			IOperation temp = s.get(0);
//			temp.setPositionSort(counter);
//			
//			IOperation nextRoute = temp.getNextRouteNode();
//			IOperation nextSequence = temp.getNextSequenceNode();
//			s.remove(0);
//			l.add(temp);
//			if(nextRoute !=null){
//				IOperation previosRoute = nextRoute.getPreviousRouteNode();
//				IOperation previousSequence = nextRoute.getPreviousSequenceNode();
//				boolean condition1=false;
//				boolean condition2 =false;
//				
//				if(previosRoute==null){
//					condition1=true;
//				}
//				else{
//					if(l.contains(previosRoute))
//						condition1=true;
//				}
//				
//				if(previousSequence==null){
//					condition2=true;
//				}
//				else{
//					if(l.contains(previousSequence))
//						condition2=true;
//				}
//				if(condition1 && condition2)
//					s.add(nextRoute);
//				
//			}
//			
//			if(nextSequence !=null){
//				IOperation previosRoute = nextSequence.getPreviousRouteNode();
//				IOperation previousSequence = nextSequence.getPreviousSequenceNode();
//				boolean condition1=false;
//				boolean condition2 =false;
//				
//				if(previosRoute==null){
//					condition1=true;
//				}
//				else{
//					if(l.contains(previosRoute))
//						condition1=true;
//				}
//				
//				if(previousSequence==null){
//					condition2=true;
//				}
//				else{
//					if(l.contains(previousSequence))
//						condition2=true;
//				}
//				if(condition1 && condition2)
//					s.add(nextSequence);
//			}
//			
//			counter++;
//		}
//		
//		if(l.size() !=totalJobs*totalStations-getNumberNullNodes())
//			throw new Exception("Prensence of cycles");
//		return l;
//	}
//	
//	public int getNumberNullNodes(){
//		int number=0;
//		for (int i = 0; i < totalJobs; i++) {
//			for (int j = 0; j < totalStations; j++) {
//				if (nodes[i][j] == null){
//					number++;	
//				}
//			}
//		}
//		return number;
//	}
//	
//	public void restartC(){
//		C=null;
//		for (int i = 0; i < totalJobs; i++) {
//			for (int j = 0; j < totalStations; j++) {
//				if (nodes[i][j] != null){
//					nodes[i][j].restartC();
//				}
//			}
//		}
//	}
//
//	public ArrayList<int[]> getWeightedNodesCriticaRoute() {
//		return weightedNodesCriticaRoute;
//	}
//
//	public void setWeightedNodesCriticaRoute(
//			ArrayList<int[]> weightedNodesCriticaRoute) {
//		this.weightedNodesCriticaRoute = weightedNodesCriticaRoute;
//	}
//
//	public void setBetas(Map<String, Beta> betas) {
//		this.betas = betas;
//	}
//
//	public ArrayList<Job> getJobs() {
//		return jobs;
//	}
//
//	public void setJobs(ArrayList<Job> jobs) {
//		this.jobs = jobs;
//	}
//
//	public ArrayList<Machine> getMachines() {
//		return machines;
//	}
//
//	public void setMachines(ArrayList<Machine> machines) {
//		this.machines = machines;
//	}
//
//	public ArrayList<Station> getStations() {
//		return stations;
//	}
//
//	public void setStations(ArrayList<Station> stations) {
//		this.stations = stations;
//	}
//	
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
}