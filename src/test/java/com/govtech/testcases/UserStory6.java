package com.govtech.testcases;

import java.io.File;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import com.govtech.assignment.BaseUI;
import io.restassured.response.Response;
import io.restassured.module.jsv.JsonSchemaValidator;

public class UserStory6 extends BaseUI {

	/*
	 * AC 1 - get number of vouchers each customer has for each voucher category
	 */
	@Test
	public void createWrokingClassHero() {

		Response response = given().contentType("application/json").when().get("/api-demo-products-id/");

		int statusCode = response.getStatusCode();
		System.out.println(statusCode);
		System.out.println(response.asString());

		// Validate response format

		response.then().assertThat().body(JsonSchemaValidator
				.matchesJsonSchema(new File("/assignment/src/test/resources/jsonfile/schemaStory6")));

	}

}
