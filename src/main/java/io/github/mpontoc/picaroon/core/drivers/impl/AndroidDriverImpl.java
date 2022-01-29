package io.github.mpontoc.picaroon.core.drivers.impl;

import static io.github.mpontoc.picaroon.core.drivers.CapabilitiesConstants.APP_NAME;
import static io.github.mpontoc.picaroon.core.drivers.CapabilitiesConstants.DEVICE_NAME;
import static io.github.mpontoc.picaroon.core.drivers.CapabilitiesConstants.PLATFORM_NAME;
import static io.github.mpontoc.picaroon.core.drivers.CapabilitiesConstants.UDID;

import java.net.URL;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.github.mpontoc.picaroon.core.drivers.Driver;
import io.github.mpontoc.picaroon.core.exception.PicaroonException;
import io.github.mpontoc.picaroon.core.mobile.Mobile;
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
            Mobile.setApp(androidDriver.getCapabilities().getCapability(APP_NAME).toString().toLowerCase());
            Mobile.setDeviceUDID(androidDriver.getCapabilities().getCapability(UDID).toString().toLowerCase());
            Mobile.setPlataforma(
                    androidDriver.getCapabilities().getCapability(PLATFORM_NAME).toString().toLowerCase());
            Mobile.setDeviceName(androidDriver.getCapabilities().getCapability(DEVICE_NAME).toString().toLowerCase());
            return androidDriver;
        } catch (Exception e) {
            throw new PicaroonException("Appium: Android driver not created " + e.getMessage());
        }
    }
}
