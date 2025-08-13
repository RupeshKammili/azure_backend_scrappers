package LinkedIn;

import AbstarctComponents.AbstractClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LinkedInPostInceptorPage extends AbstractClass {

    WebDriver driver;

    public LinkedInPostInceptorPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }



    @FindBy(xpath = "//input[contains(@placeholder, 'Enter a URL to see how')]")
    WebElement inputURL;

    @FindBy(css="#js-inspect-button")
    WebElement clickInspect;

    By b = By.cssSelector(".postPreview__preview.artdeco-card.ember-view");
    
    @FindBy(css= "#js-url-input")
    WebElement clearInput;

    int i=0;
    public String validateOGimageForURL(String pageUrl, String fileName) throws Exception {
        inputURL.sendKeys(pageUrl);
        clickInspect.click();
        waitfortheVisbilityOfElement(b);
        clearInput.clear();
        Thread.sleep(2000);
        String path = takeScreeShot(i+ fileName);
        i++;
        return path;
       
    }

    public void goToLinkedInPostInceptorPage(){
        driver.navigate().to("https://www.linkedin.com/post-inspector/");
    }
}
