package br.com.mpontoc.picaroon.core.conf;

import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;

import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(classes = CoreConfInit.class, loader = SpringBootContextLoader.class)
public class CoreConfigureSpringContext {
	@Before
	public void SetupSpringContext() {
		
	}
}
