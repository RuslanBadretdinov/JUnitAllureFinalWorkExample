package ru.rencredit.framework.managers;

import org.apache.commons.exec.OS;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import ru.rencredit.framework.utils.PropConst;

import java.net.MalformedURLException;
import java.net.URI;

public class DriverManager {

    private WebDriver driver;
    private static DriverManager INSTANCE = null;
    private final TestPropManager props = TestPropManager.getTestPropManager();


    /**
     * Конструктор специально был объявлен как private (singleton паттерн)
     *
     * @see DriverManager#getDriverManager()
     */
    private DriverManager() {
    }

    /**
     * Метод ленивой инициализации DriverManager
     *
     * @return DriverManager - возвращает DriverManager
     */
    public static DriverManager getDriverManager() {
        if (INSTANCE == null) {
            INSTANCE = new DriverManager();
        }
        return INSTANCE;
    }

    /**
     * Метод ленивой инициализации веб драйвера
     *
     * @return WebDriver - возвращает веб драйвер
     */
    public WebDriver getDriver() {
        if (driver == null) {
            initDriver();
        }
        return driver;
    }


    /**
     * Метод для закрытия сессии драйвера и браузера
     *
     * @see WebDriver#quit()
     */
    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }


    /**
     * Метод инициализирующий веб драйвер
     */
    private void initDriver() {
        if (OS.isFamilyWindows()) {
            initDriverWindowsOsFamily();
        } else if (OS.isFamilyMac()) {
            initDriverMacOsFamily();
        } else if (OS.isFamilyUnix()) {
            initDriverUnixOsFamily();
        }
    }

    /**
     * Метод инициализирующий веб драйвер под ОС семейства Windows
     */
    private void initDriverWindowsOsFamily() {
        initDriverAnyOsFamily(props.getProperty(PropConst.PATH_GECKO_DRIVER_WINDOWS), props.getProperty(PropConst.PATH_CHROME_DRIVER_WINDOWS));
    }


    /**
     * Метод инициализирующий веб драйвер под ОС семейства Mac
     */
    private void initDriverMacOsFamily() {
        initDriverAnyOsFamily(props.getProperty(PropConst.PATH_GECKO_DRIVER_MAC), props.getProperty(PropConst.PATH_CHROME_DRIVER_MAC));
    }

    /**
     * Метод инициализирующий веб драйвер под ОС семейства Unix
     */
    private void initDriverUnixOsFamily() {
        initDriverAnyOsFamily(props.getProperty(PropConst.PATH_GECKO_DRIVER_UNIX), props.getProperty(PropConst.PATH_CHROME_DRIVER_UNIX));
    }


    /**
     * Метод инициализирующий веб драйвер под любую ОС
     */
    private void initDriverAnyOsFamily(String gecko, String chrome) {
        switch (props.getProperty(PropConst.TYPE_BROWSER)) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver", gecko);
                driver = new FirefoxDriver();
                break;
            case "chrome":
                System.setProperty("webdriver.chrome.driver", chrome);
                ChromeOptions ops = new ChromeOptions();
                ops.addArguments("--disable-notifications");
                driver = new ChromeDriver(ops);
                break;
            case "remote":
                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setBrowserName("chrome");
                capabilities.setVersion("80.0");
                capabilities.setCapability("enableVNC", true);
                capabilities.setCapability("enableVideo", false);


                try {
                    /*RemoteWebDriver */ driver = new RemoteWebDriver(
                            URI.create("http://164.92.227.174:4444/wd/hub").toURL(),
                            capabilities
                    );
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            default:
                Assertions.fail("Типа браузера '" + props.getProperty(props.getProperty(PropConst.TYPE_BROWSER)) + "' не существует во фреймворке");
        }
    }
}
