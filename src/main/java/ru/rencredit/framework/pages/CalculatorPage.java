package ru.rencredit.framework.pages;

import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class CalculatorPage extends BasePage{

    @FindBy(xpath = "//div[contains(@class, 'js-deposits-calculator')]")
    private WebElement calculatorBlock;

    @Step("Проверка перехода на страницу сайта 'https://rencredit.ru/contributions/'")
    public CalculatorPage checkCalculatorPage(){
        WebElement calculatorPageTitle = calculatorBlock.findElement(By.xpath("./../h2"));
        waitUtilElementToBeVisible(calculatorPageTitle);
        Assertions.assertEquals( "Рассчитайте доходность по вкладу", calculatorPageTitle.getText(),
                "Заголовок '"+calculatorPageTitle.getText()+"' не соответствует требуемому 'Рассчитайте доходность по вкладу'");
        return this;
    }

    @Step("Выбор валюты: '{currencyName}'")
    public CalculatorPage selectCurrency(String currencyName) {
        /**
         * Клик на нужную валюту
         */
        waitUtilElementToBeClickable(
                takeNeededWebElement(calculatorBlock,
                        "//div[contains(@class, 'currency')]//span[contains(@class, 'btn')]",
                        currencyName)
        ).click();

        /**
         * Проверка валюты на странице
         */
        WebElement currencyElement = calculatorBlock.findElement(By.xpath("//div[contains(@class, 'dep-result-value')]//span[@class='js-calc-currency']"));

        String currencySymbol = "";
        switch (currencyName) {
            case "Доллары США":
                currencySymbol = "$";
                break;
            case "Рубли":
                currencySymbol = "p";
                break;
            default:
                Assertions.fail("Введённая валюта не была идентифицирована. Возможные варианты ввода: 'Рубли', 'Доллары США'");
                break;
        }

        Assertions.assertTrue(
                wait.until(ExpectedConditions.attributeContains(currencyElement, "innerText", currencySymbol)),
                "Валюта найденная на сайте:'"+currencyElement.getAttribute("innerText")+"', когда как ожидалась:'"+currencySymbol+"'.");
        ;
        return this;
    }

    @Step("Ввод первоначальной суммы '{amountValue}'")
    public CalculatorPage enterFirstAmount(String amountValue) {
        WebElement amountElement = calculatorBlock.findElement(By.xpath(".//input[@name='amount']"));
        waitUtilElementToBeClickable(amountElement).sendKeys(amountValue);
        Assertions.assertTrue(
                wait.until(ExpectedConditions.attributeContains(amountElement, "value", amountValue)),
                "Выведенное количество денег не совпала с введённой"
        );
        Assertions.assertTrue(
                wait.until(ExpectedConditions.attributeContains(calculatorBlock.findElement(By.xpath(".//span[@class=\"js-calc-amount\"]")),
                        "innerText", amountValue)),
                "Выведенное количество денег не совпала с введённой"
        );
        return this;
    }

    @Step("Выбор срока: '{durationValue}'")
    public CalculatorPage setDuration(String durationValue) {
        select = new Select(calculatorBlock.findElement(By.xpath(".//select[@name = 'period']")));
        select.selectByValue(durationValue);
        Assertions.assertTrue(
                wait.until(ExpectedConditions.attributeContains(
                        calculatorBlock.findElement(By.xpath(".//tbody//span[@class = 'js-calc-period']")),
                        "outerText",
                        durationValue
                )),
                "Длительность не равна "+durationValue+" (месяцы)"
        );
        return this;
    }

    @Step("Ввод первоначальной суммы '{amountValue}'")
    public CalculatorPage enterAmountPerMonth(String amountValue) {
        WebElement amountElement = calculatorBlock.findElement(By.xpath(".//input[@name='replenish']"));
        waitUtilElementToBeClickable(amountElement).sendKeys(amountValue);
        Assertions.assertTrue(
                wait.until(ExpectedConditions.attributeContains(amountElement, "value", amountValue)),
                "Выведенное количество денег:"+amountElement.getAttribute("value")+". не совпала с введённой:"+amountValue
        );
        return this;
    }

    @Step("Установка ежемесячной капитализации в положение 'Да'")
    public CalculatorPage setCapitalizationTrue(String isOn) {

        if(isOn.equals("true")) {
            WebElement capitalizationInput = calculatorBlock.findElement(By.xpath(".//input[@type='checkbox' and @name='capitalization']/../.."));
            waitUtilElementToBeClickable(capitalizationInput).click();

            Assertions.assertTrue(
                    wait.until(ExpectedConditions.attributeContains(
                            capitalizationInput.findElement(By.xpath("./div")),
                            "class",
                            "checked"
                    )),
                    "Чек-бокс ежемесячной капитализации не был установлен в положение 'Да'"
            );
        }
        return this;
    }

    @Step("Проверка введённых данных 'Начислено %'")
    public CalculatorPage verifyDataPercent(String percent) {
        WebElement percentElement = calculatorBlock.findElement(By.xpath(".//span[@class = 'js-calc-earned']"));
        Assertions.assertTrue(
                wait.until(ExpectedConditions.textToBePresentInElement(percentElement, percent)),
                "Поле 'Начислено %' не сходится с проверочными данными. Должно быть: " + percent+"; Стало: " + percentElement.getText()
        );
        Assertions.assertEquals(percent, percentElement.getAttribute("innerText"), "Поле 'Начислено %' не сходится с проверочными данными");
        return this;
    }

    @Step("Проверка введённых данных 'Пополнение за N месяцев'")
    public CalculatorPage verifyDataAmountPerDuration(String amountPerDuration) {
        WebElement amountPerDurationElement = calculatorBlock.findElement(By.xpath(".//span[@class = 'js-calc-replenish']"));
        Assertions.assertTrue(
                wait.until(ExpectedConditions.textToBePresentInElement(amountPerDurationElement, amountPerDuration)),
                "Поле 'Пополнение за N месяцев' не сходится с проверочными данными. Должно быть: " + amountPerDuration+"; Стало: " + amountPerDurationElement.getText()
        );
        Assertions.assertEquals(amountPerDuration, amountPerDurationElement.getAttribute("innerText"), "Поле 'Пополнение за 12 месяцев' не сходится с проверочными данными");
        return this;
    }

    @Step("Проверка введённых данных 'К снятию через N месяцев'")
    public CalculatorPage verifyDataGrandAmount(String grandAmount) {
        WebElement grandAmountElement = calculatorBlock.findElement(By.xpath(".//span[@class = 'js-calc-result']"));
        Assertions.assertTrue(
                wait.until(ExpectedConditions.textToBePresentInElement(grandAmountElement, grandAmount)),
                "Поле 'К снятию через .. месяцев' не сходится с проверочными данными. Должно быть: " + grandAmount+"; Стало: " + grandAmountElement.getText()
        );
        Assertions.assertEquals(grandAmount, grandAmountElement.getAttribute("innerText"), "Поле 'К снятию через ..' не сходится с проверочными данными");
        return this;
    }
}
