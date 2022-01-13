package io.github.mpontoc.picaroon.core.commands;

import static io.github.mpontoc.picaroon.core.drivers.DriverFactory.driver;
import static io.github.mpontoc.picaroon.core.drivers.DriverFactory.executor;
import static io.github.mpontoc.picaroon.core.elements.ElementConstants.*;
import static io.github.mpontoc.picaroon.core.elements.ElementFunctions.POSITION_ELEMENT;

import java.util.ArrayList;

import io.github.mpontoc.picaroon.core.elements.ElementConstants;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import io.github.mpontoc.picaroon.core.elements.ElementFunctions;
import io.github.mpontoc.picaroon.core.utils.Functions;
import io.github.mpontoc.picaroon.core.utils.Log;
import io.github.mpontoc.picaroon.core.utils.PropertiesVariables;

public class WebCommands {

    private static WebElement element = null;
    private static Boolean[] assertObjReceved = null;
    private static Boolean located = false;

    public static void scrollDown(int count) {
        for (int i = 0; i < count; i++) {
            try {
                executor.executeScript("window.scrollBy(0,325)");
                Functions.waitSeconds(2);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void scrollUntilWebElement(String obj) {

        boolean objlocated = ActionsCommands.waitExist(obj, 1);
        int i = 0;

        if (objlocated == false) {
            while (objlocated == false) {

                scrollDown(1);

                objlocated = ActionsCommands.waitExist(obj, 1);
                Functions.waitSeconds(1);
                i++;
                if (i == 20)
                    break;
            }
        } else
            Log.log("Object no located on screen " + obj);
    }

    public static void waitExistClickAndPerformDropDown(String menuDropDown, String link, Integer timeout,
                                                        Boolean... assertObj) {


        Actions actions = new Actions(driver);
        assertObjReceved = assertObj;
        located = false;
        for (int i = 1; i <= timeout; i++) {
            element = ElementFunctions.findBy(menuDropDown);
            if (element != null && element.isDisplayed()) {
                located = true;
                actions.moveToElement(element);
                if (PropertiesVariables.BROWSER_OR_MOBILE.contains("chrome-h")) {
                    actions.click();
                }
                actions.perform();
                if (!PropertiesVariables.BROWSER_OR_MOBILE.equals("mobile")) {
                    try {
                        executor.executeScript("arguments[0].setAttribute('style','border: solid 1px blue');", element);
                    } catch (Exception e) {
                        ;
                    }
                }
                element = null;
                element = ElementFunctions.findBy(link);
                ActionsCommands.waitExistClick(link, 2);
                Log.log("Element '" + link + "' located and clicked on menu dropdown");
                break;
            } else
                try {
                    Log.log("Cannot find the Element '" + menuDropDown + "' times " + i + " of " + timeout);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    ;
                }
        }
        ElementFunctions.validateElement(link, assertObjReceved, located);
    }

    public static void waitExistClickNewWindow(String obj, Integer numberWindow, Integer timeout,
                                               Boolean... assertObj) {

        located = ElementFunctions.locateElement(obj, timeout, CLICK, null, NEW_WINDOW);
        ElementFunctions.validateElement(obj, assertObjReceved, located);
    }

    public static void waitExistClickNewWindow(String obj[], Integer numberWindow, Integer timeout,
                                               Boolean... assertObj) {

        located = ElementFunctions.locateElement(obj[POSITION_ELEMENT], timeout, CLICK, obj, NEW_WINDOW);
        ElementFunctions.validateElement(obj[POSITION_ELEMENT], assertObjReceved, located);
    }

    public static void waitExistSetNewWindow(String obj, String textToSet, Integer numberWindow, Integer timeout,
                                             Boolean... assertObj) {
        located = ElementFunctions.locateElement(obj, timeout, SET, null, NEW_WINDOW, textToSet);
        ElementFunctions.validateElement(obj, assertObjReceved, located);
    }

    public static void waitExistSetNewWindow(String obj[], String textToSet, Integer numberWindow, Integer timeout,
                                             Boolean... assertObj) {

        located = ElementFunctions.locateElement(obj[POSITION_ELEMENT], timeout, SET, obj, NEW_WINDOW, textToSet);
        ElementFunctions.validateElement(obj[POSITION_ELEMENT], assertObjReceved, located);
    }

    public static void waitExistSelectComboBox(String obj, String value, Integer timeout, Boolean... assertObj) {
        assertObjReceved = assertObj;
        located = false;
        for (int i = 1; i <= timeout; i++) {
            element = ElementFunctions.findBy(obj);
            if (element != null) {
                located = true;
                Log.log("Element '" + obj + "' located");
                element.click();
                new Select(element).selectByVisibleText(value);
                ;
                break;
            } else
                try {
                    Log.log("Cannot find the Element '" + obj + "' times " + i + " of " + timeout);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    ;
                }
        }
        ElementFunctions.validateElement(obj, assertObjReceved, located);
    }

    public static void waitExistSelectComboBox(String[] obj, String value, Integer timeout, Boolean... assertObj) {
        assertObjReceved = assertObj;
        located = false;
        for (int i = 1; i <= timeout; i++) {
            element = ElementFunctions.findBy(obj[POSITION_ELEMENT]);
            if (element != null) {
                located = true;
                Log.log("Element '" + ElementFunctions.setElementToReportLog(obj) + "' located");
                element.click();
                new Select(element).selectByVisibleText(value);
                ;
                break;
            } else
                try {
                    Log.log("Cannot find the Element '" + obj + "' times " + i + " of " + timeout);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    ;
                }
        }
        ElementFunctions.validateElement(obj[POSITION_ELEMENT], assertObjReceved, located);
    }

    public static void scrollDownDirectlyWeb(String obj) {

        executor.executeScript("arguments[0].scrollIntoView(true);", obj);

    }

}
