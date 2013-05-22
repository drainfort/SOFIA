package modifier.impl;

import modifier.IModifier;
import common.types.PairVO;
import structure.IStructure;


/**
 * Implementation of the swap at random neighbor using the vector sequence
 * representation introduced by Yu et al. (2010)
 * 
 * @author David Mendez-Acuna
 */
public class RandomNeighbor implements IModifier {

	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	public RandomNeighbor() {

	}

	// -----------------------------------------------
	// Methods
	// -----------------------------------------------

	@Override
	public IStructure performModification(PairVO movement, IStructure currentVector) throws Exception {
		IStructure vector = currentVector.cloneStructure();
		double random = Math.random();
		
		if(random > 0.3){
			vector.exchangeOperations(movement.getoX(), movement.getoY());
		}else if(random <= 0.3 && random > 0.6){
			vector.insertOperationBefore(movement.getoX(), movement.getoY());
		}else{
			vector.insertOperationAfter(movement.getoX(), movement.getoY());
		}
		
		if(vector.validateStructure()){
			return vector;
		}
		
		vector.clean();
		return currentVector.cloneStructure();
	}
}
