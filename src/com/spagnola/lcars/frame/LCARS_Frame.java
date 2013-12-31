package com.spagnola.lcars.frame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.*;

import com.spagnola.lcars.logging.LCARSLogger;
import com.spagnola.lcars.panels.*;
import com.spagnola.lcars.test.TestPanel;

public class LCARS_Frame extends JFrame {
	/**
	 * The generated serial version identifier.
	 */
	private static final long serialVersionUID = -6830991429593466613L;
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	LoginPanel loginPanel;
	ActionListener loginAction;
	ActionListener logoutAction;

	public static void main(String[] args) {
	    SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	        	new LCARS_Frame();
	        }
	    });		
	}
	

	LMainPanel mainPanel;
	JPanel p1;
	JPanel p2;
	JPanel p3;

	public LCARS_Frame() {
		
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
		this.setTitle("LCARS Main");
		this.setUndecorated(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		/**
		 * Create action listeners for "Login" and Logout".
		 */
		loginAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login();
			}
		};
		loginPanel = new LoginPanel(loginAction);
		loginPanel.setPreferredSize(new Dimension(1920, 1080));
		//loginPanel.setPreferredSize(new Dimension(1680, 985));
		loginPanel.setTitle("Login Title");
		loginPanel.setTitle("SECOND Login Title");
		
		logoutAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logout();
			}
		};
		mainPanel = new LMainPanel(logoutAction);
		
		this.getContentPane().add(loginPanel);
		


		JButton b1 = new JButton("Test Panel 1");
		JButton b2 = new JButton("Test Panel 2");
		JButton b3 = new JButton("Test Panel 3");
		
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(1);
			}
		});
		
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(2);
			}
		});
		
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchPanels(3);
			}
		});
		
		p1 = new TestPanel();
		p2 = new TestPanel();
		p3 = new TestPanel();
		
		
		p1.add(new JLabel("Test Panel 1"));
		p2.add(new JLabel("Test Panel 2"));
		p3.add(new JLabel("Test Panel 3"));
		
		mainPanel.add(b1);
		mainPanel.add(b2);
		mainPanel.add(b3);
		
		//this.getContentPane().add(mainPanel);

		this.validate();
		
		//this.getContentPane().remove(loginPanel);
		//this.validate();
		
		
	}
	
	public void switchPanels(int activePanel) {
		this.mainPanel.setVisible(false);
		
		if(activePanel == 1) {
			
			this.p1.setVisible(true);
			if(!this.getContentPane().isAncestorOf(p1)) {
				LOGGER.info("Adding p1 to the content pane...");
				this.getContentPane().add(p1);
			}
		}
		else if(activePanel == 2) {
			this.p2.setVisible(true);
			this.getContentPane().add(p2);
		}
		else if(activePanel == 3) {
			this.p3.setVisible(true);
			this.getContentPane().add(p3);
		}
	}
	
	
	public void login() {
		this.loginPanel.setVisible(false);
		if(!this.getContentPane().isAncestorOf(this.mainPanel)) {
			LOGGER.info("Adding Main Panel to the content pane...");
			this.getContentPane().add(this.mainPanel);
		}
		else {
			this.mainPanel.setVisible(true);
		}
	}
	
	
	public void logout() {
		this.mainPanel.setVisible(false);
		this.loginPanel.setVisible(true);
	}

}