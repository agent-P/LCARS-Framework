package com.spagnola.lcars.elements;

import java.awt.Container;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;

import com.spagnola.lcars.LCARS;

public class LCARSCorner extends LCARSComponent {
	
	/**
	 * The generated serial version identifier.
	 */
	private static final long serialVersionUID = -3033525311661860404L;
	
	private static final int defaultHeight = 90;
	private static final int defaultBarV = 150;
	private static final int defaultBarH = 30;
	
	protected int barH;
	protected int barV;
	protected int barThin;
	protected double roundingFactor;
	

	/**
	 * The default constructor for the LCARSCorner class. Uses default values for a number
	 * of parameters to create a shape very close to LCARS canon. Only location and width
	 * are variable. There are canonical values for height and the thickness of the vertical
	 * and horizontal bars.
	 * 
	 * @param parent
	 * @param x
	 * @param y
	 * @param w
	 * @param style
	 */
	public LCARSCorner(int x, int y, int w, int style) {
		/**
		 * First call the <code>LCARSComponent</code> constructor with the 
		 * <code>LCARSCorner.defaultHeight</code> parameter.
		 */
		super(x, y, w, defaultHeight, style);
		
		/**
		 * Set the <code>roundingFactor</code> to 2.0 for the outer and inner rounded corners.
		 * This will give the corner a close to LCARS canonical shape.
		 */
	    roundingFactor = 2.0;
		
	    /**
	     * Initialize the component with the LCARS canonical vertical and horizontal bar
	     * thicknesses, and the component's style.
	     */
		init(defaultBarH, defaultBarV, style);
	}

	/**
	 * Constructor for the LCARSCorner class. Gives complete control over frame corner
	 * parameters. Note that Outer and inner rounded corners are not LCARS canon.
	 * 
	 * @param x  the x-axis dimension of the upper left corner of the visible component
	 * @param y  the y-axis dimension of the upper left corner of the visible component
	 * @param w  the width of the visible component
	 * @param h  the height of the visible component
	 * @param barH  the thickness of the horizontal bar
	 * @param barV  the thickness of the vertical bar
	 * @param arcO  the angle of the arc of the rounded outer corner
	 * @param arcI  the angle of the arc of the rounded inner corner
	 * @param style the LCARS style of the component, includes the location of the rounded corner and any label, the color...
	 */
	public LCARSCorner(Container parent, int x, int y, int w, int h, int barH, int barV, int style) {
		/**
		 * First call the <code>LCARSComponent</code> constructor.
		 */
		super(x, y, w, h, style);
				
		/**
		 * Set the <code>roundingFactor</code> to 1.0 for the outer and inner rounded corners.
		 * This will allow the corner to have variable vertical and horizontal bar thicknesses,
		 * and still maintain consistent relationships between inner and outer corners of the
		 * shape.
		 */
	    roundingFactor = 1.0;
	    
	    /**
	     * Initialize the component with the vertical and horizontal bar
	     * thickness arguments, and the component's style.
	     */
	    init(barH, barV, style);
	}
	
	
	protected void init(int barH, int barV, int style) {
	    
	    this.barH = barH;
	    this.barV = barV;    
	    
        if(barH < barV) {
        	barThin = (int)(barH*roundingFactor);
        }
        else {
        	barThin = (int)(barV*roundingFactor);
        }
        
	    /**
	     * Mask the LCARS style with <code>ES_SHAPE</code> to extract the corner shape.
	     */
	    int shape = LCARS.getShape(style);
	    
	    /**
	     * Create the component shape.
	     */
	    switch(shape) {
	    case LCARS.ES_SHAPE_NE: 
	    	createNEShape();
	    	break;
	    case LCARS.ES_SHAPE_NW: 
	    	createNWShape(); 
	    	break;
	    case LCARS.ES_SHAPE_SE: 
	    	createSEShape();
	    	break;
	    case LCARS.ES_SHAPE_SW: 
	    	createSWShape();
	    	break;
	    default:
	    	/**
	    	 * Log an error if there was no shape specified or an invalid shape specified.
	    	 */
	    	LOGGER.severe("No LCARS corner shape selected.");
	    }
		
	}
	
	
	/**
	 * Create the shape for a North West or upper left LCARS corner. Uses a 
	 * GeneralPath object to construct the shape. Then creates an Area object
	 * from the GeneralPath, assigns it to the <code>area</code> class variable,
	 * and returns the GeneralPath object.
	 * 
	 * @return  the GeneralPath object that defines the shape of the LCARS corner
	 */
	protected GeneralPath createNWShape() {
		
		/**
		 * Create a new GeneralPath object.
		 */
		GeneralPath componentShape = new GeneralPath();
		
		/**
		 * Build the component shape using the GeneralPath object. It is made up of
		 * six lines and an inner and outer curve.
		 */
		componentShape.moveTo(0, h);
		componentShape.lineTo(0, barThin);
		componentShape.curveTo(0, barThin, 0, 0, barThin, 0);
		componentShape.lineTo(w, 0);
		componentShape.lineTo(w, barH);
		componentShape.lineTo(barV + barThin/2, barH);
		componentShape.curveTo(barV + barThin/2, barH, barV, barH, barV, barH + barThin/2);
		componentShape.lineTo(barV, h);
		componentShape.lineTo(0, h);

		/**
		 * Create a new Area object using the GeneralPath, and assign it to the object's
		 * <code>area</code> variable.
		 */
		area = new Area(componentShape);

		/**
		 * Return the GeneralPath in case someone wants to use it.
		 */
		return componentShape;
	}
	
	protected GeneralPath createNEShape() {
		
		GeneralPath componentShape = new GeneralPath();
		
		componentShape.moveTo(0, 0);
		componentShape.lineTo(w - barThin, 0);
		componentShape.curveTo(w - barThin, 0, w, 0, w, barThin);
		componentShape.lineTo(w, h);
		componentShape.lineTo(w - barV, h);		
		componentShape.lineTo(w - barV, barH + barThin/2);
		componentShape.curveTo(w - barV, barH + barThin/2, w - barV, barH, w - barV - barThin/2, barH);
		componentShape.lineTo(0, barH);
		componentShape.lineTo(0, 0);
		componentShape.closePath();

		area = new Area(componentShape);

		return componentShape;
	}
	
	protected GeneralPath createSWShape() {
		
		GeneralPath componentShape = new GeneralPath();
		
		componentShape.moveTo(0, 0);
		componentShape.lineTo(barV, 0);
		componentShape.lineTo(barV, h - barH - barThin/2);
		componentShape.curveTo(barV, h - barH - barThin/2, barV, h - barH, barV + barThin/2, h - barH);
		componentShape.lineTo(w, h - barH);
		componentShape.lineTo(w, h);		
		componentShape.lineTo(barThin, h);
		componentShape.curveTo(barThin, h, 0, h, 0, h - barThin);
		componentShape.closePath();
		
		area = new Area(componentShape);

		return componentShape;
	}
	
	protected GeneralPath createSEShape() {
		
		GeneralPath componentShape = new GeneralPath();
		
		componentShape.moveTo(0, h - barH);
		componentShape.lineTo(w - barV - barThin/2, h - barH);
		componentShape.curveTo(w - barV - barThin/2, h - barH, w - barV, h - barH, w - barV, h - barH - barThin/2);
		componentShape.lineTo(w - barV, 0);
		componentShape.lineTo(w, 0);
		componentShape.lineTo(w, h - barThin);		
		componentShape.curveTo(w, h - barThin, w, h, w - barThin, h);
		componentShape.lineTo(0, h);
		componentShape.closePath();
		
		area = new Area(componentShape);

		return componentShape;
	}
	
	
	@Override
	protected void setTextPosition(Graphics2D g2d) {
	    /**
	     * Get the font metrics and the bounding rectangle for the component's text
	     * for use calculating the text position.
	     */
		FontMetrics fm = g2d.getFontMetrics();
		fm.stringWidth(componentText);		
		Rectangle2D r = fm.getStringBounds(componentText, g2d);
				
	    /**
	     * Mask the LCARS style with <code>ES_SHAPE</code> to extract the corner shape.
	     */
	    int shape = LCARS.getShape(style);
	    
		switch(shape) {
		case LCARS.ES_SHAPE_NE: 
		case LCARS.ES_SHAPE_NW: 
			textY = (int)(scaledArea.getBounds().height - textInsetY*scaleFactor);
			break;
		case LCARS.ES_SHAPE_SE: 
		case LCARS.ES_SHAPE_SW: 
			textY = (int)(scaledArea.getBounds().y + r.getHeight());
			break;
		default:
			break;

		}
		
		switch(shape) {
		case LCARS.ES_SHAPE_NE: 
		case LCARS.ES_SHAPE_SE: 
			textX = (int)(scaledArea.getBounds().width - barV*scaleFactor + textInsetX*scaleFactor);
			break;
		case LCARS.ES_SHAPE_NW: 
		case LCARS.ES_SHAPE_SW: 
			textX = (int)(barV*scaleFactor - r.getWidth() - textInsetX*scaleFactor);
			break;
		default:
			break;

		}
	}
}
