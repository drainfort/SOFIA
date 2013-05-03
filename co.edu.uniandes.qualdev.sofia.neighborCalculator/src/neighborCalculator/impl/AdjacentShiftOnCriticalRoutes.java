package neighborCalculator.impl;

import java.util.ArrayList;
import java.util.Properties;

import structure.IOperation;
import structure.IStructure;
import structure.impl.CriticalRoute;

import common.types.OperationIndexVO;
import common.types.PairVO;

import neighborCalculator.INeighborCalculator;


/**
 * Class that is able to calculate a neighbor of a solution using the algorithm
 * Adjacent Shift on a critical route.
 * 
 * @author Jaime Romero
 */
public class AdjacentShiftOnCriticalRoutes implements INeighborCalculator {

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------
	
	private OperationIndexVO a;
	
	private OperationIndexVO b;

	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	public AdjacentShiftOnCriticalRoutes() {

	}

	// -----------------------------------------------
	// Methods
	// -----------------------------------------------

	@Override
	public PairVO calculateNeighbor(IStructure currentGraph) throws Exception {
		IStructure clone = currentGraph.cloneStructure();
		ArrayList<CriticalRoute> routes = clone.getLongestRoutes();
		int number = randomNumber(0, routes.size() - 1);
		
		ArrayList<IOperation> selectedCritialPath = routes.get(number).getRoute();
		int i = randomNumber(0, selectedCritialPath.size() - 2);
		IOperation initialNode = selectedCritialPath.get(i);
		IOperation finalNode = selectedCritialPath.get(i + 1);
		OperationIndexVO initialOperationIndex = new OperationIndexVO(initialNode.getOperationIndex().getJobId(), initialNode.getOperationIndex().getStationId());
		OperationIndexVO finalOperationIndex = new OperationIndexVO(finalNode.getOperationIndex().getJobId(), finalNode.getOperationIndex().getStationId());
		return new PairVO(initialOperationIndex, finalOperationIndex);
		
	}

	@Override
	public ArrayList<PairVO> calculateNeighborhood(IStructure currentGraph, int start, int end)
			throws Exception {
		ArrayList<PairVO> neighborhood = new ArrayList<PairVO>();
		IStructure clone = currentGraph.cloneStructure();
		ArrayList<CriticalRoute> routes = clone.getLongestRoutes();
		int number = randomNumber(0, routes.size() - 1);
		int salida = 0;
		ArrayList<IOperation> selectedCritialPath = routes.get(number).getRoute();

		while(neighborhood.size()<end){
			int i = randomNumber(0, selectedCritialPath.size() - 2);
			IOperation initialNode = selectedCritialPath.get(i);
			IOperation finalNode = selectedCritialPath.get(i + 1);
			OperationIndexVO initialOperationIndex = new OperationIndexVO(initialNode.getOperationIndex().getJobId(), initialNode.getOperationIndex().getStationId());
			OperationIndexVO finalOperationIndex = new OperationIndexVO(finalNode.getOperationIndex().getJobId(), finalNode.getOperationIndex().getStationId());
			
			PairVO temp = new PairVO(initialOperationIndex, finalOperationIndex);
			if(!neighborhood.contains(temp)){
				neighborhood.add(temp);
				salida=0;
			}else{
				salida++;
				if(salida>=100)
					return neighborhood;
			}
		}
		
		return neighborhood;
	}
	
	/*public IStructure calculateNeighbor(IStructure currentGraph, Properties artificialRandom) throws Exception {
		
		IStructure clone = currentGraph.clone();
		ArrayList<CriticalRoute> routes = clone.getLongestRoutes();
		int number = randomNumber(0, routes.size() - 1);
		
		ArrayList<IOperation> selectedCritialPath = routes.get(number).getRoute();

		while(true){
			int i = randomNumber(0, selectedCritialPath.size() - 2);
			
			IOperation initialNode = selectedCritialPath.get(i);
			IOperation finalNode = selectedCritialPath.get(i + 1);
			
			OperationIndexVO initialOperationIndex = new OperationIndexVO(initialNode.getOperationIndex().getJobId(), initialNode.getOperationIndex().getStationId());
			OperationIndexVO finalOperationIndex = new OperationIndexVO(finalNode.getOperationIndex().getJobId(), finalNode.getOperationIndex().getStationId());
			
			//Exchange
			clone.exchangeOperations(initialOperationIndex, finalOperationIndex);
			
			try{
				clone.restartC();
				clone.topologicalSort2();
				a = initialOperationIndex;
				b = finalOperationIndex;
				return clone;
			}
			catch(Exception e){
				clone.exchangeOperations(finalOperationIndex, initialOperationIndex);
			}
		}
	}*/
		
	// -----------------------------------------------
	// Utilities
	// -----------------------------------------------
	
	private static int randomNumber(int min, int max) {
		return (int) Math.round((Math.random() * (max - min)) + min);
	}

	
}