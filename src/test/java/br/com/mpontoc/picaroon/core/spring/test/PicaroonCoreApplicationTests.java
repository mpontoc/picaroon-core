package br.com.mpontoc.picaroon.core.spring.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.mpontoc.picaroon.core.commands.ActionsCommands;
import br.com.mpontoc.picaroon.core.utils.BaseTest;
import br.com.mpontoc.picaroon.core.utils.Functions;
import br.com.mpontoc.picaroon.core.utils.Log;

@SpringBootTest
class PicaroonCoreApplicationTests {

	@Value("${prop.value.test}")
	private String valueTest;
	
	
	@Test
	void contextLoads() {
		
		Functions.apagaLog4j();
		Functions.setPathReport("testPicsCore");
		Functions.setUp();
		ActionsCommands.driver.get("https://www.google.com");
		Log.log(ActionsCommands.driver.getTitle());
		Log.log(valueTest);
		BaseTest.finalizaExecucao();
		
	}

}
