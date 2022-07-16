package pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

/**
 * Page класс поисковой страницы https://yandex.ru/
 */
public class PageYandexSearch extends BasePage {
    /**
     * xPath клик-иконки Яндекс Маркета
     */
    private static final String XPATH_ICON_YANDEX_MARKET = "//a[@data-id='market']";

    /**
     * Шаг Проверить title страницы
     * @param step номер шага для аллюра
     * @return свой PO
     */
    public PageYandexSearch checkYandexTitle(String step) {
        checkTitle(step, TITLE_YANDEX);
        return this;
    }

    /**
     * Шаг Перейти по иконке на Яндекс Маркет
     * @param step номер шага для аллюра
     * @return свой PO
     */
    @Step("step {step}. Перейти по иконке на Яндекс Маркет")  // step 3
    public PageYandexSearch clickYandexMarketAndSwitch(String step) {
        waitRealClick($x(XPATH_ICON_YANDEX_MARKET).shouldBe(visible, enabled), XPATH_ICON_YANDEX_MARKET);
        switchTo().window(TITLE_YANDEX_MARKET);
        return this;
    }
}
