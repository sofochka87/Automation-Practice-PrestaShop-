package com.automationPractice;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class PrestaShopLogInNegativeScenarios {

	WebDriver driver;

	@BeforeMethod
	public void setUp() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@Test
	public void wrongCredentialsTest() {

		driver.get(" http://automationpractice.com");
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.className("login")));
		driver.findElement(By.className("login")).click();
		driver.findElement(By.id("email")).sendKeys("emailwrong@gmail.com");
		driver.findElement(By.id("passwd")).sendKeys("asdfg12345");
		driver.findElement(By.id("SubmitLogin")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector("#center_column>div>ol>li")).getText(),
				"Authentication failed.");
	}

	@Test
	public void invalidEmailTest() {
		driver.get("http://automationpractice.com");
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.className("login")));
		driver.findElement(By.className("login")).click();
		driver.findElement(By.id("email")).sendKeys("emailwrong@");
		driver.findElement(By.id("passwd")).sendKeys("12345");
		driver.findElement(By.id("SubmitLogin")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector("#center_column>div>ol>li")).getText(),
				"Invalid email address.");
	}

	@Test
	public void blankEmailTest() {
		driver.get("http://automationpractice.com ");
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.className("login")));
		driver.findElement(By.className("login")).click();
		driver.findElement(By.id("passwd")).sendKeys("12345");
		driver.findElement(By.id("SubmitLogin")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector("#center_column>div>ol>li")).getText(),
				"An email address required.");
	}

	@Test
	public void blankPasswordTest() {
		driver.get("http://automationpractice.com ");
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.className("login")));
		driver.findElement(By.className("login")).click();
		driver.findElement(By.id("email")).sendKeys("email@gmail.com");
		driver.findElement(By.id("SubmitLogin")).click();
		Assert.assertEquals(driver.findElement(By.cssSelector("#center_column>div>ol>li")).getText(),
				"Password is required.");
	}

	@AfterMethod
	public void tearDown() {
		driver.close();
	}
}
