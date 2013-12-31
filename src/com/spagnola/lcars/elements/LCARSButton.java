/**
 * 
 */
package com.spagnola.lcars.elements;

/**
 * @author Perry Spagnola
 *
 */
public class LCARSButton extends LCARSRectangle {
	
	public static final int defaultWidth = 150;
	public static final int defaultHeight = 60;

	public LCARSButton(String text, int x, int y, int style) {
		super(x, y, defaultWidth, defaultHeight, style);
		
		init(text);
	}
	
	/**
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param style
	 */
	public LCARSButton(String text, int x, int y, int w, int h, int style) {
		super(x, y, w, h, style);
		
		init(text);
	}
	
	protected void init(String text) {
		setTextInsets(h/3, 10);

		setText(text);
	}

}
