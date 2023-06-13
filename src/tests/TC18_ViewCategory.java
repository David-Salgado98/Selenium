package tests;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TC18_ViewCategory {
	
	WebDriver driver = null;
	
	@BeforeTest(description = "Setup the driver")
	public void S000_FirstStep() {
		// Setup driver
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}
	
	@Test(description = "Go to URL 'http://automationpractice.com/index.php'", priority = 1)
	public void S001_GoWebpage() {
		// Go to web page
		driver.get("https://automationexercise.com/");
		new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/login']")));
		
		Assert.assertEquals("FEATURES ITEMS", driver.findElement(By.cssSelector("div[class='features_items'] h2[class='title text-center']")).getText());
		Reporter.log("The webpage loads correctly<br>");

	}
	
	@Test(description = "GO to Women section", priority = 2)
	public void S002_ClickWomen() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("javascript:window.scrollBy(250,250)");
		
		js.executeScript("const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (elements.length > 0) elements[0].remove()");
		driver.findElement(By.cssSelector("body")).click();
		WebElement category = driver.findElement(By.cssSelector("body > section:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > h2:nth-child(1)"));
		WebElement women = driver.findElement(By.cssSelector("body > section:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > h4:nth-child(1) > a:nth-child(1)"));
		women.click();										
		//js.executeScript("javascript:window.scrollBy(250,250)");
		Assert.assertEquals("WOMEN", women.getText());
		Assert.assertEquals("CATEGORY", category.getText());
		
	}
	
	@Test(description = "GO to Dress section", priority = 3)
	public void S003_ClickDress() throws InterruptedException {
		WebElement dress = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/category_products/1']"))); 
		dress.click();
		dress.click();
		
			
			WebElement slevelessDress = new WebDriverWait(driver, Duration.ofSeconds(10))
					.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".title.text-center"))); 
			 
			Assert.assertEquals("WOMEN - DRESS PRODUCTS", slevelessDress.getText());
	}
	
	@Test(description = "GO to Men section", priority = 4)
	public void S004_ClickMen() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("javascript:window.scrollBy(250,250)");
		
		js.executeScript("const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (elements.length > 0) elements[0].remove()");
		driver.findElement(By.cssSelector("body")).click();
		
		WebElement men = driver.findElement(By.cssSelector("body > section:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > div:nth-child(1) > h4:nth-child(1) > a:nth-child(1)"));
		men.click();
		//js.executeScript("javascript:window.scrollBy(250,250)");
		Assert.assertEquals("MEN", men.getText());
		
	}
	
	@Test(description = "GO to Men Tshirts section", priority = 5)
	public void S005_ClickTshirt() throws InterruptedException {
		WebElement tshirt = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/category_products/3']"))); 
		tshirt.click();
		
		
			
			WebElement slevelessDress = new WebDriverWait(driver, Duration.ofSeconds(10))
					.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".title.text-center"))); 
			 
			Assert.assertEquals("MEN - TSHIRTS PRODUCTS", slevelessDress.getText());
	}
	
	@AfterTest
	public void AfterClass() {
		System.out.println("END");
		// Script Ends and closes browser
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.close();
	}
	
	
}
