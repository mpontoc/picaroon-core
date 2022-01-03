package io.github.mpontoc.picaroon.core.commands;

import static io.github.mpontoc.picaroon.core.constants.ElementConstants.CLICK;
import static io.github.mpontoc.picaroon.core.constants.ElementConstants.CLICK_AND_PERFORM;
import static io.github.mpontoc.picaroon.core.constants.ElementConstants.GET_TEXT;
import static io.github.mpontoc.picaroon.core.constants.ElementConstants.SET;
import static io.github.mpontoc.picaroon.core.constants.ElementConstants.WAIT;
import static io.github.mpontoc.picaroon.core.utils.ElementFunctions.positionElement;

import java.util.List;

import org.openqa.selenium.WebElement;

import io.cucumber.java.Scenario;
import io.github.mpontoc.picaroon.core.utils.ElementFunctions;;

public class ActionsCommands {

	private static Boolean located = false;
	private static Scenario scenario = null;
	private static Boolean printedInfo = null;

	public static void waitSeconds(int segundos) {

		int segundosConvertidos = segundos * 1000;

		try {
			Thread.sleep(segundosConvertidos);
		} catch (InterruptedException e) {
			;
		}
	}

	public static void waitExistClick(String obj, Integer timeout, Boolean... assertObj) {

		located = ElementFunctions.localizaElemento(obj, timeout, CLICK);

		ElementFunctions.validaElemento(obj, assertObj, located);

	}

	public static void waitExistClick(String[] obj, Integer timeout, Boolean... assertObj) {

		ElementFunctions.setObjList = obj;

		located = ElementFunctions.localizaElemento(obj[positionElement], timeout, CLICK);

		ElementFunctions.validaElemento(obj[positionElement], assertObj, located);

	}

	public static void waitExistClickAndPerform(String obj, Integer timeout, Boolean... assertObj) {

		located = ElementFunctions.localizaElemento(obj, timeout, CLICK_AND_PERFORM);

		ElementFunctions.validaElemento(obj, assertObj, located);

	}

	public static void waitExistClickAndPerform(String[] obj, Integer timeout, Boolean... assertObj) {

		ElementFunctions.setObjList = obj;

		located = ElementFunctions.localizaElemento(obj[positionElement], timeout, CLICK_AND_PERFORM);

		ElementFunctions.validaElemento(obj[positionElement], assertObj, located);

	}

	public static void waitExistSet(String obj, String textoSet, Integer timeout, Boolean... assertObj) {

		located = ElementFunctions.localizaElemento(obj, timeout, SET, textoSet);

		ElementFunctions.validaElemento(obj, assertObj, located);
	}

	public static void waitExistSet(String[] obj, String textoSet, Integer timeout, Boolean... assertObj) {

		ElementFunctions.setObjList = obj;

		located = ElementFunctions.localizaElemento(obj[positionElement], timeout, SET, textoSet);

		ElementFunctions.validaElemento(obj[positionElement], assertObj, located);
	}

	public static boolean waitExist(String obj, Integer timeout, Boolean... assertObj) {

		located = ElementFunctions.localizaElemento(obj, timeout, WAIT);

		ElementFunctions.validaElemento(obj, assertObj, located);

		return located;
	}

	public static boolean waitExist(String[] obj, Integer timeout, Boolean... assertObj) {

		ElementFunctions.setObjList = obj;

		located = ElementFunctions.localizaElemento(obj[positionElement], timeout, WAIT);

		ElementFunctions.validaElemento(obj[positionElement], assertObj, located);

		return located;
	}

	public static WebElement waitExistElement(String obj, Integer timeout, Boolean... assertObj) {

		located = ElementFunctions.localizaElemento(obj, timeout, WAIT);

		ElementFunctions.validaElemento(obj, assertObj, located);

		return ElementFunctions.element;
	}

	public static WebElement waitExistElement(String[] obj, Integer timeout, Boolean... assertObj) {
		
		ElementFunctions.setObjList = obj;

		located = ElementFunctions.localizaElemento(obj[positionElement], timeout, WAIT);

		ElementFunctions.validaElemento(obj[positionElement], assertObj, located);

		return ElementFunctions.element;
	}

	public static String waitExistGetText(String obj, Integer timeout, Boolean... assertObj) {

		located = ElementFunctions.localizaElemento(obj, timeout, GET_TEXT);

		ElementFunctions.validaElemento(obj, assertObj, located);
		
		return ElementFunctions.textoObtido;
	}

	public static String waitExistGetText(String[] obj, Integer timeout, Boolean... assertObj) {
		
		ElementFunctions.setObjList = obj;
		
		located = ElementFunctions.localizaElemento(obj[positionElement], timeout, GET_TEXT);

		ElementFunctions.validaElemento(obj[positionElement], assertObj, located);
		
		return ElementFunctions.textoObtido;
	}

	public static String[] getStringElements(String obj, Integer timeout, Boolean... assertObj) {

		located = ElementFunctions.getElements(obj, timeout);

		ElementFunctions.validaElemento(obj, assertObj, located);
		
		return ElementFunctions.elements;

	}

	public static List<WebElement> getElements(String obj, Integer timeout, Boolean... assertObj) {
		
		located = ElementFunctions.getElements(obj, timeout);

		ElementFunctions.validaElemento(obj, assertObj, located);
		
		return ElementFunctions.listElements;
	}

	public static List<WebElement> getElements(String[] obj, Integer timeout, Boolean... assertObj) {
		
		ElementFunctions.setObjList = obj;
		
		located = ElementFunctions.getElements(obj[positionElement], timeout);

		ElementFunctions.validaElemento(obj[positionElement], assertObj, located);
		
		return ElementFunctions.listElements;
	}

	public static Scenario getScenario() {
		return scenario;
	}

	public static void setScenario(Scenario scenario) {
		ActionsCommands.scenario = scenario;
	}

	public static Boolean getPrintedInfo() {
		return printedInfo;
	}

	public static void setPrintedInfo(Boolean printedInfo) {
		ActionsCommands.printedInfo = printedInfo;
	}

}