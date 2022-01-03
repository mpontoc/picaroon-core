package io.github.mpontoc.picaroon.core.constants;

import io.github.mpontoc.picaroon.core.mobile.Mobile;
import io.github.mpontoc.picaroon.core.utils.Prop;

public class PropertiesConstants {
	
	public static final String PLATFORM = Mobile.getPlataforma();
	public static final String BROWSER_OR_MOBILE = Prop.getProp("browserOrMobile").toLowerCase();
	public static final String COLOR_BACKGROUND = Prop.getProp("colorBackground").toLowerCase();
	public static final String PRINT_AFTER_STEPS = Prop.getProp("printAfterSteps").toLowerCase();
	public static final String RESET_APP = Prop.getProp("resetApp").toLowerCase();
	public static final String BACKUP_REPORTS = Prop.getProp("backupReports").toLowerCase();
	public static final String KILL_BROWSER = Prop.getProp("killBrowser").toLowerCase();
	public static final String BASE_URL_SELENIUM_GRID = Prop.getProp("baseURLSeleniumGrid").toLowerCase();
	public static final String BASE_URL_APPIUM = Prop.getProp("baseURLAppium").toLowerCase();
	

}
