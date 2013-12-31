package com.spagnola.lcars.panels;

import com.spagnola.lcars.LCARS;
import com.spagnola.lcars.elements.*;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;

import javax.swing.*;

public class LoginPanel extends LCARSPanel {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4009903437795142762L;

	
	/**
	 * 
	 * @param parent
	 * @param loginAction
	 */
	public LoginPanel(ActionListener loginAction) {
		super();
		
		this.setTitle("Login Title");
		
		LCARSCorner header = new LCARSCorner(this,
				10,10,1490,200, 
				75, 400, 
				LCARS.EC_WHITE|LCARS.ES_SHAPE_NW|LCARS.ES_LABEL_N|LCARS.ES_STATIC);
		header.setName("Login Header");
		header.setText(header.getName());
		//header.setActionListener(exitActionListener);
		//this.add(header);
				
		LCARSCorner footerLeft = new LCARSCorner(this,
				10,980,450,100, 
				40, 400, 
				LCARS.EC_WHITE|LCARS.ES_SHAPE_SW|LCARS.ES_LABEL_NW|LCARS.ES_STATIC);
		footerLeft.setName("Login Footer Left");
		footerLeft.setText(footerLeft.getName());
		//this.add(footerLeft);
		

	
		

	}
	
	


}
