package io.github.mpontoc.picaroon.core.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
		basePackages = { 
				"io.github.mpontoc.picaroon.core.config", 
				"io.github.mpontoc.picaroon.*"
		} 
		)
//@PropertySource("application.properties")
@EntityScan(
		basePackages = {
				"io.github.mpontoc.picaroon.core.config", 
				"io.github.mpontoc.picaroon.*"
		}
		)

public class CoreConfigInit {
	

}
