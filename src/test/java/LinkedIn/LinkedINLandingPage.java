package LinkedIn;

import AbstarctComponents.AbstractClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LinkedINLandingPage extends AbstractClass {


    WebDriver driver;

    public LinkedINLandingPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    By b = By.id("username");

    @FindBy(id="username")
    WebElement userName;

    @FindBy(id="password")
    WebElement password;

    @FindBy(xpath = "//button[@aria-label='Sign in']")
    WebElement singInButton;

    public LinkedInPostInceptorPage loginIntoLinkedIn(String cUserName, String cPassword){
        userName.sendKeys(cUserName);
        password.sendKeys(cPassword);
        singInButton.click();
        return new LinkedInPostInceptorPage(driver);
    }

    public void goToLinkedInLoginPage(){
        driver.get("https://www.linkedin.com/login");
        waitfortheVisbilityOfElement(b);
    }
}
