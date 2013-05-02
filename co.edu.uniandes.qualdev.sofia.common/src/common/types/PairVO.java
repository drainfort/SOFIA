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
		boolean answer=false;
		
		if(((PairVO)pair).getX()==this.getX()&& ((PairVO)pair).getY()==this.getY())
			answer=true;
		return answer;
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