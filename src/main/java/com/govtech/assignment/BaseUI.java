package com.govtech.assignment;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Hello world!
 *
 */
public class BaseUI 
{
	public static Properties prop;
	public static WebDriver driver;
	
   @BeforeTest
   public void loadConfig() throws IOException {
	   
	   try {
		   System.out.println("Read and load Generic peroperties");
		   prop = new Properties();
		   FileInputStream fis = 
				   new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\resources\\configuration\\config.properties");
		   
		   prop.load(fis);
		   
		   System.out.println("driver" + driver);
	   }
	   catch(FileNotFoundException e){
		   e.printStackTrace();
	   }
   }
   
@SuppressWarnings("deprecation")
@BeforeTest
public static void launchApp() {
	   
	   WebDriverManager.chromedriver().setup();
	   String browserName = prop.getProperty("browser");
	   
	   if(browserName.contains("Chrome")) {
		   driver = new ChromeDriver();
	   }
	   
	   driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	   driver.manage().window().maximize();
	   driver.get(prop.getProperty("baseUrl"));
	   
   }

public static void setClipboardData(String string) {
	//StringSelection is a class that can be used for copy and paste operations.
	   StringSelection stringSelection = new StringSelection(string);
	   Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
	}

public static boolean uploadFile(String fileLocation) {
	boolean upload=false;
    try {
    	//Setting clipboard with file location
        setClipboardData(fileLocation);
        //native key strokes for CTRL, V and ENTER keys
        Robot robot = new Robot();

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        upload = true;
    } catch (Exception exp) {
    	exp.printStackTrace();
    }
	return upload;
}



public static boolean isFileDownloaded(String downloadPath, String fileName) {
	File dir = new File(downloadPath);
	File[] dirContents = dir.listFiles();

	for (int i = 0; i < dirContents.length; i++) {
		if (dirContents[i].getName().equals(fileName)) {
			// File has been found, it can now be deleted:
			dirContents[i].delete();
			return true;
		}
	}
	return false;
}

public static void verifyDownlaodedFileFormat(String filePath) throws IOException {
	
File file = new File(filePath);

    BufferedReader br= new BufferedReader(new FileReader(file)); 
    int lines = 0;
    while (br.readLine() != null) 
    	lines++;
    String st;
    if (lines==1){
    	st = br.readLine();
    	st.matches("\\d");
    }else {
    	  while ((st = br.readLine()) != null)
    	if(st.matches("^natid-\\d{1,},\\d{1,}\\.\\d{1,}$") || st.matches("\\d")){
    		System.out.println("Format is correct" + st);
    	}
    }
}

public static ResultSet verifyRecordInDB(String query) throws ClassNotFoundException, SQLException {
	
	DbConnection.openDBConnection();
	ResultSet rs = DbConnection.runQuery(query);
return rs;
}

public void closeDBConnection() throws SQLException {
	DbConnection.closeConnection();
}

public static void waitForPageToLoad(int unit) {
	driver.manage().timeouts().implicitlyWait(unit, TimeUnit.SECONDS);
}


@AfterClass
public void closeDriver() {
	driver.close();
}
}
