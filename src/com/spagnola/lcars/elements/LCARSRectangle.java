package com.spagnola.lcars.elements;

import java.awt.geom.Area;
import java.awt.geom.GeneralPath;

import com.spagnola.lcars.LCARS;

public class LCARSRectangle extends LCARSComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8251726686557724440L;

	public LCARSRectangle(int x, int y, int w, int h, int style) {
		super(x, y, w, h, style);
		// TODO Auto-generated constructor stub
		
		createRectangleShape();
	}

	protected GeneralPath createRectangleShape() {
		/**
		 * Create a new GeneralPath object.
		 */
		GeneralPath componentShape = new GeneralPath();
		
		/**
		 * Build a simple rectangle shape using the <code>GeneralPath</code> 
		 * based on the dimension arguments and the style.
		 */
		switch(LCARS.getRectangleShape(style)) {
		case LCARS.ES_RECT_RND:
			componentShape.moveTo(h/2, 0);
			componentShape.lineTo(w - h/2, 0);
			componentShape.curveTo(w - h/2, 0, w, 0, w, h/2);
			componentShape.curveTo(w, h/2, w, h, w - h/2, h);
			componentShape.lineTo(h/2, h);
			componentShape.curveTo(h/2, h, 0, h, 0, h/2);
			componentShape.curveTo(0, h/2, 0, 0, h/2, 0);
			break;
		case LCARS.ES_RECT_RND_E:
			componentShape.moveTo(0, 0);
			componentShape.lineTo(w - h/2, 0);
			componentShape.curveTo(w - h/2, 0, w, 0, w, h/2);
			componentShape.curveTo(w, h/2, w, h, w - h/2, h);
			componentShape.lineTo(0, h);
			componentShape.lineTo(0, 0);
			break;
		case LCARS.ES_RECT_RND_W:
			componentShape.moveTo(h/2, 0);
			componentShape.lineTo(w, 0);
			componentShape.lineTo(w, h);
			componentShape.lineTo(h/2, h);
			componentShape.curveTo(h/2, h, 0, h, 0, h/2);
			componentShape.curveTo(0, h/2, 0, 0, h/2, 0);
			break;
		default:
			componentShape.moveTo(0, h);
			componentShape.lineTo(0, 0);
			componentShape.lineTo(w, 0);
			componentShape.lineTo(w, h);
			componentShape.lineTo(0, h);
			break;				
		}
		
		

		/**
		 * Create a new Area object using the rectangular <code>GeneralPath</code>, 
		 * and assign it to the object's <code>area</code> variable.
		 */
		area = new Area(componentShape);
		
		return componentShape;
	}

	
}
