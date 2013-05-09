package modifier.impl;

import modifier.IModifier;
import common.types.PairVO;

import structure.IStructure;
import structure.impl.Graph;

/**
 * Implementation of the swap at random neighbor using the vector sequence
 * representation introduced by Yu et al. (2010)
 * 
 * @author David Mendez-Acuna
 * @author Lindsay Alvarez
 * @author Oriana Cendales
 * 
 */
public class LeftInsertion implements IModifier {

	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	public LeftInsertion() {

	}

	// -----------------------------------------------
	// Methods
	// -----------------------------------------------

	@Override
	public IStructure performModification(PairVO movement, IStructure currentVector) throws Exception {
		IStructure vector = currentVector.cloneStructure();
		
		vector.insertOperationBefore(movement.getoX(), movement.getoY());
		if(vector.validateStructure()){
			return vector;
		}

		vector.clean();
		return currentVector.cloneStructure();
	}
}
