package Twitter;

import AbstarctComponents.AbstractClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class TwitterLandingPage extends AbstractClass {
    WebDriver driver;

    public TwitterLandingPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public TwitterValidatorPage loginIntoTwitter(){
        return new TwitterValidatorPage(driver);
    }

    public void goToTwitter(){
        driver.get("https://tweethunter.io/tweetpik/twitter-card-validator");
    }



}
