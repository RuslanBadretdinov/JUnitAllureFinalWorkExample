package ru.rencredit.framework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.rencredit.framework.managers.DataManager;
import ru.rencredit.framework.managers.DriverManager;
import ru.rencredit.framework.managers.PageManager;
import ru.rencredit.framework.managers.TestPropManager;

import java.awt.*;

public class BasePage {
    protected final DataManager dataManager = DataManager.getDataManager();

    protected final DriverManager driverManager = DriverManager.getDriverManager();

    protected PageManager pageManager = PageManager.getPageManager();

    protected Actions action = new Actions(driverManager.getDriver());

    protected JavascriptExecutor js = (JavascriptExecutor) driverManager.getDriver();

    protected WebDriverWait wait = new WebDriverWait(driverManager.getDriver(), 10, 1000);

    protected Select select;

    private final TestPropManager props = TestPropManager.getTestPropManager();

    public BasePage() {
        PageFactory.initElements(driverManager.getDriver(), this);
    }

    protected WebElement scrollToElementJs(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        return element;
    }

    public WebElement scrollWithOffset(WebElement element, int x, int y) {
        String code = "window.scroll(" + (element.getLocation().x + x) + ","
                + (element.getLocation().y + y) + ");";
        ((JavascriptExecutor) driverManager.getDriver()).executeScript(code, element, x, y);
        return element;
    }

    public WebElement retardClickElement(WebElement element, int widthDivider, int highDivider) {
        waitUtilElementToBeClickable(element);
        action.moveToElement(element).moveToElement(element).moveByOffset(element.getSize().width/widthDivider, element.getSize().height/highDivider).click().build().perform();
        return element;
    }

    protected WebElement waitUtilElementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected WebElement waitUtilElementToBeVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void fillInputField(WebElement field, String value) {
        scrollToElementJs(field);
        waitUtilElementToBeClickable(field).click();
        field.sendKeys(value);
    }

    protected void fillDateField(WebElement field, String value) {
        scrollToElementJs(field);
        field.sendKeys(value);
    }

    protected WebElement takeNeededWebElement(WebElement common, String byXpath, String textParameter) {
        WebElement neededElement = common.findElements(By.xpath("."+byXpath)).stream()
                .filter(x->x.getText().contains(textParameter))
                .findAny().orElse(null);
        if (neededElement == null) {
            Assertions.fail("WebElement с текстовым параметром:'"+textParameter+"' не найден.");
        }
        return neededElement;
    }
}
