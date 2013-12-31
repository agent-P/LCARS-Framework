/**
 * 
 */
package com.spagnola.lcars.elements;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import com.spagnola.lcars.LCARS;

/**
 * @author Perry Spagnola
 *
 */
public class LCARSCalendarPane extends LCARSTextPane {
	
	protected static final Color COLOR_WEEKDAY = LCARS.getLCARSColor(LCARS.EC_L_BLUE);
	protected static final Color COLOR_SATURDAY = LCARS.getLCARSColor(LCARS.EC_M_BLUE);
	protected static final Color COLOR_SUNDAY = LCARS.getLCARSColor(LCARS.EC_RED);
	protected static final Color COLOR_TODAY = LCARS.getLCARSColor(LCARS.EC_YELLOW);
	
	protected static final int[] DAYS = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	protected static final String[] MONTHS = {
		"",                               /** MONTHS[1] = "January" */
		"January", 
		"February", 
		"March",
		"April", 
		"May", 
		"June",
		"July", 
		"August", 
		"September",
		"October", 
		"November", 
		"December"
	};
	
	protected static final String[] HEADER_TAB_SPACE = {
		"",
		"\t\t\t\t\t",
		"\t\t\t\t\t",
		"\t\t\t\t\t",
		"\t\t\t\t\t",
		"\t\t\t\t\t",
		"\t\t\t\t\t",
		"\t\t\t\t\t",
		"\t\t\t\t\t",
		"\t\t\t\t",
		"\t\t\t\t\t",
		"\t\t\t\t",
		"\t\t\t\t"
	};
	
	protected static final String HEADER_WHITE_SPACE = "           ";
	
	protected static final int PANE_WIDTH = 475;
	protected static final int PANE_HEIGHT = 225;
	
	protected SimpleAttributeSet title;
	protected SimpleAttributeSet weekday;
	protected SimpleAttributeSet saturday;
	protected SimpleAttributeSet sunday;
	protected SimpleAttributeSet today;
	
	protected int currentMonth;
	protected int currentDay;
	protected int currentYear;
	
	protected int displayMonth;
	protected int displayYear;
	
	protected int daysInMonth;

	protected int dayToday = 0;

	protected TimeZone timezone = Calendar.getInstance().getTimeZone();;


	public LCARSCalendarPane(int x, int y, int style) {
		super(x, y, PANE_WIDTH, PANE_HEIGHT, style);
		
		/**
		 * Set the current date, the displayed month. The displayed month is the
		 * current month.
		 */
		setCurrentDate();
		setDisplayMonth(currentMonth, currentYear);

		/**
		 * Initialize the calendar pane object.
		 */
		init();
		
	}
	
	
	public LCARSCalendarPane(int x, int y, int month, int year, int style) {
		super(x, y, PANE_WIDTH, PANE_HEIGHT, style);
		
		/**
		 * Set the current date, the displayed month. The displayed month is
		 * specified by the month and year arguments.
		 */
		setCurrentDate();
		setDisplayMonth(month, year);

		/**
		 * Initialize the calendar pane object.
		 */
		init();
		
	}
	
	
	protected void init() {
		
		title = new SimpleAttributeSet();
		weekday = new SimpleAttributeSet();
		sunday = new SimpleAttributeSet();
		saturday = new SimpleAttributeSet();
		today = new SimpleAttributeSet();
		
		/**
		 * Set the font sizes.
		 */
		StyleConstants.setFontSize(title, (int)(LCARS.getLCARSFontSize(LCARS.EF_BUTTON)*1.25));
		StyleConstants.setFontSize(weekday, (int)LCARS.getLCARSFontSize(LCARS.EF_BUTTON));
		StyleConstants.setFontSize(saturday, (int)LCARS.getLCARSFontSize(LCARS.EF_BUTTON));
		StyleConstants.setFontSize(sunday, (int)LCARS.getLCARSFontSize(LCARS.EF_BUTTON));
		StyleConstants.setFontSize(today, (int)LCARS.getLCARSFontSize(LCARS.EF_BUTTON));
		
		/**
		 * Set the filed colors.
		 */
		StyleConstants.setForeground(title, COLOR_WEEKDAY);
		StyleConstants.setForeground(weekday, COLOR_WEEKDAY);
		StyleConstants.setForeground(saturday, COLOR_SATURDAY);
		StyleConstants.setForeground(sunday, COLOR_SUNDAY);
		StyleConstants.setForeground(today, COLOR_TODAY);

		
		/**
		 * Set the number of days in the currently displayed month.
		 */
		setDaysInMonth();
		
		/**
		 * Create the calendar document for the specified or current month. This is determined
		 * by the constructor.
		 */
		createCalendarDoc();
		
		
		(new Timer()).schedule(new TimerTask() {
			@Override
			public void run() {
				updateCalendarPane();
			}
		}, LCARS.MINUTE, LCARS.MINUTE);

	}
	
	
	protected void createCalendarDoc() {
		try {
			/**
			 * Clear the document. Required for date roll overs.
			 */
			document.remove(0, document.getLength());
			
			/**
			 * Insert the calendar header of month and year.
			 */
			document.insertString(0, MONTHS[displayMonth] + HEADER_TAB_SPACE[displayMonth] + HEADER_WHITE_SPACE + displayYear + "\n", title);
			
			SimpleAttributeSet attrs = weekday;
			
			/**
			 * Get the starting day of week for the month.
			 */
			int startDay = dayOfWeek(displayMonth, 1, displayYear);
			
			/**
			 * Insert any blank days into the first line of the calendar pane.
			 */
	        for(int i = 0; i < startDay; i++) {
	        	document.insertString(document.getLength(), "\t", attrs);
	        }
	        
	        /**
	         * Iterate through the days in the month. 
	         */
	        for(int i = 1; i <= daysInMonth; i++) {
	        	/**
	        	 * Set the text style attributes of the day numbers base on:
	        	 * weekday, Sunday, Saturday, and today.
	        	 */
	        	if(isSaturday(i)) {
	        		attrs = saturday;
	        	}
	        	else if(isSunday(i)) {
	        		attrs = sunday;
	        	}
	        	else {
	        		attrs = weekday;
	        	}	        	
	        	if(isToday(i)) {
	        		attrs = today;
	        	}
	        	
	        	/**
	        	 * Provide a single space of left padding for the single digit
	        	 * dates.
	        	 */
	        	if(i<10) {
	        		document.insertString(document.getLength(), " ", attrs);
	        	}
	        	
	        	/**
	        	 * Insert the appropriate date number.
	        	 */
	        	document.insertString(document.getLength(), String.format("%2d", i), attrs);
	        	
	        	/**
	        	 * Insert a line break at the end of week and end of month. Else,
	        	 * insert a tab between the numbers.
	        	 */
	            if(((i + startDay) % 7 == 0) || (i == daysInMonth)) {
		        	document.insertString(document.getLength(), "\n", attrs);
	            }
	            else {
	            	document.insertString(document.getLength(), "\t", attrs);
	            }
	        }
		}
		catch(Exception e) {
			/**
			 * Log any exceptions that are thrown during the document creation process.
			 */
			LOGGER.severe(e.getMessage());
		}
	}
	

	/**
	 *  Returns the day of the week according to the Gregorian calendar, given 
	 *  the <code>month</code>, <code>day</code>, and <code>year</code>.
	 *  January through December equal 1 - 12, and Sunday through Saturday equal
	 *  0 - 6.
	 * @param month  the month of the date
	 * @param day  the day of the date
	 * @param year  the year of the date
	 * @return  the day of the week according to the Gregorian calendar
	 */
	public static int dayOfWeek(int month, int day, int year) {
		int y = year - (14 - month) / 12;
		int x = y + y/4 - y/100 + y/400;
		int m = month + 12 * ((14 - month) / 12) - 2;
		int d = (day + x + (31*m)/12) % 7;
		return d;
	}


    /**
     *  Returns true if the <code>currentYear</code> variable is a leap year.
     * @return  true if the given year is a leap year, false, if not
     */
    public boolean isLeapYear() {
    	/**
    	 * If the current year is evenly divisible by 4 and not by 100, return true.
    	 */
        if((displayYear % 4 == 0) && (displayYear % 100 != 0)) {
        	return true;
        }
        
        /**
         * If the current year is evenly divisible by 400, return true.
         */
        if(displayYear % 400 == 0) {
        	return true;
        }
        
        /**
         * If none of the leap year conditions is met, method falls through,
         * and returns false.
         */
        return false;
    }
    
    
    public boolean isWeekday(int day) {
    	int _day = dayOfWeek(displayMonth, day, displayYear);
    	
    	if(_day>0 && _day<6) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    
    public boolean isSunday(int day) {
    	int _day = dayOfWeek(displayMonth, day, displayYear);
    	
    	if(_day==0) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    
    public boolean isSaturday(int day) {
    	int _day = dayOfWeek(displayMonth, day, displayYear);
    	
    	if(_day==6) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    
    public boolean isToday(int day) {
    	if(displayYear == currentYear && displayMonth == currentMonth && day == currentDay) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    
	public void setTimeZone() {
		timezone = Calendar.getInstance().getTimeZone();
		updateCalendarPane();
	}
	
	
	public void setTimeZone(String timezoneID) {
		timezone = TimeZone.getTimeZone(timezoneID);
		updateCalendarPane();
	}
    
    
    protected boolean dateHasChanged() {
    	if(getCurrentDay() != currentDay || getCurrentMonth() != currentMonth) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    
    protected void updateCalendarPane() {
		if(dateHasChanged()) {					
    		setCurrentDate();
    		createCalendarDoc();
    		setDisplayMonth(currentMonth, currentYear);
    		getParent().repaint();
		}
    }
    
    
    protected void setDaysInMonth() {
    	if (displayMonth == 2 && isLeapYear()) {
    		daysInMonth = 29;
    	}
    	else {
    		daysInMonth = DAYS[displayMonth];
    	}
    }


    protected void setCurrentDate() {    	
    	currentDay = getCurrentDay();
    	currentMonth = getCurrentMonth();
    	currentYear = getCurrentYear();
    }
    
    
    protected int getCurrentDay() {
    	SimpleDateFormat dayformat = new SimpleDateFormat("d");
    	dayformat.setTimeZone(timezone);
    	//LOGGER.info(dayformat.format(Calendar.getInstance().getTime()));
    	
    	return Integer.parseInt(dayformat.format(Calendar.getInstance().getTime()));
    }
    
    
    protected int getCurrentMonth() {
       	SimpleDateFormat monthformat = new SimpleDateFormat("M");
    	monthformat.setTimeZone(timezone);
    	//LOGGER.info(monthformat.format(Calendar.getInstance().getTime()));
       	
    	return Integer.parseInt(monthformat.format(Calendar.getInstance().getTime()));
    }
    
    
    protected int getCurrentYear() {
    	SimpleDateFormat yearformat = new SimpleDateFormat("yyyy");
    	yearformat.setTimeZone(timezone);
    	//LOGGER.info(yearformat.format(Calendar.getInstance().getTime()));
       	
    	return Integer.parseInt(yearformat.format(Calendar.getInstance().getTime()));
    }
    
    
    protected void setDisplayMonth(int month, int year) {
    	displayMonth = month;
    	setDaysInMonth();
    	displayYear = year;
    }
    
    
    public void incrementMonth() {
    	int month = displayMonth;
    	int year = displayYear;
    	
    	if(month == 12) {
    		month = 1;
    		year++;
    	}
    	else {   		
    		month++;
    	}
    	
    	setDisplayMonth(month, year);
    	createCalendarDoc();
    	getParent().repaint();
    }
    
    
    public void decrementMonth() {
    	int month = displayMonth;
    	int year = displayYear;
    	
    	if(month == 1) {
    		month = 12;
    		year--;
    	}
    	else {   		
    		month--;
    	}
    	
    	setDisplayMonth(month, year);
    	createCalendarDoc();
    	getParent().repaint();
    }
    
    
    public void thisMonth() {
    	
    	setDisplayMonth(currentMonth, currentYear);
    	createCalendarDoc();
    	getParent().repaint();
    }
}
