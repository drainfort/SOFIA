package neighborCalculator.impl;

import java.util.ArrayList;

import structure.IOperation;
import structure.IStructure;
import structure.impl.CriticalPath;

import common.types.OperationIndexVO;
import common.types.PairVO;
import common.utils.RandomNumber;

import neighborCalculator.INeighborCalculator;

public class ShiftBlockAdjOnEnds implements INeighborCalculator{

	// -----------------------------------------------
		// Methods
		// -----------------------------------------------

		@Override
		public PairVO calculateNeighbor(IStructure currentStructure) throws Exception {
			IStructure clone = currentStructure.cloneStructure();
			
			// Obtaining all the critical paths of the current solutions
			ArrayList<CriticalPath> routes = clone.getCriticalPaths();
			
			int number = RandomNumber.getInstance().randomNumber(0, routes.size());
            ArrayList<ArrayList<IOperation>> blocks= routes.get(number).getBlocks();
            int number2 = RandomNumber.getInstance().randomNumber(0, blocks.size() );
            ArrayList<IOperation> block = blocks.get(number2);
            int number3= RandomNumber.getInstance().randomNumber(0, 1);
            IOperation initialNode= block.get(0);
            IOperation finalNode = block.get(1);
            if(number3==1){
                finalNode= block.get(block.size()-1);
                initialNode = block.get(block.size()-2);
            }
            		            
            OperationIndexVO initialOperationIndex = new OperationIndexVO(initialNode.getOperationIndex().getJobId(), initialNode.getOperationIndex().getStationId());
            OperationIndexVO finalOperationIndex = new OperationIndexVO(finalNode.getOperationIndex().getJobId(), finalNode.getOperationIndex().getStationId());
            
            PairVO temp = new PairVO(initialOperationIndex, finalOperationIndex);
            return temp;
		}

		@Override
		public ArrayList<PairVO> calculateNeighborhood(IStructure currentStructure, long size)
				throws Exception {

			 	ArrayList<PairVO> neighborhood = new ArrayList<PairVO>();
		        IStructure clone = currentStructure.cloneStructure();
		        
		        // Obtaining all the critical paths of the current solutions
		        ArrayList<CriticalPath> routes = clone.getCriticalPaths();
		        
//		        System.out.println(routes);
		        
		        // Selecting one of the critical paths
//		        int numberRoutes= routes.size();
//		        System.out.println("rutas:"+numberRoutes);
		        int exit = 0;
		        while(neighborhood.size() < size){
		            
		        	int number = RandomNumber.getInstance().randomNumber(0, routes.size());
		            ArrayList<ArrayList<IOperation>> blocks= routes.get(number).getBlocks();
		            int number2 = RandomNumber.getInstance().randomNumber(0, blocks.size());
		            ArrayList<IOperation> block = blocks.get(number2);
		            int number3= RandomNumber.getInstance().randomNumber(0, 1);
		            IOperation initialNode= block.get(0);
		            IOperation finalNode = block.get(1);
		            if(number3==1){
		                finalNode= block.get(block.size()-1);
		                initialNode = block.get(block.size()-2);
		            }
		            		            
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
		            IOperation finalNode1 = block.get(1);
		            OperationIndexVO initialOperationIndex = new OperationIndexVO(initialNode1.getOperationIndex().getJobId(), initialNode1.getOperationIndex().getStationId());
		            OperationIndexVO finalOperationIndex = new OperationIndexVO(finalNode1.getOperationIndex().getJobId(), finalNode1.getOperationIndex().getStationId());
		            
		            PairVO temp = new PairVO(initialOperationIndex, finalOperationIndex);
		            neighborhood.add(temp);
		            
		            IOperation    finalNode= block.get(block.size()-1);
		            IOperation    initialNode = block.get(block.size()-2);
		            initialOperationIndex = new OperationIndexVO(initialNode.getOperationIndex().getJobId(), initialNode.getOperationIndex().getStationId());
		            finalOperationIndex = new OperationIndexVO(finalNode.getOperationIndex().getJobId(), finalNode.getOperationIndex().getStationId());
		            temp = new PairVO(initialOperationIndex, finalOperationIndex);
		            neighborhood.add(temp);
		            
	        	}
	        	
	        }

			return neighborhood;
		}
	
}
