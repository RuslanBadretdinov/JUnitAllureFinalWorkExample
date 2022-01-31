package ru.rencredit.framework.pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.rencredit.framework.managers.PageManager;

public class StartPage extends BasePage{

    @FindBy(xpath = "//div[contains(@class, \"site-header__content-bottom-nav\")]")
    private WebElement tabsBlock;

    @FindBy(xpath = "//li[contains(@class,'nav__item_parent')]/div[contains(@class, 'sub_opened')]")
    private WebElement openedTab;

    @Step("Проверка перехода на сайт 'https://rencredit.ru/'")
    public StartPage checkStartPage() {
        Assertions.assertTrue(wait.until(ExpectedConditions.attributeContains(tabsBlock, "baseURI", "https://rencredit.ru/")));
        return this;
    }

    @Step("Клик на вкладку '{tabName}'")
    public StartPage clickToNeededTab(String tabName) {
        WebElement neededTab = takeNeededWebElement(
                tabsBlock,
                "//li[contains(@class,'nav__item_parent')]/a",
                tabName);
        waitUtilElementToBeClickable(neededTab).click();
        WebElement checkNeededTab = neededTab.findElement(By.xpath("./../a[contains(@class,'opened')]"));
        waitUtilElementToBeClickable(checkNeededTab);
        Assertions.assertTrue(checkNeededTab.getAttribute("outerText").contains(tabName), "Вкладка содержащая '"+tabName+"' не загрузилась");
        return this;
    }

    @Step("Клик на кнопку/ссылку '{itemName}'")
    public PageManager clickToNeededItem(String itemName) {
        takeNeededWebElement(openedTab,
                "//ul/li",
                itemName).click();
        return pageManager;
    }
}
