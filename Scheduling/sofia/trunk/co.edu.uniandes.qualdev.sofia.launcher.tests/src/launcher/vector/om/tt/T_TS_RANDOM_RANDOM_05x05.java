package launcher.vector.om.tt;

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
public class T_TS_RANDOM_RANDOM_05x05 {

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

	public T_TS_RANDOM_RANDOM_05x05(
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
				"./data/Om-TT/Algorithm/TS_RANDOM_RANDOM.properties","./data/Om-TT/05x05/05x05_01.properties", "./results/TT_05x05_01.pdf", "05x05_01"};
		datos.add(file1);

		String[] file2 = {
				"./data/Om-TT/Algorithm/TS_RANDOM_RANDOM.properties","./data/Om-TT/05x05/05x05_02.properties", "./results/TT_05x05_02.pdf", "05x05_02"};
		datos.add(file2);

		String[] file3 = {
				"./data/Om-TT/Algorithm/TS_RANDOM_RANDOM.properties","./data/Om-TT/05x05/05x05_03.properties", "./results/TT_05x05_03.pdf", "05x05_03"};
		datos.add(file3);
		
		String[] file4 = {
				"./data/Om-TT/Algorithm/TS_RANDOM_RANDOM.properties","./data/Om-TT/05x05/05x05_04.properties", "./results/TT_05x05_04.pdf", "05x05_04"};
		datos.add(file4);

		String[] file5 = {
				"./data/Om-TT/Algorithm/TS_RANDOM_RANDOM.properties","./data/Om-TT/05x05/05x05_05.properties", "./results/TT_05x05_05.pdf", "05x05_05"};
		datos.add(file5);
		
		String[] file6 = {
				"./data/Om-TT/Algorithm/TS_RANDOM_RANDOM.properties","./data/Om-TT/05x05/05x05_06.properties", "./results/TT_05x05_06.pdf", "05x05_06"};
		datos.add(file6);
		
		String[] file7 = {
				"./data/Om-TT/Algorithm/TS_RANDOM_RANDOM.properties","./data/Om-TT/05x05/05x05_07.properties", "./results/TT_05x05_07.pdf", "05x05_07"};
		datos.add(file7);
		
		String[] file8 = {
				"./data/Om-TT/Algorithm/TS_RANDOM_RANDOM.properties","./data/Om-TT/05x05/05x05_08.properties", "./results/TT_05x05_08.pdf", "05x05_08"};
		datos.add(file8);
		
		String[] file9 = {
				"./data/Om-TT/Algorithm/TS_RANDOM_RANDOM.properties","./data/Om-TT/05x05/05x05_09.properties", "./results/TT_05x05_09.pdf", "05x05_09"};
		datos.add(file9);
		
		String[] file10 = {
				"./data/Om-TT/Algorithm/TS_RANDOM_RANDOM.properties","./data/Om-TT/05x05/05x05_10.properties", "./results/TT_05x05_10.pdf", "05x05_10"};
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
			for (int i = 0; i < 20; i++) {
				
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
		ChartPrinter.getInstance().printGlobalResults("./results/Om_TT/TT_05x05_TS_RANDOM_RANDOM_05x05_consolidated.pdf");
	}
	
	// ---------------------------------------------------------
	// Main
	// ---------------------------------------------------------
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 JUnitCore.main(
	                "launcher.vector.om.tt.T_TS_RANDOM_RANDOM_05x05");           
	}
}
