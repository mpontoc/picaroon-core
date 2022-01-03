package io.github.mpontoc.picaroon.core.utils;

import java.io.IOException;

import io.github.mpontoc.picaroon.core.constants.PropertiesConstants;

public class ExternalFunctions {

	static Runtime rt = Runtime.getRuntime();

	public static void processKill() {

		String killBrowser = PropertiesConstants.KILL_BROWSER;

		if (killBrowser.equals("true")) {

			String Browser = PropertiesConstants.BROWSER_OR_MOBILE;

			if (Functions.verifyOS() == "LINUX") {

				String killFirefox = "pkill firefox";
				String killFirefoxDriver = "pkill geckodriver";
				String killChrome = "pkill chrome";
				String killChromeDriver = "pkill chromedriver";
				String verifyVersionChrome = "google-chrome -version";
				String verifyVersoinFirefox = "firefox -version";

				try {

					if (Browser == "firefox") {
						rt.exec(verifyVersoinFirefox).getOutputStream().toString();
						rt.exec(verifyVersionChrome).getOutputStream();
						rt.exec(killFirefox);
						rt.exec(killFirefoxDriver);
						Log.log(verifyVersoinFirefox);
						Log.log(verifyVersionChrome);

					} else {
						rt.exec(killChrome);
						rt.exec(killChromeDriver);
						rt.exec(verifyVersoinFirefox);
						rt.exec(verifyVersionChrome);
					}

				} catch (IOException e) {
					e.printStackTrace();
				}

			} else {

				String killWinFirefox = "taskkill /f /im firefox.exe";
				String killWinFirefoxDriver = "taskkill /f /im geckodriver.exe";
				String killWinChrome = "taskkill /f /im chrome.exe";
				String killWinChromeDriver = "taskkill /f /im chromedriver.exe";

				try {

					if (Browser.contains("firefox")) {
						rt.exec(killWinFirefox);
						rt.exec(killWinFirefoxDriver);
					} else {
						rt.exec(killWinChrome);
						rt.exec(killWinChromeDriver);
					}

				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}

	}

	/*
	 * public static void uninstallApps() {
	 * 
	 * Mobile.setPackageActivities(Mobile.getApp());
	 * 
	 * String[] packageAndActivity = Mobile.getPackageActivities();
	 * 
	 * String commands = "adb uninstall " + packageAndActivity[0];
	 * 
	 * Process proc = null; try { proc = rt.exec(commands); } catch (IOException e)
	 * { e.printStackTrace(); }
	 * 
	 * BufferedReader stdInput = new BufferedReader(new
	 * InputStreamReader(proc.getInputStream()));
	 * 
	 * // Read the output from the command
	 * Log.log("Here is the output of the uninstall command: \n"); String s = null;
	 * 
	 * try { while ((s = stdInput.readLine()) != null) { Log.log(s); } } catch
	 * (IOException e) { e.printStackTrace(); } }
	 * 
	 * public static void newAppCmd() {
	 * Mobile.setPackageActivities(Mobile.getApp());
	 * 
	 * String[] packageAndActivity = Mobile.getPackageActivities();
	 * 
	 * String commands = "adb shell am start -n " + packageAndActivity[0] + "/" +
	 * packageAndActivity[1];
	 * 
	 * Process proc = null; try { proc = rt.exec(commands); } catch (IOException e)
	 * { // TODO Auto-generated catch block e.printStackTrace(); }
	 * 
	 * BufferedReader stdInput = new BufferedReader(new
	 * InputStreamReader(proc.getInputStream()));
	 * 
	 * // Read the output from the command
	 * Log.log("Here is the output of the uninstall command: \n"); String s = null;
	 * 
	 * try { while ((s = stdInput.readLine()) != null) { Log.log(s); } } catch
	 * (IOException e) { e.printStackTrace(); } }
	 * 
	 * public static void clearApp() {
	 * 
	 * String[] packageAndActivity = Mobile.getPackageActivities();
	 * 
	 * String command = "adb shell pm clear " + packageAndActivity[0];
	 * 
	 * try { rt.exec(command); } catch (IOException e) { e.printStackTrace(); } }
	 */
}
