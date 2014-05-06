package structure.impl.decoding;

import java.util.ArrayList;
import java.util.Random;

import structure.IOperation;

public class RandomDecoding extends Decoding{
	
	ActiveDecoding active = new ActiveDecoding();
	NonDelayDecoding nonDelay = new NonDelayDecoding();
	SequencialDecoding sequencial = new SequencialDecoding();

	@Override
	public ArrayList<IOperation> decode(ArrayList<IOperation> operations) {
		
		ArrayList<Decoding> decodings = new ArrayList<Decoding>();
		decodings.add(active);
		decodings.add(nonDelay);
		decodings.add(sequencial);
		return decodings.get(randomNumber(0, decodings.size()-1)).decode(operations);
	}

	
	/**
	 * Returns a random number in the interval between the min and the max
	 * parameters
	 * 
	 * @param min
	 *            . Lower value of the interval
	 * @param max
	 * @return
	 */
	private static int randomNumber(int min, int max) {
		Random number = new Random(0);
		return number.nextInt(max)+min;
	}
}
