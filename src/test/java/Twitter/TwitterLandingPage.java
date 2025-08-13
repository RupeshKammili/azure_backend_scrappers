package Twitter;

import AbstarctComponents.AbstractClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TwitterLandingPage extends AbstractClass {
    WebDriver driver;

    public TwitterLandingPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    By b = By.xpath("//input[@name='text']");

    @FindBy(xpath = "//input[@name='text']")
    WebElement userName;

    @FindBy(xpath = "(//span[.='Next'])[1]")
    WebElement nextButton;

    @FindBy(xpath = "//input[@name='password']")
    WebElement password;

    @FindBy(xpath = "(//span[.='Log in'])[1]")
    WebElement clickButton;

    public TwitterValidatorPage loginIntoTwitter(String userNamec, String passwordc){
        userName.sendKeys(userNamec);
        nextButton.click();
        password.sendKeys(passwordc);
        clickButton.click();
        return new TwitterValidatorPage(driver);
    }

    public void goToTwitter(){
        driver.get("https://cards-dev.twitter.com/validator");
        waitfortheVisbilityOfElement(b);

    }



}
