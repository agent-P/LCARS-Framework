package com.spagnola.lcars.elements;

import java.awt.Container;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;

import com.spagnola.lcars.LCARS;

public class LCARSLabel extends LCARSComponent {
	
	
	/**
	 * The generated serial version identifier.
	 */
	private static final long serialVersionUID = 1775120001615158577L;

	/**
	 * The constructor of the <code>LCARSLabel</code> class.
	 * @param text
	 * @param x
	 * @param y
	 * @param style
	 */
	public LCARSLabel(String text, int x, int y, int style) {
		/**
		 * Call the super class constructor. Initializing the object with a zero
		 * width and height, and make sure the LCARS style contains
		 * <code>LCARS.ES_STATIC</code>.
		 */
		super(x, y, 0, 0, style | LCARS.ES_STATIC);
		
		/**
		 * Derive the font size from the <code>style</code> argument, and set
		 * the <code>fontSize</code> class variable.
		 */
		fontSize = LCARS.getLCARSFontSize(style);
		
		/**
		 * Set the <code>componentText</code> class variable to the <code>text</code> 
		 * argument.
		 */
		componentText = text;
		
		/**
		 * Get the width and height of the text string based the font metrics of the
		 * LCARS font.
		 */
	    FontMetrics fm = getFontMetrics(LCARSFont.deriveFont(fontSize));
	    w = fm.stringWidth(componentText);
	    h = fm.getHeight();
	    
	    /**
	     * Create the shape of the label component.
	     */
	    createLabelShape();
	    
	    /**
	     * Initialize the bounds of the label object to location (0, 0) and the width
	     * and height derived for the component's text string.
	     */
	    setBounds(0, 0, w, h);
		
	    /**
	     * Set the text insets to static final defaults.
	     */
		setTextInsets(0, -10);
		
		/**
		 * Set the component's foreground to the color derived from the LCARS style.
		 */
		setForeground(LCARS.getLCARSColor(style));
		setBackground(transparent);
	}
	
	
	/**
	 * Create a rectangular shape based on the width and height of the label
	 * component. The shape is used to create the object's <code>area</code> variable.
	 * @return  the <code>GeneralPath</code> object created for the parent object's <code>area</code>
	 */
	protected GeneralPath createLabelShape() {
		/**
		 * Create a new GeneralPath object.
		 */
		GeneralPath componentShape = new GeneralPath();
		
		/**
		 * Build a simple rectangle shape using the <code>GeneralPath</code> 
		 * based on the dimension arguments.
		 */
		componentShape.moveTo(0, h);
		componentShape.lineTo(0, 0);
		componentShape.lineTo(w, 0);
		componentShape.lineTo(w, h);
		componentShape.lineTo(0, h);

		/**
		 * Create a new Area object using the rectangular <code>GeneralPath</code>, 
		 * and assign it to the object's <code>area</code> variable.
		 */
		area = new Area(componentShape);
		areaChanged = true;
		
		/**
		 * Return the <code>GeneralPath</code> object just in case someone wants to 
		 * use it.
		 */
		return componentShape;
	}

	
	/**
	 * Override the super class' <code>setText()</code> method to create/recreate the
	 * component shape based on the <code>text</code> argument being specified.
	 */
	@Override
	public void setText(String text) {
		/**
		 * Set the <code>componentText</code> variable of the super class.
		 */
		super.setText(text);
		
		/**
		 * Get the width and height of the text string based the font metrics of the
		 * LCARS font.
		 */
	    FontMetrics fm = getFontMetrics(LCARSFont.deriveFont(fontSize));
	    w = fm.stringWidth(componentText);
	    h = fm.getHeight();
	    	    
	    /**
	     * Create the shape of the label component.
	     */
	    createLabelShape();
	}
	
}
