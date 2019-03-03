package CIGroup.CIProject;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestCases
{
	WebDriver driver;
	WebDriverWait wait;
	
	@BeforeClass
	public void startSession()
	{
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://localhost:3030/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 10);
	}
	
	@Test
	public void test1() throws InterruptedException
	{
		driver.findElement(By.name("username")).sendKeys("admin");
		driver.findElement(By.name("password")).sendKeys("admin");
		driver.findElement(By.cssSelector("button[type='submit']")).click();
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector("a[class='btn btn-link']"))));
		driver.findElement(By.cssSelector("a[class='btn btn-link']")).click();
		Thread.sleep(500);
		assertEquals(driver.getTitle(), "Grafana - Home");
	}
	
	@AfterClass
	public void closeSession()
	{
		driver.quit();
	}
}
