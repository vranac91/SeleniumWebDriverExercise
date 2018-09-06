package Chapter1;

import Chapter1.config.DriverFactory;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DriverBase {

    // creating a list variable that will contain all driver instances
    private static List<DriverFactory> webDriverThreadPool = Collections.synchronizedList(new ArrayList<>());

    // creating a local variable for driver threads
    private static ThreadLocal<DriverFactory> driverThread;

    // instantiate new WebDriver defined in DriverFactory
    @BeforeSuite(alwaysRun = true)
    public static void instantiateDriverObject() {
        driverThread = new ThreadLocal<DriverFactory>() {
            @Override
            protected DriverFactory initialValue() {
                DriverFactory webDriverThread = new DriverFactory();
                webDriverThreadPool.add(webDriverThread);
                return webDriverThread;
            }
        };
    }

    // create or return instance of WebDriver; method defined in DriverFactory class
    public static RemoteWebDriver getDriver() {
        return driverThread.get().getDriver();
    }

    // clear instance cookies to be able to reuse it
    @AfterMethod(alwaysRun = true)
    public static void clearCookies() {
        try {
            getDriver().manage().deleteAllCookies();
        } catch (Exception ex) {
            System.err.println("Unable to delete cookies: " + ex);
        }
    }

    // close all instances of the driver contained in webDriverThreadPool list
    @AfterSuite(alwaysRun = true)
    public static void closeDriverObjects() {
        for (DriverFactory webDriverThread : webDriverThreadPool) {
            webDriverThread.quitDriver();
        }
    }
}