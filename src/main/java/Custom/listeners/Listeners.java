package Custom.listeners;

import Custom.Screenshoters.Screenshoter;
import Custom.properties.TestData;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.WebDriverListener;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс трех вариантов листенеров
 */
public class Listeners implements WebDriverListener {
    /**
     * Запоминается драйвер от последнего вызова драйвер-листенера или null
     * Используется для листенера по элементу для скрина всего окна
     */
    private static WebDriver lastListenedDriver;
    /**
     * Тип листенера из проперти
     */
    private static final String listenerType = TestData.listener.listenerType();
    /**
     * Режим скриншотов вокруг метода из проперти
     */
    private static final String listenerAroundMethod = TestData.listener.listenerAroundMethod();
    /**
     * Список методов для скриншотов из проперти (через запятую, например  click,setValue)
     */
    private static final String listenerMethodList = TestData.listener.listenerMethodList();
    /**
     * true- есть инфа в списке методов
     */
    private static boolean isListenerMethodList = false;
    /**
     * Расшифровка списка методов из проперти
     */
    private static List<String> MethodList;
    /**
     * Mode скриншотов для листенера с элементами из проперти
     */
    private static final String listenerModeElements = TestData.listener.listenerModeElements();

    static {
        if (listenerMethodList !=null && !listenerMethodList.trim().isEmpty()) {
            MethodList = Arrays.stream(listenerMethodList.trim().split(","))
                    .map(String::trim)
                    .collect(Collectors.toList());
            if (!MethodList.isEmpty()) isListenerMethodList = true;
        }
    }

    /**
     * Геттер одного из трех вариантов листенеров в зависимости от настройки проперти
     * @return листенер
     */
    public static WebDriverListener getListener() {
        if (listenerType != null) {
            switch (listenerType) {
                case "all": return listenerAll;
                case "driver": return listenerWebDriver;
                case "elements": return listenerWebElement;
            }
        }
        return null;
    }

    /**
     * Проверка имени метода по списку для выборки
     * @param nameMethod имя метода для листенера
     * @return true- выполнять действие (все методы или есть в списке для выборки)
     */
    private static boolean checkMethod(String nameMethod) {
        return !isListenerMethodList || MethodList.contains(nameMethod);
    }

    /**
     * Геттер одного из вариантов Вокруг в зависимости от настройки проперти
     * @return 1 перед действием, 2 вокруг, 3 после действия (+click перед)
     */
    private static int getListenerAround() {
        if (listenerAroundMethod != null) {
            switch (listenerAroundMethod) {
                case "before": return 1;
                case "after": return 3;
            }
        }
        return 2;
    }

    /**
     * Геттер одного из вариантов Mode скриншота элементов в зависимости от настройки проперти
     * @return Mode 0 both, 1 window, 2 element
     */
    private static int getListenerModeElements() {
        if (listenerModeElements != null) {
            switch (listenerModeElements) {
                case "both": return 0;
                case "window": return 1;
            }
        }
        return 2;
    }

    /**
     * Действие ПЕРЕД для общего листенера (скриншот)
     * @param target объект назначения: WebDriver или WebElement
     * @param method метод назначения (перед которым исполнился листенер)
     * @param args   аргументы метода назначения
     */
    private static void actionBeforeAll(Object target, Method method, Object[] args) {
        if (target instanceof WebDriver)
            actionBeforeWebDriver((WebDriver) target, method, args);
        else if (target instanceof WebElement)
            actionBeforeWebElement(lastListenedDriver, (WebElement) target, method, args);
    }

    /**
     * Действие ПОСЛЕ для общего листенера (скриншот)
     * @param target объект назначения: WebDriver или WebElement
     * @param method метод назначения (после которого исполнился листенер)
     * @param args   аргументы метода назначения
     * @param result возврат метода назначения (null для void)
     */
    private static void actionAfterAll(Object target, Method method, Object[] args, Object result) {
        if (target instanceof WebDriver)
            actionAfterWebDriver((WebDriver) target, method, args, result);
        else if (target instanceof WebElement)
            actionAfterWebElement(lastListenedDriver, (WebElement) target, method, args, result);
    }

    /**
     * Действие ПЕРЕД для листенера действий с драйвером (скриншот окна)
     * @param driver объект назначения: WebDriver
     * @param method метод назначения (перед которым исполнился листенер)
     * @param args   аргументы метода назначения
     */
    private static void actionBeforeWebDriver(WebDriver driver, Method method, Object[] args) {
        lastListenedDriver = driver;
        if (getListenerAround()<=2 && checkMethod(method.getName())) {
            Screenshoter.getScreenDriver("Перед", driver, method.getName(), "args:  ", Arrays.toString(args));
        }
    }

    /**
     * Действие ПОСЛЕ для листенера действий с драйвером (скриншот окна)
     * @param driver объект назначения: WebDriver
     * @param method метод назначения (после которого исполнился листенер)
     * @param args   аргументы метода назначения
     * @param result возврат метода назначения (null для void)
     */
    private static void actionAfterWebDriver(WebDriver driver, Method method, Object[] args, Object result) {
        lastListenedDriver = driver;
        if (getListenerAround()>=2 && checkMethod(method.getName())) {
            Screenshoter.getScreenDriver("После", driver, method.getName(), "return:",
                    (result == null) ? "void" : result.toString());
        }
    }

    /**
     * Действие ПЕРЕД для листенера действий с элементом (скриншот окна или элемента см.listenerModeElements)
     * @param lastListenedDriver  драйвер от последнего вызова драйвер-листенера или null
     * @param el     объект назначения: WebElement
     * @param method метод назначения (перед которым исполнился листенер)
     * @param args   аргументы метода назначения
     */
    private static void actionBeforeWebElement(WebDriver lastListenedDriver, WebElement el, Method method, Object[] args) {
        // метод click- всегда, т.к.после его м.не быть из-за смены страницы
        if ((getListenerAround()<=2 || method.getName().equals("click")) && checkMethod(method.getName())) {
            if (lastListenedDriver != null && getListenerModeElements() != 2) {
                new Actions(lastListenedDriver).moveToElement(el).perform();
                Screenshoter.getScreenDriver("Перед", lastListenedDriver, method.getName(), "args:  ",
                        Arrays.toString(args));
            }
            if (lastListenedDriver == null || getListenerModeElements() != 1) {
                Screenshoter.getScreenElement("Перед", el, method.getName(), "args:  ",
                        Arrays.toString(args));
            }
        }
    }

    /**
     * Действие ПОСЛЕ для листенера действий с элементом (скриншот окна или элемента см.listenerModeElements)
     * @param lastListenedDriver  драйвер от последнего вызова драйвер-листенера или null
     * @param el     объект назначения: WebElement
     * @param method метод назначения (после которого исполнился листенер)
     * @param args   аргументы метода назначения
     * @param result возврат метода назначения (null для void)
     */
    private static void actionAfterWebElement(WebDriver lastListenedDriver, WebElement el, Method method, Object[] args, Object result) {
        if (getListenerAround()>=2 && checkMethod(method.getName())) {
            // если страница быстро среагирует на действие над элементом (напр заменится по клику),
            // то на момент попытки afterМетода el уже не существует, afterМетод проигнорится !
            if (lastListenedDriver != null && getListenerModeElements() != 2) {
                new Actions(lastListenedDriver).moveToElement(el).perform();
                Screenshoter.getScreenDriver("После", lastListenedDriver, method.getName(), "return:",
                        (result == null) ? "void" : result.toString());
            }
            if (lastListenedDriver == null || getListenerModeElements() != 1) {
                Screenshoter.getScreenElement("После", el, method.getName(), "return:",
                        (result == null) ? "void" : result.toString());
            }
        }
    }

    /**
     * Общий листенер (запускается для всех типов действий)
     * Переопределяет соответствующие методы интерфейса и назначает им методы
     */
    private static final WebDriverListener listenerAll = new WebDriverListener() {
        @Override
        public void beforeAnyCall(Object target, Method method, Object[] args) {
            actionBeforeAll(target, method, args);
        }
        @Override
        public void afterAnyCall(Object target, Method method, Object[] args, Object result) {
            actionAfterAll(target, method, args, result);
        }
    };

    /**
     * Листенер для всех действий с драйвером
     * Переопределяет соответствующие методы интерфейса и назначает им методы
     */
    private static final WebDriverListener listenerWebDriver = new WebDriverListener() {
        @Override
        public void beforeAnyWebDriverCall(WebDriver driver, Method method, Object[] args) {
            actionBeforeWebDriver(driver, method, args);
        }
        @Override
        public void afterAnyWebDriverCall(WebDriver driver, Method method, Object[] args, Object result) {
            actionAfterWebDriver(driver, method, args, result);
        }
    };

    /**
     * Листенер для всех действий с элементами
     * Переопределяет соответствующие методы интерфейса и назначает им методы
     */
    private static final WebDriverListener listenerWebElement = new WebDriverListener() {
        @Override
        public void beforeAnyWebElementCall(WebElement element, Method method, Object[] args) {
            actionBeforeWebElement(lastListenedDriver, element, method, args);
        }
        @Override
        public void afterAnyWebElementCall(WebElement element, Method method, Object[] args, Object result) {
            // если страница быстро среагирует на действие над элементом (напр заменится по клику),
            // то на момент попытки afterМетода el уже не существует, afterМетод проигнорится !
            actionAfterWebElement(lastListenedDriver, element, method, args, result);
        }
    };
}
