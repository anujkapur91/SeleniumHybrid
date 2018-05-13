package com.Selenium.TestBase;



import java.io.File;
import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.IOException;
//import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.apache.bcel.classfile.Method;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestBase {
		
	 public static WebDriver driver;
	 public FileInputStream filestream;
	 public File orFile;
	 public Properties OR;
	 
	 
	 public Properties projectConfig;
	 File projectConfigfile;
	 FileInputStream ProjectConfigStream;
	
	public static ExtentReports extent;
	public static ExtentTest test;
	public ITestResult result;
	
	static
	{
		Calendar calendar = Calendar.getInstance();
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		extent = new ExtentReports(System.getProperty("user.dir")+"\\src\\main\\java\\com\\Selenium\\Report\\Test"+formatter.format(calendar.getTime())+".html",false);
	}
	
	 public void getResult(ITestResult result) throws IOException
	 
	 {
		 if (result.getStatus()==ITestResult.SUCCESS)
		 {
			 test.log(LogStatus.PASS, result.getName()+" test is passed");
			 String Screen = getScreenShot("");
			 test.log(LogStatus.PASS,test.addScreenCapture(Screen));
		 }
		 else if (result.getStatus()==ITestResult.SKIP)
		 {
			 test.log(LogStatus.SKIP,result.getName()+" test  is skipped and reason is:- "+result.getThrowable());
			  
		 }
		 else if (result.getStatus()==ITestResult.FAILURE)
		 {
			 test.log(LogStatus.FAIL,result.getName()+" test has failed and reason is:- "+result.getThrowable());
			 String Screen = getScreenShot("");
			 test.log(LogStatus.FAIL,test.addScreenCapture(Screen));
			  
			  
		 }
		 else if (result.getStatus()==ITestResult.STARTED)
		 {
			 test.log(LogStatus.INFO,result.getName()+" test started ");
			  
		 }
		 
	 }
	 
	@AfterMethod()
	 public void afterMetod(ITestResult result) throws IOException
	 {
		 getResult(result);
		 
	 }
	 
	 @BeforeMethod()
	public void beforeMethod(Method result) 
	 {
		 test = extent.startTest(result.getName());
		 test.log(LogStatus.INFO, result.getName()+" test started");
		 
		 
	 }
	 
	 @AfterClass(alwaysRun=true)
	 public void endTest()
	 {
		 driver.quit();
		 extent.endTest(test);
		 extent.flush();
		 
	 }
	 
	 
	public static WebElement getLocator(String locator) throws Exception
	{
		String[] split = locator.split(":");
		String locatorType = split[0];
		String locatorValue = split[1];
		
		if(locatorType.toLowerCase().equals("id"))
		{
			return driver.findElement(By.id(locatorValue));
			
		}
		
		else if (locatorType.toLowerCase().contains("class"))
		{
			return driver.findElement(By.className(locatorValue));
			
		}
		
		else if (locatorType.toLowerCase().contains("name"))
		{
			return driver.findElement(By.name(locatorValue));
			
		}
		else if (locatorType.toLowerCase().contains("link"))
		{
			return driver.findElement(By.linkText(locatorValue));
			
		}
		else if (locatorType.toLowerCase().contains("xpath"))
		{
			return driver.findElement(By.xpath(locatorValue));
			
		}
		else if (locatorType.toLowerCase().contains("css"))
		{
			return driver.findElement(By.cssSelector(locatorValue));
			
		}
		else 
		{
			throw new Exception("Unknown loctor type '"+locatorType+"'");
			
		}
		} 
	
	
	public  List<WebElement> getLocators(String locator) throws Exception
	{
		String[] split = locator.split(":");
		String locatorType = split[0];
		String locatorValue = split[1];
		
		if(locatorType.toLowerCase().equals("id"))
		{
			return driver.findElements(By.id(locatorValue));
			
		}
		
		else if (locatorType.toLowerCase().contains("class"))
		{System.out.println("class");
			return driver.findElements(By.className(locatorValue));
			
		}
		
		else if (locatorType.toLowerCase().contains("name"))
		{
			return driver.findElements(By.name(locatorValue));
			
		}
		else if (locatorType.toLowerCase().contains("link"))
		{
			return driver.findElements(By.linkText(locatorValue));
			
		}
		else if (locatorType.toLowerCase().contains("xpath"))
		{System.out.println("xpth");
			return driver.findElements(By.xpath(locatorValue));
			
		}
		else if (locatorType.toLowerCase().contains("css"))
		{
			return driver.findElements(By.cssSelector(locatorValue));
			
		}
		else 
		{
			throw new Exception("Unknown loctor type '"+locatorType+"'");
			
		}
	}
	
	public void getBrowser(String browser){
		if(System.getProperty("os.name").contains("Window"))
		{
			System.out.println("Operating System:-"+System.getProperty("os.name"));
			System.out.println(System.getProperty("user.dir")); 
			if(browser.equalsIgnoreCase("firefox"))
			{
				//https://github.com/mozilla/geckodriver/releases
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"\\Driver2\\geckodriver.exe");
				driver= new FirefoxDriver();
				System.out.println("FireFox driver set");
				
			}
			else if(browser.equalsIgnoreCase("chrome"))
			{
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\Driver2\\chromedriver.exe");
				driver =new ChromeDriver();
				System.out.println("Chrome driver set");
			}
			else {System.out.println("Farzi");}
			
			
		}
		else if(System.getProperty("os.name").contains("mac"))
		{
			if(browser.equalsIgnoreCase("firefox"))
			{
				System.setProperty("webdriver.gecko.marionette", System.getProperty("user.dir")+"/drivers/geckodriver");
				driver= new FirefoxDriver();
				
			}
			else if(browser.equalsIgnoreCase("chrome"))
			{
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/drivers/chromedriver");
				driver =new ChromeDriver();
				
			}
			
			
		} 
	}
	
	public String getScreenShot(String imgName) throws IOException
	{
		
		if(imgName.equals(""))
		{
			imgName = "Default";
		}
		File Image = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String imgLocation = System.getProperty("user.dir")+"\\src\\main\\java\\com\\Selenium\\ScreenShot\\";
		Calendar calndr = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		String actualImgName = imgLocation+imgName+"_"+formatter.format(calndr.getTime())+".png";
		
		File destFile = new File(actualImgName);
		FileUtils.copyFile(Image, destFile);
		return actualImgName;
		
	}
	
	
	public WebElement waiforElement(WebDriver driver,long time, WebElement element)
	{
		WebDriverWait wait = new WebDriverWait(driver,time);
		
			return wait.until(ExpectedConditions.elementToBeClickable(element));
		
	}

	public void loadProperties() throws IOException
	{
		 OR = new Properties();
		 orFile = new File(System.getProperty("user.dir")+"\\src\\main\\java\\com\\Selenium\\Config\\OR.Properties");
		 filestream = new FileInputStream(orFile);
		 OR.load(filestream);
		 
		 
		  projectConfig = new Properties();
		  projectConfigfile = new File(System.getProperty("user.dir")+"\\src\\main\\java\\com\\Selenium\\Config\\ProjectConfig.Properties");
		  ProjectConfigStream = new FileInputStream(projectConfigfile);
		 projectConfig.load(ProjectConfigStream);
		 
		 
	}
	
	public WebElement getwebelement(String locator) throws Exception
	{
		return getLocator(OR.getProperty(locator));
		
		
	}

	public static void main(String[] args) throws Exception
	{
	
		TestBase test = new TestBase();
		test.loadProperties();
		System.out.println(test.OR.getProperty("password"));
		//test.getLocator(test.OR.getProperty("usernme"));
		
		
		
	}
	

}
