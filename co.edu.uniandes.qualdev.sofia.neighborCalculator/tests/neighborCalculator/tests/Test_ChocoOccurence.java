package neighborCalculator.tests;


import org.junit.Test;
import choco.Choco;
import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.kernel.model.Model;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.solver.Solver;

public class Test_ChocoOccurence {

	@Test
	public void testChocoOccurence() {
		Model m = new CPModel();
		Solver s = new CPSolver();
		int n=7;
		IntegerVariable[] x = Choco.makeIntVarArray("X", n, 0, 10);
		IntegerVariable z = Choco.makeIntVar("Z", 0, 10);
		m.addConstraint(Choco.occurrence(z, x, 3));
		s.read(m);
		s.solve();
		
		System.out.println(s.getVar(z).getVal());
	}
}