package br.com.mpontoc.picaroon.core.conf;

import br.com.mpontoc.picaroon.core.commands.ActionsCommands;
import br.com.mpontoc.picaroon.core.drivers.DriverFactory;
import br.com.mpontoc.picaroon.core.drivers.MobileDriverInit;
import br.com.mpontoc.picaroon.core.utils.Functions;
import br.com.mpontoc.picaroon.core.utils.Prop;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;

public class Hooks {

	private boolean isPrintedInfo = false;

	@Before
	public void setAppMobile(Scenario scenario) {

		ActionsCommands.setScenario(scenario);

		if (Prop.getProp("browserOrDevice").toLowerCase().equals("mobile")) {

			if (MobileDriverInit.driverMobile != null && Functions.getAppRunner() == true) {
				MobileDriverInit.driverMobile.resetApp();
			}

			if (Functions.getAppRunner() == true) {
				DriverFactory.newApp();
			}
		}

	}

	@BeforeStep
	public void reportBefore(Scenario scenario) {

		ActionsCommands.setScenario(scenario);
		ActionsCommands.isFirstRun = true;

		if (Prop.getProp("browserOrDevice").toLowerCase().contains("mobile")) {
			if (isPrintedInfo == false && Functions.getAppRunner() == true) {
				Functions.printInfoExec();
				isPrintedInfo = true;
			}
		} else if (isPrintedInfo == false) {
			Functions.printInfoExec();
			isPrintedInfo = true;
		}
	}

	@AfterStep
	public void reportAfter(Scenario scenario) {
		ActionsCommands.printScreenAfterStep(scenario);
	}

	@After
	public static void printTimeExecution() {
		if (Prop.getProp("printAfterSteps").equals("false")) {
			ActionsCommands.printScreen();
		}
		Functions.printTimeExecution();
		Functions.setHoraFinalTotal(Functions.retornaData().substring(11));
		ActionsCommands.cucumberWriteReport("Total time execution until 'moment/final exection' "
				+ Functions.substractHours(Functions.getHoraInicialTotal(), Functions.getHoraFinalTotal()));
	}

}
