package AbstarctComponents;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.time.Duration;

public class AbstractClass {

    WebDriver driver;

    public AbstractClass(WebDriver driver){
        this.driver = driver;
    }

    public void waitfortheVisbilityOfElement(By b){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
        wait.until(ExpectedConditions.visibilityOfElementLocated(b));
    }

    public String takeScreeShot(String fileName) throws Exception{
        TakesScreenshot ts = (TakesScreenshot) driver;
        File fs = ts.getScreenshotAs(OutputType.FILE);

        // Get static files path from environment variable, fallback to local ./static folder
        String staticFilesPath = System.getenv("STATIC_FILES_PATH");
        if(staticFilesPath == null || staticFilesPath.isEmpty()){
            staticFilesPath = System.getProperty("user.dir") + File.separator + "static";
        }

        // Ensure directory exists
        File dir = new File(staticFilesPath);
        if(!dir.exists()) {
            dir.mkdirs();
        }

        // Create the destination file
        File fd = new File(dir, fileName);

        // Copy screenshot file to destination
        FileUtils.copyFile(fs, fd);

        System.out.println("Screen shot taken successfully at: " + fd.getAbsolutePath());
        return fileName;
    }

    public void executeJSScript(String key){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(key);
    }
}
