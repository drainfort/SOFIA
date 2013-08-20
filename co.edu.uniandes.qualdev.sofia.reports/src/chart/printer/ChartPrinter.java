package chart.printer;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;

import common.types.OperationIndexVO;
import common.utils.ExecutionResults;
import common.utils.GanttTask;

/**
 * Class that is able to print a chart in a pdf using the JFreeChart and iText
 * libraries
 * 
 * @author David Mendez-Acuna
 * @author Jaime Romero
 */
public class ChartPrinter {

	// ------------------------------------------------------------
	// Attribute
	// ------------------------------------------------------------
	
	private static ChartPrinter instance;
	
	private ArrayList<ArrayList<ExecutionResults>> globalExecutionResults;
	
	private boolean printTable;
	
	private boolean printInitialSolutions;
	
	private boolean printSolutions;
	
	private boolean printLog = true;
	
	private String logFile ="";
	
	// ------------------------------------------------------------
	// Constructor
	// ------------------------------------------------------------
	
	private ChartPrinter(){
		globalExecutionResults = new ArrayList<ArrayList<ExecutionResults>>();
	}
	
	public static ChartPrinter getInstance(){
		if(instance == null){
			System.out.println("Nueva instancia");
			instance = new ChartPrinter();
		}
		return instance;
	}
	
	// ------------------------------------------------------------
	// Methods
	// ------------------------------------------------------------

	public void printGlobalResultsHTML(String resultsFile) {
		FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(resultsFile);
            System.out.println(resultsFile);
            logFile = resultsFile.replace("./results/Om_TT/experiment-results", "Log-execution");
            System.out.println(logFile);
            pw = new PrintWriter(fichero);
            printHeaderHTML(pw);
            printBodyHTML(pw);
           
     
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
        //Quitar esto para que funcionen las pruebas.
       globalExecutionResults = new ArrayList<ArrayList<ExecutionResults>>();
	}
	
	private void printBodyHTML(PrintWriter pw) {
		pw.println("<body><table style=\"width:100% height:50px;\"> <tr><td ><img src=\"styles/header.png\" style=\"max-width:80%; max-height:80%; display: block;margin-left: auto; margin-right: auto;\" border=\"0\"></td></tr></table>" +
			"<div  style=\"width: 1000px; margin: 0 auto; padding: 80px 0 40px; font: 0.85em arial;\"><ul class=\"tabs\" persist=\"true\">");
		if(printTable){		
			pw.println("<li><a href=\"#\" rel=\"view1\">Overview Results</a></li>");
			pw.println("<li><a href=\"#\" rel=\"view4\">Parameters</a></li>");
		}
		if(printSolutions){
			pw.println("<li><a href=\"#\" rel=\"view2\">Gantt of best solutions</a></li>");
		}
		if(printInitialSolutions){
			pw.println("<li><a href=\"#\" rel=\"view3\">InitialSolutions</a></li>");
		}
		if(printLog){
			pw.println("<li><a href=\"#\" rel=\"view5\">Log</a></li>");
		}
		
		
		pw.println("</ul><div class=\"tabcontents\">");
		if(printTable){	
			pw.println("<div id=\"view1\" class=\"tabcontent\">");
			printResultTable(pw);
			
			pw.println("<div id=\"view4\" class=\"tabcontent\">");
			printParams(pw);
			
		}
		if(printSolutions){
			pw.println("<div id=\"view2\" class=\"tabcontent\"><table>");
			for ( int i =0; i< globalExecutionResults.size();i++) {
				String nombre = globalExecutionResults.get(i).get(0).getInstanceName();
				pw.println("<tr><td><div id=\"title_"+nombre+"\" class=\"title\"><a href=\"javascript:openInformation('"+nombre+"');\">Open "+nombre+"</a></div>" +
						"<div class=\"informationbox\"  id=\"information_"+nombre+"\"><div style=\"width:950px; height:620px; position:relative;\" id=\"gantt_"+nombre+"\" ></div>"+
						"</div></td><tr>");
			}
			pw.println("</table></div>");
		}
		if(printInitialSolutions){
			pw.println("<div id=\"view3\" class=\"tabcontent\"> <table>");
			for ( int i =0; i< globalExecutionResults.size();i++) {
				String nombre = globalExecutionResults.get(i).get(0).getInstanceName();
				pw.println("<tr><td><div id=\"title_initial_"+nombre+"\" class=\"title\"><a href=\"javascript:openInformation('"+"initial_"+nombre+"');\">Open initial_"+nombre+"</a></div>" +
						"<div class=\"informationbox\"  id=\"information_initial_"+nombre+"\"><div style=\"width:950px; height:620px; position:relative;\" id=\"gantt_initial_"+nombre+"\" ></div>"+
						"</div></td><tr>");
			}
			pw.println("</table></div>");
			
		}
		if(printLog){
			pw.println("<div id=\"view5\" class=\"tabcontent\"> ");
			pw.println("<iframe src=\""+ logFile+"\" width=\"90%\" height=\"70%\"></iframe> ");
			pw.println("</div>");
		}
		
		pw.println("</div></div>");
		pw.println("</body></html>");
	}


	private void printParams(PrintWriter pw) {
		
		pw.println("<table id=\"roundTable\"><thead><tr>"+
				"<th scope=\"col\" class=\"leftTopCorner\">Key</th>");
		pw.println("<th scope=\"col\" class=\"rightTopCorner\">Value</th>");
		
		
		pw.println("</tr></thead><tbody>");
		ArrayList<ExecutionResults> results =globalExecutionResults.get(0);
		Properties properties= results.get(0).getParameters();
		String instanceName = results.get(0).getInstanceName();
		for(String key : properties.stringPropertyNames()) {
			  String value = properties.getProperty(key);
			  pw.println("<tr>");
			  pw.println("<td>"+key+"</td>");
			  pw.println("<td>"+value+"</td>");
			  pw.println("</tr>");
		}
			
			
		
		pw.println("</tr></tbody></table></div>");
	}

	private void printResultTable(PrintWriter pw) {
		pw.println("<table id=\"roundTable\"><thead>	<tr>"+
				"<th scope=\"col\" class=\"leftTopCorner\">PROBLEM</th>"+
				"<th scope=\"col\">INITIAL SOLUTION</th>"+
				"<th scope=\"col\">OPTIMAL</th>"+			
				"<th scope=\"col\">BEST OBJECTIVE</th>"+
				"<th scope=\"col\">AVERAGE OBJECTIVE</th>"+
				"<th scope=\"col\">STOP CRITERIA</th>"+
				"<th scope=\"col\" class=\"rightTopCorner\">VISITED NEIGHBORS</th>"+
				"</tr></thead><tbody>");
		for ( int i =0; i< globalExecutionResults.size();i++) {
			ArrayList<ExecutionResults> results =globalExecutionResults.get(i);
			String instanceName = results.get(0).getInstanceName();
			String initialCMax = results.get(0).getInitialCmax() + ""; 
			String optimalCMax = results.get(0).getOptimal() + ""; 
			String neighbor =  results.get(0).getNumberOfVisitedNeighbors()+"";
			String stopCriteria = results.get(0).getStopCriteria()+"";
			
			int bestCmax = Integer.MAX_VALUE;
			int sumBestCMax = 0;
			int iterations = 0;
			ExecutionResults bestResults = null;
			for (ExecutionResults executionResults : results) {
				if(bestCmax > executionResults.getBestCmax()){
					bestCmax = executionResults.getBestCmax();
					bestResults = executionResults;
				}
				sumBestCMax += executionResults.getBestCmax();
				iterations++;
			}
			String bestCMaxString = bestCmax + ""; 
			if(bestCmax == results.get(0).getOptimal())bestCMaxString+="*";
			if(bestCmax<results.get(0).getOptimal())bestCMaxString+="**";
			double average = sumBestCMax/iterations;
			
			if(i ==globalExecutionResults.size()-1){
				pw.println("<tr>");
				pw.println("<td class =\"leftBottomCorner\"> "+instanceName+"</td>");
				pw.println("<td>"+initialCMax+"</td>");
				pw.println("<td>"+optimalCMax+"</td>");
				pw.println("<td>"+bestCMaxString+"</td>");
				pw.println("<td>"+average+"</td>");
				pw.println("<td>"+stopCriteria+"</td>");
				pw.println("<td class =\"rightBottomCorner\"> "+neighbor+"</td>");
			}
			else{
				pw.println("<tr>");
				pw.println("<td>"+instanceName+"</td>");
				pw.println("<td>"+initialCMax+"</td>");
				pw.println("<td>"+optimalCMax+"</td>");
				pw.println("<td>"+bestCMaxString+"</td>");
				pw.println("<td>"+average+"</td>");
				pw.println("<td>"+stopCriteria+"</td>");
				pw.println("<td>"+neighbor+"</td>");
			}
		}
		pw.println("</tr></tbody></table></div>");
		
	}

	private void printHeaderHTML(PrintWriter pw) {
		
		pw.println("<!DOCTYPE html><html><head>	<title>Results</title>" +
				" <link href=\"styles/tabcontent.css\" rel=\"stylesheet\" type=\"text/css\" />" +
				"<link href=\"styles/roundtable.css\" rel=\"stylesheet\" type=\"text/css\" />" +
				"<link rel=\"stylesheet\" href=\"common/css/style.css\" type=\"text/css\" media=\"screen\" />" +
				"<link type=\"text/css\" rel=\"stylesheet\" href=\"codebase/dhtmlxgantt.css\">" +
				"<script src=\"js/tabcontent.js\" type=\"text/javascript\"></script>" +
				"<script type=\"text/javascript\" language=\"JavaScript\" src=\"codebase/dhtmlxcommon.js\"></script>" +
				"<script type=\"text/javascript\" language=\"JavaScript\" src=\"codebase/dhtmlxgantt.js\"></script>" +
				"<script type=\"text/javascript\" language=\"JavaScript\" src=\"js/openClose.js\"></script>"+
				"<script type=\"text/javascript\" language=\"JavaScript\">" );
		
		for ( int i =0; i< globalExecutionResults.size();i++) {
			if(printSolutions)
				printGanttJs(pw, globalExecutionResults.get(i).get(0),i);
			if(printInitialSolutions)
				printGanttInitialJs(pw, globalExecutionResults.get(i).get(0),i);
			
		}
		
		pw.println("window.onload = function() {");
		for ( int i =0; i< globalExecutionResults.size();i++) {
			String nombre = globalExecutionResults.get(i).get(0).getInstanceName();
			if(printSolutions){
				pw.println("createChartControl"+i+"('gantt_"+nombre+"');closeInformation('"+nombre+"');");
			}
			if(printInitialSolutions){
				pw.println("createChartInitialControl"+i+"('gantt_initial_"+nombre+"');closeInformation('initial_"+nombre+"');");
			}
		}
		
		pw.println("};</script></head>");
		
				
	}

	private void printGanttJs(PrintWriter pw,ExecutionResults executionResults, int num) {
		ArrayList<GanttTask> tasks = executionResults.getTasksFinalSolution();
		pw.println("function createChartControl"+num+"(htmlDiv1){var ganttChartControl = new GanttChart();ganttChartControl.setImagePath(\"codebase/imgs/\");ganttChartControl.setEditable(false);ganttChartControl.showContextMenu(true);ganttChartControl.showDescTask(false,'n,e');ganttChartControl.showDescProject(false,'n,d');	ganttChartControl.getMonthScaleLabel = function(date) {	return \"\";}");
		pw.println("ganttChartControl.num=0;");
		for(int j=0; j< tasks.size();j++){	
			int stationId= tasks.get(j).getStationIdentifier()+1;
			pw.println("var project"+j+" = new GanttProjectInfo("+j+", \""+tasks.get(j).getName() +"\", new Date(2010, 5, 2))");
			ArrayList<OperationIndexVO> finalSolutions = executionResults.getOperationsFinalSolution();
				
			ArrayList<String> listStations = new ArrayList<String>();
			
			for(int i=0; i<finalSolutions.size();i++){
				OperationIndexVO temp = finalSolutions.get(i);
				if(temp.getStationId()==(stationId-1)&& !listStations.contains(temp.getNameMachine())){
					listStations.add(temp.getNameMachine());
				}
			}

			for( int z=0; z< listStations.size();z++){
				String machine =listStations.get(z);
				ArrayList<OperationIndexVO> myOperations = new ArrayList<OperationIndexVO>();
				for(int i=0; i<finalSolutions.size();i++){
					OperationIndexVO temp = finalSolutions.get(i);
					if(temp.getStationId()==(stationId-1) && temp.getNameMachine().equals(machine)){
						myOperations.add(temp);
					}
				}
				pw.println("var parentTask"+j+1+z+ "= new GanttTaskInfo("+j+1+z+", \""+machine+"\", new Date(2010, 5, 2),"+executionResults.getInitialCmax()+", 100, \"\");");
				for(int i=0; i<myOperations.size();i++){
					OperationIndexVO temp = myOperations.get(i);
					pw.println("parentTask"+j+1+z+".addChildTask(new GanttTaskInfo("+j+1+i+z+", \""+temp.getNameJob()+"\", new Date(2010, 5, 2,"+temp.getInitialTime()*24+",0,0), "+(temp.getFinalTime()-temp.getInitialTime())+", 100, \"\"));");
				}
				pw.println("project"+j+".addTask(parentTask"+j+1+z+ ");");
			}
			
			pw.println("ganttChartControl.addProject(project"+j+");");

		}
		pw.println("ganttChartControl.create(htmlDiv1);}");
		
	}
	
	private void printGanttInitialJs(PrintWriter pw,ExecutionResults executionResults, int num) {
		ArrayList<GanttTask> tasks = executionResults.getTasksInitialSolution();
		pw.println("function createChartInitialControl"+num+"(htmlDiv1){var ganttChartControl = new GanttChart();ganttChartControl.setImagePath(\"codebase/imgs/\");ganttChartControl.setEditable(false);ganttChartControl.showContextMenu(true);ganttChartControl.showDescTask(false,'n,e');ganttChartControl.showDescProject(false,'n,d');	ganttChartControl.getMonthScaleLabel = function(date) {	return \"\";}");
		pw.println("ganttChartControl.num=0;");
		for(int j=0; j< tasks.size();j++){	
			int stationId= tasks.get(j).getStationIdentifier()+1;
			pw.println("var project"+j+" = new GanttProjectInfo("+j+", \""+tasks.get(j).getName() +"\", new Date(2010, 5, 2))");
			ArrayList<OperationIndexVO> initialSolutions = executionResults.getOperationsInitialSolution();
			
			ArrayList<String> listStations = new ArrayList<String>();
			
			for(int i=0; i<initialSolutions.size();i++){
				OperationIndexVO temp = initialSolutions.get(i);
				if(temp.getStationId()==(stationId-1)&& !listStations.contains(temp.getNameMachine())){
					listStations.add(temp.getNameMachine());
				}
			}

			
			for( int z=0; z< listStations.size();z++){
				String machine =listStations.get(z);
				ArrayList<OperationIndexVO> myOperations = new ArrayList<OperationIndexVO>();
				for(int i=0; i<initialSolutions.size();i++){
					OperationIndexVO temp = initialSolutions.get(i);
					if(temp.getStationId()==(stationId-1) && temp.getNameMachine().equals(machine)){
						myOperations.add(temp);
					}
				}
				pw.println("var parentTask"+j+1+z+ "= new GanttTaskInfo("+j+1+z+", \""+machine+"\", new Date(2010, 5, 2),"+executionResults.getInitialCmax()+", 100, \"\");");
				for(int i=0; i<myOperations.size();i++){
					OperationIndexVO temp = myOperations.get(i);
					pw.println("parentTask"+j+1+z+".addChildTask(new GanttTaskInfo("+j+1+i+z+", \""+temp.getNameJob()+"\", new Date(2010, 5, 2,"+temp.getInitialTime()*24+",0,0), "+(temp.getFinalTime()-temp.getInitialTime())+", 100, \"\"));");
				}
				pw.println("project"+j+".addTask(parentTask"+j+1+z+ ");");
			}	
			
			
	
			pw.println("ganttChartControl.addProject(project"+j+");");

		}
		pw.println("ganttChartControl.create(htmlDiv1);}");
		
	}
	
	public void addResults(ArrayList<ExecutionResults> results) {
		globalExecutionResults.add(results);
		printTable=globalExecutionResults.get(0).get(0).isPrintTable();
		printInitialSolutions = globalExecutionResults.get(0).get(0).isPrintInitialSolution();
		printSolutions = globalExecutionResults.get(0).get(0).isPrintFinalSolution();
	}
}