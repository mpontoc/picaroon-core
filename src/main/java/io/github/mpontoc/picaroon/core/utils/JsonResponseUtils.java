package io.github.mpontoc.picaroon.core.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonResponseUtils {
	
public static String getFieldJsonResponseString(String response, String field) {
		
		String fieldReturned = null;
		JSONObject responseObj;
		
		System.out.println(response);

		try {
			responseObj = new JSONObject(response);
			fieldReturned = responseObj.getString(field);
			Log.log("Dado do campo " + fieldReturned);
			return fieldReturned;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getArrayJsonResponseSingleFieldString(String response, String nameObjectJson, String field) {

		JSONObject responseObj;
		String fieldReturned = null;
		try {

			responseObj = new JSONObject(response.replaceAll("[\\[\\]]", ""));
			JSONObject indexJson = (JSONObject) responseObj;
			JSONObject getFields = (JSONObject) indexJson.get(nameObjectJson);
			fieldReturned = (String) getFields.get(field);
			return fieldReturned;

		} catch (Exception e) {

			e.printStackTrace();
		}
		
		return null;

	}

	public static JSONObject getArrayJsonResponseMoreOneFieldString(String response, String nameObjectJson, String field, String value) {
		
		JSONArray arrayObj = null;
		String fieldReturned = null;
		String arrValue = null;
		JSONObject getValueFieldArray = null;
		
			JSONObject jsonOBJ;
			try {
				jsonOBJ = new JSONObject(response);
				arrayObj= jsonOBJ.getJSONArray(nameObjectJson);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		

			for (int i = 0; i < arrayObj.length(); i++) {

				try {
					getValueFieldArray = arrayObj.getJSONObject(i);
					fieldReturned = (String) getValueFieldArray.get(field);
				} catch (JSONException e) {
				}
				
				if(fieldReturned.equals(value)) {
					
					Log.log(fieldReturned);
					arrValue = getValueFieldArray.toString() ;
					
					Log.log(arrValue);
					
					return getValueFieldArray;
				}
			}

		return null;
	}

}
