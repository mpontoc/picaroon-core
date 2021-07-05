package br.com.mpontoc.picaroon.core.commands;

import static io.restassured.RestAssured.given;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

public class RestAssuredCommands {

	@Test
	public static String getResponseString(String endpoint) {

		String response =

				given()
					.urlEncodingEnabled(false)
					.when()
					.get(endpoint)
					.then()
					.log()
					.all()
					.extract()
					.body()
					.asString();
		
		return response;
	}

	@Test
	public static String getJsonField(String response, String field) {
		String returnDate = null;
		JSONObject dataField;
		try {
			dataField = new JSONObject(response);
			returnDate = dataField.getString(field);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return returnDate;
	}
}
