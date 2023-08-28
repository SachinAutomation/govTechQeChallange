package com.govtech.uipages;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.FindBy;
import com.govtech.assignment.BaseUI;

public class LoginPage extends BaseUI{
	
	WebDriver driver;
	
	@FindBy(id="username-in")
	public WebElement userName;
	
	@FindBy(id="password-in")
	public WebElement password;
	
	@FindBy(xpath="//input[@type='submit']")
	public WebElement submitButton;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}
	
	public boolean loginToApplication (String username, String loginPassword) {
		boolean loginReturn=false;
		try {
		userName.sendKeys(username);
		password.sendKeys(loginPassword);
		submitButton.click();
		loginReturn=true;
		} catch(Exception e) {
			System.out.println("login is not sucessfull");
		}return loginReturn;
	}

}
