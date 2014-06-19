package common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Class that formats the lines of the log record
 * @author Jaime Romero
 */
public class HtmlFormatter extends Formatter {
	
  // This method is called for every log records
  public String format(LogRecord rec) {
    StringBuffer buf = new StringBuffer(1000);
    buf.append("<tr>");
    buf.append("<td>");

    if (rec.getLevel().intValue() >= Level.WARNING.intValue()) {
      buf.append("<b>");
      buf.append(rec.getLevel());
      buf.append("</b>");
    } else {
      buf.append(rec.getLevel());
    }
    buf.append("</td>");
    buf.append("<td>");
    buf.append(calcDate(rec.getMillis()));
    buf.append("</td>");
    buf.append("<td>");
    buf.append(formatMessage(rec));
    buf.append('\n');
    buf.append("</td>");
    buf.append("</tr>\n");
    return buf.toString();
  }

  /**
   * Calculate the current date of the execution
   * @param millisecs
   * @return string with the date.
   */
  private String calcDate(long millisecs) {
    SimpleDateFormat date_format = new SimpleDateFormat("MMM dd,yyyy HH:mm");
    Date resultdate = new Date(millisecs);
    return date_format.format(resultdate);
  }

  public String getHead(Handler h) {
    return "<HTML>\n<HEAD>\n" 
        + "\n</HEAD>\n<BODY>\n<PRE>\n"
        + "<table width=\"100%\" border>\n  "
        + "<tr><th>Level</th>" +
        "<th>Time</th>" +
        "<th>Log Message</th>" +
        "</tr>\n";
  }


  public String getTail(Handler h) {
    return "</table>\n  </PRE></BODY>\n</HTML>\n";
  }
} 

