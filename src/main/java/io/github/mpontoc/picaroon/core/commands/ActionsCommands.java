package io.github.mpontoc.picaroon.core.commands;

import static io.github.mpontoc.picaroon.core.drivers.DriverFactory.driver;
import static io.github.mpontoc.picaroon.core.utils.ElementFunctions.positionElement;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import io.github.mpontoc.picaroon.core.mobile.Mobile;
import io.github.mpontoc.picaroon.core.utils.ElementFunctions;
import io.github.mpontoc.picaroon.core.utils.Log;
import io.github.mpontoc.picaroon.core.utils.Prop;
import io.github.mpontoc.picaroon.core.utils.Report;
import io.cucumber.java.Scenario;;

public class ActionsCommands {

	public static Boolean isFirstRun = null;
	private static JavascriptExecutor executor = null;
	private static Boolean located = false;
	private static Boolean[] assertObjReceved = null;
	private static Scenario scenario;
	private static Boolean printedInfo = false;
	private static WebElement element = null;

	public static void waitSeconds(int segundos) {

		int segundosConvertidos = segundos * 1000;

		try {
			Thread.sleep(segundosConvertidos);
		} catch (InterruptedException e) {
			;
		}
	}

	public static void waitExistClick(String obj, Integer timeout, Boolean... assertObj) {
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {
			element = ElementFunctions.findBy(obj);
			if (element != null) {
				located = true;
				Log.log("Element '" + obj + "' located");
				if (!Prop.getProp("browserOrMobile").toLowerCase().equals("mobile")) {
					try {
						executor.executeScript("arguments[0].setAttribute('style','border: solid 1px blue');", element);
					} catch (Exception e) {
						;
					}
				}
				element.click();
				break;
			} else
				try {
					Log.log("Cannot find the Element '" + obj + "' times " + i + " of " + timeout);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					;
				}
		}
		ElementFunctions.validaElemento(obj, assertObjReceved, located);
	}

	public static void waitExistClick(String[] obj, Integer timeout, Boolean... assertObj) {
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {

			element = ElementFunctions.findBy(obj[positionElement]);

			if (element != null) {
				located = true;
				Log.log("Element '" + ElementFunctions.tratativaReportElemento(obj) + "' located");
				if (!Prop.getProp("browserOrMobile").toLowerCase().equals("mobile")) {
					try {
						executor.executeScript("arguments[0].setAttribute('style','border: solid 1px blue');", element);
					} catch (Exception e) {
						;
					}
				}
				element.click();
				break;
			} else
				try {
					Log.log("Cannot find the Element '" + ElementFunctions.tratativaReportElemento(obj) + "' times " + i
							+ " of " + timeout);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					;
				}
		}
		ElementFunctions.validaElemento(obj[positionElement], assertObjReceved, located);
	}

	public static void waitExistClickAndPerform(String obj, Integer timeout, Boolean... assertObj) {
		Actions actions = new Actions(driver);
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {
			element = ElementFunctions.findBy(obj);
			if (element != null) {
				located = true;
				Log.log("Element '" + obj + "' located");
				if (!Prop.getProp("browserOrMobile").toLowerCase().equals("mobile")) {
					try {
						executor.executeScript("arguments[0].setAttribute('style','border: solid 1px blue');", element);
					} catch (Exception e) {
						;
					}
				}
				try {
					actions.moveToElement(element);
					actions.click();
					actions.perform();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			} else
				try {
					Log.log("Cannot find the Element '" + obj + "' times " + i + " of " + timeout);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					;
				}
		}
		ElementFunctions.validaElemento(obj, assertObjReceved, located);
	}

	public static void waitExistClickAndPerform(String[] obj, Integer timeout, Boolean... assertObj) {
		Actions actions = new Actions(driver);
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {

			element = ElementFunctions.findBy(obj[positionElement]);

			if (element != null) {
				located = true;
				Log.log("Element '" + ElementFunctions.tratativaReportElemento(obj) + "' located");
				if (!Prop.getProp("browserOrMobile").toLowerCase().equals("mobile")) {
					try {
						executor.executeScript("arguments[0].setAttribute('style','border: solid 1px blue');", element);
					} catch (Exception e) {
						;
					}
				}
				try {
					actions.moveToElement(element);
					actions.click();
					actions.perform();
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			} else
				try {
					Log.log("Cannot find the Element '" + ElementFunctions.tratativaReportElemento(obj) + "' times " + i
							+ " of " + timeout);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					;
				}
		}
		ElementFunctions.validaElemento(obj[positionElement], assertObjReceved, located);
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
				if (Prop.getProp("browserOrMobile").toLowerCase().contains("chrome-h")) {
					actions.click();
				}
				actions.perform();
				if (!Prop.getProp("browserOrMobile").toLowerCase().equals("mobile")) {
					try {
						executor.executeScript("arguments[0].setAttribute('style','border: solid 1px blue');",
								element);
					} catch (Exception e) {
						;
					}
				}
				element = null;
				element = ElementFunctions.findBy(link);
				waitExistClick(link, 2);
				Log.log("Element '" + link + "' located");
				break;
			} else
				try {
					Log.log("Cannot find the Element '" + menuDropDown + "' times " + i + " of " + timeout);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					;
				}
		}
		ElementFunctions.validaElemento(link, assertObjReceved, located);
	}

	public static void waitExistClickNewWindow(String obj, Integer numberWindow, Integer timeout,
			Boolean... assertObj) {
		assertObjReceved = assertObj;
		located = false;

		ArrayList<String> janela = new ArrayList<String>(driver.getWindowHandles());
		Log.log(janela.toString());
		Log.log(janela.get(1));
		driver.switchTo().window((String) janela.get(0)).close();

		for (int i = 1; i <= timeout; i++) {
			driver.switchTo().window((String) janela.get(numberWindow));
			element = ElementFunctions.findBy(obj);
			if (element != null) {
				located = true;
				Log.log("Element '" + obj + "' located");
				if (!Prop.getProp("browserOrMobile").toLowerCase().equals("mobile")) {
					try {
						executor.executeScript("arguments[0].setAttribute('style','border: solid 1px blue');", element);
					} catch (Exception e) {
						;
					}
				}
				element.click();
				break;
			} else
				try {
					Log.log("Cannot find the Element '" + obj + "' times " + i + " of " + timeout);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					;
				}
		}
		ElementFunctions.validaElemento(obj, assertObjReceved, located);
	}

	public static void waitExistSet(String obj, String conteudo, Integer timeout, Boolean... assertObj) {
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {
			element = ElementFunctions.findBy(obj);
			if (element != null) {
				located = true;
				Log.log("Element '" + obj + "' located");
				element.sendKeys(conteudo);
				break;
			} else
				try {
					Log.log("Cannot find the Element '" + obj + "' times " + i + " of " + timeout);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					;
				}
		}
		ElementFunctions.validaElemento(obj, assertObjReceved, located);
	}

	public static void waitExistSet(String[] obj, String conteudo, Integer timeout, Boolean... assertObj) {
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {
			element = ElementFunctions.findBy(obj[positionElement]);
			if (element != null) {
				located = true;
				Log.log("Element '" + ElementFunctions.tratativaReportElemento(obj) + "' located");
				element.sendKeys(conteudo);
				break;
			} else
				try {
					Log.log("Cannot find the Element '" + ElementFunctions.tratativaReportElemento(obj) + "' times " + i
							+ " of " + timeout);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					;
				}
		}
		ElementFunctions.validaElemento(obj[positionElement], assertObjReceved, located);
	}

	public static void waitExistSetNewWindow(String obj, String conteudo, Integer numberWindow, Integer timeout,
			Boolean... assertObj) {
		assertObjReceved = assertObj;
		located = false;

		ArrayList<String> janela = new ArrayList<String>(driver.getWindowHandles());
		Log.log(janela.toString());
		Log.log(janela.get(1));
		driver.switchTo().window((String) janela.get(0)).close();

		for (int i = 1; i <= timeout; i++) {
			driver.switchTo().window((String) janela.get(numberWindow));
			element = ElementFunctions.findBy(obj);
			if (element != null) {
				located = true;
				Log.log("Element '" + obj + "' located");
				element.sendKeys(conteudo);
				break;
			} else
				try {
					Log.log("Cannot find the Element '" + obj + "' times " + i + " of " + timeout);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					;
				}
		}
		ElementFunctions.validaElemento(obj, assertObjReceved, located);
	}

	public static boolean waitExist(String obj, Integer timeout, Boolean... assertObj) {
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {
			element = ElementFunctions.findBy(obj);
			if (element != null) {
				located = true;
				Log.log("Element '" + obj + "' located");
				break;
			} else
				try {
					Log.log("Cannot find the Element '" + obj + "' times " + i + " of " + timeout);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					;
				}
		}
		ElementFunctions.validaElemento(obj, assertObjReceved, located);
		return located;
	}

	public static boolean waitExist(String[] obj, Integer timeout, Boolean... assertObj) {
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {
			element = ElementFunctions.findBy(obj[positionElement]);
			if (element != null) {
				located = true;
				Log.log("Element '" + ElementFunctions.tratativaReportElemento(obj) + "' located");
				break;
			} else
				try {
					Log.log("Cannot find the Element '" + ElementFunctions.tratativaReportElemento(obj) + "' times " + i
							+ " of " + timeout);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					;
				}
		}
		ElementFunctions.validaElemento(obj[positionElement], assertObjReceved, located);
		return located;
	}

	public static WebElement waitExistElement(String obj, Integer timeout, Boolean... assertObj) {
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {
			element = ElementFunctions.findBy(obj);
			if (element != null) {
				located = true;
				Log.log("Element '" + obj + "' located");
				break;
			} else
				try {
					Log.log("Cannot find the Element '" + obj + "' times " + i + " of " + timeout);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					;
				}
		}
		ElementFunctions.validaElemento(obj, assertObjReceved, located);
		return element;
	}

	public static WebElement waitExistElement(String[] obj, Integer timeout, Boolean... assertObj) {
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {
			element = ElementFunctions.findBy(obj[positionElement]);
			if (element != null) {
				located = true;
				Log.log("Element '" + ElementFunctions.tratativaReportElemento(obj) + "' located");
				break;
			} else
				try {
					Log.log("Cannot find the Element '" + ElementFunctions.tratativaReportElemento(obj) + "' times " + i
							+ " of " + timeout);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					;
				}
		}
		ElementFunctions.validaElemento(obj[positionElement], assertObjReceved, located);
		return element;
	}

	@SuppressWarnings("unused")
	public static String[] getStringElements(String obj, Integer timeout, Boolean... assertObj) {

		String[] elements = null;
		List<WebElement> listElements = null;
		assertObjReceved = assertObj;
		located = false;

		for (int i = 1; i <= timeout; i++) {
			listElements = ElementFunctions.findByElements(obj);
			elements = new String[listElements.size()];
			if (listElements != null) {
				located = true;
				int index = 0;
				for (WebElement elemento : listElements) {
					Log.log(elemento.getText());
					elements[index] = elemento.getText();
					index++;
				}
				Log.log("Elements '" + obj + "' located");
				break;
			} else {
				Log.log("Cannot find the Element '" + obj + "' times " + i + " of " + timeout);
			}
		}
		ElementFunctions.validaElemento(obj, assertObjReceved, located);
		return elements;

	}

	public static List<WebElement> getElements(String obj, Integer timeout, Boolean... assertObj) {
		List<WebElement> listElements = null;
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {
			listElements = ElementFunctions.findByElements(obj);
			if (listElements != null) {
				located = true;
				Log.log("Elements '" + obj + "' located");
				break;
			} else
				try {
					Log.log("Cannot find the Element '" + obj + "' times " + i + " of " + timeout);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					;
				}
		}
		ElementFunctions.validaElemento(obj, assertObjReceved, located);
		return listElements;
	}

	public static List<WebElement> getElements(String[] obj, Integer timeout, Boolean... assertObj) {
		List<WebElement> listElements = null;
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {
			listElements = ElementFunctions.findByElements(obj[positionElement]);
			if (listElements != null) {
				located = true;
				Log.log("Elements '" + ElementFunctions.tratativaReportElemento(obj) + "' located");
				break;
			} else
				try {
					Log.log("Cannot find the Element '" + ElementFunctions.tratativaReportElemento(obj) + "' times " + i
							+ " of " + timeout);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					;
				}
		}
		ElementFunctions.validaElemento(obj[positionElement], assertObjReceved, located);
		return listElements;
	}

	public static String waitExistGetText(String obj, Integer timeout, Boolean... assertObj) {
		String textoObtido = "";
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {
			element = ElementFunctions.findBy(obj);
			if (element != null) {
				if (Prop.getProp("browserOrMobile").toLowerCase().contains("mobile")
						&& Mobile.getPlataforma().toLowerCase().equals("ios")) {
					try {
						textoObtido = element.getAttribute("label").toString();
					} catch (Exception e) {
						textoObtido = element.getAttribute("name").toString();
					}
				} else {
					textoObtido = element.getText().toString();
				}
				if (textoObtido.length() > 3) {
					located = true;
					Log.log("Element '" + obj + "' located");
					if (!Prop.getProp("browserOrMobile").toLowerCase().contains("mobile")
							&& Prop.getProp("collorBackgroud").equals("true")) {
						try {
							executor.executeScript("arguments[0].style.backgroundColor = 'yellow';", element);
						} catch (Exception e) {
							;
						}
					}
					Log.log("Text caught [ '" + textoObtido + "' ]");
					Report.cucumberWriteReport("Text caught [ '" + textoObtido + "' ]");
					break;
				}
			} else
				try {
					Log.log("Cannot find the Element '" + obj + "' times " + i + " of " + timeout);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					;
				}
		}
		ElementFunctions.validaElemento(obj, assertObjReceved, located);
		return textoObtido;
	}

	public static String waitExistGetText(String[] obj, Integer timeout, Boolean... assertObj) {
		String textoObtido = "";
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {
			element = ElementFunctions.findBy(obj[positionElement]);
			if (element != null) {
				if (Prop.getProp("browserOrMobile").toLowerCase().contains("mobile")
						&& Mobile.getPlataforma().toLowerCase().equals("ios")) {
					textoObtido = element.getAttribute("label").toString();
				} else {
					textoObtido = element.getText().toString();
				}
				if (textoObtido.length() > 3) {
					located = true;
					Log.log("Element '" + ElementFunctions.tratativaReportElemento(obj) + "' located");
					if (!Prop.getProp("browserOrMobile").toLowerCase().contains("mobile")
							&& Prop.getProp("collorBackgroud").equals("true")) {
						try {
							executor.executeScript("arguments[0].style.backgroundColor = 'yellow';", element);
						} catch (Exception e) {
							;
						}
					}
					Log.log("Text caught [ '" + textoObtido + "' ]");
					Report.cucumberWriteReport("Text caught [ '" + textoObtido + "' ]");
					break;
				}
			} else
				try {
					Log.log("Cannot find the Element '" + ElementFunctions.tratativaReportElemento(obj) + "' times " + i
							+ " of " + timeout);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					;
				}
		}
		ElementFunctions.validaElemento(obj[positionElement], assertObjReceved, located);
		return textoObtido;
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
		ElementFunctions.validaElemento(obj, assertObjReceved, located);
	}

	public static void scrollDownDirectlyWeb(String obj) {

		ActionsCommands.executor.executeScript("arguments[0].scrollIntoView(true);", obj);

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