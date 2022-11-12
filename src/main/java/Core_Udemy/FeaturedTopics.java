package Core_Udemy;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class FeaturedTopics {

	public String website = "https://www.udemy.com/";
	WebDriver driver;

	public FeaturedTopics(WebDriver driver) {
		this.driver = driver;
	}

	// Return the main featured categories
	public String[] getCategories() {

		int catSize = driver.findElements(By.xpath("//*[@data-purpose=\"category-title\"]")).size();
		String[] category = new String[catSize];
		for (int i = 0; i < catSize; i++) {
			category[i] = (driver.findElements(By.xpath("//*[@data-purpose=\"category-title\"]")).get(i).getText());
		}
		return category;
	}

	// Return the topics for each category
	public List<String[]> getTopics() {
		List<String[]> allTopics = new ArrayList<String[]>();

		String[] dev = new String[4];
		String[] it = new String[4];
		String[] bus = new String[4];

		int topSize = driver.findElements(By.xpath("//*[@class=\"trending-topics--link--1B3Cq\"]")).size();
		String[] topic = new String[topSize];
		for (int i = 0; i < topSize; i++) {
			topic[i] = (driver.findElements(By.xpath("//*[@class=\"trending-topics--link--1B3Cq\"]")).get(i).getText());
		}

		for (int i = 0; i < 4; i++) {
			int x = i * 3;

			dev[i] = topic[x];
			bus[i] = topic[x + 1];
			it[i] = topic[x + 2];
		}
		allTopics.add(dev);
		allTopics.add(bus);
		allTopics.add(it);
		return allTopics;
	}

}
