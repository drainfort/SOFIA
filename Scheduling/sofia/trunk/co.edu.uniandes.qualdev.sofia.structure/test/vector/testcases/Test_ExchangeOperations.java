package vector.testcases;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import common.types.OperationIndexVO;
import structure.impl.Vector;
import vector.scenarios.VectorScenariosFactory;

/**
 * Test for the exchange operations functionality of the permutation list
 * @author David Mendez-Acuna
 */
public class Test_ExchangeOperations {

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
	public void testExchangeOperationsByPosition1(){
		// Initial vector: <0,0>, <0,1>, <1,0>, <1,1>
		vector.exchangeOperations(0, 2);
		
		// Testing final vector: <1,0>, <0,1>, <0,0>, <1,1>
		Assert.assertEquals(1, vector.getPosition(0).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(0).getOperationIndex().getStationId());
		
		Assert.assertEquals(0, vector.getPosition(1).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(1).getOperationIndex().getStationId());
		
		Assert.assertEquals(0, vector.getPosition(2).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(2).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(3).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(3).getOperationIndex().getStationId());
	}
	
	@Test
	public void testExchangeOperationsByOperationIndex1(){
		// Initial vector: <0,0>, <0,1>, <1,0>, <1,1>
		vector.exchangeOperations(new OperationIndexVO(0, 0), new OperationIndexVO(1, 0));
		
		// Testing final vector: <1,0>, <0,1>, <0,0>, <1,1>
		Assert.assertEquals(1, vector.getPosition(0).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(0).getOperationIndex().getStationId());
		
		Assert.assertEquals(0, vector.getPosition(1).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(1).getOperationIndex().getStationId());
		
		Assert.assertEquals(0, vector.getPosition(2).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(2).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(3).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(3).getOperationIndex().getStationId());
	}
	
	@Test
	public void testExchangeOperationsByPosition2(){
		// Initial vector: <0,0>, <0,1>, <1,0>, <1,1>
		vector.exchangeOperations(0, 3);
		
		// Testing final vector: <1,1>, <0,1>, <1,0>, <0,0>
		Assert.assertEquals(1, vector.getPosition(0).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(0).getOperationIndex().getStationId());
		
		Assert.assertEquals(0, vector.getPosition(1).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(1).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(2).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(2).getOperationIndex().getStationId());
		
		Assert.assertEquals(0, vector.getPosition(3).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(3).getOperationIndex().getStationId());
	}
	
	@Test
	public void testExchangeOperationsByOperationIndex2(){
		// Initial vector: <0,0>, <0,1>, <1,0>, <1,1>
		vector.exchangeOperations(new OperationIndexVO(0, 0), new OperationIndexVO(1, 1));
		
		// Testing final vector: <1,1>, <0,1>, <1,0>, <0,0>
		Assert.assertEquals(1, vector.getPosition(0).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(0).getOperationIndex().getStationId());
		
		Assert.assertEquals(0, vector.getPosition(1).getOperationIndex().getJobId());
		Assert.assertEquals(1, vector.getPosition(1).getOperationIndex().getStationId());
		
		Assert.assertEquals(1, vector.getPosition(2).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(2).getOperationIndex().getStationId());
		
		Assert.assertEquals(0, vector.getPosition(3).getOperationIndex().getJobId());
		Assert.assertEquals(0, vector.getPosition(3).getOperationIndex().getStationId());
	}
	
	@Test
	public void testExchangeOperationsByPosition3(){
		// Initial vector: <0,0>, <0,1>, <1,0>, <1,1>
		vector.exchangeOperations(1, 2);
		
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
	public void testExchangeOperationsByOperationIndex3(){
		// Initial vector: <0,0>, <0,1>, <1,0>, <1,1>
		vector.exchangeOperations(new OperationIndexVO(0, 1), new OperationIndexVO(1, 0));
		
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
}