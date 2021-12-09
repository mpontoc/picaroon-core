package io.github.mpontoc.picaroon.core.utils;

import static io.github.mpontoc.picaroon.core.drivers.DriverFactory.driver;
import static io.github.mpontoc.picaroon.core.drivers.DriverFactory.executor;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.appium.java_client.MobileBy;
import io.github.mpontoc.picaroon.core.drivers.DriverFactory;
import io.github.mpontoc.picaroon.core.mobile.Mobile;

public class ElementFunctions {

	public static Integer positionElement = null;

	/*
	 * set the device for position of list string ----- position 0 is android -----
	 * position 1 is ios case not mobile position 0 to web
	 */
	public static void setupElement() {

		if (Mobile.getPlataforma() != null) {
			if (Mobile.getPlataforma().equals("android"))
				// android
				positionElement = 0;
			else
				// ios
				positionElement = 1;
		} else {
			// web
			positionElement = 0;
		}
	}

	public static String tratativaReportElemento(String[] elemento) {
		String nomeObjMapeado = null;
		if (elemento.length > 2) {
			nomeObjMapeado = elemento[2];
		} else {
			nomeObjMapeado = elemento[positionElement];
		}
		return nomeObjMapeado;
	}

	private static List<By> listTypeBy(String obj) {
		List<By> byType = new ArrayList<By>();

		if (obj.contains("//")) {

			byType.add(By.xpath(obj));

		} else {

			if (Prop.getProp("browserOrMobile").toLowerCase().contains("mobile")
					&& Mobile.getPlataforma().contains("ios")) {
				byType.add(MobileBy.AccessibilityId(obj));
				byType.add(By.xpath("//*[@label='" + obj + "']"));
				byType.add(By.xpath("//*[@name='" + obj + "']"));
				byType.add(By.xpath("//*[contains(@label,'" + obj + "')]"));
				byType.add(By.xpath("//*[contains(@name,'" + obj + "')]"));
			} else if (Prop.getProp("browserOrMobile").toLowerCase().contains("mobile")
					&& Mobile.getPlataforma().contains("android")) {
				byType.add(
						By.id(DriverFactory.mobileDriver.getCapabilities().getCapability("appPackage").toString()
								+ ":id/" + obj));
				byType.add(By.id(obj));
				byType.add(By.xpath("//*[@text='" + obj + "']"));
				byType.add(By.xpath("//*[contains(@content-desc,'" + obj + "')]"));
				byType.add(By.className(obj));
			} else {
				byType.add(By.xpath("//*[contains(text,'" + obj + "')]"));
				byType.add(By.xpath("//*[@id='" + obj + "']"));
				byType.add(By.xpath("//*[@class='" + obj + "']"));
			}
			byType.add(By.xpath("//*[contains(.,'" + obj + "')]"));
		}

		return byType;
	}

	public static WebElement findBy(String obj) {

		WebElement element = null;

		for (By by : listTypeBy(obj)) {
			try {
				element = driver.findElement(by);
				if (!Prop.getProp("browserOrMobile").toLowerCase().contains("mobile")) {
					if (element.isDisplayed() == true) {
						borderStyle(element);
						Log.log("Element located by '" + by.toString());
						return element;
					} else {
						return element = null;
					}
				} else if (element != null) {
					Log.log("Element located by '" + by.toString());
					return element;
				} else {
					return element = null;
				}
			} catch (Exception e) {
			}
		}
		return element;
	}

	public static List<WebElement> findByElements(String obj) {

		List<WebElement> listElements = null;

		for (By by : listTypeBy(obj)) {
			try {
				listElements = driver.findElements(by);
				Log.log("Element located by '" + by.toString());
				return listElements;
			} catch (Exception e) {
			}
		}
		return listElements;
	}

	public static void borderStyle(WebElement element) {
		if (!Prop.getProp("browserOrMobile").toLowerCase().equals("mobile")) {
			try {
				executor.executeScript("arguments[0].setAttribute('style','border: solid 1px red');", element);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void validaElemento(String obj, Boolean[] assertObjReceved, Boolean located) {
		String acao = null;
		try {
			if (assertObjReceved[0] == true) {
				if (located == true) {
					acao = "Mandatory action with the element '" + obj + "' successfully";
					Log.log(acao);
					Report.cucumberWriteReport(acao);
				} else
					Log.log("There was a problem with the element '" + obj + "'");
				Assert.assertTrue(located);
			}
		} catch (Exception e1) {
			if (located != true)
				Log.log("Element '" + obj + "' not located");
		}

	}

}
