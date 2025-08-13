package OG_image;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import AbstarctComponents.Log;

import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class BaseTest extends EmailConfig {

    public static ThreadLocal<WebDriver> tdriver = new ThreadLocal<>();

    static ExtentReports reports;
    static ExtentSparkReporter sparkReporter;

    public static Properties prop;

    public static void setTdriver(WebDriver driver) {
        tdriver.set(driver);
    }

    public static WebDriver getDriver() {
        return tdriver.get();
    }

    @BeforeTest
    public void beforeTest() throws Exception {
        prop = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("Crendentials.properties")) {
            if (input == null) {
                System.out.println("❌ Unable to find Crendentials.properties in classpath");
            } else {
                prop.load(input);
                System.out.println("✅ Loaded Crendentials.properties successfully");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        EdgeOptions options = new EdgeOptions();

        // Stealth options
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
        options.setExperimentalOption("useAutomationExtension", false);

        // Optional: Use a real user agent
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
                + "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36");

        options.addArguments("--headless=new"); // Use "--headless" for older versions
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        options.addArguments("start-maximized");

        WebDriver driver = new EdgeDriver(options);
        ((JavascriptExecutor) driver)
                .executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})");

        Log.info("Browser session started...");
        setTdriver(driver);
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterTest
    public void afterTest() {
        getDriver().quit();
        Log.info("Browser session ended...");
        tdriver.remove();
    }

    @BeforeSuite
    public void beforeSuite() throws IOException {
        // Use environment variable for report path or fallback
        String reportPath = System.getenv("REPORT_PATH");
        if (reportPath == null || reportPath.isEmpty()) {
            reportPath = "static/index.html";
        }

        sparkReporter = new ExtentSparkReporter(reportPath);

        // Use environment variable for extent JSON config path or fallback
        String jsonConfigPath = System.getenv("EXTENT_JSON_CONFIG_PATH");
        if (jsonConfigPath == null || jsonConfigPath.isEmpty()) {
            jsonConfigPath = "./src/test/resources/extent-reports-json.json"; // Adjust if needed
        }

        File jsonConfigFile = new File(jsonConfigPath);
        if (jsonConfigFile.exists()) {
            sparkReporter.loadJSONConfig(jsonConfigFile);
            System.out.println("✅ Loaded extent-reports-json.json from: " + jsonConfigFile.getAbsolutePath());
        } else {
            System.out.println("❌ extent-reports-json.json not found at: " + jsonConfigFile.getAbsolutePath());
        }

        reports = new ExtentReports();
        reports.attachReporter(sparkReporter);

        reports.setSystemInfo("OS", System.getProperty("os.name"));
        reports.setSystemInfo("Java", System.getProperty("java.version"));
        System.out.println("Before suite");
    }

    @AfterSuite
    public void afterSuite() throws IOException {
        reports.flush();

        // Removed Desktop browsing call — not applicable on Azure

        System.out.println("After suite");
    }
}
