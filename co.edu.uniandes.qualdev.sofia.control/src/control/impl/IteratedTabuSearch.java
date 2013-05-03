package control.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

import structure.IOperation;
import structure.IStructure;

import common.types.PairVO;
import common.utils.ExecutionResults;
import control.Control;

import modifier.IModifier;
import neighborCalculator.INeighborCalculator;
import gammaCalculator.IGammaCalculator;

public class IteratedTabuSearch extends Control {

	// -----------------------------------------------
	// Constructor
	// -----------------------------------------------

	public IteratedTabuSearch() {
		super();
	}

	// -----------------------------------------------
	// Methods
	// -----------------------------------------------

	@Override
	public ExecutionResults execute(IStructure So,
			INeighborCalculator neighborCalculator, IModifier modifier,
			IGammaCalculator gammaCalculator, Properties params, Integer optimal, int yuSolution)
			throws Exception {
		
		executionResults.setOptimal(optimal);
		
		// So		So - initial solution
		// GammaSo	f(So) - objective function
		// Sb		S* - best solution known
		// Sg		S^ - current solution
		
		this.So = So.cloneStructure();
		int GammaSo = gammaCalculator.calculateGamma(So);
		System.out.println("initial solution: " + GammaSo);
		executionResults.setInitialCmax(GammaSo);
		
		boolean optimalAchieved = false;
		IStructure Sb = null;
		int GammaSb = 0;
		
		if (optimal.intValue() >= GammaSo) {
			System.out.println("Optimal found in the constructive phase!");
			optimalAchieved = true;
			Sb = So.cloneStructure();
			GammaSb = gammaCalculator.calculateGamma(Sb);
		}

		if(!optimalAchieved){
			IStructure Sg = improveByTabuSearch(So, neighborCalculator, modifier, gammaCalculator, params, optimal, yuSolution, GammaSo);
			int GammaSg = gammaCalculator.calculateGamma(Sg);
			
			if (optimal.intValue() >= GammaSg) {
				Sb = Sg.cloneStructure();
				GammaSb = GammaSg;
				
				System.out.println("Optimal found!");
				optimalAchieved = true;
			}
			
			Sb = Sg.cloneStructure();
			GammaSb = gammaCalculator.calculateGamma(Sb);
			System.out.println("Improvement: " + GammaSb);
			
			int iter = 10;
			while(iter > 0 && !optimalAchieved){
				IStructure Sa = perturbate(Sg);
				
				System.out.println();
				System.out.println("Perturbation** " + gammaCalculator.calculateGamma(Sa));
				
				IStructure Sv = improveByTabuSearch(Sa, neighborCalculator, modifier, gammaCalculator, params, optimal, yuSolution, GammaSg);
				
				Sg = Sv.cloneStructure();
				GammaSg = gammaCalculator.calculateGamma(Sg);
				
				if (optimal.intValue() >= GammaSg) {
					Sb = Sg.cloneStructure();
					GammaSb = GammaSg;
					System.out.println("Optimal found!");
					optimalAchieved = true;
					break;
				}
				if(GammaSg < GammaSb){
					Sb = Sv.cloneStructure();
					GammaSb = GammaSg;
				}
				iter--;
				Sa.clean();
			}
		}
		
		ExecutionResults result = obtainExecutionResults(Sb, gammaCalculator, (Boolean)params.get("printTable"), (Boolean)params.get("printSolutions"),(Boolean)params.get("printInitialSolution"));
		System.out.println();
		return result;
	}

	/**
	 * Improvement procedure: Tabu search
	 */
	private IStructure improveByTabuSearch(IStructure Sa,
			INeighborCalculator neighborCalculator, IModifier modifier,
			IGammaCalculator gammaCalculator, Properties params, Integer optimal, int yuSolution, int GammaInitialSolution)
			throws Exception{
		executionResults.setOptimal(optimal);
		
		// Sa		S' - solution to improve
		// nm		n * m - vector size
		// Ts		tabu list size
		// Sb		Best solution known
		// Skb		Best candidate solution
		// Sk		Candidate solution
		
		int nm = Sa.getVector().size();
		int Ts = nm;

		IStructure Sb = Sa.cloneStructure();
		int GammaSb = gammaCalculator.calculateGamma(Sb);

		boolean optimalAchieved = false;

		ArrayList<PairVO> arrayTabu = new ArrayList<PairVO>();
		int tabuIndex = 0;

		// Parameters loading
		int iterations = nm * 1000;
		int nonimproving = (int) (iterations * (Double) params.get("non-improving"));
		int maxNumberImprovements = 0;
		double percent =(Double) params.get("percent");
		int neighborhodSize = (int) ((nm * (nm - 1)) / 2 * percent);
		
		if(params.get("maxNumberImprovements")!=null){
			maxNumberImprovements = (Integer)params.get("maxNumberImprovements");
		}

		ArrayList<PairVO> arrayNeighbors = neighborCalculator.calculateNeighborhood(Sa, neighborhodSize);

		while (iterations >= 0 && nonimproving >= 0 && !optimalAchieved) {
			IStructure Skb = null;
			int GammaSkb = Integer.MAX_VALUE;
			PairVO PairSkb = null;

			// Scanning the neighborhood to find the best candidate solution
			for (int index = 0; index < arrayNeighbors.size() && !optimalAchieved; index++) {
				PairVO PairSk = arrayNeighbors.get(index);
				IStructure Sk = modifier.performModification(PairSk, Sa);
				int GammaSk = gammaCalculator.calculateGamma(Sk);

				if (GammaSk <= GammaSkb) {
					boolean tabu = false;
					for (int i = 0; i < arrayTabu.size() && !tabu; i++) {
						PairVO PairTk = arrayTabu.get(i);
						
						if (PairTk.equals(PairSk))
							tabu = true;
					}
					if (tabu) {
						// Aspiration criterion
						double acceptaceValue = Math.random();
						if (acceptaceValue <= 0.5) {
							Skb = Sk.cloneStructure();
							GammaSkb = GammaSk;
							PairSkb = PairSk;
						}
					} else {
						Skb = Sk.cloneStructure();
						GammaSkb = GammaSk;
						PairSkb = PairSk;
					}
				}
			}

			// If there is a best candidate
			if (Skb != null) {
				if (GammaSkb < GammaSb) {
					Sb = Skb.cloneStructure();
					GammaSb = GammaSkb;
					nonimproving = (int) (iterations * (Double) params.get("non-improving"));
					System.out.println("Improvement: " + GammaSb);
					
					if (optimal.intValue() >= GammaSb) {
						optimalAchieved = true;
						break;
					}
					
					if( maxNumberImprovements != 0){
						if(yuSolution>=GammaSb){
							maxNumberImprovements--;
							if(maxNumberImprovements<=0){
								System.out.println("Yu improved " + (Integer)params.get("maxNumberImprovements") + " times during iterative phase!");
								optimalAchieved = true;
							}
						}
					}
				}
				Sa = Skb.cloneStructure();

				if (tabuIndex > Ts) {
					arrayTabu.remove(0);
				}
				arrayTabu.add(PairSkb);
				tabuIndex++;

			}
			// Avance while
			iterations--;
			nonimproving--;
			arrayNeighbors = neighborCalculator.calculateNeighborhood(Sa, (Sa.getTotalStations() * Sa.getTotalJobs()) - 1);
		}
		return Sb;
	}
	
	private IStructure perturbate(IStructure Sg) throws Exception {
		IStructure Sa = Sg.cloneStructure();
		ArrayList<IOperation> operations = Sa.getOperations();
		
		int pi = randomNumber((int) (Sa.getVector().size()/2), (int) Sa.getVector().size()-1);
		ArrayList<IOperation> toStruggle = new ArrayList<IOperation>();
		for (int i = pi; i < Sg.getVector().size(); i++) {
			toStruggle.add(operations.get(i));
		}
		
		for (int i = Sg.getVector().size() - 1; i >= pi; i--) {
			Sa.removeOperationFromSchedule(operations.get(i).getOperationIndex());
		}
		
		Collections.shuffle(toStruggle);
		
		for (int i = 0; i < toStruggle.size(); i++) {
			Sa.scheduleOperation(toStruggle.get(i).getOperationIndex());
		}
		Sa.calculateCMatrix();
		
		return Sa;
	}
	
	// -----------------------------------------------
	// Utilities
	// -----------------------------------------------
	
	/**
	 * Returns a random number in the interval between the min and the max
	 * parameters
	 * 
	 * @param min
	 *            . Lower value of the interval
	 * @param max
	 * @return
	 */
	private static int randomNumber(int min, int max) {
		return (int) Math.round((Math.random() * (max - min)) + min);
	}
}