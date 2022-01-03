package io.github.mpontoc.picaroon.core.utils;

import static io.github.mpontoc.picaroon.core.constants.ElementConstants.CLICK;
import static io.github.mpontoc.picaroon.core.constants.ElementConstants.CLICK_AND_PERFORM;
import static io.github.mpontoc.picaroon.core.constants.ElementConstants.GET_TEXT;
import static io.github.mpontoc.picaroon.core.constants.ElementConstants.SET;
import static io.github.mpontoc.picaroon.core.constants.ElementConstants.WAIT;
import static io.github.mpontoc.picaroon.core.constants.PropertiesConstants.BROWSER_OR_MOBILE;
import static io.github.mpontoc.picaroon.core.constants.PropertiesConstants.COLOR_BACKGROUND;
import static io.github.mpontoc.picaroon.core.constants.PropertiesConstants.PLATFORM;
import static io.github.mpontoc.picaroon.core.drivers.DriverFactory.driver;
import static io.github.mpontoc.picaroon.core.drivers.DriverFactory.executor;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import io.appium.java_client.MobileBy;
import io.github.mpontoc.picaroon.core.constants.PropertiesConstants;
import io.github.mpontoc.picaroon.core.drivers.DriverFactory;
import io.github.mpontoc.picaroon.core.mobile.Mobile;

public class ElementFunctions {

	public static Integer positionElement = null;

	public static WebElement element = null;
	private static String obj = null;
	public static String[] setObjList = null;
	public static String textoObtido = "";
	public static List<WebElement> listElements = null;
	public static String[] elements = null;

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

		if (Mobile.getApp() != null) {
			if (elemento.length > 2) {
				nomeObjMapeado = elemento[2];
			} else {
				nomeObjMapeado = elemento[positionElement];
			}
		} else {
			if (elemento.length > 1) {
				nomeObjMapeado = elemento[1];
			} else {
				nomeObjMapeado = elemento[positionElement];
			}
		}
		return nomeObjMapeado;
	}

	private static List<By> listTypeBy(String obj) {

		List<By> byType = new ArrayList<By>();

		if (obj.contains("//")) {
			byType.add(By.xpath(obj));
		} else {
			if (PropertiesConstants.BROWSER_OR_MOBILE.contains("mobile") && Mobile.getPlataforma().contains("ios")) {
				byType.add(MobileBy.AccessibilityId(obj));
				byType.add(By.xpath("//*[@label='" + obj + "']"));
				byType.add(By.xpath("//*[@name='" + obj + "']"));
				byType.add(By.xpath("//*[contains(@label,'" + obj + "')]"));
				byType.add(By.xpath("//*[contains(@name,'" + obj + "')]"));
			} else if (PropertiesConstants.BROWSER_OR_MOBILE.contains("mobile")
					&& Mobile.getPlataforma().contains("android")) {
				byType.add(By.id(DriverFactory.mobileDriver.getCapabilities().getCapability("appPackage").toString()
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
				if (!PropertiesConstants.BROWSER_OR_MOBILE.contains("mobile")) {
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
		if (!PropertiesConstants.BROWSER_OR_MOBILE.equals("mobile")) {
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

	public static Boolean localizaElemento(String obj, Integer timeout, String acao, String... textoSet) {

		ElementFunctions.obj = obj;

		for (int i = 1; i <= timeout; i++) {
			element = ElementFunctions.findBy(obj);
			if (element != null) {
				if (!PropertiesConstants.BROWSER_OR_MOBILE.equals("mobile")) {
					executor.executeScript("arguments[0].setAttribute('style','border: solid 1px blue');", element);
				}
				if (!acao.equals(WAIT)) {
					acaoElemento(acao, textoSet);
				}
				break;
			} else {
				if (setObjList != null) {
					Log.log("Cannot find the Element '" + ElementFunctions.tratativaReportElemento(setObjList)
							+ "' times " + i + " of " + timeout);
					Functions.waitSeconds(1);
				} else {
					Log.log("Cannot find the Element '" + obj + "' times " + i + " of " + timeout);
					Functions.waitSeconds(1);
				}
			}
		}
		return false;
	}

	public static Boolean getElements(String obj, Integer timeout) {

		ElementFunctions.obj = obj;

		listElements = null;
		for (int i = 1; i <= timeout; i++) {
			listElements = ElementFunctions.findByElements(obj);
			elements = new String[listElements.size()];
			if (listElements != null) {
				int index = 0;
				for (WebElement elemento : listElements) {
					Log.log(elemento.getText());
					elements[index] = elemento.getText();
					index++;
				}
				Log.log("Elements '" + ElementFunctions.obj + "' located");
				break;
			} else {
				if (setObjList != null) {
					Log.log("Cannot find the Element '" + ElementFunctions.tratativaReportElemento(setObjList)
							+ "' times " + i + " of " + timeout);
					Functions.waitSeconds(1);
				} else {
					if (setObjList != null) {
						Log.log("Cannot find the Element '" + ElementFunctions.tratativaReportElemento(setObjList)
								+ "' times " + i + " of " + timeout);
						Functions.waitSeconds(1);
					} else {
						Log.log("Cannot find the Element '" + obj + "' times " + i + " of " + timeout);
						Functions.waitSeconds(1);
					}
				}
			}
		}
		return false;
	}

	public static void acaoElemento(String acao, String... textoSet) {

		if (acao.equals(CLICK)) {
			ElementFunctions.element.click();
			Log.log("Element '" + obj + "' located and clicked");
		} else if (acao.equals(CLICK_AND_PERFORM)) {
			Actions actions = new Actions(driver);
			actions.moveToElement(ElementFunctions.element);
			actions.click();
			actions.perform();
			Log.log("Element '" + ElementFunctions.obj + "' located and clicked by perform");
		} else if (acao.equals(SET)) {
			ElementFunctions.element.sendKeys(textoSet);
			Log.log("Element '" + ElementFunctions.obj + "' located and set with content " + textoSet);
		} else if (acao.equals(GET_TEXT)) {
			if (BROWSER_OR_MOBILE.contains("mobile") && PLATFORM.toLowerCase().equals("ios")) {
				try {
					ElementFunctions.textoObtido = ElementFunctions.element.getAttribute("label").toString();
				} catch (Exception e) {
					ElementFunctions.textoObtido = ElementFunctions.element.getAttribute("name").toString();
				}
			} else {
				ElementFunctions.textoObtido = ElementFunctions.element.getText().toString();
			}
			if (ElementFunctions.textoObtido.length() > 3) {
				Log.log("Element '" + ElementFunctions.obj + "' located");
				if (!BROWSER_OR_MOBILE.contains("mobile") && COLOR_BACKGROUND.equals("true")) {
					executor.executeScript("arguments[0].style.backgroundColor = 'yellow';", element);
				}
				Log.log("Text caught [ '" + ElementFunctions.textoObtido + "' ]");
				Report.cucumberWriteReport("Text caught [ '" + ElementFunctions.textoObtido + "' ]");
			}
		}
	}

}
