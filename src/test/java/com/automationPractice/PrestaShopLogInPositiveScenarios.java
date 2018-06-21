package com.automationPractice;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.javafaker.Address;
import com.github.javafaker.Faker;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PrestaShopLogInPositiveScenarios {
	WebDriver driver;
	Random random;
	Faker faker;
    String firstName;
    String lastName;
    String email;
    Address address;
    String password;
    
	@BeforeMethod
	public void setup() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@Test

	public void LoginTest() throws InterruptedException {
		random = new Random();
		faker = new Faker();
		firstName = faker.name().firstName();
		lastName = faker.name().lastName();
		email = faker.name().username() + "@mail.com";
		address = faker.address();
		password = firstName+lastName;
		
		driver.get("http://automationpractice.com/");
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='login']")));
		driver.findElement(By.xpath("//a[@class='login']")).click(); 
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.id("email_create")));
		driver.findElement(By.id("email_create")).sendKeys(email);
		driver.findElement(By.id("SubmitCreate")).click();
		
		Thread.sleep(3000);
		
		for(String newWindow : driver.getWindowHandles()){
			   driver.switchTo().window(newWindow); }
		
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.id("customer_firstname")));
		driver.findElement(By.id("customer_firstname")).sendKeys(firstName);
		driver.findElement(By.id("customer_lastname")).sendKeys(lastName);
		driver.findElement(By.id("passwd")).sendKeys(password);
		driver.findElement(By.id("address1")).sendKeys(address.streetAddress());
		driver.findElement(By.id("city")).sendKeys(address.cityName());
		Select dropDown=new Select (driver.findElement(By.id("id_state")));
		dropDown.selectByIndex(random.nextInt(50)+1);
		driver.findElement(By.id("postcode")).sendKeys("12345");
		driver.findElement(By.id("phone_mobile")).sendKeys(faker.phoneNumber().cellPhone());
		driver.findElement(By.id("alias")).sendKeys(address.streetAddress());
		driver.findElement(By.id("submitAccount")).click();
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='logout']")));
		driver.findElement(By.xpath("//a[@class='logout']")).click();
		driver.findElement(By.id("email")).sendKeys(email);
		driver.findElement(By.id("passwd")).sendKeys(password);
		driver.findElement(By.id("SubmitLogin")).click();
		
		String actual = driver.findElement(By.tagName("span")).getText();
		String expected= firstName+" "+lastName;
		Assert.assertEquals(actual,expected);

	}

	@AfterMethod
	public void tearDown() {
		driver.close();
	}
}