package com.spagnola.lcars.test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.spagnola.lcars.LCARS;
import com.spagnola.lcars.elements.LCARSPanel;
import com.spagnola.lcars.elements.LCARSTime;
import com.spagnola.lcars.frame.LCARS_Frame;
import com.spagnola.lcars.logging.LCARSLogger;
import com.spagnola.lcars.panels.LClockPanel;
import com.spagnola.lcars.panels.LoginPanel;

public class ClockTest extends JFrame {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	LoginPanel loginPanel;
	ActionListener loginAction;
	ActionListener logoutAction;

	public static void main(String[] args) {
	    SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	        	new ClockTest();
	        }
	    });		
	}
	
	public ClockTest() {
		
		/**
		 * Set up the logger for the LCARS application.
		 */
		try {
			LCARSLogger.setup();
		} 
		catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Problems with creating the log files");
		}
		

		/**
		 * Set up the frame as maximized and undecorated with a title of "LCARS Main",
		 * and EXIT_ON_CLOSE as the default close operation.
		 */
		this.getContentPane().setBackground(Color.BLACK);
		this.setTitle("LCARS Clock Test");
		this.setUndecorated(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		/**
		 * Create the LCARS clock panel to test.
		 */
		LClockPanel lcp = new LClockPanel();
		lcp.setPreferredSize(new Dimension(LCARSPanel.PREFERRED_WIDTH, LCARSPanel.PREFERRED_HEIGHT));
		lcp.setTitle("LCARS Clock");
		this.getContentPane().add(lcp);

		
		validate();
	}
}
