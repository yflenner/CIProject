package CIGroup.CIProject;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestCases
{
	WebDriver driver;
	WebDriverWait wait;
	ExtentReports extent;
	ExtentTest test; 
	
	@BeforeClass
	public void startSession()
	{
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://localhost:3030/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 10);
		extent = new ExtentReports("./htmlreports/ExtentReport/index.html");
	}
	
	@Test
	public void test1() throws InterruptedException, IOException
	{
		try
		{
			test = extent.startTest("Test Name", "Description");
			driver.findElement(By.name("username")).sendKeys("admin");
			test.log(LogStatus.PASS, "User Name inserted successfully");
			driver.findElement(By.name("password")).sendKeys("admin");
			test.log(LogStatus.PASS, "Password inserted successfully");
			driver.findElement(By.cssSelector("button[type='submit']")).click();
			test.log(LogStatus.PASS, "Login Clicked successfully");
			wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.cssSelector("a[class='btn btn-link']"))));
			driver.findElement(By.cssSelector("a[class='btn btn-link']")).click();
			Thread.sleep(500);
			test.log(LogStatus.PASS, "Skip clicked successfully");
			assertEquals(driver.getTitle(), "Grafana - Home");
			test.log(LogStatus.PASS, "Assert successfully");
		}
		catch (Exception exp)
		{				
			test.log(LogStatus.FAIL, "Test Failed: " + exp.getMessage() + test.addScreenCapture(takeSS()));
			fail("Test Failed, See Error in Report and: " + exp);
		}
		catch (AssertionError asr)
		{
			test.log(LogStatus.FAIL, "Test Assertion Failed: "+ asr.getMessage() + test.addScreenCapture(takeSS()));
			fail("Test Failed, See Error in Report and" + asr);
		}
	}
	
	@AfterMethod
	public void closeTest()
	{
		extent.endTest(test);
	}
	
	@AfterClass
	public void closeSession()
	{
		extent.flush();
		extent.close();
		driver.quit();
	}
	
	public String takeSS() throws IOException
	{
		String path = "./Reports/screenshot.png";
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);			
		FileUtils.copyFile(scrFile, new File(path));
		return path;
		
	}
}
