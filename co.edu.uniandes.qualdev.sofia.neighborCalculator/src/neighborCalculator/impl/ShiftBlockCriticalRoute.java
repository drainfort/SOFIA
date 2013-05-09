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
public class ShiftBlockCriticalRoute implements INeighborCalculator {

	// -----------------------------------------------
	// Attributes
	// -----------------------------------------------

	private OperationIndexVO randomA;
	private OperationIndexVO randomB;
	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	public ShiftBlockCriticalRoute() {

	}

	// -----------------------------------------------
	// Methods
	// -----------------------------------------------

	
	@Override
	public PairVO calculateNeighbor(IStructure currentGraph) throws Exception {
		IStructure clone = currentGraph.cloneStructure();
		ArrayList<CriticalRoute> routes = clone.getLongestRoutes();
		
		int number = randomNumber(0, routes.size() - 1);
		ArrayList<ArrayList<IOperation>> blocks = routes.get(number).getBlocks();
			
		number = randomNumber(0, blocks.size() - 1);
	    ArrayList<IOperation> selectedBlock = blocks.get(number);
		
		int j = randomNumber(0, 1);
		IOperation initialNode = selectedBlock.get(j);
		
		if(j==1)
			initialNode= selectedBlock.get(selectedBlock.size()-1);
		
		int i = randomNumber(1, selectedBlock.size()-1);
		IOperation finalNode = selectedBlock.get(i);

		OperationIndexVO initialOperationIndex = new OperationIndexVO(initialNode.getOperationIndex().getJobId(), initialNode.getOperationIndex().getStationId());
		OperationIndexVO finalOperationIndex = new OperationIndexVO(finalNode.getOperationIndex().getJobId(), finalNode.getOperationIndex().getStationId());
		
		return new PairVO(initialOperationIndex,finalOperationIndex);
		
	}

	@Override
	public ArrayList<PairVO> calculateNeighborhood(IStructure currentGraph, int size)
			throws Exception {
		
		ArrayList<PairVO> neighborhood = new ArrayList<PairVO>();
		IStructure clone = currentGraph.cloneStructure();
		ArrayList<CriticalRoute> routes = clone.getLongestRoutes();
		int salida=0;
		int number = randomNumber(0, routes.size() - 1);
		
		
		while(neighborhood.size()<size){
			
			for(int i=0; i< routes.size();i++){
				ArrayList<ArrayList<IOperation>> blocks = routes.get(i).getBlocks();
				for(int z=0; z<blocks.size();z++){
					ArrayList<IOperation> selectedBlock = blocks.get(z);
					System.out.println(selectedBlock);
					if(selectedBlock.size()>2){
						for(int j=1; j<selectedBlock.size()-1;i++){
							
							IOperation initial= selectedBlock.get(0);
							IOperation finalOperation = selectedBlock.get(selectedBlock.size()-1);
							IOperation temp = selectedBlock.get(j);
							
							PairVO newPair = new PairVO(initial.getOperationIndex(), temp.getOperationIndex());
							PairVO newPair2 = new PairVO(finalOperation.getOperationIndex(), temp.getOperationIndex());
							if(!neighborhood.contains(newPair)){
								neighborhood.add(newPair);
								salida=0;
							}					
							if(!neighborhood.contains(newPair2)){
								neighborhood.add(newPair2);
								salida=0;
							}else{
								salida++;
								if(salida>=100)
									return neighborhood;
							}
						}
					}
					else{
						IOperation initial= selectedBlock.get(0);
						IOperation finalOperation = selectedBlock.get(selectedBlock.size()-1);
						PairVO newPair = new PairVO(initial.getOperationIndex(), finalOperation.getOperationIndex());
						
						if(!neighborhood.contains(newPair)){
							neighborhood.add(newPair);
							salida=0;
						}					
						else{
							salida++;
							if(salida>=100)
								return neighborhood;
						}
					}
				}
			}
		}
		
		return neighborhood;
	}
		
	// -----------------------------------------------
	// Utilities
	// -----------------------------------------------
	
	private static int randomNumber(int min, int max) {
		return (int) Math.round((Math.random() * (max - min)) + min);
	}

	
}