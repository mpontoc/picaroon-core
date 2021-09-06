package br.com.mpontoc.picaroon.core.commands;

import static java.time.Duration.ofMillis;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

import br.com.mpontoc.picaroon.core.drivers.DriverFactory;
import br.com.mpontoc.picaroon.core.drivers.MobileDriverInit;
import br.com.mpontoc.picaroon.core.mobile.Mobile;
import br.com.mpontoc.picaroon.core.utils.Log;
import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class MobileCommands {

	@SuppressWarnings("unchecked")
	public static AndroidDriver<AndroidElement> androidDriver = (AndroidDriver<AndroidElement>) DriverFactory.driver;

	public static void pressKeyAndroid(String key) {

		AndroidKey command = null;

		if (key.equals("home")) {
			command = AndroidKey.HOME;
		} else if (key.equals("menu")) {
			command = AndroidKey.MENU;
		}
		try {
			MobileCommands.androidDriver.pressKey(new KeyEvent(command));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void scrollUntilElement(String obj) {

		Dimension size = DriverFactory.driver.manage().window().getSize();
		Log.log(size.toString());

		int x = size.width / 2;
		int start_y = (int) (size.height * 0.7);
		int end_y = (int) (size.height * 0.4);
		int i = 0;

		Log.log(x + " - " + start_y + " - " + end_y);

		boolean objFinded = ActionsCommands.waitExist(obj, 1);

		if (objFinded == false) {
			while (objFinded == false) {
				TouchAction actions = new TouchAction(MobileDriverInit.driverMobile);
				actions.press(PointOption.point(x, start_y));
				actions.waitAction(WaitOptions.waitOptions(ofMillis(1000)));
				actions.moveTo(PointOption.point(x, end_y)).release().perform();
				objFinded = ActionsCommands.waitExist(obj, 1);
				i++;
				if (i == 13)
					break;
			}
		} else
			Log.log("Objeto não localizado na tela " + obj);
	}

	public static void scrollUntilElement(String obj, int qtdScroll) {

		Dimension size = DriverFactory.driver.manage().window().getSize();
		Log.log(size.toString());

		int x = size.width / 2;
		int start_y = (int) (size.height * 0.7);
		int end_y = (int) (size.height * 0.5);
		int i = 0;

		Log.log(x + " - " + start_y + " - " + end_y);

		boolean objFinded = ActionsCommands.waitExist(obj, 1);

		if (objFinded == false) {
			while (objFinded == false) {
				if (i == qtdScroll) {
					Log.log("Quantidade Excedida e obj não localizado");
					break;
				}
				TouchAction actions = new TouchAction(MobileDriverInit.driverMobile);
				actions.press(PointOption.point(x, start_y));
				actions.waitAction(WaitOptions.waitOptions(ofMillis(1000)));
				actions.moveTo(PointOption.point(x, end_y)).release().perform();
				objFinded = ActionsCommands.waitExist(obj, 1);
				i++;
			}
		}
	}

	public static void scrollUntilElementUp(String obj) {

		Dimension size = DriverFactory.driver.manage().window().getSize();
		Log.log(size.toString());

		int x = size.width / 2;
		int start_y = (int) (size.height * 0.5);
		int end_y = (int) (size.height * 0.7);
		int i = 0;

		Log.log(x + " - " + start_y + " - " + end_y);

		boolean objFinded = ActionsCommands.waitExist(obj, 1);

		if (objFinded == false) {
			while (objFinded == false) {
				TouchAction actions = new TouchAction(MobileDriverInit.driverMobile);
				actions.press(PointOption.point(x, start_y));
				actions.waitAction(WaitOptions.waitOptions(ofMillis(1000)));
				actions.moveTo(PointOption.point(x, end_y)).release().perform();
				objFinded = ActionsCommands.waitExist(obj, 1);
				i++;
				if (i == 13)
					break;
			}
		} else
			Log.log("Objeto não localizado na tela " + obj);
	}

	public static void scrollUntilElementUp(String obj, int qtdScroll) {

		Dimension size = DriverFactory.driver.manage().window().getSize();
		Log.log(size.toString());

		int x = size.width / 2;
		int start_y = (int) (size.height * 0.5);
		int end_y = (int) (size.height * 0.7);
		int i = 0;

		Log.log(x + " - " + start_y + " - " + end_y);

		boolean objFinded = ActionsCommands.waitExist(obj, 1);

		if (objFinded == false) {
			while (objFinded == false) {
				if (ActionsCommands.waitExist(obj, 1) == false) {
					TouchAction actions = new TouchAction(MobileDriverInit.driverMobile);
					actions.press(PointOption.point(x, start_y));
					actions.waitAction(WaitOptions.waitOptions(ofMillis(1000)));
					actions.moveTo(PointOption.point(x, end_y)).release().perform();
					objFinded = ActionsCommands.waitExist(obj, 1);
					i++;
				} else if (ActionsCommands.waitExist(obj, 1) == true) {
					objFinded = true;
					break;
				}

				if (i == qtdScroll)
					break;
			}
		} else
			Log.log("Objeto não localizado na tela " + obj);
	}

	public static void scrollUntilElementByText(String obj) {
		if (Mobile.getPlataforma().equals("android")) {

			try {
				DriverFactory.driver.findElement(MobileBy.AndroidUIAutomator(
						"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""
								+ obj + "\").instance(" + 0 + "));"));
			} catch (Exception e) {
				Log.log("Objeto não localizado na tela " + obj);
			}
		} else {
			RemoteWebElement element = (RemoteWebElement) DriverFactory.driver
					.findElement(By.className("XCUIElementTypeTable"));
			String elementID = ((RemoteWebElement) element).getId();
			HashMap<String, String> scrollObject = new HashMap<String, String>();
			scrollObject.put("element", elementID);
			scrollObject.put("direction", "down");
			((RemoteWebDriver) DriverFactory.driver).executeScript("mobile:scroll", scrollObject);
		}
	}

	public static void scrollUntilElementByTextUp(String obj) {
		if (Mobile.getPlataforma().equals("android")) {

			try {
				DriverFactory.driver.findElement(MobileBy.AndroidUIAutomator(
						"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""
								+ obj + "\").instance(" + 0 + "));"));
			} catch (Exception e) {
				Log.log("Objeto não localizado na tela " + obj);
			}
		} else {
			RemoteWebElement element = (RemoteWebElement) DriverFactory.driver
					.findElement(By.className("XCUIElementTypeTable"));
			String elementID = ((RemoteWebElement) element).getId();
			HashMap<String, String> scrollObject = new HashMap<String, String>();
			scrollObject.put("element", elementID);
			scrollObject.put("direction", "up");
			((RemoteWebDriver) DriverFactory.driver).executeScript("mobile:scroll", scrollObject);
		}
	}

	public static void refreshTela() {

		Dimension size = DriverFactory.driver.manage().window().getSize();
		Log.log(size.toString());

		int x = size.width / 2;
		int start_y = (int) (size.height * 0.4);
		int end_y = (int) (size.height * 0.6);

		Log.log(x + " - " + start_y + " - " + end_y);

		TouchAction actions = new TouchAction(MobileDriverInit.driverMobile);
		actions.press(PointOption.point(x, start_y));
		actions.waitAction(WaitOptions.waitOptions(ofMillis(1000)));
		actions.moveTo(PointOption.point(x, end_y)).release().perform();

	}

	public static void swipeUntilElementLeft(String obj, double percentualTela) {

		Dimension size = DriverFactory.driver.manage().window().getSize();
		Log.log(size.toString());

		int y = (int) (size.height * percentualTela);
		int start_x = (int) (size.width * 0.1);
		int end_x = (int) (size.width * 0.9);
		int i = 0;

		Log.log(y + " - " + start_x + " - " + end_x);

		boolean objFinded = ActionsCommands.waitExist(obj, 1);

		if (objFinded == false) {
			while (objFinded == false) {
				TouchAction actions = new TouchAction(MobileDriverInit.driverMobile);
				actions.press(PointOption.point(start_x, y));
				actions.waitAction(WaitOptions.waitOptions(ofMillis(1000)));
				actions.moveTo(PointOption.point(end_x, y)).release().perform();
				objFinded = ActionsCommands.waitExist(obj, 1);
				i++;
				if (i == 13)
					break;
			}
		} else
			Log.log("Objeto não localizado na tela " + obj);
	}

	public static void swipeUntilElementLeft(double percentualTela, int qtd) {

		Dimension size = DriverFactory.driver.manage().window().getSize();
		Log.log(size.toString());

		int y = (int) (size.height * percentualTela);
		int start_x = (int) (size.width * 0.1);
		int end_x = (int) (size.width * 0.9);
		int i = 0;

		Log.log(y + " - " + start_x + " - " + end_x);
		while (i <= qtd) {
			TouchAction actions = new TouchAction(MobileDriverInit.driverMobile);
			actions.press(PointOption.point(start_x, y));
			actions.waitAction(WaitOptions.waitOptions(ofMillis(1000)));
			actions.moveTo(PointOption.point(end_x, y)).release().perform();
			i++;
		}
	}
	
	public static void swipeUntilElementRight(String obj, double percentualTela) {

		Dimension size = DriverFactory.driver.manage().window().getSize();
		Log.log(size.toString());

		int y = (int) (size.height * percentualTela);
		int start_x = (int) (size.width * 0.9);
		int end_x = (int) (size.width * 0.1);
		int i = 0;

		Log.log(y + " - " + start_x + " - " + end_x);

		boolean objFinded = ActionsCommands.waitExist(obj, 1);

		if (objFinded == false) {
			while (objFinded == false) {
				TouchAction actions = new TouchAction(MobileDriverInit.driverMobile);
				actions.press(PointOption.point(start_x, y));
				actions.waitAction(WaitOptions.waitOptions(ofMillis(1000)));
				actions.moveTo(PointOption.point(end_x, y)).release().perform();
				objFinded = ActionsCommands.waitExist(obj, 1);
				i++;
				if (i == 13)
					break;
			}
		} else
			Log.log("Objeto não localizado na tela " + obj);
	}
	
	public static void swipeUntilElementRight(double percentualTela, int qtd) {

		Dimension size = DriverFactory.driver.manage().window().getSize();
		Log.log(size.toString());

		int y = (int) (size.height * percentualTela);
		int start_x = (int) (size.width * 0.9);
		int end_x = (int) (size.width * 0.1);
		int i = 0;

		Log.log(y + " - " + start_x + " - " + end_x);
		while (i <= qtd) {
			TouchAction actions = new TouchAction(MobileDriverInit.driverMobile);
			actions.press(PointOption.point(start_x, y));
			actions.waitAction(WaitOptions.waitOptions(ofMillis(1000)));
			actions.moveTo(PointOption.point(end_x, y)).release().perform();
			i++;
		}
	}
}
