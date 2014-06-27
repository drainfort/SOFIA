package chart.printer;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Properties;

import structure.impl.Graph;

import common.types.OperationIndexVO;
import common.utils.ExecutionResults;
import common.utils.GanttTask;
import common.utils.Point;

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
	
	private boolean printLog;
	
	private boolean printCharts;
	
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

	public void printGlobalResultsHTML(String resultsFile, String LogFile) {
		FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(resultsFile);
            System.out.println(resultsFile);
            logFile = LogFile;
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
		pw.println("<body>" +
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
		if(printCharts){
			pw.println("<li><a href=\"#\" rel=\"view6\">Improvement</a></li>");
		}
		
		
		pw.println("</ul><div class=\"tabcontents\">");
		if(printTable){	
			pw.println("<div id=\"view1\" class=\"tabcontent\">");
			printResultTable(pw);
			
			pw.println("<div id=\"view4\" class=\"tabcontent\">");
			printParams(pw);
			
		}
		if(printSolutions){
			pw.println("<div id=\"view2\" class=\"tabcontent\"><table cellspacing=\"0\"><tr><td valign=\"top\">");
			pw.println("<table>");
			
			for ( int i =0; i< globalExecutionResults.size();i++) {
				String nombre = globalExecutionResults.get(i).get(0).getInstanceName();
				pw.println("<tr><td><div id=\"title_"+nombre+"\" class=\"title\"><a href=\"javascript:openInformationGantt('"+"gantt_"+nombre+"', 'ganttSolution');\">Open "+nombre+"</a></div>"
						+"</div></td></tr>");
			}
			pw.println("</table>");
			pw.println("</td><td>");
			pw.println("<div class=\"informationbox\" style=\"width:1250px; height:620px; position:relative;\" id=\"ganttSolution\" ></div>");
			pw.println("</td></tr>");
			
			pw.println("</table></div>");
		}
		if(printInitialSolutions){
			pw.println("<div id=\"view3\" class=\"tabcontent\"><table cellspacing=\"0\"><tr><td valign=\"top\">");
			pw.println("<table>");
			
			for ( int i =0; i< globalExecutionResults.size();i++) {
				String nombre = globalExecutionResults.get(i).get(0).getInstanceName();
				pw.println("<tr><td><div id=\"title_"+nombre+"\" class=\"title\"><a href=\"javascript:openInformationGantt('"+"initial_"+nombre+"', 'initialSolution');\">Open initial_"+nombre+"</a></div>"
						+"</div></td><tr>");
			}
			pw.println("</table>");
			pw.println("</td><td>");
			pw.println("<div class=\"informationbox\" style=\"width:1250px; height:620px; position:relative;\" id=\"initialSolution\" ></div>");
			pw.println("</td></tr>");
			pw.println("</table></div>");
			
		}
		if(printLog){
			pw.println("<div id=\"view5\" class=\"tabcontent\"> ");
			pw.println("<iframe src=\""+ logFile+"\" width=\"90%\" height=\"70%\"></iframe> ");
			pw.println("</div>");
		}
		if(printCharts){
			pw.println("<div id=\"view6\" class=\"tabcontent\"><table cellspacing=\"0\"><tr><td valign=\"top\">");
			pw.println("<table>");
			for ( int i =0; i< globalExecutionResults.size();i++) {
				String nombre = globalExecutionResults.get(i).get(0).getInstanceName();
				pw.println("<tr><td><div id=\"title_chart_"+nombre+"\" class=\"title\"><a href=\"javascript:openInformation('"+"chart_"+nombre+"');\">Open initial_"+nombre+"</a></div>" +
						"</td><tr>");

			}
			pw.println("</table>");
			pw.println("</td><td valign=\"top\">");
			pw.println("<table>");
			for ( int i =0; i< globalExecutionResults.size();i++) {
				String nombre = globalExecutionResults.get(i).get(0).getInstanceName();
				pw.println("<tr><td><div class=\"informationbox\"  id=\"information_chart_"+nombre+"\"><div style=\"width:950px; height:500px; position:relative;\" id=\"chart_"+nombre+"\" ></div>"+
						"</div></td><tr>");

			}
			pw.println("</table>");
			pw.println("</td></tr>");
			pw.println("</table></div>");
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
		//String instanceName = results.get(0).getInstanceName();
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
				"<th scope=\"col\">AVERAGE PT</th>"+
				"<th scope=\"col\">MODE</th>"+
				"<th scope=\"col\">GAP</th>"+
				"<th scope=\"col\">TimesBetter</th>"+
				"<th scope=\"col\" class=\"rightTopCorner\">VISITED NEIGHBORS</th>"+
				"</tr></thead><tbody>");
		for ( int i =0; i< globalExecutionResults.size();i++) {
			ArrayList<ExecutionResults> results =globalExecutionResults.get(i);
			String instanceName = results.get(0).getInstanceName();
			String initialCMax = results.get(0).getInitialCmax() + ""; 
			String optimalCMax = results.get(0).getOptimal() + ""; 
			String neighbor =  results.get(0).getNumberOfVisitedNeighbors()+"";
			String stopCriteria = results.get(0).getStopCriteria()+"";
			
			double bestCmax = Integer.MAX_VALUE;
			double sumBestCMax = 0;
			double iterations = 0;
			double processingTime =0;
			//ExecutionResults bestResults = null;
			int lessBKS = 0;
			for (ExecutionResults executionResults : results) {
				if(bestCmax > executionResults.getBestCmax()){
					bestCmax = executionResults.getBestCmax();
					//bestResults = executionResults;
				}
				if(executionResults.getBestCmax()<=results.get(0).getOptimal())
					lessBKS++;
				sumBestCMax += executionResults.getBestCmax();
				processingTime += executionResults.getExecutionTime();
				iterations++;
			}
			String bestCMaxString = bestCmax + ""; 
			if(bestCmax == results.get(0).getOptimal())bestCMaxString+="*";
			if(bestCmax<results.get(0).getOptimal())bestCMaxString+="**";
			double average = sumBestCMax/iterations;
			double averageProcessing =processingTime/iterations;
			
			double mode = 0;
			int maxCount = 0;
			

		    for (ExecutionResults executionResults : results) {
		        int count = 0;
		        for (ExecutionResults executionResults2 : results) {
		            if (executionResults.getBestCmax() == executionResults2.getBestCmax()) ++count;
		        }
		        if (count > maxCount) {
		            maxCount = count;
		            mode = executionResults.getBestCmax();
		        }
		    }
		    double gap = ((bestCmax-results.get(0).getOptimal())/results.get(0).getOptimal())*100;
		    DecimalFormat df2 = new DecimalFormat("###,##");
			
			//System.out.println(average);
			
			if(i ==globalExecutionResults.size()-1){
				pw.println("<tr>");
				pw.println("<td class =\"leftBottomCorner\"> "+instanceName+"</td>");
				pw.println("<td>"+initialCMax+"</td>");
				pw.println("<td>"+optimalCMax+"</td>");
				pw.println("<td>"+bestCMaxString+"</td>");
				pw.println("<td>"+average+"</td>");
				pw.println("<td>"+stopCriteria+"</td>");
				pw.println("<td>"+averageProcessing+"</td>");
				pw.println("<td>"+mode+"</td>");
				pw.println("<td>"+Double.valueOf(df2.format(gap))+"%</td>");
				pw.println("<td>"+lessBKS+"</td>");
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
				pw.println("<td>"+averageProcessing+"</td>");
				pw.println("<td>"+mode+"</td>");
				pw.println("<td>"+Double.valueOf(df2.format(gap))+"%</td>");
				pw.println("<td>"+lessBKS+"</td>");
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
				"<script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>"+
				"<script type=\"text/javascript\" language=\"JavaScript\">");
		
		pw.println("function createChartControl(htmlDiv1, name){var ganttChartControl = new GanttChart();ganttChartControl.setImagePath(\"codebase/imgs/\");ganttChartControl.setEditable(false);ganttChartControl.showContextMenu(true);ganttChartControl.showDescTask(false,'n,e');ganttChartControl.showDescProject(false,'n,d');	ganttChartControl.getMonthScaleLabel = function(date) {	return \"\";}");
		pw.println("ganttChartControl.num=0;");
		
		for ( int i =0; i< globalExecutionResults.size();i++) {
			String nombre = globalExecutionResults.get(i).get(0).getInstanceName();
			if(printSolutions)
				printGanttJs(pw, globalExecutionResults.get(i).get(0),"gantt_"+nombre);
			if(printInitialSolutions)
				printGanttJs(pw, globalExecutionResults.get(i).get(0),"initial_"+nombre);
			
		}
		pw.println("ganttChartControl.create(htmlDiv1);}");
		
		for ( int i =0; i< globalExecutionResults.size();i++) {
			if(printCharts)
				printChartJs(pw, globalExecutionResults.get(i).get(0),i);
			
		}
		
		pw.println("window.onload = function() {");
		for ( int i =0; i< globalExecutionResults.size();i++) {
			String nombre = globalExecutionResults.get(i).get(0).getInstanceName();
			if(printCharts){
				pw.println("closeInformation('chart_"+nombre+"');");
			}
		}
		
		pw.println("};</script></head>");		
				
	}

	private void printChartJs(PrintWriter pw, ExecutionResults executionResults, int i) {
		if(executionResults.getGraphics().size()>0){
			ArrayList<Point> points = executionResults.getGraphics().get(0).getPoints();
			pw.println("google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});");
			pw.println("  google.setOnLoadCallback(drawChart"+i+");");
			pw.println("  function drawChart"+i+"() {");
			pw.println("    var data = google.visualization.arrayToDataTable([");
			pw.println("       ['Time', 'OF'],");
			for(int j=0; j< points.size();j++){
				Point temp = points.get(j);
				if(j!=points.size()-1){
					pw.println("["+temp.getX()+","+temp.getY()+"],");
				}
				else{
					pw.println("["+temp.getX()+","+temp.getY()+"]");
				}
				
			}
		    pw.println("     ]);");
	
		    pw.println("    var options = {");
		    pw.println("       title: 'Iteration"+i+"'");
		    pw.println(" };");
	
		    pw.println("var chart = new google.visualization.LineChart(document.getElementById('chart_"+executionResults.getInstanceName()+"'));");
		    pw.println("    chart.draw(data, options);");
		    pw.println("  }");
		}
	}

	private void printGanttJs(PrintWriter pw,ExecutionResults executionResults, String name) {
		ArrayList<GanttTask> tasks = executionResults.getTasksFinalSolution();
		pw.println("if(name =='"+name+"'){");
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
				pw.println("var parentTask"+j+1+z+ "= new GanttTaskInfo("+j+1+z+", \""+machine+"\", new Date(2010, 5, 2),"+executionResults.getBestCmax()+", 100, \"\");");
				for(int i=0; i<myOperations.size();i++){
					OperationIndexVO temp = myOperations.get(i);
					pw.println("parentTask"+j+1+z+".addChildTask(new GanttTaskInfo("+j+1+i+z+", \""+temp.getNameJob()+"\", new Date(2010, 5, 2,"+temp.getInitialTime()*24+",0,0), "+(temp.getFinalTime()-temp.getInitialTime())+", 100, \"\"));");
				}
				pw.println("project"+j+".addTask(parentTask"+j+1+z+ ");");
			}
			
			pw.println("ganttChartControl.addProject(project"+j+");");

		}
		pw.println("}");
	}
	
	public void restart(){
		globalExecutionResults= new ArrayList<ArrayList<ExecutionResults>>();
		printTable=false;
		printInitialSolutions = false;
		printSolutions = false;
		printLog = false;
		printCharts = false;
	}
	
	
	public void addResults(ArrayList<ExecutionResults> results) {
		globalExecutionResults.add(results);
		printTable=globalExecutionResults.get(0).get(0).isPrintTable();
		printInitialSolutions = globalExecutionResults.get(0).get(0).isPrintInitialSolution();
		printSolutions = globalExecutionResults.get(0).get(0).isPrintFinalSolution();
		printLog = globalExecutionResults.get(0).get(0).isPrintLog();
		printCharts = true;
	}
	
	// ------------------------------------------------------------
	// Draw Graph
	// ------------------------------------------------------------
	
	public void drawGraph(Graph graph, String resultsFile){
		FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(resultsFile);
            System.out.println(resultsFile);
            pw = new PrintWriter(fichero);
            
            pw.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">");
            pw.println("<html lang=\"en\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">");
            pw.println("<title> Graph - </title> <link rel=\"stylesheet\" href=\"./style/style.css\" type=\"text/css\">");
		    pw.println("</head> <body> <canvas id=\"viewport\" width=\"1000\" height=\"800\"></canvas>");
		    pw.println("  <script src=\"https://ajax.googleapis.com/ajax/libs/jquery/1.6.1/jquery.min.js\"></script>");
		    pw.println("  <script src=\"./js/arbor.js\"></script>  ");
		    pw.println("  <script type=\"text/javascript\" language=\"JavaScript\">");
		    pw.println("  	(function($){");
		    pw.println("  var Renderer = function(canvas){");
		    pw.println("    var canvas = $(canvas).get(0)");
		    pw.println("    var ctx = canvas.getContext(\"2d\");");
		    pw.println("    var particleSystem");
		    pw.println("    var that = {");
		    pw.println("      init:function(system){");
		    pw.println("        particleSystem = system");
		    pw.println("        particleSystem.screenSize(canvas.width, canvas.height)");
		    pw.println("        particleSystem.screenPadding(10)},");
		    pw.println("      redraw:function(){");
		    pw.println("        ctx.fillStyle = \"white\"");
		    pw.println("        ctx.fillRect(0,0, canvas.width, canvas.height)");
		    pw.println("        particleSystem.eachEdge(function(edge, pt1, pt2){");
		    pw.println("          ctx.strokeStyle = \"rgba(0,0,0, .333)\"");
		    pw.println("          ctx.lineWidth = 1");
		    pw.println("          ctx.beginPath()");
		    pw.println("          ctx.moveTo(pt1.x, pt1.y)");
		    pw.println("          ctx.lineTo(pt2.x, pt2.y)");
		    pw.println("          ctx.stroke()})");
		    pw.println("        particleSystem.eachNode(function(node, pt){");
		    pw.println("          var w = 50");
		    pw.println("          ctx.fillStyle = (node.data.critical) ? \"#EEE8E8\" : (node.data.change) ? \"#22F9FF\": \"white\"");
		    pw.println("          ctx.fillRect(pt.x-w/2, pt.y-w/2, w,w)");
		    pw.println("		  if(node.data.change){");
		    pw.println("			ctx.fillStyle = \"#EEE8E8\""); 
		    pw.println("			ctx.fillRect(pt.x-w/2+w/8, pt.y-w/2+w/8, 6*w/8,6*w/8)}");
		    pw.println("		  label = node.data.label || \"\"");
		    pw.println("		  if (label){");
		    pw.println("            ctx.font = \"bold 11px Arial\"");
		    pw.println("            ctx.textAlign = \"center\"");
		    pw.println("            ctx.fillStyle = \"black\"");
		    pw.println("            ctx.fillText(label||\"\", pt.x, pt.y+4)}})  },      }");
		    pw.println("    return that}    ");
		    pw.println("  $(document).ready(function(){");
		    pw.println("    var sys = arbor.ParticleSystem(50, 800, 0) ");
		    pw.println("    sys.parameters({gravity:false}) ");
		    pw.println("    sys.renderer = Renderer(\"#viewport\")"); 
		    
		    //Iterar sobre el grafo
		    
		    
		    
		    pw.println("    sys.addNode('f', {label:'<1,2,3>', mass:1, critical:true})");
		    pw.println("	sys.addNode('1', {label:'<1,1,1>', mass:1, change:true})");
		    pw.println("	sys.addEdge('f','1')");
		    pw.println("	sys.addNode('2', {label:'<1,2,1>', mass:1})");
		    pw.println("	sys.addEdge('2','1') })");
		    
		    //Iterar sobre el grafo
		    pw.println("})(this.jQuery)");
		    pw.println("</script></body></html>");
            

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
	}
}