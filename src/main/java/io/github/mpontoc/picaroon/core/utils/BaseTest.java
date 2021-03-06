package io.github.mpontoc.picaroon.core.utils;

import org.junit.AfterClass;

import io.github.mpontoc.picaroon.core.drivers.DriverFactory;

public class BaseTest {

	@AfterClass
	public static void endExecution() {
		try {
			DriverFactory.driver.quit();
		} catch (Exception e) {
		}
		Log.log("Report saved on path: " + Functions.getPathReportCompleto());
		Functions.zipReportFiles();
		Log.log("driver killed [ " + PropertiesVariables.BROWSER_OR_MOBILE + " ]");
	}

}
