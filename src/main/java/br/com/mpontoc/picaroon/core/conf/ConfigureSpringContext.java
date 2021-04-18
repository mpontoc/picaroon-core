package br.com.mpontoc.picaroon.core.conf;

import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;

import io.cucumber.java.Before;

@ContextConfiguration(classes = ConfInit.class, loader = SpringBootContextLoader.class)
public class ConfigureSpringContext {
	@Before
	public void SetupSpringContext() {
		
	}
}
