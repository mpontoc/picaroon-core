package br.com.mpontoc.picaroon.core.drivers;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import br.com.mpontoc.picaroon.core.mobile.Mobile;
import br.com.mpontoc.picaroon.core.utils.Functions;
import br.com.mpontoc.picaroon.core.utils.Log;
import br.com.mpontoc.picaroon.core.utils.Prop;

public class DriverFactory {

	public static WebDriver driver = null;
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
			} else if (MobileDriverInit.driver() != null) {
				driver = MobileDriverInit.driverMobile;
			} else {
				Log.log("Cannot possible to create driver");
			}
		} else {
			driver = WebDriverInit.driver();
			executor = (JavascriptExecutor) driver;
		}
	}

	public static void newApp() {

		if (Functions.getAppRunner() != true && Mobile.getApp() != null) {
			if (driver != null) {
				Capabilities caps = MobileDriverInit.driverMobile.getCapabilities();
				if (!caps.toString().contains(Mobile.getApp().toLowerCase())) {
					Log.log("Starting app " + Mobile.getApp());
					driver.quit();
					driver = null;
					driver = MobileDriverInit.driver();
				} else {
					Log.log("Reseting app " + Mobile.getApp());
					MobileDriverInit.driverMobile.resetApp();
					Functions.printInfoExec();
				}
			} else {
				try {
					driver = MobileDriverInit.driver();
					Log.log("Appium driver inicializado com o app: " + Mobile.getApp());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			if (driver != null && Functions.reiniciaApp == null) {
				Functions.reiniciaApp = true;
				driver = MobileDriverInit.driverMobile;
			} else if (driver != null && Functions.reiniciaApp != null) {
				MobileDriverInit.driverMobile.resetApp();
				driver = MobileDriverInit.driverMobile;
			} else {
				driver = null;
				driver = MobileDriverInit.driver();
			}
		}
	}

}
