/* Reference Documentation - Appium with hooks */
/* https://www.lambdatest.com/support/docs/appium-visual-regression/ */

package com.lambdaTestApp.iOS;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import okhttp3.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Visual_Regression_IOSApp_hooks
{
    String userName = System.getenv("LT_USERNAME") == null ?
            "username" : System.getenv("LT_USERNAME"); //Add username here
    String accessKey = System.getenv("LT_ACCESS_KEY") == null ?
            "accessKey" : System.getenv("LT_ACCESS_KEY"); //Add accessKey here

    public String gridURL = "@mobile-hub.lambdatest.com/wd/hub";

    String sProjectName = "lambdatest-realdevices-demo";
    String sBuildName = "iOS - Visual Regression with Java TestNG";

    String sSpeedTestScreenshot = "iOS-SpeedTestScreenshot";
    String sGeolocationScreenshot = "iOS-GeolocationScreenshot";

    private final String credential = Credentials.basic(userName, accessKey);

    AppiumDriver driver;

    @BeforeSuite
    public void beforeSuite() throws IOException, InterruptedException {
        if (isAppPresent()){
            System.out.println("App already present, skipping app upload...");
        }else {
            System.out.println("App not present, uploading app...");
            uploadApp();
            System.out.println("App upload successful");
            System.out.println("Waiting for tests to be initiated. It will take approx. 1 min for app to be configured to run with network logs");
            Thread.sleep(60000);
            System.out.println("Starting the test execution...");
        }
    }

    @Test(priority = 1)
    @Parameters(value = {"device", "version", "platform"})
    public void iOSApp1(String device, String version, String platform)
    {
        try
        {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("build","Visual Regression: Java TestNG iOS");
            capabilities.setCapability("name",platform+" "+device+" "+version);
            capabilities.setCapability("deviceName", device);
            capabilities.setCapability("platformVersion",version);
            capabilities.setCapability("platformName", platform);
            capabilities.setCapability("isRealMobile", true);
            /* Enter your app url */
            capabilities.setCapability("app", "proverbial-ios");
            capabilities.setCapability("deviceOrientation", "PORTRAIT");
            capabilities.setCapability("console", true);
            capabilities.setCapability("network", true);
            capabilities.setCapability("visual", true);
            capabilities.setCapability("devicelog", true);
            capabilities.setCapability("appProfiling", true);
            capabilities.setCapability("autoGrantPermissions", true);
            capabilities.setCapability("autoAcceptAlerts", true);

            /************* SmartUI Related Capabilities *****************/
            /* Mandatory - Replace the name of project with the new project name */
            capabilities.setCapability("smartUI.project", sProjectName);
            /* Optional -  Replace the name of Build with the new Build name */
            capabilities.setCapability("smartUI.build", sBuildName);
            /* Enable if you want to update to a new baseline build (Optional) */
            capabilities.setCapability("smartUI.baseline", true);
            /* Optional (By default true) */ 
            capabilities.setCapability("smartUI.cropStatusBar", true);
            /* Optional (By default false) */
            capabilities.setCapability("smartUI.cropFooter", true);

            String hub = "https://" + userName + ":" + accessKey + gridURL;
            driver = new AppiumDriver(new URL(hub), capabilities);

            WebDriverWait Wait = new WebDriverWait(driver,30);

            /* Changes the color of the text */
            Wait.until(ExpectedConditions.presenceOfElementLocated(
                MobileBy.AccessibilityId("color"))).click();
            Thread.sleep(1000);

            /* Changes the text to "Proverbial" */
            Wait.until(ExpectedConditions.presenceOfElementLocated(
                MobileBy.AccessibilityId("Text"))).click();
            Thread.sleep(1000);

            /* Toast will be visible */
            Wait.until(ExpectedConditions.presenceOfElementLocated(
                MobileBy.AccessibilityId("toast"))).click();
            Thread.sleep(1000);

            /* Notification will be visible */
            Wait.until(ExpectedConditions.presenceOfElementLocated(
                MobileBy.AccessibilityId("notification"))).click();
            Thread.sleep(4000);

            /* Opens the geolocation page */
            Wait.until(ExpectedConditions.presenceOfElementLocated(
                MobileBy.AccessibilityId("geoLocation"))).click();
            Thread.sleep(4000);

            /* Invoke the function to take a SmartUI screenshot */
            takeSmartUIScreenshot(driver, sGeolocationScreenshot, true, 2);
            System.out.println("Geolocation 1: SmartUI screenshot captured successfully.");

            /* Takes back */
            driver.navigate().back();

            /* Takes to speedtest page */
            Wait.until(ExpectedConditions.presenceOfElementLocated(
                MobileBy.AccessibilityId("speedTest"))).click();
            Thread.sleep(4000);

            /* Invoke the function to take a SmartUI screenshot */
            takeSmartUIScreenshot(driver, sSpeedTestScreenshot, true, 4);
            System.out.println("SpeedTest 1: SmartUI screenshot captured successfully.");

            driver.navigate().back();

            /* Opens the browser */
            Wait.until(ExpectedConditions.presenceOfElementLocated(
                MobileBy.AccessibilityId("Browser"))).click();
            Thread.sleep(1000);

            MobileElement url = (MobileElement) driver.findElementByAccessibilityId("url");
            url.click();
            url.sendKeys("https://www.lambdatest.com");

            Wait.until(ExpectedConditions.presenceOfElementLocated(
                MobileBy.AccessibilityId("find"))).click();
            Thread.sleep(1000);

            driver.quit();

        }
        catch (Exception e) 
        {
            e.printStackTrace();
            try
            {
                driver.quit();
            }
            catch(Exception e1)
            {
                e.printStackTrace();
            }
        }
    }

    @Test(priority = 2)
    @Parameters(value = {"device", "version", "platform"})
    public void iOSApp2(String device, String version, String platform)
    {
        try
        {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("build","Visual Regression: Java TestNG iOS");
            capabilities.setCapability("name",platform+" "+device+" "+version);
            capabilities.setCapability("deviceName", device);
            capabilities.setCapability("platformVersion",version);
            capabilities.setCapability("platformName", platform);
            capabilities.setCapability("isRealMobile", true);
            /* Enter your app url */
            capabilities.setCapability("app", "proverbial-ios");
            capabilities.setCapability("deviceOrientation", "PORTRAIT");
            capabilities.setCapability("console", true);
            capabilities.setCapability("network", true);
            capabilities.setCapability("visual", true);
            capabilities.setCapability("devicelog", true);
            capabilities.setCapability("appProfiling", true);
            capabilities.setCapability("autoGrantPermissions", true);
            capabilities.setCapability("autoAcceptAlerts", true);

            /************* SmartUI Related Capabilities *****************/
            /* Mandatory - Replace the name of project with the new project name */
            capabilities.setCapability("smartUI.project", sProjectName);
            /* Optional -  Replace the name of Build with the new Build name */
            capabilities.setCapability("smartUI.build", sBuildName);
            /* Enable if you want to update to a new baseline build (Optional) */
            capabilities.setCapability("smartUI.baseline", false);
            /* Optional (By default true) */ 
            capabilities.setCapability("smartUI.cropStatusBar", true);
            /* Optional (By default false) */
            capabilities.setCapability("smartUI.cropFooter", true);

            String hub = "https://" + userName + ":" + accessKey + gridURL;
            driver = new AppiumDriver(new URL(hub), capabilities);

            WebDriverWait Wait = new WebDriverWait(driver,30);

            /* Changes the color of the text */
            Wait.until(ExpectedConditions.presenceOfElementLocated(
                MobileBy.AccessibilityId("color"))).click();
            Thread.sleep(1000);

            /* Changes the text to "Proverbial" */
            Wait.until(ExpectedConditions.presenceOfElementLocated(
                MobileBy.AccessibilityId("Text"))).click();
            Thread.sleep(1000);

            /* Toast will be visible */
            Wait.until(ExpectedConditions.presenceOfElementLocated(
                MobileBy.AccessibilityId("toast"))).click();
            Thread.sleep(1000);

            /* Notification will be visible */
            Wait.until(ExpectedConditions.presenceOfElementLocated(
                MobileBy.AccessibilityId("notification"))).click();
            Thread.sleep(4000);

            /* Opens the geolocation page */
            /* Removed for generating image difference */
            /* Wait.until(ExpectedConditions.presenceOfElementLocated(
                MobileBy.AccessibilityId("geoLocation"))).click();
            Thread.sleep(4000);
            */

            /* Invoke the function to take a SmartUI screenshot */
            takeSmartUIScreenshot(driver, sGeolocationScreenshot, true, 2);
            System.out.println("Geolocation 2: SmartUI screenshot captured successfully.");

            /* Takes back */
            driver.navigate().back();

            /* Takes to speedtest page */
            /* Commenting for generating image difference */
            /*
            Wait.until(ExpectedConditions.presenceOfElementLocated(
                MobileBy.AccessibilityId("speedTest"))).click();
            Thread.sleep(4000);
            */

            /* Invoke the function to take a SmartUI screenshot */
            takeSmartUIScreenshot(driver, sSpeedTestScreenshot, true, 4);
            System.out.println("SpeedTest 2: SmartUI screenshot captured successfully.");

            driver.navigate().back();

            /* Opens the browser */
            Wait.until(ExpectedConditions.presenceOfElementLocated(
                MobileBy.AccessibilityId("Browser"))).click();
            Thread.sleep(1000);

            MobileElement url = (MobileElement) driver.findElementByAccessibilityId("url");
            url.click();
            url.sendKeys("https://www.lambdatest.com");

            Wait.until(ExpectedConditions.presenceOfElementLocated(
                MobileBy.AccessibilityId("find"))).click();
            Thread.sleep(1000);

            driver.quit();

        }
        catch (Exception e) 
        {
            e.printStackTrace();
            try
            {
                driver.quit();
            }
            catch(Exception e1)
            {
                e.printStackTrace();
            }
        }
    }

    private void uploadApp() throws IOException, InterruptedException
    {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("appFile", "apps/proverbial_ios.ipa",
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File("apps/proverbial_ios.ipa")))
                .addFormDataPart("custom_id","proverbial-ios")
                .addFormDataPart("name","proverbial-ios")
                .build();
        Request request = new Request.Builder()
                .url("https://manual-api.lambdatest.com/app/upload/realDevice")
                .method("POST", body)
                .addHeader("Authorization", credential)
                .build();
        Response response = client.newCall(request).execute();
        Thread.sleep(15000);
    }

    private boolean isAppPresent() throws IOException
    {
        String jsonString = getResponseAsJson("ios");
        List<String> listOfApps = getAppIdsFromJson(jsonString);
        return listOfApps.contains("proverbial-ios");
    }

    private String getResponseAsJson(String platform) throws IOException
    {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://manual-api.lambdatest.com/app/data?type="+platform+"&level=user")
                .method("GET",null)
                .addHeader("Authorization", credential)
                .build();

        Response response = client.newCall(request).execute();
        assert response.body() != null;
        return response.body().string();
    }

    private List<String> getAppIdsFromJson(String jsonData)
    {
        List<String> namesList = new ArrayList<String>();
        try
        {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonData);
            JSONArray dataArray = (JSONArray) jsonObject.get("data");
            for (Object data : dataArray)
            {
                JSONObject dataObject = (JSONObject) data;
                String name = (String) dataObject.get("name");
                namesList.add(name);
            }
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return namesList;
    }

    private static void takeSmartUIScreenshot(AppiumDriver driver, 
        String screenshotName,
        boolean fullPage,
        int pageCount) 
    {
        /* Please note that this webhook is only applicable to 
        native app screenshots and has known limitations. You can use an
        optimized value of page count (between 1 and 20) to get the best
        results of your full page screenshots, according to your use case.
        */
        if (pageCount < 1 || pageCount > 20)
        {
            throw new IllegalArgumentException("PageCount must be between 1 and 20.");
        }

        /* Prepare the SmartUI config */
        /* Documentation - https://www.lambdatest.com/support/docs/appium-visual-regression/
            #for-capturing-full-page-screenshot-in-native-apps- */
        Map<String, Object> config = new HashMap<>();
        config.put("screenshotName", screenshotName);
        config.put("fullPage", fullPage);
        config.put("pageCount", pageCount);

        /* Execute the SmartUI screenshot webhook */
        ((JavascriptExecutor) driver).executeScript("smartui.takeScreenshot", config);
    }
}