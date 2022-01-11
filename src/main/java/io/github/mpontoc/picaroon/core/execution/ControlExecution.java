package io.github.mpontoc.picaroon.core.execution;

import org.junit.AfterClass;

import io.github.mpontoc.picaroon.core.drivers.DriverFactory;
import io.github.mpontoc.picaroon.core.utils.Functions;
import io.github.mpontoc.picaroon.core.utils.Log;
import io.github.mpontoc.picaroon.core.utils.PropertiesVariables;

public class ControlExecution {

	@AfterClass
	public static void tearDown() {
		try {
			DriverFactory.driver.quit();
		} catch (Exception e) {
		}
		Log.log("Report saved on path: " + Functions.getPathReportCompleto());
		Functions.zipReportFiles();
		Log.log("driver killed [ " + PropertiesVariables.BROWSER_OR_MOBILE + " ]");
	}

}
