package br.com.mpontoc.picaroon.core.mobile;

import java.io.File;

import org.openqa.selenium.remote.DesiredCapabilities;

import br.com.mpontoc.picaroon.core.utils.Log;
import br.com.mpontoc.picaroon.core.utils.Prop;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class Mobile {

	private static String app = null;
	private static String plataforma = null;
	private static String deviceUDID = null;
	private static String deviceName = null;

	private static String packageApp = null;
	private static String activityApp = null;
	private static String[] packageActivities = { packageApp, activityApp };
	private static String pathIOSapps = null;

	static String DEVICE_ENV = System.getenv("DEVICE");
	static String DEVICE_NAME_ENV = System.getenv("DEVICE_NAME");

	public static void setApp(String app) {
		Mobile.app = app;
		Mobile.setPackageActivities(app);
	}

	public static String getApp() {
		return app;
	}

	public static String[] setPackageActivities(String app) {

		switch (app.trim()) {

		case "calc":

			try {

				packageActivities[0] = "com.calc";
				packageActivities[1] = "com.calc.activity";
				Log.log("Iniciando app " + app);
				return Mobile.packageActivities;

			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		}
		return Mobile.packageActivities;

	}

	public static String[] getPackageActivities() {
		return Mobile.packageActivities;

	}

	public static DesiredCapabilities caps() {
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.0");
		caps.setCapability("automationName", "uiautomator2");
		caps.setCapability("appPackage", packageActivities[0]);
		caps.setCapability("appActivity", packageActivities[1]);
		caps.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
		// caps.setCapability("autoAcceptAlerts", true);
		caps.setCapability("noReset", false);
		if (DEVICE_ENV == null) {
			caps.setCapability(MobileCapabilityType.UDID, getDeviceUDID());
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, getDeviceName());
		} else {
			caps.setCapability(MobileCapabilityType.UDID, DEVICE_ENV);
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, DEVICE_NAME_ENV);
		}
		return caps;
	}

	public static String getPlataforma() {
		return plataforma;
	}

	public static void setPlataforma(String device) {
		Mobile.plataforma = device;
	}

	public static DesiredCapabilities capsIOS() {
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("automationName", "XCUITest");
		caps.setCapability("platformName", "IOS");
		caps.setCapability("platformVersion", "13.3");
		caps.setCapability("noReset", false);
		caps.setCapability("autoGrantPermissions", true);
		switch (Mobile.getApp()) {

		case "calc":
			caps.setCapability("app", Mobile.getPathIOSapps() + File.separator + "calc.app");
			caps.setCapability("bundleId", "com.calc");
			break;
		}

		if (DEVICE_ENV == null) {
			caps.setCapability("udid", getDeviceUDID());
			caps.setCapability("deviceName", getDeviceName());
		} else {
			caps.setCapability("udid", DEVICE_ENV);
			caps.setCapability("deviceName", DEVICE_NAME_ENV);
		}

		return caps;
	}

	public static String getDeviceName() {
		return deviceName;
	}

	public static void setDeviceName(String deviceName) {
		Mobile.deviceName = deviceName;
	}

	public static String getDeviceUDID() {
		return deviceUDID;
	}

	public static void setDeviceUDID(String deviceUDID) {
		Mobile.deviceUDID = deviceUDID;
	}

	public static String getPathIOSapps() {
		return pathIOSapps;
	}

	public static void setPathIOSapps(String pathIOSapps) {
		Mobile.pathIOSapps = pathIOSapps;
	}

}