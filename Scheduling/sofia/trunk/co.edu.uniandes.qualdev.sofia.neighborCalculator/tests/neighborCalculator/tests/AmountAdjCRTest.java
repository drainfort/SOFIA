package neighborCalculator.tests;

import java.util.ArrayList;

import neighborCalculator.impl.AdjacentShiftOnCriticalRoutes;
import neighborCalculator.impl.ShiftBlockAdjOnEnds;
import neighborCalculator.impl.ShiftBlockEndStartAnyCriticalRoute;

import org.junit.Before;
import org.junit.Test;

import structure.factory.impl.VectorFactory;
import structure.impl.Vector;

import common.types.BetaVO;
import common.types.OperationIndexVO;
import common.utils.MatrixUtils;
import static org.junit.Assert.*;

public class AmountAdjCRTest {

	Vector vector;
	private static AdjacentShiftOnCriticalRoutes neighbor = new AdjacentShiftOnCriticalRoutes();
	private static ShiftBlockAdjOnEnds neighbor2 = new ShiftBlockAdjOnEnds();
	private static ShiftBlockEndStartAnyCriticalRoute neighbor3 = new ShiftBlockEndStartAnyCriticalRoute();
	
	@Before
	public void setupScenario() throws Exception {

		ArrayList<String> problemFiles = new ArrayList<String>();

		String TFile = "./data/T-10x10-01.txt";
		Integer[][] T = MatrixUtils.loadMatrix(TFile);
		
		String TTFile = "./data/TT-10x10-01.txt";
		Integer[][] TT = MatrixUtils.loadMatrix(TTFile);
		
		problemFiles.add(TFile);
		problemFiles.add(TTFile);

		ArrayList<BetaVO> betas = new ArrayList<BetaVO>();
		ArrayList<String> informationFiles = new ArrayList<String>();
		informationFiles.add(TTFile);

		BetaVO TTBeta = new BetaVO("TravelTimes", "beta.impl.TravelTimes", informationFiles, true);
		BetaVO TearDownTT = new BetaVO("TearDownTravelTime", "beta.impl.TearDownTravelTime", informationFiles, true);
		betas.add(TTBeta);
		betas.add(TearDownTT);

		vector = (Vector) VectorFactory.createNewInstance(
				"structure.factory.impl.VectorFactory").createSolutionStructure(problemFiles, betas);
		
		for(int i=0; i<10;i++){
			for(int j=0; j<10;j++){
				vector.scheduleOperation(new OperationIndexVO(i, j));
			}
		}
				
	}
	
	@Test
	public void testCalculateAmountNeighbors(){
		
		try {
			//Numero de parejas adjacentes en un ruta critica es necesariamente el tamaño del arreglo -1
			//Si se tiene un ruta 4 de nodos seria 1-2, 2-3, 3-4. (3 posibles vecinos)
			int number = vector.getCriticalPaths().get(0).getRoute().size()-1;
			assertTrue(number==neighbor.calculateNeighborhood(vector, 100).size());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCalculateAmountNeighbors2(){
		
		try {
			
			int number = vector.getCriticalPaths().get(0).getBlocks().size()*2;
			assertTrue(number==neighbor2.calculateNeighborhood(vector, 100).size());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCalculateAmountNeighbors3(){
		
		try {
			int number =0; 
			System.out.println(vector.getCriticalPaths().get(0).getBlocks());
			for( int i=0; i<vector.getCriticalPaths().get(0).getBlocks().size();i++){
				number+= (vector.getCriticalPaths().get(0).getBlocks().get(i).size()-1)*2;
			}
			System.out.println(number);
			System.out.println(neighbor3.calculateNeighborhood(vector, 200).size());
			assertTrue(number==neighbor3.calculateCompleteNeighborhood(vector).size());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}


