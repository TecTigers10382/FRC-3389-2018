/*
* Copyright (c) 2017-2018 FRC TEAM 3389. All Rights Reserved.
* Open Source Software - may be modified and shared by FRC teams. The code
* must be accompanied by the FIRST BSD license file in the root directory of
* the project.
*/

package org.usfirst.frc.team3389.robot.subsystems;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * This class provides logging methods useful for tracking code execution, state, trace, and debugging.
 * The primary method of interest is Logger.log(...).
 * Only messages with a log level greater or equal to the current log level will be logged.
 * Messages are logged to stderr and optionally to a USB drive if available in the lower USB port.
 * Log message will include the sourced object class and method name.
 */

public class Logger {

	public final static int NONE = 0;
	public final static int SEVERE = 1;
	public final static int ERROR = 2;
	public final static int WARNING = 3;
	public final static int INFO = 4;
	public final static int DEBUG = 5;

	private int logLevel = NONE;
	private PrintWriter oLogFile = null;
	
	/**
	 * This Logger constructor is the default with not parameters.
	 * It will initialize the logger with a level of NONE to prevent any output.
	 * There is an additional constructor which sets the initial logging level.
	 */
	public Logger() {
		logLevel = NONE;
		initUSBLogFile();
	}
	/**
	 * This Logger constructor initializes logging with an initial logging level parameter
	 * There is an additional constructor which defaults the initial logging level to NONE.
	 * 
	 * @param level one of the Logger level values of NONE, SEVERE, ERROR, WARNING, INFO, or DEBUG
	 */
	public Logger(final int level) {
		logLevel = level;
		initUSBLogFile();
	}
	protected void finalize() {
		if (oLogFile != null)
			return;
		oLogFile.close();
		oLogFile = null;
	}

	private final void initUSBLogFile() {
		if (oLogFile != null)
			return;
		File fp = new File("/U");
		if (fp.exists()) {
			fp = new File("/U/robot.log");
			// if file already exists will do nothing
			try { fp.createNewFile(); } catch (IOException e1) { /* ignore */ } 
			try { 
				oLogFile = new PrintWriter("/U/robot.log"); 
			} catch (FileNotFoundException e) {
				log(Logger.WARNING, this, e.getMessage());
			} 
		}
	}
	
	/**
	 * This Logger method allows the logging level to be set/changed at any time.
	 * 
	 * @param level one of the Logger level values of NONE, SEVERE, ERROR, WARNING, INFO, or DEBUG
	 */
	public final void setLevel (int level) {
		if (level < NONE)
			level = NONE;
		if (level > DEBUG)
			level = DEBUG;
		logLevel = level;
	}
	
	/**
	 * This Logger method is used to log messages of a given severity level.
	 * Only messages with a designated level greater than or equal to the current logging level will be logged.
	 * 
	 * @param level  one of the Logger level values of NONE, SEVERE, ERROR, WARNING, INFO, or DEBUG
	 * @param src    the object responsible for the log message, typically use 'this' to indicate the current object 
	 * @param msg    a String representing the message to log
	 */
	public final void log(final int level, Object src, String msg) {
		if (level > logLevel)
			return;
		String fullMessage;
		try {
			fullMessage = src.getClass().getCanonicalName() + "."
					+ Thread.currentThread().getStackTrace()[2].getMethodName()
					+ " " + msg;
		} catch(Exception e) {
			fullMessage = src.getClass().getCanonicalName() + " "
					+ msg;
		}
		if (oLogFile != null)
			oLogFile.println(fullMessage);
			System.err.println(fullMessage);
	  }
}
