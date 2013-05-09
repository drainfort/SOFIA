package modifier.impl;

import java.util.Properties;

import modifier.IModifier;

import common.types.NeighborInformation;
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
public class RightInsertion implements IModifier {

	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	public RightInsertion() {

	}

	// -----------------------------------------------
	// Methods
	// -----------------------------------------------

	@Override
	public IStructure performModification(PairVO movement, IStructure currentVector) throws Exception {
		IStructure vector = currentVector.cloneStructure();
		vector.insertOperationAfter(movement.getoY(), movement.getoX());
		if(vector.validateStructure()){
			return vector;
		}
		vector.clean();
		return currentVector.cloneStructure();
	}
}
