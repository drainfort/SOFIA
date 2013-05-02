package vector.testcases;

/**
 * Test for the insertBefore functionality of the permutation list
 * @author David Mendez-Acuna
 */
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import common.types.OperationIndexVO;

import structure.impl.Vector;
import vector.scenarios.VectorScenariosFactory;

public class InsertOperationBeforeTest {

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
	public void testInsertOperationBeforeByPosition1(){
		// Initial vector: <0,0>, <0,1>, <1,0>, <1,1>
		vector.insertOperationBefore(0, 3);
		
		// Testing final vector: <1,1>, <0,0>, <0,1>, <1,0>
		Assert.assertEquals(1, vector.getPosition(0).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(0).getOperationIndex().getStationId());
		
		Assert.assertEquals(0, vector.getPosition(1).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(1).getOperationIndex().getStationId());
		
		Assert.assertEquals(0, vector.getPosition(2).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(2).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(3).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(3).getOperationIndex().getStationId());
	}
	
	@Test
	public void testInsertOperationBeforeByOperationIndex1(){
		// Initial vector: <0,0>, <0,1>, <1,0>, <1,1>
		vector.insertOperationBefore(new OperationIndexVO(0, 0), new OperationIndexVO(1, 1));
		
		// Testing final vector: <1,1>, <0,0>, <0,1>, <1,0>
		Assert.assertEquals(1, vector.getPosition(0).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(0).getOperationIndex().getStationId());
		
		Assert.assertEquals(0, vector.getPosition(1).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(1).getOperationIndex().getStationId());
		
		Assert.assertEquals(0, vector.getPosition(2).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(2).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(3).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(3).getOperationIndex().getStationId());
	}
	
	@Test
	public void testInsertOperationBeforeByPosition2(){
		// Initial vector: <0,0>, <0,1>, <1,0>, <1,1>
		vector.insertOperationBefore(1, 2);
		
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
	public void testInsertOperationBeforeByOperationIndex2(){
		// Initial vector: <0,0>, <0,1>, <1,0>, <1,1>
		vector.insertOperationBefore(new OperationIndexVO(0, 1), new OperationIndexVO(1, 0));
		
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
	public void testInsertOperationBeforeByPosition3(){
		// Initial vector: <0,0>, <0,1>, <1,0>, <1,1>
		vector.insertOperationBefore(3, 0);
		
		// Testing final vector: <0,1>, <1,0>, <0,0>, <1,1>
		Assert.assertEquals(0, vector.getPosition(0).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(0).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(1).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(1).getOperationIndex().getStationId());
		
		Assert.assertEquals(0, vector.getPosition(2).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(2).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(3).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(3).getOperationIndex().getStationId());
	}
	
	@Test
	public void testInsertOperationBeforeByOperationIndex3(){
		// Initial vector: <0,0>, <0,1>, <1,0>, <1,1>
		vector.insertOperationBefore(new OperationIndexVO(1, 1), new OperationIndexVO(0, 0));
		
		// Testing final vector: <0,1>, <1,0>, <0,0>, <1,1>
		Assert.assertEquals(0, vector.getPosition(0).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(0).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(1).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(1).getOperationIndex().getStationId());
		
		Assert.assertEquals(0, vector.getPosition(2).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(2).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(3).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(3).getOperationIndex().getStationId());
	}
	
	@Test
	public void testInsertOperationBeforeByPosition4(){
		// Initial vector: <0,0>, <0,1>, <1,0>, <1,1>
		vector.insertOperationBefore(3, 2);
		
		// Testing final vector: <0,1>, <1,0>, <0,0>, <1,1>
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
	public void testInsertOperationBeforeByOperationIndex4(){
		// Initial vector: <0,0>, <0,1>, <1,0>, <1,1>
		vector.insertOperationBefore(new OperationIndexVO(1, 1), new OperationIndexVO(1, 0));
		
		// Testing final vector: <0,1>, <1,0>, <0,0>, <1,1>
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
