/* Reference Documentation - Appioum with SmartUI SDK */
/* https://www.lambdatest.com/support/docs/smartui-app-sdk/ */

/* Steps */
/* 1. export PROJECT_TOKEN=407654#f926c172-d1e2-4847-8c4e-4f802ecd206e#Android-SmartUI-SDK-Demo */
/* 2. npm install @lambdatest/smartui-cli --force */
/* Referhttps://github.com/LambdaTest/smartui-java-testng-sample/blob/main/src/test/
    java/com/lambdatest/sdk/SmartUISDKCloud.java and make changes */

package com.lambdaTestApp.android;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import okhttp3.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.lambdatest.SmartUIAppSnapshot;

public class Visual_Regression_AndroidApp_sdk
{

    String userName = System.getenv("LT_USERNAME") == null ? "Your LT Username" : 
                    System.getenv("LT_USERNAME");
    String accessKey = System.getenv("LT_ACCESS_KEY") == null ? "Your LT AccessKey" :
                    System.getenv("LT_ACCESS_KEY");

    String sProjectName = "lambdatest-realdevices-demo";
    String sBuildName = "Android - [SDK] Visual Regression with Java TestNG";

    String credential = Credentials.basic(userName, accessKey);
    public String gridURL = "@mobile-hub.lambdatest.com/wd/hub";
    String sSpeedTestScreenshot = "Android-SpeedTestScreenshot";
    String sGeolocationScreenshot = "Android-GeolocationScreenshot";

    AppiumDriver driver;

    /* Reference - https://github.com/LambdaTest/smartui-appium-java-sample/blob/
    705347e2fc60cf219100c143736313e21b5c0792/src/test/java/SmartuiAppIos.java#L36
    */
    SmartUIAppSnapshot smartUIAppSnapshot = new SmartUIAppSnapshot();

    @BeforeSuite
    public void beforeSuite() throws IOException, InterruptedException 
    {
        if (isAppPresent())
        {
            System.out.println("App already present, skipping app upload...");
        }
        else
        {
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
    public void AndroidApp1(String device, @Optional String version, String platform) throws Exception
    {
        /*
        Map<String, String> ssConfig = new HashMap<>();
        ssConfig.put("projectToken", "123456#1234abcd-1234-1234-1234-123456789101");
        ssConfig.put("deviceName", device);
        ssConfig.put("platform", platform);
        ssConfig.put("platformVersion", version);
        ssConfig.put("app", "proverbial-android");
        ssConfig.put("autoGrantPermissions", "true");
        ssConfig.put("autoAcceptAlerts", "true");
        ssConfig.put("deviceOrientation", "PORTRAIT");
        ssConfig.put("console", "true");
        ssConfig.put("network", "true");
        ssConfig.put("visual", "true");
        */
        DesiredCapabilities capabilities = new DesiredCapabilities();
        Map<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("build","Visual Regression SDK: Java TestNG Android");
        ltOptions.put("name",platform+" "+device+" "+version);
        ltOptions.put("deviceName", device);
        ltOptions.put("platformVersion",version);
        ltOptions.put("platformName", platform);
        ltOptions.put("isRealMobile", true);
        ltOptions.put("autoGrantPermissions", true);
        ltOptions.put("autoAcceptAlerts", true);
        /* Enter the App URL */    
        ltOptions.put("app", "proverbial-android");
        /* capabilities.setCapability("app", "lt://APP101606121746688544351330"); */
        ltOptions.put("deviceOrientation", "PORTRAIT");
        ltOptions.put("console", true);
        ltOptions.put("network", true);
        ltOptions.put("visual", true);
        ltOptions.put("devicelog", true);
        /* Enable App Profiling with caution - not needed in all the cases */
        ltOptions.put("appProfiling", true);
        // ltOptions.put("projectToken", 
        //               "2564217#f3286639-ed58-452f-b80b-71297198c3b3#SmartUI-SDK-Demo");

        capabilities.setCapability("lt:options", ltOptions);

        String hub = "https://" + userName + ":" + accessKey + gridURL;
        driver = new AppiumDriver(new URL(hub), capabilities);

        /* SmartUI.start(ssConfig); */

        Map<String, String> screenshotConfig = new HashMap<>();
        screenshotConfig.put("deviceName",device);
        screenshotConfig.put("platform",platform);
        screenshotConfig.put("projectToken", 
                      "2564217#f3286639-ed58-452f-b80b-71297198c3b3");
        try 
        {            
            smartUIAppSnapshot.start();

            MobileElement color = (MobileElement) driver.findElementById(
                            "com.lambdatest.proverbial:id/color");
            smartUIAppSnapshot.smartuiAppSnapshot(driver, "proverbial-android-scr-1", screenshotConfig);
            System.out.println("SmartUI SDK 1: Android - SmartUI:1 screenshot captured successfully.");
            //Changes color to pink
            color.click();
            Thread.sleep(1000);
            //Back to orginal color
            color.click();

            MobileElement text = (MobileElement) driver.findElementById(
                "com.lambdatest.proverbial:id/Text");

            //Changes the text to "Proverbial"
            text.click();
            smartUIAppSnapshot.smartuiAppSnapshot(driver, "proverbial-android-scr-2", screenshotConfig);
            System.out.println("SmartUI SDK 1: Android - SmartUI:2 screenshot captured successfully.");

            //toast will be visible
            MobileElement toast = (MobileElement) driver.findElementById(
                "com.lambdatest.proverbial:id/toast");
            toast.click();

            /* Notification will be visible */
            MobileElement notification = (MobileElement) driver.findElementById(
                "com.lambdatest.proverbial:id/notification");
            notification.click();
            Thread.sleep(2000);

            /* Opens the geolocation page */
            MobileElement geo = (MobileElement) driver.findElementById(
                "com.lambdatest.proverbial:id/geoLocation");
            geo.click();
            Thread.sleep(5000);

            smartUIAppSnapshot.smartuiAppSnapshot(driver, "proverbial-android-scr-3", screenshotConfig);
            System.out.println("SmartUI SDK 1: Android - SmartUI:3 screenshot captured successfully.");

            /* Takes back to home page */
            MobileElement home = (MobileElement) driver.findElementByAccessibilityId("Home");
            home.click();

            /* Takes to speed test page */
            MobileElement speedtest = (MobileElement) driver.findElementById(
                "com.lambdatest.proverbial:id/speedTest");
            speedtest.click();
            Thread.sleep(5000);

            MobileElement Home = (MobileElement) driver.findElementByAccessibilityId("Home");
            Home.click();

            /* Opens the browser */
            MobileElement browser = (MobileElement) driver.findElementByAccessibilityId("Browser");
            browser.click();

            MobileElement url = (MobileElement) driver.findElementById(
                            "com.lambdatest.proverbial:id/url");
            url.sendKeys("https://www.lambdatest.com");
            smartUIAppSnapshot.smartuiAppSnapshot(driver, "proverbial-android-scr-4", screenshotConfig);
            System.out.println("SmartUI SDK 1: Android - SmartUI:4 screenshot captured successfully.");


            MobileElement find = (MobileElement) driver.findElementById(
                            "com.lambdatest.proverbial:id/find");
            find.click();

        } 
        catch (Exception e)
        {
            e.printStackTrace();
            try
            {
                smartUIAppSnapshot.stop();
                driver.quit();
            }
            catch(Exception e1)
            {
                e.printStackTrace();
            }
        }
        finally
        {
            smartUIAppSnapshot.stop();
            driver.quit();
        }
    }

    @Test(priority = 2)
    @Parameters(value = {"device", "version", "platform"})
    public void AndroidApp2(String device, @Optional String version, String platform) throws Exception
    {
        /*
        Map<String, String> ssConfig = new HashMap<>();
        ssConfig.put("projectToken", "123456#1234abcd-1234-1234-1234-123456789101");
        ssConfig.put("deviceName", device);
        ssConfig.put("platform", platform);
        ssConfig.put("platformVersion", version);
        ssConfig.put("app", "proverbial-android");
        ssConfig.put("autoGrantPermissions", "true");
        ssConfig.put("autoAcceptAlerts", "true");
        ssConfig.put("deviceOrientation", "PORTRAIT");
        ssConfig.put("console", "true");
        ssConfig.put("network", "true");
        ssConfig.put("visual", "true");
        */
        DesiredCapabilities capabilities = new DesiredCapabilities();
        Map<String, Object> ltOptions = new HashMap<>();
        ltOptions.put("build","Visual Regression SDK: Java TestNG Android");
        ltOptions.put("name",platform+" "+device+" "+version);
        ltOptions.put("deviceName", device);
        ltOptions.put("platformVersion",version);
        ltOptions.put("platformName", platform);
        ltOptions.put("isRealMobile", true);
        ltOptions.put("autoGrantPermissions", true);
        ltOptions.put("autoAcceptAlerts", true);
        /* Enter the App URL */    
        ltOptions.put("app", "proverbial-android");
        /* capabilities.setCapability("app", "lt://APP101606121746688544351330"); */
        ltOptions.put("deviceOrientation", "PORTRAIT");
        ltOptions.put("console", true);
        ltOptions.put("network", true);
        ltOptions.put("visual", true);
        ltOptions.put("devicelog", true);
        /* Enable App Profiling with caution - not needed in all the cases */
        ltOptions.put("appProfiling", true);
        ltOptions.put("projectToken", 
                      "407654#f926c172-d1e2-4847-8c4e-4f802ecd206e#Android-SmartUI-SDK-Demo");

        capabilities.setCapability("lt:options", ltOptions);

        String hub = "https://" + userName + ":" + accessKey + gridURL;
        driver = new AppiumDriver(new URL(hub), capabilities);

        /* SmartUI.start(ssConfig); */

        Map<String, String> screenshotConfig = new HashMap<>();
        screenshotConfig.put("deviceName",device);
        screenshotConfig.put("platform",platform);
        screenshotConfig.put("projectToken", 
                      "123456#1234abcd-1234-1234-1234-123456789101");
        try 
        {            
            smartUIAppSnapshot.start();

            MobileElement color = (MobileElement) driver.findElementById(
                            "com.lambdatest.proverbial:id/color");
            smartUIAppSnapshot.smartuiAppSnapshot(driver, "proverbial-android-scr-1", screenshotConfig);
            System.out.println("SmartUI SDK 2: Android - SmartUI:1 screenshot captured successfully.");
            //Changes color to pink
            color.click();
            Thread.sleep(1000);
            //Back to orginal color
            color.click();

            MobileElement text = (MobileElement) driver.findElementById(
                "com.lambdatest.proverbial:id/Text");

            //Changes the text to "Proverbial"
            text.click();
            smartUIAppSnapshot.smartuiAppSnapshot(driver, "proverbial-android-scr-2", screenshotConfig);
            System.out.println("SmartUI SDK 2: Android - SmartUI:2 screenshot captured successfully.");

            //toast will be visible
            /* Deliberate changes for visual comparison */
            /* 
            MobileElement toast = (MobileElement) driver.findElementById(
                "com.lambdatest.proverbial:id/toast");
            toast.click();
            */

            /* Notification will be visible */
            MobileElement notification = (MobileElement) driver.findElementById(
                "com.lambdatest.proverbial:id/notification");
            notification.click();
            Thread.sleep(2000);

            /* Deliberate changes for visual comparison */
            /* 
            MobileElement geo = (MobileElement) driver.findElementById(
                "com.lambdatest.proverbial:id/geoLocation");
            geo.click();
            Thread.sleep(5000);
            */

            smartUIAppSnapshot.smartuiAppSnapshot(driver, "proverbial-android-scr-3", screenshotConfig);
            System.out.println("SmartUI SDK 3: Android - SmartUI:3 screenshot captured successfully.");

            /* Takes back to home page */
            MobileElement home = (MobileElement) driver.findElementByAccessibilityId("Home");
            home.click();

            /* Takes to speed test page */
            MobileElement speedtest = (MobileElement) driver.findElementById(
                "com.lambdatest.proverbial:id/speedTest");
            speedtest.click();
            Thread.sleep(5000);

            MobileElement Home = (MobileElement) driver.findElementByAccessibilityId("Home");
            Home.click();

            /* Opens the browser */
            MobileElement browser = (MobileElement) driver.findElementByAccessibilityId("Browser");
            browser.click();

            MobileElement url = (MobileElement) driver.findElementById(
                            "com.lambdatest.proverbial:id/url");
            url.sendKeys("https://www.duckduckgo.com");
            smartUIAppSnapshot.smartuiAppSnapshot(driver, "proverbial-android-scr-4", screenshotConfig);
            System.out.println("SmartUI SDK 4: Android - SmartUI:4 screenshot captured successfully.");


            MobileElement find = (MobileElement) driver.findElementById(
                            "com.lambdatest.proverbial:id/find");
            find.click();

        } 
        catch (Exception e)
        {
            e.printStackTrace();
            try
            {
                smartUIAppSnapshot.stop();
                driver.quit();
            }
            catch(Exception e1)
            {
                e.printStackTrace();
            }
        }
        finally
        {
            smartUIAppSnapshot.stop();
            driver.quit();
        }
    }

    private  void uploadApp() throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("appFile","apps/proverbial_android.apk",
                        RequestBody.create(MediaType.parse("application/octet-stream"),
                                new File("apps/proverbial_android.apk")))
                .addFormDataPart("custom_id","proverbial-android")
                .addFormDataPart("name","proverbial-android")
                .build();
        Request request = new Request.Builder()
                .url("https://manual-api.lambdatest.com/app/upload/realDevice")
                .method("POST", body)
                .addHeader("Authorization", credential)
                .build();
        Response response = client.newCall(request).execute();
        //System.out.println(response.body().string());
    }

    private boolean isAppPresent() throws IOException
    {
        String jsonString = getResponseAsJson("android");
        List<String> listOfApps = getAppIdsFromJson(jsonString);
        return listOfApps.contains("proverbial-android");
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
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonData);
            JSONArray dataArray = (JSONArray) jsonObject.get("data");
            for (Object data : dataArray) {
                JSONObject dataObject = (JSONObject) data;
                String name = (String) dataObject.get("name");
               namesList.add(name);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return namesList;
    }
}