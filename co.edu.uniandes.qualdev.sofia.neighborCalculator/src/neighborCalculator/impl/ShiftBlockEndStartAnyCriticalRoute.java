package neighborCalculator.impl;

import java.util.ArrayList;

import structure.IOperation;
import structure.IStructure;
import structure.impl.CriticalPath;

import common.types.OperationIndexVO;
import common.types.PairVO;
import common.utils.RandomNumber;

import neighborCalculator.INeighborCalculator;

/**
 * Class that is able to calculate a neighbor of a solution using the algorithm
 * Adjacent Shift on a critical route.
 * 
 * @author Jaime Romero
 */
public class ShiftBlockEndStartAnyCriticalRoute implements INeighborCalculator {

	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	public ShiftBlockEndStartAnyCriticalRoute() {

	}

	// -----------------------------------------------
	// Methods
	// -----------------------------------------------

	
	@Override
	public PairVO calculateNeighbor(IStructure currentGraph) throws Exception {
		IStructure clone = currentGraph.cloneStructure();
		ArrayList<CriticalPath> routes = clone.getCriticalPaths();
		
		int number = RandomNumber.getInstance().randomNumber(0, routes.size());
        ArrayList<ArrayList<IOperation>> blocks= routes.get(number).getBlocks();
        int number2 = RandomNumber.getInstance().randomNumber(0, blocks.size());
        ArrayList<IOperation> block = blocks.get(number2);
        int number3= RandomNumber.getInstance().randomNumber(0, 1);
        int number4 = RandomNumber.getInstance().randomNumber(1, block.size());
        IOperation initialNode= block.get(0);
        IOperation finalNode = block.get(number4);
        if(number3==1){
        	number4 = RandomNumber.getInstance().randomNumber(0, block.size()-1);
            finalNode= block.get(block.size()-1);
            initialNode = block.get(number4);
        }

		OperationIndexVO initialOperationIndex = new OperationIndexVO(initialNode.getOperationIndex().getJobId(), initialNode.getOperationIndex().getStationId());
		OperationIndexVO finalOperationIndex = new OperationIndexVO(finalNode.getOperationIndex().getJobId(), finalNode.getOperationIndex().getStationId());
		
		return new PairVO(initialOperationIndex,finalOperationIndex);
		
	}

	@Override
	public ArrayList<PairVO> calculateNeighborhood(IStructure currentGraph, long size)
			throws Exception {
		
		ArrayList<PairVO> neighborhood = new ArrayList<PairVO>();
		IStructure clone = currentGraph.cloneStructure();
		ArrayList<CriticalPath> routes = clone.getCriticalPaths();
		int salida=0;

		
		while(neighborhood.size()<size){
			
			int number = RandomNumber.getInstance().randomNumber(0, routes.size());
            ArrayList<ArrayList<IOperation>> blocks= routes.get(number).getBlocks();
            int number2 =RandomNumber.getInstance().randomNumber(0, blocks.size());
            ArrayList<IOperation> block = blocks.get(number2);
            int number3= RandomNumber.getInstance().randomNumber(0, 1);
            int number4 = RandomNumber.getInstance().randomNumber(1, block.size());
            IOperation initialNode= block.get(0);
            IOperation finalNode = block.get(number4);
            if(number3==1){
            	number4 = RandomNumber.getInstance().randomNumber(0, block.size()-1);
                finalNode= block.get(block.size()-1);
                initialNode = block.get(number4);
            }
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
	
	@Override
	public ArrayList<PairVO> calculateCompleteNeighborhood(
			IStructure currentStructure) throws Exception {
		ArrayList<PairVO> neighborhood = new ArrayList<PairVO>();
		IStructure clone = currentStructure.cloneStructure();
        
        // Obtaining all the critical paths of the current solutions
        ArrayList<CriticalPath> routes = clone.getCriticalPaths();
        
        for(int i=0; i< routes.size();i++){
        	
        	CriticalPath selectedRoute = routes.get(i);
        	ArrayList<ArrayList<IOperation>> blocks= selectedRoute.getBlocks();
        	for(int j=0; j < blocks.size();j++){
        		ArrayList<IOperation> block = blocks.get(j);
        		IOperation initialNode1= block.get(0);
        		for(int z=1; z< block.size(); z++){
        			IOperation finalNode1 = block.get(z);
        			OperationIndexVO initialOperationIndex = new OperationIndexVO(initialNode1.getOperationIndex().getJobId(), initialNode1.getOperationIndex().getStationId());
    	            OperationIndexVO finalOperationIndex = new OperationIndexVO(finalNode1.getOperationIndex().getJobId(), finalNode1.getOperationIndex().getStationId());
    	            
    	            PairVO temp = new PairVO(initialOperationIndex, finalOperationIndex);
    	            neighborhood.add(temp);
    	            
        		}
        		
        		initialNode1= block.get(block.size()-1);
        		for(int z=0; z< block.size()-1; z++){
        			IOperation finalNode1 = block.get(z);
        			OperationIndexVO initialOperationIndex = new OperationIndexVO(initialNode1.getOperationIndex().getJobId(), initialNode1.getOperationIndex().getStationId());
    	            OperationIndexVO finalOperationIndex = new OperationIndexVO(finalNode1.getOperationIndex().getJobId(), finalNode1.getOperationIndex().getStationId());
    	            
    	            PairVO temp = new PairVO(initialOperationIndex, finalOperationIndex);
    	            neighborhood.add(temp);
        		}

        	}
        	
        }

		return neighborhood;
	}
	
}