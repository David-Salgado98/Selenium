package tests;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

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

public class TC19_ViewAndCart {
WebDriver driver = null;
	List<WebElement> hijos = new ArrayList<>();
	List<String> hijosTexto = new ArrayList<>();
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
	
	@Test(description = "GO to Brand section", priority = 2)
	public void S002_ClickBRand() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("javascript:window.scrollBy(250,250)");
		
		js.executeScript("const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (elements.length > 0) elements[0].remove()");
		driver.findElement(By.cssSelector("body")).click();
		
		WebElement child = driver.findElement(By.cssSelector(".nav.nav-pills.nav-stacked"));
		List<WebElement> brands = child.findElements(By.xpath("./child::*"));
		
		WebElement polo = driver.findElement(By.cssSelector("a[href='/brand_products/Polo']"));
		polo.click();										
		WebElement link = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/brand_products/Polo']")));
		WebElement brand = driver.findElement(By.cssSelector("div[class='brands_products'] h2"));
		Assert.assertTrue( link.getText().contains("POLO"));
		Assert.assertTrue(brands.size()>7);
		Assert.assertEquals("BRANDS", brand.getText());
		
	}
	
	@Test(description = "Check the Brand section", priority = 3)
	public void S003_ClickBrandAgain() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("javascript:window.scrollBy(0,250)");
		js.executeScript("const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (elements.length > 0) elements[0].remove()");
		driver.findElement(By.cssSelector("body")).click();
		
		WebElement child = driver.findElement(By.cssSelector(".nav.nav-pills.nav-stacked"));
		List<WebElement> brands = child.findElements(By.xpath("./child::*"));
		
		
		WebElement hm = driver.findElement(By.cssSelector("a[href='/brand_products/H&M']"));
		hm.click();	
		try{
			hm.click();	
		}catch (Exception e) {
			// TODO: handle exception
		}
		WebElement link = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/brand_products/H&M']")));
		
		WebElement brand = driver.findElement(By.cssSelector("div[class='brands_products'] h2"));
		
		Assert.assertTrue( link.getText().contains("H&M"));
		Assert.assertTrue(brands.size()>7);
		Assert.assertEquals("BRANDS", brand.getText());
		
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
