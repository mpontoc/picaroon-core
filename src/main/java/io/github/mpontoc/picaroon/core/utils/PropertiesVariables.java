package io.github.mpontoc.picaroon.core.utils;

import io.github.mpontoc.picaroon.core.mobile.Mobile;

public class PropertiesVariables {
	
	public static String MOBILE_PLATFORM = Mobile.getPlataforma().toLowerCase();
	public static String BROWSER_OR_MOBILE = Prop.getProp("browserOrMobile").toLowerCase();
	public static String COLOR_BACKGROUND = Prop.getProp("colorBackground").toLowerCase();
	public static String PRINT_AFTER_STEPS = Prop.getProp("printAfterSteps").toLowerCase();
	public static String RESET_APP = Prop.getProp("resetApp").toLowerCase();
	public static String BACKUP_REPORTS = Prop.getProp("backupReports").toLowerCase();
	public static String KILL_BROWSER = Prop.getProp("killBrowser").toLowerCase();
	public static String BASE_URL_SELENIUM_GRID = Prop.getProp("baseURLSeleniumGrid").toLowerCase();
	public static String BASE_URL_APPIUM = Prop.getProp("baseURLAppium").toLowerCase();
	public static String ANDROID = "android";
	public static String IOS = "ios";
	

}
