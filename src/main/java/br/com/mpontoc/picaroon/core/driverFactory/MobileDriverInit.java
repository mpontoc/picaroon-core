package br.com.mpontoc.picaroon.core.driverFactory;

import java.net.URL;

import br.com.mpontoc.picaroon.core.mobile.Mobile;
import br.com.mpontoc.picaroon.core.utils.Log;
import br.com.mpontoc.picaroon.core.utils.Prop;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class MobileDriverInit {

	public static AppiumDriver<MobileElement> driverMobile;

	private static AppiumDriver<MobileElement> createMobileDriver() {

		String Browser = Prop.getProp("browserOrDevice");
		String BROWSER_ENV = System.getenv("BROWSER");

		if (BROWSER_ENV != null) {
			Browser = BROWSER_ENV;
			Log.log(Browser);
			Log.log(BROWSER_ENV);
		}

		switch (Browser.trim()) {

		case "mobile":

			try {
				// caso for deixar o apk no projeto passar este caminho
				// String caminhoAPK = System.getProperty("user.dir") + File.separator + "src" +
				// File.separator + "test"
				// + File.separator + "resources" + File.separator + "apk" + File.separator +
				// "original.apk";
				// Log.log(caminhoAPK);
				// passar o apk para ser instalado no momento da execução
				// caps.setCapability(MobileCapabilityType.APP, caminhoAPK);
				URL urlAppium = new URL(Prop.getProp("baseAppium"));
				String device = Mobile.getPlataforma();
				if (device.equals("android")) {
					driverMobile = new AndroidDriver<MobileElement>(urlAppium, Mobile.caps());
				} else {
					driverMobile = new IOSDriver<MobileElement>(urlAppium, Mobile.capsIOS());
				}
				Thread.sleep(2000);
			} catch (Exception e) {
				e.printStackTrace();
				Log.log("Não foi possível conectar ao Appium");
			}
			break;
		}

		return driverMobile;

	}

	public static AppiumDriver<MobileElement> driver() {

		return createMobileDriver();
	}

}