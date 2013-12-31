package com.spagnola.lcars.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TimeZone;

import com.spagnola.lcars.LCARS;
import com.spagnola.lcars.elements.LCARSButton;
import com.spagnola.lcars.elements.LCARSCalendarPane;
import com.spagnola.lcars.elements.LCARSCorner;
import com.spagnola.lcars.elements.LCARSLabel;
import com.spagnola.lcars.elements.LCARSPanel;
import com.spagnola.lcars.elements.LCARSRectangle;
import com.spagnola.lcars.elements.LCARSTime;

public class LClockPanel extends LCARSPanel {
	
	protected LCARSTime time;
	protected LCARSTime time_with_seconds;
	protected LCARSTime am_pm;
	protected LCARSTime date;
	protected LCARSTime day_of_week;
	protected LCARSTime seconds;
	protected LCARSTime time_zone;
	
	protected int mode = 0;

	protected LCARSCalendarPane calendar;
	
	public LClockPanel() {
		super();
		
		/**
		 * Clock Panel statistics
		 */
		LCARSRectangle titleRect = new LCARSRectangle(10, 10, 150, 60, LCARS.EC_ORANGE|LCARS.ES_STATIC);
		add(titleRect);
		
		LCARSLabel label = new LCARSLabel("HOST NAME :", 1500, 100,  LCARS.EC_ORANGE|LCARS.ES_LABEL_C|LCARS.EF_BUTTON);
		add(label);
		
		label = new LCARSLabel(LCARS.getHostName(), 1700, 100,  LCARS.EC_ORANGE|LCARS.ES_LABEL_C|LCARS.EF_BUTTON);
		add(label);

		label = new LCARSLabel("IP ADDRESS :", 1500, 130,  LCARS.EC_ORANGE|LCARS.ES_LABEL_C|LCARS.EF_BUTTON);
		add(label);
		
		label = new LCARSLabel(LCARS.getIPAddress(), 1700, 130,  LCARS.EC_ORANGE|LCARS.ES_LABEL_C|LCARS.EF_BUTTON);
		add(label);

		/**
		 * Calendar pane and controls
		 */
		calendar = new LCARSCalendarPane(200, 70, LCARS.EF_BODY);
		add(calendar);
		
		LCARSButton lb = new LCARSButton("Next", 10, 75, LCARS.EF_BUTTON|LCARS.EC_L_BLUE);
		lb.setName("Next Button");
		lb.setActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calendar.incrementMonth();
			}});
		add(lb);

		lb = new LCARSButton("", 10, 140, LCARS.EF_BUTTON|LCARS.EC_L_BLUE);
		lb.setName("This Month Button");
		lb.setActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calendar.thisMonth();
			}});
		add(lb);

		lb = new LCARSButton("Previous", 10, 205, LCARS.EF_BUTTON|LCARS.EC_L_BLUE);
		lb.setName("Previous Button");
		lb.setActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				calendar.decrementMonth();
			}});
		add(lb);

		/**
		 * Frame components
		 */
		LCARSCorner calendarFrame = new LCARSCorner(10, 270, 650,
				LCARS.EC_L_BLUE|LCARS.ES_SHAPE_SW|LCARS.ES_STATIC);
		calendarFrame.setName("calendar");
		add(calendarFrame);
		
		LCARSCorner clockFrame = new LCARSCorner(10, 372, 650,
				LCARS.EC_ORANGE|LCARS.ES_SHAPE_NW|LCARS.ES_STATIC);
		clockFrame.setName("clock");
		add(clockFrame);
		
		LCARSRectangle rect = new LCARSRectangle(665, 326, 30, 40, LCARS.EC_ORANGE|LCARS.ES_STATIC);
		add(rect);

		rect = new LCARSRectangle(700, 345, 775, 20, LCARS.EC_YELLOW|LCARS.ES_STATIC);
		add(rect);

		rect = new LCARSRectangle(700, 372, 775, 20, LCARS.EC_YELLOW|LCARS.ES_STATIC);
		add(rect);

		rect = new LCARSRectangle(1480, 335, 430, 30, LCARS.EC_ORANGE|LCARS.ES_STATIC);
		add(rect);

		rect = new LCARSRectangle(1480, 372, 430, 30, LCARS.EC_M_BLUE|LCARS.ES_STATIC);
		add(rect);

		rect = new LCARSRectangle(665, 372, 30, 40, LCARS.EC_ORANGE|LCARS.ES_STATIC);
		add(rect);

		/**
		 * Time fields including: basic time, AM/PM designation, seconds, day, date, 
		 * time zone, 24 hour basic time, and 24 hour time with seconds.
		 */
		time_with_seconds = new LCARSTime(725, 450, LCARSTime.DEFAULT, 500, LCARS.EC_YELLOW|LCARS.ES_LABEL_NW);
		add(time_with_seconds);
		time_with_seconds.setVisible(false);
		
		time = new LCARSTime(725, 450, LCARSTime.TIME, 500, LCARS.EC_YELLOW|LCARS.ES_LABEL_NW);
		add(time);
		
		seconds = new LCARSTime(1600, 450, LCARSTime.SECONDS, 240, LCARS.EC_M_BLUE|LCARS.ES_LABEL_NW);
		add(seconds);
		
		am_pm = new LCARSTime(1600, 685, LCARSTime.AM_PM, 240, LCARS.EC_M_BLUE|LCARS.ES_LABEL_NW);
		add(am_pm);
		
		day_of_week = new LCARSTime(825, 50, LCARSTime.DAY_OF_WEEK, 175, LCARS.EC_YELLOW|LCARS.ES_LABEL_NW);
		add(day_of_week);
		
		date = new LCARSTime(825, 250, LCARSTime.DATE, 75, LCARS.EC_YELLOW|LCARS.ES_LABEL_NW);
		add(date);
		
		time_zone = new LCARSTime(825, 1000, LCARSTime.TIME_ZONE, 75, LCARS.EC_YELLOW|LCARS.ES_LABEL_NW);
		add(time_zone);
		
		/**
		 * Time Zone controls
		 */
		lb = new LCARSButton("Previous", 1480, 1000, 
				LCARS.ES_LABEL_C|LCARS.ES_RECT_RND_W|LCARS.EF_BUTTON|LCARS.EC_M_BLUE);
		lb.setName("Previous Time Zone");
		lb.setActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				decrementTimeZone();
			}});
		add(lb);
		
		lb = new LCARSButton("TIME ZONE", 1635, 1000, 100, LCARSButton.defaultHeight, 
				LCARS.ES_LABEL_C|LCARS.EF_BUTTON|LCARS.EC_M_BLUE);
		lb.setName("This Time Zone");
		lb.setActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setLocalTimeZone();
			}});
		add(lb);
		
		lb = new LCARSButton("Next", 1740, 1000, 
				LCARS.ES_LABEL_C|LCARS.ES_RECT_RND_E|LCARS.EF_BUTTON|LCARS.EC_M_BLUE);
		lb.setName("Next Time Zone");
		lb.setActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				incrementTimeZone();
			}});
		add(lb);
		
		
		
	}
	
	
	public void incrementTimeZone() {
		TimeZone _tz = LCARSTime.getTimeZone();

		int rawOffset = _tz.getRawOffset();

		String[] tzid = TimeZone.getAvailableIDs(rawOffset+LCARS.MILISECS_IN_HOUR);

		if(tzid[0] != null) {
			LCARSTime.setTimeZone(tzid[0]);
			calendar.setTimeZone(tzid[0]);
		}
	}

	
	public void decrementTimeZone() {
		TimeZone _tz = LCARSTime.getTimeZone();

		int rawOffset = _tz.getRawOffset();

		String[] tzid = TimeZone.getAvailableIDs(rawOffset-LCARS.MILISECS_IN_HOUR);

		if(tzid[0] != null) {
			LCARSTime.setTimeZone(tzid[0]);
			calendar.setTimeZone(tzid[0]);
		}
	}

	
	public void setLocalTimeZone() {
		LCARSTime.setTimeZone();
		calendar.setTimeZone();
	}
	
	
	public void setMode(int mode) {
		
	}
}
