package br.com.mpontoc.picaroon.core.drivers.impl;

import java.net.URL;

import org.openqa.selenium.WebDriver;

import br.com.mpontoc.picaroon.core.drivers.Driver;
import br.com.mpontoc.picaroon.core.mobile.Mobile;
import br.com.mpontoc.picaroon.core.utils.Log;
import br.com.mpontoc.picaroon.core.utils.Prop;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class IOSDriverImpl implements Driver {

	public static IOSDriver<IOSElement> iosDriver;

	public void createIOSDriver() {

		try {

			URL urlAppium = new URL(Prop.getProp("baseAppium"));

			iosDriver = new IOSDriver<IOSElement>(urlAppium,
					Mobile.caps(Mobile.getCapsFileJson(), Mobile.getCapsNameDeviceOrApp()));

			Thread.sleep(2000);
			Mobile.setApp(iosDriver.getCapabilities().getCapability("appName").toString().toLowerCase());
			Mobile.setDeviceUDID(iosDriver.getCapabilities().getCapability("udid").toString().toLowerCase());
			Mobile.setPlataforma(iosDriver.getCapabilities().getCapability("platformName").toString().toLowerCase());
			Mobile.setDeviceName(iosDriver.getCapabilities().getCapability("deviceName").toString().toLowerCase());
		} catch (Exception e) {
			e.printStackTrace();
			Log.log("Não foi possível conectar ao Appium");

		}
	}

	@Override
	public WebDriver driver() {
		
		createIOSDriver();

		return iosDriver;

	}

}
