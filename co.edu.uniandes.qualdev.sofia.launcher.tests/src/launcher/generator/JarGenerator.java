package launcher.generator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

public class JarGenerator {
	
	public static String TABUSEARCH="TS";
	public static String SIMULATEDANNELING="SA";
	public static String GRASP="GRASP";
	public static String RANDOM="RANDOM";
	
	public static String CRITICAL_ADJACENT="CR_ADJ";
	
	public static String GRAPH="G";
	public static String VECTOR="V";
	
	public static String ruta1="C:/Users/ja.romero940/workspace/sofia/";
	public static String ruta2="C:\\Users\\ja.romero940\\workspace\\sofia\\";
	public static String rutaEclipse="C:\\Users\\ja.romero940\\Desktop\\";
	
	public JarGenerator(){
		
		generateJavaFiles("07x07", TABUSEARCH, CRITICAL_ADJACENT, 1);
		/*generateJavaFiles("10x10", TABUSEARCH, RANDOM, 1);
		generateJavaFiles("15x15", TABUSEARCH, RANDOM, 1);
		generateJavaFiles("07x07", SIMULATEDANNELING, RANDOM, 1);
		generateJavaFiles("10x10", SIMULATEDANNELING, RANDOM, 1);
		generateJavaFiles("15x15", SIMULATEDANNELING, RANDOM, 1);
		generateJavaFiles("07x07", GRASP, RANDOM, 1);
		generateJavaFiles("10x10", GRASP, RANDOM, 1);
		generateJavaFiles("15x15", GRASP, RANDOM, 1);*/

	}
	
	private void generateJavaFiles(String sizeInstance, String algorithym, String neighbors, int instancesXFile){
		
		int num=0;
		int numarchivo=0;
		while(num<10){
			int j=0;
			
			ArrayList<String> instances = new ArrayList<String>();
			while(j<instancesXFile){
				if(num<10){
					int position = num+1;
					if(position==10){
						instances.add(sizeInstance+"_10");
					}
					else{
						instances.add(sizeInstance+"_0"+position);
					}
					num++;
					j++;
				}
				else{
					j++;
				}
			}
			numarchivo++;
			printJavaFile(instances, algorithym, neighbors, numarchivo, sizeInstance);
		}
        
	}
	
	private void printJavaFile(ArrayList<String> instances, String algorithym, String neighbors, int number ,String sizeInstance){
		FileWriter fichero = null;
        PrintWriter pw = null;
		try
        {
			String name ="T_"+algorithym+"_"+neighbors+"_SWAP_"+sizeInstance+"_"+number;
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
            pw.println("	private String resultsFile;");		
            pw.println("	private String instanceName;");
            pw.println("	public "+name+"(String pAlgorithmFile, String pProblemFile, String pResultsFile, String pInstanceName) {");
            pw.println("			super();");
            pw.println("			algorithmFile = pAlgorithmFile;");
            pw.println("			problemFile = pProblemFile;");
            pw.println("			resultsFile = pResultsFile;");
            pw.println("			instanceName = pInstanceName;");
            pw.println("	}");

            pw.println("	@Parameters");
            pw.println("	public static ArrayList<String[]> datos() {");
            pw.println("		ArrayList<String[]> datos = new ArrayList<String[]>();");
			
            for(int i=0; i<instances.size();i++){
            	int a=i+1; 
            	String instanceName = instances.get(i);
            	 pw.println("		String[] file"+a+"= {");
            	 pw.println("			\"./data/Om-TT/Algorithm/"+algorithym+"_"+neighbors+"_SWAP_"+sizeInstance+".properties\",\"./data/Om-TT/"+sizeInstance+"/"+instanceName+".properties\", \"./results/TT_"+instanceName+".pdf\", \""+instanceName+"\"};");
            	 pw.println("		datos.add(file"+a+");");
            }
					
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
		    pw.println("				results.add(launcher.launch(algorithmFile,problemFile, resultsFile, instanceName));");
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
            
    		fichero.close();
    		
    		//compileFile(name);
     
        } catch (Exception e) {
            e.printStackTrace();
        }   
	}

	private void compileFile(String name) {
		String fileToCompile = "./src/launcher/om/tt/generator/"+name+".java";
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		int compilationResult = compiler.run(null, null, null, fileToCompile);
        if(compilationResult == 0){
            System.out.println("Compilation is successful");
            
        }else{
            System.out.println("Compilation Failed");
        }
        
        try {
			Process process = Runtime.getRuntime().exec("java " + "./src/launcher/om/tt/generator/"+name+".java");
			process.destroy();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        printANT(name);
	}
	

	private void printANT(String name){
		
		FileWriter fichero = null;
        PrintWriter pw = null;
		try{
		
	        fichero = new FileWriter(new File("./src/launcher/om/tt/generator/"+name+".xml"));
	        pw = new PrintWriter(fichero);		
			
			pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>");
			pw.println("<project default=\"create_run_jar\" name=\"Create Runnable Jar for Project co.edu.uniandes.qualdev.victoria.launcher.tests with Jar-in-Jar Loader\">");
			pw.println("<!--this file was created by Eclipse Runnable JAR Export Wizard-->");
			pw.println("<!--ANT 1.7 is required                                        -->");
			pw.println("<target name=\"create_run_jar\">");
			pw.println("<jar destfile=\""+ruta1+"co.edu.uniandes.qualdev.victoria.launcher.tests/jars/"+name+".jar\">");
			pw.println("<manifest>");
			pw.println("<attribute name=\"Main-Class\" value=\"org.eclipse.jdt.internal.jarinjarloader.JarRsrcLoader\"/>");
			pw.println("<attribute name=\"Rsrc-Main-Class\" value=\"launcher.om.tt.generator."+name+"\"/>");
			pw.println("<attribute name=\"Class-Path\" value=\".\"/>");
			pw.println("<attribute name=\"Rsrc-Class-Path\" value=\"./ junit.jar org.hamcrest.core_1.1.0.v20090501071000.jar gnujaxp.jar jcommon-1.0.17.jar jfreechart-1.0.14.jar itext-pdfa-5.3.1.jar itext-xtra-5.3.1.jar itextpdf-5.3.1.jar bcmail-jdk15-146.jar bcprov-jdk15-146.jar bctsp-jdk15-146.jar itext-asian.jar itext-hyph-xml.jar jgraphx.jar jcommon-1.0.17.jar jfreechart-1.0.14.jar ant-launcher.jar ant.jar\"/>");
			pw.println("</manifest>");
			pw.println("<zipfileset src=\"jar-in-jar-loader.zip\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.launcher.tests/bin\"/>");
			pw.println("<zipfileset dir=\""+rutaEclipse+"eclipse\\plugins\\org.junit_4.10.0.v4_10_0_v20120426-0900\" includes=\"junit.jar\"/>");
			pw.println("<zipfileset dir=\""+rutaEclipse+"eclipse\\plugins\" includes=\"org.hamcrest.core_1.1.0.v20090501071000.jar\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.common/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.control/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.gammaCalculator/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.vector/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.neighborCalculator/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.modifier/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.control.simpleSimulatedAnnealing/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.chartprinter/bin\"/>");
			pw.println("<zipfileset dir=\""+ruta2+"co.edu.uniandes.qualdev.victoria.chartprinter\\lib\" includes=\"gnujaxp.jar\"/>");
			pw.println("<zipfileset dir=\""+ruta2+"co.edu.uniandes.qualdev.victoria.chartprinter\\lib\" includes=\"jcommon-1.0.17.jar\"/>");
			pw.println("<zipfileset dir=\""+ruta2+"co.edu.uniandes.qualdev.victoria.chartprinter\\lib\" includes=\"jfreechart-1.0.14.jar\"/>");
			pw.println("<zipfileset dir=\""+ruta2+"co.edu.uniandes.qualdev.victoria.chartprinter\\lib\" includes=\"itext-pdfa-5.3.1.jar\"/>");
			pw.println("<zipfileset dir=\""+ruta2+"co.edu.uniandes.qualdev.victoria.chartprinter\\lib\" includes=\"itext-xtra-5.3.1.jar\"/>");
			pw.println("<zipfileset dir=\""+ruta2+"co.edu.uniandes.qualdev.victoria.chartprinter\\lib\" includes=\"itextpdf-5.3.1.jar\"/>");
			pw.println("<zipfileset dir=\""+ruta2+"co.edu.uniandes.qualdev.victoria.chartprinter\\lib\" includes=\"bcmail-jdk15-146.jar\"/>");
			pw.println("<zipfileset dir=\""+ruta2+"co.edu.uniandes.qualdev.victoria.chartprinter\\lib\" includes=\"bcprov-jdk15-146.jar\"/>");
			pw.println("<zipfileset dir=\""+ruta2+"co.edu.uniandes.qualdev.victoria.chartprinter\\lib\" includes=\"bctsp-jdk15-146.jar\"/>");
			pw.println("<zipfileset dir=\""+ruta2+"co.edu.uniandes.qualdev.victoria.chartprinter\\lib\" includes=\"itext-asian.jar\"/>");
			pw.println("<zipfileset dir=\""+ruta2+"co.edu.uniandes.qualdev.victoria.chartprinter\\lib\" includes=\"itext-hyph-xml.jar\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.vector.vector.impl/bin\"/>");
			pw.println("<zipfileset dir=\""+ruta2+"co.edu.uniandes.qualdev.victoria.vector.vector.impl\\lib\" includes=\"jgraphx.jar\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.modifier.swap/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.control.enrichedSimulatedAnnealing/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.gammaCalculator.cmax/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.modifier.leftInsertion/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.modifier.rightInsertion/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.launcher/bin\"/>");
			pw.println("<zipfileset dir=\""+ruta2+"co.edu.uniandes.qualdev.victoria.launcher\\lib\" includes=\"jcommon-1.0.17.jar\"/>");
			pw.println("<zipfileset dir=\""+ruta2+"co.edu.uniandes.qualdev.victoria.launcher\\lib\" includes=\"jfreechart-1.0.14.jar\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.initialSolBuilder/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.initialSolBuilder.LPTNonDelay/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.algorithm/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.algorithm.multistart/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.algorithm.trayectoryBased/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.initialSolBuilder.SPTNonDelay/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.initialSolBuilder.BestDispatchingRule/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.initialSolBuilder.LRPTNonDelay/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.initialSolBuilder.SRPTNonDelay/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.initialSolBuilder.AFSLPTNonDelay/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.afs/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.initialSolBuilder.AFSLRPTNonDelay/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.initialSolBuilder.AFSSPTNonDelay/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.initialSolBuilder.AFSSRPTNonDelay/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.gammaCalculator.meanFlowTime/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.control.tabuSearch/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.control.GRASP/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.initialSolBuilder.StochasticLPTNonDelay/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.initialSolBuilder.StochasticSPTNonDelay/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.neighborCalculator.random/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.neighborCalculator.complete/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.control.tabuSearchWithRandomJumps/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.control.randomWalk/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.control.GRASP.ERLS/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.initialSolBuilder.StochasticERM/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.instancesgenerator/bin\"/>");
			pw.println("<fileset dir=\""+ruta1+"co.edu.uniandes.qualdev.victoria.initialSolBuilder.RandomDispatchingRule/bin\"/>");
			pw.println("<zipfileset dir=\""+ruta2+"co.edu.uniandes.qualdev.victoria.launcher.tests\\lib\" includes=\"ant-launcher.jar\"/>");
			pw.println("<zipfileset dir=\""+ruta2+"co.edu.uniandes.qualdev.victoria.launcher.tests\\lib\" includes=\"ant.jar\"/>");
			pw.println("</jar>");
			pw.println("</target>");
			pw.println("</project>");

			fichero.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}

		
		
	   File buildFile = new File("./src/launcher/om/tt/generator/"+name+".xml");
	   Project p = new Project();
	   p.setUserProperty("ant.file", buildFile.getAbsolutePath());
	   p.init();
	   ProjectHelper helper = ProjectHelper.getProjectHelper();
	   p.addReference("ant.projectHelper", helper);
	   helper.parse(p, buildFile);
	   p.executeTarget(p.getDefaultTarget());
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new JarGenerator();

	}

}
