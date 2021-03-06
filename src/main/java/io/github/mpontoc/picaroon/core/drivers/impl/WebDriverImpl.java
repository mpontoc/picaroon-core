package io.github.mpontoc.picaroon.core.drivers.impl;

import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.mpontoc.picaroon.core.drivers.Driver;
import io.github.mpontoc.picaroon.core.utils.Functions;
import io.github.mpontoc.picaroon.core.utils.Log;
import io.github.mpontoc.picaroon.core.utils.PropertiesVariables;

public class WebDriverImpl implements Driver {

	private static WebDriver driver;

	@Override
	public WebDriver createDriver() {
		String Browser = PropertiesVariables.BROWSER_OR_MOBILE;
		String BROWSER_ENV = System.getenv("BROWSER");
		String baseURLSeleniumGrip = PropertiesVariables.BASE_URL_SELENIUM_GRID;

		if (BROWSER_ENV != null) {
			Browser = BROWSER_ENV;
			Log.log(Browser);
			Log.log(BROWSER_ENV);
		}

		switch (Browser.trim()) {

		case "firefox":

			try {
				WebDriverManager.firefoxdriver().setup();
			} catch (Exception e3) {
				Log.log("\n Ex: \n" + "lib/webdriver/linux/geckodriver for linux \n or \n"
						+ "lib/webdriver/geckodriver.exe for windows");
				Functions.setPropDriver();
			}

			try {
				FirefoxOptions optionsFirefox = new FirefoxOptions();
				optionsFirefox.addArguments("--width=1024");
				optionsFirefox.addArguments("--height=768");
				optionsFirefox.addArguments("--start-maximized");
				driver = new FirefoxDriver(optionsFirefox);
				Thread.sleep(1000);
				Log.log("Window sizes " + driver.manage().window().getSize().toString());
			} catch (Exception e2) {
				Log.log("Não foi possível iniciar o driver " + PropertiesVariables.BROWSER_OR_MOBILE);
				e2.printStackTrace();
			}

			break;

		case "firefox-headless":

			try {
				WebDriverManager.firefoxdriver().setup();
			} catch (Exception e2) {
				Log.log("\n Ex: \n" + "lib/webdriver/linux/geckodriver for linux \n or \n"
						+ "lib/webdriver/geckodriver.exe for windows");
				Functions.setPropDriver();
			}

			try {
				FirefoxOptions optHeadlessFirefox = new FirefoxOptions();
				optHeadlessFirefox.setHeadless(true);
				optHeadlessFirefox.addArguments("--width=1024");
				optHeadlessFirefox.addArguments("--height=768");
				driver = new FirefoxDriver(optHeadlessFirefox);
				Thread.sleep(1000);
				Log.log("Window sizes " + driver.manage().window().getSize().toString());
			} catch (Exception e1) {
				Log.log("Problema para rodar com firefox headless");
				e1.printStackTrace();
			}

			break;

		case "firefox-hub":

			try {
				DesiredCapabilities cap = new DesiredCapabilities();
				cap.setBrowserName("firefox");
				cap.setPlatform(Platform.LINUX);
				FirefoxOptions optRemoteFirefox = new FirefoxOptions();
				optRemoteFirefox.merge(cap);
				optRemoteFirefox.setHeadless(true);
				optRemoteFirefox.addArguments("--width=1024");
				optRemoteFirefox.addArguments("--height=768");
				driver = new RemoteWebDriver(new URL(baseURLSeleniumGrip), optRemoteFirefox);
				Thread.sleep(2000);
				Log.log("Window sizes " + driver.manage().window().getSize().toString());
			} catch (Exception e) {
				Log.log("Não foi possível conectar o selenium hub firefox");
				e.printStackTrace();
			}
			break;

		case "chrome":

			try {
				WebDriverManager.chromedriver().setup();
			} catch (Exception e2) {
				Log.log("\n Ex: \n" + "lib/webdriver/linux/chromedriver for linux \n or \n"
						+ "lib/webdriver/chromedriver.exe for windows");
				Functions.setPropDriver();
			}

			try {
				ChromeOptions optionsChrome = new ChromeOptions();
				optionsChrome.addArguments("--window-size=1024,768");
				driver = new ChromeDriver(optionsChrome);
				Thread.sleep(1000);
				Log.log("Window sizes " + driver.manage().window().getSize().toString());
			} catch (Exception e1) {
				Log.log("Não foi possível iniciar o driver " + PropertiesVariables.BROWSER_OR_MOBILE);
				e1.printStackTrace();
			}
			break;

		case "chrome-headless":

			try {
				WebDriverManager.chromedriver().setup();
			} catch (Exception e2) {
				Log.log("\n Ex: \n" + "lib/webdriver/linux/chromedriver for linux \n or \n"
						+ "lib/webdriver/chromedriver.exe for windows");
				Functions.setPropDriver();
			}

			try {
				ChromeOptions optHeadlessChrome1 = new ChromeOptions();
				optHeadlessChrome1.setHeadless(true);
				optHeadlessChrome1.addArguments("--window-size=1024,768");
				// optHeadlessChrome.addArguments("--log-level=3");
				driver = new ChromeDriver(optHeadlessChrome1);
				Thread.sleep(1000);
				Log.log("Window sizes " + driver.manage().window().getSize().toString());
			} catch (Exception e1) {
				Log.log("Não foi possível iniciar o driver " + PropertiesVariables.BROWSER_OR_MOBILE);
				e1.printStackTrace();
			}
			break;

		case "chrome-hub":

			try {
				DesiredCapabilities cap = new DesiredCapabilities();
				cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				cap.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);

				cap.setBrowserName("chrome");
				cap.setPlatform(Platform.LINUX);
				ChromeOptions optRemoteChrome = new ChromeOptions();
				optRemoteChrome.merge(cap);
				optRemoteChrome.setHeadless(true);
				optRemoteChrome.addArguments("--aggressive-cache-discard");
				optRemoteChrome.addArguments("--allow-insecure-localhost");
				optRemoteChrome.addArguments("--disable-application-cache");
				optRemoteChrome.addArguments("--disable-browser-side-navigation");
				optRemoteChrome.addArguments("--disable-cache");
				optRemoteChrome.addArguments("--disable-dev-shm-usage");
				optRemoteChrome.addArguments("--disable-extensions");
				optRemoteChrome.addArguments("--disable-gpu");
				optRemoteChrome.addArguments("--disable-offline-load-stale-cache");
				optRemoteChrome.addArguments("--dns-prefetch-disable");
				optRemoteChrome.addArguments("--log-level=3");
				optRemoteChrome.addArguments("--no-proxy-server");
				optRemoteChrome.addArguments("--no-sandbox");
				optRemoteChrome.addArguments("--silent");
				optRemoteChrome.addArguments("enable-automation");
				optRemoteChrome.setProxy(null);
				optRemoteChrome.addArguments("--window-size=1024,768");

				driver = new RemoteWebDriver(new URL(baseURLSeleniumGrip), optRemoteChrome);

				Thread.sleep(2000);
				Log.log("Window sizes " + driver.manage().window().getSize().toString());
			} catch (Exception e) {
				Log.log("Não foi possível conectar o selenium hub chrome");
				e.printStackTrace();
			}
			break;

		case "mobile-docker":

			try {
				DesiredCapabilities caps = new DesiredCapabilities();
				caps.setCapability("platformName", "Android");
				caps.setCapability("deviceName", "Android Emulator");
				caps.setCapability("automationName", "UIAutomator2");
				caps.setCapability("avd", "nexus_5_7.1.1");
				caps.setCapability("browserName", "android");
				String caminhoAPK = "/root/tmp/original.apk";
				Log.log(caminhoAPK);
				// passar o apk para ser instalado no momento da execução
				caps.setCapability("app", caminhoAPK);
				driver = new RemoteWebDriver(new URL(baseURLSeleniumGrip), caps);
				Thread.sleep(3000);
			} catch (Exception e) {
				e.printStackTrace();
				Log.log("Não foi possível conectar ao Docker-Hub Android");
			}
			break;
		}
		return driver;
	}

}
