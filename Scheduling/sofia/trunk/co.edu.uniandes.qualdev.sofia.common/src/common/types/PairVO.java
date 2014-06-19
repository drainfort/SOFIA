package common.types;

/**
 * Class that conatins a pair of operations to be modify over the data structure to generate a modification over it.
 * @author Rubby Casallas
 * @author David Méndez Acuña
 * @author Jaime Romero
 * 
 */
public class PairVO {

	// ---------------------------------------
	// Attributes
	// ---------------------------------------

	// First operation index of the pair to evaluate
	private OperationIndexVO oX;
	
	// Second operation index of the pair to evaluate
	private OperationIndexVO oY;
	
	// Objective function after the evaluation of the pair
	private double gamma;
	
	// ---------------------------------------
	// Constructor
	// ---------------------------------------
	
	/**
	 * Constructor of the class
	 * @param oX - first operation index
	 * @param oY - second operation index
	 */
	public PairVO(OperationIndexVO oX, OperationIndexVO oY){
		this.oX = oX;
		this.oY = oY;
	}

	// ---------------------------------------
	// Methods
	// ---------------------------------------
	
	@Override
	public boolean equals(Object pair){
				
		boolean OIIndexes = (oX != null && oY != null) ? (oX.getJobId() == ((PairVO)pair).getoX().getJobId() && oX.getStationId() == ((PairVO)pair).getoX().getStationId() && oX.getMachineId() == ((PairVO)pair).getoX().getMachineId() &&
				oY.getJobId() == ((PairVO)pair).getoY().getJobId() && oY.getStationId() == ((PairVO)pair).getoY().getStationId() && oY.getMachineId() == ((PairVO)pair).getoY().getMachineId()) : true;
		
		return OIIndexes;
	}
	
	// ---------------------------------------
	// Getters and setters
	// ---------------------------------------
	
	public String toString(){
		return "<" + oX.toString() + "," + oY.toString() + ">";
	}

	public OperationIndexVO getoX() {
		return oX;
	}

	public void setoX(OperationIndexVO oX) {
		this.oX = oX;
	}

	public OperationIndexVO getoY() {
		return oY;
	}

	public void setoY(OperationIndexVO oY) {
		this.oY = oY;
	}

	public double getGamma() {
		return gamma;
	}

	public void setGamma(double gamma) {
		this.gamma = gamma;
	}
		
}