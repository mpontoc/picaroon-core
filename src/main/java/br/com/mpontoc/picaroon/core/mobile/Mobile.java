package br.com.mpontoc.picaroon.core.mobile;

import java.util.HashMap;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.mpontoc.picaroon.core.utils.JsonFileReader;

public class Mobile {

	private static String app = null;
	private static String plataforma = null;
	private static String deviceUDID = null;
	private static String deviceName = null;
	private static String pathIOSapps = null;
	private static String capsFileJosn = null;

	@SuppressWarnings("unchecked")
	private static HashMap<String, Object> convertCapsToHashMap(String nameFileJson, String capsNameDevice) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(JsonFileReader.getCapsJson(nameFileJson, capsNameDevice).toString(),
					HashMap.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static DesiredCapabilities caps(String nameFileJson, String capsNameDevice) {
		HashMap<String, Object> caps = convertCapsToHashMap(nameFileJson, capsNameDevice);
		return new DesiredCapabilities(caps);
	}

	public static String getPlataforma() {
		return plataforma;
	}

	public static void setPlataforma(String device) {
		Mobile.plataforma = device;
	}

	public static void setApp(String app) {
		Mobile.app = app;
	}

	public static String getApp() {
		return app;
	}

	public static String getDeviceName() {
		return deviceName;
	}

	public static void setDeviceName(String deviceName) {
		Mobile.deviceName = deviceName;
	}

	public static String getDeviceUDID() {
		return deviceUDID;
	}

	public static void setDeviceUDID(String deviceUDID) {
		Mobile.deviceUDID = deviceUDID;
	}

	public static String getPathIOSapps() {
		return pathIOSapps;
	}

	public static void setPathIOSapps(String pathIOSapps) {
		Mobile.pathIOSapps = pathIOSapps;
	}

	public static String getCapsFileJosn() {
		return capsFileJosn;
	}

	public static void setCapsFileJosn(String capsFileJosn) {
		Mobile.capsFileJosn = capsFileJosn;
	}

}