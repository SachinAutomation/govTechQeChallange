package com.govtech.uipages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;
import com.govtech.assignment.BaseUI;

public class BookKeeperPage extends BaseUI{
	
	WebDriver driver;
	
	@FindBy(id="tax_relief_btn")
	public WebElement generateTaxReliefFileButton;
	
	@FindBy(xpath="//*[text()='Book Keeper Dashboard']")
	public WebElement bookKeeperDashBoardMessage;
	
	@FindBy(xpath="//*[text()='Welcome Home Moh Peh!']")
	public WebElement welcomeMessage;
	
	@FindBy(id="tax_relief_status_id")
	public WebElement fileProcessingStatus;
	
	public BookKeeperPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}
	
	public String verifyBookKeeperDashboard() {
		String message = bookKeeperDashBoardMessage.getText();
		return message;
	}
	
	public String verifyWelcometext() {
		String message = welcomeMessage.getText();
		return message;
	}
	
	public boolean clickgenerateTaxReliefFileButton() {
		boolean button=false;
		try {		
			generateTaxReliefFileButton.click();
		button=true;
		}catch(Exception e) {
			System.out.println("Tax Relief button is not clicked");
		}
		return button;
	}


}
