package br.com.mpontoc.picaroon.core.commands;

import static io.restassured.RestAssured.given;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import br.com.mpontoc.picaroon.core.utils.Log;

public class RestAssuredCommands {

	@Test
	public static String responseString(String endpoint, String method, int statusCode) {

		String response =

				given()
				.urlEncodingEnabled(false)
				.when()
				.get(endpoint)
				.then()
				.log()
				.all()
				.statusCode(statusCode)
				.extract()
				.body()
				.asString();
		return response;
	}

	@Test
	public static String[] dadosCartao(String response) {
		
		String[] retornaDados = null;
		JSONObject dadosCartaoObj;
		
		try {
			dadosCartaoObj = new JSONObject(response.replaceAll("[\\[\\]]", ""));
			
			String[] cartao_dados = { 
					dadosCartaoObj.getString("txtCard").substring(3), 
					dadosCartaoObj.getString("txtPass") 
			};
			retornaDados = cartao_dados;
			Log.log("Card " + cartao_dados[0] + " - Pass " + cartao_dados[1]);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return retornaDados;
	}
	
}
