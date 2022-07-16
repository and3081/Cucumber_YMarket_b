package custom.screenshoters;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * Класс скриншотеров для аллюра. Могут вызываться листенерами или напрямую
 */
public class Screenshoter {
    /**
     * Скриншотер всего окна драйвера, аттачится в аллюр
     * @param name        начало названия скриншота (для аллюра)
     * @param driver      WebDriver
     * @param methodName  имя метода, обработанного листенером (для аллюра)
     * @param type        название типа: аргументы ПЕРЕД или возврат ПОСЛЕ метода (для аллюра)
     * @param values      значения: аргументов ПЕРЕД или возврата ПОСЛЕ метода (для аллюра)
     * @return            байт-массив скриншота, автоматически аттачится в аллюр
     */
    @Attachment(value = "{name} | {methodName} | {type} {values}")
    public static byte[] getScreenDriver(String name, WebDriver driver, String methodName, String type, String values){
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }

    /**
     * Скриншотер конкретного элемента, аттачится в аллюр
     * @param name        начало названия скриншота (для аллюра)
     * @param el          WebElement
     * @param methodName  имя метода, обработанного листенером (для аллюра)
     * @param type        название типа: аргументы ПЕРЕД или возврат ПОСЛЕ метода (для аллюра)
     * @param values      значения: аргументов ПЕРЕД или возврата ПОСЛЕ метода (для аллюра)
     * @return            байт-массив скриншота, автоматически аттачится в аллюр
     */
    @Attachment(value = "{name} | {methodName} | {type} {values}")
    public static byte[] getScreenElement(String name, WebElement el, String methodName, String type, String values){
        return el.getScreenshotAs(OutputType.BYTES);
    }

    /**
     * Скриншотер всего окна драйвера со скроллом на конкретный элемент, аттачится в аллюр
     * ВНИМАНИЕ ! Перемещает указатель мыши, может нарушить логику теста
     * @param name        названия скриншота (для аллюра)
     * @param driver      WebDriver
     * @param el          WebElement
     * @return            байт-массив скриншота, автоматически аттачится в аллюр
     */
    @Attachment(value = "{name}")
    public static byte[] getScreenMoveToElem(String name, WebDriver driver, WebElement el){
        new Actions(driver).moveToElement(el).perform();
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }
}
