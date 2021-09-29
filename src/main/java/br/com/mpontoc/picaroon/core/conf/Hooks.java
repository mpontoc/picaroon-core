package br.com.mpontoc.picaroon.core.conf;

import br.com.mpontoc.picaroon.core.commands.ActionsCommands;
import br.com.mpontoc.picaroon.core.drivers.DriverFactory;
import br.com.mpontoc.picaroon.core.utils.Functions;
import br.com.mpontoc.picaroon.core.utils.Prop;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;

public class Hooks {

	@Before
	public void setAppMobile(Scenario scenario) {

		ActionsCommands.setScenario(scenario);
		if (Prop.getProp("browserOrDevice").toLowerCase().contains("mobile")) {

			if (Functions.getAppRunner() == true) {
				DriverFactory.newApp();
			}
		}
	}

	@BeforeStep
	public void reportBeforeStep(Scenario scenario) {

		ActionsCommands.setScenario(scenario);
		ActionsCommands.isFirstRun = true;

		System.out.println(ActionsCommands.getPrintedInfo());

		if (ActionsCommands.getPrintedInfo() == false) {
			Functions.printInfoExec();
		}
	}

	@AfterStep
	public void reportAfterStep(Scenario scenario) {

		if (DriverFactory.driver != null) {
			ActionsCommands.printScreenAfterStep(scenario);
		}
	}

	@After
	public static void printTimeExecution() {

		if (Prop.getProp("printAfterSteps").equals("false")) {
			ActionsCommands.printScreen();
		}
		Functions.printTimeExecution();
		Functions.setHoraFinalTotal(Functions.retornaData().substring(11));
		ActionsCommands.cucumberWriteReport("Total time execution until 'moment/final execution' "
				+ Functions.substractHours(Functions.getHoraInicialTotal(), Functions.getHoraFinalTotal()));
		ActionsCommands.setPrintedInfo(false);
	}

}
