package vector.testcases;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import common.types.OperationIndexVO;
import structure.impl.Vector;
import vector.scenarios.VectorScenariosFactory;

/**
 * Test for the insertAfter functionality of the permutation list
 * @author David Mendez-Acuna
 */
public class Test_InsertOperationAfter {

	// ----------------------------------------
	// Attributes
	// ----------------------------------------
	
	private Vector vector;
	
	// ----------------------------------------
	// Setup scenarios
	// ----------------------------------------
	
	@Before
	public void setupScenario(){
		vector = VectorScenariosFactory.buildDummySolution();
	}
	
	// ----------------------------------------
	// Test cases
	// ----------------------------------------
	
	@Test
	public void testInsertOperationAfterByPosition0(){
		// Initial vector: <0,0>, <0,1>, <1,0>, <1,1>
		vector.insertOperationAfter(3, 3);
		
		// Testing final vector: <0,1>, <1,0>, <1,1>, <0,0>
		Assert.assertEquals(0, vector.getPosition(0).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(0).getOperationIndex().getStationId());
		
		Assert.assertEquals(0, vector.getPosition(1).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(1).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(2).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(2).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(3).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(3).getOperationIndex().getStationId());
	}
	
	@Test
	public void testInsertOperationAfterByOperationIndex0(){
		// Initial vector: <0,0>, <0,1>, <1,0>, <1,1>
		vector.insertOperationAfter(new OperationIndexVO(3, 3), new OperationIndexVO(3, 3));
		
		// Testing final vector: <0,1>, <1,0>, <1,1>, <0,0>
		Assert.assertEquals(0, vector.getPosition(0).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(0).getOperationIndex().getStationId());
		
		Assert.assertEquals(0, vector.getPosition(1).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(1).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(2).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(2).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(3).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(3).getOperationIndex().getStationId());
	}
	
	@Test
	public void testInsertOperationAfterByPosition1(){
		// Initial vector: <0,0>, <0,1>, <1,0>, <1,1>
		vector.insertOperationAfter(3, 0);
		
		// Testing final vector: <0,1>, <1,0>, <1,1>, <0,0>
		Assert.assertEquals(0, vector.getPosition(0).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(0).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(1).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(1).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(2).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(2).getOperationIndex().getStationId());
		
		Assert.assertEquals(0, vector.getPosition(3).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(3).getOperationIndex().getStationId());
	}

	@Test
	public void testInsertOperationAfterByOperationIndex1(){
		// Initial vector: <0,0>, <0,1>, <1,0>, <1,1>
		vector.insertOperationAfter(new OperationIndexVO(1, 1), new OperationIndexVO(0, 0));
		
		// Testing final vector: <0,1>, <1,0>, <1,1>, <0,0>
		Assert.assertEquals(0, vector.getPosition(0).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(0).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(1).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(1).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(2).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(2).getOperationIndex().getStationId());
		
		Assert.assertEquals(0, vector.getPosition(3).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(3).getOperationIndex().getStationId());
	}

	@Test
	public void testInsertOperationAfterByPosition2(){
		// Initial vector: <0,0>, <0,1>, <1,0>, <1,1>
		vector.insertOperationAfter(1, 2);
		
		// Testing final vector: <0,0>, <0,1>, <1,0>, <1,1>
		Assert.assertEquals(0, vector.getPosition(0).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(0).getOperationIndex().getStationId());
		
		Assert.assertEquals(0, vector.getPosition(1).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(1).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(2).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(2).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(3).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(3).getOperationIndex().getStationId());
	}
	
	@Test
	public void testInsertOperationAfterByOperationIndex2(){
		// Initial vector: <0,0>, <0,1>, <1,0>, <1,1>
		vector.insertOperationAfter(new OperationIndexVO(0, 1), new OperationIndexVO(1, 0));
		
		// Testing final vector: <0,0>, <0,1>, <1,0>, <1,1>
		Assert.assertEquals(0, vector.getPosition(0).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(0).getOperationIndex().getStationId());
		
		Assert.assertEquals(0, vector.getPosition(1).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(1).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(2).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(2).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(3).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(3).getOperationIndex().getStationId());
	}
	
	@Test
	public void testInsertOperationAfterByPosition3(){
		// Initial vector: <0,0>, <0,1>, <1,0>, <1,1>
		vector.insertOperationAfter(2, 1);
		
		// Testing final vector: <0,0>, <1,0>, <0,1>, <1,1>
		Assert.assertEquals(0, vector.getPosition(0).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(0).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(1).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(1).getOperationIndex().getStationId());
		
		Assert.assertEquals(0, vector.getPosition(2).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(2).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(3).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(3).getOperationIndex().getStationId());
	}
	
	@Test
	public void testInsertOperationAfterByOperationIndex3(){
		// Initial vector: <0,0>, <0,1>, <1,0>, <1,1>
		vector.insertOperationAfter(new OperationIndexVO(1, 0), new OperationIndexVO(0, 1));
		
		// Testing final vector: <0,0>, <1,0>, <0,1>, <1,1>
		Assert.assertEquals(0, vector.getPosition(0).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(0).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(1).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(1).getOperationIndex().getStationId());
		
		Assert.assertEquals(0, vector.getPosition(2).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(2).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(3).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(3).getOperationIndex().getStationId());
	}
	
	@Test
	public void testInsertOperationAfterByPosition4(){
		// Initial vector: <0,0>, <0,1>, <1,0>, <1,1>
		vector.insertOperationAfter(0, 3);
		
		// Testing final vector: <0,0>, <1,1>, <0,1>, <1,0>
		Assert.assertEquals(0, vector.getPosition(0).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(0).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(1).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(1).getOperationIndex().getStationId());
		
		Assert.assertEquals(0, vector.getPosition(2).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(2).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(3).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(3).getOperationIndex().getStationId());
	}
	
	@Test
	public void testInsertOperationAfterByOperationIndex4(){
		// Initial vector: <0,0>, <0,1>, <1,0>, <1,1>
		vector.insertOperationAfter(new OperationIndexVO(0, 0), new OperationIndexVO(1, 1));
		
		// Testing final vector: <0,0>, <1,1>, <0,1>, <1,0>
		Assert.assertEquals(0, vector.getPosition(0).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(0).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(1).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(1).getOperationIndex().getStationId());
		
		Assert.assertEquals(0, vector.getPosition(2).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(2).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(3).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(3).getOperationIndex().getStationId());
	}
	
	@Test
	public void testInsertOperationAfterByPosition5(){
		// Initial vector: <0,0>, <0,1>, <1,0>, <1,1>
		vector.insertOperationAfter(2, 3);
		
		// Testing final vector: <0,0>, <0,1>, <1,0>, <1,1>
		Assert.assertEquals(0, vector.getPosition(0).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(0).getOperationIndex().getStationId());
		
		Assert.assertEquals(0, vector.getPosition(1).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(1).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(2).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(2).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(3).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(3).getOperationIndex().getStationId());
	}
	
	@Test
	public void testInsertOperationAfterByOperationIndex5(){
		// Initial vector: <0,0>, <0,1>, <1,0>, <1,1>
		vector.insertOperationAfter(new OperationIndexVO(1, 0), new OperationIndexVO(1, 1));
		
		// Testing final vector: <0,0>, <0,1>, <1,0>, <1,1>
		Assert.assertEquals(0, vector.getPosition(0).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(0).getOperationIndex().getStationId());
		
		Assert.assertEquals(0, vector.getPosition(1).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(1).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(2).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(2).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(3).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(3).getOperationIndex().getStationId());
	}
}