package br.com.mpontoc.picaroon.core.drivers;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import br.com.mpontoc.picaroon.core.drivers.impl.AndroidDriverImpl;
import br.com.mpontoc.picaroon.core.drivers.impl.IOSDriverImpl;
import br.com.mpontoc.picaroon.core.drivers.impl.WebDriverImpl;
import br.com.mpontoc.picaroon.core.mobile.Mobile;
import br.com.mpontoc.picaroon.core.utils.ElementFunctions;
import br.com.mpontoc.picaroon.core.utils.Functions;
import br.com.mpontoc.picaroon.core.utils.Log;
import br.com.mpontoc.picaroon.core.utils.Prop;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class DriverFactory {

	public static WebDriver driver = null;
	public static IOSDriver<IOSElement> iosDriver = null;
	public static AndroidDriver<AndroidElement> androidDriver = null;
	public static AppiumDriver<MobileElement> mobileDriver = null;
	public static JavascriptExecutor executor = (JavascriptExecutor) driver;
	private static boolean startDriver = false;

	@SuppressWarnings("unchecked")
	public static WebDriver setupDriver() {

		String device = Mobile.getPlataforma();
		ElementFunctions.setupElement();

		if (Prop.getProp("browserOrMobile").toLowerCase().contains("mobile") && Functions.getAppRunner() == true || startDriver == true) {

			if (device.toLowerCase().equals("ios")) {
				iosDriver = new IOSDriverImpl().createDriver();
				driver = iosDriver;
				mobileDriver = (AppiumDriver<MobileElement>) driver;
				return driver;
			} else {
				androidDriver = new AndroidDriverImpl().createDriver();
				driver = androidDriver;
				mobileDriver = (AppiumDriver<MobileElement>) driver;
				return driver;
			}

		} else {

			driver = new WebDriverImpl().createDriver();
			return driver;
		}
	}

	public static void newApp() {

		if (driver != null) {
			String appRunning = mobileDriver.getCapabilities().getCapability("appName").toString();
			if (!appRunning.toLowerCase().equals(Mobile.getApp())) {
				Log.log("Starting app '" + Mobile.getApp() + "'");
				driver.quit();
				driver = null;
				driver = setupDriver();
			} else if (Prop.getProp("resetApp").equals("true")) {
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
