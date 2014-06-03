package structure.impl.decoding;

import java.util.ArrayList;
import java.util.Random;

import structure.IOperation;

public class RandomDecoding extends Decoding{
	
	public static Random number;
	
	ActiveDecoding active = new ActiveDecoding();
	NonDelayDecoding nonDelay = new NonDelayDecoding();
	SequencialDecoding sequencial = new SequencialDecoding();

	@Override
	public ArrayList<IOperation> decode(ArrayList<IOperation> operations) {
	
		ArrayList<Decoding> decodings = new ArrayList<Decoding>();
		decodings.add(active);
		decodings.add(nonDelay);
		decodings.add(sequencial);
		int number =randomNumber(0, decodings.size()-1);
		Decoding temp = decodings.get(number);
		temp.setVector(this.vector);
		return temp.decode(operations);
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
		//return getInstance().nextInt(max);
		return (int) Math.round((Math.random() * (max - min)) + min);
	}
	
	public static Random getInstance(){
		if(number==null){
			number = new Random();
		}
		return number;
	}
}
