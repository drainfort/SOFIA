package chart.printer;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import common.types.OperationIndexVO;
import common.utils.ExecutionResults;
import common.utils.GanttTask;

/**
 * Class that is able to print a chart in a pdf using the JFreeChart and iText
 * libraries
 * 
 * @author David Mendez-Acuna
 * 
 */
public class ChartPrinter {

	// ------------------------------------------------------------
	// Attribute
	// ------------------------------------------------------------
	
	private static ChartPrinter instance;
	
	private ArrayList<ArrayList<ExecutionResults>> globalExecutionResults;
	
	// ------------------------------------------------------------
	// Constructor
	// ------------------------------------------------------------
	
	private ChartPrinter(){
		globalExecutionResults = new ArrayList<ArrayList<ExecutionResults>>();
	}
	
	public static ChartPrinter getInstance(){
		if(instance == null)
			instance = new ChartPrinter();
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
	}
	
	private void printBodyHTML(PrintWriter pw) {
		pw.println("<body><table style=\"width:100% height:50px;\"> <tr><td ><img src=\"styles/header.png\" style=\"max-width:80%; max-height:80%; display: block;margin-left: auto; margin-right: auto;\" border=\"0\"></td></tr></table>" +
			"<div  style=\"width: 1000px; margin: 0 auto; padding: 80px 0 40px; font: 0.85em arial;\">"+
	        "<ul class=\"tabs\" persist=\"true\"><li><a href=\"#\" rel=\"view1\">Overview Results</a></li>" +
	        "<li><a href=\"#\" rel=\"view2\">Gantt of best solutions</a></li><li><a href=\"#\" rel=\"view3\">InitialSolutions</a></li>" +
	        "</ul><div class=\"tabcontents\"><div id=\"view1\" class=\"tabcontent\">");
		printResultTable(pw);
		pw.println("<div id=\"view2\" class=\"tabcontent\"><table>");
		for ( int i =0; i< globalExecutionResults.size();i++) {
			String nombre = globalExecutionResults.get(i).get(0).getInstanceName();
			pw.println("<tr><td><div id=\"title_"+nombre+"\" class=\"title\"><a href=\"javascript:openInformation('"+nombre+"');\">Open "+nombre+"</a></div>" +
					"<div class=\"informationbox\"  id=\"information_"+nombre+"\"><div style=\"width:950px; height:620px; position:relative;\" id=\"gantt_"+nombre+"\" ></div>"+
					"</div></td><tr>");
		}			
		pw.println("</table></div><div id=\"view3\" class=\"tabcontent\"> <table>");
		for ( int i =0; i< globalExecutionResults.size();i++) {
			String nombre = globalExecutionResults.get(i).get(0).getInstanceName();
			pw.println("<tr><td><div id=\"title_initial_"+nombre+"\" class=\"title\"><a href=\"javascript:openInformation('"+"initial_"+nombre+"');\">Open initial_"+nombre+"</a></div>" +
					"<div class=\"informationbox\"  id=\"information_initial_"+nombre+"\"><div style=\"width:950px; height:620px; position:relative;\" id=\"gantt_initial_"+nombre+"\" ></div>"+
					"</div></td><tr>");
		}	
		pw.println("</div></div></div></table>");	
		pw.println("</body></html>");
	}


	private void printResultTable(PrintWriter pw) {
		pw.println("<table id=\"roundTable\"><thead>	<tr>"+
				"<th scope=\"col\" class=\"leftTopCorner\">PROBLEM</th>"+
				"<th scope=\"col\">INITIAL SOLUTION</th>"+
				"<th scope=\"col\">OPTIMAL</th>"+			
				"<th scope=\"col\">BEST OBJECTIVE</th>"+
				"<th scope=\"col\">AVERAGE OBJECTIVE</th>"+
				"<th scope=\"col\" class=\"rightTopCorner\">VISITED NEIGHBORS</th>"+
				"</tr></thead><tbody>");
		for ( int i =0; i< globalExecutionResults.size();i++) {
			ArrayList<ExecutionResults> results =globalExecutionResults.get(i);
			String instanceName = results.get(0).getInstanceName();
			String initialCMax = results.get(0).getInitialCmax() + ""; 
			String optimalCMax = results.get(0).getOptimal() + ""; 
			String neighbor =  results.get(0).getNumberOfVisitedNeighbors()+"";
			
			int bestCmax = Integer.MAX_VALUE;
			int sumBestCMax = 0;
			int iterations = 0;
			for (ExecutionResults executionResults : results) {
				if(bestCmax > executionResults.getBestCmax()){
					bestCmax = executionResults.getBestCmax();
				}
				sumBestCMax += executionResults.getBestCmax();
				iterations++;
			}
			String bestCMaxString = bestCmax + ""; 
			if(bestCmax == results.get(0).getOptimal())bestCMaxString+="*";
			double average = sumBestCMax/iterations;
			
			if(i ==globalExecutionResults.size()-1){
				pw.println("<tr>");
				pw.println("<td class =\"leftBottomCorner\"> "+instanceName+"</td>");
				pw.println("<td>"+initialCMax+"</td>");
				pw.println("<td>"+optimalCMax+"</td>");
				pw.println("<td>"+bestCMaxString+"</td>");
				pw.println("<td>"+average+"</td>");
				pw.println("<td class =\"rightBottomCorner\"> "+neighbor+"</td>");
			}
			else{
				pw.println("<tr>");
				pw.println("<td>"+instanceName+"</td>");
				pw.println("<td>"+initialCMax+"</td>");
				pw.println("<td>"+optimalCMax+"</td>");
				pw.println("<td>"+bestCMaxString+"</td>");
				pw.println("<td>"+average+"</td>");
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
			printGanttJs(pw, globalExecutionResults.get(i).get(0),i);
			printGanttInitialJs(pw, globalExecutionResults.get(i).get(0),i);
			
		}
		
		pw.println("window.onload = function() {");
		for ( int i =0; i< globalExecutionResults.size();i++) {
			String nombre = globalExecutionResults.get(i).get(0).getInstanceName();
			pw.println("createChartControl"+i+"('gantt_"+nombre+"');");
			pw.println("createChartInitialControl"+i+"('gantt_initial_"+nombre+"');closeInformation('"+nombre+"');closeInformation('initial_"+nombre+"');");
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
			ArrayList<OperationIndexVO> myOperations = new ArrayList<OperationIndexVO>();
			String machine = "";
			for(int i=0; i<finalSolutions.size();i++){
				OperationIndexVO temp = finalSolutions.get(i);
				if(temp.getStationId()==(stationId-1)){
					myOperations.add(temp);
					machine =temp.getNameMachine();
				}
			}
			pw.println("var parentTask"+j+1+ "= new GanttTaskInfo("+j+1+", \""+machine+"\", new Date(2010, 5, 2),"+executionResults.getBestCmax()+", 100, \"\");");
			for(int i=0; i<myOperations.size();i++){
				OperationIndexVO temp = myOperations.get(i);
				pw.println("parentTask"+j+1+".addChildTask(new GanttTaskInfo("+j+1+i+", \""+temp.getNameJob()+"\", new Date(2010, 5, 2,"+temp.getInitialTime()*24+",0,0), "+(temp.getFinalTime()-temp.getInitialTime())+", 100, \"\"));");
			}
			
			pw.println("project"+j+".addTask(parentTask"+j+1+ ");");
		
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
			ArrayList<OperationIndexVO> myOperations = new ArrayList<OperationIndexVO>();
			String machine = "";
			for(int i=0; i<initialSolutions.size();i++){
				OperationIndexVO temp = initialSolutions.get(i);
				if(temp.getStationId()==(stationId-1)){
					myOperations.add(temp);
					machine =temp.getNameMachine();
				}
			}
			pw.println("var parentTask"+j+1+ "= new GanttTaskInfo("+j+1+", \""+machine+"\", new Date(2010, 5, 2),"+executionResults.getBestCmax()+", 100, \"\");");
			for(int i=0; i<myOperations.size();i++){
				OperationIndexVO temp = myOperations.get(i);
				pw.println("parentTask"+j+1+".addChildTask(new GanttTaskInfo("+j+1+i+", \""+temp.getNameJob()+"\", new Date(2010, 5, 2,"+temp.getInitialTime()*24+",0,0), "+(temp.getFinalTime()-temp.getInitialTime())+", 100, \"\"));");
			}
			
			pw.println("project"+j+".addTask(parentTask"+j+1+ ");");
		
			pw.println("ganttChartControl.addProject(project"+j+");");

		}
		pw.println("ganttChartControl.create(htmlDiv1);}");
		
	}
	
	public void printGlobalResults(String resultsFile) {
		Document document = new Document();
		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(resultsFile));
			document.open();
			
			PdfPTable resultsTable = new PdfPTable(6);
			Font fontbold = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
			
			Phrase c1Phrase = new Phrase("Problem");
			c1Phrase.setFont(fontbold);
			PdfPCell c1 = new PdfPCell(c1Phrase);
		    resultsTable.addCell(c1);
		    
		    PdfPCell c2 = new PdfPCell(new Phrase("Initial Solution"));
		    c2.setHorizontalAlignment(Element.ALIGN_CENTER);
		    resultsTable.addCell(c2);
		    
		    PdfPCell c3 = new PdfPCell(new Phrase("Optimal"));
		    c3.setHorizontalAlignment(Element.ALIGN_CENTER);
		    resultsTable.addCell(c3);
		    
		    PdfPCell c4 = new PdfPCell(new Phrase("Best Objective"));
		    c4.setHorizontalAlignment(Element.ALIGN_CENTER);
		    resultsTable.addCell(c4);
		    
		    PdfPCell c5 = new PdfPCell(new Phrase("Average Objective"));
		    c5.setHorizontalAlignment(Element.ALIGN_CENTER);
		    resultsTable.addCell(c5);
		    
		    PdfPCell c6 = new PdfPCell(new Phrase("Visited Neighbors"));
		    c6.setHorizontalAlignment(Element.ALIGN_CENTER);
		    resultsTable.addCell(c6);
			
			for (ArrayList<ExecutionResults> results : globalExecutionResults) {
				String instanceName = results.get(0).getInstanceName();
				String initialCMax = results.get(0).getInitialCmax() + ""; 
				String optimalCMax = results.get(0).getOptimal() + ""; 
				String neighbor =  results.get(0).getNumberOfVisitedNeighbors()+"";
				
				int bestCmax = Integer.MAX_VALUE;
				int sumBestCMax = 0;
				int iterations = 0;
				for (ExecutionResults executionResults : results) {
					if(bestCmax > executionResults.getBestCmax()){
						bestCmax = executionResults.getBestCmax();
					}
					sumBestCMax += executionResults.getBestCmax();
					iterations++;
				}
				String bestCMaxString = bestCmax + ""; 
				if(bestCmax == results.get(0).getOptimal())bestCMaxString+="*";

				PdfPCell instanceNameCell = new PdfPCell(new Phrase(instanceName));
			    resultsTable.addCell(instanceNameCell);
				
				PdfPCell initialCMaxCell = new PdfPCell(new Phrase(initialCMax));
				initialCMaxCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			    resultsTable.addCell(initialCMaxCell);
			    
			    PdfPCell optimalCMaxCell = new PdfPCell(new Phrase(optimalCMax));
			    optimalCMaxCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			    resultsTable.addCell(optimalCMaxCell);
				
				PdfPCell bestCMaxCell = new PdfPCell(new Phrase(bestCMaxString));
				bestCMaxCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			    resultsTable.addCell(bestCMaxCell);
				
				double average = sumBestCMax/iterations;
				
				PdfPCell averageCMaxCell = new PdfPCell(new Phrase(average+""));
				averageCMaxCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			    resultsTable.addCell(averageCMaxCell);
				
			    PdfPCell neighborCell = new PdfPCell(new Phrase(neighbor));
			    neighborCell.setHorizontalAlignment(Element.ALIGN_CENTER);
			    resultsTable.addCell(neighborCell);
			}
			document.add(resultsTable);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			document.close();
		}
	}
	
	public void addResults(ArrayList<ExecutionResults> results) {
		globalExecutionResults.add(results);
	}
	
	// ------------------------------------------------------------
	// Utilities
	// ------------------------------------------------------------

	/**
	 * Utility method for creating <code>Date</code> objects.
	 * 
	 * @param day
	 *            the date.
	 * @param month
	 *            the month.
	 * @param year
	 *            the year.
	 * 
	 * @return a date.
	 */
	private static Date date(final int hrs, final int min) {
		@SuppressWarnings("deprecation")
		final Date result0 = new Date(1, 1, 1, hrs, min);
		return result0;
	}

}
