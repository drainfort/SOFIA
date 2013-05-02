package vector.scenarios;

import structure.impl.Operation;
import structure.impl.Vector;

/**
 * Class that is able to build a simple scenario for testing the vector structure
 * @author Rubby Casallas
 * @author David Mendez
 */
public class VectorScenariosFactory {

	// -----------------------------------------------------------
	// Methods
	// -----------------------------------------------------------
	
	/**
	 * Returns a simple vector with a simple scenario for testing the
	 * functionality of the vector
	 * 
	 * @return vector
	 */
	public static Vector buildDummySolution(){
		Vector vector = new Vector(3, 3);
		
		Operation o1 = new Operation(0, 0, 0);
		vector.scheduleOperation(o1.getOperationIndex());
		
		Operation o2 = new Operation(0, 0, 1);
		vector.scheduleOperation(o2.getOperationIndex());
		
		Operation o3 = new Operation(0, 1, 0);
		vector.scheduleOperation(o3.getOperationIndex());
		
		Operation o4 = new Operation(0, 1, 1);
		vector.scheduleOperation(o4.getOperationIndex());
		
		return vector;
	}
}
