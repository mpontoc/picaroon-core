package io.github.mpontoc.picaroon.core.elements;

import static io.github.mpontoc.picaroon.core.elements.ElementConstants.DOWN;
import static io.github.mpontoc.picaroon.core.elements.ElementConstants.LEFT;
import static io.github.mpontoc.picaroon.core.elements.ElementConstants.SCROLL_SCREEN;
import static io.github.mpontoc.picaroon.core.elements.ElementConstants.SWIPE_SCREEN;
import static io.github.mpontoc.picaroon.core.elements.ElementConstants.RIGHT;
import static io.github.mpontoc.picaroon.core.elements.ElementConstants.UP;
import static io.github.mpontoc.picaroon.core.utils.PropertiesVariables.ANDROID;
import static io.github.mpontoc.picaroon.core.utils.PropertiesVariables.MOBILE_PLATFORM;
import static java.time.Duration.ofMillis;

import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import io.github.mpontoc.picaroon.core.commands.ActionsCommands;
import io.github.mpontoc.picaroon.core.drivers.DriverFactory;
import io.github.mpontoc.picaroon.core.utils.Log;

public class MobileElementFunctions {

	private final static String START_POSITION_Y = "start_y";
	private final static String END_POSITION_Y = "end_y";
	private final static String START_POSITION_X = "start_x";
	private final static String END_POSITION_X = "end_x";
	private static boolean objFinded = false;

	@SuppressWarnings("rawtypes")
	public static void scrollDirection(String obj, String direction, int... qtdScroll) {

		HashMap<String, Integer> directionY = getDirection(direction);

		int qtdTotalScroll = 12;

		if (qtdScroll.length > 0) {
			qtdTotalScroll = qtdScroll[0];
		}

		Dimension size = DriverFactory.driver.manage().window().getSize();
		Log.log(size.toString());

		int x = size.width / 2;
		int start_y = (int) (size.height * directionY.get(START_POSITION_Y));
		int end_y = (int) (size.height * directionY.get(END_POSITION_Y));

		Log.log(x + " - " + start_y + " - " + end_y);

		int i = 0;

		while (objFinded == false) {

			if (i == qtdTotalScroll) {
				Log.log("Exceeded quantity to scroll of " + qtdTotalScroll);
				break;
			}

			if (objFinded == false && !obj.equals(SCROLL_SCREEN)) {
				objFinded = ActionsCommands.waitExist(obj, 1);
			}

			TouchAction actions = new TouchAction(DriverFactory.mobileDriver);
			actions.press(PointOption.point(x, start_y));
			actions.waitAction(WaitOptions.waitOptions(ofMillis(1000)));
			actions.moveTo(PointOption.point(x, end_y)).release().perform();
			i++;
		}
	}

	@SuppressWarnings("rawtypes")
	public static void swipeDirection(String obj, double percentScrenn, String direction, int... qtdSwipe) {

		HashMap<String, Integer> directionX = getDirection(direction);

		int qtdTotalSwipe = 6;

		if (qtdSwipe.length > 0) {
			qtdTotalSwipe = qtdSwipe[0];
		}

		Dimension size = DriverFactory.driver.manage().window().getSize();
		Log.log(size.toString());

		int y = (int) (size.height * percentScrenn);
		int start_x = (int) (size.width * directionX.get(START_POSITION_X));
		int end_x = (int) (size.width * directionX.get(START_POSITION_Y));

		Log.log(y + " - " + start_x + " - " + end_x);

		int i = 0;

		while (objFinded == false) {

			if (i == qtdTotalSwipe) {
				Log.log("Exceeded quantity to swipe of " + qtdTotalSwipe);
				break;
			}

			if (objFinded == false && !obj.equals(SWIPE_SCREEN)) {
				objFinded = ActionsCommands.waitExist(obj, 1);
			}

			TouchAction actions = new TouchAction(DriverFactory.mobileDriver);
			actions.press(PointOption.point(start_x, y));
			actions.waitAction(WaitOptions.waitOptions(ofMillis(1000)));
			actions.moveTo(PointOption.point(end_x, y)).release().perform();
			i++;
		}

	}

	private static HashMap<String, Integer> getDirection(final String direction) {

		HashMap<String, Integer> directionValues = new HashMap<String, Integer>();

		switch (direction) {

		case DOWN:

			directionValues.put(START_POSITION_Y, (int) 0.7);
			directionValues.put(END_POSITION_Y, (int) 0.4);
			break;

		case UP:

			directionValues.put(START_POSITION_Y, (int) 0.4);
			directionValues.put(END_POSITION_Y, (int) 0.7);
			break;

		case LEFT:
			
			directionValues.put(START_POSITION_X, (int) 0.1);
			directionValues.put(END_POSITION_X, (int) 0.9);
			break;

		case RIGHT:
			
			directionValues.put(START_POSITION_X, (int) 0.9);
			directionValues.put(END_POSITION_X, (int) 0.1);
			break;
		}
		
		return directionValues;
	}

	public static void scrollByText(String obj, String direction) {

		if (MOBILE_PLATFORM.equals(ANDROID)) {

			try {
				DriverFactory.driver.findElement(MobileBy.AndroidUIAutomator(
						"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""
								+ obj + "\").instance(" + 0 + "));"));
			} catch (Exception e) {
				Log.log("Cannot find the Element " + obj);
			}
		} else {
			RemoteWebElement element = (RemoteWebElement) DriverFactory.driver
					.findElement(By.className("XCUIElementTypeTable"));
			String elementID = ((RemoteWebElement) element).getId();
			HashMap<String, String> scrollObject = new HashMap<String, String>();
			scrollObject.put("element", elementID);
			scrollObject.put("direction", direction);
			((RemoteWebDriver) DriverFactory.driver).executeScript("mobile:scroll", scrollObject);
		}
	}
}
