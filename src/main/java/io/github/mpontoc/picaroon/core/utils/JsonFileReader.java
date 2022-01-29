package io.github.mpontoc.picaroon.core.utils;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonFileReader {

	private static String pathToJson;

	private static String PATH_TO_JSON_FILES;

	static void setPathJson() {

		if (getPathToJson() == null) {
			PATH_TO_JSON_FILES = "./src/test/resources/json/";
		} else {
			PATH_TO_JSON_FILES = getPathToJson();
		}

	}

	public static JSONObject getJsonSingleObj(String nameFileJson, String nameObjectJson) {

		setPathJson();

		JSONParser parser = new JSONParser();
		try {

			Object obj = parser.parse(new FileReader(PATH_TO_JSON_FILES + nameFileJson));
			Log.log(obj.toString());
			JSONObject indexJson = (JSONObject) obj;
			return (JSONObject) indexJson.get(nameObjectJson);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;
	}

	public static String getJsonSingleObjString(String nameFileJson, String nameObjectJson) {

		setPathJson();

		JSONParser parser = new JSONParser();
		JSONObject fieldReturned = null;
		try {

			Object obj = parser.parse(new FileReader(PATH_TO_JSON_FILES + nameFileJson));
			Log.log(obj.toString());
			JSONObject indexJson = (JSONObject) obj;
			fieldReturned = (JSONObject) indexJson.get(nameObjectJson);
			Log.log(fieldReturned.toJSONString());

			return fieldReturned.toJSONString();

		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;
	}

	public static String getJsonSingleFieldString(String nameFileJson, String nameObjectJson, String field) {

		setPathJson();

		JSONParser parser = new JSONParser();
		try {

			Object obj = parser.parse(new FileReader(PATH_TO_JSON_FILES + nameFileJson));
			Log.log(obj.toString());
			JSONObject indexJson = (JSONObject) obj;
			JSONObject getFields = (JSONObject) indexJson.get(nameObjectJson);
			return (String) getFields.get(field);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;
	}

	public static String getArrayJsonString(String nameFileJson, String nameObjectJson, String field) {

		setPathJson();

		JSONParser parser = new JSONParser();
		String fieldReturned = null;
		try {
			JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(PATH_TO_JSON_FILES + nameFileJson));

			for (Object obj : jsonArray) {

				JSONObject indexJsonArray = (JSONObject) obj;
				Log.log(indexJsonArray.toString());

				JSONObject getValueFieldArray = (JSONObject) indexJsonArray.get(nameObjectJson);

				Log.log(fieldReturned);
				fieldReturned = (String) getValueFieldArray.get(field);

			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;
	}

	public static String getJsonToString(String nameFileJson) {

		setPathJson();

		JSONParser parser = new JSONParser();

		try {

			Object obj = parser.parse(new FileReader(PATH_TO_JSON_FILES + nameFileJson));
			Log.log(obj.toString());
			JSONObject objJson = (JSONObject) obj;
			return objJson.toJSONString();

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	public static JSONObject getJsonObject(String nameFileJson) {

		setPathJson();

		JSONParser parser = new JSONParser();
		JSONObject objJson = null;

		try {

			Object obj = parser.parse(new FileReader(PATH_TO_JSON_FILES + nameFileJson));
			Log.log(obj.toString());
			objJson = (JSONObject) obj;
			Log.log(objJson.toString());
			return objJson;

		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	public static JSONObject getCapsJson(String nameFileJson, String capsNameDeviceOrApp) {

		setPathJson();

		JSONParser parser = new JSONParser();
		try {
			JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(PATH_TO_JSON_FILES + nameFileJson));

			for (Object obj : jsonArray) {

				JSONObject caps = (JSONObject) obj;
				Log.log(caps.toString());

				if (caps.get("name").toString().equalsIgnoreCase(capsNameDeviceOrApp)) {

					return (JSONObject) caps.get("caps");
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return null;
	}

	/**
	 * @return the pathToJson
	 */
	public static String getPathToJson() {
		return pathToJson;
	}

	/**
	 * @param pathToJson the pathToJson to set
	 */
	public static void setPathToJson(String pathToJson) {
		JsonFileReader.pathToJson = pathToJson;
	}

}
