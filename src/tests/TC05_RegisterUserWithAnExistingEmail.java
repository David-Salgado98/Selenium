package tests;

import java.time.Duration;
import java.util.Random;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TC05_RegisterUserWithAnExistingEmail {
	WebDriver driver = null;

	Random rand = new Random();
	
	int upperbound2 = 2;
	int y = rand.nextInt(upperbound2) + 2;

	String nombre = "David";
	
	int upperbound = 2555;
	int int_random1 = rand.nextInt(upperbound);
	int int_random2 = rand.nextInt(upperbound);

	String correo = "david" + int_random1 + int_random2 + "@gmail.com";
	String password = "12345";

	@BeforeTest(description = "Setup the driver")
	public void S000_FirstStep() {
		// Setup driver
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@Test(description = "Go to URL 'https://automationexercise.com/'", priority = 1)
	public void S001_GoWebpage() {
		// Go to web page
		driver.get("https://automationexercise.com/");
		new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/login']")));
		
		Assert.assertEquals("FEATURES ITEMS", driver.findElement(By.cssSelector("div[class='features_items'] h2[class='title text-center']")).getText());
		Reporter.log("The webpage loads correctly<br>");

	}

	@Test(description = "Create User", priority = 2)
	public void S002_CreateUser() throws InterruptedException {
		// Women menu
		driver.findElement(By.cssSelector("a[href='/login']")).click();
		WebElement signup = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-qa='signup-button']")));
			driver.findElement(By.cssSelector("input[placeholder='Name']")).sendKeys(nombre);
			driver.findElement(By.cssSelector("input[data-qa='signup-email']")).sendKeys(correo);
			signup.click();
			WebElement firstname = new WebDriverWait(driver, Duration.ofSeconds(10))
					.until(ExpectedConditions.elementToBeClickable(By.id("first_name")));
	       
			driver.findElement(By.id("password")).sendKeys(password);
			firstname.sendKeys("Eleanor");
			WebElement lastname =  driver.findElement(By.id("last_name"));
			lastname.sendKeys("Rossvelt");
			WebElement address =  driver.findElement(By.id("address1"));
			address.sendKeys("New california republic , 5th street chritsmas way");
			WebElement state =  driver.findElement(By.id("state"));
			state.sendKeys("California");
			WebElement city =  driver.findElement(By.id("city"));
			city.sendKeys("San Diego");
			WebElement zip = driver.findElement(By.id("zipcode"));
			zip.sendKeys("1234");
			WebElement number1 = driver.findElement(By.id("mobile_number"));
			number1.sendKeys("1234567899");
			driver.findElement(By.cssSelector("button[data-qa='create-account']")).click();
			Wait<WebDriver> waitAssert = new FluentWait<WebDriver>(driver)
		       		  .withTimeout(Duration.ofSeconds(5))
		       		  .pollingEvery(Duration.ofSeconds(1))
		       		  .ignoring(NoSuchElementException.class);
		       	
		       	WebElement asserResult = waitAssert.until(new Function<WebDriver, WebElement>() {
		            public WebElement apply(WebDriver driver) {
		                return driver.findElement(By.xpath("//a[normalize-space()='Continue']"));
		              }
		            });
			String UserName = driver.findElement(By.xpath("//b[normalize-space()='Account Created!']")).getText();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (elements.length > 0) elements[0].remove()");
			Assert.assertTrue(asserResult.isDisplayed());
			Assert.assertTrue(UserName.contains("ACCOUNT CREATED!"));
			Reporter.log("The account is created<br>");

	}
	
	@Test(description = "Check the login name", priority = 3)
	public void S003_LoginName() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;	
		js.executeScript("const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (elements.length > 0) elements[0].remove()");
		
		driver.findElement(By.xpath("//a[normalize-space()='Continue']")).click();
		driver.findElement(By.xpath("//a[normalize-space()='Continue']")).click();
	WebElement name = new WebDriverWait(driver, Duration.ofSeconds(10))
			.until(ExpectedConditions.elementToBeClickable(By.cssSelector("li:nth-child(10) a:nth-child(1)")));
	Assert.assertTrue(name.getText().contains("Logged in as "+nombre));
	Reporter.log("The name is showed<br>");
	}
	
	

	@Test(description = "Logoutfrom create account", priority = 4)

	public void S007_Logout() throws InterruptedException {
		
		driver.findElement(By.cssSelector("a[href='/logout']")).click();
		
		
		WebElement logout = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-qa='login-button']")));
		Assert.assertTrue(logout.isDisplayed());
		Reporter.log("The Login form appears<br>");
	}
	
	@Test(description = "Go to Home", priority = 5)

	public void S005_GoHome() throws InterruptedException {
		
		driver.findElement(By.cssSelector("li:nth-child(1) a:nth-child(1)")).click();
		WebElement login = new WebDriverWait(driver, Duration.ofSeconds(10))
				
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/login']")));
		Assert.assertEquals("FEATURES ITEMS", driver.findElement(By.cssSelector("div[class='features_items'] h2[class='title text-center']")).getText());
		Reporter.log("The webpage loads correctly<br>");
	}
	
	@Test(description = "Click login", priority = 6)

	public void S006_GoLogin() throws InterruptedException {
		
		driver.findElement(By.cssSelector("a[href='/login']")).click();
		WebElement login = new WebDriverWait(driver, Duration.ofSeconds(10))
				
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div[class='signup-form'] h2")));
		Assert.assertEquals("New User Signup!", login.getText());
		Reporter.log("Login correctly<br>");
	}
	
	@Test(description = "Login create account", priority = 7)

	public void S007_TypeEmail() throws InterruptedException {
		
		
		WebElement signup = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-qa='signup-button']")));
			driver.findElement(By.cssSelector("input[placeholder='Name']")).sendKeys(nombre);
			driver.findElement(By.cssSelector("input[data-qa='signup-email']")).sendKeys(correo);
			signup.click();
			
			WebElement error = new WebDriverWait(driver, Duration.ofSeconds(10))
					.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("body > section:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(3) > div:nth-child(1) > form:nth-child(2) > p:nth-child(5)")));
		Assert.assertEquals("Email Address already exist!",error.getText());
		Reporter.log("The error message was displayed Succesfull<br>");
	}
	
	
	@AfterTest
	public void LastStep() throws InterruptedException {
		System.out.println("END");
		// Script Ends and closes browser
		Thread.sleep(3000);
		driver.close();
	}
}
