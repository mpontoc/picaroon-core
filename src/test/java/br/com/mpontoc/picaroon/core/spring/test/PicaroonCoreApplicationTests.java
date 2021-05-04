package br.com.mpontoc.picaroon.core.spring.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.mpontoc.picaroon.core.utils.Functions;
import br.com.mpontoc.picaroon.core.utils.Log;

@SpringBootTest
class PicaroonCoreApplicationTests {

	@Test
	void contextLoads() {
		
		Functions.setPathReport("testPicsCore");
		Functions.setUp();
		
		String [] textos = {"fdfd" , "fdsfa"};
		
		Log.log("testes");
		Log.log(textos[0]);
		
	}

}
