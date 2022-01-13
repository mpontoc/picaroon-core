package io.github.mpontoc.picaroon.core.execution.report;

import static io.github.mpontoc.picaroon.core.drivers.DriverFactory.driver;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import io.cucumber.java.Scenario;
import io.github.mpontoc.picaroon.core.commands.ActionsCommands;
import io.github.mpontoc.picaroon.core.execution.Execution;
import io.github.mpontoc.picaroon.core.utils.Log;
import io.github.mpontoc.picaroon.core.utils.PropertiesVariables;

public class Report {

	// Cucumber Report

	private static String cucumberReportMessage = null;

	public static void printScreen() {

		printScreenAfterStep(ActionsCommands.getScenario(), true);
	}

	public static void printScreenAfterStep(Scenario scenario, Boolean... printAnyway) {

		Boolean print = false;

		if (printAnyway.length > 0) {
			print = printAnyway[0];
		}

		if (PropertiesVariables.PRINT_AFTER_STEPS.equals("true")
				&& !PropertiesVariables.BROWSER_OR_MOBILE.contains("false") || print) {
			if (Execution.getIsFirstRun() == true) {
				scenario.log("\n");
				scenario.attach(resizeScreenshot(), "image/png", scenario.getName());
			}
			scenario.log("\n");
			Execution.setIsFirstRun(false);
		} else {
			Log.log("Already printed on cucumber Report or only printed after scenario execution");
		}
	}

	public static byte[] resizeScreenshot() {
		int width = 0;
		int height = 0;
		byte[] imageBytes = null;

		final File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		if (PropertiesVariables.BROWSER_OR_MOBILE.contains("mobile")) {
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
		writeReportStep(ActionsCommands.getScenario());
	}

	public static void writeReportStep(Scenario scenario) {
		scenario = ActionsCommands.getScenario();
		scenario.log(getCucumberReportMessage());
		cucumberReportMessage = "";
	}

	public static void printReportSlyled(String text) {
		StringBuilder html = new StringBuilder();
		html.append("<p style='background-color:Black;color:white'>" + text + "</p>");
		cucumberWriteReport(html.toString());
	}

}
