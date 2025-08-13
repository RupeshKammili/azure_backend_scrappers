package FaceBook;

import AbstarctComponents.AbstractClass;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FacebookLandingPage extends AbstractClass {

    WebDriver driver;

    public FacebookLandingPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    //driver.findElement(By.id("email")).sendKeys("webdev.surface@gmail.com");
    @FindBy(id = "email")
    WebElement userName;

    //driver.findElement(By.id("pass")).sendKeys("Surface");
    @FindBy(id="pass")
    WebElement password;

    //driver.findElement(By.xpath("//button[.='Log in']")).click();
    @FindBy(xpath = "//button[.='Log in']")
    WebElement signInButton;
    
    By bdebugele = By.xpath("//input[contains(@placeholder, 'Enter a URL')]");

    public void LoginIntotheFacebbook(String cUserName, String cPassword) throws Exception {
    	Actions action = new Actions(driver);
        action.moveToElement(userName).click().sendKeys(cUserName).perform();
        action.moveToElement(password).click().sendKeys(cPassword).perform();
        signInButton.click();
        waitfortheVisbilityOfElement(bdebugele);
    }

}
