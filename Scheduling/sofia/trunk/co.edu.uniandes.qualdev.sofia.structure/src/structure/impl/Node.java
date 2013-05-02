package structure.impl;

import structure.IOperation;

public class Node {

	// -------------------------------------
	// Attributes
	// -------------------------------------
	
	private IOperation operation;
	
	private Node nextRouteNode;
	
	private Node nextSequenceNode;
	
	private Node previousRouteNode;
	
	private Node previousSequenceNode;

	private boolean cCalculated = false;

	private boolean rankCalculated = false;
	
	private int rank;
	
	private Graph onwerGraph;
	
	// -------------------------------------
	// Constructor
	// -------------------------------------
	
	public Node(IOperation operation, Graph ownerGraph){
		this.operation = operation;
		this.onwerGraph = ownerGraph;
	}
	
	// -------------------------------------
	// Methods
	// -------------------------------------
	
	public int calculateC() throws Exception{

		if(cCalculated){
			return operation.getFinalTime();
		}else if(this.nextRouteNode == null && this.nextSequenceNode == null && 
				this.previousRouteNode == null && this.previousSequenceNode == null &&
						onwerGraph.getInitialJobNode(this.getOperation().getOperationIndex().getJobId()) != this &&
								onwerGraph.getInitialStationNode(this.getOperation().getOperationIndex().getStationId()) != this){
			return 0;
		}else{
			int ci = this.getPreviousRouteNode() != null ? this.getPreviousRouteNode().calculateC() : 0;
			int cj = this.getPreviousSequenceNode() != null ? this.getPreviousSequenceNode().calculateC() : 0;

			int initialTime = Math.max(ci + this.onwerGraph.getTTBetas(this.getOperation(), 
					this.getPreviousRouteNode() != null ? this.getPreviousRouteNode().getOperation() : null ), cj);
			this.operation.setInitialTime(initialTime);
			
			int finalTime = initialTime + operation.getProcessingTime();
			this.operation.setFinalTime(finalTime);

			cCalculated = true;
			return finalTime;
		}
	}
	
	public int calculateInitialTime() throws Exception {
		return this.calculateC() - this.operation.getProcessingTime();
	}
	
	public void restartC() {
		this.operation.setFinalTime(0);
		cCalculated = false;
	}
	
	public int getRank() {
		// Base case 1: The C is already calculated. Returns the current
		// calculated value.
		if (this.rankCalculated) {
			return rank;
		}

		// Base case 2: The node does not have any predecessors. Returns 1.
		else if (this.getPreviousRouteNode() == null
				&& this.getPreviousSequenceNode() == null) {
			rank = 1;
			return rank;
		}

		// Recursive case
		else {
			Integer CJob = -1, CMachine = -1;
			if (this.getPreviousRouteNode() != null)
				CJob = this.getPreviousRouteNode().getRank();

			if (this.getPreviousSequenceNode() != null)
				CMachine = this.getPreviousSequenceNode()
						.getRank();

			rank = Math.max(CJob, CMachine) + 1;
			rankCalculated = true;
			return rank;
		}
	}
	
	// -------------------------------------
	// Getters and setters
	// -------------------------------------
	
	public IOperation getOperation() {
		return operation;
	}

	public void setOperation(IOperation operation) {
		this.operation = operation;
	}

	public Node getNextRouteNode() {
		return nextRouteNode;
	}

	public void setNextRouteNode(Node nextRouteNode) {
		this.nextRouteNode = nextRouteNode;
	}

	public Node getNextSequenceNode() {
		return nextSequenceNode;
	}

	public void setNextSequenceNode(Node nextSequenceNode) {
		this.nextSequenceNode = nextSequenceNode;
	}

	public Node getPreviousRouteNode() {
		return previousRouteNode;
	}

	public void setPreviousRouteNode(Node previousRouteNode) {
		this.previousRouteNode = previousRouteNode;
	}

	public Node getPreviousSequenceNode() {
		return previousSequenceNode;
	}

	public void setPreviousSequenceNode(Node previousSequenceNode) {
		this.previousSequenceNode = previousSequenceNode;
	}
}
