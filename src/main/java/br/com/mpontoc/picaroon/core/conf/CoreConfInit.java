package br.com.mpontoc.picaroon.core.conf;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
		basePackages = { 
				"br.com.mpontoc.picaroon.core.conf", 
				"br.com.mpontoc.picaroon.*"
		} 
		)
//@PropertySource("application.properties")
@EntityScan(
		basePackages = {
				"br.com.mpontoc.picaroon.core.conf", 
				"br.com.mpontoc.picaroon.*"
		}
		)

public class CoreConfInit {
	

}
