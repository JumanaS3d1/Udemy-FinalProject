package Test_Udemy;

import static org.testng.Assert.assertEquals;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Core_Task.Booking;
import Core_Task.OpenBrowsers;
import Core_Task.ReadCsvFile;
import Core_Task.TakeScreenShot;
import Core_Task.WriteCsvFile;
import Core_Udemy.FeaturedTopics;
import Core_Udemy.LoginClick;
import Core_Udemy.LoginPage;
import Core_Udemy.SearchCourses;
import Core_Udemy.TopCategory;
import Core_Udemy.UserCredentials;

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

	@Test(priority=2)
	public void topCategories() {
		TopCategory topCat = new TopCategory(driver);
		String[] headers = new String[1];
		headers[0] = "Top Categories: ";
		WriteCsvFile.writeDataLineByLine("TopCategories.csv", topCat.getTopCat(), headers);
		driver.quit();

	}

	
	
	@Test(priority=4, dataProvider = "getData")
	public void searchSubjects(String name) throws InterruptedException, IOException {
		
		driver = OpenBrowsers.openBrowser("chrome");
		// driver = OpenBrowsers.openchromeWithOptions();
		Thread.sleep(3000);
		driver.manage().window().maximize();
		driver.get(name);

		Thread.sleep(5000);
		ArrayList<String> currOutput = new ArrayList<String>();
		currOutput.add("Course Subject");
		

		
		SearchCourses course = new SearchCourses(driver);
		//course.searchSubject(name);
	
		
		
		TakeScreenShot takeSc = new TakeScreenShot(driver);
		takeSc.takeScreenShot("downloads/"  + "_Search.jpg");
		
		Thread.sleep(3000);
		//course.clickSearch();
		//Thread.sleep(10000);
		
		String courseName = course.getCourseName();
		Thread.sleep(1000);
		String courserating= course.getRating();
		Thread.sleep(1000);
		String coursePrice = course.getPrice();
		
		Thread.sleep(4000);
		currOutput.add(courseName);
		Thread.sleep(1000);
		currOutput.add(courserating);
		Thread.sleep(1000);
		currOutput.add(coursePrice);
		
		Thread.sleep(4000);
		
		
//		course.clickCourse();
//		String oldTab = driver.getWindowHandle();
//		for (String winHandle : driver.getWindowHandles()) {
//			driver.switchTo().window(winHandle);
//		}
//		
//		Thread.sleep(5000);
//		
//		System.out.println(driver.getCurrentUrl());
//		TakeScreenShot takeSc2 = new TakeScreenShot(driver);
//		takeSc2.takeScreenShot("downloads/"  + "_Course_Info.jpg");
//		String hotelURL = driver.getCurrentUrl();
//		
//		Thread.sleep(3000);
//		
//		currOutput.add(hotelURL);
		outputData.add(currOutput);
		
//		driver.switchTo().window(oldTab);
//		driver.navigate().back();
		
		Thread.sleep(4000);
		
		driver.quit();
		
	}
	
	@Test(/*priority=3, groups= "login"*/ enabled = false)
	public void logIn() throws InterruptedException, IOException {
		LoginClick logClc = new LoginClick(driver);
		
		
		logClc.clickLogin();
		System.out.println("d43535435 here");
		Thread.sleep(4000);
		LoginPage loginPage = new LoginPage(driver);
		System.out.println("dsfsdfdsf here");
		
		
	 UserCredentials cred = new UserCredentials();
	 String username = cred.getUserName();
	 String password = cred.getPass();
		
		loginPage.fillEmail(username);
		loginPage.fillPassword(password);
		loginPage.logIn();		
	}
	
	
	

	@AfterSuite
	public void afterSuite() {
		//driver.quit();
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
