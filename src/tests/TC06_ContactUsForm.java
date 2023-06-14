package tests;

import java.time.Duration;
import java.util.Random;
import java.util.function.Function;

import org.openqa.selenium.Alert;
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


public class TC06_ContactUsForm {
	String nombre = "David";
	String asunto = "Asunto";
	String mensaje = "Mensaje Generico";
	Random rand = new Random();
	int upperbound = 2555;
	int int_random1 = rand.nextInt(upperbound);
	int int_random2 = rand.nextInt(upperbound);
	 String MainWindow =null;

	String correo = "david" + int_random1 + int_random2 + "@gmail.com";
	WebDriver driver = null;
	@BeforeTest(description = "Setup the driver")
	public void S000_BeforeTest() throws InterruptedException {
		// set driver
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		MainWindow = driver.getWindowHandle();
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

	@Test(description = "Click on Contact Us button", priority = 2)
	public void S002_LogInClick() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (elements.length > 0) elements[0].remove()");
		driver.findElement(By.cssSelector("body")).click();
		driver.findElement(By.cssSelector("a[href='/contact_us']")).click();
		try{ 
			driver.findElement(By.cssSelector("a[href='/contact_us']")).click();
		}catch (Exception e) {
			// TODO: handle exception
		}
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
	       		  .withTimeout(Duration.ofSeconds(10))
	       		  .pollingEvery(Duration.ofSeconds(2))
	       		  .ignoring(NoSuchElementException.class);
	       	
	       	WebElement message = wait.until(new Function<WebDriver, WebElement>() {
	            public WebElement apply(WebDriver driver) {
	                return driver.findElement(By.cssSelector("div[class='contact-form'] h2[class='title text-center']"));
	              }
	            });
		Assert.assertEquals("GET IN TOUCH", message.getText());
		Reporter.log("The Contact page loads correctly<br>");
	}
	
	@Test(description = "Full fill Contact Form", priority = 3)
	public void S003_FillForm() {
		// Gender
		WebElement name = driver.findElement(By.cssSelector("input[placeholder='Name']"));
		name.sendKeys(nombre);
		WebElement email = driver.findElement(By.cssSelector("input[placeholder='Email']"));
		email.sendKeys(correo);
		WebElement subject = driver.findElement(By.cssSelector("input[placeholder='Subject']"));
		subject.sendKeys(asunto);
		WebElement message = driver.findElement(By.cssSelector("#message"));
		message.sendKeys(mensaje);
		Assert.assertEquals(nombre, name.getAttribute("value"));
		Assert.assertEquals(correo, email.getAttribute("value"));
		Assert.assertEquals(asunto, subject.getAttribute("value"));
		Assert.assertEquals(mensaje, message.getAttribute("value"));
		
		Reporter.log("The form was filled <br>");
	}
	
	@Test(description = "Full fill Contact Form", priority = 4)
	public void S004_UplaodFile() {
		// Gender
		WebElement file = driver.findElement(By.cssSelector("input[name='upload_file']"));
		file.sendKeys("D:\\Imagenes\\foto1.jpg");
		
		Assert.assertTrue(file.getAttribute("value").contains("foto1.jpg"));
		Reporter.log("The file was upload <br>");
	}
	
	@Test(description = "Submit Contact Form", priority = 5)
	public void S005_SubmitForm() {
		// Gender
		WebElement submit = driver.findElement(By.cssSelector("input[value='Submit']"));
		submit.click();
		
		  // This step will result in an alert on screen
		  
		    Alert simpleAlert = driver.switchTo().alert();
		    simpleAlert.accept();
		    driver.switchTo().window(MainWindow);
		    
		    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
		       		  .withTimeout(Duration.ofSeconds(10))
		       		  .pollingEvery(Duration.ofSeconds(2))
		       		  .ignoring(NoSuchElementException.class);
		       	
		       	WebElement message = wait.until(new Function<WebDriver, WebElement>() {
		            public WebElement apply(WebDriver driver) {
		                return driver.findElement(By.cssSelector(".status.alert.alert-success"));
		              }
		            });
		       	
		       
			    
		Assert.assertTrue(message.getText().contains("Success! Your details have been submitted successfully."));
		Reporter.log("The form was sent correctly <br>");
	}
	
	@Test(description = "Go to the webpage", priority = 6)
	public void S006_MainPage() throws InterruptedException {
		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (elements.length > 0) elements[0].remove()");
		driver.findElement(By.cssSelector("body")).click();
		WebElement home = driver.findElement(By.cssSelector(".fa.fa-angle-double-left"));
		home.click();
		try {
		home.click();
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		WebElement inicio = new WebDriverWait(driver, Duration.ofSeconds(10))
		.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[class='features_items'] h2[class='title text-center']")));
		
		Assert.assertEquals("FEATURES ITEMS", inicio.getText());
		Reporter.log("The webpage loads correctly<br>");
	}
	
	@AfterTest
	public void LastStep() throws InterruptedException {
		System.out.println("END");
		// Script Ends and closes browser
		Thread.sleep(3000);
		driver.close();
	}
	
}
