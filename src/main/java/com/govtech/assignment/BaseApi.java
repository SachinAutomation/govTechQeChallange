package com.govtech.assignment;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import com.govtech.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class BaseApi extends BaseUI{


	public static JSONObject readJsonFile(String jsonFile) throws IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = null;
		try {
			// Parsing the contents of the JSON file
			jsonObject = (JSONObject) jsonParser.parse(new FileReader(jsonFile));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
}
