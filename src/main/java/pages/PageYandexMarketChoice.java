package pages;

import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;
import org.junit.Assert;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Page класс страницы Яндекс Маркет (выбор)
 */
public class PageYandexMarketChoice extends BasePage {
    private int versionPage = 2;
    /**
     * xPath меню 'Хлебные крошки'
     */
    public static final String XPATH_CRUMBS = "//a/span[./@itemprop]";
    /**
     * xPath базовый блока выбора 'Производителей'
     */
    public String XPATH_BASE_FACTORIES = "//fieldset[.//legend[.//text()[contains(.,'Производитель')]]]";
    /**
     * xPath кнопки 'Показать всё' производителей
     */
    public String XPATH_ALL_FACTORIES_BUTTON = XPATH_BASE_FACTORIES + "//span[@role='button']/span";
    /**
     * xPath поля поиска производителей
     */
    public String XPATH_FACTORIES_SEARCH = XPATH_BASE_FACTORIES + "//div[label[text()='Найти производителя']]//input";
    /**
     * xPath итема в списке производителей
     */
    public String XPATH_FACTORIES_ITEM = XPATH_BASE_FACTORIES + "//label[.//input[@type='checkbox']]//span[text()]";
    /**
     * xPath элемента для анализа появления и пропадания серого экрана
     */
    public String XPATH_CHOICE_PROGRESS = "//div[@aria-label='Результаты поиска']/parent::div/div";
    /**
     * xPath названий списка показанных товаров
     */
    public static final String XPATH_SEARCHED_ARTICLES_TEXT =
            "//div[@aria-label='Результаты поиска']//article//a[@title]//span[text()]";
    /**
     * xPath меню пагинации
     */
    public String XPATH_PAGINATION_BUTTONS = "//div[@data-auto[contains(.,'pagination')]]";

    /**
     * Шаг Проверить название открытого подраздела в крошках
     * @param step номер шага для аллюра
     * @param name наименование подраздела
     * @return свой PO
     */
    @Step("step {step}. Проверить название открытого подраздела '{name}' в крошках")  // step 9
    public PageYandexMarketChoice checkNameInCrumbs(String step, String name) {
        $$x(XPATH_CRUMBS).shouldBe(sizeGreaterThanOrEqual(3)).get(2)
                .should(be(visible), have(exactText(name)));
        return this;
    }

    /**
     * Шаг Нажать кнопку производителей 'Показать всё'
     * @param step номер шага для аллюра
     * @return свой PO
     */
    @Step("step {step}. Нажать кнопку производителей 'Показать всё'")  // step 10
    public PageYandexMarketChoice clickAllFactoriesButton(String step) {
        waitRealClick($x(XPATH_ALL_FACTORIES_BUTTON).shouldBe(visible, enabled), XPATH_ALL_FACTORIES_BUTTON);
        return this;
    }

    /**
     * Шаг Ввести в поле выбора название производителя
     * @param step        номер шага для аллюра
     * @param nameFactory наименование производителя
     * @return свой PO
     */
    @Step("step {step}. Ввести в поле выбора название производителя '{nameFactory}'")  // step 11
    public PageYandexMarketChoice inputFactorySearch(String step, String nameFactory) {
        $x(XPATH_FACTORIES_SEARCH).shouldBe(visible, enabled).setValue(nameFactory).pressEnter();
        return this;
    }

    /**
     * Шаг Поставить чекбокс производителя и ожидать выборку
     * @param step        номер шага для аллюра
     * @param nameFactory наименование производителя
     * @return свой PO
     */
    @Step("step {step}. Поставить чекбокс производителя '{nameFactory}' и ожидать выборку")  // step 12
    public PageYandexMarketChoice clickFactoryItemAndWait(String step, String nameFactory) {
        waitRealClick($x(XPATH_FACTORIES_ITEM)
                .should(be(visible), be(enabled), have(exactText(nameFactory))),
                XPATH_FACTORIES_ITEM);
        waitEndChoice();
        return this;
    }

    /**
     * Шаг Проверить все найденные товары для производителя на всех страницах
     *     (пока есть кнопка пагинации 'Вперед')
     * @param step        номер шага для аллюра
     * @param nameFactory наименование производителя
     * @return свой PO
     */
    @Step("step {step}. Проверить все найденные товары для производителя '{factory}' на всех страницах")  // step 14
    public PageYandexMarketChoice checkAllPagesArticlesName(String step, String nameFactory) {
        int i = 100;  // предохранитель
        do { checkSearchedArticlesName(nameFactory);
        } while ((--i >0) && isClickButtonForwardAndWait());
        return this;
    }

    /**
     * Проверка всех найденных товаров для производителя на текущей странице
     * @param nameFactory наименование производителя
     * @return свой PO
     */
    public PageYandexMarketChoice checkSearchedArticlesName(String nameFactory) {
        ElementsCollection list = $$x(XPATH_SEARCHED_ARTICLES_TEXT).shouldBe(sizeGreaterThan(0))
                .excludeWith(text(nameFactory));  //.shouldBe(empty);
        if (list.size() > 0) {
            Assert.fail("Обнаружен производитель не '" + nameFactory + "': " + list.get(0).getText());
        }
        return this;
    }

    /**
     * Переход на следующую страницу кнопкой пагинации 'Вперед', если она есть, и ожидание выборки
     * @return true- есть кнопка и клик 'Вперед'
     */
    public boolean isClickButtonForwardAndWait() {
        ElementsCollection listFiltered;
        listFiltered = $$x(XPATH_PAGINATION_BUTTONS)  //.shouldBe(sizeGreaterThan(0))  м.не быть if 1 экран
                .filterBy(attribute("data-auto", "pagination-next"));
        if (listFiltered.size()>0 && waitRealClick(listFiltered.get(0)
                .scrollIntoView(false).shouldBe(visible, enabled), null)) {  // scroll тут на всяк, иначе иногда не попадает клик
            waitEndChoice();
            return true;
        }
        return false;
    }

    /**
     * Ожидание завершения выборки (появление и пропадание серого экрана)
     * @return свой PO
     */
    public PageYandexMarketChoice waitEndChoice() {
        $$x(XPATH_CHOICE_PROGRESS).shouldBe(sizeGreaterThan(1));
        $$x(XPATH_CHOICE_PROGRESS).shouldBe(size(1));
        return this;
    }
}
