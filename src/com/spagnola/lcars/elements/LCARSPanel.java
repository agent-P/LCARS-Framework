package com.spagnola.lcars.elements;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.spagnola.lcars.LCARS;

public abstract class LCARSPanel extends JPanel {

	/**
	 * The generated serial version identifier.
	 */
	private static final long serialVersionUID = -9217248917750616751L;
	
	/**
	 * A reference to the global Logger object.
	 */
	protected final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public static final int PREFERRED_WIDTH = 1920;
	public static final int PREFERRED_HEIGHT = 1080;
	
	protected String title = "";
	protected String subtitle = "";
	
	protected LCARSLabel titleLabel;
	
	JButton exitButton;
	
	protected ActionListener exitActionListener;
	
	public LCARSPanel() {
		super();

		/**
		 * Set the default background color to <code>Color.black</code>.
		 */
		this.setBackground(Color.black);
		
		/**
		 * Set the panel's layout to null to facilitate absolute
		 * placement of LCARS components.
		 */
		setLayout(null);
				
		
		exitActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		};

		exitButton = new JButton("EXIT");
		exitButton.addActionListener(exitActionListener);
		this.add(exitButton);

	}
	

	
	protected void exit() {
		LOGGER.info("exit action invoked...");
		this.setVisible(false);
		getParent().setVisible(true);
	}
	
	

	public String getTitle() {
		return title;
	}
	

	public void setTitle(String title) {
		/**
		 * Assign argument to the class variable.
		 */
		this.title = title;
		
		/**
		 * Remove the label from the panel if it already exists.
		 */
		if(titleLabel != null) {
			this.remove(titleLabel);
		}
		
		/**
		 * Get the width of the text string based the font metrics of the
		 * LCARS font.
		 */
	    FontMetrics fm = getFontMetrics(LCARS.getSingletonObject().getLCARSFont().deriveFont(LCARS.getLCARSFontSize(LCARS.EF_TITLE)));
		
	    /**
	     * Create the LCARS label object and add it to the panel. Note that
	     * its location is set to the upper right of the panel.
	     */
		titleLabel = new LCARSLabel(title,
				PREFERRED_WIDTH - fm.stringWidth(title) - 100, 10, 
				LCARS.EC_ORANGE|LCARS.ES_LABEL_NE|LCARS.EF_TITLE);
		this.add(titleLabel);	
	}


	public String getSubtitle() {
		return subtitle;
	}



	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}


}
