package Test_Udemy;

import static org.testng.Assert.assertEquals;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import Core_Udemy.FeaturedTopics;
import Core_Udemy.LoginClick;
import Core_Udemy.LoginPage;
import Core_Udemy.OpenBrowsers;
import Core_Udemy.ReadCsvFile;
import Core_Udemy.SearchCourses;
import Core_Udemy.TakeScreenShot;
import Core_Udemy.TopCategory;
import Core_Udemy.UserCredentials;
import Core_Udemy.WriteCsvFile;

public class UdemyTest {

	WebDriver driver;
	ArrayList<String> outputHeaders = new ArrayList<String>();
	ArrayList<ArrayList<String>> outputData = new ArrayList<ArrayList<String>>();


	@BeforeSuite
	public void beforeSuite() throws InterruptedException {
		outputHeaders.add("Subject");
		outputHeaders.add("name");
		outputHeaders.add("rating");
		outputHeaders.add("price");
	}

	@BeforeTest
	public void openB() throws InterruptedException {
		driver = OpenBrowsers.openBrowser("chrome");
		// driver = OpenBrowsers.openchromeWithOptions();
		Thread.sleep(3000);
		driver.manage().window().maximize();
		driver.get("https://www.udemy.com/");
	}

	@DataProvider
	public static Object[][] getData() throws Exception {

		List<String[]> lines = ReadCsvFile.readAllLines("Courses.csv");
		lines.remove(0);
		Object[][] data = new Object[lines.size()][lines.get(0).length];
		int index = 0;
		for (String[] line : lines) {
			data[index] = line;
			index++;
		}
		return data;
	}

	
	@Test(priority=1)
	public void featuredTopics() {
		FeaturedTopics featured = new FeaturedTopics(driver);
		WriteCsvFile.writeDataLineByLine("FeaturedTopics.csv", featured.getTopics(), featured.getCategories());

	}

	@Test(priority= 2)
	public void topCategories() {
		TopCategory topCat = new TopCategory(driver);
		String[] headers = new String[1];
		headers[0] = "Top Categories: ";
		WriteCsvFile.writeDataLineByLine("TopCategories.csv", topCat.getTopCat(), headers);
		driver.quit();

	}

	@Test(priority = 3, dataProvider = "getData")//, enabled = false)
	public void searchSubjects(String name) throws InterruptedException, IOException {

		driver = OpenBrowsers.openBrowser("chrome");
		Thread.sleep(3000);
		driver.manage().window().maximize();
		driver.get(name);

		Thread.sleep(5000);
		ArrayList<String> currOutput = new ArrayList<String>();
		currOutput.add("Course Subject");

		SearchCourses course = new SearchCourses(driver);
		// course.searchSubject(name);

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,-500)", "");

		TakeScreenShot takeSc = new TakeScreenShot(driver);
		takeSc.takeScreenShot("downloads/" + "_Search.jpg");

		Thread.sleep(3000);
		// course.clickSearch();
		// Thread.sleep(10000);

		String courseName = course.getCourseName();
		String courserating = course.getRating();
		String coursePrice = course.getPrice();

		Thread.sleep(2000);
		currOutput.add(courseName);
		currOutput.add(courserating);
		currOutput.add(coursePrice);

		Thread.sleep(4000);

		outputData.add(currOutput);

		Thread.sleep(4000);

		driver.quit();

	}
	@Test(priority = 4)
	public void Filtering() throws InterruptedException {
		driver = OpenBrowsers.openBrowser("chrome");
		Thread.sleep(3000);
		driver.manage().window().maximize();
		driver.get("https://www.udemy.com/courses/music/");
		Thread.sleep(3000);
		driver.findElements(By.xpath("//*[@class=\"ud-accordion-panel-heading udlite-accordion-panel-heading\"]")).get(6).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@class=\"ud-toggle-input-container udlite-toggle-input-container ud-text-sm udlite-text-sm\"]")).click();
		Thread.sleep(3000);
		
	}

	@Test(priority = 4,  enabled = false)
	public void logIn() throws InterruptedException, IOException {

		driver = OpenBrowsers.openBrowser("chrome");
		Thread.sleep(3000);
		driver.manage().window().maximize();
		driver.get("https://www.udemy.com/join/login-popup/");

		LoginClick logClc = new LoginClick(driver);

		// logClc.clickLogin();
		// System.out.println("d43535435 here");
		Thread.sleep(4000);
		LoginPage loginPage = new LoginPage(driver);
		// System.out.println("dsfsdfdsf here");

		UserCredentials cred = new UserCredentials();
		String username = cred.getUserName();
		String password = cred.getPass();

		loginPage.fillEmail(username);
		loginPage.fillPassword(password);
		loginPage.logIn();
	}

	@AfterSuite
	public void afterSuite() {
		// driver.quit();
		List<String[]> data = new ArrayList<String[]>();
		for (ArrayList<String> row : outputData) {
			String[] row_data = new String[row.size()];
			for (int i = 0; i < row.size(); i++) {
				row_data[i] = row.get(i);
			}
			data.add(row_data);
		}
		String[] headers = new String[outputHeaders.size()];
		for (int i = 0; i < outputHeaders.size(); i++) {
			headers[i] = outputHeaders.get(i);
		}
		WriteCsvFile.writeDataLineByLine("CoursesInfo.csv", data, headers);
	}
}
