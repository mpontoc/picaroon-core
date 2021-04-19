package br.com.mpontoc.picaroon.core.steps;

import static br.com.mpontoc.picaroon.core.commands.ActionsCommands.driver;

import br.com.mpontoc.picaroon.core.commands.ActionsCommands;
import br.com.mpontoc.picaroon.core.utils.Functions;
import br.com.mpontoc.picaroon.core.utils.Log;
import br.com.mpontoc.picaroon.core.utils.Prop;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

public class ExampleBrowser_Steps {

	@BeforeStep("@browser")
	public void reportClear(Scenario scenario) {
		ActionsCommands.isFirstRun = true;
		ActionsCommands.setScenario(scenario);
	}

	@AfterStep("@browser")
	public void report(Scenario scenario) {
		ActionsCommands.printScreenAfterStep(scenario);
	}

	@After
	public static void printTimeExecution() {
		if (Prop.getProp("printAfterSteps").equals("false")) {
			ActionsCommands.printScreen();
		}
		Functions.printTimeExecution();
	}

	@Dado("que eu acesse o site {string}")
	public void que_eu_acesse_o_site(String site) {
		
		Functions.printInfoExec();
		
		driver.get(site);
		
	}

	@Quando("serão apresentadas informações do dia")
	public void serão_apresentadas_informações_do_dia() {
		
		ActionsCommands.cucumberWriteReport(driver.getTitle());
		
	}

	@Então("estarei atualizado com o que está acontecendo no momento")
	public void estarei_atualizado_com_o_que_está_acontecendo_no_momento() {
		
		Log.log(driver.getTitle());
		
	}

}
