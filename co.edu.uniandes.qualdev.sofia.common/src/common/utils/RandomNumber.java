package common.utils;

import java.util.Random;

public class RandomNumber {
	
	public static RandomNumber number;

	private Random randomNumber;
	
	private RandomNumber(int seed){
		randomNumber = new Random(seed);
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
	public int randomNumber(int min, int max) {
		int a = randomNumber.nextInt(max);
		return a;
	}
	
	public double randomDouble(){
		return randomNumber.nextDouble();
	}
	
	public static RandomNumber getInstance(){
		if(number==null){
			number = new RandomNumber(0);
		}
		return number;
	}
}
