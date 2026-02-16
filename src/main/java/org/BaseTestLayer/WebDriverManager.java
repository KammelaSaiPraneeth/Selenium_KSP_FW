package org.BaseTestLayer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.sql.DriverManager;
import java.time.Duration;

    public final class WebDriverManager
    {
        private static volatile WebDriverManager instance; // Singleton instance
        public static ThreadLocal<WebDriver> tdriver= new ThreadLocal<>();

        private WebDriverManager()
        {
        }
        public static WebDriverManager getInstance(String browser)
        {
            if (instance == null) {
                synchronized (DriverManager.class)
                {
                    if (instance == null)
                    {
                        instance = new WebDriverManager();
                    }
                }
            }
            if(tdriver.get()==null)
            {
                instance.initDriver(browser);
            }
            return instance;
        }

        public void initDriver(String browser)
        {
            switch (browser)
            {
                case "chrome": {
                    ChromeOptions options = new ChromeOptions();
                    // options.addArguments("--start-maximized");
                    tdriver.set(new ChromeDriver(options));
                    break;
                }
                case "firefox": {
                    FirefoxOptions options = new FirefoxOptions();
                    tdriver.set(new FirefoxDriver(options));
                    break;
                }
                case "edge": {
                    EdgeOptions options = new EdgeOptions();
                    tdriver.set(new EdgeDriver(options));
                    break;
                }
                default:
                    throw new IllegalArgumentException(
                            "Unsupported browser: " + browser + " (use chrome|firefox|edge)");
            }
        }

            public WebDriver getDriver()
        {
                if (tdriver == null) {
                    throw new IllegalStateException("Driver not initialized. Call initDriver(browser) first.");
                }
                return tdriver.get();
            }

            public void quitDriver ()
            {
                if (tdriver.get() != null) {
                    tdriver.get().quit();
                    tdriver.remove();
                }
            }
        }




