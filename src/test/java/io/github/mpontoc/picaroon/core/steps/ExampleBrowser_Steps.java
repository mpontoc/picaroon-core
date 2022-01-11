package io.github.mpontoc.picaroon.core.steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.github.mpontoc.picaroon.core.drivers.DriverFactory;
import io.github.mpontoc.picaroon.core.execution.report.Report;
import io.github.mpontoc.picaroon.core.utils.Functions;
import io.github.mpontoc.picaroon.core.utils.Log;

public class ExampleBrowser_Steps {

	@Dado("que eu acesse o site {string}")
	public void que_eu_acesse_o_site(String site) {

		Functions.printInfoExec();

		DriverFactory.driver.get(site);

	}

	@Quando("serão apresentadas informações do dia")
	public void serão_apresentadas_informações_do_dia() {

		Report.cucumberWriteReport(DriverFactory.driver.getTitle());

	}

	@Então("estarei atualizado com o que está acontecendo no momento")
	public void estarei_atualizado_com_o_que_está_acontecendo_no_momento() {

		Log.log(DriverFactory.driver.getTitle());

	}

}
