package br.com.mpontoc.picaroon.core.commands;

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

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import br.com.mpontoc.picaroon.core.driverFactory.DriverInit;
import br.com.mpontoc.picaroon.core.driverFactory.MobileDriverInit;
import br.com.mpontoc.picaroon.core.mobile.Mobile;
import br.com.mpontoc.picaroon.core.utils.Functions;
import br.com.mpontoc.picaroon.core.utils.Log;
import br.com.mpontoc.picaroon.core.utils.Prop;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.StartsActivity;
import io.cucumber.core.api.Scenario;

public class ActionsCommands {

	public static WebDriver driver = null;
	public static Integer deviceElement = null;
	private static String cucumberReportMessage = "";
	public static JavascriptExecutor executor = null;
	public static Boolean located = false;
	public static Boolean[] assertObjReceved = null;
	public static Boolean isFirstRun = null;
	private static Scenario scenario;

	public static void setUpDriver() {

		if (Mobile.getPlataforma() != null) {
			if (Mobile.getPlataforma().equals("android"))
				// android
				deviceElement = 0;
			else
				// ios
				deviceElement = 1;
		}

		if (Prop.getProp("browserOrDevice").contains("mobile")) {

			if (Mobile.getApp() == null || driver == null) {
				driver = null;
			} else if (MobileDriverInit.driver() != null) {
				driver = MobileDriverInit.driverMobile;
			} else {
				Log.log("Não foi possível criar o driver");
			}
		} else {
			driver = DriverInit.driver();
			executor = (JavascriptExecutor) driver;
		}
	}

	public static void printScreen() {

		Prop.setPropAndSave("printAfterSteps", "true");
		printScreenAfterStep(getScenario());
		Prop.setPropAndSave("printAfterSteps", "false");

	}

	public static void printScreenAfterStep(Scenario scenario) {

		if (Prop.getProp("printAfterSteps").equals("true")
				&& Prop.getProp("browserOrDevice").equals("false") == false) {

			if (isFirstRun == true) {

				scenario.write("\n");

				if (Prop.getProp().contains("mobile")) {
					scenario.embed(resizeScreenshot(), "image/png");
				} else {
					final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
					scenario.embed(screenshot, "image/png");
				}

				scenario.write("\n");

				isFirstRun = false;

			} else {
				Log.log("Ja imprimiu no cucumber Report");
			}
		}
	}

	public static byte[] resizeScreenshot() {
		int width = 0;
		int height = 0;
		byte[] imageBytes = null;

		final File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		if (Prop.getProp().contains("mobile")) {
			width = 480;
			height = 854;
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
			// TODO Auto-generated catch block
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
		scenario.write(getCucumberReportMessage());
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

	public static void scrollDown(int count) {
		for (int i = 0; i < count; i++) {
			try {
				executor.executeScript("window.scrollBy(0,325)");
				Thread.sleep(2000);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public static String tratativaReportElemento(String[] elemento) {

		String nomeObjMapeado = null;

		if (elemento[2] != null) {
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

			if (Prop.getProp("browserOrDevice").contains("mobile") && Mobile.getPlataforma().contains("ios")) {
				byType.add(MobileBy.AccessibilityId(obj));
				byType.add(By.xpath("//*[@name='" + obj + "']"));
				byType.add(By.xpath("//*[contains(@name,'" + obj + "')]"));
				byType.add(By.xpath("//*[contains(@label,'" + obj + "')]"));
			} else if (Prop.getProp("browserOrDevice").contains("mobile")
					&& Mobile.getPlataforma().contains("android")) {
				byType.add(By.id(MobileDriverInit.driverMobile.getCapabilities().getCapability("appPackage").toString()
						+ ":id/" + obj));
				byType.add(By.id(obj));
				byType.add(By.xpath("//*[contains(@content-desc,'" + obj + "')]"));
				byType.add(By.xpath("//*[@text='" + obj + "']"));
			} else {
				byType.add(By.xpath("//*[(contains[text,'" + obj + "')]"));
				byType.add(By.xpath("//*[@id='" + obj + "']"));
				byType.add(By.xpath("//*[@class='" + obj + "']"));
			}

//			byType.add(By.id("android" + ":id/" + obj));
			byType.add(By.xpath("//*[(contains[.,'" + obj + "')]"));
		}

		return byType;
	}

	public static WebElement findBy(String obj) {

		WebElement element = null;

		for (By by : listTypeBy(obj)) {
			try {
				element = driver.findElement(by);
				if (!Prop.getProp("browserOrDevice").contains("mobile")) {
					if (element.isDisplayed() == true) {
						borderStyle(element);
						Log.log("Elemento idenficado por '" + by.toString());
						return element;
					} else {
						return element = null;
					}
				} else if (element != null) {
					Log.log("Elemento idenficado por '" + by.toString());
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
				Log.log("Elemento idenficado por '" + by.toString());
				return listElements;
			} catch (Exception e) {
			}
		}
		return listElements;
	}

	public static void borderStyle(WebElement element) {
		if (!Prop.getProp("browserOrDevice").equals("mobile")) {
			try {
				executor.executeScript("arguments[0].setAttribute('style','border: solid 1px red');", element);
//				executor.executeScript("arguments[0].style.border = 'medium solid red';", element);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void validaElemento(String obj, Boolean[] assertObjReceved) {
		String acao = null;
		try {
			if (assertObjReceved[0] == true) {
				if (located == true) {
					acao = "Ação obrigatória com o elemento '" + obj + "' efetuada com sucesso";
					Log.log(acao);
					ActionsCommands.cucumberWriteReport(acao);
				} else
					Log.log("Ocorreu um problema com o elemento '" + obj + "'");
				Assert.assertTrue(located);
			}
		} catch (Exception e1) {
			if (located != true)
				Log.log("Elemento '" + obj + "' nao econtrado");
		}

	}

	public static void waitExistClick(String obj, Integer timeout, Boolean... assertObj) {
		WebElement element = null;
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {
			element = findBy(obj);
			if (element != null) {
				located = true;
				Log.log("Elemento '" + obj + "' encontrado");
				if (!Prop.getProp("browserOrDevice").equals("mobile")) {
					try {
						executor.executeScript("arguments[0].setAttribute('style','border: solid 1px blue');", element);
						// executor.executeScript("arguments[0].style.border = 'medium solid blue';",
						// element);
					} catch (Exception e) {
						;
					}
				}
				element.click();
				break;
			} else
				try {
					Log.log("Não foi possível encontrar o elemento '" + obj + "' na tentativa " + i + " de " + timeout);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					;
				}
		}
		validaElemento(obj, assertObjReceved);
	}

	public static void waitExistClick(String[] obj, Integer timeout, Boolean... assertObj) {
		WebElement element = null;
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {

			element = findBy(obj[deviceElement]);

			if (element != null) {
				located = true;
				Log.log("Elemento '" + tratativaReportElemento(obj) + "' encontrado");
				if (!Prop.getProp("browserOrDevice").equals("mobile")) {
					try {
						executor.executeScript("arguments[0].setAttribute('style','border: solid 1px blue');", element);
//						executor.executeScript("arguments[0].style.border = 'medium solid blue';", element);
					} catch (Exception e) {
						;
					}
				}
				element.click();
				break;
			} else
				try {
					Log.log("Não foi possível encontrar o elemento '" + tratativaReportElemento(obj) + "' na tentativa "
							+ i + " de " + timeout);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					;
				}
		}
		validaElemento(obj[deviceElement], assertObjReceved);
	}

	public static void waitExistClickAndPerform(String menuDropDown, String link, Integer timeout,
			Boolean... assertObj) {
		Actions actions = new Actions(driver);
		WebElement element1 = null;
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {
			element1 = findBy(menuDropDown);
			if (element1 != null && element1.isDisplayed()) {
				located = true;
				actions.moveToElement(element1);
				if (Prop.getProp("browserOrDevice").contains("chrome-h")) {
					actions.click();
				}
				actions.perform();
				if (!Prop.getProp("browserOrDevice").equals("mobile")) {
					try {
						executor.executeScript("arguments[0].setAttribute('style','border: solid 1px blue');",
								element1);
//						executor.executeScript("arguments[0].style.border = 'medium solid blue';", element1);
					} catch (Exception e) {
						;
					}
				}
				element1 = null;
				element1 = findBy(link);
				waitExistClick(link, 2);
				Log.log("Elemento '" + link + "' encontrado");
				break;
			} else
				try {
					Log.log("Não foi possível encontrar o elemento '" + menuDropDown + "' na tentativa " + i + " de "
							+ timeout);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					;
				}
		}
		validaElemento(link, assertObjReceved);
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
			element = findBy(obj);
			if (element != null) {
				located = true;
				Log.log("Elemento '" + obj + "' encontrado");
				if (!Prop.getProp("browserOrDevice").equals("mobile")) {
					try {
						executor.executeScript("arguments[0].setAttribute('style','border: solid 1px blue');", element);
//						executor.executeScript("arguments[0].style.border = 'medium solid blue';", element);
					} catch (Exception e) {
						;
					}
				}
				element.click();
				break;
			} else
				try {
					Log.log("Não foi possível encontrar o elemento '" + obj + "' na tentativa " + i + " de " + timeout);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					;
				}
		}
		validaElemento(obj, assertObjReceved);
	}

	public static void waitExistSet(String obj, String conteudo, Integer timeout, Boolean... assertObj) {
		WebElement element = null;
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {
			element = findBy(obj);
			if (element != null) {
				located = true;
				Log.log("Elemento '" + obj + "' encontrado");
				element.sendKeys(conteudo);
				break;
			} else
				try {
					Log.log("Não foi possível encontrar o elemento '" + obj + "' na tentativa " + i + " de " + timeout);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					;
				}
		}
		validaElemento(obj, assertObjReceved);
	}

	public static void waitExistSet(String[] obj, String conteudo, Integer timeout, Boolean... assertObj) {
		WebElement element = null;
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {
			element = findBy(obj[deviceElement]);
			if (element != null) {
				located = true;
				Log.log("Elemento '" + tratativaReportElemento(obj) + "' encontrado");
				element.sendKeys(conteudo);
				break;
			} else
				try {
					Log.log("Não foi possível encontrar o elemento '" + tratativaReportElemento(obj) + "' na tentativa "
							+ i + " de " + timeout);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					;
				}
		}
		validaElemento(obj[deviceElement], assertObjReceved);
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
			element = findBy(obj);
			if (element != null) {
				located = true;
				Log.log("Elemento '" + obj + "' encontrado");
				element.sendKeys(conteudo);
				break;
			} else
				try {
					Log.log("Não foi possível encontrar o elemento '" + obj + "' na tentativa " + i + " de " + timeout);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					;
				}
		}
		validaElemento(obj, assertObjReceved);
	}

	public static boolean waitExist(String obj, Integer timeout, Boolean... assertObj) {
		WebElement element = null;
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {
			element = findBy(obj);
			if (element != null) {
				located = true;
				Log.log("Elemento '" + obj + "' encontrado");
				break;
			} else
				try {
					Log.log("Não foi possível encontrar o elemento '" + obj + "' na tentativa " + i + " de " + timeout);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					;
				}
		}
		validaElemento(obj, assertObjReceved);
		return located;
	}

	public static WebElement waitExistWebElement(String obj, Integer timeout, Boolean... assertObj) {
		WebElement element = null;
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {
			element = findBy(obj);
			if (element != null) {
				located = true;
				Log.log("Elemento '" + obj + "' encontrado");
				break;
			} else
				try {
					Log.log("Não foi possível encontrar o elemento '" + obj + "' na tentativa " + i + " de " + timeout);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					;
				}
		}
		validaElemento(obj, assertObjReceved);
		return element;
	}

	public static String[] getStringElements(String obj, Integer timeout, Boolean... assertObj) {

		String[] elementos = null;
		List<WebElement> listElements = null;
		assertObjReceved = assertObj;
		located = false;

		for (int i = 1; i <= timeout; i++) {
			listElements = findByElements(obj);
			elementos = new String[listElements.size()];
			if (listElements != null) {
				located = true;
				int index = 0;
				for (WebElement elemento : listElements) {
					Log.log(elemento.getText());
					elementos[index] = elemento.getText();
					index++;
				}
				Log.log("Elementos '" + obj + "' encontrados");
				break;
			} else
				try {
					Log.log("Não foi possível encontrar o elemento '" + obj + "' na tentativa " + i + " de " + timeout);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					;
				}
		}
		validaElemento(obj, assertObjReceved);
		return elementos;

	}

	public static List<WebElement> getElements(String obj, Integer timeout, Boolean... assertObj) {
		List<WebElement> listElements = null;
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {
			listElements = findByElements(obj);
			if (listElements != null) {
				located = true;
				Log.log("Elementos '" + obj + "' encontrados");
				break;
			} else
				try {
					Log.log("Não foi possível encontrar o elemento '" + obj + "' na tentativa " + i + " de " + timeout);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					;
				}
		}
		validaElemento(obj, assertObjReceved);
		return listElements;
	}

	public static String waitExistGetText(String obj, Integer timeout, Boolean... assertObj) {
		WebElement element = null;
		String textoObtido = "";
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {
			element = findBy(obj);
			if (element != null) {
				if (Prop.getProp("browserOrDevice").contains("mobile") && Mobile.getPlataforma().equals("ios")) {
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
					Log.log("Elemento '" + obj + "' encontrado");
					try {
						executor.executeScript("arguments[0].style.backgroundColor = 'yellow';", element);
					} catch (Exception e) {
						;
					}
					Log.log("Texto obtido [ '" + textoObtido + "' ]");
					ActionsCommands.cucumberWriteReport("Texto obtido [ '" + textoObtido + "' ]");
					break;
				}
			} else
				try {
					Log.log("Não foi possível encontrar o elemento '" + obj + "' na tentativa " + i + " de " + timeout);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					;
				}
		}
		validaElemento(obj, assertObjReceved);
		return textoObtido;
	}

	public static String waitExistGetText(String[] obj, Integer timeout, Boolean... assertObj) {
		WebElement element = null;
		String textoObtido = "";
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {
			element = findBy(obj[deviceElement]);
			if (element != null) {
				if (Prop.getProp("browserOrDevice").contains("mobile") && Mobile.getPlataforma().equals("ios")) {
					textoObtido = element.getAttribute("label").toString();
				} else {
					textoObtido = element.getText().toString();
				}
				if (textoObtido.length() > 3) {
					located = true;
					Log.log("Elemento '" + tratativaReportElemento(obj) + "' encontrado");
					try {
						executor.executeScript("arguments[0].style.backgroundColor = 'yellow';", element);
					} catch (Exception e) {
						;
					}
					Log.log("Texto obtido [ '" + textoObtido + "' ]");
					ActionsCommands.cucumberWriteReport("Texto obtido [ '" + textoObtido + "' ]");
					break;
				}
			} else
				try {
					Log.log("Não foi possível encontrar o elemento '" + tratativaReportElemento(obj) + "' na tentativa "
							+ i + " de " + timeout);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					;
				}
		}
		validaElemento(obj[deviceElement], assertObjReceved);
		return textoObtido;
	}

	public static void waitExistSelectComboBox(String obj, String value, Integer timeout, Boolean... assertObj) {
		WebElement element = null;
		assertObjReceved = assertObj;
		located = false;
		for (int i = 1; i <= timeout; i++) {
			element = findBy(obj);
			if (element != null) {
				located = true;
				Log.log("Elemento '" + obj + "' encontrado");
				element.click();
				new Select(element).selectByVisibleText(value);
				;
				break;
			} else
				try {
					Log.log("Não foi possível encontrar o elemento '" + obj + "' na tentativa " + i + " de " + timeout);
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					;
				}
		}
		validaElemento(obj, assertObjReceved);
	}

	public static WebDriver newApp() {

		if (Functions.getAppRunner() != true) {

			if (driver != null) {
				Capabilities caps = MobileDriverInit.driverMobile.getCapabilities();
				if (!caps.toString().contains(Mobile.getApp().toLowerCase())) {
					Log.log("Iniciando novo app " + Mobile.getApp());
					driver.quit();
					driver = null;
					driver = MobileDriverInit.driver();
				} else {
					Log.log("Reiniciando o app " + Mobile.getApp());
					MobileDriverInit.driverMobile.resetApp();
				}
			} else {

				try {
					driver = MobileDriverInit.driver();
					Log.log("Appium driver inicializado com o app: " + Mobile.getApp());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {

			if (driver != null && Functions.reiniciaApp == null) {
				Functions.reiniciaApp = true;
				driver = MobileDriverInit.driverMobile;
			} else if (driver != null && Functions.reiniciaApp != null) {
				MobileDriverInit.driverMobile.resetApp();
				driver = MobileDriverInit.driverMobile;
			} else {
				driver = null;
				driver = MobileDriverInit.driver();
			}

		}
		return driver;
	}

	/*
	 * public static void newAppActivity() {
	 * 
	 * Mobile.setPackageActivities(Mobile.getApp());
	 * 
	 * Log.log(packageAndActivity[0]); Log.log(packageAndActivity[1]);
	 * 
	 * Activity activity = new Activity(packageAndActivity[0],
	 * packageAndActivity[1]); activity.setStopApp(false);
	 * 
	 * try { ((StartsActivity) driver).startActivity(activity); } catch (Exception
	 * e) { Log.log("Problemas na tentativa de iniciar o driver");
	 * e.printStackTrace(); } Log.log("App aberto pelos Activities packages: " +
	 * packageAndActivity[0] + " - " + packageAndActivity[1]); }
	 */
	public static void scrollUntilWebElement(String obj) {

		boolean objFinded = ActionsCommands.waitExist(obj, 1);

		if (objFinded == false) {
			int i = 0;
			while (objFinded == false) {

				ActionsCommands.scrollDown(1);

				objFinded = ActionsCommands.waitExist(obj, 1);
				i++;
				if (i == 10)
					break;
			}
		} else
			Log.log("Objeto não localizado na tela " + obj);
	}

	public static Scenario getScenario() {
		return scenario;
	}

	public static void setScenario(Scenario scenario) {
		ActionsCommands.scenario = scenario;
	}

}