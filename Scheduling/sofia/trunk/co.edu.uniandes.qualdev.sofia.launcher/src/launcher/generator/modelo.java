package launcher.generator;
import java.util.ArrayList;
import launcher.JUnitLauncher;
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
public class modelo{
	private JUnitLauncher launcher;
	private String algorithmFile;
	private String problemFile;
	private String currentBks;
	private String instanceName;
	private String instanceType;
	public modelo(String pAlgorithmFile, String pProblemFile, String pCurrentBks, String pInstanceType, String pInstanceName) {
			super();
			algorithmFile = pAlgorithmFile;
			problemFile = pProblemFile;
			currentBks = pCurrentBks;
			instanceType = pInstanceType;
			instanceName = pInstanceName;
	}
	@Parameters
	public static ArrayList<String[]> datos() {
		ArrayList<String[]> datos = new ArrayList<String[]>();
		String[] file1= {
			"./data/Om-TT/Algorithm/Vector/TS_RN_CR_ADJ_SWAP.properties","./data/Om-TT/04x04/04x04_01.properties", "gamma.cmax.bks.om", "Taillard", "04x04_01"};
		datos.add(file1);
			return datos;
	}
	@Before
	public void setupScenario() {
		launcher = new JUnitLauncher();
	}
	@Test
	public void testLaunch() throws Exception {
		try {
			ArrayList<ExecutionResults> results = new ArrayList<ExecutionResults>();
			for (int i = 0; i < 1; i++) {	
				results.add(launcher.launch(algorithmFile,problemFile, currentBks, instanceType, instanceName));
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
