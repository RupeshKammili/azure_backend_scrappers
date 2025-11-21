
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.*;

public class TestTwitter {

	public static void main(String[] args) {
		
		
		String userDataDir = "C:\\SeleniumProfiles";
 
        String profileName = "Profile 1";   
 
        EdgeOptions options = new EdgeOptions();
        options.addArguments("user-data-dir=" + userDataDir); 
        options.addArguments("profile-directory=" + profileName);  

        System.setProperty("webdriver.edge.driver", "C:\\Users\\MKTtools\\Downloads\\edgedriver_win64\\msedgedriver.exe");
		WebDriver driver = new EdgeDriver(options);
		driver.manage().window().maximize();
		driver.get("https://cards-dev.twitter.com/validator");
	

	}

}
