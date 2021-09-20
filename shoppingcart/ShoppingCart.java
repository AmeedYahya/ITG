package shoppingcart;

import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.pdfbox.pdmodel.interactive.action.OpenMode;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;



@SuppressWarnings("unused")
public class ShoppingCart {
    
WebDriver driver;
 
//Store current project workspace location in a string variable ‘path’
String path = System.getProperty("user.dir");
     
@SuppressWarnings("deprecation")
@BeforeTest
public void SetDriver(){
 
	//Mention the location of ChromeDriver in localsystem
	System.setProperty("webdriver.chrome.driver",path+"\\Driver\\chromedriver.exe");
	driver = new ChromeDriver(); // Object is created- Chrome browser is opened
	driver.manage().window().maximize();
	driver.get("https://www.garnethill.com/ShoppingCartView");
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	driver.manage().deleteAllCookies();
 
}


@SuppressWarnings("deprecation")
@Test(priority=1)
public void verifyHeader(){
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	String ActualTitle = driver.getTitle();
	String ExpectedTitle = "Shopping Cart | Garnet Hill";
	Assert.assertEquals(ActualTitle, ExpectedTitle);
	System.out.println("Header Verified");

}


@Test(priority=2)
public void verifyEmptyShoppingCart(){
	String bodyText = driver.findElement(By.tagName("body")).getText();
	Assert.assertTrue(bodyText.contains("Your shopping cart is empty. Please choose a category above to start shopping or log in to see items previously saved in your cart."), "Empty Shopping Cart Message Displayed Correctly");
	System.out.println("Empty Shopping Cart Message Displayed Correctly");

}

@SuppressWarnings("deprecation")
@Test(priority=3)
public void verifyForgottenPassword(){
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);	
	driver.findElement(By.linkText("Forgot Your Password?")).click();
	String bodyText = driver.findElement(By.tagName("body")).getText();
	Assert.assertTrue(bodyText.contains("Your password will be reset and sent to your email account. Enter your Email Address and click 'Continue'"), "Modal Displayed");
	System.out.println("Modal Displayed");
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	driver.get("https://www.garnethill.com/ShoppingCartView");

}

//@SuppressWarnings("deprecation")
//@AfterTest()
//public void delay() {
//	driver.get("https://www.garnethill.com/ShoppingCartView");
//	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//	driver.manage().deleteAllCookies();
//	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//	
//}

@Test(priority=4)
public void verifyInvalidEmailInvalidPassword() throws IOException{
	FileInputStream fs = new FileInputStream(path+"\\Data\\EmailTest.xlsx");
	//Creating a workbook
	@SuppressWarnings("resource")
	XSSFWorkbook workbook = new XSSFWorkbook(fs);
	XSSFSheet sheet = workbook.getSheetAt(0);
	Row row1 = sheet.getRow(0);
	Cell cell1 = row1.getCell(0);
	String key1 = cell1.getStringCellValue();
	Row row2 = sheet.getRow(2);
	Cell cell2 = row2.getCell(0);
	String key2 = cell2.getStringCellValue();
	WebElement email = driver.findElement(By.xpath("//input[@id=\"gwt-sign-in-modal\"]"));
	WebElement password = driver.findElement(By.xpath("//input[@id='passwordReset']"));
	email.sendKeys(key1);
	password.sendKeys(key2);
	driver.findElement(By.xpath("//button[@id='logonButton']")).click();
	String bodyText = driver.findElement(By.tagName("body")).getText();
	Assert.assertTrue(bodyText.contains("Error: Please enter Email Address in valid format."), "Email is Invalid, Error Message Displayed");
	System.out.println("Both Entries Invalid, Error Message Displayed");
}

//@Test(priority=5)
//public void verifyValidEmailInvalidPassword() throws IOException{
//	FileInputStream fs = new FileInputStream(path+"\\Data\\EmailTest.xlsx");
//	//Creating a workbook
//	@SuppressWarnings("resource")
//	XSSFWorkbook workbook = new XSSFWorkbook(fs);
//	XSSFSheet sheet = workbook.getSheetAt(0);
//	Row row1 = sheet.getRow(1);
//	Cell cell1 = row1.getCell(0);
//	String key1 = cell1.getStringCellValue();
//	Row row2 = sheet.getRow(2);
//	Cell cell2 = row2.getCell(0);
//	String key2 = cell2.getStringCellValue();
//	WebElement email = driver.findElement(By.xpath("//input[@id=\"gwt-sign-in-modal\"]"));
//	WebElement password = driver.findElement(By.xpath("//input[@id='passwordReset']"));
//	email.sendKeys(key1);
//	password.sendKeys(key2);
//	driver.findElement(By.xpath("//button[@id='logonButton']")).click();
//	String bodyText = driver.findElement(By.tagName("body")).getText();
//	Assert.assertFalse(bodyText.contains("Error: Please enter Email Address in valid format."), "Email is Invalid, Error Message Displayed");
//	Assert.assertTrue(bodyText.contains("An unknown server response error has occurred. Status code is 403"), "Password is Invalid, Error Message Displayed");
//	System.out.println("Email is Valid But Password is Invalid");
//}


}
