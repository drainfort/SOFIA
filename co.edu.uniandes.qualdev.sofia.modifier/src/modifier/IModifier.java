package modifier;

import common.types.PairVO;

import structure.IStructure;

public interface IModifier {

	public IStructure performModification(PairVO movement, IStructure vector) throws Exception;
}
