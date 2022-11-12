package Core_Udemy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginClick {

	WebDriver driver;
	WebElement loginBtn; 

	public LoginClick(WebDriver driver) {
		this.driver = driver;
		loginBtn = driver.findElement(By.xpath("//*[@data-purpose=\"header-login\"]"));
	}
	
	public void clickLogin() {
		loginBtn.click();
	}
}
