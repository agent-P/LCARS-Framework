/**
 * 
 */
package com.spagnola.lcars.elements;

import java.awt.FontMetrics;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import com.spagnola.lcars.LCARS;

/**
 * @author spagnola
 *
 */
public class LCARSAlarm extends LCARSLabel {
	
	public static final int MODE_24_HOUR = 0;
	public static final int MODE_12_HOUR = 1;
	
	protected static final String[] AM_PM = { "AM", "PM" };
	
	protected static TimeZone timezone;
	
	protected int mode;
	
	protected int alarmHour;
	protected int alarmMinute;
	
	protected static final int maxHour = 23;
	
	public LCARSAlarm(int x, int y, int style) {
		super("00:00", x, y, style);		
		
		init();
	}
	
	
	public LCARSAlarm(int x, int y, int size, int style) {
		super("", x, y, style);
		
		fontSize = size;
		
		init();
	}


	protected void init() {
		mode = MODE_12_HOUR;
		updateDisplay();
		timezone = Calendar.getInstance().getTimeZone();
		
	    FontMetrics fm = getFontMetrics(LCARSFont.deriveFont(fontSize));
	    w = fm.stringWidth(componentText);
	    h = fm.getHeight();
	    createLabelShape();
	    
	    setBounds(0, 0, w, h);
		
		setTextInsets(0, -25);
		
		(new Timer()).schedule(new TimerTask() {
			@Override
			public void run() {
				//setText(format.format(Calendar.getInstance().getTime()));
				//repaint();
			}
		}, LCARS.SECOND, LCARS.SECOND);		
	}
	
	
	
	public void on() {
		
	}
	
	
	public void off() {
		
	}
	
	
	public void snooze() {
		
	}
	
	
	public void updateDisplay() {
		String.format("%2d:%02d", alarmHour, alarmMinute);
		
		if(mode == MODE_24_HOUR) {
			setText(String.format("%02d:%02d", alarmHour, alarmMinute));
		}
		else {
			int displayHour = alarmHour;
			if(displayHour > 12) {
				displayHour = alarmHour - 12;
			}
			setText(String.format("%2d:%02d %s", displayHour, alarmMinute, AM_PM[alarmHour/12]));
		}
		
		repaint();
	}
	
	
	public void incrementAlarmHour() {
		if(alarmHour == maxHour) {
			alarmHour = 0;
		}
		else {
			alarmHour = alarmHour + 1;
		}
		
		updateDisplay();
	}

	
	public void decrementAlarmHour() {
		if(alarmHour == 0) {
			alarmHour = maxHour;
		}
		else {
			alarmHour = alarmHour - 1;
		}
		
		updateDisplay();
	}

	
	public void incrementAlarmMinute() {
		if(alarmMinute == 59) {
			alarmMinute = 0;
		}
		else {
			alarmMinute = alarmMinute + 1;
		}
		
		updateDisplay();
	}
	
	
	public void decrementAlarmMinute() {
		if(alarmMinute == 0) {
			alarmMinute = 59;
		}
		else {
			alarmMinute = alarmMinute - 1;
		}
		
		updateDisplay();
	}
	
	
	public int getMode() {
		return mode;
	}
	
	
	public void setMode(int mode) {
		if(mode == MODE_24_HOUR || mode == MODE_12_HOUR) {
			this.mode = mode;
		}
		else {
			this.mode = MODE_12_HOUR;
			LOGGER.warning("Attempt to set invalid time mode value: " + mode);
		}
	}
	
	
	public void toggleMode() {
		if(mode == MODE_24_HOUR) {
			mode = MODE_12_HOUR;
		}
		else if(mode == MODE_12_HOUR) {
			mode = MODE_24_HOUR;
		}
		else {
			LOGGER.warning("invalid time mode value: " + mode + " - reset to MODE_12_HOUR");
			mode = MODE_12_HOUR;
		}
		
		updateDisplay();
	}
	
	
	public static TimeZone getTimeZone() {
		return timezone;
	}
	
	
	public static void setTimeZone() {
		timezone = Calendar.getInstance().getTimeZone();
	}
	
	
	public static void setTimeZone(String timezoneID) {
		timezone = TimeZone.getTimeZone(timezoneID);
	}
	
}
