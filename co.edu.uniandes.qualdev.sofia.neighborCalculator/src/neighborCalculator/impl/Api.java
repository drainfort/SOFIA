package neighborCalculator.impl;

import java.util.ArrayList;

import structure.IStructure;
import structure.impl.Graph;

import common.types.OperationIndexVO;
import common.types.PairVO;
import common.utils.RandomNumber;

import neighborCalculator.INeighborCalculator;

/**
 * Class that is able to calculate a neighbor of a solution using the algorithm
 * termed "API".
 * 
 * @author David Mendez-Acuna
 */
public class Api implements INeighborCalculator {

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------

	private int[][] A;
	private OperationIndexVO randomA;
	private OperationIndexVO randomB;

	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	public Api() {

	}

	// -----------------------------------------------
	// Methods
	// -----------------------------------------------

	@Override
	public PairVO calculateNeighbor(IStructure currentStructure) throws Exception {
		A = currentStructure.calculateAMatrix();

		boolean finish = false;
		OperationIndexVO initialOperationIndex = null;
		OperationIndexVO finalOperationIndex = null;

		while (!finish) {
			int i = RandomNumber.getInstance().randomNumber(0, currentStructure.getTotalJobs() - 1);
			int j = RandomNumber.getInstance().randomNumber(0, currentStructure.getTotalStations() - 1);

			Integer aij = A[i][j];
			if (aij != null) {
				double directionRandom = Math.random();

				if(directionRandom < 0.5){
					// route
					double successorOrPredecessorRandom = Math.random();
					if(successorOrPredecessorRandom < 0.5 && ((Graph)currentStructure).getNode(i, j).getPreviousRouteNode() != null){
						//predecessor
						int newI = ((Graph)currentStructure).getNode(i, j).getPreviousRouteNode().getOperation().getOperationIndex().getJobId();
						int newJ = ((Graph)currentStructure).getNode(i, j).getPreviousRouteNode().getOperation().getOperationIndex().getStationId();
						
						if((Math.abs(A[i][j]-A[newI][newJ]) == 1)){
							initialOperationIndex = new OperationIndexVO(i, j);
							finalOperationIndex = new OperationIndexVO(newI, newJ);
							finish = true;
						}
					}else if(successorOrPredecessorRandom >= 0.5 && ((Graph)currentStructure).getNode(i, j).getNextRouteNode() != null){
						//successor
						int newI = ((Graph)currentStructure).getNode(i, j).getNextRouteNode().getOperation().getOperationIndex().getJobId();
						int newJ = ((Graph)currentStructure).getNode(i, j).getNextRouteNode().getOperation().getOperationIndex().getStationId();
						
						if((Math.abs(A[i][j]-A[newI][newJ]) == 1)){
							initialOperationIndex = new OperationIndexVO(i, j);
							finalOperationIndex = new OperationIndexVO(newI, newJ);
							finish = true;
						}
					}
					
				}else{
					// sequence
					double successorOrPredecessorRandom = Math.random();
					if(successorOrPredecessorRandom < 0.5 && ((Graph)currentStructure).getNode(i, j).getNextSequenceNode() != null){
						//predecessor
						int newI = ((Graph)currentStructure).getNode(i, j).getNextSequenceNode().getOperation().getOperationIndex().getJobId();
						int newJ = ((Graph)currentStructure).getNode(i, j).getNextSequenceNode().getOperation().getOperationIndex().getStationId();
						
						if((Math.abs(A[i][j]-A[newI][newJ]) == 1)){
							initialOperationIndex = new OperationIndexVO(i, j);
							finalOperationIndex = new OperationIndexVO(newI, newJ);
							finish = true;
						}
					}else if(successorOrPredecessorRandom >= 0.5 && ((Graph)currentStructure).getNode(i, j).getPreviousSequenceNode() != null){
						//successor
						int newI = ((Graph)currentStructure).getNode(i, j).getPreviousSequenceNode().getOperation().getOperationIndex().getJobId();
						int newJ = ((Graph)currentStructure).getNode(i, j).getPreviousSequenceNode().getOperation().getOperationIndex().getStationId();
						
						if((Math.abs(A[i][j]-A[newI][newJ]) == 1)){
							initialOperationIndex = new OperationIndexVO(i, j);
							finalOperationIndex = new OperationIndexVO(newI, newJ);
							finish = true;
						}
					}
				}
			}
		}

		randomA = initialOperationIndex;
		randomB = finalOperationIndex;
		return new PairVO(randomA, randomB);
	}

	@Override
	public ArrayList<PairVO> calculateNeighborhood(IStructure currentStructure, long size)
			throws Exception {
		A = currentStructure.calculateAMatrix();
		ArrayList<PairVO> neighborhood = new ArrayList<PairVO>();
		OperationIndexVO initialOperationIndex = null;
		OperationIndexVO finalOperationIndex = null;

		while (size>=0) {
			int i = RandomNumber.getInstance().randomNumber(0, currentStructure.getTotalJobs() - 1);
			int j = RandomNumber.getInstance().randomNumber(0, currentStructure.getTotalStations() - 1);

			Integer aij = A[i][j];
			if (aij != null) {
				double directionRandom = Math.random();

				if(directionRandom < 0.5){
					// route
					double successorOrPredecessorRandom = Math.random();
					if(successorOrPredecessorRandom < 0.5 && ((Graph)currentStructure).getNode(i, j).getPreviousRouteNode() != null){
						//predecessor
						int newI = ((Graph)currentStructure).getNode(i, j).getPreviousRouteNode().getOperation().getOperationIndex().getJobId();
						int newJ = ((Graph)currentStructure).getNode(i, j).getPreviousRouteNode().getOperation().getOperationIndex().getStationId();
						if((Math.abs(A[i][j]-A[newI][newJ]) == 1)){
							initialOperationIndex = new OperationIndexVO(i, j);
							finalOperationIndex = new OperationIndexVO(newI, newJ);
							PairVO temp = new PairVO(initialOperationIndex, finalOperationIndex);
							if(!neighborhood.contains(temp)){
								size--;
								neighborhood.add(temp);
							}
						}
					}else if(successorOrPredecessorRandom >= 0.5 && ((Graph)currentStructure).getNode(i,j).getNextRouteNode() != null){
						//successor
						int newI = ((Graph)currentStructure).getNode(i, j).getNextRouteNode().getOperation().getOperationIndex().getJobId();
						int newJ = ((Graph)currentStructure).getNode(i, j).getNextRouteNode().getOperation().getOperationIndex().getStationId();
						
						if((Math.abs(A[i][j]-A[newI][newJ]) == 1)){
							initialOperationIndex = new OperationIndexVO(i, j);
							finalOperationIndex = new OperationIndexVO(newI, newJ);
							PairVO temp = new PairVO(initialOperationIndex, finalOperationIndex);
							if(!neighborhood.contains(temp)){
								size--;
								neighborhood.add(temp);
							}
						}
					}
				}else{
					// sequence
					double successorOrPredecessorRandom = Math.random();
					if(successorOrPredecessorRandom < 0.5 && ((Graph)currentStructure).getNode(i, j).getNextSequenceNode() != null){
						//predecessor
						int newI = ((Graph)currentStructure).getNode(i, j).getNextSequenceNode().getOperation().getOperationIndex().getJobId();
						int newJ = ((Graph)currentStructure).getNode(i, j).getNextSequenceNode().getOperation().getOperationIndex().getStationId();
						
						if((Math.abs(A[i][j]-A[newI][newJ]) == 1)){
							initialOperationIndex = new OperationIndexVO(i, j);
							finalOperationIndex = new OperationIndexVO(newI, newJ);
							PairVO temp = new PairVO(initialOperationIndex, finalOperationIndex);
							if(!neighborhood.contains(temp)){
								size--;
								neighborhood.add(temp);
							}
						}
					}else if(successorOrPredecessorRandom >= 0.5 && ((Graph)currentStructure).getNode(i, j).getPreviousSequenceNode() != null){
						//successor
						int newI = ((Graph)currentStructure).getNode(i, j).getPreviousSequenceNode().getOperation().getOperationIndex().getJobId();
						int newJ = ((Graph)currentStructure).getNode(i, j).getPreviousSequenceNode().getOperation().getOperationIndex().getStationId();
						
						if((Math.abs(A[i][j]-A[newI][newJ]) == 1)){
							initialOperationIndex = new OperationIndexVO(i, j);
							finalOperationIndex = new OperationIndexVO(newI, newJ);
							PairVO temp = new PairVO(initialOperationIndex, finalOperationIndex);
							if(!neighborhood.contains(temp)){
								size--;
								neighborhood.add(temp);
							}
						}
					}
				}
			}
		}

		return neighborhood;
	}
	
	@Override
	public ArrayList<PairVO> calculateCompleteNeighborhood(
			IStructure currentStructure) throws Exception {
		A = currentStructure.calculateAMatrix();
		
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[0].length; j++) {
				
			}
		}
		return null;
	}
	
}