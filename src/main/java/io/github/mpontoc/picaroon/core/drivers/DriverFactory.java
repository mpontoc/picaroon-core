package io.github.mpontoc.picaroon.core.drivers;

import static io.github.mpontoc.picaroon.core.drivers.CapabilitiesConstants.APP_NAME;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.github.mpontoc.picaroon.core.drivers.impl.AndroidDriverImpl;
import io.github.mpontoc.picaroon.core.drivers.impl.IOSDriverImpl;
import io.github.mpontoc.picaroon.core.drivers.impl.WebDriverImpl;
import io.github.mpontoc.picaroon.core.elements.ElementFunctions;
import io.github.mpontoc.picaroon.core.execution.Execution;
import io.github.mpontoc.picaroon.core.mobile.Mobile;
import io.github.mpontoc.picaroon.core.utils.Functions;
import io.github.mpontoc.picaroon.core.utils.Log;
import io.github.mpontoc.picaroon.core.utils.PropertiesVariables;

public class DriverFactory {

	public static WebDriver driver = null;
	public static IOSDriver<IOSElement> iosDriver = null;
	public static AndroidDriver<AndroidElement> androidDriver = null;
	public static AppiumDriver<MobileElement> mobileDriver = null;
	public static JavascriptExecutor executor = null;
	private static boolean startDriver = false;

	@SuppressWarnings("unchecked")
	public static WebDriver setupDriver() {

		String device = Mobile.getPlataforma();
		ElementFunctions.setupElement();

		if (PropertiesVariables.BROWSER_OR_MOBILE.contains("mobile") && Execution.getAppRunner() == true || startDriver == true) {

			if (device.toLowerCase().equals("ios")) {
				iosDriver = new IOSDriverImpl().createDriver();
				driver = iosDriver;
				mobileDriver = (AppiumDriver<MobileElement>) driver;
				executor = (JavascriptExecutor) driver;
				return driver;
			} else {
				androidDriver = new AndroidDriverImpl().createDriver();
				driver = androidDriver;
				mobileDriver = (AppiumDriver<MobileElement>) driver;
				executor = (JavascriptExecutor) driver;
				return driver;
			}

		} else {
			driver = new WebDriverImpl().createDriver();
			executor = (JavascriptExecutor) driver;
			return driver;
		}
	}

	public static void newApp() {

		if (driver != null) {
			String appRunning = mobileDriver.getCapabilities().getCapability(APP_NAME).toString();
			if (!appRunning.toLowerCase().equals(Mobile.getApp())) {
				Log.log("Starting app '" + Mobile.getApp() + "'");
				driver.quit();
				driver = null;
				driver = setupDriver();
			} else if (PropertiesVariables.RESET_APP.equals("true")) {
				Log.log("Reseting app '" + Mobile.getApp() + "'");
				mobileDriver.resetApp();
				Functions.printInfoExec();
			} else {
				driver.quit();
				driver = null;
				driver = setupDriver();
				Log.log("Creating a new driver to app '" + Mobile.getApp() + "'");
			}
		} else {
			startDriver = true;
			driver = setupDriver();
			Log.log("Creating a new driver to app '" + Mobile.getApp() + "'");
		}

	}

}
