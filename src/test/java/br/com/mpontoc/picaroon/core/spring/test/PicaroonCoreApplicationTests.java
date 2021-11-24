package br.com.mpontoc.picaroon.core.spring.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.mpontoc.picaroon.core.drivers.DriverFactory;
import br.com.mpontoc.picaroon.core.utils.BaseTest;
import br.com.mpontoc.picaroon.core.utils.Functions;
import br.com.mpontoc.picaroon.core.utils.Log;
import br.com.mpontoc.picaroon.core.utils.Prop;

@SpringBootTest
class PicaroonCoreApplicationTests {

	@Value("${prop.value.test}")
	private String valueTest;
	
	
	@Test
	void contextLoads() {
		
		Prop.setPropAndSave("browserOrDevice", "chrome-headless");
		Functions.setPathReport("testPicsCore");
		Functions.setupExecution();
		DriverFactory.driver.get("https://www.google.com");
		Log.log(DriverFactory.driver.getTitle());
		Log.log(valueTest);
		BaseTest.endExecution();
		
	}

}
