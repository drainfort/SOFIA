package control.impl;

import java.util.ArrayList;
import java.util.Properties;

import structure.IStructure;

import common.types.PairVO;
import common.utils.ExecutionResults;
import control.Control;

import modifier.IModifier;
import neighborCalculator.INeighborCalculator;
import gammaCalculator.IGammaCalculator;

public class TabuSearchWithRandomJumps extends Control {

	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	public TabuSearchWithRandomJumps() {
		super();
	}

	// -----------------------------------------------
	// Methods
	// -----------------------------------------------

	@Override
	public ExecutionResults execute(IStructure initialSolution,
			INeighborCalculator neighborCalculator, IModifier modifier,
			IGammaCalculator gammaCalculator, Properties params, Integer optimal, int yuSolution)
			throws Exception {
		
		 int numberOfVisitedNeighbors=0;
		 int GammaInitialSolution = gammaCalculator
		 .calculateGamma(initialSolution);
		 executionResults.setInitialCmax(GammaInitialSolution);
		 this.So = initialSolution.cloneStructure();
		
		 int tabuSize = initialSolution.getTotalJobs()
		 * initialSolution.getTotalStations() - 2;
		
		 ArrayList<Integer> arrayTabu = new ArrayList<Integer>();
		 ArrayList<PairVO> arrayNeighbors = new ArrayList<PairVO>();
		
		 // Provides an initial solution (X) from the problem
		 // TODO SE DEBE CAMBIAR LA FUNCION OBJETIVO
		 IStructure X = initialSolution;
		 int XCMax = gammaCalculator.calculateGamma(X);
		
		 // Initializes the best solution (XBest) as the first one (X)
		 IStructure XBest = X.cloneStructure();
		 int XBestCMax = gammaCalculator.calculateGamma(XBest);
		 System.out.println("initial solution (XBestCMax): " + XBestCMax);
		
		 // Obtaining the parameters from the algorithm configuration.
		 Integer nonImproving = (Integer) params.get("non-improving");
		 executionResults.setOptimal(optimal);
		
		 boolean optimalAchieved = false;
		
		 if (optimal.intValue() >= XBestCMax) {
			 System.out.println("Optimal CMax found!");
			 optimalAchieved = true;
		 }
		
		 int maxIter = 0;
		 int count = 0;
		
		
		 while (maxIter < nonImproving && !optimalAchieved) {
		
			 Integer k = (Integer) params.get("k");

			 while (k > 0 && !optimalAchieved) {

				 // Obtains a next solution (Y) from the current one (X)
				 // TODO AQUI SE GENERAN 200 VECINOS Y SE ESCOGE EL MEJOR DEJANDO
				 // ESTE CAMBIO EN LA LISTA TABU
				 IStructure Y = X.cloneStructure();
				 IStructure BestY = X.cloneStructure();
				 int deltaXY;
				 int deltaBestYY;
				 int BestYCmax = 999999999;
				 int movementX = 0;
				 int movementY = 0;
				 boolean isTabu;

				 int totalJobs = X.getTotalJobs();
				 int totalStations = X.getTotalStations();
				 arrayNeighbors = neighborCalculator.calculateNeighborhood(X, 0,
						 totalJobs * totalStations - 1);

				 for (int i = 0; i < arrayNeighbors.size(); i++) {
					 isTabu = false;
					 PairVO y = arrayNeighbors.get(i);
					 Y = modifier.performModification(y, X);
					 numberOfVisitedNeighbors++;

					 movementX = y.getX();
					 movementY = y.getY();

					 if (arrayTabu.size() != 0) {
						 for (int j = 0; j < arrayTabu.size() && !isTabu; j += 2) {
							 int a = (Integer) arrayTabu.get(j);
							 int b = (Integer) arrayTabu.get(j + 1);
							 if (a == movementX && b == movementY) {
								 isTabu = true;
								 j = arrayTabu.size();
							 }
						 }
					 }

					 // TODO Actualizar el criterio de Aspiracion para poder
					 // evaluar un vecino aun siendo un movimiento Tabu
					 if (!isTabu) {
						 int YCMax = gammaCalculator.calculateGamma(Y);
						 deltaBestYY = YCMax - BestYCmax;
						 if (deltaBestYY < 0) {
							 BestY = Y.cloneStructure();
							 BestYCmax = gammaCalculator.calculateGamma(BestY);
						 }
						 i++;
					 }
				 }
				 // TODO BORRAR System.out.println("bestY: " + BestY.getCMax());
				 deltaXY = BestYCmax - XCMax;

				 // TODO Actualizar el criterio de Diversificacion
				 if (deltaXY > 0) {
					 double acceptaceValue = Math.random();
					 double acceptanceRandom = Math.random();

					 if (acceptanceRandom <= acceptaceValue) {
						 X = Y.cloneStructure();
						 XCMax = gammaCalculator.calculateGamma(X);
						 // TODO BORRAR ESTO PARA PRUEBA
						 // System.out.println("--Aceptado por criterio--: " +
						 // XCMax);

						 if (arrayTabu.size() < tabuSize) {
							 if (movementY < movementX) {
								 arrayTabu.add(movementY);
								 arrayTabu.add(movementX - 1);
							 } else {
								 arrayTabu.add(movementY + 1);
								 arrayTabu.add(movementX);
							 }
						 } else {
							 arrayTabu.remove(0);
							 arrayTabu.remove(1);
							 arrayTabu.add(movementY);
							 arrayTabu.add(movementX + 1);
						 }

					 }
				 } else {
					 X = BestY.cloneStructure();
					 XCMax = gammaCalculator.calculateGamma(X);

					 if (arrayTabu.size() < tabuSize) {
						 arrayTabu.add(movementY);
						 arrayTabu.add(movementX + 1);
					 } else {
						 arrayTabu.remove(0);
						 arrayTabu.remove(1);
						 arrayTabu.add(movementY);
						 arrayTabu.add(movementX + 1);
					 }
				 }

				 if (XCMax < XBestCMax) {
					 XBest = X.cloneStructure();
					 XBestCMax = gammaCalculator.calculateGamma(XBest);
					 maxIter = 0;
					 System.out.println("CMax improvement: " + XBestCMax);

					 if (optimal.intValue() >= XBestCMax) {
						 System.out.println("Optimal CMax found!");
						 optimalAchieved = true;
					 }
				 }
				 count++;
				 k--;
				 Y.clean();
			 }
			 maxIter++;
		 }
		 System.out.println();
		ExecutionResults result = obtainExecutionResults(XBest, gammaCalculator, (Boolean)params.get("printTable"), (Boolean)params.get("printSolutions"),(Boolean)params.get("printInitialSolution"));
		result.setNumberOfVisitedNeighbors(numberOfVisitedNeighbors);
		return result;

	}
}