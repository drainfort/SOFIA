package launcher.generator;
import java.util.ArrayList;
import launcher.Launcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import common.utils.ExecutionResults;
import chart.printer.ChartPrinter;
@RunWith(Parameterized.class)
public class T_04x04_01_TS_RN_CR_ADJ_SWAP_Vector{
	private Launcher launcher;
	private String algorithmFile;
	private String problemFile;
	private String resultsFile;
	private String instanceName;
	public T_04x04_01_TS_RN_CR_ADJ_SWAP_Vector(String pAlgorithmFile, String pProblemFile, String pResultsFile, String pInstanceName) {
			super();
			algorithmFile = pAlgorithmFile;
			problemFile = pProblemFile;
			resultsFile = pResultsFile;
			instanceName = pInstanceName;
	}
	@Parameters
	public static ArrayList<String[]> datos() {
		ArrayList<String[]> datos = new ArrayList<String[]>();
		String[] file1= {
			"./data/Om-TT/Algorithm/Vector/TS_RN_CR_ADJ_SWAP.properties","./data/Om-TT/04x04/04x04_01.properties", "./results/TT_04x04_01.pdf", "04x04_01"};
		datos.add(file1);
			return datos;
	}
	@Before
	public void setupScenario() {
		launcher = new Launcher();
	}
	@Test
	public void testLaunch() throws Exception {
		try {
			ArrayList<ExecutionResults> results = new ArrayList<ExecutionResults>();
			for (int i = 0; i < 1; i++) {	
				results.add(launcher.launch(algorithmFile,problemFile, resultsFile, instanceName));
			}
			ChartPrinter.getInstance().addResults(results);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@After
	public void tearDown(){
		ChartPrinter.getInstance().printGlobalResultsHTML("./results/Om_TT/T_04x04_01_TS_RN_CR_ADJ_SWAP_Vector_consolidated.html");
	}
	public static void main(String[] args) {
 		JUnitCore.main("launcher.generator.T_04x04_01_TS_RN_CR_ADJ_SWAP_Vector");  
	}
}
