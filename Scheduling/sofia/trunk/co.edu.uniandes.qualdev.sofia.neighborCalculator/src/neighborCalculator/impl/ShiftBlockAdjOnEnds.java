package neighborCalculator.impl;

import java.util.ArrayList;

import structure.IOperation;
import structure.IStructure;
import structure.impl.CriticalRoute;

import common.types.OperationIndexVO;
import common.types.PairVO;

import neighborCalculator.INeighborCalculator;

public class ShiftBlockAdjOnEnds implements INeighborCalculator{

	// -----------------------------------------------
		// Methods
		// -----------------------------------------------

		@Override
		public PairVO calculateNeighbor(IStructure currentStructure) throws Exception {
			IStructure clone = currentStructure.cloneStructure();
			
			// Obtaining all the critical paths of the current solutions
			ArrayList<CriticalRoute> routes = clone.getLongestRoutes();
			
			int number = randomNumber(0, routes.size() - 1);
            ArrayList<ArrayList<IOperation>> blocks= routes.get(number).getBlocks();
            int number2 = randomNumber(0, blocks.size() - 1);
            ArrayList<IOperation> block = blocks.get(number2);
            int number3= randomNumber(0, 1);
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
		public ArrayList<PairVO> calculateNeighborhood(IStructure currentStructure, int size)
				throws Exception {

			 ArrayList<PairVO> neighborhood = new ArrayList<PairVO>();
		        IStructure clone = currentStructure.cloneStructure();
		        
		        // Obtaining all the critical paths of the current solutions
		        ArrayList<CriticalRoute> routes = clone.getLongestRoutes();
		        
//		        System.out.println(routes);
		        
		        // Selecting one of the critical paths
		        int numberRoutes= routes.size();
//		        System.out.println("rutas:"+numberRoutes);
		        int exit = 0;
		        while(neighborhood.size() < size){
		            
		            int number = randomNumber(0, routes.size() - 1);
		            ArrayList<ArrayList<IOperation>> blocks= routes.get(number).getBlocks();
		            int number2 = randomNumber(0, blocks.size() - 1);
		            ArrayList<IOperation> block = blocks.get(number2);
		            int number3= randomNumber(0, 1);
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

			return neighborhood;
		}
		
		// -----------------------------------------------
		// Utilities
		// -----------------------------------------------
		
		private static int randomNumber(int min, int max) {
			return (int) Math.round((Math.random() * (max - min)) + min);
		}
	
}
