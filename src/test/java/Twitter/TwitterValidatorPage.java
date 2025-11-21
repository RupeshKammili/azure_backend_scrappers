package Twitter;

import AbstarctComponents.AbstractClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TwitterValidatorPage extends AbstractClass {

    WebDriver driver;

    public TwitterValidatorPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //driver.findElement(By.xpath("//input[@name='url']")).sendKeys("https://www.microsoft.com/en-us/surface");
    @FindBy(xpath = "//input[@name='url']")
    WebElement testPageURL;

    //driver.findElement(By.xpath("//input[@value='Preview card']")).click();
    @FindBy(xpath = "//button[.='Check preview']")
    WebElement previewCardButton;

    //driver.findElement(By.xpath("//input[@name='url']")).clear();
    @FindBy(xpath = "//input[@name='url']")
    WebElement clearPreviewCard;

    By b = By.cssSelector(".css-13zknum");

    int i=0;
    public String twitterPageurlsValidation(String pageUrl) throws Exception {

        testPageURL.sendKeys(pageUrl);
        previewCardButton.click();
        executeJSScript("window.scrollBy(0,550);");
        
        Thread.sleep(2000);
        waitfortheVisbilityOfElement(b);
        
        String path = takeScreeShot(i + "Twitter.jpg");
        executeJSScript("window.scrollTo(500,0);");
        
        Thread.sleep(2000);
        clearPreviewCard.clear();
        i++;
        return path;
    }

}
