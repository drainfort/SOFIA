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
public class N5_RandomInCriticalBlock implements INeighborCalculator {

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------

//	private OperationIndexVO randomA;
//	private OperationIndexVO randomB;
	
	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	public N5_RandomInCriticalBlock() {

	}

	// -----------------------------------------------
	// Methods
	// -----------------------------------------------

	
	@Override
	public PairVO calculateNeighbor(IStructure currentGraph) throws Exception {
		IStructure clone = currentGraph.cloneStructure();
		ArrayList<CriticalPath> routes = clone.getCriticalPaths();
		
		int number = 0;
		if(routes.size()!=1)
			number = RandomNumber.getInstance().randomNumber(0, routes.size() - 1);
        ArrayList<ArrayList<IOperation>> blocks= routes.get(number).getBlocks();
        int number2 = RandomNumber.getInstance().randomNumber(0, blocks.size() - 1);
        ArrayList<IOperation> block = blocks.get(number2);
        int number3= RandomNumber.getInstance().randomNumber(0, block.size()-1);
        int number4= RandomNumber.getInstance().randomNumber(0, block.size()-1);
        while(number4==number3)
        	number4= RandomNumber.getInstance().randomNumber(0, block.size()-1);
        IOperation initialNode= block.get(number3);
        IOperation finalNode = block.get(number4);
		

        OperationIndexVO initialOperationIndex = new OperationIndexVO(0,initialNode.getOperationIndex().getJobId(), initialNode.getOperationIndex().getStationId(), initialNode.getOperationIndex().getMachineId());
        OperationIndexVO finalOperationIndex = new OperationIndexVO(0, finalNode.getOperationIndex().getJobId(), finalNode.getOperationIndex().getStationId(), finalNode.getOperationIndex().getMachineId());
        
		clone.clean();
		return new PairVO(initialOperationIndex,finalOperationIndex);
		
	}

	@Override
	public ArrayList<PairVO> calculateNeighborhood(IStructure currentGraph, long size)
			throws Exception {
		
		ArrayList<PairVO> neighborhood = new ArrayList<PairVO>();
        
        // Obtaining all the critical paths of the current solutions
        ArrayList<CriticalPath> routes = currentGraph.getCriticalPaths();
                
        int exit = 0;
        ArrayList<ArrayList<IOperation>> blocks= null;
        ArrayList<IOperation> block = null;
        while(neighborhood.size() < size){
            
        	int number = 0;
    		if(routes.size()!=1)
    			number = RandomNumber.getInstance().randomNumber(0, routes.size() - 1);
            blocks= routes.get(number).getBlocks();
            int number2 = RandomNumber.getInstance().randomNumber(0, blocks.size() - 1);
            block = blocks.get(number2);
            int number3= RandomNumber.getInstance().randomNumber(0, block.size()-1);
            int number4= RandomNumber.getInstance().randomNumber(0, block.size()-1);
            while(number4==number3)
            	number4= RandomNumber.getInstance().randomNumber(0, block.size()-1);
            IOperation initialNode= block.get(number3);
            IOperation finalNode = block.get(number4);

            		            
            OperationIndexVO initialOperationIndex = new OperationIndexVO(0,initialNode.getOperationIndex().getJobId(), initialNode.getOperationIndex().getStationId(), initialNode.getOperationIndex().getMachineId());
            OperationIndexVO finalOperationIndex = new OperationIndexVO(0, finalNode.getOperationIndex().getJobId(), finalNode.getOperationIndex().getStationId(), finalNode.getOperationIndex().getMachineId());
            
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
		ArrayList<PairVO> neighborhood = new ArrayList<PairVO>();
		IStructure clone = currentStructure.cloneStructure();
		ArrayList<CriticalPath> routes = clone.getCriticalPaths();
		
		for(int i=0; i< routes.size();i++){
			ArrayList<ArrayList<IOperation>> blocks= routes.get(i).getBlocks();
			
			for(int j=0; j<blocks.size();j++){
				ArrayList<IOperation> block = blocks.get(j);
				for(int i1=0; i1<block.size();i1++){
					IOperation initialNode= block.get(i1);  		            
			        OperationIndexVO initialOperationIndex = new OperationIndexVO(initialNode.getOperationIndex().getJobId(), initialNode.getOperationIndex().getStationId()); 
					for(int j1=0; j1<block.size();j1++){
						IOperation finalNode = block.get(j1);
						OperationIndexVO finalOperationIndex = new OperationIndexVO(finalNode.getOperationIndex().getJobId(), finalNode.getOperationIndex().getStationId());
						PairVO temp = new PairVO(initialOperationIndex, finalOperationIndex);
			            if(!initialOperationIndex.equals(finalOperationIndex)){
			                neighborhood.add(temp);
			            }
					}
				}
				
			}
			
			
		}
		
		return neighborhood;
		
	}
	
}