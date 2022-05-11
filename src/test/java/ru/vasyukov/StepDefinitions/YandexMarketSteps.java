package ru.vasyukov.StepDefinitions;

import Pages.BasePage;
import Pages.PageYandexMarketChoice;
import Pages.PageYandexMarketMain;
import Pages.PageYandexSearch;
import io.cucumber.java.ru.*;

import java.util.List;

public class YandexMarketSteps {
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

    @Затем("^Открываем каталог и раздел$")
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

    @Затем("^Раскрываем перечень производителей, ищем и отмечаем нужного$")
    public void unfoldFactoriesAndMark (List<String> factory) {
        pageYandexMarketChoice.clickAllFactoriesButton("10")
                .inputFactorySearch("11", factory.get(0))
                .clickFactoryItemAndWait("12", factory.get(0));
    }

    @И("^Выбираем количество просмотра$")
    public void choiceViewCount (List<String> countForOld) {
        pageYandexMarketChoice.selectChoiceCountViewAndWaitForOld("13", countForOld.get(0));
    }

    @Тогда("^Проверяем производителя на всех найденных страницах$")
    public void checkAllPages (List<String> factory) {
        pageYandexMarketChoice.checkAllPagesArticlesName("14", factory.get(0));
    }
}
