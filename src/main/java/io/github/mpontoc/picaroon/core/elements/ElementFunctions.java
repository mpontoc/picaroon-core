package io.github.mpontoc.picaroon.core.elements;

import io.appium.java_client.MobileBy;
import io.github.mpontoc.picaroon.core.commands.ActionsCommands;
import io.github.mpontoc.picaroon.core.drivers.DriverFactory;
import io.github.mpontoc.picaroon.core.exception.PicaroonException;
import io.github.mpontoc.picaroon.core.execution.report.Report;
import io.github.mpontoc.picaroon.core.mobile.Mobile;
import io.github.mpontoc.picaroon.core.utils.Functions;
import io.github.mpontoc.picaroon.core.utils.Log;
import io.github.mpontoc.picaroon.core.utils.PropertiesVariables;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

import static io.github.mpontoc.picaroon.core.drivers.DriverFactory.driver;
import static io.github.mpontoc.picaroon.core.drivers.DriverFactory.executor;
import static io.github.mpontoc.picaroon.core.elements.ElementConstants.*;
import static io.github.mpontoc.picaroon.core.utils.PropertiesVariables.*;

public class ElementFunctions {

    public static Integer POSITION_ELEMENT = null;

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
                POSITION_ELEMENT = 0;
            else
                // ios
                POSITION_ELEMENT = 1;
        } else {
            // web
            POSITION_ELEMENT = 0;
        }
    }

    public static String setElementToReportLog(String[] elemento) {

        String nomeObjMapeado = null;

        if (Mobile.getApp() != null) {
            if (elemento.length > 2) {
                nomeObjMapeado = elemento[2];
            } else {
                nomeObjMapeado = elemento[POSITION_ELEMENT];
            }
        } else {
            if (elemento.length > 1) {
                nomeObjMapeado = elemento[1];
            } else {
                nomeObjMapeado = elemento[POSITION_ELEMENT];
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
            } else if (PropertiesVariables.BROWSER_OR_MOBILE.contains("mobile") && Mobile.getPlataforma().contains("android")) {
                byType.add(By.id(DriverFactory.mobileDriver.getCapabilities().getCapability("appPackage").toString() + ":id/" + obj));
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

    public static void validateElement(String obj, Boolean[] assertObjReceived, Boolean located) {

        String action = null;
        String error = null;
        boolean mustValidate = false;

        if (assertObjReceived != null && assertObjReceived.length >= 1) {
            mustValidate = assertObjReceived[0];
        }

        if (mustValidate == true) {
            if (located == true) {
                action = "Mandatory action with the element '" + obj + "' successfully";
                Log.log(action);
                Report.cucumberWriteReport(action);
            } else {
                error = "There was a problem with the element '" + obj + "'";
                Log.log(error);
                throw new PicaroonException(error);
            }
        } else {
            Log.log("Element '" + obj + "' not located");
        }
    }

    public static Boolean locateElement(String obj, Integer timeout, String action, String[] objList, String... textToSetOrNewWindowOrMenuDropDown) {

        setElement(null);

        for (int i = 1; i <= timeout; i++) {
            setElement(findBy(obj));
            if (getElement() != null) {
                if (!PropertiesVariables.BROWSER_OR_MOBILE.equals("mobile")) {
                    executor.executeScript("arguments[0].setAttribute('style','border: solid 1px blue');", element);
                }
                if (!action.equals(WAIT)) {
                    actionToElement(action, obj, textToSetOrNewWindowOrMenuDropDown);
                }
                return true;
            } else {
                returnsTry(obj, timeout, objList, i);
            }
        }
        return false;
    }

    public static void actionToElement(String action, String obj, String... textToSetOrValueToComboBoxOrMenuDropDown) {

        setTextoObtido("");

        switch (action) {

            case CLICK:

                if (!BROWSER_OR_MOBILE.contains("mobile") && COLOR_BACKGROUND.equals("true")) {
                    executor.executeScript("arguments[0].setAttribute('style','border: solid 1px blue');", getElement());
                }
                getElement().click();
                Log.log("Element '" + obj + "' located and clicked");
                break;

            case CLICK_AND_PERFORM:

                if (!BROWSER_OR_MOBILE.contains("mobile") && COLOR_BACKGROUND.equals("true")) {
                    executor.executeScript("arguments[0].setAttribute('style','border: solid 1px blue');", getElement());
                }
                Actions actions = new Actions(driver);
                actions.moveToElement(getElement());
                actions.click();
                actions.perform();
                Log.log("Element '" + obj + "' located and clicked by perform");
                break;

            case SET:
                getElement().clear();
                getElement().sendKeys(textToSetOrValueToComboBoxOrMenuDropDown[0]);
                Log.log("Element '" + obj + "' located and set with content " + textToSetOrValueToComboBoxOrMenuDropDown[0]);
                break;

            case GET_TEXT:
                if (BROWSER_OR_MOBILE.contains("mobile") && MOBILE_PLATFORM.toLowerCase().equals("ios")) {
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
                break;

            case COMBO_BOX:
                getElement().click();
                Functions.waitSeconds(2);
                new Select(getElement()).selectByVisibleText(textToSetOrValueToComboBoxOrMenuDropDown[0]);
                ActionsCommands.waitExistClick(textToSetOrValueToComboBoxOrMenuDropDown[0], 1);
                Log.log("Combo Box '" + obj + "' located and select the content " + textToSetOrValueToComboBoxOrMenuDropDown[0]);
                break;

            case MENU_DROP_DOWN:

                Actions actions2 = new Actions(driver);

                actions2.moveToElement(getElement());
                if (PropertiesVariables.BROWSER_OR_MOBILE.contains("chrome-h")) {
                    actions2.click();
                }
                actions2.perform();
                if (!PropertiesVariables.BROWSER_OR_MOBILE.equals("mobile")) {
                    executor.executeScript("arguments[0].setAttribute('style','border: solid 1px blue');", element);
                }
                Functions.waitSeconds(2);
                ActionsCommands.waitExistClick(textToSetOrValueToComboBoxOrMenuDropDown[0], 2);
                Log.log("Element '" + textToSetOrValueToComboBoxOrMenuDropDown[0] + "' located and clicked on menu dropdown");
                break;
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

                if (typeGetElements.equals(GET_ELEMENTS)) return (T) listElements;
                else if (typeGetElements.equals(GET_STRING_ELEMENTS)) return (T) listStringElements;
            } else returnsTry(obj, timeout, objListElements, i);
        }
        return null;
    }

    public static void returnsTry(String obj, Integer timeout, String[] objListElements, int i) {
        if (objListElements != null) {
            Log.log("Cannot find the Element '" + setElementToReportLog(objListElements) + "' times " + i + " of " + timeout);
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
