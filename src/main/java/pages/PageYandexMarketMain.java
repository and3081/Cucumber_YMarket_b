package pages;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Page класс страницы Яндекс Маркет (главная и каталог)
 */
public class PageYandexMarketMain extends BasePage {
    /**
     * xPath кнопки 'Каталог'
     */
    public String XPATH_BUTTON_CATALOG = "//button[@id='catalogPopupButton']";
    /**
     * xPath пункта 'Раздел/Подраздел' (подставляется) в меню каталога
     */
    public String XPATH_ITEM_MENU = "//li//a[.//text()='REPLACEMENT']";
    /**
     * xPath заголовка 'Раздела' (подставляется) после его открытия
     */
    public String XPATH_HEAD_CHAPTER_CATALOG = "//div/h1[text()='REPLACEMENT']";

    /**
     * Шаг Проверить title страницы
     * @param step номер шага для аллюра
     * @return свой PO
     */
    public PageYandexMarketMain checkYandexMarketTitle(String step) {
        checkTitle(step, TITLE_YANDEX_MARKET);
        return this;
    }

    /**
     * Шаг Открыть каталог
     * @param step номер шага для аллюра
     * @return свой PO
     */
    @Step("step {step}. Открыть каталог")  // step 5
    public PageYandexMarketMain clickCatalogButton(String step) {
        waitRealClick($x(XPATH_BUTTON_CATALOG).shouldBe(visible, enabled));
        return this;
    }

    /**
     * Шаг Открыть раздел в меню каталога
     * @param step         номер шага для аллюра
     * @param itemNameMenu наименование раздела
     * @return свой PO
     */
    @Step("step {step}. Открыть в меню каталога '{itemNameMenu}'")  // step 6/8
    public PageYandexMarketMain clickItemCatalog(String step, String itemNameMenu) {
        waitRealClick($x(XPATH_ITEM_MENU.replace("REPLACEMENT", itemNameMenu)).shouldBe(visible, enabled));
        return this;
    }

    /**
     * Шаг Проверить название открытого раздела
     * @param step         номер шага для аллюра
     * @param itemNameMenu наименование раздела
     * @return свой PO
     */
    @Step("step {step}. Проверить название открытого раздела '{itemNameMenu}'")  // step 7
    public PageYandexMarketMain checkHeadChapterCatalog(String step, String itemNameMenu) {
        $x(XPATH_HEAD_CHAPTER_CATALOG.replace("REPLACEMENT", itemNameMenu)).shouldBe(visible);
        return this;
    }
}
