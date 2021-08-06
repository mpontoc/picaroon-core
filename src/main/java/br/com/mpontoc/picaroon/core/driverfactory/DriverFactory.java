package br.com.mpontoc.picaroon.core.driverfactory;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import br.com.mpontoc.picaroon.core.mobile.Mobile;
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

}
