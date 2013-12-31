package com.spagnola.lcars.test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.spagnola.lcars.LCARS;
import com.spagnola.lcars.elements.LCARSButton;
import com.spagnola.lcars.elements.LCARSCalendarPane;
import com.spagnola.lcars.elements.LCARSCorner;
import com.spagnola.lcars.elements.LCARSLabel;
import com.spagnola.lcars.elements.LCARSRectangle;
import com.spagnola.lcars.elements.LCARSTextPane;
import com.spagnola.lcars.elements.LCARSTime;
import com.spagnola.lcars.logging.LCARSLogger;

public class ComponentTest {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	

	public static void main(String[] args) {
		try {
			LCARSLogger.setup();
		} 
		catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Problems with creating the log files");
		}
		
	
        JFrame f = new JFrame("LCARS Component Test");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(1920, 1080));
		panel.setLayout(null);
		panel.setBackground(Color.black);
		
		LCARSTime lt = new LCARSTime(500, 700, LCARSTime.TIME, 100,
				LCARS.EF_TITLE|LCARS.EC_ORANGE|LCARS.ES_LABEL_C);
		panel.add(lt);
		
		lt.setMode(LCARSTime.DEFAULT);
		
        LCARSCorner lc = new LCARSCorner(10, 10, 1490,  
				LCARS.EC_ORANGE|LCARS.ES_SHAPE_NW|LCARS.ES_LABEL_NE|LCARS.ES_STATIC);
        lc.setText("Login Panel Header");
        lc.setName("Login Panel Header");
        
        
		panel.add(lc);
		
		lc = new LCARSCorner(10, 200, 1490, 
				LCARS.EC_BLUE|LCARS.ES_SHAPE_SW|LCARS.ES_LABEL_NE|LCARS.ES_STATIC);
        lc.setText("Login Panel 2");
        lc.setName("Login Panel 2");
		panel.add(lc);
		
		lc = new LCARSCorner(10, 400, 1490,  
				LCARS.EC_D_BLUE|LCARS.ES_SHAPE_NE|LCARS.ES_LABEL_NE|LCARS.ES_STATIC);
        lc.setText("Login Panel 3");
        lc.setName("Login Panel 3");
		panel.add(lc);
		
		lc = new LCARSCorner(10, 600, 1490,  
				LCARS.EC_L_BLUE|LCARS.ES_SHAPE_SE|LCARS.ES_LABEL_NE|LCARS.ES_STATIC);
        lc.setText("Login Panel 4");
        lc.setName("Login Panel 4");
		panel.add(lc);
		
		
		
		LCARSRectangle lr = new LCARSRectangle(200, 50, 200, 100, LCARS.ES_RECT_RND|LCARS.ES_STATIC);
		panel.add(lr);
		
		lr = new LCARSRectangle(500, 50, 200, 100, LCARS.ES_RECT_RND_E|LCARS.ES_STATIC);
		panel.add(lr);
		
		lr = new LCARSRectangle(800, 50, 200, 100, LCARS.ES_RECT_RND_W|LCARS.ES_STATIC);
		panel.add(lr);
		
		lr = new LCARSRectangle(1100, 50, 200, 100, LCARS.ES_STATIC);
		panel.add(lr);
		
		LCARSButton lb = new LCARSButton("Test Button", 200, 450, 
				LCARS.EF_BUTTON|LCARS.ES_RECT_RND|LCARS.EC_ORANGE);
		lb.setName("Test Button");
		panel.add(lb);
		
		lb = new LCARSButton("Test Button 2", 500, 450, 
				LCARS.EF_BUTTON|LCARS.ES_RECT_RND_W|LCARS.EC_ORANGE);
		lb.setName("Test Button 2");
		panel.add(lb);
		
		lb = new LCARSButton("Test Button 3", 800, 450, 
				LCARS.EF_BUTTON|LCARS.ES_RECT_RND_E|LCARS.EC_ORANGE);
		lb.setName("Test Button 3");
		panel.add(lb);
		
		lb = new LCARSButton("Test Button 4", 1100, 450, 
				LCARS.EF_BUTTON|LCARS.EC_ORANGE);
		lb.setName("Test Button 4");
		panel.add(lb);
		
//		LCARSLabel titleLabel = new LCARSLabel("LCARS Label",
//				1510, 10, 
//				LCARS.EC_ORANGE|LCARS.ES_LABEL_NW|LCARS.EF_TITLE);
//		titleLabel.setName("LCARS Label");		
//		panel.add(titleLabel);
//		
//		titleLabel.setText("LCARS Long Label");
		
//		LCARSTextPane ltp = new LCARSTextPane(100, 550, 200, 100, LCARS.EC_BLUE | LCARS.EF_BODY);
//		ltp.setText("Line 2\n");
//		ltp.appendText("Line 3\n");
//		ltp.prependText("Line 1\n");
//
//		panel.add(ltp);
		
		
		LCARSCalendarPane lcp = new LCARSCalendarPane(20, 700, LCARS.EF_BODY);
		panel.add(lcp);
		
//		lcp = new LCARSCalendarPane(850, 700, 11, 2013, LCARS.EF_BODY);
//		panel.add(lcp);
		
		f.getContentPane().add(panel);
        
        
        f.setSize(new Dimension(1200,800));
        f.setVisible(true);

	}

}
