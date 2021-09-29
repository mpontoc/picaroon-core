package br.com.mpontoc.picaroon.core.drivers;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import br.com.mpontoc.picaroon.core.commands.ActionsCommands;
import br.com.mpontoc.picaroon.core.drivers.impl.AppiumDriverImpl;
import br.com.mpontoc.picaroon.core.mobile.Mobile;
import br.com.mpontoc.picaroon.core.utils.Functions;
import br.com.mpontoc.picaroon.core.utils.Log;
import br.com.mpontoc.picaroon.core.utils.Prop;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class DriverFactory {

	public static WebDriver driver = null;
	public static IOSDriver<IOSElement> iosDriver = null;
	public static AndroidDriver<AndroidElement> androidDriver = null;	
	public static Integer deviceElement = null;
	public static JavascriptExecutor executor = null;

	/*
	 * set the device for position of list string ----- position 0 is android -----
	 * position 1 is ios
	 */
	public static void setDeviceElement() {

		if (Mobile.getPlataforma() != null) {
			if (Mobile.getPlataforma().equals("android"))
				// android
				deviceElement = 0;
			else
				// ios
				deviceElement = 1;
		}
	}

	public static void setupDriver() {

		if (Prop.getProp("browserOrDevice").toLowerCase().contains("mobile")) {
			setDeviceElement();
			if (Mobile.getApp() == null || driver == null) {
				driver = null;
			} else if (AppiumDriverImpl.driverMobile != null) {
				driver = AppiumDriverImpl.driverMobile;
			} else if (Mobile.getApp() != null) {
				driver = SetupDriver.createDriver();
			} else {
				Log.log("Cannot possible to create driver");
			}
		} else {
			driver = SetupDriver.createDriver();
			executor = (JavascriptExecutor) driver;
		}
	}

	public static void newApp() {

		if (driver != null) {
			String appRunning = AppiumDriverImpl.driverMobile.getCapabilities().getCapability("appName").toString();
			if (!appRunning.toLowerCase().equals(Mobile.getApp())) {
				Log.log("Starting app " + Mobile.getApp());
				driver.quit();
				driver = null;
				driver = SetupDriver.createDriver();
			} else if (Prop.getProp("resetApp").equals("true")) {
				Log.log("Reseting app " + Mobile.getApp());
				AppiumDriverImpl.driverMobile.resetApp();
				Functions.printInfoExec();
			} else {
				driver.quit();
				driver = null;
				driver = SetupDriver.createDriver();
				Log.log("Creating a new driver to app " + Mobile.getApp());
			}
		} else {
			driver = SetupDriver.createDriver();
			Log.log("Creating a new driver to app " + Mobile.getApp());
		}
		

	}

}
