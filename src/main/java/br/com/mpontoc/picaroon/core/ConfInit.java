package br.com.mpontoc.picaroon.core;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(
		basePackages = { 
				"br.com.mpontoc.picaroon.core", 
				"br.com.mpontoc.picaroon.*"
		} 
		)
//@PropertySource("application.properties")
@EntityScan(
		basePackages = {
				"br.com.mpontoc.picaroon.core", 
				"br.com.mpontoc.picaroon.*"
		}
		)

public class ConfInit {


}
