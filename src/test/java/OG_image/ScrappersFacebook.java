package OG_image;

import FaceBook.FacebbokPostInceptorpage;
import FaceBook.FacebookLandingPage;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import AbstarctComponents.Log;

import org.testng.annotations.Test;

public class ScrappersFacebook extends BaseTest {

	FacebbokPostInceptorpage postInceptorpage;

	ExtentTest test;

	@Test
	public void facebookLanding() throws Exception {

		String fuser = prop.get("Fusername").toString();
		String fpass = prop.getProperty("Fpassword").toString();

		FacebookLandingPage facebookLandingPage = new FacebookLandingPage(getDriver());
		postInceptorpage = new FacebbokPostInceptorpage(getDriver());
		postInceptorpage.goToPostIncepatorPage();// go to debug URL
		Log.info("Navigating to facebook post incepator page...");
		facebookLandingPage.LoginIntotheFacebbook(fuser, fpass);// enter credentials and signIn
		Log.info("Loging in...");

		test = reports.createTest("Test Facebook Scrapper", "Please see the details tab below for the test URL and the OG image.")
				.assignAuthor("Rupesh").assignDevice("Chrome");

		Log.info("Validating each URL and taking screen shots...");
	}

	@Test(dataProvider = "needTotestUrls", dataProviderClass = DBScrappers.class, dependsOnMethods = {
			"facebookLanding" })
	public void facebookPostInce(String urls) throws Exception {
		String path = postInceptorpage.validateOGImage(urls, "window.scrollBy(0, 500);", "FaceBook.png");
		test.info("Test URL: " + urls);
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(path).build());

	}

	@Test(dependsOnMethods = {"facebookPostInce"})
	public void logLastLine() {
		Log.info("Facebook scrappers validation has been completed..!");
	}

}
