package custom.properties;

import org.aeonbits.owner.Config;

/**
 * Интерфейс для работы с проперти из файла listener.properties и системными проперти
 * usage:  TestData.listener.имяМетодаКлюча()
 * Описание в файле проперти
 */
@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "system:env",
        "file:listener.properties"
})
public interface Listener extends Config {
    @Key("listener.type")
    String listenerType();

    @Key("listener.around.method")
    String listenerAroundMethod();

    @Key("listener.method.list")
    String listenerMethodList();

    @Key("listener.mode.elements")
    String listenerModeElements();
}
