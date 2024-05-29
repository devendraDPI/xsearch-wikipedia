package xsearchwikipedia;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;

public class TestCases {
    WebDriver driver;

    public TestCases() {
        System.out.println("Constructor: TestCases");
        System.out.println("Start Tests: TestCases");

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        // Set log level and type
        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");

        // Set path for log file
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "logs" + File.separator + "chromedriver.log");

        driver = new ChromeDriver(options);

        // Implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    public static void logStatus(String testCaseID, String testStep, String testMessage, String testStatus) {
        System.out.println(String.format("%s | %s | %s | %s | %s", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), testCaseID, testStep, testMessage, testStatus));
    }

    public void endTest() {
        System.out.println("End Tests: TestCases");
        driver.quit();
    }

    /**
     * <STRONG>Test Case 01</STRONG>: Verify Wikipedia Homepage URL <p>
     *
     *  1. Navigate to the Wikipedia homepage (https://www.wikipedia.org) <p>
     *  2. Ensure that the current URL contains the expected title "wikipedia" <p>
     */
    public void testCase01() {
        logStatus("TC001", "Start", "Verify Wikipedia Homepage URL", "DONE");

        // Navigate to the Wikipedia homepage (https://www.wikipedia.org)
        driver.get("https://www.wikipedia.org");

        // Ensure that the current URL contains the expected title "wikipedia"
        String expectedTitle = "wikipedia";
        String actualTitle = driver.getCurrentUrl();
        boolean status = actualTitle.contains(expectedTitle);
        logStatus("TC001", "Step", "Verify Wikipedia Homepage URL contains expected title", status ? "PASS" : "FAIL");

        logStatus("TC001", "End", "Verify Wikipedia Homepage URL", status ? "PASS" : "FAIL");
    }

    /**
     * <STRONG>Test Case 02</STRONG>: Verify Wikipedia Header and Footer <p>
     *
     *  1. Navigate to the Wikipedia homepage (https://www.wikipedia.org) <p>
     *  2. Verify if the header text is "Wikipedia" <p>
     *  3. Confirm that the footer links contain "Terms of Use" and "Privacy Policy" <p>
     */
    public void testCase02() {
        logStatus("TC002", "Start", "Verify Wikipedia Header and Footer", "DONE");

        boolean status = false;

        // Navigate to the Wikipedia homepage (https://www.wikipedia.org)
        driver.get("https://www.wikipedia.org");

        // Verify if the header text is "Wikipedia"
        WebElement headerText = driver.findElement(By.xpath("//span[contains(@class, 'central-textlogo')]"));
        status = headerText.getText().contains("Wikipedia");
        logStatus("TC002", "Step", "Verify Header text is 'Wikipedia'", status ? "PASS" : "FAIL");

        // Confirm that the footer links contain "Terms of Use" and "Privacy Policy"
        WebElement termsOfUseActual = driver.findElement(By.xpath("//a[contains(text(), 'Terms of Use')]"));
        WebElement privacyPolicyActual = driver.findElement(By.xpath("//a[contains(text(), 'Privacy Policy')]"));
        status = termsOfUseActual.getText().contains("Terms of Use") && privacyPolicyActual.getText().contains("Privacy Policy");
        logStatus("TC002", "Step", "Footer links contain 'Terms of Use' and 'Privacy Policy'", status ? "PASS" : "FAIL");

        logStatus("TC002", "End", "Verify Wikipedia Header and Footer", status ? "PASS" : "FAIL");
    }

    /**
     * <STRONG>Test Case 03</STRONG>: Verify the search functionality <p>
     *
     *  1. Navigate to the Wikipedia homepage (https://www.wikipedia.org)
     *  2. Search for the text "apple" in the search bar
     *  3. Click on the search result for "Apple Inc."
     *  4. Check if "Steve Jobs" is listed as a founder
     */
    public void testCase03() {
        logStatus("TC003", "Start", "Verify the search functionality", "DONE");

        boolean status = false;

        // Navigate to the Wikipedia homepage (https://www.wikipedia.org)
        driver.get("https://www.wikipedia.org");

        // Search for the text "apple" in the search bar.
        WebElement searchBox = driver.findElement(By.xpath("//input[contains(@id, 'searchInput')]"));
        searchBox.sendKeys("apple");

        // Click on the search result for "Apple Inc."
        WebElement suggestionsDropdown = driver.findElement(By.xpath("//h3[contains(text(), 'Inc.')]"));
        suggestionsDropdown.click();

        // Check if "Steve Jobs" is listed as a founder
        WebElement founder = driver.findElement(By.xpath("//table//th[contains(text(), 'Founders')]/following-sibling::td//a[contains(text(), 'Steve Jobs')]"));
        status = founder.getText().equals("Steve Jobs");
        logStatus("TC003", "Step", "'Steve Jobs' is listed as a founder", status ? "PASS" : "FAIL");

        logStatus("TC003", "End", "Verify the search functionality", status ? "PASS" : "FAIL");
    }

    /**
     * <STRONG>Test Case 04</STRONG>: Validate Hyperlink Functionality <p>
     *
     *  1. Navigate to the Wikipedia homepage (https://www.wikipedia.org) <p>
     *  2. Search for the text "microsoft" in the search bar <p>
     *  3. Click on the search result link for "Microsoft" <p>
     *  4. Check if "Bill Gates" is listed as a founder, and click on his name if found <p>
     *  5. Check if the opened URL contains "Bill_Gates" <p>
     */
    public void testCase04() {
        logStatus("TC004", "Start", "Validate Hyperlink Functionality", "DONE");

        boolean status = false;

        // Navigate to the Wikipedia homepage (https://www.wikipedia.org)
        driver.get("https://www.wikipedia.org");

        // Search for the text "microsoft" in the search bar
        WebElement searchBox = driver.findElement(By.xpath("//input[contains(@id, 'searchInput')]"));
        searchBox.sendKeys("microsoft");

        // Click on the search result link for "Microsoft"
        WebElement suggestionsDropdown = driver.findElement(By.xpath("//h3/em[text()='Microsoft']"));
        suggestionsDropdown.click();

        // Check if "Bill Gates" is listed as a founder, and click on his name if found
        WebElement founder = driver.findElement(By.xpath("//table//th[contains(text(), 'Founders')]/following-sibling::td//a[contains(text(), 'Bill Gates')]"));
        status = founder.isDisplayed();
        logStatus("TC004", "Step", "'Bill Gates' is listed as a founder", status ? "PASS" : "FAIL");

        if (status) {
            founder.click();
        }

        // Check if the opened URL contains "Bill_Gates"
        String currentUrl = driver.getCurrentUrl();
        status = currentUrl.contains("Bill_Gates");
        logStatus("TC004", "Step", "Opened URL contains 'Bill_Gates'", status ? "PASS" : "FAIL");

        logStatus("TC004", "End", "Validate Hyperlink Functionality", status ? "PASS" : "FAIL");
    }

    /**
     * <STRONG>Test Case 05</STRONG>: Verify 'About Wikipedia' Link and URL in Dropdown <p>
     *
     *  1. Navigate to the Wikipedia English homepage (https://en.wikipedia.org/wiki/Main_Page)
     *  2. Click on the Main menu to expand it
     *  3. Click on the link for "About Wikipedia" in the menu
     *  4. Check if the opened URL contains "About"
     */
    public void testCase05() {
        logStatus("TC005", "Start", "Verify 'About Wikipedia' Link and URL in Dropdown", "DONE");

        boolean status = false;

        // Navigate to the Wikipedia English homepage (https://en.wikipedia.org/wiki/Main_Page)
        driver.get("https://en.wikipedia.org/wiki/Main_Page");

        // Click on the Main menu to expand it
        WebElement mainMenu = driver.findElement(By.xpath("//div[contains(@class, 'vector-main-menu-dropdown')]"));
        mainMenu.click();

        // Click on the link for "About Wikipedia" in the menu.
        WebElement aboutWikipedia = driver.findElement(By.xpath("//a[contains(@title, 'about Wikipedia')]"));
        aboutWikipedia.click();

        // Check if the opened URL contains "About"
        String currentUrl = driver.getCurrentUrl();
        status = currentUrl.contains("About");
        logStatus("TC005", "Step", "Current URL contains the expected title 'About'", status ? "PASS" : "FAIL");

        logStatus("TC005", "End", "Verify 'About Wikipedia' Link and URL in Dropdown", status ? "PASS" : "FAIL");
    }
}
