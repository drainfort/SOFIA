package structure.impl.decoding;

import java.util.ArrayList;
import java.util.Random;

import common.utils.RandomNumber;

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
		int number =RandomNumber.getInstance().randomNumber(0, decodings.size()-1);
		Decoding temp = decodings.get(number);
		temp.setVector(this.vector);
		return temp.decode(operations);
	}

	
}
