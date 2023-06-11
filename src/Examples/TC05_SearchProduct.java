package Examples;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
public class TC05_SearchProduct {
	private WebDriver driver;
	String text01 = null;
	String text02 = null;
	
	@BeforeTest
	public void S000_BeforeTest() {
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}


	@Test(description = "Go to the webpage", priority = 1)

	public void S001_OpenWebPage() throws InterruptedException {
		driver.get("https://automationexercise.com/");
		WebElement firstname = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/login']")));
		
		Assert.assertEquals("FEATURES ITEMS", driver.findElement(By.cssSelector("div[class='features_items'] h2[class='title text-center']")).getText());
		Reporter.log("The webpage loads correctly<br>");
	}

	@Test(description = "choose Women Section", priority = 2)
	public void S002_SelectWomen() throws InterruptedException {
		WebElement products = driver.findElement(By.cssSelector("a[href='/products']"));
		products.click();
		Thread.sleep(1000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (elements.length > 0) elements[0].remove()");
		driver.findElement(By.cssSelector("body")).click();
		
		driver.findElement(By.cssSelector("body > section:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > h4:nth-child(1) > a:nth-child(1)")).click();
		js.executeScript("javascript:window.scrollBy(250,250)");
		try {
			WebElement section =  driver.findElement(By.cssSelector("a[href='/category_products/1']"));
			System.out.println(section.getText());
			Assert.assertEquals("DRESS", section.getText());
		}catch (Exception e) {
			Assert.fail();
		}
	}

	@Test(description = "choose T-shirt Section", priority = 3)
	public void S003_SelectTshirt() throws InterruptedException {
		
		
		//driver.findElement(By.cssSelector("body")).click();
		driver.findElement(By.cssSelector("body > section:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > div:nth-child(1) > h4:nth-child(1) > a:nth-child(1)")).click();
		WebElement tshirt = new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/category_products/3']")));
		driver.findElement(By.cssSelector("a[href='/category_products/3']")).click();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("javascript:window.scrollBy(250,250)");
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
	       		  .withTimeout(Duration.ofSeconds(5))
	       		  .pollingEvery(Duration.ofSeconds(1))
	       		  .ignoring(NoSuchElementException.class);
	       	
		Function<WebDriver, Boolean> predicate = new Function<WebDriver, Boolean>()
		{

			public Boolean apply(WebDriver arg0) {
				WebElement element = arg0.findElement(By.xpath("//div[@class='productinfo text-center']//p[contains(text(),'Pure Cotton V-Neck T-Shirt')]"));
				
				
					String mobile = element.getText();
					
					
					if(!mobile.isBlank())
					{
						return true;
					}
				
				
				return false;
			}
		};
		wait.until(predicate);
		text01 = driver.findElement(By.xpath("//div[@class='productinfo text-center']//p[contains(text(),'Pure Cotton V-Neck T-Shirt')]")).getText();
		Assert.assertEquals(text01, "Pure Cotton V-Neck T-Shirt");
		Reporter.log("Name displayed matches with the item searched <br>");
	}

	@Test(description = "search the same item on the search bar", priority = 4)
	public void S004_SearchItem() throws InterruptedException {
		WebElement products = driver.findElement(By.cssSelector("a[href='/products']"));
		products.click();
		Thread.sleep(1000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("const elements = document.getElementsByClassName('adsbygoogle adsbygoogle-noablate'); while (elements.length > 0) elements[0].remove()");
		driver.findElement(By.id("search_product")).sendKeys("Pure Cotton V-Neck T-Shirt");
		driver.findElement(By.id("submit_search")).click();
		Thread.sleep(2000);
		text02 = driver.findElement(By.xpath("//div[@class='productinfo text-center']//p[contains(text(),'Pure Cotton V-Neck T-Shirt')]")).getText();
		js.executeScript("javascript:window.scrollBy(250,550)");
		Assert.assertTrue(text02.contains("Cotton"));
		Reporter.log("Item searched matches with the item displayed <br>");
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
