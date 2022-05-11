package ru.vasyukov.Hooks;

import Custom.listeners.Listeners;
import Custom.properties.TestData;
import com.codeborne.selenide.Configuration;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.events.WebDriverListener;

import static com.codeborne.selenide.WebDriverRunner.*;

/**
 * Класс для хуков (методы Before и After для каждого теста)
 * Настройка опций браузера и листенера выполняется один раз перед всеми тестами
 */
public class WebHooks {
    /**
     * Объект Listeners в зависимости от настройки в проперти или null
     */
    private static final WebDriverListener listener = Listeners.getListener();

    // Настройка опций браузера и листенера первый раз
    static {
        Configuration.timeout = Long.parseLong(TestData.props.defaultTimeoutImplicitMs());
        if (listener != null) addListener(listener);
        if (TestData.props.headlessMode() != null) Configuration.headless = true;
        if (TestData.props.dontCloseBrowser() != null) Configuration.holdBrowserOpen = true;
        if (TestData.props.remoutUrl() != null) Configuration.remote = TestData.props.remoutUrl();

        if ((TestData.props.typeBrowser() == null || TestData.props.typeBrowser().equals("chrome")) &&
                TestData.props.webdriverChromeLocalPath() != null) {
            System.setProperty("webdriver.chrome.driver", TestData.props.webdriverChromeLocalPath());
            WebDriver driver = new ChromeDriver();
            setWebDriver(driver);
        } else if (TestData.props.typeBrowser() != null)
            if (TestData.props.typeBrowser().equals("edge")) {
                System.setProperty("webdriver.edge.driver", TestData.props.webdriverEdgeLocalPath());
                WebDriver driver = new EdgeDriver();
                setWebDriver(driver);
            } else Configuration.browser = TestData.props.typeBrowser();
        else Configuration.browser = "chrome";
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("start-maximized");  // устарел, use  getWebDriver().manage().window().maximize();
//        DesiredCapabilities capabilities = new DesiredCapabilities();  // old
//        MutableCapabilities capabilities = new MutableCapabilities();  // new
//        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
//        Configuration.browserCapabilities = capabilities;
    }

    /**
     * перед каждым тестом (тут нет, open в тесте),
     * но нужен для инициализации static
     */
    @Before
    public void openBrowser() {
    }

    /**
     * Закрытие браузера после каждого теста,
     * необходимо при повторе теста по параметризованным производителям
     */
    @After
    public void closeBrowser() {
        //closeWindow();  // holdBrowserOpen с этим не работает
        closeWebDriver();
    }
}
