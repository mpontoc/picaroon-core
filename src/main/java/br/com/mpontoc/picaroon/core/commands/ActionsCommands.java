package br.com.mpontoc.picaroon.core.commands;

import static br.com.mpontoc.picaroon.core.drivers.DriverFactory.deviceElement;
import static br.com.mpontoc.picaroon.core.drivers.DriverFactory.driver;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import br.com.mpontoc.picaroon.core.mobile.Mobile;
import br.com.mpontoc.picaroon.core.utils.ElementFunctions;
import br.com.mpontoc.picaroon.core.utils.Log;
import br.com.mpontoc.picaroon.core.utils.Prop;
import io.cucumber.java.Scenario;;

public class ActionsCommands {

	private static String cucumberReportMessage = null;
	private static JavascriptExecutor executor = null;
	private static Boolean located = false;
	private static Boolean[] assertObjReceved = null;
	public static Boolean isFirstRun = null;
	private static Scenario scenario;
	private static Boolean printedInfo = false;

	// ******* Cucumber Report *******

	public static void printScreen() {

		Prop.setPropAndSave("printAfterSteps", "true");
		printScreenAfterStep(getScenario());
		Prop.setPropAndSave("printAfterSteps", "false");
	}

	public static void printScreenAfterStep(Scenario scenario) {

		if (Prop.getProp("printAfterSteps").equals("true")
				&& !Prop.getProp("browserOrDevice").toLowerCase().contains("false")) {
			if (isFirstRun == true) {
				scenario.log("\n");
				scenario.attach(resizeScreenshot(), "image/png", scenario.getName());
			}
			scenario.log("\n");
			isFirstRun = false;
		} else {
			Log.log("Already printed on cucumber Report");
		}
	}

	public static byte[] resizeScreenshot() {
		int width = 0;
		int height = 0;
		byte[] imageBytes = null;

		final File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		if (Prop.getProp("browserOrDevice").toLowerCase().contains("mobile")) {
			width = 480;
			height = 854;
		} else {

			width = 1024;
			height = 768;

		}

		try {
			BufferedImage image = ImageIO.read(screenshot);
			Image originalImage = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
			int type = ((image.getType() == 0) ? BufferedImage.TYPE_INT_ARGB : image.getType());
			BufferedImage resizedImage = new BufferedImage(width, height, type);
			Graphics2D g2d = resizedImage.createGraphics();
			g2d.setComposite(AlphaComposite.Src);
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.drawImage(originalImage, 0, 0, width, height, null);
			g2d.dispose();
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ImageIO.write(resizedImage, "png", byteArrayOutputStream);
			imageBytes = byteArrayOutputStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return imageBytes;

	}

	private static void setCucumberReportMessage(String msg) {
		cucumberReportMessage = msg;
	}

	private static String getCucumberReportMessage() {
		return cucumberReportMessage;
	}

	public static void cucumberWriteReport(String msg) {
		setCucumberReportMessage(msg);
		writeReportStep(getScenario());
	}

	public static void writeReportStep(Scenario scenario) {
		scenario = getScenario();
		scenario.log(getCucumberReportMessage());
		cucumberReportMessage = "";
	}

	public static void waitSeconds(int segundos) {

		int segundosConvertidos = segundos * 1000;

		try {
			Thread.sleep(segundosConvertidos);
		} catch (InterruptedException e) {
			;
		}
	}

	public static void waitExistClick(String obj, Integer timeout, Boolean... assertObj) {
		WebElement element = null;
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {
			element = ElementFunctions.findBy(obj);
			if (element != null) {
				located = true;
				Log.log("Element '" + obj + "' located");
				if (!Prop.getProp("browserOrDevice").toLowerCase().equals("mobile")) {
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
		WebElement element = null;
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {

			element = ElementFunctions.findBy(obj[deviceElement]);

			if (element != null) {
				located = true;
				Log.log("Element '" + ElementFunctions.tratativaReportElemento(obj) + "' located");
				if (!Prop.getProp("browserOrDevice").toLowerCase().equals("mobile")) {
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
		ElementFunctions.validaElemento(obj[deviceElement], assertObjReceved, located);
	}

	public static void waitExistClickAndPerform(String obj, Integer timeout, Boolean... assertObj) {
		WebElement element = null;
		Actions actions = new Actions(driver);
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {
			element = ElementFunctions.findBy(obj);
			if (element != null) {
				located = true;
				Log.log("Element '" + obj + "' located");
				if (!Prop.getProp("browserOrDevice").toLowerCase().equals("mobile")) {
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
		WebElement element = null;
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {

			element = ElementFunctions.findBy(obj[deviceElement]);

			if (element != null) {
				located = true;
				Log.log("Element '" + ElementFunctions.tratativaReportElemento(obj) + "' located");
				if (!Prop.getProp("browserOrDevice").toLowerCase().equals("mobile")) {
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
		ElementFunctions.validaElemento(obj[deviceElement], assertObjReceved, located);
	}

	public static void waitExistClickAndPerformDropDown(String menuDropDown, String link, Integer timeout,
			Boolean... assertObj) {
		Actions actions = new Actions(driver);
		WebElement element1 = null;
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {
			element1 = ElementFunctions.findBy(menuDropDown);
			if (element1 != null && element1.isDisplayed()) {
				located = true;
				actions.moveToElement(element1);
				if (Prop.getProp("browserOrDevice").toLowerCase().contains("chrome-h")) {
					actions.click();
				}
				actions.perform();
				if (!Prop.getProp("browserOrDevice").toLowerCase().equals("mobile")) {
					try {
						executor.executeScript("arguments[0].setAttribute('style','border: solid 1px blue');",
								element1);
					} catch (Exception e) {
						;
					}
				}
				element1 = null;
				element1 = ElementFunctions.findBy(link);
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
		WebElement element = null;
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
				if (!Prop.getProp("browserOrDevice").toLowerCase().equals("mobile")) {
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
		WebElement element = null;
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
		WebElement element = null;
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {
			element = ElementFunctions.findBy(obj[deviceElement]);
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
		ElementFunctions.validaElemento(obj[deviceElement], assertObjReceved, located);
	}

	public static void waitExistSetNewWindow(String obj, String conteudo, Integer numberWindow, Integer timeout,
			Boolean... assertObj) {
		WebElement element = null;
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
		WebElement element = null;
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
		WebElement element = null;
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {
			element = ElementFunctions.findBy(obj[deviceElement]);
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
		ElementFunctions.validaElemento(obj[deviceElement], assertObjReceved, located);
		return located;
	}

	public static WebElement waitExistElement(String obj, Integer timeout, Boolean... assertObj) {

		WebElement element = null;
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

		WebElement element = null;
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {
			element = ElementFunctions.findBy(obj[deviceElement]);
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
		ElementFunctions.validaElemento(obj[deviceElement], assertObjReceved, located);
		return element;
	}

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
			} else
				try {
					Log.log("Cannot find the Element '" + obj + "' times " + i + " of " + timeout);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
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
			listElements = ElementFunctions.findByElements(obj[deviceElement]);
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
		ElementFunctions.validaElemento(obj[deviceElement], assertObjReceved, located);
		return listElements;
	}

	public static String waitExistGetText(String obj, Integer timeout, Boolean... assertObj) {
		WebElement element = null;
		String textoObtido = "";
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {
			element = ElementFunctions.findBy(obj);
			if (element != null) {
				if (Prop.getProp("browserOrDevice").toLowerCase().contains("mobile")
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
					if (!Prop.getProp("browserOrDevice").toLowerCase().contains("mobile")
							&& Prop.getProp("collorBackgroud").equals("true")) {
						try {
							executor.executeScript("arguments[0].style.backgroundColor = 'yellow';", element);
						} catch (Exception e) {
							;
						}
					}
					Log.log("Text caught [ '" + textoObtido + "' ]");
					ActionsCommands.cucumberWriteReport("Text caught [ '" + textoObtido + "' ]");
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
		WebElement element = null;
		String textoObtido = "";
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {
			element = ElementFunctions.findBy(obj[deviceElement]);
			if (element != null) {
				if (Prop.getProp("browserOrDevice").toLowerCase().contains("mobile")
						&& Mobile.getPlataforma().toLowerCase().equals("ios")) {
					textoObtido = element.getAttribute("label").toString();
				} else {
					textoObtido = element.getText().toString();
				}
				if (textoObtido.length() > 3) {
					located = true;
					Log.log("Element '" + ElementFunctions.tratativaReportElemento(obj) + "' located");
					if (!Prop.getProp("browserOrDevice").toLowerCase().contains("mobile")
							&& Prop.getProp("collorBackgroud").equals("true")) {
						try {
							executor.executeScript("arguments[0].style.backgroundColor = 'yellow';", element);
						} catch (Exception e) {
							;
						}
					}
					Log.log("Text caught [ '" + textoObtido + "' ]");
					ActionsCommands.cucumberWriteReport("Text caught [ '" + textoObtido + "' ]");
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
		ElementFunctions.validaElemento(obj[deviceElement], assertObjReceved, located);
		return textoObtido;
	}

	public static void waitExistSelectComboBox(String obj, String value, Integer timeout, Boolean... assertObj) {

		WebElement element = null;
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