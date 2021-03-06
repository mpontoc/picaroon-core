package io.github.mpontoc.picaroon.core.utils;

import static io.github.mpontoc.picaroon.core.drivers.DriverFactory.driver;
import static io.github.mpontoc.picaroon.core.drivers.DriverFactory.executor;
import static io.github.mpontoc.picaroon.core.utils.ElementConstants.CLICK;
import static io.github.mpontoc.picaroon.core.utils.ElementConstants.CLICK_AND_PERFORM;
import static io.github.mpontoc.picaroon.core.utils.ElementConstants.GET_ELEMENTS;
import static io.github.mpontoc.picaroon.core.utils.ElementConstants.GET_STRING_ELEMENTS;
import static io.github.mpontoc.picaroon.core.utils.ElementConstants.GET_TEXT;
import static io.github.mpontoc.picaroon.core.utils.ElementConstants.SET;
import static io.github.mpontoc.picaroon.core.utils.ElementConstants.WAIT;
import static io.github.mpontoc.picaroon.core.utils.PropertiesVariables.BROWSER_OR_MOBILE;
import static io.github.mpontoc.picaroon.core.utils.PropertiesVariables.COLOR_BACKGROUND;
import static io.github.mpontoc.picaroon.core.utils.PropertiesVariables.PLATFORM;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import io.appium.java_client.MobileBy;
import io.github.mpontoc.picaroon.core.drivers.DriverFactory;
import io.github.mpontoc.picaroon.core.mobile.Mobile;

public class ElementFunctions {

	public static Integer positionElement = null;

	private static List<WebElement> listElements = null;
	private static WebElement element = null;
	private static String textoObtido = "";

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
			if (PropertiesVariables.BROWSER_OR_MOBILE.contains("mobile") && Mobile.getPlataforma().contains("ios")) {
				byType.add(MobileBy.AccessibilityId(obj));
				byType.add(By.xpath("//*[@label='" + obj + "']"));
				byType.add(By.xpath("//*[@name='" + obj + "']"));
				byType.add(By.xpath("//*[contains(@label,'" + obj + "')]"));
				byType.add(By.xpath("//*[contains(@name,'" + obj + "')]"));
			} else if (PropertiesVariables.BROWSER_OR_MOBILE.contains("mobile")
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

		WebElement elementFindBy = null;

		for (By by : listTypeBy(obj)) {
			try {
				elementFindBy = driver.findElement(by);
				if (!PropertiesVariables.BROWSER_OR_MOBILE.contains("mobile")) {
					if (elementFindBy.isDisplayed() == true) {
						borderStyle(elementFindBy);
						Log.log("Element located by '" + by.toString());
						return elementFindBy;
					} else {
						return elementFindBy = null;
					}
				} else if (elementFindBy != null) {
					Log.log("Element located by '" + by.toString());
					return elementFindBy;
				} else {
					return elementFindBy = null;
				}
			} catch (Exception e) {
			}
		}
		return elementFindBy;
	}

	private static List<WebElement> findByElements(String obj) {

		listElements = null;

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

	private static void borderStyle(WebElement element) {
		if (!PropertiesVariables.BROWSER_OR_MOBILE.equals("mobile")) {
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

	public static Boolean localizaElemento(String obj, Integer timeout, String acao, String[] objList,
			String... textoSet) {

		setElement(null);

		for (int i = 1; i <= timeout; i++) {
			setElement(findBy(obj));
			if (getElement() != null) {
				if (!PropertiesVariables.BROWSER_OR_MOBILE.equals("mobile")) {
					executor.executeScript("arguments[0].setAttribute('style','border: solid 1px blue');", element);
				}
				if (!acao.equals(WAIT)) {
					acaoElemento(acao, obj, textoSet);
				}
				return true;
			} else {
				retornLogTentativa(obj, timeout, objList, i);
			}
		}
		return false;
	}

	private static void acaoElemento(String acao, String obj, String... textoSet) {

		setTextoObtido("");

		if (acao.equals(CLICK)) {
			getElement().click();
			Log.log("Element '" + obj + "' located and clicked");
		} else if (acao.equals(CLICK_AND_PERFORM)) {
			Actions actions = new Actions(driver);
			actions.moveToElement(getElement());
			actions.click();
			actions.perform();
			Log.log("Element '" + obj + "' located and clicked by perform");
		} else if (acao.equals(SET)) {
			getElement().sendKeys(textoSet[0]);
			Log.log("Element '" + obj + "' located and set with content " + textoSet[0]);
		} else if (acao.equals(GET_TEXT)) {
			if (BROWSER_OR_MOBILE.contains("mobile") && PLATFORM.toLowerCase().equals("ios")) {
				try {
					setTextoObtido(getElement().getAttribute("label").toString());
				} catch (Exception e) {
					setTextoObtido(getElement().getAttribute("name").toString());
				}
			} else {
				setTextoObtido(getElement().getText().toString());
			}
			if (getTextoObtido().length() > 3) {
				Log.log("Element '" + obj + "' located");
				if (!BROWSER_OR_MOBILE.contains("mobile") && COLOR_BACKGROUND.equals("true")) {
					executor.executeScript("arguments[0].style.backgroundColor = 'yellow';", getElement());
				}
				Log.log("Text caught [ '" + getTextoObtido() + "' ]");
				Report.cucumberWriteReport("Text caught [ '" + getTextoObtido() + "' ]");
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getElements(String obj, Integer timeout, String[] objListElements, String typeGetElements) {

		listElements = null;
		String[] listStringElements = null;

		for (int i = 1; i <= timeout; i++) {
			listElements = findByElements(obj);
			listStringElements = new String[listElements.size()];

			if (listElements != null) {

				int index = 0;

				for (WebElement elemento : listElements) {
					Log.log(elemento.getText());
					listStringElements[index] = elemento.getText();
					index++;
				}

				Log.log("Elements '" + obj + "' located");

				if (typeGetElements.equals(GET_ELEMENTS))
					return (T) listElements;
				else if (typeGetElements.equals(GET_STRING_ELEMENTS))
					return (T) listStringElements;
			} else
				retornLogTentativa(obj, timeout, objListElements, i);
		}
		return null;
	}

	private static void retornLogTentativa(String obj, Integer timeout, String[] objListElements, int i) {
		if (objListElements != null) {
			Log.log("Cannot find the Element '" + tratativaReportElemento(objListElements) + "' times " + i + " of "
					+ timeout);
			Functions.waitSeconds(1);
		} else {
			Log.log("Cannot find the Element '" + obj + "' times " + i + " of " + timeout);
			Functions.waitSeconds(1);
		}
	}

	/**
	 * @return the element
	 */
	public static WebElement getElement() {
		return element;
	}

	/**
	 * @param element the element to set
	 */
	public static void setElement(WebElement element) {
		ElementFunctions.element = element;
	}

	/**
	 * @return the textoObtido
	 */
	public static String getTextoObtido() {
		return textoObtido;
	}

	/**
	 * @param textoObtido the textoObtido to set
	 */
	public static void setTextoObtido(String textoObtido) {
		ElementFunctions.textoObtido = textoObtido;
	}

}
