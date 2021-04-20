package br.com.mpontoc.picaroon.core.utils;

import org.junit.AfterClass;

import br.com.mpontoc.picaroon.core.commands.ActionsCommands;

public class BaseTest {

	
	@AfterClass
	public static void finalizaExecucao() {
		try {
			ActionsCommands.driver.quit();
		} catch (Exception e) {
		}
		Functions.waitSeconds(3);
		if (!Prop.getProp("executionSilk").equals("true")) {
			Log.log("Report salvo no caminho: " + Functions.getPathReportCompleto());
		} else {
			Log.log("Report salvo no Silk Central");
		}
		Functions.zipReportFiles();
		Log.log("driver finalizado [ " + Prop.getProp("browserOrDevice") + " ]");
	}

}
