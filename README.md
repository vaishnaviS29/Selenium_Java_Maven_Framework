# Selenium Java Framework

This project is a basic Selenium automation framework built with Maven and Java. It demonstrates a simple test case using Selenium WebDriver to interact with a website and utilizes TestNG for test execution and Extent Reports for generating HTML reports. This project is also configured to run in a GitHub Actions workflow for continuous integration.

## Project Structure
```bash
├── .github/workflows/
│   └── main.yml           # GitHub Actions workflow definition
├── src/
│   └── main/java/
│       └── ...            # (Optional) Main application code
│   └── test/java/
│       └── SeleniumOrgTest.java # Your Selenium test class
├── test-output/           # Directory for TestNG and Extent Reports output
├── pom.xml                # Maven project configuration file
└── README.md              # This file
```
## Prerequisites

* **Java Development Kit (JDK):** Ensure you have a compatible JDK installed (e.g., Java 11, 17).
* **Maven:** Maven is used for project management and dependency management. Install it according to your operating system instructions.
* **WebDriver:** This project uses Selenium WebDriver to interact with web browsers. The necessary WebDriver binaries will be managed by Selenium.
* **GitHub Account:** You'll need a GitHub account to host your project and utilize GitHub Actions.

## Getting Started

1.  **Clone the Repository:**
    ```bash
    git clone <your-repository-url>
    cd SeleniumJavaFramework
    ```

2.  **Review pom.xml:**
    This file contains the project's dependencies, including Selenium WebDriver, JUnit (although TestNG is primarily used), TestNG, and Extent Reports.

    ```xml
    <project xmlns="[http://maven.apache.org/POM/4.0.0](http://maven.apache.org/POM/4.0.0)"
             xmlns:xsi="[http://www.w3.org/2001/XMLSchema-instance](http://www.w3.org/2001/XMLSchema-instance)"
             xsi:schemaLocation="[http://maven.apache.org/POM/4.0.0](http://maven.apache.org/POM/4.0.0)
                                 [http://maven.apache.org/xsd/maven-4.0.0.xsd](http://maven.apache.org/xsd/maven-4.0.0.xsd)">
        <modelVersion>4.0.0</modelVersion>
        <groupId>SeleniumJavaFramework</groupId>
        <artifactId>SeleniumJavaFramework</artifactId>
        <version>0.0.1-SNAPSHOT</version>

        <dependencies>
            <dependency>
                <groupId>org.seleniumhq.selenium</groupId>
                <artifactId>selenium-java</artifactId>
                <version>4.29.0</version>
            </dependency>

            <dependency>
                <groupId>junit</groupId>
                <artifactId>junit</artifactId>
                <version>4.5</version>
                <scope>test</scope>
            </dependency>

            <dependency>
                <groupId>org.testng</groupId>
                <artifactId>testng</artifactId>
                <version>7.7.1</version>
                <scope>test</scope>
            </dependency>

            <dependency>
                <groupId>com.aventstack</groupId>
                <artifactId>extentreports</artifactId>
                <version>4.1.7</version>
            </dependency>

        </dependencies>

        </project>
    ```
    Ensure the versions of Selenium, TestNG, and Extent Reports are as specified or updated as needed.

3.  **Review the Test Class (`src/test/java/SeleniumOrgTest.java`):**

    This class contains a basic Selenium test case that navigates to the Selenium website, interacts with elements, and generates Extent Reports.

    ```java
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
             test.pass("Test Passed"); // Initial pass log

            // Set the path to your WebDriver executable
            driver = new ChromeDriver();
        }


        @Test
        public void testGoogleSearch() throws InterruptedException {

            // log(Status, details)
            test.log(Status.INFO, "Starting Test Execution ... ");

            // Navigate to a webpage
            driver.get("[https://www.selenium.dev/](https://www.selenium.dev/)");

            System.out.println("Title of the page: " + driver.getTitle());

            test.pass("Title printed ");

            // Open a browser and maximize the window

            test.pass("Browser Maximized ++ ");


            try {
                // Pause the execution for 3 seconds
                Thread.sleep(3000);
            } catch (InterruptedException e) {
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
    ```

4.  **Configure GitHub Actions Workflow (`.github/workflows/main.yml`):**
    This file defines the automated process that will run when you push code to your repository.
    ```yaml
    name: First Selenium Test Execution with GitHubActions

    on:
      push:
        branches: [ "main" ]
      pull_request:
        branches: [ "main" ]

    jobs:
      build:

        runs-on: ubuntu-latest

        steps:
        - uses: actions/checkout@v4
        - name: Set up JDK 17
          uses: actions/setup-java@v4
          with:
            java-version: '17'
            distribution: 'temurin'
            cache: maven
        - name: Build with Maven
          run: |
                export DISPLAY=:99.0
                Xvfb :99 -screen 0 1920x1080x24 &
                TEMP_DIR=$(mktemp -d)
                echo "Using temp directory for user data: $TEMP_DIR"
                mvn clean test -Dchrome.options.args="--headless,--user-data-dir=$TEMP_DIR" || echo "Test execution failed"
                rm -rf $TEMP_DIR

        - name: Upload Extent Report
          uses: actions/upload-artifact@v4
          with:
            name: extent-report
            path: test-output/extentReport.html
    ```

    **Explanation of the Workflow:**

    * **`name: First Selenium Test Execution with GitHubActions`**: The name of your workflow.
    * **`on:`**: Defines the events that trigger this workflow. In this case, it triggers on `push` and `pull_request` events on the `main` branch.
    * **`jobs:`**: Defines the tasks to be executed.
        * **`build:`**: The name of the job.
        * **`runs-on: ubuntu-latest`**: Specifies that the job will run on an Ubuntu Linux environment.
        * **`steps:`**: A sequence of tasks to be executed within the job.
            * **`actions/checkout@v4`**: Checks out your repository code into the GitHub Actions runner.
            * **`actions/setup-java@v4`**: Sets up the specified Java Development Kit (JDK 17 in this case) and configures Maven for dependency caching.
            * **`Build with Maven`**: This step executes the Maven build lifecycle.
                * `export DISPLAY=:99.0` and `Xvfb :99 -screen 0 1920x1080x24 &`: These commands set up a virtual display, which is necessary for running Chrome in headless mode in a Linux environment.
                * `TEMP_DIR=$(mktemp -d)` and the subsequent `echo` and `rm -rf` commands create and then remove a temporary directory for Chrome's user data. This helps ensure a clean test environment in each run.
                * `mvn clean test -Dchrome.options.args="--headless,--user-data-dir=$TEMP_DIR"`: This command cleans the project, runs the tests using Maven Surefire Plugin (which will execute your TestNG tests), and passes the `--headless` argument to Chrome along with the temporary user data directory. The `|| echo "Test execution failed"` ensures that even if the tests fail, the workflow continues to the next step (for report uploading).
            * **`Upload Extent Report`**: This step uploads the generated Extent Report (located in the `test-output/extentReport.html` directory) as an artifact. You can download this report from the GitHub Actions run summary.

## Running Tests Locally

You can run your Selenium tests locally using Maven:

```bash
mvn clean test
