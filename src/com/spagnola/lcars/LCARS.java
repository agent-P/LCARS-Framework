package com.spagnola.lcars;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Logger;

public class LCARS {
	/**
	 *  ES_XXX - Element styles
	 */
	public static final int ES_SHAPE      = 0x0000000F;   // Mask for ES_SHAPE_XXX styles
	public static final int ES_LABEL      = 0x000000F0;   // Mask for ES_LABEL_XXX styles
	public static final int ES_STYLE      = 0x0000FC00;   // Mask for color style
	public static final int ES_COLOR      = 0x0000FF00;   // Mask for EC_XXX styles
	public static final int ES_FONT       = 0x000F0000;   // Mask for EF_XXX styles
	public static final int ES_BEHAVIOR   = 0x0F000000;   // Mask for EB_XXX styles
	public static final int ES_CLASS      = 0xF0000000;   // Mask for class specific styles
	public static final int ES_SELECTED   = 0x00000100;   // Element selected
	public static final int ES_DISABLED   = 0x00000200;   // Element disabled
	public static final int ES_SELDISED   = 0x00000300;   // Element selected and disabled
	public static final int ES_STATIC     = 0x00100000;   // Element does not accept user input
	public static final int ES_BLINKING   = 0x00200000;   // Element blinks
	public static final int ES_MODAL      = 0x00400000;   // Element is always opaque
	public static final int ES_SILENT     = 0x00800000;   // Element does not play a sound
	public static final int ES_NONE       = 0x00000000;   // Element does not have a style

	/**
	 *  ES_SHAPE_XXX - Element shape orientation
	 */
	public static final int ES_SHAPE_NW   = 0x00000000;   // Shape oriented to the north-west
	public static final int ES_SHAPE_SW   = 0x00000001;   // Shape oriented to the south-west
	public static final int ES_SHAPE_NE   = 0x00000002;   // Shape oriented to the north-east
	public static final int ES_SHAPE_SE   = 0x00000004;   // Shape oriented to the south-east
	public static final int ES_OUTLINE    = 0x00000008;   // Outline

	/**
	 *  ES_LABEL_XXX - Element label position
	 */
	public static final int ES_LABEL_SE   = 0x00000000;   // Label in the south-east - the default for LCARS components
	public static final int ES_LABEL_S    = 0x00000010;   // Label in the south
	public static final int ES_LABEL_SW   = 0x00000020;   // Label in the south-west
	public static final int ES_LABEL_W    = 0x00000030;   // Label in the west
	public static final int ES_LABEL_NW   = 0x00000040;   // Label in the north-west
	public static final int ES_LABEL_N    = 0x00000050;   // Label in the north
	public static final int ES_LABEL_NE   = 0x00000060;   // Label in the north-east
	public static final int ES_LABEL_E    = 0x00000070;   // Label in the east
	public static final int ES_LABEL_C    = 0x00000080;   // Label in the center

	/**
	 *  ES_RECT_XXX - Rectangle/Button element styles
	 */
	public static final int ES_RECT_RND   = 0x30000000;   // Rounded rectangle shape
	public static final int ES_RECT_RND_E = 0x10000000;   // Rounded rectangle shape in the east
	public static final int ES_RECT_RND_W = 0x20000000;   // Rounded rectangle shape in the west

	/**
	 *  EC_XXX - Colors by Name
	 */
	public static final int EC_WHITE      = 0x00000000;   // 
	public static final int EC_L_BLUE     = 0x00000400;   // 
	public static final int EC_M_BLUE     = 0x00000800;   // 
	public static final int EC_BLUE       = 0x00000C00;   // 
	public static final int EC_D_BLUE     = 0x00001000;   // 
	public static final int EC_YELLOW     = 0x00001400;   // 
	public static final int EC_ORANGE     = 0x00001800;
	public static final int EC_RED        = 0x00001C00;
	
	public static final Color COLORS_BG[] = {
		new Color(0xCC, 0xDD, 0xFF),
		new Color(0x55, 0x99, 0xFF),
		new Color(0x33, 0x66, 0xFF),
		new Color(0x00, 0x11, 0xEE),
		new Color(0x00, 0x00, 0x88),
		new Color(0xBB, 0xAA, 0x55),
		new Color(0xBB, 0x44, 0x11),
		new Color(0x88, 0x22, 0x11)
	};

	// EF_XXX - Fonts
	public static final int EF_NORMAL     = 0x00000000;   // The normal LCARS font
	public static final int EF_TITLE      = 0x00010000;   // The title font
	public static final int EF_SUBTITLE   = 0x00020000;   // The subtitle font
	public static final int EF_BUTTON     = 0x00030000;   // The default button text font
	public static final int EF_BODY       = 0x00040000;   // The default body text font 
	public static final int EF_TINY       = 0x00050000;   // A tiny font 

	public static final float FONT_TITLE_SIZE = 60;
	public static final float FONT_SUBTITLE_SIZE = 40;
	public static final float FONT_BUTTON_SIZE = 25;
	public static final float FONT_BODY_SIZE = 20;
	public static final float FONT_TINY_SIZE = 10;

	public static final String FN_SWISS911 = "Swiss911 UCm BT";
	private static final String LCARS_FONT_FILENAME = "/com/spagnola/lcars/resources/swz911uc.ttf";
	private static Font LCARSFont;
	
	
	/**
	 * Time constant for second in miliseconds.
	 */
	public static final int SECOND = 1000;

	/**
	 * Time constant for minute in miliseconds.
	 */
	public static final int MINUTE = 60000;

	/**
	 * Time constans for miliseconds in an hour.
	 */
	public static final int MILISECS_IN_HOUR = 3600000;

	/**
	 * A reference to the global Logger object.
	 */
	protected final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private static LCARS reference;	  

	/**
	 * Cache for the {@link #getHostName()} method.
	 */
	private static String hostName;

	/**
	 * Cache for the {@link #getIPAddress()} method.
	 */
	private static String ipAddress;


	/**
	 * The default constructor for the LCARS class. Instantiates an LCARS object and loads
	 * the LCARS font.
	 */
	private LCARS() {
		/**
		 * Load the LCARS Font.
		 */
		loadLCARSFont();
	}

	
	/**
	 * Get the singleton object for the LCARS class. It creates the object if it does
	 * not already exist.
	 * @return  a reference to the singleton LCARS object
	 */
	public static LCARS getSingletonObject() {
		/**
		 * If the singleton object reference is <code>null</code> it hasn't been 
		 * created, yet. So, create it using the default constructor.
		 */
		if(reference == null) {
			reference = new LCARS();
		}
		
		/**
		 * Return a reference to this singleton object.
		 */
		return reference;
	}

	
	/**
	 * Load the LCARS font from a resource in the classpath.
	 */
	public void loadLCARSFont() {
		try {
			/**
			 * Try to load the LCARS font.
			 */
			URL url = this.getClass().getResource(LCARS_FONT_FILENAME);
			LCARSFont = Font.createFont(Font.TRUETYPE_FONT, new File(url.toURI()));
		} catch (IOException | FontFormatException | URISyntaxException e) {
			/**
			 *  Log IOException, FontFormatException, and URISyntaxException if thrown.
			 */
			LOGGER.severe("Error loading LCARS font: " + e.getMessage());
		}		  
	}

	
	/**
	 * Simple accessor to get the LCARS font.
	 * @return  the LCARS font being used
	 */
	public Font getLCARSFont() {
		return LCARSFont;		
	}
	
	
	public static float getLCARSFontSize(int style) {
		int fontSizeID = style & LCARS.ES_FONT;
		
		switch(fontSizeID) {
		case LCARS.EF_TITLE:
			return LCARS.FONT_TITLE_SIZE;
		case LCARS.EF_SUBTITLE:
			return LCARS.FONT_SUBTITLE_SIZE;
		case LCARS.EF_BUTTON:
			return LCARS.FONT_BUTTON_SIZE;
		case LCARS.EF_BODY:
			return LCARS.FONT_BODY_SIZE;
		default:
			return 0F;
		}
	}
	
	
	/**
	 * Returns the color derived from the LCARS component style passed
	 * in as an argument.
	 * @param componentStyle  the style of an LCARS component
	 * @return  the color derived from the component's style
	 */
	public static Color getLCARSColor(int componentStyle) {
		
		/**
		 * Derive the color array index from the component style.
		 */
		int colorIndex = (componentStyle & LCARS.ES_COLOR) >> 10;
		
		/**
		 * If the index into the colors array is valid, use it to
		 * retrieve the assigned color and return it. Else, return
		 * <code>Color.lightGray</code>.
		 */
		if(colorIndex >= 0 && colorIndex < COLORS_BG.length) {
			return COLORS_BG[colorIndex];
		}
		else {
			return Color.lightGray;
		}
	}
	
	
	public static Color getLCARSTextColor(int componentStyle) {
		/**
		 * Derive the color array index from the component style.
		 */
		int colorIndex = (componentStyle & LCARS.ES_COLOR) >> 10;
		
		if(colorIndex == 3 || colorIndex == 4) {
			return COLORS_BG[0];
		}
		else {
			return Color.black;
		}
	}
	
	
	public static int getShape(int style) {
		return style & LCARS.ES_SHAPE & ~LCARS.ES_OUTLINE;
	}
	
	
	
	public static boolean isStatic(int style) {
		if((style & LCARS.ES_STATIC) == 0) {
	    	return false;
	    }
		else {
			return true;
		}
	}
	
	
	public static int getRectangleShape(int style) {
		return style & ES_RECT_RND;
	}
	
	
	public static String getHostName() {
		if(LCARS.hostName == null) {
			try {
				LCARS.hostName = InetAddress.getLocalHost().getHostName();
			}
			catch (java.net.UnknownHostException e) {
				LCARS.hostName = "localhost";
			}
		}
		
		return LCARS.hostName;
	}
	
	
	public static String getIPAddress() {
		if(LCARS.ipAddress == null) {
			try {
				LCARS.ipAddress = InetAddress.getLocalHost().getHostAddress();
			}
			catch (java.net.UnknownHostException e) {
				LCARS.ipAddress = "127.0.0.1";
			}
		}
		
		return LCARS.ipAddress;
	}
}
