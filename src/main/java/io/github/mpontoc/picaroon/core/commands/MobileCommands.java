package io.github.mpontoc.picaroon.core.commands;

import static io.github.mpontoc.picaroon.core.elements.ElementConstants.DOWN;
import static io.github.mpontoc.picaroon.core.elements.ElementConstants.LEFT;
import static io.github.mpontoc.picaroon.core.elements.ElementConstants.RIGHT;
import static io.github.mpontoc.picaroon.core.elements.ElementConstants.SCROLL_SCREEN;
import static io.github.mpontoc.picaroon.core.elements.ElementConstants.SWIPE_SCREEN;
import static io.github.mpontoc.picaroon.core.elements.ElementConstants.UP;
import static io.github.mpontoc.picaroon.core.elements.ElementFunctions.POSITION_ELEMENT;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.github.mpontoc.picaroon.core.drivers.DriverFactory;
import io.github.mpontoc.picaroon.core.elements.MobileElementFunctions;
import io.github.mpontoc.picaroon.core.utils.Functions;
import io.github.mpontoc.picaroon.core.utils.Log;

public class MobileCommands {

	public static void scrollDownUntilElement(String obj) {

		MobileElementFunctions.scrollDirection(obj, DOWN);
	}

	public static void scrollDownUntilElement(String[] obj) {

		MobileElementFunctions.scrollDirection(obj[POSITION_ELEMENT], DOWN);
	}

	public static void scrollDownUntilElement(String obj, int qtdScroll) {

		MobileElementFunctions.scrollDirection(obj, DOWN, qtdScroll);
	}

	public static void scrollDownUntilElement(String[] obj, int qtdScroll) {

		MobileElementFunctions.scrollDirection(obj[POSITION_ELEMENT], DOWN, qtdScroll);
	}

	public static void scrollUpUntilElement(String obj) {

		MobileElementFunctions.scrollDirection(obj, UP);
	}

	public static void scrollUpUntilElement(String[] obj) {

		MobileElementFunctions.scrollDirection(obj[POSITION_ELEMENT], UP);
	}

	public static void scrollUpUntilElement(String obj, int qtdScroll) {

		MobileElementFunctions.scrollDirection(obj, UP, qtdScroll);
	}

	public static void scrollUpUntilElement(String[] obj, int qtdScroll) {

		MobileElementFunctions.scrollDirection(obj[POSITION_ELEMENT], UP, qtdScroll);
	}

	public static void scrollDown(int qtdScroll) {

		MobileElementFunctions.scrollDirection(SCROLL_SCREEN, DOWN, qtdScroll);
	}

	public static void scrollUp(int qtdScroll) {

		MobileElementFunctions.scrollDirection(SCROLL_SCREEN, UP, qtdScroll);
	}

	public static void refreshScreen() {

		MobileElementFunctions.scrollDirection(SCROLL_SCREEN, UP, 1);
		Log.log("Refresh Screen done!");
	}

	public static void scrollDownUntilElementByText(String obj) {

		MobileElementFunctions.scrollByText(obj, DOWN);
	}

	public static void scrollDownUntilElementByText(String[] obj) {

		MobileElementFunctions.scrollByText(obj[POSITION_ELEMENT], DOWN);
	}

	public static void scrollUpUntilElementByText(String obj) {

		MobileElementFunctions.scrollByText(obj, UP);
	}

	public static void scrollUpUntilElementByText(String[] obj) {

		MobileElementFunctions.scrollByText(obj[POSITION_ELEMENT], UP);
	}

	public static void swipeLeftUntilElement(String obj, double percentScreen) {

		MobileElementFunctions.swipeDirection(obj, percentScreen, LEFT);
	}

	public static void swipeLeftUntilElement(String[] obj, double percentScreen) {

		MobileElementFunctions.swipeDirection(obj[POSITION_ELEMENT], percentScreen, LEFT);
	}

	public static void swipeLeft(double percentScreen, int qtd) {

		MobileElementFunctions.swipeDirection(SWIPE_SCREEN, percentScreen, LEFT, qtd);
	}

	public static void swipeRightUntilElement(String obj, double percentScreen) {

		MobileElementFunctions.swipeDirection(obj, percentScreen, RIGHT);
	}

	public static void swipeRightUntilElement(String[] obj, double percentScreen) {

		MobileElementFunctions.swipeDirection(obj[POSITION_ELEMENT], percentScreen, RIGHT);
	}

	public static void swipeRight(double percentScreen, int qtd) {

		MobileElementFunctions.swipeDirection(SWIPE_SCREEN, percentScreen, RIGHT, qtd);
	}

	public static void changeContextNativeOrWebview(String nativeOrWebview) {

		String currentContext = DriverFactory.mobileDriver.getContext().toString().toLowerCase();
		Log.log(currentContext);

		Map<String, String> driverContext = new HashMap<String, String>();
		Set<String> contextName = DriverFactory.mobileDriver.getContextHandles();
		Log.log(contextName.toString());

		if (!currentContext.contains(nativeOrWebview.toLowerCase())) {
			for (String contexts : contextName) {
				Log.log(contexts);
				if (contextName.contains("NATIVE_APP")) {
					driverContext.put("native", contextName.toString());
				} else if (contextName.contains("WEBVIEW")) {
					driverContext.put("webview", contextName.toString());
				}
				Functions.waitSeconds(2);
			}
			if (driverContext.containsKey(nativeOrWebview.toLowerCase())) {
				DriverFactory.mobileDriver.context(driverContext.get(nativeOrWebview.toLowerCase()));
				Log.log(DriverFactory.mobileDriver.getContext().toString());
			} else {
				Log.log("The context " + nativeOrWebview.toLowerCase() + " is not present on view!");
			}
		} else {
			Log.log("Are same context: " + currentContext + " - " + nativeOrWebview.toLowerCase());
		}

	}

}
