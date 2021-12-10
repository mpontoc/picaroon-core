package io.github.mpontoc.picaroon.core.drivers.impl;

import java.net.URL;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.github.mpontoc.picaroon.core.drivers.Driver;
import io.github.mpontoc.picaroon.core.mobile.Mobile;
import io.github.mpontoc.picaroon.core.utils.Log;
import io.github.mpontoc.picaroon.core.utils.Prop;

public class IOSDriverImpl implements Driver {

	private static IOSDriver<IOSElement> iosDriver;

	@Override
	public IOSDriver<IOSElement> createDriver() {

		try {
			URL urlAppium = new URL(Prop.getProp("baseAppium"));
			iosDriver = new IOSDriver<IOSElement>(urlAppium,
					Mobile.caps(Mobile.getCapsFileJson(), Mobile.getCapsNameDeviceOrApp()));

			Thread.sleep(2000);
			Mobile.setApp(iosDriver.getCapabilities().getCapability("appName").toString().toLowerCase());
			Mobile.setDeviceUDID(iosDriver.getCapabilities().getCapability("udid").toString().toLowerCase());
			Mobile.setPlataforma(iosDriver.getCapabilities().getCapability("platformName").toString().toLowerCase());
			Mobile.setDeviceName(iosDriver.getCapabilities().getCapability("deviceName").toString().toLowerCase());
			return iosDriver;
		} catch (Exception e) {
			e.printStackTrace();
			Log.log("Não foi possível conectar ao Appium");

		}
		return null;
	}
}
