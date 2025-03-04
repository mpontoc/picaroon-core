package io.github.mpontoc.picaroon.core.utils;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.springframework.util.FileSystemUtils;
import org.zeroturnaround.zip.ZipUtil;

import io.github.mpontoc.picaroon.core.commands.ActionsCommands;
import io.github.mpontoc.picaroon.core.config.Execution;
import io.github.mpontoc.picaroon.core.drivers.DriverFactory;
import io.github.mpontoc.picaroon.core.mobile.Mobile;

public class Functions {

	public static boolean feriado;
	private static String pathReport = null;
	private static String pathReportCompleto = null;
	private static String descricaoReport = null;
	public static Execution execution = new Execution();

	public static void setupExecution() {
		Functions.apagaLog4j();
		System.setProperty("java.awt.headless", "false");
		printOSandFrame();
		apagaReportAntesExecucao();
		Execution.setHoraInicialTotal(retornaData().substring(11));
		DriverFactory.setupDriver();

	}

	public static String verifyOS() {
		String OS = null;
		if (System.getProperty("os.name").toLowerCase().indexOf("windows") > -1) {
			OS = "WINDOWS";
		} else {
			OS = "LINUX";
		}
		assertNotNull(OS);
		return OS;
	}

	public static String FindTextStringRegex(String string, String regex) {
		String stringRetorned = null;

		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(string);

		if (m.find() == true) {
			stringRetorned = m.group();
		} else {
			stringRetorned = "Text not located";
		}
		return stringRetorned;
	}

	public static String retornaData() {

		String data = "";

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-3:00"));
		data = dateFormat.format(new Date(System.currentTimeMillis()));

		return data;
	}

	public static String substractHours(String horaIninical, String horaFinal) {

		String horaSubtraida = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date d1 = null;
		Date d2 = null;
		Date d3 = null;

		try {
			d1 = dateFormat.parse(horaIninical);
			d2 = dateFormat.parse(horaFinal);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long timeDiff = d2.getTime() - d1.getTime();
		long diffHour = timeDiff / (60 * 60 * 1000) % 24;
		long diffMinutes = timeDiff / (60 * 1000) % 60;
		long diffSeconds = timeDiff / 1000 % 60;

		horaSubtraida = diffHour + ":" + diffMinutes + ":" + diffSeconds;

		try {
			d3 = dateFormat.parse(horaSubtraida);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		horaSubtraida = d3.toString().substring(11, 20);

		return horaSubtraida;

	}

	public static void printInfoExec() {

		Report.cucumberWriteReport(" Execution Data \n Date and hour: " + retornaData());
		Execution.setHoraInicial(null);
		Execution.setHoraInicial(retornaData().substring(11));
		Report.cucumberWriteReport("\n " + printOSandFrame());

		if (PropertiesVariables.BROWSER_OR_MOBILE.equals("mobile")) {

			Report.cucumberWriteReport("\n Plataforma : " + Mobile.getPlataforma());
			Report.cucumberWriteReport("\n Device : " + Mobile.getDeviceName());
			Report.cucumberWriteReport("\n UDID : " + Mobile.getDeviceUDID());
		} else {
			Report.cucumberWriteReport("\n Browser : " + PropertiesVariables.BROWSER_OR_MOBILE);
		}
		if (getDescricaoReport() != null) {
			Report.cucumberWriteReport("\n More details : " + getDescricaoReport());
		}
		ActionsCommands.setPrintedInfo(true);
	}

	public static void printTimeExecution() {
		String finalHora = null;
		finalHora = Functions.retornaData().substring(11);
		Report.cucumberWriteReport(
				" Execution time : " + Functions.substractHours(Execution.getHoraInicial(), finalHora));
	}

	public static Boolean verificaFeriado() {

		feriado = false;
		String data = "";

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-3:00"));
		data = dateFormat.format(new Date(System.currentTimeMillis())).substring(0, 10);
		Log.log("Data de hoje " + data);

		try {
			String responseGetDia = given().when().get("http://elekto.com.br/api/Calendars/br-SP").then()
					.statusCode(200).extract().body().asString();

			Log.log("imprimindo o get " + responseGetDia);

			if (responseGetDia.contains(data)) {
				feriado = true;
				Log.log("Hoje é feriado em São Paulo " + data);
			} else {
				feriado = false;
				Log.log("Hoje não é feriado em São Paulo " + data);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void waitSeconds(int segundos) {

		int segundosConvertidos = segundos * 1000;

		try {
			Thread.sleep(segundosConvertidos);
		} catch (InterruptedException e) {
			;
		}
	}

	public static void setPropDriver() {

		ExternalFunctions.processKill();

		String OS = verifyOS();

		if (OS == "LINUX") {
			System.setProperty("webdriver.chrome.driver", "lib/webdriver/linux/chromedriver");
			System.setProperty("webdriver.chrome.verboseLogging", "false");
			System.setProperty("webdriver.chrome.silentOutput", "true");
			System.setProperty("webdriver.gecko.driver", "lib/webdriver/linux/geckodriver");
			System.setProperty("webdriver.firefox.logfile", "/dev/null");

		} else {
			System.setProperty("webdriver.chrome.driver", ".\\lib\\webdriver\\chromedriver.exe");
			System.setProperty("webdriver.chrome.verboseLogging", "false");
			System.setProperty("webdriver.chrome.silentOutput", "true");
			System.setProperty("webdriver.gecko.driver", ".\\lib\\webdriver\\geckodriver.exe");
			System.setProperty("webdriver.firefox.logfile", "/dev/null");
		}
	}

	public static void apagaLog4j() {

		String pathLog = System.getProperty("user.dir") + File.separator + "target" + File.separator + "log";

		File dirLog = new File(pathLog);
		File[] listFiles = dirLog.listFiles();
		if (listFiles != null) {
			for (File file : listFiles) {
				if (file.getName().contains(".log")) {
					file.delete();
				}
				Log.log("Log file deleted - " + file.getName());
			}
		}
	}

	public static void apagaReportAntesExecucao() {

		String pathDefault = System.getProperty("user.dir") + File.separator + "target" + File.separator
				+ "cucumber-reports";

		setPathReportCompleto(System.getProperty("user.dir") + File.separator + "target" + File.separator
				+ "cucumber-reports" + File.separator + Functions.getPathReport());

		File pathDefault_ = new File(pathDefault);

		if (!pathDefault_.exists())
			pathDefault_.mkdir();

		try {
			File dir = null;
			dir = new File(getPathReportCompleto());
			File[] listFiles = dir.listFiles();

			if (listFiles != null) {

				for (File file : listFiles) {
					if (file.getName().contains(".png") == false) {
						Log.log("The file not be deleted " + file.getName());
					} else {
						Log.log("Deleting " + file.getName());
						file.delete();
					}
				}
			}

			Log.log("Files deleted successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public static void zipReportFiles() {

		String pathReportBackup = null;
		if (PropertiesVariables.BACKUP_REPORTS.equals("true")) {

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
			dateFormat.setTimeZone(TimeZone.getTimeZone("GMT-3:00"));
			String data = dateFormat.format(new Date(System.currentTimeMillis()));
			String pathReport = System.getProperty("user.dir") + File.separator + "target" + File.separator
					+ "cucumber-reports" + File.separator + getPathReport();

			pathReportBackup = System.getProperty("user.dir") + File.separator + "target" + File.separator
					+ "cucumber-reports-backup";

			File log4j = new File(System.getProperty("user.dir") + File.separator + "target" + File.separator + "log");

			try {
				FileSystemUtils.copyRecursively(log4j, new File(pathReport));
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			File path = new File(pathReportBackup);
			String pathReportZip = pathReportBackup + File.separator + "Report_" + data + ".zip";

			if (!path.exists())
				path.mkdir();

			try {
				ZipUtil.pack(new File(pathReport), new File(pathReportZip));
				Log.log("Backup of Report saved on path: " + pathReportZip);
			} catch (Exception e) {
				Log.log("Cannot zip a folder to Report");
				e.printStackTrace();
			}
		}
	}

	public static String printOSandFrame() {

		String osFrame = null;
		osFrame = "Running on " + System.getProperty("os.name") + " *** by picaroon framework ***";
		Log.log(osFrame);
		return osFrame;

	}

	public static String getSystemClipboard() {

		// System.setProperty("java.awt.headless", "false");
		Toolkit toolkit = null;
		Clipboard clipboard = null;
		toolkit = Toolkit.getDefaultToolkit();
		clipboard = toolkit.getSystemClipboard();
		String result = null;
		try {
			result = (String) clipboard.getData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getPathReport() {
		return pathReport;
	}

	public static void setPathReport(String pathReport) {
		Functions.pathReport = pathReport;
	}

	public static String getPathReportCompleto() {
		return pathReportCompleto;
	}

	public static void setPathReportCompleto(String pathReportCompleto) {
		Functions.pathReportCompleto = pathReportCompleto;
	}

	public static String getDescricaoReport() {
		return descricaoReport;
	}

	public static void setDescricaoReport(String descricaoReport) {
		Functions.descricaoReport = descricaoReport;
	}


}