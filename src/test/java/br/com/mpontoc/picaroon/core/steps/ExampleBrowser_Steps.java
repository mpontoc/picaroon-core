package br.com.mpontoc.picaroon.core.steps;

import br.com.mpontoc.picaroon.core.commands.ActionsCommands;
import br.com.mpontoc.picaroon.core.drivers.DriverFactory;
import br.com.mpontoc.picaroon.core.utils.Functions;
import br.com.mpontoc.picaroon.core.utils.Log;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;

public class ExampleBrowser_Steps {

	@Dado("que eu acesse o site {string}")
	public void que_eu_acesse_o_site(String site) {

		Functions.printInfoExec();

		DriverFactory.driver.get(site);

	}

	@Quando("serão apresentadas informações do dia")
	public void serão_apresentadas_informações_do_dia() {

		ActionsCommands.cucumberWriteReport(DriverFactory.driver.getTitle());

	}

	@Então("estarei atualizado com o que está acontecendo no momento")
	public void estarei_atualizado_com_o_que_está_acontecendo_no_momento() {

		Log.log(DriverFactory.driver.getTitle());

	}

}
