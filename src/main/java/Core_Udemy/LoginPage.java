package Core_Udemy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
	
	WebDriver driver;
	WebElement userMail;
	WebElement userPass; 
	WebElement login;
	WebElement errMsg;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		userMail= driver.findElement(By.xpath("//*[@data-purpose=\"email\"]"));
		userPass = driver.findElement(By.name("password"));
		login = driver.findElement(By.name("submit"));
		errMsg = driver.findElement(By.id("error-alert"));  
	}
	
	public void fillEmail(String txt) {
		userMail.sendKeys(txt);
	}

	public void fillPassword(String txt) {
		userPass.sendKeys(txt);
	}
	public void logIn() {
		login.click();
	}
	public void getErrorMsg() {
		errMsg.getText();
	}
	
	
}
