package com.govtech.testcases;

import org.junit.Assert;
import org.testng.annotations.Test;
import com.govtech.assignment.BaseUI;
import com.govtech.uipages.ClerkDashboardPage;
import com.govtech.uipages.LoginPage;

public class UserStory2 extends BaseUI {
	@Test
	public void testCsvUpload() {

		LoginPage login = new LoginPage(driver);
		ClerkDashboardPage clerkDashboardPage = new ClerkDashboardPage(driver);

		String uName = prop.getProperty("userClerk");
		String pwd = prop.getProperty("passwordClerk");

		// AC-1 - login as Clerk and click uplaod a CSV file
		
		Assert.assertTrue(login.loginToApplication(uName, pwd));
		Assert.assertTrue(clerkDashboardPage.clickAddHeroButton());
		Assert.assertTrue(clerkDashboardPage.clickUploadCsvButton());

		// AC-2 - uplaod a CSV file in correct format

		String sucessFileLoaction = "C:\\Users\\Romi\\Desktop\\GOVTech\\assignment\\src\\main\\resources\\test_sucess.csv";
		Assert.assertTrue(BaseUI.uploadFile(sucessFileLoaction));

		// AC-3 - Verify Success message after clicking on create
		
		String sucessMessage = "Created Successfully!";
		Assert.assertTrue(clerkDashboardPage.getSucessMessage().equals(sucessMessage));

		// AC-4 - Verify error message after uploading CSV file in wrong format
		
		Assert.assertTrue(clerkDashboardPage.clickBackButton());
		String errorFileLoaction = "C:\\Users\\Romi\\Desktop\\GOVTech\\assignment\\src\\main\\resources\\test_failure.csv";
		Assert.assertTrue(BaseUI.uploadFile(errorFileLoaction));
		String errorMessage = "Unable to create hero!";
		String failureReason = "There are 1 records which were not persisted! Please contact tech support for help!";
		Assert.assertTrue(clerkDashboardPage.getErrorMessage().equals(errorMessage));
		Assert.assertTrue(clerkDashboardPage.getFailureReason().equals(failureReason));

	}

}
