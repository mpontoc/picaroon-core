package io.github.mpontoc.picaroon.core.commands;

import io.cucumber.java.Scenario;
import io.github.mpontoc.picaroon.core.elements.ElementFunctions;
import org.openqa.selenium.WebElement;

import java.util.List;

import static io.github.mpontoc.picaroon.core.elements.ElementConstants.*;
import static io.github.mpontoc.picaroon.core.elements.ElementFunctions.POSITION_ELEMENT;

public class ActionsCommands {

	private static Boolean located = false;
	private static Scenario scenario = null;
	private static Boolean printedInfo = null;
	private static List<WebElement> listElements = null;

	public static void waitSeconds(int segundos) {

		int segundosConvertidos = segundos * 1000;

		try {
			Thread.sleep(segundosConvertidos);
		} catch (InterruptedException e) {
			;
		}
	}

	public static void waitExistClick(String obj, Integer timeout, Boolean... assertObj) {

		located = ElementFunctions.locateElement(obj, timeout, CLICK, null);

		ElementFunctions.validateElement(obj, assertObj, located);

	}

	public static void waitExistClick(String[] obj, Integer timeout, Boolean... assertObj) {


		located = ElementFunctions.locateElement(obj[POSITION_ELEMENT], timeout, CLICK, obj);

		ElementFunctions.validateElement(obj[POSITION_ELEMENT], assertObj, located);

	}

	public static void waitExistClickAndPerform(String obj, Integer timeout, Boolean... assertObj) {

		located = ElementFunctions.locateElement(obj, timeout, CLICK_AND_PERFORM, null);

		ElementFunctions.validateElement(obj, assertObj, located);

	}

	public static void waitExistClickAndPerform(String[] obj, Integer timeout, Boolean... assertObj) {

		located = ElementFunctions.locateElement(obj[POSITION_ELEMENT], timeout, CLICK_AND_PERFORM, obj);

		ElementFunctions.validateElement(obj[POSITION_ELEMENT], assertObj, located);

	}

	public static void waitExistSet(String obj, String textoSet, Integer timeout, Boolean... assertObj) {

		located = ElementFunctions.locateElement(obj, timeout, SET, null, textoSet);

		ElementFunctions.validateElement(obj, assertObj, located);
	}

	public static void waitExistSet(String[] obj, String textoSet, Integer timeout, Boolean... assertObj) {

		located = ElementFunctions.locateElement(obj[POSITION_ELEMENT], timeout, SET, obj, textoSet);

		ElementFunctions.validateElement(obj[POSITION_ELEMENT], assertObj, located);
	}

	public static Boolean waitExist(String obj, Integer timeout, Boolean... assertObj) {

		located = ElementFunctions.locateElement(obj, timeout, WAIT, null);

		ElementFunctions.validateElement(obj, assertObj, located);

		return located;
	}

	public static Boolean waitExist(String[] obj, Integer timeout, Boolean... assertObj) {

		located = ElementFunctions.locateElement(obj[POSITION_ELEMENT], timeout, WAIT, obj);

		ElementFunctions.validateElement(obj[POSITION_ELEMENT], assertObj, located);

		return located;
	}

	public static WebElement waitExistElement(String obj, Integer timeout, Boolean... assertObj) {

		located = ElementFunctions.locateElement(obj, timeout, WAIT, null);

		ElementFunctions.validateElement(obj, assertObj, located);

		return ElementFunctions.getElement();
	}

	public static WebElement waitExistElement(String[] obj, Integer timeout, Boolean... assertObj) {
		
		located = ElementFunctions.locateElement(obj[POSITION_ELEMENT], timeout, WAIT, obj);

		ElementFunctions.validateElement(obj[POSITION_ELEMENT], assertObj, located);

		return ElementFunctions.getElement();
	}

	public static String waitExistGetText(String obj, Integer timeout, Boolean... assertObj) {

		located = ElementFunctions.locateElement(obj, timeout, GET_TEXT, null);

		ElementFunctions.validateElement(obj, assertObj, located);
		
		return ElementFunctions.getTextoObtido();
	}

	public static String waitExistGetText(String[] obj, Integer timeout, Boolean... assertObj) {
		
		located = ElementFunctions.locateElement(obj[POSITION_ELEMENT], timeout, GET_TEXT, obj);

		ElementFunctions.validateElement(obj[POSITION_ELEMENT], assertObj, located);
		
		return ElementFunctions.getTextoObtido();
	}

	public static String[] getStringElements(String obj, Integer timeout, Boolean... assertObj) {
		
		String[] listStringElements  = ElementFunctions.getElements(obj, timeout, null, GET_STRING_ELEMENTS);

		ElementFunctions.validateElement(obj, assertObj, located);
		
		return listStringElements;

	}

	public static List<WebElement> getElements(String obj, Integer timeout, Boolean... assertObj) {
		
		listElements = ElementFunctions.getElements(obj, timeout, null, GET_ELEMENTS);

		ElementFunctions.validateElement(obj, assertObj, located);
		
		return listElements;
	}

	public static List<WebElement> getElements(String[] obj, Integer timeout, Boolean... assertObj) {
		
		listElements = ElementFunctions.getElements(obj[POSITION_ELEMENT], timeout, obj, GET_ELEMENTS);

		ElementFunctions.validateElement(obj[POSITION_ELEMENT], assertObj, located);
		
		return listElements;
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