package ru.vasyukov.Test;

import static Pages.BasePage.openFirstPageYandexSearch;

/**
 * Класс тест-кейсов с настраиваемыми листенерами в проперти:
 *       - какие методы скринить: драйвер, элементы, все варианты или отключить
 *       - скрининг элементов:    всё окно, только элемент или оба скрина сразу
 *
 *       В аллюре скрины подписаны- перед и после какого метода, аргументы и возврат метода
 *
 *       - выбор браузера в проперти для прогона тестов:  Chrome, Edge
 *
 * @author Васюков А.Ю.  GitHub  https://github.com/and3081/Selenide_YMarket_b
 * @version 1.0
 * Описание тест-кейса:
 * 1. Открыть браузер и развернуть на весь экран.
 * 2. Зайти на yandex.ru.
 * 3. Перейти в яндекс маркет
 * 4. Выбрать раздел Электроника
 * 5. Выбрать раздел Смартфоны
 * 6. Задать параметр «Производитель» Apple.
 * 8. Дождаться результатов поиска.
 * 9. Установить количество показываемых элементов на страницу 48
 * 10. Убедится что в выборку попали только iPhone. Если страниц несколько – проверить все.
 * 11. Тест должен работать для любого производителя
 */
public class Tests {
    /**
     * Тест-кейс выборки и поиска в Яндекс-маркет (версии v.1 и v.2)
     * параметры поставляются провайдером данных providerYandexMarket()
     * @param itemNameMenu  список названий - Раздел и Подраздел каталога
     * @param factory        название Производителя (тест повторяется для разных производителей)
     * @param countForOld    (для старой версии) количество Просмотра (48 для ускорения тестирования)
     */
    public static void testYandexMarketChoice(String itemNameMenu, String itemNameSubMenu, String factory, String countForOld) {
        openFirstPageYandexSearch("1")
                .checkYandexTitle("2")
                .clickYandexMarketAndSwitch("3").nextPageYandexMarketMain()
                .checkYandexMarketTitle("4")
                .clickCatalogButton("5")
                .clickItemCatalog("6", itemNameMenu)
                .checkHeadChapterCatalog("7", itemNameMenu)
                .clickItemCatalog("8", itemNameSubMenu).nextPageYandexMarketChoice()
                .checkNameInCrumbs("9", itemNameSubMenu)
                .clickAllFactoriesButton("10")
                .inputFactorySearch("11", factory)
                .clickFactoryItemAndWait("12", factory)
                .selectChoiceCountViewAndWaitForOld("13", countForOld)
                .checkAllPagesArticlesName("14", factory);
    }
}
