package Core_Udemy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchCourses {

	WebDriver driver;
	
	WebElement searchBox;
	WebElement searchBtn;
	WebElement courseName;
	WebElement courseRating;
	WebElement coursePrice;

	public SearchCourses(WebDriver driver) {
		this.driver = driver;
		searchBox = driver.findElement(By.name("q"));
		searchBtn = driver.findElement(By.xpath("//*[@type=\"submit\"]"));
		//courseName = driver.findElements(By.xpath("//*[@data-purpose=\"course-title-url\"]")).get(0);
		//courseRating = driver.findElements(By.xpath("//*[@data-purpose=\"rating-number\"]")).get(0);
		//coursePrice = driver.findElements(By.xpath("//*[@data-purpose=\"course-price-text\"]")).get(0);
	}

	public void searchSubject(String sub) {
		searchBox.sendKeys(sub);
	}
	
	public void clickSearch() {
		searchBtn.click();
	}
	public String getCourseName() {
		return driver.findElements(By.xpath("//*[@data-purpose=\"course-title-url\"]")).get(0).getText();
	}
	public String getRating() {
		return driver.findElements(By.xpath("//*[@data-purpose=\"rating-number\"]")).get(0).getText();
	}
	public String getPrice() {
		return driver.findElements(By.xpath("//*[@data-purpose=\"course-price-text\"]")).get(0).getText();
	}
	
	public void clickCourse() {
		driver.findElements(By.xpath("//*[@data-purpose=\"course-title-url\"]")).get(0).click();
	}
	
	public void clickk() {
		 driver.findElements(By.xpath("\"//*[@class=\\\"ud-accordion-panel-heading udlite-accordion-panel-heading\\\"]\"")).get(6).click();
	}

}
