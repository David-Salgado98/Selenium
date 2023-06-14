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

public class TC03_LoginIncorrectUser {
	WebDriver driver = null;
	String nombre = "David";
	String password = "12345";
	String apellido = "Salgado";
	Random rand = new Random();
	int upperbound = 2555;
	int int_random1 = rand.nextInt(upperbound);
	int int_random2 = rand.nextInt(upperbound);

	String correo = "david" + int_random1 + int_random2 + "@gmail.com";

	
	@BeforeTest(description = "Setup the driver")
	public void S000_BeforeTest() throws InterruptedException {
		// set driver
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@Test(description = "Go to the webpage", priority = 1)
	public void S001_MainPage() throws InterruptedException {
		// Set browser
		driver.get("https://automationexercise.com/");
		WebElement inicio = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='features_items'] h2[class='title text-center']")));
				
				Assert.assertEquals("FEATURES ITEMS", inicio.getText());
		Reporter.log("The webpage loads correctly<br>");
	}

	@Test(description = "Click on login button", priority = 2)
	public void S002_LogInClick() {
		driver.findElement(By.cssSelector("a[href='/login']")).click();
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
	       		  .withTimeout(Duration.ofSeconds(10))
	       		  .pollingEvery(Duration.ofSeconds(2))
	       		  .ignoring(NoSuchElementException.class);
	       	
	       	WebElement login = wait.until(new Function<WebDriver, WebElement>() {
	            public WebElement apply(WebDriver driver) {
	                return driver.findElement(By.cssSelector("div[class='login-form'] h2"));
	              }
	            });
	       	Assert.assertEquals("Login to your account", login.getText());
			Reporter.log("Login correctly<br>");
	}
	@Test(description = "Login with invalid credentials", priority = 3)

	public void S003_TypeEmail() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (elements.length > 0) elements[0].remove()");
		driver.findElement(By.cssSelector("body")).click();
		
		driver.findElement(By.cssSelector("input[data-qa='login-email']")).sendKeys(correo);
		driver.findElement(By.cssSelector("input[placeholder='Password']")).sendKeys(password);
		
		driver.findElement(By.cssSelector("button[data-qa='login-button']")).click();
		try{
			driver.findElement(By.cssSelector("button[data-qa='login-button']")).click();
		}catch (Exception e) {
			// TODO: handle exception
		}
		WebElement logout = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("body > section:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > form:nth-child(2) > p:nth-child(4)")));
		Assert.assertTrue(logout.getText().contains("Your email or password is incorrect!"));
		Reporter.log("Error messaje showed<br>");
	}
	
	@AfterTest
	public void LastStep() throws InterruptedException {
		System.out.println("END");
		// Script Ends and closes browser
		Thread.sleep(3000);
		driver.close();
	}
	
	
}
