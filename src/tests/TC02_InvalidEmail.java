package tests;
import org.testng.annotations.Test;
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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TC02_InvalidEmail {

	// BEFORE
		WebDriver driver = null;
		Random rand = new Random();
		int upperbound = 2555;
		int int_random1 = rand.nextInt(upperbound);
		int int_random2 = rand.nextInt(upperbound);
		int i = 0;

		// INPUTS
		String nombre = "prueba";
		String correo0 = "testmail" + int_random1 + int_random2 + "gmail.com";
		String correo1 = "testmail" + int_random1 + int_random2;
		String correo2 = "testmail";
		String correo3 = "" + int_random1 + int_random2;
		String correo4 = "testmail" + int_random1 + int_random2 + "@gmailcom";
		String correo5 = "testmail" + int_random1 + int_random2 + "@";
		String correo6 = "@gmail.com";
		String correo7 = "testmail" + int_random1 + int_random2 + "@.com";
		String correo8 = "!#$%&/&())=&&(//ии[";
		String correo9 = "   " + "@gmail.com";
		String correo10 = "testmail" + int_random1 + int_random2 + "@%$&##$.com";

	

		// ARRAYS
		String correos[] = { correo0, correo1, correo2, correo3,
				correo4, 
				correo5, correo6, correo7, correo8, correo9,
				correo10 };
		

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
			Thread.sleep(2000);
			Assert.assertEquals("FEATURES ITEMS", driver.findElement(By.cssSelector("div[class='features_items'] h2[class='title text-center']")).getText());
			Reporter.log("The webpage loads correctly<br>");
		}

		@Test(description = "Click on login button", priority = 2)
		public void S002_LogInClick() {
			driver.findElement(By.cssSelector("a[href='/login']")).click();
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
		       		  .withTimeout(Duration.ofSeconds(10))
		       		  .pollingEvery(Duration.ofSeconds(2))
		       		  .ignoring(NoSuchElementException.class);
		       	
		       	WebElement firstResult = wait.until(new Function<WebDriver, WebElement>() {
		            public WebElement apply(WebDriver driver) {
		                return driver.findElement(By.cssSelector("div[class='signup-form'] h2"));
		              }
		            });
			Assert.assertEquals("Automation Exercise - Signup / Login", driver.getTitle());
			Reporter.log("The login page loads correctly<br>");
		}
		
		@Test(description = "Type an email in the 'Email' box and click on 'create account' button", priority = 3, invocationCount = 10)
		public void S003_EnterEmailAdress() throws InterruptedException {
			// driver.findElement(By.name("email_create")).click();

			driver.findElement(By.cssSelector("input[placeholder='Name']")).clear();
			driver.findElement(By.cssSelector("input[placeholder='Name']")).sendKeys(nombre);
			driver.findElement(By.cssSelector("input[data-qa='signup-email']")).clear();
			driver.findElement(By.cssSelector("input[data-qa='signup-email']")).sendKeys(correos[i]);
			

			
			Assert.assertEquals(correos[i].trim(),
					driver.findElement(By.cssSelector("input[data-qa='signup-email']")).getAttribute("value"));
			driver.findElement(By.cssSelector("button[data-qa='signup-button']")).click();
			
			try
			{
				driver.findElement(By.cssSelector("button[data-qa='signup-button']")).isDisplayed(); 
				
				
			}catch (Exception e) {
				driver.navigate().back();
				Reporter.log(correos[i] + " <br> ");
				i++;
				Assert.fail();
			}

			System.out.println(correos[i]);

			JavascriptExecutor js = (JavascriptExecutor)driver;
			WebElement field = driver.findElement(By.cssSelector("input[data-qa='signup-email']"));
			
			
			
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
		       		  .withTimeout(Duration.ofSeconds(5))
		       		  .pollingEvery(Duration.ofSeconds(1))
		       		  .ignoring(NoSuchElementException.class);
		       	
			Function<WebDriver, Boolean> predicate = new Function<WebDriver, Boolean>()
			{

				public Boolean apply(WebDriver arg0) {
					
					
					 return !(Boolean)js.executeScript("return arguments[0].checkValidity();", field);
					
				}
			};
			wait.until(predicate);
			String message = (String)js.executeScript("return arguments[0].validationMessage;", field);
			System.out.println(message);
			Assert.assertTrue(message != null);

			Reporter.log(message + " <br> ");

			
			i++;

		}

		@AfterTest
		public void FinalStep() throws InterruptedException {
			System.out.println("END");
			// Script Ends and closes browser
			Thread.sleep(5000);
			driver.close();
		}

}
