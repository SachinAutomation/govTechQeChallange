package com.govtech.testcases;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.joda.time.DateTime;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import com.govtech.assignment.BaseApi;
import com.govtech.assignment.BaseUI;
import com.govtech.assignment.DbConnection;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

public class UserStory1 extends BaseUI {
	
	String filePath = "src/test/resources/jsonfile/userStory1.json";

	/*
	 * AC 1 - Create single working class hero via API call
	 */
	@Test
	public void createWrokingClassHero() {
		{
			
			File file = new File(filePath);
			String postReq = "/api/v1/hero";
			RestAssured.baseURI = "http://localhost:9997";
			RequestSpecification request = RestAssured.given().contentType("application/json").body(file);
			Response response = request.post(postReq);
			int statusCode = response.getStatusCode();
			System.out.println(statusCode);
			System.out.println(response.asString());
		}
	}
	
	/*
	 * AC 2 - do field validation
	 */
	@Test
	public void verifyJsonFields() throws IOException, ParseException {
		{
			JSONObject jsonObject= BaseApi.readJsonFile(filePath);
			//validate natid format and number range
			 String natid = (String) jsonObject.get("natid");
			 Assert.assertTrue(natid.matches("^natid-\\d{2}$"));
			 String ch = natid.replace("natid-", "");
			 Assert.assertTrue(ch.matches("(0|([1-9]\\d{0,7}))?"));
			 
			 //verify name field contains alphabet and length is between 1 and 1oo
			 String name = (String) jsonObject.get("name");
			 Assert.assertTrue(name.matches("^[a-zA-Z]*$"));
			 Assert.assertTrue(name.length()>=1 && name.length()<=100);
			
			 // Verify Genger Must be MALE or FEMALE
			 String gender = (String) jsonObject.get("gender");
			 Assert.assertTrue(gender.equals("MALE") || gender.equals("FEMALE"));
			 
			 // verify data format and it should not be a future date
			 String birthDate = (String) jsonObject.get("birthDate");
			 Assert.assertTrue(birthDate.matches("^\\d{4}-\\d{2}-\\d{2}'T'\\d{2}:\\d{2}.\\d{2}$"));
			 DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd'T'HH:mm:ss");  
			   LocalDateTime now = LocalDateTime.now();  
			   String currentDate= dtf.format(now); 
			   Assert.assertTrue(birthDate.compareTo(currentDate)<0);
			   
			 //Verify salary is a decimal and greater than 0
			 String salary = (String) jsonObject.get("salary");
			 int sal=Integer.parseInt(salary);
			 Assert.assertTrue(sal %1 !=0 && sal>0);
			 
			//Verify taxPaid is a decimal and greater than 0
			 String taxPaid = (String) jsonObject.get("taxPaid");
			 int tax=Integer.parseInt(salary);
			 Assert.assertTrue(tax %1 !=0 && sal>0);
			 
			 // Verify browniePoints is nullable
			 String browniePoints = (String) jsonObject.get("browniePoints");
			 Assert.assertTrue(browniePoints.equals("NULL") && browniePoints.equals("\\d"));
			 
			 
		}
	}

	/*
	 * AC 3 - If the natid already exists in the database, return error status code
	 * 400
	 */

	@Test
	public void VerifyClassHeroExist() {
		{
			File file = new File(filePath);
			String postReq = "/api/v1/hero";
			RestAssured.baseURI = "http://localhost:9997";
			RequestSpecification request = RestAssured.given().contentType("application/json").body(file);
			Response response = request.post(postReq);
			response.then().assertThat().statusCode(400);
			JsonPath jsonPath = response.jsonPath();
			String errorMessage = jsonPath.get("errorMsg");
			System.out.println(errorMessage);
			Assert.assertTrue(errorMessage.contains("already exists!"));

		}
	}

	/*
	 * AC 4 - Verify record created in Working class Hero Table
	 */
	@Test
	public void verifyRecordInDatabase() throws ClassNotFoundException, SQLException {

		ResultSet rs = verifyRecordInDB("SELECT COUNT() FROM working_class_heroes WHERE natid = 'natid-12';");

		if (rs == null)
			System.out.println("Record is not present");
		else {
			System.out.println("record exist in the table" + rs);
		}

		closeDBConnection();
	}

}
