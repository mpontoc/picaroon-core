package io.github.mpontoc.picaroon.core.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(
		basePackages = { 
				"io.github.mpontoc.picaroon.core.config", 
				"io.github.mpontoc.picaroon.*"
		} 
		)
@EntityScan(
		basePackages = {
				"io.github.mpontoc.picaroon.core.config", 
				"io.github.mpontoc.picaroon.*"
		}
		)
@PropertySource("release.properties")

public class PicaroonCoreConfigSpringScanPacks {
	

}
