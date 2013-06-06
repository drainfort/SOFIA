package launcher.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import launcher.Launcher;
import launcher.generator.vos.AlgorithmConfigurationVO;
import launcher.generator.vos.ParameterVO;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

/**
 * Class that offers the functionality for automatically creating the test cases
 * for the experimentation of the algorithms
 * 
 * @author Jaime Romero
 * @author David Mendez Acuna
 */
public class JarGenerator {
	
	// -------------------------------------------------------------
	// Constants
	// -------------------------------------------------------------
	
	// Meta-heuristics
	public static String TABU_SEARCH_COMPLETE_NEIGHBORHOOD = "TS_CN";
	public static String TABU_SEARCH_RESTRICTED_NEIGHBORHOOD = "TS_RN";
	public static String SIMULATED_ANNELING ="SA";
	public static String GRASP = "GRASP";
	public static String RANDOM_WALK = "RANDOM";
	// Meta-heuristics Classes
	public static String TABU_SEARCH_COMPLETE_NEIGHBORHOOD_CLASS = "TabuSearchCN";
	public static String TABU_SEARCH_RESTRICTED_NEIGHBORHOOD_CLASS = "TabuSearchRN";
	public static String SIMULATED_ANNELING_CLASS ="SimpleSimulatedAnnealing";
	public static String GRASP_CLASS = "GRASP";
	public static String RANDOM_WALK_CLASS = "RandomWalk";
	
	// Neighborhood algorithm
	public static String RANDOM_NEIGHBORHOOD = "RDM";
	public static String CRITICAL_ADJACENT="CR_ADJ";
	public static String CRITICAL_ADJACENT_MACHINES = "CR_ADJ_MS";
	public static String CRITICAL_BLOCK_ADJACENT="CR_BLQADJ";
	public static String CRITICAL_BLOCK="CR_BLQ";
	public static String CRITICAL_BLOCK_ENDSTART="CR_BLQENDSTART";
	// Neighborhood algorithm Classes
	public static String RANDOM_NEIGHBORHOOD_CLASS = "Random";
	public static String CRITICAL_ADJACENT_CLASS="AdjacentShiftOnCriticalRoutes";
	public static String CRITICAL_ADJACENT_MACHINES_CLASS = "AdjacentMachinesShiftOnCriticalRoutes";
	public static String CRITICAL_BLOCK_ADJACENT_CLASS="ShiftBlockAdjOnEnds";
	public static String CRITICAL_BLOCK_CLASS="ShiftRandomBlockCriticalRoute";
	public static String CRITICAL_BLOCK_ENDSTART_CLASS="ShiftBlockEndStartAnyCriticalRoute";
	
	// Representation structure
	public static String GRAPH = "Graph";
	public static String VECTOR = "Vector";
	
	// Representation structure classes
    public static String GRAPH_CLASS = "GraphFactory";
    public static String VECTOR_CLASS = "VectorFactory";
	
	// Modifiers
	public static String RANDOM_MODFIER = "RANDOM";
	public static String SWAP = "SWAP";
	public static String LEFT_INSERTION = "LEFTINSERTION";
	public static String RIGHT_INSERTION="RIGHTINSERTION";
	
	// Modifiers classes
	public static String RANDOM_MODFIER_CLASS = "RandomNeighbor";
	public static String SWAP_CLASS = "Swap";
	public static String LEFT_INSERTION_CLASS = "LeftInsertion";
	public static String RIGHT_INSERTION_CLASS="RightInsertion";
	
	
	// Initial solution builders classes
	public final static String LPTNonDelay_CLASS = "LPTNonDelay";
	public final static String LRPTNonDelay_CLASS = "LRPTNonDelay";
	public final static String SPTNonDelay_CLASS = "SPTNonDelay";
	public final static String SRPTNonDelay_CLASS = "SRPTNonDelay";
	public final static String RandomDispatchingRule_CLASS = "RandomDispatchingRule";
	public final static String BestDispatchingRule_CLASS = "BestDispatchingRule";
	public final static String StochasticERM_CLASS = "StochasticERM";
	public final static String StochasticLPTNonDelay_CLASS = "StochasticLPTNonDelay";
	public final static String StochasticSPTNonDelay_CLASS = "StochasticSPTNonDelay";
	
	// -------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------
	
	private String workspacePath;
	private String workspacePathBackSlash;
	private String eclipsePath;
	
	// -------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------

	/**
	 * Default constructor of the class
	 */
	public JarGenerator(String workspacePath, String eclipsePath){
		this.workspacePath = workspacePath.replace("\\", "/") + "/";
		this.workspacePathBackSlash = workspacePath.replace("\\", "\\\\") + "\\\\";
		this.eclipsePath = eclipsePath.replace("\\", "\\\\") + "\\\\";
	}
	
	// -------------------------------------------------------------
	// Methods
	// -------------------------------------------------------------
	
	public void generateJavaFiles(ArrayList<String> instancesIdentifiers, AlgorithmConfigurationVO algorithmConfiguration){
		String algorithm = null;
		String algorithm_class =null;
		
		if(algorithmConfiguration.getMetaheuristic().equals(AlgorithmConfigurationVO.TABU_SEARCH_COMPLETE_NEIGHBORHOOD)){
			algorithm = TABU_SEARCH_COMPLETE_NEIGHBORHOOD;
			algorithm_class = TABU_SEARCH_COMPLETE_NEIGHBORHOOD_CLASS;
		}else if(algorithmConfiguration.getMetaheuristic().equals(AlgorithmConfigurationVO.TABU_SEARCH_RESTRICTED_NEIGHBORHOOD)){
			algorithm = TABU_SEARCH_RESTRICTED_NEIGHBORHOOD;
			algorithm_class = TABU_SEARCH_RESTRICTED_NEIGHBORHOOD_CLASS;
		}else if(algorithmConfiguration.getMetaheuristic().equals(AlgorithmConfigurationVO.SIMULATED_ANNELING)){
			algorithm = SIMULATED_ANNELING;
			algorithm_class = SIMULATED_ANNELING_CLASS;
		}else if(algorithmConfiguration.getMetaheuristic().equals(AlgorithmConfigurationVO.GRASP)){
			algorithm = GRASP;
			algorithm_class = GRASP_CLASS;
		}
		
		String neighbors = null;
		String neighbors_class =null;
		
		if(algorithmConfiguration.getNeighborhood().equals(AlgorithmConfigurationVO.RANDOM_NEIGHBORHOOD)){
			neighbors = RANDOM_NEIGHBORHOOD;
			neighbors_class = RANDOM_NEIGHBORHOOD_CLASS;
		}else if(algorithmConfiguration.getNeighborhood().equals(AlgorithmConfigurationVO.CRITICAL_ROUTE_ADJACENT_NEIGHBORHOOD)){
			neighbors = CRITICAL_ADJACENT;
			neighbors_class = CRITICAL_ADJACENT_CLASS;
		}else if(algorithmConfiguration.getNeighborhood().equals(AlgorithmConfigurationVO.CRITICAL_ROUTE_ADJACENT_MACHINES_NEIGHBORHOOD)){
			neighbors = CRITICAL_ADJACENT_MACHINES;
			neighbors_class = CRITICAL_ADJACENT_MACHINES_CLASS;
		}else if(algorithmConfiguration.getNeighborhood().equals(AlgorithmConfigurationVO.CRITICAL_BLOCK_RANDOM_NEIGHBORHOOD)){
			neighbors = CRITICAL_BLOCK;
			neighbors_class = CRITICAL_BLOCK_CLASS;
		}else if(algorithmConfiguration.getNeighborhood().equals(AlgorithmConfigurationVO.CRITICAL_BLOCK_ADJACENT_ON_END_NEIGHBORHOOD)){
			neighbors = CRITICAL_BLOCK_ADJACENT;
			neighbors_class = CRITICAL_BLOCK_ADJACENT_CLASS;
		}else if(algorithmConfiguration.getNeighborhood().equals(AlgorithmConfigurationVO.CRITICAL_BLOCK_END_START_ANY_NEIGHBORHOOD)){
			neighbors = CRITICAL_BLOCK_ENDSTART;
			neighbors_class = CRITICAL_BLOCK_ENDSTART_CLASS;
		}
		
		String modifier = null;
		String modifier_class = null;
		
		if(algorithmConfiguration.getModifier().equals(AlgorithmConfigurationVO.RANDOM_MODFIER)){
			modifier = RANDOM_MODFIER;
			modifier_class = RANDOM_MODFIER_CLASS;
		}else if(algorithmConfiguration.getModifier().equals(AlgorithmConfigurationVO.SWAP)){
			modifier = SWAP;
			modifier_class = SWAP_CLASS;
		}else if(algorithmConfiguration.getModifier().equals(AlgorithmConfigurationVO.LEFT_INSERTION)){
			modifier = LEFT_INSERTION;
			modifier_class = LEFT_INSERTION_CLASS;
		}else if(algorithmConfiguration.getModifier().equals(AlgorithmConfigurationVO.RIGHT_INSERTION)){
			modifier = RIGHT_INSERTION;
			modifier_class = RIGHT_INSERTION_CLASS;
		}
		
		String representation = null;
		String representation_class = null;
		
		if(algorithmConfiguration.getRepresentation().equals(AlgorithmConfigurationVO.VECTOR)){
			representation = VECTOR;
			representation_class = VECTOR_CLASS;
		}else if(algorithmConfiguration.getRepresentation().equals(AlgorithmConfigurationVO.GRAPH)){
			representation = GRAPH;
			representation_class = GRAPH_CLASS;
		}
		
		String builder_class = null;
		
		if(algorithmConfiguration.getInitialSolutionBuilder().equals(AlgorithmConfigurationVO.LPTNonDelay)){
			builder_class = LPTNonDelay_CLASS;
		}else if(algorithmConfiguration.getInitialSolutionBuilder().equals(AlgorithmConfigurationVO.LRPTNonDelay)){
			builder_class = LRPTNonDelay_CLASS;
		}else if(algorithmConfiguration.getInitialSolutionBuilder().equals(AlgorithmConfigurationVO.SPTNonDelay)){
			builder_class = SPTNonDelay_CLASS;
		}else if(algorithmConfiguration.getInitialSolutionBuilder().equals(AlgorithmConfigurationVO.SRPTNonDelay)){
			builder_class = SRPTNonDelay_CLASS;
		}else if(algorithmConfiguration.getInitialSolutionBuilder().equals(AlgorithmConfigurationVO.RandomDispatchingRule)){
			builder_class = RandomDispatchingRule_CLASS;
		}else if(algorithmConfiguration.getInitialSolutionBuilder().equals(AlgorithmConfigurationVO.BestDispatchingRule)){
			builder_class = BestDispatchingRule_CLASS;
		}else if(algorithmConfiguration.getInitialSolutionBuilder().equals(AlgorithmConfigurationVO.StochasticERM)){
			builder_class = StochasticERM_CLASS;
		}else if(algorithmConfiguration.getInitialSolutionBuilder().equals(AlgorithmConfigurationVO.StochasticLPTNonDelay)){
			builder_class = StochasticLPTNonDelay_CLASS;
		}else if(algorithmConfiguration.getInitialSolutionBuilder().equals(AlgorithmConfigurationVO.StochasticSPTNonDelay)){
			builder_class = StochasticSPTNonDelay_CLASS;
		}
	
	    String problem =null;
	    if(algorithmConfiguration.getSelectedBetas().size()==0){
	    	problem = "gamma.cmax.bks.om";
	    }else{
	    	if(algorithmConfiguration.getSelectedBetas().contains("Travel times")){
	    		if(algorithmConfiguration.getSelectedBetas().size()==1){
	    			problem = "gamma.cmax.bks.om.tt";
	    		}
	    	}
	    }
		
		printJavaFile(instancesIdentifiers, algorithm, neighbors, representation, modifier, algorithmConfiguration.getInstanceType(),problem );
		printConfigurationFile("./jars/data/Om-TT/Algorithm/"+representation+"/"+algorithm+"_"+neighbors+"_"+modifier+".properties", algorithmConfiguration, representation_class, algorithm_class, neighbors_class, modifier_class, builder_class);
	}
	
	private void printConfigurationFile(String filename, AlgorithmConfigurationVO algorithmConfiguration, String structure_class, String algo_class, String neigh_class, String modifier_class, String builder_class){
		FileWriter fichero = null;
        PrintWriter pw = null;
		try
        {
			fichero = new FileWriter(new File(filename));
			pw = new PrintWriter(fichero);
			 
			pw.println("# -------------------------------------------------");
			pw.println("# The algorithm configuration");
			pw.println("# -------------------------------------------------");

			pw.println("scheduling.structureFactory=structure.factory.impl."+structure_class);
			pw.println("scheduling.neighborCalculator=neighborCalculator.impl."+neigh_class);
			pw.println("scheduling.modifier=modifier.impl."+modifier_class);
			pw.println("scheduling.gammaCalculator=gammaCalculator.impl.CMaxCalculator");
			pw.println("scheduling.control=control.impl."+algo_class);
			pw.println("scheduling.initialSolutionBuilder=initialSolBuilder.impl."+builder_class);
			pw.println("scheduling.parametersLoader=control.impl."+algo_class+"ParametersLoader");

			pw.println("# -------------------------------------------------");
			pw.println("# Parameters required by the control component");
			pw.println("# -------------------------------------------------");

			
			for(int i=0; i< algorithmConfiguration.getMetaheuristicParams().size();i++){
				ParameterVO temp = algorithmConfiguration.getMetaheuristicParams().get(i);
				pw.println(temp.getName()+"="+temp.getValue());
			}
			pw.close();
			fichero.close();
        }
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	private void printJavaFile(ArrayList<String> instances, String metaheuristic, String neighbors, String structure, String modifier, String instanceType, String betas){
		FileWriter fichero = null;
        PrintWriter pw = null;
		try
        {
			 for(int i=0; i<instances.size();i++){
	            	int a=i+1; 
	            	String instanceName = instances.get(i);
					String name = "T_" + instanceName + "_" + metaheuristic + "_" + neighbors + "_" + modifier + "_" + structure+"_" + instanceType;
			        fichero = new FileWriter(new File("./src/launcher/generator/"+name+".java"));
			        
			        pw = new PrintWriter(fichero);
			        pw.println("package launcher.generator;");
			        pw.println("import java.util.ArrayList;");           
			        pw.println("import launcher.Launcher;");
			        pw.println("import org.junit.After;" );
			        pw.println("import org.junit.Before;" );
			        pw.println("import org.junit.Test;" );
			        pw.println("import org.junit.runner.JUnitCore;" );
			        pw.println("import org.junit.runner.RunWith;");
			        pw.println("import org.junit.runners.Parameterized;");
			        pw.println("import org.junit.runners.Parameterized.Parameters;");
			        pw.println("import common.utils.ExecutionResults;");
			        pw.println("import chart.printer.ChartPrinter;");
			
			        pw.println("@RunWith(Parameterized.class)");
			        pw.println("public class "+name+"{");
			        pw.println("	private Launcher launcher;");
			        pw.println("	private String algorithmFile;");
			        pw.println("	private String problemFile;");	
			        pw.println("	private String currentBks;");	
			        pw.println("	private String instanceName;");	
			        pw.println("	private String instanceType;");				       
			        
			        pw.println("	public "+name+"(String pAlgorithmFile, String pProblemFile, String pCurrentBks, String pInstanceType, String pInstanceName) {");
			        pw.println("			super();");
			        pw.println("			algorithmFile = pAlgorithmFile;");
			        pw.println("			problemFile = pProblemFile;");
			        pw.println("			currentBks = pCurrentBks;");
			        pw.println("			instanceName = pInstanceName;");
			        pw.println("			instanceType = pInstanceType;");
			        pw.println("	}");
			
			        pw.println("	@Parameters");
			        pw.println("	public static ArrayList<String[]> datos() {");
			        pw.println("		ArrayList<String[]> datos = new ArrayList<String[]>();");
					
			        pw.println("		String[] file"+a+"= {");
			        pw.println("			\"./data/Om-TT/Algorithm/"+structure+"/"+metaheuristic+"_"+neighbors+"_"+modifier+".properties\",\"./data/Om-TT/"+instanceName.substring(0,5)+"/"+instanceName+".properties\", \""+betas+"\", \""+instanceType+"\",\""+instanceName+"\"};");
			        pw.println("		datos.add(file"+a+");");
							
				    pw.println("			return datos;");
				    pw.println("	}");
							
				    pw.println("	@Before");
				    pw.println("	public void setupScenario() {");
				    pw.println("		launcher = new Launcher();");
				    pw.println("	}");
			
							
				    pw.println("	@Test");
				    pw.println("	public void testLaunch() throws Exception {");
				    pw.println("		try {");
				    pw.println("			ArrayList<ExecutionResults> results = new ArrayList<ExecutionResults>();");
				    pw.println("			for (int i = 0; i < 1; i++) {	");			
				    pw.println("				results.add(launcher.launch(algorithmFile,problemFile, currentBks, instanceType, instanceName));");
				    pw.println("			}");
					pw.println("			ChartPrinter.getInstance().addResults(results);");
					pw.println("		} catch (Exception e) {");
					pw.println("			e.printStackTrace();");
					pw.println("			throw e;");
					pw.println("		}");
					pw.println("	}");
					pw.println("	@After");
					pw.println("	public void tearDown(){");
					pw.println("		ChartPrinter.getInstance().printGlobalResultsHTML(\"./results/Om_TT/"+name+"_consolidated.html\");");
					pw.println("	}");
					
					
					pw.println("	public static void main(String[] args) {");
					pw.println(" 		JUnitCore.main(\"launcher.generator."+name+"\");  ");         
					pw.println("	}");
					pw.println("}");
			        
					pw.close();
					fichero.close();
					
					compileFile(name);
			}
    		
     
        } catch (Exception e) {
            e.printStackTrace();
        }   
	}

	private void compileFile(String name) {
		String fileToCompile = "./src/launcher/generator/" + name + ".java";
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		int compilationResult = compiler.run(null, null, null, fileToCompile);
        if(compilationResult == 0){
            System.out.println("Compilation is successful");
            
        }else{
            System.out.println("Compilation Failed");
        }
        
        try {
			Process process = Runtime.getRuntime().exec("java " + "./src/launcher/generator/"+name+".java");
			process.destroy();
        } catch (IOException e) {
			e.printStackTrace();
		}
        
        printANT(name);
	}
	

	private void printANT(String name){
		
		FileWriter fichero = null;
        PrintWriter pw = null;
		try{
		
	        fichero = new FileWriter(new File("./src/launcher/generator/"+name+".xml"));
	        pw = new PrintWriter(fichero);		
			
			pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
			pw.println("<project default=\"create_run_jar\" name=\"Create Runnable Jar for Project co.edu.uniandes.qualdev.victoria.launcher.tests with Jar-in-Jar Loader\">");
			pw.println("<!--this file was created by Eclipse Runnable JAR Export Wizard-->");
			pw.println("<!--ANT 1.7 is required                                        -->");
			pw.println("<target name=\"create_run_jar\">");
			pw.println("<jar destfile=\"" + workspacePath + "co.edu.uniandes.qualdev.sofia.launcher.tests/jars/"+name+".jar\">");
			pw.println("<manifest>");
			pw.println("<attribute name=\"Main-Class\" value=\"org.eclipse.jdt.internal.jarinjarloader.JarRsrcLoader\"/>");
			pw.println("<attribute name=\"Rsrc-Main-Class\" value=\"launcher.generator."+name+"\"/>");
			pw.println("<attribute name=\"Class-Path\" value=\".\"/>");
			pw.println("<attribute name=\"Rsrc-Class-Path\" value=\"./ junit.jar org.hamcrest.core_1.1.0.v20090501071000.jar ant-launcher.jar ant.jar gnujaxp.jar jcommon-1.0.17.jar jfreechart-1.0.14.jar itext-pdfa-5.3.1.jar itext-xtra-5.3.1.jar itextpdf-5.3.1.jar bcmail-jdk15-146.jar bcprov-jdk15-146.jar bctsp-jdk15-146.jar itext-asian.jar itext-hyph-xml.jar jcommon-1.0.17.jar jfreechart-1.0.14.jar\"/>");
			pw.println("</manifest>");
			pw.println("<zipfileset src=\"jar-in-jar-loader.zip\"/>");
			pw.println("<fileset dir=\"" + workspacePath + "co.edu.uniandes.qualdev.sofia.launcher.tests/bin\"/>");
			pw.println("<zipfileset dir=\"" + eclipsePath + "plugins\\org.junit_4.10.0.v4_10_0_v20120426-0900\" includes=\"junit.jar\"/>");
			pw.println("<zipfileset dir=\"" + eclipsePath + "plugins\" includes=\"org.hamcrest.core_1.1.0.v20090501071000.jar\"/>");
			pw.println("<fileset dir=\"" + workspacePathBackSlash + "co.edu.uniandes.qualdev.sofia.launcher.tests\\lib\" includes=\"ant-launcher.jar\"/>");
			pw.println("<fileset dir=\"" + workspacePathBackSlash + "co.edu.uniandes.qualdev.sofia.launcher.tests\\lib\" includes=\"ant.jar\"/>");
			pw.println("<fileset dir=\"" + workspacePath + "co.edu.uniandes.qualdev.sofia.afs/bin\"/>");
			pw.println("<fileset dir=\"" + workspacePath + "co.edu.uniandes.qualdev.sofia.algorithm/bin\"/>");
			pw.println("<fileset dir=\"" + workspacePath + "co.edu.uniandes.qualdev.sofia.common/bin\"/>");
			pw.println("<fileset dir=\"" + workspacePath + "co.edu.uniandes.qualdev.sofia.control/bin\"/>");
			pw.println("<fileset dir=\"" + workspacePath + "co.edu.uniandes.qualdev.sofia.gammaCalculator/bin\"/>");
			pw.println("<fileset dir=\"" + workspacePath + "co.edu.uniandes.qualdev.sofia.structure/bin\"/>");
			pw.println("<fileset dir=\"" + workspacePath + "co.edu.uniandes.qualdev.sofia.modifier/bin\"/>");
			pw.println("<fileset dir=\"" + workspacePath + "co.edu.uniandes.qualdev.sofia.neighborCalculator/bin\"/>");
			pw.println("<fileset dir=\"" + workspacePath + "co.edu.uniandes.qualdev.sofia.initialSolBuilder/bin\"/>");
			pw.println("<fileset dir=\"" + workspacePath + "co.edu.uniandes.qualdev.sofia.reports/bin\"/>");
			pw.println("<fileset dir=\""+workspacePath+"co.edu.uniandes.qualdev.sofia.instancesgenerator/bin\"/>");
			pw.println("<fileset dir=\""+workspacePath+"co.edu.uniandes.qualdev.sofia.launcher/bin\"/>");
			pw.println("<zipfileset dir=\""+workspacePathBackSlash+"co.edu.uniandes.qualdev.sofia.launcher\\lib\" includes=\"jcommon-1.0.17.jar\"/>");
			pw.println("<zipfileset dir=\""+workspacePathBackSlash+"co.edu.uniandes.qualdev.sofia.launcher\\lib\" includes=\"jfreechart-1.0.14.jar\"/>");
			pw.println("</jar>");
			pw.println("</target>");
			pw.println("</project>");

			fichero.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	   File buildFile = new File("./src/launcher/generator/"+name+".xml");
	   Project p = new Project();
	   p.setUserProperty("ant.file", buildFile.getAbsolutePath());
	   p.init();
	   ProjectHelper helper = ProjectHelper.getProjectHelper();
	   p.addReference("ant.projectHelper", helper);
	   helper.parse(p, buildFile);
	   p.executeTarget(p.getDefaultTarget());
		
	}
}