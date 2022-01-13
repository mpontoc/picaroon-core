package io.github.mpontoc.picaroon.core.commands;

import io.github.mpontoc.picaroon.core.elements.ElementFunctions;
import io.github.mpontoc.picaroon.core.utils.Functions;
import io.github.mpontoc.picaroon.core.utils.Log;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;

import static io.github.mpontoc.picaroon.core.drivers.DriverFactory.driver;
import static io.github.mpontoc.picaroon.core.drivers.DriverFactory.executor;
import static io.github.mpontoc.picaroon.core.elements.ElementConstants.COMBO_BOX;
import static io.github.mpontoc.picaroon.core.elements.ElementConstants.MENU_DROP_DOWN;
import static io.github.mpontoc.picaroon.core.elements.ElementFunctions.POSITION_ELEMENT;
import static io.github.mpontoc.picaroon.core.elements.ElementFunctions.getElement;

public class WebCommands {

    private static Boolean[] assertObjReceved = null;
    private static Boolean located = false;

    public static void swichNewWindowAndClosePrevious() {

        ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
        Log.log(windows.toString());
        if (windows.size() > 1) {
            Log.log(windows.get(1));
            driver.switchTo().window((String) windows.get(0)).close();
            driver.switchTo().window((String) windows.get(1));
        }
    }

    public static void swichNewWindow(Integer numberwindow) {

        ArrayList<String> windows = new ArrayList<String>(driver.getWindowHandles());
        Log.log(windows.toString());
        if (windows.size() > 1) {
            Log.log(windows.get(numberwindow));
            driver.switchTo().window((String) windows.get(numberwindow));
        }
    }

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

    public static void waitExistSelectComboBox(String obj, String value, Integer timeout, Boolean... assertObj) {

        located = ElementFunctions.locateElement(obj, timeout, COMBO_BOX, null, value);
        ElementFunctions.validateElement(obj, assertObj, located);
    }

    public static void waitExistSelectComboBox(String[] obj, String value, Integer timeout, Boolean... assertObj) {

        located = ElementFunctions.locateElement(obj[POSITION_ELEMENT], timeout, COMBO_BOX, obj, value);
        ElementFunctions.validateElement(obj[POSITION_ELEMENT], assertObjReceved, located);
    }

    public static void waitExistClickAndPerformMenuDropDown(String menuDropDown, String link, Integer timeout,
                                                            Boolean... assertObj) {
        located = ElementFunctions.locateElement(menuDropDown, timeout, MENU_DROP_DOWN, null, link);
        ElementFunctions.validateElement(link, assertObjReceved, located);
    }

}
