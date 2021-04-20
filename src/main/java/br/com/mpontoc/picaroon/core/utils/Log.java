package br.com.mpontoc.picaroon.core.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log {

	public static Logger logger;
	private static final Level LOG = Level.forName("LOG", 100);

	public static void log(String msg) {
		logger = LogManager.getLogger(getCallerClassName());
		logger.log(LOG, msg);
		System.out.println(getCallerClassName() + " - " + msg);
	}
	
	public static void log(String[] msg) {
		logger = LogManager.getLogger(getCallerClassName());
		logger.log(LOG, msg);
		System.out.println(getCallerClassName() + " - " + msg);
	}

	public Logger getLogger() {
		return logger;
	}

	public static String getCallerClassName() {
		StackTraceElement[] elements = Thread.currentThread().getStackTrace();
		for (int i = 1; i < elements.length; i++) {
			if (!elements[i].getClassName().equals(Log.class.getName())
					&& !elements[i].getClassName().equals(Functions.class.getName())
					&& elements[i].getClassName().indexOf("java.lang.Thread") != 0) {
				String methodName = elements[i].getMethodName();
				String lineNumber = Integer.toString(elements[i].getLineNumber());
				String fileName = elements[i].getFileName();
				String stringCaller = fileName.replace(".java", "") + "." + methodName + "(" + lineNumber + ")";
				return stringCaller;
			}
		}
		return null;
	}

}