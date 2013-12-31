package com.spagnola.lcars.logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LCARSLogger {

	static private FileHandler fileTxt;
	static private SimpleFormatter formatterTxt;

	static private FileHandler fileHTML;
	static private LCARSHtmlFormatter formatterHTML;

	static public void setup() throws IOException {

		/** Get the global logger to configure it */
		Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		logger.setLevel(Level.INFO);

		fileTxt = new FileHandler("Logging.txt");
		fileHTML = new FileHandler("Logging.html");

		/** Create the txt Formatter */
		formatterTxt = new SimpleFormatter();
		fileTxt.setFormatter(formatterTxt);
		logger.addHandler(fileTxt);

		/** Create the HTML Formatter */
		formatterHTML = new LCARSHtmlFormatter();
		fileHTML.setFormatter(formatterHTML);
		logger.addHandler(fileHTML);
		
		logger.info("Logger setup completed...");
		logger.warning("Logger test warning...");
		logger.severe("Logger test severe...");
	}

}
