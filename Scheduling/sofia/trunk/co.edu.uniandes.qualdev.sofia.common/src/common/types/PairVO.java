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
		
		/**Position of the first operation */
		private int x;
		/**Position of the second operation */
		private int y;
		
		/**First operation information */
		private OperationIndexVO oX;
		
		/**Second operation information */
		private OperationIndexVO oY;
		
		/**Objective function after evaluating the neighbor pair*/
		private double gamma;
		
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
			return "<" + oX.toString() + "," + oY.toString() + ">";
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

		public double getGamma() {
			return gamma;
		}

		public void setGamma(double gamma) {
			this.gamma = gamma;
		}
		
}