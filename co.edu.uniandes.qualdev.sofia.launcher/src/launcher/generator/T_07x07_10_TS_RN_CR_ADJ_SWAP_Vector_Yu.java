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
public class T_07x07_10_TS_RN_CR_ADJ_SWAP_Vector_Yu{
	private JUnitLauncher launcher;
	private String algorithmFile;
	private String problemFile;
	private String currentBks;
	private String instanceName;
	private String instanceType;
	public T_07x07_10_TS_RN_CR_ADJ_SWAP_Vector_Yu(String pAlgorithmFile, String pProblemFile, String pCurrentBks, String pInstanceType, String pInstanceName) {
			super();
			algorithmFile = pAlgorithmFile;
			problemFile = pProblemFile;
			currentBks = pCurrentBks;
			instanceName = pInstanceName;
			instanceType = pInstanceType;
	}
	@Parameters
	public static ArrayList<String[]> datos() {
		ArrayList<String[]> datos = new ArrayList<String[]>();
		String[] file1= {
			"./data/Om-TT/Algorithm/Vector/TS_RN_CR_ADJ_SWAP.properties","./data/Om-TT/07x07/07x07_10.properties", "gamma.cmax.bks.om.tt", "Yu","07x07_10"};
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
		ChartPrinter.getInstance().printGlobalResultsHTML("./results/Om_TT/T_07x07_10_TS_RN_CR_ADJ_SWAP_Vector_Yu_consolidated.html");
	}
	public static void main(String[] args) {
 		JUnitCore.main("launcher.generator.T_07x07_10_TS_RN_CR_ADJ_SWAP_Vector_Yu");  
	}
}
