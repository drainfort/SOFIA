package common.types;

public class PairVO {

	// ---------------------------------------
	// Attributes
	// ---------------------------------------
	
	private int x;
	
	private int y;
	
	private OperationIndexVO oX;
	
	private OperationIndexVO oY;
	
	// ---------------------------------------
	// Constructor
	// ---------------------------------------
	
	public PairVO(int x, int y){
		this.x = x;
		this.y = y;
	}

	public PairVO(OperationIndexVO oX, OperationIndexVO oY){
		this.oX = oX;
		this.oY = oY;
	}

	// ---------------------------------------
	// Methods
	// ---------------------------------------
	
	@Override
	public boolean equals(Object pair){
		
		boolean intIndexes = ((PairVO)pair).getX() == this.getX()&& ((PairVO)pair).getY()==this.getY();
		
		boolean OIIndexes = (oX != null && oY != null) ? (oX.getJobId() == ((PairVO)pair).getoX().getJobId() && oX.getStationId() == ((PairVO)pair).getoX().getStationId() && oX.getMachineId() == ((PairVO)pair).getoX().getMachineId() &&
				oY.getJobId() == ((PairVO)pair).getoY().getJobId() && oY.getStationId() == ((PairVO)pair).getoY().getStationId() && oY.getMachineId() == ((PairVO)pair).getoY().getMachineId()) : true;
		
		return intIndexes && OIIndexes;
	}
	
	// ---------------------------------------
	// Getters and setters
	// ---------------------------------------
	
	public String toString(){
		return "<" + x + "," + y + ">";
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
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
}