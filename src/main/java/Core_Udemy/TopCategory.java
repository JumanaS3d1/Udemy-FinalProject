package Core_Udemy;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TopCategory {

	public String website = "https://www.udemy.com/";
	WebDriver driver;
	
	public TopCategory(WebDriver driver) {
		this.driver = driver;
	}
	
	public List<String[]> getTopCat() {
		List<String[]> allCat = new ArrayList<String[]>();
		int catSize = driver.findElements(By.xpath("//*[@class=\"category-card--category-card-title--3TiqD\"]")).size();
		for(int i=0; i<catSize ; i++) {
		
			String[] a = new String[1];
			a[0] = driver.findElements(By.xpath("//*[@class=\"category-card--category-card-title--3TiqD\"]")).get(i).getText();
			allCat.add(a); 
		}
		
		return allCat;
	}
}
