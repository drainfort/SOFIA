package neighborCalculator.tests;

import org.junit.Test;
import choco.Choco;
import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.kernel.model.Model;
import choco.kernel.model.variables.integer.IntegerVariable;
import choco.kernel.solver.Solver;

public class Test_ChocoNTH {

	@Test
	public void testChocoNTH() {
		Model m = new CPModel();
		Solver s = new CPSolver();
		int[][] values = new int[][] { { 1, 2, 0, 4, 5 }, { 2, 1, 0, 3, 42 },
				{ 6, 1, -7, 4, -40 }, { -1, 0, 6, 2, -33 }, { 2, 3, 0, -1, 49 } };
		IntegerVariable index1 = Choco.makeIntVar("index1", -3, 10);
		IntegerVariable index2 = Choco.makeIntVar("index2", -3, 10);
		IntegerVariable var = Choco.makeIntVar("value", -20, 20);
		m.addConstraint(Choco.nth(index1, index2, values, var));
		s.read(m);
		s.solveAll();
		System.out.println(s.getVar(var).getVal());
	}
}