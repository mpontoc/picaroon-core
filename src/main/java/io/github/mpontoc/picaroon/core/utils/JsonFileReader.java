package io.github.mpontoc.picaroon.core.utils;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;

public class JsonFileReader {

    private static String PATH_TO_JSON_FILES = "./src/test/resources/json/";

    public static String getJsonSingleField(String nameFileJson, String nameObjectJson, String field) {

        JSONParser parser = new JSONParser();
        String fieldReturned = null;
        try {

            Object obj = parser.parse(new FileReader(PATH_TO_JSON_FILES + nameFileJson));
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
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(PATH_TO_JSON_FILES + nameFileJson));

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

            Object obj = parser.parse(new FileReader(PATH_TO_JSON_FILES + nameFileJson));
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

            Object obj = parser.parse(new FileReader(PATH_TO_JSON_FILES + nameFileJson));
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
}
