package instancesgenerator;

/**
 * Class that represents an interval for the instances generation
 * @author David Mendez-Acuna
 */
public class Interval {

	// -----------------------------------------------------
	// Attributes
	// -----------------------------------------------------
	
	private int lowerBound;
	
	private int upperBound;
	
	// -----------------------------------------------------
	// Constructor
	// -----------------------------------------------------
	
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
