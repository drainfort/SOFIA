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
public class modeloParallel{
	private Launcher launcher;
	private String algorithmFile;
	private String problemFile;
	private String currentBks;
	private String instanceName;
	private String instanceType;
	public modeloParallel(String pAlgorithmFile, String pProblemFile, String pCurrentBks, String pInstanceType, String pInstanceName) {
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
			"./data/ParallelMachines/Algorithm/Vector/TS_RN_CR_ADJ_SWAP.properties","./data/FileIndex/04x04x02/04x04x02_01.properties", "gamma.cmax.bks.om.tt", "Parallel", "04x04x02_02"};
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
