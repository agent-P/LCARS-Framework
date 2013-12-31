/**
 * 
 */
package com.spagnola.lcars.elements;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.logging.Logger;

import javax.swing.JTextPane;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.LabelView;
import javax.swing.text.ParagraphView;
import javax.swing.text.Position;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

import com.spagnola.lcars.LCARS;

/**
 * @author Perry Spagnola
 *
 */
public class LCARSTextPane extends JTextPane {

	/**
	 * A reference to the global Logger object.
	 */
	protected final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	protected int x;
	protected int y;
	protected int w;
	protected int h;
	
	protected double scaleFactor = 0.0;
	
	protected static final Color transparent = new Color(0f,0f,0f,0f);
	
	protected StyledDocument document;
	protected SimpleAttributeSet attributes;
	

    public LCARSTextPane(int x, int y, int w, int h, int style) {
        super();
        
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        
        setPreferredSize(new Dimension(w, h));
        setBounds(x, y, w, h);
        setFont(LCARS.getSingletonObject().getLCARSFont());
        setBackground(transparent);
        setForeground(LCARS.getLCARSColor(style));
        setEditable(false); // gets rid of the cursor
        setFocusable(false); // prevents selection highlighting
      
        attributes = new SimpleAttributeSet();
        StyleConstants.setFontSize(attributes, (int)LCARS.getLCARSFontSize(style));
        setEditorKit(new ScaledEditorKit());
        document = (StyledDocument)getDocument();
        
    }
    
	@Override
	protected void paintComponent(Graphics g) {

	    double scaleFactor;
	    
	    double sw = (double)getParent().getWidth() / (double)getParent().getPreferredSize().width;
	    double sh = (double)getParent().getHeight() / (double)getParent().getPreferredSize().height;
	    
	    if(sh < sw) {
	    	scaleFactor = sh;
	    }
	    else {
	    	scaleFactor = sw;
	    }
	    
	    if(scaleFactor != this.scaleFactor) {
	    	this.scaleFactor = scaleFactor;
	    	
			/**
			 * Calculate x-axis and y-axis offsets to center the scaled component within the display.
			 */
			int _x = (int) ((getParent().getWidth() - getParent().getPreferredSize().width*scaleFactor) / 2);
			int _y = (int) ((getParent().getHeight() - getParent().getPreferredSize().height*scaleFactor) / 2);

			/**
			 * Set the bounds of the component to set its scaled on-screen position and make it visible.
			 */
			setBounds((int)(x*scaleFactor)+_x, (int)(y*scaleFactor)+_y, (int)(getPreferredSize().width*scaleFactor), (int)(getPreferredSize().height*scaleFactor));

			/**
			 * Set the <code>ZOOM_FACTOR</code> of the pane's styled document to the 
			 * <code>scaleFactor</code> of the component.
			 */
			getDocument().putProperty("ZOOM_FACTOR", scaleFactor);
	    }
		
		super.paintComponent(g);
	}
	
	
	public void repaint(int x, int y, int width, int height) {
		super.repaint(x, y, getWidth(), getHeight());
	}


	public void setText(String text) {
		try {
			document.remove(0, document.getLength());
			document.insertString(0, text, attributes);
		}
		catch(Exception e) {

		}	
	}

	
	public void appendText(String text) {
        try {
        	document.insertString(document.getLength(), text, attributes);
        }
        catch(Exception e) {
        	
        }	
	}

	
	public void prependText(String text) {
        try {
        	document.insertString(0, text, attributes);
        }
        catch(Exception e) {
        	
        }	
	}

	
	public void insertText(String text, int offset) {
        try {
        	document.insertString(offset, text, attributes);
        }
        catch(Exception e) {
        	
        }	
	}

	
	public void removeText(int offset, int length) {
        try {
        	document.remove(offset, length);
        }
        catch(Exception e) {
        	
        }	
	}
	
}

class ScaledEditorKit extends StyledEditorKit {
    public ViewFactory getViewFactory() {
        return new StyledViewFactory();
    }

    class StyledViewFactory implements ViewFactory {

        public View create(Element elem) {
            String kind = elem.getName();
            if (kind != null) {
                if (kind.equals(AbstractDocument.ContentElementName)) {
                    return new LabelView(elem);
                }
                else if (kind.equals(AbstractDocument.ParagraphElementName)) {
                    return new ParagraphView(elem);
                }
                else if (kind.equals(AbstractDocument.SectionElementName)) {
                    return new ScaledView(elem, View.Y_AXIS);
                }
                else if (kind.equals(StyleConstants.ComponentElementName)) {
                    return new ComponentView(elem);
                }
                else if (kind.equals(StyleConstants.IconElementName)) {
                    return new IconView(elem);
                }
            }

            // default to text display
            return new LabelView(elem);
        }
    }
}

class ScaledView extends BoxView {
    public ScaledView(Element elem, int axis) {
        super(elem, axis);
    }

    public double getZoomFactor() {
        Double scale = (Double) getDocument().getProperty("ZOOM_FACTOR");
        if (scale != null) {
            return scale.doubleValue();
        }

        return 1;
    }

    public void paint(Graphics g, Shape allocation) {
        Graphics2D g2d = (Graphics2D) g;
        double zoomFactor = getZoomFactor();
        AffineTransform old = g2d.getTransform();
        g2d.scale(zoomFactor, zoomFactor);
        super.paint(g2d, allocation);
        g2d.setTransform(old);
    }

    public float getMinimumSpan(int axis) {
        float f = super.getMinimumSpan(axis);
        f *= getZoomFactor();
        return f;
    }

    public float getMaximumSpan(int axis) {
        float f = super.getMaximumSpan(axis);
        f *= getZoomFactor();
        return f;
    }

    public float getPreferredSpan(int axis) {
        float f = super.getPreferredSpan(axis);
        f *= getZoomFactor();
        return f;
    }

    protected void layout(int width, int height) {
        super.layout(new Double(width / getZoomFactor()).intValue(),
                     new Double(height *
                                getZoomFactor()).intValue());
    }

    public Shape modelToView(int pos, Shape a, Position.Bias b) throws BadLocationException {
        double zoomFactor = getZoomFactor();
        Rectangle alloc;
        alloc = a.getBounds();
        Shape s = super.modelToView(pos, alloc, b);
        alloc = s.getBounds();
        alloc.x *= zoomFactor;
        alloc.y *= zoomFactor;
        alloc.width *= zoomFactor;
        alloc.height *= zoomFactor;

        return alloc;
    }

    public int viewToModel(float x, float y, Shape a, Position.Bias[] bias) {
        double zoomFactor = getZoomFactor();
        Rectangle alloc = a.getBounds();
        x /= zoomFactor;
        y /= zoomFactor;
        alloc.x /= zoomFactor;
        alloc.y /= zoomFactor;
        alloc.width /= zoomFactor;
        alloc.height /= zoomFactor;

        return super.viewToModel(x, y, alloc, bias);
    }
}
