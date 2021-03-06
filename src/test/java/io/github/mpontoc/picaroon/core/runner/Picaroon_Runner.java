package io.github.mpontoc.picaroon.core.runner;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.github.mpontoc.picaroon.core.utils.BaseTest;
import io.github.mpontoc.picaroon.core.utils.Functions;


@RunWith(Cucumber.class)

@CucumberOptions(
		
		tags = { " @tag " },
		features = "src/test/resources/features", // local onde estão as features
		glue = { 
				"io.github.mpontoc.picaroon.core.steps", // package onde estão os steps
				"io.github.mpontoc.picaroon.core.config" // chamada do spring
		}, 
		plugin = { "pretty", // imprime a descrição da feature
				 "json:target/reports/results.json",
				 "html:target/cucumber-reports/runner",
				}, 
		monochrome = true, // deixa o console só com fonte cor preta
		dryRun = false, // ao rodar quando true percorre toda automação verificando se faltam passos
		strict = true // quando está true ele falha o cenario inteiro caso estja faltando algum step
)

public class Picaroon_Runner extends BaseTest {
	
	@BeforeClass
	
	public static void setUp() {
		Functions.apagaLog4j();
//		Functions.setAppRunner(true);
//		Mobile.setApp("calc");
//		Mobile.setPlataforma("android");
//		Mobile.setDeviceName("emulator-5554");
//		Mobile.setDeviceUDID("emulator-5554");
		Functions.setPathReport("runner");
//		Functions.setDescricaoReport("Backend Test");
		Functions.setupExecution();
		
	}


}
