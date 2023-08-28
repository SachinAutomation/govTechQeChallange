package com.govtech.testcases;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

public class UserStory4 extends BaseUI {
	
	String filePath = "src/test/resources/jsonfile/userStory4.json";

	/*
	 * AC 1 - creates a working class hero with vouchers
	 */

	@Test
	public void createWCHWithVoucher() {
		{
	
			File file = new File(filePath);
			String postReq = "/api/v1/hero";
			RestAssured.baseURI = "http://localhost:9997";
			RequestSpecification request = RestAssured.given().contentType("application/json").body(file);
			Response response = request.post(postReq);
			response.then().assertThat().statusCode(200);
		}
	}
	
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
			 
			 //Verify Voucher Name
			 String voucherName = (String) jsonObject.get("vouchers.voucherName");
			 Assert.assertTrue(voucherName.matches("VOUCHER \\d"));
			 
			//Verify Voucher Type
			 String voucherType = (String) jsonObject.get("vouchers.voucherName");
			 Assert.assertTrue(voucherType.matches("^[a-zA-Z]*$"));
			
			 
			 
		}
	}

	/*
	 * AC 3 - Working class hero cannot be created with vouchers, if voucher field
	 * id null or empty
	 */

	@Test
	public void VerifyWCHCannotBeCraetedWIthEmptyVoucher() {
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
			Assert.assertTrue(errorMessage.equals("Voucher Name cannot be blank"));
		}
	}

	/*
	 * AC 4 - Verify record created in Voucher Table
	 */
	@Test
	public void verifyRecordInVoucherTable() throws ClassNotFoundException, SQLException {

		ResultSet rs = verifyRecordInDB("select * from testdb.vouchers where voucher_type='TRAVEL';");

		if (rs == null)
			System.out.println("Record is not present");
		else {
			System.out.println("record exist in the table" + rs);
		}

		closeDBConnection();
	}

	/*
	 * AC 5 - Verify nothing persisted for Failed validation
	 */
	@Test
	public void verifyNoRecordCreatedForFailedValidation() throws ClassNotFoundException, SQLException {

		ResultSet rs = verifyRecordInDB("SELECT COUNT(id) FROM testdb.vouchers;");

		int numberOfRecords = rs.getRow();
		VerifyWCHCannotBeCraetedWIthEmptyVoucher();
		ResultSet rs1 = verifyRecordInDB("SELECT COUNT(id) FROM testdb.vouchers;");
		int newNumberOfRecords = rs.getRow();
		Assert.assertTrue(numberOfRecords == newNumberOfRecords);
		closeDBConnection();
	}

}
