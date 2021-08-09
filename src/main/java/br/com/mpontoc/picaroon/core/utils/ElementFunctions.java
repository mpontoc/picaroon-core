package br.com.mpontoc.picaroon.core.utils;

import static br.com.mpontoc.picaroon.core.drivers.DriverFactory.deviceElement;
import static br.com.mpontoc.picaroon.core.drivers.DriverFactory.driver;
import static br.com.mpontoc.picaroon.core.drivers.DriverFactory.executor;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import br.com.mpontoc.picaroon.core.commands.ActionsCommands;
import br.com.mpontoc.picaroon.core.drivers.MobileDriverInit;
import br.com.mpontoc.picaroon.core.mobile.Mobile;
import io.appium.java_client.MobileBy;

public class ElementFunctions {
	
	public static String tratativaReportElemento(String[] elemento) {
		String nomeObjMapeado = null;
		if (elemento.length > 2) {
			nomeObjMapeado = elemento[2];
		} else {
			nomeObjMapeado = elemento[deviceElement];
		}
		return nomeObjMapeado;
	}

	private static List<By> listTypeBy(String obj) {
		List<By> byType = new ArrayList<By>();

		if (obj.contains("//")) {

			byType.add(By.xpath(obj));

		} else {

			if (Prop.getProp("browserOrDevice").toLowerCase().contains("mobile")
					&& Mobile.getPlataforma().contains("ios")) {
				byType.add(MobileBy.AccessibilityId(obj));
				byType.add(By.xpath("//*[@label='" + obj + "']"));
				byType.add(By.xpath("//*[@name='" + obj + "']"));
				byType.add(By.xpath("//*[contains(@label,'" + obj + "')]"));
				byType.add(By.xpath("//*[contains(@name,'" + obj + "')]"));
			} else if (Prop.getProp("browserOrDevice").toLowerCase().contains("mobile")
					&& Mobile.getPlataforma().contains("android")) {
				byType.add(By.id(MobileDriverInit.driverMobile.getCapabilities().getCapability("appPackage").toString()
						+ ":id/" + obj));
				byType.add(By.id(obj));
				byType.add(By.xpath("//*[@text='" + obj + "']"));
				byType.add(By.xpath("//*[contains(@content-desc,'" + obj + "')]"));
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
				if (!Prop.getProp("browserOrDevice").toLowerCase().contains("mobile")) {
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
		if (!Prop.getProp("browserOrDevice").toLowerCase().equals("mobile")) {
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
					ActionsCommands.cucumberWriteReport(acao);
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
