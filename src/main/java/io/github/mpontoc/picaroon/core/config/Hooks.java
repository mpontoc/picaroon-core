package io.github.mpontoc.picaroon.core.config;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import io.github.mpontoc.picaroon.core.commands.ActionsCommands;
import io.github.mpontoc.picaroon.core.drivers.DriverFactory;
import io.github.mpontoc.picaroon.core.execution.Execution;
import io.github.mpontoc.picaroon.core.execution.report.Report;
import io.github.mpontoc.picaroon.core.utils.Functions;
import io.github.mpontoc.picaroon.core.utils.Log;
import io.github.mpontoc.picaroon.core.utils.PropertiesVariables;

public class Hooks {

	@Before
	public void setAppMobile(Scenario scenario) {

		ActionsCommands.setScenario(scenario);
		if (PropertiesVariables.BROWSER_OR_MOBILE.contains("mobile")) {

			if (Execution.getAppRunner() == true) {
				DriverFactory.newApp();
			}
		}
	}

	@BeforeStep
	public void reportBeforeStep(Scenario scenario) {

		ActionsCommands.setScenario(scenario);
		Execution.setIsFirstRun(true);

		if (ActionsCommands.getPrintedInfo() == false) {
			Functions.printInfoExec();
		}
	}

	@AfterStep
	public void reportAfterStep(Scenario scenario) {

		if (DriverFactory.driver != null) {
			Report.printScreenAfterStep(scenario);
		}
	}

	@After(order = 1)
	public static void printTimeExecution() {

		if (PropertiesVariables.PRINT_AFTER_STEPS.equals("false")) {
			Report.printScreen();
		}
		Functions.printTimeExecution();
		Execution.setHoraFinalTotal(Functions.retornaData().substring(11));
		Report.cucumberWriteReport("Total execution time until 'moment/final'"
				+ Functions.substractHours(Execution.getHoraInicialTotal(), Execution.getHoraFinalTotal()));
		ActionsCommands.setPrintedInfo(false);
	}
	
	@After(order = 0)
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
