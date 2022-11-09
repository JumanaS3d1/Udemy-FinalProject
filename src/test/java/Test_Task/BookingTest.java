package Test_Task;

import org.testng.annotations.Test;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import Core_Task.WriteCsvFile;
import Core_Task.ReadCsvFile;
import Core_Task.TakeScreenShot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.WebDriver;

import Core_Task.Booking;
import Core_Task.OpenBrowsers;

public class BookingTest {

	WebDriver driver;
	ArrayList<String> outputHeaders = new ArrayList<String>();
	ArrayList<ArrayList<String>> outputData = new ArrayList<ArrayList<String>>();

	@BeforeSuite
	public void beforeSuite() throws InterruptedException {
		outputHeaders.add("city");
		outputHeaders.add("check_in date");
		outputHeaders.add("check_out date");
		outputHeaders.add("Hotel name");
		outputHeaders.add("Hotel review");
		outputHeaders.add("Booking url");
		}

	@BeforeTest
	public void openB() throws InterruptedException {
		driver = OpenBrowsers.openBrowser("chrome");
		//driver = OpenBrowsers.openchromeWithOptions();
		Thread.sleep(3000);
		driver.manage().window().maximize();
		driver.get("https://www.booking.com/");
	}

	@DataProvider
	public static Object[][] getData() throws Exception {

		List<String[]> lines = ReadCsvFile.readAllLines("ResData.csv");
		lines.remove(0);
		Object[][] data = new Object[lines.size()][lines.get(0).length];
		int index = 0;
		for (String[] line : lines) {
			data[index] = line;
			index++;
		}
		return data;
	}

	@Test(dataProvider = "getData")
	public void TestBooking(String city, String check_in, String check_out) throws InterruptedException, IOException {

		ArrayList<String> currOutput = new ArrayList<String>();
		currOutput.add(city);
		currOutput.add(check_in);
		currOutput.add(check_out);

		Booking booking = new Booking(driver);
		Thread.sleep(1000);
		booking.stayIndate(check_in, check_out);
		Thread.sleep(1000);
		booking.StayInCity(city);
		Thread.sleep(10000);

		TakeScreenShot takeSc = new TakeScreenShot(driver);
		takeSc.takeScreenShot("downloads/" + city + "_booking.jpg");

		booking.clickSearch();
		Thread.sleep(2000);
		String oldTab = driver.getWindowHandle();

		String hotelName = booking.getHotelName();
		String hotelReview = booking.getHotelReview();
		// Thread.sleep(1000);
		booking.clickHotel();

		for (String winHandle : driver.getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}

		Thread.sleep(7000);
		System.out.println(driver.getCurrentUrl());
		TakeScreenShot takeSc2 = new TakeScreenShot(driver);
		takeSc2.takeScreenShot("downloads/" + city + "_hotels.jpg");
		String hotelURL = driver.getCurrentUrl();

		Thread.sleep(7000);

		currOutput.add(hotelName);
		currOutput.add(hotelReview);
		currOutput.add(hotelURL);

		outputData.add(currOutput);

		driver.switchTo().window(oldTab);
		driver.navigate().back();
	}

	@AfterSuite
	public void afterSuite() {
		driver.quit();
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
		WriteCsvFile.writeDataLineByLine("output.csv", data, headers);
	}

}
