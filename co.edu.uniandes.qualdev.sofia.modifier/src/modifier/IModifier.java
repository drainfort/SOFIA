package modifier;

import common.types.PairVO;

import structure.IStructure;

/**
 * Interface for the modifier of the data structure
 * 
 * @author David Mendez-Acuna
 * @author Jaime Romero
 */
public interface IModifier {

	/**
	 * Perform the modification of the candidate pair and the structure
	 * @param movement - candidate pair
	 * @param structure - current structure that is going to be modified
	 * @return structure - new structure after the modification
	 * @throws Exception
	 */
	public IStructure performModification(PairVO movement, IStructure structure) throws Exception;
}
