package modifier.impl;

import structure.IStructure;
import structure.impl.Graph;

import common.types.PairVO;

import modifier.IModifier;

/**
 * Implementation of the reversal procedure in the vector
 * @author Vanessa Londoño
 * @author Jaime Romero
 */
public class Reversal implements IModifier{

	// -----------------------------------------------
		// Constructor
		// -----------------------------------------------

		public Reversal() {

		}

		// -----------------------------------------------
		// Methods
		// -----------------------------------------------

		@Override
		public IStructure performModification(PairVO movement, IStructure currentVector) throws Exception {
			IStructure vector = currentVector.cloneStructure();
			vector.reverseOperationsBetween(movement.getoX(), movement.getoY());
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

