package modifier.impl;

import structure.IStructure;

import common.types.PairVO;

import modifier.IModifier;

public class Reversal implements IModifier{

	@Override
	public IStructure performModification(PairVO movement, IStructure vector)
			throws Exception {
		IStructure temp = vector.cloneStructure();
		
		return temp;
	}

}
