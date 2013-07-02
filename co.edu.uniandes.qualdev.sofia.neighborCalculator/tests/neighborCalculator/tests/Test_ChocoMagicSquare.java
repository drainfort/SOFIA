package neighborCalculator.tests;

import java.text.MessageFormat;

import org.junit.Test;
import choco.Choco;
import choco.cp.model.CPModel;
import choco.cp.solver.CPSolver;
import choco.kernel.model.constraints.Constraint;
import choco.kernel.model.variables.integer.IntegerVariable;

public class Test_ChocoMagicSquare {

	@Test
	public void testChocoMagicSquare() {
		// Constant declaration
		int n = 3; // Order of the magic square
		int magicSum = n * (n * n + 1) / 2; // Magic sum

		CPModel m = new CPModel();

		// Creation of an array of variables
		IntegerVariable[][] var = new IntegerVariable[n][n];
		// For each variable, we define its name and the boundaries of its
		// domain.
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				var[i][j] = Choco.makeIntVar("var_" + i + "_" + j, 1, n * n);
				// Associate the variable to the model.
				m.addVariable(var[i][j]);
			}
		}

		// All cells of the matrix must be different
		for (int i = 0; i < n * n; i++) {
			for (int j = i + 1; j < n * n; j++) {
				Constraint c = (Choco.neq(var[i / n][i % n], var[j / n][j % n]));
				m.addConstraint(c);
			}
		}

		// All row's sum has to be equal to the magic sum
		for (int i = 0; i < n; i++) {
			m.addConstraint(Choco.eq(Choco.sum(var[i]), magicSum));
		}

		IntegerVariable[][] varCol = new IntegerVariable[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				// Copy of var in the column order
				varCol[i][j] = var[j][i];
			}
			// All column's sum is equal to the magic sum
			m.addConstraint(Choco.eq(Choco.sum(varCol[i]), magicSum));
		}

		IntegerVariable[] varDiag1 = new IntegerVariable[n];
		IntegerVariable[] varDiag2 = new IntegerVariable[n];
		for (int i = 0; i < n; i++) {
			varDiag1[i] = var[i][i]; // Copy of var in varDiag1
			varDiag2[i] = var[(n - 1) - i][i]; // Copy of var in varDiag2
		}
		// All diagonal's sum has to be equal to the magic sum
		m.addConstraint(Choco.eq(Choco.sum(varDiag1), magicSum));
		m.addConstraint(Choco.eq(Choco.sum(varDiag2), magicSum));

		// Build the solver
		CPSolver s = new CPSolver();

		// Read the model
		s.read(m);
		// Solve the model
		s.solve();
		// Print the solution
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(MessageFormat.format("{0} ",
						s.getVar(var[i][j]).getVal()));
			}
			System.out.println();
		}
	}
}