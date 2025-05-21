/* Inspiration - https://github.com/LambdaTest/smartui-java-testng-sample/blob/
main/src/test/java/com/lambdatest/sdk/SmartUISDKCloud.java
*/

/* Reference doc - https://www.lambdatest.com/support/docs/smartui-selenium-java-sdk/ */

package com.lambdatest.sdk;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.github.lambdatest.SmartUISnapshot;

public class SmartUISDKCloud
{

    private RemoteWebDriver driver;
    private String Status = "failed";
    private String githubURL = System.getenv("GITHUB_URL");

    @BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {
        String username = System.getenv("LT_USERNAME") == null ? "Your LT Username" : System.getenv("LT_USERNAME");
        String authkey = System.getenv("LT_ACCESS_KEY") == null ? "Your LT AccessKey" : System.getenv("LT_ACCESS_KEY");
        String hub = "@hub.lambdatest.com/wd/hub";

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platform", "Windows 11");
        caps.setCapability("browserName", "chrome");
        caps.setCapability("version", "latest");
        caps.setCapability("build", "Smart SDK: TestNG With Java");
        caps.setCapability("projectToken",
                      "550422#d21222ea-2c65-46e9-9e18-9bb79126659a#Web-SmartUI-SDK-Demo");
        caps.setCapability("name", m.getName() + " - " + this.getClass().getName());
        
        if (githubURL != null)
        {
            Map<String, String> github = new HashMap<String, String>();
            github.put("url",githubURL);
            caps.setCapability("github", github);
        }
        System.out.println(caps);
        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), caps);

    }

    @Test(priority=1)
    public void basicTest() throws Exception {
        System.out.println("Loading Url");
        driver.get("https://www.lambdatest.com/visual-regression-testing");
        Thread.sleep(1000);
        SmartUISnapshot.smartuiSnapshot(driver, "visual-regression-testing");
        Thread.sleep(5000);
        driver.get("https://www.lambdatest.com");
        Thread.sleep(1000);
        SmartUISnapshot.smartuiSnapshot(driver, "homepage");
        Thread.sleep(1000);
        System.out.println("Test Finished");
    }

    @AfterMethod
    public void tearDown() {
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }
}