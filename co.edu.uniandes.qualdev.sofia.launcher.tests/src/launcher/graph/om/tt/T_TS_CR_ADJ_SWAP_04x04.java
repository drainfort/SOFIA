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
public class T_TS_CR_ADJ_SWAP_04x04 {

	// --------------------------------------------------
	// Attributes
	// --------------------------------------------------

	private Launcher launcher;

	private String algorithmFile;
		
	private String problemFile;

	private String resultsFile;
	
	private String instanceName;
	
	// --------------------------------------------------
	// Constructor
	// --------------------------------------------------

	public T_TS_CR_ADJ_SWAP_04x04(
			String pAlgorithmFile, String pProblemFile, String pResultsFile, String pInstanceName) {
		super();
		algorithmFile = pAlgorithmFile;
		problemFile = pProblemFile;
		resultsFile = pResultsFile;
		instanceName = pInstanceName;
	}

	// --------------------------------------------------
	// Setup scenario
	// --------------------------------------------------

	@Parameters
	public static ArrayList<String[]> datos() {
		ArrayList<String[]> datos = new ArrayList<String[]>();

		String[] file1 = {
				"./data/Om-TT/Algorithm/TS_CR_ADJ_RANDOM_G.properties","./data/Om-TT/04x04/04x04_01.properties", "./results/TT_04x04_01.pdf", "04x04_01"};
		datos.add(file1);

		String[] file2 = {
				"./data/Om-TT/Algorithm/TS_CR_ADJ_RANDOM_G.properties","./data/Om-TT/04x04/04x04_02.properties", "./results/TT_04x04_02.pdf", "04x04_02"};
		datos.add(file2);

		String[] file3 = {
				"./data/Om-TT/Algorithm/TS_CR_ADJ_RANDOM_G.properties","./data/Om-TT/04x04/04x04_03.properties", "./results/TT_04x04_03.pdf", "04x04_03"};
		datos.add(file3);
		
		String[] file4 = {
				"./data/Om-TT/Algorithm/TS_CR_ADJ_RANDOM_G.properties","./data/Om-TT/04x04/04x04_04.properties", "./results/TT_04x04_04.pdf", "04x04_04"};
		datos.add(file4);

		String[] file5 = {
				"./data/Om-TT/Algorithm/TS_CR_ADJ_RANDOM_G.properties","./data/Om-TT/04x04/04x04_05.properties", "./results/TT_04x04_05.pdf", "04x04_05"};
		datos.add(file5);
		
		String[] file6 = {
				"./data/Om-TT/Algorithm/TS_CR_ADJ_RANDOM_G.properties","./data/Om-TT/04x04/04x04_06.properties", "./results/TT_04x04_06.pdf", "04x04_06"};
		datos.add(file6);
		
		String[] file7 = {
				"./data/Om-TT/Algorithm/TS_CR_ADJ_RANDOM_G.properties","./data/Om-TT/04x04/04x04_07.properties", "./results/TT_04x04_07.pdf", "04x04_07"};
		datos.add(file7);
		
		String[] file8 = {
				"./data/Om-TT/Algorithm/TS_CR_ADJ_RANDOM_G.properties","./data/Om-TT/04x04/04x04_08.properties", "./results/TT_04x04_08.pdf", "04x04_08"};
		datos.add(file8);
		
		String[] file9 = {
				"./data/Om-TT/Algorithm/TS_CR_ADJ_RANDOM_G.properties","./data/Om-TT/04x04/04x04_09.properties", "./results/TT_04x04_09.pdf", "04x04_09"};
		datos.add(file9);
		
		String[] file10 = {
				"./data/Om-TT/Algorithm/TS_CR_ADJ_RANDOM_G.properties","./data/Om-TT/04x04/04x04_10.properties", "./results/TT_04x04_10.pdf", "04x04_10"};
		datos.add(file10);
		
		return datos;
	}

	@Before
	public void setupScenario() {
		launcher = new Launcher();
	}

	// --------------------------------------------------
	// Test cases
	// --------------------------------------------------

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
		ChartPrinter.getInstance().printGlobalResultsHTML("./results/Om_TT/TT_04x04_TS_CR_ADJ_RANDOM_G_04x04_consolidated.html");
	}
	
	// ---------------------------------------------------------
	// Main
	// ---------------------------------------------------------
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 JUnitCore.main(
	                "launcher.graph.om.tt.T_TS_CR_ADJ_RANDOM_G_04x04");           
	}
}
