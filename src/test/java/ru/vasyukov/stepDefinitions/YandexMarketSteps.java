package ru.vasyukov.stepDefinitions;

import pages.BasePage;
import pages.PageYandexMarketChoice;
import pages.PageYandexMarketMain;
import pages.PageYandexSearch;
import io.cucumber.java.ru.*;

import java.util.List;

/**
 * Класс определений для кукумбера
 */
public class YandexMarketSteps {
    /**
     * Page Object-ы страниц
     */
    private static PageYandexSearch pageYandexSearch;
    private static PageYandexMarketMain pageYandexMarketMain;
    private static PageYandexMarketChoice pageYandexMarketChoice;

    @Когда("^Открываем Яндекс$")
    public void openYandex () {
        pageYandexSearch = BasePage.openFirstPageYandexSearch("1");
    }

    @Тогда("^Проверяем title страницы Яндекс$")
    public void checkTitleYandex () {
        pageYandexSearch.checkYandexTitle("2");
    }

    @Затем("^Переходим на Яндекс Маркет$")
    public void goToYandexMarket () {
        pageYandexMarketMain = pageYandexSearch.clickYandexMarketAndSwitch("3")
                .nextPageYandexMarketMain();
    }

    @Тогда("^Проверяем title страницы Яндекс Маркет$")
    public void checkTitleYandexMarket () {
        pageYandexMarketMain.checkYandexMarketTitle("4");
    }

    @Когда("^Открываем каталог и раздел$")
    public void openCatalogChapter (List<String> itemNameMenu) {
        pageYandexMarketMain.clickCatalogButton("5")
                .clickItemCatalog("6", itemNameMenu.get(0));
    }

    @Тогда("^Проверяем заголовок раздела$")
    public void checkHeadChapter (List<String> itemNameMenu) {
        pageYandexMarketMain.checkHeadChapterCatalog("7", itemNameMenu.get(0));
    }

    @Затем("^Открываем подраздел$")
    public void openSubChapter (List<String> itemNameSubMenu) {
        pageYandexMarketChoice = pageYandexMarketMain.clickItemCatalog("8", itemNameSubMenu.get(0))
                .nextPageYandexMarketChoice();
    }

    @Тогда("^Проверяем заголовок подраздела в крошках$")
    public void checkHeadSubChapter (List<String> itemNameSubMenu) {
        pageYandexMarketChoice.checkNameInCrumbs("9", itemNameSubMenu.get(0));
    }

    @Затем("^Раскрываем перечень производителей, ищем и отмечаем ([^ ]+)")
    public void unfoldFactoriesAndMark (String factory) {
        pageYandexMarketChoice.clickAllFactoriesButton("10")
                .inputFactorySearch("11", factory)
                .clickFactoryItemAndWait("12", factory);
    }

    @И("^Выбираем количество просмотра$")
    public void choiceViewCount (List<String> countForOld) {
        pageYandexMarketChoice.selectChoiceCountViewAndWaitForOld("13", countForOld.get(0));
    }

    @Тогда("^На всех найденных страницах проверяем производителя ([^ ]+)")
    public void checkAllPages (String factory) {
        pageYandexMarketChoice.checkAllPagesArticlesName("14", factory);
    }
}
