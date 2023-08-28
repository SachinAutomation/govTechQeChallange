package com.govtech.testcases;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Assert;
import org.testng.annotations.Test;

import com.govtech.assignment.BaseUI;
import com.govtech.assignment.DbConnection;
import com.govtech.uipages.BookKeeperPage;
import com.govtech.uipages.LoginPage;

public class UserStory3 extends BaseUI {
	@Test
	public void verifyTaxReliefFile() throws IOException, ClassNotFoundException, SQLException {

		LoginPage login = new LoginPage(driver);
		BookKeeperPage bookKeeperPage = new BookKeeperPage(driver);

		String uName = prop.getProperty("userBookKeeper");
		String pwd = prop.getProperty("passwordBookKeeper");

		// AC-1 - login as Book Keeper and click Generate Tax Relief File and verify
		// file is generated
		Assert.assertTrue(login.loginToApplication(uName, pwd));
		waitForPageToLoad(5);
		String dashboardMessage = "Book Keeper Dashboard";
		Assert.assertTrue(bookKeeperPage.bookKeeperDashBoardMessage.equals(dashboardMessage));
		String welcomeMessage = "Book Keeper Dashboard";
		Assert.assertTrue(bookKeeperPage.welcomeMessage.equals(welcomeMessage));
		Assert.assertTrue(bookKeeperPage.clickgenerateTaxReliefFileButton());
		isFileDownloaded("C:\\Users\\Sachin\\Desktop\\Assignment", "taxrelief");

		/*
		 * AC - 2 Verify File contain a body where each line is in the format: <natid>,
		 * <tax relief amount> with footer
		 * AC- 3 : If there are no records to be
		 * generated, a file is still being generated containing only the footer
		 */

		verifyDownlaodedFileFormat("C:\\Users\\Sachin\\Desktop\\Assignment\\taxrelief");

		// AC-4 TAX_RELIEF record is being persisted into a database table FILE
		// containing the file status with count

		ResultSet rs = verifyRecordInDB("select * from testdb.file where file_type ='TAX_RELIEF';");
		String fileStatus = rs.getString("file_status");
		System.out.println(fileStatus);
		int count = rs.getInt("total_count");
		System.out.println(count);
		closeDBConnection();

	}
}
