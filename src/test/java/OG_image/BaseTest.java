package OG_image;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import AbstarctComponents.Log;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;

import java.awt.*;
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
		
		ChromeOptions options = new ChromeOptions();

		// Stealth options
		options.addArguments("--disable-blink-features=AutomationControlled");
		options.setExperimentalOption("excludeSwitches", Arrays.asList("enable-automation"));
		options.setExperimentalOption("useAutomationExtension", false);

		// Optional: Use a real user agent
		options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) "
				+ "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36");

		//options.addArguments("--headless=new"); // Use "--headless" for older
		// versions
		options.addArguments("--disable-gpu");
		//options.addArguments("--window-size=1920,1080");

		options.addArguments("start-maximized");
		
		//System.setProperty("webdriver.edge.driver", "C:\\Users\\v-rkammili\\Downloads\\edgedriver_win64\\msedgedriver.exe");

		WebDriver driver = new ChromeDriver(options);
		((JavascriptExecutor) driver).executeScript(
			    "Object.defineProperty(navigator, 'webdriver', {get: () => undefined})"
			);


		Log.info("Browser session satrted...");
		setTdriver(driver);
		getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}

	@AfterTest
	public void afterTest() {
		getDriver().quit();
		Log.info("Browser session ended...");

	}

	@BeforeSuite
	public void beforeSuite() throws IOException {

		sparkReporter = new ExtentSparkReporter("static/index.html");
		reports = new ExtentReports();
		sparkReporter.loadJSONConfig(new File("./src/test/resources/extent-reports-json.json"));
		// sparkReporter.config().setOfflineMode(true);
		reports.attachReporter(sparkReporter);

//		String css = new String(Files.readAllBytes(Paths.get("./src/test/resources/extent.css")),
//				StandardCharsets.UTF_8);
//		String js = new String(Files.readAllBytes(Paths.get("./src/test/resources/custom.js")), StandardCharsets.UTF_8);
		
//		sparkReporter.config().setCss(css);
//		sparkReporter.config().setJs(js);

		reports.setSystemInfo("OS", System.getProperty("os.name"));
		reports.setSystemInfo("java", System.getProperty("java.version"));
		System.out.println("Before suite");

	}

	@AfterSuite
	public void afterSuite() throws IOException {
		reports.flush();
		Desktop.getDesktop().browse(new File("static/index.html").toURI());
		tdriver.remove();
		//EmailConfig.sendEmail();
		System.out.println("After suite");
	}

}
