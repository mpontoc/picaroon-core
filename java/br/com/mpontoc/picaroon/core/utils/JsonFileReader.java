package br.com.mpontoc.picaroon.core.utils;

import java.io.FileReader;

import org.junit.Test;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;

public class JsonFileReader {

	@Test
	public void receiveSingleJson() {

		JSONParser parser = new JSONParser();
		try {

			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("./src/test/resources/json/gmail.json"));
			Log.log(jsonObject.toString());

			Object obj = new Object();
			obj = jsonObject;

			JSONObject indexJson = (JSONObject) obj;

			JSONObject getValueField = (JSONObject) indexJson.get("gmail");

			String firstName = (String) getValueField.get("user");
			Log.log(firstName);

			String lastName = (String) getValueField.get("pass");
			Log.log(lastName);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public static String getJsonSingleField(String nameArchiveJson, String nameObjectJson, String field) {
		
		JSONParser parser = new JSONParser();
		
		String fieldReturn = "";
		
		try {

			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("./src/test/resources/json/" + nameArchiveJson));
			Log.log(jsonObject.toString());

			Object obj = new Object();
			obj = jsonObject;

			JSONObject indexJson = (JSONObject) obj;
			JSONObject getFields = (JSONObject) indexJson.get(nameObjectJson);
			fieldReturn = (String) getFields.get(field);

			return fieldReturn;

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}
	
	@Test
	public void testGetSingleJson() {
		
		String user = getJsonSingleField("gmail.json", "gmail", "user");
		Log.log("O usuario eh " + user);
		
	}

	@Test
	public void receiveArrayJson() {

		JSONParser parser = new JSONParser();
		try {
			JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("./src/test/resources/json/employees.json"));

			for (Object obj : jsonArray) {

				JSONObject indexJsonArray = (JSONObject) obj;
				Log.log(indexJsonArray.toString());

				JSONObject getValueFieldArray = (JSONObject) indexJsonArray.get("employee");

				String firstName = (String) getValueFieldArray.get("firstName");
				Log.log(firstName);

				String lastName = (String) getValueFieldArray.get("lastName");
				Log.log(lastName);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
	}

}
