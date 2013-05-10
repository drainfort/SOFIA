package launcher.graph.om.tt;
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
public class T_TS_CR_BLQ_SWAP_07x07_1_Graph{
	private Launcher launcher;
	private String algorithmFile;
	private String problemFile;
	private String resultsFile;
	private String instanceName;
	public T_TS_CR_BLQ_SWAP_07x07_1_Graph(String pAlgorithmFile, String pProblemFile, String pResultsFile, String pInstanceName) {
			super();
			algorithmFile = pAlgorithmFile;
			problemFile = pProblemFile;
			resultsFile = pResultsFile;
			instanceName = pInstanceName;
	}
	@Parameters
	public static ArrayList<String[]> datos() {
		ArrayList<String[]> datos = new ArrayList<String[]>();
		/*String[] file1= {
			"./data/Om-TT/Algorithm/Vector/TS_CR_BLQ_SWAP.properties","./data/Om-TT/07x07/07x07_01.properties", "./results/TT_07x07_01.pdf", "07x07_01"};
		datos.add(file1);
		String[] file2= {
			"./data/Om-TT/Algorithm/Vector/TS_CR_BLQ_SWAP.properties","./data/Om-TT/07x07/07x07_02.properties", "./results/TT_07x07_02.pdf", "07x07_02"};
		datos.add(file2);
		String[] file3= {
			"./data/Om-TT/Algorithm/Vector/TS_CR_BLQ_SWAP.properties","./data/Om-TT/07x07/07x07_03.properties", "./results/TT_07x07_03.pdf", "07x07_03"};
		datos.add(file3);
		String[] file4= {
			"./data/Om-TT/Algorithm/Vector/TS_CR_BLQ_SWAP.properties","./data/Om-TT/07x07/07x07_04.properties", "./results/TT_07x07_04.pdf", "07x07_04"};
		datos.add(file4);*/
		String[] file5= {
			"./data/Om-TT/Algorithm/Vector/TS_CR_BLQ_SWAP.properties","./data/Om-TT/07x07/07x07_05.properties", "./results/TT_07x07_05.pdf", "07x07_05"};
		datos.add(file5);
		String[] file6= {
				"./data/Om-TT/Algorithm/Vector/TS_CR_BLQ_SWAP.properties","./data/Om-TT/10x10/10x10_05.properties", "./results/TT_10x10_05.pdf", "10x10_05"};
		datos.add(file6);
		String[] file7= {
				"./data/Om-TT/Algorithm/Vector/TS_CR_BLQ_SWAP.properties","./data/Om-TT/15x15/15x15_05.properties", "./results/TT_15x15_05.pdf", "15x15_05"};
		datos.add(file7);
		/*String[] file8= {
			"./data/Om-TT/Algorithm/Vector/TS_CR_BLQ_SWAP.properties","./data/Om-TT/07x07/07x07_08.properties", "./results/TT_07x07_08.pdf", "07x07_08"};
		datos.add(file8);
		String[] file9= {
			"./data/Om-TT/Algorithm/Vector/TS_CR_BLQ_SWAP.properties","./data/Om-TT/07x07/07x07_09.properties", "./results/TT_07x07_09.pdf", "07x07_09"};
		datos.add(file9);
		String[] file10= {
			"./data/Om-TT/Algorithm/Vector/TS_CR_BLQ_SWAP.properties","./data/Om-TT/07x07/07x07_10.properties", "./results/TT_07x07_10.pdf", "07x07_10"};
		datos.add(file10);*/
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
			for (int i = 0; i < 10; i++) {	
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
		ChartPrinter.getInstance().printGlobalResultsHTML("./results/Om_TT/T_TS_CR_BLQ_SWAP_07x07_1_Vector_consolidated.html");
	}
	public static void main(String[] args) {
 		JUnitCore.main("launcher.generator.T_TS_CR_BLQ_SWAP_07x07_1_Vector");  
	}
}
