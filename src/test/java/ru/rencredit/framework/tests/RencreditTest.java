package ru.rencredit.framework.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.rencredit.framework.basetestsclass.BaseTests;

@Tag("rencreditAll")
@DisplayName("Набор тестов для калькулятора rencredit.ru")
public class RencreditTest extends BaseTests {

    @Test
    @Tag("rencreditCalculatorTest")
    @DisplayName("ru.rencredit - тест калькулятора доходности RUB")
    public void calculatorRubTest() {
        pageManager.getStartPage()
                .checkStartPage()
                .clickToNeededTab("Вклады")
                .clickToNeededItem("Калькулятор доходности").getCalculatorPage()
                .checkCalculatorPage()
                .selectCurrency("Рубли") //  Валюта: Рубли // Доллары США
                .enterFirstAmount("300 000")
                .setDuration("6")   //месяцы (Value)
                .enterAmountPerMonth("50 000")
                .setCapitalizationTrue("true")
                .verifyDataPercent("15 478,55")
                .verifyDataAmountPerDuration("250 000")
                .verifyDataGrandAmount("565 478,55")
        ;
    }

    @Test
    @Tag("rencreditCalculatorTest")
    @DisplayName("ru.rencredit - тест калькулятора доходности USD")
    public void calculatorUsdTest() {
        pageManager.getStartPage()
                .checkStartPage()
                .clickToNeededTab("Вклады")
                .clickToNeededItem("Калькулятор доходности").getCalculatorPage()
                .checkCalculatorPage()
                .selectCurrency("Доллары США") //  Валюта: Рубли // Доллары США
                .enterFirstAmount("500 000")
                .setDuration("12")   //месяцы (Value)
                .enterAmountPerMonth("20 000")
                .setCapitalizationTrue("true") //true | false
                .verifyDataPercent("920,60")
                .verifyDataAmountPerDuration("220 000")
                .verifyDataGrandAmount("720 920,60")
        ;
    }

    @ParameterizedTest(name = "ru.rencredit - тест калькулятора доходности = {2}")
    @CsvSource(delimiter = '|',
    value = {
            "Вклады|Калькулятор доходности|Рубли|300 000|6|50 000|true|15 478,55|250 000|565 478,55",
            "Вклады|Калькулятор доходности|Доллары США|500 000|12|20 000|true|920,60|220 000|720 920,60"
    })
    @Tag("rencreditCalculatorParametrizedTestAll")
    @DisplayName(("ru.rencredit - тест калькулятора доходности"))
    public void calculatorParametrizedTest(
            String tabName, String itemName, String currencyName, String firstAmountValue, String durationValue,
            String amountPerMonthValue, String isCapitalization, String percent, String amountPerDuration, String grandAmount
    ) {
        //  mvn clean test -Dmytag=rencreditCalculatorParametrizedTestAll allure:serve
        pageManager.getStartPage()
                .checkStartPage()
                .clickToNeededTab(tabName)
                .clickToNeededItem(itemName).getCalculatorPage()
                .checkCalculatorPage()
                .selectCurrency(currencyName) //  Валюта: Рубли // Доллары США
                .enterFirstAmount(firstAmountValue)
                .setDuration(durationValue)   //месяцы (Value)
                .enterAmountPerMonth(amountPerMonthValue)
                .setCapitalizationTrue(isCapitalization) //true | false
                .verifyDataPercent(percent)
                .verifyDataAmountPerDuration(amountPerDuration)
                .verifyDataGrandAmount(grandAmount)
        ;
    }
}
