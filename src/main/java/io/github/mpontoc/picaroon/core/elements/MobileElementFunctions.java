package io.github.mpontoc.picaroon.core.elements;

import static io.github.mpontoc.picaroon.core.elements.ElementConstants.DOWN;
import static io.github.mpontoc.picaroon.core.elements.ElementConstants.LEFT;
import static io.github.mpontoc.picaroon.core.elements.ElementConstants.RIGHT;
import static io.github.mpontoc.picaroon.core.elements.ElementConstants.SCROLL_SCREEN;
import static io.github.mpontoc.picaroon.core.elements.ElementConstants.SWIPE_SCREEN;
import static io.github.mpontoc.picaroon.core.elements.ElementConstants.UP;
import static io.github.mpontoc.picaroon.core.utils.PropertiesVariables.ANDROID;
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
import io.github.mpontoc.picaroon.core.mobile.Mobile;
import io.github.mpontoc.picaroon.core.utils.Log;

public class MobileElementFunctions {

	private final static String START_POSITION_Y = "start_y";
	private final static String END_POSITION_Y = "end_y";
	private final static String START_POSITION_X = "start_x";
	private final static String END_POSITION_X = "end_x";
	private static boolean objFound = false;

	@SuppressWarnings("rawtypes")
	public static void scrollDirection(String obj, String direction, int... qtdScroll) {

		HashMap<String, Float> directionY = getDirection(direction);

		int qtdTotalScroll = 12;

		if (qtdScroll.length > 0) {
			qtdTotalScroll = qtdScroll[0];
		}

		Dimension size = DriverFactory.driver.manage().window().getSize();
		Log.log(size.toString());

		float x = size.width / 2;
		float start_y = (float) (size.height * directionY.get(START_POSITION_Y));
		float end_y = (float) (size.height * directionY.get(END_POSITION_Y));

		Log.log(x + " - " + start_y + " - " + end_y);

		int i = 0;

		while (objFound == false) {

			if (i == qtdTotalScroll) {
				Log.log("Exceeded quantity to scroll of " + qtdTotalScroll);
				break;
			}

			if (objFound == false && !obj.equals(SCROLL_SCREEN)) {
				objFound = ActionsCommands.waitExist(obj, 1);
			}

			TouchAction actions = new TouchAction(DriverFactory.mobileDriver);
			actions.press(PointOption.point((int) x, (int) start_y));
			actions.waitAction(WaitOptions.waitOptions(ofMillis(1000)));
			actions.moveTo(PointOption.point((int) x, (int) end_y)).release().perform();
			i++;
		}
	}

	@SuppressWarnings("rawtypes")
	public static void swipeDirection(String obj, double percentScreen, String direction, int... qtdSwipe) {

		HashMap<String, Float> directionX = getDirection(direction);

		int qtdTotalSwipe = 6;

		if (qtdSwipe.length > 0) {
			qtdTotalSwipe = qtdSwipe[0];
		}

		Dimension size = DriverFactory.driver.manage().window().getSize();
		Log.log(size.toString());

		float y = (float) (size.height * percentScreen);
		float start_x = (float) (size.width * directionX.get(START_POSITION_X));
		float end_x = (float) (size.width * directionX.get(START_POSITION_Y));

		Log.log(y + " - " + start_x + " - " + end_x);

		int i = 0;

		while (!objFound) {

			if (i == qtdTotalSwipe) {
				Log.log("Exceeded quantity to swipe of " + qtdTotalSwipe);
				break;
			}

			if (!objFound && !obj.equals(SWIPE_SCREEN)) {
				objFound = ActionsCommands.waitExist(obj, 1);
			}

			TouchAction actions = new TouchAction(DriverFactory.mobileDriver);
			actions.press(PointOption.point((int) start_x, (int) y));
			actions.waitAction(WaitOptions.waitOptions(ofMillis(1000)));
			actions.moveTo(PointOption.point((int) end_x, (int) y)).release().perform();
			i++;
		}

	}

	private static HashMap<String, Float> getDirection(final String direction) {

		HashMap<String, Float> directionValues = new HashMap<String, Float>();

		switch (direction) {

		case DOWN:

			directionValues.put(START_POSITION_Y, (float) 0.7);
			directionValues.put(END_POSITION_Y, (float) 0.4);
			break;

		case UP:

			directionValues.put(START_POSITION_Y, (float) 0.4);
			directionValues.put(END_POSITION_Y, (float) 0.7);
			break;

		case LEFT:
			
			directionValues.put(START_POSITION_X, (float) 0.1);
			directionValues.put(END_POSITION_X, (float) 0.9);
			break;

		case RIGHT:
			
			directionValues.put(START_POSITION_X, (float) 0.9);
			directionValues.put(END_POSITION_X, (float) 0.1);
			break;
		}
		
		return directionValues;
	}

	public static void scrollByText(String obj, String direction) {

		if (Mobile.getPlataforma().equals(ANDROID)) {

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
