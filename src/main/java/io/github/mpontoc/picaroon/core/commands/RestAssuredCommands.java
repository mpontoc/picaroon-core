package io.github.mpontoc.picaroon.core.commands;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;

public class RestAssuredCommands {

	public static String getSimpleResponseString(String endpoint, String... token) {

		String response =

				given()
					.urlEncodingEnabled(false)
					.relaxedHTTPSValidation()
					.header("Authorization", token[0])
					.log()
					.all()
					.when()
					.get(endpoint)
					.then()
					.log().all().extract().body().asString();
		
		return response;
	}
	
	public static Response getSimpleResponse(String endpoint, String... token) {

		Response response =

				given()
					.urlEncodingEnabled(false)
					.relaxedHTTPSValidation()
					.header("Authorization", token[0])
					.log()
					.all()
					.when()
					.get(endpoint)
					.then()
					.log().all().extract().response();
		
		return response;
	}
}
