package neighborCalculator.impl;

import java.util.ArrayList;

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
public class ShiftRandomBlockCriticalRoute implements INeighborCalculator {

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------

	private OperationIndexVO randomA;
	private OperationIndexVO randomB;
	
	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	public ShiftRandomBlockCriticalRoute() {

	}

	// -----------------------------------------------
	// Methods
	// -----------------------------------------------

	
	@Override
	public PairVO calculateNeighbor(IStructure currentGraph) throws Exception {
		IStructure clone = currentGraph.cloneStructure();
		ArrayList<CriticalRoute> routes = clone.getLongestRoutes();
		
		int number = randomNumber(0, routes.size() - 1);
        ArrayList<ArrayList<IOperation>> blocks= routes.get(number).getBlocks();
        int number2 = randomNumber(0, blocks.size() - 1);
        ArrayList<IOperation> block = blocks.get(number2);
        int number3= randomNumber(0, block.size()-1);
        int number4= randomNumber(0, block.size()-1);
        while(number4==number3)
        	number4= randomNumber(0, block.size()-1);
        IOperation initialNode= block.get(number3);
        IOperation finalNode = block.get(number4);
		

		OperationIndexVO initialOperationIndex = new OperationIndexVO(initialNode.getOperationIndex().getJobId(), initialNode.getOperationIndex().getStationId());
		OperationIndexVO finalOperationIndex = new OperationIndexVO(finalNode.getOperationIndex().getJobId(), finalNode.getOperationIndex().getStationId());
		
		return new PairVO(initialOperationIndex,finalOperationIndex);
		
	}

	@Override
	public ArrayList<PairVO> calculateNeighborhood(IStructure currentGraph, int size)
			throws Exception {
		
		ArrayList<PairVO> neighborhood = new ArrayList<PairVO>();
        IStructure clone = currentGraph.cloneStructure();
        
        // Obtaining all the critical paths of the current solutions
        ArrayList<CriticalRoute> routes = clone.getLongestRoutes();
        
//        System.out.println(routes);
        
        // Selecting one of the critical paths
        int numberRoutes= routes.size();
//        System.out.println("rutas:"+numberRoutes);
        int exit = 0;
        while(neighborhood.size() < size){
            
            int number = randomNumber(0, routes.size() - 1);
            ArrayList<ArrayList<IOperation>> blocks= routes.get(number).getBlocks();
            int number2 = randomNumber(0, blocks.size() - 1);
            ArrayList<IOperation> block = blocks.get(number2);
            int number3= randomNumber(0, block.size()-1);
            int number4= randomNumber(0, block.size()-1);
            while(number4==number3)
            	number4= randomNumber(0, block.size()-1);
            IOperation initialNode= block.get(number3);
            IOperation finalNode = block.get(number4);

            		            
            OperationIndexVO initialOperationIndex = new OperationIndexVO(initialNode.getOperationIndex().getJobId(), initialNode.getOperationIndex().getStationId());
            OperationIndexVO finalOperationIndex = new OperationIndexVO(finalNode.getOperationIndex().getJobId(), finalNode.getOperationIndex().getStationId());
            
            PairVO temp = new PairVO(initialOperationIndex, finalOperationIndex);
            if(!neighborhood.contains(temp)){
                neighborhood.add(temp);
                exit=0;
            }else{
                exit++;
                if(exit>=100){
                    return neighborhood;
                }
            }
        }
        return neighborhood;
	}
		
	@Override
	public ArrayList<PairVO> calculateCompleteNeighborhood(
			IStructure currentStructure) throws Exception {
		// TODO calculateCompleteNeighborhood for ShiftBlockCriticalRoute
		return null;
	}
	
	// -----------------------------------------------
	// Utilities
	// -----------------------------------------------
	
	private static int randomNumber(int min, int max) {
		return (int) Math.round((Math.random() * (max - min)) + min);
	}
}