package br.com.mpontoc.picaroon.core.commands;

import org.openqa.selenium.JavascriptExecutor;

import br.com.mpontoc.picaroon.core.utils.Functions;
import br.com.mpontoc.picaroon.core.utils.Log;

public class WebCommands {

	public static JavascriptExecutor executor = null;

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

	public static void scrollUntilWebElement(String obj) {

		boolean objlocated = ActionsCommands.waitExist(obj, 1);

		if (objlocated == false) {
			int i = 0;
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

}
