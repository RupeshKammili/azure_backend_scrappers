package FaceBook;

import AbstarctComponents.AbstractClass;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FacebbokPostInceptorpage extends AbstractClass {

    WebDriver driver;


    public FacebbokPostInceptorpage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //driver.findElement(By.xpath("(//a[.='Log In'])[1]")).click();
    @FindBy(xpath = "(//a[.='Log In'])[1]")
    WebElement clickFacebook;

    //driver.findElement(By.xpath("//span[.='Log in with Facebook']")).click();
    @FindBy(xpath = "//span[.='Log in with Facebook']")
    WebElement click1Facebook;

    // WebElement element = driver.findElement(By.xpath("(//input[@name='q'])[2]"));
    @FindBy(xpath = "//input[contains(@placeholder, 'Enter a URL')]")
    WebElement inputURL;
    
    //driver.findElement(By.xpath("//button[.='Debug']")).click();
    @FindBy(xpath = "//button[.='Debug']")
    WebElement debugButton;
    
    By b = By.xpath("//button[.='Scrape Again']");
    
    @FindBy(xpath = "//button[.='Scrape Again']")
    WebElement scrapButton;

    int i=0;
    public String validateOGImage(String pageURL, String Key, String fileName) throws Exception {
        inputURL.sendKeys(pageURL);
        debugButton.click();
        waitfortheVisbilityOfElement(b);
        scrapButton.click();
        if(scrapButton.isEnabled()) {
        	executeJSScript(Key);
        }
        String path = takeScreeShot(i+ fileName);
        i++;
        inputURL.clear();
        return path;
    }

    public void goToPostIncepatorPage(){
        driver.navigate()
                .to("https://developers.facebook.com/tools/debug/");
        clickFacebook.click();
        click1Facebook.click();
    }
}
