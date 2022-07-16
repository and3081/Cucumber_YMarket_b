package custom.properties;

import org.aeonbits.owner.ConfigFactory;

/**
 * Класс экзекутор для работы с проперти
 * usage:  TestData.props.имяМетодаКлюча()
 */
public class TestData {
    /**
     * static метод для работы с проперти из файлов:
     * application.properties
     * browser.properties
     * listener.properties
     */
    public static Application application = ConfigFactory.create(Application.class);
    public static Browser browser = ConfigFactory.create(Browser.class);
    public static Listener listener = ConfigFactory.create(Listener.class);
}
