package OG_image;

import Twitter.TwitterLandingPage;
import Twitter.TwitterValidatorPage;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import AbstarctComponents.Log;

import org.testng.annotations.Test;

public class ScrappersTwitter extends BaseTest {

	TwitterValidatorPage twitterValidatorPage;

	ExtentTest test;

	@Test
	public void twitterLanding() {

		TwitterLandingPage lpage = new TwitterLandingPage(getDriver());
		lpage.goToTwitter();

		Log.info("Navigating to Twitter post incepator page...");
		twitterValidatorPage = lpage.loginIntoTwitter();
		Log.info("Loging in...");

		test = reports.createTest("Test Twitter Scrapper", "Please see the details tab below for the test URL and the OG image.")
				.assignAuthor("Rupesh").assignDevice("Chrome");

		Log.info("Validating each URL and taking screen shots...");
	}

	@Test(dataProvider = "needTotestUrls", dataProviderClass = DBScrappers.class, dependsOnMethods = {
			"twitterLanding" })
	public void twitterInspectPost(String urls) throws Exception {

		String path = twitterValidatorPage.twitterPageurlsValidation(urls);
		test.info("Test URL: " + urls);
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(path).build());
	}

	@Test(dependsOnMethods = {"twitterInspectPost"})
	public void logLastLine() {
		Log.info("twitter scrappers validation has been completed..!");
	}
}
