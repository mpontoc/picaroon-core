package io.github.mpontoc.picaroon.core.drivers.impl;

import java.net.URL;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.github.mpontoc.picaroon.core.drivers.Driver;
import io.github.mpontoc.picaroon.core.mobile.Mobile;
import io.github.mpontoc.picaroon.core.utils.Log;
import io.github.mpontoc.picaroon.core.utils.PropertiesVariables;

public class AndroidDriverImpl implements Driver {

	private static AndroidDriver<AndroidElement> androidDriver;

	@Override
	public AndroidDriver<AndroidElement> createDriver() {

		try {
			URL urlAppium = new URL(PropertiesVariables.BASE_URL_APPIUM);
			androidDriver = new AndroidDriver<AndroidElement>(urlAppium,
					Mobile.caps(Mobile.getCapsFileJson(), Mobile.getCapsNameDeviceOrApp()));
			Thread.sleep(2000);
			Mobile.setApp(androidDriver.getCapabilities().getCapability("appName").toString().toLowerCase());
			Mobile.setDeviceUDID(androidDriver.getCapabilities().getCapability("udid").toString().toLowerCase());
			Mobile.setPlataforma(
					androidDriver.getCapabilities().getCapability("platformName").toString().toLowerCase());
			Mobile.setDeviceName(androidDriver.getCapabilities().getCapability("deviceName").toString().toLowerCase());
			return androidDriver;
		} catch (Exception e) {
			e.printStackTrace();
			Log.log("Não foi possível conectar ao Appium");
		}
		return null;
	}
}
