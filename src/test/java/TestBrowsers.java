import static org.testng.Assert.assertEquals;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class TestBrowsers {

	WebDriver driver;

	@BeforeSuite
	public void before() throws InterruptedException {
		driver = OpenBrowsers.openBrowser("chrome");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));
		driver.get("https://people.sc.fsu.edu/~jburkardt/data/csv/csv.html");
	}

	@Test
	public void f() {

		String pTitle = driver.getTitle();
		WebElement title = driver.findElement(By.xpath("/html/body/h1"));
		assertEquals(pTitle, title.getText());

	}
}
