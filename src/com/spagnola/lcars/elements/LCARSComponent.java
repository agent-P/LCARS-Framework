package com.spagnola.lcars.elements;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.event.MouseInputListener;

import com.spagnola.lcars.LCARS;

/**
 * 
 * @author Perry Spagnola
 *
 */
public abstract class LCARSComponent extends JComponent implements MouseInputListener, ComponentListener {

	/**
	 * The generated serial version identifier.
	 */
	private static final long serialVersionUID = 5796010256072072526L;

	/**
	 * A reference to the global Logger object.
	 */
	protected final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	protected String componentText = "";
	
	protected int x;
	protected int y;
	protected int w;
	protected int h;
	
	protected int textInsetX = 10;
	protected int textInsetY = 10;
	
	protected int textX;
	protected int textY;
	
	protected double scaleFactor = 0.0;
	
	protected Area area;
	protected Area scaledArea;
	protected int style;
	protected boolean areaChanged;
	
	protected Color normalColor;
	protected Color pressedColor;
	protected Color hoverColor;
	protected static final Color transparent = new Color(0f,0f,0f,0f);
	
	protected Font LCARSFont;
	protected Font scaledLCARSFont;
	protected float fontSize;
	
	protected AffineTransform renderingTransform;
	
	protected ActionListener actionListener;
	
	public LCARSComponent(int x, int y, int w, int h, int style) {
		
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;

	    this.style = style;
	    
	    normalColor = LCARS.getLCARSColor(style);
	    pressedColor = normalColor.darker();
	    hoverColor = normalColor.brighter();
	    	    
		setBackground(normalColor);
		setForeground(LCARS.getLCARSTextColor(style));

	    /**
	     * Get the LCARS font from the LCARS singleton object.
	     */
	    LCARSFont = LCARS.getSingletonObject().getLCARSFont();
	    
	    setFontSize();
	    
	    setBounds(0,0,w,h);
	    
	    textInsetX = 10;
	    textInsetY = 10;
	    
	    /**
	     * Only add a mouse listener if the <code>style</code> argument does not
	     * contain <code>LCARS.ES_STATIC</code>.
	     */
	    if(!isStatic()) {
	    	addMouseListener(this);
	    	addMouseMotionListener(this);
	    }
	    
	    
	    //addComponentListener(this);
	}

	
	@Override
	protected void paintComponent(Graphics g) {

		/**
		 * First, paint the background if the component is opaque. Required when 
		 * JComponent is extended, and the paintCompnent() method is overridden.
		 */
		if(isOpaque()) {
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		
		/**
		 * Create a Graphics2D object so we can use Java 2D features.
		 */
		Graphics2D g2d = (Graphics2D) g.create();
		
		/**
		 * Set the font, the anti aliasing rendering hint, and the color to draw 
		 * the component shape with.
		 */
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(getBackground());
		g2d.setFont(scaledLCARSFont);
		
		/**
		 * Scale the LCARS component, both shape and text.
		 */
		scaleComponent(g2d);		

		/**
		 * Draw the scaled Area object of the LCARS component, and fill it.
		 */
		g2d.draw(scaledArea);
		g2d.fill(scaledArea);
		
		/**
		 * Set the color to draw the font, and draw the component text.
		 */
		g2d.setColor(getForeground());
		g2d.drawString(getText(), textX, textY);
						
		/**
		 * Clean up when the method has completed by disposing the Graphics2D object that was created.
		 */
		g2d.dispose();
		
	}
	
	
	protected void scaleComponent(Graphics2D g2d) {
	    double sw = (double)getParent().getWidth() / (double)getParent().getPreferredSize().width;
	    double sh = (double)getParent().getHeight() / (double)getParent().getPreferredSize().height;
	    
	    double scaleFactor;
	    
	    if(sh < sw) {
	    	scaleFactor = sh;
	    }
	    else {
	    	scaleFactor = sw;
	    }
	    
	    if(scaleFactor != this.scaleFactor || areaChanged) {
	    	this.scaleFactor = scaleFactor;
	    	areaChanged = false;
	    	
	    	scaledArea = new Area(area);
			renderingTransform = new AffineTransform();
			renderingTransform.scale(scaleFactor, scaleFactor);
			scaledArea.transform(renderingTransform);	    	
			
			scaledLCARSFont = LCARSFont.deriveFont(fontSize*(float)scaleFactor);

			/**
			 * Calculate x-axis and y-axis offsets to center the scaled component within the display.
			 */
			int _x = (int) ((getParent().getWidth() - getParent().getPreferredSize().width*scaleFactor) / 2);
			int _y = (int) ((getParent().getHeight() - getParent().getPreferredSize().height*scaleFactor) / 2);

			/**
			 * Set the bounds of the component to set its on-screen position and make it visible.
			 */
			setBounds((int)(x*scaleFactor)+_x, (int)(y*scaleFactor)+_y, scaledArea.getBounds().width, scaledArea.getBounds().height);
	    }
	    
		/**
		 * Set the text position of the scaled component.
		 */
		setTextPosition(g2d);
	}
	
	
	protected void setTextPosition(Graphics2D g2d) {
		
		/**
		 * Extract the component's text position from the style using the 
		 * <code>LCARS.ES_LABEL</code> mask.
		 */
	    int textPosition = style & LCARS.ES_LABEL;
	    
	    /**
	     * Get the font metrics and the bounding rectangle for the component's text
	     * for use calculating the text position.
	     */
		FontMetrics fm = g2d.getFontMetrics();
		fm.stringWidth(componentText);
		Rectangle2D r = fm.getStringBounds(componentText, g2d);
				

	    /**
	     * Conditionally calculate and set the <code>y</code> position of the component text.  
	     * The switch statement groups together the north, south and horizontal centers.
	     */
	    switch(textPosition) {
	    case LCARS.ES_LABEL_NW: 
	    case LCARS.ES_LABEL_N: 
	    case LCARS.ES_LABEL_NE: 
	    	textY = (int)(fm.getAscent() + textInsetY*scaleFactor);
	    	break;
	    case LCARS.ES_LABEL_SW: 
	    case LCARS.ES_LABEL_S: 
	    case LCARS.ES_LABEL_SE: 
	    	textY = (int)(scaledArea.getBounds().height - textInsetY*scaleFactor);
	    	break;
	    case LCARS.ES_LABEL_W: 
	    case LCARS.ES_LABEL_C: 
	    case LCARS.ES_LABEL_E: 
	    	textY = (int)(scaledArea.getBounds().height/2 + fm.getAscent()/2);
	    	break;
	    default:
	    	/**
	    	 * Log an error for the <code>y</code> position, if there was no text position specified 
	    	 * or an invalid position specified.
	    	 */
	    	LOGGER.info("No LCARS text position selected, y position not set.");
	    }

	    /**
	     * Conditionally calculate and set the <code>x</code> position of the component text.  
	     * The switch statement groups together the east, west and vertical centers.
	     */
	    switch(textPosition) {
	    case LCARS.ES_LABEL_NW: 
	    case LCARS.ES_LABEL_W: 
	    case LCARS.ES_LABEL_SW: 
	    	textX = (int)(textInsetX*scaleFactor);
	    	break;
	    case LCARS.ES_LABEL_NE: 
	    case LCARS.ES_LABEL_E: 
	    case LCARS.ES_LABEL_SE: 
	    	textX = (int)(scaledArea.getBounds().width - r.getWidth() - textInsetX*scaleFactor);
	    	break;
	    case LCARS.ES_LABEL_N: 
	    case LCARS.ES_LABEL_C: 
	    case LCARS.ES_LABEL_S: 
	    	textX = (int)(scaledArea.getBounds().width/2 - r.getWidth()/2);
	    	break;
	    default:
	    	/**
	    	 * Log an error for the <code>x</code> position, if there was no text position specified 
	    	 * or an invalid position specified.
	    	 */
	    	LOGGER.info("No LCARS text position selected, x position not set.");
	    }
	}
	
	/**
	 * Simple accessor method to set the component's displayed text. Set to <code>""</code>
	 * as a default for blank text
	 * @param text  the text to be displayed on the component, cannot be null
	 */
	public void setText(String text) {
		componentText = text;
	}
	
	
	/**
	 * Simple accessor method to get the component's displayable text.
	 * @return  the displayed text of the component
	 */
	public String getText() {
		return componentText;
	}
	
	
	/**
	 * Simple accessor method to set the insets for the component's displayed text.
	 * @param x  the x-axis offset inside of the component shape's bounds
	 * @param y  the y-axis offset inside of the component shape's bounds
	 */
	public void setTextInsets(int x, int y) {
		this.textInsetX = x;
		this.textInsetY = y;
	}
	
	
	/**
	 * Simple accessor method to get the x-axis inset for the component's displayed text.
	 * @return  the x-axis offset inside of the component shape's bounds
	 */
	public int getTextInsetX() {
		return textInsetX;
	}
	
	
	/**
	 * Simple accessor method to get the y-axis inset for the component's displayed text.
	 * @return  the y-axis offset inside of the component shape's bounds
	 */
	public int getTextInsetY() {
		return textInsetY;
	}
	
	
	protected void setFontSize() {
		fontSize = LCARS.getLCARSFontSize(style);
		if(fontSize == 0F) {
			fontSize = LCARS.getLCARSFontSize(LCARS.EF_BUTTON);
		}
	}


	public void setActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
	}
	
	
	public boolean isStatic() {
		return LCARS.isStatic(style);
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		//Point pt = e.getPoint();
		//LOGGER.info("mouseClicked: " + this.getName() + " - " + pt.getX() + ", " + pt.getY());
		
		if(scaledArea.contains(e.getPoint())) {
			
			if(actionListener != null) {
				actionListener.actionPerformed(new ActionEvent(e.getSource(), e.getID(), e.paramString()));
			}
			
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(scaledArea.contains(e.getPoint())) {
			//Point pt = e.getPoint();
			//LOGGER.info("mousePressed: " + this.getName() + " - " + pt.getX() + ", " + pt.getY());
			
			setBackground(pressedColor);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(scaledArea.contains(e.getPoint())) {
			//Point pt = e.getPoint();
			//LOGGER.info("mouseReleased: " + this.getName() + " - " + pt.getX() + ", " + pt.getY());
			
			setBackground(hoverColor);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		if(scaledArea.contains(e.getPoint())) {
			//Point pt = e.getPoint();
			//LOGGER.info("mouseEntered: " + this.getName() + " - " + pt.getX() + ", " + pt.getY());
			
			setBackground(hoverColor);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		if(!scaledArea.contains(e.getPoint())) {
			//Point pt = e.getPoint();
			//LOGGER.info("mouseExited: " + this.getName() + " - " + pt.getX() + ", " + pt.getY());
			
			setBackground(normalColor);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("mouseDragged()...");
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		if(scaledArea.contains(e.getPoint())) {
			//Point pt = e.getPoint();
			//LOGGER.info("mouseMoved: " + this.getName() + " - " + pt.getX() + ", " + pt.getY());
			
			setBackground(hoverColor);
		}
		else {
			setBackground(normalColor);
		}
	}
	
	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		//LOGGER.info("componentResized: " + getName());
		//getParent().paintComponents(getParent().getGraphics());
	}


	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		//LOGGER.info("componentMoved: " + getName());
	}


	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		//LOGGER.info("componentShown");
		
	}


	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		//LOGGER.info("componentHidden");
		
	}

}