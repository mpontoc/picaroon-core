package br.com.mpontoc.picaroon.core.utils;

import org.junit.AfterClass;

import br.com.mpontoc.picaroon.core.commands.ActionsCommands;
import br.com.mpontoc.picaroon.core.driverfactory.DriverFactory;

public class BaseTest {

	@AfterClass
	public static void endExection() {
		try {
			DriverFactory.driver.quit();
		} catch (Exception e) {
		}
		Log.log("Report saved on path: " + Functions.getPathReportCompleto());
		Functions.zipReportFiles();
		Log.log("driver killed [ " + Prop.getProp("browserOrDevice") + " ]");
	}

}
