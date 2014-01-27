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
 * @author Rubby Casallas
 * @author Lindsay Alvarez
 * @author Oriana Cendales
 */
public class Swap implements IModifier {

	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	public Swap() {

	}

	// -----------------------------------------------
	// Methods
	// -----------------------------------------------

	@Override
	public IStructure performModification(PairVO movement, IStructure currentVector) throws Exception {
		IStructure vector = currentVector.cloneStructure();
		vector.exchangeOperations(movement.getoX(), movement.getoY());
		if(vector instanceof Graph){
			try{
				((Graph) vector).topologicalSort2();
			}
			catch (Exception e)
			{
				return null;
			}
			
		}
			
		return vector;
	}
}
