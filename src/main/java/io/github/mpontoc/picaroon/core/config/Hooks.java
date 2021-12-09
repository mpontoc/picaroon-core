package io.github.mpontoc.picaroon.core.config;

import io.github.mpontoc.picaroon.core.commands.ActionsCommands;
import io.github.mpontoc.picaroon.core.drivers.DriverFactory;
import io.github.mpontoc.picaroon.core.utils.Functions;
import io.github.mpontoc.picaroon.core.utils.Prop;
import io.github.mpontoc.picaroon.core.utils.Report;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;

public class Hooks {

	@Before
	public void setAppMobile(Scenario scenario) {

		ActionsCommands.setScenario(scenario);
		if (Prop.getProp("browserOrMobile").toLowerCase().contains("mobile")) {

			if (Functions.getAppRunner() == true) {
				DriverFactory.newApp();
			}
		}
	}

	@BeforeStep
	public void reportBeforeStep(Scenario scenario) {

		ActionsCommands.setScenario(scenario);
		ActionsCommands.isFirstRun = true;

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

	@After
	public static void printTimeExecution() {

		if (Prop.getProp("printAfterSteps").equals("false")) {
			Report.printScreen();
		}
		Functions.printTimeExecution();
		Functions.setHoraFinalTotal(Functions.retornaData().substring(11));
		Report.cucumberWriteReport("Total time execution until 'moment/final execution' "
				+ Functions.substractHours(Functions.getHoraInicialTotal(), Functions.getHoraFinalTotal()));
		ActionsCommands.setPrintedInfo(false);
	}

}
