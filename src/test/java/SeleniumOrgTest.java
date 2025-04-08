
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class SeleniumOrgTest {
	
	WebDriver driver;
	 ExtentTest test;
	 ExtentReports extent;
	
	 @BeforeClass
	    public void setUp() {
	       
		// Initialize the ExtentSparkReporter
		 ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/extentReport.html");

	        // Initialize ExtentReports and attach the SparkReporter
	        extent = new ExtentReports();
	        extent.attachReporter(sparkReporter);

	        // Create a test in the report
	         test = extent.createTest("First Selenium Test");
	        test.pass("Test Passed");

	        // Set the path to your WebDriver executable
	        driver = new ChromeDriver();
	    }
	    


    	
       
		
    @Test
    public void testGoogleSearch() throws InterruptedException {
       
    	 // log(Status, details)
        test.log(Status.INFO, "Starting Test Execution ... ");
    	
    	// Navigate to a webpage
        driver.get("https://www.selenium.dev/");
        
        System.out.println("Title of the page: " + driver.getTitle());
        
        test.pass("Title printed ");
        
        // Open a browser and maximize the window

        test.pass("Browser Maximized ++ ");

        
 
        try {
        	// Pause the execution for 5 seconds
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // Wait for the page to load
        Thread.sleep(2000);

        
        WebElement readMore = driver.findElement(By.xpath("//a[contains(text(),'Read more')]"));
       
     // Scroll to the element using JavascriptExecutor
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", readMore);

        // Click on the element using JavascriptExecutor
        js.executeScript("arguments[0].click();", readMore);
        
        test.pass("Clicked Read More  ");
        
        Thread.sleep(2000);
        // Navigate back to the previous page
        driver.navigate().back();
        
        test.pass("Navigated Back ");
        WebElement documentation = driver.findElement(By.xpath(" //a[contains(@href,'documentation')]"));
        js.executeScript("arguments[0].click();", documentation);
     
        // Wait to observe the navigation
        Thread.sleep(3000);

        test.pass("Clicked Documentation link and test completed  ");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
        // Save the report
        extent.flush();
        
    }
       
     
    static ChromeOptions getChromeOptions(DesiredCapabilities cap) {
    	
    	ChromeOptions co = new ChromeOptions();
        co.addArguments("--headless"); 
        co.addArguments("--disable-gpu");
        co.addArguments("--no-sandbox");
        cap.setCapability(ChromeOptions.CAPABILITY,co);
        co.merge(cap);
        return co;
        
    	
    }
	

}
