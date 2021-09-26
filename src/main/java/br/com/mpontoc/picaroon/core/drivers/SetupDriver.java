package br.com.mpontoc.picaroon.core.drivers;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import br.com.mpontoc.picaroon.core.drivers.impl.AndroidDriverImpl;
import br.com.mpontoc.picaroon.core.drivers.impl.AppiumDriverImpl;
import br.com.mpontoc.picaroon.core.drivers.impl.IOSDriverImp;
import br.com.mpontoc.picaroon.core.drivers.impl.WebDriverImpl;
import br.com.mpontoc.picaroon.core.mobile.Mobile;
import br.com.mpontoc.picaroon.core.utils.Functions;
import br.com.mpontoc.picaroon.core.utils.Prop;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class SetupDriver {

	@SuppressWarnings("unused")
	private static WebDriver driverReturned = null;

	@SuppressWarnings("unchecked")
	public static WebDriver createDriver() {

		String device = Mobile.getPlataforma();

		if (Prop.getProp("browserOrDevice").toLowerCase().contains("mobile")) {
			DriverFactory.setDeviceElement();

			if (device.toLowerCase().equals("android")) {
				Driver driverAndroid = new AndroidDriverImpl();
				AndroidDriver<AndroidElement> driver = (AndroidDriver<AndroidElement>) driverAndroid.driver();
				DriverFactory.driver = driver;
				AppiumDriverImpl.driverMobile = (AppiumDriver<MobileElement>) DriverFactory.driver;
				Functions.printInfoExec();
				return driverReturned = driver;
			} else {
				Driver driverIOS = new IOSDriverImp();
				IOSDriver<IOSElement> driver = (IOSDriver<IOSElement>) driverIOS.driver();
				DriverFactory.driver = driver;
				AppiumDriverImpl.driverMobile = (AppiumDriver<MobileElement>) DriverFactory.driver;
				Functions.printInfoExec();
				return driverReturned = driver;
			}
		} else {
			Driver webDriver = new WebDriverImpl();
			WebDriver driver = (WebDriver) webDriver.driver();
			DriverFactory.driver = driver;
			DriverFactory.executor = (JavascriptExecutor) driver;
			return driverReturned = driver;
		}
	}

}
