package instancesgenerator;

/**
 * Class that represents an interval for the instances generation
 * @author David Mendez-Acuna
 */
public class Interval {

	// -----------------------------------------------------
	// Attributes
	// -----------------------------------------------------
	/**
	 * Lower bound of the interval
	 */
	private int lowerBound;
	
	/**
	 * Upper bound of the interval
	 */
	private int upperBound;
	
	// -----------------------------------------------------
	// Constructor
	// -----------------------------------------------------
	
	/**
	 * Constructor of the class
	 * @param lowerBound - lower bound of the interval
	 * @param upperBound - upper bound of the interval
	 */
	public Interval(int lowerBound, int upperBound){
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}

	// -----------------------------------------------------
	// Methods
	// -----------------------------------------------------
	
	public int getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(int lowerBound) {
		this.lowerBound = lowerBound;
	}

	public int getUpperBound() {
		return upperBound;
	}

	public void setUpperBound(int upperBound) {
		this.upperBound = upperBound;
	}
}
