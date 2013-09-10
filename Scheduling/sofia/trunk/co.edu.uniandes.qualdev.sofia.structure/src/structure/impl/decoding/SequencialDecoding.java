package structure.impl.decoding;

import java.util.ArrayList;

import structure.IOperation;

/**
 * Class that implements a sequencial decoding for permutation lists. 
 * 
 * @author Jaime Romero
 * @author David Mendez-Acuna
 */
public class SequencialDecoding extends Decoding{

	// ––––––––––––––––––––––––––––––––––––––––––––––
	// Concrete methods
	// ––––––––––––––––––––––––––––––––––––––––––––––
	
	@Override
	public ArrayList<IOperation> decode(ArrayList<IOperation> operations) {
		ArrayList<IOperation> sequencialDecoding = operations;
		this.calculateCMatrix(sequencialDecoding);
		return sequencialDecoding;
	}
}
