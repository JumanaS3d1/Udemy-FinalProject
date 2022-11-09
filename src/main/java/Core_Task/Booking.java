package Core_Task;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Booking {
	
	public String website = "https://www.booking.com/";
	WebDriver driver;
	WebElement searchCityBtn;
	WebElement searchBtn;
	WebElement hotel;
	

	public Booking(WebDriver driver) {
		this.driver = driver;
		searchCityBtn = driver.findElement(By.name("ss"));
		searchBtn = driver.findElement(By.className("sb-searchbox__button"));
	}

	public void stayIndate(String inDate, String outDate) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.getElementsByClassName(\"sb-date-field__field\")[0].click();");
		js.executeScript("document.querySelector('[data-date=\"" + inDate + "\"]').click();");
		js.executeScript("document.querySelector('[data-date=\"" + outDate + "\"]').click();");
	}
	
	public void StayInCity(String cityName) {
		searchCityBtn.sendKeys(cityName);
	}

	
	public void clickSearch() {
		searchBtn.click();
	}
	
	public String getHotelName() {
		return driver.findElement(By.xpath("//*[@data-testid=\"title\"]/..")).getText();
	}
	
	public String getHotelReview() {
		return driver.findElement(By.xpath("//*[@data-testid=\"review-score\"]/..")).getText();
	}
	
	public void clickHotel() {
		driver.findElement(By.xpath("//*[@data-testid=\"title\"]/..")).click();
	}

}
