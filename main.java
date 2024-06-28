package com.org.happyfox;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

public class Testcase101 {

    public static void main(String[] args) throws InterruptedException, AWTException {
        // Avoid using hardcoded paths use dynamic system paths we can use System.getProperty
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Johny\\Downloads\\geckodriver-v0.33.0-win64\\geckodriver.exe"); // We can use selenium Driver manager in selenium v4 ChromeDriver is automatically managed.
        WebDriver driver = new FirefoxDriver();
        driver.get("https://interview.supporthive.com/staff/");
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.findElement(By.id("id_username")).sendKeys("Agent"); // It's not best practice to store username and password in code we can use environment variables or env files for multiple Id's.
        driver.findElement(By.id("id_password")).sendKeys("Agent@123");
        driver.findElement(By.id("btn-submit")).click();
        WebElement tickets = driver.findElement(By.id("ember29"));
        Actions action = new Actions(driver);
        action.moveToElement(tickets).build().perform();
        WebElement statuses = driver.findElement(By.linkText("Statuses"));
        statuses.click();
        driver.findElement(By.xpath("/html/body/div[3]/div/section/section/div/header/button")).click(); // Avoid using Absolute xpaths use dynamic xpaths
        driver.findElement(By.tagName("input")).sendKeys("Issue Created");
        WebElement statusColourSelect = driver.findElement(By.xpath("//div[@class='sp-replacer sp-light']"));
        statusColourSelect.click();
        WebElement statusColourEnter = driver.findElement(By.xpath("//input[@class='sp-input']"));
        statusColourEnter.clear();
        statusColourEnter.sendKeys("#47963f");
        Robot r = new Robot();
        r.keyPress(KeyEvent.VK_ESCAPE); //Avoid using the Robot class for tasks that can be handled by Selenium directly Use action class instead.

        WebElement firstElement = driver.findElement(By.xpath("//a[@id='first-link']"));
        firstElement.click();

        WebElement secondElement = driver.findElement(By.xpath("//a[@id='second-link']")); // We can maintain a generic class for these findElements click and other selenium methods to increase code reusablity.
        secondElement.click();

        driver.findElement(By.tagName("textarea")).sendKeys("Status when a new ticket is created in HappyFox");
        WebElement addCreate = driver.findElement(By.xpath("//button[@class ='hf-entity-footer_primary hf-primary-action ember-view']"));
        addCreate.click();

        Thread.sleep(3000); // Avoid hard sleep try to use explicit waits provided by selenium

        WebElement moveTo = driver.findElement(By.xpath("//td[@class ='lt-cell align-center hf-mod-no-padding ember-view']"));
        action.moveToElement(moveTo).build().perform();
        moveTo.click();

        Thread.sleep(9000); // Avoid hard sleep try to use explicit waits provided by selenium

        WebElement issue = driver.findElement(By.xpath("//div[contains(text(),'Issue Created')]"));
        action.moveToElement(issue).build().perform();

        WebElement make = driver.findElement(By.linkText("Make Default"));
        make.click();
        driver.findElement(By.linkText("Priorities")).click();
        driver.findElement(By.xpath("//header/button[1]")).click();
        driver.findElement(By.tagName("input")).sendKeys("Assistance required");
        driver.findElement(By.tagName("textarea")).sendKeys("Priority of the newly created tickets");
        WebElement button = driver.findElement(By.cssSelector("button[data-test-id='add-priority']"));
        button.click();

        Thread.sleep(9000); // Avoid hard sleep try to use explicit waits provided by selenium

        WebElement tickets2 = driver.findElement(By.id("ember29"));
        action.moveToElement(tickets2).build().perform();
        WebElement priorities2 = driver.findElement(By.linkText("Priorities"));
        priorities2.click();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS); // Multiple implicit waits should not be used as it is a global wait.
        driver.findElement(By.xpath("/html[1]/body[1]/div[3]/div[1]/section[1]/section[1]/div[1]/div[1]/section[1]/div[1]/div[1]/div[2]/div[1]/div[2]/div[1]/table[1]/tbody[1]/tr[9]/td[2]")).click(); // use dynamic xpaths
        driver.findElement(By.linkText("Delete")).click();
        WebElement delete = driver.findElement(By.cssSelector("button[data-test-id='delete-dependants-primary-action']"));
        delete.click();

        Thread.sleep(9000); // use explicit wait
        driver.findElement(By.xpath("/html[1]/body[1]/div[3]/div[1]/header[1]/div[2]/nav[1]/div[7]/div[1]/div[1]")).click(); // use dynamic xpath
        driver.findElement(By.linkText("Logout")).click();
    }

    
    // Method to automate login and navigate to homepage
    public class PagesforAutomationAssignment { // We can use Page Object Model for better structuring and code reusability. Move PagesforAutomationAssignment and other nested classes to their own files for better structure.

        public static void main(String[] args) {  // Better to use Some Testing Library like Junit or Testng.
            ChromeDriver driver = new ChromeDriver(); //Use the WebDriver Interface: This allows you to easily switch to other browsers if needed without changing much of your code.
            driver.get("https://www.happyfox.com");

            LoginPage loginPage = new LoginPage(driver);
            loginPage.login("username", "password");

            HomePage homePage = new HomePage(driver);
            homePage.verifyHomePage();

            driver.quit();
        }

        static class BasePage { //We can make this class abstract to prevent instantiation
            protected WebDriver driver;

            public BasePage(WebDriver driver) {
                this.driver = driver;
            }
        }

        // Class representing Login Page
        static class LoginPage extends BasePage {
            public LoginPage(WebDriver driver) {
                super(driver);
            }

            // Method to perform login
            public void login(String username, String password) {
                driver.findElement(By.id("username")).sendKeys(username);
                driver.findElement(By.id("password")).sendKeys(password);
                driver.findElement(By.id("loginButton")).click();
            }

            // Method to handle forgot password functionality
            public void forgotPassword() {
                driver.findElement(By.linkText("Forgot password?")).click();
            }
        }

        // Class representing Home Page
        static class HomePage extends BasePage {
            public HomePage(WebDriver driver) {
                super(driver);
            }

            // Method to verify if on the home page
            public void verifyHomePage() {
                if (!driver.getCurrentUrl().equals("https://www.happyfox.com/home")) { // Assertions are missing in whole code.
                    throw new IllegalStateException("Not on the home page");
                }
            }

            // Method to navigate to user profile
            public void navigateToProfile() {
                driver.findElement(By.id("profileLink")).click();
            }
        }

        // Class representing a generic Table Page
        public class TablePage extends BasePage {

            private By rowLocator = By.xpath("//table[@id='dataTable']/tbody/tr");

            public TablePage(WebDriver driver) {
                super(driver);
            }

            // Method to retrieve texts from table rows
            public void retrieveRowTexts() {
                List<WebElement> rows = driver.findElements(rowLocator);

                for (int i = 0; i < rows.size(); i++) {
                    WebElement row = rows.get(i);
                    String rowText = row.getText();
                    System.out.println("Row " + i + " Text: " + rowText);
                }
            }
        }
    }
}

