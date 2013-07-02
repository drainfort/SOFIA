package vector.testcases;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import structure.IOperation;
import structure.IStructure;
import structure.factory.AbstractStructureFactory;

import common.types.BetaVO;
import common.types.OperationIndexVO;
import common.utils.MatrixUtils;

//TODO ¿Qué vamos a hacer con este test?
public class Test_ValidateSolution {

//	// ----------------------------------------
//		// Attributes
//		// ----------------------------------------
//
//		private Vector vector;
//
//		// ----------------------------------------
//		// Setup scenarios
//		// ----------------------------------------
//
//		@Before
//		public void setupScenario() throws Exception {
//
//			ArrayList<String> problemFiles = new ArrayList<String>();
//
//			String TFile = "./data/T-04x04-01.txt";
//			Integer[][] T = MatrixUtils.loadMatrix(TFile);
//			
//			String TTFile = "./data/TT-04x04-01.txt";
//			Integer[][] TT = MatrixUtils.loadMatrix(TTFile);
//			
//			problemFiles.add(TFile);
//			problemFiles.add(TTFile);
//
//			ArrayList<BetaVO> betas = new ArrayList<BetaVO>();
//			ArrayList<String> informationFiles = new ArrayList<String>();
//			informationFiles.add(TTFile);
//
//			BetaVO TTBeta = new BetaVO("TravelTimes", "beta.TravelTimes",
//					informationFiles);
//			BetaVO TearDownTT = new BetaVO("TearDownTravelTime",
//					"beta.TearDownTravelTime", informationFiles);
//			betas.add(TTBeta);
//			betas.add(TearDownTT);
//
//			vector = (Vector) VectorFactory.createNewInstance(
//					"vector.factory.VectorFactory").createSolutionStructure(problemFiles, betas);
//			
//			for(int i=0; i<T.length;i++){
//				for(int j=0; j<T[i].length;j++){
//					Operation o1= new Operation(T[j][i], j, i);
//					vector.addOperation(o1);
//				}
//			}
//			
//			
//		}
//		
//		@Test
//		public void testCalculateCMatrix(){
//			System.out.println(validateSolution(vector));
//		}
//		
//		private boolean validateSolution(IVector S0){
//			
//			int [][] initialTimeMatrix = S0.calculateInitialTimesMatrix();
//			int [][][] finalTimeMatrix = S0.calculateCMatrix();
//			
//			for(int i=0; i<S0.getTotalJobs();i++){
//				ArrayList<IOperation> job = new ArrayList<IOperation>();
//				for(IOperation operation : S0.getVector()){
//					if(operation.getOperationIndex().getJobId()==i){
//						job.add(operation);
//					}
//				}
//				IOperation firstOperation = job.get(0);
//				int initialTime = initialTimeMatrix[firstOperation.getOperationIndex().getJobId()][firstOperation.getOperationIndex().getStationId()];
//				int traveltime = S0.getTT(-1,firstOperation.getOperationIndex().getStationId());
//				if(traveltime> initialTime){
//					return false;
//				}
//
//				for(int j =0; j<job.size()-1;j++){
//					firstOperation = job.get(j);
//					IOperation secondOperation = job.get(j+1);
//					int finalTime1 = finalTimeMatrix[firstOperation.getOperationIndex().getJobId()][firstOperation.getOperationIndex().getStationId()][firstOperation.getOperationIndex().getMachineId()];
//					traveltime = S0.getTT(firstOperation.getOperationIndex().getStationId(),secondOperation.getOperationIndex().getStationId());
//					int intalTime2 = initialTimeMatrix[secondOperation.getOperationIndex().getJobId()][secondOperation.getOperationIndex().getStationId()];
//					if(finalTime1+traveltime>intalTime2){
//						return false;
//					}
//				}
//				
//			}
//			
//			for(int i=0; i<S0.getTotalStations();i++){
//				ArrayList<IOperation> station = new ArrayList<IOperation>();
//				for(IOperation operation : S0.getVector()){
//					if(operation.getOperationIndex().getStationId()==i){
//						station.add(operation);
//					}
//				}
//
//				for(int j =0; j<station.size()-1;j++){
//					IOperation firstOperation = station.get(j);
//					IOperation secondOperation = station.get(j+1);
//					int finalTime1 = finalTimeMatrix[firstOperation.getOperationIndex().getJobId()][firstOperation.getOperationIndex().getStationId()][firstOperation.getOperationIndex().getMachineId()];
//					int intalTime2 = initialTimeMatrix[secondOperation.getOperationIndex().getJobId()][secondOperation.getOperationIndex().getStationId()];
//					if(finalTime1>intalTime2){
//						return false;
//					}
//				}
//				
//			}
//			
//			return true;
//		}
//	
}
