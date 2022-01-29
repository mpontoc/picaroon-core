package io.github.mpontoc.picaroon.core.drivers.impl;

import static io.github.mpontoc.picaroon.core.drivers.CapabilitiesConstants.APP_NAME;
import static io.github.mpontoc.picaroon.core.drivers.CapabilitiesConstants.DEVICE_NAME;
import static io.github.mpontoc.picaroon.core.drivers.CapabilitiesConstants.PLATFORM_NAME;
import static io.github.mpontoc.picaroon.core.drivers.CapabilitiesConstants.UDID;

import java.net.URL;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.github.mpontoc.picaroon.core.drivers.Driver;
import io.github.mpontoc.picaroon.core.exception.PicaroonException;
import io.github.mpontoc.picaroon.core.mobile.Mobile;
import io.github.mpontoc.picaroon.core.utils.PropertiesVariables;

public class IOSDriverImpl implements Driver {

    private static IOSDriver<IOSElement> iosDriver;

    @Override
    public IOSDriver<IOSElement> createDriver() {

        try {
            URL urlAppium = new URL(PropertiesVariables.BASE_URL_APPIUM);
            iosDriver = new IOSDriver<IOSElement>(urlAppium,
                    Mobile.caps(Mobile.getCapsFileJson(), Mobile.getCapsNameDeviceOrApp()));

            Thread.sleep(2000);
            Mobile.setApp(iosDriver.getCapabilities().getCapability(APP_NAME).toString().toLowerCase());
            Mobile.setDeviceUDID(iosDriver.getCapabilities().getCapability(UDID).toString().toLowerCase());
            Mobile.setPlataforma(iosDriver.getCapabilities().getCapability(PLATFORM_NAME).toString().toLowerCase());
            Mobile.setDeviceName(iosDriver.getCapabilities().getCapability(DEVICE_NAME).toString().toLowerCase());
            return iosDriver;
        } catch (Exception e) {
            throw new PicaroonException("Appium: iOS driver not created " + e.getMessage());
        }
    }
}
