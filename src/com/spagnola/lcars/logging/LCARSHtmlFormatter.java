package com.spagnola.lcars.logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class LCARSHtmlFormatter extends Formatter {

	@Override
	public String format(LogRecord record) {
		StringBuffer buf = new StringBuffer(1000);

		buf.append("<tr>");
		buf.append("<td>");

		/**
		 * Bold any levels, WARNING or more severe.
		 */
		if (record.getLevel().intValue() >= Level.WARNING.intValue()) {
			buf.append("<b>");
			buf.append(record.getLevel());
			buf.append("</b>");
		} 
		else {
			buf.append(record.getLevel());
		}

		buf.append("</td>");
		buf.append("<td>");
		buf.append(calcDate(record.getMillis()));
		buf.append("</td>");
		buf.append("<td>");
		buf.append(formatMessage(record));
		buf.append('\n');
		buf.append("</td>");
		buf.append("</tr>\n");
		return buf.toString();
	}

	/**
	 * 
	 * @param millisecs
	 * @return
	 */
	private String calcDate(long millisecs) {

		SimpleDateFormat date_format = new SimpleDateFormat("MMM dd,yyyy HH:mm");
		Date resultdate = new Date(millisecs);

		return date_format.format(resultdate);
	}


	/** 
	 * This method is called just after the handler using this
	 * formatter is created.
	 * 
	 * @see java.util.logging.Formatter#getHead(java.util.logging.Handler)
	 */
	public String getHead(Handler h) {

		return "<HTML>\n<HEAD>\n" + (new Date()) 
				+ "\n</HEAD>\n<BODY>\n<PRE>\n"
		        + "<table width=\"100%\" border>\n  "
		        + "<tr><th>Level</th>" +
		        "<th>Time</th>" +
		        "<th>Log Message</th>" +
		        "</tr>\n";
	}


	
	/**
	 * This method is called just after the handler using this
	 * formatter is closed.
	 * 
	 * @see java.util.logging.Formatter#getTail(java.util.logging.Handler)
	 */
	public String getTail(Handler h) {

		return "</table>\n  </PRE></BODY>\n</HTML>\n";
	}

}
