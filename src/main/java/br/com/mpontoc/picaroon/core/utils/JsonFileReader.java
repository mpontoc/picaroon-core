package br.com.mpontoc.picaroon.core.utils;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;

public class JsonFileReader {

	public static String getJsonSingleField(String nameFileJson, String nameObjectJson, String field) {

		JSONParser parser = new JSONParser();
		String fieldReturned = null;
		try {

			Object obj = parser.parse(new FileReader("./src/test/resources/json/" + nameFileJson));
			Log.log(obj.toString());
			JSONObject indexJson = (JSONObject) obj;
			JSONObject getFields = (JSONObject) indexJson.get(nameObjectJson);
			fieldReturned = (String) getFields.get(field);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return fieldReturned;
	}

	public static String getArrayJsonString(String nameFileJson, String nameObjectJson, String field) {

		JSONParser parser = new JSONParser();
		String fieldReturned = null;
		try {
			JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("./src/test/resources/json/" + nameFileJson));

			for (Object obj : jsonArray) {

				JSONObject indexJsonArray = (JSONObject) obj;
				Log.log(indexJsonArray.toString());

				JSONObject getValueFieldArray = (JSONObject) indexJsonArray.get(nameObjectJson);

				fieldReturned = (String) getValueFieldArray.get(field);
				Log.log(fieldReturned);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return fieldReturned;
	}

	public static String getJsonToString(String nameFileJson) {

		JSONParser parser = new JSONParser();
		String jsonToString = null;

		try {

			Object obj = parser.parse(new FileReader("./src/test/resources/json/" + nameFileJson));
			Log.log(obj.toString());
			JSONObject objJson = (JSONObject) obj;
			jsonToString = objJson.toJSONString();

		} catch (Exception e) {

			e.printStackTrace();
		}
		return jsonToString;
	}

	public static JSONObject getJsonObject(String nameFileJson) {

		JSONParser parser = new JSONParser();
		JSONObject objJson = null;

		try {

			Object obj = parser.parse(new FileReader("./src/test/resources/json/" + nameFileJson));
			Log.log(obj.toString());
			objJson = (JSONObject) obj;
			Log.log(objJson.toString());

		} catch (Exception e) {

			e.printStackTrace();
		}
		return objJson;
	}

	public static JSONObject getCapsJson(String nameFileJson, String capsNameDeviceOrApp) {

		JSONParser parser = new JSONParser();
		try {
			JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("./src/test/resources/json/" + nameFileJson));

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

//	https://medium.com/geekculture/how-to-set-up-appium-desired-capabilities-from-a-json-file-91b3e0bb16dc#id_token=eyJhbGciOiJSUzI1NiIsImtpZCI6ImNkNDliMmFiMTZlMWU5YTQ5NmM4MjM5ZGFjMGRhZGQwOWQ0NDMwMTIiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJuYmYiOjE2MjE2ODQ1MTcsImF1ZCI6IjIxNjI5NjAzNTgzNC1rMWs2cWUwNjBzMnRwMmEyamFtNGxqZGNtczAwc3R0Zy5hcHBzLmdvb2dsZXVzZXJjb250ZW50LmNvbSIsInN1YiI6IjEwMzIxMzUwNDg5MjU3MTIzNDE3NiIsImVtYWlsIjoibXBvbnRvY0BnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiYXpwIjoiMjE2Mjk2MDM1ODM0LWsxazZxZTA2MHMydHAyYTJqYW00bGpkY21zMDBzdHRnLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiaWF0IjoxNjIxNjg0ODE3LCJleHAiOjE2MjE2ODg0MTcsImp0aSI6IjgwYjliZmI2MWVmNWJjYjRlOTRkMWRiODIzZmI3NjBkM2YwZjJlYTUifQ.B5bId2zNH_vV5TPW3gLWJ6lKC-ANmt-2Az1lPlK999M4QTwE0M6wCZ5tC7JGAdrBSNNcdDqc6fgzbhQJibHTrMTDwYVWNN5Do34XjF4eD9BkcJ-7ScKI0meENgoR4HTc2JTijGjI5gOo0NlY-0tu0exdmMrYRC8nqpaH4-EdiBO3uPhOpyBJStOlUkdlpa7qHP6vLO6tyJIWh_aTUitlx_tNnVBYXEVLx4yMktYupgwb00tAfdZf0ESh03_UNc-4dJsaJGD0Fq1ctciUk9lWYDpeQVkQrG89USEQz0PVTv1zaiERvNeqgnq6X25dP41Xcd-Jt_O_AEJw_FsEJpEnnA

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

	@Test
	public void testGetSingleJson() {

		String user = getJsonSingleField("gmail.json", "gmail", "user");
		Log.log("O usuario eh " + user);

	}

}
