package org.usfirst.frc.team3389.robot.subsystems;

public class Logger {
	/**
	 * Log message to stderr along with the caller class and method name.
	 * @param msg message to log
	 */
	// TODO - need to log to a file

	public final static int NONE = 0;
	public final static int SEVERE = 1;
	public final static int ERROR = 2;
	public final static int WARNING = 3;
	public final static int INFO = 4;
	public final static int DEBUG = 5;

	private int logLevel = NONE;
	private boolean logConsole = false; 
	
	public Logger() {
		logLevel = NONE;
		logConsole = false;
	}
	public Logger(final int level) {
		logLevel = level;
		logConsole = false;
	}
	public Logger(final int level, boolean console) {
		logLevel = level;
		logConsole = console;
	}
	
	public final void setLevel (int level) {
		if (level < NONE)
			level = NONE;
		if (level > DEBUG)
			level = DEBUG;
		logLevel = level;
	}
	
	public final void disableConsole() {
		logConsole = false;
	}
	
	public final void enableConsole() {
		logConsole = true;
	}
	
	public final void log(final int level, Object src, String msg) {
		if (level < logLevel)
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
		if (logConsole)
			System.err.println(fullMessage);
	  }
}
