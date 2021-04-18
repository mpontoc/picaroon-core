package br.com.mpontoc.picaroon.core.runner;

import org.junit.Before;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)

@CucumberOptions(
		
		tags = { " @tag1 " },
		features = "src/test/resources/features", // local onde estão as features
		glue = { 
				"br.com.mpontoc.picaroon.core.steps", // package onde estão os steps
				"br.com.mpontoc.picaroon.core.conf" // chamada do spring
		}, 
		plugin = { "pretty", // imprime a descrição da feature
				 "json:target/reports/results.json",
				 "html:target/cucumber-reports",
				}, 
		monochrome = true, // deixa o console só com fonte cor preta
		dryRun = false, // ao rodar quando true percorre toda automação verificando se faltam passos
		strict = true // quando está true ele falha o cenario inteiro caso estja faltando algum step
)

public class Picaroon_Runner {
	
	@Before
	public void init() {
		
	}


}
