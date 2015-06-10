LCARS-Framework
==========================

A java framework for building LCARS displays as one or more swing application. It extends <code>JComponent</code> to create LCARS components. It currently supports the following components:
* frames
  * LCARS_Frame
* panels
  * LApplicationPanel
  * LClockPanel
  * LMainPanel
  * LoginPanel
* elements
  * LCARSPanel
  * LCARSRectangle
  * LCARSButton
  * LCARSTextPane
  * LCARSLabel
  * LCARSCorner
  * LCARSCalendarPane
  * LCARSTime
  * LCARSAlarm

The test application, <code>com.spagnola.lcars.test.ClockTest.java</code> demonstrates how the components can be used. See the following image for visual of Component types and some of the color palette.

##### LCARS Clock Test Screen

![LCARS Component Test Screen](https://github.com/agent-P/LCARS-Framework/raw/master/docs/LCARSClockTest.png)


Building LCARS-Framework
------------------------

Currently, importing existing source into eclipse is the easiest way to build and test the framework. A maven POM is under development.