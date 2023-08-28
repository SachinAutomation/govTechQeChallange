package com.govtech.uipages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;
import com.govtech.assignment.BaseUI;

public class ClerkDashboardPage extends BaseUI{
	
	WebDriver driver;
	
	@FindBy(xpath="//h1[text()='Clerk Dashboard']")
	public WebElement clerkDashboardTitle;
	
	@FindBy(xpath="//*[@class='btn btn-primary btn-small']")
	public WebElement logoutButton;
	
	@FindBy(id="dropdownMenuButton2")
	public WebElement addAHeroButton;
	
	@FindBy(xpath="//*[@href='clerk/add']")
	public WebElement addButton;
	
	@FindBy(xpath="//*[@href='/clerk/upload-csv']")
	public WebElement uploadACsvButton;
	
	@FindBy(xpath="//*[@href='/clerk/dashboard']")
	public WebElement backButton;
	
	@FindBy(id="upload-csv-file")
	public WebElement chooseFileButton;
	
	@FindBy(xpath="//*[@onclick='uploadCsv()']")
	public WebElement createButton;
	
	@FindBy(xpath="//*[@id='notification-block']//h3")
	public WebElement sucessfullMessage;
	
	@FindBy(xpath="//*[@id='notification-block']//h3")
	public WebElement failureMessage;
	
	@FindBy(xpath="//*[text()='There are 1 records which were not persisted! Please contact tech support for help!']")
	public WebElement failureMessageReason;
	
	public ClerkDashboardPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}
	
	public boolean clickAddHeroButton() {
		boolean button=false;
		try {		
		addAHeroButton.click();
		button=true;
		}catch(Exception e) {
			System.out.println("button is not clicked");
		}
		return button;
	}

	public boolean clickUploadCsvButton() {
		boolean button=false;
		try {		
		uploadACsvButton.click();
		button=true;
		}catch(Exception e) {
			System.out.println("button is not clicked");
		}
		return button;
	}
	
	public boolean clickCreateButton() {
		boolean button=false;
		try {		
		createButton.click();
		button=true;
		}catch(Exception e) {
			System.out.println("button is not clicked");
		}
		return button;
	}
	
	
	public String getSucessMessage() {
		String message = sucessfullMessage.getText();
		return message;
	}
	
	public boolean clickBackButton() {
		boolean button=false;
		try {		
		backButton.click();
		button=true;
		}catch(Exception e) {
			System.out.println("button is not clicked");
		}
		return button;
	}

	public String getErrorMessage() {
		String message = failureMessage.getText();
		return message;
	}
	
	public String getFailureReason() {
		String message = failureMessageReason.getText();
		return message;
	}

}
