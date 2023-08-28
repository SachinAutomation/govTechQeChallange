package com.govtech.testcases;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.testng.annotations.Test;
import com.govtech.assignment.BaseUI;
import com.govtech.assignment.DbConnection;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

public class UserStory5 extends BaseUI {

	/*
	 * AC 1 - external service’s API to find out if a working class hero owes money
	 */

	@Test
	public void VerifyClassHeroExist() {

		// AC - 1 GET owe-money status with query parameter natid=<number>

		String getReq = "/api/v1/hero/owe-money?natid=1";
		RestAssured.baseURI = "http://localhost:9997";
		RequestSpecification request = RestAssured.given().contentType("application/json");
		Response response = request.get();
		int statusCode = response.getStatusCode();
		System.out.println(statusCode);
		JsonPath jsonPath = response.jsonPath();
		String natid = jsonPath.get("message.data");
		System.out.println(natid);

		// AC-2 Verify natid is a number
		
		String ch = natid.replace("natid-", "");
		Integer.parseInt(ch);

		// AC-3 receive response paylaod and verify status

		String responseNatid = jsonPath.get("data");
		System.out.println(responseNatid);
		Assert.assertEquals(natid, responseNatid);
		String owingStatus = jsonPath.get("data");
		System.out.println(owingStatus);
		if (owingStatus.equals("OWE")) {
			System.out.println(responseNatid + "owes the money");
		} else {
			System.out.println(responseNatid + "doe not owes the money");
		}
		

		// AC-4 validate the format pf response paylaod

		response.then().assertThat().body(JsonSchemaValidator
				.matchesJsonSchema(new File("/assignment/src/test/resources/jsonfile/schemaStory5")));

	}

}
